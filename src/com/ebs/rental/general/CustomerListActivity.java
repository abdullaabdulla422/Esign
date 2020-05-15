package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebs.rental.adapters.CustomerListAdapter;
import com.ebs.rental.objects.CustomerList;
import com.ebs.rental.utils.RootActivity;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CustomerListActivity extends RootActivity implements
        OnItemClickListener, OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static TextView title;
    private static Dialog dialog;
    private CustomerListAdapter customerAdapter;
    private ListView customerList;
    private ImageView back;
    private ImageView Home;
    private ArrayList<CustomerList> customerRentalsList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_list_view);
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.img_back);
        Home = (ImageView) findViewById(R.id.img_next);
        customerList = (ListView) findViewById(R.id.customerlist);

        customerRentalsList = new ArrayList<>();
        title.setText("Customer List");
        back.setOnClickListener(this);
        customerList.setOnItemClickListener(this);
        new AsyncCustomerList().execute();

    }

    private class AsyncCustomerList extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute(){

            ProgressBar.showCommonProgressDialog(CustomerListActivity.this);

        };

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... params){
            try {
                customerRentalsList = WebServiceConsumer.getInstance()
                        .getRentalsCustomerList(
                                SessionData.getInstance().getUsertoken());
                Log.d("The customer list size is",
                        "" + customerRentalsList.size());

            } catch (java.net.SocketTimeoutException e) {
                customerRentalsList = null;
            } catch (Exception e){
                customerRentalsList = null;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (customerRentalsList != null) {
                ProgressBar.dismiss();

                if (customerRentalsList.get(0).getMessage().equals("")) {
                    back.setVisibility(View.VISIBLE);
                    customerAdapter = new CustomerListAdapter(
                            CustomerListActivity.this, customerRentalsList);
                    customerAdapter.notifyDataSetChanged();
                    customerList.setAdapter(customerAdapter);
                } else {
                    dialog = new Dialog(CustomerListActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(customerRentalsList.get(0).getMessage());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent inspection = new Intent(CustomerListActivity.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(CustomerListActivity.this,
                        "Check WEbservice");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {

        Log.d("Item Click", "" + customerRentalsList.get(position).getCustNum());
        SessionData.getInstance().setCustNum(
                customerRentalsList.get(position).getCustNum());
        SessionData.getInstance().setCustName(
                customerRentalsList.get(position).getCustomerName());
        finish();
        Intent intent = new Intent(CustomerListActivity.this,
                RentalListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(CustomerListActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v){
        if (v == back) {
            onBackPressed();

        }
    }
}
