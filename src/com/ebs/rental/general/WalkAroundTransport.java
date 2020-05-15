package com.ebs.rental.general;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CategoryObject;
import com.ebs.rental.utils.SessionData;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class WalkAroundTransport extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera camera;
    private PaintSurface4 cameraSurfaceView;
    MyCountDownTimer myCountDownTimer;
    private SurfaceHolder surfaceHolder;
    private boolean previewing = false;
    private LinearLayout capture_image;
    private LayoutInflater controlInflater = null;

    private Thread mSplashThread;

    private ExifInterface exif;

    private Canvas CSanvas;

    private ArrayList<CategoryObject> categoryObjects;

    private byte[] rawImage ;

    private ArrayList<byte[]> attachedFilesData;


    private final ArrayList<String> arrayNotes1 = new ArrayList<>();
    private final ArrayList<String> arrayType1= new ArrayList<>();
    private final ArrayList<String> arrayCategoryID1 = new ArrayList<>();
    private int SPEECH_CODE_ONE = 101;

    private ArrayList<byte[]> AddtoSession= new ArrayList<>();

    private ArrayList<String> arrayNotes = new ArrayList<>();
    private ArrayList<String> arrayType = new ArrayList<>();
    private ArrayList<String> arrayCategoryID = new ArrayList<>();

    private int capture = 0;


    private TextView buttonTakePicture;
    private Paint drawingPaint;
    private static final String IMAGE_DIRECTORY_NAME = "camera_touch/Camera";
    //TextView prompt;
    private File mediaFile;
    private File medialatlongfile;
    private Gallery gallery;
    private ImageView close_popup;
    private ImageView image_show;
    private Bitmap bitmap;
    private static int SPLASH_TIME_OUT = 2000;
    private float MaxHight;
    private float MaxWidth;
    private float TouchHight;
    private float TouchWidth;
    private float x;
    private float y;

    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtEquipID;
    private DrawingView drawingView;
    private Face[] detectedFaces;
    private Dialog Image_dialog;
    private boolean haveFace;
    private int i=0;

    private final ArrayList<Integer> in = new ArrayList<>();

    private Button btnSave;
    private Button btnDelete;

    private Spinner SelectType;
    private EditText notes;

    final int RESULT_SAVEIMAGE = 0;
    private Handler handler;
    private ScheduledExecutorService myScheduledExecutorService;

    private GoogleApiClient client;
    private String selectedId;
    private Button btn_continue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_touch4);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            String[] perms = {"android.permission.CAMERA"};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);        }

        gallery = (Gallery) findViewById(R.id.Image_gallery);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        attachedFilesData = new ArrayList<byte[]>();
        AddtoSession = SessionData.getInstance().getWalkaroundgeneralimages();
        arrayNotes = SessionData.getInstance().getWalkAroundNotes();
        arrayType = SessionData.getInstance().getWalkAroundType();
        arrayCategoryID = SessionData.getInstance().getWalkAroundCategoryId();
        getWindow().setFormat(PixelFormat.UNKNOWN);
        handler = new Handler();
        cameraSurfaceView = (PaintSurface4) findViewById(R.id.surfaceView);

        buttonTakePicture = (TextView) findViewById(R.id.capture);

        capture_image = (LinearLayout) findViewById(R.id.Capture_image);

        txtLatitude = (TextView) findViewById(R.id.lat);
        txtLongitude = (TextView) findViewById(R.id.longi);
        txtEquipID = (TextView) findViewById(R.id.equipmentId);

        txtEquipID.setText(SessionData.getInstance().getWalkAroundEquipmentID());

        categoryObjects = SessionData.getInstance().getCategoryObjects();

        txtLatitude.setText("" + SessionData.getInstance().getLatitude());
        txtLongitude.setText("" + SessionData.getInstance().getLongitude());




        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View v, final int position, long id)
            {
                ImagePopup(position);
            }
        });

        gallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final Dialog dialog_delete_image = new Dialog(WalkAroundTransport.this);
                dialog_delete_image.setCancelable(false);
                dialog_delete_image.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_delete_image.setContentView(R.layout.dialog_delete_image);
                dialog_delete_image.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                dialog_delete_image.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

                Button gallery_ok = (Button) dialog_delete_image.findViewById(R.id.gallery_ok);
                Button gallery_cancel = (Button) dialog_delete_image.findViewById(R.id.gallery_cancel);


                gallery_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_delete_image.dismiss();
                    }
                });


                gallery_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AddtoSession.remove(attachedFilesData.get(position));
                        arrayType.remove(arrayType1.get(position));
                        arrayNotes.remove(arrayNotes1.get(position));
                        arrayCategoryID.remove(arrayCategoryID1.get(position));

                        SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                        SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                        SessionData.getInstance().setWalkAroundCategoryId(arrayCategoryID);
                        SessionData.getInstance().setWalkAroundType(arrayType);

                        attachedFilesData.remove(position);
                        arrayNotes1.remove(position);
                        arrayCategoryID1.remove(position);
                        arrayType1.remove(position);


                        gallery.setAdapter(new WalkAroundTransport.ImageAdapter(WalkAroundTransport.this));

                        Image_dialog.dismiss();

                        dialog_delete_image.dismiss();
                    }
                });




                dialog_delete_image.show();


                return false;
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        MaxWidth = size.x;
        MaxHight = size.y;

        Log.d("Max","width"+MaxWidth);
        Log.d("max","hight"+MaxHight);




        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                camera.takePicture(myShutterCallback,
                        myPictureCallback_RAW, myPictureCallback_JPG);
            }
        });

        surfaceHolder = cameraSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



        cameraSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TouchWidth = event.getX();
                TouchHight = event.getY();
                Log.d("Touch Pointxxx X","width "+TouchWidth);
                Log.d("Touch Pointyyy Y","Height "+TouchHight);

                Log.d("Touch MaxWidth X","width "+MaxWidth);
                Log.d("Touch MaxHight Y","Height" +MaxHight);

                if(MaxWidth>MaxHight){

//                    y = (TouchHight / MaxWidth ) * 1920;
//                    x = (TouchWidth / MaxHight ) * 1080;

                    x = (TouchWidth * 100)/MaxWidth;
//                    Log.d("Touch_X"," % " +x);
                    x = (x /100) * 1920;
//                    Log.d("Touch_X% = "," = " +x);

                    y = (TouchHight * 100)/MaxHight;
                    Log.d("Touch_Y"," % " +y);
                    y = (y /100) * 1080;


                    int temp_height = (int) (MaxHight / 2);  Log.d("Touch_temp_height"," % " +temp_height);
                    float temp = (0.2f * y);   Log.d("Touch_temp"," % " +temp);


                    if (temp_height < temp)
                    {
                        y = temp + y ;
                    }
//                    else
//                    {
//                        y =  y - temp  ;
//                    }


                    Log.d("Touch_Y% = "," = " +y);

                }else{

                    x = (TouchWidth / MaxWidth ) * 1080;
                    y = (TouchHight / MaxHight ) * 1920;
                    y = y + 70;
                }

                int pointerCount = event.getPointerCount();
                int action=event.getAction() & MotionEvent.ACTION_MASK;

                Log.d("pointerCount",""+pointerCount);
                Log.d("action",""+action);

                if(capture==0){
                    if (myShutterCallback != null && myPictureCallback_RAW != null && myPictureCallback_JPG != null && camera != null) {

                        camera.takePicture(myShutterCallback,
                                myPictureCallback_RAW, myPictureCallback_JPG);
                        capture = 1;
                    }
                }


                return false;
            }
        });

        drawingView = new DrawingView(this);

        LayoutParams layoutParamsDrawing
                = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        this.addContentView(drawingView, layoutParamsDrawing);

        controlInflater = LayoutInflater.from(getBaseContext());
        // View viewControl = controlInflater.inflate(R.layout.control, null);
