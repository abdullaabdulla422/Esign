package com.ebs.rental.general;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CheckinDetail;
import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.Equipmentsubstatusdesc;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.ReadJson;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by techunity on 18/10/16.
 */

@SuppressWarnings("ALL")
public class CustomizedGeneralpartsCheck extends AppCompatActivity {

    private LinearLayout linearMain;
    private ScrollView scroll;
    private static Dialog dialog;

    private int session = 0;
    CheckBox checkBox;
    //	CheckBox typecheckBox;
    Spinner spinner;
    private ArrayList<EquipmentCheckParts> partsList;
    private ArrayList<CheckList> checkList;
    private RentalCheck rentalcheckin;
    CheckinEqupments checkinequpments;
    CheckinDetail checkindetails;
    RentalChecklistPDF checklistpdf;
    private EqupmentSubStatus equpsubStatus;

    int hr;
    private int checkin;
    private ArrayList<String> mylabel;
    String label;
    private ArrayList<Integer> spinn;

    User objUser = null;

    private EqupmentSubStatus equpsubStatusdata;
    private Equipmentsubstatusdesc equpsubstatusdec;
    String rentalCheckinList;
    private static int i = 0;
    private static int j = 0;
    public static int z;
    int getrentalcheck;
    private double dpi;

    Button next, back, submitButton;
    private Button nextBtn;
    private Button backBtn;
    private Button submitBtn;
    private EditText hourMeter;
    private Spinner Fuellevel;
    private Spinner substatus;
    private Spinner substatushide;
    private Spinner eqpStatus;
    private TextView due;
    private TextView Hourmeter;
    private TextView FuelLevel;
    private TextView Due;
    private TextView Substatus;
    private TextView Substatusdec;
    private TextView Equpstatus;
    private TextView txtsubStatus;
    String selectedValues;
    private String substatusdesc;
    private String statusforeqp;
    private String statusselect;

    private static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int LIBRARY_IMAGE_REQUEST_CODE = 101;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "rental_data/Camera";
    private static int RESULT_CODE = -1;
    private Uri fileUri;
    private ExifInterface exif;
    private ImageView imgback;
    private int count;
    private User user;
    String cc;
    private String pmSpc;
    private String dueStatus;
    private String emeter;
    private String ereading;
    private String lastday;
    private String lasthour;
    int rentaldetailid;
    private final String h = "H";
    private final String r = "R";
    private String fuel;
    private String equpst;
    private boolean[] thumbnailsselection;

    private CheckinListData checklistdataentry;
    CheckinListData check1;
    private EquipmentCheckParts checkparts;
    private ArrayList<String> cheklistArray;
    private ArrayList<CheckinListData> notes;
    TextView txtTitle;
    private TextView backbtn;
   // ArrayList<RentalDetails> selectedDetail;
   private RentalDetails detail;
    private ArrayList<RentalDetails> detailList;
    private ArrayList<String> aStatus;
    private int sub;
    private String str;
    private String kcust;
    private String custnum;
    private GeneralEquipmentSearchObject generalEquipment;
    private GeneralEquipmentSearchObject generalEquipmentSearchObject;

    private TextView txtMake;
    private TextView txtModel;
    private TextView txtSerialNo;
    private TextView txtUnitId;
    private int SPEECH_CODE_ONE = 101;
    private EditText speech_textView;
    int previoushours;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customized_general_parts_check);

