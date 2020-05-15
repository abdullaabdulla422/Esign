package com.ebs.rental.general;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ebs.rental.TabFragments.InspectionFragment;
import com.ebs.rental.utils.SessionData;

import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class SimpleScannerActivity extends BaseScannerActivity implements ZBarScannerView.ResultHandler, ZBarConstants {
    private ZBarScannerView mScannerView;
    ImageView cancel;
    //  private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner);
        setupToolbar();

        if (!isCameraAvailable()) {
            // Cancel request if there is no rear-facing camera.
            cancelRequest();
            return;
        }


        ViewGroup contentFrame = findViewById(R.id.camera_preview);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);

        cancel = findViewById(R.id.cancel);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        cancel.startAnimation(animation2);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public void cancelRequest() {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(ERROR_INFO, "Camera unavailable");
        setResult(Activity.RESULT_CANCELED, dataIntent);
        finish();
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void handleResult(final Result rawResult) {
//        Toast.makeText(this, "Contents = " + rawResult.getContents() +
//                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(SimpleScannerActivity.this);

                if (SessionData.getInstance().getZbarScan() == 1) {
                    Intent intent = new Intent(SimpleScannerActivity.this, EbsMenu.class);
                    List<String> result = Arrays.asList(rawResult.getContents().split(","));
                    if (result.size() == 1) {
                        intent.putExtra("value", rawResult.getContents());
                    } else {
                        intent.putExtra("value", result.get(0));
                    }

                    SessionData.getInstance().setScanFlag(1);

                    startActivity(intent);
                    finish();
                } else if (SessionData.getInstance().getZbarScan() == 2) {
                    Intent intent = new Intent(SimpleScannerActivity.this, RentalOrderSearch.class);
                    List<String> result = Arrays.asList(rawResult.getContents().split(","));
                    if (result.size() == 1) {
                        intent.putExtra("value", rawResult.getContents());
                    } else {
                        intent.putExtra("value", result.get(0));
                    }

                    startActivity(intent);
                    finish();
                } else if (SessionData.getInstance().getZbarScan() == 3) {
                    Intent intent = new Intent(SimpleScannerActivity.this, EbsMenu.class);
                    List<String> result = Arrays.asList(rawResult.getContents().split(","));
                    if (result.size() == 1) {
                        intent.putExtra("value", rawResult.getContents());
                    } else {
                        intent.putExtra("value", result.get(0));
                    }
                    startActivity(intent);
                    finish();
                }
//                if (Sessiondata.getInstance().getScanner_partreceipt() == 1){
//                    Intent intent = new Intent(SimpleScannerActivity.this,PartReceiptActivity.class);
//                    intent.putExtra("value", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }else if (Sessiondata.getInstance().getScanner_partreceiving() == 2){
//                    Intent intent = new Intent(SimpleScannerActivity.this,PartsReceivingDetailsActivity.class);
//                    intent.putExtra("value", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }else if (Sessiondata.getInstance().getScanner_replace() == 3){
//                    Intent intent = new Intent(SimpleScannerActivity.this,ReplacePartsActivity.class);
//                    intent.putExtra("value", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }else if (Sessiondata.getInstance().getScanner_counting1() == 4){
//                    Intent intent = new Intent(SimpleScannerActivity.this,Physical_counting_activity.class);
//                    intent.putExtra("value1", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }else if (Sessiondata.getInstance().getScanner_counting2() == 5){
//                    Intent intent = new Intent(SimpleScannerActivity.this,Physical_counting_activity.class);
//                    intent.putExtra("value2", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }else if (Sessiondata.getInstance().getScanner_inventory() == 6){
//                    Intent intent = new Intent(SimpleScannerActivity.this,equipment_inventory_activity.class);
//                    intent.putExtra("value", rawResult.getContents());
//                    startActivity(intent);
//                    finish();
//                }

            }
        }, 500);
    }


}
