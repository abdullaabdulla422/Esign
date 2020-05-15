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

import com.ebs.rental.objects.CheckinDetail;
import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
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
public class GeneralSummaryActivity extends AppCompatActivity {

    private double dpi;

    private static Dialog dialog;
    TextView due;
    private RentalCheck rentalcheckin;
    private CheckinEqupments checkinequpments;
    private int getrentalcheck;
    private ArrayList<String> cheklistArray;
    private RentalChecklistPDF checklistpdf;
    private Customeremails customeremails;
    EqupmentSubStatus equpsubStatus;
    private static final int CUSTOM_DIALOG_ID = 0;
    private static final int CUSTOM_DIALOG_IDD = 0;
    ArrayList<RentalDetails> selectedDetail;
    private RentalDetails detail;
    private Button reviewsummary;
    private Button cancelsummary;
    private ArrayList<String> summarydesc;
    ArrayList<CheckList> checkList;
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
    private ImageView imgBack;
    private TextView hourMeter;
    TextView date;
    TextView Hourmeter;
    private TextView FuelLevel;
    private TextView Due;
    private TextView Substatus;
    private TextView Substatusdec;
    TextView Equpstatus;
    TextView txtsubStatus;
    TextView Fuellevel;
    TextView substatus;
    TextView substatushide;
    TextView eqpStatus;
    private TextView currentstatus;
    private TextView vent;
    private TextView spin;

    private TextView Make;
    private TextView Model;
    private TextView SerialNo;
    private TextView UnityId;
    private TextView txtBack;

    ArrayList<CheckList> check;
    private final Context context = this;
    private CheckinDetail checkindetails;

    private ArrayList<String> Notes;
    private ArrayList<String> Type;
    private ArrayList<byte[]> imageattach= new ArrayList<>();
    private GeneralEquipmentSearchObject generalEquipment;

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

