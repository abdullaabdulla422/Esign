package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportList extends AppCompatActivity {
    private static Dialog dialog;
    ImageView popup_image;
    ListView popup_list, transportList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    LinearLayout spinner_filter;
    ArrayAdapter<String> filterarrayAdapter;
    EditText edt_pdt_Filter;

    TextView txtBack;
    TextView footerlisttext, footermaptext;
    ImageView imgBack, ImgDropDown;
    EditText Edt_selectedtruck;
    LinearLayout footer, footermap, footerlist;
    String transaddress;
    LatLng latLng;
    List<Address> addressList = null;

    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    ArrayList<String> arrayNotes = new ArrayList<>();
    ArrayList<String> arrayType = new ArrayList<>();
    int RCno;
    Context context;
    private ArrayList<byte[]> AddtoSession = new ArrayList<>();
    private TrasnportAdapter adap;
    private int count;
    private ArrayList<TransportListObject> transportTruckList, filteredlist;
    // private ArrayList<TransportListObject> transportFilteredList;
    private ArrayList<GetTransportDetailsDescOjbect> transportDetailsDescOjbects;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.transport_list);

        transportTruckList = SessionData.getInstance().getTransportTruckList();
        filteredlist = new ArrayList<>();
        if(transportTruckList!=null)
        filteredlist.addAll(transportTruckList);

        SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
        SessionData.getInstance().setWalkAroundNotes(arrayNotes);
        SessionData.getInstance().setWalkAroundType(arrayType);

        SessionData.getInstance().setWalkaroundNotes("");
        SessionData.getInstance().setPrevioushours("0");

        context = getApplicationContext();
        edt_pdt_Filter = findViewById(R.id.edt_pdt_Filter);
        spinner_filter = findViewById(R.id.layout_spinner_pdt_Filter);
        Edt_selectedtruck = findViewById(R.id.selected_truck);
        transportList = findViewById(R.id.list_transport);
        user = SessionData.getInstance().getUser();
        footer = findViewById(R.id.transport_bottom);
        footermap = findViewById(R.id.layout_transmap);
        footermap.setBackgroundColor(getResources().getColor(R.color.white));
        footerlist = findViewById(R.id.layout_translist);
        footerlist.setBackgroundColor(getResources().getColor(R.color.intake_bg));
        footerlisttext = findViewById(R.id.transport_list);
        footermaptext = findViewById(R.id.transport_map);
        ImgDropDown = findViewById(R.id.down_arrow);

        footerlist.setBackgroundColor(getResources().getColor(R.color.intake_bg));
        footermap.setBackgroundColor(getResources().getColor(R.color.white));
        footerlisttext.setTextColor(getResources().getColor(R.color.white));
        footermaptext.setTextColor(getResources().getColor(R.color.black));
        footer.setVisibility(View.VISIBLE);

        txtBack = findViewById(R.id.backtext);
        imgBack = findViewById(R.id.backicon);
        popup_image = findViewById(R.id.mannerservice_btn);


        ImgDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<String> pdlist = new ArrayList<String>();

                pdlist.add("All");
                pdlist.add("Pickup");
                pdlist.add("Delivery");
                pdlist.add("Transfer");


                dialog = new Dialog(TransportList.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customer_branch_popup);

                TextView header = dialog.findViewById(R.id.header);

                header.setText("Select the Filter Value");

                filterarrayAdapter = new ArrayAdapter<>(TransportList.this,
                        R.layout.child_filter_pdt, pdlist);

                ImageView close = dialog.findViewById(R.id.cancelbtn);
                final ListView list = dialog.findViewById(R.id.listview_customerbranch);
                list.setAdapter(filterarrayAdapter);


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        edt_pdt_Filter.setText(pdlist.get(position));
                        filteredlist.clear();
                        for (int i = 0; i < transportTruckList.size(); i++) {

                            if (position == 1) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("P")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 2) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("D")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 3) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("T")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else {
                                filteredlist.clear();
                                filteredlist.addAll(transportTruckList);
                            }

                        }
                        adap = new TrasnportAdapter(TransportList.this, filteredlist);
                        transportList.setAdapter(adap);
                        adap.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        edt_pdt_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<String> pdlist = new ArrayList<String>();

                pdlist.add("All");
                pdlist.add("Pickup");
                pdlist.add("Delivery");
                pdlist.add("Transfer");


                dialog = new Dialog(TransportList.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customer_branch_popup);

                TextView header = dialog.findViewById(R.id.header);

                header.setText("Select the Filter Value");

                filterarrayAdapter = new ArrayAdapter<>(TransportList.this,
                        R.layout.child_filter_pdt, pdlist);

                ImageView close = dialog.findViewById(R.id.cancelbtn);
                final ListView list = dialog.findViewById(R.id.listview_customerbranch);
                list.setAdapter(filterarrayAdapter);


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        edt_pdt_Filter.setText(pdlist.get(position));
                        filteredlist.clear();
                        for (int i = 0; i < transportTruckList.size(); i++) {

                            if (position == 1) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("P")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 2) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("D")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 3) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("T")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else {
                                filteredlist.addAll(transportTruckList);
                            }

                        }
                        adap = new TrasnportAdapter(TransportList.this, filteredlist);
                        transportList.setAdapter(adap);
                        adap.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        spinner_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<String> pdlist = new ArrayList<String>();

                pdlist.add("All");
                pdlist.add("Pickup");
                pdlist.add("Delivery");
                pdlist.add("Transfer");


                dialog = new Dialog(TransportList.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customer_branch_popup);

                TextView header = dialog.findViewById(R.id.header);

                header.setText("Select the Filter Value");

                filterarrayAdapter = new ArrayAdapter<>(TransportList.this,
                        R.layout.child_filter_pdt, pdlist);

                ImageView close = dialog.findViewById(R.id.cancelbtn);
                final ListView list = dialog.findViewById(R.id.listview_customerbranch);
                list.setAdapter(filterarrayAdapter);


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        edt_pdt_Filter.setText(pdlist.get(position));
                        filteredlist.clear();
                        for (int i = 0; i < transportTruckList.size(); i++) {

                            if (position == 1) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("P")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 2) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("D")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else if (position == 3) {

                                if (transportTruckList.get(i).getAction().equalsIgnoreCase("T")) {
                                    filteredlist.add(transportTruckList.get(i));
                                }

                            } else {
                                filteredlist.addAll(transportTruckList);
                            }

                        }
                        adap = new TrasnportAdapter(TransportList.this, filteredlist);
                        transportList.setAdapter(adap);
                        adap.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        popup_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(TransportList.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popuplist);
                arrayList = new ArrayList<>();

                for (int i = 0; i < transportTruckList.size(); i++) {
                    arrayList.add(transportTruckList.get(i).getTrucknumber());
                }
                arrayAdapter = new ArrayAdapter<String>(TransportList.this, R.layout.transport_truck_row, R.id.list_content, arrayList);
                popup_list = dialog.findViewById(R.id.popup_list);
                ImageView clsImg=dialog.findViewById(R.id.close_img);
                popup_list.setAdapter(arrayAdapter);
                clsImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                popup_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Edt_selectedtruck.setText(arrayList.get(position));
                        footerlist.setBackgroundColor(getResources().getColor(R.color.intake_bg));
                        footermap.setBackgroundColor(getResources().getColor(R.color.white));
                        footerlisttext.setTextColor(getResources().getColor(R.color.white));
                        footermaptext.setTextColor(getResources().getColor(R.color.black));
                        footer.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });


        footermap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTransportMap().execute();
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportList.this, EbsMenu.class);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportList.this, EbsMenu.class);
                startActivity(intent);
            }
        });


        SessionData.getInstance().setTransportTruckList(transportTruckList);
        count = transportTruckList.size();
        adap = new TrasnportAdapter(TransportList.this, filteredlist);
        transportList.setAdapter(adap);

        transportList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(filteredlist.get(position).getAction().contains("T")){
                    RCno = Integer.parseInt(filteredlist.get(position).getRecnum());
                    new AsyncTrasportDetailTransfer().execute();

                    SessionData.getInstance().setTransportObject(filteredlist.get(position));
                }else{
                    RCno = Integer.parseInt(filteredlist.get(position).getRecnum());
                    new AsyncTrasportDetailDesc().execute();

                    SessionData.getInstance().setTransportObject(filteredlist.get(position));
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TransportList.this, EbsMenu.class);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTrasportDetailDesc extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransportList.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                transportDetailsDescOjbects = WebServiceConsumer.getInstance().GetTransportDesc(user.getUserDescription(), RCno);
            } catch (java.net.SocketTimeoutException e) {
                transportDetailsDescOjbects = null;

            } catch (Exception e) {
                transportDetailsDescOjbects = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (transportDetailsDescOjbects != null && !(transportDetailsDescOjbects.equals(""))) {

                if (transportDetailsDescOjbects.size() != 0) {

                    if (transportDetailsDescOjbects.get(0).getMessage() == null) {

                        SessionData.getInstance().setTransportDetailsDescOjbects(transportDetailsDescOjbects);

                        Intent deliveyintent = new Intent(TransportList.this, TransportListDetails.class);
                        startActivity(deliveyintent);

                    } else {

                        if (transportDetailsDescOjbects.get(0).getMessage().contains("Session")) {

                            new AsyncLoginTask1().execute();

                        } else {
                            dialog = new Dialog(TransportList.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);

                            TextView Message = dialog.findViewById(R.id.txt_dialog);
                            TextView yes = dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(transportTruckList.get(0).getMessage());

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
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportList.this,
                        "Data is not found.");
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTrasportDetailTransfer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransportList.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                transportDetailsDescOjbects = WebServiceConsumer.getInstance().GetTransportForTransfer(user.getUserDescription(), RCno);
            } catch (java.net.SocketTimeoutException e) {
                transportDetailsDescOjbects = null;

            } catch (Exception e) {
                transportDetailsDescOjbects = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (transportDetailsDescOjbects != null && !(transportDetailsDescOjbects.equals(""))) {

                if (transportDetailsDescOjbects.size() != 0) {

                    if (transportDetailsDescOjbects.get(0).getMessage() == null) {

                        SessionData.getInstance().setTransportDetailsDescOjbects(transportDetailsDescOjbects);

                        Intent deliveyintent = new Intent(TransportList.this, TransportListDetails.class);
                        startActivity(deliveyintent);

                    } else {

                        if (transportDetailsDescOjbects.get(0).getMessage().contains("Session")) {

                            new AsyncLoginTask2().execute();

                        } else {
                            dialog = new Dialog(TransportList.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);

                            TextView Message = dialog.findViewById(R.id.txt_dialog);
                            TextView yes = dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(transportTruckList.get(0).getMessage());

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
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportList.this,
                        "Data is not found.");
            }
        }
    }

//    private class AsyncTrasportList extends AsyncTask<Void, Void, Void> {
//
//        protected void onPreExecute() {
//
//            ProgressBar.showCommonProgressDialog(TransportList.this);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//
//                transportFilteredList = WebServiceConsumer.getInstance().GetTransportList(user.getUserDescription(), Edt_selectedtruck.getText().toString());
//            } catch (java.net.SocketTimeoutException e) {
//                transportFilteredList = null;
//
//            } catch (Exception e) {
//                transportFilteredList = null;
//
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            ProgressBar.dismiss();
//
//            if (transportFilteredList != null && !(transportFilteredList.equals(""))) {
//
//                if(transportFilteredList.size()!=0){
//
//                    if (transportFilteredList.get(0).getMessage().length()== 0) {
//                        SessionData.getInstance().setTransportTruckList(transportFilteredList);
//                        count = transportFilteredList.size();
//                        adap = new TrasnportAdapter(TransportList.this,
//                                transportFilteredList);
//                        transportList.setAdapter(adap);
//
//                    }
//                    else {
//
//                        if (transportTruckList.get(0).getMessage().contains("Session")) {
//
//                            new AsyncLoginTask().execute();
//
//                        } else {
//                            dialog = new Dialog(TransportList.this);
//                            dialog.setCanceledOnTouchOutside(true);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.message);
//
//                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                            Message.setText(transportTruckList.get(0).getMessage());
//
//                            yes.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
////								Intent inspection = new Intent(EbsMenu.this,
////										MainActivity.class);
////								startActivity(inspection);
//                                    dialog.dismiss();
//                                }
//                            });
//                            dialog.show();
//                        }
//                    }
//              }
//
//
//
//            } else {
//                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(TransportList.this,
//                        "Data is not found.");
//            }
//        }
//    }

//    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {
//
//        protected void onPreExecute() {
//            ProgressBar.showCommonProgressDialog(TransportList.this);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                user = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());
//                Log.d("Session Expiered", "Session Expiered");
//                Log.d("New User Token", "" + SessionData.getInstance().getTemp_userToken());
//                Log.d("After Session Expired", "Equip_ID" + SessionData.getInstance().getTemp_equipId());
//            } catch (java.net.SocketTimeoutException e) {
//                user = null;
//            } catch (Exception e) {
//                user = null;
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            ProgressBar.dismiss();
//            if (user != null) {
//                SessionData.getInstance().setUser(user);
//
//
//                if (user.getUserDescription().contains("Login is already in use")) {
//                    dialog = new Dialog(TransportList.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.message);
//
//
//                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                    Message.setText(user.getUserDescription());
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(TransportList.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();
//                } else {
//                    new AsyncTrasportList().execute();
//                }
//
//            } else {
//                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(TransportList.this,
//                        "Data is not found");
//            }
//        }
//    }

    private class AsyncLoginTask1 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportList.this);
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
                SessionData.getInstance().setUser(user);

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(TransportList.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
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

                            Intent inspection = new Intent(TransportList.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    new AsyncTrasportDetailDesc().execute();
                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportList.this,
                        "Data is not found");
            }
        }
    }

    private class AsyncLoginTask2 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportList.this);
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
                SessionData.getInstance().setUser(user);

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(TransportList.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
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

                            Intent inspection = new Intent(TransportList.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    new AsyncTrasportDetailTransfer().execute();
                }

            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportList.this,
                        "Data is not found");
            }
        }
    }


    public class TrasnportAdapter extends BaseAdapter {
        @SuppressLint("UseSparseArrays")
        public final HashMap<Integer, String> myList = new HashMap<Integer, String>();
        private final Context mContext;
        private final ArrayList<TransportListObject> list;
        private LayoutInflater mInflater;

        public TrasnportAdapter(Context context,
                                ArrayList<TransportListObject> list) {
            mContext = context;
            this.list = list;
//            list = null;
//            for (int i = 0; i < count; i++) {
//                myList.put(i, "");
//            }
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final TransportListObject deal = list
                    .get(position);

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.transport_list_row, null);

                holder.TxtCustomer = convertView
                        .findViewById(R.id.txt_customer);
                holder.TxtEquipId = convertView
                        .findViewById(R.id.txt_equipid);
                holder.TxtPid = convertView
                        .findViewById(R.id.txt_prty);
                holder.TxtPD = convertView
                        .findViewById(R.id.txt_pd);
//                holder.TxtEstimated = (TextView) convertView
//                        .findViewById(R.id.txt_estimated);
                holder.TxtDispatched = convertView.findViewById(R.id.txt_dispatched);
                holder.imgNotesIndicator = convertView.findViewById(R.id.notes_indicator);


				/*holder.Txtprogram = (TextView) convertView
						.findViewById(R.id.program);*/
                holder.TxtCall = convertView
                        .findViewById(R.id.txt_call);
                holder.TxtCity = convertView.findViewById(R.id.txt_city);

                convertView.setTag(holder);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //   holder.ref = position;
            holder.TxtCustomer.setText(deal.getCustname());
            holder.TxtPid.setText(deal.getParty());
            holder.TxtPD.setText(deal.getAction());
//            holder.TxtEstimated.setText(deal.getEstimated());
            //holder.Txtprogram.setText(deal.getProgram());
            holder.TxtDispatched.setText(deal.getDispatched());
            holder.TxtCall.setText(deal.getCallnum());
            holder.TxtCity.setText(deal.getCustcity());
            holder.TxtEquipId.setText(deal.getKequipnum());

            if ((deal.getNotes01().length() != 0) || (deal.getNotes02().length() != 0) || (deal.getNotes03().length() != 0) || (deal.getNotes04().length() != 0)) {
                holder.imgNotesIndicator.setVisibility(View.VISIBLE);
            } else {
                holder.imgNotesIndicator.setVisibility(View.GONE);
            }


            return convertView;
        }

        class ViewHolder {
            TextView TxtCustomer, TxtPid, TxtPD,
//                    TxtEstimated,
                    TxtDispatched, TxtCall, TxtCity, TxtEquipId;
            ImageView imgNotesIndicator;
            int ref;
        }
    }

    private class AsyncTransportMap extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransportList.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Geocoder geocoder = new Geocoder(TransportList.this);
            latitude = new ArrayList<>();
            longitude = new ArrayList<>();

            for (int i = 0; i < transportTruckList.size(); i++) {
                transaddress = transportTruckList.get(i).getCustadd() + transportTruckList.get(i).getCustcity() + transportTruckList.get(i).getCustadd01()
                        + transportTruckList.get(i).getCustzip() + transportTruckList.get(i).getCuststate() + transportTruckList.get(i).getCustname()
                        + transportTruckList.get(i).getCustsnum();

                try {
                    addressList = geocoder.getFromLocationName(transaddress, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addressList != null) {
                    if (addressList.size() != 0) {
                        Address location = addressList.get(0);
                        location.getLatitude();
                        location.getLongitude();
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        latitude.add(location.getLatitude());
                        longitude.add(location.getLongitude());
                    }
                }
            }
            SessionData.getInstance().setArraylatitude(latitude);
            SessionData.getInstance().setArryalongitud(longitude);
            return null;
        }

        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            Intent intent = new Intent(TransportList.this, TransportMapActivity.class);
            startActivity(intent);
        }
    }
}
