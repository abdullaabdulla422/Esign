package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by techunity on 14/10/16.
 */
@SuppressWarnings("ALL")
public class GeneralEquipmentSearchObject {
/*
    [{"equipmentid":"HMD030571KC","mfg":"RAY","model":"112TMFRE60L","serialno":"112-04-55212",
            "description":"HOME DEPOT #6683- LEASE #9164QREL","pmspec":"D","equipmentreading":60,
            "equipmentdatelast":"07/20/2011","lasthours":4293,"hourmeter":3868,"message":"","equipmentmeter":0,"eqpstatus":"AV"}]*/

   private static  final String TAG_EQUIPMENTID = "equipmentid";
    private static final String TAG_MFG = "mfg";
    private static final String TAG_MODEL = "model";
    private static final String TAG_SERIAL_NO = "serialno";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PMSPEC = "pmspec";
    private static final String TAG_EQUIPMENT_READING = "equipmentreading";
    private static final String TAG_EQUIPMENT_DATE_LAST = "equipmentdatelast";
    private static final String TAG_EQUIPMENT_STATUS = "eqpstatus";
    private static final String TAG_LASTHOURS = "lasthours";
    public static final String TAG_HOURMETER = "hourmeter";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SECTION = "section";
    private static final String TAG_SPOT = "spot";



    private String equipmentid;
    private String mfg;
    private String model;
    private String serialno;
    private String description;
    private String pmspec;
    private int  equipmentreading;
    private String equipmentdatelast;
    private String lasthours;
    private String message;
    private String equipmentstatus;
    private int hourmeter;
    private int section;
    private int spot;

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSpot() {
        return spot;
    }

    public void setSpot(int spot) {
        this.spot = spot;
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

    public String getLasthours() {
        return lasthours;
    }

    private void setLasthours(String lasthours) {
        this.lasthours = lasthours;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public static GeneralEquipmentSearchObject  parseData(String response) throws Exception{

        GeneralEquipmentSearchObject rental =new GeneralEquipmentSearchObject();

        JSONArray setResult = new JSONArray(response);

        JSONObject result  = setResult.getJSONObject(0);

        rental.setEquipmentid(result.getString(TAG_EQUIPMENTID));
        rental.setMessage(result.getString(TAG_MESSAGE));
        rental.setMfg(result.getString(TAG_MFG));
        rental.setModel(result.getString(TAG_MODEL));
        rental.setSerialno(result.getString(TAG_SERIAL_NO));
        rental.setDescription(result.getString(TAG_DESCRIPTION));
        rental.setPmspec(result.getString(TAG_PMSPEC));
        rental.setEquipmentdatelast(result.getString(TAG_EQUIPMENT_DATE_LAST));
        rental.setEquipmentreading(result.getInt(TAG_EQUIPMENT_READING));
        rental.setLasthours(result.getString(TAG_LASTHOURS));
        rental.setHourmeter(result.getInt(TAG_HOURMETER));
        rental.setEquipmentstatus(result.getString(TAG_EQUIPMENT_STATUS));
       // rental.setSection(result.getInt(TAG_SECTION));
       // rental.setSpot(result.getInt(TAG_SPOT));


        return rental;

    }

    public int getHourmeter() {
        return hourmeter;
    }

    private void setHourmeter(int hourmeter) {
        this.hourmeter = hourmeter;
    }

    public String getEquipmentid() {
        return equipmentid;
    }
    private void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }
    private void setEquipmentstatus(String equipmentstatus) {
        this.equipmentstatus = equipmentstatus;
    }

    public String getEquipmentstatus() {
        return equipmentstatus;
    }


}