//        txtTitle = (TextView) findViewById(R.id.rentalcompanyname);
        backbtn = (TextView) findViewById(R.id.back);
        imgback = (ImageView) findViewById(R.id.close);
        backbtn.setOnClickListener(back1);
        imgback.setOnClickListener(back1);
        nextBtn = (Button) findViewById(R.id.btnnextpress);
        backBtn = (Button) findViewById(R.id.btnbackpress);
        submitBtn = (Button) findViewById(R.id.btnsubmitpress);

        generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        txtMake = (TextView)findViewById(R.id.mfg);
        txtModel = (TextView)findViewById(R.id.model);
        txtSerialNo = (TextView)findViewById(R.id.serialno);
        txtUnitId = (TextView)findViewById(R.id.unit_id);

        txtMake.setText(generalEquipment.getMfg());
        txtModel.setText(generalEquipment.getModel());
        txtSerialNo.setText(generalEquipment.getSerialno());
        txtUnitId.setText(generalEquipment.getEquipmentid());

        substatus = new Spinner(this);
        scroll = (ScrollView) findViewById(R.id.linearScroll);
        //SessionData.getInstance().setLabelcondition(0);
        dpi = getResources().getDisplayMetrics().density;
        notes = new ArrayList<CheckinListData>();
        linearMain = (LinearLayout) findViewById(R.id.linearMain);
        linearMain.setOrientation(LinearLayout.VERTICAL);
        linearMain.removeAllViews();
        checkparts = new EquipmentCheckParts();


        if(SessionData.getInstance().getMulticheckinvalidate() == 0)
        {
            i = 0;

            j = SessionData.getInstance().getChecklistdata();
//	initializeViews(checkList);
        }

        else {
            j = 0;
            SessionData.getInstance().setChecklistdata(0);
            j = SessionData.getInstance().getChecklistdata();
        }



       // selectedDetail = SessionData.getInstance().getSelectedDetail();


        user = SessionData.getInstance().getUser();

        if(SessionData.getInstance().getRentalOrGeneral()==0){
            detailList = SessionData.getInstance().getDetail();
            detail = detailList.get(0);
            count = detailList.size();
        }else {
            count = 1;
        }
        mylabel = new ArrayList<String>();
        spinn = new ArrayList<Integer>();

        thumbnailsselection = new boolean[count];

        if(SessionData.getInstance().getRentalOrGeneral()==0){
            Log.d("The selected equipments size1", ""
                    + SessionData.getInstance().getCheckListData().size());
            cheklistArray = new ArrayList<String>(SessionData.getInstance()
                    .getCheckListData().values());
            Log.d("The Checklist Array Size", "" + cheklistArray.size());
            Log.d(" Array index", "" + j);
        }else{
            Log.d("The selected equipments size2", ""
                    + SessionData.getInstance().getGeneralcheckListData().size());
            cheklistArray = new ArrayList<String>(SessionData.getInstance()
                    .getGeneralcheckListData().values());
            Log.d("The gendralChecklist Array Size", "" + cheklistArray.size());
            Log.d(" Array index", "" + j);
        }


        if (cheklistArray!=null&&cheklistArray.size()>0) {
            checkList = ReadJson.getPartsList(cheklistArray.get(j));
        }

        new Asyncequpment().execute();

        getGps();

    }

    private class Asyncequpment extends AsyncTask<Void, Void, Void>{

        protected void onPreExecute(){
            ProgressBar.showCommonProgressDialog(CustomizedGeneralpartsCheck.this);
        };

        @Override
        protected Void doInBackground(Void... params){
            try {

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                equpsubStatus = WebServiceConsumer
                        .getInstance()
                        .equpmentSubStatus(user.getUserDescription(),
                                SessionData.getInstance().getEqpStatus().get(j));
            } catch (java.net.SocketTimeoutException e){

                equpsubStatus = null;
            } catch (Exception e){

                equpsubStatus = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(SessionData.getInstance().getEqupmentSubStatusResult()==1){
                ProgressBar.dismiss();

                if(equpsubStatus.getMessage().contains("Session")){

                    session = 0;
                    new AsyncSessionLoginTask_General().execute();

                }else{
                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(equpsubStatus.getMessage());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }



            }
            else{
                SessionData.getInstance().setSubstatus(equpsubStatus.getResult());
                str = equpsubStatus.getResult();
                SessionData.getInstance().setDd( equpsubStatus.getResult());

                Log.d("Sessiondata", "" + equpsubStatus.getResult());
                ProgressBar.dismiss();

                initializeViews(checkList);
            }


        }
    }

    private class Asyncequpmentdec extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(CustomizedGeneralpartsCheck.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());
//                if(statusselect.contains("Select")){
//                    statusselect="";
//                }
                //Log.d("statusempty1",""+statusselect);
                equpsubstatusdec = WebServiceConsumer.getInstance()
                        .equpmentdescription(user.getUserDescription(),
                                statusselect, equpst.trim());
            } catch (java.net.SocketTimeoutException e) {

                equpsubstatusdec = null;
            } catch (Exception e) {

                equpsubstatusdec = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(SessionData.getInstance().getEquipmentsubstatusdescResult()==1){

                if(rentalcheckin.getMessage().contains("Session")){
                    session = 1;
                    new AsyncSessionLoginTask_General().execute();
                }
                else{
                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(rentalcheckin.getMessage());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }

            }
            else{

                SessionData.getInstance().setEqupsubstatusdes(
                        equpsubstatusdec.getResult());

                Log.d("Sessiondata", "" + equpsubstatusdec.getResult());
                ProgressBar.dismiss();

                txtsubStatus.setText(SessionData.getInstance()
                        .getEqupsubstatusdes());
            }

        }
    }

    private class Asyncequpments extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            // ProgressBar.showCommonProgressDialog(CustomizedPartsCheck.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());
                if(statusselect.contains("Select Status")){
                    statusselect="";
                }
                Log.d(".",""+statusselect);
                equpsubStatusdata = WebServiceConsumer.getInstance()
                        .equpmentSubStatus(user.getUserDescription(),
                                statusselect);
            } catch (java.net.SocketTimeoutException e) {

                equpsubStatusdata = null;
            } catch (Exception e) {

                equpsubStatusdata = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(SessionData.getInstance().getEqupmentSubStatusResult()==1){
                ProgressBar.dismiss();
                if(rentalcheckin!=null) {

                    if (rentalcheckin.getMessage().contains("Session")) {
                        session = 2;
                        new AsyncSessionLoginTask_General().execute();
                    } else {
                        dialog = new Dialog(CustomizedGeneralpartsCheck.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(rentalcheckin.getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                            Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }

            }
            else {
                SessionData.getInstance().setSubstatus(
                        equpsubStatusdata.getResult());
                Log.d("Sessiondatasubstatus", "" + equpsubStatusdata.getResult());
                str = equpsubStatusdata.getResult();
                Log.d("string", "" + str);

                 ArrayList<String> aStatuss = new
                 ArrayList<String>(Arrays.asList(str
                 .split(",")));
                data();
            }
            // ProgressBar.dismiss();

            // initializeViews(checkList);
        }


    }


    private class AsyncSessionLoginTask_General extends AsyncTask<Void, Void, Void>{

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(CustomizedGeneralpartsCheck.this);
        };

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());
                Log.d("Session Expiered","Session Expiered");
                Log.d("New User Token",""+SessionData.getInstance().getTemp_userToken());
                Log.d("After Session Expired","Equip_ID"+SessionData.getInstance().getTemp_equipId());
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

            if(user!=null){

                if(user.getUserId()==0){
                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
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

                            Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }else {
                    SessionData.getInstance().setUser(user);
                    if(session==0){
                        new Asyncequpment().execute();
                    }else if(session == 1){
                        new Asyncequpmentdec().execute();
                    }else if(session ==2){
                        new Asyncequpments().execute();
                    }

                }
            }else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(CustomizedGeneralpartsCheck.this,
                        "Data is not found");
            }
        }
    }


    private void data() {


        aStatus = new
                ArrayList<String>(Arrays.asList(str.split(",")));
        int dataret = aStatus.size();

        String status = null;

        int m;

        for (m = 0; m < dataret; m++) {
            String str = aStatus.get(m);
            String[] str1 = str.split("~");
            if (str1.length == 2) {

                status = str1[0];

                aStatus.set(m, status);

            } else {
                status = str1[0];
                aStatus.set(m, status);

            }
        }
        ArrayAdapter<String> dataAdapters = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aStatus);
        dataAdapters
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        substatus.setAdapter(dataAdapters);
    }

    private void getGps() {
        GPSTracker gps  = new GPSTracker(CustomizedGeneralpartsCheck.this);
        if (gps.canGetLocation()){
            Double latitude = gps.getLatitude();
            Double longitude = gps.getLongitude();
            SessionData.getInstance().setLatitude(latitude);
            SessionData.getInstance().setLongitude(longitude);
        } else {
            gps.showSettingsAlert();
        }
    }

    public int getCount() {

        return count;
    }

    public Object getItem(int position) {

        return null;
    }

    private final View.OnClickListener back1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onBackPressed();

        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    private void initializeViews(ArrayList<CheckList> check) {

//        txtTitle.setText(SessionData.getInstance().getKpartlist().get(j));
        LinearLayout lllayout = new LinearLayout(this);
        lllayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, (int) (50 * dpi));
        lllayout.setLayoutParams(lparams);
        LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, (int) (50 * dpi));
        lllayout.setLayoutParams(bparams);
        LinearLayout.LayoutParams bbparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, (int) (50 * dpi));
        lllayout.setLayoutParams(bbparams);

        lparams.gravity = Gravity.CENTER;
        bparams.gravity = Gravity.LEFT;
        bbparams.gravity = Gravity.END;

        lparams.setMargins(10, 10, 10, 10);
        Log.d("the page list size is", "" + checkList.size());

        if ((checkList.size() == 1)) {
            nextBtn.setVisibility(View.GONE);
            backBtn.setVisibility(View.GONE);
        }
