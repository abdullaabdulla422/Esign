package com.ebs.rental.general;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebs.rental.objects.EquipmentParts;
import com.ebs.rental.objects.PartsPath;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.RootActivity;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.visual.fragments.PartsCapture;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

/**
 * 
 * @author Sabareesh {@link Class}
 */
@SuppressWarnings("ALL")
public class VisualInspection extends RootActivity implements OnClickListener {

	/**
	 * {@link Field}
	 */

	public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
			LIBRARY_IMAGE_REQUEST_CODE = 101;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int FRONT = 1;
	private static final int LEFT_FRONT = 2;
	private static final int LEFT = 3;
	private static final int LEFT_REAR = 4;
	private static final int REAR = 5;
	private static final int RIGHT_REAR = 6;
	private static final int RIGHT = 7;
	private static final int RIGHT_FRONT = 8;
	private static final int CENTER = 9;
	public static boolean fromtablet = false;

	private static final String IMAGE_DIRECTORY_NAME = "RentalInspection/Camera";
	public static File mediaStorageDir;
	private ArrayList<EquipmentParts> allPartsName;
	private ArrayList<EquipmentParts> partsName;
	Button leftView, btnNext, btnBack;
	ImageView captureImage;
	private ImageView back;
	private ImageView home;
	Dialog insertImageDialog;
	@SuppressLint("StaticFieldLeak")
	private static TextView title;
	private Context context;
	private static Bundle partsId;
	@SuppressLint("StaticFieldLeak")
	private static PartsCapture inspectionView;
	private String inspectionId;
	public static Uri fileUri;
	Bitmap bitmap;
	File mediaFile;
	private PartsPath path;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		path = new PartsPath();
		if (isTablet(this)) {
			// It's a tablet
			SessionData.isTablet = true;
			setContentView(R.layout.activity_visual_inspectiontab);

			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		} else {
			// It's a phone
			setContentView(R.layout.activity_visual_inspection);
		}

		createFile();
		partsId = new Bundle();

