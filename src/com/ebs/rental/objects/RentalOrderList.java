package com.ebs.rental.objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalOrderList {
	private static final String TAG_BRANCH = "branch";
	private static final String TAG_ORDER_NO = "orderno";
	private static final String TAG_ORDER_DATE = "orderdate";
	private static final String TAG_CUST_NO = "custno";
	private static final String TAG_SHIP_TO = "shipto";
	private static final String TAG_CUST_NAME = "custname";
	private static final String TAG_CONTACT = "contact";
	
	private static final String TAG_SIGNADOCID = "signdocid";
	private static final String TAG_BRANCH_NAME = "branchname";
	private static final String TAG_CUSTOMER_PO = "pono";
	private static final String TAG_TYPE = "ordertype";
	private static final String TAG_MESSAGE = "message";
	
	
//	[{"branch":"300","orderno":655253,"orderdate":"07/29/2011","custno":"847070","shipto":"001",
//		"custname":"BLACK & DECKER/STANLEY","contact":"HUGO ALONZO","signdocid":0,
//		"branchname":"RHSI - ONTARIO","pono":"HUGO ALONZO","ordertype":"S"},
//		{"branch":"300","orderno":655579,"orderdate":"08/03/2011","custno":"847070",
//			"shipto":"001","custname":"BLACK & DECKER/STANLEY","contact":"HUGO ALONZO",
//		"signdocid":0,"branchname":"RHSI - ONTARIO","pono":"HUGO ALONZO","ordertype":"S"}]

	
	private String branch;
	private int orderno;
	private String orderdate;
	private String custno;
	private String shipto;
	private String custname;
	private String contact;
	private String branchName;
	private String customerPo;
	private String type;
	private String message;
	
	private int signdocid;
	public String getBranch() {
		return branch;
	}
	private void setBranch(String branch) {
		this.branch = branch;
	}
	public int getOrderno() {
		return orderno;
	}
	private void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getOrderdate() {
		return orderdate;
	}
	private void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getCustno() {
		return custno;
	}
	private void setCustno(String custno) {
		this.custno = custno;
	}
	public String getShipto() {
		return shipto;
	}
	private void setShipto(String shipto) {
		this.shipto = shipto;
	}
	public String getCustname() {
		return custname;
	}
	private void setCustname(String custname) {
		this.custname = custname;
	}
	public String getContact() {
		return contact;
	}
	private void setContact(String contact) {
		this.contact = contact;
	}
	
	public int getSigndocid() {
		return signdocid;
	}
	private void setSigndocid(int signdocid) {
		this.signdocid = signdocid;
	}
	public String getBranchName() {
		return branchName;
	}
	private void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	private void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getType() {
		return type;
	}
	private void setType(String type) {
		this.type = type;
	}

	public static ArrayList<RentalOrderList> parseData(String response)
			throws Exception {

		ArrayList<RentalOrderList> custlist = new ArrayList<>();

		RentalOrderList detail = new RentalOrderList();

		JSONArray detailarray = new JSONArray();
		detailarray = new JSONArray(response);

		for (int i = 0; i < detailarray.length(); i++) {
			detail = new RentalOrderList();
			JSONObject userDetails = detailarray.getJSONObject(i);
			detail.setBranch(userDetails.getString(TAG_BRANCH));
			Log.d("Result for order list", "" + userDetails.getString(TAG_BRANCH));
			detail.setCustname(userDetails.getString(TAG_CUST_NAME));
			Log.d("Result for order list", "" + userDetails.getString(TAG_CUST_NAME));
			detail.setContact(userDetails.getString(TAG_CONTACT));
			Log.d("Result for order list", "" + userDetails.getString(TAG_CONTACT));
			detail.setCustno(userDetails.getString(TAG_CUST_NO));
			Log.d("Result for order list", "" + userDetails.getString(TAG_CUST_NO));
			detail.setOrderdate(userDetails.getString(TAG_ORDER_DATE));
			Log.d("Result for order list", "" + userDetails.getString(TAG_ORDER_DATE));
			detail.setOrderno(userDetails.getInt(TAG_ORDER_NO));
			Log.d("Result for order list", "" + userDetails.getInt(TAG_ORDER_NO));
			detail.setShipto(userDetails.getString(TAG_SHIP_TO));
			Log.d("Result for order list", "" + userDetails.getString(TAG_SHIP_TO));
			detail.setSigndocid(userDetails.getInt(TAG_SIGNADOCID));
			Log.d("Result for order list", "" + userDetails.getInt(TAG_SIGNADOCID));
			detail.setBranchName(userDetails.getString(TAG_BRANCH_NAME));
			Log.d("Result for order list", "" + userDetails.getString(TAG_BRANCH_NAME));
			detail.setCustomerPo(userDetails.getString(TAG_CUSTOMER_PO));
			Log.d("Result for order list", "" + userDetails.getString(TAG_CUSTOMER_PO));
			detail.setType(userDetails.getString(TAG_TYPE));
			Log.d("Result for order list", "" + userDetails.getString(TAG_TYPE));
			detail.setMessage(userDetails.getString(TAG_MESSAGE));
//			[{"branch":"300","orderno":655253,"orderdate":"07/29/2011","custno":"847070","shipto":"001",
//			"custname":"BLACK & DECKER/STANLEY","contact":"HUGO ALONZO","signdocid":0,
//			"branchname":"RHSI - ONTARIO","pono":"HUGO ALONZO","ordertype":"S"},
//			{"branch":"300","orderno":655579,"orderdate":"08/03/2011","custno":"847070",
//				"shipto":"001","custname":"BLACK & DECKER/STANLEY","contact":"HUGO ALONZO",
//			"signdocid":0,"branchname":"RHSI - ONTARIO","pono":"HUGO ALONZO","ordertype":"S"}]
			
			custlist.add(detail);
			Log.d("Result for order list", "" + custlist);
		}
		return custlist;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
