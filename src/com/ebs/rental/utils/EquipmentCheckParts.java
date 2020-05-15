package com.ebs.rental.utils;

@SuppressWarnings("ALL")
public class EquipmentCheckParts {
	
	private String partsLabel;
	private int partsID;
	private int partType;
	private boolean partRequired;
	private boolean partCaptureRequired;
	private int lineItemID;
	private String controlsVAlue;
	private String controlsText;
	private String resultValue;
	private String notes;
	private String imagePaths;
	private boolean issignneed;
	
	//private int  pageID;
	
	
//	public int getPageID() {
//		return pageID;
//	}
//
//	public void setPageID(int pageID) {
//		this.pageID = pageID;
//	}

	public String getPartsLabel() {
		return partsLabel;
	}
	
	public void setPartsLabel(String partsLabel) {
		this.partsLabel = partsLabel;
	}
	
	
	public int getPartsID() {
		return partsID;
	}

	public void setPartsID(int partsID) {
		this.partsID = partsID;
	}


	public int getLineItemID() {
		return lineItemID;
	}

	public void setLineItemID(int lineItemID) {
		this.lineItemID = lineItemID;
	}

	public int getPartType() {
		return partType;
	}

	public void setPartType(int partType) {
		this.partType = partType;
	}

	public boolean isPartRequired() {
		return partRequired;
	}

	public void setPartRequired(boolean partRequired) {
		this.partRequired = partRequired;
	}

	public boolean isPartCaptureRequired() {
		return partCaptureRequired;
	}

	public void setPartCaptureRequired(boolean partCaptureRequired) {
		this.partCaptureRequired = partCaptureRequired;
	}

	public String getControlsVAlue() {
		return controlsVAlue;
	}

	public void setControlsVAlue(String controlsVAlue) {
		this.controlsVAlue = controlsVAlue;
	}

	public String getControlsText() {
		return controlsText;
	}

	public void setControlsText(String controlsText) {
		this.controlsText = controlsText;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(String imagePaths) {
		this.imagePaths = imagePaths;
	}

	public Boolean getIssignneed() {
		return issignneed;
	}

	public void setIssignneed(Boolean issignneed) {
		this.issignneed = issignneed;
	}
	

}
