package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalListSelectorObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class RentalListSelector extends AppCompatActivity {
    private ListView listview;
    private static Dialog dialog;
    ArrayList<String> items = new ArrayList<String>();
    private ArrayList<RentalDetails> detailList;
    private TextView txtCompanyname;
    private TextView txtContact;
    private TextView txtDate;
    private TextView txtAddress1;
    private TextView txtAddress2; /*
     * txtBranch
     */
    private TextView txtRentalID;
    private TextView txtLocation;
    private TextView txtRep;
    private TextView txtPhone;
    private TextView textRentalComName;
    private TextView textRentalorderno;
    private RentalDetails detail;
    private User user;
    private String currentDate;
    String currentTime;
    private RentalListSelectorObject rentalCheckinList;
    EqupmentSubStatus equSubStatus;
    private int count;
    private boolean[] thumbnailsselection;
    private ImageAdapter adap;
    User objUser = null;
    Cursor cursor;
    private ImageView back;
    private Button check;
    private String strlogout;

    private ArrayList<byte[]> attachedFilesData;
    private ArrayList<byte[]> AddtoSession = new ArrayList<>();

    private ArrayList<String> arrayNotes = new ArrayList<>();
    private ArrayList<String> arrayType = new ArrayList<>();
    private ArrayList<String> CategoryID = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rental_list_selector);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(back1);
        check = (Button) findViewById(R.id.btncheck);

        if (SessionData.getInstance().getRentalIn_Out() == "I") {
            check.setText("Check In");
        } else {
            check.setText("Check Out");
        }

        SessionData.getInstance().getSelectedDetail().clear();

//		SessionData.getInstance().getSelectedDetail().clear();
        SessionData.getInstance().getCheckListData().clear();
        SessionData.getInstance().getDataHandlelist().clear();
        SessionData.getInstance().getKpartlist().clear();
        SessionData.getInstance().getHourmeterlist().clear();
        SessionData.getInstance().getEqpStatus().clear();

        SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
        SessionData.getInstance().setWalkAroundNotes(arrayNotes);
        SessionData.getInstance().setWalkAroundType(arrayType);
        SessionData.getInstance().setWalkAroundCategoryId(CategoryID);
        SessionData.getInstance().setPrevioushours("0");
        attachedFilesData = new ArrayList<>();

        SessionData.getInstance().setWalkaroundgeneralimages(attachedFilesData);


        detailList = SessionData.getInstance().getDetail();
        count = detailList.size();
        SessionData.getInstance().setDecode(null);
        detail = detailList.get(0);
        SessionData.getInstance().setInspectionID(detail.getInspectionId());
        user = SessionData.getInstance().getUser();
        initilizeViews();
        setValuesToViews();
        SessionData.getInstance().setChecklist(1);
//SessionData.getInstance().setChecklistdata(0);
        currentDate = java.text.DateFormat.getDateInstance().format(new Date());

        textRentalComName = (TextView) findViewById(R.id.rentalcompanyname);
        textRentalorderno = (TextView) findViewById(R.id.orderno);

        thumbnailsselection = new boolean[count];

        listview = (ListView) findViewById(R.id.listView1);
        adap = new ImageAdapter(RentalListSelector.this, detailList);
        listview.setAdapter(adap);
        listview.clearChoices();

        adap.notifyDataSetChanged();

    }

    private final View.OnClickListener back1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//			onBackPressed();
