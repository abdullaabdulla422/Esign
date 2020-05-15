package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CheckinDetail;
import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.Equipmentsubstatusdesc;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.TransportDetailsPDFObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import com.google.android.gms.common.api.GoogleApiClient;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class CheckInMailDetails extends AppCompatActivity implements OnClickListener {
    ListView list;
    TextView backtext;
    ImageView back;
    EditText editmail;
    Customeremails customeremails;
    String stremail;
    String email, emailPattern;
    static Dialog dialog;
    private int count;
    private int countdouble;
    ArrayList<String> aList;
    ArrayList<EquipmentCheckParts> partsList;

    ArrayList<CustomerNameMail> customerNameMails = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsSession = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsNew = new ArrayList<>();
    private Adaptor adap;
    ArrayList<CheckList> checkList;
    RentalCheck rentalcheckin;
    CheckinEqupments checkinequpments;
    CheckinDetail checkindetails;
    RentalChecklistPDF checklistpdf;
    ArrayList<String> cheklistArray;
    EqupmentSubStatus equpsubStatus;
    ArrayList<RentalDetails> selectedDetail;
    RentalDetails detail;
    EqupmentSubStatus equpsubStatusdata;
    Equipmentsubstatusdesc equpsubstatusdec;
    String rentalCheckinList;
    int getrentalcheck;
    User user;
    String str;
    String string;
    ArrayList<RentalDetails> detailList;
    ArrayList<RentalOrderListDetailObject> listDetailObjects;
    private TransportListObject transportObject;

    private double dpi;
    public static int i = 0;
    public static int j = 0;
    public static int z;

    ImageView imgAddMail;
    User objUser = null;

    TransportDetailsPDFObject transportDetailsPDFObject;

    ArrayAdapter<String> adapter;
    Button submit;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String session = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_mail);
        list = (ListView) findViewById(R.id.maillist);

        editmail = (EditText) findViewById(R.id.edtmail);
        backtext = (TextView) findViewById(R.id.backtext);
        back = (ImageView) findViewById(R.id.backicon);
        imgAddMail = (ImageView) findViewById(R.id.img_add_mail);
        imgAddMail.setOnClickListener(this);
        back.setOnClickListener(this);
        backtext.setOnClickListener(this);
        list.setTextFilterEnabled(true);

        submit = (Button) findViewById(R.id.btnsubmit);
        submit.setOnClickListener(this);
        selectedDetail = SessionData.getInstance().getSelectedDetail();
        cheklistArray = new ArrayList<String>();
//		customeremails = SessionData.getInstance().getCustomeremails();
//		// listDetailObjects = SessionData.getInstance().getOrderListDetail();
//		string = customeremails.getResult();
//		Log.d("mail data", "" + customeremails.getResult());
//		aList = new ArrayList<String>(Arrays.asList(string.split(",")));
//		adapter = new ArrayAdapter<String>(this, R.layout.simple_list_multi_choice, aList);
//		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//		list.setAdapter(adapter);


        customerNameMails = SessionData.getInstance().getCustomerNameMails();
        customerNameMailsSession.addAll(SessionData.getInstance().getCustomerNameMails());
        adap = new Adaptor(CheckInMailDetails.this, customerNameMails);

        list.setAdapter(adap);


        detailList = SessionData.getInstance().getDetail();

        // j = SessionData.getInstance().getChecklistdata();
        user = SessionData.getInstance().getUser();
        detail = detailList.get(0);
        count = detailList.size();

        cheklistArray = new ArrayList<String>(SessionData.getInstance().getCheckListData().values());
//        SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();