//        LayoutParams layoutParamsControl
//                = new LayoutParams(LayoutParams.FILL_PARENT,
//                LayoutParams.FILL_PARENT);
//        this.addContentView(viewControl, layoutParamsControl);

//        buttonTakePicture = (Button)findViewById(R.id.takepicture);
//        buttonTakePicture.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//            public void onClick(View arg0) {
//                //
//                camera.takePicture(myShutterCallback,
//                        myPictureCallback_RAW, myPictureCallback_JPG);
//            }});

        /*
        LinearLayout layoutBackground = (LinearLayout)findViewById(R.id.background);
        layoutBackground.setOnClickListener(new LinearLayout.OnClickListener(){

   @Override
   public void onClick(View arg0) {
    //

    buttonTakePicture.setEnabled(false);
    camera.autoFocus(myAutoFocusCallback);
   }});
  */

        // prompt = (TextView)findViewById(R.id.prompt);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public void ImagePopup(final int position)
    {
        Image_dialog = new Dialog(WalkAroundTransport.this);
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
        Image_dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT);

        image_show = (ImageView) Image_dialog.findViewById(R.id.image_show);
        close_popup = (ImageView) Image_dialog.findViewById(R.id.close_btn);
        btnSave = (Button)Image_dialog.findViewById(R.id.btn_save);
        btnDelete = (Button)Image_dialog.findViewById(R.id.btn_delete);
        SelectType = (Spinner)Image_dialog.findViewById(R.id.select_type);
        notes = (EditText)Image_dialog.findViewById(R.id.notes);
        btn_continue = (Button)Image_dialog.findViewById(R.id.btn_continue);

        notes.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ImageView img_notes = (ImageView)Image_dialog.findViewById(R.id.img_note);


        img_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speech.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.speech_prompt));

                try {
                    startActivityForResult(speech, SPEECH_CODE_ONE);
                } catch (ActivityNotFoundException a) {

                    Toast.makeText(WalkAroundTransport.this, "Sorry! Your device doesn\\'t support speech input " , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str;
                str = notes.getText().toString();
                String type = SelectType.getSelectedItem().toString();

                arrayType1.add(position,type);
                arrayNotes1.add(position,str);
                arrayCategoryID1.add(position,selectedId);
                Log.d("Selected Item",""+SelectType.getSelectedItem().toString());
                arrayType.add(arrayType1.get(position));
                arrayNotes.add(arrayNotes1.get(position));
                arrayCategoryID.add(arrayCategoryID1.get(position));
                SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                SessionData.getInstance().setWalkAroundCategoryId(arrayCategoryID);
                AddtoSession.add(attachedFilesData.get(position));
                SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                SessionData.getInstance().setWalkAroundType(arrayType);
                in.add(position,1);
//                        attachedFilesData.remove(position);
//                        gallery.setAdapter(new ImageAdapter(WalkAroundCamera.this));
                Image_dialog.dismiss();
                finish();
                Intent inspection = new Intent(WalkAroundTransport.this, CustomizedTransportPartsCheck.class);
                startActivity(inspection);

            }
        });

        List<String> list = new ArrayList<String>();
