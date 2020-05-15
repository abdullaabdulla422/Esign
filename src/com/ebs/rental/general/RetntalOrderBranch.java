package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RetntalOrderBranch extends AppCompatActivity implements OnClickListener {
	private ListView list;
	String data;
	private EditText inputSearch;
	private ArrayList<DealerBranches> dealer;
	RentalOrderList rens;
	private static Dialog dialog;
	private String unitIdChanged = "";
	private DealerAdapter adap;
	private int count;
	private DealerBranches dealcount;
	private int i;
	private Button ormlist;
	ArrayAdapter<String> adapter;
	DealerBranches deal2;
	private User user;
	private int inst = 1;
	private int inen = 15;
	private boolean[] thumbnailsselection;
	private TextView backtext;
	private ImageView back;
	private int session = 0;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rental_order_branch);
		dealer = SessionData.getInstance().getDealer();

		Log.d("size", "" + dealer.size());

		count = dealer.size();

		thumbnailsselection = new boolean[count];
		// adapter = new ArrayAdapter<String>(this, R.layout.dealer_row,
		// R.id.branchrow);
		// list.setAdapter(adapter);
		adap = new DealerAdapter(RetntalOrderBranch.this, dealer);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		

		ormlist = (Button) findViewById(R.id.moreorderlist);
		ormlist.setOnClickListener(this);
		backtext = (TextView) findViewById(R.id.backtext);
		back = (ImageView) findViewById(R.id.backicon);
		back.setOnClickListener(this);
		backtext.setOnClickListener(this);
		list = (ListView) findViewById(R.id.dealerlist);
		list.setAdapter(adap);
		list.setTextFilterEnabled(true);
		user = SessionData.getInstance().getUser();
		dealcount = dealer.get(0);
		i = dealcount.getProwcnt();
		Log.d("value for count", "" + i);
		inputSearch.requestFocus();
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// ListView Item Click Listener
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SessionData.getInstance().setCustomshipto(
						SessionData.getInstance().getDealer().get(position)
								.getkBranch());
				SessionData.getInstance().setBranchcode(
						SessionData.getInstance().getDealer().get(position)
								.getkBranch());
				SessionData.getInstance().setSind(
						SessionData.getInstance().getDealer().get(position)
								.getPrownum());
				SessionData.getInstance().setRcount(
						SessionData.getInstance().getDealer().get(position)
								.getProwcnt());
				// Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
				// Toast.LENGTH_LONG).show();

				Intent inspection = new Intent(RetntalOrderBranch.this,
						RentalOrderSearch.class);
				startActivity(inspection);

			}

		});
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,
					int count) {
				// When user changed the Text

			}

			@Override
			public void beforeTextChanged(CharSequence cs, int start,
					int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable theWatchedText) {
				// TODO Auto-generated method stub
				unitIdChanged = theWatchedText.toString();
				// deal2.getBranchName();
				SessionData.getInstance().setBname(unitIdChanged);
				new AsyncDealerBranchFiltter().execute();

			}

		});

	}

	@Override
	public void onClick(View v) {
		if (v == ormlist) {
			if (inst < i) {

				// if(inst < dealcount.getProwcnt()){
				// if(inst < SessionData.getInstance().getRcount()){
				new AsyncDealerBranch().execute();
			} else {
				new AsyncDealerBranchFiltter().execute();
			}

		}
		else if(v == back){
			onBackPressed();
		}
		else if (v == backtext){
			onBackPressed();
		}
	}

	private class AsyncDealerBranch extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RetntalOrderBranch.this);
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				inst = inen + 1;

				inen = inen + 15;

				dealer = WebServiceConsumer.getInstance().getDealerBranch(
						user.getUserDescription(),
						SessionData.getInstance().getBname(), inst, inen);

			} catch (java.net.SocketTimeoutException e) {
				dealer = null;

			} catch (Exception e) {
				dealer = null;

				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();
			if (dealer.size() == 0) {
				Toast.makeText(getApplicationContext(), "No More Data to Load",
						Toast.LENGTH_LONG).show();
			} else {
//				SessionData.getInstance().setDealer(dealer);
//				count = dealer.size();
//				list.setAdapter(adap);

				if(dealer.get(0).getMessage().equals("")){
					SessionData.getInstance().setDealer(dealer);
					count = dealer.size();
					list.setAdapter(adap);
				}
				else{

					if(dealer.get(0).getMessage().contains("Session")){
						session = 0;
						new AsyncLoginTask().execute();
					}else{
						dialog = new Dialog(RetntalOrderBranch.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						Message.setText(dealer.get(0).getMessage());

						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

								Intent inspection = new Intent(RetntalOrderBranch.this,
										MainActivity.class);
								startActivity(inspection);
								dialog.dismiss();
							}
						});


						dialog.show();
					}

				}
			}

		}

	}

	private class AsyncDealerBranchFiltter extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RetntalOrderBranch.this);
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				inst = 1;

				inen = 15;

				dealer = WebServiceConsumer.getInstance().getDealerBranch(
						user.getUserDescription(),
						SessionData.getInstance().getBname(), inst, inen);

			} catch (java.net.SocketTimeoutException e) {
				dealer = null;

			} catch (Exception e) {
				dealer = null;

				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();

			if (dealer.size() == 0) {
				Toast.makeText(getApplicationContext(), "No More Data to Load",
						Toast.LENGTH_LONG).show();
			} else {


				if(dealer.get(0).getMessage().equals("")){
					SessionData.getInstance().setDealer(dealer);
					count = dealer.size();
					list.setAdapter(adap);
				}
				else{
					if(dealer.get(0).getMessage().contains("Session")){
						session = 1;
						new AsyncLoginTask().execute();
					}else{
						dialog = new Dialog(RetntalOrderBranch.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						Message.setText(dealer.get(0).getMessage());

						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

//								Intent inspection = new Intent(RetntalOrderBranch.this,
//										MainActivity.class);
//								startActivity(inspection);
								dialog.dismiss();
							}
						});


						dialog.show();
					}

				}
			}
		}
	}


	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RetntalOrderBranch.this);
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


				if(user.getUserDescription().contains("Login is already in use")){
					dialog = new Dialog(RetntalOrderBranch.this);
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

							Intent inspection = new Intent(RetntalOrderBranch.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{
					if(session==0){
						new AsyncDealerBranch().execute();
					}else if (session == 1){
						new AsyncDealerBranchFiltter().execute();
					}
				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RetntalOrderBranch.this,
						"Data is not found");
			}



		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		dealer = SessionData.getInstance().getDealer();
	}

	public class DealerAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private final Context mContext;

		private final ArrayList<DealerBranches> list;

		public DealerAdapter(Context context, ArrayList<DealerBranches> list) {
			mContext = context;
			this.list = list;
			list = null;

		}

		@Override
		public int getCount() {

			return count;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			DealerBranches deal = (DealerBranches) dealer.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.dealer_row, null);

				holder.customerName = (TextView) convertView
						.findViewById(R.id.branchrow);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.customerName.setText(deal.getBranchName());

			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

	}

	class ViewHolder {
		TextView customerName;
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent intent = new Intent(RetntalOrderBranch.this, EbsMenu.class);
		startActivity(intent);

	}

}
