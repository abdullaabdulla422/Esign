package com.ebs.rental.objects;

import org.json.JSONObject;

@SuppressWarnings("ALL")
public class RentalOrderDocumentPdf {

	private static final String TAG_RESULT = "Result";

	private String Result;

	public String getResult() {
		return Result;
	}

	private void setResult(String result) {
		Result = result;
	}

	public static RentalOrderDocumentPdf parseUser(String response) throws Exception {
		RentalOrderDocumentPdf rental = new RentalOrderDocumentPdf();

		JSONObject userDetails = new JSONObject(response);

		rental.setResult(userDetails.getString(TAG_RESULT));

		return rental;
	}

}