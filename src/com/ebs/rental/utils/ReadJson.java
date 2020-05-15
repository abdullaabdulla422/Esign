package com.ebs.rental.utils;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressWarnings("ALL")
public class ReadJson {
	private static final String TAG_LINE_ITEM_ID = "listitemsid";
	private static final String TAG_CHK_PART_ID = "chkpartid";
	//public static final String TAG_PAGE_ID = "pageid";
	private static final String TAG_CONTROL_TYPE_ID = "controltypeid";
	private static final String TAG_IS_REQUIRED = "isrequired";
	private static final String TAG_LABEL = "label";
	private static final String TAG_IS_IMAGEALLOWED = "isimageallowed";
	private static final String TAG_CONTROLS_VALUE = "controlsvalue";
	private static final String TAG_CONTROLS_TEXT = "controlstext";
	private static final String TAG_RESULT_VALUES = "resultvalue";
	private static final String TAG_NOTES = "notes";
	private static final String TAG_IMAGE_PATHS="imagepaths";
    private static final String TAG_ISSIGNATURENEED = "issignatureneed";
	private static ArrayList<EquipmentCheckParts> partList;
	private static ArrayList<CheckList> checklist;
	private static String myjsonstring;
	@SuppressLint("LongLogTag")
	public static ArrayList<CheckList> getPartsList(String file) {
		
		checklist = new ArrayList<>();
		myjsonstring = file;
//		Log.d("checkit", myjsonstring);

		// Try to parse JSON
		try {
			JSONObject jsonObject = new JSONObject(myjsonstring);
			// Creating JSONObject from String
			JSONArray jsonObjMain = jsonObject.getJSONArray("Checklist");
			Log.d("The Checklist Size", ""+jsonObjMain.length());
			for (int i = 0; i < jsonObjMain.length(); i++) {
				JSONObject page  = jsonObjMain.getJSONObject(i);
				
				JSONArray pageList = page.getJSONArray("Page");
				
				Log.d("The individual page Size", ""+pageList.length());
				
				CheckList check = new CheckList();
				partList = new ArrayList<>();
				for (int j = 0; j < pageList.length(); j++) {
					JSONObject pageContent = pageList.getJSONObject(j);
					EquipmentCheckParts partsCheck = new EquipmentCheckParts();
					partsCheck.setLineItemID(pageContent.getInt(TAG_LINE_ITEM_ID));
					//partsCheck.setPageID(pageContent.getInt(TAG_PAGE_ID));
					partsCheck.setPartCaptureRequired(pageContent.getBoolean(TAG_IS_IMAGEALLOWED));
					partsCheck.setPartRequired(pageContent.getBoolean(TAG_IS_REQUIRED));
					partsCheck.setPartsID(pageContent.getInt(TAG_CHK_PART_ID));
					partsCheck.setPartsLabel(pageContent.getString(TAG_LABEL));
					partsCheck.setPartType(pageContent.getInt(TAG_CONTROL_TYPE_ID));
					partsCheck.setControlsVAlue(pageContent.getString(TAG_CONTROLS_VALUE));
					partsCheck.setControlsText(pageContent.getString(TAG_CONTROLS_TEXT));
					partsCheck.setResultValue(pageContent.getString(TAG_RESULT_VALUES));
					partsCheck.setNotes(pageContent.getString(TAG_NOTES));
					partsCheck.setImagePaths(pageContent.getString(TAG_IMAGE_PATHS));
					partsCheck.setIssignneed(pageContent.getBoolean(TAG_ISSIGNATURENEED));
					partList.add(partsCheck);
				}
				check.setChecklist(partList);
	        	checklist.add(check);
			}
			
		}
		catch (Exception e) {
            e.printStackTrace();
        }
        return checklist;
	}
	
}
