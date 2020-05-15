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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ScannerActivity extends AppCompatActivity implements OnClickListener {
    private Button btnSubmit;
    private Button btnScan;
    private EditText equipmentId;
    private ArrayList<RentalDetails> detail;

    private GeneralEquipmentSearchObject generalEquipmentDetail;
    private static Dialog dialog;
    private Class<?> mClss;

    private static final int ZBAR_CAMERA_PERMISSION = 1;

    @SuppressLint("StaticFieldLeak")
    static Context context;
    User objUser;

    private int selectOption = 0;

    private RadioButton Select_Rental;
    private RadioButton Select_General;
    private RadioButton Select_Equipment;
    private User user;
    private ImageView closeBtm;
    private TextView back;
    private String decode;
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private TransferEquipmentSearchObject transferEquipmentSearchObject;
    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scanner1);
//		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
//			String[] perms = {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"
//					,"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"};
//
//			int permsRequestCode = 200;
//			requestPermissions(perms, permsRequestCode);
//		}
        SessionData.getInstance().setWalkaroundNotes("");
        SessionData.getInstance().setPrevioushours("0");
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        SessionData.getInstance().setScanNavigation(0);
        btnScan = (Button) findViewById(R.id.btn_scan);
        equipmentId = (EditText) findViewById(R.id.edt_equipmentId);
        closeBtm = (ImageView) findViewById(R.id.close);
        back = (TextView) findViewById(R.id.back);
        Select_Rental = (RadioButton) findViewById(R.id.select_rental);
        Select_General = (RadioButton) findViewById(R.id.select_general);
        Select_Equipment = (RadioButton) findViewById(R.id.select_equipment);

        SessionData.getInstance().getWalkAroundNotes().clear();
        SessionData.getInstance().getWalkaroundgeneralimages().clear();
        SessionData.getInstance().getWalkAroundType().clear();
        SessionData.getInstance().getWalkAroundCategoryId().clear();

        if (SessionData.getInstance().getScan_equipment() == 0) {
            Select_Rental.setChecked(true);
            selectOption = 1;
        } else if (SessionData.getInstance().getScan_equipment() == 1) {
            Select_Equipment.setChecked(true);
            selectOption = 3;
        }

//		InputMethodManager imm = (InputMethodManager) this
//				.getSystemService(Context.INPUT_METHOD_SERVICE);
//
//		if (imm.isAcceptingText()) {
//			Log.d("","Software Keyboard was shown");
//		} else {
//			Log.d("","Software Keyboard was not shown");
//		}


        decode = SessionData.getInstance().getDecode();
        equipmentId.setText(decode);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String equipid = bundle.getString("value");
            equipmentId.setText(equipid);

            submittAction();


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
        if (v == btnSubmit) {

            submittAction();

        } else if (v == btnScan) {

            if (isCameraAvailable()) {
                SessionData.getInstance().setZbarScan(1);
                launchActivity(SimpleScannerActivity.class);
//	            Intent intent = new Intent(this, ZBarScannerActivity.class);
//	            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
            } else {
                Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
            }

        } else if (v == closeBtm) {
            Intent intent = new Intent(ScannerActivity.this,
                    EbsMenu.class);
            startActivity(intent);


        } else if (v == back) {
            Intent intent = new Intent(ScannerActivity.this,
                    EbsMenu.class);
            startActivity(intent);

        } else if (v == Select_Rental) {
            selectOption = 1;
        } else if (v == Select_General) {
            selectOption = 2;
        } else if (v == Select_Equipment) {
            selectOption = 3;
        }
    }
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//
////		try {
////			Intent i = getIntent();
////			Boolean isExit = i.getBooleanExtra("isExitAction", false);
////			if (isExit) {
////				this.finish();
////			}
////		} catch (Exception e) {
////		}
//	}

    public void submittAction() {
        SessionData.getInstance().setGeneralcheckListData(null);
        SessionData.getInstance().setWalkAroundEquipmentID(equipmentId.getText().toString());
        SessionData.getInstance().setEnteredEquipID(equipmentId.getText().toString());
        SessionData.getInstance().setTemp_equipId(equipmentId.getText().toString());

        if (selectOption == 1) {

            SessionData.getInstance().setRentalOrGeneral(0);
            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0) {
                dialog = new Dialog(this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.rental_select_option);

                TextView check_in = (TextView) dialog.findViewById(R.id.dialog_receive);
                TextView check_out = (TextView) dialog.findViewById(R.id.dialog_transfer);
                ImageView clsImg=dialog.findViewById(R.id.close_img);
                clsImg.setOnClickListener(new OnClickListener() {
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
            } else {
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Please enter the Equipment ID.");
            }
        } else if (selectOption == 2) {

            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0
            ) {
                SessionData.getInstance().setRentalOrGeneral(1);
                new AsyncGeneralDetail().execute();
            } else {
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Please enter the Equipment ID.");
            }


        } else if (selectOption == 3) {
            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0
            ) {

                dialog = new Dialog(this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_option);

                TextView receive = (TextView) dialog.findViewById(R.id.dialog_receive);
                TextView transfer = (TextView) dialog.findViewById(R.id.dialog_transfer);

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

            } else {
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Please enter the Equipment ID.");

            }


        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        try {
            Intent i = getIntent();
            Boolean isExit = i.getBooleanExtra("isExitAction", false);
            if (isExit) {
                this.finish();
            }
        } catch (Exception e) {
        }

    }

    private boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

