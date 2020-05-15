package com.ebs.rental.objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DealerBranches {
	private static final String TAG_BRANCH_NO = "kbranch";
	private static final String TAG_BRANCH_NAME = "branchName";
	private static final String TAG_ROWNUM = "rownum";
	public static final String TAG_END_INDEX = "endindex";
	private static final String TAG_ROWCNT = "rowcnt";
	private static final String TAG_MESSAGE = "message";
	private String kBranch;
	private String branchName;
	private int prownum;
	private int prowcnt;

	private String Message;
	
	 
  
	public String getkBranch() {
		return kBranch;
	} 
 
	private void setkBranch(String kBranch) {
		this.kBranch = kBranch; 
	}

	public String getBranchName() {  
		return branchName;
	}

	private void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public int getPrownum() {
		return prownum;
	}

	private void setPrownum(int prownum) {
		this.prownum = prownum;
	}

	public int getProwcnt() {
		return prowcnt;
	}

	private void setProwcnt(int prowcnt) {
		this.prowcnt = prowcnt;
	}


	public static ArrayList<DealerBranches> parseData(String response)
			throws Exception {

		ArrayList<DealerBranches> dealer = new ArrayList<>();
 
		DealerBranches detail = new DealerBranches();

		JSONArray detailarray = new JSONArray();
		detailarray = new JSONArray(response);

		for (int i = 0; i < detailarray.length(); i++) { 
			detail = new DealerBranches();
			JSONObject userDetails = detailarray.getJSONObject(i);
			detail.setBranchName(userDetails.getString(TAG_BRANCH_NAME));
			Log.d("branchname", "" + userDetails.getString(TAG_BRANCH_NAME));
			detail.setkBranch(userDetails.getString(TAG_BRANCH_NO));
			detail.setPrownum(userDetails.getInt(TAG_ROWNUM));
			detail.setProwcnt(userDetails.getInt(TAG_ROWCNT));
			Log.d("RowCount", "" + userDetails.getInt(TAG_ROWCNT));
			detail.setMessage(userDetails.getString(TAG_MESSAGE));

			dealer.add(detail);
		}
		return dealer;
	}



	public String getMessage() {
		return Message;
	}

	private void setMessage(String message) {
		Message = message;
	}
}
