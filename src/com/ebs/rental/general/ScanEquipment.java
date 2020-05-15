package com.ebs.rental.general;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



@SuppressWarnings("ALL")
public class ScanEquipment extends AppCompatActivity implements View.OnClickListener {

    private Button Scan;
    private Button Submit;
    private EditText edt_Equipment;
    private static final int ZBAR_SCANNER_REQUEST = 0;

    private ImageView imgBack;
    private TextView txtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.scan_equipment);
        Scan = (Button)findViewById(R.id.btn_scan);
        Submit = (Button)findViewById(R.id.btn_submit);
        imgBack = (ImageView)findViewById(R.id.close);
        txtBack = (TextView)findViewById(R.id.back);

        edt_Equipment = (EditText)findViewById(R.id.edt_equipmentId);

        Scan.setOnClickListener(this);
        Submit.setOnClickListener(this);

        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(Scan == v){
            if (isCameraAvailable()) {
//                Intent intent = new Intent(this, ZBarScannerActivity.class);
//                startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
            } else {
                Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
            }
        }
        else if(Submit == v){

        }
        else if(imgBack == v){
            onBackPressed();
        }
        else if(txtBack == v){
            onBackPressed();
        }

    }

    private boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case ZBAR_SCANNER_REQUEST:
////	            case ZBAR_QR_SCANNER_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    edt_Equipment.setText(data.getStringExtra(ZBarConstants.SCAN_RESULT));
////	                    Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
//                } else if(resultCode == RESULT_CANCELED && data != null) {
//                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
//                    if(!TextUtils.isEmpty(error)) {
//                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ScanEquipment.this,
                EbsMenu.class);
        startActivity(intent);

        super.onBackPressed();

    }
}
