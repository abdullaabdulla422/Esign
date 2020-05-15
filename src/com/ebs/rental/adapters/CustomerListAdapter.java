package com.ebs.rental.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebs.rental.general.R;
import com.ebs.rental.objects.CustomerList;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CustomerListAdapter extends BaseAdapter {

	private final ArrayList<CustomerList> customerList;
	private static LayoutInflater inflater = null;

	public CustomerListAdapter(Activity context,
			ArrayList<CustomerList> customerList) {
		Activity context1 = context;
		this.customerList = customerList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	@Override
	public int getCount() {
		if ((customerList == null) || (customerList.size() < 0)) {
			return 1;
		}
		return customerList.size();

	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint("LongLogTag")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CustomerList rentalsList = (CustomerList) customerList.get(position);
		@SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.activity_customer_list_view,
				parent, false);
		TextView customerName = (TextView) rowView.findViewById(R.id.customername);
		TextView customerNo = (TextView) rowView.findViewById(R.id.customernum);

		Log.d("CusomerListAdapter", "" + rentalsList.getCustomerName());
		Log.d("CusomerListAdapterNumber", "" + rentalsList.getCustNum());

		customerName.setText(rentalsList.getCustomerName());
		customerNo.setText(rentalsList.getCustNum());
		return rowView;
	}

}
