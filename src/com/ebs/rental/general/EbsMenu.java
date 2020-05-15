package com.ebs.rental.general;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebs.rental.TabFragments.InspectionFragment;
import com.ebs.rental.TabFragments.OrderFragment;
import com.ebs.rental.TabFragments.TransportationFragment;
import com.ebs.rental.objects.CrossReferenceobject;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.parts.RentalPartsOrderBranch;
import com.ebs.rental.uidesigns.SpinnerDialog;
import com.ebs.rental.uidesigns.SpinnerInterface;
import com.ebs.rental.uidesigns.Spinnerview;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

import static com.ebs.rental.TabFragments.OrderFragment.sprOrderType;

@SuppressWarnings("ALL")
public class EbsMenu extends AppCompatActivity implements OnClickListener, SpinnerInterface {
    private Button parts;
    private Button checkin;
    private Button workOrder;
    private Button rentalOrders;
    private Button btn_transport;
    private ArrayList<DealerBranches> dealer;
    private ArrayList<TransportListObject> transportTruckList;
    private User user;
    RentalOrderList ren;
    private CrossReferenceobject crossReference;
    private int session = 0;
    //	private ImageView logout;
    private static Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private TextView logouttext;
    private String strlogout;


//    private Spinnerview spnrInspectionType, checklist, spnrOrderType;
//    private EditText equipment_id;
//    private EditText order_branch;
//    private Button order_search;
//    private Button inspection_submit;
//    private TextView txt_inspections;
//    private TextView txt_orders;
//    private TextView txt_transportation;

//    LinearLayout linear_inspection, linear_orders, linear_transportation;
//    LinearLayout linear_inspection_type, linear_order_type;
//    LinearLayout linearChecklist;
//    private TextView txtSize;

//    ArrayList<String> inspectionTypes = new ArrayList<String>();
//    ArrayList<String> orderTypes = new ArrayList<String>();


//    private float x1, x2;
//    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = EbsMenu.this;
        setContentView(R.layout.main_menu);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);


        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.material_blue));
            drawable.setSize(1, 1);
            ((LinearLayout) root).setDividerPadding(0);
            ((LinearLayout) root).setDividerDrawable(drawable);

        }

        TabLayout.Tab tab = tabLayout.getTabAt(SessionData.getInstance().getPreviousTabSelection());
        tab.select();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                SessionData.getInstance().setPreviousTabSelection(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        SessionData.getInstance().setTransportTransfer(0);
        SessionData.getInstance().setPrevioushours("0");
        SessionData.getInstance().setWalkaroundNotes("");
        parts = (Button) findViewById(R.id.parts);
        checkin = (Button) findViewById(R.id.checkin);
        workOrder = (Button) findViewById(R.id.workorderquotes);
        rentalOrders = (Button) findViewById(R.id.rentalorders);
        btn_transport = (Button) findViewById(R.id.transport);
        //spinner view
//        spnrInspectionType = (Spinnerview) findViewById(R.id.spr_inspection_type);
//        checklist = (Spinnerview) findViewById(R.id.spinner_checklist);
//        spnrOrderType = (Spinnerview) findViewById(R.id.spinner_order_type);

//        order_branch = (EditText) findViewById(R.id.ac_txt_order_Branch);
//        inspection_submit = (Button) findViewById(R.id.btn_submit_inspection);
//        order_search = (Button) findViewById(R.id.btn_order_search);
//        txt_inspections = (TextView) findViewById(R.id.txt_inspection);
//        txt_orders = (TextView) findViewById(R.id.txt_orders);
//        txt_transportation = (TextView) findViewById(R.id.txt_transportation);
//        linear_inspection = (LinearLayout) findViewById(R.id.linear_inspection);
//        linear_orders = (LinearLayout) findViewById(R.id.linear_orders);
//        linear_transportation = (LinearLayout) findViewById(R.id.linear_transportation);
//        linear_inspection_type = (LinearLayout) findViewById(R.id.linear_type);
//        linear_order_type = (LinearLayout) findViewById(R.id.linear_order_type);
//        linearChecklist = (LinearLayout) findViewById(R.id.linear_checklist);
//        txtSize = (TextView) findViewById(R.id.textview_listview);

//        linear_inspection.setBackgroundResource(R.drawable.round_corner_blue_left);
//        txt_inspections.setTextColor(getResources().getColor(R.color.white));
//        linear_orders.setBackgroundColor(getResources().getColor(R.color.white));
//        linear_transportation.setBackgroundColor(getResources().getColor(R.color.white));

//        linear_inspection_type.setVisibility(View.VISIBLE);
//        linear_order_type.setVisibility(View.GONE);
//        linearChecklist.setVisibility(View.GONE);


//        linear_inspection.setOnClickListener(this);
//
//        linear_orders.setOnClickListener(this);
//        linear_transportation.setOnClickListener(this);
//        linearChecklist.setOnClickListener(this);

//		logout = (ImageView) findViewById(R.id.logouticon);
        logouttext = (TextView) findViewById(R.id.logout);
        user = SessionData.getInstance().getUser();
        parts.setOnClickListener(this);
        checkin.setOnClickListener(this);
        workOrder.setOnClickListener(this);
        rentalOrders.setOnClickListener(this);
//		logout.setOnClickListener(this);
        logouttext.setOnClickListener(this);
        btn_transport.setOnClickListener(this);

//        spnrInspectionType.setOnClickListener(this);
//        checklist.setOnClickListener(this);
//        spnrOrderType.setOnClickListener(this);
//        sprOrderType.setOnClickListener(get);
//        sprOrderType.setTitle("new");


//        inspectionTypes.add("General");
//        inspectionTypes.add("Transfer");
//        inspectionTypes.add("Rental");
//
//        orderTypes.add("Part Orders");
//        orderTypes.add("Rental Orders");

//        linear_inspection_type.setVisibility(View.VISIBLE);
//        linear_order_type.setVisibility(View.VISIBLE);
        SessionData.getInstance().setTab("1");


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            String[] permission = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
            requestPermissions(permission, 200);
        }
        SessionData.getInstance().setBname("");

        new AsyncTrasportTruck().execute();

        closeKeyboard();
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);

        TextView cancel_btn = (TextView) dialog.findViewById(R.id.dialog_cancel);
        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
        ImageView clsImg=dialog.findViewById(R.id.close_img);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncLogoutTask().execute();
                dialog.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        clsImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


