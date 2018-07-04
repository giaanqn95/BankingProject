package com.example.e7440.bankingproject.module.introduce;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.GooglePlaceAPI;
import com.example.e7440.bankingproject.components.SessionManagerUser;
import com.example.e7440.bankingproject.module.base.BaseActivity;
import com.example.e7440.bankingproject.module.config.Config;
import com.example.e7440.bankingproject.module.config.ConfigGeneral;
import com.example.e7440.bankingproject.module.config.ConfigPresenterImpl;
import com.example.e7440.bankingproject.module.login.LoginActivity;
import com.example.e7440.bankingproject.module.main.MainActivity;
import com.example.e7440.bankingproject.module.model.Tab;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by E7440 on 6/12/2018.
 */

public class IntroduceActivity extends BaseActivity implements View.OnClickListener, ConfigGeneral.ConfigView {

    @BindView(R.id.btn_next)
    Button mButtonNext;
    @BindView(R.id.btn_logout)
    Button mButtonLogout;
    @BindView(R.id.tv_welcome)
    TextView mTextView;
    private ConfigPresenterImpl mConfigPresenter;
    private Double lat,log;

    private static final int REQUEST_CODE = 1002, CHOOSE_PLACE = 101;
    private static final String[] PERMISSIONS_LIST = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INSTALL_LOCATION_PROVIDER,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        init();
        verifyPermission();
        String imei = getUniqueIMEIId(this);
        Log.d("CCCCC", imei);
    }

    private void init() {
        mConfigPresenter = new ConfigPresenterImpl(this);
        mConfigPresenter.getConfig();
        mButtonLogout.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next: {
                Intent intent = new Intent(IntroduceActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_logout:{
                SessionManagerUser.getInstance().logoutUser();
                Intent intent = new Intent(IntroduceActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.tv_welcome:{
                mTextView.setEnabled(false);
                GooglePlaceAPI.showGooglePlaces(IntroduceActivity.this, CHOOSE_PLACE);
                break;
            }
        }
    }

    @Override
    public void getDataConfig(Tab mExample) {
        if (mExample != null) {
            Config.getInstance().saveData(mExample);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PLACE){
            if (resultCode ==  RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                String mLocation = String.format("%s", place.getAddress());
                mTextView.setText(mLocation);
                lat = place.getLatLng().latitude;
                log = place.getLatLng().longitude;
                Log.d("AAAAA", lat + "  " + log);
            }
            mTextView.setEnabled(true);
        }
    }

    //Permission
    private boolean verifyPermission() {
        int camera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_LIST, REQUEST_CODE
            );

            return false;
        }
        return true;
    }


    //Get IMEI SIM
    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return "";
            }
            String imei = telephonyManager.getDeviceId();
            Log.e("BBBBB", "=" + imei);
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
}