//		else
//		{
//			linearMain.removeAllViews();
//
//			nextBtn.setVisibility(View.VISIBLE);
//			backBtn.setVisibility(View.GONE);
//			submitBtn.setVisibility(View.GONE);
//
//		}
        if (i >= 0 && i < (checkList.size() - 1)) {
            if (i == 0) {
                nextBtn.setVisibility(View.VISIBLE);
				backBtn.setVisibility(View.GONE);
				submitBtn.setVisibility(View.GONE);

            } else {
                backBtn.setVisibility(View.VISIBLE);
            }

        }
        LinearLayout.LayoutParams editprams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (50 * dpi));
        editprams.setMargins(10, 5, 10, 5);

        partsList = check.get(0).getChecklist();
        hourMeter = new EditText(this);
        hourMeter.setHint("Enter hour Meter");
        if(SessionData.getInstance().getPrevioushours().equals("0")){
            if (generalEquipment.getHourmeter() == 0) {
                hourMeter.setText("");
            } else {
                hourMeter.setText("" + generalEquipment.getHourmeter());
            }
            if(generalEquipment.getHourmeter()==0){
                previoushours = 0;
            }else{
                previoushours = generalEquipment.getHourmeter();
            }
        }else{
            hourMeter.setText(SessionData.getInstance().getPrevioushours());
            if(SessionData.getInstance().getPrevioushours().length()==0) {
                previoushours = 0;
            }
            else{
                previoushours = Integer.parseInt(SessionData.getInstance().getPrevioushours());
            }
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        hourMeter.setBackgroundResource(R.drawable.editext_background);

        hourMeter.setLayoutParams(editprams);
        hourMeter.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtsubStatus = new TextView(this);

        int maxLength = 50;
        txtsubStatus
                .setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                        maxLength) });
        txtsubStatus.setBackgroundResource(R.drawable.editext_background);
        txtsubStatus.setLayoutParams(editprams);
        txtsubStatus.setTextSize(20);

        Fuellevel = new Spinner(this);
        Fuellevel.setLayoutParams(editprams);

        substatus.setLayoutParams(editprams);
        substatushide = new Spinner(this);
        substatushide.setLayoutParams(editprams);
        due = new TextView(this);
        due.setBackgroundResource(R.drawable.editext_background);
        due.setLayoutParams(editprams);
        due.setTextSize(20);
        statusforeqp = SessionData.getInstance().getEqpStatus().get(j);
        eqpStatus = new Spinner(this);

        eqpStatus.setLayoutParams(editprams);

        List<String> list1 = new ArrayList<String>();

        String str_status = generalEquipment.getEquipmentstatus();
//        list1.add(str_status);

        if (str_status.equals("")) {
            list1.add("Select Status");
        }else{
            list1.add(str_status);
        }

        if (!str_status.equals("AV")) {
            list1.add("AV");
        }
        if (!str_status.equals("MA")) {
            list1.add("MA");
        }
//        if (!str_status.equals("PI")) {
//            list1.add("PI");
//        }

        ArrayAdapter<String> dataAdapterstatus = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapterstatus
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eqpStatus.setAdapter(dataAdapterstatus);
        eqpStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                statusselect = arg0.getItemAtPosition(arg2).toString();
                Log.d("statusselect", "" + statusselect);
                new Asyncequpments().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

      //  kcust =SessionData.getInstance().getSelectedDetail().get(j).getKcustnum();
       // custnum = SessionData.getInstance().getSelectedDetail().get(j).getCustsnum();
        SessionData.getInstance().setKcustadd(kcust);
        SessionData.getInstance().setCustadd(custnum);
        pmSpc = (generalEquipment
                .getPmspec());
        dueStatus = ("");
        emeter = ("");
        ereading = String.valueOf((generalEquipment
                .getEquipmentreading()));
        lastday = (generalEquipment
                .getEquipmentdatelast());
        lasthour = (generalEquipment
                .getLasthours());
