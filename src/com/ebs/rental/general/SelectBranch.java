package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.Custnolist;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class SelectBranch extends AppCompatActivity implements View.OnClickListener {

    private ListView BranchSearch;
    private String unitIdChanged = "";
    private Button submit;
    private ImageView imgback;
    private TextView txtback;
    //Spinner branch;
    Spinner customerBranch;
    private EditText txtSelectbranch;
    private ImageView imgSelectBranch;
    private EditText txtSelectCustomerBranch;
    private ImageView imgSelectCustomerBranch;

    private int session = 0;

    User objUser = null;

    private ListView list;
    private int count;

    private static Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    static Context context;

    private ArrayList<DealerBranches> dealer;
    private ArrayList<DealerBranches> dealer_new;
    private int inst = 1;
    private int inen = 15;
    private DealerAdapter adap;
    private final List<String> custList = new ArrayList<String>();
    private User user;
    private ArrayList<String> branchdeal = new ArrayList<>();
    private ArrayList<Custnolist> custnolists = new ArrayList<>();
    private ArrayAdapter<String> CustomerListAdapter;
    private TransferEquipmentSearchObject transferEquipmentSearchObject;
    int size = 0;
    ArrayList<String> branchcode = new ArrayList<>();
    ArrayList<String> dealarbranchname;
    private TransportListObject transportObject;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.branch);

        BranchSearch = (ListView) findViewById(R.id.listView);
        submit = (Button) findViewById(R.id.submit);
        txtSelectbranch = (EditText) findViewById(R.id.txt_select_branch);
        imgSelectBranch = (ImageView) findViewById(R.id.img_select_branch);
        txtSelectCustomerBranch = (EditText) findViewById(R.id.txt_select_customer_branch);
        imgSelectCustomerBranch = (ImageView) findViewById(R.id.img_select_customer_branch);

        transferEquipmentSearchObject = SessionData.getInstance().getTransferEquipment();


        user = SessionData.getInstance().getUser();


        custnolists = SessionData.getInstance().getCustnolists();
        txtSelectbranch.setText(SessionData.getInstance().getLoginbranch().toString());

        imgback = (ImageView) findViewById(R.id.close);
        txtback = (TextView) findViewById(R.id.back);

        //branch = (Spinner)findViewById(R.id.select_transfer_branch);
        //customerBranch = (Spinner)findViewById(R.id.select_customer_branch);
        imgSelectBranch.setOnClickListener(this);
        imgSelectCustomerBranch.setOnClickListener(this);

        if (SessionData.getInstance().getTransportTransfer() == 1) {
            transportObject = SessionData.getInstance().getTransportObject();
            txtSelectCustomerBranch.setText(transportObject.getTobranch());
            imgSelectCustomerBranch.setVisibility(View.GONE);
            SessionData.getInstance().setSelectedCustomerBranch(txtSelectCustomerBranch.getText().toString());


        } else {
            txtSelectCustomerBranch.setText("");
            imgSelectCustomerBranch.setVisibility(View.VISIBLE);
        }


//        String[] values = new String[] { "001-NATIONAL SERVICE CENTER",
//                "100-YOUR EQUIPMENT COMPANY",
//                "108-NATIONAL SERVICE CENTER",

//        };

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_single_choice, android.R.id.text1, values);

//        // Assign adapter to ListView
//        BranchSearch.setAdapter(adapter);

//        BranchSearch.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//        List<String> list = new ArrayList<String>();
//
//        list.add("001-NATIONAL SERVICE CENTER");
//        list.add("100-YOUR EQUIPMENT COMPANY");
//        list.add("108-NATIONAL SERVICE CENTER");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


        if (custnolists != null) {
            for (int i = 0; i < custnolists.size(); i++) {
                custList.add(custnolists.get(i).getCustnum());
            }

        }


//        list.add("001-NATIONAL SERVICE CENTER");
//        list.add("100-YOUR EQUIPMENT COMPANY");
//        list.add("108-NATIONAL SERVICE CENTER");

        /*CustomerListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, custList);*/


