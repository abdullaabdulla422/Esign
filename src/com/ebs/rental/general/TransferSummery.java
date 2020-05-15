package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.EquipmentTransferChecklistDetailObject;
import com.ebs.rental.objects.EquipmentTransferHeadObject;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("ALL")
public class TransferSummery extends AppCompatActivity {

    private double dpi;

    private static Dialog dialog;

    private EquipmentTransferHeadObject equipmentTransferHead;
    private CheckinEqupments checkinequpments;
    private int getrentalcheck;
    private ArrayList<String> cheklistArray;
    private GeneralEquipmentSearchObject generalEquipment;
    private RentalChecklistPDF checklistpdf;
    private ArrayList<CustomerNameMail> customeremails;
    EqupmentSubStatus equpsubStatus;
    private static final int CUSTOM_DIALOG_ID = 0;
    private static final int CUSTOM_DIALOG_IDD = 0;
    ArrayList<RentalDetails> selectedDetail;
    private RentalDetails detail;
    private Button reviewsummary;
    private Button cancelsummary;
    private ArrayList<String> summarydesc;
    ArrayList<CheckList> checkList;
    private String SelectedTransferBranch;
    private Summaryadapter sadap;
    private SummaryWalkadapter summaryWalkadapter;
    private EquipmentCheckParts parts;
    private ArrayList<EquipmentCheckParts> partsList;
    private TextView txtCheckList;
    private TextView txtWalkAround;
    private ArrayList<String> chk;
    private ArrayList<String> status;
    private ArrayList<String> label;
    private ArrayList<String> image;
    private ArrayList<String> imageview;
    Button cancel;
    private ListView dialog_ListView;
    private ImageView dialog_image;
    int position = 0;
    private Button accept;
    private Button cancell;
    private ImageView dialogimage;

    private String CurrentBranch;

    private TransferEquipmentSearchObject transferEquipmentSearchObject;

    private TextView txtMake;
    private TextView txtModel;
    private TextView txtSerialNo;
    private TextView txtCurrectBranch;
    private TextView txtTransferBranch;
    private TextView txtCustomerReceivingBranch;
    private TextView txtBack;
    private TextView txtHourMeter;
    ArrayList<CheckList> check;
    private final Context context = this;
    private ImageView imgBack;
    private EquipmentTransferChecklistDetailObject checkindetails;

    private ArrayList<String> Notes;
    private ArrayList<String> Type;
    private ArrayList<byte[]> imageattach = new ArrayList<>();
    // GeneralEquipmentSearchObject generalEquipment;

    public static int i = 0;
    private static int j = 0;
    public static int z;
    private int count;
    private Bitmap decode;
    private int data = 0;
    String[] str1;
    private String str;
    public boolean issignneeded;
    int index = 0;
    private User user;

    User objUser = null;

    String str2;
    private ArrayList<String> mylabelstr;
    private ArrayList<Integer> camera;
    private FrameLayout footerLayout;

    private CheckBox transportNeeded;


    private boolean checked;


    int TransportListIndex = 0;
    ArrayList<String> Kequipnu = new ArrayList<String>();
    ArrayList<GetTransportDetailsDescOjbect> getTransportDetailsDescOjbects = new ArrayList<>();
    private String session = "";

