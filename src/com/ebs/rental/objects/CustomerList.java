package com.ebs.rental.objects;

import android.util.Log;

import com.ebs.rental.webutils.SoapUtils;

import org.ksoap2.serialization.SoapObject;

public class CustomerList extends SoapUtils {

	private static final String TAG_CUSTOMER_NAME = "customername";
	private static final String TAG_CUST_NUM = "kcustnum";
	private static final String TAG_MESSAGE = "message";
	private String customerName;
	private String custNum;
	private String message;

	public String getCustomerName() {
		return customerName;
	}

	private void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustNum() {
		return custNum;
	}

	private void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	@SuppressWarnings("RedundantThrows")
	public static CustomerList parseCustomerList(SoapObject soapObject) throws Exception {
		
		CustomerList customer = new CustomerList();
		customer.setCustomerName(getProperty(soapObject, TAG_CUSTOMER_NAME));
		customer.setCustNum(getProperty(soapObject, TAG_CUST_NUM));
		customer.setMessage(getProperty(soapObject, TAG_MESSAGE));
		Log.d("Customer", ""+customer.getCustomerName());
		Log.d("Customer", ""+customer.getCustNum());
		return customer;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
