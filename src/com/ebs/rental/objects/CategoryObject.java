package com.ebs.rental.objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;


@SuppressWarnings("ALL")
public class CategoryObject {

    private static final String TAG_ID = "id";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_MESSAGE = "message";

    private String id;
    private String category;
    private String message;


    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public static ArrayList<CategoryObject> parseData(String response) throws Exception {

        ArrayList<CategoryObject> rental = new ArrayList<>();

        CategoryObject detail;

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length",""+detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {
            detail = new CategoryObject();
            JSONObject userDetails = detailarray.getJSONObject(i);

            detail.setCategory(userDetails.getString(TAG_CATEGORY));
            detail.setMessage(userDetails.getString(TAG_MESSAGE));
            detail.setId(userDetails.getString(TAG_ID));
            rental.add(detail);
        }
        return rental;

    }

}