//                list.add("Select Type");
//                list.add("scratch");
//                list.add("dent");
//                list.add("broken");
//                list.add("Damage");
        final List<String> categoryId = new ArrayList<String>();
        if(categoryObjects.size()!=0){
            for(int i = 0; i<categoryObjects.size();i++){

                list.add(categoryObjects.get(i).getCategory());
                categoryId.add(categoryObjects.get(i).getId());
            }
        }



//                list = SessionData.getInstance().getCategoryObjects().
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(WalkAroundTransport.this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectType.setAdapter(dataAdapter);


        SelectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedId = categoryId.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(in.get(position)==1){
            btnSave.setVisibility(View.GONE);

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(notes.getText().toString().trim().length()==0){
                    Toast.makeText(WalkAroundTransport.this,"Enter Notes",Toast.LENGTH_LONG).show();
                }else{
                    String str;
                    str = notes.getText().toString();
                    String type = SelectType.getSelectedItem().toString();

                    arrayType1.add(position,type);
                    arrayNotes1.add(position,str);
                    arrayCategoryID1.add(position,selectedId);
                    Log.d("Selected Item",""+SelectType.getSelectedItem().toString());
                    arrayType.add(arrayType1.get(position));
                    arrayNotes.add(arrayNotes1.get(position));
                    arrayCategoryID.add(arrayCategoryID1.get(position));
                    SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                    SessionData.getInstance().setWalkAroundCategoryId(arrayCategoryID);
                    AddtoSession.add(attachedFilesData.get(position));
                    SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                    SessionData.getInstance().setWalkAroundType(arrayType);
                    in.add(position,1);
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
                arrayType.remove(arrayType1.get(position));
                arrayNotes.remove(arrayNotes1.get(position));
                arrayCategoryID.remove(arrayCategoryID1.get(position));

                SessionData.getInstance().setWalkaroundgeneralimages(AddtoSession);
                SessionData.getInstance().setWalkAroundNotes(arrayNotes);
                SessionData.getInstance().setWalkAroundCategoryId(arrayCategoryID);
                SessionData.getInstance().setWalkAroundType(arrayType);

                attachedFilesData.remove(position);
                arrayNotes1.remove(position);
                arrayCategoryID1.remove(position);
                arrayType1.remove(position);


                gallery.setAdapter(new ImageAdapter(WalkAroundTransport.this));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SPEECH_CODE_ONE) {
            if (resultCode == RESULT_OK && data != null) {
                String str = notes.getText().toString();
                ArrayList<String> value = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                notes.setText(str + " " + value.get(0));

            }

        }

    }

    public void touchFocus(Rect tfocusRect) {

//        buttonTakePicture.setEnabled(false);

        /* camera.stopFaceDetection();*/

        //Convert from View's width and height to +/- 1000

        //cameraSurfaceView.setClickable(false);

        Rect targetFocusRect = new Rect(
                tfocusRect.left * 2000 / drawingView.getWidth() ,
                tfocusRect.top * 2000 / drawingView.getHeight() ,
                tfocusRect.right * 2000 / drawingView.getWidth() ,
                tfocusRect.bottom * 2000 / drawingView.getHeight() );

        List<Camera.Area> focusList = new ArrayList<Camera.Area>();
        Camera.Area focusArea = new Camera.Area(targetFocusRect, 100);
        focusList.add(focusArea);

        Parameters para = camera.getParameters();
        para.setFocusAreas(focusList);
        para.setMeteringAreas(focusList);
//        camera.setParameters(para);


        drawingView.setHaveTouch(true, tfocusRect);
        drawingView.invalidate();

        try{
            camera.autoFocus(myAutoFocusCallback);
        }catch (Exception e){
            Log.d("exception",e.toString(), e);
        }



        i=0;

//        camera.takePicture(myShutterCallback,
//                myPictureCallback_RAW, myPictureCallback_JPG);



    }

    private final FaceDetectionListener faceDetectionListener
            = new FaceDetectionListener() {

        @Override
        public void onFaceDetection(Face[] faces, Camera tcamera) {

            if (faces.length == 0) {
                //prompt.setText(" No Face Detected! ");
                drawingView.setHaveFace(false);
            } else {
                //prompt.setText(String.valueOf(faces.length) + " Face Detected :) ");
                drawingView.setHaveFace(true);
                detectedFaces = faces;

                //Set the FocusAreas using the first detected face
                List<Camera.Area> focusList = new ArrayList<Camera.Area>();
                Camera.Area firstFace = new Camera.Area(faces[0].rect, 100);
                focusList.add(firstFace);

                Parameters para = camera.getParameters();

                if (para.getMaxNumFocusAreas() > 0) {
                    para.setFocusAreas(focusList);
                }

                if (para.getMaxNumMeteringAreas() > 0) {
                    para.setMeteringAreas(focusList);
                }

                camera.setParameters(para);

                // buttonTakePicture.setEnabled(false);

                //Stop further Face Detection
                camera.stopFaceDetection();

                //   buttonTakePicture.setEnabled(false);

    /*
     * Allways throw java.lang.RuntimeException: autoFocus failed
     * if I call autoFocus(myAutoFocusCallback) here!
     *
     camera.autoFocus(myAutoFocusCallback);
    */

                //Delay call autoFocus(myAutoFocusCallback)
                myScheduledExecutorService = Executors.newScheduledThreadPool(1);
                myScheduledExecutorService.schedule(new Runnable() {
                    public void run() {
                        camera.autoFocus(myAutoFocusCallback);
                    }
                }, 500, TimeUnit.MILLISECONDS);

            }

            drawingView.invalidate();

        }
    };

    File mediaStorageDir = new File(
            Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            IMAGE_DIRECTORY_NAME);

    private final AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            // TODO Auto-generated method stub
            if (arg0) {
                //  buttonTakePicture.setEnabled(true);
                camera.cancelAutoFocus();
            }

            float focusDistances[] = new float[3];
            arg1.getParameters().getFocusDistances(focusDistances);
//            prompt.setText("Optimal Focus Distance(meters): "
//                    + focusDistances[Camera.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX]);

        }
    };

    private final ShutterCallback myShutterCallback = new ShutterCallback() {

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }
    };

    private final PictureCallback myPictureCallback_RAW = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub

        }
    };

    private final PictureCallback myPictureCallback_JPG = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
   /*Bitmap bitmapPicture
    = BitmapFactory.decodeByteArray(arg0, 0, arg0.length); */


            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

