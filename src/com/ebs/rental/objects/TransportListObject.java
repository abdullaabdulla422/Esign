package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransportListObject {

    private static final String TAG_ESTIMATED = "estimated";
    private static final String TAG_DISPATCHED = "dispatched";
    private static final String TAG_CUST_NAME = "custname";
    private static final String TAG_CUST_ADD = "custadd";
    private static final String TAG_KEQUIP_NUM = "kequipnum";
    private static final String TAG_KCUST_NUM = "kcustnum";
    private static final String TAG_TRUCK_NUMBER = "trucknumber";
    private static final String TAG_ACTION = "action";
    private static final String TAG_CALL_NUM = "callnum";
    private static final String TAG_CUST_STATE = "custstate";
    private static final String TAG_PARTY = "party";
    private static final String TAG_CUST_CITY = "custcity";
    private static final String TAG_OE_JOB_NUM = "oejobnum";
    private static final String TAG_NOTES = "notes";
    private static final String TAG_NOTES1 = "notes01";
    private static final String TAG_NOTES2 = "notes02";
    private static final String TAG_NOTES3 = "notes03";
    private static final String TAG_NOTES4 = "notes04";
    private static final String TAG_CUSTS_NUM = "custsnum";
    private static final String TAG_CUST_ADD01 = "custadd01";
    private static final String TAG_CUST_ADD02 = "custadd02";
    private static final String TAG_CUST_ZIP = "custzip";
    private static final String TAG_CUST_PHONE = "custphone";
    private static final String TAG_RECNUM = "recnum";
    private static final String TAG_TRANSPORT_ID = "transport_id";
    private static final String TAG_KORDERNUM = "kordnum";
    private static final String TAG_KBRANCH = "kbranch";
    private static final String TAG_TOBRANCH = "tobranch";
    private static final String TAG_MOBILE_APP_TRACK_STATUS = "mobapptrackstatus";


    //tobranch

    private static final String TAG_MESSAGE = "message";


    private String estimated;
    private String dispatched;
    private String custname;
    private String custadd;
    private String kequipnum;
    private String kcustnum;
    private String trucknumber = "";
    private String action;
    private String callnum;
    private String custstate;
    private String party;
    private String custcity;
    private String oejobnum;
    private String notes;
    private String notes01;
    private String notes02;
    private String notes03;
    private String notes04;
    private String custsnum;
    private String custadd01;
    private String custadd02;
    private String custzip;
    private String custphone;
    private String recnum;
    private String transport_id;
    private String kordnum;
    private String kbranch;
    private String tobranch;
    private String mobapptrackstatus = "0";


    private String message;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEstimated() {
        return estimated;
    }

    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }

    public String getDispatched() {
        return dispatched;
    }

    public void setDispatched(String dispatched) {
        this.dispatched = dispatched;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustadd() {
        return custadd;
    }

    public void setCustadd(String custadd) {
        this.custadd = custadd;
    }

    public String getKequipnum() {
        return kequipnum;
    }

    public void setKequipnum(String kequipnum) {
        this.kequipnum = kequipnum;
    }

    public String getKcustnum() {
        return kcustnum;
    }

    public void setKcustnum(String kcustnum) {
        this.kcustnum = kcustnum;
    }

    public String getTrucknumber() {
        return trucknumber;
    }

    public void setTrucknumber(String trucknumber) {
        this.trucknumber = trucknumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCallnum() {
        return callnum;
    }

    public void setCallnum(String callnum) {
        this.callnum = callnum;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getOejobnum() {
        return oejobnum;
    }

    public void setOejobnum(String oejobnum) {
        this.oejobnum = oejobnum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKbranch() {
        return kbranch;
    }

    public void setKbranch(String kbranch) {
        this.kbranch = kbranch;
    }

    public static ArrayList<TransportListObject> parseData(String response)
            throws Exception {

        ArrayList<TransportListObject> transportlist = new ArrayList<>();

        TransportListObject transport = new TransportListObject();

        JSONArray detailarray = new JSONArray();
        detailarray = new JSONArray(response);

        for (int i = 0; i < detailarray.length(); i++) {

            transport = new TransportListObject();
            JSONObject userDetails = detailarray.getJSONObject(i);
            transport.setEstimated(userDetails.getString(TAG_ESTIMATED));
            transport.setDispatched(userDetails.getString(TAG_DISPATCHED));
            transport.setCustname(userDetails.getString(TAG_CUST_NAME));
            transport.setCustadd(userDetails.getString(TAG_CUST_ADD));
            transport.setKequipnum(userDetails.getString(TAG_KEQUIP_NUM));
            transport.setKcustnum(userDetails.getString(TAG_KCUST_NUM));
//            transport.setTrucknumber(userDetails.getString(TAG_TRUCK_NUMBER));
            transport.setAction(userDetails.getString(TAG_ACTION));
            transport.setCallnum(userDetails.getString(TAG_CALL_NUM));
            transport.setCuststate(userDetails.getString(TAG_CUST_STATE));
            transport.setParty(userDetails.getString(TAG_PARTY));
            transport.setCustcity(userDetails.getString(TAG_CUST_CITY));
            transport.setOejobnum(userDetails.getString(TAG_OE_JOB_NUM));
            transport.setNotes(userDetails.getString(TAG_NOTES));
            transport.setNotes01(userDetails.getString(TAG_NOTES1));
            transport.setNotes02(userDetails.getString(TAG_NOTES2));
            transport.setNotes03(userDetails.getString(TAG_NOTES3));
            transport.setNotes04(userDetails.getString(TAG_NOTES4));
            transport.setCustsnum(userDetails.getString(TAG_CUSTS_NUM));
            transport.setCustadd01(userDetails.getString(TAG_CUST_ADD01));
            transport.setCustadd02(userDetails.getString(TAG_CUST_ADD02));
            transport.setCustzip(userDetails.getString(TAG_CUST_ZIP));
            transport.setCustphone(userDetails.getString(TAG_CUST_PHONE));
            transport.setRecnum(userDetails.getString(TAG_RECNUM));
            transport.setTransport_id(userDetails.getString(TAG_TRANSPORT_ID));
            transport.setKordnum(userDetails.getString(TAG_KORDERNUM));
            transport.setKbranch(userDetails.getString(TAG_KBRANCH));
            transport.setTobranch(userDetails.getString(TAG_TOBRANCH));
            transport.setMobapptrackstatus(userDetails.getString(TAG_MOBILE_APP_TRACK_STATUS));

            transport.setMessage(userDetails.getString(TAG_MESSAGE));
            transportlist.add(transport);

        }

        return transportlist;

    }

    public String getCuststate() {
        return custstate;
    }

    public void setCuststate(String custstate) {
        this.custstate = custstate;
    }

    public String getNotes01() {
        return notes01;
    }

    public void setNotes01(String notes01) {
        this.notes01 = notes01;
    }

    public String getNotes02() {
        return notes02;
    }

    public void setNotes02(String notes02) {
        this.notes02 = notes02;
    }

    public String getNotes03() {
        return notes03;
    }

    public void setNotes03(String notes03) {
        this.notes03 = notes03;
    }

    public String getNotes04() {
        return notes04;
    }

    public void setNotes04(String notes04) {
        this.notes04 = notes04;
    }

    public String getCustsnum() {
        return custsnum;
    }

    public void setCustsnum(String custsnum) {
        this.custsnum = custsnum;
    }

    public String getCustadd01() {
        return custadd01;
    }

    public void setCustadd01(String custadd01) {
        this.custadd01 = custadd01;
    }

    public String getCustadd02() {
        return custadd02;
    }

    public void setCustadd02(String custadd02) {
        this.custadd02 = custadd02;
    }

    public String getRecnum() {
        return recnum;
    }

    public void setRecnum(String recnum) {
        this.recnum = recnum;
    }

    public String getCustzip() {
        return custzip;
    }

    public void setCustzip(String custzip) {
        this.custzip = custzip;
    }

    public String getCustphone() {
        return custphone;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    public String getKordnum() {
        return kordnum;
    }

    public void setKordnum(String kordnum) {
        this.kordnum = kordnum;
    }

    public String getTobranch() {
        return tobranch;
    }

    public void setTobranch(String tobranch) {
        this.tobranch = tobranch;
    }

    public String getMobapptrackstatus() {
        return mobapptrackstatus;
    }

    public void setMobapptrackstatus(String mobapptrackstatus) {
        this.mobapptrackstatus = mobapptrackstatus;
    }
}
