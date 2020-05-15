package com.ebs.rental.visual.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.InspectionActivity;
import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.general.VisualInspection;
import com.ebs.rental.general.VisualInspection.Inspection;
import com.ebs.rental.objects.EquipmentParts;
import com.ebs.rental.objects.PartsPath;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@SuppressWarnings("ALL")
public class PartsCapture extends Fragment implements OnKeyListener, OnClickListener {
	
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int LIBRARY_IMAGE_REQUEST_CODE = 101;
	private static Uri fileUri;
	public static boolean fromtablet = false;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private File mediaFile;
	private Bitmap[] bitmap;
	
	Context context;
	private int partsFaceNo;
	private PartsPath path;
	private ArrayList<EquipmentParts> partsName;
	LinearLayout equipmentList;
	LinearLayout equipmentAdapter;
	
	View view;
	private TextView parts;
	private TextView partHeader;
	TextView selectImage;
	private TextView takePhoto;
	private TextView btnCancel;
	Button btnCapture;
	private Button selectedCamera;
	private Spinner partsCheck;
	private Dialog dialog;
	
	private EquipmentParts equipmentPart;
	private ListView partsListView;
	private ArrayList<String> files;
	private ArrayAdapter<EquipmentParts> listAdapter;
	private View rowView;
	private int temp = -1;
	private int temp1 = -1;
	
	private int partId;
	private String partName;
	private final String TAG = "PartsCapture";
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		setRetainInstance(true);
		View inspect = inflater.inflate(R.layout.fragment_view_capture, container, false);
		partHeader = (TextView) inspect.findViewById(R.id.partlist);
		if (isTablet(this)) {
			// It's a tablet
			SessionData.isTablet = true;
			partHeader.setVisibility(View.VISIBLE);
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		} else {
			// It's a phone
			partHeader.setVisibility(View.GONE);
		}
		files = new ArrayList<>();
		partsListView = (ListView) inspect.findViewById(R.id.equipmentlist);
		partsName = new ArrayList<>();
		partsFaceNo = getArguments().getInt("PartsFaceNo");
		
		Log.d("EquipmentsParts face No", " " + partsFaceNo);
		Log.d("User Token", " " + SessionData.getInstance().getUsertoken());
		
