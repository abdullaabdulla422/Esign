package com.ebs.rental.TabFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.General_WalkAroundInspection;
import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.general.RecieveWalkAround;
import com.ebs.rental.general.RentalListSelector;
import com.ebs.rental.general.SelectBranch;
import com.ebs.rental.general.SimpleScannerActivity;
import com.ebs.rental.objects.AvailableChecklist;
import com.ebs.rental.objects.CrossReferenceobject;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.InspectionChecklist;
import com.ebs.rental.objects.ProductSearchObject;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.uidesigns.SpinnerDialog;
import com.ebs.rental.uidesigns.SpinnerInterface;
import com.ebs.rental.uidesigns.Spinnerview;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ebs.rental.general.EbsMenu.collapse;
import static com.ebs.rental.general.EbsMenu.expand;

public class InspectionFragment extends Fragment implements View.OnClickListener {

    private static final int ZBAR_CAMERA_PERMISSION = 1;
    public static TextView branch;
    public static Spinnerview sprInspectionType;
    public static Button btnInspectionSubmit;
    public static LinearLayout linearChecklist;
    public static ArrayList<String> inspectionTypes = new ArrayList<String>();
    private static Dialog dialog;
    User user;
    int session = 0;
    String method = "";
    ArrayList<String> checklistTypes = new ArrayList<>();
    private ArrayList<DealerBranches> dealer;
    private CrossReferenceobject crossReference;
    private ArrayList<ProductSearchObject> productSearchObject = new ArrayList<>();
    private String Str_ProductId;
    private EditText equipmentId;
    private int count;
    private Dialog popup_dialog;
    private EditText edt_search_equip;
    private Button btn_more;
    private ListView equipList;
    private ArrayList<RentalDetails> detail;
    private String str_SelectedEquipId;
    private int selectOption = 0;
    private ProductAdapter adap;
    private String search_EquipId;
    private GeneralEquipmentSearchObject generalEquipmentDetail;
    private TransferEquipmentSearchObject transferEquipmentSearchObject;
    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;
    private int inst = 1;
    private int inen = 15;
    private Spinnerview checklist;
    private ImageView imgEquipScan;
    private Class<?> mClss;


