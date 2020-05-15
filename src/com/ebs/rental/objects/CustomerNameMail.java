package com.ebs.rental.objects;

import com.ebs.rental.utils.SessionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerNameMail {

    private static final String TAG_CUSTNUM = "custnum";
    private static final String TAG_CUSTNAME = "custname";
    private static final String TAG_MAIL = "email";
    private static final String TAG_MESSAGE = "message";
    private String custnum;
    private String custname;
    private String email;
    private String message;
    private boolean selected = false;
    private int isAlreadyExit = 0;

    public CustomerNameMail() {
    }

    public String getCustnum ()
    {
        return custnum;
    }

    private void setCustnum(String custnum)
    {
        this.custnum = custnum;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static ArrayList<CustomerNameMail> parseData(String response) throws Exception {

        ArrayList<CustomerNameMail> rental = new ArrayList<>();

        CustomerNameMail detail = new CustomerNameMail();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {
            detail = new CustomerNameMail();
            JSONObject userDetails = detailarray.getJSONObject(i);

            detail.setCustnum(userDetails.getString(TAG_CUSTNUM));
            detail.setCustname(userDetails.getString(TAG_CUSTNAME));
            detail.setEmail(userDetails.getString(TAG_MAIL));
            detail.setMessage(userDetails.getString(TAG_MESSAGE));
            detail.setIsAlreadyExit(1);

            rental.add(detail);
        }

        return rental;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getIsAlreadyExit() {
        return isAlreadyExit;
    }

    public void setIsAlreadyExit(int isAlreadyExit) {
        this.isAlreadyExit = isAlreadyExit;
    }
}
