package com.ebs.rental.general;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.ProductSearchObject;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

/**
 * Created by techunity on 21/11/16.
 */

@SuppressWarnings("ALL")
public class ScannerProductActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSubmit;
    private Button btnScan;
    private EditText equipmentId;
    private static Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private Dialog popup_dialog;
    private ImageView closeBtm;
    private TextView back;
    private ProductAdapter adap;
    private RadioButton Select_Rental;
    private RadioButton Select_General;
    private RadioButton Select_Equipment;
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private int selectOption = 0;
    private String decode;
    private String Str_ProductId;
    private ArrayList<ProductSearchObject> productSearchObject = new ArrayList<>();
    private User user;

    private int inst = 1;
    private int inen = 15;

    private int session = 0;

    private int count;
    private String search_EquipId;
    private String str_SelectedEquipId;
    private EditText edt_search_equip;
    private Button btn_more;
    private ListView equipList;
    private ArrayList<RentalDetails> detail;

    private Class<?> mClss;

    private static final int ZBAR_CAMERA_PERMISSION = 1;

    private GeneralEquipmentSearchObject generalEquipmentDetail;

    private TransferEquipmentSearchObject transferEquipmentSearchObject;

    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.scan_product1);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            String[] perms = {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"
                    ,"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"};

            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnScan = (Button) findViewById(R.id.btn_scan);
        equipmentId = (EditText) findViewById(R.id.edt_equipmentId);
        SessionData.getInstance().setScanNavigation(1);
        closeBtm = (ImageView) findViewById(R.id.close);
        back = (TextView) findViewById(R.id.back);
        Select_Rental = (RadioButton)findViewById(R.id.select_rental);
        Select_General = (RadioButton)findViewById(R.id.select_general);
        Select_Equipment = (RadioButton)findViewById(R.id.select_equipment);

        SessionData.getInstance().setWalkaroundNotes("");
        SessionData.getInstance().setPrevioushours("0");

        SessionData.getInstance().getWalkAroundNotes().clear();
        SessionData.getInstance().getWalkaroundgeneralimages().clear();
        SessionData.getInstance().getWalkAroundType().clear();
        SessionData.getInstance().getWalkAroundCategoryId().clear();

        if(SessionData.getInstance().getScan_equipment()==0){
            Select_Rental.setChecked(true);
            selectOption = 1;
        }else if(SessionData.getInstance().getScan_equipment()==1){
            Select_Equipment.setChecked(true);
            selectOption = 3;
        }



        decode = SessionData.getInstance().getDecode();
        equipmentId.setText(decode);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String strorderno = bundle.getString("value");
            equipmentId.setText(strorderno);

            SessionData.getInstance().setGeneralcheckListData(null);
            Str_ProductId = equipmentId.getText().toString();
            new AsyncproductSearch().execute();
        }
        user = SessionData.getInstance().getUser();
        btnSubmit.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        closeBtm.setOnClickListener(this);
        back.setOnClickListener(this);
        Select_Rental.setOnClickListener(this);
        Select_General.setOnClickListener(this);
        Select_Equipment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(btnSubmit == v){

            SessionData.getInstance().setGeneralcheckListData(null);
            Str_ProductId = equipmentId.getText().toString();

            new AsyncproductSearch().execute();

        }

        else if (v == btnScan) {


            if (isCameraAvailable()) {
                SessionData.getInstance().setZbarScan(3);
                launchActivity(SimpleScannerActivity.class);
//                Intent intent = new Intent(this, ZBarScannerActivity.class);
//                startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
            } else {
                Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
            }

        } else if (v == closeBtm) {
            Intent intent = new Intent(ScannerProductActivity.this,
                    EbsMenu.class);
            startActivity(intent);


        }
        else if(v == back){
            Intent intent = new Intent(ScannerProductActivity.this,
                    EbsMenu.class);
            startActivity(intent);

        }
        else if(v == Select_Rental){
            selectOption = 1;
        }
        else if(v == Select_General){
            selectOption = 2;
        }
        else if(v == Select_Equipment){
            selectOption = 3;
        }
    }

    public void launchActivity(Class<?> clss){

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
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults){
        switch (requestCode){
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null){
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private class AsyncproductSearchFiltter extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        }

        ;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                int val = 1;
                int vals = 15;

//                user = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                productSearchObject = WebServiceConsumer.getInstance().ProductSearch(
                        Str_ProductId,
                        search_EquipId,
                        val,
                        vals, user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                productSearchObject = null;

            } catch (Exception e) {
                productSearchObject = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (productSearchObject != null && !(productSearchObject.equals(""))) {
                count = productSearchObject.size();
                adap = new ProductAdapter(ScannerProductActivity.this, productSearchObject);
                equipList.setAdapter(adap);
            }
            else{


                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found.");
            }
        }
    }


    private class AsyncproductSearchMore extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        }

        ;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                inst = inen + 1;

                inen = inen + 15;

