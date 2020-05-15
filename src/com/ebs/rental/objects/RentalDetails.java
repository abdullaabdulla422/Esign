package com.ebs.rental.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("ALL")
public class RentalDetails {
	
	
	private static final String TAG_ORDER_NO = "orderno";
	private static final String TAG_CUST_NAME = "custname";
	private static final String TAG_K_PART = "Kpart";
	private static final String TAG_CONTACT = "contact";
	private static final String TAG_ADDRESS_1 = "addressl1";
	private static final String TAG_ADDRESS_2 = "addressl2";
	private static final String TAG_CITY = "city";
	private static final String TAG_STATE = "state";
	private static final String TAG_ZIP = "zip";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_ORDER_DATE = "orderdate";
	private static final String TAG_BRANCH = "branch";
	private static final String TAG_RENTAL_ID = "rentalid";
	private static final String TAG_SALES_REP = "salesrep";
	private static final String TAG_DESC = "equipdesc";
	private static final String TAG_SPEC="pmspec";
	private static final String TAG_METER="equipmentmeter";
	private static final String TAG_READING="equipmentreading";
	private static final String TAG_DATE_LAST="equipmentdatelast";
	private static final String TAG_LAST_HOURS="lasthours";
	private static final String TAG_STATUS="pmduestatus";
	private static final String TAG_EQUPMENT_ID="equipid";
	private static final String TAG_RENTAL_DETAIL_ID="rentdetlid";
	private static final String TAG_INSPECTION_ID="inspectionid";
	private static final String  TAG_EQUIPMENT_INSPECTION_ID = "equipinspectionid";
	private static final String TAG_HOUR_METER = "hourmeter";
	private static final String TAG_EQP_STATUS = "eqpstatus";
	private static final String TAG_KCUSTNUM = "kcustnum";
	private static final String TAG_CUSTSNUM = "custsnum";
	private static final String TAG_MESSSAGE = "message";
	
	
	
	
	private int orderNo;
	private String custName;
	private String kPart;
	private String contact;
	private String contactdup;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String phoneNo;
	private String orderDate;
	private String branch;
	private int rentalID;
	private String salesRep;
	private String desc;
	private String pmSpec;
	private String equipmentMeter;
	private String equipmentReading;
	private String lastDate;
	private String lastHours;
	private String dueStatus;
	private int equpId;
	private int rentDetId;
	private int inspectionId;
	private int equipmentInspectionId;
	private String hourMeter;
	private String eqpStatus;
	private String kcustnum;
	private String custsnum;
	private String message;

	private boolean selected = false;
	
	public int getOrderNo() {
		return orderNo;
	}

	private void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustName() {
		return custName;
	}

	private void setCustName(String custName) {
		this.custName = custName;
	}

	public String getkPart() {
		return kPart;
	}

	private void setkPart(String kPart) {
		this.kPart = kPart;
	}

	public String getContact() {
		return contact;
	}

