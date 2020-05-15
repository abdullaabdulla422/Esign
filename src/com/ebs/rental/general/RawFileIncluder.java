package com.ebs.rental.general;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.utils.SessionData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class RawFileIncluder extends AppCompatActivity {
	RelativeLayout rll;
	private Button take_picture;
	Button search;
	private Button btn_cancel;
	private Uri fileUri;

	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
			LIBRARY_IMAGE_REQUEST_CODE = 101;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int MEDIA_TYPE_VIDEO = 2;
	private int processOrderID;
	private static final String IMAGE_DIRECTORY_NAME = "rental_data/Camera";

	private static int RESULT_CODE = -1;
	CheckinListData checklistdataentry;
	private ArrayList<String> cheklistArray;
	Bundle extra;
	private ExifInterface exif;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.attachment_options);
		cheklistArray = new ArrayList<String>(SessionData.getInstance()
				.getCheckListData().values());

		if (getIntent().getExtras() != null) {
			processOrderID = getIntent().getExtras().getInt("processOrderID");
		}

		btn_cancel = (Button) findViewById(R.id.button3_cancel);
		take_picture = (Button) findViewById(R.id.button2_take_picture);

		take_picture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				captureImage();

				// getGps();

			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SessionData.getInstance().setImageDatas(null);
				finish();
			}
		});

		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(),
					"Sorry! Your device doesn't support camera",
					Toast.LENGTH_LONG).show();
			SessionData.getInstance().setImageData(null);
			finish();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		RESULT_CODE = requestCode;
		if (RESULT_CODE == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				addImageToSessionData();
				finish();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (RESULT_CODE == LIBRARY_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				try {
					fileUri = Uri.fromFile(new File(getPath(this,
							data.getData())));
					addImageToSessionData();
					finish();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(RawFileIncluder.this,
							"Exception in choosing file", Toast.LENGTH_LONG)
							.show();
				}

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"User cancelled image selection", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to Choose image", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
				Orientation.LEFT_RIGHT);
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private File getOutputMediaFile(int type) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {

			if (!mediaStorageDir.mkdirs()) {
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse("file://"
								+ Environment.getExternalStorageDirectory())));

				String root = Environment.getExternalStorageDirectory()
						.toString();
				File myDir = new File(root + "/saved_images");
				myDir.mkdirs();
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				String fname = "Image-" + n + ".jpg";
				File file = new File(myDir, fname);
				if (file.exists())
					file.delete();
				try {
					FileOutputStream out = new FileOutputStream(file);

					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	private boolean isDeviceSupportCamera() {
		return (getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA));
	}

	private void addImageToSessionData() {
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8; // already 8 was here
			Bitmap bitmap = BitmapFactory
					.decodeFile(fileUri.getPath(), options);
			// /////////////////
			exif = new ExifInterface(fileUri.getPath());
			
			String orientString = exif
					.getAttribute(ExifInterface.TAG_ORIENTATION);
			exif.saveAttributes();

			int orientation = orientString != null ? Integer
					.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
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
			// ///////////////
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

			SessionData.getInstance().setImageDatas(setExifMetaData(stream));

		} catch (NullPointerException e) {
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Toast.makeText(this, ioe.getMessage(), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, "General ex : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	private byte[] setExifMetaData(ByteArrayOutputStream stream)
			throws IOException {
		String newFile = fileUri.getPath() + "_cmp.jpg";
		FileOutputStream fout = new FileOutputStream(newFile);
		fout.write(stream.toByteArray());
		fout.flush();
		fout.close();

		ExifInterface newExif = new ExifInterface(newFile);

		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_FOCAL_LENGTH);
		// newExif = setExifAttribute(exif, newExif, ExifInterface.TAG_FLASH);

		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_ALTITUDE);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_ALTITUDE_REF);
		// // newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_LATITUDE);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_LATITUDE_REF);
		//
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_LONGITUDE);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_LONGITUDE_REF);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_PROCESSING_METHOD);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_TIMESTAMP);

		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_GPS_DATESTAMP);
		// newExif = setExifAttribute(exif, newExif,
		// ExifInterface.TAG_EXPOSURE_TIME);
		newExif = setExifAttribute(exif, newExif, ExifInterface.TAG_DATETIME);

		// newExif = setExifAttribute(ExifInterface.TAG_GPS_LATITUDE,latitude);
		newExif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
				exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
		newExif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
				exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));

		// if(exif.getAttribute(ExifInterface.TAG_DATETIME) != null ) {
		// newExif.setAttribute("DateTaken",
		// exif.getAttribute(ExifInterface.TAG_DATETIME));
		// newExif.setAttribute("Date Taken",
		// exif.getAttribute(ExifInterface.TAG_DATETIME));
		// newExif.setAttribute("Date taken",
		// exif.getAttribute(ExifInterface.TAG_DATETIME));
		// }

		newExif.saveAttributes();
		// newExif = new ExifInterface(newFile);
		// Logger.log("\nDateTaken:After writing: "+newExif.getAttribute(ExifInterface.TAG_DATETIME));
		FileInputStream inStream = new FileInputStream(newFile);
		byte[] buffer = new byte[inStream.available()];
		inStream.read(buffer);
		inStream.close();
		return buffer;
	}

	private ExifInterface setExifAttribute(ExifInterface exif,
			ExifInterface newExif, String attr) {

		if (exif.getAttribute(attr) != null) {
			newExif.setAttribute(attr, exif.getAttribute(attr));
		}

		return newExif;
	}

	/**
	 * Choose files from directory
	 */

	private static String getPath(Context context, Uri uri)
			throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

}
