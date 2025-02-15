package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.AvailableParking;
import com.ebs.rental.objects.CategoryObject;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.RentalListSelectorObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by techunity on 5/10/16.
 */
@SuppressWarnings("ALL")
public class RecieveWalkAround extends AppCompatActivity implements View.OnClickListener {

    private static final int SPEECH_CODE_ONE = 100;
    private Button next;
    private ImageView imgback;
    private TextView txtback;
    private Button Camera;
    private double latitude;
    private double longitude;
    private GPSTracker gps;

    private Gallery gallery;
    private Dialog Image_dialog;

    private User user;
    ArrayList<AvailableParking> parkingList = new ArrayList<>();

    private int session = 0;
    User objUser = null;

    private GeneralEquipmentSearchObject generalEquipment;
    private RentalListSelectorObject rentalCheckinList;

    private ArrayList<byte[]> attachedFilesData;
    private ArrayList<byte[]> AddtoSession = new ArrayList<>();

    private ArrayList<String> arrayNotes = new ArrayList<>();
    private ArrayList<String> arrayType = new ArrayList<>();
    private TextView CurrentBranch;


    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;


    private Button btnSave;
    private Button btnDelete;
    private Spinner SelectType;
    private EditText edt_WalkAroundnotes;
    private EditText notes;
    private static Dialog dialog;
    private ArrayList<CategoryObject> categoryObjects = new ArrayList<>();

    private ImageView close_popup;
    private ImageView image_show;
    private ImageView mic;

    private TextView make;
    private TextView model;
    private TextView serialNo;
    private TextView UnitID;
    private TextView TransferedBranch;
    private String strlogout;

