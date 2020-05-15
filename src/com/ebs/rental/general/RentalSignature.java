package com.ebs.rental.general;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CheckinDetail;
import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.Equipmentsubstatusdesc;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("ALL")
public class RentalSignature extends AppCompatActivity implements OnClickListener {
    private SignaturePanel mSignature;
    private LinearLayout mContent;
    private LinearLayout btnsShow1;
    private LinearLayout btnsShow2;
    ArrayList<EquipmentCheckParts> partsList;
    ArrayList<CheckList> checkList;
    RentalCheck rentalcheckin;
    CheckinEqupments checkinequpments;
    CheckinDetail checkindetails;
    RentalChecklistPDF checklistpdf;
    private ArrayList<String> cheklistArray;
    private ArrayList<CustomerNameMail> customeremails;
    EqupmentSubStatus equpsubStatus;
    private ArrayList<RentalDetails> selectedDetail;
    private RentalDetails detail;
    EqupmentSubStatus equpsubStatusdata;
    Equipmentsubstatusdesc equpsubstatusdec;
    String rentalCheckinList;
    int getrentalcheck;
    private User user;
    private User objUser = null;
    private ArrayList<RentalDetails> detailList;
    private double dpi;
    public static int i = 0;
    private static int j = 0;
    public static int z;
    String str;
    private static Dialog dialog;
    private byte[] signatureData;
    private TextView textCancel;
    TextView encode;
    private int count;
    private Button btnClear;
    private Button btnSaveSignature;
    private Button NewSignature;
    private String sign;
    TextView txtSigneeName;
    ImageView imgback;

    //LinearLayout linearMainn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rental_signature);
        new ContextWrapper(getApplicationContext());
        btnClear = (Button) findViewById(R.id.clearpsignature);

        btnSaveSignature = (Button) findViewById(R.id.pgetsignrental);
        mContent = (LinearLayout) findViewById(R.id.linearLayoutpSignnature);
        textCancel = (TextView) findViewById(R.id.pViewbacksign);
        btnsShow1 = (LinearLayout) findViewById(R.id.linearLayoutpSignButsignature);
        btnsShow2 = (LinearLayout) findViewById(R.id.linearLayoutpSign);
        txtSigneeName = (TextView) findViewById(R.id.txt_Signame);
        imgback = (ImageView) findViewById(R.id.img_back);
        SessionData.getInstance().setMail(1);
        //linearMainn = (LinearLayout) findViewById(R.id.linearMain);
        //	linearMain.setOrientation(LinearLayout.VERTICAL);
        NewSignature = (Button) findViewById(R.id.pnewSignrental);
        selectedDetail = SessionData.getInstance().getSelectedDetail();
        cheklistArray = new ArrayList<String>();
        detailList = SessionData.getInstance().getDetail();
        j = SessionData.getInstance().getChecklistdata();
        user = SessionData.getInstance().getUser();
        detail = detailList.get(0);
        count = detailList.size();
        txtSigneeName.setText(detail.getContact());
        cheklistArray = new ArrayList<String>(SessionData.getInstance()
                .getCheckListData().values());

