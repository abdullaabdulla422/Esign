package com.ebs.rental.objects;

import org.json.JSONObject;

@SuppressWarnings("ALL")
public class RentalDocumentDetail {

	private static final String TAG_RESULT = "Result";

	private String Result;

	public String getResult() {
		return Result;
	}

	private void setResult(String result) {
		Result = result;
	}

	public static RentalDocumentDetail parseUser(String response) throws Exception {
		RentalDocumentDetail rental = new RentalDocumentDetail();

		JSONObject userDetails = new JSONObject(response);

		rental.setResult(userDetails.getString(TAG_RESULT));

		return rental;
	}

}
