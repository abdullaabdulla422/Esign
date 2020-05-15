package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalOrderListDetailObject {
	private static final String TAG_DESCRIPTION = "Description";
	private static final String TAG_USER_NAME = "username";
	private static final String TAG_SHIP_ADD = "shipadd";
	private static final String TAG_SHIP_STATE = "shipstat";
	private static final String TAG_PROGRAM = "program";
	private static final String TAG_KMODEL = "kmodel";
	private static final String TAG_SERIAL_NUM = "kserialnum";
	private static final String TAG_EQUP_NUM = "kequipnum";
	private static final String TAG_OEONRENT_DATE = "oeonrent";
	private static final String TAG_ADDRESS = "Address";
	private static final String TAG_ADDRESS1 = "Address1";
	private static final String TAG_ADDRESS2 = "Address2";
	private static final String TAG_ADDRESS3 = "Address3";
	private static final String TAG_CUST_PHONE = "custphone";
	private static final String TAG_CUST_SEARCH = "kcustsrch";
	private static final String TAG_CUST_NUM = "custsnum";
	private static final String TAG_KCUST_NUM = "kcustnum";
	private static final String TAG_KMFG = "kmfg";
	private static final String TAG_KPART = "kpart";
	private static final String TAG_EQPIGRP = "eqpigrp";
	private static final String TAG_SHIP_CITY = "shipcity";
	private static final String TAG_RATE = "Rate";
	private static final String TAG_OEPSELL = "oepsell";
	private static final String TAG_ACTION = "action";
	private static final String TAG_QTY = "qty";
	private static final String TAG_OE_DETAIL_ID = "oedetailId";
	private static final String TAG_QTY_SHIPPED ="qtyshipped";

	private static final String TAG_MESSAGE = "message";


	private static final String str = "\\n";
	private static final String rep = "\n";
	private String description;
	private String userName;
	private String shipAdd;
	private String shipState;
	private String program;
	private String kmodel; 
	private String kserialNum;
	private String kequipNum;
	private String oeonrentDate;
	private String address;
	private String address1;
	private String address2;
	private String address3;
	private String custPhone;
	private String kcustSrch;
	private String custsnum;
	private String kcustnum; 
	private String kmanufacture;
	private String kpart;
	private String eqpiGroup;
	private String shipCity;
	private String rate;
	private String oepsell;
	private String action;
	private String duplicatedesc;
	private String filterdesc;
	private String filterequpid;
	private int oedetailId;
	private String message;
	private int qty;
	private String pickNotes;
	private int qtyshipped;

	public String getDescription() {

		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShipAdd() {
		return shipAdd;
	}

	private void setShipAdd(String shipAdd) {
		this.shipAdd = shipAdd;
	}

	public String getShipState() {
		return shipState;
	}

	private void setShipState(String shipState) {
		this.shipState = shipState;
	}

	public String getProgram() {
		return program;
	}

	private void setProgram(String program) {
		this.program = program;
	}

	public String getKmodel() {
		return kmodel;
	}

	private void setKmodel(String kmodel) {
		this.kmodel = kmodel;
	}

	public String getKserialNum() {
		return kserialNum;
	}

	private void setKserialNum(String kserialNum) {
		this.kserialNum = kserialNum;
	}

	public String getKequipNum() {
		return kequipNum;
	}

	private void setKequipNum(String kequipNum) {
		this.kequipNum = kequipNum;
	}

	public String getOeonrentDate() {
		return oeonrentDate;
	}

	private void setOeonrentDate(String oeonrentDate) {
		this.oeonrentDate = oeonrentDate;
	}

	public String getAddress() {
		return address;
	}

	private void setAddress(String address) {
		this.address = address;
	}

	public String getAddress1() {
		return address1;
	}

	private void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	private void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	private void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCustPhone() {
		return custPhone;
	}

	private void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getKcustSrch() {
		return kcustSrch;
	}

	private void setKcustSrch(String kcustSrch) {
		this.kcustSrch = kcustSrch;
	}

	public String getCustsnum() {
		return custsnum;
	}

	private void setCustsnum(String custsnum) {
		this.custsnum = custsnum;
	}

	public String getKcustnum() {
		return kcustnum;
	}

	private void setKcustnum(String kcustnum) {
		this.kcustnum = kcustnum;
	}

	public String getKmanufacture() {
		return kmanufacture;
	}

	private void setKmanufacture(String kmanufacture) {
		this.kmanufacture = kmanufacture;
	}

	public String getEqpiGroup() {
		return eqpiGroup;
	}

	private void setEqpiGroup(String eqpiGroup) {
		this.eqpiGroup = eqpiGroup;
	}

	public String getKpart() {
		return kpart;
	}

	private void setKpart(String kpart) {
		this.kpart = kpart;
	}

	public String getShipCity() {
		return shipCity;
	}

	private void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getRate() {
		return rate;
	}

	private void setRate(String rate) {
		this.rate = rate;
	}

	public String getOepsell() {
		return oepsell;
	}

	private void setOepsell(String oepsell) {
		this.oepsell = oepsell;
	}

	public String getAction() {
		return action;
	}

	private void setAction(String action) {
		this.action = action;
	}

	public static ArrayList<RentalOrderListDetailObject> parseData(
			String response) throws Exception {

		ArrayList<RentalOrderListDetailObject> custlist = new ArrayList<>();

		RentalOrderListDetailObject detail = new RentalOrderListDetailObject();

		JSONArray detailarray = new JSONArray();
		detailarray = new JSONArray(response);

		for (int i = 0; i < detailarray.length(); i++) {
			detail = new RentalOrderListDetailObject();
			JSONObject userDetails = detailarray.getJSONObject(i);
			detail.setAddress(userDetails.getString(TAG_ADDRESS));
			detail.setAddress1(userDetails.getString(TAG_ADDRESS1));
			detail.setAddress2(userDetails.getString(TAG_ADDRESS2));
			detail.setAddress3(userDetails.getString(TAG_ADDRESS3));
			detail.setCustPhone(userDetails.getString(TAG_CUST_PHONE));
			detail.setCustsnum(userDetails.getString(TAG_CUST_NUM));
			detail.setDescription(userDetails.getString(TAG_DESCRIPTION)
					.replace(str, rep));
			detail.setEqpiGroup(userDetails.getString(TAG_EQPIGRP));
			detail.setKcustnum(userDetails.getString(TAG_KCUST_NUM));
			detail.setKcustSrch(userDetails.getString(TAG_CUST_SEARCH));
			detail.setKequipNum(userDetails.getString(TAG_EQUP_NUM));
			detail.setKmanufacture(userDetails.getString(TAG_KMFG));
			detail.setKmodel(userDetails.getString(TAG_KMODEL));
			detail.setKpart(userDetails.getString(TAG_KPART));
			detail.setKserialNum(userDetails.getString(TAG_SERIAL_NUM));
			detail.setOeonrentDate(userDetails.getString(TAG_OEONRENT_DATE));
			detail.setProgram(userDetails.getString(TAG_PROGRAM));
			detail.setShipAdd(userDetails.getString(TAG_SHIP_ADD));
			detail.setShipCity(userDetails.getString(TAG_SHIP_CITY));
			detail.setShipState(userDetails.getString(TAG_SHIP_STATE));
			detail.setUserName(userDetails.getString(TAG_USER_NAME)); 
			detail.setRate(userDetails.getString(TAG_RATE));
			detail.setOepsell(userDetails.getString(TAG_OEPSELL));
			detail.setAction(userDetails.getString(TAG_ACTION));
			detail.setQty(userDetails.getInt(TAG_QTY));
			detail.setMessage(userDetails.getString(TAG_MESSAGE));
			detail.setOedetailId(userDetails.getInt(TAG_OE_DETAIL_ID));
			detail.setQtyshipped(userDetails.getInt(TAG_QTY_SHIPPED));

			custlist.add(detail);
		}
		return custlist;
	}

	public String getDuplicatedesc() {
		return duplicatedesc;
	}

	public void setDuplicatedesc(String duplicatedesc) {
		this.duplicatedesc = duplicatedesc;
	}

	public String getFilterdesc() {
		return filterdesc;
	}

	public void setFilterdesc(String filterdesc) {
		this.filterdesc = filterdesc;
	}

	public String getFilterequpid() {
		return filterequpid;
	}

	public void setFilterequpid(String filterequpid) {
		this.filterequpid = filterequpid;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public int getQty() {
		return qty;
	}

	private void setQty(int qty) {
		this.qty = qty;
	}

	public int getOedetailId() {
		return oedetailId;
	}

	public void setOedetailId(int oedetailId) {
		this.oedetailId = oedetailId;
	}

	public String getPickNotes() {
		return pickNotes;
	}

	public void setPickNotes(String pickNotes) {
		this.pickNotes = pickNotes;
	}

	public int getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(int qtyshipped) {
		this.qtyshipped = qtyshipped;
	}
}