//        rentaldetailid = (SessionData.getInstance().getSelectedDetail().get(j).getRentDetId());
//        SessionData.getInstance().setRentaldetlid(rentaldetailid);
        Log.d("PMSPEC", "" + pmSpc);
        Log.d("lastday", "" + lastday);
        Log.d("dueStatus", "" + dueStatus);
        Log.d("emeter", "" + emeter);
        Log.d("ereading", "" + ereading);
        Log.d("lasthour", "" + lasthour);

        if (pmSpc.equals(r)) {
            if (!dueStatus.equals("")) {
                due.setText("PM is Due. Equipment Reading :" + emeter
                        + ereading);

            } else {
                due.setText("Equipment Reading :" + emeter + ereading);

            }

        }
        if (pmSpc.equals("D")) {
            if (!dueStatus.equals("")) {
                due.setText("PM is Due. Date Last :" + lastday);

            } else {
                due.setText("Date Last :" + lastday);

            }

        }
        if (pmSpc.equals(h)) {

            if (!dueStatus.equals("")) {
                due.setText("PM is Due. Last Hours :" + lasthour);


            } else {
                due.setText("Last Hours :" + lasthour);

            }

        }

        // Hourmeter, FuelLevel,Due,Substatus,Substatusdec,Equpstatus;

        Hourmeter = new TextView(this);
        Hourmeter.setText("Hour meter :");
        Hourmeter.setLayoutParams(editprams);
        Hourmeter.setTextSize(20);
        FuelLevel = new TextView(this);
        FuelLevel.setText("Fuel level :");
        FuelLevel.setLayoutParams(editprams);
        FuelLevel.setTextSize(20);
        Due = new TextView(this);
        Due.setText("Due status :");
        Due.setLayoutParams(editprams);
        Due.setTextSize(20);

        Substatus = new TextView(this);
        Substatus.setText("Sub status :");
        Substatus.setLayoutParams(editprams);
        Substatus.setTextSize(20);
        Substatusdec = new TextView(this);
        Substatusdec.setText("Description :");
        Substatusdec.setLayoutParams(editprams);
        Substatusdec.setTextSize(20);
        Equpstatus = new TextView(this);
        Equpstatus.setText("Current status :");
        Equpstatus.setLayoutParams(editprams);
        Equpstatus.setTextSize(20);
        List<String> list = new ArrayList<String>();
        list.add("Full");
        list.add("7/8");
        list.add("3/4");
        list.add("5/8");
        list.add("1/2");
        list.add("3/8");
        list.add("1/4");
        list.add("1/8");
        list.add("Empty");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Fuellevel.setAdapter(dataAdapter);
        Fuellevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                fuel = arg0.getItemAtPosition(arg2).toString();
                SessionData.getInstance().setFuel(fuel);
                Log.d("Fuellevel", "" + fuel);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Log.d("data for spinner", "" + str);

        ArrayList<String> aList = new ArrayList<String>(Arrays.asList(str
                .split(",")));
        aStatus = new ArrayList<String>(Arrays.asList(str
                .split(",")));
        ArrayList<String> aDesc = new ArrayList<String>(Arrays.asList(str
                .split(",")));

        int dataret = aList.size();

        String status = null;
        String desc = null;
        int m;

        for (m = 0; m < dataret; m++) {
            String str = aList.get(m);
            String[] str1 = str.split("~");
            if (str1.length == 2) {

                status = str1[0];
                desc = str1[1];
                aStatus.set(m, status);
                aDesc.set(m, desc);

            } else {

                status = str1[0];
                aStatus.set(m, status);
                aDesc.set(m, "");

            }
        }

        ArrayAdapter<String> dataAdapters = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aStatus);
        dataAdapters
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        substatus.setAdapter(dataAdapters);
        dataAdapters.notifyDataSetChanged();

        substatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg,
                                       long arg3) {
                sub = substatus.getSelectedItemPosition();

                equpst = arg0.getItemAtPosition(arg).toString();
                if(equpst.contains("Select"))
                {
                    equpst ="";
                }
                else
                {
                    equpst = arg0.getItemAtPosition(arg).toString();
                }
                Log.d("Eqpsubstatus number", "" + sub);

                new Asyncequpmentdec().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
//
//        //

        ArrayAdapter<String> dataAdapterss = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, aDesc);

        dataAdapterss
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        substatushide.setAdapter(dataAdapterss);

        substatushide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg,
                                       long arg3) {

                substatusdesc = arg0.getItemAtPosition(arg).toString();
                Log.d("desss", "" + substatusdesc);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Log.d("text for values", ""
                + SessionData.getInstance().getEqupsubstatusdes());

        linearMain.addView(Hourmeter);
        linearMain.addView(hourMeter);
        linearMain.addView(FuelLevel);
        linearMain.addView(Fuellevel);
        linearMain.addView(Due);
        linearMain.addView(due);
//
        substatushide.setVisibility(View.GONE);

        if (i >= 0 && i < (checkList.size() - 1)) {
            if (i == 0) {
                // Hourmeter, FuelLevel,Due,Substatus,Substatusdec,Equpstatus;
                hourMeter.setVisibility(View.VISIBLE);
                Fuellevel.setVisibility(View.VISIBLE);
                due.setVisibility(View.VISIBLE);
////				eqpStatus.setVisibility(View.GONE);
////				substatus.setVisibility(View.GONE);
////				txtsubStatus.setVisibility(View.GONE);
                Hourmeter.setVisibility(View.VISIBLE);
                FuelLevel.setVisibility(View.VISIBLE);
                Due.setVisibility(View.VISIBLE);
//				Substatus.setVisibility(View.GONE);
//				Substatusdec.setVisibility(View.GONE);
//				Equpstatus.setVisibility(View.GONE);

            } else {
                hourMeter.setVisibility(View.GONE);
                Fuellevel.setVisibility(View.GONE);
                due.setVisibility(View.GONE);
////				eqpStatus.setVisibility(View.GONE);
////				substatus.setVisibility(View.GONE);
////				txtsubStatus.setVisibility(View.GONE);
                Hourmeter.setVisibility(View.GONE);
                FuelLevel.setVisibility(View.GONE);
                Due.setVisibility(View.GONE);
//				Substatus.setVisibility(View.GONE);
//				Substatusdec.setVisibility(View.GONE);
//				Equpstatus.setVisibility(View.GONE);

            }
        }
//        if ((checkList.size() == 1)) {
//            nextBtn.setVisibility(View.GONE);
//            backBtn.setVisibility(View.GONE);
//        }
//        if (i >= 0 && i < (checkList.size() - 1)) {
//            if (i == 0) {
//                nextBtn.setVisibility(View.VISIBLE);
//                backBtn.setVisibility(View.GONE);
//                submitBtn.setVisibility(View.GONE);
//            } else {
//                backBtn.setVisibility(View.VISIBLE);
////                vignesh changes
//                backBtn.setVisibility(View.GONE);
//                submitBtn.setVisibility(View.GONE);
////
//            }
//
//        }
        linearMain.addView(nextPage(partsList));

        linearMain.addView(Equpstatus);
        linearMain.addView(eqpStatus);
        linearMain.addView(Substatus);
        linearMain.addView(substatus);
        linearMain.addView(substatushide);
        linearMain.addView(Substatusdec);
        linearMain.addView(txtsubStatus);

        if(i==(checkList.size()-1)){
            eqpStatus.setVisibility(View.VISIBLE);
            substatus.setVisibility(View.VISIBLE);
            txtsubStatus.setVisibility(View.VISIBLE);

            Substatus.setVisibility(View.VISIBLE);
            Substatusdec.setVisibility(View.VISIBLE);
            Equpstatus.setVisibility(View.VISIBLE);
        }
        else {
            eqpStatus.setVisibility(View.GONE);
            substatus.setVisibility(View.GONE);
            txtsubStatus.setVisibility(View.GONE);

            Substatus.setVisibility(View.GONE);
            Substatusdec.setVisibility(View.GONE);
            Equpstatus.setVisibility(View.GONE);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String hourMetername = hourMeter.getText().toString();

                String subStatus = equpst;

                if (hourMetername.equals("") ) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter The Hour Meter", Toast.LENGTH_LONG);
                    toast.show();

                } else if(previoushours>Integer.parseInt(hourMetername)){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Hours should be greater or equal to previous hours", Toast.LENGTH_LONG);
                    toast.show();
                }
                    else {
                    if (i >= 0 && i < (checkList.size() - 1)) {
                        linearMain.removeAllViews();
                        i++;
                        ArrayList<EquipmentCheckParts> parts = checkList.get(i)
                                .getChecklist();
                        linearMain.addView(nextPage(parts));
                        linearMain.addView(Equpstatus);
                        linearMain.addView(eqpStatus);
                        linearMain.addView(Substatus);
                        linearMain.addView(substatus);
                        linearMain.addView(substatushide);
                        linearMain.addView(Substatusdec);
                        linearMain.addView(txtsubStatus);
                        backBtn.setVisibility(View.VISIBLE);

                        if (i == (checkList.size() - 1)) {
                            nextBtn.setVisibility(View.GONE);
                            submitBtn.setVisibility(View.VISIBLE);
                        } else {
                            submitBtn.setVisibility(View.GONE);
                            nextBtn.setVisibility(View.VISIBLE);

                        }

                   }
                }

                if(i==(checkList.size()-1)){
                    eqpStatus.setVisibility(View.VISIBLE);
                    substatus.setVisibility(View.VISIBLE);
                    txtsubStatus.setVisibility(View.VISIBLE);

                    Substatus.setVisibility(View.VISIBLE);
                    Substatusdec.setVisibility(View.VISIBLE);
                    Equpstatus.setVisibility(View.VISIBLE);
                }
                else {
                    eqpStatus.setVisibility(View.GONE);
                    substatus.setVisibility(View.GONE);
                    txtsubStatus.setVisibility(View.GONE);

                    Substatus.setVisibility(View.GONE);
                    Substatusdec.setVisibility(View.GONE);
                    Equpstatus.setVisibility(View.GONE);
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (i < (checkList.size())) {
                    linearMain.removeAllViews();
                    i--;
                    ArrayList<EquipmentCheckParts> parts = checkList.get(i)
                            .getChecklist();
                    if (i == 0) {
                        hourMeter.setVisibility(View.VISIBLE);
                        Fuellevel.setVisibility(View.VISIBLE);
                        due.setVisibility(View.VISIBLE);
						eqpStatus.setVisibility(View.GONE);
						substatus.setVisibility(View.GONE);
						txtsubStatus.setVisibility(View.GONE);
                        Hourmeter.setVisibility(View.VISIBLE);
                        FuelLevel.setVisibility(View.VISIBLE);
                        Due.setVisibility(View.VISIBLE);
						Substatus.setVisibility(View.GONE);
						Substatusdec.setVisibility(View.GONE);
						Equpstatus.setVisibility(View.GONE);

                        linearMain.addView(Hourmeter);
                        linearMain.addView(hourMeter);
                        linearMain.addView(FuelLevel);
                        linearMain.addView(Fuellevel);
                        linearMain.addView(Due);
                        linearMain.addView(due);


//						linearMain.addView(Equpstatus);
//						linearMain.addView(eqpStatus);
//						linearMain.addView(Substatus);
//						linearMain.addView(substatus);
//						linearMain.addView(Substatusdec);
//						linearMain.addView(txtsubStatus);

                    } else {
                        hourMeter.setVisibility(View.GONE);
                        Fuellevel.setVisibility(View.GONE);
                        due.setVisibility(View.GONE);
////						eqpStatus.setVisibility(View.GONE);
////						substatus.setVisibility(View.GONE);
////						txtsubStatus.setVisibility(View.GONE);
                        Hourmeter.setVisibility(View.GONE);
                        FuelLevel.setVisibility(View.GONE);
                        Due.setVisibility(View.GONE);
//						Substatus.setVisibility(View.GONE);
//						Substatusdec.setVisibility(View.GONE);
//						Equpstatus.setVisibility(View.GONE);

                    }
                    linearMain.addView(nextPage(parts));
                    nextBtn.setVisibility(View.VISIBLE);

                    if (i == 0) {
                        backBtn.setVisibility(View.GONE);
                    } else {
                        backBtn.setVisibility(View.VISIBLE);

                    }
                    if (i == (checkList.size())) {
                        submitBtn.setVisibility(View.VISIBLE);


                    }

                    else {
                        submitBtn.setVisibility(View.GONE);
                    }
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(statusselect.contains("Select Status")){

                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText("Select Status");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });


                    dialog.show();

                }else if(statusselect.toString().length()==0){

                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText("Select Status");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });


                    dialog.show();

                }
                else
               // if(statusselect.contains("AV") || statusselect.contains("MA") || statusselect.contains("RE")|| statusselect.contains("DE"))
                {

                    Log.d("statusemptysubmit",""+statusselect);
                    SessionData.getInstance().setGeneral_hourmeter(hourMeter.getText().toString());
                    SessionData.getInstance().setGeneral_fuelmeter(fuel);
                    SessionData.getInstance().setGeneral_dueStatus(due.getText().toString());
                    SessionData.getInstance().setGeneral_subStatus(equpst);
                    SessionData.getInstance().setGeneral_currentStatus(statusselect);
                    SessionData.getInstance().setGeneral_description(txtsubStatus.getText().toString());

                    Log.d("The Session data Size 1", ""
                            + SessionData.getInstance().getDataHandlelist().size());
                    Logger.log("The Session data Size 1" + ""
                            + SessionData.getInstance().getDataHandlelist().size());

                    String hourMetername = hourMeter.getText().toString();

                    if (hourMetername.equals("")) {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Enter The Hour Meter", Toast.LENGTH_LONG);
                        toast.show();
                    }else if(previoushours>Integer.parseInt(hourMetername)){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Hours should be greater or equal to previous hours", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    else {
                        Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
                                .entrySet();

                        Iterator<?> it = entrySet.iterator();
                        while (it.hasNext()) {
                            Map.Entry me = (Map.Entry) it.next();
                            Log.d("Desc for webservice", ""
                                    + ((CheckinListData) me.getValue()).getNotes());

                        }
                        //	new AysncSubmitData().execute();
//                    hr = Integer.parseInt(hourMeter.getText().toString());
//                    SessionData.getInstance().setDuestatus(due.getText().toString());
//                    SessionData.getInstance().setCurrentstatus(statusselect);
//                    SessionData.getInstance().setSubstatus(equpst);
//                    SessionData.getInstance().setDesc(substatusdesc);
//                    //	SessionData.getInstance().setCheckinequip(user.getUserDescription());
//
//                    //checkin =Integer.parseInt(rentalcheckin.getResult());
//
//                    SessionData.getInstance().setHrmeter(hourMeter.getText().toString());
//                    Log.d("hr", ""+ hr);
                        SessionData.getInstance().setCheckin(checkin);

                        Intent in = new Intent(CustomizedGeneralpartsCheck.this,GeneralSummaryActivity.class);
                        startActivity(in);

                    }

                }
