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

import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;

import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
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
public class ReceiveSummary extends AppCompatActivity {

    private double dpi;

    private static Dialog dialog;

    private EquipmentTransferHeadObject equipmentTransferHead;
    private CheckinEqupments checkinequpments;
    private int getrentalcheck;
    private ArrayList<String> cheklistArray;
    // GeneralEquipmentSearchObject generalEquipment;
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
    String SelectedTransferBranch;
    String from_branch;
    String CurrentBranch;
    //String SelectedTransferBranch;
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

    private User objUser = null;

    TransferEquipmentSearchObject transferEquipmentSearchObject;

    //String CurrentBranch;

    // TransferEquipmentSearchObject transferEquipmentSearchObject;
    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;
    // TextView make, model, serialNo, UnitID, TransferedBranch;


    private TextView txtMake;
    private TextView txtModel;
    private TextView txtSerialNo;
    private TextView txtCurrectBranch;
    private TextView txtTransferBranch;
    private TextView txtHourMeter;
    private TextView txtFuelMeter;
    private TextView txtCurrentStatus;
    private TextView txtSubStatus;
    private TextView txtDescription;

    ArrayList<CheckList> check;
    private final Context context = this;
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
    String str2;
    private ArrayList<String> mylabelstr;
    private ArrayList<Integer> camera;
    private FrameLayout footerLayout;
    private TextView txtBack;
    private ImageView imgBack;
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
        setContentView(R.layout.receive_summery);

        SessionData.getInstance().setMail(0);
        detail = new RentalDetails();
        Log.d("condition", "" + SessionData.getInstance().getLabelcondition());

        cancelsummary = (Button) findViewById(R.id.btncancelsummary);
        reviewsummary = (Button) findViewById(R.id.btnreviewsummary);

        txtCurrentStatus = (TextView) findViewById(R.id.currentstatusvalues);
        txtSubStatus = (TextView) findViewById(R.id.substatusvalues);
        txtDescription = (TextView) findViewById(R.id.descvalues);

        txtCurrentStatus.setText(SessionData.getInstance().getResieveSelectedStatus());
        txtSubStatus.setText(SessionData.getInstance().getReceiveSelectedSubStatus());
        txtDescription.setText(SessionData.getInstance().getEqupsubstatusdes());


        SelectedTransferBranch = SessionData.getInstance().getCurrentBranch_receive_from();

        //SelectedTransferBranch = SelectedTransferBranch.substring(0, 3);

        for (int j = 0; j < SelectedTransferBranch.length(); j++) {
            Character character = SelectedTransferBranch.charAt(j);

            if (character.toString().equals("-")) {
                SelectedTransferBranch = SelectedTransferBranch.substring(0, j);
                break;
            }
        }

        Log.d("ReceiveSummaryBranch", "" + SelectedTransferBranch);


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

        ReceiveEquipmentSearchObject = SessionData.getInstance().getReceiveEquipmentSearchObject();

        // transferEquipmentSearchObject = SessionData.getInstance().getTransferEquipment();
        //  generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

//        SelectedTransferBranch = SessionData.getInstance().getSelectedTransferbranch();
//
//        SelectedTransferBranch = SelectedTransferBranch.substring(0, 3);
//
//        Log.d("TransferBranch",""+SelectedTransferBranch);

//        CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();
//
//        CurrentBranch = CurrentBranch.substring(0, 3);
//
//        Log.d("CurrentBranch",""+CurrentBranch);

        // generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

        j = SessionData.getInstance().getChecklistdata();

        partsList = new ArrayList<EquipmentCheckParts>();
        parts = new EquipmentCheckParts();
        user = SessionData.getInstance().getUser();
        camera = new ArrayList<Integer>();

        txtMake = (TextView) findViewById(R.id.make_values);
        txtModel = (TextView) findViewById(R.id.model_values);
        txtSerialNo = (TextView) findViewById(R.id.serial_no);
        txtCurrectBranch = (TextView) findViewById(R.id.current_branch);
        txtTransferBranch = (TextView) findViewById(R.id.from_branch);
        txtHourMeter = (TextView) findViewById(R.id.hourmeter);
        txtFuelMeter = (TextView) findViewById(R.id.fuellevelvalues);

