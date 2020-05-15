package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.TabFragments.TransportationFragment;
import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalListSelectorObject;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TransportListDetails extends AppCompatActivity {

    TextView txtCustNo, txtCustName, txtShipto, txtAdd, txtCity, txtState, txtZip, txtPhone;
    TextView TxtBack;
    ImageView ImgBack, imgInspection;
    private TransportListObject transportObject;
    private ArrayList<RentalDetails> detail;
    ArrayList<GetTransportDetailsDescOjbect> getTransportDetailsDescOjbects = new ArrayList<>();
    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;
    ListView list;
    LinearLayout trans_delivery_layout, layout_inspection;
    TextView txtNotes1, txtNotes2, txtNotes3;
    Button btnInspection, btnSpecialinstructions;
    private RentalListSelectorObject rentalCheckinList;

    ArrayList<String> Eqpdesc = new ArrayList<String>();
    ArrayList<String> Kequipnu = new ArrayList<String>();
    ArrayList<String> Kmfg = new ArrayList<String>();
    ArrayList<String> Kmodel = new ArrayList<String>();
    ArrayList<String> Kserialn = new ArrayList<String>();

    int TransportListIndex = 0;

    private TransferEquipmentSearchObject transferEquipmentSearchObject;

    int count;
    private User user;
    String notificationResult = "";

    private TrasnportAdapter adap;

    private static Dialog dialog;
    private ArrayList<TransportListObject> transportTruckList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.transport_delivery_details);

        getTransportDetailsDescOjbects = SessionData.getInstance().getTransportDetailsDescOjbects();
        SessionData.getInstance().setTransportListIndex(TransportListIndex);
        SessionData.getInstance().getCheckinequip().clear();
        SessionData.getInstance().getCheckinequiplocal().clear();
        SessionData.getInstance().getDataHandlelist().clear();
        txtCustNo = findViewById(R.id.transport_custnum);
        txtCustName = findViewById(R.id.transport_custname);
        txtShipto = findViewById(R.id.transport_shiptotext);
        txtAdd = findViewById(R.id.transport_address);
        txtCity = findViewById(R.id.transport_custcity);
        txtState = findViewById(R.id.transport_custstate);
        txtZip = findViewById(R.id.transport_custzip);
        txtPhone = findViewById(R.id.transport_phno);
        TxtBack = findViewById(R.id.backtext);
        ImgBack = findViewById(R.id.backicon);

        user = SessionData.getInstance().getUser();

        txtNotes1 = findViewById(R.id.transport_note1);
        txtNotes2 = findViewById(R.id.transport_note2);
        txtNotes3 = findViewById(R.id.transport_note3);

        btnInspection = findViewById(R.id.btn_inspection);
        btnSpecialinstructions = findViewById(R.id.btn_special_instructions);

        trans_delivery_layout = findViewById(R.id.transport_delivery_layout);
        layout_inspection = findViewById(R.id.layout_inspection);

        list = findViewById(R.id.list);

        transportObject = SessionData.getInstance().getTransportObject();


        if (getTransportDetailsDescOjbects.size() != 0) {
            Eqpdesc = getTransportDetailsDescOjbects.get(0).getEqpdesc();
            Kequipnu = getTransportDetailsDescOjbects.get(0).getKequipnu();
            Kmfg = getTransportDetailsDescOjbects.get(0).getKmfg();
            Kmodel = getTransportDetailsDescOjbects.get(0).getKmodel();
            Kserialn = getTransportDetailsDescOjbects.get(0).getKserialn();
            count = Kserialn.size();
            Log.d("Count", "" + count);
            adap = new TrasnportAdapter(TransportListDetails.this,
                    Eqpdesc, Kequipnu, Kmfg, Kmodel, Kserialn);
            list.setAdapter(adap);
            justifyListViewHeightBasedOnChildren(list);
        }


        txtCustNo.setText(transportObject.getKcustnum());
        txtCustName.setText(transportObject.getCustname());
        txtShipto.setText(transportObject.getCustsnum());
        txtAdd.setText(transportObject.getCustadd());
        txtCity.setText(transportObject.getCustcity());
        txtState.setText(transportObject.getCuststate());
        txtZip.setText(transportObject.getCustzip());
        txtPhone.setText(transportObject.getCustphone());

