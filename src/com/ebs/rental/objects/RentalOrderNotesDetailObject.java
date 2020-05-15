package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cbe-tecmubt-016 on 17/2/18.
 */

public class RentalOrderNotesDetailObject {

    private static final String TAG_OEDETAIL_ID = "oedetl_id";
    private static final String TAG_KPART = "kpart";
    private static final String TAG_PMDESC = "pmdesc";
    private static final String TAG_ITEM_NUMBER = "ItemNumber";
    private static final String TAG_NOTES_DETAIL = "NotesDetail";
    private static final String TAG_MESSAGE = "Message";

    private int oedetl_id;
    private String kpart;
    private String pmdesc;
    private int ItemNumber;
    private String NotesDetail;
    private String message;

    public int getOedetl_id() {
        return oedetl_id;
    }

    public void setOedetl_id(int oedetl_id) {
        this.oedetl_id = oedetl_id;
    }

    public String getKpart() {
        return kpart;
    }

    public void setKpart(String kpart) {
        this.kpart = kpart;
    }

    public String getPmdesc() {
        return pmdesc;
    }

    public void setPmdesc(String pmdesc) {
        this.pmdesc = pmdesc;
    }



    public String getNotesDetail() {
        return NotesDetail;
    }

    public void setNotesDetail(String notesDetail) {
        NotesDetail = notesDetail;
    }


    public static ArrayList<RentalOrderNotesDetailObject> parseData(
            String response) throws Exception {

        ArrayList<RentalOrderNotesDetailObject> custlist = new ArrayList<>();

        RentalOrderNotesDetailObject detail = new RentalOrderNotesDetailObject();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {
            detail = new RentalOrderNotesDetailObject();
            JSONObject userDetails = detailarray.getJSONObject(i);
            detail.setOedetl_id(userDetails.getInt(TAG_OEDETAIL_ID));
            detail.setKpart(userDetails.getString(TAG_KPART));
            detail.setPmdesc(userDetails.getString(TAG_PMDESC));
            detail.setItemNumber(userDetails.getInt(TAG_ITEM_NUMBER));
            detail.setNotesDetail(userDetails.getString(TAG_NOTES_DETAIL));
            detail.setMessage(userDetails.getString(TAG_MESSAGE));

            custlist.add(detail);
        }
        return custlist;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(int itemNumber) {
        ItemNumber = itemNumber;
    }
}
