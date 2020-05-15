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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.AvailableParking;
import com.ebs.rental.objects.CategoryObject;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.User;
import com.ebs.rental.uidesigns.MaterialRippleLayout;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ALL")
public class TransportWalkAroundInspection extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private ImageView imgback;
    private TextView txtback;
    private Button Camera;
    private User user;
    private double latitude;
    private double longitude;
    private GPSTracker gps;

    private int session = 0;
    private String category;
    private static Dialog dialog;

    private Gallery gallery;
    private Dialog Image_dialog;

    private ArrayList<RentalDetails> detail;

    private ArrayList<byte[]> attachedFilesData;
    private ArrayList<byte[]> AddtoSession = new ArrayList<>();

    private ArrayList<String> arrayNotes = new ArrayList<>();
    private ArrayList<String> arrayType = new ArrayList<>();
    private ArrayList<String> CategoryID = new ArrayList<>();

    User objUser = null;

    private ArrayList<CategoryObject> categoryObjects = new ArrayList<>();
    private Button btnSave;
    private Button btnDelete;
    private Spinner SelectType;
    private EditText notes;

    private EditText edt_WalkAroundnotes;
    TextView txtEquipId;
    public MaterialRippleLayout rippleLayout;
    private ImageView img_note;

    private ImageView close_popup;
    private ImageView image_show;

    private int SPEECH_CODE_ONE = 101;

    private TextView section;
    private TextView spot;
    private Button btn_availableParking;
    private ListView availableList;
    //    CustomAdapter customAdapter;
    private LinearLayout linearbottom;
    AvailableAdapter availableAdapter;
    ArrayList<AvailableParking> parkingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rentalinspection_walkin);

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
//            String[] perms = {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"
//                    ,"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION"};
//            int permsRequestCode = 200;
//            requestPermissions(perms, permsRequestCode);
//        }

        next = (Button) findViewById(R.id.btn_next);

        edt_WalkAroundnotes = (EditText) findViewById(R.id.walk_around_notes);
        //SessionData.getInstance().setWalkAroundEquipmentID("");
        Camera = (Button) findViewById(R.id.btn_capture);
        imgback = (ImageView) findViewById(R.id.rental_close);
        txtback = (TextView) findViewById(R.id.rental_back);
        rippleLayout = findViewById(R.id.ripple_avail_btn);
        linearbottom = findViewById(R.id.lrn_bottom);


        section = (TextView) findViewById(R.id.txt_section);
        spot = (TextView) findViewById(R.id.txt_spot);
        btn_availableParking = (Button) findViewById(R.id.btn_available_parking);