    @SuppressLint({"InflateParams", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.general_summery);
        hourMeter = (TextView) findViewById(R.id.hourmetervalues);
        FuelLevel = (TextView) findViewById(R.id.fuellevelvalues);
        Due = (TextView) findViewById(R.id.duestatusvalues);
        SessionData.getInstance().setMail(0);
        detail = new RentalDetails();
        Log.d("condition", "" + SessionData.getInstance().getLabelcondition());

        Make = (TextView)findViewById(R.id.make_values);
        SerialNo = (TextView)findViewById(R.id.serial_no);
        Model = (TextView)findViewById(R.id.model_value);
        UnityId = (TextView)findViewById(R.id.unit_id_value);

        cancelsummary = (Button) findViewById(R.id.btncancelsummary);
        reviewsummary = (Button) findViewById(R.id.btnreviewsummary);

        Substatus = (TextView) findViewById(R.id.substatusvalues);
        Substatusdec = (TextView) findViewById(R.id.descvalues);
        currentstatus = (TextView) findViewById(R.id.currentstatusvalues);
        generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

        Make.setText(generalEquipment.getMfg());
        SerialNo.setText(generalEquipment.getSerialno());
        Model.setText(generalEquipment.getModel());
        UnityId.setText(generalEquipment.getEquipmentid());

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

        txtCheckList = (TextView) findViewById(R.id.check_list);
        txtWalkAround = (TextView) findViewById(R.id.walk_around);
        txtCheckList.setBackgroundResource(R.color.intake_bg);
        txtCheckList.setTextColor(Color.parseColor("#ffffff"));
        txtWalkAround.setBackgroundResource(R.color.white);
        txtWalkAround.setTextColor(Color.parseColor("#007AE0"));



        dialog_ListView = (ListView)
                findViewById(R.id.listViewsummary);
        dialog_image = (ImageView) findViewById(R.id.img_button);





        txtCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckList.setBackgroundResource(R.color.intake_bg);
                txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                txtWalkAround.setBackgroundResource(R.color.white);
                txtWalkAround.setTextColor(Color.parseColor("#007AE0"));

                sadap = new Summaryadapter(GeneralSummaryActivity.this, summarydesc);
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


                summaryWalkadapter = new SummaryWalkadapter(GeneralSummaryActivity.this, Notes);
                dialog_ListView.setAdapter(summaryWalkadapter);
                dialog_ListView.clearChoices();
                summaryWalkadapter.notifyDataSetChanged();
            }
        });
        sadap = new Summaryadapter(GeneralSummaryActivity.this, summarydesc);
        dialog_ListView.setAdapter(sadap);
        dialog_ListView.clearChoices();
        sadap.notifyDataSetChanged();


        j = SessionData.getInstance().getChecklistdata();
        vent = (TextView) findViewById(R.id.des);
        spin = (TextView) findViewById(R.id.dd);
        partsList = new ArrayList<EquipmentCheckParts>();
        parts = new EquipmentCheckParts();
        user = SessionData.getInstance().getUser();
        camera = new ArrayList<Integer>();

        Notes = SessionData.getInstance().getWalkAroundNotes();
        Type = SessionData.getInstance().getWalkAroundType();
        imageattach = SessionData.getInstance().getWalkaroundgeneralimages();


        SessionData.getInstance().setSignData(null);
        cheklistArray = new ArrayList<String>(SessionData.getInstance()
                .getCheckListData().values());
        footerLayout = (FrameLayout) getLayoutInflater().inflate(
                R.layout.footer_view, null);

        Log.d("The Checklist Array Size", "" + cheklistArray.size());

        chk = new ArrayList<String>();
        status = new ArrayList<String>();
        label = new ArrayList<String>();
        image = new ArrayList<String>();
        imageview = new ArrayList<String>();

        checkindetails = new CheckinDetail();

        hourMeter.setText(SessionData.getInstance().getGeneral_hourmeter());
        FuelLevel.setText(SessionData.getInstance().getGeneral_fuelmeter());
        Due.setText(SessionData.getInstance().getGeneral_dueStatus());
        Substatus.setText(SessionData.getInstance().getGeneral_subStatus());
        currentstatus.setText(SessionData.getInstance().getGeneral_currentStatus());
        Substatusdec.setText(SessionData.getInstance().getGeneral_description());



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

        // chk.add(CheckinListData)

        data = status.size();
        Log.d("count of list", "" + data);


        reviewsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (SessionData.getInstance().getIssign() == false) {
                    SessionData.getInstance().setSignData(null);
                    // new AsynMails().execute();
                    new AysncSubmitData().execute();

                } else {
                    Intent intent = new Intent(GeneralSummaryActivity.this,
                            General_Signature_Capture.class);
                    startActivity(intent);
                }

             //   showDialog(CUSTOM_DIALOG_ID);

                // summary(Summaryactivity.this);

            }
        });
        cancelsummary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                SessionData.getInstance().setLabelcondition(1);
                Log.d("condition", ""
                        + SessionData.getInstance().getLabelcondition());


            }
        });

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

        final Dialog dialog = new Dialog(GeneralSummaryActivity.this);

        switch (id) {
            case CUSTOM_DIALOG_ID:

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.summarylistview);

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

                        if (SessionData.getInstance().getIssign() == false) {
                            SessionData.getInstance().setSignData(null);
                           // new AsynMails().execute();
                            new AysncSubmitData().execute();

                        } else {
                            Intent intent = new Intent(GeneralSummaryActivity.this,
                                    General_Signature_Capture.class);
                            startActivity(intent);
                        }

                    }
                });
                cancell.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog_image.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
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

                        sadap = new Summaryadapter(GeneralSummaryActivity.this, summarydesc);
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


                        summaryWalkadapter = new SummaryWalkadapter(GeneralSummaryActivity.this, Notes);
                        dialog_ListView.setAdapter(summaryWalkadapter);
                        dialog_ListView.clearChoices();
                        summaryWalkadapter.notifyDataSetChanged();
                    }
                });
                sadap = new Summaryadapter(GeneralSummaryActivity.this, summarydesc);
                dialog_ListView.setAdapter(sadap);
                dialog_ListView.clearChoices();
                sadap.notifyDataSetChanged();

        }
        return dialog;
    }


    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {

        String resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(GeneralSummaryActivity.this);
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

                rentalcheckin = WebServiceConsumer.getInstance().generalCheckin(
                        0, 0,
                        0, "G",
                        user.getUserDescription());
                if (SessionData.getInstance().getRentalCheckinResult() == 1) {

                    if(rentalcheckin.getMessage().contains("Session")){
                        new AsyncLoginTask().execute();
                    }
                    else{
                        dialog = new Dialog(GeneralSummaryActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(rentalcheckin.getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                                    generalEquipment.getEquipmentid(),
                                    Integer.parseInt(SessionData.getInstance().getGeneral_hourmeter()),
                                    SessionData.getInstance().getFuel(),
                                    "0",
                                    user.getUserDescription(),
                                    SessionData.getInstance().getSubstatus(),
                                    SessionData.getInstance().getEqupsubstatusdes(),
                                    SessionData.getInstance().getGeneral_currentStatus(),
                                    str, "", "",2,"G","");
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
                                    .GeneralCheckInImages(
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
                                            generalEquipment.getEquipmentid());
                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                        }

                    }
                    checklistpdf = WebServiceConsumer.getInstance()
                            .generalChecklistPdf(
                                    user.getUserDescription(),
                                    generalEquipment.getEquipmentid(),
                                    0,
                                    "0", "", "", "",
                                    0,Integer.parseInt(rentalcheckin.getResult()),user.getUsername());
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

            if(checklistpdf==null){

            }
            else{
                Toast.makeText(GeneralSummaryActivity.this,
                        "Inspection Completed Successfully", Toast.LENGTH_SHORT).show();

                if(SessionData.getInstance().getScanNavigation()==0){
//                    Intent inspection = new Intent(GeneralSummaryActivity.this, ScannerActivity.class);
//                    startActivity(inspection);

                    Intent inspection = new Intent(GeneralSummaryActivity.this, EbsMenu.class);
                    startActivity(inspection);
                }else{
//                    Intent intent = new Intent(GeneralSummaryActivity.this,
//                            ScannerProductActivity.class);
//                    startActivity(intent);

                    Intent inspection = new Intent(GeneralSummaryActivity.this, EbsMenu.class);
                    startActivity(inspection);
                }
//                Intent hr = new Intent(GeneralSummaryActivity.this,
//                        ScannerActivity.class);
//                startActivity(hr);
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
            ProgressBar.showCommonProgressDialog(GeneralSummaryActivity.this);
        };

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
                if(user.getUserId()==0){
                    dialog = new Dialog(GeneralSummaryActivity.this);
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



                            Intent inspection = new Intent(GeneralSummaryActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else{
                    SessionData.getInstance().setUser(user);
//                SessionData.getInstance().setUsername(objUser.getUsername());
//
//                SessionData.getInstance().setTemp_Username(objUser.getUsername());
//                SessionData.getInstance().setTemp_password(objUser.getPassword());

//                SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());

                    new AysncSubmitData().execute();
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
                dialog = new Dialog(GeneralSummaryActivity.this);
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

            return data;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

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

                holder.thumbnail = (ImageView) convertView
                        .findViewById(R.id.thumbnail);

                holder.view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

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
                holder.thumbnail.setVisibility(View.VISIBLE);
                String s = imageview.get(holder.ref);
                byte[] decodestring = Base64.decode(s, Base64.DEFAULT);
                decode = BitmapFactory.decodeByteArray(decodestring, 0,
                        decodestring.length);
                holder.thumbnail.setImageBitmap(decode);
            }

            if (camera.get(position) == 0) {
                // holder.view.setEnabled(false);
                holder.view.setVisibility(View.GONE);
                holder.thumbnail.setVisibility(View.INVISIBLE);
            }



            if (imageview.get(position).length() == 0)
            {
                holder.view.setVisibility(View.INVISIBLE);
                holder.thumbnail.setVisibility(View.INVISIBLE);
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
            list = null;

        }

        @Override
        public int getCount() {

            return Notes.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

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
//			   holder.txtNotes.setText(mylabelstr.get(position).replace("\\[|\\]",
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
//    private class AsynMails extends AsyncTask<Void, Void, Void> {
//        protected void onPreExecute() {
//
//            ProgressBar.showCommonProgressDialog(GeneralSummaryActivity.this);
//        };
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                customeremails = WebServiceConsumer.getInstance().customermails(
//                        SessionData.getInstance().getTemp_userToken(),SessionData.getInstance().getKcustadd(),SessionData.getInstance().getCustadd(),"R");
//
//
//                Log.d("summarykcustadd", ""+SessionData.getInstance().getKcustadd());
//                Log.d("summarycustadd",""+SessionData.getInstance().getCustadd());
//
//            } catch (java.net.SocketTimeoutException e) {
//                customeremails = null;
//
//            } catch (Exception e) {
//                customeremails = null;
//
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            ProgressBar.dismiss();
//            if (customeremails != null){
//
//                if(SessionData.getInstance().getCustomeremailsResult()==1){
//                    dialog = new Dialog(GeneralSummaryActivity.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.message);
//
//
//                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                    Message.setText(customeremails.getMessage());
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(GeneralSummaryActivity.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    dialog.show();
//                }
//                else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
//
//                    Intent intent = new Intent(GeneralSummaryActivity.this,
//                            General_email.class);
//
//                    startActivity(intent);
//                    finish();
//                }
//            }
//            else{
//                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(GeneralSummaryActivity.this,
//                        "Data is not found.");
//            }
//        }
//
//    }



    class ViewHolder {
        TextView txtdesc, txtdescvalues, txtstatus, txtimage;
        ImageView view, thumbnail;
        int ref;

    }

    class Holder {
        TextView txtType, txtNotes;
        ImageView view, thumbnail;
        int ref;

    }

    public void summary(Activity activity) {

        final Dialog popupActivity = new Dialog(activity);
        // popupActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupActivity.setContentView(R.layout.summarylistview);
        ListView li = (ListView) findViewById(R.id.listViewsummary);
        // summarydesc = new ArrayList<String>();
        Summaryadapter sadap = new Summaryadapter(GeneralSummaryActivity.this,
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
