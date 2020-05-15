package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalOrderCustomerList {
	private static final String TAG_CUST_NAME = "custname";
	private static final String TAG_CUST_NUM = "custnum";
	private static final String TAG_SHIP_TO = "custsnum";
	private static final String TAG_MESSAGE = "message";
	
	private String custName;
	private String custNum;
	private String shipTo;
	private String message;

	public String getCustName() {
		return custName;
	}
	private void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustNum() {
		return custNum;
	}
	private void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public String getShipTo() {
		return shipTo;
	}
	private void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	public static ArrayList<RentalOrderCustomerList> parseData(String response)
			throws Exception {

		ArrayList<RentalOrderCustomerList> custlist = new ArrayList<>();

		RentalOrderCustomerList detail = new RentalOrderCustomerList();

		JSONArray detailarray = new JSONArray();
		detailarray = new JSONArray(response);

		for (int i = 0; i < detailarray.length(); i++) {
			detail = new RentalOrderCustomerList();
			JSONObject userDetails = detailarray.getJSONObject(i);
			detail.setCustName(userDetails.getString(TAG_CUST_NAME));
			detail.setCustNum(userDetails.getString(TAG_CUST_NUM));
			detail.setShipTo(userDetails.getString(TAG_SHIP_TO));
			detail.setMessage(userDetails.getString(TAG_MESSAGE));
			
			custlist.add(detail);
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
