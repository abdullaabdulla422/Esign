package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TransportMapActivity extends FragmentActivity implements OnMapReadyCallback {

    LinearLayout footerlist, footermap;
    TextView TxtBack;
    ImageView ImgBack;
    ImageView router;
    TextView footermaptext, footerlisttext;
    private ArrayList<TransportListObject> transportTruckList;
    private ArrayList<GetTransportDetailsDescOjbect> transportDetailsDescOjbects;
    private User user;

    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    private static Dialog dialog;

    int RCno;
    double lng;double lat;

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_mapfragment);

        TxtBack = findViewById(R.id.backtext);
        ImgBack = findViewById(R.id.backicon);
        router= findViewById(R.id.navigation);
        user = SessionData.getInstance().getUser();

        footerlist = findViewById(R.id.layout_translist);
        footermap = findViewById(R.id.layout_transmap);
        footerlisttext = findViewById(R.id.transport_list);
        footermaptext = findViewById(R.id.transport_map);
        footermap.setBackgroundColor(getResources().getColor(R.color.intake_bg));
        footerlist.setBackgroundColor(getResources().getColor(R.color.white));
        footerlisttext.setTextColor(getResources().getColor(R.color.black));
        footermaptext.setTextColor(getResources().getColor(R.color.white));
        transportTruckList = SessionData.getInstance().getTransportTruckList();

        footerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TxtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initmap();
    }

    private void initmap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        googleMap.getUiSettings().setMapToolbarEnabled(false);

        if (SessionData.getInstance().getArraylatitude() != null) {

            for (int i = 0; i < SessionData.getInstance().getArraylatitude().size(); i++) {

                final LatLng latLng = new LatLng(SessionData.getInstance().getArraylatitude().get(i), SessionData.getInstance().getArryalongitud().get(i));

                if (transportTruckList.get(i).getCustadd().equals("")) {

                } else {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(transportTruckList.get(i).getKequipnum() + "," + transportTruckList.get(i).getCustname() + ":" + transportTruckList.get(i).getCustadd());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    googleMap.addMarker(markerOptions);


                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        router.setVisibility(View.VISIBLE);
                          lat = marker.getPosition().latitude;
                           lng = marker.getPosition().longitude;
                        return false;
                    }
                });

                router.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng + "&avoid=tf");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        router.setVisibility(View.GONE);
                    }
                });

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        router.setVisibility(View.GONE);
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {


                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        int j = Integer.parseInt(marker.getId()
                                .substring(1));

                        RCno = Integer.parseInt(transportTruckList.get(j).getRecnum());
                        new AsyncTrasportDetailDesc().execute();

                        SessionData.getInstance().setTransportObject(transportTruckList.get(0));


                    }
                });

                }
            }
        }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTrasportDetailDesc extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransportMapActivity.this);
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

                if(transportDetailsDescOjbects.size()!=0){

                    if (transportDetailsDescOjbects.get(0).getMessage()==null) {

                        SessionData.getInstance().setTransportDetailsDescOjbects(transportDetailsDescOjbects);

                        Intent deliveyintent=new Intent(TransportMapActivity.this,TransportListDetails.class);
                        startActivity(deliveyintent);

                    }
                    else {

                        if (transportDetailsDescOjbects.get(0).getMessage().contains("Session")) {

                            new AsyncLoginTask1().execute();

                        } else {
                            dialog = new Dialog(TransportMapActivity.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);

                            TextView Message = dialog.findViewById(R.id.txt_dialog);
                            TextView yes = dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(transportTruckList.get(0).getMessage());

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
                AlertDialogBox.showAlertDialog(TransportMapActivity.this,
                        "Data is not found.");
            }
        }
    }

    private class AsyncLoginTask1 extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportMapActivity.this);
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
                    dialog = new Dialog(TransportMapActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = dialog.findViewById(R.id.txt_dialog);
                    TextView yes = dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(user.getUserDescription());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent inspection = new Intent(TransportMapActivity.this,
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
                AlertDialogBox.showAlertDialog(TransportMapActivity.this,
                        "Data is not found");
            }
        }
    }

    }






