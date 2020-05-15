package com.ebs.rental.objects;

@SuppressWarnings("ALL")
public class CheckinListData {
	
	private int data;
	private String imagePath;
	private int lineitemId;
	private String controlValue;
	private String imageName;
	private String notes;  
	private String status;  
	private String values;
	private String Labels;
	private int cameraid;
	
	
	
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public String getImagePath() {
		if(imagePath == null) {
			imagePath = "";
		}
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getLineitemId() {
		return lineitemId;
	}
	public void setLineitemId(int lineitemId) {
		this.lineitemId = lineitemId;
	}
	

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getControlValue() {
		return controlValue;
	}

	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getLabels() {
		return Labels;
	}

	public void setLabels(String labels) {
		Labels = labels;
	}

	public int getCameraid() {
		return cameraid;
	}

	public void setCameraid(int cameraid) {
		this.cameraid = cameraid;
	}

	


	}