//                user = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                productSearchObject = WebServiceConsumer.getInstance().ProductSearch(
                        Str_ProductId,
                        search_EquipId,
                        inst,
                        inen, user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                productSearchObject = null;

            } catch (Exception e) {
                productSearchObject = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (productSearchObject != null && !(productSearchObject.equals(""))) {
                count = productSearchObject.size();
                adap = new ProductAdapter(ScannerProductActivity.this, productSearchObject);
                equipList.setAdapter(adap);
            }
            else{


                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found.");
            }
        }
    }




    private class AsyncproductSearch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };



        @Override
        protected Void doInBackground(Void... params) {
            try {
                int val= 1;
                int vals=15;

//                user = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                productSearchObject = WebServiceConsumer.getInstance().ProductSearch(
                        Str_ProductId,
                        "",
                        val,
                        vals, user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                productSearchObject = null;

            } catch (Exception e) {
                productSearchObject = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (productSearchObject != null && !(productSearchObject.size()==0) ) {

//				if(!(dealer.get(0).getMessage().equals(""))){
//					ProgressBar.dismiss();
//					AlertDialogBox.showAlertDialog(EbsMenu.this,
//							dealer.get(0).getMessage());
//				}
//				else {
//					Intent inspection = new Intent(EbsMenu.this,
//							RentalPartsOrderBranch.class);
//					startActivity(inspection);
//				}

                if (productSearchObject != null && !(productSearchObject.size()==0) ) {

                    if (productSearchObject.get(0).getMessage().equals("")) {
                        SessionData.getInstance().setProductSearchObject(productSearchObject);

                        count = productSearchObject.size();

                        popup_dialog = new Dialog(ScannerProductActivity.this);
                        popup_dialog.setCanceledOnTouchOutside(true);
                        popup_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        popup_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        //  Image_dialog.getWindow().setFlags(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                        popup_dialog.setContentView(R.layout.product_equip_list);

                        Window window = popup_dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();

                        wlp.gravity = Gravity.CENTER;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                        window.setAttributes(wlp);
                        popup_dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                        ImageView close = (ImageView)popup_dialog.findViewById(R.id.backicon);
                        edt_search_equip = (EditText)popup_dialog.findViewById(R.id.equip_search);
                        btn_more = (Button)popup_dialog.findViewById(R.id.morelist);
                        equipList = (ListView)popup_dialog.findViewById(R.id.equip_list);

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup_dialog.dismiss();
                            }
                        });


                        equipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                str_SelectedEquipId = productSearchObject.get(position).getEquipmentid();

                                SessionData.getInstance().setWalkAroundEquipmentID(str_SelectedEquipId);
                                SessionData.getInstance().setEnteredEquipID(str_SelectedEquipId);
                                SessionData.getInstance().setTemp_equipId(str_SelectedEquipId);

                                if(selectOption==1) {

                                    SessionData.getInstance().setRentalOrGeneral(0);

                                        dialog = new Dialog(ScannerProductActivity.this);
                                        dialog.setCanceledOnTouchOutside(true);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.rental_select_option);

                                        TextView check_in = (TextView)dialog.findViewById(R.id.dialog_receive);
                                        TextView check_out = (TextView)dialog.findViewById(R.id.dialog_transfer);
                                    ImageView clsImg=dialog.findViewById(R.id.close_img);
                                    clsImg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                        check_in.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                SessionData.getInstance().setRentalIn_Out("I");
                                                new AsynccheckinDetail().execute();

                                                dialog.dismiss();

                                            }
                                        });

                                        check_out.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                SessionData.getInstance().setRentalIn_Out("O");
                                                new AsynccheckoutDetail().execute();


                                                dialog.dismiss();

