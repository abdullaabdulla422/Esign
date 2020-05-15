package com.ebs.rental.objects;

import com.ebs.rental.webutils.SoapUtils;

import org.ksoap2.serialization.SoapObject;

@SuppressWarnings("ALL")
public class EquipmentParts extends SoapUtils{

	private static final String TAG_PART_ID = "id";
	private static final String TAG_PART_FACE_NO = "partfaceno";
	private static final String TAG_PART_NAME = "partname";

	private static final String TAG_MESSAGE = "message";
	
	private int partID;
	private int partFaceNo;
	private String partName;
	private String message;
	
	public int getPartID() {
		return partID;
	}

	public int getPartFaceNo() {
		return partFaceNo;
	}

	public String getPartName() {
		return partName;
	}

	private void setPartID(int partID) {
		this.partID = partID;
	}

	private void setPartFaceNo(int partFaceNo) {
		this.partFaceNo = partFaceNo;
	}

	private void setPartName(String partName) {
		this.partName = partName;
	}


	@SuppressWarnings("RedundantThrows")
	public static EquipmentParts parseEquipments(SoapObject soapObject) throws Exception {
		
		EquipmentParts parts = new EquipmentParts();
			parts.setPartID(getPropertyAsInt(soapObject, TAG_PART_ID));
			parts.setPartFaceNo(getPropertyAsInt(soapObject, TAG_PART_FACE_NO));
			parts.setPartName(getProperty(soapObject, TAG_PART_NAME));
		    parts.setMessage(getProperty(soapObject, TAG_MESSAGE));
		return parts;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
