package com.ebs.rental.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.general.R;
import com.ebs.rental.general.VisualInspection;
import com.ebs.rental.objects.PartsPath;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SuppressWarnings("ALL")
public class ListVerificationAdapter extends BaseAdapter {

	private final Activity context;
	private static LayoutInflater inflater = null;
	private final LinkedHashMap<String, PartsPath> verification;
	ImageView imageName;
	private final String[] mKeys;
	private String[] mValues;
	private final PartsPath[] imagePath;
	private String visualInspectionId;
	private boolean status;
	Dialog dialog;
	ArrayAdapter<?> viewAdapter;
	ListView imageList;
	View galleryView;

	public ListVerificationAdapter(Activity context,
			LinkedHashMap<String, PartsPath> verification) {

		Logger.log("HashmapSize = "
				+ SessionData.getInstance().getListVerification().size());
		this.context = context;
		this.verification = verification;
		mKeys = this.verification.keySet().toArray(
				new String[verification.size()]);
		imagePath = this.verification.values().toArray(
				new PartsPath[verification.size()]);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (verification == null || verification.size() <= 0)
			return 1;
		return verification.size();
	}

	@Override
	public Object getItem(int position) {
		return verification.get(mKeys[position]);
	}

	public Object getValue(int position) {
		return verification.get(mValues[position]);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("SetTextI18n")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		@SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.fragment_list_verification,
				parent, false);
		TextView partsVerification = (TextView) rowView
				.findViewById(R.id.partsverification);
		TextView partsCheck = (TextView) rowView.findViewById(R.id.partscheck);
		ImageView takenImage = (ImageView) rowView.findViewById(R.id.partsimage);

		partsVerification.setText(mKeys[position]);

		Log.d("Mkeys", "" + mKeys[position]);
		Logger.log("Mkeys" + mKeys[position]);
		Log.d("Position", "" + position);
		Logger.log("Position" + position);
		Log.d("Status in get view", "" + status);
		Logger.log("Status in get view" + status);

		String partName = mKeys[position];
		int value = new ArrayList<Integer>(SessionData.getInstance()
				.getGetKey().values()).get(position);

		Log.d("Part Id", "" + value);

		if (imagePath[position] == null) {
			partsCheck.setText("No");
			status = true;
		} else {
			status = false;
			Bitmap imageBitmap = VisualInspection
					.bitmapConverter(imagePath[position].getImagePath().get(0));
			new AsyncListVerification(value, partName).execute();
			takenImage.setVisibility(View.VISIBLE);
			partsCheck.setVisibility(View.GONE);
			takenImage.setImageBitmap(imageBitmap);
		}

		takenImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Position", "" + position);
				Logger.log("Position" + position);
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(imagePath[position]
						.getImagePath().get(0))), "image/*");
				context.startActivity(intent);
			}
		});

		return rowView;
	}

	public class AsyncListVerification extends AsyncTask<Void, Void, Void> {
		private final int tempValue;
		private final String tempPartName;

		public AsyncListVerification(int value, String partName) {
			tempValue = value;
			tempPartName = partName;
		}

		@Override
		protected Void doInBackground(Void... params) {

			Log.d("Part Id Value", "" + tempValue);
			Logger.log("Part Id Value" + tempValue);
			Log.d("Part Name in Adapter", "" + tempPartName);
			Logger.log("Part Name in Adapter" + tempPartName);
			Logger.log("Status" + status);

//			visualInspectionId = WebServiceConsumer.getInstance()
//					.setRentalVisualInspection(
//							Integer.parseInt(SessionData.getInstance()
//									.getInspectionId()), tempValue, status, 0,
//							SessionData.getInstance().getUsertoken());
			SessionData.getInstance().getVisualInspectionId()
					.put(tempPartName, visualInspectionId);

			Log.d("Visual Inpection Id", "" + visualInspectionId);
			Logger.log("Visual Inpection Id" + visualInspectionId);

			return null;
		}

	}

	// public void ViewDialog(final int clickedPosition,
	// final PartsPath imagePath[]) {
	// dialog = new Dialog(context);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.setContentView(R.layout.imageview_list);
	// imageList = (ListView) dialog.findViewById(R.id.imagename_list);
	// viewAdapter = new ArrayAdapter<String>(context,
	// R.layout.imageview_adapter, imagePath[clickedPosition].getImagePath()) {
	// @Override
	// public View getView(int listposition, View convertView,
	// ViewGroup parent) {
	// Logger.log("Clicked Position" + clickedPosition);
	// Log.d("Clicked Position", "" + clickedPosition);
	// View galleryView = inflater.inflate(R.layout.imageview_adapter,
	// null, true);
	// imageName = (ImageView) galleryView.findViewById(R.id.imagename);
	// Bitmap imageBitmap = VisualInspection
	// .bitmapConverter(imagePath[clickedPosition].getImagePath().get(listposition));
	// imageName.setImageBitmap(imageBitmap);
	// return galleryView;
	// }
	// };
	// imageList.setAdapter(viewAdapter);
	// dialog.show();
	// }

}
