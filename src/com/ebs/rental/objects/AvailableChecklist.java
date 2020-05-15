package com.ebs.rental.objects;


import android.util.Log;

import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableChecklist extends SoapUtils {

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CHECKLIST = "listname";
    private static final String TAG_CHECKLISTID = "lstid";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_LSTDATE = "lstdate";
    private static final String TAG_ISDEFAULT = "isdefault";

    private String checklistname;
    private String checklistid;
    private String username;
    private String lstdate;
    private String isdefault;


    private String Message;
    private ArrayList<String> checklists;

    public AvailableChecklist() {

    }



    public static AvailableChecklist parseChecklistold(String response) throws Exception {
        AvailableChecklist inspectionChecklist = new AvailableChecklist();
        ArrayList<String> checklist = new ArrayList<>();


        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {

            JSONObject userDetails = detailarray.getJSONObject(i);
            String detail;

            inspectionChecklist.setMessage(userDetails.getString(TAG_MESSAGE));
            inspectionChecklist.setChecklistname(userDetails.getString(TAG_CHECKLIST));
            inspectionChecklist.setChecklistid(userDetails.getString(TAG_CHECKLISTID));
        }
        return inspectionChecklist;
    }

    public static ArrayList<AvailableChecklist> parseChecklist(String response) throws Exception {

        ArrayList<AvailableChecklist> checklist = new ArrayList<>();


        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {

            JSONObject userDetails = detailarray.getJSONObject(i);
            AvailableChecklist inspectionChecklist = new AvailableChecklist();

            inspectionChecklist.setMessage(userDetails.getString(TAG_MESSAGE));
            inspectionChecklist.setChecklistname(userDetails.getString(TAG_CHECKLIST));
            inspectionChecklist.setChecklistid(userDetails.getString(TAG_CHECKLISTID));
            inspectionChecklist.setUsername(userDetails.getString(TAG_USERNAME));
            inspectionChecklist.setLstdate(userDetails.getString(TAG_LSTDATE));
            inspectionChecklist.setIsdefault(userDetails.getString(TAG_ISDEFAULT));
            checklist.add(inspectionChecklist);
        }
        return checklist;
    }

    public static ArrayList<String> parseData(String response) throws Exception {

        ArrayList<String> checklist = new ArrayList<>();


        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {
// detail = new CategoryObject();

            JSONObject userDetails = detailarray.getJSONObject(i);
            String detail;

            detail = (userDetails.getString(TAG_CHECKLIST));
// detail.setMessage(userDetails.getString(TAG_MESSAGE));
// detail.setId(userDetails.getString(TAG_ID));
            checklist.add(detail);
        }
        return checklist;

    }

    public static ArrayList<String> parseMessage(String response) throws Exception {
        ArrayList<String> checklist = new ArrayList<>();


        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {
// detail = new CategoryObject();

            JSONObject userDetails = detailarray.getJSONObject(i);
            String detail;

            detail = (userDetails.getString(TAG_MESSAGE));
// detail.setMessage(userDetails.getString(TAG_MESSAGE));
// detail.setId(userDetails.getString(TAG_ID));
            checklist.add(detail);
        }
        return checklist;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getChecklists() {
        return checklists;
    }

    public void setChecklists(ArrayList<String> checklists) {
        this.checklists = checklists;
    }

    public String getChecklistname() {
        return checklistname;
    }

    public void setChecklistname(String checklistname) {
        this.checklistname = checklistname;
    }

    public String getChecklistid() {
        return checklistid;
    }

    public void setChecklistid(String checklistid) {
        this.checklistid = checklistid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLstdate() {
        return lstdate;
    }

    public void setLstdate(String lstdate) {
        this.lstdate = lstdate;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }
}