package com.ebs.rental.general;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.ebs.rental.general.SimpleGestureFilter.SimpleGestureListener;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.EBSRentalConstants;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.IOException;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements
		SimpleGestureListener, OnClickListener {

	public static boolean fromtablet = false;
	private LinearLayout popup;
	private Button btnLogin;
	private Button btnSave;
	private Button btnCancel;
	ActionBar actionBar;
	private User objUser = null;
	private String username1;
	private String password1;
	private ToggleButton remember;
	ImageView logo;
	private EditText username;
	private EditText password;
	private EditText serviceLink;
	private SimpleGestureFilter detector;
	private static SharedPreferences sharedPreferences;
	private static SharedPreferences sharedPreferencess;

	private static SharedPreferences.Editor sharedPreferlogin;
	private static SharedPreferences.Editor sharedPreferserver;
	private Boolean saveLogin, server;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		sharedPreferences = getSharedPreferences("EBSRental", MODE_PRIVATE);
		sharedPreferencess = getSharedPreferences("EBSRentals", MODE_PRIVATE);
		sharedPreferserver = sharedPreferencess.edit();
		sharedPreferlogin = sharedPreferences.edit();
		setContentView(R.layout.activity_main1);

		SessionData.getInstance().clearSessionData();
		if (isTablet(this)) {
			// It's a tablet
			// setContentView(R.layout.activity_hdmain);
			SessionData.isTablet = true;

			// setContentView(R.layout.activity_hdmain);
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		} else {
			// It's a phone
			// setContentView(R.layout.activity_main);
		}
	//	EBSRentalConstants.SOAP_BASE_ADDRESS = sharedPreferences.getString("serviceURL", "http://portal.ebs-next.com/RentalsInspection/");
		
		serviceLink = (EditText)findViewById(R.id.enter_url);
		server = sharedPreferencess.getBoolean("saveLogin", false);
		if (server == true) {
			
			serviceLink.setText(sharedPreferencess.getString("serviceURL", ""));
			
		}

		serviceLink.setText(EBSRentalConstants.SOAP_BASE_ADDRESS);
		EBSRentalConstants.SOAP_BASE_ADDRESS = serviceLink.getText().toString();
//		logo = (ImageView) findViewById(R.id.img_back);
//		logo.setVisibility(View.VISIBLE);
//		logo.setBackgroundResource(R.drawable.signature);
		remember = (ToggleButton) findViewById(R.id.chk_validate);
		btnLogin = (Button) findViewById(R.id.btn_login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		detector = new SimpleGestureFilter(this, this);
		popup = (LinearLayout) findViewById(R.id.popup_window);
		popup.bringToFront();

		btnSave = (Button) findViewById(R.id.buttonSave);
		btnCancel = (Button) findViewById(R.id.buttonCancel);
		popup.setVisibility(View.GONE);


		btnLogin.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);



		remember.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				username1 = username.getText().toString();
//				password1 = password.getText().toString();


/*
				if (remember.isChecked()) {
					sharedPreferlogin.putBoolean("saveLogin", true);
					sharedPreferlogin.putString("username", username1);
					sharedPreferlogin.putString("password", password1);
					sharedPreferlogin.commit();
				} else {
					sharedPreferlogin.clear();
					sharedPreferlogin.commit();
				}*/

			}

		});

		saveLogin = sharedPreferences.getBoolean("saveLogin", false);
		if (saveLogin == true) {
			username.setText(sharedPreferences.getString("username", ""));
			password.setText(sharedPreferences.getString("password", ""));

				//serviceLink.setText(sharedPreferencess.getString("serviceURL", ""));

			remember.setChecked(true);
		}

		server = sharedPreferencess.getBoolean("server", false);
		if(server == true) {
			serviceLink.setText(sharedPreferencess.getString("serviceURL", ""));
			//remember.setChecked(true);
		}

		SessionData.getInstance().setPreviousTabSelection(0);

	}

	private boolean isTablet(Context context) {
		
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;

		// return (getResources().getBoolean(R.bool.isTablet));
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		this.detector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onSwipe(int direction) {
		// String str = "";

		switch (direction) {
		case SimpleGestureFilter.SWIPE_DOWN:
			// str = "Swipe Down";
			popup.setVisibility(View.VISIBLE);
			break;

		case SimpleGestureFilter.SWIPE_UP:
			// str = "Swipe Up";
			popup.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onDoubleTap() {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		final Intent relaunch = new Intent(this, Exiter.class)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK // CLEAR_TASK requires
														// this
						| Intent.FLAG_ACTIVITY_CLEAR_TASK // finish everything
															// else in the task
						| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); // hide
																		
		startActivity(relaunch);
		finish();
	}

	@Override
	public void onClick(View v) {
		if (v == btnLogin){


			if (remember.isChecked()) {
				username1 = username.getText().toString();
				password1 = password.getText().toString();
				sharedPreferlogin.putBoolean("saveLogin", true);
				sharedPreferlogin.putString("username", username1);
				sharedPreferlogin.putString("password", password1);
				sharedPreferlogin.commit();
			} else {
				sharedPreferlogin.clear();
				sharedPreferlogin.commit();
			}

			if (username.getText().toString().trim().equals("")
					|| password.getText().toString().trim().equals("")) {

				AlertDialogBox.showAlertDialog(MainActivity.this,
						"Username and Password cannot be empty");
				return;
			} else {
				SessionData.getInstance().setLogin_username(username.getText().toString());
				SessionData.getInstance().setLogin_password(password.getText().toString());
				EBSRentalConstants.SOAP_BASE_ADDRESS = serviceLink.getText()
						.toString().trim();
				EBSRentalConstants.resetSOAPAddress();
//				SharedPreferences.Editor editor = sharedPreferences.edit();
//				editor.putString("serviceURL", EBSRentalConstants.SOAP_BASE_ADDRESS);
//				editor.commit();
				sharedPreferserver.putBoolean("server", true);
				sharedPreferserver.putString("serviceURL", EBSRentalConstants.SOAP_BASE_ADDRESS);
			//	sharedPreferlogin.putString("password", password1);
				sharedPreferserver.commit();
				new AsyncLoginTask().execute();
			}

		}
		if (v == btnSave) {
			
			EBSRentalConstants.SOAP_BASE_ADDRESS = serviceLink.getText()
					.toString().trim();
			EBSRentalConstants.resetSOAPAddress();
//			SharedPreferences.Editor editor = sharedPreferences.edit();
//			editor.putString("serviceURL", EBSRentalConstants.SOAP_BASE_ADDRESS);
//			editor.commit();
			sharedPreferserver.putBoolean("server", true);
			sharedPreferserver.putString("serviceURL", EBSRentalConstants.SOAP_BASE_ADDRESS);
		//	sharedPreferlogin.putString("password", password1);
			sharedPreferserver.commit();
			popup.setVisibility(View.GONE);
		}
		if (v == btnCancel) {
			serviceLink.setText(EBSRentalConstants.SOAP_BASE_ADDRESS);
			popup.setVisibility(View.GONE);
		}

	}

	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(MainActivity.this);
		};
		@Override
		protected Void doInBackground(Void... params) {
			try {
				objUser = WebServiceConsumer.getInstance()
						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
								SessionData.getInstance().getLogin_password());

			} catch (java.net.SocketTimeoutException e) {
				objUser = null;
				e.printStackTrace();
			} catch (IOException e) {
				objUser = null;
				e.printStackTrace();
			} catch (Exception e) {
				objUser = null;
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
				ProgressBar.dismiss();


				if (objUser != null && objUser.getUsername() != null) {

					ProgressBar.dismiss();
					SessionData.getInstance().setUser(objUser);
					SessionData.getInstance().setUsername(objUser.getUsername());

					SessionData.getInstance().setTemp_Username(objUser.getUsername());
					SessionData.getInstance().setTemp_password(SessionData.getInstance().getLogin_password());
					SessionData.getInstance().setLoginbranch(objUser.getBranch());

					// SessionData.getInstance().setUsertoken(objUser.getUserToken());
					// Log.d("thse usertoken is ",
					// SessionData.getInstance().getUsertoken());

					if (objUser.getUserId() == 0) {
						ProgressBar.dismiss();
						AlertDialogBox.showAlertDialog(MainActivity.this,
								SessionData.getInstance().getTemp_userToken());
					} else if (!(objUser.getVersion().contains("2.0"))) {
						ProgressBar.dismiss();
						AlertDialogBox.showAlertDialog(MainActivity.this,
								"This application requires Server Version 2.0. Please contact technical support.");
					} else {

						Intent inspection = new Intent(MainActivity.this, EbsMenu.class);
						startActivity(inspection);
					}

					// Intent inspection = new Intent(MainActivity.this,
					// ScannerActivity.class);
					// startActivity(inspection);
				} else {
					ProgressBar.dismiss();
					AlertDialogBox.showAlertDialog(MainActivity.this,
							"Login Failed! Check your service URL and credential.");
				}
		}
	}
//
//	private void closeKeyboard() {
//		View view = this.getCurrentFocus();
//		if (view != null) {
//			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//		}
//
//	}
}