//	    @Override
//	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	        switch (requestCode) {
//	            case ZBAR_SCANNER_REQUEST:
////	            case ZBAR_QR_SCANNER_REQUEST:
//	                if (resultCode == RESULT_OK) {
//	                	equipmentId.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
////	                    Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
//	                } else if(resultCode == RESULT_CANCELED && data != null) {
//	                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//	                    if(!TextUtils.isEmpty(error)) {
//	                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//	                    }
//	                }
//	                break;
//	        }
//	    }

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


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(ScannerActivity.this,
                EbsMenu.class);
        startActivity(intent);

//		Intent intent = new Intent(ScannerActivity.this,
//				MainActivity.class);
//		startActivity(intent);
        SessionData.getInstance().setDecode(null);

    }

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode,
//			Intent intent) {
//		if (requestCode == 0) {
//			if (resultCode == RESULT_OK) {
//
//				String contents = intent.getStringExtra("SCAN_RESULT");
//				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
//				equipmentId.setText(contents);
//
//			} else if (resultCode == RESULT_CANCELED) {
//				Log.i("App", "Scan unsuccessful");
//			}
//		}
//	}

    private class AsynccheckoutDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

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

                if (detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setEnteredEquipID(
                            equipmentId.getText().toString());
                    Intent intent = new Intent(ScannerActivity.this,
                            RentalListSelector.class);
                    startActivity(intent);
                } else {
                    ProgressBar.dismiss();
                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncSessionLoginTask_CheckOut().execute();
                    } else {
                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(detail.get(0).getMessage());

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

                    Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                    Log.d("CheckOut", "Expired ");


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                detail = WebServiceConsumer.getInstance().getRentalDetail(
                        equipmentId.getText().toString(), SessionData.getInstance().getTemp_userToken());

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

                if (detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setEnteredEquipID(
                            equipmentId.getText().toString());
                    Intent intent = new Intent(ScannerActivity.this,
                            RentalListSelector.class);
                    startActivity(intent);
                } else {
                    dialog = new Dialog(ScannerActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(detail.get(0).getMessage());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//							Intent inspection = new Intent(ScannerActivity.this,
//									MainActivity.class);
//							startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsynccheckinDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

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

                if (detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                    Intent intent = new Intent(ScannerActivity.this,
                            RentalListSelector.class);
                    startActivity(intent);
                } else {

                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncSessionLoginTask().execute();
                    } else {
                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(detail.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//							Intent inspection = new Intent(ScannerActivity.this,
//									MainActivity.class);
//							startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                    Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                    Log.d("CheckIn", "Expired ");


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
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

//	public class AsynccheckinDetailnew extends AsyncTask<Void, Void, Void> {
//
//		protected void onPreExecute() {
//			//ProgressBar.showCommonProgressDialog(ScannerActivity.this);
//		};
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			try {
//				Log.d("Temp_eqipId",""+SessionData.getInstance().getTemp_equipId());
//				Log.d("New User_token",""+SessionData.getInstance().getTemp_userToken());
//				detail = WebServiceConsumer.getInstance().getRentalDetail(
//						SessionData.getInstance().getTemp_equipId(), SessionData.getInstance().getTemp_userToken());
//
//			} catch (java.net.SocketTimeoutException e) {
//				detail = null;
//
//			} catch (Exception e) {
//				detail = null;
//
//
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			ProgressBar.dismiss();
//
//			if (detail != null && detail.size() > 0) {
//
//				//if(detail.get(0).getMessage().equals("")) {
//
////					SessionData.getInstance().setDetail(detail);
////					SessionData.getInstance().setEnteredEquipID(
////							equipmentId.getText().toString());
//					Intent intent = new Intent(ScannerActivity.this,
//							RentalListSelector.class);
//					startActivity(intent);
//				//}
////				else{
////					new AsyncSessionLoginTask().execute();
//
////					dialog = new Dialog(ScannerActivity.this);
////					dialog.setCanceledOnTouchOutside(true);
////					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//////				requestWindowFeature(Window.FEATURE_NO_TITLE);
////					dialog.setContentView(R.layout.message);
////
////
////					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
////					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
////					Message.setText(detail.get(0).getMessage());
////
////					yes.setOnClickListener(new View.OnClickListener() {
////						@Override
////						public void onClick(View v) {
////
////							Intent inspection = new Intent(ScannerActivity.this,
////									MainActivity.class);
////							startActivity(inspection);
////							dialog.dismiss();
////						}
////					});
////
////
////					dialog.show();
////				}
//			} else {
//				//ProgressBar.dismiss();
//				AlertDialogBox.showAlertDialog(ScannerActivity.this,
//						"EquipmentID entered is invalid.");
//			}
//
//		}
//
//	}

    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
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
                    dialog = new Dialog(ScannerActivity.this);
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

                            Intent inspection = new Intent(ScannerActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);
                    new AsynccheckinDetail().execute();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Data is not Found");
            }


        }
    }


    private class AsyncSessionLoginTask_CheckOut extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
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

            if (user != null) {

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(ScannerActivity.this);
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

                            Intent inspection = new Intent(ScannerActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setUser(user);
                    new AsynccheckoutDetail().execute();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
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
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                generalEquipmentDetail = WebServiceConsumer.getInstance().generalEquipSearch(
                        SessionData.getInstance().getTemp_equipId(), user.getUserDescription());

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

                if (generalEquipmentDetail.getEquipmentstatus().toString().contains("ON")) {
                    ProgressBar.dismiss();
                    dialog = new Dialog(ScannerActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText("Equipment is on Rent");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                                Intent inspection = new Intent(ScannerActivity.this,
//                                        ScannerActivity.class);
//                                startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    if (generalEquipmentDetail.getMessage().equals("")) {
                        ProgressBar.dismiss();
                        //SessionData.getInstance().setDetail(detail);
                        SessionData.getInstance().setGeneralEquipmentSearchObject(generalEquipmentDetail);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                        Intent inspection = new Intent(ScannerActivity.this, General_WalkAroundInspection.class);
                        startActivity(inspection);
                    } else {

                        ProgressBar.dismiss();
                        if (generalEquipmentDetail.getMessage().contains("Session")) {
                            Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                            Log.d("General", "Expired ");
                            new AsyncSessionLoginTask_General().execute();
                        } else {
                            dialog = new Dialog(ScannerActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(detail.get(0).getMessage());

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
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncSessionLoginTask_General extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
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
                    dialog = new Dialog(ScannerActivity.this);
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

                            Intent inspection = new Intent(ScannerActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);
                    new AsyncGeneralDetail().execute();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Data is not found");
            }


        }
    }


    private class AsyncTransferDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

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

                Log.d("transferEquipmentSearch message", "" + transferEquipmentSearchObject.getMessage());

                if (transferEquipmentSearchObject.getMessage().length() == 0) {
                    ProgressBar.dismiss();
                    //SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setTransferEquipment(transferEquipmentSearchObject);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());
                    if (transferEquipmentSearchObject.getEquipstatus() != null) {
                        if (transferEquipmentSearchObject.getEquipstatus().contains("AV")) {
                            Intent inspection = new Intent(ScannerActivity.this, SelectBranch.class);
                            startActivity(inspection);
                            SessionData.getInstance().setSelectEquip(0);
                        } else {
                            dialog = new Dialog(ScannerActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText("This Asset is not available for transfer");

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

                    } else {

                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText("This Asset is Empty for transfer");

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//								Intent inspection = new Intent(ScannerActivity.this,
//										MainActivity.class
// );
//								startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }


//					Intent inspection = new Intent(ScannerActivity.this, General_WalkAroundInspection.class);
//					startActivity(inspection);
                } else {


                    if (transferEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Transfer", "Expired ");
                        new AsyncSessionLoginTask_Transfer().execute();
                    } else {
                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        if (detail.get(0).getMessage().toString().length() != 0) {
                            Message.setText(detail.get(0).getMessage());
                        }


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

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_Transfer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
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
            if (user != null) {

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(ScannerActivity.this);
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

                            Intent inspection = new Intent(ScannerActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    SessionData.getInstance().setUser(user);
                    new AsyncTransferDetail().execute();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Data is not found");
            }


        }
    }


    private class AsyncReceviceDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                Log.d("", "Session call");
                ReceiveEquipmentSearchObject = WebServiceConsumer.getInstance().receiveEquipSearch(
                        SessionData.getInstance().getTemp_equipId(), user.getUserDescription());
                Log.d("", "Receive Equip call");

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

                if (ReceiveEquipmentSearchObject.getMessage().equals("")) {
                    ProgressBar.dismiss();
                    //SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setReceiveEquipmentSearchObject(ReceiveEquipmentSearchObject);
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());

                    if (ReceiveEquipmentSearchObject.getFrombranch().length() != 0) {
                        Intent inspection = new Intent(ScannerActivity.this, RecieveWalkAround.class);
                        startActivity(inspection);
                        SessionData.getInstance().setSelectEquip(1);
                    } else {
                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText("There is no Transfer for this Equipment");

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


//					Intent inspection = new Intent(ScannerActivity.this, General_WalkAroundInspection.class);
//					startActivity(inspection);
                } else {
                    ProgressBar.dismiss();

                    if (ReceiveEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Receive", "Expired ");
                        new AsyncSessionLoginTask_Receive().execute();
                    } else {
                        dialog = new Dialog(ScannerActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(detail.get(0).getMessage());

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
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncSessionLoginTask_Receive extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ScannerActivity.this);
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

            if (user != null) {

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(ScannerActivity.this);
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

                            Intent inspection = new Intent(ScannerActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    ProgressBar.dismiss();
                    SessionData.getInstance().setUser(user);
                    new AsyncReceviceDetail().execute();
                }
            } else {

                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ScannerActivity.this,
                        "Data is not found");

            }


        }
    }

}