////        branch.setAdapter(dataAdapter);
//
//        customerBranch.setAdapter(CustomerListAdapter);

        submit.setOnClickListener(this);
        imgback.setOnClickListener(this);
        txtback.setOnClickListener(this);


        new AsyncDealer().execute();

    }

    @Override
    public void onClick(View v) {

        if (submit == v) {
            if (txtSelectCustomerBranch.getText().toString().length() == 0) {
                Toast.makeText(this, "Select Customer Branch", Toast.LENGTH_LONG).show();
            } else if (txtSelectbranch.getText().toString().length() == 0) {
                Toast.makeText(this, "Select Transfer Branch", Toast.LENGTH_LONG).show();
                SessionData.getInstance().setCurrentBranch_TransIn_Out(transferEquipmentSearchObject.getCurrentbranch());

            } else if (txtSelectbranch.getText().toString().equals(transferEquipmentSearchObject.getCurrentbranch())) {
                Log.d("transfer branch", "" + txtSelectbranch.getText().toString());
                Log.d("Current Branch", "" + transferEquipmentSearchObject.getCurrentbranch());
                Toast.makeText(this, "Transfer Branch and Current Branch Should not be same", Toast.LENGTH_LONG).show();
            } else if (txtSelectbranch.getText().toString().equals(txtSelectCustomerBranch.getText().toString())) {
                Toast.makeText(this, "Transfer From and Transfer To Should not be same", Toast.LENGTH_LONG).show();
            } else {
                String strr = txtSelectbranch.getText().toString();
                for (int j = 0; j < strr.length(); j++) {
                    Character character = strr.charAt(j);

                    if (character.toString().equals("-")) {
                        strr = strr.substring(0, j);
                        break;
                    }
                }
                SessionData.getInstance().setCustomshipto(
                        strr);
                SessionData.getInstance().setBranchcode(
                        strr);
                SessionData.getInstance().setCurrentBranch_TransIn_Out(txtSelectCustomerBranch.getText().toString());
                SessionData.getInstance().setTobranch(txtSelectCustomerBranch.getText().toString());
                SessionData.getInstance().setSelectedTransferbranch(txtSelectbranch.getText().toString());
                Intent inspection = new Intent(SelectBranch.this, Equip_WalkAround.class);
                startActivity(inspection);
            }

        } else if (imgback == v) {
            onBackPressed();
//            if(SessionData.getInstance().getTransportTransfer()==1){
//                Intent inspection = new Intent(SelectBranch.this, TransportListDetails.class);
//                startActivity(inspection);
//            }
//            else{
//                if(SessionData.getInstance().getScanNavigation()==0){
//                    Intent inspection = new Intent(SelectBranch.this, ScannerActivity.class);
//                    startActivity(inspection);
//                }else{
//                    Intent intent = new Intent(SelectBranch.this,
//                            ScannerProductActivity.class);
//
//                    startActivity(intent);
//                }
//            }
        } else if (txtback == v) {
            onBackPressed();

//            if(SessionData.getInstance().getTransportTransfer()==1){
//                Intent inspection = new Intent(SelectBranch.this, TransportListDetails.class);
//                startActivity(inspection);
//            }
//            else {
//                if (SessionData.getInstance().getScanNavigation() == 0) {
//                    Intent inspection = new Intent(SelectBranch.this, ScannerActivity.class);
//                    startActivity(inspection);
//                } else {
//                    Intent intent = new Intent(SelectBranch.this,
//                            ScannerProductActivity.class);
//
//                    startActivity(intent);
//                }
//            }
        } else if (imgSelectBranch == v) {

            new AsyncDealerBranch().execute();

        } else if (imgSelectCustomerBranch == v) {

            dialog = new Dialog(SelectBranch.this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.customer_branch_popup);

            if (SessionData.getInstance().getTransferEquipment().getCustrebranch().toString().equalsIgnoreCase("0")) {

//              size = dealer.size();
//
//              if(size==0){
//                  CustomerListAdapter = new ArrayAdapter<String>(this,
//                          android.R.layout.simple_list_item_1, custList);
//              }else{
//                  for (int i=0; i<size; i++){
//                      branchdeal.add(dealer.get(i).getBranchName());
//                  }
//                  branchdeal.add(SessionData.getInstance().getLoginbranch());
//
//                  CustomerListAdapter = new ArrayAdapter<String>(this,
//                          android.R.layout.simple_list_item_1, branchdeal);
//              }
                CustomerListAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, dealarbranchname);

            } else {
                CustomerListAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, custList);
            }

            ImageView close = (ImageView) dialog.findViewById(R.id.cancelbtn);
            final ListView list = (ListView) dialog.findViewById(R.id.listview_customerbranch);
            list.setAdapter(CustomerListAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (SessionData.getInstance().getTransferEquipment().getCustrebranch().toString().equalsIgnoreCase("0")) {
                        txtSelectCustomerBranch.setText(dealarbranchname.get(position));
                        SessionData.getInstance().setSelectedCustomerBranch(dealarbranchname.get(position));
                    } else {
                        txtSelectCustomerBranch.setText(custList.get(position));
                        SessionData.getInstance().setSelectedCustomerBranch(custList.get(position));
                        String str = custList.get(position);
//                        str = str.substring(4,10);
                        for (int j = 0; j < str.length(); j++) {
                            Character character = str.charAt(j);

                            if (character.toString().equals("-")) {
                                str = str.substring(0, j);
                                break;
                            }
                        }
                        SessionData.getInstance().setTransferKcustnum(str);
                        Log.d("Splited value", str);
                    }

//                    SessionData.getInstance().setSelectedCustomerBranch(custList.get(position));

                    dialog.cancel();

                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

//            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//            Message.setText(dealer.get(0).getMessage());
//
//            yes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent inspection = new Intent(SelectBranch.this,
//                            MainActivity.class);
//                    startActivity(inspection);
//                    dialog.dismiss();
//                }
//            });


            dialog.show();

        }

    }


    private class AsyncDealerBranch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SelectBranch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int dval = 1;
                int dvals = 15;

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

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
            branchdeal.clear();
            if (dealer != null && !(dealer.equals(""))) {

                if (dealer.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setDealer(dealer);


//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
//                    Intent inspection = new Intent(SelectBranch.this,
//                            RetntalOrderBranch.class);
//                    startActivity(inspection);
//					}
                    for (int i = 0; i < dealer.size(); i++) {
                        branchcode.add(SessionData.getInstance().getDealer().get(i).getkBranch());
                    }

                    String kbranch = SessionData.getInstance().getLoginbranch();

                    for (int j = 0; j < kbranch.length(); j++) {
                        Character character = kbranch.charAt(j);

                        if (character.toString().equals("-")) {
                            kbranch = kbranch.substring(0, j);
                            break;
                        }
                    }
                    branchcode.add(kbranch);
//                    kbranch=kbranch.substring()

                    size = dealer.size();

                    for (int i = 0; i < size; i++) {
                        branchdeal.add(dealer.get(i).getBranchName());

                        if (dealer.get(i).getBranchName().equalsIgnoreCase(SessionData.getInstance().getLoginbranch())) {
                            value = 1;
                        }
                    }
                    if (value == 1) {

                    } else {
                        branchdeal.add(SessionData.getInstance().getLoginbranch());
                        value = 0;
                    }

                    adap = new DealerAdapter(SelectBranch.this, branchdeal);

                    dialog = new Dialog(SelectBranch.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.transfer_branch_popup);

                    ImageView close = (ImageView) dialog.findViewById(R.id.cancelbtn);

                    DealerBranches dealerBranches = new DealerBranches();


                    dealer = SessionData.getInstance().getDealer();


                    count = dealer.size();
                    count = branchdeal.size();
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    list = (ListView) dialog.findViewById(R.id.listview_transferbranch);
                    EditText inputSearch = (EditText) dialog.findViewById(R.id.inputSearch);

                    list.setAdapter(adap);


                    list.setTextFilterEnabled(true);
                    user = SessionData.getInstance().getUser();
                    //  dealcount = dealer.get(0);
                    // i = dealcount.getProwcnt();
                    // Log.d("value for count", "" + i);
                    inputSearch.requestFocus();
                    dialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    // ListView Item Click Listener
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            SessionData.getInstance().setCustomshipto(
                                    branchcode.get(position).toString());
                            SessionData.getInstance().setBranchcode(
                                    branchcode.get(position).toString());
//                            SessionData.getInstance().setSind(
//                                    SessionData.getInstance().getDealer().get(position)
//                                            .getPrownum());
//                            SessionData.getInstance().setRcount(
//                                    SessionData.getInstance().getDealer().get(position)
//                                            .getProwcnt());
                            txtSelectbranch.setText(branchdeal.get(position).toString());
                            SessionData.getInstance().setSelectedTransferbranch(txtSelectbranch.getText().toString());

                            // Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
                            // Toast.LENGTH_LONG).show();

//                            Intent inspection = new Intent(SelectBranch.this,
//                                    RentalOrderSearch.class);
//                            startActivity(inspection);


                            dialog.cancel();

                        }

                    });
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int start, int before,
                                                  int count) {
                            // When user changed the Text

                        }

                        @Override
                        public void beforeTextChanged(CharSequence cs, int start,
                                                      int count, int after) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable theWatchedText) {
                            // TODO Auto-generated method stub
                            unitIdChanged = theWatchedText.toString();
                            // deal2.getBranchName();
                            SessionData.getInstance().setBname(unitIdChanged);
                            new AsyncDealerBranchFiltter().execute();

                        }

                    });


                    // Message.setText(dealer.get(0).getMessage());

