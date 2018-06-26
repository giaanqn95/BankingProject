package com.example.e7440.bankingproject.components;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

public class GooglePlaceAPI {
    public static void showGooglePlaces(Activity activity, int PLACE_PICKER_REQUEST) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent myIntent;
            myIntent = builder.build(activity);
            activity.startActivityForResult(myIntent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