//        int itemCount = list.getCount();
//        if (count == 0) {
//            list.setVisibility(View.INVISIBLE);
//
//            for (int i = itemCount - 1; i >= 0; i--) {
//                if (checkedItemPositions.get(i)) {
//                    adapter.remove(aList.get(i));
//                }
//            }
//
//            checkedItemPositions.clear();
//            adapter.notifyDataSetChanged();
//        } else {
//            list.setVisibility(View.VISIBLE);
//        }



		/*if (count == 0) {
			list.setEmptyView(findViewById(android.R.id.empty));
//			list.setVisibility(View.INVISIBLE);
		}
		else{
			list.setVisibility(View.VISIBLE);
		}

*/

        // if (countdouble == 0) {
        // list.setVisibility(View.INVISIBLE);
        // }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


    }


    public class Adaptor extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<CustomerNameMail> list;

        public Adaptor(Context context, ArrayList<CustomerNameMail> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        public int getCount() {
            // this.notifyDataSetChanged();
            return list.size();
        }

        public Object getItem(int position) {
            // this.notifyDataSetChanged();

            return null;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.activity_mail_row, null);
                // holder.txtCustName = (TextView) convertView
                // .findViewById(R.id.txt_custname);
                holder.txt_customerName = (TextView) convertView
                        .findViewById(R.id.txt_cusomter_name);
                holder.txt_customerMail = (TextView) convertView
                        .findViewById(R.id.txt_customer_mail);

                holder.checkbox = (CheckBox) convertView
                        .findViewById(R.id.select_mail);

                holder.edt_customerName = (EditText) convertView.findViewById(R.id.edt_cusomter_name);
                holder.edt_customerMail = (EditText) convertView.findViewById(R.id.edt_customer_mail);
                holder.new_layout = (LinearLayout) convertView.findViewById(R.id.new_layout);
                holder.existing_layout = (LinearLayout) convertView.findViewById(R.id.existing_layout);
                holder.img_delete = (ImageView) convertView.findViewById(R.id.delete_mail);

                if (list.get(position).getIsAlreadyExit() == 1) {
                    holder.new_layout.setVisibility(View.GONE);
                    holder.existing_layout.setVisibility(View.VISIBLE);
                } else {
                    holder.new_layout.setVisibility(View.VISIBLE);
                    holder.existing_layout.setVisibility(View.GONE);
                }

                convertView.setTag(holder);
                // this.notifyDataSetChanged();
                holder.checkbox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        //CustomerNameMail detail = (CustomerNameMail) cb.getTag();
                        list.get(position).setSelected(cb.isChecked());
                        customerNameMails.get(position).setSelected(cb.isChecked());

                    }
                });
                holder.img_delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                holder.edt_customerMail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (position > customerNameMailsSession.size()) {
                            customerNameMailsNew.get(position - customerNameMailsSession.size()).setEmail(s.toString());
                        }
                        customerNameMails.get(position).setEmail(s.toString());
                        list.get(position).setEmail(s.toString());
                        //	adap.notifyDataSetChanged();
                    }
                });

                holder.edt_customerName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (position > customerNameMailsSession.size()) {
                            Log.i("customerNameMailsNew", "customerNameMailsNew.size()= "
                                    + customerNameMailsNew.size() + "positio= " + position
                                    + "customerNameMailsSession.size()=" + customerNameMailsSession.size());

                            customerNameMailsNew.get(position - customerNameMailsSession.size()).setCustname(s.toString());
                        }
                        customerNameMails.get(position).setCustname(s.toString());
                        list.get(position).setCustname(s.toString());
                        //adap.notifyDataSetChanged();
                    }
                });

                holder.img_delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (position > customerNameMailsSession.size()) {
                            customerNameMailsNew.remove(position - customerNameMailsSession.size());
                        }

                        customerNameMails.remove(position);

                        //list.remove(position);
                        adap.notifyDataSetChanged();
                    }
                });

                //	adap.notifyDataSetChanged();

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//			textRentalComName.setText(detailList.get(0).getCustName());
//			textRentalorderno.setText("" + detail.getOrderNo());

