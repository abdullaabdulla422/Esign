package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.adapters.RentalListAdapter;
import com.ebs.rental.objects.RentalsList;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;


@SuppressWarnings("ALL")
public class RentalListActivity extends AppCompatActivity implements
		OnItemClickListener, OnClickListener {

	@SuppressLint("StaticFieldLeak")
	private static TextView title;
	private static Dialog dialog;
	private ArrayList<RentalsList> rentalList;
	private RentalListAdapter rentalAdapter;
	private ImageView back;
	private ImageView home;
	private ListView rentalListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		title = (TextView) findViewById(R.id.title_text);
		title.setText(SessionData.getInstance().getCustName());
		setContentView(R.layout.fragment_rental_list);
		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);

		home = (ImageView) findViewById(R.id.img_next);
		home.setOnClickListener(this);
		rentalList = new ArrayList<RentalsList>();
		rentalListView = (ListView) findViewById(R.id.rental_list);
		rentalListView.setOnItemClickListener(this);
		new AsyncRental().execute();
	}

	private class AsyncRental extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalListActivity.this);

		}

		@SuppressLint("LongLogTag")
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Log.d("UserToken", ""
						+ SessionData.getInstance().getUsertoken());
				rentalList = WebServiceConsumer.getInstance().getRentalList(
						SessionData.getInstance().getUsertoken(),
						SessionData.getInstance().getCustNum());
				Log.d("The customer list size is", "" + rentalList.size());
			} catch (java.net.SocketTimeoutException e) {
				rentalList = null;
			} catch (Exception e) {
				rentalList = null;
				e.printStackTrace();

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (rentalList != null) {
				ProgressBar.dismiss();
				if(rentalList.get(0).getMessage().equals("")){
					back.setVisibility(View.VISIBLE);
					home.setVisibility(View.VISIBLE);
					rentalAdapter = new RentalListAdapter(RentalListActivity.this,
							rentalList);
					rentalAdapter.notifyDataSetChanged();
					rentalListView.setAdapter(rentalAdapter);
				}
				else{
					dialog = new Dialog(RentalListActivity.this);
					dialog.setCanceledOnTouchOutside(true);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.message);


					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
					Message.setText(rentalList.get(0).getMessage());

					yes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							Intent inspection = new Intent(RentalListActivity.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}


			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalListActivity.this,
						"Check WEbservice");
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		SessionData.getInstance().setEquipmentId(
				rentalList.get(position).getEquipmentId());
		SessionData.getInstance().setRentalId(
				rentalList.get(position).getRentId());
		SessionData.getInstance().setOrderNo(
				rentalList.get(position).getOrderNo());
		SessionData.getInstance().setDeliveryId(
				rentalList.get(position).getDeliveryId());

		SessionData.getInstance().setReturnId(
				rentalList.get(position).getReturnId());
		finish();
		Intent intent = new Intent(RentalListActivity.this,
				InspectionActivity.class);
		startActivity(intent);

	}

	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(RentalListActivity.this,
				CustomerListActivity.class));
	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			onBackPressed();
		}
		if (v == home) {
			onBackPressed();
		}
	}
}