    public InspectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inspection_tab, container, false);

        branch = view.findViewById(R.id.ac_txt_order_Branch);
        sprInspectionType = view.findViewById(R.id.spr_inspection_type);
        equipmentId = view.findViewById(R.id.edt_equipment_id);
        equipmentId.setText(SessionData.getInstance().getInspectionEquipmentId());
        btnInspectionSubmit = view.findViewById(R.id.btn_submit_inspection);
        linearChecklist = view.findViewById(R.id.linear_checklist);
        checklist = view.findViewById(R.id.spinner_checklist);
        imgEquipScan = view.findViewById(R.id.img_eqipment_scan);

        inspectionTypes.clear();
        inspectionTypes.add("Rental");
        inspectionTypes.add("General");
        inspectionTypes.add("Transfer");
        selectOption = SessionData.getInstance().getInspectionType();

        sprInspectionType.setTitle(inspectionTypes.get(selectOption));
        if (selectOption == 1) {
            linearChecklist.setVisibility(View.VISIBLE);
        } else {
            linearChecklist.setVisibility(View.GONE);
        }

        checklistTypes.clear();
        if (!SessionData.getInstance().getInspectionChecklist().equals("")) {
            checklist.setTitle(SessionData.getInstance().getInspectionChecklist());
        }

        user = SessionData.getInstance().getUser();
        new AsyncCrossReference().execute();

        checklist.setOnClickListener(this);
        sprInspectionType.setOnClickListener(this);
        imgEquipScan.setOnClickListener(this);
        btnInspectionSubmit.setOnClickListener(this);

        System.out.println("Bundle data " + getActivity().getIntent().getExtras());


        if (SessionData.getInstance().getScanFlag() == 1) {

            Bundle bundle = getActivity().getIntent().getExtras();
            if (bundle != null) {
                String strorderno = bundle.getString("value");
                equipmentId.setText(strorderno);

                SessionData.getInstance().setGeneralcheckListData(null);
                Str_ProductId = equipmentId.getText().toString();
                ProgressBar.dismiss();
                if (!SessionData.getInstance().isProductSearch()) {
                    submittAction();
                } else {

                    new AsyncproductSearch().execute();
                }


                SessionData.getInstance().setScanFlag(0);

            }
        }


        // Inflate the layout for this fragment
        return view;

    }

    private void bundleData() {

    }


    @Override
    public void onClick(View v) {

        if (v == checklist) {

            new GetAvailableCheckLists().execute();
//
//            SpinnerDialog.ShowSpinnerDialog(getContext(), checklistTypes, new SpinnerInterface() {
//                @Override
//                public void position(int pos, int view_id) {
//
//                    checklist.setTitle(checklistTypes.get(pos));
//                    SessionData.getInstance().setInspectionChecklist(checklistTypes.get(pos));
//
//                }
//            }, R.id.spinner_checklist, "Select Checklist");

        }
        if (v == sprInspectionType) {
            SpinnerDialog.ShowSpinnerDialog(getActivity(), inspectionTypes, new SpinnerInterface() {
                @Override
                public void position(int pos, int view_id) {
                    sprInspectionType.setTitle(inspectionTypes.get(pos));
                    if (pos == 0) {
                        if (linearChecklist.getVisibility() == View.VISIBLE)
                            collapse(linearChecklist);
                        selectOption = 0;
                        SessionData.getInstance().setInspectionType(0);
                        SessionData.getInstance().setInspectionChecklist("");
                    } else if (pos == 1) {
                        if (linearChecklist.getVisibility() == View.GONE)
                            expand(linearChecklist);
                        selectOption = 1;
                        SessionData.getInstance().setInspectionType(1);
                        session = 0;
                        SessionData.getInstance().setScan_equipment(0);

                    } else if (pos == 2) {
                        if (linearChecklist.getVisibility() == View.VISIBLE)
                            collapse(linearChecklist);
                        selectOption = 2;
                        SessionData.getInstance().setInspectionType(2);
                        SessionData.getInstance().setInspectionChecklist("");
                    }
                }
            }, R.id.spr_inspection_type, "Select Inspection type");
        }

        if (v == imgEquipScan) {

            if (isCameraAvailable()) {
                SessionData.getInstance().setZbarScan(1);
                launchActivity(SimpleScannerActivity.class);


//	            Intent intent = new Intent(this, ZBarScannerActivity.class);
//	            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
            } else {
                Toast.makeText(getActivity(), "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
            }

        }
        if (v == btnInspectionSubmit) {

            SessionData.getInstance().setInspectionEquipmentId(equipmentId.getText().toString());
            SessionData.getInstance().setGeneralcheckListData(null);
            Str_ProductId = equipmentId.getText().toString();
            if (SessionData.getInstance().isProductSearch()) {
                new AsyncproductSearch().execute();
            } else {
                submittAction();
            }
        }
    }

    public void submittAction() {

        SessionData.getInstance().setGeneralcheckListData(null);
        SessionData.getInstance().setWalkAroundEquipmentID(equipmentId.getText().toString());
        SessionData.getInstance().setEnteredEquipID(equipmentId.getText().toString());
        SessionData.getInstance().setTemp_equipId(equipmentId.getText().toString());

        if (selectOption == 0) {

            SessionData.getInstance().setRentalOrGeneral(0);
            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0) {
                dialog = new Dialog(getActivity());
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.rental_select_option);

                TextView check_in = (TextView) dialog.findViewById(R.id.dialog_receive);
                TextView check_out = (TextView) dialog.findViewById(R.id.dialog_transfer);
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

//						Intent inspection = new Intent(getActivity(), SelectBranch.class);
//						startActivity(inspection);
//						dialog.dismiss();
//						SessionData.getInstance().setSelectEquip(0);
                    }
                });

                dialog.show();
					/*SessionData.getInstance().setRentalEquipId(equipmentId.getText().toString());
					new AsyncRentalDetail().execute();*/
            } else {
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Please enter the Equipment ID.");

            }
        } else if (selectOption == 1) {

            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0
            ) {
                SessionData.getInstance().setRentalOrGeneral(1);

                if (SessionData.getInstance().getInspectionChecklist().equals("")) {

                    AlertDialogBox.showAlertDialog(getActivity(),
                            "Please select any checklist ");

                } else {

                    new AsyncGeneralDetail().execute();

//                    ProgressBar.dismiss();

                }
            } else {
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Please enter the Equipment ID.");
            }


        } else if (selectOption == 2) {
            if (equipmentId.getText().toString() != null
                    && equipmentId.getText().toString().length() != 0
            ) {

                dialog = new Dialog(getActivity());
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_option);

                ImageView imgview = (ImageView) dialog.findViewById(R.id.close_img);
                TextView receive = (TextView) dialog.findViewById(R.id.dialog_receive);
                TextView transfer = (TextView) dialog.findViewById(R.id.dialog_transfer);

                transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SessionData.getInstance().setTransferIn_out("O");
                        new AsyncTransferDetail().execute();
                        dialog.dismiss();


//						Intent inspection = new Intent(getActivity(), SelectBranch.class);
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

                imgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            } else {
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Please enter the Equipment ID.");

            }


        }
    }

    private boolean isCameraAvailable() {
        PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(getActivity(), clss);
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        Intent intent = new Intent(getActivity(), mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<InspectionFragment> appReference;

        public AsyncLoginTask(InspectionFragment context) {
            appReference = new WeakReference<>(context);
        }

        protected void onPreExecute() {

//            ProgressBar.showCommonProgressDialog(getActivity());
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

            InspectionFragment fragment = appReference.get();
            if (fragment == null || fragment.isRemoving()) return;


            ProgressBar.dismiss();
            if (user != null) {
                SessionData.getInstance().setUser(user);


                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(getActivity());
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

                            Intent inspection = new Intent(getActivity(),
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
//                    if (session == 0) {
//                        new AsyncCrossReference().execute();
//                    } else if (session == 1) {
//                    new AsyncDealerpartsBranch().execute();
//                    } else if (session == 2) {
//                        new AsyncDealerBranch().execute();
//                    } else if (session == 3) {
//                        new AsyncTrasportTruck().execute();
//                    }

                    switch (method) {
                        case "AsyncCrossReference":
                            new AsyncCrossReference().execute();
                            break;

                        case "AsyncproductSearch":
                            new AsyncproductSearch().execute();
                            break;

                        case "AsyncTransferDetail":
                            new AsyncTransferDetail().execute();
                            break;

                        case "AsyncGeneralDetail":
                            new AsyncGeneralDetail().execute();
                            break;

                        case "AsynccheckoutDetail":
                            new AsynccheckoutDetail().execute();
                            break;

                        case "AsynccheckinDetail":
                            new AsynccheckinDetail().execute();
                            break;

                        case "GetInspectionCheckLists":
                            new GetInspectionCheckLists().execute();
                            break;
                        case "GetAvailableCheckLists":
                            new GetAvailableCheckLists().execute();
                            break;
                        case "AsyncReceviceDetail":
                            new AsyncReceviceDetail().execute();
                            break;
                        default:

                    }
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(getActivity(),

                        "Data is not found");

            }
        }
    }

    private class AsyncCrossReference extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
//            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsyncCrossReference";
                user = SessionData.getInstance().getUser();

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                crossReference = WebServiceConsumer.getInstance().CrossReference(
                        user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                crossReference = null;

            } catch (Exception e) {
                crossReference = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

//            ProgressBar.dismiss();

            if (crossReference != null) {


                if (SessionData.getInstance().getRentalChecklistPDFResult() == 1) {

                    if (crossReference.getMessage().contains("Session")) {

                        new AsyncLoginTask(InspectionFragment.this).execute();

                    } else {
                        dialog = new Dialog(getActivity());
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(crossReference.getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


                } else {

                    SessionData.getInstance().setRefer(crossReference.getResult());
                    if (crossReference.getResult().contains("False")) {

                        SessionData.getInstance().setProductSearch(true);
                        equipmentId.setHint("Type Product ID or Click Scan");

//                        Intent inspection = new Intent(getActivity(), ScannerProductActivity.class);
//                        startActivity(inspection);

                    } else {
                        SessionData.getInstance().setProductSearch(false);
                        equipmentId.setHint("Type EquipID or Click Scan");

//                        Intent inspection = new Intent(getActivity(), ScannerActivity.class);
//                        startActivity(inspection);
                    }
                }

            } else {
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Network Error");
            }
        }
    }

    private class AsyncproductSearch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
//            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsyncproductSearch";
                int val = 1;
                int vals = 15;
                user = SessionData.getInstance().getUser();

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

            if (productSearchObject != null && !(productSearchObject.size() == 0)) {

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

                if (productSearchObject != null && !(productSearchObject.size() == 0)) {

                    if (productSearchObject.get(0).getMessage().equals("")) {
                        SessionData.getInstance().setProductSearchObject(productSearchObject);

                        count = productSearchObject.size();

                        popup_dialog = new Dialog(getActivity());
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

                        ImageView close = (ImageView) popup_dialog.findViewById(R.id.backicon);
                        edt_search_equip = (EditText) popup_dialog.findViewById(R.id.equip_search);
                        btn_more = (Button) popup_dialog.findViewById(R.id.morelist);
                        equipList = (ListView) popup_dialog.findViewById(R.id.equip_list);

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

                                if (selectOption == 0) {

                                    SessionData.getInstance().setRentalOrGeneral(0);

                                    dialog = new Dialog(getActivity());
                                    dialog.setCanceledOnTouchOutside(true);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.rental_select_option);

                                    TextView check_in = (TextView) dialog.findViewById(R.id.dialog_receive);
                                    TextView check_out = (TextView) dialog.findViewById(R.id.dialog_transfer);
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

//						Intent inspection = new Intent(getActivity(), SelectBranch.class);
//						startActivity(inspection);
//						dialog.dismiss();
//						SessionData.getInstance().setSelectEquip(0);
                                        }
                                    });

                                    dialog.show();
					/*SessionData.getInstance().setRentalEquipId(equipmentId.getText().toString());
					new AsyncRentalDetail().execute();*/

                                } else if (selectOption == 1) {

                                    SessionData.getInstance().setRentalOrGeneral(1);
                                    if (SessionData.getInstance().getInspectionChecklist().equals("")) {

                                        AlertDialogBox.showAlertDialog(getActivity(),
                                                "Please select any checklist ");

                                    } else {
                                        new AsyncGeneralDetail().execute();

                                    }

                                } else if (selectOption == 2) {

                                    dialog = new Dialog(getActivity());
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

//						Intent inspection = new Intent(getActivity(), SelectBranch.class);
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


                        adap = new ProductAdapter(getActivity(), productSearchObject);
                        equipList.setAdapter(adap);

                        popup_dialog.show();

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
//                        Intent inspection = new Intent(getActivity(),
//                                RentalPartsOrderBranch.class);
//                        startActivity(inspection);
//					}
                    } else {

                        if (productSearchObject.get(0).getMessage().contains("Session")) {

                            session = 0;
                            new AsyncLoginTask(InspectionFragment.this).execute();

                        } else {
                            dialog = new Dialog(getActivity());
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(productSearchObject.get(0).getMessage());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                Intent inspection = new Intent(getActivity(),
//                                        MainActivity.class);
//                                startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    }
                }

            } else {

                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");


            }

        }

    }

    public class ProductAdapter extends BaseAdapter {

        private final Context mContext;
        private final ArrayList<ProductSearchObject> list;
        private LayoutInflater mInflater;

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

        class ViewHolder {
            TextView EquipmentId, Make, Model, SerialNo;
        }
    }

    private class AsynccheckinDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsynccheckinDetail";
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
                    Intent intent = new Intent(getActivity(),
                            RentalListSelector.class);
                    startActivity(intent);
                } else {

                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncLoginTask(InspectionFragment.this).execute();
                    } else {
                        dialog = new Dialog(getActivity());
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

//							Intent inspection = new Intent(getActivity(),
//									MainActivity.class);
//							startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                    Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                    Log.d("CheckIn", "Expired ");


//					dialog = new Dialog(getActivity());
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
//							Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsynccheckoutDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsynccheckoutDetail";

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
                    Intent intent = new Intent(getActivity(),
                            RentalListSelector.class);
                    startActivity(intent);
                } else {
                    ProgressBar.dismiss();
                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncLoginTask(InspectionFragment.this).execute();
                    } else {
                        dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncGeneralDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsyncGeneralDetail";
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

            ProgressBar.dismiss();
            if (generalEquipmentDetail != null) {

                if (generalEquipmentDetail.getEquipmentstatus().toString().contains("ON")) {
                    ProgressBar.dismiss();
                    dialog = new Dialog(getActivity());
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

//                                Intent inspection = new Intent(getActivity(),
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
                        Intent inspection = new Intent(getActivity(), General_WalkAroundInspection.class);
                        startActivity(inspection);
                    } else {

                        ProgressBar.dismiss();
                        if (generalEquipmentDetail.getMessage().contains("Session")) {
                            Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                            Log.d("General", "Expired ");
                            new AsyncLoginTask(InspectionFragment.this).execute();
                        } else {
                            dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "EquipmentID entered is invalid.");


            }

        }

    }

    private class AsyncTransferDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsyncTransferDetail";
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
                            Intent inspection = new Intent(getActivity(), SelectBranch.class);
                            startActivity(inspection);
                            SessionData.getInstance().setSelectEquip(0);
                        } else {
                            dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
//										MainActivity.class);
//								startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    } else {

                        dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
//										MainActivity.class
// );
//								startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }


//					Intent inspection = new Intent(getActivity(), General_WalkAroundInspection.class);
//					startActivity(inspection);
                } else {


                    if (transferEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Transfer", "Expired ");
                        new AsyncLoginTask(InspectionFragment.this).execute();
                    } else {
                        dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncReceviceDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

//            if (ProgressBar.getDialogStatus())

            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                method = "AsyncReceviceDetail";
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
                        Intent inspection = new Intent(getActivity(), RecieveWalkAround.class);
                        startActivity(inspection);
                        SessionData.getInstance().setSelectEquip(1);
                    } else {
                        dialog = new Dialog(getActivity());
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


//					Intent inspection = new Intent(getActivity(), General_WalkAroundInspection.class);
//					startActivity(inspection);
                } else {
                    ProgressBar.dismiss();

                    if (ReceiveEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Receive", "Expired ");
                        method = "AsyncReceviceDetail";
                        new AsyncLoginTask(InspectionFragment.this).execute();
                    } else {
                        dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "EquipmentID entered is invalid.");
            }

        }

    }

    private class AsyncproductSearchMore extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
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
                adap = new ProductAdapter(getActivity(), productSearchObject);
                equipList.setAdapter(adap);
            } else {


                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");

            }
        }
    }

    private class AsyncproductSearchFiltter extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
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
                adap = new ProductAdapter(getActivity(), productSearchObject);
                equipList.setAdapter(adap);
            } else {


                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");

            }
        }
    }

    private class GetInspectionCheckLists extends AsyncTask<Void, Void, Void> {

        ArrayList<String> tempChecklistTypes = new ArrayList<>();
        ArrayList<InspectionChecklist> inspectionChecklist = new ArrayList<>();

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                inspectionChecklist.clear();


                inspectionChecklist = WebServiceConsumer.getInstance().GetInspectionCheckLists(

                        user.getUserDescription(), user.getUserId());

            } catch (java.net.SocketTimeoutException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            int tag = 1;
            if (tempChecklistTypes != null && !(tempChecklistTypes.equals(""))) {

            }
            if (inspectionChecklist != null && !(inspectionChecklist.size() == 0)) {
//                checklistTypes.clear();
//                checklistTypes.addAll(tempChecklistTypes);
//                for (int i = 0; i < checklistTypes.size(); i++) {
//                    if (checklistTypes.get(i).contains("Session has expired")) {
//                        tag = 0;
//                        method = "GetInspectionCheckLists";
//
//                        new AsyncLoginTask(InspectionFragment.this).execute();
//                        break;
//                    }
//                }
//                if (tag == 1) {
//                    SpinnerDialog.ShowSpinnerDialog(getContext(), checklistTypes, new SpinnerInterface() {
//                        @Override
//                        public void position(int pos, int view_id) {
//
//                            checklist.setTitle(checklistTypes.get(pos));
//                            SessionData.getInstance().setInspectionChecklist(checklistTypes.get(pos));
//
//                        }
//                    }, R.id.spinner_checklist, "Select Checklist");
//                }

//                checklistTypes.addAll(tempChecklistTypes);
                for (int i = 0; i < inspectionChecklist.size(); i++) {
                    if (inspectionChecklist.get(i).getMessage().contains("Session has expired")) {
                        tag = 0;
                        method = "GetInspectionCheckLists";

                        new AsyncLoginTask(InspectionFragment.this).execute();
                        break;
                    }
                }
                checklistTypes.clear();

                for (int i = 0; i < inspectionChecklist.size(); i++) {
                    checklistTypes.add(inspectionChecklist.get(i).getChecklistname());
                }
                if (tag == 1) {
                    SpinnerDialog.ShowSpinnerDialog(getContext(), checklistTypes, new SpinnerInterface() {
                        @Override
                        public void position(int pos, int view_id) {

                            checklist.setTitle(checklistTypes.get(pos));
                            SessionData.getInstance().setInspectionChecklist(checklistTypes.get(pos));
                            SessionData.getInstance().setChecklistid(inspectionChecklist.get(pos).getChecklistid());

                        }
                    }, R.id.spinner_checklist, "Select Checklist");
                }
            } else {

                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");

            }
        }
    }

    private class GetAvailableCheckLists extends AsyncTask<Void, Void, Void> {

        ArrayList<AvailableChecklist> availableChecklist = new ArrayList<>();

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                availableChecklist.clear();


                availableChecklist = WebServiceConsumer.getInstance().GetAvailableCheckListsV1(

                        user.getUserDescription(), user.getUserId());

            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            int tag = 1;

            if (availableChecklist != null && !(availableChecklist.size() == 0)) {

                for (int i = 0; i < availableChecklist.size(); i++) {
                    if (availableChecklist.get(i).getMessage().contains("Session has expired")) {
                        tag = 0;
                        method = "GetAvailableCheckLists";

                        new AsyncLoginTask(InspectionFragment.this).execute();
                        break;
                    }
                }
                checklistTypes.clear();

                for (int i = 0; i < availableChecklist.size(); i++) {
                    checklistTypes.add(availableChecklist.get(i).getChecklistname());
                }
                if (tag == 1) {
                    SpinnerDialog.ShowSpinnerDialog(getContext(), checklistTypes, new SpinnerInterface() {
                        @Override
                        public void position(int pos, int view_id) {

                            checklist.setTitle(checklistTypes.get(pos));
                            SessionData.getInstance().setInspectionChecklist(checklistTypes.get(pos));
                            SessionData.getInstance().setChecklistid(availableChecklist.get(pos).getChecklistid());

                        }
                    }, R.id.spinner_checklist, "Select Checklist");
                }
            } else {

                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");

            }
        }
    }

}