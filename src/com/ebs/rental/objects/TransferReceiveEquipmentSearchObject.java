package com.ebs.rental.objects;

import com.ebs.rental.utils.SessionData;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by techunity on 27/10/16.
 */
@SuppressWarnings("ALL")
public class TransferReceiveEquipmentSearchObject {

//    [{"equipmentid":"DP150","mfg":"CAT","model":"DP150","serialno":"CT6SD54G5H","hourmeter":5002,"fuellevel":"","equipstatus":"ON",
//            "equipdescription":"CATERPILLAR DP150 LIFT TRUCK","transportid":0,"transid":0,"ponum":0,"tobranch":"","frombranch":"",
//            "message":"","transtype":1}]


  //  [{"equipmentid":"DP150","mfg":"CAT","model":"DP150","serialno":"CT6SD54G5H","hourmeter":5002,"fuellevel":"",
  // "equipstatus":"ON","equipdescription":"CATERPILLAR DP150 LIFT TRUCK","transportid":88,"transid":14,"ponum":0,
  // "currentbranch":"001-NATIONAL SERVICE CENTER-HOUSTON","frombranch":"100-YOUR EQUIPMENT COMPANY","message":"","transtype":1}]

    private static final String TAG_EQUIPMENTID = "equipmentid";
    private static final String TAG_MFG = "mfg";
    private static final String TAG_MODEL = "model";
    private static final String TAG_SERIAL_NO = "serialno";

    private static final String TAG_HOURMETER = "hourmeter";

    private static final String TAG_FUELLEVEL = "fuellevel";
    private static final String TAG_EQUIPSTATUS = "equipstatus";
    private static final String TAG_EQUIP_DESCRIPTION = "equipdescription";
    private static final String TAG_TRANSPORTID = "transportid";
    private static final String TAG_TRANSID = "transid";
    private static final String TAG_PONUM = "ponum";
    private static final String TAG_TO_BRANCH = "currentbranch";
    private static final String TAG_FROM_BRANCH = "frombranch";
    private static final String TAG_TRANSTYPE = "transtype";

    private static final String TAG_MESSAGE = "message";


    private String equipmentid;
    private String mfg;
    private String model;
    private String serialno;
    private int hourmeter;
    private String fuellevel;
    private int  transportid;
    private int transid;
    private int ponum;
    private int transtype;
    private String equipstatus;
    private String equipdescription;
    private String tobranch;
    private String frombranch;
    private String message;

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

    public int getHourmeter() {
        return hourmeter;
    }

    private void setHourmeter(int hourmeter) {
        this.hourmeter = hourmeter;
    }

    public String getFuellevel() {
        return fuellevel;
    }

    private void setFuellevel(String fuellevel) {
        this.fuellevel = fuellevel;
    }

    public int getTransportid() {
        return transportid;
    }

    private void setTransportid(int transportid) {
        this.transportid = transportid;
    }

    public int getTransid() {
        return transid;
    }

    private void setTransid(int transid) {
        this.transid = transid;
    }

    public int getPonum() {
        return ponum;
    }

    private void setPonum(int ponum) {
        this.ponum = ponum;
    }

    public int getTranstype() {
        return transtype;
    }

    private void setTranstype(int transtype) {
        this.transtype = transtype;
    }

    public String getEquipstatus() {
        return equipstatus;
    }

    private void setEquipstatus(String equipstatus) {
        this.equipstatus = equipstatus;
    }

    public String getEquipdescription() {
        return equipdescription;
    }

    private void setEquipdescription(String equipdescription) {
        this.equipdescription = equipdescription;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public static TransferReceiveEquipmentSearchObject  parseData(String response) throws Exception{

        TransferReceiveEquipmentSearchObject rental =new TransferReceiveEquipmentSearchObject();

        JSONArray setResult = new JSONArray(response);

        JSONObject result  = setResult.getJSONObject(0);

        rental.setMessage(result.getString(TAG_MESSAGE));
        rental.setMfg(result.getString(TAG_MFG));
        rental.setModel(result.getString(TAG_MODEL));
        rental.setSerialno(result.getString(TAG_SERIAL_NO));
        rental.setTransportid(result.getInt(TAG_TRANSPORTID));
        rental.setFuellevel(result.getString(TAG_FUELLEVEL));
        rental.setEquipmentid(result.getString(TAG_EQUIPMENTID));
        rental.setEquipstatus(result.getString(TAG_EQUIPSTATUS));
        rental.setEquipdescription(result.getString(TAG_EQUIP_DESCRIPTION));
        rental.setEquipdescription(result.getString(TAG_EQUIP_DESCRIPTION));
        rental.setTransid(result.getInt(TAG_TRANSID));
        rental.setPonum(result.getInt(TAG_PONUM));
        rental.setTobranch(result.getString(TAG_TO_BRANCH));
        SessionData.getInstance().setCurrentBranch_receive(rental.getTobranch());
        rental.setFrombranch(result.getString(TAG_FROM_BRANCH));
        SessionData.getInstance().setCurrentBranch_receive_from(rental.getFrombranch());
        rental.setHourmeter(result.getInt(TAG_HOURMETER));
        rental.setTranstype(result.getInt(TAG_TRANSTYPE));

        return rental;

    }

    public String getTobranch() {
        return tobranch;
    }

    private void setTobranch(String tobranch) {
        this.tobranch = tobranch;
    }

    public String getFrombranch() {
        return frombranch;
    }

    private void setFrombranch(String frombranch) {
        this.frombranch = frombranch;
    }
}