        txtMake.setText(ReceiveEquipmentSearchObject.getMfg());
        txtModel.setText(ReceiveEquipmentSearchObject.getModel());
        txtSerialNo.setText(ReceiveEquipmentSearchObject.getSerialno());
        txtCurrectBranch.setText(ReceiveEquipmentSearchObject.getTobranch());

        txtTransferBranch.setText(ReceiveEquipmentSearchObject.getFrombranch());

        txtFuelMeter.setText(SessionData.getInstance().getFuel());

        txtHourMeter.setText(SessionData.getInstance().getTransfer_hourMeter());

        from_branch = ReceiveEquipmentSearchObject.getTobranch();

        //from_branch = from_branch.substring(0, 3);
        //String strr=txtSelectbranch.getText().toString();

//        for(int j=0; j<from_branch.length();j++){
//            Character character=from_branch.charAt(j);
//
//            if(character.toString().equals("-")){
//                from_branch=from_branch.substring(0,j);
//                break;
//            }
//        }

        Log.d("From_branch", "" + from_branch);

//        SelectedTransferBranch = SessionData.getInstance().getSelectedTransferbranch();
//
//        SelectedTransferBranch = SelectedTransferBranch.substring(0, 3);
//
//        Log.d("TransferBranch",""+SelectedTransferBranch);
//
//        CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();
//
//        CurrentBranch = CurrentBranch.substring(0, 3);
//
//        Log.d("CurrentBranch",""+CurrentBranch);


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
        // sadap = new Summaryadapter(Summaryactivity.this, summarydesc);
        // li.setAdapter(sadap);
        // li.clearChoices();
        // sadap.notifyDataSetChanged();

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

            // label.add(((CheckinListData) me.getValue())
            // .getLabels());

        }
        // chk.add(CheckinListData)

        data = status.size();
        Log.d("count of list", "" + data);
        //
        // int m;
        // for(m = 0; m<data;m++)
        // {
        // str = mylabelstr.get(m);
        // str1 = str.split(",");
        // //str2 = str1[index];
        // // str2 = str1.toString();
        //
        // //str2 = str1[0];
        // //vent.setText(str1.toString());
        // }
        // Log.d("sss", ""+ str1);

        reviewsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (SessionData.getInstance().getIssign() == false) {
                    SessionData.getInstance().setSignData(null);
                    // new AsynMails().execute();


                    new AysncSubmitData().execute();

                    //	new AysncSubmitData().execute();


//						Intent intnt = new Intent(Summaryactivity.this,
//								CheckInMailDetails.class);
//						startActivity(intnt);
                } else {
                    Intent intent = new Intent(ReceiveSummary.this,
                            RecieveSignature.class);
                    startActivity(intent);

                }

                //   showDialog(CUSTOM_DIALOG_ID);

                // summary(Summaryactivity.this);

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


        txtCheckList = (TextView) findViewById(R.id.check_list);
        txtWalkAround = (TextView) findViewById(R.id.walk_around);


        txtCheckList.setBackgroundResource(R.color.intake_bg);
        txtCheckList.setTextColor(Color.parseColor("#ffffff"));
        txtWalkAround.setBackgroundResource(R.color.white);
        txtWalkAround.setTextColor(Color.parseColor("#58a9b7"));

        dialog_ListView = (ListView)
                findViewById(R.id.listViewsummary);


        txtCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckList.setBackgroundResource(R.color.intake_bg);
                txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                txtWalkAround.setBackgroundResource(R.color.white);
                txtWalkAround.setTextColor(Color.parseColor("#58a9b7"));


                sadap = new Summaryadapter(ReceiveSummary.this, summarydesc);
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


                summaryWalkadapter = new SummaryWalkadapter(ReceiveSummary.this, Notes);
                dialog_ListView.setAdapter(summaryWalkadapter);
                dialog_ListView.clearChoices();
                summaryWalkadapter.notifyDataSetChanged();
            }
        });
        sadap = new Summaryadapter(ReceiveSummary.this, summarydesc);
        dialog_ListView.setAdapter(sadap);
        dialog_ListView.clearChoices();
        sadap.notifyDataSetChanged();

    }

