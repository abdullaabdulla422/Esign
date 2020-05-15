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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.Filtereddatas;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderNotesDetailObject;
import com.ebs.rental.objects.RentalOrderTerms;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class RentalOrderListDetail extends AppCompatActivity implements OnClickListener {

	private TextView TxtCustName;
	private TextView TxtShipto;
	private TextView TxtShiptoAdd;
	private TextView TxtShiptoCity;
	private TextView TxtShiptoState;
	private TextView TxtCustPhone;
	private TextView Txtorder;
	private TextView TxtDate;
	private TextView backtext;
	private ImageView back;
	private ListView list;
	private static Dialog dialog;
	private Button terms;
	private ArrayList<RentalOrderListDetailObject> listDetailObjects;

	private ArrayList<RentalOrderNotesDetailObject> NotesDetailObjects;

	private ArrayList<RentalOrderListDetailObject> filterlistDetailObjects = new ArrayList<>();
	ArrayList<Filtereddatas> filtereddatas;
	private RentalOrderListDetailObject DetailObjects;
	private RentalOrderTerms orderTerms;
	private User user;
	String str;
	private String description;
	private OrderDetailAdapter adap;
	private int count;  
	private boolean[] thumbnailsselection; 
	private final int docid = SessionData.getInstance().getSigndocid();
	private int array;

	private String signature;
	TextView txtHeaderNotes;

    LinearLayout notesLayout;
    Boolean bool = false;
 
	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_rental_order_detail);
		listDetailObjects = SessionData.getInstance().getOrderListDetail();
		array = listDetailObjects.size();
		for (int i = 0; i < array; i ++){
			
        listDetailObjects.get(i).setDuplicatedesc(listDetailObjects.get(i).getKpart());
			
		}

		for(int i = 0; i < array; i ++){
			if(!listDetailObjects.get(i).getAction().contains("3")){
				filterlistDetailObjects.add(listDetailObjects.get(i));
			}

		}

		NotesDetailObjects = SessionData.getInstance().getNotesDetailObjects();

        notesLayout = (LinearLayout)findViewById(R.id.notes_layout);

		if(NotesDetailObjects!=null){
			if(NotesDetailObjects.size()!=0){
			for(int i = 0; i < NotesDetailObjects.size();i++) {
				int itemNumber = NotesDetailObjects.get(i).getItemNumber();
				String Notes = NotesDetailObjects.get(i).getNotesDetail();
				for (int j = 0; j < filterlistDetailObjects.size(); j++) {
					if (itemNumber == filterlistDetailObjects.get(j).getOedetailId()) {
						filterlistDetailObjects.get(j).setPickNotes(Notes);

					}
				}
			}
			}
		}


		
		TxtCustName = (TextView) findViewById(R.id.txtcustname_no);
		TxtShipto = (TextView) findViewById(R.id.txtshipto);
		TxtShiptoAdd = (TextView) findViewById(R.id.txtshipadd);
		TxtShiptoCity = (TextView) findViewById(R.id.txtshipcity);
		TxtShiptoState = (TextView) findViewById(R.id.txtshipstate);
		TxtCustPhone = (TextView) findViewById(R.id.txtcustphone);
		Txtorder = (TextView) findViewById(R.id.txtorder);
		TxtDate = (TextView) findViewById(R.id.txtdate);
		terms = (Button) findViewById(R.id.btn_accept_terms);
		user = SessionData.getInstance().getUser();
		backtext = (TextView) findViewById(R.id.backtext);
		back = (ImageView) findViewById(R.id.backicon);

		txtHeaderNotes = (TextView) findViewById(R.id.txtpicknotes);



		back.setOnClickListener(this);
		backtext.setOnClickListener(this);
		terms.setOnClickListener(this); 
		
		adap = new OrderDetailAdapter(RentalOrderListDetail.this,
				filterlistDetailObjects);
		list = (ListView) findViewById(R.id.detaillist_view);
		list.setAdapter(adap);
		list.setTextFilterEnabled(true);

		count = filterlistDetailObjects.size();
		thumbnailsselection = new boolean[count];

		Log.d("size", "" + listDetailObjects.size());
		DetailObjects = listDetailObjects.get(0);              
		TxtCustName.setText(DetailObjects.getKcustnum() + " : "
				+ DetailObjects.getUserName());
		SessionData.getInstance().setKcustnum(DetailObjects.getKcustnum());
		TxtCustPhone.setText(DetailObjects.getCustPhone());
		TxtDate.setText(DetailObjects.getOeonrentDate());
		TxtShipto.setText("Shipto : " + DetailObjects.getCustsnum());
		SessionData.getInstance().setCustomshipto(DetailObjects.getCustsnum());
		TxtShiptoAdd.setText(DetailObjects.getShipAdd());
		TxtShiptoCity.setText(DetailObjects.getShipCity());
		TxtShiptoState.setText(DetailObjects.getShipState());
		Txtorder.setText("" + SessionData.getInstance().getOrdernumber());

		for(int i = 0; i<listDetailObjects.size();i++){
			if(listDetailObjects.get(i).getAction().contains("3")){
				txtHeaderNotes.setText(listDetailObjects.get(i).getDescription());
                bool = true;
			}
		}

        if(bool==false){
            notesLayout.setVisibility(View.GONE);
        }
		

	}

	public class OrderDetailAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private final Context mContext;
		private final ArrayList<RentalOrderListDetailObject> list;
		@SuppressLint("UseSparseArrays")
		public final HashMap<Integer,String> myList=new HashMap<Integer,String>();

		public OrderDetailAdapter(Context context,
				ArrayList<RentalOrderListDetailObject> list) {
			mContext = context;
			this.list = list;
			list = null;
		    for(int i=0;i<count;i++)
		    {
		       myList.put(i,"");
		    }

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			final RentalOrderListDetailObject deal = (RentalOrderListDetailObject) filterlistDetailObjects
					.get(position);
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.rental_order_detail_row, null);

				holder.Txtkpart = (TextView) convertView
						.findViewById(R.id.kpartid);
				holder.Txtkmanufacture = (TextView) convertView
						.findViewById(R.id.kmanufacture);
				holder.Txtdescription = (EditText) convertView
						.findViewById(R.id.descript);
				holder.Txtkmodel = (TextView) convertView
						.findViewById(R.id.kmodel);
				holder.txtQty = (TextView)convertView.findViewById(R.id.qty);



				/*holder.Txtprogram = (TextView) convertView
						.findViewById(R.id.program);*/
				holder.Txtkserialno = (TextView) convertView
						.findViewById(R.id.kserialno);
				holder.Txtrate = (TextView) convertView.findViewById(R.id.rate);
				holder.Txtcellprice = (TextView) convertView.findViewById(R.id.cellprice);
				holder.txtNotes = (TextView) convertView.findViewById(R.id.txt_notes);

				
