package com.example.e7440.bankingproject.components;

import android.content.SharedPreferences;

import com.example.e7440.bankingproject.module.base.BaseApplication;
import com.example.e7440.bankingproject.module.model.User;

import java.util.HashMap;

import static com.example.e7440.bankingproject.module.introduce.IntroduceActivity.timeStart;

public class SessionManagerUser {

    private static SessionManagerUser instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;

    public SessionManagerUser() {
        pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionManagerUser getInstance() {
        if (instance == null) {
            instance = new SessionManagerUser();
        }
        return instance;
    }


    private static final String PREF_NAME = "DataUser";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_WALLET = "wallet";
    public static final String KEY_ALLOW_NOTIFICATION = "allowNotification";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TIME = "timestart";


    /**
     * Create login session
     */
    public void createLoginSession(User dataUser) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing in pref
//        editor.putString(KEY_ID, String.valueOf(dataUser.getId()));
//        editor.putString(KEY_FULLNAME, dataUser.getName());
//        editor.putString(KEY_AVATAR, dataUser.getAvatar().getFullLink());
//        editor.putString(KEY_EMAIL, dataUser.getEmail());
//        editor.putString(KEY_PHONE, dataUser.getPhone());
//        editor.putString(KEY_ADDRESS, dataUser.getAddress().getName());
//        editor.putString(KEY_BIRTHDAY, dataUser.getBirthday());
//        editor.putString(KEY_GENDER, String.valueOf(dataUser.getGender()));
//        editor.putString(KEY_WALLET, String.valueOf(dataUser.getWallet().getAmount()));
//        editor.putString(KEY_UNIT, dataUser.getWallet().getUnit());
//        editor.putString(KEY_ALLOW_NOTIFICATION, String.valueOf(dataUser.getNotification()));
//        editor.putString(KEY_TYPE, String.valueOf(dataUser.getType()));
//        editor.putString(KEY_TOKEN, dataUser.getToken());
//        editor.putString(KEY_LAT, String.valueOf(dataUser.getAddress().getCoordinates().getLat()));
//        editor.putString(KEY_LNG, String.valueOf(dataUser.getAddress().getCoordinates().getLng()));
        editor.putString(KEY_USERNAME, dataUser.getUsername());
        editor.putString(KEY_PASSWORD, dataUser.getPassword());
        editor.putString(KEY_TIME, timeStart);
        // commit changes
        editor.commit();
    }


//    public void updateUserProfileSession(User dataUser) {
//        // Storing in pref
//        editor.putString(KEY_ID, dataUser.getDataUser().get_id());
//        editor.putString(KEY_FULLNAME, dataUser.getDataUser().getFullname());
//        editor.putString(KEY_GENDER, String.valueOf(dataUser.getDataUser().getGender()));
//        editor.putString(KEY_ADDRESS, dataUser.getDataUser().getAddress());
//        editor.putString(KEY_PHONE, dataUser.getDataUser().getPhone());
//        editor.putString(KEY_BIRTHDAY, dataUser.getDataUser().getBirthday());
//        editor.putString(KEY_IMAGE, dataUser.getDataUser().getAvatar());
//        // commit changes
//        editor.commit();
//    }

    public void createLoginSessionFace() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    // Remove value whose key
    public void removeValue() {

        editor.remove(KEY_ID);
        editor.remove(KEY_FULLNAME);
        editor.remove(KEY_AVATAR);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PHONE);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_BIRTHDAY);
        editor.remove(KEY_GENDER);
        editor.remove(KEY_WALLET);
        editor.remove(KEY_PHONE);
        editor.remove(KEY_ALLOW_NOTIFICATION);
        editor.remove(KEY_TYPE);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_LAT);
        editor.remove(KEY_LNG);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> data = new HashMap<String, String>();
        // data name
        data.put(KEY_ID, pref.getString(KEY_ID, ""));
        data.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        data.put(KEY_AVATAR, pref.getString(KEY_AVATAR, null));
        data.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        data.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        data.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        data.put(KEY_BIRTHDAY, pref.getString(KEY_BIRTHDAY, ""));
        data.put(KEY_GENDER, pref.getString(KEY_GENDER, ""));
        data.put(KEY_WALLET, pref.getString(KEY_WALLET, ""));
        data.put(KEY_PHONE, pref.getString(KEY_PHONE, ""));
        data.put(KEY_TOKEN, pref.getString(KEY_TOKEN, ""));
        data.put(KEY_ALLOW_NOTIFICATION, pref.getString(KEY_ALLOW_NOTIFICATION, ""));
        data.put(KEY_TYPE, pref.getString(KEY_TYPE, ""));
        data.put(KEY_LAT, pref.getString(KEY_LAT, "0"));
        data.put(KEY_LNG, pref.getString(KEY_LNG, "0"));
        data.put(KEY_UNIT, pref.getString(KEY_UNIT, "$"));
        // return data
        return data;
    }

    public String getTimeStart() {
        return pref.getString(KEY_TIME, timeStart);
    }

    public String getUserToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public int getTypeLogin() {
        return Integer.parseInt(pref.getString(KEY_TYPE, "1"));
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    //change noti
    public void addNotificationSession(int state) {
        editor.putInt(KEY_ALLOW_NOTIFICATION, state);
        editor.commit();
    }

    public int getNotification() {
        int state = pref.getInt(KEY_ALLOW_NOTIFICATION, 0);
        return state;
    }

    public void updateWallet(Long wallet) {
        editor.putString(KEY_WALLET, String.valueOf(wallet));
        editor.commit();
    }

}
