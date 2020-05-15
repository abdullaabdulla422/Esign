package com.ebs.rental.parts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderSignedDocmuntPDFObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class PartsMailDetails extends AppCompatActivity implements OnClickListener {
    private ListView list;
    private Customeremails customeremails;
    private String string;
    private ArrayList<String> aList;
    ArrayList<String> edtlist;
//    private ArrayAdapter<String> adapter;
    private int count;
    private Button submit;
    private EditText editmail;
    private RentalOrderList deal;
    private ImageView back;
    private static Dialog dialog;
    private TextView backtext;
    RentalOrderListDetailObject DetailObjects;
    private String email;
    String emailPattern;
    private User user;
    private RentalOrderSignedDocmuntPDFObject rentaorderdocpdf;
    private String str;
    private String signature;

    ArrayList<CustomerNameMail> customerNameMails = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsSession = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsNew = new ArrayList<>();
    private Adaptor adap;
    private ImageView imgAddMail;
    private String session="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.parts_mail_view);
        // SessionData.getInstance().setBranch(deal.getBranch());
        customeremails = SessionData.getInstance().getCustomeremails();
        string = customeremails.getResult();
        // signature = SessionData.getInstance().getSignData().toString();
        if (SessionData.getInstance().getSig() != null) {
            signature = Base64.encodeToString(SessionData.getInstance()
                    .getSig().clone(), Base64.DEFAULT);
        }

        Log.d("signature data", "" + SessionData.getInstance().getSig());
        Log.d("signature data", "" + signature);
        aList = new ArrayList<String>(Arrays.asList(string.split(",")));
        list = (ListView) findViewById(R.id.pmaillist);
//        adapter = new ArrayAdapter<String>(this,
//                R.layout.simple_list_multi_choice, aList);
        // adap = new MailAdapter(CustomerMailDetails.this, aList);
//        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        editmail = (EditText) findViewById(R.id.pedtmail);
        editmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        backtext = (TextView) findViewById(R.id.backtext);

        imgAddMail = (ImageView) findViewById(R.id.img_add_mail);
        imgAddMail.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.backicon);
        back.setOnClickListener(this);
        backtext.setOnClickListener(this);
        user = SessionData.getInstance().getUser();
        deal = SessionData.getInstance().getVal();
        count = aList.size();