//           Uri uriTarget = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    IMAGE_DIRECTORY_NAME);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return;
                }
            }

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp );

            medialatlongfile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG__" + timeStamp);

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(mediaFile);
                fos.write(arg0);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            rawImage = arg0;

            CanvasImage(arg0);

            camera.startPreview();

            cameraSurfaceView.setClickable(true);

            // cameraSurfaceView.setEnabled(true);
            /*      camera. ();*/
        }
    };

    private void CanvasImage(byte[] bytes)  {



        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;


//             bitmap = BitmapFactory.decodeByteArray(data , 0, data.length,options);

            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


//            bitmap = BitmapFactory.decodeFile(mediaFile.getPath(), options);

            Log.d("File name", "" + mediaFile.getPath());

            exif = new ExifInterface(mediaFile.getPath());
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, String.valueOf(1));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, String.valueOf(2));

            // exif.saveAttributes();

//            int height = camera.getParameters().getPreviewSize().height;
//            int width = camera.getParameters().getPreviewSize().width;
//            int bitsPerPixel = ImageFormat.getBitsPerPixel(camera.getParameters().getPreviewFormat());
//
//            byte[] buffer = new byte[width * height * bitsPerPixel];
//            buffer = rawImage;
//            bitmap = BitmapFactory.decodeByteArray(buffer , 0, buffer.length,options);

            bitmap = Bitmap.createScaledBitmap(bitmap, 1920, 1080, true);
            int rotationAngle = 90;

            Matrix matrix = new Matrix();

            matrix.setRotate(rotationAngle, (float) 1080, (float) 1920);

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 1920, 1080, matrix, true);