//				holder.Txtdescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//				        public void onFocusChange(View v, boolean hasFocus) {
//				            if (!hasFocus) {
//				                EditText et = (EditText) v.findViewById(R.id.description);
//				                listDetailObjects.get(position).setDescription(et.getText().toString().trim());
//			                	 Log.d("des","" + listDetailObjects.get(position).getDescription());
//				              //  data.get(position).put("dueAmount", et.getText().toString().trim());
//				            }
//				        }
//				    });
				if(SessionData.getInstance().getEquipenable() == 1){
					holder.Txtdescription.setEnabled(false);
				}
				else{
					if(SessionData.getInstance().getEdtEquipId().contains("True")){
						holder.Txtdescription.addTextChangedListener(new TextWatcher(){// add text watcher to update your data after editing
							public void afterTextChanged(Editable s) {
								filterlistDetailObjects.get(holder.ref).setDuplicatedesc(s.toString());
								Log.d("des","" + filterlistDetailObjects.get(position));
							}
							public void beforeTextChanged(CharSequence s, int start, int count, int after){}
							public void onTextChanged(CharSequence s, int start, int before, int count){


							}
						});
					}else{
						holder.Txtdescription.setEnabled(false);
					}

				}
				


 
				convertView.setTag(holder);  
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.ref = position;
			holder.Txtkpart.setText(deal.getDescription());
			holder.Txtkmanufacture.setText(deal.getKmanufacture());
			holder.Txtdescription.setText(deal.getDuplicatedesc());   
			holder.Txtkmodel.setText(deal.getKmodel()); 
			//holder.Txtprogram.setText(deal.getProgram());  
			holder.Txtkserialno.setText(deal.getKserialNum());
			holder.Txtrate.setText(deal.getRate());
	        holder.Txtcellprice.setText(deal.getOepsell());
			holder.txtQty.setText(""+deal.getQty());
			String str1 = "";
			if(deal.getPickNotes()!=null) {
				 str1 = deal.getPickNotes().replace("\\n", "\n");
			}
			holder.txtNotes.setText(str1);
	        //description = holder.Txtdescription.getText().toString();
	        Log.d("des", "" + holder);
	        return convertView;
		}  
	} 
   
	class ViewHolder {
		TextView Txtkpart, Txtkmanufacture,  Txtkmodel,
				Txtprogram, Txtkserialno, Txtrate, Txtcellprice, txtQty, txtNotes;
	    EditText Txtdescription;
	    int ref;
	}

	@Override
	public void onClick(View v){
		if (v == terms) {
			int j = 0;
			int size = 1;
			ArrayList<String> desc = new ArrayList<String>();
			ArrayList<String> equpoldid = new ArrayList<String>();

	        ArrayList<String> equpid = new ArrayList<String>();
			for(int i = 0;i < filterlistDetailObjects.size(); i ++){
				if(!(filterlistDetailObjects.get(i).getKpart().equals(filterlistDetailObjects.get(i).getDuplicatedesc()))){
					//
					desc.add((filterlistDetailObjects.get(i).getDuplicatedesc()));
					equpoldid.add((filterlistDetailObjects.get(i).getKpart()));
					equpid.add(Integer.toString(i));
					//filtereddatas.get(i).setFilterdesc((listDetailObjects.get(i).getDuplicatedesc()));
					Log.d("filterdes","" + (filterlistDetailObjects.get(i).getDuplicatedesc()));
					Log.d("filtereqp","" + i);
					
				}
			}
			SessionData.getInstance().setFiltereddesc(desc);
			SessionData.getInstance().setFilteredequip(equpid);
			SessionData.getInstance().setFilteredequoldip(equpoldid);
			
			Log.d("sessionfilterdes","" + SessionData.getInstance().getFiltereddesc());
			Log.d("sessionfiltereqp","" + SessionData.getInstance().getFilteredequip());
			Log.d("sessionfiltereqp","" + SessionData.getInstance().getFilteredequoldip());
			
			
			//.getInstance().setFiltereddatas(filtereddatas);
			
			if (docid == 0){
				  Log.d("des", "" + description);
			new Asynterms().execute();
			
			}
			else{
				Toast.makeText(getApplicationContext(), "Document Already Signed", Toast.LENGTH_SHORT).show();
				Intent backsearch = new Intent(RentalOrderListDetail.this,RentalOrderSearch.class);
				startActivity(backsearch);
			}
		}
		else if (v == back){
			onBackPressed();
		}
		else if (v == backtext){
			onBackPressed();
		}

	}  

	private class Asynterms extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {

			ProgressBar.showCommonProgressDialog(RentalOrderListDetail.this);
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				orderTerms = WebServiceConsumer.getInstance().rentalOrderTerms(
						user.getUserDescription());

			} catch (java.net.SocketTimeoutException e) {
				orderTerms = null;

			} catch (Exception e) {
				orderTerms = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();
			Log.d("orderTerms", "" + orderTerms);
			if (orderTerms != null && !(orderTerms.equals("")) ) {
			SessionData.getInstance().setTerms(orderTerms);

				if(SessionData.getInstance().getRentalChecklistPDFResult()==1){

					if(orderTerms.getMessage().contains("Session")){
						new AsyncLoginTask().execute();
					}
					else{
						dialog = new Dialog(RentalOrderListDetail.this);
						dialog.setCanceledOnTouchOutside(true);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.message);


						TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
						TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
						Message.setText(orderTerms.getMessage());

						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

//								Intent inspection = new Intent(RentalOrderListDetail.this,
//										MainActivity.class);
//								startActivity(inspection);
								dialog.dismiss();
							}
						});


						dialog.show();
					}

				}
				else{
					Intent intent = new Intent(RentalOrderListDetail.this,
							SubmitRentalOrder.class);

					startActivity(intent);
					finish();
				}


			}
			else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalOrderListDetail.this,
						"Data is not found.");
				
			}
		}
	}


	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(RentalOrderListDetail.this);
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
					dialog = new Dialog(RentalOrderListDetail.this);
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

							Intent inspection = new Intent(RentalOrderListDetail.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{

					new Asynterms().execute();

				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(RentalOrderListDetail.this,
						"Data is not found");
			}



		}
	}
	
	@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent intent = new Intent(RentalOrderListDetail.this,
				RentalOrderOrderlist.class);

		startActivity(intent);

	}
}