//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(SelectBranch.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//                    close.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });


                    dialog.show();
                } else {
                    if (dealer.get(0).getMessage().contains("Session")) {
                        session = 0;
                        new AsyncSessionLoginTask_Transfer().execute();
                    } else {
                        dialog = new Dialog(SelectBranch.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(dealer.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(SelectBranch.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }


                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(SelectBranch.this,
                        "Data is not found.");
            }

        }

    }

    private class AsyncDealer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SelectBranch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int dval = 1;
                int dvals = 15;

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                dealer_new = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        "", dval,
                        dvals);

            } catch (java.net.SocketTimeoutException e) {
                dealer_new = null;

            } catch (Exception e) {
                dealer_new = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            dealarbranchname = new ArrayList<>();
            ProgressBar.dismiss();
            //  branchdeal.clear();
            if (dealer_new != null && !(dealer_new.equals(""))) {

                if (dealer_new.get(0).getMessage().equals("")) {
//                    SessionData.getInstance().setDealer(dealer_new);

                    for (int i = 0; i < dealer_new.size(); i++) {
                        dealarbranchname.add(dealer_new.get(i).getBranchName());

                        if (dealer_new.get(i).getBranchName().equalsIgnoreCase(SessionData.getInstance().getLoginbranch())) {
                            value = 1;
                        }
                    }
                    if (value == 1) {

                    } else {
                        dealarbranchname.add(SessionData.getInstance().getLoginbranch());
                        value = 0;
                    }

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{
//                    Intent inspection = new Intent(SelectBranch.this,
//                            RetntalOrderBranch.class);
//                    startActivity(inspection);
//					}
//                    for(int i=0; i<dealer.size();i++){
//                        branchcode.add(SessionData.getInstance().getDealer().get(i).getkBranch());
//                    }
//
//                    String kbranch=SessionData.getInstance().getLoginbranch();
//
//                    for(int j=0; j<kbranch.length();j++){
//                        Character character=kbranch.charAt(j);
//
//                        if(character.toString().equals("-")){
//                            kbranch=kbranch.substring(0,j);
//                            break;
//                        }
//                    }
//                    branchcode.add(kbranch);
////                    kbranch=kbranch.substring()
//
//                    size = dealer.size();
//
//                    for (int i=0; i<size; i++){
//                        branchdeal.add(dealer.get(i).getBranchName());
//
//                        if(dealer.get(i).getBranchName().equalsIgnoreCase(SessionData.getInstance().getLoginbranch())){
//                            value =1;
//                        }
//                    }
//                    if(value==1){
//
//                    }else {
//                        branchdeal.add(SessionData.getInstance().getLoginbranch());
//                        value=0;
//                    }
//
//                    adap = new DealerAdapter(SelectBranch.this, branchdeal);
//
//                    dialog = new Dialog(SelectBranch.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.transfer_branch_popup);
//
//                    ImageView close = (ImageView)dialog.findViewById(R.id.cancelbtn);
//
//                    DealerBranches dealerBranches=new DealerBranches();
//
//
//
//                    dealer = SessionData.getInstance().getDealer();
//
//
//                    count = dealer.size();
//                    count= branchdeal.size();
//                    close.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    list = (ListView) dialog.findViewById(R.id.listview_transferbranch);
//                    EditText inputSearch = (EditText) dialog.findViewById(R.id.inputSearch);
//
//                    list.setAdapter(adap);
//
//
//                    list.setTextFilterEnabled(true);
//                    user = SessionData.getInstance().getUser();
//                    //  dealcount = dealer.get(0);
//                    // i = dealcount.getProwcnt();
//                    // Log.d("value for count", "" + i);
//                    inputSearch.requestFocus();
//                    dialog.getWindow().setSoftInputMode(
//                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                    // ListView Item Click Listener
//                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view,
//                                                int position, long id) {
//                            SessionData.getInstance().setCustomshipto(
//                                    branchcode.get(position).toString());
//                            SessionData.getInstance().setBranchcode(
//                                    branchcode.get(position).toString());
////                            SessionData.getInstance().setSind(
////                                    SessionData.getInstance().getDealer().get(position)
////                                            .getPrownum());
////                            SessionData.getInstance().setRcount(
////                                    SessionData.getInstance().getDealer().get(position)
////                                            .getProwcnt());
//                            txtSelectbranch.setText(branchdeal.get(position).toString());
//                            SessionData.getInstance().setSelectedTransferbranch(branchdeal.get(position).toString());
//
//                            // Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
//                            // Toast.LENGTH_LONG).show();
//
////                            Intent inspection = new Intent(SelectBranch.this,
////                                    RentalOrderSearch.class);
////                            startActivity(inspection);
//
//
//                            dialog.cancel();
//
//                        }
//
//                    });
//                    inputSearch.addTextChangedListener(new TextWatcher() {
//
//                        @Override
//                        public void onTextChanged(CharSequence cs, int start, int before,
//                                                  int count) {
//                            // When user changed the Text
//
//                        }
//
//                        @Override
//                        public void beforeTextChanged(CharSequence cs, int start,
//                                                      int count, int after) {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable theWatchedText) {
//                            // TODO Auto-generated method stub
//                            unitIdChanged = theWatchedText.toString();
//                            // deal2.getBranchName();
//                            SessionData.getInstance().setBname(unitIdChanged);
//                            new AsyncDealerBranchFiltter().execute();
//
//                        }
//
//                    });


                    // Message.setText(dealer.get(0).getMessage());

//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(SelectBranch.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//                    close.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });


//                    dialog.show();
                } else {
                    if (dealer.get(0).getMessage().contains("Session")) {
                        session = 2;
                        new AsyncSessionLoginTask_Transfer().execute();
                    } else {
//                        dialog = new Dialog(SelectBranch.this);
//                        dialog.setCanceledOnTouchOutside(true);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setContentView(R.layout.message);
//
//
//                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                        Message.setText(dealer.get(0).getMessage());
//
//                        yes.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
////                                Intent inspection = new Intent(SelectBranch.this,
////                                        MainActivity.class);
////                                startActivity(inspection);
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();
                    }


                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(SelectBranch.this,
                        "Data is not found.");
            }

        }

    }

    private class AsyncDealerBranchFiltter extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SelectBranch.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                inst = 1;

                inen = 15;

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                dealer = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        SessionData.getInstance().getBname(), inst, inen);

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

            Log.d("Dealer List size", "" + dealer.size());

            if (dealer.size() == 0) {
                Toast.makeText(getApplicationContext(), "No More Data to Load",
                        Toast.LENGTH_LONG).show();
                count = dealer.size();
                list.setAdapter(adap);
            } else {


                if (dealer.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setDealer(dealer);
                    count = dealer.size();
                    list.setAdapter(adap);
                } else {
                    if (dealer.get(0).getMessage().contains("Session")) {
                        session = 1;
                        new AsyncSessionLoginTask_Transfer().execute();
                    } else {
                        dialog = new Dialog(SelectBranch.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(dealer.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Intent inspection = new Intent(SelectBranch.this,
//                                        MainActivity.class);
//                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }
            }
        }
    }


    private class AsyncSessionLoginTask_Transfer extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SelectBranch.this);
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
            if (user != null) {
                ProgressBar.dismiss();
                if (user.getUserId() == 0) {
                    dialog = new Dialog(SelectBranch.this);
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

                            Intent inspection = new Intent(SelectBranch.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    SessionData.getInstance().setUser(user);

                    if (session == 1) {
                        new AsyncDealerBranchFiltter().execute();
                    } else if (session == 0) {
                        new AsyncDealerBranch().execute();
                    } else if (session == 2) {
                        new AsyncDealer().execute();
                    }

                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(SelectBranch.this,
                        "Data is not found");
            }


        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(SelectBranch.this, EbsMenu.class);
        startActivity(intent);
//        if(SessionData.getInstance().getTransportTransfer()==1){
//            Intent inspection = new Intent(SelectBranch.this, TransportListDetails.class);
//            startActivity(inspection);
//        }
//        else {
//            if (SessionData.getInstance().getScanNavigation() == 0) {
//                Intent inspection = new Intent(SelectBranch.this, ScannerActivity.class);
//                startActivity(inspection);
//            } else {
//                Intent intent = new Intent(SelectBranch.this,
//                        ScannerProductActivity.class);
//
//                startActivity(intent);
//            }
//        }


    }

    public class DealerAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<String> list;

        public DealerAdapter(Context context, ArrayList<String> branchdeal) {
            mContext = context;
            this.list = branchdeal;
            // list = null;

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
            // DealerBranches deal = (DealerBranches) dealer.get(position);

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.dealer_row, null);

                holder.customerName = (TextView) convertView
                        .findViewById(R.id.branchrow);
                convertView.setTag(holder);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.customerName.setText(list.get(position).toString());
            //   holder.customerName.setText(deal.getBranchName());


            return convertView;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    }

    class ViewHolder {
        TextView customerName;
    }
}