    private TextView section;
    private TextView spot;
    private Button available_parking;
    private ListView availableList;
    AvailableAdapter availableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.receive_walkin);
        next = (Button) findViewById(R.id.btn_next);

        Camera = (Button) findViewById(R.id.btn_capture);
        imgback = (ImageView) findViewById(R.id.close);
        txtback = (TextView) findViewById(R.id.back);
        mic = (ImageView) findViewById(R.id.mic);

        user = SessionData.getInstance().getUser();

        new AsyncType().execute();

        edt_WalkAroundnotes = (EditText) findViewById(R.id.walk_around_notes);

        edt_WalkAroundnotes.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_WalkAroundnotes.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edt_WalkAroundnotes.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_WalkAroundnotes.setText(SessionData.getInstance().getWalkaroundNotes());

        make = (TextView) findViewById(R.id.make);
        model = (TextView) findViewById(R.id.model);
        serialNo = (TextView) findViewById(R.id.serialno);
        UnitID = (TextView) findViewById(R.id.unit_id);

        section = (TextView) findViewById(R.id.txt_section);
        spot = (TextView) findViewById(R.id.txt_spot);
        available_parking = (Button) findViewById(R.id.btn_available_parking);
        available_parking.setOnClickListener(this);

        TransferedBranch = (TextView) findViewById(R.id.transfered_branch);


        ReceiveEquipmentSearchObject = SessionData.getInstance().getReceiveEquipmentSearchObject();
        make.setText(ReceiveEquipmentSearchObject.getMfg());
        model.setText(ReceiveEquipmentSearchObject.getModel());
        serialNo.setText(ReceiveEquipmentSearchObject.getSerialno());
        TransferedBranch.setText(ReceiveEquipmentSearchObject.getFrombranch());

        CurrentBranch = (TextView) findViewById(R.id.current_branch_receive);
        CurrentBranch.setText(ReceiveEquipmentSearchObject.getTobranch());
        UnitID.setText(ReceiveEquipmentSearchObject.getEquipmentid());

        gallery = (Gallery) findViewById(R.id.Image_gallery);

        AddtoSession = SessionData.getInstance().getWalkaroundgeneralimages();
        arrayNotes = SessionData.getInstance().getWalkAroundNotes();
        arrayType = SessionData.getInstance().getWalkAroundType();

        attachedFilesData = new ArrayList<>();

        attachedFilesData = SessionData.getInstance().getWalkaroundgeneralimages();

        gallery.setAdapter(new ImageAdapter(RecieveWalkAround.this));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                Image_dialog = new Dialog(RecieveWalkAround.this);
                Image_dialog.setCanceledOnTouchOutside(true);
                Image_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Image_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //  Image_dialog.getWindow().setFlags(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                Image_dialog.setContentView(R.layout.image_view_popup);

                Window window = Image_dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                Image_dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                image_show = (ImageView) Image_dialog.findViewById(R.id.image_show);
                close_popup = (ImageView) Image_dialog.findViewById(R.id.close_btn);
                btnSave = (Button) Image_dialog.findViewById(R.id.btn_save);
                btnDelete = (Button) Image_dialog.findViewById(R.id.btn_delete);
                SelectType = (Spinner) Image_dialog.findViewById(R.id.select_type);
                notes = (EditText) Image_dialog.findViewById(R.id.notes);
                Button btnContinue = (Button) Image_dialog.findViewById(R.id.btn_continue);
                btnContinue.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                notes.setText(SessionData.getInstance().getWalkAroundNotes().get(position));


                List<String> list = new ArrayList<String>();
                final List<String> categoryId = new ArrayList<String>();
                for (int i = 0; i < categoryObjects.size(); i++) {

                    list.add(categoryObjects.get(i).getCategory());
                    categoryId.add(categoryObjects.get(i).getId());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RecieveWalkAround.this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SelectType.setAdapter(dataAdapter);

                SelectType.setSelection(dataAdapter.getPosition(SessionData.getInstance().getWalkAroundType().get(position)));


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (SelectType.getSelectedItemPosition() == 0) {

                            Toast.makeText(RecieveWalkAround.this, "Select type", Toast.LENGTH_LONG).show();

                        } else if (notes.getText().toString().trim().length() == 0) {
                            Toast.makeText(RecieveWalkAround.this, "Enter Notes", Toast.LENGTH_LONG).show();
                        } else {
                            String str;
                            str = notes.getText().toString();
                            String type = SelectType.getSelectedItem().toString();

                            Log.d("Selected Item", "" + SelectType.getSelectedItem().toString());
                            arrayType.remove(position);
                            arrayNotes.remove(position);

                            arrayType.add(type);
                            arrayNotes.add(str);

                            SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                            SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                            SessionData.getInstance().setWalkAroundType(arrayType);

                            Image_dialog.dismiss();
                        }


//
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AddtoSession.remove(attachedFilesData.get(position));
                        SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);

                        attachedFilesData.remove(position);

                        gallery.setAdapter(new ImageAdapter(RecieveWalkAround.this));

                        Image_dialog.dismiss();

                    }
                });
                close_popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Image_dialog.dismiss();
                    }
                });
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;

                byte[] data = attachedFilesData.get(position);
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                        data.length, options);

                image_show.setImageBitmap(bmp);

                Image_dialog.show();

            }
        });


        next.setOnClickListener(this);
        imgback.setOnClickListener(this);
        txtback.setOnClickListener(this);
        Camera.setOnClickListener(this);
        mic.setOnClickListener(this);
    }


    private class AsyncType extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RecieveWalkAround.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                categoryObjects = WebServiceConsumer.getInstance()
                        .getCategory(SessionData.getInstance().getTemp_userToken());
            } catch (java.net.SocketTimeoutException e) {
                categoryObjects = null;
            } catch (Exception e) {
                categoryObjects = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            ProgressBar.dismiss();
//            Log.d("categoryObjects Size","" + categoryObjects.size());

            if (categoryObjects != null && !(categoryObjects.equals(""))) {

                if (categoryObjects.get(0).getMessage().equals("")) {


                    SessionData.getInstance().setCategoryObjects(categoryObjects);
                    // SessionData.getInstance().setDealer(dealer);

//					if(!(dealer.get(0).getMessage().equals(""))){
//						ProgressBar.dismiss();
//						AlertDialogBox.showAlertDialog(EbsMenu.this,
//								dealer.get(0).getMessage());
//					}
//					else{

//					}
                } else {
                    if (categoryObjects.get(0).getMessage().contains("Session")) {
                        session = 1;
                        new AsyncSessionLoginTask_Receive().execute();
                    } else {
                        dialog = new Dialog(RecieveWalkAround.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(categoryObjects.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent inspection = new Intent(RecieveWalkAround.this,
                                        MainActivity.class);
                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }


            }


        }
    }


    private class AsyncSessionLoginTask_Receive extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RecieveWalkAround.this);
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

                if (user.getUserId() == 0) {
                    dialog = new Dialog(RecieveWalkAround.this);
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

                            Intent inspection = new Intent(RecieveWalkAround.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    ProgressBar.dismiss();
                    SessionData.getInstance().setUser(user);

                    if (session == 1) {
                        new AsyncType().execute();
                    } else if (session == 0) {
                        new AsyncRentalDetail().execute();
                    }


                }
            } else {

                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RecieveWalkAround.this,
                        "Data is not found");

            }

        }
    }


    @Override
    protected void onResume() {
        attachedFilesData = SessionData.getInstance().getWalkaroundgeneralimages();

        gallery.setAdapter(new ImageAdapter(RecieveWalkAround.this));
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        if (next == v) {

            if (TransferedBranch.getText().toString().length() == 0 || CurrentBranch.getText().toString().length() == 0) {
                dialog = new Dialog(RecieveWalkAround.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                Message.setText("There is no Transfer for this Equipment");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


                dialog.show();
            } else {
                SessionData.getInstance().setWalkaroundNotes(edt_WalkAroundnotes.getText().toString());
                new AsyncRentalDetail().execute();
            }


        } else if (available_parking == v) {
            new GetAvailableParkingSpots().execute();
        } else if (imgback == v) {
            onBackPressed();

//            if(SessionData.getInstance().getTransportTransfer()==1){
//                SessionData.getInstance().setTransportTransfer(0);
//                Intent inspection = new Intent(RecieveWalkAround.this, TransportListDetails.class);
//                startActivity(inspection);
//            }else{
//                if(SessionData.getInstance().getScanNavigation()==0){
//                    Intent inspection = new Intent(RecieveWalkAround.this, ScannerActivity.class);
//                    startActivity(inspection);
//                }else{
//                    Intent intent = new Intent(RecieveWalkAround.this,
//                            ScannerProductActivity.class);
//
//                    startActivity(intent);
//                }
//            }
        } else if (txtback == v) {
            onBackPressed();

//            if(SessionData.getInstance().getTransportTransfer()==1){
//                SessionData.getInstance().setTransportTransfer(0);
//                Intent inspection = new Intent(RecieveWalkAround.this, TransportListDetails.class);
//                startActivity(inspection);
//            }else{
//                if(SessionData.getInstance().getScanNavigation()==0){
//                    Intent inspection = new Intent(RecieveWalkAround.this, ScannerActivity.class);
//                    startActivity(inspection);
//                }else{
//                    Intent intent = new Intent(RecieveWalkAround.this,
//                            ScannerProductActivity.class);
//
//                    startActivity(intent);
//                }
//            }

        } else if (mic == v) {
            Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            speech.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.speech_prompt));

            try {
                startActivityForResult(speech, SPEECH_CODE_ONE);
            } catch (ActivityNotFoundException a) {

                Toast.makeText(RecieveWalkAround.this, "Sorry! Your device doesn\\'t support speech input ", Toast.LENGTH_SHORT).show();
            }
        } else if (Camera == v) {
            if (TransferedBranch.getText().toString().length() == 0 || CurrentBranch.getText().toString().length() == 0) {
                dialog = new Dialog(RecieveWalkAround.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                Message.setText("There is no Transfer for this Equipment");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


                dialog.show();
            } else {
                getGps();
                SessionData.getInstance().setLatitude(latitude);
                SessionData.getInstance().setLongitude(longitude);
                Intent inspection = new Intent(RecieveWalkAround.this, WalkAroundReceive.class);
                startActivity(inspection);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SPEECH_CODE_ONE) {
            if (resultCode == RESULT_OK && data != null) {
                String str = edt_WalkAroundnotes.getText().toString();
                ArrayList<String> value = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edt_WalkAroundnotes.setText(str + " " + value.get(0));

            }

        }
    }


    public class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        LinkedHashMap<GeneralEquipmentSearchObject, String> data;

        protected void onPreExecute() {
            data = new LinkedHashMap<>();
            ProgressBar.showCommonProgressDialog(RecieveWalkAround.this);
        }

        ;

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... params) {

            try {
//                for (int i = 0; i < SessionData.getInstance()
//                        .getSelectedDetail().size(); i++) {
//
//                    RentalDetails rentDetail = SessionData.getInstance()
//                            .getSelectedDetail().get(0);

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                rentalCheckinList = WebServiceConsumer.getInstance()
                        .RentalCheckinList("", ReceiveEquipmentSearchObject.getEquipmentid(),
                                0,
                                0,
                                user.getUserDescription());

                if (SessionData.getInstance().getRentalListSelector() == 0) {
                    Log.d("rental list selector result", "" + rentalCheckinList.getResult());

                    data.put(generalEquipment, rentalCheckinList.getResult());


                    SessionData.getInstance().getKpartlist()
                            .add(model.getText().toString());
                    SessionData.getInstance().getEqpStatus()
                            .add("");
                    SessionData.getInstance().getHourmeterlist()
                            .add("");
                    SessionData.getInstance().getPmspeclist()
                            .add("");
                    SessionData.getInstance().getDuestatuslist()
                            .add("");
                    SessionData.getInstance().getEqupmentmeterlist()
                            .add("");
                    SessionData.getInstance().getEqupmentreadinglist()
                            .add(String.valueOf(""));
                    SessionData.getInstance().getLastdatelist()
                            .add("");
                    SessionData.getInstance().getLasthourlist()
                            .add("");
                }


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
                    dialog = new Dialog(RecieveWalkAround.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText("There is no Checklist defined");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            new RecieveWalkAround.AsyncLogoutTask().execute();
//                            Intent inspection = new Intent(RecieveWalkAround.this,
//                                    ScannerProductActivity.class);
//                            startActivity(inspection);
                            if (SessionData.getInstance().getTransportTransfer() == 1) {
                                SessionData.getInstance().setTransportTransfer(0);
                                Intent inspection = new Intent(RecieveWalkAround.this, TransportListDetails.class);
                                startActivity(inspection);
                            } else {
                                if (SessionData.getInstance().getRefer().contains("False")) {


//                                    Intent inspection = new Intent(RecieveWalkAround.this, ScannerProductActivity.class);
//                                    startActivity(inspection);
                                    Intent inspection = new Intent(RecieveWalkAround.this, EbsMenu.class);
                                    startActivity(inspection);

                                } else {
//                                    Intent inspection = new Intent(RecieveWalkAround.this, ScannerActivity.class);
//                                    startActivity(inspection);
                                    Intent inspection = new Intent(RecieveWalkAround.this, EbsMenu.class);
                                    startActivity(inspection);

                                }
                            }


                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setChecklist(20);
                    SessionData.getInstance().setMulticheckinvalidate(1);

                    SessionData.getInstance().getGeneralcheckListData().putAll(data);

                    Intent inspection = new Intent(RecieveWalkAround.this, ReceiveCheckIn.class);
                    startActivity(inspection);

                    // startActivity(intent);
                    finish();
                }
            } else {
                if (rentalCheckinList.getMessage().contains("Session")) {
                    session = 0;
                    new AsyncSessionLoginTask_Receive().execute();
                } else {


                    dialog = new Dialog(RecieveWalkAround.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    Message.setText(rentalCheckinList.getMessage());

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent inspection = new Intent(RecieveWalkAround.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }

            }

        }

    }

    public class ImageAdapter extends BaseAdapter {
        private final Context context;
        private final int itemBackground;
        final LayoutInflater inflator;

        public ImageAdapter(Context c) {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();

            inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // returns the number of images
        public int getCount() {
            return attachedFilesData.size();
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }


        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView = new ImageView(context);
            @SuppressLint({"InflateParams", "ViewHolder"}) final View view = (View) inflator.inflate(R.layout.sub_image_view, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_camera);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            byte[] data = attachedFilesData.get(position);
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                    data.length, options);

            imageView.setImageBitmap(bmp);


            imageView.setBackgroundResource(itemBackground);
            return view;

        }
    }

    private void getGps() {
        gps = new GPSTracker(RecieveWalkAround.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onBackPressed() {
//
//        if(SessionData.getInstance().getTransportTransfer()==1){
//            SessionData.getInstance().setTransportTransfer(0);
//            Intent inspection = new Intent(RecieveWalkAround.this, TransportListDetails.class);
//            startActivity(inspection);
//        }else{
//            if(SessionData.getInstance().getScanNavigation()==0){
//                Intent inspection = new Intent(RecieveWalkAround.this, ScannerActivity.class);
//                startActivity(inspection);
//            }else{
//                Intent intent = new Intent(RecieveWalkAround.this,
//                        ScannerProductActivity.class);
//
//                startActivity(intent);
//            }
//        }


        Intent intent = new Intent(RecieveWalkAround.this,
                EbsMenu.class);

        startActivity(intent);


        super.onBackPressed();

    }

    private class AsyncLogoutTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RecieveWalkAround.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

//				user = WebServiceConsumer.getInstance()
//						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//								SessionData.getInstance().getLogin_password());

                strlogout = WebServiceConsumer.getInstance().RemoveActiveUser(
                        user.getUserDescription(),
                        user.getUserId());

            } catch (Exception e) {
                strlogout = null;

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (strlogout != null) {
                Intent intent = new Intent(RecieveWalkAround.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public class AvailableAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;
        private ArrayList<AvailableParking> mparkingList;

        public AvailableAdapter(Context mContext, ArrayList<AvailableParking> parkingList) {
            this.mContext = mContext;
            this.mparkingList = parkingList;
        }

        @Override
        public int getCount() {
            return mparkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return mparkingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView txtSection;
            TextView txtSpot;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
//                holder = new Equip_WalkAround().AvailableAdapter.ViewHolder();

//                holder=new ViewHolder();
//                holder= new ViewHolder();

                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.child_available_list, null);
                holder.txtSection = (TextView) convertView.findViewById(R.id.txt_avail_section);
                holder.txtSpot = (TextView) convertView.findViewById(R.id.txt_avail_spot);
                convertView.setFocusable(false);
                convertView.setTag(holder);

            } else {
//                holder = (ViewHolder) convertView.getTag();
//                holder=(ViewHolder) convertView.getTag();
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtSection.setText(mparkingList.get(position).getSection());
            holder.txtSpot.setText(mparkingList.get(position).getSpot());
            return convertView;

        }
    }

    private class GetAvailableParkingSpots extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RecieveWalkAround.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                parkingList.clear();


                parkingList = WebServiceConsumer.getInstance().GetAvailableParkingSpots(

                        user.getUserDescription(), user.getBranch());

            } catch (java.net.SocketTimeoutException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();

            if (parkingList.get(0).getMessage().contains("Session")) {
                session = 2;
                new AsyncSessionLoginTask_Receive().execute();

            } else {
                if (parkingList != null && (parkingList.size() > 0)) {
                    final Dialog dialog = new Dialog(RecieveWalkAround.this);
                    dialog.setCancelable(false);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_available_parking);
                    TextView Header_title = (TextView) dialog.findViewById(R.id.header_title);
                    ImageView imgview = (ImageView) dialog.findViewById(R.id.close_img);
                    availableList = (ListView) dialog.findViewById(R.id.available_list);
                    availableAdapter = new AvailableAdapter(RecieveWalkAround.this, parkingList);
                    availableList.setAdapter(availableAdapter);
                    availableList.setTextFilterEnabled(true);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else {

                    AlertDialogBox.showAlertDialog(RecieveWalkAround.this,
                            "Data is not found.");

                }
            }
        }
    }
}
