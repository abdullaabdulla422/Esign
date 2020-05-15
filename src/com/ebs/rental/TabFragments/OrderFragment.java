package com.ebs.rental.TabFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.general.RentalOrderSearch;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.User;
import com.ebs.rental.parts.RentalPartsOrderSearch;
import com.ebs.rental.uidesigns.SpinnerDialog;
import com.ebs.rental.uidesigns.SpinnerInterface;
import com.ebs.rental.uidesigns.Spinnerview;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;

public class OrderFragment extends Fragment implements View.OnClickListener {

    public static TextView branch;
    public static Spinnerview sprOrderType;
    public static Button btnOrderSearch;
    public static ArrayList<String> orderTypes = new ArrayList<String>();

    private ArrayList<DealerBranches> dealer;
    User user;
    private static Dialog dialog;

    int session = 0;

    ListView listView;
    private String unitIdChanged = "";
    private int inst = 1;
    private int inen = 15;
    private int countp;
    DealerAdapter custom_adapter;
    boolean fromClickSearch = false;
    private int count;
    private DealerBranches dealcount;
    private int i;
    private Button ormlist;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.order_tab, container, false);

        branch = view.findViewById(R.id.ac_txt_order_Branch);
        sprOrderType = view.findViewById(R.id.spr_order_type);
        btnOrderSearch = view.findViewById(R.id.btn_order_search);

        orderTypes.clear();
        orderTypes.add("Part Orders");
        orderTypes.add("Rental Orders");
        sprOrderType.setTitle(orderTypes.get(0));

        listView = view.findViewById(R.id.workorder_list);
        ormlist = (Button) view.findViewById(R.id.moreorderlist);


        dealer = SessionData.getInstance().getDealer();
        custom_adapter = new DealerAdapter(getActivity(), dealer);
//        custom_adapter.notifyDataSetChanged();
        listView.setAdapter(custom_adapter);
        listView.setTextFilterEnabled(true);

        user = SessionData.getInstance().getUser();
        if (dealer.size() > 0) {
            dealcount = dealer.get(0);
            i = dealcount.getProwcnt();
            Log.d("value for count", "" + i);
        }

        new AsyncDealerpartsBranch().execute();


        sprOrderType.setOnClickListener(this);
        btnOrderSearch.setOnClickListener(this);
        ormlist.setOnClickListener(this);

        branch.addTextChangedListener(new TextWatcher() {

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

//                if (session == 0) {
//
//                    new AsyncDealerBranchFiltter().execute();
//                } else if (session == 1) {
//                    new AsyncDealerBranchFiltter().execute();
//
//                }

            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SessionData.getInstance().setCustomshipto(
                        SessionData.getInstance().getDealer().get(position)
                                .getkBranch());
                SessionData.getInstance().setBranchcode(
                        SessionData.getInstance().getDealer().get(position)
                                .getkBranch());
                SessionData.getInstance().setSind(
                        SessionData.getInstance().getDealer().get(position)
                                .getPrownum());
                SessionData.getInstance().setRcount(
                        SessionData.getInstance().getDealer().get(position)
                                .getProwcnt());

                // Toast.makeText(getApplicationContext(),SessionData.getInstance().getDealer().get(position).getBranchName(),
                // Toast.LENGTH_LONG).show();
                if (session == 0) {
                    Intent inspection = new Intent(getActivity(),
                            RentalPartsOrderSearch.class);
                    startActivity(inspection);
                } else if (session == 1) {
                    Intent inspection = new Intent(getActivity(),
                            RentalOrderSearch.class);
                    startActivity(inspection);
                }


            }

        });
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == btnOrderSearch) {

//            unitIdChanged = branch.getText().toString();
            // deal2.getBranchName();
//            SessionData.getInstance().setBname(unitIdChanged);
            fromClickSearch = true;

            new AsyncDealerBranchFiltter().execute();

        } else if (v == sprOrderType) {

            SpinnerDialog.ShowSpinnerDialog(getActivity(), orderTypes, new SpinnerInterface() {
                @Override
                public void position(int pos, int view_id) {
                    sprOrderType.setTitle(orderTypes.get(pos));
                    if (pos == 0) {

                        //part Orders
                        session = 0;

                        new AsyncDealerpartsBranch().execute();

                    } else if (pos == 1) {
                        //rental Orders
                        session = 1;

                        new AsyncDealerBranch().execute();

                    }
                }
            }, R.id.spr_order_type, "Select Order type");
        } else if (v == ormlist) {
            if (inst < i) {

                // if(inst < dealcount.getProwcnt()){
                // if(inst < SessionData.getInstance().getRcount()){
                new AsyncDealerBranch().execute();
            } else {
                new AsyncDealerBranchFiltter().execute();
            }

        }
    }


    private class AsyncDealerpartsBranch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
