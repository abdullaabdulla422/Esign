package com.ebs.rental.objects;

import org.json.JSONObject;

/**
 * Created by techunity on 20/9/16.
 */
@SuppressWarnings("ALL")
public class GeneralChecklistObject {

    public static final String TAG_RESULT = "Result";
    private static final String TAG_MESSAGE = "Message";

    private String Result;
    private String Message;


    public String getResult() {
        return Result;
    }

    private void setResult(String result) {
        Result = result;
    }

    @SuppressWarnings("RedundantThrows")
    public static GeneralChecklistObject parseUser(String response) throws Exception {
        GeneralChecklistObject rental = new GeneralChecklistObject();

        String str = response;
        str = str.replace("{ \"Result\" : [","");
        str = str.replace("] }","");


       // JSONObject userDetails = new JSONObject(response);

        rental.setResult(str);

        return rental;
    }


    public static GeneralChecklistObject parseMessage(String response) throws Exception {
        GeneralChecklistObject rental = new GeneralChecklistObject();

        JSONObject userDetails = new JSONObject(response);

        rental.setMessage(userDetails.getString(TAG_MESSAGE));

        return rental;
    }

    public String getMessage() {
        return Message;
    }

    private void setMessage(String message) {
        Message = message;
    }
}