//	public class AysncSubmitData extends AsyncTask<Void, Void, Void> {
//
//		String resultdata;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			ProgressBar.showCommonProgressDialog(Summaryactivity.this);
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			try {
//
//				Log.d("The Session data Size", ""
//						+ SessionData.getInstance().getDataHandlelist().size());
//				Logger.log("The Session data Size" + ""
//						+ SessionData.getInstance().getDataHandlelist().size());
//
//				Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
//						.entrySet();
//
//				Iterator<?> it = entrySet.iterator();
//				Log.d("The size of IT", ""
//						+ SessionData.getInstance().getDataHandlelist()
//								.entrySet().size());
//				rentalcheckin = WebServiceConsumer.getInstance().RentalCheckin(
//						SessionData.getInstance().getRentalId(), 0,
//						SessionData.getInstance().getInspectionID(),
//						user.getUserDescription());
//				getrentalcheck = Integer.parseInt(rentalcheckin.getResult());
//				checkinequpments = WebServiceConsumer
//						.getInstance()
//						.rentalCheckinEqupments(
//								Integer.parseInt(rentalcheckin.getResult()),
//								SessionData.getInstance().getSelectedDetail()
//										.get(j).getkPart(),
//								Integer.parseInt(SessionData.getInstance()
//										.getHrmeter()),
//
//								SessionData.getInstance().getFuel(),
//								Integer.toString(SessionData.getInstance()
//										.getEquipinspectionID()),
//								user.getUserDescription(),
//								SessionData.getInstance().getSubstatus(),
//								SessionData.getInstance().getEqupsubstatusdes(),
//								SessionData.getInstance().getCurrentstatus(),
//								"");
//				// Log.d("Equpstatus", "" + statusselect);
//				Log.d("result data", "" + rentalcheckin.getResult());
//				// int rentalCheckinDetail;
//				while (it.hasNext()) {
//					Map.Entry me = (Map.Entry) it.next();

//					checkindetails = WebServiceConsumer.getInstance()
//							.rentalCheckinDetl(
//									Integer.parseInt(checkinequpments
//											.getResult()),
//									(int) me.getKey(),
//									((CheckinListData) me.getValue())
//											.getStatus(),
//
//									((CheckinListData) me.getValue())
//											.getNotes(), 0,
//									user.getUserDescription());
//					Log.d("Description for webservice", ""
//							+ ((CheckinListData) me.getValue()).getNotes());
//
//					if ((((CheckinListData) me.getValue()).getImagePath()
//							.length() > 4)) {
//						resultdata = WebServiceConsumer.getInstance()
//								.RentalChickinImages(
//										Integer.parseInt(checkindetails
//												.getResult()),
//
//										((CheckinListData) me.getValue())
//												.getImageName(),
//										"",
//										0,
//										((CheckinListData) me.getValue())
//												.getImagePath(),
//										0,
//										user.getUserDescription(),
//										SessionData.getInstance()
//												.getKpartlist().get(j));
//						Log.d("equipimagesid", ""
//								+ SessionData.getInstance().getEnteredEquipID());
//
//					}
//
//				}
//				checklistpdf = WebServiceConsumer.getInstance()
//						.RentalChecklistPdf(
//								user.getUserDescription(),
//								SessionData.getInstance().getSelectedDetail()
//										.get(j).getkPart(),
//								SessionData.getInstance().getSelectedDetail()
//										.get(j).getEqupId(),
//								Integer.toString(SessionData.getInstance()
//										.getSelectedDetail().get(j)
//										.getRentalID()), "");
//
//			} catch (java.net.SocketTimeoutException e) {
//				resultdata = null;
//
//			} catch (Exception e) {
//				resultdata = null;
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			super.onPostExecute(result);
//			ProgressBar.dismiss();
//			// linearMain.removeAllViews();
//
//			SessionData.getInstance().getDataHandlelist().clear();
//
//			if (j < cheklistArray.size() - 1) {
//				SessionData.getInstance().setChecklist(0);
//				SessionData.getInstance().setChecklistdata(j + 1);
//
//				j++;
//				Intent hr = new Intent(Summaryactivity.this,
//						CustomizedPartsCheck.class);
//				startActivity(hr);
//				// j++;
//				// checkList = ReadJson.getPartsList(cheklistArray.get(j));
//				// SessionData.getInstance().getInspectionID();
//				//
//				// i = 0;
//				SessionData.getInstance().setInspectionID(getrentalcheck);
//				// Intent inn = new
//				// Intent(Summaryactivity.this,CustomizedPartsCheck.class);
//				// startActivity(inn);
//				// initializeViews(checkList);
//				Log.d("I IS PARSING 1", "" + checkList);
//
//			}
//
//			else {
//
//				// SessionData.getInstance().getDataHandlelist().clear();
//				new AsyncTask<Void, Void, Void>() {
//
//					@Override
//					protected void onPreExecute() {
//						ProgressBar
//								.showCommonProgressDialog(Summaryactivity.this);
//					}
//
//					@Override
//					protected Void doInBackground(Void... params) {
//						try {
//							selectedDetail = WebServiceConsumer.getInstance()
//									.getRentalDetail(
//											SessionData.getInstance()
//													.getEnteredEquipID(),
//											user.getUserDescription());
//							SessionData.getInstance().setDetail(selectedDetail);
//							Log.d("I IS PARSING 2", "" + checkList);
//						} catch (java.net.SocketTimeoutException e) {
//							selectedDetail = null;
//						} catch (Exception e) {
//							selectedDetail = null;
//							e.printStackTrace();
//						}
//						return null;
//
//					}
//
//					@Override
//					protected void onPostExecute(Void result) {
//
//						selectedDetail.clear();
//
//					}
//
//				};
//
//				try {
//					selectedDetail = WebServiceConsumer.getInstance()
//							.getRentalDetail(
//									SessionData.getInstance()
//											.getEnteredEquipID(),
//									user.getUserDescription());
//					SessionData.getInstance().setDetail(selectedDetail);
//				} catch (java.net.SocketTimeoutException e) {
//					selectedDetail = null;
//				} catch (Exception e) {
//					selectedDetail = null;
//					e.printStackTrace();
//				}
//				i = 0;
//				j = 0;
//
//				SessionData.getInstance().getSelectedDetail().clear();
//				SessionData.getInstance().getCheckListData().clear();
//				SessionData.getInstance().getDataHandlelist().clear();
//				SessionData.getInstance().getKpartlist().clear();
//				SessionData.getInstance().getHourmeterlist().clear();
//				SessionData.getInstance().getEqpStatus().clear();
//				/* SessionData.getInstance().getGetKey().clear(); */
//
//				Toast.makeText(Summaryactivity.this, "Rental Inpection Over",
//						Toast.LENGTH_LONG).show();
//
//				finish();
//				ProgressBar.dismiss();
//				new AsyncRentalDetail().execute();
//
//			}
//
//		}
//
//	}

//    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {
//
//        protected void onPreExecute() {
//
//        };
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                selectedDetail = WebServiceConsumer.getInstance()
//                        .getRentalDetail(
//                                SessionData.getInstance().getEnteredEquipID(),
//                                user.getUserDescription());
//            } catch (java.net.SocketTimeoutException e) {
//                selectedDetail = null;
//            } catch (Exception e) {
//                selectedDetail = null;
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            // if (selectedDetail != null && selectedDetail.size() > 0) {
//
//            SessionData.getInstance().getSelectedDetail().clear();
//            SessionData.getInstance().getCheckListData().clear();
//            SessionData.getInstance().getDataHandlelist().clear();
//            SessionData.getInstance().getKpartlist().clear();
//            SessionData.getInstance().getHourmeterlist().clear();
//            SessionData.getInstance().getEqpStatus().clear();
//            SessionData.getInstance().setDetail(selectedDetail);
//            SessionData.getInstance().setEnteredEquipID(
//                    SessionData.getInstance().getEnteredEquipID());
//
//            Intent intent = new Intent(GeneralSummaryActivity.this,
//                    RentalListSelector.class);
//            startActivity(intent);
//            finish();
//
//            // }
//        }
//    }

    @SuppressLint("SetTextI18n")
    protected Dialog onCreateDialog(int id) {

        final Dialog dialog = new Dialog(ReceiveSummary.this);

        switch (id) {
            case CUSTOM_DIALOG_ID:

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.summarylistview);

                Window window = dialog.getWindow();
                assert window != null;
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                //noinspection ConstantConditions
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

                // footer =
                // (Button)footerLayout.findViewById(R.id.btnGetMoreResults);
                // dialog_ListView.addFooterView(footer);

                // dialog_ListView.addFooterView(more);
                // dialog_ListView.addFooterView(cancel);
                // lllayout.addView(dialog_ListView);
                // lllayout.addView(more);
                // lllayout.addView(cancel);
                accept = (Button) dialog.findViewById(R.id.accept);

                cancell = (Button) dialog.findViewById(R.id.cancel);
                accept.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (SessionData.getInstance().getIssign() == false) {
                            SessionData.getInstance().setSignData(null);
                            // new AsynMails().execute();


                            new AysncSubmitData().execute();

                            //	new AysncSubmitData().execute();


//						Intent intnt = new Intent(Summaryactivity.this,
//								CheckInMailDetails.class);
//						startActivity(intnt);
                        } else {
                            Intent intent = new Intent(ReceiveSummary.this,
                                    RecieveSignature.class);
                            startActivity(intent);

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


                        sadap = new Summaryadapter(ReceiveSummary.this, summarydesc);
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


                        summaryWalkadapter = new SummaryWalkadapter(ReceiveSummary.this, Notes);
                        dialog_ListView.setAdapter(summaryWalkadapter);
                        dialog_ListView.clearChoices();
                        summaryWalkadapter.notifyDataSetChanged();
                    }
                });
                sadap = new Summaryadapter(ReceiveSummary.this, summarydesc);
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
            ProgressBar.showCommonProgressDialog(ReceiveSummary.this);
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

