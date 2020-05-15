package com.ebs.rental.general;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderTerms;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TransportTearmsAndConditions extends AppCompatActivity implements OnClickListener{
    private TextView tearms;
    private Button Accept;
    private Button Reject;
    private RentalOrderTerms orderTerms;
    private String str_Terms;


    private Customeremails customeremails;
    private CheckBox nosign;
    private static Dialog dialog;
    private User user;
    private TextView backtext;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tearms_conditions);
        user = SessionData.getInstance().getUser();

        orderTerms = SessionData.getInstance().getTerms();
        tearms = (TextView)findViewById(R.id.txttearms);
        Accept = (Button) findViewById(R.id.btn_accept);
        Reject = (Button) findViewById(R.id.btn_reject);
        nosign = (CheckBox)findViewById(R.id.nosign);
        nosign.setVisibility(View.GONE);
        str_Terms = orderTerms.getResult();
        str_Terms = str_Terms.replace("\n","\\n");
        str_Terms = str_Terms.replace("\\n","\n");
        tearms.setText(str_Terms);
        backtext = (TextView) findViewById(R.id.backtext);
        back = (ImageView) findViewById(R.id.backicon);
        back.setOnClickListener(this);
        backtext.setOnClickListener(this);
        Accept.setOnClickListener(this);
        Reject.setOnClickListener(this);
        nosign.setOnClickListener(this);
        SessionData.getInstance().setSignData(null);



    }

    @Override
    public void onClick(View v) {
        if(v == Accept){

//            if(!nosign.isChecked()){
//                SessionData.getInstance().setHassign(true);
//                SessionData.getInstance().setSignData(null);
                Intent intent = new Intent(TransportTearmsAndConditions.this,
                        TransportSignature.class);
                startActivity(intent);
//            }
//            else{
//                SessionData.getInstance().setHassign(false);
////                new AsynMails().execute();
//            }

        }
        else if (v == Reject){

            Intent intent = new Intent(TransportTearmsAndConditions.this,
                    TransportSummeryActivity.class);
            startActivity(intent);

        }
        else if (v == back){
            onBackPressed();
        }
        else if (v == backtext){
            onBackPressed();
        }

    }
//    private class AsynMails extends AsyncTask<Void, Void, Void> {
//        protected void onPreExecute() {
//
//            ProgressBar.showCommonProgressDialog(TransportTearmsAndConditions.this);
//        };
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                customeremails = WebServiceConsumer.getInstance().customermails(
//                        user.getUserDescription(),DetailObjects.getKcustnum(),DetailObjects.getCustsnum(),"R");
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
//                if(SessionData.getInstance().getCustomeremailsResult()==1){
//
//                    if(customeremails.getMessage().contains("Session")){
//                        new AsyncLoginTask().execute();
//                    }else{
//                        dialog = new Dialog(TransportTearmsAndConditions.this);
//                        dialog.setCanceledOnTouchOutside(true);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setContentView(R.layout.message);
//
//
//                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                        Message.setText(customeremails.getMessage());
//
//                        yes.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                Intent inspection = new Intent(TransportTearmsAndConditions.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
//                                dialog.dismiss();
//                            }
//                        });
//
//
//                        dialog.show();
//                    }
//
//                }
//                else {
//
//                    SessionData.getInstance().setCustomeremails(customeremails);
//
//                    Intent intent = new Intent(TransportTearmsAndConditions.this,
//                            CustomerMailDetails.class);
//
//                    startActivity(intent);
//                    finish();
//                }
//            }
//            else{
//                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(TransportTearmsAndConditions.this,
//                        "Data is not found.");
//            }
//        }

//    }

//    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {
//
//        protected void onPreExecute() {
//            ProgressBar.showCommonProgressDialog(TransportTearmsAndConditions.this);
//        };
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                user = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());
//                Log.d("Session Expiered", "Session Expiered");
//                Log.d("New User Token",""+SessionData.getInstance().getTemp_userToken());
//                Log.d("After Session Expired","Equip_ID"+SessionData.getInstance().getTemp_equipId());
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
//            if(user!=null){
//                SessionData.getInstance().setUser(user);
//
//
//                if(user.getUserId()==0){
//                    dialog = new Dialog(TransportTearmsAndConditions.this);
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
//                            Intent inspection = new Intent(TransportTearmsAndConditions.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    dialog.show();
//                }else{
//
//                    new AsynMails().execute();
//
//                }
//
//
//
//            }else{
//                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(TransportTearmsAndConditions.this,
//                        "Data is not found");
//            }
//
//
//
//        }
//    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(TransportTearmsAndConditions.this,
                TransportSummeryActivity.class);

        startActivity(intent);

    }


}