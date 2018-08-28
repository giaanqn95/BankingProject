package com.example.e7440.bankingproject.module.upload.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.connect_api.config.ApiClientDiff;
import com.example.e7440.bankingproject.connect_api.config.ApiInterfaceDiff;
import com.example.e7440.bankingproject.connect_api.responses.ResponseApi;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.model.Image;
import com.example.e7440.bankingproject.module.upload.adapter.ImageAdapter;
import com.example.e7440.bankingproject.module.upload.adapter.StartSnapHelper;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.e7440.bankingproject.module.main.MainActivity.dataJSON;

public class UploadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_back_loan)
    Button mButtonBack;
    @BindView(R.id.img_camera)
    ImageView mImageView;
    @BindView(R.id.img_view)
    ImageView mImageView_Test;
    @BindView(R.id.rv_upload)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_next_upload)
    Button mButtonUpload;

    private Image image;
    private List<Image> imageList;
    private ImageAdapter imageAdapter;
    private ResponseApi mResponseApi;

    private static final int CHOOSE_PLACES = 120,
            DIALOG_SHOW_IMAGE = 130,
            ACTIVITY_START_CAMERA_APP = 0,
            CAMERA_PIC_REQUEST = 100;
    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private boolean backPressedToExitOnce = false;
    private String GALLERY_LOCATION = "image gallery";
    private String mImageFileLocation = "";
    private Double mLat, mLng;
    private MultipartBody.Part part;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        getUniqueIMEIId(this);
        adapterClick();
        String imei = getUniqueIMEIId(this);
        Log.d("IMEI", imei);
        Log.d("JSONDATA", dataJSON);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ResponseApi responseApi) {
        mResponseApi = responseApi;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeAll();
    }

    private void init() {
        mButtonBack.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mButtonUpload.setOnClickListener(this);
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(UploadActivity.this, 3));
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        //I don't know how to explain it :)) just remove it, you will understand :))
        SnapHelper mSnapHelper = new StartSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    //The click event created the adapter and override it
    public void adapterClick() {
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                //remove item when click icon X
                deleteImage(imageList.get(position).getImage());
                imageList.remove(position);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickImage(int position) {
                //Open fullscreen image when click image item
                showDialogImage(DIALOG_SHOW_IMAGE, imageList.get(position).getImage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_loan: {
                finish();
                break;
            }
            case R.id.img_camera: {
                imagePicker();
                break;
            }
            case R.id.btn_next_upload: {
                uploadImage();
                break;
            }
        }
    }

    public void uploadImage() {
        Collections.sort(imageList);
        for (Image image : imageList) {
            new Thread(() -> {
                try {
                    File file = new File(image.getImage());
                    part = MultipartBody.Part.createFormData("files[]", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    callApiPost(part, image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void removeAll(){
        for (int i = 0; i < imageList.size(); i++) {
            deleteImage(imageList.get(i).getImage());
        }
        imageList.clear();
        imageAdapter.notifyDataSetChanged();
    }

    public void callApiPost(MultipartBody.Part image, Image imageFile) {
        RequestBody requestBodyKey = RequestBody.create(MediaType.parse("text/plain"), mResponseApi.getApiKey());
        ApiInterfaceDiff apiInterfaceDiff = ApiClientDiff.getRetrofit().create(ApiInterfaceDiff.class);
        apiInterfaceDiff.postImage(image, requestBodyKey).enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful()) {
                    Log.d("Success:", "Completed");
                    deleteImage(imageFile.getImage());
                    imageList.remove(imageFile);
                    imageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Log.d("Failure:", t.getMessage());
            }
        });
    }

    //Create dialog
    public void imagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.register_choose_image)).setItems(R.array.register_choose_photo, (dialog, i) -> {
            if (i == 0) {
                takePhoto();
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PLACES) {
            if (resultCode == RESULT_OK) {
                //Open google API Places
                Place place = PlacePicker.getPlace(data, this);
                String mLocation = String.format("%s", place.getAddress());
                mLat = place.getLatLng().latitude;
                mLng = place.getLatLng().longitude;
            }
        } else if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            //Add image into list after capture
            int size = mImageFileLocation.length();
            image = new Image(mImageFileLocation, size);
            imageList.add(image);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResultItem) {
        super.onClickDialog(dialogResultItem);
        switch (dialogResultItem.getDialogId()) {
            case DIALOG_SHOW_IMAGE: {
                mMessageDialogManger.onDimiss();
                break;
            }
        }
    }


    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }

    private boolean verifyCamerapermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PIC_REQUEST);
            return false;
        }
        return true;
    }

    private boolean verifyOpenCamera() {
        int camera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_CAMERA, CAMERA_PIC_REQUEST
            );
            return false;
        }
        return true;
    }

    //Open camera2 and create URI into memory device
    public void takePhoto() {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (callCameraApplicationIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.e7440.bankingproject",
                        photoFile);
                verifyCamerapermission();
                verifyOpenCamera();
                callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
            }
        }

    }

    //Create link file
    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }

    //Refresh folder contains the image
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    //No need  explain :))
    public void deleteImage(String imageFile) {
        String file_dj_path = Environment.getExternalStorageDirectory().getAbsolutePath() + imageFile;
        File fdelete = new File(imageFile).getAbsoluteFile();
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("aloola", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("aloola", "file not Deleted :" + file_dj_path);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> backPressedToExitOnce = false, 2000);
        }
//        new Handler().postDelayed(() -> {
//
//        }, 1000);
    }

}