		title = (TextView) findViewById(R.id.title_text);
		title.setText("Visual Inspection");
		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);
		home = (ImageView) findViewById(R.id.img_next);
		allPartsName = new ArrayList<>();
		home.setOnClickListener(this);

		context = this;
		if (savedInstanceState == null) {
			if (isTablet(this)) {

				getFragmentManager().beginTransaction()
						.add(R.id.container1, new Inspection()).commit();
			} else
				getFragmentManager().beginTransaction()
						.add(R.id.container, new Inspection()).commit();

			back.setVisibility(View.VISIBLE);
			home.setVisibility(View.VISIBLE);
		}
		new AsyncVisualTask().execute();

		// btnBack = (Button)findViewById(R.id.btn_visual_back);
		// btnNext = (Button)findViewById(R.id.btn_visual_next);
		// btnBack.setOnClickListener(this);
		// btnBack.setOnClickListener(this);

	}

	private boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
		// return (getResources().getBoolean(R.bool.isTablet));

	}

	private class AsyncVisualTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(VisualInspection.this);
		}

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 1; i <= 9; i++) {
				try {
					partsName = WebServiceConsumer.getInstance()
							.getEquipmentParts(i,
									SessionData.getInstance().getUsertoken());
					Logger.log("For All Parts Name Size" + i + partsName.size());
					Log.d("For All Parts Name Size", "" + i + partsName.size());
					if (partsName != null)
						allPartsName.addAll(partsName);

				} catch (java.net.SocketTimeoutException e) {
					partsName = null;
				} catch (Exception e) {
					partsName = null;
					e.printStackTrace();
				}
			}
			try {
//				
				Logger.log("Webservice return" + inspectionId);
				Log.d("Webservice return", "" + inspectionId);
			} catch (Exception e) {

			}
			ArrayList<String> list = new ArrayList<>();
			list.add("No");
			path.setImagePath(list);
			for (int j = 0; j < allPartsName.size(); j++) {

				SessionData.getInstance().getListVerification()
						.put(allPartsName.get(j).getPartName(), null);
				SessionData
						.getInstance()
						.getGetKey()
						.put(allPartsName.get(j).getPartName(),
								allPartsName.get(j).getPartID());

				Log.d("Part Id", "" + allPartsName.get(j).getPartID());
				Logger.log("the parts are" + allPartsName.get(j).getPartName());
				Log.d("the parts are", "" + allPartsName.get(j).getPartName());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (allPartsName != null && inspectionId != null) {
//				SessionData.getInstance().setInspectionId(inspectionId);
//				ProgressBar.dismiss();
			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(VisualInspection.this,
						"Check WEbservice");
			}
		}
	}

	private File createFile() {
		mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		return mediaStorageDir;
	}

	public static Bitmap bitmapConverter(String filePath) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8; // already 8 was here
		File fileUri = new File(filePath);
		Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(fileUri.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
		int orientation = orientString != null ? Integer.parseInt(orientString)
				: ExifInterface.ORIENTATION_NORMAL;
		int rotationAngle = 0;

		if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
			rotationAngle = 90;
		if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
			rotationAngle = 180;
		if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
			rotationAngle = 270;

		Matrix matrix = new Matrix();
		matrix.setRotate(rotationAngle, (float) bitmap.getWidth(),
				(float) bitmap.getHeight() / 2);
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap = rotatedBitmap;
		return bitmap;

	}

	public static class Inspection extends Fragment implements OnClickListener {
		ImageView inspectFront, inspectLeft, inspectRight, inspectRear,
				inspectLeftFront, inspectLeftRear, inspectRightFront,
				inspectRightRear, inspectCenter;
		Button submit;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			setRetainInstance(true);
			View inspect = inflater.inflate(
					R.layout.fragment_visual_inspection, container, false);
			inspectFront = (ImageView) inspect.findViewById(R.id.img_front);
			inspectLeft = (ImageView) inspect.findViewById(R.id.img_left);
			inspectRight = (ImageView) inspect.findViewById(R.id.img_right);
			inspectRear = (ImageView) inspect.findViewById(R.id.img_rear);

			inspectCenter = (ImageView) inspect.findViewById(R.id.img_center);
			submit = (Button) inspect.findViewById(R.id.btn_submit);
			inspectLeftFront = (ImageView) inspect
					.findViewById(R.id.img_left_front);
			inspectLeftRear = (ImageView) inspect
					.findViewById(R.id.img_left_rear);
			inspectRightFront = (ImageView) inspect
					.findViewById(R.id.img_right_front);
			inspectRightRear = (ImageView) inspect
					.findViewById(R.id.img_right_rear);

			submit.setOnClickListener(this);
			inspectFront.setOnClickListener(this);
			inspectLeft.setOnClickListener(this);
			inspectRight.setOnClickListener(this);
			inspectRear.setOnClickListener(this);
			inspectLeftFront.setOnClickListener(this);

			inspectLeftRear.setOnClickListener(this);
			inspectRightFront.setOnClickListener(this);
			inspectRightRear.setOnClickListener(this);
			inspectCenter.setOnClickListener(this);
			return inspect;
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void onClick(View v) {
			inspectionView = new PartsCapture();
			if (v == inspectFront) {
				partsId.putInt("PartsFaceNo", FRONT);
				inspectionView.setArguments(partsId);
				title.setText("Front Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectLeftFront) {
				partsId.putInt("PartsFaceNo", LEFT_FRONT);
				inspectionView.setArguments(partsId);
				title.setText("Left Front Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectLeft) {
				partsId.putInt("PartsFaceNo", LEFT);
				inspectionView.setArguments(partsId);
				title.setText("Left Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectLeftRear) {
				partsId.putInt("PartsFaceNo", LEFT_REAR);
				inspectionView.setArguments(partsId);
				title.setText("Left Rear Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectRear) {
				partsId.putInt("PartsFaceNo", REAR);
				inspectionView.setArguments(partsId);
				title.setText("Rear Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectRightRear) {
				partsId.putInt("PartsFaceNo", RIGHT_REAR);
				inspectionView.setArguments(partsId);
				title.setText("Right Rear Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectRight) {
				partsId.putInt("PartsFaceNo", RIGHT);
				inspectionView.setArguments(partsId);
				title.setText("Right Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectRightFront) {
				partsId.putInt("PartsFaceNo", RIGHT_FRONT);
				inspectionView.setArguments(partsId);
				title.setText("Right Front Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == inspectCenter) {
				partsId.putInt("PartsFaceNo", CENTER);
				inspectionView.setArguments(partsId);
				title.setText("Center Inspect");
				getFragmentManager().beginTransaction()
						.replace(R.id.container, inspectionView).commit();
			}
			if (v == submit) {
				Intent verification = new Intent(getActivity(),
						VerificationActivity.class);
				startActivity(verification);
			}
		}
	}

	/*
	 * private boolean isDeviceSupportCamera() { return
	 * (this.getPackageManager()
	 * .hasSystemFeature(PackageManager.FEATURE_CAMERA)); }
	 */

	@Override
	public void onBackPressed() {
		finish();
		Intent intent = new Intent(VisualInspection.this,
				InspectionActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			onBackPressed();
		}
		if (v == home) {
			finish();
			Intent intent1 = new Intent(VisualInspection.this,
					CustomerListActivity.class);
			startActivity(intent1);
		}

	}

}