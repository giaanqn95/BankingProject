package com.example.e7440.bankingproject.module.upload;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.model.Image;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_back_loan)
    Button mButtonBack;
    @BindView(R.id.img_camera)
    ImageView mImageView;
    @BindView(R.id.img_view)
    ImageView mImageView_Test;
    @BindView(R.id.rv_upload)
    RecyclerView mRecyclerView;

    private Image image;
    private List<Image> imageList;
    private ImageAdapter imageAdapter;

    private static final int  CHOOSE_PLACES = 120, DIALOG_SHOW_IMAGE = 130, ACTIVITY_START_CAMERA_APP = 0;
    private String GALLERY_LOCATION = "image gallery";
    private String mImageFileLocation = "";
    private File mGalleryFolder;
    private Double mLat, mLng;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        createImageGallery();
        ButterKnife.bind(this);
        init();
        getUniqueIMEIId(this);
        adapterClick();
        String imei = getUniqueIMEIId(this);
        Log.d("AAAAA", imei);
    }

    private void init() {
        mButtonBack.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(UploadActivity.this, 3));
        mRecyclerView.setAdapter(imageAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public void adapterClick() {
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                imageList.remove(position);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickImage(int position) {
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
        }
    }

    public void imagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.register_choose_image)).setItems(R.array.register_choose_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    takePhoto();
                }
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PLACES) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String mLocation = String.format("%s", place.getAddress());
                mLat = place.getLatLng().latitude;
                mLng = place.getLatLng().longitude;
            }
        } else if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            image = new Image(mImageFileLocation);
            imageList.add(image);
            imageAdapter.notifyDataSetChanged();
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

    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs();
        }

    }

    public void takePhoto() {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }

}