//        SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
//        int itemCount = list.getCount();
//        if (count == 0) {
//            list.setVisibility(View.INVISIBLE);
//
//            for (int i = itemCount - 1; i >= 0; i--) {
//                if (checkedItemPositions.get(i)) {
//                    adapter.remove(aList.get(i));
//                }
//            }
//
//            checkedItemPositions.clear();
//            adapter.notifyDataSetChanged();
//        } else {
//            list.setVisibility(View.VISIBLE);
//        }
//		/*if (count == 0) {
//			list.setVisibility(View.GONE);
//		}*/
//        list.setAdapter(adapter);
//        list.setTextFilterEnabled(true);


        customerNameMails = SessionData.getInstance().getCustomerNameMails();
        customerNameMailsSession.addAll(SessionData.getInstance().getCustomerNameMails());
        adap = new Adaptor(PartsMailDetails.this, customerNameMails);

        list.setAdapter(adap);


        submit = (Button) findViewById(R.id.btnpsubmit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
//            SparseBooleanArray checked = list.getCheckedItemPositions();
//            ArrayList<String> selecteditems = new ArrayList<String>();
//            for (int i = 0; i < checked.size(); i++) {
//                int position = checked.keyAt(i);
//
////                if (checked.valueAt(i))
////                    selecteditems.add(adapter.getItem(position));
////                if (checkedItemPositions.get(i)) {
////                    adapter.remove(aList.get(i));
////                }
//            }
//            String[] outputStrArr = new String[selecteditems.size()];
//            for (int i = 0; i < selecteditems.size(); i++) {
//                outputStrArr[i] = selecteditems.get(i);
//            }
//
//            str = selecteditems.toString();
//            str = str.replace("[", "");
//            str = str.replace("]", "");
//            email = editmail.getText().toString().trim();
//            email = email.replace(" ", "");
//            String[] test = email.split(",");
//            boolean emailflag = true;
//            for (int i = 0; i < test.length; i++) {
//
//                if (validEmail(test[i])) {
//                    emailflag = true;
//                } else {
//                    emailflag = false;
//                    break;
//                }
//
//            }
//            if (editmail.getText().toString().length() == 0 && str.isEmpty()) {
//                Toast.makeText(getApplicationContext(),
//                        "Select or Enter the Mail", Toast.LENGTH_LONG).show();
//            } else {
//                if (editmail.getText().toString().length() != 0
//                        && emailflag == false) {
//                    Toast.makeText(getApplicationContext(), "Invalid Mail ID",
//                            Toast.LENGTH_LONG).show();
//                } else {
//
//                    new AsynSubmitSigPdf().execute();
//                }
//            }

            updateEmailsAndSubmit();
        } else if (v == back) {
            onBackPressed();
        } else if (v == backtext) {
            onBackPressed();
        }else if (v==imgAddMail){
            addMail();
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(PartsMailDetails.this,
                PartsTermsAndConditions.class);
        startActivity(intent);

    }

    private class AsynSubmitSigPdf extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(PartsMailDetails.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try {

                rentaorderdocpdf = WebServiceConsumer.getInstance()
                        .rentalOrderSignedDocumentPdf(
                                user.getUserDescription(),
                                Integer.toString(SessionData.getInstance()
                                        .getOrdernumber()),
                                SessionData.getInstance().getKcustnum(),
                                SessionData.getInstance().getBranchcode(),
                                /* deal.getBranch(), */
                                "P", SessionData.getInstance().getSigned(), 0,
                                SessionData.getInstance().getCustomshipto(),
                                str, email,
                                SessionData.getInstance().getContact(),
                                SessionData.getInstance().getHassign(), "", "", "", "");
                /*
                 * user.getUserDescription(),
                 * Integer.toString(SessionData.getInstance().getOrdernumber()),
                 * DetailObjects.getCustsnum(), deal.getBranch(), "R",
                 * signature, 0, deal.getShipto(), str, email,
                 * SessionData.getInstance().getContact()
                 */

            } catch (java.net.SocketTimeoutException e) {
                rentaorderdocpdf = null;

            } catch (Exception e) {
                rentaorderdocpdf = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();


            if (rentaorderdocpdf != null) {
                if (SessionData.getInstance().getRentalOrderSignedDocumentPdf() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Order # Signed Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PartsMailDetails.this,
                            RentalPartsOrderSearch.class);
                    startActivity(intent);
                } else {

                    if (rentaorderdocpdf.getMessage().contains("Session")) {
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(PartsMailDetails.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(rentaorderdocpdf.getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//								Intent inspection = new Intent(PartsMailDetails.this,
//										MainActivity.class);
//								startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }
            } else {
                ProgressBar.dismiss();
            }

        }

    }


    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(PartsMailDetails.this);
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
                SessionData.getInstance().setUser(user);


                if (user.getUserId() == 0) {
                    dialog = new Dialog(PartsMailDetails.this);
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

                            Intent inspection = new Intent(PartsMailDetails.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    new AsynSubmitSigPdf().execute();

                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(PartsMailDetails.this,
                        "Data is not found");
            }
        }
    }

    private boolean validEmail(String email) {

        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public class Adaptor extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<CustomerNameMail> list;

        public Adaptor(Context context, ArrayList<CustomerNameMail> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        public int getCount() {
            // this.notifyDataSetChanged();
            return list.size();
        }

        public Object getItem(int position) {
            // this.notifyDataSetChanged();

            return null;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.activity_mail_row, null);
                // holder.txtCustName = (TextView) convertView
                // .findViewById(R.id.txt_custname);
                holder.txt_customerName = (TextView) convertView
                        .findViewById(R.id.txt_cusomter_name);
                holder.txt_customerMail = (TextView) convertView
                        .findViewById(R.id.txt_customer_mail);

                holder.checkbox = (CheckBox) convertView
                        .findViewById(R.id.select_mail);

                holder.edt_customerName = (EditText) convertView.findViewById(R.id.edt_cusomter_name);
                holder.edt_customerMail = (EditText) convertView.findViewById(R.id.edt_customer_mail);
                holder.new_layout = (LinearLayout) convertView.findViewById(R.id.new_layout);
                holder.existing_layout = (LinearLayout) convertView.findViewById(R.id.existing_layout);
                holder.img_delete = (ImageView) convertView.findViewById(R.id.delete_mail);

                if (list.get(position).getIsAlreadyExit() == 1) {
                    holder.new_layout.setVisibility(View.GONE);
                    holder.existing_layout.setVisibility(View.VISIBLE);
                } else {
                    holder.new_layout.setVisibility(View.VISIBLE);
                    holder.existing_layout.setVisibility(View.GONE);
                }

                convertView.setTag(holder);
                // this.notifyDataSetChanged();
                holder.checkbox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        //CustomerNameMail detail = (CustomerNameMail) cb.getTag();
                        list.get(position).setSelected(cb.isChecked());
                        customerNameMails.get(position).setSelected(cb.isChecked());

                    }
                });
                holder.img_delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                holder.edt_customerMail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (position > customerNameMailsSession.size()) {
                            customerNameMailsNew.get(position - customerNameMailsSession.size()).setEmail(s.toString());
                        }

                        customerNameMails.get(position).setEmail(s.toString());
                        list.get(position).setEmail(s.toString());
                        //	adap.notifyDataSetChanged();
                    }
                });

                holder.edt_customerName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (position > customerNameMailsSession.size()) {
                            Log.i("customerNameMailsNew", "customerNameMailsNew.size()= "
                                    + customerNameMailsNew.size() + "position= " + position
                                    + "customerNameMailsSession.size()=" + customerNameMailsSession.size());

                            customerNameMailsNew.get(position - customerNameMailsSession.size()).setCustname(s.toString());
                        }

                        customerNameMails.get(position).setCustname(s.toString());
                        list.get(position).setCustname(s.toString());
                        //adap.notifyDataSetChanged();
                    }
                });

                holder.img_delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position > customerNameMailsSession.size()) {
                            customerNameMailsNew.remove(position - customerNameMailsSession.size());
                        }
                        customerNameMails.remove(position);
                        //list.remove(position);
                        adap.notifyDataSetChanged();
                    }
                });

                //	adap.notifyDataSetChanged();

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//			textRentalComName.setText(detailList.get(0).getCustName());
//			textRentalorderno.setText("" + detail.getOrderNo());