	private void setContact(String contact) {
		this.contact = contact;
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

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	private void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	private void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	private void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	private void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getBranch() {
		return branch;
	}

	private void setBranch(String branch) {
		this.branch = branch;
	}

	public int getRentalID() {
		return rentalID;
	}

	private void setRentalID(int rentalID) {
		this.rentalID = rentalID;
	}

	public String getSalesRep() {
		return salesRep;
	}

	private void setSalesRep(String salesRep) {
		this.salesRep = salesRep;
	}

	public String getDesc() {
		return desc;
	}

	private void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	
	public int getEquipmentInspectionId() {
		return equipmentInspectionId;
	}

	private void setEquipmentInspectionId(int equipmentInspectionId) {
		this.equipmentInspectionId = equipmentInspectionId;
	}

	public static ArrayList<RentalDetails> parseData(String response) throws Exception{
		
		ArrayList<RentalDetails> rental = new ArrayList<>();
		
		RentalDetails detail =  new RentalDetails();
		
		JSONArray detailarray = new JSONArray();
		detailarray = new JSONArray(response);
		
		for (int i = 0; i < detailarray.length(); i++) {
			detail =  new RentalDetails();
			JSONObject userDetails = detailarray.getJSONObject(i);
		
			detail.setCustName(userDetails.getString(TAG_CUST_NAME));
			detail.setOrderNo(userDetails.getInt(TAG_ORDER_NO));
			detail.setkPart(userDetails.getString(TAG_K_PART));
			detail.setContact(userDetails.getString(TAG_CONTACT));
			detail.setAddress1(userDetails.getString(TAG_ADDRESS_1));
			detail.setAddress2(userDetails.getString(TAG_ADDRESS_2));
			detail.setCity(userDetails.getString(TAG_CITY));
			detail.setState(userDetails.getString(TAG_STATE));
			detail.setZip(userDetails.getString(TAG_ZIP));
			detail.setPhoneNo(userDetails.getString(TAG_PHONE));
			detail.setOrderDate(userDetails.getString(TAG_ORDER_DATE));
			detail.setBranch(userDetails.getString(TAG_BRANCH));
			detail.setRentalID(userDetails.getInt(TAG_RENTAL_ID));
			detail.setSalesRep(userDetails.getString(TAG_SALES_REP));	
			detail.setDesc(userDetails.getString(TAG_DESC));
			detail.setPmSpec(userDetails.getString(TAG_SPEC));
			detail.setEquipmentMeter(userDetails.getString(TAG_METER));
			detail.setEquipmentReading(userDetails.getString(TAG_READING));
			detail.setLastDate(userDetails.getString(TAG_DATE_LAST));
			detail.setLastHours(userDetails.getString(TAG_LAST_HOURS));
			detail.setDueStatus(userDetails.getString(TAG_STATUS));
			detail.setEqupId(userDetails.getInt(TAG_EQUPMENT_ID));
			detail.setInspectionId(userDetails.getInt(TAG_INSPECTION_ID));
			detail.setRentDetId(userDetails.getInt(TAG_RENTAL_DETAIL_ID));
			detail.setEquipmentInspectionId(userDetails.getInt(TAG_EQUIPMENT_INSPECTION_ID));
			detail.setHourMeter(userDetails.getString(TAG_HOUR_METER));
			detail.setEqpStatus(userDetails.getString(TAG_EQP_STATUS));
			detail.setKcustnum(userDetails.getString(TAG_KCUSTNUM));
			detail.setCustsnum(userDetails.getString(TAG_CUSTSNUM));
			detail.setMessage(userDetails.getString(TAG_MESSSAGE));
			rental.add(detail);
		}
		
		return rental;
		
	}

	public String getPmSpec() {
		return pmSpec;
	}

	private void setPmSpec(String pmSpec) {
		this.pmSpec = pmSpec;
	}

	public String getEquipmentMeter() {
		return equipmentMeter;
	}

	private void setEquipmentMeter(String equipmentMeter) {
		this.equipmentMeter = equipmentMeter;
	}

	public String getEquipmentReading() {
		return equipmentReading;
	}

	private void setEquipmentReading(String equipmentReading) {
		this.equipmentReading = equipmentReading;
	}

	public String getLastDate() {
		return lastDate;
	}

	private void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getLastHours() {
		return lastHours;
	}

	private void setLastHours(String lastHours) {
		this.lastHours = lastHours;
	}

	public String getDueStatus() {
		return dueStatus;
	}

	private void setDueStatus(String dueStatus) {
		this.dueStatus = dueStatus;
	}

	public int getEqupId() {
		return equpId;
	}

	private void setEqupId(int equpId) {
		this.equpId = equpId;
	}

	public int getRentDetId() {
		return rentDetId;
	} 

	private void setRentDetId(int rentDetId) {
		this.rentDetId = rentDetId;
	}

	public int getInspectionId() {
		return inspectionId;
	}

	private void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
	}

	private Collection<? extends RentalDetails> readAll() {
		
		return readAll();
	}

	public String getHourMeter() {
		return hourMeter;
	}

	private void setHourMeter(String hourMeter) {
		this.hourMeter = hourMeter;
	}

	public String getEqpStatus() {
		return eqpStatus;
	}

	private void setEqpStatus(String eqpStatus) {
		this.eqpStatus = eqpStatus;
	}

	public String getContactdup() {
		return contactdup;
	}

	public void setContactdup(String contactdup) {
		this.contactdup = contactdup;
	}

	public String getKcustnum() {
		return kcustnum;
	}

	private void setKcustnum(String kcustnum) {
		this.kcustnum = kcustnum;
	}

	public String getCustsnum() {
		return custsnum;
	}

	private void setCustsnum(String custsnum) {
		this.custsnum = custsnum;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