//			finish();
            Intent intent = new Intent(RentalListSelector.this, EbsMenu.class);
            startActivity(intent);
            RentalListSelector.super.onBackPressed();


        }
    };

    private void initilizeViews() {
        txtCompanyname = (TextView) findViewById(R.id.txt_companyname);
        txtContact = (TextView) findViewById(R.id.txt_contact);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtAddress1 = (TextView) findViewById(R.id.txt_address1);
        txtAddress2 = (TextView) findViewById(R.id.txt_address2);

        /* txtBranch = (TextView) findViewById(R.id.txt_branch); */
        txtRentalID = (TextView) findViewById(R.id.txt_rental_id);
        txtLocation = (TextView) findViewById(R.id.txt_location);
        txtRep = (TextView) findViewById(R.id.txt_sales_man);
        txtPhone = (TextView) findViewById(R.id.txt_phone);

    }

    @SuppressLint("SetTextI18n")
    private void setValuesToViews() {
        txtCompanyname.setText(detail.getCustName());
        SessionData.getInstance().setCustname(detail.getCustName());
        txtContact.setText(detail.getContact());
        SessionData.getInstance().setContactsummary(detail.getContact());
        txtDate.setText(formattedDateFromString(detail.getOrderDate()));
        SessionData.getInstance().setKord_num(detail.getOrderNo());
//		txtDate.setText(formattedDateFromString(detail.getOrderDate()));
        SessionData.getInstance().setDatasummary(formattedDateFromString(detail.getOrderDate()));
        txtAddress1.setText(detail.getAddress1());
        txtAddress2.setText(detail.getAddress2());

        /* txtBranch.setText(detail.getBranch()); */
        txtRentalID.setText("" + detail.getOrderNo());
        Log.d("orrr", "" + detail.getOrderNo());
        SessionData.getInstance().setOrder(detail.getOrderNo());

        txtLocation.setText(detail.getCity() + ",  " + detail.getState()
                + ",  " + detail.getZip());
        txtRep.setText(detail.getSalesRep());
        txtPhone.setText(detail.getPhoneNo());
    }

    private static String formattedDateFromString(String input) {
        try {
            String inputTimeStamp = input;

            final String inputFormat = "MM/dd/yyyy HH:mm:ss a";
            final String outputFormat = "MM/dd/yyyy";

            String output = (TimeStampConverter(inputFormat, inputTimeStamp,
                    outputFormat));
            return output;
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    private static String TimeStampConverter(final String inputFormat,
                                             String inputTimeStamp, final String outputFormat)

            throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(
                inputFormat).parse(inputTimeStamp));
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<RentalDetails> list;

        public ImageAdapter(Context context, ArrayList<RentalDetails> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        public int getCount() {
            // this.notifyDataSetChanged();
            return count;
        }

        public Object getItem(int position) {
            // this.notifyDataSetChanged();

            return null;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.rental_list_view, null);
                // holder.txtCustName = (TextView) convertView
                // .findViewById(R.id.txt_custname);
                holder.txtEquipmentKprt = (TextView) convertView
                        .findViewById(R.id.txt_kpart);
                holder.txtEquipmentdesc = (TextView) convertView
                        .findViewById(R.id.txt_equipment_name);

                holder.checkbox = (CheckBox) convertView
                        .findViewById(R.id.itemCheckBox);

                convertView.setTag(holder);
                // this.notifyDataSetChanged();
                holder.checkbox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        RentalDetails detail = (RentalDetails) cb.getTag();
                        detail.setSelected(cb.isChecked());

                    }
                });

                adap.notifyDataSetChanged();

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            textRentalComName.setText(detailList.get(0).getCustName());
            textRentalorderno.setText("" + detail.getOrderNo());

            SessionData.getInstance().setRentalId(
                    detailList.get(0).getRentalID());

            holder.txtEquipmentKprt
                    .setText(detailList.get(position).getkPart());
            holder.txtEquipmentdesc.setText(detailList.get(position).getDesc());
            if (detailList.get(position).getEquipmentInspectionId() == 0) {
                holder.checkbox.setEnabled(true);
            } else {
                holder.checkbox.setEnabled(false);

            }

            holder.checkbox.setChecked(detailList.get(position).isSelected());
            holder.checkbox.setTag(detailList.get(position));

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
        TextView txtEquipmentKprt, txtEquipmentdesc;
        CheckBox checkbox;

    }

    public void click(View v) {
        if (v.getId() == R.id.btncheck) {
            StringBuffer responseText = new StringBuffer();
            responseText.append("The following were selected...\n");

            ArrayList<RentalDetails> rental = adap.list;
            SessionData.getInstance().setLabelcondition(0);
            // adap.notifyDataSetChanged();
            for (int i = 0; i < rental.size(); i++) {
                RentalDetails detail = detailList.get(i);
                if (detail.isSelected()) {

                    responseText.append("\n" + detail.getkPart());
                    SessionData.getInstance().getSelectedDetail().add(detail);
                }

            }
            adap.notifyDataSetChanged();

            int TAG_CHECKLIST = 0;

            if (SessionData.getInstance().getSelectedDetail().size() <= 0) {
                Log.d("The List Is MT", "");
                for (int i = 0; i < detailList.size(); i++) {
                    if (detailList.get(i).getEquipmentInspectionId() != 0) {
                        TAG_CHECKLIST = 0;
                    } else {
                        TAG_CHECKLIST = 1;
                        break;
                    }
                }
                if (TAG_CHECKLIST == 1) {
                    Toast.makeText(this, "Please Select the Item",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new Dialog(RentalListSelector.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText("All Equipment has been Checked for this Order");

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();

                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }


            } else {
                Log.d("Selected rental list", ""
                        + SessionData.getInstance().getSelectedDetail().size());

                new AsyncRentalDetail().execute();

            }
        }
    }

    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        LinkedHashMap<RentalDetails, String> data;

        protected void onPreExecute() {
            data = new LinkedHashMap<>();
            ProgressBar.showCommonProgressDialog(RentalListSelector.this);
        }

        ;

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... params) {

            try {
                for (int i = 0; i < SessionData.getInstance()
                        .getSelectedDetail().size(); i++) {

                    RentalDetails rentDetail = SessionData.getInstance()
                            .getSelectedDetail().get(i);
//					to create new user token
//					objUser = WebServiceConsumer.getInstance()
//							.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//									SessionData.getInstance().getLogin_password());
//
                    rentalCheckinList = WebServiceConsumer.getInstance()
                            .RentalCheckinList("", rentDetail.getkPart(),
                                    rentDetail.getEqupId(),
                                    rentDetail.getRentalID(),
                                    user.getUserDescription());

                    if (SessionData.getInstance().getRentalListSelector() == 0) {
                        Log.d("rental list selector result", "" + rentalCheckinList.getResult());

                        data.put(rentDetail, rentalCheckinList.getResult());


                        SessionData.getInstance().getKpartlist()
                                .add(rentDetail.getkPart());
                        SessionData.getInstance().getEqpStatus()
                                .add(rentDetail.getEqpStatus());
                        SessionData.getInstance().getHourmeterlist()
                                .add(rentDetail.getHourMeter());
                        SessionData.getInstance().getPmspeclist()
                                .add(rentDetail.getPmSpec());
                        SessionData.getInstance().getDuestatuslist()
                                .add(rentDetail.getDueStatus());
                        SessionData.getInstance().getEqupmentmeterlist()
                                .add(rentDetail.getEquipmentMeter());
                        SessionData.getInstance().getEqupmentreadinglist()
                                .add(rentDetail.getEquipmentReading());
                        SessionData.getInstance().getLastdatelist()
                                .add(rentDetail.getLastDate());
                        SessionData.getInstance().getLasthourlist()
                                .add(rentDetail.getLastHours());
                    }

                }

            } catch (java.net.SocketTimeoutException e) {
                rentalCheckinList = null;

            } catch (Exception e) {
                rentalCheckinList = null;

                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("rentalCheckinList", "" + rentalCheckinList);
            ProgressBar.dismiss();
            if (SessionData.getInstance().getRentalListSelector() == 0) {
                if (rentalCheckinList.getResult().contains("{\"Checklist\":[{}]}")) {
                    dialog = new Dialog(RentalListSelector.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText("There is no Checklist defined");

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//							new RentalListSelector.AsyncLogoutTask().execute();
//                            Intent inspection = new Intent(RentalListSelector.this,
//                                    ScannerProductActivity.class);
//                            startActivity(inspection);
                            if (SessionData.getInstance().getRefer().contains("False")) {


                                Intent inspection = new Intent(RentalListSelector.this, EbsMenu.class);
                                startActivity(inspection);

                            } else {
                                Intent inspection = new Intent(RentalListSelector.this, EbsMenu.class);
                                startActivity(inspection);
                            }
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setChecklist(20);
                    SessionData.getInstance().setMulticheckinvalidate(1);

                    SessionData.getInstance().getCheckListData().putAll(data);

                    SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance().getKpartlist().get(0));
                    RentalDetails rentDetail = SessionData.getInstance()
                            .getSelectedDetail().get(0);
                    SessionData.getInstance().setEnteredEquipID(rentDetail.getkPart());
                    Log.d("Selected Equipment", "" + SessionData.getInstance().getKpartlist().get(0));

                    Intent intent = new Intent(RentalListSelector.this,
                            RentalInspectionWalkAround.class);

                    startActivity(intent);
                    finish();
                }
            } else {

                if (rentalCheckinList.getMessage().contains("Session")) {
                    new AsyncSessionLoginTask().execute();
                } else {
                    dialog = new Dialog(RentalListSelector.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText(rentalCheckinList.getMessage());

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//							Intent inspection = new Intent(RentalListSelector.this,
//									MainActivity.class);
//							startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }

            }

        }

    }


    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalListSelector.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());


                Log.d("Session Expiered", "Session Expiered");
                Log.d("New User Token", "" + SessionData.getInstance().getTemp_userToken());
                Log.d("After Session Expired", "Equip_ID" + SessionData.getInstance().getTemp_equipId());
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
            if (user != null) {

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(RentalListSelector.this);
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

                            Intent inspection = new Intent(RentalListSelector.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);
                    new AsyncRentalDetail().execute();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalListSelector.this,
                        "Data is not Found");
            }


        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//		if(SessionData.getInstance().getScanNavigation()==0){
//			Intent intent = new Intent(RentalListSelector.this,
//					ScannerActivity.class);
//
//			startActivity(intent);
//		}else{
//			Intent intent = new Intent(RentalListSelector.this,
//					ScannerProductActivity.class);
//
//			startActivity(intent);
//		}


    }

    private class AsyncLogoutTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalListSelector.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                strlogout = WebServiceConsumer.getInstance().RemoveActiveUser(
                        user.getUserDescription(),
                        user.getUserId());

            } catch (Exception e) {
                strlogout = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (strlogout != null) {
                Intent intent = new Intent(RentalListSelector.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
