package com.ebs.rental.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

@SuppressWarnings("ALL")
class ImageAdapter extends BaseAdapter {
	private final Context mContext;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private ArrayList<String> list;

	// Keep all Images in array

	// Constructor
	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
		return imageView;
	}

}
