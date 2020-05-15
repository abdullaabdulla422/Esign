package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalListSelectorObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import com.google.android.gms.common.api.GoogleApiClient;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class Summaryactivity extends AppCompatActivity {
    private double dpi;
    private TextView companyvalues;
    private static Dialog dialog;
    TextView due;
    RentalCheck rentalcheckin;
    CheckinEqupments checkinequpments;
    int getrentalcheck;
    private ArrayList<String> cheklistArray;
    RentalChecklistPDF checklistpdf;
    private ArrayList<CustomerNameMail> customeremails;
    EqupmentSubStatus equpsubStatus;
    private static final int CUSTOM_DIALOG_ID = 0;
    private static final int CUSTOM_DIALOG_IDD = 0;
    private ArrayList<RentalDetails> selectedDetail;
    private RentalDetails detail;
    private ArrayList<RentalDetails> detailarray;
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
    private LinearLayout Switch_Background;

    int TransportListIndex = 0;
    ArrayList<String> Kequipnu = new ArrayList<String>();
    ArrayList<GetTransportDetailsDescOjbect> getTransportDetailsDescOjbects = new ArrayList<>();

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
    private TextView contact;
    private TextView date;
    private TextView orderno;
    TextView Hourmeter;
    private TextView txtBack;
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
    ArrayList<CheckList> check;

    private User objUser = null;

    private final Context context = this;
    private CheckinDetail checkindetails;

    private ArrayList<String> Notes;
    private ArrayList<String> Type;
    private ArrayList<byte[]> imageattach = new ArrayList<>();

    public static int i = 0;
    private static int j = 0;
    public static int z;
    private int count;
    private Bitmap decode;
    private int data = 0;
    String[] str1;
    String str;
    public boolean issignneeded;
    int index = 0;
    private User user;
    String str2;
    private ArrayList<String> mylabelstr;
    private ArrayList<Integer> camera;
    private FrameLayout footerLayout;
    private RentalListSelectorObject rentalCheckinList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private LinearLayout tab_rentalSummary;
    private LinearLayout layout_rentalSummary;
    private ImageView drop_down_img;

    // LinearLayout lllayout;
    @SuppressLint({"InflateParams", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.summary_checklist);
        setContentView(R.layout.summary_checklist_2);
        hourMeter = (TextView) findViewById(R.id.hourmetervalues);
        FuelLevel = (TextView) findViewById(R.id.fuellevelvalues);
        Due = (TextView) findViewById(R.id.duestatusvalues);
        SessionData.getInstance().setMail(0);
        detail = new RentalDetails();
        Log.d("condition", "" + SessionData.getInstance().getLabelcondition());
        date = (TextView) findViewById(R.id.datevalues);
        cancelsummary = (Button) findViewById(R.id.btncancelsummary);
        reviewsummary = (Button) findViewById(R.id.btnreviewsummary);
        contact = (TextView) findViewById(R.id.Contactvalues);
        companyvalues = (TextView) findViewById(R.id.Companyvalues);
        Substatus = (TextView) findViewById(R.id.substatusvalues);
        Substatusdec = (TextView) findViewById(R.id.descvalues);
        currentstatus = (TextView) findViewById(R.id.currentstatusvalues);
        orderno = (TextView) findViewById(R.id.ordervalues);
        j = SessionData.getInstance().getChecklistdata();
        vent = (TextView) findViewById(R.id.des);
        spin = (TextView) findViewById(R.id.dd);
        partsList = new ArrayList<EquipmentCheckParts>();
        parts = new EquipmentCheckParts();
        user = SessionData.getInstance().getUser();
        camera = new ArrayList<Integer>();

        tab_rentalSummary = (LinearLayout) findViewById(R.id.tab_rentalSummary);
        layout_rentalSummary = (LinearLayout) findViewById(R.id.layout_rentalSummary);
        drop_down_img = (ImageView) findViewById(R.id.drop_down_img);

        TransportListIndex = SessionData.getInstance().getTransportListIndex();

        getTransportDetailsDescOjbects = SessionData.getInstance().getTransportDetailsDescOjbects();

        if (getTransportDetailsDescOjbects != null) {
            for (int i = 0; i < getTransportDetailsDescOjbects.size(); i++) {
                Kequipnu = getTransportDetailsDescOjbects.get(0).getKequipnu();
            }

        }


        tab_rentalSummary.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (layout_rentalSummary.getVisibility() == View.VISIBLE) {
                        layout_rentalSummary.setVisibility(View.GONE);
    //                    drop_down_img.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_down_right));
                        drop_down_img.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down_right));


                    } else {
                        layout_rentalSummary.setVisibility(View.VISIBLE);
    //                    drop_down_img.setImageDrawable(getApplicationContext().getDrawable(R.drawable.arrow_down_down));
                        drop_down_img.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down_down));

                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


        Notes = SessionData.getInstance().getWalkAroundNotes();
        Type = SessionData.getInstance().getWalkAroundType();
        imageattach = SessionData.getInstance().getWalkaroundgeneralimages();

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

        checkindetails = new CheckinDetail();
        // sadap = new Summaryadapter(Summaryactivity.this, summarydesc);
        // li.setAdapter(sadap);
        // li.clearChoices();
        // sadap.notifyDataSetChanged();
        hourMeter.setText(SessionData.getInstance().getHrmeter());
        FuelLevel.setText(SessionData.getInstance().getFuel());
        Due.setText(SessionData.getInstance().getDuestatus());
        if (SessionData.getInstance().getSubstatus() != null) {
            String strSubStatus = SessionData.getInstance().getSubstatus().trim();
            Substatus.setText(strSubStatus);
        } else {
            Substatus.setText("");
        }

        currentstatus.setText(SessionData.getInstance().getCurrentstatus());
        Substatusdec.setText(SessionData.getInstance().getEqupsubstatusdes());
        companyvalues.setText(SessionData.getInstance().getCustname());
        contact.setText(SessionData.getInstance().getContactsummary());
        date.setText(SessionData.getInstance().getDatasummary());
        orderno.setText(String.valueOf(SessionData.getInstance().getOrder()));
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


        reviewsummary.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                showDialog(CUSTOM_DIALOG_ID);

                // summary(Summaryactivity.this);

            }
        });
        cancelsummary.setOnClickListener(new OnClickListener() {

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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        initSummaryList();
    }

    public void initSummaryList() {

        txtCheckList = (TextView) findViewById(R.id.check_list);
        txtWalkAround = (TextView) findViewById(R.id.walk_around);
        Switch_Background = (LinearLayout) findViewById(R.id.layout_global);


        txtCheckList.setBackgroundResource(R.color.white);
        txtCheckList.setTextColor(Color.parseColor("#007aff"));
        txtWalkAround.setBackgroundResource(R.color.intake_bg);
        txtWalkAround.setTextColor(Color.parseColor("#ffffff"));


        dialog_ListView = (ListView) findViewById(R.id.listViewsummary);
        dialog_image = (ImageView) findViewById(R.id.img_button);

        accept = (Button) findViewById(R.id.accept);

        cancell = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (SessionData.getInstance().getIssign() == false) {
                    SessionData.getInstance().setSignData(null);
                    new AsynMails().execute();


                } else {
                    if (SessionData.getInstance().getTransportTransfer() == 1) {
                        if ((TransportListIndex + 1) == Kequipnu.size()) {
                            Intent intent = new Intent(Summaryactivity.this,
                                    RentalSignature.class);
                            startActivity(intent);
                            finish();
                        } else {

                            SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                            new AysncSubmitData().execute();

                        }

                    } else {
                        Intent intent = new Intent(Summaryactivity.this,
                                RentalSignature.class);
                        startActivity(intent);

                    }

                }

            }
        });
        cancell.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//				startActivity(new Intent(Summaryactivity.this, CustomizedPartsCheck.class));
                finish();