        // checkList = ReadJson.getPartsList(cheklistArray.get(j));
        textCancel.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSaveSignature.setOnClickListener(this);
        NewSignature.setOnClickListener(this);
        imgback.setOnClickListener(this);
        if (signatureData == null) {
            btnsShow1.setVisibility(View.GONE);
            btnsShow2.setVisibility(View.VISIBLE);

            btnSaveSignature.setEnabled(false);
        } else {
            btnsShow1.setVisibility(View.VISIBLE);
            btnsShow2.setVisibility(View.GONE);
        }
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
            mContent.addView(mSignature, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
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
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
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
            btnSaveSignature.setEnabled(true);

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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == textCancel) {
            finish();
        }
        if (v == btnClear) {
            mSignature.clear();
            btnSaveSignature.setEnabled(false);
        }else if(v==imgback){
            super.onBackPressed();



        } else if (v == btnSaveSignature) {
            SessionData.getInstance().setRentalSigneeName(txtSigneeName.getText().toString().trim());
            mContent.setDrawingCacheEnabled(true);
            mSignature.save(mContent);


            if (txtSigneeName.getText().toString().isEmpty()) {
                Toast.makeText(RentalSignature.this,
                        "Singee Name should not be empty", Toast.LENGTH_LONG).show();
            } else {

                new AsynMails().execute();

                //new AysncSubmitData().execute();

//			Intent intnt = new Intent(RentalSignature.this,CheckInMailDetails.class);
//			startActivity(intnt);

                Toast.makeText(RentalSignature.this,
                        "Signature saved successfully", Toast.LENGTH_LONG).show();
            }

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

    private class AsyncRentalDetail extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                objUser = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
                                SessionData.getInstance().getLogin_password());

                selectedDetail = WebServiceConsumer.getInstance()
                        .getRentalDetail(SessionData.getInstance().getEnteredEquipID(),
                                SessionData.getInstance().getTemp_userToken());
            } catch (java.net.SocketTimeoutException e) {
                selectedDetail = null;
            } catch (Exception e) {
                selectedDetail = null;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (selectedDetail != null) {

                if (selectedDetail.get(0).getMessage().equals("")) {

                    SessionData.getInstance().getSelectedDetail().clear();
                    SessionData.getInstance().getCheckListData().clear();
                    SessionData.getInstance().getDataHandlelist().clear();
                    SessionData.getInstance().getKpartlist().clear();
                    SessionData.getInstance().getHourmeterlist().clear();
                    SessionData.getInstance().getEqpStatus().clear();
                    SessionData.getInstance().setDetail(selectedDetail);

                    SessionData.getInstance().setEnteredEquipID(
                            SessionData.getInstance().getEnteredEquipID());
                    Intent intent = new Intent(RentalSignature.this,
                            RentalListSelector.class);
                    startActivity(intent);
                } else {
                    dialog = new Dialog(RentalSignature.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText(selectedDetail.get(0).getMessage());

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent inspection = new Intent(RentalSignature.this,
                                    MainActivity.class);
                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
            }
            //finish();

            // }
        }
    }


    private class AsynMails extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(RentalSignature.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {


//						objUser = WebServiceConsumer.getInstance()
//								.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//										SessionData.getInstance().getLogin_password());

                customeremails = WebServiceConsumer.getInstance().customermailsv1(
                        user.getUserDescription(), SessionData.getInstance().getKcustadd(),
                        SessionData.getInstance().getCustadd(), "R");
                Log.d("kcustadd", "" + SessionData.getInstance().getKcustadd());
                Log.d("custadd", "" + SessionData.getInstance().getCustadd());

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

                            new AsyncSessionLoginTask().execute();

                        } else {
                            dialog = new Dialog(RentalSignature.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            ImageView closeImg=dialog.findViewById(R.id.close_img);

                            Message.setText(customeremails.get(0).getMessage());

                            closeImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }

                    } else {
//                        SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(RentalSignature.this,
                                CheckInMailDetails.class);

                        startActivity(intent);
                        finish();
                    }
                } else {
//                    SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                    Intent intent = new Intent(RentalSignature.this,
                            CheckInMailDetails.class);

                    startActivity(intent);
                    finish();
                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalSignature.this,
                        "Data is not found.");
            }
        }

    }


    private class AsyncSessionLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(RentalSignature.this);
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
                    dialog = new Dialog(RentalSignature.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.message);


                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                    ImageView closeImg=dialog.findViewById(R.id.close_img);

                    Message.setText(user.getUserDescription());

                    closeImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                } else {
                    SessionData.getInstance().setUser(user);

                    new AsynMails().execute();


                }
            } else {
                ProgressBar.dismiss();
                AlertDialogBox.showAlertDialog(RentalSignature.this,
                        "Data is not Found");
            }


        }
    }


}

