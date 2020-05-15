package com.ebs.rental.objects;

import org.json.JSONObject;

/**
 * Created by techunity on 20/9/16.
 */
@SuppressWarnings("ALL")
public class RentalOrderSignedDocmuntPDFObject {

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

    public static RentalOrderSignedDocmuntPDFObject parseUser(String response) throws Exception {
        RentalOrderSignedDocmuntPDFObject rental = new RentalOrderSignedDocmuntPDFObject();

        JSONObject userDetails = new JSONObject(response);

        rental.setResult(userDetails.getString(TAG_RESULT));

        return rental;
    }


    public static RentalOrderSignedDocmuntPDFObject parseMessage(String response) throws Exception {
        RentalOrderSignedDocmuntPDFObject rental = new RentalOrderSignedDocmuntPDFObject();

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
