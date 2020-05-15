package com.ebs.rental.objects;

import android.util.Log;

import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableParking extends SoapUtils {


    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SECTION = "section";
    private static final String TAG_SPOT = "spot";

    private String Message;
    private String Section;
    private String Spot;

    public AvailableParking() {

    }

    public AvailableParking(String section, String spot) {
        Section = section;
        Spot = spot;
    }


    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getSpot() {
        return Spot;
    }

    public void setSpot(String spot) {
        Spot = spot;
    }



    public static  ArrayList<AvailableParking> parseParkingList (String response) throws Exception {


        ArrayList<AvailableParking> parkingList = new ArrayList<>();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {

            JSONObject userDetails = detailarray.getJSONObject(i);
            AvailableParking availableParking = new AvailableParking();

            availableParking.setMessage(userDetails.getString(TAG_MESSAGE));
            availableParking.setSection(userDetails.getString(TAG_SECTION));
            availableParking.setSpot(userDetails.getString(TAG_SPOT));
            parkingList.add(availableParking);
        }

//        for (int i = 0; i < 50; i++) {
//            AvailableParking availableParking = new AvailableParking();
//
//            availableParking.setMessage("");
//            availableParking.setSection("A"+i);
//            availableParking.setSpot(""+i);
//            parkingList.add(availableParking);
//        }
        return parkingList;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
