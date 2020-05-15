package com.ebs.rental.general;

import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.EquipmentTransferChecklistDetailObject;
import com.ebs.rental.objects.EquipmentTransferHeadObject;
import com.ebs.rental.objects.Equipmentsubstatusdesc;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransportDetailsPDFObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.CheckList;
import com.ebs.rental.utils.EquipmentCheckParts;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class TransferSignature extends AppCompatActivity implements View.OnClickListener {
    private SignaturePanel mSignature;
    private LinearLayout mContent;
    private LinearLayout btnsShow1;
    private LinearLayout btnsShow2;
    ArrayList<EquipmentCheckParts> partsList;
    private GeneralEquipmentSearchObject generalEquipment;
    private EquipmentTransferChecklistDetailObject checkindetails;
    ArrayList<CheckList> checkList;
    RentalCheck rentalcheckin;
    private CheckinEqupments checkinequpments;

    private RentalChecklistPDF checklistpdf;
    private ArrayList<String> cheklistArray;
    private ArrayList<CustomerNameMail> customeremails;
    EqupmentSubStatus equpsubStatus;
    private EquipmentTransferHeadObject equipmentTransferHead;
    private TransferEquipmentSearchObject transferEquipmentSearchObject;

    //ArrayList<RentalDetails> selectedDetail;

    RentalDetails detail;
    EqupmentSubStatus equpsubStatusdata;
    Equipmentsubstatusdesc equpsubstatusdec;
    String rentalCheckinList;
    private int getrentalcheck;
    private User user;

    User objUser = null;

    private double dpi;
    public static int i = 0;
    private static int j = 0;
    public static int z;
    private String str;
    private static Dialog dialog;
    private byte[] signatureData;
    private TextView textCancel;
    private TransportListObject transportObject;
    TransportDetailsPDFObject transportDetailsPDFObject;
    TextView encode;
    private int count;
    private Button btnClear;
    private Button btnSaveSignature;
    private Button NewSignature;
    private String sign;
    private String SelectedTransferBranch;
    private String CurrentBranch;
    TextView txtSigneeName;
    private String session="";

    //LinearLayout linearMainn;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rental_signature);
        new ContextWrapper(getApplicationContext());

        generalEquipment = SessionData.getInstance().getGeneralEquipmentSearchObject();
        btnClear = (Button) findViewById(R.id.clearpsignature);

        btnSaveSignature = (Button) findViewById(R.id.pgetsignrental);

        mContent = (LinearLayout) findViewById(R.id.linearLayoutpSignnature);

        textCancel = (TextView) findViewById(R.id.pViewbacksign);

        btnsShow1 = (LinearLayout) findViewById(R.id.linearLayoutpSignButsignature);

        btnsShow2 = (LinearLayout) findViewById(R.id.linearLayoutpSign);

        transferEquipmentSearchObject = SessionData.getInstance().getTransferEquipment();

        SelectedTransferBranch = SessionData.getInstance().getSelectedTransferbranch();

        String[] test = SelectedTransferBranch.split("-");

        SelectedTransferBranch = test[0];

        txtSigneeName = (TextView) findViewById(R.id.txt_Signame);

//        SelectedTransferBranch = SelectedTransferBranch.substring(0, 3);

        Log.d("TransferBranch", "" + SelectedTransferBranch);

        if (transferEquipmentSearchObject.getCurrentbranch().length() != 0) {
            CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();

            CurrentBranch = CurrentBranch.substring(0, 3);

            Log.d("CurrentBranch", "" + CurrentBranch);

        } else {
            CurrentBranch = transferEquipmentSearchObject.getCurrentbranch();

            Log.d("CurrentBranch", "" + CurrentBranch);
        }

        SessionData.getInstance().setMail(1);
        //linearMainn = (LinearLayout) findViewById(R.id.linearMain);
        //	linearMain.setOrientation(LinearLayout.VERTICAL);
        NewSignature = (Button) findViewById(R.id.pnewSignrental);
        // selectedDetail = SessionData.getInstance().getSelectedDetail();
        cheklistArray = new ArrayList<String>();
        // detailList = SessionData.getInstance().getDetail();
        j = SessionData.getInstance().getChecklistdata();
        user = SessionData.getInstance().getUser();

        txtSigneeName.setText(user.getUsername());
        // detail = detailList.get(0);
        count = 1;
        cheklistArray = new ArrayList<String>(SessionData.getInstance()
                .getCheckListData().values());

        // checkList = ReadJson.getPartsList(cheklistArray.get(j));
        textCancel.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSaveSignature.setOnClickListener(this);
        NewSignature.setOnClickListener(this);
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
            mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
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

        if (v == textCancel) {
            finish();
        }
        if (v == btnClear) {
            mSignature.clear();
            btnSaveSignature.setEnabled(false);


        } else if (v == btnSaveSignature) {
            mContent.setDrawingCacheEnabled(true);
            mSignature.save(mContent);

            // new AsynMails().execute();


            if (txtSigneeName.getText().toString().isEmpty()) {
                Toast.makeText(TransferSignature.this,
                        "Singee Name should not be empty", Toast.LENGTH_LONG).show();
            } else {

                new AysncSubmitData().execute();

                //new AysncSubmitData().execute();

//			Intent intnt = new Intent(RentalSignature.this,CheckInMailDetails.class);
//			startActivity(intnt);

                Toast.makeText(TransferSignature.this,
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
//                        .getRentalDetail(SessionData.getInstance().getEnteredEquipID(),
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
//            if (selectedDetail != null ) {
//
//                if (selectedDetail.get(0).getMessage().equals("")) {
//
//                    SessionData.getInstance().getSelectedDetail().clear();
//                    SessionData.getInstance().getCheckListData().clear();
//                    SessionData.getInstance().getDataHandlelist().clear();
//                    SessionData.getInstance().getKpartlist().clear();
//                    SessionData.getInstance().getHourmeterlist().clear();
//                    SessionData.getInstance().getEqpStatus().clear();
//                    SessionData.getInstance().setDetail(selectedDetail);
//
//                    SessionData.getInstance().setEnteredEquipID(
//                            SessionData.getInstance().getEnteredEquipID());
//                    Intent intent = new Intent(General_Signature_Capture.this,
//                            RentalListSelector.class);
//                    startActivity(intent);
//                } else {
//                    dialog = new Dialog(General_Signature_Capture.this);
//                    dialog.setCanceledOnTouchOutside(true);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////				requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.message);
//
//
//                    TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
//                    TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
//                    Message.setText(selectedDetail.get(0).getMessage());
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent inspection = new Intent(General_Signature_Capture.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
//                            dialog.dismiss();
//                        }
//                    });
//
//
//                    dialog.show();
//                }
//            }
//            //finish();
//
//            // }
//        }
//    }


    public class AysncSubmitData extends AsyncTask<Void, Void, Void> {

        EquipmentTransferChecklistDetailObject resultdata;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar.showCommonProgressDialog(TransferSignature.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Log.d("The Session data Size", ""
                        + SessionData.getInstance().getDataHandlelist().size());
                Logger.log("The Session data Size" + ""
                        + SessionData.getInstance().getDataHandlelist().size());

                Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
                        .entrySet();

                Iterator<?> it = entrySet.iterator();
                Log.d("The size of IT", ""
                        + SessionData.getInstance().getDataHandlelist()
                        .entrySet().size());

//                objUser = WebServiceConsumer.getInstance()
//                        .authenticateUserDealer(SessionData.getInstance().getLogin_username(),
//                                SessionData.getInstance().getLogin_password());

                if (SessionData.getInstance().getTransportTransfer() == 1) {
                    equipmentTransferHead = WebServiceConsumer.getInstance().EquipmentTransferHead(
                            0, SessionData.getInstance().getSelectedTransferbranch(),
                            SessionData.getInstance().getTransferIn_out(),
                            user.getUserDescription(), SessionData.getInstance().getTransportcallNum());
                } else {
                    equipmentTransferHead = WebServiceConsumer.getInstance().EquipmentTransferHead(
                            0, SessionData.getInstance().getSelectedTransferbranch(),
                            SessionData.getInstance().getTransferIn_out(),
                            user.getUserDescription(), 0);
                    Log.d("equipmentTransferHead_Message", " = " + equipmentTransferHead.getMessage());
                }
                if (SessionData.getInstance().getEquipmentTransferHeadObject() == 1) {

                    if (equipmentTransferHead.getMessage().contains("Session")) {
                        session="AysncSubmitData";

                        new AsyncLoginTask().execute();

                    } else {
                        TransferSignature.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog = new Dialog(TransferSignature.this);
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.message);


                                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                                ImageView closeImg=dialog.findViewById(R.id.close_img);

                                Message.setText(equipmentTransferHead.getMessage());
                                Log.d("equipmentTransferHead_Message", " = " + equipmentTransferHead.getMessage());

                                closeImg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSignature.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        });

                    }


                } else {
                    getrentalcheck = Integer.parseInt(equipmentTransferHead.getResult());
                    if (SessionData.getInstance().getSig() != null) {
                        str = Base64.encodeToString(SessionData.getInstance()
                                .getSig().clone(), Base64.DEFAULT);
                    }
                    int cust_branch;
                    if (SessionData.getInstance().getTransferEquipment().getCustrebranch().toString().equalsIgnoreCase("0")) {
                        cust_branch = 0;
                    } else {
                        cust_branch = 1;
                    }
                    String kbran = SessionData.getInstance().getSelectedCustomerBranch();
                    for (int j = 0; j < kbran.length(); j++) {
                        Character character = kbran.charAt(j);

                        if (character.toString().equals("-")) {
                            kbran = kbran.substring(0, j);
                            break;
                        }
                    }
                    if (SessionData.getInstance().getTransfer_hourMeter().isEmpty()) {
                        SessionData.getInstance().setTransfer_hourMeter("0");
                    }
                    checkinequpments = WebServiceConsumer
                            .getInstance()
                            .EquipmentTransferDetailWithWalkAround(
                                    user.getUserDescription(),
                                    0,
                                    Integer.parseInt(equipmentTransferHead.getResult()),
                                    transferEquipmentSearchObject.getEquipmentid(),
                                    transferEquipmentSearchObject.getPonum(),
                                    kbran,
                                    transferEquipmentSearchObject.getTransportid(),
                                    SessionData.getInstance().getWalkaroundNotes(), SessionData.getInstance().getSelectedCustomerBranch(),
                                    Integer.parseInt(transferEquipmentSearchObject.getTranstype()),
                                    SessionData.getInstance().isTransportNeeded(), SessionData.getInstance().getTransferKcustnum(), transferEquipmentSearchObject.getMfg(), transferEquipmentSearchObject.getModel(),
                                    transferEquipmentSearchObject.getSerialno(), SelectedTransferBranch,
                                    txtSigneeName.getText().toString(), Integer.parseInt(SessionData.getInstance().getTransfer_hourMeter()), cust_branch);
                    // Log.d("Equpstatus", "" + statusselect);
                    //     Log.d("result data", "" + rentalcheckin.getResult());
                    // int rentalCheckinDetail;
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();

                        checkindetails = WebServiceConsumer.getInstance()
                                .equipmentTransferChecklistDetail(
                                        Integer.parseInt(checkinequpments
                                                .getResult()),
                                        (int) me.getKey(),
                                        ((CheckinListData) me.getValue())
                                                .getStatus(),

                                        ((CheckinListData) me.getValue())
                                                .getNotes(), 0,
                                        user.getUserDescription());
                        Log.d("Description for webservice", ""
                                + ((CheckinListData) me.getValue()).getNotes());

                        if ((((CheckinListData) me.getValue()).getImagePath()
                                .length() > 4)) {
                            resultdata = WebServiceConsumer.getInstance()
                                    .equipmentTransferChecklistImages(
                                            Integer.parseInt(checkindetails
                                                    .getResult()),
                                            ((CheckinListData) me.getValue())
                                                    .getImageName(),
                                            "",
                                            0,
                                            ((CheckinListData) me.getValue())
                                                    .getImagePath(),
                                            0,
                                            user.getUserDescription(),
                                            transferEquipmentSearchObject.getEquipmentid(), SessionData.getInstance().getTransferIn_out());
                            Log.d("multicheckin", ""
                                    + SessionData.getInstance().getEnteredEquipID());

                        }


                    }
                    checklistpdf = WebServiceConsumer.getInstance()
                            .transferChecklistPdf(
                                    user.getUserDescription(),
                                    transferEquipmentSearchObject.getEquipmentid(),
                                    0,
                                    "0", str, "", "",
                                    0, Integer.parseInt(equipmentTransferHead.getResult()),
//                                    transferEquipmentSearchObject.getCurrentbranch(),
//                                    SessionData.getInstance().getSelectedTransferbranch(),
                                    SessionData.getInstance().getSelectedTransferbranch(), SessionData.getInstance().getTobranch(),
                                    txtSigneeName.getText().toString());

                    if (SessionData.getInstance().getTransportTransfer() == 1) {
                        transportObject = SessionData.getInstance().getTransportObject();
                        transportDetailsPDFObject = WebServiceConsumer.getInstance().UpdateTransportDeliveryDet(user.getUserDescription(), Integer.parseInt(transportObject.getTransport_id()), transportObject.getOejobnum(), Integer.parseInt(transportObject.getKordnum()));
                    }

                    Log.d("rentaldetailid", ""
                            + SessionData.getInstance().getRentaldetlid());
                }

            } catch (SocketTimeoutException e) {
                checklistpdf = null;

            } catch (Exception e) {
                checklistpdf = null;
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ProgressBar.dismiss();
            // linearMainn.removeAllViews();
            SessionData.getInstance().getDataHandlelist().clear();

            if (checklistpdf == null) {
                dialog = new Dialog(TransferSignature.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message);


                TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                ImageView closeImg=dialog.findViewById(R.id.close_img);

                Message.setText("Equipment Transfered not Successfully");

                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                            Intent inspection = new Intent(TransferSignature.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                Toast.makeText(TransferSignature.this,
                        "Equipment Transfered Successfully", Toast.LENGTH_SHORT).show();
                if (SessionData.getInstance().getTransportTransfer() == 1) {
                    SessionData.getInstance().setTransportTransfer(0);
                    Intent intent = new Intent(TransferSignature.this,
                            EbsMenu.class);

                    startActivity(intent);
                } else {
                    if (SessionData.getInstance().getScanNavigation() == 0) {
//                        Intent inspection = new Intent(TransferSignature.this, ScannerActivity.class);
//                        startActivity(inspection);

                        Intent inspection = new Intent(TransferSignature.this, EbsMenu.class);
                        startActivity(inspection);
                    } else {
//                        Intent intent = new Intent(TransferSignature.this,
//                                ScannerProductActivity.class);
//                        startActivity(intent);
                        Intent inspection = new Intent(TransferSignature.this, EbsMenu.class);
                        startActivity(inspection);
                    }
                }


            }


//            if (j < cheklistArray.size() - 1) {
//
//                SessionData.getInstance().setChecklist(0);
//                SessionData.getInstance().setChecklistdata(j + 1);
//                SessionData.getInstance().setMulticheckinvalidate(0);
//
//                j++;
//                Intent hr = new Intent(General_email.this,
//                        CustomizedPartsCheck.class);
//                startActivity(hr);
//                // checkList = ReadJson.getPartsList(cheklistArray.get(j));
//                // SessionData.getInstance().setInspectionID(getrentalcheck);
//                //
//                // i = 0;
//                // initializeViews(checkList);
//                // Log.d("I IS PARSING 1", "" + checkList);
//                SessionData.getInstance().setInspectionID(getrentalcheck);
//
//            } else {
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected void onPreExecute() {
//                        ProgressBar
//                                .showCommonProgressDialog(General_email.this);
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
//                        } catch (SocketTimeoutException e) {
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
//                        if(selectedDetail.get(0).getMessage().equals("")) {
//                            selectedDetail.clear();
//                        }
//                        else{
//                            dialog = new Dialog(General_email.this);
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
//                                    Intent inspection = new Intent(General_email.this,
//                                            MainActivity.class);
//                                    startActivity(inspection);
//                                    dialog.dismiss();
//                                }
//                            });
//
//
//                            dialog.show();
//                        }
//
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
//                } catch (SocketTimeoutException e) {
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
//                // checkList.clear();
//
//				/* SessionData.getInstance().getGetKey().clear(); */
//
//                Toast.makeText(General_email.this,
//                        "Rental Inpection Over", Toast.LENGTH_SHORT).show();
//
//                // finish();
//                ProgressBar.dismiss();
//             //   new AsyncRentalDetail().execute();

//            }

            // Intent in = new
            // Intent(RentalSignature.this,ScannerActivity.class);
            // startActivity(in);

        }
    }


    private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            ProgressBar.showCommonProgressDialog(TransferSignature.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                user = WebServiceConsumer.getInstance()
                        .authenticateUserDealer(SessionData.getInstance().getTemp_Username(),
                                SessionData.getInstance().getTemp_password());
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
                    dialog = new Dialog(TransferSignature.this);
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


//                            Intent inspection = new Intent(TransferSignature.this,
//                                    MainActivity.class);
//                            startActivity(inspection);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    if (session=="AsynMails") {

                        SessionData.getInstance().setUser(user);
//                SessionData.getInstance().setUsername(objUser.getUsername());
//
//                SessionData.getInstance().setTemp_Username(objUser.getUsername());
//                SessionData.getInstance().setTemp_password(objUser.getPassword());
//                SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());

                        new AsynMails().execute();
                    }if (session=="AysncSubmitData") {


                        SessionData.getInstance().setUser(user);
//                SessionData.getInstance().setUsername(objUser.getUsername());
//
//                SessionData.getInstance().setTemp_Username(objUser.getUsername());
//                SessionData.getInstance().setTemp_password(objUser.getPassword());
//                SessionData.getInstance().setTemp_Usertoken(objUser.getUserDescription());

                        new AysncSubmitData().execute();
                    }
                }


            } else {
                ProgressBar.dismiss();

            }
        }
    }


