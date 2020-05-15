package com.ebs.rental.objects;

import org.json.JSONObject;

public class PartOrderTerms {
	private static final String TAG_RESULT = "Result";
	private static final String TAG_MESSAGE = "Message";

	private String Result;
	private String Message;


	public String getResult() {
		return Result;
	}

	private void setResult(String result) {
		Result = result;
	}
	public static PartOrderTerms parseUser(String response) throws Exception {
		PartOrderTerms prental = new PartOrderTerms();

		JSONObject userDetails = new JSONObject(response);

		prental.setResult(userDetails.getString(TAG_RESULT));

		return prental;
	}

	public static PartOrderTerms parseMessage(String response) throws Exception {
		PartOrderTerms prental = new PartOrderTerms();

		JSONObject userDetails = new JSONObject(response);

		prental.setMessage(userDetails.getString(TAG_MESSAGE));

		return prental;
	}

	public String getMessage() {
		return Message;
	}

	private void setMessage(String message) {
		Message = message;
	}
}
