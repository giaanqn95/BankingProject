package com.example.e7440.bankingproject.module.detail_schedules;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.GooglePlaceAPI;
import com.example.e7440.bankingproject.components.message_dialog.DialogResultItem;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.model.DetailUser;
import com.example.e7440.bankingproject.module.model.Image;
import com.example.e7440.bankingproject.module.model.Schedules;
import com.example.e7440.bankingproject.module.schedules.SchedulesActivity;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.e7440.bankingproject.AppConstants.KEY_SECRET;
import static com.example.e7440.bankingproject.components.message_dialog.MessageDialogHelper.getHeightStatusbar;
import static com.example.e7440.bankingproject.components.message_dialog.MessageDialogHelper.getHeightToolbar;
import static com.example.e7440.bankingproject.module.introduce.IntroduceActivity.CHOOSE_PLACE;

public class DetailSchedulesActivity extends BaseActivity implements DetailSchedulesGeneral.DetailSchedulesView {

    public static final int ELECTRIC = 5;
    public static final int WATER = 6;
    public static final int NUMBER = 7;
    public static final int LAND = 8;
    private int bill = 0;

    private DetailSchedulesPresenterImpl detailSchedulesPresenter;
    public final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    Calendar calendar = Calendar.getInstance();
    private DatePickerDialog mDatePickerDialog;
    private int step = 0;

    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CHOOSE_PLACES = 120,
            DIALOG_SHOW_IMAGE = 130,
            ACTIVITY_START_CAMERA_APP = 0,
            CAMERA_PIC_REQUEST = 100;

    private String mImageFileLocation = "";
    private Image image;
    private List<Image> imageList = new ArrayList<>();
    private MultipartBody.Part part;


    //Basic Info
    private ConstraintLayout layoutBasicInfo;
    private Button mButtonConDuct, mButtonLater;
    private EditText mEditTextName, mEditTextEmail, mEditTextPhone, mEditTextAddress;
    private TextView mTextViewBirth, mTextViewTitle;
    private Double lat, log;
    private String mLocation = "";
    private String id_cumtomer = "";
    private JSONObject jsonObjectData = new JSONObject();
    private JSONObject jsonObjectDataDetail = new JSONObject();
    private String name, email, phone, birth, address;

    //Detail Info
    private ConstraintLayout layoutDetailInfo;
    private EditText mEditTextId, mEditTextPlaceOfIssue, mEditTextNationality, mEditTextResidence,
            mEditTextMarial, mEditTextOccupation, mEditTextIndustry, mEditTextPosition, mEditTextMonthly;
    private TextView mTextViewDateOfIssue;
    private Button mButtonContinue, mButtonCancel;
    private int count = 0;
    private Boolean checkId = false, checkPlace = false, checkNationality = false, checkResidence = false,
            checkMarial = false, checkOccupation = false, checkIndustry = false, checkPosition = false,
            checkMonthly = false, checkDate = false;