//            getGps();
            bitmap = rotatedBitmap;
            String Lat = "Latitude : " + SessionData.getInstance().getLatitude();
            String Long = "Longitude : " + SessionData.getInstance().getLongitude();
//
//            String lati = String.valueOf(Lat);
//            String longi = String.valueOf(Long);

            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas newCanvas = new Canvas(mutableBitmap);

            newCanvas.drawBitmap(mutableBitmap, 0.0f, 0.0f, null);


//           y = y ;
            Log.d("Touch draw X",""+x);
            Log.d("Touch draw Y",""+y);

            if(i==0) {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(4);
                paint.setColor(Color.RED);
                RectF oval1 = new RectF(x - 75, y - 75, x + 75, y + 75);
//                newCanvas.drawOval(oval1, paint);
//                newCanvas.drawCircle(x + 290, y+240, 50, paint);

                newCanvas.drawCircle(x , y+50, 150, paint);
            }
            // newCanvas.drawColor(Color.WHITE);
            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setStyle(Paint.Style.STROKE);
            paintText.setColor(Color.WHITE);
            Path mPath = new Path();

            RectF mRectF = new RectF(0, 50, bitmap.getWidth(), 0);
            mPath.addRect(mRectF, Path.Direction.CCW);
            paintText.setStrokeWidth(50);
            paintText.setStyle(Paint.Style.STROKE);