//						Intent inspection = new Intent(ScannerActivity.this, SelectBranch.class);
//						startActivity(inspection);
//						dialog.dismiss();
//						SessionData.getInstance().setSelectEquip(0);
                                            }
                                        });

                                        dialog.show();
					/*SessionData.getInstance().setRentalEquipId(equipmentId.getText().toString());
					new AsyncRentalDetail().execute();*/

                                }

                                else if(selectOption == 2){


                                        SessionData.getInstance().setRentalOrGeneral(1);
                                        new AsyncGeneralDetail().execute();



                                }

                                else if(selectOption == 3){


                                        dialog = new Dialog(ScannerProductActivity.this);
                                        dialog.setCanceledOnTouchOutside(true);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setContentView(R.layout.select_option);

                                        TextView receive = (TextView)dialog.findViewById(R.id.dialog_receive);
                                        TextView transfer = (TextView)dialog.findViewById(R.id.dialog_transfer);

                                        transfer.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                SessionData.getInstance().setTransferIn_out("O");
                                                new AsyncTransferDetail().execute();


                                                dialog.dismiss();

//						Intent inspection = new Intent(ScannerActivity.this, SelectBranch.class);
//						startActivity(inspection);
//						dialog.dismiss();
//						SessionData.getInstance().setSelectEquip(0);

                                            }
                                        });

                                        receive.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                SessionData.getInstance().setTransferIn_out("I");
                                                new AsyncReceviceDetail().execute();

                                                dialog.dismiss();

                                            }
                                        });

                                        dialog.show();



                                }

                            }
                        });

                        btn_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AsyncproductSearchMore().execute();
                            }
                        });

                        edt_search_equip.requestFocus();

                        edt_search_equip.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void onTextChanged(CharSequence cs, int start, int before,
                                                      int count) {
                                // When user changed the Text

                            }

                            @Override
                            public void beforeTextChanged(CharSequence cs, int start,
                                                          int count, int after) {


                            }

                            @Override
                            public void afterTextChanged(Editable theWatchedText) {

                                search_EquipId = theWatchedText.toString();
                                // deal2.getBranchName();
//                                SessionData.getInstance().setBname(unitIdChanged);
                                new AsyncproductSearchFiltter().execute();

                            }

                        });


                        adap = new ProductAdapter(ScannerProductActivity.this, productSearchObject);
                        equipList.setAdapter(adap);

                        popup_dialog.show();

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
//                        Intent inspection = new Intent(ScannerProductActivity.this,
//                                RentalPartsOrderBranch.class);
//                        startActivity(inspection);
//					}
                    } else {

                        if(productSearchObject.get(0).getMessage().contains("Session")){

                            session = 0;
                            new AsyncSessionLoginTask().execute();

                        }else{
                            dialog = new Dialog(ScannerProductActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(productSearchObject.get(0).getMessage());

                            closeImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    }
                }

            }
            else{


                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found.");
            }

        }

    }

    private boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case ZBAR_SCANNER_REQUEST:
////	            case ZBAR_QR_SCANNER_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    equipmentId.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
////	                    Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
//                } else if(resultCode == RESULT_CANCELED && data != null) {
//                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//                    if(!TextUtils.isEmpty(error)) {
//                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(ScannerProductActivity.this,
                EbsMenu.class);
        startActivity(intent);