//        if (Integer.parseInt(transportObject.getMobapptrackstatus()) == 2) {
        if (transportObject.getAction().equalsIgnoreCase("D")) {
            btnInspection.setText("Ready to Inspection");
        }else {
            btnInspection.setText("Inspection");

        }

//        } else {
//            btnInspection.setText("Inspection");


//        }

        if (transportObject.getKordnum().length() != 0) {
            SessionData.getInstance().setTransportOrderNum(Integer.parseInt(transportObject.getKordnum()));
        }
        if (transportObject.getRecnum().length() != 0) {
            SessionData.getInstance().setTransportcallNum(Integer.parseInt(transportObject.getRecnum()));
        }


        txtNotes1.setVisibility(View.GONE);
        txtNotes2.setVisibility(View.GONE);
        txtNotes3.setVisibility(View.GONE);


        if ((transportObject.getNotes01().length() != 0) || (transportObject.getNotes02().length() != 0) || (transportObject.getNotes03().length() != 0) || (transportObject.getNotes04().length() != 0)) {
            btnSpecialinstructions.setVisibility(View.VISIBLE);
            layout_inspection.setVisibility(View.VISIBLE);
        } else {
            layout_inspection.setVisibility(View.GONE);
            btnSpecialinstructions.setVisibility(View.GONE);
        }


        TxtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTrasportTruck().execute();
            }
        });

        ImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTrasportTruck().execute();
            }
        });

        btnInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (/*transportObject.getAction().equalsIgnoreCase("T")
                        ||*/ transportObject.getAction().equalsIgnoreCase("D")) {

                    if (Integer.parseInt(transportObject.getMobapptrackstatus()) == 1) {

                        notificationDialog();

                    } else if (Integer.parseInt(transportObject.getMobapptrackstatus()) == 2) {

                        notificationDialog();

                    } else if (Integer.parseInt(transportObject.getMobapptrackstatus()) == 3) {

                        if (transportObject.getAction().contains("D")) {
                            SessionData.getInstance().setTransportTransfer(1);
                            SessionData.getInstance().setRentalIn_Out("O");

                            new AsynccheckoutDetail().execute();
                        } else if (transportObject.getAction().contains("P")) {
                            SessionData.getInstance().setTransportTransfer(1);

                            SessionData.getInstance().setRentalIn_Out("I");
                            new AsynccheckinDetail().execute();

                        } else if (transportObject.getAction().contains("T")) {

                            SessionData.getInstance().setTransportTransfer(1);
                            SessionData.getInstance().setTransferIn_out("O");
                            new AsyncTransferDetail().execute();

                        }
                    } else {
                        if (transportObject.getAction().contains("D")) {
                            SessionData.getInstance().setTransportTransfer(1);
                            SessionData.getInstance().setRentalIn_Out("O");

                            new AsynccheckoutDetail().execute();
                        } else if (transportObject.getAction().contains("P")) {
                            SessionData.getInstance().setTransportTransfer(1);

                            SessionData.getInstance().setRentalIn_Out("I");
                            new AsynccheckinDetail().execute();

                        } else if (transportObject.getAction().contains("T")) {

                            SessionData.getInstance().setTransportTransfer(1);
                            SessionData.getInstance().setTransferIn_out("O");
                            new AsyncTransferDetail().execute();

                        }
                    }
                }else {
                    if (transportObject.getAction().contains("D")) {
                        SessionData.getInstance().setTransportTransfer(1);
                        SessionData.getInstance().setRentalIn_Out("O");

                        new AsynccheckoutDetail().execute();
                    } else if (transportObject.getAction().contains("P")) {
                        SessionData.getInstance().setTransportTransfer(1);

                        SessionData.getInstance().setRentalIn_Out("I");
                        new AsynccheckinDetail().execute();

                    } else if (transportObject.getAction().contains("T")) {

                        SessionData.getInstance().setTransportTransfer(1);
                        SessionData.getInstance().setTransferIn_out("O");
                        new AsyncTransferDetail().execute();

                    }
                }


            }
        });

        btnSpecialinstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(TransportListDetails.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_special_instructions);
                TextView Message = dialog.findViewById(R.id.txt_special_instructions);
                TextView yes = dialog.findViewById(R.id.dialog_Ok);
                ImageView closeImg = dialog.findViewById(R.id.image_close);

                String Notes = "";


                if (transportObject.getNotes().length() != 0) {
                    Notes = transportObject.getNotes();
                }

                if (transportObject.getNotes01().length() != 0) {
                    Notes = Notes + "\n" + transportObject.getNotes01();
                }

                if (transportObject.getNotes02().length() != 0) {
                    Notes = Notes + "\n" + transportObject.getNotes02();
                }

                if (transportObject.getNotes03().length() != 0) {
                    Notes = Notes + "\n" + transportObject.getNotes03();
                }
                if (transportObject.getNotes04().length() != 0) {
                    Notes = Notes + "\n" + transportObject.getNotes04();
                }


                Message.setText(Notes);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

    }


    private class AsynccheckinDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                detail = WebServiceConsumer.getInstance().getRentalDetail1(
                        Kequipnu.get(0), user.getUserDescription());

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
                    SessionData.getInstance().getSelectedDetail().add(detail.get(0));
                    SessionData.getInstance().setOrder(detail.get(0).getOrderNo());
                    SessionData.getInstance().setCustname(detail.get(0).getCustName());

                    SessionData.getInstance().setContactsummary(detail.get(0).getContact());

                    SessionData.getInstance().setDatasummary(formattedDateFromString(detail.get(0).getOrderDate()));
                    SessionData.getInstance().setEnteredEquipID(
                            Kequipnu.get(0));

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

                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncSessionLoginTask1().execute();
                    } else {
                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);

                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg = dialog.findViewById(R.id.close_img);

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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "EquipmentID is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask1 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
                    dialog = new Dialog(TransportListDetails.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);

                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg = dialog.findViewById(R.id.close_img);

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

                            Intent inspection = new Intent(TransportListDetails.this,
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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "Data is not Found");
            }


        }
    }


    private class AsyncTransferDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                transferEquipmentSearchObject = WebServiceConsumer.getInstance().transferEquipmentSearch(
                        Kequipnu.get(0),
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
                    SessionData.getInstance().setTransferEquipment(transferEquipmentSearchObject);

                    if (transferEquipmentSearchObject.getEquipstatus() != null) {
                        if (transferEquipmentSearchObject.getEquipstatus().contains("AV")) {
                            Intent inspection = new Intent(TransportListDetails.this, SelectBranch.class);
                            startActivity(inspection);
                            SessionData.getInstance().setSelectEquip(0);
                        } else {
                            dialog = new Dialog(TransportListDetails.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);

                            ImageView closeImg = dialog.findViewById(R.id.close_img);

                            TextView Message = dialog.findViewById(R.id.txt_dialog);
                            TextView yes = dialog.findViewById(R.id.dialog_Ok);
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

                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    } else {

                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);

                        ImageView closeImg = dialog.findViewById(R.id.close_img);

                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
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
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }

                } else {


                    if (transferEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Transfer", "Expired ");
                        new AsyncSessionLoginTask_Transfer().execute();
                    } else {
                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg = dialog.findViewById(R.id.close_img);

                        if (detail.get(0).getMessage().length() != 0) {
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

                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "EquipmentID  is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_Transfer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
                    dialog = new Dialog(TransportListDetails.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg = dialog.findViewById(R.id.close_img);

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

                            Intent inspection = new Intent(TransportListDetails.this,
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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "Data is not found");
            }


        }
    }


    private class AsyncReceviceDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Log.d("", "Session call");
                ReceiveEquipmentSearchObject = WebServiceConsumer.getInstance().receiveEquipSearch(
                        Kequipnu.get(0), user.getUserDescription());
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
                    SessionData.getInstance().setReceiveEquipmentSearchObject(ReceiveEquipmentSearchObject);

                    Intent inspection = new Intent(TransportListDetails.this, RecieveWalkAround.class);
                    startActivity(inspection);
                    SessionData.getInstance().setSelectEquip(1);

                } else {
                    ProgressBar.dismiss();

                    if (ReceiveEquipmentSearchObject.getMessage().contains("Session")) {
                        Log.d("old User Token", "" + SessionData.getInstance().getTemp_userToken());
                        Log.d("Receive", "Expired ");
                        new AsyncSessionLoginTask_Receive().execute();
                    } else {
                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);

                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg = dialog.findViewById(R.id.close_img);

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

                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "EquipmentID is invalid.");
            }

        }

    }


    private class AsyncSessionLoginTask_Receive extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
                    dialog = new Dialog(TransportListDetails.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg = dialog.findViewById(R.id.close_img);

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

                            Intent inspection = new Intent(TransportListDetails.this,
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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "Data is not found");

            }


        }
    }


    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        TrasnportAdapter adapter = (TrasnportAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private class AsynccheckoutDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                detail = WebServiceConsumer.getInstance().getRentalDetail1(
                        Kequipnu.get(0), user.getUserDescription());

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
                    SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().getSelectedDetail().add(detail.get(0));
                    SessionData.getInstance().setOrder(detail.get(0).getOrderNo());
                    SessionData.getInstance().setCustname(detail.get(0).getCustName());

                    SessionData.getInstance().setContactsummary(detail.get(0).getContact());

                    SessionData.getInstance().setDatasummary(formattedDateFromString(detail.get(0).getOrderDate()));
                    SessionData.getInstance().setEnteredEquipID(
                            Kequipnu.get(0));
                    SessionData.getInstance().getKpartlist().clear();
                    SessionData.getInstance().getEqpStatus().clear();
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
                    ProgressBar.dismiss();
                    if (detail.get(0).getMessage().contains("Session")) {
                        new AsyncSessionLoginTask_CheckOut().execute();
                    } else {
                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg = dialog.findViewById(R.id.close_img);

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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
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


    private class AsyncSessionLoginTask_CheckOut extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
                    dialog = new Dialog(TransportListDetails.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);

                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg = dialog.findViewById(R.id.close_img);

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

                            Intent inspection = new Intent(TransportListDetails.this,
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
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "Data is Not Found");
            }

        }
    }

    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        LinkedHashMap<RentalDetails, String> data;

        protected void onPreExecute() {
            data = new LinkedHashMap<>();
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... params) {

            try {

                RentalDetails rentDetail;

                rentDetail = detail.get(0);

                SessionData.getInstance().setRentalId(
                        detail.get(0).getRentalID());
                SessionData.getInstance().setInspectionID(detail.get(0).getInspectionId());

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

                    if (transportObject.getAction().contains("D")) {
                        Intent intent = new Intent(TransportListDetails.this,
                                TransportWalkAroundInspection.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(TransportListDetails.this,
                                RentalInspectionWalkAround.class);

                        startActivity(intent);
                        finish();
                    }

                    finish();

                } else {
                    //  if (rentalCheckinList.getMessage().length() == 0) {
                    SessionData.getInstance().setChecklist(20);
                    SessionData.getInstance().setMulticheckinvalidate(1);

                    SessionData.getInstance().getCheckListData().putAll(data);

                    SessionData.getInstance().setWalkAroundEquipmentID(SessionData.getInstance().getKpartlist().get(0));

                    Log.d("Selected Equipment", "" + SessionData.getInstance().getKpartlist().get(0));

                    if (transportObject.getAction().contains("D")) {
                        Intent intent = new Intent(TransportListDetails.this,
                                TransportWalkAroundInspection.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(TransportListDetails.this,
                                RentalInspectionWalkAround.class);

                        startActivity(intent);
                        finish();
                    }

                }

            }

        }

    }

    public class TrasnportAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;
        ArrayList<String> equpdesc;
        ArrayList<String> equpnum;
        ArrayList<String> mfg;
        ArrayList<String> model;
        ArrayList<String> serial;


        public TrasnportAdapter(Context context,
                                ArrayList<String> equpdesc, ArrayList<String> equpnum, ArrayList<String> mfg,
                                ArrayList<String> model, ArrayList<String> serial) {
            mContext = context;
            this.equpdesc = equpdesc;
            this.equpnum = equpnum;
            this.mfg = mfg;
            this.model = model;
            this.serial = serial;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return equpnum.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }


        @SuppressLint({"InflateParams", "SetTextI18n"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            final String str_equpdesc = equpdesc
                    .get(position);
            final String str_equpnum = equpnum
                    .get(position);
            final String str_mfg = mfg
                    .get(position);
            final String str_model = model
                    .get(position);
            final String str_serial = serial
                    .get(position);

            ViewHolder holder = null;

            // Inflater for custom layout
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.transportdetails_row, parent, false);

                holder = new ViewHolder();

                holder.TxtEquipDesc = convertView.findViewById(R.id.txt_equipdesc);
                holder.TxtEquipNum = convertView
                        .findViewById(R.id.txt_equipnum);
                holder.TxtMfg = convertView
                        .findViewById(R.id.txt_make);
                holder.TxtModel = convertView
                        .findViewById(R.id.txt_model);
                holder.TxtSerial = convertView
                        .findViewById(R.id.txt_serial);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.TxtEquipDesc.setText(str_equpdesc);
            holder.TxtEquipNum.setText(str_equpnum);
            holder.TxtMfg.setText(str_mfg);
            holder.TxtModel.setText(str_model);
            holder.TxtSerial.setText(str_serial);

            return convertView;
        }

        class ViewHolder {
            TextView TxtEquipDesc, TxtEquipNum, TxtMfg,
                    TxtModel, TxtSerial;
            int ref;
        }
    }

    @Override
    public void onBackPressed() {

        new AsyncTrasportTruck().execute();

    }

    private class AsyncTrasportTruck extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransportListDetails.this);

        }

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

            if (transportTruckList != null && !(transportTruckList.equals(""))) {
                if (transportTruckList.get(0).getMessage().length() == 0) {

                    SessionData.getInstance().setTransportTruckList(transportTruckList);
//                    Intent intent = new Intent(TransportListDetails.this,
//                            TransportList.class);
//                    startActivity(intent);
                    Intent intent = new Intent(TransportListDetails.this,
                            EbsMenu.class);
                    startActivity(intent);

                } else {

                    if (transportTruckList.get(0).getMessage().contains("Session")) {

                        new AsyncLoginTask().execute();

                    } else {
                        dialog = new Dialog(TransportListDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);
                        TextView message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
                        ImageView closeImg = dialog.findViewById(R.id.close_img);

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
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportListDetails.this,
                        "Data is not found.");
            }

        }
    }

    public class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
            if (user != null && user.getUsername() != null) {

                ProgressBar.dismiss();
                SessionData.getInstance().setUser(user);
                new AsyncTrasportTruck().execute();

            } else {
                ProgressBar.dismiss();

            }
        }
    }

    public class AsyncLoginTask2 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

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
            if (user != null && user.getUsername() != null) {

                ProgressBar.dismiss();
                SessionData.getInstance().setUser(user);
                new AsyncSendNotification().execute();

            } else {
                ProgressBar.dismiss();

            }
        }
    }

    public class AsyncSendNotification extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportListDetails.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                notificationResult = WebServiceConsumer.getInstance()
                        .SendDispatchNotification(user.getUserDescription(), transportObject.getRecnum(),
                                "3");
                Log.d("AsyncSendNotification", "3");

            } catch (java.net.SocketTimeoutException e) {
                notificationResult = null;
            } catch (Exception e) {
                notificationResult = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (notificationResult.contains("Session")) {

                new TransportListDetails.AsyncLoginTask2().execute();

            } else {

                if (transportObject.getAction().contains("D")) {
                    SessionData.getInstance().setTransportTransfer(1);
                    SessionData.getInstance().setRentalIn_Out("O");

                    new AsynccheckoutDetail().execute();
                } else if (transportObject.getAction().contains("P")) {
                    SessionData.getInstance().setTransportTransfer(1);

                    SessionData.getInstance().setRentalIn_Out("I");
                    new AsynccheckinDetail().execute();

                } else if (transportObject.getAction().contains("T")) {

                    SessionData.getInstance().setTransportTransfer(1);
                    SessionData.getInstance().setTransferIn_out("O");
                    new AsyncTransferDetail().execute();

                }
                ProgressBar.dismiss();
            }
        }
    }


    private void notificationDialog() {
        dialog = new Dialog(TransportListDetails.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);

        TextView txtDialog = dialog.findViewById(R.id.txt_dialog);
        TextView txtOk = dialog.findViewById(R.id.dialog_Ok);
        TextView txtNo = dialog.findViewById(R.id.dialog_cancel);
        ImageView clsImg = dialog.findViewById(R.id.close_img);

        txtDialog.setText("Do you want to send notification as driver has arrived to the location?");
        txtOk.setText("OK");
        txtNo.setText("Cancel");
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncSendNotification().execute();
                dialog.dismiss();

            }
        });
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        clsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