//                else{
//                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.message);
//
//
//                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                    Message.setText("Current status should not be "+statusselect);
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    dialog.show();
//                }
           }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent dataGallery) {
        RESULT_CODE = requestCode;
        if (RESULT_CODE == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                CheckinListData data = SessionData.getInstance()
                        .getDataHandlelist()
                        .get(CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                addImageToSessionData(data);


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (RESULT_CODE == LIBRARY_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    fileUri = Uri.fromFile(new File(getPath(this,
                            dataGallery.getData())));
                    addImageToSessionData(checklistdataentry);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CustomizedGeneralpartsCheck.this,
                            "Exception in choosing file", Toast.LENGTH_LONG)
                            .show();
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to Choose image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == SPEECH_CODE_ONE) {
            if (resultCode == RESULT_OK && dataGallery != null) {
                final ArrayList<String> value = dataGallery.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (speech_textView != null)
                {
                    String s = value.get(0);
                    String text = speech_textView.getText().toString() + " " + s ;
                    speech_textView.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
                    speech_textView.setText(text);
                }




            }

        }


    }

    private static String getPath(Context context, Uri uri)
            throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void addImageToSessionData(CheckinListData data) {
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // already 8 was here
            Bitmap bitmap = BitmapFactory
                    .decodeFile(fileUri.getPath(), options);

            Log.d("The file name  is ",
                    fileUri.getPath().substring(
                            fileUri.getPath().lastIndexOf("/") + 1));
            Logger.log("The file name  is "
                    + fileUri.getPath().substring(
                    fileUri.getPath().lastIndexOf("/") + 1));

            exif = new ExifInterface(fileUri.getPath());

            String orientString = exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION);
            exif.saveAttributes();

            int orientation = orientString != null ? Integer
                    .parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bitmap.getWidth(),
                    (float) bitmap.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap = rotatedBitmap;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);


            //

            setExifMetaData(stream);String Lat = "Latitude : " + SessionData.getInstance().getLatitude();
            String Long = "Longitude : " + SessionData.getInstance().getLongitude();
