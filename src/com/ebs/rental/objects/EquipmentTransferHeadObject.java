package com.ebs.rental.objects;

import org.json.JSONObject;


public class EquipmentTransferHeadObject  {

    private static final String TAG_RESULT = "Result";
    private static final String TAG_MESSAGE = "Message";

    private String Result;
    private String Message;

    public String getResult() {
        return Result;
    }

    private void setResult(String result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    private void setMessage(String message) {
        Message = message;
    }



    public static EquipmentTransferHeadObject parseUser(String response) throws Exception {
        EquipmentTransferHeadObject rental = new EquipmentTransferHeadObject();

        JSONObject userDetails = new JSONObject(response);

        rental.setResult(userDetails.getString(TAG_RESULT));

        return rental;
    }


    public static EquipmentTransferHeadObject parseMessage(String response) throws Exception {
        EquipmentTransferHeadObject rental = new EquipmentTransferHeadObject();

        JSONObject userDetails = new JSONObject(response);

        rental.setMessage(userDetails.getString(TAG_MESSAGE));

        return rental;
    }


}