//		Intent intent = new Intent(ScannerActivity.this,
//				MainActivity.class);
//		startActivity(intent);
        SessionData.getInstance().setDecode(null);

    }

    private class AsynccheckinDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());
//				objUser = user;
                detail = WebServiceConsumer.getInstance().getRentalDetail(
                        SessionData.getInstance().getTemp_equipId(), user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                detail = null;

            } catch (Exception e) {
                detail = null;


                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ProgressBar.dismiss();
            if (detail != null && detail.size() > 0) {

                if(detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                    Intent intent = new Intent(ScannerProductActivity.this,
                            RentalListSelector.class);
                    startActivity(intent);
                }
                else{

                    if(detail.get(0).getMessage().contains("Session")){
                        session = 1;
                        new AsyncSessionLoginTask().execute();
                    }else{
                        dialog = new Dialog(ScannerProductActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(detail.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                    Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                    Log.d("CheckIn","Expired ");


//					dialog = new Dialog(ScannerActivity.this);
//					dialog.setCanceledOnTouchOutside(true);
//					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialog.setContentView(R.layout.message);
//
//
//					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//					Message.setText(detail.get(0).getMessage());
//
//					yes.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//
//							Intent inspection = new Intent(ScannerActivity.this,
//									MainActivity.class);
//							startActivity(inspection);
//							dialog.dismiss();
//						}
//					});
//
//
//					dialog.show();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());


                Log.d("Session Expiered","Session Expiered");
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

                if(user.getUserDescription().contains("Login is already in use")){
                    dialog = new Dialog(ScannerProductActivity.this);
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

                            Intent inspection = new Intent(ScannerProductActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    SessionData.getInstance().setUser(user);
                    if(session==0){
                        new AsyncproductSearch().execute();
                    }else if(session==1){
                        new AsynccheckinDetail().execute();
                    }

                }
            }
            else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not Found");
            }


        }
    }

    private class AsynccheckoutDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                detail = WebServiceConsumer.getInstance().getRentalDetail(
                        SessionData.getInstance().getEnteredEquipID(), user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                detail = null;

            } catch (Exception e) {
                detail = null;


                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (detail != null && detail.size() > 0) {

                if(detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setEnteredEquipID(
                            equipmentId.getText().toString());
                    Intent intent = new Intent(ScannerProductActivity.this,
                            RentalListSelector.class);
                    startActivity(intent);
                }
                else{
                    ProgressBar.dismiss();
                    if(detail.get(0).getMessage().contains("Session")){
                        new AsyncSessionLoginTask_CheckOut().execute();
                    }
                    else{
                        dialog = new Dialog(ScannerProductActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(detail.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                    Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                    Log.d("CheckOut", "Expired ");


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncSessionLoginTask_CheckOut extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());
                Log.d("Session Expiered","Session Expiered");
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

            if(user!=null){
                ProgressBar.dismiss();
                if(user.getUserDescription().contains("Login is already in use")){
                    dialog = new Dialog(ScannerProductActivity.this);
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

                            Intent inspection = new Intent(ScannerProductActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    SessionData.getInstance().setUser(user);
                    new AsynccheckoutDetail().execute();
                }


            }
            else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is Not Found");
            }

			/*if (objUser != null && objUser.getUsername() != null) {

				ProgressBar.dismiss();
				SessionData.getInstance().setUser(objUser);
				SessionData.getInstance().setUsername(objUser.getUsername());

				SessionData.getInstance().setTemp_Username(objUser.getUsername());
				SessionData.getInstance().setTemp_password(objUser.getPassword());
				SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());
				// SessionData.getInstance().setUsertoken(objUser.getUserToken());
				// Log.d("thse usertoken is ",
				// SessionData.getInstance().getUsertoken());

				if(objUser.getUserId()==0){
					ProgressBar.dismiss();
					AlertDialogBox.showAlertDialog(ScannerActivity.this,
							objUser.getUserDescription());
				}
				else{

				}

				// Intent inspection = new Intent(MainActivity.this,
				// ScannerActivity.class);
				// startActivity(inspection);
			} else {
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(MainActivity.this,
						"Login Failed! Check your service URL and credential.");
			}*/
        }
    }


    private class AsyncGeneralDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                generalEquipmentDetail = WebServiceConsumer.getInstance().generalEquipSearch(
                        SessionData.getInstance().getTemp_equipId(),user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                generalEquipmentDetail = null;

            } catch (Exception e) {
                generalEquipmentDetail = null;


                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (generalEquipmentDetail != null) {

                if(generalEquipmentDetail.getEquipmentstatus().toString().contains("ON")){
                    ProgressBar.dismiss();
                    dialog = new Dialog(ScannerProductActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText("Equipment is on Rent");

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        ScannerProductActivity.class);
//                                startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else
                {

                  if(generalEquipmentDetail.getMessage().equals("")) {
                    ProgressBar.dismiss();
                    //SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setGeneralEquipmentSearchObject(generalEquipmentDetail);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                    Intent inspection = new Intent(ScannerProductActivity.this, General_WalkAroundInspection.class);
                    startActivity(inspection);
                }
                else {

                      ProgressBar.dismiss();
                      if (generalEquipmentDetail.getMessage().contains("Session")) {
                          Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                          Log.d("General", "Expired ");
                          new AsyncSessionLoginTask_General().execute();
                      } else {
                          dialog = new Dialog(ScannerProductActivity.this);
                          dialog.setCanceledOnTouchOutside(true);
                          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                          dialog.setContentView(R.layout.message);


                          TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                          TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                          ImageView closeImg=dialog.findViewById(R.id.close_img);

                          Message.setText(detail.get(0).getMessage());

                          closeImg.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  dialog.dismiss();
                              }
                          });
                          yes.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                  dialog.dismiss();
                              }
                          });


                          dialog.show();
                      }


                  }
                }
            }
            else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_General extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());
                Log.d("Session Expiered","Session Expiered");
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

                if(user.getUserDescription().contains("Login is already in use")){
                    dialog = new Dialog(ScannerProductActivity.this);
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

                            Intent inspection = new Intent(ScannerProductActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    SessionData.getInstance().setUser(user);
                    new AsyncGeneralDetail().execute();
                }
            }else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found");
            }
        }
    }


    private class AsyncTransferDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                transferEquipmentSearchObject = WebServiceConsumer.getInstance().transferEquipmentSearch(
                        SessionData.getInstance().getTemp_equipId(),
                        user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                transferEquipmentSearchObject = null;

            } catch (Exception e) {
                transferEquipmentSearchObject = null;


                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint({"LongLogTag", "SetTextI18n"})
        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (transferEquipmentSearchObject != null) {

                Log.d("transferEquipmentSearchObject", "" + transferEquipmentSearchObject.toString());
                Log.d("transferEquipmentSearch Message", "" + transferEquipmentSearchObject.getMessage().length());

                if(transferEquipmentSearchObject.getMessage().length()==0) {
                    ProgressBar.dismiss();
                    //SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setTransferEquipment(transferEquipmentSearchObject);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                    if(transferEquipmentSearchObject.getEquipstatus() != null) {
                        if (transferEquipmentSearchObject.getEquipstatus().contains("AV")) {
                            Intent inspection = new Intent(ScannerProductActivity.this, SelectBranch.class);
                            startActivity(inspection);
                            SessionData.getInstance().setSelectEquip(0);
                        } else {
                            dialog = new Dialog(ScannerProductActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText("This Asset is not available for transfer");

                            closeImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//								Intent inspection = new Intent(ScannerActivity.this,
//										MainActivity.class);
//								startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    }
                    else
                    {

                        dialog = new Dialog(ScannerProductActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText("This Asset is Empty for transfer");

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//								Intent inspection = new Intent(ScannerActivity.this,
//										MainActivity.class);
//								startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }



//					Intent inspection = new Intent(ScannerActivity.this, General_WalkAroundInspection.class);
//					startActivity(inspection);
                }
                else {


                    if(transferEquipmentSearchObject.getMessage().contains("Session")){
                        Log.d("old User Token",""+SessionData.getInstance().getTemp_userToken());
                        Log.d("Transfer","Expired ");
                        new AsyncSessionLoginTask_Transfer().execute();
                    }
                    else{
                        dialog = new Dialog(ScannerProductActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        if (detail.get(0).getMessage().toString().length()!=0) {
                            Message.setText(detail.get(0).getMessage());
                        }

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_Transfer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());
                Log.d("Session Expiered","Session Expiered");
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
            if(user!=null){

                if(user.getUserDescription().contains("Login is already in use")){
                    dialog = new Dialog(ScannerProductActivity.this);
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

                            Intent inspection = new Intent(ScannerProductActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    SessionData.getInstance().setUser(user);
                    new AsyncTransferDetail().execute();
                }
            }else{
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found");
            }



        }
    }


    private class AsyncReceviceDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                Log.d("","Session call");
                ReceiveEquipmentSearchObject = WebServiceConsumer.getInstance().receiveEquipSearch(
                        SessionData.getInstance().getTemp_equipId(), user.getUserDescription());
                Log.d("","Receive Equip call");

            } catch (java.net.SocketTimeoutException e) {
                ReceiveEquipmentSearchObject = null;

            } catch (Exception e) {
                ReceiveEquipmentSearchObject = null;


                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(Void result) {
            ProgressBar.dismiss();

            if (ReceiveEquipmentSearchObject != null) {

                Log.d("transferEquipmentSearchObject", "" + ReceiveEquipmentSearchObject.toString());

                if(ReceiveEquipmentSearchObject.getMessage().equals("")) {
                    ProgressBar.dismiss();
                    //SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setReceiveEquipmentSearchObject(ReceiveEquipmentSearchObject);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());

                    Intent inspection = new Intent(ScannerProductActivity.this, RecieveWalkAround.class);
                    startActivity(inspection);
                    SessionData.getInstance().setSelectEquip(1);


//					Intent inspection = new Intent(ScannerActivity.this, General_WalkAroundInspection.class);
//					startActivity(inspection);
                }
                else{
                    ProgressBar.dismiss();

                    if(ReceiveEquipmentSearchObject.getMessage().contains("Session")){
                        Log.d("old User Token",""+SessionData.getInstance().getTemp_userToken());
                        Log.d("Receive","Expired ");
                        new AsyncSessionLoginTask_Receive().execute();
                    }
                    else{
                        dialog = new Dialog(ScannerProductActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(detail.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerProductActivity.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }




                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_Receive extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerProductActivity.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());
                Log.d("Session Expiered","Session Expiered");
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

            if(user!=null){

                if(user.getUserDescription().contains("Login is already in use")){
                    dialog = new Dialog(ScannerProductActivity.this);
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

                            Intent inspection = new Intent(ScannerProductActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setUser(user);
                    new AsyncReceviceDetail().execute();
                }
            }else{

                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerProductActivity.this,
                        "Data is not found");

            }

        }
    }



    public class ProductAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<ProductSearchObject> list;

        public ProductAdapter(Context context, ArrayList<ProductSearchObject> list) {
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
            ProductSearchObject deal = (ProductSearchObject) productSearchObject.get(position);
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.equip_list_row, null);

                holder.EquipmentId = (TextView) convertView
                        .findViewById(R.id.equipmentId);
                holder.Make = (TextView) convertView
                        .findViewById(R.id.make);
                holder.Model = (TextView) convertView
                        .findViewById(R.id.model);
                holder.SerialNo = (TextView) convertView
                        .findViewById(R.id.serial_no);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.EquipmentId.setText(deal.getEquipmentid());
            holder.Make.setText(deal.getMfg());
            holder.Model.setText(deal.getModel());
            holder.SerialNo.setText(deal.getSerialno());

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    }

    class ViewHolder {
        TextView EquipmentId, Make, Model, SerialNo;
    }
}
