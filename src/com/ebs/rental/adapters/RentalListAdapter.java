package com.ebs.rental.adapters;

import java.util.ArrayList;

import com.ebs.rental.general.R;
import com.ebs.rental.objects.RentalsList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class RentalListAdapter extends BaseAdapter {

	private final Activity context;
	private static LayoutInflater inflater;
	private final ArrayList<RentalsList> rentalList;
	private TextView shipToName;
	private TextView rentDate;
	private TextView equipmentId;

	public RentalListAdapter(Activity context, ArrayList<RentalsList> rentalList) {

		this.context = context;
		this.rentalList = rentalList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (rentalList == null || rentalList.size() < 0) {
			return 1;
		}
		return rentalList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		@SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.rental_list_adapter, parent,false);
		shipToName = (TextView) rowView.findViewById(R.id.shipto_name);
		equipmentId = (TextView) rowView.findViewById(R.id.rental_equipmentId);
		rentDate = (TextView) rowView.findViewById(R.id.rentdate);
		
		Log.d("RentalList", "" + rentalList.get(position).getOrderNo());
		Log.d("RentalList", "" + rentalList.get(position).getEquipmentId());
		Log.d("Rental List", "" + rentalList.get(position).getRentId());
		
		equipmentId.setText(rentalList.get(position).getEquipmentId());
		shipToName.setText(rentalList.get(position).getShipToName());
		rentDate.setText(rentalList.get(position).getRentalDate());
		equipmentId.setText(rentalList.get(position).getEquipmentId());

		return rowView;
	}
}
