package com.ebs.rental.general;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.RentalOrderCustomerList;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.User;
import com.ebs.rental.uidesigns.SpinnerDialog;
import com.ebs.rental.uidesigns.SpinnerInterface;
import com.ebs.rental.uidesigns.Spinnerview;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RentalOrderSearch extends AppCompatActivity implements OnClickListener {
    private EditText orderno;
    private Button search;
    private Button clear;
    Button dealer;
    private static Dialog dialog;
    private ArrayList<RentalOrderCustomerList> orderCustlist;
    private ArrayList<RentalOrderList> orderLists;
    private ArrayList<RentalOrderList> equipmentlist;
    private User user;
    private TextView backtext;
    private ImageView back;
    private RadioButton radioequipID;
    private RadioButton radioorder;
    private RadioButton radiocustname;
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private Class<?> mClss;
    int editEqupID = 0;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private int session = 0;
    private String EquipEditable;

    private Spinnerview sprSearchBy;

    private ImageView imgEquipScan;
    private Button btnSearch;
    private TextView txtOrderno;
    private int select = 0;

    ArrayList<String> searchBy = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.rental_order_search);

        orderno = (EditText) findViewById(R.id.edt_orderno);
        //	custname = (EditText) findViewById(R.id.edt_custname);
        search = (Button) findViewById(R.id.btn_search);

        clear = (Button) findViewById(R.id.btn_clear);
        radioequipID = (RadioButton) findViewById(R.id.radio_equip);
        radioorder = (RadioButton) findViewById(R.id.radio_order);
        radiocustname = (RadioButton) findViewById(R.id.radio_customer);


        sprSearchBy = (Spinnerview) findViewById(R.id.img_order_search);
        imgEquipScan = (ImageView) findViewById(R.id.img_eqipment_scan);
        btnSearch = (Button) findViewById(R.id.btn_order_search);
        txtOrderno = (TextView) findViewById(R.id.txt_orderno);

        int maxLength = 12;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        orderno.setFilters(fArray);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String strorderno = bundle.getString("value");
            orderno.setText(strorderno);
        }

        user = SessionData.getInstance().getUser();

        backtext = (TextView) findViewById(R.id.backtext);
        back = (ImageView) findViewById(R.id.backicon);
        back.setOnClickListener(this);
        backtext.setOnClickListener(this);
        clear.setOnClickListener(this);
        imgEquipScan.setOnClickListener(this);
        search.setOnClickListener(this);
        radioequipID.setOnClickListener(this);
        radioorder.setOnClickListener(this);
        radiocustname.setOnClickListener(this);

        sprSearchBy.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        searchBy.clear();
        searchBy.add("Equipment ID");
        searchBy.add("Order #");
        searchBy.add("Customer");

        sprSearchBy.setTitle(searchBy.get(0));

    }

    @Override
    public void onClick(View v) {

        if (v == search) {
            if (orderno.getText().length() != 0) {
                if (radioorder.isChecked()) {

                    editEqupID = 1;

                    new AsyncEquipmentEditableTask().execute();
//				new AsycOrderList().execute();
                    int filterlength = Integer.parseInt("12");


                } else if (radiocustname.isChecked()) {

                    editEqupID = 0;

                    new AsyncEquipmentEditableTask().execute();


                    int maxLength = 50;


                } else if (radioequipID.isChecked()) {

                    new AsyncEquiplist().execute();
                    orderno.setInputType(InputType.TYPE_CLASS_TEXT);
                    SessionData.getInstance().setEquipenable(1);


                }

            } else {

                Toast.makeText(getApplicationContext(), "Enter the field",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (v == radioequipID) {
            clear.setVisibility(View.VISIBLE);
            orderno.setText("");
            orderno.setInputType(InputType.TYPE_CLASS_TEXT);
            int maxLength = 12;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            orderno.setFilters(fArray);
        }
        if (v == radiocustname) {
            clear.setVisibility(View.GONE);
            orderno.setText("");
            orderno.setInputType(InputType.TYPE_CLASS_TEXT);
            int maxLength = 50;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            orderno.setFilters(fArray);
        }
        if (v == radioorder) {
            clear.setVisibility(View.GONE);
            orderno.setText("");
            orderno.setInputType(InputType.TYPE_CLASS_NUMBER);
            int maxLength = 50;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            orderno.setFilters(fArray);
            SessionData.getInstance().setEquipenable(0);
        }
        if (v == clear) {
//			orderno.setText("");
//			custname.setText("");
//			orderno.setEnabled(true);
//			custname.setEnabled(true);
            if (radioequipID.isChecked()) {
                //clear.setEnabled(true);
                if (isCameraAvailable()) {
                    SessionData.getInstance().setZbarScan(2);
                    launchActivity(SimpleScannerActivity.class);
//		            Intent intent = new Intent(this, ZBarScannerActivity.class);
//		            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                } else {
                    Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                }
            } else {
                //clear.setEnabled(false);
            }
        }
        if (v == btnSearch) {
            if (orderno.getText().length() != 0) {
                if (select == 1) {

                    editEqupID = 1;

                    new AsyncEquipmentEditableTask().execute();
//				new AsycOrderList().execute();
                    int filterlength = Integer.parseInt("12");


                } else if (select == 2) {

                    editEqupID = 0;

                    new AsyncEquipmentEditableTask().execute();


                    int maxLength = 50;


                } else if (select == 0) {

                    new AsyncEquiplist().execute();
                    orderno.setInputType(InputType.TYPE_CLASS_TEXT);
                    SessionData.getInstance().setEquipenable(1);


                }

            } else {

                Toast.makeText(getApplicationContext(), "Enter the field",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (v == imgEquipScan) {

//			orderno.setText("");
//			custname.setText("");
//			orderno.setEnabled(true);
//			custname.setEnabled(true);

            if (select == 0) {
                //clear.setEnabled(true);
                if (isCameraAvailable()) {
                    SessionData.getInstance().setZbarScan(2);
                    launchActivity(SimpleScannerActivity.class);
//		            Intent intent = new Intent(this, ZBarScannerActivity.class);
//		            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                } else {
                    Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                }
            } else {
//                Log.d("imgEquipScan", "onClick: not Equip.Id  ");
                //clear.setEnabled(false);
            }
        } else if (v == back) {
            onBackPressed();
        } else if (v == backtext) {
            onBackPressed();
        }

        if (v == sprSearchBy) {
            SpinnerDialog.ShowSpinnerDialog(RentalOrderSearch.this, searchBy, new SpinnerInterface() {
                @Override
                public void position(int pos, int view_id) {
                    sprSearchBy.setTitle(searchBy.get(pos));
                    if (pos == 0) {
                        txtOrderno.setText("Equip.Id");
                        orderno.setHint("Type EquipId or Click Scan");
                        select = 0;
                        imgEquipScan.setVisibility(View.VISIBLE);
//                        new AsyncEquiplist().execute();
//                        orderno.setInputType(InputType.TYPE_CLASS_TEXT);
//                        SessionData.getInstance().setEquipenable(1);
                    } else if (pos == 1) {
                        txtOrderno.setText("Order #");
                        orderno.setHint("Type Order #");
                        select = 1;
                        imgEquipScan.setVisibility(View.GONE);

//                        editEqupID = 1;

//                        new AsyncEquipmentEditableTask().execute();
//	new AsycOrderList().execute();
//                        int filterlength = Integer.parseInt("12");

                    } else if (pos == 2) {
                        txtOrderno.setText("Customer");
                        orderno.setHint("Type Customer ");
                        select = 2;

//                        editEqupID = 0;

//                        new AsyncEquipmentEditableTask().execute();


//                        int maxLength = 50;


                    }


                }
            }, R.id.btn_order_search, "Select SearchBy");
        }

    }

    private boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchActivity(Class<?> clss) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
        }
    }


//	  @Override
//	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	        switch (requestCode) {
//	            case ZBAR_SCANNER_REQUEST:
////	            case ZBAR_QR_SCANNER_REQUEST:
//	                if (resultCode == RESULT_OK) {
//	                	orderno.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
////	                    Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
//	                } else if(resultCode == RESULT_CANCELED && data != null) {
//	                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//	                    if(!TextUtils.isEmpty(error)) {
//	                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//	                    }
//	                }
//				break;
//	        }
//	    }

    private class AsycCusterlist extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalOrderSearch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                orderCustlist = WebServiceConsumer.getInstance()
                        .getRentalOrderCustomerList(user.getUserDescription(),
                                orderno.getText().toString().trim());

            } catch (java.net.SocketTimeoutException e) {
                orderCustlist = null;

            } catch (Exception e) {
                orderCustlist = null;

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("OrderCUSTlist", "" + orderCustlist);
            if (orderCustlist != null && orderCustlist.size() > 0) {
                ProgressBar.dismiss();

                if (orderCustlist.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setCustomerlist(orderCustlist);

                    Intent intent = new Intent(RentalOrderSearch.this,
                            RentalOrderCustomer.class);
                    startActivity(intent);
                } else {
                    if (orderCustlist.get(0).getMessage().contains("Session")) {
                        session = 0;
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(RentalOrderSearch.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(orderCustlist.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent inspection = new Intent(RentalOrderSearch.this,
                                        MainActivity.class);
                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalOrderSearch.this,
                        "Customer Name entered is not found.");
            }
        }
    }

    private class AsyncEquiplist extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalOrderSearch.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                equipmentlist = WebServiceConsumer.getInstance()
                        .getRentalEquipsearch(user.getUserDescription(),
                                orderno.getText().toString(), "R", SessionData.getInstance().getBranchcode());

            } catch (java.net.SocketTimeoutException e) {
                equipmentlist = null;

            } catch (Exception e) {
                equipmentlist = null;

                e.printStackTrace();
            }
            return null;
        }

        ;

        @Override
        protected void onPostExecute(Void result) {
            Log.d("equipmentlist", "" + equipmentlist);
            if (equipmentlist != null && equipmentlist.size() > 0) {
                ProgressBar.dismiss();

                if (!(equipmentlist.get(0).getMessage().equals(""))) {
//					AlertDialogBox.showAlertDialog(RentalOrderSearch.this,
//							equipmentlist.get(0).getMessage());
                    if (equipmentlist.get(0).getMessage().contains("Session")) {
                        session = 1;
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(RentalOrderSearch.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(equipmentlist.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent inspection = new Intent(RentalOrderSearch.this,
                                        MainActivity.class);
                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                } else {
                    SessionData.getInstance().setEquipID(orderno.getText().toString());
                    SessionData.getInstance().setOrderlist(equipmentlist);
                    SessionData.getInstance().setBack(0);
                    Intent intent = new Intent(RentalOrderSearch.this,
                            RentalOrderOrderlist.class);
                    startActivity(intent);
                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalOrderSearch.this,
                        "Equip.Id entered is not found.");
            }
        }
    }


    private class AsycOrderList extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalOrderSearch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                orderLists = WebServiceConsumer.getInstance()
                        .getRentalOrderList(user.getUserDescription(),
                                SessionData.getInstance().getBranchcode(),
                                orderno.getText().toString(), "", "");

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
            Log.d("orderLists", "" + orderLists);
            if (orderLists != null && orderLists.size() > 0) {
                ProgressBar.dismiss();

                if (!(orderLists.get(0).getMessage().equals(""))) {

                    if (orderLists.get(0).getMessage().contains("Session")) {
                        session = 2;
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(RentalOrderSearch.this);
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

                                Intent inspection = new Intent(RentalOrderSearch.this,
                                        MainActivity.class);
                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


                } else {
                    SessionData.getInstance().setOrderlist(orderLists);
                    SessionData.getInstance().setBack(0);
                    Intent intent = new Intent(RentalOrderSearch.this,
                            RentalOrderOrderlist.class);
                    startActivity(intent);
                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalOrderSearch.this,
                        "Order No entered is not found.");
            }
        }
    }

    private class AsyncEquipmentEditableTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalOrderSearch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                EquipEditable = WebServiceConsumer.getInstance()
                        .equipmentEditables(user.getUserDescription());
            } catch (java.net.SocketTimeoutException e) {
                EquipEditable = null;
            } catch (Exception e) {
                EquipEditable = null;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (EquipEditable != null) {

                SessionData.getInstance().setEdtEquipId(EquipEditable);

                if (editEqupID == 0) {
                    new AsycCusterlist().execute();
                } else {
                    new AsycOrderList().execute();
                }


            } else {
                if (editEqupID == 0) {
                    new AsycCusterlist().execute();
                } else {
                    new AsycOrderList().execute();
                }
            }
        }
    }

    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalOrderSearch.this);
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
                SessionData.getInstance().setUser(user);


                if (user.getUserId() == 0) {
                    dialog = new Dialog(RentalOrderSearch.this);
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

                            Intent inspection = new Intent(RentalOrderSearch.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    if (session == 0) {
                        new AsycCusterlist().execute();
                    } else if (session == 1) {
                        new AsyncEquiplist().execute();
                    } else if (session == 2) {
                        new AsycOrderList().execute();
                    }
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalOrderSearch.this,
                        "Data is not found");
            }


        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        Intent intent = new Intent(RentalOrderSearch.this,
//                RetntalOrderBranch.class);

//        startActivity(intent);
        Intent intent = new Intent(RentalOrderSearch.this,
                EbsMenu.class);
        startActivity(intent);

    }
}