//            if (!ProgressBar.getDialogStatus()) {
//                ProgressBar.showCommonProgressDialog(getActivity());
//            }
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int val = 1;
                int vals = 15;
                user = SessionData.getInstance().getUser();

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                dealer = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        SessionData.getInstance().getBname(),
                        val,
                        vals);

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

            if (dealer != null && !(dealer.equals(""))) {

//				if(!(dealer.get(0).getMessage().equals(""))){
//					ProgressBar.dismiss();
//					AlertDialogBox.showAlertDialog(getActivity(),
//							dealer.get(0).getMessage());
//				}
//				else {
//					Intent inspection = new Intent(getActivity(),
//							RentalPartsOrderBranch.class);
//					startActivity(inspection);
//				}

                if (dealer != null && !(dealer.equals(""))) {

                    if (dealer.get(0).getMessage().equals("")) {
                        SessionData.getInstance().setDealer(dealer);

//                        Custom_adapter custom_adapter = new Custom_adapter(dealer, getActivity());
                        if (SessionData.getInstance().isOrderFirstload()) {
                            dealcount = dealer.get(0);
                            i = dealcount.getProwcnt();
                            Log.d("value for count", "" + i);
                            SessionData.getInstance().setOrderFirstload(false);
                        }
                        countp = dealer.size();
                        listView.setAdapter(custom_adapter);

//                        custom_adapter.notifyDataSetChanged();


//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(getActivity(),
//								dealer.get(0).getMessage());
//					}
//					else{
//-----------------------------------------------------------------------------------

//                        Intent inspection = new Intent(getActivity(),
//                                RentalPartsOrderBranch.class);
//                        startActivity(inspection);
////-----------------------------------------------------------------------------------

//					}
                    } else {

                        if (dealer.get(0).getMessage().contains("Session")) {
                            session=0;
                            new AsyncLoginTask().execute();

                        } else {
                            dialog = new Dialog(getActivity());
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

//									Intent inspection = new Intent(getActivity(),
//											MainActivity.class);
//									startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }
                    }
                }

            } else {

                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");
            }

        }

    }

    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
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


                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(getActivity());
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

                            Intent inspection = new Intent(getActivity(),
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    if (session == 0) {
//                        new AsyncCrossReference().execute();
//                    } else if (session == 1) {

                    new AsyncDealerpartsBranch().execute();
                    } else if (session == 1) {
                        new AsyncDealerBranch().execute();
                    } else if (session == 2) {
                        new AsyncDealerBranchFiltter().execute();
                    }
                }


            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found");
            }


        }
    }


    class Custom_adapter extends BaseAdapter {

        ArrayList<DealerBranches> arrayList;
        Context context;

        Custom_adapter(ArrayList<DealerBranches> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return countp;
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(context).inflate(R.layout.child_dealer_list, null);

                holder.txtBranch = (TextView) convertView
                        .findViewById(R.id.txt_branch_no);
                holder.txtBranchName = (TextView) convertView
                        .findViewById(R.id.txt_branch_name);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }

//            TextView branch = convertView.findViewById(R.id.txt_branch_no);
//            TextView branchName = convertView.findViewById(R.id.txt_branch_name);


            holder.txtBranch.setText(arrayList.get(position).getkBranch());
            holder.txtBranchName.setText(arrayList.get(position).getBranchName().split("-")[1]);

            return convertView;
        }


    }

    class ViewHolder {
        TextView txtBranch;
        TextView txtBranchName;
    }

    public class DealerAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private final Context mContext;

        private final ArrayList<DealerBranches> list;

        public DealerAdapter(Context context, ArrayList<DealerBranches> list) {
            mContext = context;
            this.list = list;
            list = null;

        }

        @Override
        public int getCount() {

            return countp;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (dealer.size() <= position) {

            } else {
                DealerBranches deal = (DealerBranches) dealer.get(position);
                if (convertView == null) {
                    holder = new ViewHolder();

                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.child_dealer_list, null);


                    holder.txtBranch = (TextView) convertView
                            .findViewById(R.id.txt_branch_no);
                    holder.txtBranchName = (TextView) convertView
                            .findViewById(R.id.txt_branch_name);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.txtBranch.setText(deal.getkBranch());
                holder.txtBranchName.setText(deal.getBranchName().split("-")[1]);
            }
            return convertView;

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    }

    private class AsyncDealerBranchFiltter extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            if (fromClickSearch) {
                fromClickSearch = false;
                ProgressBar.showCommonProgressDialog(getActivity());
            }
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                inst = 1;

                inen = 15;

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
            if (dealer.size() == 0) {
                Toast.makeText(getActivity(), "No More Data to Load", Toast.LENGTH_LONG).show();
            } else {

                if (dealer.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setDealer(dealer);
                    countp = dealer.size();
                    listView.setAdapter(custom_adapter);

//                    custom_adapter.notifyDataSetChanged();
                } else {
                    if (dealer.get(0).getMessage().contains("Session")) {
                        session = 2;
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(getActivity());
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


                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }
            }

        }

    }

    private class AsyncDealerBranch extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(getActivity());
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int dval = 1;
                int dvals = 15;

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());


                dealer = WebServiceConsumer.getInstance().getDealerBranch(
                        user.getUserDescription(),
                        SessionData.getInstance().getBname(), dval,
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
            if (dealer != null && !(dealer.equals(""))) {

                if (dealer.get(0).getMessage().equals("")) {
                    SessionData.getInstance().setDealer(dealer);

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(getActivity(),
//								dealer.get(0).getMessage());
//					}
//					else{
//                    Intent inspection = new Intent(getActivity(),
//                            RetntalOrderBranch.class);
//                    startActivity(inspection);
//					}
                } else {

                    if (dealer.get(0).getMessage().contains("Session")) {
                        session =1;
                        new AsyncLoginTask().execute();

                    } else {
                        dialog = new Dialog(getActivity());
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

//								Intent inspection = new Intent(getActivity(),
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
                AlertDialogBox.showAlertDialog(getActivity(),
                        "Data is not found.");
            }

        }

    }

}