package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.objects.RentalOrderCustomerList;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalOrderCustomer extends AppCompatActivity implements OnClickListener{
	private ListView list;
	String data;
	private ArrayList<RentalOrderCustomerList> orderCustlist;
	private ArrayList<RentalOrderList> orderLists;
	private User user;
	private ImageView back;
	private static Dialog dialog;
	private TextView backtext;
	private customerAdapter adap;
	private int count;
	private boolean[] thumbnailsselection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rental_order_custname);
		orderCustlist = SessionData.getInstance().getCustomerlist();
		count = orderCustlist.size();
		thumbnailsselection = new boolean[count];
		user = SessionData.getInstance().getUser();
		SessionData.getInstance().setBack(1);
		adap = new customerAdapter(RentalOrderCustomer.this, orderCustlist);
		list = (ListView) findViewById(R.id.custlist);
		list.setAdapter(adap);
		backtext = (TextView) findViewById(R.id.backtext);
		back = (ImageView) findViewById(R.id.backicon);
		back.setOnClickListener(this);
		backtext.setOnClickListener(this);
		list.setTextFilterEnabled(true);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SessionData.getInstance().setCustnum(
						SessionData.getInstance().getCustomerlist()
								.get(position).getShipTo());
				SessionData.getInstance().setKcustnum(
						SessionData.getInstance().getCustomerlist()
								.get(position).getCustNum());
				new AsycOrderLists().execute();
				// Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
				// Toast.LENGTH_LONG).show();

			}

		});
	}

	public class customerAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private final Context mContext;

		private final ArrayList<RentalOrderCustomerList> list;

		public customerAdapter(Context context,
				ArrayList<RentalOrderCustomerList> orderCustlist) {
			mContext = context;
			this.list = orderCustlist;
			orderCustlist = null;
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			RentalOrderCustomerList deal = (RentalOrderCustomerList) orderCustlist
					.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.custlist_row, null);

				holder.shipto = (TextView) convertView
						.findViewById(R.id.shipto);
				holder.custno = (TextView) convertView
						.findViewById(R.id.custnum);
				holder.custname = (TextView) convertView
						.findViewById(R.id.custname);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.shipto.setText(deal.getShipTo());
			holder.custno.setText(deal.getCustNum());
			holder.custname.setText(deal.getCustName());
			return convertView;
		}

	}

	class ViewHolder {
		TextView shipto, custno, custname;
	}

	private class AsycOrderLists extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalOrderCustomer.this);
		};

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				orderLists = WebServiceConsumer.getInstance()
						.getRentalOrderList(user.getUserDescription(),
								SessionData.getInstance().getCustomshipto(),
								"", SessionData.getInstance().getKcustnum(),
								SessionData.getInstance().getCustnum());

			} catch (java.net.SocketTimeoutException e) {
				orderLists = null;

			} catch (Exception e) {
				orderLists = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.d("Orderlist", "" + orderLists);
			if (orderLists != null && orderLists.size() > 0) {
				ProgressBar.dismiss();
				if(orderLists.get(0).getMessage().equals("")){
					SessionData.getInstance().setOrderlist(orderLists);
					Intent intent = new Intent(RentalOrderCustomer.this,
							RentalOrderOrderlist.class);
					startActivity(intent);
				}
				else{

					if(orderLists.get(0).getMessage().contains("Session")){
						new AsyncLoginTask().execute();
					}else{
						dialog = new Dialog(RentalOrderCustomer.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						Message.setText(orderLists.get(0).getMessage());

						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

								Intent inspection = new Intent(RentalOrderCustomer.this,
										MainActivity.class);
								startActivity(inspection);
								dialog.dismiss();
							}
						});


						dialog.show();
					}

				}

//				if((!orderLists.get(0).getMessage().equals(""))){
//					ProgressBar.dismiss();
//					AlertDialogBox.showAlertDialog(RentalOrderCustomer.this,
//							orderLists.get(0).getMessage());
//				}
//				else{
//
//				}

			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalOrderCustomer.this,
						"Data is not found.");
			}
		}

	}




	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalOrderCustomer.this);
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				user = WebServiceConsumer.getInstance()
						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
								SessionData.getInstance().getLogin_password());
				Log.d("Session Expiered", "Session Expiered");
				Log.d("New User Token",""+SessionData.getInstance().getTemp_userToken());
				Log.d("After Session Expired","Equip_ID"+SessionData.getInstance().getTemp_equipId());
			} catch (java.net.SocketTimeoutException e) {
				user = null;
			} catch (Exception e) {
				user = null;
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();
			if(user!=null){
				SessionData.getInstance().setUser(user);


				if(user.getUserId()==0){
					dialog = new Dialog(RentalOrderCustomer.this);
					dialog.setCanceledOnTouchOutside(true);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.message);


					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
					Message.setText(user.getUserDescription());

					yes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

//							Intent inspection = new Intent(RentalOrderCustomer.this,
//									MainActivity.class);
//							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{

						new AsycOrderLists().execute();

				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalOrderCustomer.this,
						"Data is not found");
			}



		}
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent intent = new Intent(RentalOrderCustomer.this,
				RentalOrderSearch.class);

		startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 if (v == back){
			onBackPressed();
		}
		else if (v == backtext){
			onBackPressed();
		}
	}

}