//			SessionData.getInstance().setRentalId(
//					detailList.get(0).getRentalID());


            holder.txt_customerName.setText(list.get(position).getCustname());
            holder.txt_customerMail.setText(list.get(position).getEmail());

            holder.edt_customerName.setText(list.get(position).getCustname());
            holder.edt_customerMail.setText(list.get(position).getEmail());

            if (list.get(position).isSelected()) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }

            // this.notifyDataSetChanged();
            return convertView;

        }

        public class MyListAdapter extends ArrayAdapter<Object> {
            private final boolean[] mCheckedState;
            private final Context mContext;

            public MyListAdapter(Context context, int resource,
                                 int textViewResourceId, List<Object> objects) {
                super(context, resource, textViewResourceId, objects);

                mCheckedState = new boolean[objects.size()];
                mContext = context;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                CheckBox result = (CheckBox) convertView;
                if (result == null) {
                    result = new CheckBox(mContext);
                }
                result.setChecked(mCheckedState[position]);
                return result;
            }
        }

        @Override
        public long getItemId(int position) {

            return position;

        }

    }

    class ViewHolder {
        TextView txt_customerName, txt_customerMail;
        EditText edt_customerName, edt_customerMail;
        LinearLayout existing_layout, new_layout;
        CheckBox checkbox;
        ImageView img_delete;

    }

    @Override
    public void onClick(View v) {


        if (v == submit) {

            /*
             * SparseBooleanArray checkedItemPositions =
             * getListView().getCheckedItemPositions(); int itemCount
             * =getListView().getCount(); for(int i=itemCount-1; i >= 0; i--){
             * if(checkedItemPositions.get(i)){ adapter.remove(list.get(i)); } }
             * checkedItemPositions.clear(); adapter.notifyDataSetChanged();
             */
//			SparseBooleanArray checked = list.getCheckedItemPositions();
//			ArrayList<String> selecteditems = new ArrayList<String>();
//			for (int i = 0; i < checked.size(); i++) {
//				int position = checked.keyAt(i);
//				if (checked.valueAt(i)) {
//					selecteditems.add(adapter.getItem(position));
//
//				}
//			}
//			adapter.notifyDataSetChanged();
//			String[] outputStrArr = new String[selecteditems.size()];
//			for (int i = 0; i < selecteditems.size(); i++) {
//				outputStrArr[i] = selecteditems.get(i);
//
//			}
//
//			stremail = selecteditems.toString();
//			stremail = stremail.replace("[", "");
//			stremail = stremail.replace("]", "");
//			email = editmail.getText().toString().trim();
//			email = email.replace(" ", "");
//			String[] test = email.split(",");
//			boolean emailflag = true;
//			for (int i = 0; i < test.length; i++) {
//
//				if (validEmail(test[i])) {
//					emailflag = true;
//				} else {
//					emailflag = false;
//					break;
//				}
//
//			}
//			if (editmail.getText().toString().length() == 0
//					&& stremail.isEmpty()) {
//				Toast.makeText(getApplicationContext(),
//						"Select or Enter the Mail", Toast.LENGTH_LONG).show();
//			} else {
//				if (editmail.getText().toString().length() != 0
//						&& emailflag == false) {
//					Toast.makeText(getApplicationContext(), "Invalid Mail ID",
//							Toast.LENGTH_LONG).show();
//				} else {

//            new AysncSubmitData().execute();
            // Intent in = new Intent(CheckInMailDetails.this,
            // Summaryactivity.class);
            // startActivity(in);

//				}
//			}

            updateEmailsAndSubmit();


        } else if (v == back) {
            onBackPressed();
        } else if (v == backtext) {
            onBackPressed();
        } else if (v == imgAddMail) {
            if (customerNameMails.size() > 0) {
                if (customerNameMails.get(customerNameMails.size() - 1).getEmail().length() != 0) {

                    boolean emailflag = true;


                    if (validEmail(customerNameMails.get(customerNameMails.size() - 1).getEmail())) {
                        emailflag = true;
                    } else {
                        emailflag = false;

                    }

                    if (emailflag) {
                        CustomerNameMail customerNameMail = new CustomerNameMail();
                        customerNameMail.setIsAlreadyExit(0);
                        customerNameMail.setMessage("");
                        customerNameMail.setEmail("");
                        customerNameMail.setCustname("");
                        customerNameMail.setSelected(false);
                        customerNameMails.add(customerNameMail);
                        customerNameMailsNew.add(customerNameMail);

                        adap = new Adaptor(CheckInMailDetails.this, customerNameMails);
                        list.setAdapter(adap);
                        adap.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CheckInMailDetails.this, "Enter the Vaild Mail", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(CheckInMailDetails.this, "Enter the Mail", Toast.LENGTH_LONG).show();
                }
            } else {
                CustomerNameMail customerNameMail = new CustomerNameMail();
                customerNameMail.setIsAlreadyExit(0);
                customerNameMail.setMessage("");
                customerNameMail.setEmail("");
                customerNameMail.setCustname("");
                customerNameMail.setSelected(false);
                customerNameMails.add(customerNameMail);
                customerNameMailsNew.add(customerNameMail);
                adap = new Adaptor(CheckInMailDetails.this, customerNameMails);
                list.setAdapter(adap);
                adap.notifyDataSetChanged();
            }

        }

    }


    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                selectedDetail = WebServiceConsumer.getInstance()
                        .getRentalDetail(
                                SessionData.getInstance().getEnteredEquipID(),
                                user.getUserDescription());
            } catch (SocketTimeoutException e) {
                selectedDetail = null;
            } catch (Exception e) {
                selectedDetail = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // if (selectedDetail != null && selectedDetail.size() > 0) {

            if (selectedDetail.get(0).getMessage().equals("")) {

                SessionData.getInstance().getSelectedDetail().clear();
                SessionData.getInstance().getCheckListData().clear();
                SessionData.getInstance().getDataHandlelist().clear();
                SessionData.getInstance().getKpartlist().clear();
                SessionData.getInstance().getHourmeterlist().clear();
                SessionData.getInstance().getEqpStatus().clear();
                SessionData.getInstance().setDetail(selectedDetail);
                // checkList.clear();
                SessionData.getInstance().setEnteredEquipID(
                        SessionData.getInstance().getEnteredEquipID());
                Intent intent = new Intent(CheckInMailDetails.this,
                        RentalListSelector.class);
                startActivity(intent);
            } else {
                dialog = new Dialog(CheckInMailDetails.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                Message.setText(selectedDetail.get(0).getMessage());

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent inspection = new Intent(CheckInMailDetails.this,
                                MainActivity.class);
                        startActivity(inspection);
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
            // finish();

            // }
        }
    }

    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {

        String resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(CheckInMailDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Log.d("The Session data Size", ""
                        + SessionData.getInstance().getDataHandlelist().size());
                Logger.log("The Session data Size" + ""
                        + SessionData.getInstance().getDataHandlelist().size());

                Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
                        .entrySet();

                Iterator<?> it = entrySet.iterator();
                Log.d("The size of IT", ""
                        + SessionData.getInstance().getDataHandlelist()
                        .entrySet().size());

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                if (SessionData.getInstance().getTransportTransfer() == 1) {

                    rentalcheckin = WebServiceConsumer.getInstance().RentalCheckinV3(
                            SessionData.getInstance().getRentalId(), 0,
                            SessionData.getInstance().getInspectionID(),
                            user.getUserDescription(),
                            SessionData.getInstance().getRentalIn_Out(), SessionData.getInstance().getTransportOrderNum(), SessionData.getInstance().getTransportcallNum());
                } else {
                    rentalcheckin = WebServiceConsumer.getInstance().RentalCheckinV3(
                            SessionData.getInstance().getRentalId(), 0,
                            SessionData.getInstance().getInspectionID(),
                            user.getUserDescription(),
                            SessionData.getInstance().getRentalIn_Out(), SessionData.getInstance().getKord_num(), 0);
                }

                if (SessionData.getInstance().getRentalCheckinResult() == 1) {


                    if (rentalcheckin.getMessage().contains("Session")) {
                        session = "AsyncUpdateContactEmails";
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(CheckInMailDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(rentalcheckin.getMessage());

                        yes.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

//							Intent inspection = new Intent(CheckInMailDetails.this,
//									MainActivity.class);
//							startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                } else {

                    getrentalcheck = Integer.parseInt(rentalcheckin.getResult());
                    if (SessionData.getInstance().getSig() != null) {
                        str = Base64.encodeToString(SessionData.getInstance()
                                .getSig().clone(), Base64.DEFAULT);
                    }
                    checkinequpments = WebServiceConsumer
                            .getInstance()
                            .rentalCheckinEqupmentsWalkAround(
                                    Integer.parseInt(rentalcheckin.getResult()),
                                    SessionData.getInstance().getSelectedDetail()
                                            .get(j).getkPart(),
                                    Integer.parseInt(SessionData.getInstance()
                                            .getHrmeter()),
                                    SessionData.getInstance().getFuel(),
                                    Integer.toString(SessionData.getInstance()
                                            .getEquipinspectionID()),
                                    user.getUserDescription(),
                                    SessionData.getInstance().getSubstatus(),
                                    SessionData.getInstance().getEqupsubstatusdes(),
                                    SessionData.getInstance().getCurrentstatus(),
                                    str, email, stremail, 1, SessionData.getInstance().getRentalIn_Out()
                                    , SessionData.getInstance().getRentalSigneeName());
                    Log.d("Check type", "" + SessionData.getInstance().getRentalIn_Out());
                    // Log.d("Equpstatus", "" + statusselect);
                    Log.d("result data", "" + rentalcheckin.getResult());
                    // int rentalCheckinDetail;
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();

                        checkindetails = WebServiceConsumer.getInstance()
                                .rentalCheckinDetl(
                                        Integer.parseInt(checkinequpments
                                                .getResult()),
                                        (int) me.getKey(),
                                        ((CheckinListData) me.getValue())
                                                .getStatus(),

                                        ((CheckinListData) me.getValue())
                                                .getNotes(), 0,
                                        user.getUserDescription());
                        Log.d("Description for webservice", ""
                                + ((CheckinListData) me.getValue()).getNotes());

                        if ((((CheckinListData) me.getValue()).getImagePath()
                                .length() > 4)) {
                            resultdata = WebServiceConsumer.getInstance()
                                    .RentalChickinImagesV2(
                                            Integer.parseInt(checkindetails
                                                    .getResult()),
                                            ((CheckinListData) me.getValue())
                                                    .getImageName(),
                                            "",
                                            0,
                                            ((CheckinListData) me.getValue())
                                                    .getImagePath(),
                                            0,
                                            user.getUserDescription(),
                                            SessionData.getInstance()
                                                    .getKpartlist().get(j), SessionData.getInstance().getRentalIn_Out());
                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                        }

                    }
                    if (SessionData.getInstance().getTransportTransfer() == 1) {
                        checklistpdf = WebServiceConsumer.getInstance()
                                .RentalChecklistPdfV3(
                                        user.getUserDescription(),
                                        SessionData.getInstance().getSelectedDetail()
                                                .get(j).getkPart(),
                                        SessionData.getInstance().getSelectedDetail()
                                                .get(j).getEqupId(),
                                        Integer.toString(SessionData.getInstance()
                                                .getSelectedDetail().get(j)
                                                .getRentalID()), str, stremail, email,
                                        SessionData.getInstance().getRentaldetlid(),
                                        Integer.parseInt(rentalcheckin.getResult()), SessionData.getInstance().getRentalIn_Out()
                                        , SessionData.getInstance().getRentalSigneeName());
                    } else {
                        checklistpdf = WebServiceConsumer.getInstance()
                                .RentalChecklistPdfV2(
                                        user.getUserDescription(),
                                        SessionData.getInstance().getSelectedDetail()
                                                .get(j).getkPart(),
                                        SessionData.getInstance().getSelectedDetail()
                                                .get(j).getEqupId(),
                                        Integer.toString(SessionData.getInstance()
                                                .getSelectedDetail().get(j)
                                                .getRentalID()), str, stremail, email,
                                        SessionData.getInstance().getRentaldetlid(),
                                        Integer.parseInt(rentalcheckin.getResult()), SessionData.getInstance().getRentalIn_Out()
                                        , SessionData.getInstance().getRentalSigneeName());
                    }


//					transportDetailsPDFObject = WebServiceConsumer.getInstance()
//							.TransportDetailsPDF(user.getUserDescription(),transportObject.getKequipnum(),
//									0,SessionData.getInstance().getRentalId(),str,stremail,email,
//									SessionData.getInstance().getRentalId(),
//									Integer.parseInt(rentalcheckin.getResult())
//									,"",SessionData.getInstance().getRentalSigneeName(),
//									Integer.parseInt(transportObject.getKordnum()), transportObject.getKcustnum(),
//									transportObject.getKbranch(),"R",transportObject.getCustsnum(),
//									"","","");
                    Log.d("rentaldetailid", ""
                            + SessionData.getInstance().getRentaldetlid());

                    if (SessionData.getInstance().getTransportTransfer() == 1) {
                        transportObject = SessionData.getInstance().getTransportObject();
                        transportDetailsPDFObject = WebServiceConsumer.getInstance().UpdateTransportDeliveryDet(user.getUserDescription(), Integer.parseInt(transportObject.getTransport_id()), transportObject.getOejobnum(), Integer.parseInt(transportObject.getKordnum()));
                    }
                }

            } catch (SocketTimeoutException e) {
                resultdata = null;

            } catch (Exception e) {
                resultdata = null;
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ProgressBar.dismiss();
            // linearMainn.removeAllViews();
            SessionData.getInstance().getDataHandlelist().clear();
            if (j < cheklistArray.size() - 1) {

                SessionData.getInstance().setChecklist(0);
                SessionData.getInstance().setChecklistdata(j + 1);
                SessionData.getInstance().setMulticheckinvalidate(0);
                SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance().getKpartlist().get(j + 1));
//				SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance()
//						.getSelectedDetail().get(j + 1).getkPart());

                j++;
                SessionData.getInstance().getWalkAroundNotes().clear();
                SessionData.getInstance().getWalkaroundgeneralimages().clear();
                SessionData.getInstance().getWalkAroundType().clear();
                SessionData.getInstance().getWalkAroundCategoryId().clear();
                Intent hr = new Intent(CheckInMailDetails.this,
                        RentalInspectionWalkAround.class);
                startActivity(hr);
                startActivity(hr);
                // checkList = ReadJson.getPartsList(cheklistArray.get(j));
                // SessionData.getInstance().setInspectionID(getrentalcheck);
                //
                // i = 0;
                // initializeViews(checkList);
                // Log.d("I IS PARSING 1", "" + checkList);
                SessionData.getInstance().setInspectionID(getrentalcheck);

            } else {


                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        ProgressBar.showCommonProgressDialog(CheckInMailDetails.this);
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            selectedDetail = WebServiceConsumer.getInstance().getRentalDetail(
                                    SessionData.getInstance().getEnteredEquipID(), user.getUserDescription());
                            SessionData.getInstance().setDetail(selectedDetail);
                            Log.d("I IS PARSING 2", "" + checkList);
                        } catch (SocketTimeoutException e) {
                            selectedDetail = null;
                        } catch (Exception e) {
                            selectedDetail = null;
                            e.printStackTrace();
                        }
                        return null;

                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (selectedDetail.get(0).getMessage().equals("")) {
                            selectedDetail.clear();
                        } else {

                            dialog = new Dialog(CheckInMailDetails.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(selectedDetail.get(0).getMessage());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent inspection = new Intent(CheckInMailDetails.this,
                                            MainActivity.class);
                                    startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    }

                };

                try {
                    selectedDetail = WebServiceConsumer.getInstance()
                            .getRentalDetail(
                                    SessionData.getInstance()
                                            .getEnteredEquipID(),
                                    SessionData.getInstance().getTemp_userToken());
                    SessionData.getInstance().setDetail(selectedDetail);
                } catch (SocketTimeoutException e) {
                    selectedDetail = null;
                } catch (Exception e) {
                    selectedDetail = null;
                    e.printStackTrace();
                }
                i = 0;
                j = 0;

                SessionData.getInstance().getSelectedDetail().clear();
                SessionData.getInstance().getCheckListData().clear();
                SessionData.getInstance().getDataHandlelist().clear();
                SessionData.getInstance().getKpartlist().clear();
                SessionData.getInstance().getHourmeterlist().clear();
                SessionData.getInstance().getEqpStatus().clear();
                // checkList.clear();

                /* SessionData.getInstance().getGetKey().clear(); */

                Toast.makeText(CheckInMailDetails.this,
                        "Inspection Completed Successfully", Toast.LENGTH_SHORT).show();

                // finish();
                ProgressBar.dismiss();
                if (SessionData.getInstance().getTransportTransfer() == 1) {

                    Intent inspection = new Intent(CheckInMailDetails.this,
                            EbsMenu.class);
                    startActivity(inspection);
                } else {
                    new AsyncRentalDetail().execute();

                }

            }

            // Intent in = new
            // Intent(RentalSignature.this,ScannerActivity.class);
            // startActivity(in);

        }
    }

    public class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(CheckInMailDetails.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getTemp_Username(),
                                SessionData.getInstance().getTemp_password());
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
            if (user != null && user.getUsername() != null) {

                ProgressBar.dismiss();
                SessionData.getInstance().setUser(user);
//				SessionData.getInstance().setUsername(objUser.getUsername());
//
//				SessionData.getInstance().setTemp_Username(objUser.getUsername());
//				SessionData.getInstance().setTemp_password(objUser.getPassword());
//				SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());

                if (session == "AysncSubmitData") {
                    new AysncSubmitData().execute();
                } else if (session == "AsyncUpdateContactEmails") {
                    new AsyncUpdateContactEmails().execute(customerNameMailsNew);
                }

            } else {
                ProgressBar.dismiss();

            }
        }
    }


    public boolean validEmail(String email) {

        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    }

    public class AsyncUpdateContactEmails extends AsyncTask<ArrayList<CustomerNameMail>, Void, Void> {
        String result = "";

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(CheckInMailDetails.this);
        }

        ;

        @Override
        protected Void doInBackground(ArrayList<CustomerNameMail>... params) {
            try {

                for (int k = 0; k < params[0].size(); k++) {
                    result = WebServiceConsumer.getInstance()
                            .updateContactEmails(SessionData.getInstance().getTemp_userToken(),
                                    SessionData.getInstance().getStrKcustnum(),
                                    SessionData.getInstance().getStrCustnum(),
                                    SessionData.getInstance().getStrSigntype(),
                                    params[0].get(k).getCustname(),
                                    params[0].get(k).getEmail());

                    Log.i("updateContactEmails", "name: " + params[0].get(k).getCustname() + "; email: " + params[0].get(k).getEmail() + "; item: " + k + "; result" + result.toString());
                }

            } catch (java.net.SocketTimeoutException e) {
                result = null;
                e.printStackTrace();
            } catch (Exception e) {
                result = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            ProgressBar.dismiss();

            if (result != null) {
                if (result.contains("Session")) {
                    session = "AsyncUpdateContactEmails";
                    new AsyncLoginTask().execute();
                } else {
                    ProgressBar.dismiss();

//                if (mailUpdateCount == customerNameMails.size())
                    new AysncSubmitData().execute();
                }
            } else {
                ProgressBar.dismiss();

                //                if (mailUpdateCount == customerNameMails.size())
                new AysncSubmitData().execute();
            }
        }
    }

    public String getEmailSting(ArrayList<CustomerNameMail> customerNameMails) {
        String strEmail = "";

        for (int i = 0; i < customerNameMails.size(); i++) {

            if (strEmail == "") {
                strEmail = strEmail + customerNameMails.get(i).getEmail();
            } else {
                strEmail = strEmail + "," + customerNameMails.get(i).getEmail();
            }
        }

        return strEmail;
    }


    public String getStrEmailSting(ArrayList<CustomerNameMail> customerNameMails) {
        String strEmail = "";

        for (int i = 0; i < customerNameMails.size(); i++) {
            if (customerNameMails.get(i).isSelected()) {
                if (strEmail == "") {
                    strEmail = strEmail + customerNameMails.get(i).getEmail();
                } else {
                    strEmail = strEmail + "," + customerNameMails.get(i).getEmail();
                }
            }
        }

        return strEmail;
    }

    public void updateEmailsAndSubmit() {

        if (customerNameMailsNew.size() > 0) {
            if (validEmail(customerNameMailsNew.get(customerNameMailsNew.size() - 1).getEmail())) {
                stremail = getStrEmailSting(customerNameMails);
                email = getEmailSting(customerNameMailsNew);

                Log.i("getStrEmailSting", "stremail: " + stremail);
                Log.i("getEmailSting", "email: " + email);

                new AsyncUpdateContactEmails().execute(customerNameMailsNew);
            } else {

                Toast.makeText(this, "Enter a valid mail", Toast.LENGTH_SHORT).show();

            }
        } else {
            stremail = getStrEmailSting(customerNameMails);
            email = getEmailSting(customerNameMailsNew);

            Log.i("getStrEmailSting", "stremail: " + stremail);
            Log.i("getEmailSting", "email: " + email);

            new AsyncUpdateContactEmails().execute(customerNameMailsNew);
        }
    }
}
