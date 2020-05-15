package com.ebs.rental.objects;

import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONObject;

/**
 * Created by techunity on 21/11/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class CrossReferenceobject extends SoapUtils {

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



    public static CrossReferenceobject parseUser(String response) throws Exception {
        CrossReferenceobject rental = new CrossReferenceobject();

        JSONObject userDetails = new JSONObject(response);

        rental.setResult(userDetails.getString(TAG_RESULT));

        return rental;
    }

    public static CrossReferenceobject parseMessage(String response) throws Exception {
        CrossReferenceobject rental = new CrossReferenceobject();

        JSONObject userDetails = new JSONObject(response);

        rental.setMessage(userDetails.getString(TAG_MESSAGE));

        return rental;
    }
}