//		super.onBackPressed();
//		Intent intent = new Intent(EbsMenu.this, MainActivity.class);
//		startActivity(intent);

    }

    @Override
    public void position(int pos, int view_id) {
//        if (view_id == R.id.spr_regulartime) {
//            spinner_regular_time.setTitle(regulartime.get(pos));
//        }
    }

    private class AsyncLogoutTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(EbsMenu.this);
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
                Intent intent = new Intent(EbsMenu.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == parts) {

            session = 1;
            // Toast.makeText(getApplicationContext(), "", ).sh
            new AsyncDealerpartsBranch().execute();
        }
        if (v == checkin) {


            session = 0;
            SessionData.getInstance().setScan_equipment(0);
            new AsyncCrossReference().execute();

        }
        if (v == workOrder) {
            session = 0;
            SessionData.getInstance().setScan_equipment(1);
            new AsyncCrossReference().execute();
        }
        if (v == rentalOrders) {

            session = 2;
            new AsyncDealerBranch().execute();

        }

        if (v == logouttext) {
            onBackPressed();
        }

        if (v == btn_transport) {

            session = 3;
            new AsyncTrasportTruck().execute();

        }

//
//        if (v == linear_inspection) {
//            setLinearInspection();
//        }
//
//
//        if (v == linear_orders) {
//
//            setLinearOrders();
//        }
//
//
//        if (v == linear_transportation) {
//            swipetoLeftTransportation();
//            setLinearTransportation();
//        }
//
//
//        if (v == spnrInspectionType) {
//
//            SpinnerDialog.ShowSpinnerDialog(EbsMenu.this, inspectionTypes, new SpinnerInterface() {
//                @Override
//                public void position(int pos, int view_id) {
//                    spnrInspectionType.setTitle(inspectionTypes.get(pos));
//                    if (pos == 0) {
//                        if (linearChecklist.getVisibility() == View.GONE)
//                            expand(linearChecklist);
//                    } else if (pos == 1) {
//                        if (linearChecklist.getVisibility() == View.VISIBLE)
//                            collapse(linearChecklist);
//                    } else if (pos == 2) {
//                        if (linearChecklist.getVisibility() == View.VISIBLE)
//                            collapse(linearChecklist);
//                    }
//                }
//            }, R.id.spr_inspection_type, "Select Inspection type");
//
//        }
//        if (v == checklist) {
//
//
//        }
//        if (v == spnrOrderType) {
//
//            SpinnerDialog.ShowSpinnerDialog(EbsMenu.this, orderTypes, new SpinnerInterface() {
//                @Override
//                public void position(int pos, int view_id) {
//                    spnrOrderType.setTitle(orderTypes.get(pos));
//                }
//            }, R.id.spinner_order_type, "Select order type");
//        }
//        if (v == sprOrderType) {
//
//            SpinnerDialog.ShowSpinnerDialog(EbsMenu.this, orderTypes, new SpinnerInterface() {
//                @Override
//                public void position(int pos, int view_id) {
//                    sprOrderType.setTitle(orderTypes.get(pos));
//                }
//            }, R.id.spr_order_type, "Select order type");
//        }

    }

    private class AsyncTrasportTruck extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(EbsMenu.this);

        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                transportTruckList = WebServiceConsumer.getInstance().GetTransportList(user.getUserDescription(), user.getTruckId());
            } catch (java.net.SocketTimeoutException e) {
                transportTruckList = null;

            } catch (Exception e) {
                transportTruckList = null;

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (transportTruckList != null && transportTruckList.size() != 0) {
                //transportTruckList.get(0).setMessage(null);

                if (transportTruckList.get(0).getMessage().length() == 0) {

                    SessionData.getInstance().setTransportTruckList(transportTruckList);
//                    Intent intent = new Intent(EbsMenu.this, TransportList.class);
//                    startActivity(intent);
//					}
                } else {

                    if (transportTruckList.get(0).getMessage().contains("Session")) {

                        new AsyncLoginTask().execute();

                    } else {
                        dialog = new Dialog(EbsMenu.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);

                        TextView message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        message.setText(transportTruckList.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//								Intent inspection = new Intent(EbsMenu.this,
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
//                AlertDialogBox.showAlertDialog(EbsMenu.this,
//                        "Data is not found.");

            }

        }
    }

    private class AsyncDealerBranch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(EbsMenu.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int dval = 1;
                int dvals = 15;

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());


                dealer = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        "", dval,
                        dvals);

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
            if (dealer != null && !(dealer.equals(""))) {

                if (dealer.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setDealer(dealer);

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
                    Intent inspection = new Intent(EbsMenu.this,
                            RetntalOrderBranch.class);
                    startActivity(inspection);
//					}
                } else {

                    if (dealer.get(0).getMessage().contains("Session")) {

                        new AsyncLoginTask().execute();

                    } else {
                        dialog = new Dialog(EbsMenu.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(dealer.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//								Intent inspection = new Intent(EbsMenu.this,
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
                AlertDialogBox.showAlertDialog(EbsMenu.this,
                        "Data is not found.");
            }

        }

    }


    private class AsyncCrossReference extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(EbsMenu.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

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

            ProgressBar.dismiss();

            if (crossReference != null) {


                if (SessionData.getInstance().getRentalChecklistPDFResult() == 1) {

                    if (crossReference.getMessage().contains("Session")) {

                        new AsyncLoginTask().execute();

                    } else {
                        dialog = new Dialog(EbsMenu.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(crossReference.getMessage());

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


                } else {

                    SessionData.getInstance().setRefer(crossReference.getResult());
                    if (crossReference.getResult().contains("False")) {
                        SessionData.getInstance().setProductSearch(true);

//                        Intent inspection = new Intent(EbsMenu.this, ScannerProductActivity.class);
//                        startActivity(inspection);

                    } else {

//                        Intent inspection = new Intent(EbsMenu.this, ScannerActivity.class);
//                        startActivity(inspection);
                    }
                }

            } else {
                AlertDialogBox.showAlertDialog(EbsMenu.this,
                        "Network Error");
            }
        }
    }


    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(EbsMenu.this);
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


                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(EbsMenu.this);
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

                            Intent inspection = new Intent(EbsMenu.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    if (session == 0) {
                        new AsyncCrossReference().execute();
                    } else if (session == 1) {
                        new AsyncDealerpartsBranch().execute();
                    } else if (session == 2) {
                        new AsyncDealerBranch().execute();
                    } else if (session == 3) {
                        new AsyncTrasportTruck().execute();
                    }
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(EbsMenu.this,
                        "Data is not found");
            }


        }
    }


    private class AsyncDealerpartsBranch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(EbsMenu.this);
        }

        ;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                int val = 1;
                int vals = 15;

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                dealer = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        "",
                        val,
                        vals);

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

            if (dealer != null && !(dealer.equals(""))) {

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

                if (dealer != null && !(dealer.equals(""))) {

                    if (dealer.get(0).getMessage().equals("")) {
                        SessionData.getInstance().setDealer(dealer);

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
                        Intent inspection = new Intent(EbsMenu.this,
                                RentalPartsOrderBranch.class);
                        startActivity(inspection);
//					}
                    } else {

                        if (dealer.get(0).getMessage().contains("Session")) {

                            new AsyncLoginTask().execute();

                        } else {
                            dialog = new Dialog(EbsMenu.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(dealer.get(0).getMessage());

                            closeImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//									Intent inspection = new Intent(EbsMenu.this,
//											MainActivity.class);
//									startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }
                    }
                }

            } else {


                AlertDialogBox.showAlertDialog(EbsMenu.this,
                        "Data is not found.");
            }

        }

    }


    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 4);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight * 4 / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