//        btn_availableParking.setVisibility(View.GONE);
//        rippleLayout.setVisibility(View.GONE);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 100);
//        lp.weight = 2;
//        linearbottom.setLayoutParams(lp);


        user = SessionData.getInstance().getUser();

        txtEquipId = (TextView) findViewById(R.id.equip_id);
        img_note = (ImageView) findViewById(R.id.img_note);

        gallery = (Gallery) findViewById(R.id.Image_gallery);
        new AsyncType().execute();

        edt_WalkAroundnotes.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edt_WalkAroundnotes.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edt_WalkAroundnotes.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt_WalkAroundnotes.setText(SessionData.getInstance().getWalkaroundNotes());

        txtEquipId.setText(SessionData.getInstance().getEnteredEquipID());

        AddtoSession = SessionData.getInstance().getWalkaroundgeneralimages();
        arrayNotes = SessionData.getInstance().getWalkAroundNotes();
        arrayType = SessionData.getInstance().getWalkAroundType();
        CategoryID = SessionData.getInstance().getWalkAroundCategoryId();

        attachedFilesData = new ArrayList<>();

        attachedFilesData = SessionData.getInstance().getWalkaroundgeneralimages();

        gallery.setAdapter(new ImageAdapter(TransportWalkAroundInspection.this));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                Image_dialog = new Dialog(TransportWalkAroundInspection.this);
                Image_dialog.setCanceledOnTouchOutside(true);
                Image_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                Image_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(TransportWalkAroundInspection.this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SelectType.setAdapter(dataAdapter);

                SelectType.setSelection(dataAdapter.getPosition(SessionData.getInstance().getWalkAroundType().get(position)));

                SelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        category = categoryId.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (SelectType.getSelectedItemPosition() == 0) {

                            Toast.makeText(TransportWalkAroundInspection.this, "Select type", Toast.LENGTH_LONG).show();

                        } else if (notes.getText().toString().trim().length() == 0) {
                            Toast.makeText(TransportWalkAroundInspection.this, "Enter Notes", Toast.LENGTH_LONG).show();
                        } else {
                            String str;
                            str = notes.getText().toString();
                            String type = SelectType.getSelectedItem().toString();

                            Log.d("Selected Item", "" + SelectType.getSelectedItem().toString());
                            arrayType.remove(position);
                            arrayNotes.remove(position);
                            CategoryID.remove(position);
                            //AddtoSession.remove(position);

                            arrayType.add(type);
                            arrayNotes.add(str);
                            CategoryID.add(category);
                            //AddtoSession.add(attachedFilesData.get(position));

                            SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                            SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                            SessionData.getInstance().setWalkAroundType(arrayType);
                            SessionData.getInstance().setWalkAroundCategoryId(CategoryID);
                            //  in.add(position,1);
//                        attachedFilesData.remove(position);
//                        gallery.setAdapter(new ImageAdapter(WalkAroundCamera.this));
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

                        //  attachedFilesData.remove(position);
                        arrayNotes.remove(position);
                        CategoryID.remove(position);
                        arrayType.remove(position);
                        SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                        SessionData.getInstance().setWalkAroundType(arrayType);
                        SessionData.getInstance().setWalkAroundCategoryId(CategoryID);

                        gallery.setAdapter(new ImageAdapter(TransportWalkAroundInspection.this));

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
        img_note.setOnClickListener(this);
        btn_availableParking.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        attachedFilesData = SessionData.getInstance().getWalkaroundgeneralimages();

        arrayNotes = SessionData.getInstance().getWalkAroundNotes();
        arrayType = SessionData.getInstance().getWalkAroundType();
        CategoryID = SessionData.getInstance().getWalkAroundCategoryId();

        gallery.setAdapter(new ImageAdapter(TransportWalkAroundInspection.this));
        super.onResume();
    }

    public class ImageAdapter extends BaseAdapter {
        private final Context context;
        private final int itemBackground;
        final LayoutInflater inflator;

        public ImageAdapter(Context c) {
            context = c;

            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();

            inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        public int getCount() {
            return attachedFilesData.size();
        }


        public Object getItem(int position) {
            return position;
        }


        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

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
        gps = new GPSTracker(TransportWalkAroundInspection.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            SessionData.getInstance().setLatitude(latitude);
            SessionData.getInstance().setLongitude(longitude);
            Intent inspection = new Intent(TransportWalkAroundInspection.this, WalkAroundTransport.class);
            startActivity(inspection);
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onBackPressed() {

//        Intent inspection = new Intent(TransportWalkAroundInspection.this, TransportListDetails.class);
//        startActivity(inspection);
//
        Intent inspection = new Intent(TransportWalkAroundInspection.this, EbsMenu.class);
        startActivity(inspection);
        finish();

//        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        if (next == v) {
            SessionData.getInstance().setWalkaroundNotes(edt_WalkAroundnotes.getText().toString());
            Intent inspection = new Intent(TransportWalkAroundInspection.this, CustomizedTransportPartsCheck.class);
            startActivity(inspection);
        } else if (imgback == v) {
            onBackPressed();
//            new AsyncRentalDetail().execute();
//            Intent inspection = new Intent(TransportWalkAroundInspection.this, TransportListDetails.class);
//            startActivity(inspection);
        } else if (txtback == v) {
            onBackPressed();
            //  new AsyncRentalDetail().execute();
//            Intent inspection = new Intent(TransportWalkAroundInspection.this, TransportListDetails.class);
//            startActivity(inspection);
        } else if (Camera == v) {

            getGps();

        } else if (img_note == v) {

            Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            speech.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.speech_prompt));

            try {
                startActivityForResult(speech, SPEECH_CODE_ONE);
            } catch (ActivityNotFoundException a) {

                Toast.makeText(TransportWalkAroundInspection.this, "Sorry! Your device doesn\\'t support speech input ", Toast.LENGTH_SHORT).show();
            }

        } else if (btn_availableParking == v) {

//            new General_WalkAroundInspection.GetAvailableParkingSpots().execute();
            new GetAvailableParkingSpots().execute();
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

    private class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportWalkAroundInspection.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                detail = WebServiceConsumer.getInstance().getRentalDetail(
                        SessionData.getInstance().getEnteredEquipID(), SessionData.getInstance().getTemp_userToken());

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
            if (detail != null && detail.size() > 0) {

                if (detail.get(0).getMessage().equals("")) {
                    ProgressBar.dismiss();
                    SessionData.getInstance().setDetail(detail);
                    SessionData.getInstance().setEnteredEquipID(
                            SessionData.getInstance().getEnteredEquipID());
                    Intent intent = new Intent(TransportWalkAroundInspection.this,
                            RentalListSelector.class);
                    startActivity(intent);
                } else {

                    if (detail.get(0).getMessage().contains("Session")) {
                        session = 1;
                        new AsyncSessionLoginTask().execute();
                    } else {
                        dialog = new Dialog(TransportWalkAroundInspection.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);


                        TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                        TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                        Message.setText(detail.get(0).getMessage());

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent inspection = new Intent(TransportWalkAroundInspection.this,
                                        MainActivity.class);
                                startActivity(inspection);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }

                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportWalkAroundInspection.this,
                        "EquipmentID entered is invalid.");
            }

        }

    }


    private class AsyncType extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportWalkAroundInspection.this);
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
            // Log.d("categoryObjects Size","" + categoryObjects.size());

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
                        session = 0;
                        new AsyncSessionLoginTask().execute();
                    } else {
                        dialog = new Dialog(TransportWalkAroundInspection.this);
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

                                Intent inspection = new Intent(TransportWalkAroundInspection.this,
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

    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportWalkAroundInspection.this);
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

                if (user.getUserDescription().contains("Login is already in use")) {
                    dialog = new Dialog(TransportWalkAroundInspection.this);
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

                            Intent inspection = new Intent(TransportWalkAroundInspection.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);
                    if (session == 0) {
                        new AsyncType().execute();
                    } else if (session == 1) {
                        Intent inspection = new Intent(TransportWalkAroundInspection.this,
                                EbsMenu.class);
                        startActivity(inspection);
//                        new AsyncRentalDetail().execute();

                    } else if (session == 2) {
                        new GetAvailableParkingSpots().execute();
                    }


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(TransportWalkAroundInspection.this,
                        "Data is not Found");
            }


        }
    }

    public class CustomAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private final Context mContext;

        public CustomAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
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
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.child_available_list, null);
                holder.txtSection = (TextView) convertView.findViewById(R.id.txt_avail_section);
                holder.txtSpot = (TextView) convertView.findViewById(R.id.txt_avail_spot);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.txtSection.setText(generalEquipment.getSection());