//			SessionData.getInstance().setRentalId(
//					detailList.get(0).getRentalID());


            holder.txt_customerName.setText(list.get(position).getCustname());
            holder.txt_customerMail.setText(list.get(position).getEmail());

            holder.edt_customerName.setText(list.get(position).getCustname());
            holder.edt_customerMail.setText(list.get(position).getEmail());

            if (list.get(position).isSelected()) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }

            // this.notifyDataSetChanged();
            return convertView;

        }

        class ViewHolder {
            TextView txt_customerName, txt_customerMail;
            EditText edt_customerName, edt_customerMail;
            LinearLayout existing_layout, new_layout;
            CheckBox checkbox;
            ImageView img_delete;

        }

        public class MyListAdapter extends ArrayAdapter<Object> {
            private final boolean[] mCheckedState;
            private final Context mContext;

            public MyListAdapter(Context context, int resource,
                                 int textViewResourceId, List<Object> objects) {
                super(context, resource, textViewResourceId, objects);

                mCheckedState = new boolean[objects.size()];
                mContext = context;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                CheckBox result = (CheckBox) convertView;
                if (result == null) {
                    result = new CheckBox(mContext);
                }
                result.setChecked(mCheckedState[position]);
                return result;
            }
        }

        @Override
        public long getItemId(int position) {

            return position;

        }

    }

    public void addMail() {
        if (customerNameMails.size()>0) {
            if (customerNameMails.get(customerNameMails.size() - 1).getEmail().length() != 0) {

                boolean emailflag = true;


                if (validEmail(customerNameMails.get(customerNameMails.size() - 1).getEmail())) {
                    emailflag = true;
                } else {
                    emailflag = false;

                }

                if (emailflag) {
                    CustomerNameMail customerNameMail = new CustomerNameMail();
                    customerNameMail.setIsAlreadyExit(0);
                    customerNameMail.setMessage("");
                    customerNameMail.setEmail("");
                    customerNameMail.setCustname("");
                    customerNameMail.setSelected(false);
                    customerNameMails.add(customerNameMail);
                    customerNameMailsNew.add(customerNameMail);
                    adap = new Adaptor(PartsMailDetails.this, customerNameMails);
                    list.setAdapter(adap);
                    adap.notifyDataSetChanged();
                } else {
                    Toast.makeText(PartsMailDetails.this, "Enter the Vaild Mail", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(PartsMailDetails.this, "Enter the Mail", Toast.LENGTH_LONG).show();
            }
        }else {
            CustomerNameMail customerNameMail = new CustomerNameMail();
            customerNameMail.setIsAlreadyExit(0);
            customerNameMail.setMessage("");
            customerNameMail.setEmail("");
            customerNameMail.setCustname("");
            customerNameMail.setSelected(false);
            customerNameMails.add(customerNameMail);
            customerNameMailsNew.add(customerNameMail);
            adap = new Adaptor(PartsMailDetails.this, customerNameMails);
            list.setAdapter(adap);
            adap.notifyDataSetChanged();
        }

    }


    public class AsyncUpdateContactEmails extends AsyncTask<ArrayList<CustomerNameMail>, Void, Void> {
        String result = "";

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(PartsMailDetails.this);
        }

        ;

        @Override
        protected Void doInBackground(ArrayList<CustomerNameMail>... params) {
            try {

                for (int k = 0; k < params[0].size(); k++) {
                    result = WebServiceConsumer.getInstance()
                            .updateContactEmails(SessionData.getInstance().getTemp_userToken(),
                                    SessionData.getInstance().getStrKcustnum(),
                                    SessionData.getInstance().getStrCustnum(),
                                    SessionData.getInstance().getStrSigntype(),
                                    params[0].get(k).getCustname(),
                                    params[0].get(k).getEmail());

                    Log.i("updateContactEmails", "name: " + params[0].get(k).getCustname() + "; email: " + params[0].get(k).getEmail() + "; item: " + k + "; result" + result.toString());
                }

            } catch (java.net.SocketTimeoutException e) {
                result = null;
                e.printStackTrace();
            } catch (Exception e) {
                result = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            ProgressBar.dismiss();
            if (result != null) {
                if (result.contains("Session")) {
                    session = "AsyncUpdateContactEmails";
                    new AsyncLoginTask().execute();
                } else {
                    ProgressBar.dismiss();

//                if (mailUpdateCount == customerNameMails.size())
                    new AsynSubmitSigPdf().execute();
                }
            } else {
                ProgressBar.dismiss();

                //                if (mailUpdateCount == customerNameMails.size())
                new AsynSubmitSigPdf().execute();
            }
        }
    }

    public String getEmailSting(ArrayList<CustomerNameMail> customerNameMails) {
        String strEmail = "";

        for (int i = 0; i < customerNameMails.size(); i++) {

            if (strEmail == "") {
                strEmail = strEmail + customerNameMails.get(i).getEmail();
            } else {
                strEmail = strEmail + "," + customerNameMails.get(i).getEmail();
            }
        }

        return strEmail;
    }


    public String getStrEmailSting(ArrayList<CustomerNameMail> customerNameMails) {
        String strEmail = "";

        for (int i = 0; i < customerNameMails.size(); i++) {
            if (customerNameMails.get(i).isSelected()) {
                if (strEmail == "") {
                    strEmail = strEmail + customerNameMails.get(i).getEmail();
                } else {
                    strEmail = strEmail + "," + customerNameMails.get(i).getEmail();
                }
            }
        }

        return strEmail;
    }

    public void updateEmailsAndSubmit(){

        if (customerNameMailsNew.size() > 0) {
            if (validEmail(customerNameMailsNew.get(customerNameMailsNew.size() - 1).getEmail())) {
                str = getStrEmailSting(customerNameMails);
                email = getEmailSting(customerNameMailsNew);

                Log.i("getStrEmailSting", "str: " + str);
                Log.i("getEmailSting", "email: " + email);

                new AsyncUpdateContactEmails().execute(customerNameMailsNew);
            } else {

                Toast.makeText(this, "Enter a valid mail", Toast.LENGTH_SHORT).show();

            }
        }else {
            str = getStrEmailSting(customerNameMails);
            email = getEmailSting(customerNameMailsNew);

            Log.i("getStrEmailSting", "str: " + str);
            Log.i("getEmailSting", "email: " + email);

            new AsyncUpdateContactEmails().execute(customerNameMailsNew);
        }
    }
}