//            newCanvas.drawPath(mPath, paintText);
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

            // File mediaStorageDir = new File(
            // Environment
            // .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            // IMAGE_DIRECTORY_NAME);

            // Convert bitmap to byte array



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytearrays = stream.toByteArray();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(medialatlongfile);
                fos.write(bytearrays);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //
//            InputStream is = new ByteArrayInputStream(bytearrays);
//
//            TiffOutputSet outputSet = null;
//
//            IImageMetadata metadata = Sanselan.getMetadata(new File(mediaFile
//                    .getPath()));
//            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
//            if (null != jpegMetadata) {
//                TiffImageMetadata exif = jpegMetadata.getExif();
//                if (null != exif) {
//                    outputSet = exif.getOutputSet();
//                }
//            }
//            if (null != outputSet) {
//                stream.flush();
//                stream.close();
//                stream = new ByteArrayOutputStream();
//                ExifRewriter ER = new ExifRewriter();
//                ER.updateExifMetadataLossless(bytearrays, stream, outputSet);
//                bytearrays = stream.toByteArray();
//
//            }

            // mediaFile = new File(mediaFile.getPath());
            // exif = new ExifInterface(mediaFile.getPath());
            //
            // exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
            // String.valueOf(latitude));
            // exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
            // String.valueOf(longitude));
            // exif
            // .getAttribute(ExifInterface.TAG_ORIENTATION);
            // exif
            // .getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            // exif
            // .getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            //
            // Log.d("lat:long", "" + latitude+":"+longitude);
            // exif.saveAttributes();
            // SessionData.getInstance().setImageData(bytearrays);



            in.add(0);
            attachedFilesData.add(bytearrays);
            arrayType1.add("");
            arrayNotes1.add("");
            arrayCategoryID1.add("");
            capture=0;
            ImagePopup(attachedFilesData.size() - 1);

            gallery.setAdapter(new ImageAdapter(this));

        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Toast.makeText(this, ioe.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "General ex : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if (previewing) {
            camera.stopFaceDetection();
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null) {

            Parameters parameters = camera.getParameters();

            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            Camera.Size size = sizes.get(0);
            for(int i=0;i<sizes.size();i++)
            {
                if(sizes.get(i).width > size.width)
                    size = sizes.get(i);
            }
            parameters.setPictureSize(size.width, size.height);
            camera.setParameters(parameters);


            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                camera.setDisplayOrientation(90);

//                prompt.setText(String.valueOf(
//                        "Max Face: " + camera.getParameters().getMaxNumDetectedFaces()));
//                camera.startFaceDetection();
                previewing = true;
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open();
        camera.setFaceDetectionListener(faceDetectionListener);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //   camera.stopFaceDetection();
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }



    private class DrawingView extends View {




        boolean haveTouch;
        Rect touchArea;

        public DrawingView(Context context) {
            super(context);
            haveFace = false;
            drawingPaint = new Paint();
            drawingPaint.setColor(Color.RED);
            drawingPaint.setStyle(Paint.Style.STROKE);
            drawingPaint.setStrokeWidth(8);


            haveTouch = false;
        }

        public void setHaveFace(boolean h) {
            haveFace = h;
        }

        public void setHaveTouch(boolean t, Rect tArea) {
            haveTouch = t;
            touchArea = tArea;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onDraw(final Canvas canvas) {
            // TODO Auto-generated method stub
    /*        if (haveFace) {

                // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
                // UI coordinates range from (0, 0) to (width, height).

                int vWidth = getWidth();
                int vHeight = getHeight();

                for (int i = 0; i < detectedFaces.length; i++) {

                    if (i == 0) {
                        drawingPaint.setColor(Color.GREEN);
                    } else {
                        drawingPaint.setColor(Color.RED);
                    }

                    RectF oval1 = new RectF(TouchWidth, TouchHight, TouchWidth+25 ,TouchHight+25);
                    canvas.drawOval(oval1, drawingPaint);


//
//                    int l = detectedFaces[i].rect.left;
//                    int t = detectedFaces[i].rect.top;
//                    int r = detectedFaces[i].rect.right;
//                    int b = detectedFaces[i].rect.bottom;
//                    int left = (l + 100) * vWidth / 10;
//                    int top = (t + 100) * vHeight / 10;
//                    int right = (r + 100) * vWidth / 10;
//                    int bottom = (b + 100) * vHeight / 10;
//                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                    {
//// Do something for Lollipop and above versions
//                        canvas.drawOval(left, top, right, bottom, drawingPaint);
//                    } else{
//// do something for phones running an SDK before Lollipop
//                        RectF mRectF = new RectF(left,top,right,bottom);
//                        canvas.drawOval(mRectF, drawingPaint);
//                    }
                    haveTouch = true;
                }
            } else {
                canvas.drawColor(Color.TRANSPARENT);
            }
*/
            if (haveTouch) {

                setLayerType(LAYER_TYPE_HARDWARE, null);
                drawingPaint.setColor(Color.RED);

                RectF oval1 = new RectF(TouchWidth-(MaxWidth/10), TouchHight-(MaxWidth/10), TouchWidth+(MaxWidth/10) ,TouchHight+(MaxWidth/10));
                canvas.drawOval(oval1, drawingPaint);

                CSanvas = canvas;
//                canvas.drawOval(touchArea.left, touchArea.top, touchArea.right, touchArea.bottom,
//                        drawingPaint);
            /*    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
// Do something for Lollipop and above versions
                    canvas.drawOval(touchArea.left, touchArea.top, touchArea.right, touchArea.bottom,
                        drawingPaint);
                } else{
// do something for phones running an SDK before Lollipop
                    RectF mRectF = new RectF(touchArea.left, touchArea.top, touchArea.right, touchArea.bottom);
                    canvas.drawOval(mRectF, drawingPaint);
                }*/
//                drawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));


                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);

                haveTouch = false;

                if(!haveTouch){

//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            drawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//                            //haveTouch = true;
////
//                        }
//                    }, 1500);

//                    myCountDownTimer = new MyCountDownTimer(1000, 1000);
//                    myCountDownTimer.start();

                    mSplashThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(300);


                            } catch (InterruptedException ex) {
                            }


                            setWillNotDraw(false);
                            drawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                        }
                    }; mSplashThread.start();


                }







            }

        }

    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {


        }
        @Override
        public void onFinish() {
            //   drawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
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
            final View view = (View) inflator.inflate(R.layout.sub_image_view, null);
            ImageView  imageView = (ImageView) view.findViewById(R.id.img_camera);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            haveFace = false;
            byte[] data = attachedFilesData.get(position);
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                    data.length, options);

            imageView.setImageBitmap(bmp);


            imageView.setBackgroundResource(itemBackground);
            return view;

        }
    }

}