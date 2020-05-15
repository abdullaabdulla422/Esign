package com.ebs.rental.parts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.general.GPSTracker;
import com.ebs.rental.general.MainActivity;
import com.ebs.rental.general.R;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.PartOrderTerms;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderSignedDocmuntPDFObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class SubmitPartOrder extends AppCompatActivity implements View.OnClickListener {

    private SignaturePanel mSignature;
    private byte[] signatureData;
    CardView crdTermsandConditions, crdisSignRequired, crdSelectEmail, crdExpandTerms, crdExpandsignature, crdExpandEmail;
    ImageView imgTermsandConditions, imgSignRequired, imgSelectEmail;

    Button btnSubmit, btnCancel, clear_sign;

    private TextView txtTearms;

    EditText username;

    LinearLayout mContent;
    private String sign;

    private PartOrderTerms orderTerms;
    private static Dialog dialog;
    private ArrayList<RentalOrderListDetailObject> listDetailObjects;
    private RentalOrderListDetailObject DetailObjects;
    private ArrayList<CustomerNameMail> customeremails;
    private User user;
    String Str_Terms;
    int intTerms = 0, intsignature = 0, intemail = 0, isSigned = 0, isSigneeRequired = 0;


    private ListView list;

    private String string;
    private ArrayList<String> aList;
    ArrayList<String> edtlist;
    private ArrayAdapter<String> adapter;
    private int count;
    private Button submit;
    private EditText editmail;
    private RentalOrderList deal;
    private ImageView imgBack;

    private TextView txtBack;

    private String email;
    String emailPattern;

    private RentalOrderSignedDocmuntPDFObject rentaorderdocpdf;
    private String str;
    private String signature;
    LinearLayout layout;

    TextView txtHeader;

    LinearLayout layoutSubmit, layoutTearms;

    Button btnAccept, btnDecline, btnSave;

    int EditFocus = 0;

    private CheckBox nosign;


    ArrayList<CustomerNameMail> customerNameMails = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsSession = new ArrayList<>();
    ArrayList<CustomerNameMail> customerNameMailsNew = new ArrayList<>();
    private Adaptor adap;
    private ImageView imgAddMail;
    private String session = "";


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.submit_part_order);
        crdTermsandConditions = findViewById(R.id.card_view_terms);
        crdisSignRequired = findViewById(R.id.card_view_signature);
        crdSelectEmail = findViewById(R.id.card_view_email);

        crdExpandTerms = findViewById(R.id.expand_card_view_terms);
        crdExpandsignature = findViewById(R.id.expand_card_view_signature);
        crdExpandEmail = findViewById(R.id.expand_card_view_email);

        nosign = findViewById(R.id.nosign);


        imgTermsandConditions = findViewById(R.id.drop_down_terms);
        imgSignRequired = findViewById(R.id.drop_down_signature);
        imgSelectEmail = findViewById(R.id.drop_down_email);

        txtHeader = findViewById(R.id.txt_header);

        txtHeader.setText("Acceptance");


        imgBack = findViewById(R.id.backicon);
        txtBack = findViewById(R.id.backtext);
        imgAddMail = findViewById(R.id.img_add_mail);
        imgAddMail.setOnClickListener(this);

        layout = findViewById(R.id.layout);
        layoutSubmit = findViewById(R.id.submit_button);
        layoutTearms = findViewById(R.id.tearms_button);