//                getSelectedTransferbranch()
                Log.d("Receive_branch1", "" + SessionData.getInstance().getCurrentBranch_receive());
                Log.d("Receive_branch2", "" + SessionData.getInstance().getCurrentBranch_TransIn_Out());
                equipmentTransferHead = WebServiceConsumer.getInstance().EquipmentTransferHead(
                        0,
                        //SessionData.getInstance().getSelectedTransferbranch(),
                        from_branch,
                        SessionData.getInstance().getTransferIn_out(),
                        user.getUserDescription(), 0);

                if (SessionData.getInstance().getEquipmentTransferHeadObject() == 1) {

                    if (equipmentTransferHead.getMessage().contains("Session")) {
                        session = "AysncSubmitData";
                        new AsyncLoginTask().execute();
                    } else {
                        ReceiveSummary.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = new Dialog(ReceiveSummary.this);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.message);


                                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                                ImageView closeImg=dialog.findViewById(R.id.close_img);

                                Message.setText(equipmentTransferHead.getMessage());

                                closeImg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


//                            Intent inspection = new Intent(ReceiveSummary.this,
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
                    checkinequpments = WebServiceConsumer
                            .getInstance()
                            .EquipmentTransferReceiveDetailWithWalkAround(
                                    user.getUserDescription(),
                                    0,
                                    Integer.parseInt(equipmentTransferHead.getResult()),
                                    ReceiveEquipmentSearchObject.getEquipmentid(),
                                    String.valueOf(ReceiveEquipmentSearchObject.getPonum()),
                                    SelectedTransferBranch,
                                    ReceiveEquipmentSearchObject.getTransid(),
                                    SessionData.getInstance().getWalkaroundNotes(), user.getUsername(), Integer.parseInt(SessionData.getInstance().getTransfer_hourMeter()), ReceiveEquipmentSearchObject.getTranstype());
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
                                            ReceiveEquipmentSearchObject.getEquipmentid(), SessionData.getInstance().getTransferIn_out());

                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                            Log.d("Equip ID", "" + ReceiveEquipmentSearchObject.getEquipmentid());

                        }

                    }
                    checklistpdf = WebServiceConsumer.getInstance()
                            .transferChecklistPdf(
                                    user.getUserDescription(),
                                    ReceiveEquipmentSearchObject.getEquipmentid(),
                                    0,
                                    "0", "", "", "",
                                    0, Integer.parseInt(equipmentTransferHead.getResult()),

                                    ReceiveEquipmentSearchObject.getTobranch(),
                                    ReceiveEquipmentSearchObject.getFrombranch(),
                                    user.getUsername());

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

            //noinspection StatementWithEmptyBody
            if (checklistpdf == null) {
                dialog = new Dialog(ReceiveSummary.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                ImageView closeImg=dialog.findViewById(R.id.close_img);

                Message.setText("Equipment Received not Successfully");

                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                            Intent inspection = new Intent(ReceiveSummary.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                        dialog.dismiss();
                    }
                });


                dialog.show();

            } else {
                Toast.makeText(ReceiveSummary.this,
                        "Equipment Received Successfully", Toast.LENGTH_SHORT).show();
                if (SessionData.getInstance().getScanNavigation() == 0) {

//                    Intent inspection = new Intent(ReceiveSummary.this, ScannerActivity.class);
//                    startActivity(inspection);
                    Intent inspection = new Intent(ReceiveSummary.this, EbsMenu.class);
                    startActivity(inspection);

                } else {
//                    Intent intent = new Intent(ReceiveSummary.this,
//                            ScannerProductActivity.class);
//                    startActivity(intent);
                    Intent inspection = new Intent(ReceiveSummary.this, EbsMenu.class);
                    startActivity(inspection);
                }


            }


