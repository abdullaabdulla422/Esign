package com.ebs.rental.parts;

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

import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.general.RentalOrderOrderlist;
import com.ebs.rental.objects.PartorderList;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderNotesDetailObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalPartsOrderlist extends AppCompatActivity implements OnClickListener{
	private ListView list;
	String data;
	private ArrayList<PartorderList> orderLists;
	private ArrayList<RentalOrderListDetailObject> listDetailObjects;
	private User user;
	private OrderListAdapter adap;
	private ImageView back;
	private TextView backtext;
	private static Dialog dialog;
	private int count;
	private boolean[] thumbnailsselection;

	private ArrayList<RentalOrderNotesDetailObject> NotesDetailObjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rental_parts_order_list);
		orderLists = SessionData.getInstance().getPorder();
		count = orderLists.size();
		thumbnailsselection = new boolean[count];
		adap = new OrderListAdapter(RentalPartsOrderlist.this, orderLists);
		user = SessionData.getInstance().getUser();
		list = (ListView) findViewById(R.id.partsorderlist);
		list.setAdapter(adap);
		list.setTextFilterEnabled(true);
		backtext = (TextView) findViewById(R.id.backtext);
		back = (ImageView) findViewById(R.id.backicon);
		back.setOnClickListener(this);
		backtext.setOnClickListener(this);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// SessionData.getInstance().setCustomshipto(
				// SessionData.getInstance().getDealer().get(position)
				// .getkBranch());

				// Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
				// Toast.LENGTH_LONG).show();
				SessionData.getInstance().setOrdernumber(
						(SessionData.getInstance().getPorder().get(position)
								.getOrderno()));
				SessionData.getInstance().setSigndocid(
						SessionData.getInstance().getPorder().get(position)
								.getSigndocid());
				SessionData.getInstance().setCustomernumber(
						SessionData.getInstance().getPorder().get(position)
								.getCustno());
				SessionData.getInstance().setContact(
						SessionData.getInstance().getPorder().get(position)
								.getContact());

				new AsycNotesListDetail().execute();
				// new AsycOrderListDetail().execute();

			}

		});

	}


	private class AsycNotesListDetail extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderlist.this);
		};

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				NotesDetailObjects = WebServiceConsumer.getInstance()
						.getRentalOrderNotes(
								user.getUserDescription(),
								SessionData.getInstance()
										.getOrdernumber(),
								SessionData.getInstance().getBranchcode(),"P");

				Log.d("order", "" + Integer.toString(SessionData.getInstance()
						.getOrdernumber()));
				Log.d("cust", "" + SessionData.getInstance().getCustomernumber());


			} catch (java.net.SocketTimeoutException e) {
				NotesDetailObjects = null;

			} catch (Exception e) {
				NotesDetailObjects = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.d("OrderCUSTlist", "" + orderLists);
			SessionData.getInstance().setNotesDetailObjects(NotesDetailObjects);

			if (NotesDetailObjects != null && NotesDetailObjects.size() > 0) {
				ProgressBar.dismiss();

				if(NotesDetailObjects.get(0).getMessage().equals("")){
					new AsycOrderListDetail().execute();
				}
				else{

					if(NotesDetailObjects.get(0).getMessage().contains("Session")){
						new AsyncLoginTask().execute();
					}
					else{
						new AsycOrderListDetail().execute();
					}

				}

			} else {
				ProgressBar.dismiss();
				new AsycOrderListDetail().execute();
			}
		}

	}


	public class OrderListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private final Context mContext;

		private final ArrayList<PartorderList> list;

		public OrderListAdapter(Context context, ArrayList<PartorderList> list) {
			mContext = context;
			this.list = list;
			list = null;
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

		@SuppressLint({"InflateParams", "SetTextI18n"})
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			PartorderList deal = (PartorderList) orderLists.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.parts_orderlist_row, null);

				holder.Txtbranch = (TextView) convertView
						.findViewById(R.id.pbranchcode);
				holder.Txtorderno = (TextView) convertView
						.findViewById(R.id.pordernoo);
				holder.TxtCustno = (TextView) convertView
						.findViewById(R.id.pcustno);
				holder.TxtShipto = (TextView) convertView
						.findViewById(R.id.pshiptoo);
				holder.Txtorderdate = (TextView) convertView
						.findViewById(R.id.porderdate);
				holder.Txtcomany = (TextView) convertView
						.findViewById(R.id.pcompany);
				holder.Txttype = (TextView) convertView
						.findViewById(R.id.ptype);
				holder.txtCustpo = (TextView) convertView
						.findViewById(R.id.pcustomerPO);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.Txtbranch.setText(deal.getBranch());

			holder.Txtorderno.setText("" + deal.getOrderno());
			holder.TxtCustno.setText(deal.getCustno());
			holder.TxtShipto.setText(deal.getShipto());
			holder.Txtorderdate.setText(deal.getOrderdate());
			holder.Txtcomany.setText(deal.getCustname());
			holder.Txttype.setText(deal.getType());
			holder.txtCustpo.setText(deal.getCustomerPo());

			return convertView;
		}
	}

	class ViewHolder {
		TextView Txtbranch, Txtorderno, TxtCustno, TxtShipto, Txtorderdate,
				Txttype, Txtcomany, txtCustpo;
	}

	private class AsycOrderListDetail extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderlist.this);
		};

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				listDetailObjects = WebServiceConsumer.getInstance()
						.getRentalOrderListDetail(
								user.getUserDescription(),
								Integer.toString(SessionData.getInstance()
										.getOrdernumber()),
								SessionData.getInstance().getCustomernumber(),"P",SessionData.getInstance().getBranchcode());

			} catch (java.net.SocketTimeoutException e) {
				listDetailObjects = null;

			} catch (Exception e) {
				listDetailObjects = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ProgressBar.dismiss();
			Log.d("OrderCUSTlist", "" + orderLists);
			if (listDetailObjects != null && listDetailObjects.size() > 0) {
				ProgressBar.dismiss();

				if(listDetailObjects.get(0).getMessage().equals("")){
					SessionData.getInstance().setOrderListDetail(listDetailObjects);
					Intent intent = new Intent(RentalPartsOrderlist.this,
							RentalPartsOrderListDetail.class);
					startActivity(intent);
				}
				else{

					if(listDetailObjects.get(0).getMessage().contains("Session")){
						new AsyncLoginTask().execute();
					}else{
						dialog = new Dialog(RentalPartsOrderlist.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						ImageView closeImg=dialog.findViewById(R.id.close_img);

						Message.setText(listDetailObjects.get(0).getMessage());

						closeImg.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

//								Intent inspection = new Intent(RentalPartsOrderlist.this,
//										MainActivity.class);
//								startActivity(inspection);
								dialog.dismiss();
							}
						});


						dialog.show();
					}


				}

			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalPartsOrderlist.this,
						"Data not found");
			}
		}

	}


	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderlist.this);
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
					dialog = new Dialog(RentalPartsOrderlist.this);
					dialog.setCanceledOnTouchOutside(true);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.message);


					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
					ImageView closeImg=dialog.findViewById(R.id.close_img);

					Message.setText(user.getUserDescription());

					closeImg.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					yes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							Intent inspection = new Intent(RentalPartsOrderlist.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{

					new AsycOrderListDetail().execute();

				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalPartsOrderlist.this,
						"Data is not found");
			}



		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if(SessionData.getInstance().getBack() == 1){
			Intent intent = new Intent(RentalPartsOrderlist.this,
					PartsOrderCustomer.class);
			startActivity(intent);
		}
		else{
			Intent intent = new Intent(RentalPartsOrderlist.this,
					RentalPartsOrderSearch.class);
			startActivity(intent);
		}
	
		

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
