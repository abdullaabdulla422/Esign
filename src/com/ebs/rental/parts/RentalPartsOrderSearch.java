package com.ebs.rental.parts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.EbsMenu;
import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.objects.PartorderList;
import com.ebs.rental.objects.RentalOrderCustomerList;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import com.google.android.gms.common.api.GoogleApiClient;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalPartsOrderSearch extends AppCompatActivity implements OnClickListener {
	private EditText orderno;
	private EditText custname;
	private Button search;
	private Button clear;
	private Button dealer;
	private ArrayList<RentalOrderCustomerList> orderCustlist;
	private ArrayList<PartorderList> orderLists;
	private User user;
	private static Dialog dialog;
	private ImageView back;

	private TextView backtext;
	private int session = 0;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rental_parts_order_search);
		orderno = (EditText) findViewById(R.id.edt_porderno);
		custname = (EditText) findViewById(R.id.edt_pcustname);
		search = (Button) findViewById(R.id.btn_psearch);
		dealer = (Button) findViewById(R.id.btn_pdealer);
		clear = (Button) findViewById(R.id.btn_pclear);
		user = SessionData.getInstance().getUser();
		backtext = (TextView) findViewById(R.id.backtext);
		back = (ImageView) findViewById(R.id.backicon);
		back.setOnClickListener(this);
		backtext.setOnClickListener(this);
		orderno.setFilters(new InputFilter[]{new InputFilter() {
			public CharSequence filter(CharSequence src, int start, int end,
									   Spanned dst, int dstart, int dend) {
				if (src.length() > 0) {
					custname.setText("");
					//custname.setEnabled(false);
				}
				return src;

			}

		}});
		custname.setFilters(new InputFilter[]{new InputFilter() {
			public CharSequence filter(CharSequence src, int start, int end,
									   Spanned dst, int dstart, int dend) {
				if (src.length() > 0) {
					orderno.setText("");
//					orderno.setEnabled(false);
				}
				return src;

			}

		}});
		clear.setOnClickListener(this);
		search.setOnClickListener(this);
		dealer.setOnClickListener(this);


		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == search) {
			if (orderno.getText().toString().length() != 0) {
				new AsycOrderList().execute();

			} else if (custname.getText().toString().length() != 0) {

				new AsycCusterlist().execute();

			} else {

				Toast.makeText(getApplicationContext(), "Enter the field",
						Toast.LENGTH_LONG).show();
			}
		}
		if (v == clear) {
			orderno.setText("");
			custname.setText("");
			orderno.setEnabled(true);
			custname.setEnabled(true);

		}
		if (v == dealer) {
			Intent inten = new Intent(RentalPartsOrderSearch.this,
					RentalPartsOrderBranch.class);

			startActivity(inten);
		} else if (v == back) {
			onBackPressed();
		}
		else if (v == backtext) {
			onBackPressed();
		}
	}

//	@Override
//	public void onStart() {
//		super.onStart();
//
//		// ATTENTION: This was auto-generated to implement the App Indexing API.
//		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		client.connect();
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"RentalPartsOrderSearch Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app deep link URI is correct.
//				Uri.parse("android-app://com.ebs.rental.parts/http/host/path")
//		);
//		AppIndex.AppIndexApi.start(client, viewAction);
//	}
//
//	@Override
//	public void onStop() {
//		super.onStop();
//
//		// ATTENTION: This was auto-generated to implement the App Indexing API.
//		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"RentalPartsOrderSearch Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app deep link URI is correct.
//				Uri.parse("android-app://com.ebs.rental.parts/http/host/path")
//		);
//		AppIndex.AppIndexApi.end(client, viewAction);
//		client.disconnect();
//	}

	private class AsycCusterlist extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderSearch.this);
		}

		;

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				orderCustlist = WebServiceConsumer.getInstance()
						.getRentalOrderCustomerList(user.getUserDescription(),
								custname.getText().toString().trim());

			} catch (SocketTimeoutException e) {
				orderCustlist = null;

			} catch (Exception e) {
				orderCustlist = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ProgressBar.dismiss();
			Log.d("OrderCUSTlist", "" + orderCustlist);
			if (orderCustlist != null && orderCustlist.size() > 0) {

				if (orderCustlist.get(0).getMessage().equals("")) {
					ProgressBar.dismiss();
					SessionData.getInstance().setCustomerlist(orderCustlist);

					Intent intent = new Intent(RentalPartsOrderSearch.this,
							PartsOrderCustomer.class);
					startActivity(intent);
				} else {

					if(orderCustlist.get(0).getMessage().contains("Session")){
						session = 0;
						new AsyncLoginTask().execute();
					}
					else{
						dialog = new Dialog(RentalPartsOrderSearch.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						ImageView closeImg=dialog.findViewById(R.id.close_img);

						Message.setText(orderCustlist.get(0).getMessage());

						closeImg.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});

						yes.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {

//								Intent inspection = new Intent(RentalPartsOrderSearch.this,
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
				AlertDialogBox.showAlertDialog(RentalPartsOrderSearch.this,
						"Customer Name entered is invalid.");
			}
		}

	}

	private class AsycOrderList extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderSearch.this);
		}

		;

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				orderLists = WebServiceConsumer.getInstance().getPartorderList(
						user.getUserDescription(),
						SessionData.getInstance().getBranchcode(),
						orderno.getText().toString(), "", "");

			} catch (SocketTimeoutException e) {
				orderLists = null;

			} catch (Exception e) {
				orderLists = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.d("orderLists", "" + orderLists);
			ProgressBar.dismiss();

			if (orderLists != null && orderLists.size() > 0) {

				if(orderLists.get(0).getMessage().equals("")){
					SessionData.getInstance().setPorder(orderLists);
					SessionData.getInstance().setBack(0);
					Intent intent = new Intent(RentalPartsOrderSearch.this,
							RentalPartsOrderlist.class);
					startActivity(intent);
				}
				else{
					if(orderLists.get(0).getMessage().contains("Session")){
						session = 1;
						new AsyncLoginTask().execute();
					}else{
						dialog = new Dialog(RentalPartsOrderSearch.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						ImageView closeImg=dialog.findViewById(R.id.close_img);

						Message.setText(orderLists.get(0).getMessage());

						closeImg.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {


								dialog.dismiss();
							}
						});


						dialog.show();
					}

				}

			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalPartsOrderSearch.this,
						"Order No entered is invalid.");
			}
		}

	}

	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalPartsOrderSearch.this);
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
					dialog = new Dialog(RentalPartsOrderSearch.this);
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

							Intent inspection = new Intent(RentalPartsOrderSearch.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{
					if(session==0){
						new AsycCusterlist().execute();
					}else if (session == 1){
						new AsycOrderList().execute();
					}
				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalPartsOrderSearch.this,
						"Data is not found");
			}



		}
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
//		Intent intent = new Intent(RentalPartsOrderSearch.this,
//				RentalPartsOrderBranch.class);
//
//		startActivity(intent);

		Intent intent = new Intent(RentalPartsOrderSearch.this,
				EbsMenu.class);

		startActivity(intent);
	}
}
