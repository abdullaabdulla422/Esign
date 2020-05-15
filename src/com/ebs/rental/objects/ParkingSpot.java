package com.ebs.rental.objects;

import android.util.Log;

import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingSpot extends SoapUtils {


    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SECTION = "section";
    private static final String TAG_SPOT = "spot";
    private static final String TAG_RESULT = "Result";

    private String Message="";
    private String Section="";
    private String Spot="";
    private String Result="";

    public ParkingSpot() {

    }

    public ParkingSpot(String section, String spot) {
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


    public static ParkingSpot ParseParkingList(String response) throws Exception {




//        JSONArray detailarray = new JSONArray();
//        detailarray = new JSONArray(response);

//        Log.d("Array Length", "" + detailarray.length());
//        for (int i = 0; i < detailarray.length(); i++) {

        JSONObject userDetails = new JSONObject(response);
        ParkingSpot parkingSpot = new ParkingSpot();

     //   parkingSpot.setMessage(userDetails.getString(TAG_MESSAGE));

        parkingSpot.setResult(userDetails.getString(TAG_RESULT));

        if (parkingSpot.getResult().contains("-")){

            try {
                String section = parkingSpot.getResult().split("-")[0];
                String spot = parkingSpot.getResult().split("-")[1];
                parkingSpot.setSection(section);
                parkingSpot.setSpot(spot);
            } catch (Exception e) {
                parkingSpot.setSection("");
                parkingSpot.setSpot("");
                e.printStackTrace();
            }
        }
        if (response.contains("Session")){
            parkingSpot.setMessage("Session");
        }



//        }

//        for (int i = 0; i < 50; i++) {
//            AvailableParking availableParking = new AvailableParking();
//
//            availableParking.setMessage("");
//            availableParking.setSection("A"+i);
//            availableParking.setSpot(""+i);
//            parkingList.add(availableParking);
//        }
        return parkingSpot;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