//		 public class AysncSubmitData extends AsyncTask<Void, Void, Void> {
//
//				String resultdata;
//
//				@Override
//				protected void onPreExecute() {
//					super.onPreExecute();
//					ProgressBar.showCommonProgressDialog(RentalSignature.this);
//				}
//
//				@Override
//				protected Void doInBackground(Void... params) {
//					try {
//
//						Log.d("The Session data Size", ""
//								+ SessionData.getInstance().getDataHandlelist().size());
//						Logger.log("The Session data Size" + ""
//								+ SessionData.getInstance().getDataHandlelist().size());
//
//						Set<?> entrySet = SessionData.getInstance().getDataHandlelist()
//								.entrySet();
//
//						Iterator<?> it = entrySet.iterator();
//						Log.d("The size of IT", ""
//								+ SessionData.getInstance().getDataHandlelist()
//										.entrySet().size());
//						rentalcheckin = WebServiceConsumer.getInstance().RentalCheckin(
//								SessionData.getInstance().getRentalId(), 0,
//								SessionData.getInstance().getInspectionID(),
//								user.getUserDescription());
//						getrentalcheck = Integer.parseInt(rentalcheckin.getResult());
//						if (SessionData.getInstance().getSig() != null) {
//							str = Base64.encodeToString(SessionData.getInstance()
//									.getSig().clone(), Base64.DEFAULT);
//						}
//						checkinequpments = WebServiceConsumer
//								.getInstance()
//								.rentalCheckinEqupments(
//										Integer.parseInt(rentalcheckin.getResult()),
//										SessionData.getInstance().getSelectedDetail()
//												.get(j).getkPart(),
//										Integer.parseInt(SessionData.getInstance().getHrmeter()),
//										SessionData.getInstance().getFuel(),
//										Integer.toString(SessionData.getInstance()
//												.getEquipinspectionID()),
//										user.getUserDescription(), SessionData.getInstance().getSubstatus(),
//										SessionData.getInstance()
//										.getEqupsubstatusdes(), SessionData.getInstance().getCurrentstatus(),str,);
//					//	Log.d("Equpstatus", "" + statusselect);
//						Log.d("result data", "" + rentalcheckin.getResult());
//						// int rentalCheckinDetail;
//						while (it.hasNext()) {
//							Map.Entry me = (Map.Entry) it.next();
//
//							checkindetails = WebServiceConsumer.getInstance()
//									.rentalCheckinDetl(
//											Integer.parseInt(checkinequpments
//													.getResult()),
//											(int) me.getKey(),
//											((CheckinListData) me.getValue())
//													.getStatus(),
//
//											((CheckinListData) me.getValue())
//													.getNotes(), 0,
//											user.getUserDescription());
//							Log.d("Description for webservice", ""
//									+ ((CheckinListData) me.getValue()).getNotes());
//
//							if ((((CheckinListData) me.getValue()).getImagePath()
//									.length() > 4)) {
//								resultdata = WebServiceConsumer.getInstance()
//										.RentalChickinImages(
//												Integer.parseInt(checkindetails
//														.getResult()),
//												((CheckinListData) me.getValue())
//														.getImageName(),
//												"",
//												0,
//												((CheckinListData) me.getValue())
//														.getImagePath(), 0,
//												user.getUserDescription(),
//												SessionData.getInstance().getKpartlist().get(j)
//												);
//								Log.d("multicheckin", ""+SessionData.getInstance().getEnteredEquipID());
//
//							}
//
//						}
//						checklistpdf = WebServiceConsumer.getInstance()
//								.RentalChecklistPdf(
//										user.getUserDescription(),
//										SessionData.getInstance().getSelectedDetail()
//												.get(j).getkPart(),
//										SessionData.getInstance().getSelectedDetail()
//												.get(j).getEqupId(),
//										Integer.toString(SessionData.getInstance()
//												.getSelectedDetail().get(j)
//												.getRentalID()),str);
//
//					} catch (java.net.SocketTimeoutException e) {
//						resultdata = null;
//
//					} catch (Exception e) {
//						resultdata = null;
//						e.printStackTrace();
//					}
//					return null;
//				}
//				protected void onPostExecute(Void result) {
//					super.onPostExecute(result);
//					ProgressBar.dismiss();
//					//linearMainn.removeAllViews();
//					SessionData.getInstance().getDataHandlelist().clear();
//					if (j < cheklistArray.size() - 1) {
//
//						SessionData.getInstance().setChecklist(0);
//						SessionData.getInstance().setChecklistdata(j+1);
//
//						j++;
//						Intent hr = new Intent(RentalSignature.this,CustomizedPartsCheck.class);
//						startActivity(hr);
////						checkList = ReadJson.getPartsList(cheklistArray.get(j));
////						SessionData.getInstance().setInspectionID(getrentalcheck);
////
////						i = 0;
////						initializeViews(checkList);
////						Log.d("I IS PARSING 1", "" + checkList);
//						SessionData.getInstance().setInspectionID(getrentalcheck);
//
//					}
//					else
//					{
//						new AsyncTask<Void, Void, Void>() {
//
//							@Override
//							protected void onPreExecute() {
//								ProgressBar
//										.showCommonProgressDialog(RentalSignature.this);
//							}
//
//							@Override
//							protected Void doInBackground(Void... params) {
//								try {
//									selectedDetail = WebServiceConsumer.getInstance()
//											.getRentalDetail(
//													SessionData.getInstance()
//															.getEnteredEquipID(),
//													user.getUserDescription());
//									SessionData.getInstance().setDetail(selectedDetail);
//									Log.d("I IS PARSING 2", "" + checkList);
//								} catch (java.net.SocketTimeoutException e) {
//									selectedDetail = null;
//								} catch (Exception e) {
//									selectedDetail = null;
//									e.printStackTrace();
//								}
//								return null;
//
//							}
//
//							@Override
//							protected void onPostExecute(Void result) {
//
//								selectedDetail.clear();
//
//							}
//
//						};
//
//						try {
//							selectedDetail = WebServiceConsumer.getInstance()
//									.getRentalDetail(
//											SessionData.getInstance()
//													.getEnteredEquipID(),
//											user.getUserDescription());
//							SessionData.getInstance().setDetail(selectedDetail);
//						} catch (java.net.SocketTimeoutException e) {
//							selectedDetail = null;
//						} catch (Exception e) {
//							selectedDetail = null;
//							e.printStackTrace();
//						}
//						i = 0;
//						j = 0;
//
//						SessionData.getInstance().getSelectedDetail().clear();
//						SessionData.getInstance().getCheckListData().clear();
//						SessionData.getInstance().getDataHandlelist().clear();
//						SessionData.getInstance().getKpartlist().clear();
//						SessionData.getInstance().getHourmeterlist().clear();
//						SessionData.getInstance().getEqpStatus().clear();
//						/* SessionData.getInstance().getGetKey().clear(); */
//
//						Toast.makeText(RentalSignature.this,
//								"Rental Inpection Over", Toast.LENGTH_LONG).show();
//
//						//finish();
//						ProgressBar.dismiss();
//						new AsyncRentalDetail().execute();
//
//					}
//
////						Intent in = new Intent(RentalSignature.this,ScannerActivity.class);
////						startActivity(in);
//
//					}
//				}

    private class AsynMails extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {

            ProgressBar.showCommonProgressDialog(TransferSignature.this);
        }

        ;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                customeremails = WebServiceConsumer.getInstance().customermailsv1(
                        SessionData.getInstance().getTemp_userToken(), SessionData.getInstance().getKcustadd(),
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
                            session="AsynMails";
                            new AsyncLoginTask().execute();
                        } else {
                            dialog = new Dialog(TransferSignature.this);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.message);


                            TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
                            TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
                            Message.setText(customeremails.get(0).getMessage());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent inspection = new Intent(TransferSignature.this,
                                            MainActivity.class);
                                    startActivity(inspection);
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                        }
                    } else {
//                        SessionData.getInstance().setCustomeremails(customeremails);
                        SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(TransferSignature.this,
                                General_email.class);

                        startActivity(intent);
                        finish();
                    }
                }else {
//                        SessionData.getInstance().setCustomeremails(customeremails);
                    SessionData.getInstance().setCustomerNameMails(customeremails);

                        Intent intent = new Intent(TransferSignature.this,
                                General_email.class);

                        startActivity(intent);
                        finish();
                    }
                } else {
                    ProgressBar.dismiss();
                    AlertDialogBox.showAlertDialog(TransferSignature.this,
                            "Data is not found.");
                }
            }

        }
    }