    //Image Info
    private ConstraintLayout layoutImage;
    private TextView mTextViewElectric, mTextViewWater, mTextViewNumber, mTextViewLand;
    private Button mButtonFinish, mButtonCancelImage;
    private ImageView mImageViewElectric, mImageViewWater, mImageViewNumber, mImageViewLand;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cases);
        initPresenter();
        initView();
        clickView();
        checkInputDetailInfo();
        dateTimePicker();
        Log.d("LIFE", "create");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Schedules schedules) {
        id_cumtomer = schedules.id_customer;
        if (step == 0) {
            if (schedules.getStatus().equals("waiting")) {
                Log.d("Getstatus", schedules.getStatus());
                layoutBasicInfo.setVisibility(View.VISIBLE);
                layoutDetailInfo.setVisibility(View.GONE);
                layoutImage.setVisibility(View.GONE);
                setBasicInfo(schedules);
            } else {
                layoutDetailInfo.setVisibility(View.VISIBLE);
                layoutBasicInfo.setVisibility(View.GONE);
                layoutImage.setVisibility(View.GONE);
                detailSchedulesPresenter.getDetail(id_cumtomer, KEY_SECRET);
                step = 1;
            }
        }
    }

    public void initPresenter() {
        detailSchedulesPresenter = new DetailSchedulesPresenterImpl(this);
    }

    public void initView() {
        //Basic Info
        layoutBasicInfo = findViewById(R.id.ct_basic_info);
        mButtonConDuct = findViewById(R.id.btn_conduct);
        mButtonLater = findViewById(R.id.btn_later);
        mEditTextName = findViewById(R.id.et_detail_name);
        mEditTextEmail = findViewById(R.id.et_detail_email);
        mEditTextPhone = findViewById(R.id.et_detail_phone);
        mEditTextAddress = findViewById(R.id.et_detail_address);
        mTextViewTitle = findViewById(R.id.tv_title_name);
        mTextViewBirth = findViewById(R.id.et_detail_birth);

        //Detail Info
        layoutDetailInfo = findViewById(R.id.ct_detail_info);
        mEditTextId = findViewById(R.id.et_detail_id_card);
        mEditTextPlaceOfIssue = findViewById(R.id.et_detail_place_of_issue);
        mEditTextNationality = findViewById(R.id.et_detail_nationality);
        mEditTextResidence = findViewById(R.id.et_detail_residence);
        mTextViewDateOfIssue = findViewById(R.id.et_detail_date_of_issue);
        mEditTextMarial = findViewById(R.id.et_detail_marial);
        mEditTextOccupation = findViewById(R.id.et_detail_occupation);
        mEditTextIndustry = findViewById(R.id.et_detail_industry);
        mEditTextPosition = findViewById(R.id.et_detail_position);
        mEditTextMonthly = findViewById(R.id.et_detail_monthly);
        mButtonContinue = findViewById(R.id.btn_continue);
        mButtonCancel = findViewById(R.id.btn_cancel);

        //Image Info
        layoutImage = findViewById(R.id.ct_image);
        mTextViewElectric = findViewById(R.id.tv_electric);
        mTextViewWater = findViewById(R.id.tv_water);
        mTextViewNumber = findViewById(R.id.tv_number);
        mTextViewLand = findViewById(R.id.tv_land);
        mButtonFinish = findViewById(R.id.btn_finish);
        mButtonCancelImage = findViewById(R.id.btn_cancel_image);
        mImageViewElectric = findViewById(R.id.iv_electric);
        mImageViewWater = findViewById(R.id.iv_water);
        mImageViewNumber = findViewById(R.id.iv_number);
        mImageViewLand = findViewById(R.id.iv_land);
        if (imageList.size() < 0) {
            mButtonFinish.setBackground(getResources().getDrawable(R.drawable.background_border_button_grey));
            mButtonFinish.setEnabled(false);
        } else {
            mButtonFinish.setBackground(getResources().getDrawable(R.drawable.background_border_button));
            mButtonFinish.setEnabled(true);
        }
    }

    public void setBasicInfo(Schedules basicInfo) {
        mEditTextName.setText(basicInfo.getName());
        mEditTextPhone.setText(basicInfo.getPhoneNumber());
        mTextViewDateOfIssue.setText(basicInfo.getDateOfBirth());
        mEditTextAddress.setText(basicInfo.getAddress());
        mTextViewTitle.setText(basicInfo.getName());
//        convertBasicData(basicInfo.getName(), "antran@yopmail.com", basicInfo.getPhoneNumber(), basicInfo.getDateOfBirth(), basicInfo.getAddress());
    }

    public void convertBasicData(String name, String email, String phone, String date, String address) {
        JSONObject jsonObjectBasic = new JSONObject();
        try {
            jsonObjectBasic.put("Name", name);
            jsonObjectBasic.put("Email", email);
            jsonObjectBasic.put("PhoneNumber", phone);
            jsonObjectBasic.put("DateOfBirth", date);
            jsonObjectBasic.put("Address", address);
            jsonObjectBasic.put("Lat", lat.toString());
            jsonObjectBasic.put("Lng", log.toString());
            jsonObjectBasic.put("Location", mLocation);
            jsonObjectData.put("data", jsonObjectBasic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clickView() {
        mTextViewBirth.setOnClickListener(view -> mDatePickerDialog.show());

        mButtonConDuct.setOnClickListener(view -> {
            showDialog();
            name = mEditTextName.getText().toString();
            email = mEditTextEmail.getText().toString();
            phone = mEditTextPhone.getText().toString();
            birth = mTextViewBirth.getText().toString();
            address = mEditTextAddress.getText().toString();
        });
        mButtonLater.setOnClickListener(view -> finish());

        mButtonContinue.setOnClickListener(view -> {
            String id = mEditTextId.getText().toString();
            String dateOfIssue = mTextViewDateOfIssue.getText().toString();
            String placeOfIssue = mEditTextPlaceOfIssue.getText().toString();
            String nationality = mEditTextNationality.getText().toString();
            String residence = mEditTextResidence.getText().toString();
            String marial = mEditTextMarial.getText().toString();
            String occupation = mEditTextOccupation.getText().toString();
            String industry = mEditTextIndustry.getText().toString();
            String position = mEditTextPosition.getText().toString();
            String monthly = mEditTextMonthly.getText().toString();
            if (!monthly.equals("")) {
                convertDetailData(id, dateOfIssue, placeOfIssue, nationality, residence,
                        marial, occupation, industry, position, monthly);
            }
            detailSchedulesPresenter.postDetailInfo(id_cumtomer, KEY_SECRET, jsonObjectDataDetail);
        });

        mButtonCancel.setOnClickListener(view -> showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.dialog_messages_cancel)));
//        mTextViewBirth.setOnClickListener(view -> );

        mTextViewDateOfIssue.setOnClickListener(view -> mDatePickerDialog.show());

        mTextViewElectric.setOnClickListener(view -> {
            takePhoto();
            bill = ELECTRIC;
        });

        mTextViewWater.setOnClickListener(view -> {
            takePhoto();
            bill = WATER;
        });

        mTextViewNumber.setOnClickListener(view -> {
            takePhoto();
            bill = NUMBER;
        });

        mTextViewLand.setOnClickListener(view -> {
            takePhoto();
            bill = LAND;
        });

        mButtonFinish.setOnClickListener(view -> {
            if (imageList.size() > 0) {
                Log.d("AAAAA", String.valueOf(imageList.size()));
                uploadImage();
                reload();
            }
        });

        mButtonCancelImage.setOnClickListener(view -> showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.dialog_messages_cancel)));

    }

    public void uploadImage() {
        Collections.sort(imageList);
        for (Image image : imageList) {
            Log.d("AAAAA", image.getImage());
            new Thread(() -> {
                try {
                    File file = new File(image.getImage());
                    part = MultipartBody.Part.createFormData("files[]", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    detailSchedulesPresenter.postSaveImage(id_cumtomer, KEY_SECRET, part);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void removeAll() {
        for (int i = 0; i < imageList.size(); i++) {
            deleteImage(imageList.get(i).getImage());
        }
        imageList.clear();
    }

    @Override
    public void postDataBasicSuccess() {
        layoutBasicInfo.setVisibility(View.GONE);
        layoutDetailInfo.setVisibility(View.VISIBLE);
        detailSchedulesPresenter.getDetail(id_cumtomer, KEY_SECRET);
        step = 1;
    }

    @Override
    public void postDataDetailSuccess() {
        layoutDetailInfo.setVisibility(View.GONE);
        layoutImage.setVisibility(View.VISIBLE);
        step = 2;
    }

    @Override
    public void getDetailSuccess(DetailUser detailUser) {
        mEditTextId.setText(detailUser.getiDCard());
        mTextViewDateOfIssue.setText(detailUser.getDateOfIssue());
        mEditTextPlaceOfIssue.setText(detailUser.getPlaceOfIssue());
        mEditTextNationality.setText(detailUser.getNationality());
        mEditTextResidence.setText(detailUser.getResidenceStatus());
        mEditTextMarial.setText(detailUser.getMarialStatus());
        mEditTextOccupation.setText(detailUser.getOccupation());
        mEditTextIndustry.setText(detailUser.getIndustry());
        mEditTextPosition.setText(detailUser.getPosition());
        mEditTextMonthly.setText(detailUser.getMonthlyIncome());
    }

    @Override
    public void postSaveImageSuccess() {

    }

    public void convertDetailData(String id, String dateOfIssue, String placeOfIssue, String nationality, String residence,
                                  String marial, String occupation, String industry, String position, String monthly) {
        JSONObject jsonObjectDetail = new JSONObject();
        try {
            jsonObjectDetail.put("ID Card", id);
            jsonObjectDetail.put("Date Of Issue", dateOfIssue);
            jsonObjectDetail.put("Place Of Issue", placeOfIssue);
            jsonObjectDetail.put("Nationality", nationality);
            jsonObjectDetail.put("Residence Status", residence);
            jsonObjectDetail.put("Marial Status", marial);
            jsonObjectDetail.put("Occupation", occupation);
            jsonObjectDetail.put("Industry", industry);
            jsonObjectDetail.put("Position", position);
            jsonObjectDetail.put("Monthly Income", monthly);
            jsonObjectDataDetail.put("data", jsonObjectDetail);
        } catch (Exception e) {

        }
    }

    public void checkInputDetailInfo() {
        mEditTextId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextBeforeChanged", String.valueOf(charSequence.length()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextChanged", String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkId = true;
                else
                    checkId = false;
                checkEmpty();
            }
        });
        mTextViewDateOfIssue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkDate = true;
                else
                    checkDate = false;
                checkEmpty();
            }
        });
        mEditTextPlaceOfIssue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkPlace = true;
                else
                    checkPlace = false;
                checkEmpty();
            }
        });
        mEditTextNationality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkNationality = true;
                else
                    checkNationality = false;
                checkEmpty();
            }
        });
        mEditTextResidence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkResidence = true;
                else
                    checkResidence = false;
                checkEmpty();
            }
        });
        mEditTextMarial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkMarial = true;
                else
                    checkMarial = false;
                checkEmpty();
            }
        });
        mEditTextOccupation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkOccupation = true;
                else
                    checkOccupation = false;
                checkEmpty();
            }
        });
        mEditTextIndustry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkIndustry = true;
                else
                    checkIndustry = false;
                checkEmpty();
            }
        });
        mEditTextPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkPosition = true;
                else
                    checkPosition = false;
                checkEmpty();
            }

        });
        mEditTextMonthly.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    checkMonthly = true;
                else
                    checkMonthly = false;
                checkEmpty();
            }

        });
    }

    public void checkEmpty() {
        if (checkId && checkDate && checkPlace && checkNationality && checkResidence
                && checkMarial && checkOccupation && checkIndustry && checkPosition && checkMonthly) {
            mButtonContinue.setEnabled(true);
            mButtonContinue.setBackground(getResources().getDrawable(R.drawable.background_border_button));
        } else {
            mButtonContinue.setEnabled(false);
            mButtonContinue.setBackground(getResources().getDrawable(R.drawable.background_border_button_grey));
        }
    }

    public void dateTimePicker() {
        mDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            mTextViewBirth.setText(dateFormatter.format(newDate.getTime()));
            mTextViewDateOfIssue.setText(dateFormatter.format(newDate.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_type_checkin, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height - getHeightToolbar(this) - getHeightStatusbar(this);
        dialog.setContentView(view);


        TextView textView = dialog.findViewById(R.id.tv_description);
        View viewDialog = dialog.findViewById(R.id.dialog_check);
        Button btnLocation = dialog.findViewById(R.id.btn_location);
        Button btnOTP = dialog.findViewById(R.id.btn_otp);
        Button btnCheck = dialog.findViewById(R.id.btn_check_in);
        Button btnNotYet = dialog.findViewById(R.id.btn_not_yet);
        btnLocation.setOnClickListener(v -> {
            btnCheck.setVisibility(View.VISIBLE);
            btnNotYet.setVisibility(View.VISIBLE);
            btnLocation.setVisibility(View.GONE);
            btnOTP.setVisibility(View.GONE);
            textView.setText("Have you came?");
        });
//        btnOTP.setOnClickListener(view -> );

        btnNotYet.setOnClickListener(v -> {
            btnCheck.setVisibility(View.GONE);
            btnNotYet.setVisibility(View.GONE);
            btnLocation.setVisibility(View.VISIBLE);
            btnOTP.setVisibility(View.VISIBLE);
            textView.setText("Please choose check-in channel");
        });

        btnCheck.setOnClickListener(v -> {
            GooglePlaceAPI.showGooglePlaces(DetailSchedulesActivity.this, CHOOSE_PLACE);
            if (!mLocation.equals("")) {
                textView.setText(mLocation);
            }
            dialog.dismiss();
        });

        viewDialog.setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PLACE) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                mLocation = String.format("%s", place.getAddress());
                lat = place.getLatLng().latitude;
                log = place.getLatLng().longitude;
                Log.d("GetLocation", lat + "\n " + log + "\n" + mLocation);
                convertBasicData(name, email, phone, birth, address);
                detailSchedulesPresenter.postBasicInfo(id_cumtomer, KEY_SECRET, jsonObjectData);
            }
        } else if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            int size = mImageFileLocation.length();
            image = new Image(mImageFileLocation, size);
            imageList.add(image);
            switch (bill) {
                case ELECTRIC: {
                    mImageViewElectric.setImageDrawable(getResources().getDrawable(R.drawable.ic_oke));
                    mTextViewElectric.setEnabled(false);
                    break;
                }
                case WATER: {
                    mImageViewWater.setImageDrawable(getResources().getDrawable(R.drawable.ic_oke));
                    mTextViewWater.setEnabled(false);
                    break;
                }
                case NUMBER: {
                    mImageViewNumber.setImageDrawable(getResources().getDrawable(R.drawable.ic_oke));
                    mTextViewNumber.setEnabled(false);
                    break;
                }
                case LAND: {
                    mImageViewLand.setImageDrawable(getResources().getDrawable(R.drawable.ic_oke));
                    mTextViewLand.setEnabled(false);
                    break;
                }
            }
        }
    }


    @Override
    public void onClickDialog(DialogResultItem dialogResultItem) {
        super.onClickDialog(dialogResultItem);
        switch (dialogResultItem.getDialogId()) {
            case DIALOG_YESNO: {
                switch (dialogResultItem.getResultMessageDialog()) {
                    case MESSAGEDIALOG_BUTTON_YES: {
                        reload();
                        break;
                    }
                    case MESSAGEDIALOG_BUTTON_NO: {
                        mMessageDialogManger.onDimiss();
                    }
                }
                break;
            }
        }
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
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, (path, uri) -> {
                Log.e("ExternalStorage", "Scanned " + path + ":");
                Log.e("ExternalStorage", "-> uri=" + uri);
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

    public void reload() {
        Intent intent = new Intent(this, SchedulesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        removeAll();
    }

    @Override
    public void onBackPressed() {
        switch (step) {
            case 1: {
                showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.dialog_messages_cancel));
                break;
            }
            case 2: {
                showDialogYesNo(DIALOG_YESNO, getResources().getString(R.string.dialog_messages_cancel));
                break;
            }
        }
    }

}
