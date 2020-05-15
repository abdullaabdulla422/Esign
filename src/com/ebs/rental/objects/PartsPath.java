package com.ebs.rental.objects;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class PartsPath {
	private String partsName;
	private ArrayList<String> imagePath;

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public ArrayList<String> getImagePath() {
		if (imagePath == null) {
			imagePath = new ArrayList<>();
		}
		return imagePath;
	}

	public void setImagePath(ArrayList<String> imagePath) {
		this.imagePath = imagePath;
	}

}