		ProgressBar.dismiss();
		new AsyncEquimentsParts().execute();
		inspect.setOnKeyListener(this);
		inspect.setFocusableInTouchMode(true);
		inspect.requestFocus();
		return inspect;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.v(TAG, "In frag's on create");
		this.setRetainInstance(true);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
	}
	
	private boolean isTablet(PartsCapture partsCapture) {

		return (partsCapture.getResources().getConfiguration().screenLayout & 
				Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;

	}

	private class AsyncEquimentsParts extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(getActivity());
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				partsName = WebServiceConsumer.getInstance().getEquipmentParts(partsFaceNo, SessionData.getInstance().getUsertoken());

			} catch (java.net.SocketTimeoutException e) {
				partsName = null;
			} catch (Exception e) {
				partsName = null;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (partsName != null) {
				ProgressBar.dismiss();

				if(partsName.get(0).getMessage().equals("")) {

					listAdapter = new ArrayAdapter<EquipmentParts>(getActivity(), R.layout.fragment_list_viewcapture, partsName) {
						@Override
						public View getView(final int listposition, View convertView, ViewGroup parent) {

							equipmentPart = (EquipmentParts) partsName.get(listposition);
							Log.d("PartsName", equipmentPart.getPartName());
							inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							rowView = inflater.inflate(R.layout.fragment_list_viewcapture, null, true);

							partsCheck = (Spinner) rowView.findViewById(R.id.parts_spinner);
							ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.partscheck_arrays,
									android.R.layout.simple_spinner_item);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							partsCheck.setAdapter(adapter);

							parts = (TextView) rowView.findViewById(R.id.txt_partsname);
							parts.setText(equipmentPart.getPartName());
							partsCheck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
									selectedCamera = (Button) partsListView.getChildAt(listposition).findViewById(R.id.img_btn_capture);
									if (position == 1) {
										Log.d("Yes is", "Clicked");
										selectedCamera.setEnabled(true);
										selectedCamera.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												temp = listposition;
												partName = (String) partsName.get(listposition).getPartName();
												if (SessionData.getInstance().getListVerification().get(partName) == null) {
													path = new PartsPath();
												} else {
													path = SessionData.getInstance().getListVerification().get(partName);
												}
												Logger.log("partName is " + partName);
												Log.d("partName is ", "" + partName);
												partId = (int) partsName.get(listposition).getPartID();
												captureDialog();
											}
										});

									} else if (position == 0) {
										Log.d("No is", "Clicked");
										selectedCamera.setEnabled(false);
									}
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {

								}
							});

							Logger.log("Equipment part_order_detail_row id" + equipmentPart.getPartID());
							Log.d("Equipment part_order_detail_row id", "" + equipmentPart.getPartID());
							return rowView;
						}

					};
					listAdapter.notifyDataSetChanged();
					partsListView.setAdapter(listAdapter);
				}
				else{
					dialog = new Dialog(getActivity());
					dialog.setCanceledOnTouchOutside(true);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.message);


					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
					ImageView closeImg=dialog.findViewById(R.id.close_img);

					Message.setText(partsName.get(0).getMessage());

					closeImg.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					yes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							Intent inspection = new Intent(getActivity(),
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}

			}

			// for (int i = 0; i < partsName.size(); i++) {
			// bitmap = new Bitmap[partsName.size()];
			// equipmentPart = (EquipmentParts) partsName.get(i);
			// view = layoutInflater.inflate(
			// R.layout.fragment_list_viewcapture, null, false);
			// parts = (TextView) view.findViewById(R.id.txt_partsname);
			// image = (ImageView) view.findViewById(R.id.img_partsname);
			// partsCheck = (Spinner) view
			// .findViewById(R.id.parts_spinner);
			// ArrayAdapter<CharSequence> adapter = ArrayAdapter
			// .createFromResource(getActivity(),
			// R.array.partscheck_arrays,
			// android.R.layout.simple_spinner_item);
			// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// partsCheck.setAdapter(adapter);
			// image.setId(i);
			// parts.setText(equipmentPart.getPartName());
			// equipmentList.addView(view);
			// equipmentList.getChildAt(i);
			// temp = i;
			// partsCheck.setOnItemSelectedListener(new OnItemSelectedListener()
			// {
			//
			// @Override
			// public void onItemSelected(AdapterView<?> adapter,
			// View v, int position, long id) {
			// String item = adapter.getItemAtPosition(position).toString();
			// if(partsCheck.getSelectedItem().toString().equalsIgnoreCase("No"))
			// image.setVisibility(View.VISIBLE);
			// else
			// if(partsCheck.getSelectedItem().toString().equalsIgnoreCase("Yes"))
			// image.setVisibility(View.GONE);
			// }
			//
			// @Override
			// public void onNothingSelected(AdapterView<?> arg0) {
			//
			// }
			// });
			// image.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// dialog = new Dialog(getActivity());
			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// dialog.setContentView(R.layout.select_image_dialog);
			// selectImage = (TextView) dialog
			// .findViewById(R.id.txt_select_image);
			// takePhoto = (TextView) dialog
			// .findViewById(R.id.txt_take_photo);
			// selectImage
			// .setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// dialog.dismiss();
			// showFileChooser();
			//
			// }
			// });
			// takePhoto
			// .setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// dialog.dismiss();
			// captureImage();
			// }
			// });
			// dialog.show();
			// }
			// });
			// }

			else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(getActivity(),"Check WEbservice");
			}
		}

	}

	private void captureDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.select_image_dialog);
		takePhoto = (TextView) dialog.findViewById(R.id.txt_take_photo);
		
		btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
		takePhoto.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		dialog.show();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isTablet(this)) {
				getActivity().finish();
				Intent intent = new Intent(getActivity(),InspectionActivity.class);
				startActivity(intent);
			} else {
				getFragmentManager().beginTransaction().replace(R.id.container, new Inspection()).commit();
			}
			return true;
		} else {
			return false;
		}
	}

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, 90);
		Log.d("CaptureImage", "Function");
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private File getOutputMediaFile(int type) {

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

		if (type == MEDIA_TYPE_IMAGE) {
			if (InspectionActivity.isDelivery) {
				mediaFile = new File(VisualInspection.mediaStorageDir.getPath() + File.separator + "D_" + SessionData.getInstance().getCustNum() 
						+ "_" + SessionData.getInstance().getOrderNo() + "_" + SessionData.getInstance().getEquipmentId() + "_" + timeStamp 
						+ ".jpg");
			} else {
				mediaFile = new File(VisualInspection.mediaStorageDir.getPath() + File.separator + "R_" + SessionData.getInstance().getCustNum() 
						+ "_" + SessionData.getInstance().getOrderNo() + "_" + SessionData.getInstance().getEquipmentId() + "_" + timeStamp 
						+ ".jpg");
			}

		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("On activity result called ", "Image");
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				path.getImagePath().add(fileUri.getPath());
				Logger.log("Files size" + path.getImagePath().size());
				Log.d("Files size", "" + path.getImagePath().size());
				SessionData.getInstance().getListVerification().put(partName, path);
				HashMap<String, Object> partsList;
				partsList = new HashMap<>();
				temp1 = temp;
				partsList.put(partName, path);
				Logger.log("Files size" + files.size());
				Log.d("Files size", "" + files.size());
				Logger.log("HashMap new size" + partsList.size());
				Log.d("HashMap new size", "" + partsList.size());
				Logger.log("file size" + fileUri.getPath());
				Log.d("file size", " " + fileUri.getPath());
			}		
		} else if (requestCode == LIBRARY_IMAGE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					fileUri = Uri.fromFile(new File(getPath(getActivity(), data.getData())));
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Exception in choosing file", Toast.LENGTH_LONG).show();
				}

			} else if (resultCode == Activity.RESULT_CANCELED) {

				showFileChooser();
				Toast.makeText(getActivity(), "User cancelled image selection", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "Sorry! Failed to Choose image", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void showFileChooser() {
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// GET_CONTENT
		intent.setType("image/*"); // */*
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), LIBRARY_IMAGE_REQUEST_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
		}
	}

	private static String getPath(Context context, Uri uri) throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection, null, null, null);
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

	public Bitmap bitmapConverter(int i) {
		
		Logger.log("On activity result called ");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8; // already 8 was here
		bitmap[i] = BitmapFactory.decodeFile(fileUri.getPath(), options);
		
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(fileUri.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
		int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
		
		int rotationAngle = 0;
		if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
			rotationAngle = 90;
		if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
			rotationAngle = 180;
		if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
			rotationAngle = 270;

		Matrix matrix = new Matrix();
		matrix.setRotate(rotationAngle, (float) bitmap[i].getWidth(), (float) bitmap[i].getHeight() / 2);
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap[i], 0, 0, bitmap[i].getWidth(), bitmap[i].getHeight(), matrix, true);
		bitmap[i] = rotatedBitmap;
		Log.d("On activity result called ", "Image");
		return bitmap[i];
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ProgressBar.dismiss();

	}

	@Override
	public void onClick(View v) {
		if (v == takePhoto) {
			dialog.dismiss();
			captureImage();
		} else if (v == btnCancel) {
			dialog.dismiss();
		}
	}

}