//            holder.txtSpot.setText(generalEquipment.getSpot());
            return convertView;
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
//                    holder = new TransportWalkAroundInspection.AvailableAdapter.ViewHolder();
//                    holder= new ViewHolder();

                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.child_available_list, null);
                holder.txtSection = (TextView) convertView.findViewById(R.id.txt_avail_section);
                holder.txtSpot = (TextView) convertView.findViewById(R.id.txt_avail_spot);
                convertView.setFocusable(false);
                convertView.setTag(holder);

            } else {
//                    holder =(ViewHolder) convertView.getTag();
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtSection.setText(mparkingList.get(position).getSection());
            holder.txtSpot.setText(mparkingList.get(position).getSpot());
            return convertView;

        }
    }


    private class GetAvailableParkingSpots extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransportWalkAroundInspection.this);
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
                new AsyncSessionLoginTask().execute();

            } else {

                if (parkingList != null && (parkingList.size() > 0)) {
                    final Dialog dialog = new Dialog(TransportWalkAroundInspection.this);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_available_parking);
                    TextView Header_title = (TextView) dialog.findViewById(R.id.header_title);
                    ImageView imgview = (ImageView) dialog.findViewById(R.id.close_img);
                    availableList = (ListView) dialog.findViewById(R.id.available_list);
                    availableAdapter = new AvailableAdapter(TransportWalkAroundInspection.this, parkingList);
                    availableList.setAdapter(availableAdapter);
                    availableList.setEnabled(true);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    AlertDialogBox.showAlertDialog(TransportWalkAroundInspection.this,
                            "Data is not found.");

                }
            }
        }
    }

}




