package com.ebs.rental.objects;


import android.util.Log;

import com.ebs.rental.utils.CheckList;
import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class InspectionChecklist extends SoapUtils {

    private static final String TAG_RESULT = "Result";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CHECKLIST = "checklistname";
    private static final String TAG_CHECKLISTID = "checklistid";

    private String Result;
    private String checklistname;
    private String checklistid;

    private String Message;
    private ArrayList<String> checklists;

    public InspectionChecklist() {

    }

    public String getResult() {
        return Result;
    }

    private void setResult(String result) {
        Result = result;
    }

    public static InspectionChecklist parseChecklistold(String response) throws Exception {
        InspectionChecklist inspectionChecklist = new InspectionChecklist();
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

    public static ArrayList<InspectionChecklist> parseChecklist(String response) throws Exception {

        ArrayList<InspectionChecklist> checklist = new ArrayList<>();


        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        Log.d("Array Length", "" + detailarray.length());
        for (int i = 0; i < detailarray.length(); i++) {

            JSONObject userDetails = detailarray.getJSONObject(i);
            InspectionChecklist inspectionChecklist = new InspectionChecklist();

            inspectionChecklist.setMessage(userDetails.getString(TAG_MESSAGE));
            inspectionChecklist.setChecklistname(userDetails.getString(TAG_CHECKLIST));
            inspectionChecklist.setChecklistid(userDetails.getString(TAG_CHECKLISTID));
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
}