//
//            String lati = String.valueOf(Lat);
//            String longi = String.valueOf(Long);

            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas newCanvas = new Canvas(mutableBitmap);

            newCanvas.drawBitmap(mutableBitmap, 0.0f, 0.0f, null);



            // newCanvas.drawColor(Color.WHITE);
            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setStyle(Paint.Style.STROKE);
            paintText.setColor(Color.WHITE);
            Path mPath = new Path();

            RectF mRectF = new RectF(0, 50, bitmap.getWidth(), 0);
            mPath.addRect(mRectF, Path.Direction.CCW);
            paintText.setStrokeWidth(50);
            paintText.setStyle(Paint.Style.STROKE);
            //newCanvas.drawPath(mPath, paintText);
            newCanvas.drawRect(mRectF,paintText);

            paintText.setColor(Color.BLACK);

            paintText.setTextSize(20);
            paintText.setStyle(Paint.Style.FILL);
            paintText.setShadowLayer(20f, 20f, 20f, Color.WHITE);

            Rect rectText = new Rect();
            String Workorder = "Equip ID : " + SessionData.getInstance().getWalkAroundEquipmentID();


            paintText.getTextBounds(Workorder, 0, Workorder.length(), rectText);

            newCanvas.drawText(Workorder, 0, rectText.height(), paintText);
            newCanvas.drawText(Lat, 0, 50, paintText);


//            newCanvas.drawText(Lat, bitmap.getWidth() - 113, rectText.height(),
//                    paintText);

            newCanvas.drawText(Long, bitmap.getWidth() - 250, 50, paintText);

            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytearrays = stream.toByteArray();

            setExifMetaData(stream);
            byte[] b = stream.toByteArray();

//            Bitmap image = bitmapConverter(setExifMetaData(stream));
//            image = Bitmap.createScaledBitmap(image, 1080, 1920, true);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

            data.setImagePath(imageEncoded);
            data.setImageName(fileUri.getPath().substring(
                    fileUri.getPath().lastIndexOf("/") + 1));

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
        }
    }

    private String setExifMetaData(ByteArrayOutputStream stream)
            throws IOException {
        String newFile = fileUri.getPath() + "_cmp.jpg";
        FileOutputStream fout = new FileOutputStream(newFile);
        fout.write(stream.toByteArray());
        fout.flush();
        fout.close();

        FileInputStream inStream = new FileInputStream(newFile);
        byte[] buffer = new byte[inStream.available()];
        inStream.read(buffer);
        inStream.close();
        return newFile;
    }

    private View nextPage(ArrayList<EquipmentCheckParts> partsList) {

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(llp);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinkedHashMap<String, Integer> alphabet = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < partsList.size(); i++) {

            EquipmentCheckParts parts = partsList.get(i);
            alphabet.put(parts.getPartsLabel(), parts.getPartType());
            if (parts.getPartType() == 2) {
                if (SessionData.getInstance().getDataHandlelist()
                        .get(parts.getLineItemID()) == null) {
                    checklistdataentry = new CheckinListData();

                } else {
                    checklistdataentry = SessionData.getInstance()
                            .getDataHandlelist().get(parts.getLineItemID());

                }

                checklistdataentry.setLineitemId(parts.getLineItemID());
                layout.addView(addRow(2, parts, checklistdataentry));

            }

            else if (parts.getPartType() == 1) {

                if (SessionData.getInstance().getDataHandlelist()
                        .get(parts.getLineItemID()) == null) {
                    checklistdataentry = new CheckinListData();
                } else {
                    checklistdataentry = SessionData.getInstance()
                            .getDataHandlelist().get(parts.getLineItemID());
                }

                checklistdataentry.setLineitemId(parts.getLineItemID());
                layout.addView(addRow(1, parts, checklistdataentry));

            } else if (parts.getPartType() == 3) {

                if (SessionData.getInstance().getDataHandlelist()
                        .get(parts.getLineItemID()) == null) {
                    checklistdataentry = new CheckinListData();
                } else {
                    checklistdataentry = SessionData.getInstance()
                            .getDataHandlelist().get(parts.getLineItemID());
                }

                checklistdataentry.setLineitemId(parts.getLineItemID());


                layout.addView(addRow(3, parts, checklistdataentry));
                //check1 = SessionData.getInstance().getDataHandlelist().get(parts.getPartsLabel());
                //cc = parts.getPartsLabel().toString();
                //Log.d("cc", ""+ cc);
                //SessionData.getInstance().setCheckinequip(parts.getPartsLabel());


            }

        }
        return layout;

    }

    private TableLayout addRow(int controlType, EquipmentCheckParts parts,
                               final CheckinListData data) {
        TableLayout tl = new TableLayout(this);
        tl.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TableLayout.LayoutParams tparams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        tparams.setMargins(10, 10, 10, 10);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableRow editt = new TableRow(this);
        editt.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        View custom_view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_to_speech, null);

        final EditText addEdit = (EditText) custom_view.findViewById(R.id.speech_edit_text);
        ImageView img_note = (ImageView) custom_view.findViewById(R.id.img_note);

        img_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//				speech_interface.setText(addEdit);
                speech_textView = (EditText) addEdit;

                SessionData.getInstance().setHint_toSpech(addEdit.getHint().toString());

                Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speech.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.speech_prompt));
//				speech.putExtra("find_view", "text");

                try {
                    startActivityForResult(speech, SPEECH_CODE_ONE);
                } catch (ActivityNotFoundException a) {

                    Toast.makeText(CustomizedGeneralpartsCheck.this, "Sorry! Your device doesn\\'t support speech input " , Toast.LENGTH_SHORT).show();
                }

            }
        });

        TextView textView = new TextView(this);

        ImageView camera = new ImageView(this);

