package com.ebs.rental.objects;

import com.ebs.rental.webutils.SoapUtils;

import org.ksoap2.serialization.SoapObject;

@SuppressWarnings("ALL")
public class RentalCheckinList extends SoapUtils{
	
	private static final String TAG_LIST_ITEM_ID = "listitemsid";
	private static final String TAG_CHECK_PART_ID = "chkpartid";
	private static final String TAG_PAGE_ID = "pageid";
	private static final String TAG_LABEL = "label";
	private static final String TAG_CONTROL_TYPE_ID = "controltypeid";
	
	private static final String TAG_IS_REQUIRED = "isrequired";
	private static final String TAG_IS_IMAGE_ALLOWED = "isimageallowed";

	
	
	private int listItemID;
	private int checkPartID;
	private int  pageID;
	private String label;
	private int controlTypeID;
	private boolean isRequired;
	private boolean isImageAllowed;
	
	public int getListItemID() {
		return listItemID;
	}
	
	private void setListItemID(int listItemID) {
		this.listItemID = listItemID;
	}
	
	public int getCheckPartID() {
		return checkPartID;
	}
	
	private void setCheckPartID(int checkPartID) {
		this.checkPartID = checkPartID;
	}
	public int getPageID() {
		return pageID;
	}
	
	private void setPageID(int pageID) {
		this.pageID = pageID;
	}
	public String getLabel() {
		return label;
	}
	private void setLabel(String label) {
		this.label = label;
	}
	
	public int getControlTypeID() {
		return controlTypeID;
	}
	
	private void setControlTypeID(int controlTypeID) {
		this.controlTypeID = controlTypeID;
	}
	
	public boolean isRequired() {
		return isRequired;
	}
	
	private void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	public boolean isImageAllowed() {
		return isImageAllowed;
	}
	
	private void setImageAllowed(boolean isImageAllowed) {
		this.isImageAllowed = isImageAllowed;
	}

	@SuppressWarnings("RedundantThrows")
	public static RentalCheckinList parseRentalCheckinList(SoapObject soapObject)throws Exception {
		RentalCheckinList list = new RentalCheckinList();
		list.setListItemID(getPropertyAsInt(soapObject,TAG_LIST_ITEM_ID));
		list.setCheckPartID(getPropertyAsInt(soapObject,TAG_CHECK_PART_ID));
		list.setPageID(getPropertyAsInt(soapObject,TAG_PAGE_ID));
		list.setLabel(getProperty(soapObject,TAG_LABEL));
		list.setControlTypeID(getPropertyAsInt(soapObject,TAG_CONTROL_TYPE_ID));
		list.setRequired(getPropertyAsBoolean(soapObject,TAG_IS_REQUIRED));
		list.setImageAllowed(getPropertyAsBoolean(soapObject,TAG_IS_IMAGE_ALLOWED));
		// TODO Auto-generated method stub
		return list;
	}
	
	
	
	
	

}