//            if (j < cheklistArray.size() - 1) {
//
//                SessionData.getInstance().setChecklist(0);
//                SessionData.getInstance().setChecklistdata(j + 1);
//                SessionData.getInstance().setMulticheckinvalidate(0);
//
//                j++;
//                Intent hr = new Intent(General_email.this,
//                        CustomizedPartsCheck.class);
//                startActivity(hr);
//                // checkList = ReadJson.getPartsList(cheklistArray.get(j));
//                // SessionData.getInstance().setInspectionID(getrentalcheck);
//                //
//                // i = 0;
//                // initializeViews(checkList);
//                // Log.d("I IS PARSING 1", "" + checkList);
//                SessionData.getInstance().setInspectionID(getrentalcheck);
//
//            } else {
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected void onPreExecute() {
//                        ProgressBar
//                                .showCommonProgressDialog(General_email.this);
//                    }
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        try {
//                            selectedDetail = WebServiceConsumer.getInstance()
//                                    .getRentalDetail(
//                                            SessionData.getInstance()
//                                                    .getEnteredEquipID(),
//                                            user.getUserDescription());
//                            SessionData.getInstance().setDetail(selectedDetail);
//                            Log.d("I IS PARSING 2", "" + checkList);
//                        } catch (SocketTimeoutException e) {
//                            selectedDetail = null;
//                        } catch (Exception e) {
//                            selectedDetail = null;
//                            e.printStackTrace();
//                        }
//                        return null;
//
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void result) {
//                        if(selectedDetail.get(0).getMessage().equals("")) {
//                            selectedDetail.clear();
//                        }
//                        else{
//                            dialog = new Dialog(General_email.this);
//                            dialog.setCanceledOnTouchOutside(true);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.message);
//
//
//                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                            Message.setText(selectedDetail.get(0).getMessage());
//
//                            yes.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    Intent inspection = new Intent(General_email.this,
//                                            MainActivity.class);
//                                    startActivity(inspection);
//                                    dialog.dismiss();
//                                }
//                            });
//
//
//                            dialog.show();
//                        }
//
//                    }
//
//                };
//
//                try {
//                    selectedDetail = WebServiceConsumer.getInstance()
//                            .getRentalDetail(
//                                    SessionData.getInstance()
//                                            .getEnteredEquipID(),
//                                    user.getUserDescription());
//                    SessionData.getInstance().setDetail(selectedDetail);
//                } catch (SocketTimeoutException e) {
//                    selectedDetail = null;
//                } catch (Exception e) {
//                    selectedDetail = null;
//                    e.printStackTrace();
//                }
//                i = 0;
//                j = 0;
//
//                SessionData.getInstance().getSelectedDetail().clear();
//                SessionData.getInstance().getCheckListData().clear();
//                SessionData.getInstance().getDataHandlelist().clear();
//                SessionData.getInstance().getKpartlist().clear();
//                SessionData.getInstance().getHourmeterlist().clear();
//                SessionData.getInstance().getEqpStatus().clear();
//                // checkList.clear();
//
//				/* SessionData.getInstance().getGetKey().clear(); */
//
//                Toast.makeText(General_email.this,
//                        "Rental Inpection Over", Toast.LENGTH_SHORT).show();
//
//                // finish();
//                ProgressBar.dismiss();
//             //   new AsyncRentalDetail().execute();

//            }

            // Intent in = new
            // Intent(RentalSignature.this,ScannerActivity.class);
            // startActivity(in);

        }
    }

    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(ReceiveSummary.this);
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

                ProgressBar.dismiss();

                if (user.getUserId() == 0) {
                    dialog = new Dialog(ReceiveSummary.this);
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


                            Intent inspection = new Intent(ReceiveSummary.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    if (session == "AysncSubmitData") {
                        SessionData.getInstance().setUser(user);
                        new AysncSubmitData().execute();
                    } else if (session == "AsynMails") {
                        new AsynMails().execute();
                    }
                }


//                SessionData.getInstance().setUsername(objUser.getUsername());
//
//                SessionData.getInstance().setTemp_Username(objUser.getUsername());
//                SessionData.getInstance().setTemp_password(objUser.getPassword());
//                SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());


            } else {
                ProgressBar.dismiss();

            }
        }
    }


    protected Dialog onCreateDialogg(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_IDD:
                dialog = new Dialog(ReceiveSummary.this);
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
            //noinspection UnusedAssignment
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
            // holder.txtimage.setText( imageview.get(position));
            // holder.txtdesc.setText(partsList.get(position).getPartsLabel());
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

            // holder.txtdesc.setText(Arrays.toString(str1).replaceAll("\\[|\\]",
            // ""));
            // Log.d("uuu", ""+SessionData.getInstance().getCheckinequip());

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
            //noinspection UnusedAssignment
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
                        // Toast.LENGTH_SHORT).show();
//						String s = imageview.get(holder.ref);
//						byte[] decodestring = Base64.decode(s, Base64.DEFAULT);
//						decode = BitmapFactory.decodeByteArray(decodestring, 0,
//								decodestring.length);
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

                // holder.view.setImageResource(R.id.camera);
                //

                convertView.setTag(holder);
                summaryWalkadapter.notifyDataSetChanged();

            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ref = position;
            // holder.txtimage.setText( imageview.get(position));
            // holder.txtdesc.setText(partsList.get(position).getPartsLabel());
//			holder.txtNotes.setText(mylabelstr.get(position).replace("\\[|\\]",
//					""));
            holder.txtType.setText(Notes.get(position));
            holder.txtNotes.setText(Type.get(position));
//			if (camera.get(position) == 1) {
//				// holder.view.setEnabled(true);
//				holder.view.setVisibility(View.VISIBLE);
//			}
//
//			if (camera.get(position) == 0) {
//				// holder.view.setEnabled(false);
//				holder.view.setVisibility(View.GONE);
//			}

            // holder.txtdesc.setText(Arrays.toString(str1).replaceAll("\\[|\\]",
            // ""));
            // Log.d("uuu", ""+SessionData.getInstance().getCheckinequip());

            return convertView;
        }

    }

    private class AsynMails extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(ReceiveSummary.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                objUser = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());

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
                            session = "AysncSubmitData";
                            new AsyncLoginTask().execute();

                        } else {

//                if(SessionData.getInstance().getCustomeremailsResult()==1){
                            dialog = new Dialog(ReceiveSummary.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(customeremails.get(0).getMessage());

                            closeImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent inspection = new Intent(ReceiveSummary.this,
                                            MainActivity.class);
                                    startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }
                    } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(ReceiveSummary.this,
                                General_email.class);

                        startActivity(intent);
                        finish();
                    }
                } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                    Intent intent = new Intent(ReceiveSummary.this,
                            General_email.class);

                    startActivity(intent);
                    finish();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(ReceiveSummary.this,
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
        Summaryadapter sadap = new Summaryadapter(ReceiveSummary.this,
                summarydesc);
        li.setAdapter(sadap);
        li.clearChoices();
        sadap.notifyDataSetChanged();

        /*
         * ImageView img = ((ImageView) popupActivity
         * .findViewById(R.id.img_button));
         *
         * img.setOnClickListener(new OnClickListener() {
         *
         * @Override public void onClick(View v) { popupActivity.dismiss();
         *
         * } });
         */
        // WebView web = ((WebView) popupActivity.findViewById(R.id.web_about));
        // web.loadData(aboutContent, "text/html", "UTF-8");
        // popupActivity.getWindow().setWindowAnimations(R.anim.zoom_display);
        // popupActivity.getWindow().setBackgroundDrawableResource(
        // android.R.drawable.alert_light_frame);
        // popupActivity.setCanceledOnTouchOutside(true);
        // popupActivity.setCancelable(true);
        // popupActivity.show();
    }

}