//        EditText addEdit = new EditText(this);

        camera.setBackgroundResource(R.drawable.attachment_blue);
        LinearLayout.LayoutParams cameraparams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        camera.setLayoutParams(cameraparams);
        cameraparams.setMargins(5, 10, 15, 5);

        if (parts.isPartCaptureRequired()) {
            camera.setEnabled(true);
            data.setCameraid(1);
        } else {
            camera.setEnabled(false);
            camera.setVisibility(View.GONE);
            data.setCameraid(0);

            // Toast.makeText(getApplicationContext(),
            // "Is Not Required In this Part", Toast.LENGTH_SHORT)
            // .show();

        }

        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CaptureImage(data);
            }

            private void CaptureImage(CheckinListData data) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                        GradientDrawable.Orientation.LEFT_RIGHT);
                CAMERA_CAPTURE_IMAGE_REQUEST_CODE = data.getLineitemId();
                startActivityForResult(intent,
                        CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }

            public Uri getOutputMediaFileUri(int type) {
                return Uri.fromFile(getOutputMediaFile(type));
            }

            private File getOutputMediaFile(int type) {
                File mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        IMAGE_DIRECTORY_NAME);

                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {

                        return null;
                    }
                }

                String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss",
                        Locale.getDefault()).format(new Date());
                File mediaFile;
                if (type == MEDIA_TYPE_IMAGE) {
                    mediaFile = new File(mediaStorageDir.getPath()
                            + File.separator
                            + SessionData.getInstance().getWalkAroundEquipmentID()
                            + "_"
                             + "_" + timeStamp
                    );

                } else {
                    return null;
                }

                return mediaFile;
            }
        });

        LinearLayout.LayoutParams textparams = new TableRow.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
        textView.setLayoutParams(textparams);
        textView.setText(parts.getPartsLabel());
        mylabel.add(parts.getPartsLabel());
        Log.d("mylabel", ""+mylabel.size());
        SessionData.getInstance().setCheckinequip(mylabel);
        textparams.setMargins(15, 10, 5, 5);

        textView.setTextSize(20);
        textView.setPadding(0, 0, 0, 15);

        LinearLayout.LayoutParams editprams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (150 * dpi), 2f);
        editprams.setMargins(10, 5, 10, 5);
        addEdit.setHint(" Enter notes about " + parts.getPartsLabel());
        Log.d("isisigned", ""+parts.getIssignneed());
        SessionData.getInstance().setIssign(parts.getIssignneed());
        Log.d("label", ""+parts.getPartsLabel());
        addEdit.setBackgroundResource(R.drawable.editext_background);
        custom_view.setLayoutParams(editprams);
        addEdit.setGravity(Gravity.TOP);
        addEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                data.setNotes(s.toString());
            }
        });


        addEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        addEdit.setRawInputType(InputType.TYPE_CLASS_TEXT);
        Log.d("notes", ""+data.getNotes());

        data.setLabels(parts.getPartsLabel());
        if (controlType == 2) {
            LinearLayout.LayoutParams params = new TableRow.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 2f);

            final CheckBox	 typecheckBox = new CheckBox(this);

            typecheckBox.setText(parts.getPartsLabel());
            typecheckBox.setTextSize(20);
            typecheckBox.setLayoutParams(params);
            tr.addView(typecheckBox);
            data.setLineitemId(parts.getLineItemID());

            typecheckBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (typecheckBox.isChecked()) {
                        data.setStatus("1");
                        data.setValues("Yes");
                        Log.d("Selected","" +"Selected");

                        // selectedValues = "Yes";
                    }
                    else if (!(typecheckBox.isChecked())){
                        data.setStatus("0");
                        data.setValues("No");
                        data.setData(0);
                        Log.d("Not Selected", ""+ "Not Selected");
                        // selectedValues = "No";
                    }
                }
            });
//            if (typecheckBox.isSelected()) {
//                data.setStatus("1");
//                data.setValues("Yes");
//                Log.d("Selected","" +"Selected");
//
//                // selectedValues = "Yes";
//            }
//            else{
//                data.setStatus("0");
//                data.setValues("No");
//                data.setData(0);
//                Log.d("Not Selected", ""+ "Not Selected");
//                // selectedValues = "No";
//            }
            if(data.getValues()!=null){

                if(data.getValues().equals("Yes")){
                    typecheckBox.setChecked(true);
                }

            }

            Log.d("Checkbox", "" + data.getStatus());

            SessionData.getInstance().getDataHandlelist()
                    .put(parts.getLineItemID(), data);

        } else {
            tr.addView(textView);
        }

        if (controlType == 1) {
            String string = (parts.getControlsText());

            ArrayList<String> aList = new ArrayList<String>(
                    Arrays.asList(string.split(",")));

            final Spinner spinner = new Spinner(this);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, aList);

            spinner.setAdapter(spinnerArrayAdapter);
            LinearLayout.LayoutParams spinparams = new TableRow.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
            spinner.setLayoutParams(spinparams);
            spinparams.setMargins(5, 5, 5, 5);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                    data.setData(spinner.getSelectedItemPosition());
                    String str = Integer.toString(spinner
                            .getSelectedItemPosition() + 1);
                    Log.d("Spiner values", "" + str);

                    data.setStatus(str);

                    data.setValues(spinner.getSelectedItem().toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

            data.setData(spinner.getSelectedItemPosition());
            data.setNotes(data.getNotes());

            spinner.setSelection(spinnerArrayAdapter.getPosition(data.getValues()));
            data.setImagePath("");
            SessionData.getInstance().getDataHandlelist()
                    .put(parts.getLineItemID(), data);
            tr.addView(spinner);
            data.setLineitemId(parts.getLineItemID());

        }
        if (controlType == 3) {

            EditText edtText = new EditText(this);
            edtText.setSingleLine();
            edtText.setHint("Enter the details");
            LinearLayout.LayoutParams spinparams = new TableRow.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
            edtText.setLayoutParams(spinparams);
            spinparams.setMargins(0, 10, 5, 5);
            data.setData(1);
            data.setLineitemId(parts.getLineItemID());
            SessionData.getInstance().getDataHandlelist()
                    .put(parts.getLineItemID(), data);
            edtText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    data.setStatus(s.toString());
                    data.setValues(s.toString());
//                    selectedValues = (s.toString());
//                    Log.d("sel", ""+selectedValues);
                    //	SessionData.getInstance().setSpinvalues(selectedValues);


                }
            });

            edtText.setText(data.getValues());
          //  Log.d("sel", ""+selectedValues);
            tr.addView(edtText);

        }
        addEdit.setText(data.getNotes());

        tr.addView(camera);
        editt.addView(custom_view);
        tl.addView(tr);
        tl.addView(editt);
        return tl;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        i = 0;
        j = 0;
        checkList.clear();
        SessionData.getInstance().getSelectedDetail().clear();
        SessionData.getInstance().getCheckListData().clear();
        SessionData.getInstance().getDataHandlelist().clear();
        SessionData.getInstance().getKpartlist().clear();
        SessionData.getInstance().getHourmeterlist().clear();
        SessionData.getInstance().getEqpStatus().clear();
        Intent inspection = new Intent(CustomizedGeneralpartsCheck.this, General_WalkAroundInspection.class);
        startActivity(inspection);
        //new AsyncRentalDetail().execute();

    }

