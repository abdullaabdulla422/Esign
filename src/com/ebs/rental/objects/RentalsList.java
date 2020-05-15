package com.ebs.rental.objects;

import com.ebs.rental.webutils.SoapUtils;

import org.ksoap2.serialization.SoapObject;

@SuppressWarnings("ALL")
public class RentalsList extends SoapUtils {

	private static final String TAG_SHIPTO_NAME = "shiptoname";
	private static final String TAG_SHIPTO_ADDRESS = "shiptoaddress";
	private static final String TAG_ORDER_NO = "orderno";
	private static final String TAG_RENT_DATE = "rentdate";
	private static final String TAG_RENT_ID = "rentid";
	private static final String TAG_CUST_NAME = "custname";
	private static final String TAG_EQUIPMENT_ID = "equipmentid";
	private static final String TAG_DEALER_BRANCH_CODE = "dealerbranchcode";
	private static final String TAG_DEALER_BRANCH_NAME = "dealerbranchname";
	private static final String TAG_DELIVERY_INSPECTION_ID="deliveryinspectionid";
	private static final String TAG_RETURN_INSPECTION_ID = "returninspectionid";
	private static final String TAG_MESSAGE = "message";

	private String shipToName;
	private String shipToAddress;
	private int orderNo;
	private int rentId;
	private String custName;
	private String equipmentId;
	private String dealerCode;
	private String dealerName;
	private String rentalDate;
	private String deliveryId;
	private String returnId;
	private String message;

	public String getRentalDate() {
		return rentalDate;
	}

	private void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}

	public String getShipToName() {
		return shipToName;
	}

	private void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	private void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public int getOrderNo() {
		return orderNo;
	}

	private void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getRentId() {
		return rentId;
	}

	private void setRentId(int rentId) {
		this.rentId = rentId;
	}

	public String getCustName() {
		return custName;
	}

	private void setCustName(String custName) {
		this.custName = custName;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	private void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	private void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	private void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	private void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getReturnId() {
		return returnId;
	}

	private void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	@SuppressWarnings("RedundantThrows")
	public static RentalsList parseRentalsList(SoapObject soapObject) throws Exception {
		
		RentalsList rentalsList = new RentalsList();
		rentalsList.setShipToName(getProperty(soapObject, TAG_SHIPTO_NAME));
		rentalsList.setShipToAddress(getProperty(soapObject, TAG_SHIPTO_ADDRESS));
		rentalsList.setOrderNo(getPropertyAsInt(soapObject, TAG_ORDER_NO));
		rentalsList.setRentId(getPropertyAsInt(soapObject, TAG_RENT_ID));
		rentalsList.setCustName(getProperty(soapObject, TAG_CUST_NAME));
		rentalsList.setEquipmentId(getProperty(soapObject, TAG_EQUIPMENT_ID));
		rentalsList.setDealerCode(getProperty(soapObject, TAG_DEALER_BRANCH_CODE));
		rentalsList.setDealerName(getProperty(soapObject, TAG_DEALER_BRANCH_NAME));
		rentalsList.setRentalDate(getProperty(soapObject, TAG_RENT_DATE));
		rentalsList.setDeliveryId(getProperty(soapObject, TAG_DELIVERY_INSPECTION_ID));
		rentalsList.setReturnId(getProperty(soapObject, TAG_RETURN_INSPECTION_ID));
		rentalsList.setMessage(getProperty(soapObject, TAG_MESSAGE));
		return rentalsList;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