//        SessionData.getInstance().setContact("");

        submit = findViewById(R.id.btn_submit);

        SessionData.getInstance().setHassign(false);


        list = findViewById(R.id.pmaillist);

        editmail = findViewById(R.id.pedtmail);
        editmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        deal = SessionData.getInstance().getVal();

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        btnAccept = findViewById(R.id.btn_accept_terms);
        btnDecline = findViewById(R.id.btn_decline);
        btnSave = findViewById(R.id.save);
        username = findViewById(R.id.pSigname);
        username.setText(SessionData.getInstance().getContact());
        clear_sign = findViewById(R.id.clear);
        mContent = findViewById(R.id.linearLayoutpSign);


        mContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);

                return false;
            }
        });

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditFocus = 1;
                return false;
            }
        });

        txtTearms = findViewById(R.id.txt_tearms);
        btnSave.setOnClickListener(this);


        user = SessionData.getInstance().getUser();
        listDetailObjects = SessionData.getInstance().getOrderListDetail();
        DetailObjects = listDetailObjects.get(0);
        orderTerms = SessionData.getInstance().getPterms();
        SessionData.getInstance().setSigned("");
        // txtTearms = (TextView)findViewById(R.id.txtptearms);

        Str_Terms = orderTerms.getResult();
        Str_Terms = Str_Terms.replace("\n", "\\n");
        Str_Terms = Str_Terms.replace("\\n", "\n");
        txtTearms.setText(Str_Terms);
        SessionData.getInstance().setSig(null);

        crdTermsandConditions.setOnClickListener(this);
        crdisSignRequired.setOnClickListener(this);
        crdSelectEmail.setOnClickListener(this);
        clear_sign.setOnClickListener(this);
        submit.setOnClickListener(this);
        nosign.setOnClickListener(this);

        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);


        mSignature = new SignaturePanel(this, null);
        if (signatureData != null) {
            final Paint paint = new Paint();
            final Bitmap mBitmap = BitmapFactory.decodeByteArray(signatureData,
                    0, signatureData.length);
            mContent.setBackground(new Drawable() {

                @Override
                public void setColorFilter(ColorFilter cf) {

                }

                @Override
                public void setAlpha(int alpha) {

                }

                @SuppressLint("WrongConstant")
                @Override
                public int getOpacity() {
                    return 0;
                }

                @Override
                public void draw(Canvas canvas) {
                    canvas.drawBitmap(mBitmap, 0, 0, paint);

                }
            });
        } else {
            mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }


        new AsynMails().execute();

    }

    protected void hideKeyboard(View view) {

    }

    @Override
    public void onClick(View v) {


        if (crdTermsandConditions == v) {
            imgSignRequired.setImageResource(R.drawable.right_arrow);
            crdExpandsignature.setVisibility(View.GONE);
            intsignature = 0;
            imgSelectEmail.setImageResource(R.drawable.right_arrow);
            crdExpandEmail.setVisibility(View.GONE);
            intemail = 0;
            if (intTerms == 0) {
                imgTermsandConditions.setImageResource(R.drawable.down_arrow);
                crdExpandTerms.setVisibility(View.VISIBLE);
                intTerms = 1;
//                layoutSubmit.setVisibility(View.GONE);
//                layoutTearms.setVisibility(View.VISIBLE);
//                crdisSignRequired.setVisibility(View.GONE);
//                crdSelectEmail.setVisibility(View.GONE);
            } else {
                imgTermsandConditions.setImageResource(R.drawable.right_arrow);
                crdExpandTerms.setVisibility(View.GONE);
                intTerms = 0;
//                layoutSubmit.setVisibility(View.GONE);
//                layoutTearms.setVisibility(View.VISIBLE);
            }
        }
        if (crdisSignRequired == v) {
            layoutSubmit.setVisibility(View.VISIBLE);
            layoutTearms.setVisibility(View.GONE);
            imgTermsandConditions.setImageResource(R.drawable.right_arrow);
            crdExpandTerms.setVisibility(View.GONE);
            intTerms = 0;
            imgSelectEmail.setImageResource(R.drawable.right_arrow);
            crdExpandEmail.setVisibility(View.GONE);
            intemail = 0;
            if (intsignature == 0) {
                imgSignRequired.setImageResource(R.drawable.down_arrow);
                crdExpandsignature.setVisibility(View.VISIBLE);
                intsignature = 1;
            } else {
                imgSignRequired.setImageResource(R.drawable.right_arrow);
                crdExpandsignature.setVisibility(View.GONE);
                intsignature = 0;

            }
        }
        if (crdSelectEmail == v) {
            layoutSubmit.setVisibility(View.VISIBLE);
            layoutTearms.setVisibility(View.GONE);
            imgTermsandConditions.setImageResource(R.drawable.right_arrow);
            crdExpandTerms.setVisibility(View.GONE);
            intTerms = 0;
            imgSignRequired.setImageResource(R.drawable.right_arrow);
            crdExpandsignature.setVisibility(View.GONE);
            intsignature = 0;
            if (intemail == 0) {
                imgSelectEmail.setImageResource(R.drawable.down_arrow);
                crdExpandEmail.setVisibility(View.VISIBLE);
                intemail = 1;

            } else {
                imgSelectEmail.setImageResource(R.drawable.right_arrow);
                crdExpandEmail.setVisibility(View.GONE);
                intemail = 0;
            }
            crdSelectEmail.setVisibility(View.VISIBLE);

            setListViewHeightBasedOnChildren(list);
        }
        if (clear_sign == v) {
            SessionData.getInstance().setHassign(false);
            isSigned = 0;
            mSignature.clear();
            btnSave.setVisibility(View.GONE);
        }
        if (submit == v) {
            if (isSigned == 1) {
                SessionData.getInstance().setContact(username.getText().toString());
                mContent.setDrawingCacheEnabled(true);
                mSignature.save(mContent);
            }
            SparseBooleanArray checked = list.getCheckedItemPositions();
            ArrayList<String> selecteditems = new ArrayList<String>();
            if (checked != null) {
                for (int i = 0; i < checked.size(); i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i))
                        selecteditems.add(adapter.getItem(position));

                }
            }
            String[] outputStrArr = new String[selecteditems.size()];
            for (int i = 0; i < selecteditems.size(); i++) {
                outputStrArr[i] = selecteditems.get(i);
            }

            str = selecteditems.toString();
            str = str.replace("[", "");
            str = str.replace("]", "");
            email = editmail.getText().toString().trim();
            email = email.replace(" ", "");
            String[] test = email.split(",");
            boolean emailflag = true;
            for (int i = 0; i < test.length; i++) {

                if (validEmail(test[i])) {
                    emailflag = true;
                } else {
                    emailflag = false;
                    break;
                }

            }


            if (isSigneeRequired == 1) {
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Signee Name should not be empty",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (editmail.getText().toString().length() == 0 && str.isEmpty()) {
//                Toast.makeText(getApplicationContext(),
//                        "Select or Enter the Mail", Toast.LENGTH_LONG).show();

//                        new AsynSubmitSigPdf().execute();
                        updateEmailsAndSubmit();

                    } else {
                        if (editmail.getText().toString().length() != 0
                                && emailflag == false) {
                            Toast.makeText(getApplicationContext(), "Invalid Mail ID",
                                    Toast.LENGTH_LONG).show();
                        } else {

//                            new AsynSubmitSigPdf().execute();
                            updateEmailsAndSubmit();

                        }
                    }
                }

            } else {
                if (editmail.getText().toString().length() == 0 && str.isEmpty()) {
//                Toast.makeText(getApplicationContext(),
//                        "Select or Enter the Mail", Toast.LENGTH_LONG).show();

//                    new AsynSubmitSigPdf().execute();
                    updateEmailsAndSubmit();

                } else {
                    if (editmail.getText().toString().length() != 0
                            && emailflag == false) {
                        Toast.makeText(getApplicationContext(), "Invalid Mail ID",
                                Toast.LENGTH_LONG).show();
                    } else {

//                        new AsynSubmitSigPdf().execute();
                        updateEmailsAndSubmit();

                    }
                }
            }

        }

        if (txtBack == v) {
            Intent intent = new Intent(SubmitPartOrder.this,
                    RentalPartsOrderListDetail.class);
            startActivity(intent);
        }
        if (imgBack == v) {
            Intent intent = new Intent(SubmitPartOrder.this,
                    RentalPartsOrderListDetail.class);
            startActivity(intent);
        }
        if (btnCancel == v) {
            Intent intent = new Intent(SubmitPartOrder.this,
                    RentalPartsOrderListDetail.class);
            startActivity(intent);
        }
        if (btnAccept == v) {
            if (!nosign.isChecked()) {
                crdisSignRequired.setVisibility(View.VISIBLE);
                crdSelectEmail.setVisibility(View.VISIBLE);
                imgSignRequired.setImageResource(R.drawable.down_arrow);
                crdExpandsignature.setVisibility(View.VISIBLE);
                intsignature = 1;

                isSigneeRequired = 1;
            } else {
                crdisSignRequired.setVisibility(View.GONE);
                crdSelectEmail.setVisibility(View.VISIBLE);
                imgSelectEmail.setImageResource(R.drawable.down_arrow);
                crdExpandEmail.setVisibility(View.VISIBLE);
                intemail = 1;
            }
            layoutSubmit.setVisibility(View.VISIBLE);
            layoutTearms.setVisibility(View.GONE);
            imgTermsandConditions.setImageResource(R.drawable.right_arrow);
            crdExpandTerms.setVisibility(View.GONE);
            intTerms = 0;

        }
        if (btnDecline == v) {
            Intent intent = new Intent(SubmitPartOrder.this,
                    RentalPartsOrderListDetail.class);
            startActivity(intent);
        }
        if (btnSave == v) {

            Toast.makeText(getApplicationContext(), "Signature Saved Successfully",
                    Toast.LENGTH_LONG).show();
            imgSelectEmail.setImageResource(R.drawable.down_arrow);
            crdExpandEmail.setVisibility(View.VISIBLE);
            intemail = 1;
            imgSignRequired.setImageResource(R.drawable.right_arrow);
            crdExpandsignature.setVisibility(View.GONE);
            intsignature = 0;

        }
        if (imgAddMail == v) {
            addMail();
            list.setVisibility(View.VISIBLE);
        }


    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 60;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private class AsynSubmitSigPdf extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(SubmitPartOrder.this);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                rentaorderdocpdf = WebServiceConsumer.getInstance()
                        .rentalOrderSignedDocumentPdfV2(
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
                    Intent intent = new Intent(SubmitPartOrder.this,
                            RentalPartsOrderSearch.class);
                    startActivity(intent);
                    SessionData.getInstance().setContact("");
                } else {

                    if (rentaorderdocpdf.getMessage().contains("Session")) {
                        new AsyncLoginTask().execute();
                    } else {
                        dialog = new Dialog(SubmitPartOrder.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.message);

                        TextView Message = dialog.findViewById(R.id.txt_dialog);
                        TextView yes = dialog.findViewById(R.id.dialog_Ok);
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

    public class SignaturePanel extends View {
        private static final float STROKE_WIDTH = 2f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private final Paint paint = new Paint();
        private final Path path = new Path();

        public Bitmap mBitmap;
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public SignaturePanel(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);

        }

        public void save(View v) {


            mBitmap = Bitmap.createBitmap(mContent.getWidth(),
                    mContent.getHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(mBitmap);

            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                try {
                    SessionData.getInstance().setSig(
                            setExifMetaData(stream));
                    sign = Base64.encodeToString(setExifMetaData(stream), Base64.DEFAULT);
                    SessionData.getInstance().setSigned(sign);
                    Log.d("signature data", "" + setExifMetaData(stream));
                } catch (Exception e) {
                    SessionData.getInstance().setSig(stream.toByteArray());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void clear() {
            mContent.setBackgroundResource(R.drawable.backgroundsignature);
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            SessionData.getInstance().setHassign(true);

            isSigned = 1;
            btnSave.setVisibility(View.VISIBLE);

            if (EditFocus == 1) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private byte[] setExifMetaData(ByteArrayOutputStream stream)
            throws IOException {
        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        path = path + "/Data/signature";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");
        new File(path).mkdirs();
        path += "/sign_" + format.format(date) + ".jpg";

        FileOutputStream fout = new FileOutputStream(path);
        fout.write(stream.toByteArray());
        fout.flush();
        fout.close();

        ExifInterface newexif = new ExifInterface(path);

        newexif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, "0");

        double latitude = 0D, longitude = 0D;

        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        newexif.setAttribute(ExifInterface.TAG_DATETIME, format.format(date));
        newexif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude + "");
        newexif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, "" + longitude);

        newexif.saveAttributes();

        FileInputStream inStream = new FileInputStream(path);
        byte[] buffer = new byte[inStream.available()];
        inStream.read(buffer);
        inStream.close();
        // new File(path).delete();
        return buffer;
    }

    private class AsynMails extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(SubmitPartOrder.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                customeremails = WebServiceConsumer.getInstance().customermailsv1(
                        user.getUserDescription(), DetailObjects.getKcustnum(), DetailObjects.getCustsnum(), "P");

            } catch (java.net.SocketTimeoutException e) {
                customeremails = null;

            } catch (Exception e) {
                customeremails = null;

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ProgressBar.dismiss();
            if (customeremails != null) {

                if (customeremails.size() != 0) {
                    if (customeremails.get(0).getMessage().length() != 0) {
                        if (customeremails.get(0).getMessage().contains("Session")) {
                            new AsyncLoginTask().execute();
                        } else {
                            dialog = new Dialog(SubmitPartOrder.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = dialog.findViewById(R.id.txt_dialog);
                            TextView yes = dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(customeremails.get(0).getMessage());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//							Intent inspection = new Intent(PartsTermsAndConditions.this,
//									MainActivity.class);
//							startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        // customeremails = SessionData.getInstance().getCustomeremails();


//                    string = customeremails.get(0).getResult();

//                        if (!string.isEmpty()) {
                        if (customeremails.size() != 0) {

//                            aList = new ArrayList<String>(Arrays.asList(string.split(",")));
//                            adapter = new ArrayAdapter<String>(SubmitPartOrder.this,
//                                    R.layout.simple_list_multi_choice, aList);
                            // adap = new MailAdapter(CustomerMailDetails.this, aList);
//                            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


                            count = customeremails.size();

                            customerNameMails = SessionData.getInstance().getCustomerNameMails();
                            customerNameMailsSession.clear();
                            customerNameMailsSession.addAll(SessionData.getInstance().getCustomerNameMails());
                            adap = new Adaptor(SubmitPartOrder.this, customerNameMails);

                            list.setAdapter(adap);

                            SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
                            int itemCount = list.getCount();


                            if (count == 0) {
                                list.setVisibility(View.INVISIBLE);

//                                for (int i = itemCount - 1; i >= 0; i--) {
//                                    if (checkedItemPositions.get(i)) {
//                                        adapter.remove(aList.get(i));
//                                    }
//                                }
//
//                                checkedItemPositions.clear();
//                                adapter.notifyDataSetChanged();
                            } else {
                                list.setVisibility(View.VISIBLE);
                            }

		/*if (count == 0) {
			list.setVisibility(View.GONE);
		}*/
                            list.setAdapter(adap);
//                            list.setAdapter(adapter);
                            list.setTextFilterEnabled(true);
                            setListViewHeightBasedOnChildren(list);
                        } else {
                           // list.setVisibility(View.GONE);
                        }

                    }
                } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                    // customeremails = SessionData.getInstance().getCustomeremails();


//                    string = customeremails.get(0).getResult();
//                    if (!string.isEmpty()) {

                    if (customeremails.size() != 0) {

//                        aList = new ArrayList<String>(Arrays.asList(string.split(",")));
//                        adapter = new ArrayAdapter<String>(SubmitPartOrder.this,
//                                R.layout.simple_list_multi_choice, aList);
//                        // adap = new MailAdapter(CustomerMailDetails.this, aList);
//                        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                        count = aList.size();
//                        count = customeremails.size();

                        customerNameMails = SessionData.getInstance().getCustomerNameMails();
                        customerNameMailsSession.clear();
                        customerNameMailsSession.addAll(SessionData.getInstance().getCustomerNameMails());
                        adap = new Adaptor(SubmitPartOrder.this, customerNameMails);

                        list.setAdapter(adap);

                        SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
                        int itemCount = list.getCount();
                        if (count == 0) {
                            list.setVisibility(View.INVISIBLE);

//                            for (int i = itemCount - 1; i >= 0; i--) {
//                                if (checkedItemPositions.get(i)) {
//                                    adapter.remove(aList.get(i));
//                                }
//                            }
//
//                            checkedItemPositions.clear();
//                            adapter.notifyDataSetChanged();
                        } else {
                            list.setVisibility(View.VISIBLE);
                        }

		/*if (count == 0) {
			list.setVisibility(View.GONE);
		}*/
//                        list.setAdapter(adapter);
                        list.setAdapter(adap);
                        list.setTextFilterEnabled(true);
                        setListViewHeightBasedOnChildren(list);
                    } else {
                        list.setVisibility(View.GONE);
                    }

                }
            } else {
                ProgressBar.dismiss();
//                AlertDialogBox.showAlertDialog(PartsTermsAndConditions.this,
//                        "Data is not found.");
            }
        }

    }

    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SubmitPartOrder.this);
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


                if (user.getUserId() == 0) {
                    dialog = new Dialog(SubmitPartOrder.this);
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

                            Intent inspection = new Intent(SubmitPartOrder.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {

                    if (session.equals("AsynMails")) {
                        new AsynMails().execute();
                    } else if (session.equals("AsynSubmitSigPdf")) {
                        new AsynSubmitSigPdf().execute();
                    } else if (session.equals("AsyncUpdateContactEmails")) {
                        new AsyncUpdateContactEmails().execute(customerNameMailsNew);
                    }

                }


            } else {
                ProgressBar.dismiss();

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
//                holder.img_delete.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });

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

                holder.img_delete.setOnClickListener(new View.OnClickListener() {
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


    public class AsyncUpdateContactEmails extends AsyncTask<ArrayList<CustomerNameMail>, Void, Void> {
        String result = "";

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(SubmitPartOrder.this);
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

    public void updateEmailsAndSubmit() {

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
        } else {
            str = getStrEmailSting(customerNameMails);
            email = getEmailSting(customerNameMailsNew);

            Log.i("getStrEmailSting", "str: " + str);
            Log.i("getEmailSting", "email: " + email);

            new AsyncUpdateContactEmails().execute(customerNameMailsNew);
        }
    }

    public void addMail() {
        if (customerNameMails.size() > 0) {
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
                    adap = new Adaptor(SubmitPartOrder.this, customerNameMails);
                    list.setAdapter(adap);
                    adap.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(list);
                } else {
                    Toast.makeText(SubmitPartOrder.this, "Enter the Vaild Mail", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(SubmitPartOrder.this, "Enter the Mail", Toast.LENGTH_LONG).show();
            }
        } else {

            CustomerNameMail customerNameMail = new CustomerNameMail();
            customerNameMail.setIsAlreadyExit(0);
            customerNameMail.setMessage("");
            customerNameMail.setEmail("");
            customerNameMail.setCustname("");
            customerNameMail.setSelected(false);
            customerNameMails.add(customerNameMail);
            customerNameMailsNew.add(customerNameMail);
            adap = new Adaptor(SubmitPartOrder.this, customerNameMails);
            list.setAdapter(adap);
            adap.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(list);
        }

    }

}