//        linear_inspection_type.setVisibility(View.GONE);
//        linear_order_type.setVisibility(View.GONE);
//        linearChecklist.setVisibility(View.GONE);
//
//        linear_order_type.animate()
//                .translationX(linear_order_type.getWidth())
//                .translationY(-linear_inspection_type.getHeight())
//                .alpha(1f)
//                .setDuration(0)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//
//                    }
//                });
//
//        if (SessionData.getInstance().getTab().equals("1")) {
//            linear_order_type.animate()
//                    .translationX(linear_order_type.getWidth())
//                    .alpha(1f)
////                    .setDuration(500)
//                    .setListener(new AnimatorListenerAdapter() {
//
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            linear_inspection_type.animate()
//                                    .translationX(0)
//                                    .alpha(1f)
////                                    .setDuration(500)
//                            ;
//                        }
//                    });
//
//        } else if (SessionData.getInstance().getTab().equals("2")) {
//            linear_inspection_type.animate()
//                    .translationX(-linear_inspection_type.getWidth())
//                    .alpha(1f)
////                    .setDuration(500)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            linear_order_type.animate()
//                                    .translationX(0)
//                                    .alpha(1f)
////                                    .setDuration(500)
//                            ;
//                        }
//                    });
//        }
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                float deltaX = x2 - x1;
//
//                if (Math.abs(deltaX) > MIN_DISTANCE) {
//                    // Left to Right swipe action
//                    if (x2 > x1) {
//
//                        if (SessionData.getInstance().getTab().equals("1")) {
////                            swipetoRightTransportation();
////                            setLinearTransportation();
//                        } else if (SessionData.getInstance().getTab().equals("2")) {
//                            setLinearInspection();
//                        } else if (SessionData.getInstance().getTab().equals("3")) {
//                            setLinearOrders();
//                        }
//                    }
//
//                    // Right to left swipe action
//                    else {
//
//                        if (SessionData.getInstance().getTab().equals("1")) {
//                            setLinearOrders();
//                        } else if (SessionData.getInstance().getTab().equals("2")) {
//                            setLinearTransportation();
//                            swipetoLeftTransportation();
//                        } else if (SessionData.getInstance().getTab().equals("3")) {
////                            setLinearInspection();
//                        }
//                    }
//
//                } else {
//                    // consider as something else - a screen tap for example
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private void setLinearInspection() {
//
//        SessionData.getInstance().setTab("1");
//
//        linear_inspection.setBackgroundResource(R.drawable.round_corner_blue_left);
//        txt_inspections.setTextColor(getResources().getColor(R.color.white));
//
//        linear_orders.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_orders.setTextColor(getResources().getColor(R.color.black));
//
//        linear_transportation.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_transportation.setTextColor(getResources().getColor(R.color.black));
//
//
//        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in);
//
//
////            linear_inspection_type.setVisibility(View.VISIBLE);
////            linear_order_type.setVisibility(View.VISIBLE);
//
//
//        linear_order_type.animate()
//                .translationX(linear_order_type.getWidth())
//                .translationY(-linear_inspection_type.getHeight())
//                .alpha(1f)
//                .setDuration(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        super.onAnimationStart(animation);
//                        linear_inspection_type.animate()
//                                .translationX(0)
//                                .alpha(1f)
//                                .setDuration(500)
//                                .setListener(new AnimatorListenerAdapter() {
//                                    @Override
//                                    public void onAnimationEnd(Animator animation) {
//                                        super.onAnimationEnd(animation);
//                                    }
//                                });
//                    }
//                });
//
//
////            linear_order_type.startAnimation(anim);
//
////            linear_order_type.setVisibility(View.GONE);
//    }
//
//    private void setLinearOrders() {
//
//        SessionData.getInstance().setTab("2");
//        linear_inspection.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_inspections.setTextColor(getResources().getColor(R.color.black));
//
//        linear_orders.setBackgroundColor(getResources().getColor(R.color.material_blue));
//        txt_orders.setTextColor(getResources().getColor(R.color.white));
//
//        linear_transportation.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_transportation.setTextColor(getResources().getColor(R.color.black));
//
////            linear_inspection_type.setVisibility(View.GONE);
////            linear_order_type.setVisibility(View.VISIBLE);
//
//
//        linear_inspection_type.animate()
//                .translationX(-linear_inspection_type.getWidth())
////                    .translationZ(-linear_inspection_type.getWidth())
//                .alpha(1f)
//                .setDuration(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        super.onAnimationStart(animation);
//
////                            if (linearChecklist.getVisibility() == View.GONE){
////                                linear_order_type.animate()
////                                        .translationX(0)
////                                        .translationY(-linear_inspection_type.getHeight())
////                                        .alpha(1f)
////                                        .setDuration(500)
////                                        .setListener(new AnimatorListenerAdapter() {
////                                            @Override
////                                            public void onAnimationEnd(Animator animation) {
////                                                super.onAnimationEnd(animation);
////                                            }
////                                        });
////                            }else {
//                        linear_order_type.animate()
//                                .translationX(0)
//                                .translationY(-linear_inspection_type.getHeight())
//                                .alpha(1f)
//                                .setDuration(500)
//                                .setListener(new AnimatorListenerAdapter() {
//                                    @Override
//                                    public void onAnimationEnd(Animator animation) {
//                                        super.onAnimationEnd(animation);
////                                            linear_order_type.animate()
////                                                    .translationX(0)
////                                                    .alpha(1f)
////                                                    .setDuration(500)
//                                        ;
//                                    }
//                                });
//
////                            }
//
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//
//                    }
//                });
//
//
//    }
//
//    private void setLinearTransportation() {
//        SessionData.getInstance().setTab("3");
//
//        linear_transportation.setBackgroundResource(R.drawable.round_corner_blue_right);
//        txt_transportation.setTextColor(getResources().getColor(R.color.white));
//
//        linear_inspection.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_inspections.setTextColor(getResources().getColor(R.color.black));
//
//        linear_orders.setBackgroundColor(getResources().getColor(R.color.white));
//        txt_orders.setTextColor(getResources().getColor(R.color.black));
//
//
//        session = 3;
//        new AsyncTrasportTruck().execute();
//    }
//
//    private void swipetoLeftTransportation() {
//        linear_inspection_type.animate()
//                .translationX(2 * -linear_inspection_type.getWidth())
//                .alpha(1f)
//                .setDuration(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        super.onAnimationStart(animation);
//
//                        linear_order_type.animate()
//                                .translationX(-linear_inspection_type.getWidth())
//                                .translationY(-linear_inspection_type.getHeight())
//                                .alpha(1f)
//                                .setDuration(500);
//                    }
//                });
//
//    }
//
//    private void swipetoRightTransportation() {
//        linear_inspection_type.animate()
//                .translationX(linear_inspection_type.getWidth())
//                .alpha(1f)
//                .setDuration(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
////                        linear_order_type.animate()
////                                .translationX(2 * linear_order_type.getWidth())
////                                .alpha(1f)
////                                .setDuration(500);
//                    }
//
//                });
//
//    }
//

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new InspectionFragment();

                case 1:

                    return new OrderFragment();

                case 2:
                    return new TransportationFragment();

                default:
                    return new InspectionFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "Inspection";
                case 1:
                    return "Orders";
                case 2:
                    return "Transportation";
                default:
                    return "Inspection";
            }
        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}
