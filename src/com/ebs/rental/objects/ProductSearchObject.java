package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by techunity on 22/11/16.
 */
@SuppressWarnings("ALL")
public class ProductSearchObject {

    private static final String TAG_EQUIPMENT_ID = "equipmentid";
    private static final String TAG_MFG = "mfg";
    private static final String TAG_MODEL = "model";
    private static final String TAG_SERIAL_NO = "serialno";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PMSPEC = "pmspec";
    private static final String TAG_EQUIPMENT_READING = "equipmentreading";
    private static final String TAG_EQUIPMENT_DATE_LAST = "equipmentdatelast";
    private static final String TAG_LAST_HOURS = "lasthours";
    private static final String TAG_HOUR_METER = "hourmeter";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_EQUIPMENT_METER = "equipmentmeter";
    public static final String TAG_EQP_STATUS = "eqpstatus";
    private static final String TAG_ROW_NUM = "rownum";
    private static final String TAG_ROW_CNT = "rowcnt";

    private String equipmentid;
    private String mfg;
    private String model;
    private String serialno;
    private String description;
    private String pmspec;
    private int equipmentreading;
    private String equipmentdatelast;
    private String message;
    private int lasthours;
    private int hourmeter;
    private int equipmentmeter;
    private String eqpstatus;
    private int rownum;
    private int rowcnt;


    public String getEquipmentid() {
        return equipmentid;
    }

    private void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getMfg() {
        return mfg;
    }

    private void setMfg(String mfg) {
        this.mfg = mfg;
    }

    public String getModel() {
        return model;
    }

    private void setModel(String model) {
        this.model = model;
    }

    public String getSerialno() {
        return serialno;
    }

    private void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getPmspec() {
        return pmspec;
    }

    private void setPmspec(String pmspec) {
        this.pmspec = pmspec;
    }

    public int getEquipmentreading() {
        return equipmentreading;
    }

    private void setEquipmentreading(int equipmentreading) {
        this.equipmentreading = equipmentreading;
    }

    public String getEquipmentdatelast() {
        return equipmentdatelast;
    }

    private void setEquipmentdatelast(String equipmentdatelast) {
        this.equipmentdatelast = equipmentdatelast;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public int getLasthours() {
        return lasthours;
    }

    private void setLasthours(int lasthours) {
        this.lasthours = lasthours;
    }

    public int getHourmeter() {
        return hourmeter;
    }

    private void setHourmeter(int hourmeter) {
        this.hourmeter = hourmeter;
    }

    public int getEquipmentmeter() {
        return equipmentmeter;
    }

    private void setEquipmentmeter(int equipmentmeter) {
        this.equipmentmeter = equipmentmeter;
    }

    public String getEqpstatus() {
        return eqpstatus;
    }

    public void setEqpstatus(String eqpstatus) {
        this.eqpstatus = eqpstatus;
    }


    public static ArrayList<ProductSearchObject> parseData(String response) throws Exception{

        ArrayList<ProductSearchObject> rental = new ArrayList<>();

        ProductSearchObject detail =  new ProductSearchObject();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {
            detail =  new ProductSearchObject();
            JSONObject userDetails = detailarray.getJSONObject(i);

            detail.setEquipmentid(userDetails.getString(TAG_EQUIPMENT_ID));
            detail.setMfg(userDetails.getString(TAG_MFG));
            detail.setModel(userDetails.getString(TAG_MODEL));
            detail.setSerialno(userDetails.getString(TAG_SERIAL_NO));
            detail.setDescription(userDetails.getString(TAG_DESCRIPTION));
            detail.setPmspec(userDetails.getString(TAG_PMSPEC));
            detail.setEquipmentreading(userDetails.getInt(TAG_EQUIPMENT_READING));
            detail.setEquipmentdatelast(userDetails.getString(TAG_EQUIPMENT_DATE_LAST));
            detail.setLasthours(userDetails.getInt(TAG_LAST_HOURS));
            detail.setHourmeter(userDetails.getInt(TAG_HOUR_METER));
            detail.setEquipmentmeter(userDetails.getInt(TAG_EQUIPMENT_METER));
            detail.setRownum(userDetails.getInt(TAG_ROW_NUM));
            detail.setRowcnt(userDetails.getInt(TAG_ROW_CNT));

            detail.setMessage(userDetails.getString(TAG_MESSAGE));
            rental.add(detail);
        }

        return rental;

    }


    public int getRownum() {
        return rownum;
    }

    private void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getRowcnt() {
        return rowcnt;
    }

    private void setRowcnt(int rowcnt) {
        this.rowcnt = rowcnt;
    }
}
