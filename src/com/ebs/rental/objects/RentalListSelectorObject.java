package com.ebs.rental.objects;

import org.json.JSONObject;

/**
 * Created by techunity on 20/9/16.
 */
@SuppressWarnings("ALL")
public class RentalListSelectorObject {

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
    public static RentalListSelectorObject parseUser(String response) throws Exception {
        RentalListSelectorObject rental = new RentalListSelectorObject();

        String str = response;
        str = str.replace("{ \"Result\" : [","");
        str = str.replace("] }","");


       // JSONObject userDetails = new JSONObject(response);

        rental.setResult(str);

        return rental;
    }


    public static RentalListSelectorObject parseMessage(String response) throws Exception {
        RentalListSelectorObject rental = new RentalListSelectorObject();

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