//    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {
//
//        String resultdata;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            ProgressBar.showCommonProgressDialog(CustomizedGeneralpartsCheck.this);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//
//                Log.d("The Session data Size", ""
//                        + SessionData.getInstance().getDataHandlelist().size());
//                Logger.log("The Session data Size" + ""
//                        + SessionData.getInstance().getDataHandlelist().size());
//
//                Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
//                        .entrySet();
//
//                Iterator<?> it = entrySet.iterator();
//                Log.d("The size of IT", ""
//                        + SessionData.getInstance().getDataHandlelist()
//                        .entrySet().size());
//                rentalcheckin = WebServiceConsumer.getInstance().RentalCheckin(
//                        SessionData.getInstance().getRentalId(), 0,
//                        SessionData.getInstance().getInspectionID(),
//                        user.getUserDescription());
//                if (SessionData.getInstance().getRentalCheckinResult() == 1) {
//                    dialog = new Dialog(CustomizedGeneralpartsCheck.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.message);
//
//
//                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                    Message.setText(rentalcheckin.getMessage());
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    dialog.show();
//                } else {
//                    getrentalcheck = Integer.parseInt(rentalcheckin.getResult());
////					checkinequpments = WebServiceConsumer
////							.getInstance()
////							.rentalCheckinEqupments(
////									Integer.parseInt(rentalcheckin.getResult()),
////									SessionData.getInstance().getSelectedDetail()
////											.get(j).getkPart(),
////									Integer.parseInt(hourMeter.getText().toString()),
////
////									fuel,
////									Integer.toString(SessionData.getInstance()
////											.getEquipinspectionID()),
////									user.getUserDescription(), equpst,
////									txtsubStatus.getText().toString(), statusselect, "", "", "");
//                 //   Log.d("Equpstatus", "" + statusselect);
//                    Log.d("result data", "" + rentalcheckin.getResult());
//                    // int rentalCheckinDetail;
//                    while (it.hasNext()) {
//                        Map.Entry me = (Map.Entry) it.next();
//
//                        checkindetails = WebServiceConsumer.getInstance()
//                                .rentalCheckinDetl(
//                                        Integer.parseInt(checkinequpments
//                                                .getResult()),
//                                        (int) me.getKey(),
//                                        ((CheckinListData) me.getValue())
//                                                .getStatus(),
//
//                                        ((CheckinListData) me.getValue())
//                                                .getNotes(), 0,
//                                        user.getUserDescription());
//                        Log.d("Description for webservice", ""
//                                + ((CheckinListData) me.getValue()).getNotes());
//
//                        if ((((CheckinListData) me.getValue()).getImagePath()
//                                .length() > 4)) {
//                            resultdata = WebServiceConsumer.getInstance()
//                                    .RentalChickinImages(
//                                            Integer.parseInt(checkindetails
//                                                    .getResult()),
//
//                                            ((CheckinListData) me.getValue())
//                                                    .getImageName(),
//                                            "",
//                                            0,
//                                            ((CheckinListData) me.getValue())
//                                                    .getImagePath(), 0,
//                                            user.getUserDescription(),
//                                            txtTitle.getText().toString());
//
//                        }
//
//                    }
//                    checklistpdf = WebServiceConsumer.getInstance()
//                            .RentalChecklistPdf(
//                                    user.getUserDescription(),
//                                    SessionData.getInstance().getSelectedDetail()
//                                            .get(j).getkPart(),
//                                    SessionData.getInstance().getSelectedDetail()
//                                            .get(j).getEqupId(),
//                                    Integer.toString(SessionData.getInstance()
//                                            .getSelectedDetail().get(j)
//                                            .getRentalID()), "", "", "", 0);
//                }
//            } catch (java.net.SocketTimeoutException e) {
//                resultdata = null;
//
//            } catch (Exception e) {
//                resultdata = null;
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            ProgressBar.dismiss();
//            linearMain.removeAllViews();
//
//            SessionData.getInstance().getDataHandlelist().clear();
//
//            if (j < cheklistArray.size() - 1) {
//                j++;
//                checkList = ReadJson.getPartsList(cheklistArray.get(j));
//                SessionData.getInstance().setInspectionID(getrentalcheck);
//
//                i = 0;
//                //initializeViews(checkList);
//                Log.d("I IS PARSING 1", "" + checkList);
//
//            }
//
//            else {
//
//                // SessionData.getInstance().getDataHandlelist().clear();
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected void onPreExecute() {
//                        ProgressBar
//                                .showCommonProgressDialog(CustomizedGeneralpartsCheck.this);
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
//                        } catch (java.net.SocketTimeoutException e) {
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
//
//                        if(selectedDetail.get(0).getMessage().equals("")) {
//                            selectedDetail.clear();
//
//                        }
//                        else{
//                            dialog = new Dialog(CustomizedGeneralpartsCheck.this);
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
//                                    Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                            MainActivity.class);
//                                    startActivity(inspection);
//                                    dialog.dismiss();
//                                }
//                            });
//
//
//                            dialog.show();
//                        }
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
//                } catch (java.net.SocketTimeoutException e) {
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
//				/* SessionData.getInstance().getGetKey().clear(); */
//
//                Toast.makeText(CustomizedGeneralpartsCheck.this,
//                        "Rental Inpection Over", Toast.LENGTH_LONG).show();
//
//                finish();
//                ProgressBar.dismiss();
//                new AsyncRentalDetail().execute();
//
//            }
//
//        }
//
//    }
//
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
//                        .getRentalDetail(txtTitle.getText().toString(),
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
//            if(selectedDetail.get(0).getMessage().equals("")) {
//
//                SessionData.getInstance().getSelectedDetail().clear();
//                SessionData.getInstance().getCheckListData().clear();
//                SessionData.getInstance().getDataHandlelist().clear();
//                SessionData.getInstance().getKpartlist().clear();
//                SessionData.getInstance().getHourmeterlist().clear();
//                SessionData.getInstance().getEqpStatus().clear();
//                SessionData.getInstance().setDetail(selectedDetail);
//
//                SessionData.getInstance().setEnteredEquipID(
//                        txtTitle.getText().toString());
//                Intent intent = new Intent(CustomizedGeneralpartsCheck.this,
//                        RentalListSelector.class);
//                startActivity(intent);
//                finish();
//            }
//            else{
//                dialog = new Dialog(CustomizedGeneralpartsCheck.this);
//                dialog.setCanceledOnTouchOutside(true);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.message);
//
//
//                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                Message.setText(selectedDetail.get(0).getMessage());
//
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent inspection = new Intent(CustomizedGeneralpartsCheck.this,
//                                MainActivity.class);
//                        startActivity(inspection);
//                        dialog.dismiss();
//                    }
//                });
//
//
//                dialog.show();
//            }
//
//            // }
//        }
//    }

    private static Bitmap bitmapConverter(String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // already 8 was here
        File fileUri = new File(filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(fileUri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString)
                : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            rotationAngle = 270;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bitmap.getWidth(),
                (float) bitmap.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap = rotatedBitmap;
        return bitmap;

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
