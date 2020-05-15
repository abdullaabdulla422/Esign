package com.ebs.rental.general;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.adapters.ListVerificationAdapter;
import com.ebs.rental.objects.PartsPath;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.RootActivity;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

@SuppressWarnings("ALL")
public class VerificationActivity extends RootActivity implements
		OnClickListener {

	@SuppressLint("StaticFieldLeak")
	private static TextView title;
	private ImageView back;
	private ImageView home;
	private static final String TAG_PLACEHOLDER = "placholder";

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new PlaceholderFragment(),
							TAG_PLACEHOLDER).commit();
		}
		title = (TextView) findViewById(R.id.title_text);
		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);
		home = (ImageView) findViewById(R.id.img_next);
		home.setOnClickListener(this);
		back.setVisibility(View.VISIBLE);
		home.setVisibility(View.VISIBLE);
		title.setText("Verification");
	}

	@Override
	public void onClick(View v) {

		if (v == back)
			onBackPressed();

		if (v == home) {
			finish();
			Intent intent1 = new Intent(VerificationActivity.this,
					CustomerListActivity.class);
			startActivity(intent1);
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		TextView customerName, rentalId, equipmentId;
		View rootView;
		ListVerificationAdapter verificationAdapter;
		ListView verificationList;
		Button submit;
		PartsPath imagePath;
		Bitmap image;
		String rentalImages;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (isTablet(getActivity())) {
				// It's a tablet
				SessionData.isTablet = true;
				rootView = inflater.inflate(R.layout.fragment_verification_tab,
						container, false);
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
			}

			else {
				// It's a phone
				rootView = inflater.inflate(R.layout.fragment_verification,
						container, false);
			}

			customerName = (TextView) rootView
					.findViewById(R.id.txt_customerName);
			rentalId = (TextView) rootView.findViewById(R.id.rentalId);
			equipmentId = (TextView) rootView.findViewById(R.id.equipmentId);
			verificationList = (ListView) rootView
					.findViewById(R.id.verificationlist);
			submit = (Button) rootView.findViewById(R.id.submitverify);

			Log.d("Equipment", "" + SessionData.getInstance().getEquipmentId());
			Log.d("Rental Id", "" + SessionData.getInstance().getRentalId());
			Log.d("Customer Name", "" + SessionData.getInstance().getCustName());

			equipmentId.setText(SessionData.getInstance().getEquipmentId());
			customerName.setText(SessionData.getInstance().getCustName());
			rentalId.setText(String.valueOf(SessionData.getInstance()
					.getRentalId()));

			if (SessionData.getInstance().getListVerification().size() > 0) {
				verificationAdapter = new ListVerificationAdapter(
						getActivity(), SessionData.getInstance()
								.getListVerification());
				verificationList.setAdapter(verificationAdapter);
			}
			submit.setOnClickListener(this);
			return rootView;
		}

		private boolean isTablet(Context context) {
			return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;

			// return (getResources().getBoolean(R.bool.isTablet));
		}

		@Override
		public void onClick(View v) {
			if (v == submit) {
				Logger.log("Visual Inspection Size"
						+ SessionData.getInstance().getVisualInspectionId().size());
				new AsyncVerificationTask().execute();
			}
		}

		public class AsyncVerificationTask extends AsyncTask<Void, Void, Void> {
			@Override
			protected void onPreExecute() {
				ProgressBar.showCommonProgressDialog(getActivity());
			}

			@SuppressLint("LongLogTag")
			@Override
			protected Void doInBackground(Void... params) {
				for (int i = 0; i < SessionData.getInstance()
						.getVisualInspectionId().size(); i++) {

					Logger.log("Inside For i" + i);
					imagePath = SessionData
							.getInstance()
							.getListVerification()
							.get(new ArrayList<String>(SessionData
									.getInstance().getVisualInspectionId()
									.keySet()).get(i));
					Logger.log("Imagepath size"
							+ imagePath.getImagePath().size());
					for (int j = 0; j < imagePath.getImagePath().size(); j++) {

						Logger.log("Inside For j" + j);
						try {

							Logger.log("Inside Else" + j);
							long offset = 0;

							Bitmap image = VisualInspection
									.bitmapConverter(imagePath.getImagePath()
											.get(j));
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							image.compress(Bitmap.CompressFormat.JPEG, 100,
									baos);
							byte[] b = baos.toByteArray();
							String imageEncoded = Base64.encodeToString(b,
									Base64.DEFAULT);

							Logger.log("Visual Inspection ID"
									+ (new ArrayList<String>(SessionData
											.getInstance()
											.getVisualInspectionId().values())
											.get(i)));
							Log.d("Visual Inspection ID",
									""
											+ (new ArrayList<String>(
													SessionData
															.getInstance()
															.getVisualInspectionId()
															.values()).get(i)));
							Logger.log("Offset" + offset);
							Log.d("UserToken", ""
									+ SessionData.getInstance().getUsertoken());
							Logger.log("User Token"
									+ SessionData.getInstance().getUsertoken());
							Log.d("Offset", "" + offset);

							String fullPath = imagePath.getImagePath().get(j);
							int index = fullPath.lastIndexOf("/");
							String fileName = fullPath.substring(index + 1);

							Log.d("File Name", "" + fileName);
							Logger.log("File Name" + fileName);
							Log.d("Verification Hash Map Size", ""
									+ SessionData.getInstance()
											.getListVerification().size());
							Logger.log("Verification Hash Map Size"
									+ SessionData.getInstance()
											.getListVerification().size());
							Log.d("Get Key Hash Map Size", ""
									+ SessionData.getInstance().getGetKey()
											.size());
							Logger.log("Get Key Hash Map Size"
									+ SessionData.getInstance().getGetKey()
											.size());
							Log.d("Visual Inspection Id Hash Map Size", ""
									+ SessionData.getInstance()
											.getVisualInspectionId().size());
							Logger.log("Visual Inspection Id Hash Map Size"
									+ SessionData.getInstance()
											.getVisualInspectionId().size());

							rentalImages = WebServiceConsumer
									.getInstance()
									.setRentalImages(
											Integer.parseInt(new ArrayList<String>(
													SessionData
															.getInstance()
															.getVisualInspectionId()
															.values()).get(i)),
											fileName,
											"",
											0,
											imageEncoded,
											offset,
											SessionData.getInstance()
													.getUsertoken());
							Log.d("Images String", "" + rentalImages);
							Logger.log("Images String" + rentalImages);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if (rentalImages != null
						&& SessionData.getInstance().getVisualInspectionId()
								.size() > 0) {
					ProgressBar.dismiss();
					AlertDialogBox.showAlertDialog(getActivity(),
							"Images Uploaded Successfully");
				} else if (SessionData.getInstance().getVisualInspectionId()
						.size() <= 0) {
					ProgressBar.dismiss();
					AlertDialogBox.showAlertDialog(getActivity(),
							"No Parts have been Inspected");
				} else {
					ProgressBar.dismiss();
					AlertDialogBox.showAlertDialog(getActivity(),
							"Check Webservice");
				}
			}
		}

		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		// long arg3) {
		// getFragmentManager().beginTransaction().replace(R.id.container,new
		// ImageViewSlider()).commit();
		// Bundle args = new Bundle();
		// args.putInt("Position", position);
		// ImageViewSlider objectPosition = new ImageViewSlider();
		// objectPosition.setArguments(args);
		//
		// }
	}
}
