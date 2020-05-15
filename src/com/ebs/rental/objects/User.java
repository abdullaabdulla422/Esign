package com.ebs.rental.objects;

import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.SoapUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class User extends SoapUtils {

	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "userpassword";
	private static final String TAG_DESCRIPTION = "Description";
	public static final String TAG_ROLE = "Role";
	private static final String TAG_USERID = "RcdNum";
	private static final String TAG_VERSION = "Version";
	public static final String TAG_USER_ROLE_ID = "usrroleid";
	public static final String TAG_USER_MAIL = "email";
	public static final String TAG_THEME = "Theme";
	public static final String TAG_USER_TOKEN = "usrtoken";
	public static final String TAG_TICKET_COUNT = "TicketCount";
	public static final String TAG_HAS_RIGHTS_MENU_ACCESS = "HasRightsMenuAccess";
	public static final String TAG_PHONE = "phone";
	public static final String TAG_CREATE_RIGHTS = "hasquotecreaterights";
	public static final String TAG_CONTACT = "contact";
	public static final String TAG_SALE_CONVERT_RIGHTS = "hasquotetosaleconvertrights";
	public static final String TAG_BRANCH = "Branch";
	public static final String TAG_TRUCKID = "truckId";

	
	public static HashMap<String, String> userMap;

	private String username;
	private String password;
	private String role;
	private int userId;
	private int userRoleId;
	private String theme;
	private String userToken;
	private String ticketCount;
	private boolean hasRightsMenuAccess;
	private String phone;
	private boolean hasquotecreaterights;
	private boolean hasquotetosaleconvertrights;
	private String contact;
	private String userMail;
	private String userDescription;

	private String Version;
	private String Branch;
	private String truckId;

	// Getters
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public int getUserId() {
		return userId;
	}

	public String getTheme() {
		return theme;
	}

	public String getUserToken() {
		return userToken;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public boolean isHasRightsMenuAccess() {
		return hasRightsMenuAccess;
	}

	public String getPhone() {
		return phone;
	}

	public String getContact() {
		return contact;
	}

	// Setters
	private void setUsername(String username) {
		this.username = username;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private void setUserId(int userId) {
		this.userId = userId;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public void setHasRightsMenuAccess(boolean hasRightsMenuAccess) {
		this.hasRightsMenuAccess = hasRightsMenuAccess;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	

//	public static User parseUser(SoapObject soapObject) throws Exception {
//
//		User object = new User();
//		object.setUserId(Integer.parseInt(getProperty(soapObject, TAG_USERID)));
//		object.setRole(getProperty(soapObject, TAG_ROLE));
//		object.setTheme(getProperty(soapObject, TAG_THEME));
//		object.setUsername(getProperty(soapObject, TAG_USERNAME));
//		object.setUserToken(getProperty(soapObject, TAG_USER_TOKEN));
//		object.setTicketCount(getProperty(soapObject, TAG_TICKET_COUNT));
//		object.setHasRightsMenuAccess(getPropertyAsBoolean(soapObject, TAG_HAS_RIGHTS_MENU_ACCESS));
//		object.setPhone(getProperty(soapObject, TAG_PHONE));
//		object.setContact(getProperty(soapObject, TAG_CONTACT));
//
//		return object;
//	}
	
	
	
	public String getUserMail() {
		return userMail;
	}

	public String getUserDescription() {
		return userDescription;
	}

	private void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	public boolean isHasquotecreaterights() {
		return hasquotecreaterights;
	}

	public void setHasquotecreaterights(boolean hasquotecreaterights) {
		this.hasquotecreaterights = hasquotecreaterights;
	}

	public boolean isHasquotetosaleconvertrights() {
		return hasquotetosaleconvertrights;
	}

	public void setHasquotetosaleconvertrights(boolean hasquotetosaleconvertrights) {
		this.hasquotetosaleconvertrights = hasquotetosaleconvertrights;
	}

	public static User parseUser(String response) throws Exception {
		User user = new User();
		
		JSONArray array = new JSONArray(response);
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject userDetails = array.getJSONObject(0);
			
			user.setUsername(userDetails.getString(TAG_USERNAME));
			user.setPassword(userDetails.getString(TAG_PASSWORD));
			user.setUserDescription(userDetails.getString(TAG_DESCRIPTION));
			user.setVersion(userDetails.getString(TAG_VERSION));
			SessionData.getInstance().setTemp_userToken(user.getUserDescription());
//			user.setUserToken(userDetails.getString(TAG_USER_TOKEN));
					user.setUserId(userDetails.getInt(TAG_USERID));
			SessionData.getInstance().setTemp_userid(userDetails.getInt(TAG_USERID));

			user.setBranch(userDetails.getString(TAG_BRANCH));
			user.setTruckId(userDetails.getString(TAG_TRUCKID));

//			user.setUserRoleId(userDetails.getInt(TAG_USER_ROLE_ID));
//			user.setUserMail(userDetails.getString(TAG_USER_MAIL));
//			user.setPhone(userDetails.getString(TAG_PHONE));
//			user.setHasquotecreaterights(userDetails.getBoolean(TAG_CREATE_RIGHTS));
//			user.setHasquotetosaleconvertrights(userDetails.getBoolean(TAG_SALE_CONVERT_RIGHTS));
			
		}
		
		
		

		return user;
	}


	public String getVersion() {
		return Version;
	}

	private void setVersion(String version) {
		Version = version;
	}

	public String getBranch() {
		return Branch;
	}

	public void setBranch(String branch) {
		Branch = branch;
	}

	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
}
