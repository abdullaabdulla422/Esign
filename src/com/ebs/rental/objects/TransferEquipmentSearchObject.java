package com.ebs.rental.objects;

import android.util.Log;

import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


//@SuppressWarnings("ALL")
public class TransferEquipmentSearchObject  extends SoapUtils {

    private static final String TAG_EQUIPMENTID = "equipmentid";

    private static final String TAG_MFG = "mfg";
    private static final String TAG_MODEL = "model";
    private static final String TAG_SERIALNO = "serialno";
    private static final String TAG_PONUM = "ponum";
    private static final String TAG_CURRENT_BRANCH = "currentbranch";
    private static final String TAG_HOURMETER = "hourmeter";
    private static final String TAG_FUELLEVEL = "fuellevel";
    private static final String TAG_EQUIPSTATUS = "equipstatus";
    private static final String TAG_EQUIPDESCRIPTION = "equipdescription";
    private static final String TAG_TRASPORTID = "transportid";
    private static final String TAG_TRANS_TYPE = "transtype";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CUSTNOLIST = "custnolist";
    private static final String  TAG_CUSTREBRANCH = "custrecbranch";





    private String equipmentid;
    private String Message;
    private String mfg;
    private String model;
    private String serialno;
    private String ponum;
    private String currentbranch;
    private int hourmeter;
    private String fuellevel;
    private String equipstatus;
    private String equipdescription;
    private int transportid;

    private String custnum;
    private String transtype;
    private String custrebranch;


    public String getEquipmentid() {
        return equipmentid;
    }

    private void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }





    private String isNull(String s){
        if(s==null)
            return "";
        else if(s.startsWith("")||s.startsWith("anyType{}"))
            return "";
        else
            return s;
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

    public String getPonum() {
        return ponum;
    }

    private void setPonum(String ponum) {
        this.ponum = ponum;
    }

    public String getCurrentbranch() {
        return currentbranch;
    }

    private void setCurrentbranch(String currentbranch) {
        this.currentbranch = currentbranch;
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

    public int getTransportid() {
        return transportid;
    }

    private void setTransportid(int transportid) {
        this.transportid = transportid;
    }

    public String getCustnum() {
        return custnum;
    }

    public void setCustnum(String custnum) {
        this.custnum = custnum;
    }


    public static TransferEquipmentSearchObject parseUser(String response) throws Exception {
        TransferEquipmentSearchObject transfer = new TransferEquipmentSearchObject();



        ArrayList<TransferEquipmentSearchObject> rental = new ArrayList<>();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        if(detailarray.length()!=0){
            JSONObject userDetails = detailarray.getJSONObject(0);

            transfer.setEquipmentid(userDetails.getString(TAG_EQUIPMENTID));
            Log.d("Message for transfer"+"",userDetails.getString(TAG_MESSAGE));
            transfer.setMessage(userDetails.getString(TAG_MESSAGE));
            transfer.setMfg(userDetails.getString(TAG_MFG));
            transfer.setModel(userDetails.getString(TAG_MODEL));
            transfer.setSerialno(userDetails.getString(TAG_SERIALNO));
            transfer.setPonum(userDetails.getString(TAG_PONUM));
            transfer.setCurrentbranch(userDetails.getString(TAG_CURRENT_BRANCH));
            SessionData.getInstance().setCurrentBranch_TransIn_Out(transfer.getCurrentbranch());
            transfer.setHourmeter(userDetails.getInt(TAG_HOURMETER));
            transfer.setFuellevel(userDetails.getString(TAG_FUELLEVEL));
            transfer.setEquipstatus(userDetails.getString(TAG_EQUIPSTATUS));
            transfer.setEquipdescription(userDetails.getString(TAG_EQUIPDESCRIPTION));
            transfer.setTransportid(userDetails.getInt(TAG_TRASPORTID));
            transfer.setTranstype(userDetails.getString(TAG_TRANS_TYPE));
            transfer.setCustrebranch(userDetails.getString(TAG_CUSTREBRANCH));

            Custnolist.parseData(userDetails.getString(TAG_CUSTNOLIST));
        }
        if(detailarray.length()==0){
            transfer=null;
        }



        return transfer;
    }

    public String getTranstype() {
        return transtype;
    }

    private void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getCustrebranch() {
        return custrebranch;
    }

    public void setCustrebranch(String custrebranch) {
        this.custrebranch = custrebranch;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