    // LinearLayout lllayout;
    @SuppressLint({"InflateParams", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.transfer_summery);

        SessionData.getInstance().setMail(0);
        detail = new RentalDetails();
        Log.d("condition", "" + SessionData.getInstance().getLabelcondition());

        cancelsummary = (Button) findViewById(R.id.btncancelsummary);
        reviewsummary = (Button) findViewById(R.id.btnreviewsummary);

        transferEquipmentSearchObject = SessionData.getInstance().getTransferEquipment();
        generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

        transportNeeded = (CheckBox) findViewById(R.id.transport_needed);
        if (transportNeeded.isChecked()) {
            checked = true;
        } else {
            checked = false;
        }

        transportNeeded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transportNeeded.isChecked()) {
                    checked = true;
                    SessionData.getInstance().setTransportNeeded(checked);
                } else {
                    checked = false;
                    SessionData.getInstance().setTransportNeeded(checked);
                }
            }
        });

        Log.d("Transfer Needed", "" + checked);

        SessionData.getInstance().setTransportNeeded(checked);
        if (SessionData.getInstance().getTransportTransfer() == 1) {
            transportNeeded.setVisibility(View.GONE);
            SessionData.getInstance().setTransportNeeded(checked);
        }
        // generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

        j = SessionData.getInstance().getChecklistdata();

        partsList = new ArrayList<EquipmentCheckParts>();
        parts = new EquipmentCheckParts();
        user = SessionData.getInstance().getUser();
        camera = new ArrayList<Integer>();

        txtMake = (TextView) findViewById(R.id.make_values);
        txtModel = (TextView) findViewById(R.id.model_values);
        txtSerialNo = (TextView) findViewById(R.id.serial_no);
        //txtCurrectBranch = (TextView)findViewById(R.id.current_branch);
        txtTransferBranch = (TextView) findViewById(R.id.transfer_branch);
        txtCustomerReceivingBranch = (TextView) findViewById(R.id.customer_reciving_branch);
        txtHourMeter = (TextView) findViewById(R.id.hourmeter);

        txtMake.setText(transferEquipmentSearchObject.getMfg());
        txtModel.setText(transferEquipmentSearchObject.getModel());
        txtSerialNo.setText(transferEquipmentSearchObject.getSerialno());
        //txtCurrectBranch.setText(transferEquipmentSearchObject.getCurrentbranch());

        txtTransferBranch.setText(SessionData.getInstance().getSelectedTransferbranch());

        txtCustomerReceivingBranch.setText(SessionData.getInstance().getSelectedCustomerBranch());

        txtHourMeter.setText(SessionData.getInstance().getTransfer_hourMeter());

        SelectedTransferBranch = SessionData.getInstance().getSelectedTransferbranch();

        TransportListIndex = SessionData.getInstance().getTransportListIndex();

        getTransportDetailsDescOjbects = SessionData.getInstance().getTransportDetailsDescOjbects();

        if (getTransportDetailsDescOjbects != null) {
            Kequipnu = getTransportDetailsDescOjbects.get(0).getKequipnu();
        }


        txtCheckList = (TextView) findViewById(R.id.check_list);
        txtWalkAround = (TextView) findViewById(R.id.walk_around);


        txtCheckList.setBackgroundResource(R.color.intake_bg);
        txtCheckList.setTextColor(Color.parseColor("#ffffff"));
        txtWalkAround.setBackgroundResource(R.color.white);
        txtWalkAround.setTextColor(Color.parseColor("#007AE0"));

        dialog_ListView = (ListView)
                findViewById(R.id.listViewsummary);


        txtBack = (TextView) findViewById(R.id.back);
        imgBack = (ImageView) findViewById(R.id.close);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SessionData.getInstance().setLabelcondition(1);
                Log.d("condition", ""
                        + SessionData.getInstance().getLabelcondition());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SessionData.getInstance().setLabelcondition(1);
                Log.d("condition", ""
                        + SessionData.getInstance().getLabelcondition());
            }
        });

        SelectedTransferBranch = SelectedTransferBranch.substring(0, 3);

        Log.d("TransferBranch", "" + SelectedTransferBranch);

        if (transferEquipmentSearchObject.getCurrentbranch().length() != 0) {
            CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();

            CurrentBranch = CurrentBranch.substring(0, 3);

            Log.d("CurrentBranch", "" + CurrentBranch);

        } else {
            CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();

            Log.d("CurrentBranch", "" + CurrentBranch);
        }

        Notes = SessionData.getInstance().getWalkAroundNotes();
        Type = SessionData.getInstance().getWalkAroundType();
        imageattach = SessionData.getInstance().getWalkaroundgeneralimages();


        SessionData.getInstance().setSignData(null);
        cheklistArray = new ArrayList<String>(SessionData.getInstance()
                .getCheckListData().values());
        footerLayout = (FrameLayout) getLayoutInflater().inflate(
                R.layout.footer_view, null);
        // lllayout = new LinearLayout(this);
        Log.d("The Checklist Array Size", "" + cheklistArray.size());

        //checkList = ReadJson.getPartsList(cheklistArray.get(j));

        // li = (ListView)findViewById(R.id.listView1);
        // cancel = (Button)findViewById(R.id.cancel);
        chk = new ArrayList<String>();
        status = new ArrayList<String>();
        label = new ArrayList<String>();
        image = new ArrayList<String>();
        imageview = new ArrayList<String>();

        checkindetails = new EquipmentTransferChecklistDetailObject();

        if (SessionData.getInstance().getLabelcondition() == 0) {
            SessionData.getInstance().setCheckinequiplocal(
                    SessionData.getInstance().getCheckinequip());
        }
        mylabelstr = SessionData.getInstance().getCheckinequiplocal();
        Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
                .entrySet();

        Iterator<?> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            Log.d("Description for webservice",
                    "" + ((CheckinListData) me.getValue()).getNotes());
            camera.add(((CheckinListData) me.getValue()).getCameraid());
            imageview.add(((CheckinListData) me.getValue()).getImagePath());
            // checkindetails = (CheckinListData) me.getValue()).getNotes());
            chk.add(((CheckinListData) me.getValue()).getNotes());
            status.add(((CheckinListData) me.getValue()).getValues());
            image.add(((CheckinListData) me.getValue()).getImageName());


        }


        data = status.size();
        Log.d("count of list", "" + data);


        reviewsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //      showDialog(CUSTOM_DIALOG_ID);

                // summary(Summaryactivity.this);

                if (SessionData.getInstance().getIssign() == false) {
                    SessionData.getInstance().setSignData(null);
                    // new AsynMails().execute();

                    if ((TransportListIndex + 1) == Kequipnu.size()) {
                        new AysncSubmitData().execute();
                    } else {
                        SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                        new AysncSubmitData1().execute();
                    }


                } else {
                    if (SessionData.getInstance().getTransportTransfer() == 1) {

                        if ((TransportListIndex + 1) == Kequipnu.size()) {
                            Intent intent = new Intent(TransferSummery.this,
                                    TransferSignature.class);
                            startActivity(intent);
                        } else {
                            SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                            new AysncSubmitData1().execute();
                        }

                    } else {
                        Intent intent = new Intent(TransferSummery.this,
                                TransferSignature.class);
                        startActivity(intent);
                    }

                }

            }
        });
        cancelsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                SessionData.getInstance().setLabelcondition(1);
                Log.d("condition", ""
                        + SessionData.getInstance().getLabelcondition());
                // SessionData.getInstance().setCheckinequip(null);

            }
        });


        txtCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckList.setBackgroundResource(R.color.intake_bg);
                txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                txtWalkAround.setBackgroundResource(R.color.white);
                txtWalkAround.setTextColor(Color.parseColor("#007AE0"));


                sadap = new Summaryadapter(TransferSummery.this, summarydesc);
                dialog_ListView.setAdapter(sadap);
                dialog_ListView.clearChoices();
                sadap.notifyDataSetChanged();
            }
        });

        txtWalkAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                txtCheckList.setBackgroundResource(R.color.white);
                txtCheckList.setTextColor(Color.parseColor("#007AE0"));
                txtWalkAround.setBackgroundResource(R.color.intake_bg);
                txtWalkAround.setTextColor(Color.parseColor("#ffffff"));


                summaryWalkadapter = new SummaryWalkadapter(TransferSummery.this, Notes);
                dialog_ListView.setAdapter(summaryWalkadapter);
                dialog_ListView.clearChoices();
                summaryWalkadapter.notifyDataSetChanged();
            }
        });
        sadap = new Summaryadapter(TransferSummery.this, summarydesc);
        dialog_ListView.setAdapter(sadap);
        dialog_ListView.clearChoices();
        sadap.notifyDataSetChanged();


    }


    @SuppressLint("SetTextI18n")
    protected Dialog onCreateDialog(int id) {

        final Dialog dialog = new Dialog(TransferSummery.this);

        switch (id) {
            case CUSTOM_DIALOG_ID:

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.summarylistview);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                // lllayout.setOrientation(LinearLayout.HORIZONTAL);
                final Button more = new Button(this);
                more.setText("Accept");
                more.setBackgroundColor(getResources().getColor(R.color.green));

                // Button footer;
                // final Button cancel = new Button(this);
                // cancel.setText("Cancel");
                txtCheckList = (TextView) dialog.findViewById(R.id.check_list);
                txtWalkAround = (TextView) dialog.findViewById(R.id.walk_around);


                txtCheckList.setBackgroundResource(R.color.intake_bg);
                txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                txtWalkAround.setBackgroundResource(R.color.white);
                txtWalkAround.setTextColor(Color.parseColor("#58a9b7"));

                dialog_ListView = (ListView) dialog
                        .findViewById(R.id.listViewsummary);
                dialog_image = (ImageView) dialog.findViewById(R.id.img_button);


                accept = (Button) dialog.findViewById(R.id.accept);

                cancell = (Button) dialog.findViewById(R.id.cancel);
                accept.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (SessionData.getInstance().getIssign() == false) {
                            SessionData.getInstance().setSignData(null);
                            // new AsynMails().execute();

                            if ((TransportListIndex + 1) == Kequipnu.size()) {
                                new AysncSubmitData().execute();
                            } else {
                                SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                                new AysncSubmitData1().execute();
                            }


                        } else {
                            if (SessionData.getInstance().getTransportTransfer() == 1) {

                                if ((TransportListIndex + 1) == Kequipnu.size()) {
                                    Intent intent = new Intent(TransferSummery.this,
                                            TransferSignature.class);
                                    startActivity(intent);
                                } else {
                                    SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                                    new AysncSubmitData1().execute();
                                }

                            } else {
                                Intent intent = new Intent(TransferSummery.this,
                                        TransferSignature.class);
                                startActivity(intent);
                            }

                        }

                    }
                });
                cancell.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        dialog.dismiss();
                    }
                });

                dialog_image.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                txtCheckList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtCheckList.setBackgroundResource(R.color.intake_bg);
                        txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                        txtWalkAround.setBackgroundResource(R.color.white);
                        txtWalkAround.setTextColor(Color.parseColor("#58a9b7"));


                        sadap = new Summaryadapter(TransferSummery.this, summarydesc);
                        dialog_ListView.setAdapter(sadap);
                        dialog_ListView.clearChoices();
                        sadap.notifyDataSetChanged();
                    }
                });

                txtWalkAround.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        txtCheckList.setBackgroundResource(R.color.white);
                        txtCheckList.setTextColor(Color.parseColor("#58a9b7"));
                        txtWalkAround.setBackgroundResource(R.color.intake_bg);
                        txtWalkAround.setTextColor(Color.parseColor("#ffffff"));


                        summaryWalkadapter = new SummaryWalkadapter(TransferSummery.this, Notes);
                        dialog_ListView.setAdapter(summaryWalkadapter);
                        dialog_ListView.clearChoices();
                        summaryWalkadapter.notifyDataSetChanged();
                    }
                });
                sadap = new Summaryadapter(TransferSummery.this, summarydesc);
                dialog_ListView.setAdapter(sadap);
                dialog_ListView.clearChoices();
                sadap.notifyDataSetChanged();

        }
        return dialog;
    }


    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {

        EquipmentTransferChecklistDetailObject resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(TransferSummery.this);
        }

        @SuppressLint("LongLogTag")
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
//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                equipmentTransferHead = WebServiceConsumer.getInstance().EquipmentTransferHead(
                        0, SessionData.getInstance().getSelectedTransferbranch(),
                        SessionData.getInstance().getTransferIn_out(),
                        user.getUserDescription(), 0);

                if (SessionData.getInstance().getEquipmentTransferHeadObject() == 1) {

                    if (equipmentTransferHead.getMessage().contains("Session")) {
                        session = "AysncSubmitData";
                        new AsyncLoginTask().execute();
                    } else {
                        TransferSummery.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = new Dialog(TransferSummery.this);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.message);


                                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                                Message.setText(equipmentTransferHead.getMessage());

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSummery.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                                        dialog.dismiss();
                                    }
                                });


                                dialog.show();
                            }
                        });
                    }


                } else {
                    getrentalcheck = Integer.parseInt(equipmentTransferHead.getResult());
                    if (SessionData.getInstance().getSig() != null) {
                        str = Base64.encodeToString(SessionData.getInstance()
                                .getSig().clone(), Base64.DEFAULT);
                    }
                    int cust_branch;
                    if (SessionData.getInstance().getTransferEquipment().getCustrebranch().toString().equalsIgnoreCase("0")) {
                        cust_branch = 0;
                    } else {
                        cust_branch = 1;
                    }
                    Log.d("custbr", "" + cust_branch);
                    String kbran = SessionData.getInstance().getSelectedCustomerBranch();
                    for (int j = 0; j < kbran.length(); j++) {
                        Character character = kbran.charAt(j);

                        if (character.toString().equals("-")) {
                            kbran = kbran.substring(0, j);
                            break;
                        }
                    }
                    checkinequpments = WebServiceConsumer
                            .getInstance()
                            .EquipmentTransferDetailWithWalkAround(
                                    user.getUserDescription(),
                                    0,
                                    Integer.parseInt(equipmentTransferHead.getResult()),
                                    transferEquipmentSearchObject.getEquipmentid(),
                                    transferEquipmentSearchObject.getPonum(),
                                    kbran,
                                    transferEquipmentSearchObject.getTransportid(),
                                    SessionData.getInstance().getWalkaroundNotes(), SessionData.getInstance().getSelectedCustomerBranch(),
                                    Integer.parseInt(transferEquipmentSearchObject.getTranstype()),
                                    SessionData.getInstance().isTransportNeeded(), SessionData.getInstance().getTransferKcustnum(), transferEquipmentSearchObject.getMfg(), transferEquipmentSearchObject.getModel(),
                                    transferEquipmentSearchObject.getSerialno(), SelectedTransferBranch,
                                    user.getUsername(), Integer.parseInt(SessionData.getInstance().getTransfer_hourMeter()), cust_branch);
                    // Log.d("Equpstatus", "" + statusselect);
                    Log.d("result data", "" + equipmentTransferHead.getResult());

                    // int rentalCheckinDetail;
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();

                        checkindetails = WebServiceConsumer.getInstance()
                                .equipmentTransferChecklistDetail(
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

                        Log.d("Description length for webservice", ""
                                + ((CheckinListData) me.getValue()).getImagePath().length());

                        if ((((CheckinListData) me.getValue()).getImagePath()
                                .length() > 4)) {
                            resultdata = WebServiceConsumer.getInstance()
                                    .equipmentTransferChecklistImages(
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
                                            transferEquipmentSearchObject.getEquipmentid(), SessionData.getInstance().getTransferIn_out());

                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                        }

                    }

                    String from_branch;
                    if (transferEquipmentSearchObject.getCurrentbranch().toString().equalsIgnoreCase("")) {

                        from_branch = SessionData.getInstance().getSelectedTransferbranch().toString();
                    } else {
                        from_branch = transferEquipmentSearchObject.getCurrentbranch();
                    }


                    checklistpdf = WebServiceConsumer.getInstance()
                            .transferChecklistPdf(
                                    user.getUserDescription(),
                                    transferEquipmentSearchObject.getEquipmentid(),
                                    0,
                                    "0", "", "", "",
                                    0, Integer.parseInt(equipmentTransferHead.getResult()),
                                    SessionData.getInstance().getSelectedTransferbranch(), SessionData.getInstance().getTobranch(), user.getUsername());

                    Log.d("rentaldetailid", ""
                            + SessionData.getInstance().getRentaldetlid());
                }

            } catch (SocketTimeoutException e) {
                checklistpdf = null;

            } catch (Exception e) {
                checklistpdf = null;
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ProgressBar.dismiss();
            // linearMainn.removeAllViews();
            SessionData.getInstance().getDataHandlelist().clear();

            if (checklistpdf == null) {
                dialog = new Dialog(TransferSummery.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                Message.setText("Equipment Transfered not Successfully");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSummery.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                        dialog.dismiss();
                    }
                });


                dialog.show();
            } else {
                Toast.makeText(TransferSummery.this,
                        "Equipment Transfered Successfully", Toast.LENGTH_SHORT).show();
                if (SessionData.getInstance().getScanNavigation() == 0) {
//                    Intent inspection = new Intent(TransferSummery.this, ScannerActivity.class);
//                    startActivity(inspection);
                    Intent inspection = new Intent(TransferSummery.this, EbsMenu.class);
                    startActivity(inspection);
                } else {
//                    Intent intent = new Intent(TransferSummery.this,
//                            ScannerProductActivity.class);
//                    startActivity(intent);
                    Intent inspection = new Intent(TransferSummery.this, EbsMenu.class);
                    startActivity(inspection);
                }


            }


        }
    }


    public class AysncSubmitData1 extends AsyncTask<Void, Void, Void> {

        EquipmentTransferChecklistDetailObject resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(TransferSummery.this);
        }

        @SuppressLint("LongLogTag")
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
//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());


                equipmentTransferHead = WebServiceConsumer.getInstance().EquipmentTransferHead(
                        0, SessionData.getInstance().getSelectedTransferbranch(),
                        SessionData.getInstance().getTransferIn_out(),
                        user.getUserDescription(), SessionData.getInstance().getTransportcallNum());

                if (SessionData.getInstance().getEquipmentTransferHeadObject() == 1) {

                    if (equipmentTransferHead.getMessage().contains("Session")) {
                        new AsyncLoginTask().execute();
                    } else {
                        TransferSummery.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = new Dialog(TransferSummery.this);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.message);


                                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                                Message.setText(equipmentTransferHead.getMessage());

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSummery.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                                        dialog.dismiss();
                                    }
                                });


                                dialog.show();
                            }
                        });
                    }


                } else {
                    getrentalcheck = Integer.parseInt(equipmentTransferHead.getResult());
                    if (SessionData.getInstance().getSig() != null) {
                        str = Base64.encodeToString(SessionData.getInstance()
                                .getSig().clone(), Base64.DEFAULT);
                    }
                    int cust_branch;
                    if (SessionData.getInstance().getTransferEquipment().getCustrebranch().toString().equalsIgnoreCase("0")) {
                        cust_branch = 0;
                    } else {
                        cust_branch = 1;
                    }
                    Log.d("custbr", "" + cust_branch);
                    String kbran = SessionData.getInstance().getSelectedCustomerBranch();
                    for (int j = 0; j < kbran.length(); j++) {
                        Character character = kbran.charAt(j);

                        if (character.toString().equals("-")) {
                            kbran = kbran.substring(0, j);
                            break;
                        }
                    }
                    checkinequpments = WebServiceConsumer
                            .getInstance()
                            .EquipmentTransferDetailWithWalkAround(
                                    user.getUserDescription(),
                                    0,
                                    Integer.parseInt(equipmentTransferHead.getResult()),
                                    transferEquipmentSearchObject.getEquipmentid(),
                                    transferEquipmentSearchObject.getPonum(),
                                    kbran,
                                    transferEquipmentSearchObject.getTransportid(),
                                    SessionData.getInstance().getWalkaroundNotes(), SessionData.getInstance().getSelectedCustomerBranch(),
                                    Integer.parseInt(transferEquipmentSearchObject.getTranstype()),
                                    SessionData.getInstance().isTransportNeeded(), SessionData.getInstance().getTransferKcustnum(), transferEquipmentSearchObject.getMfg(), transferEquipmentSearchObject.getModel(),
                                    transferEquipmentSearchObject.getSerialno(), SelectedTransferBranch,
                                    user.getUsername(), Integer.parseInt(SessionData.getInstance().getTransfer_hourMeter()), cust_branch);
                    // Log.d("Equpstatus", "" + statusselect);
                    Log.d("result data", "" + equipmentTransferHead.getResult());

                    // int rentalCheckinDetail;
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();

                        checkindetails = WebServiceConsumer.getInstance()
                                .equipmentTransferChecklistDetail(
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

                        Log.d("Description length for webservice", ""
                                + ((CheckinListData) me.getValue()).getImagePath().length());

                        if ((((CheckinListData) me.getValue()).getImagePath()
                                .length() > 4)) {
                            resultdata = WebServiceConsumer.getInstance()
                                    .equipmentTransferChecklistImages(
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
                                            transferEquipmentSearchObject.getEquipmentid(), SessionData.getInstance().getTransferIn_out());

                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                        }

                    }

                    String from_branch;
                    if (transferEquipmentSearchObject.getCurrentbranch().toString().equalsIgnoreCase("")) {

                        from_branch = SessionData.getInstance().getSelectedTransferbranch().toString();
                    } else {
                        from_branch = transferEquipmentSearchObject.getCurrentbranch();
                    }


                    Log.d("rentaldetailid", ""
                            + SessionData.getInstance().getRentaldetlid());
                }

            } catch (SocketTimeoutException e) {
                checkindetails = null;

            } catch (Exception e) {
                checkindetails = null;
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ProgressBar.dismiss();
            // linearMainn.removeAllViews();
            SessionData.getInstance().getDataHandlelist().clear();
            SessionData.getInstance().getWalkaroundgeneralimages().clear();
            SessionData.getInstance().getWalkAroundNotes().clear();
            SessionData.getInstance().getWalkAroundType().clear();
            SessionData.getInstance().getWalkAroundCategoryId().clear();


            if (checkindetails == null) {
                dialog = new Dialog(TransferSummery.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                Message.setText("Equipment Transfered not Successfully");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSummery.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                        dialog.dismiss();
                    }
                });


                dialog.show();
            } else {
                Toast.makeText(TransferSummery.this,
                        "Equipment Transfered Successfully", Toast.LENGTH_SHORT).show();
                new AsyncTransferDetail().execute();


            }


        }
    }


    private class AsyncTransferDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransferSummery.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {


                transferEquipmentSearchObject = WebServiceConsumer.getInstance().transferEquipmentSearch(
                        Kequipnu.get(TransportListIndex + 1),
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

                        Intent inspection = new Intent(TransferSummery.this, SelectBranch.class);
                        startActivity(inspection);
                        SessionData.getInstance().setSelectEquip(0);

                    } else {
                        ProgressBar.dismiss();
                        AlertDialogBox.showAlertDialog(TransferSummery.this,
                                "EquipmentID  is invalid.");
                    }
                }
            }
        }
    }


    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransferSummery.this);
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
            if (user != null) {

                if (user.getUserId() == 0) {
                    dialog = new Dialog(TransferSummery.this);
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


                            Intent inspection = new Intent(TransferSummery.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    ProgressBar.dismiss();

                    if (session == "AsynMails") {
                        SessionData.getInstance().setUser(user);

                        new AsynMails().execute();
                    } else if (session == "AysncSubmitData") {
                        SessionData.getInstance().setUser(user);

                        new AysncSubmitData().execute();
                    }
                }


            } else {
                ProgressBar.dismiss();

            }
        }
    }


    private class AsyncLoginTask1 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransferSummery.this);
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
            if (user != null) {

                if (user.getUserId() == 0) {
                    dialog = new Dialog(TransferSummery.this);
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


                            Intent inspection = new Intent(TransferSummery.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setUser(user);


                    new AysncSubmitData1().execute();
                }


            } else {
                ProgressBar.dismiss();

            }
        }
    }

    protected Dialog onCreateDialogg(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_IDD:
                dialog = new Dialog(TransferSummery.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.imagelist);

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialogimage = (ImageView) dialog.findViewById(R.id.imagesummay);
                dialogimage.setImageBitmap(decode);

        }
        return dialog;
    }

    private class Summaryadapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;
        private final ArrayList<String> list;

        public Summaryadapter(Context context, ArrayList<String> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.summaryadapter, null);
                // holder.txtCustName = (TextView) convertView
                // .findViewById(R.id.txt_custname);
                holder.txtdesc = (TextView) convertView
                        .findViewById(R.id.descrption);
                holder.txtdescvalues = (TextView) convertView
                        .findViewById(R.id.descvalues);
                holder.txtstatus = (TextView) convertView
                        .findViewById(R.id.status);
                holder.view = (ImageView) convertView.findViewById(R.id.camera);
                holder.txtimage = (TextView) convertView
                        .findViewById(R.id.ttimage);
                holder.view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // Toast.makeText(getApplicationContext(), "image",
                        // Toast.LENGTH_SHORT).show();
                        String s = imageview.get(holder.ref);
                        byte[] decodestring = Base64.decode(s, Base64.DEFAULT);
                        decode = BitmapFactory.decodeByteArray(decodestring, 0,
                                decodestring.length);
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.imagelist);

                        ImageView im = (ImageView) dialog
                                .findViewById(R.id.imagesummay);
                        im.setImageBitmap(decode);

                        ImageButton close = (ImageButton) dialog
                                .findViewById(R.id.img_close);
                        close.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        // showDialog(CUSTOM_DIALOG_IDD);

                    }
                });

                // holder.view.setImageResource(R.id.camera);
                //

                convertView.setTag(holder);
                sadap.notifyDataSetChanged();

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ref = position;

            holder.txtdesc.setText(mylabelstr.get(position).replace("\\[|\\]",
                    ""));
            holder.txtdescvalues.setText(chk.get(position));
            holder.txtstatus.setText(status.get(position));
            if (camera.get(position) == 1) {
                // holder.view.setEnabled(true);
                holder.view.setVisibility(View.VISIBLE);
            }

            if (camera.get(position) == 0) {
                // holder.view.setEnabled(false);
                holder.view.setVisibility(View.GONE);
            }


            if (imageview.get(position).length() == 0) {
                holder.view.setVisibility(View.INVISIBLE);

            }


            return convertView;
        }

    }


    private class SummaryWalkadapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;
        private final ArrayList<String> list;

        public SummaryWalkadapter(Context context, ArrayList<String> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Notes.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            final Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.summaryadapterwalk, null);
                // holder.txtCustName = (TextView) convertView
                // .findViewById(R.id.txt_custname);
                holder.txtNotes = (TextView) convertView
                        .findViewById(R.id.notes);
                holder.txtType = (TextView) convertView
                        .findViewById(R.id.type);
                holder.view = (ImageView) convertView.findViewById(R.id.camera);
                holder.view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // Toast.makeText(getApplicationContext(), "image",

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.imagelist);

                        ImageView im = (ImageView) dialog
                                .findViewById(R.id.imagesummay);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;

                        byte[] data = imageattach.get(holder.ref);
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length, options);

                        im.setImageBitmap(bmp);

                        ImageButton close = (ImageButton) dialog
                                .findViewById(R.id.img_close);
                        close.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        // showDialog(CUSTOM_DIALOG_IDD);

                    }
                });

                convertView.setTag(holder);
                summaryWalkadapter.notifyDataSetChanged();

            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ref = position;

            holder.txtType.setText(Notes.get(position));
            holder.txtNotes.setText(Type.get(position));


            return convertView;
        }

    }

    private class   AsynMails extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransferSummery.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                customeremails = WebServiceConsumer.getInstance().customermailsv1(
                        SessionData.getInstance().getTemp_userToken(), SessionData.getInstance().getKcustadd(), SessionData.getInstance().getCustadd(), "R");


                Log.d("summarykcustadd", "" + SessionData.getInstance().getKcustadd());
                Log.d("summarycustadd", "" + SessionData.getInstance().getCustadd());

            } catch (java.net.SocketTimeoutException e) {
                customeremails = null;

            } catch (Exception e) {
                customeremails = null;

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (customeremails != null) {
                if (customeremails.size() != 0) {
                    if (customeremails.get(0).getMessage().length() != 0) {
                        if (customeremails.get(0).getMessage().contains("Session")) {

                            session = "AsynMails";
                            new AsyncLoginTask().execute();

                        } else {

                            dialog = new Dialog(TransferSummery.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(customeremails.get(0).getMessage());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent inspection = new Intent(TransferSummery.this,
                                            MainActivity.class);
                                    startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }
                    } else {
//                        SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(TransferSummery.this,
                                General_email.class);

                        startActivity(intent);
                        finish();
                    }
                } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                    Intent intent = new Intent(TransferSummery.this,
                            General_email.class);

                    startActivity(intent);
                    finish();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransferSummery.this,
                        "Data is not found.");
            }
        }

    }


    class ViewHolder {
        TextView txtdesc, txtdescvalues, txtstatus, txtimage;
        ImageView view;
        int ref;

    }

    class Holder {
        TextView txtType, txtNotes;
        ImageView view;
        int ref;

    }

    public void summary(Activity activity) {

        final Dialog popupActivity = new Dialog(activity);
        // popupActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupActivity.setContentView(R.layout.summarylistview);
        ListView li = (ListView) findViewById(R.id.listViewsummary);
        // summarydesc = new ArrayList<String>();
        Summaryadapter sadap = new Summaryadapter(TransferSummery.this,
                summarydesc);
        li.setAdapter(sadap);
        li.clearChoices();
        sadap.notifyDataSetChanged();


    }

}