//				dialog.dismiss();
            }
        });

        dialog_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				dialog.dismiss();
            }
        });

        txtCheckList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckList.setBackgroundResource(R.color.intake_bg);
                txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                txtWalkAround.setBackgroundResource(R.color.white);
                txtWalkAround.setTextColor(Color.parseColor("#007aff"));
                //Switch_Background.setBackgroundResource(R.drawable.borders);

                sadap = new Summaryadapter(Summaryactivity.this, summarydesc);
                dialog_ListView.setAdapter(sadap);
                dialog_ListView.clearChoices();
                sadap.notifyDataSetChanged();
            }
        });

        txtWalkAround.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckList.setBackgroundResource(R.color.white);
                txtCheckList.setTextColor(Color.parseColor("#007aff"));
                txtWalkAround.setBackgroundResource(R.color.intake_bg);
                txtWalkAround.setTextColor(Color.parseColor("#ffffff"));
                //Switch_Background.setBackgroundResource(R.drawable.borders);

                summaryWalkadapter = new SummaryWalkadapter(Summaryactivity.this, Notes);
                dialog_ListView.setAdapter(summaryWalkadapter);
                dialog_ListView.clearChoices();
                summaryWalkadapter.notifyDataSetChanged();
            }
        });
        summaryWalkadapter = new SummaryWalkadapter(Summaryactivity.this, Notes);
        dialog_ListView.setAdapter(summaryWalkadapter);
        dialog_ListView.clearChoices();
        summaryWalkadapter.notifyDataSetChanged();
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


    @SuppressLint("SetTextI18n")
    protected Dialog onCreateDialog(int id) {

        final Dialog dialog = new Dialog(Summaryactivity.this);

        switch (id) {
            case CUSTOM_DIALOG_ID:

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				dialog.setContentView(R.layout.summarylistview);
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


                txtCheckList = (TextView) dialog.findViewById(R.id.check_list);
                txtWalkAround = (TextView) dialog.findViewById(R.id.walk_around);
                Switch_Background = (LinearLayout) dialog.findViewById(R.id.layout_global);


                txtCheckList.setBackgroundResource(R.color.white);
                txtCheckList.setTextColor(Color.parseColor("#000000"));
                txtWalkAround.setBackgroundResource(R.color.intake_bg);
                txtWalkAround.setTextColor(Color.parseColor("#ffffff"));


                dialog_ListView = (ListView) dialog
                        .findViewById(R.id.listViewsummary);
                dialog_image = (ImageView) dialog.findViewById(R.id.img_button);

                accept = (Button) dialog.findViewById(R.id.accept);

                cancell = (Button) dialog.findViewById(R.id.cancel);
                accept.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (SessionData.getInstance().getIssign() == false) {
                            SessionData.getInstance().setSignData(null);
                            new AsynMails().execute();


                        } else {
                            if (SessionData.getInstance().getTransportTransfer() == 1) {
                                if ((TransportListIndex + 1) == Kequipnu.size()) {
                                    Intent intent = new Intent(Summaryactivity.this,
                                            RentalSignature.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    SessionData.getInstance().setTransportListIndex(TransportListIndex + 1);
                                    new AysncSubmitData().execute();

                                }

                            } else {
                                Intent intent = new Intent(Summaryactivity.this,
                                        RentalSignature.class);
                                startActivity(intent);

                            }

                        }

                    }
                });
                cancell.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        dialog.dismiss();
                    }
                });

                dialog_image.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                txtCheckList.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtCheckList.setBackgroundResource(R.color.intake_bg);
                        txtCheckList.setTextColor(Color.parseColor("#ffffff"));
                        txtWalkAround.setBackgroundResource(R.color.white);
                        txtWalkAround.setTextColor(Color.parseColor("#000000"));
                        //Switch_Background.setBackgroundResource(R.drawable.borders);

                        sadap = new Summaryadapter(Summaryactivity.this, summarydesc);
                        dialog_ListView.setAdapter(sadap);
                        dialog_ListView.clearChoices();
                        sadap.notifyDataSetChanged();
                    }
                });


                txtWalkAround.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtCheckList.setBackgroundResource(R.color.white);
                        txtCheckList.setTextColor(Color.parseColor("#007aff"));
                        txtWalkAround.setBackgroundResource(R.color.intake_bg);
                        txtWalkAround.setTextColor(Color.parseColor("#ffffff"));
                        //Switch_Background.setBackgroundResource(R.drawable.borders);

                        summaryWalkadapter = new SummaryWalkadapter(Summaryactivity.this, Notes);
                        dialog_ListView.setAdapter(summaryWalkadapter);
                        dialog_ListView.clearChoices();
                        summaryWalkadapter.notifyDataSetChanged();
                    }
                });
                summaryWalkadapter = new SummaryWalkadapter(Summaryactivity.this, Notes);
                dialog_ListView.setAdapter(summaryWalkadapter);
                dialog_ListView.clearChoices();
                summaryWalkadapter.notifyDataSetChanged();

        }
        return dialog;
    }


    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {

        String resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
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


                rentalcheckin = WebServiceConsumer.getInstance().RentalCheckinV3(
                        SessionData.getInstance().getRentalId(), 0,
                        SessionData.getInstance().getInspectionID(),
                        user.getUserDescription(),
                        SessionData.getInstance().getRentalIn_Out(), SessionData.getInstance().getTransportOrderNum(), SessionData.getInstance().getTransportcallNum());


                if (SessionData.getInstance().getRentalCheckinResult() == 1) {


                    if (rentalcheckin.getMessage().contains("Session")) {
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(Summaryactivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(rentalcheckin.getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yes.setOnClickListener(new OnClickListener() {
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
                                    "", "", "", 1, SessionData.getInstance().getRentalIn_Out()
                                    , "");
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
            if (checkindetails != null) {
                SessionData.getInstance().getDataHandlelist().clear();
                SessionData.getInstance().getWalkAroundNotes().clear();
                SessionData.getInstance().getWalkaroundgeneralimages().clear();
                SessionData.getInstance().getWalkAroundType().clear();
                SessionData.getInstance().getWalkAroundCategoryId().clear();


                SessionData.getInstance().getSelectedDetail().clear();
                SessionData.getInstance().getCheckListData().clear();
                SessionData.getInstance().getDataHandlelist().clear();
                SessionData.getInstance().getKpartlist().clear();
                SessionData.getInstance().getHourmeterlist().clear();
                SessionData.getInstance().getEqpStatus().clear();


                Toast.makeText(Summaryactivity.this,
                        "Inspection Completed Successfully", Toast.LENGTH_SHORT).show();

                new AsynccheckinDetail().execute();

            }


        }


    }

    public class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
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
            ProgressBar.dismiss();
            if (user != null && user.getUsername() != null) {

                ProgressBar.dismiss();
                SessionData.getInstance().setUser(user);


                new AysncSubmitData().execute();

            } else {
                ProgressBar.dismiss();

            }
        }
    }


    private class AsynccheckinDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {


                detailarray = WebServiceConsumer.getInstance().getRentalDetail1(
                        Kequipnu.get(TransportListIndex + 1), user.getUserDescription());

            } catch (java.net.SocketTimeoutException e) {
                detailarray = null;

            } catch (Exception e) {
                detailarray = null;


                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ProgressBar.dismiss();
            if (detailarray != null && detailarray.size() > 0) {

                if (detailarray.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();

                    SessionData.getInstance().setDetail(detailarray);
                    SessionData.getInstance().getSelectedDetail().add(detailarray.get(0));
                    SessionData.getInstance().setOrder(detailarray.get(0).getOrderNo());
                    SessionData.getInstance().setCustname(detailarray.get(0).getCustName());

                    SessionData.getInstance().setContactsummary(detailarray.get(0).getContact());

                    SessionData.getInstance().setDatasummary(formattedDateFromString(detailarray.get(0).getOrderDate()));
                    SessionData.getInstance().setEnteredEquipID(
                            Kequipnu.get(TransportListIndex + 1));
//					SessionData.getInstance().setEnteredEquipID(
//							equipmentId.getText().toString());

                    SessionData.getInstance().getKpartlist().clear();
                    SessionData.getInstance().getHourmeterlist()
                            .clear();
                    SessionData.getInstance().getPmspeclist()
                            .clear();
                    SessionData.getInstance().getDuestatuslist()
                            .clear();
                    SessionData.getInstance().getEqupmentmeterlist()
                            .clear();
                    SessionData.getInstance().getEqupmentreadinglist()
                            .clear();
                    SessionData.getInstance().getLastdatelist()
                            .clear();
                    SessionData.getInstance().getLasthourlist()
                            .clear();
                    new AsyncRentalDetail().execute();

                } else {

                    if (detailarray.get(0).getMessage().contains("Session")) {
                        new AsyncSessionLoginTask1().execute();
                    } else {
                        dialog = new Dialog(Summaryactivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg=dialog.findViewById(R.id.close_img);

                        Message.setText(detailarray.get(0).getMessage());

                        closeImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
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


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(Summaryactivity.this,
                        "EquipmentID is invalid.");
            }

        }

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

    private class AsyncSessionLoginTask1 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
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
                    dialog = new Dialog(Summaryactivity.this);
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

                            Intent inspection = new Intent(Summaryactivity.this,
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
                AlertDialogBox.showAlertDialog(Summaryactivity.this,
                        "Data is not Found");
            }


        }
    }


    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        LinkedHashMap<RentalDetails, String> data;

        protected void onPreExecute() {
            data = new LinkedHashMap<>();
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
        }

        ;

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... params) {

            try {


                RentalDetails rentDetail;

                rentDetail = detailarray.get(0);

                SessionData.getInstance().setRentalId(
                        detailarray.get(0).getRentalID());
                SessionData.getInstance().setInspectionID(detailarray.get(0).getInspectionId());

//
                rentalCheckinList = WebServiceConsumer.getInstance()
                        .RentalCheckinList("", rentDetail.getkPart(),
                                rentDetail.getEqupId(),
                                rentDetail.getRentalID(),
                                user.getUserDescription());

                //   if (SessionData.getInstance().getRentalListSelector() == 0) {
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
                //    }


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


                    SessionData.getInstance().setChecklist(20);
                    SessionData.getInstance().setMulticheckinvalidate(1);

                    SessionData.getInstance().getCheckListData().putAll(data);

                    SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance().getKpartlist().get(0));

                    Log.d("Selected Equipment", "" + SessionData.getInstance().getKpartlist().get(0));


                    Intent intent = new Intent(Summaryactivity.this,
                            RentalInspectionWalkAround.class);

                    startActivity(intent);
                    finish();


                    finish();

                } else {
                    //  if (rentalCheckinList.getMessage().length() == 0) {
                    SessionData.getInstance().setChecklist(20);
                    SessionData.getInstance().setMulticheckinvalidate(1);

                    SessionData.getInstance().getCheckListData().putAll(data);

                    SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance().getKpartlist().get(0));

                    Log.d("Selected Equipment", "" + SessionData.getInstance().getKpartlist().get(0));


                    Intent intent = new Intent(Summaryactivity.this,
                            RentalInspectionWalkAround.class);

                    startActivity(intent);
                    finish();


                }

            }

        }

    }

    protected Dialog onCreateDialogg(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_IDD:
                dialog = new Dialog(Summaryactivity.this);
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
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);


                holder.view.setOnClickListener(new OnClickListener() {

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
                        close.setOnClickListener(new OnClickListener() {

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

            if (imageview.get(position).length() == 0) {
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
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;

                byte[] data = imageattach.get(position);
                Bitmap bmp_1 = BitmapFactory.decodeByteArray(data, 0,
                        data.length, options);

                holder.thumbnail.setImageBitmap(bmp_1);


                holder.view.setOnClickListener(new OnClickListener() {

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
                        close.setOnClickListener(new OnClickListener() {

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

            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {

            try {

//				objUser = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                customeremails = WebServiceConsumer.getInstance().customermailsv1(
                        user.getUserDescription(), SessionData.getInstance().getKcustadd(), SessionData.getInstance().getCustadd(), "R");


                Log.d("summarykcustadd", "" + SessionData.getInstance().getKcustadd());
                Log.d("summarycustadd", "" + SessionData.getInstance().getCustadd());

            } catch (SocketTimeoutException e) {
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

                            new AsyncSessionLoginTask().execute();

                        } else {
                            dialog = new Dialog(Summaryactivity.this);
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
                            yes.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

//								Intent inspection = new Intent(Summaryactivity.this,
//										MainActivity.class);
//								startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    } else {
//					SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(Summaryactivity.this,
                                CheckInMailDetails.class);

                        startActivity(intent);
                        finish();
                    }
                } else {
//					SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                    Intent intent = new Intent(Summaryactivity.this,
                            CheckInMailDetails.class);

                    startActivity(intent);
                    finish();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(Summaryactivity.this,
                        "Data is not found.");
            }
        }

    }


    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(Summaryactivity.this);
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
                    dialog = new Dialog(Summaryactivity.this);
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

                            Intent inspection = new Intent(Summaryactivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);

                    new AsynMails().execute();


//					if(session == 0){
//						new Asyncequpment().execute();
//					}
//					else if(session == 1){
//						new Asyncequpmentdec().execute();
//					}


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(Summaryactivity.this,
                        "Data is not Found");
            }


        }
    }


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
        Summaryadapter sadap = new Summaryadapter(Summaryactivity.this,
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
