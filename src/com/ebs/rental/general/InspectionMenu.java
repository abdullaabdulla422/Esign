package com.ebs.rental.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by techunity on 5/10/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public class InspectionMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btn_GeneralInspection;
    private Button btn_RentalInspection;
    private TextView txtBack;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.inspection_menu);

        btn_GeneralInspection = findViewById(R.id.rental_inspection);
        btn_RentalInspection = findViewById(R.id.general_inspection);

        txtBack = findViewById(R.id.back);
        imgBack = findViewById(R.id.backicon);
        btn_RentalInspection.setOnClickListener(this);
        btn_GeneralInspection.setOnClickListener(this);

        txtBack.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (btn_GeneralInspection == v) {
//            Intent inspection = new Intent(InspectionMenu.this, ScannerActivity.class);
//            startActivity(inspection);
            Intent inspection = new Intent(InspectionMenu.this, EbsMenu.class);
            startActivity(inspection);
        } else if (btn_RentalInspection == v) {
//            Intent inspection = new Intent(InspectionMenu.this, ScannerActivity.class);
//            startActivity(inspection);
            Intent inspection = new Intent(InspectionMenu.this, EbsMenu.class);
            startActivity(inspection);    } else if (txtBack == v) {
            onBackPressed();
        } else if (imgBack == v) {
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {

        Intent inspection = new Intent(InspectionMenu.this, EbsMenu.class);
        startActivity(inspection);
        finish();

    }
}
