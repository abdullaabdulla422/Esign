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
@SuppressWarnings("ALL")
public class EquipmentDetails extends AppCompatActivity implements View.OnClickListener {


    private Button Btnnext;
    private TextView txtBack;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.general_inspection_equip_details);

        Btnnext = (Button) findViewById(R.id.btn_next);
        imgBack = (ImageView) findViewById(R.id.close);
        txtBack = (TextView) findViewById(R.id.back);

        Btnnext.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (Btnnext == v) {
            Intent inspection = new Intent(EquipmentDetails.this, General_WalkAroundInspection.class);
            startActivity(inspection);

        } else if (imgBack == v) {

            Intent inspection = new Intent(EquipmentDetails.this, EbsMenu.class);
            startActivity(inspection);
        } else if (txtBack == v) {
            Intent inspection = new Intent(EquipmentDetails.this, EbsMenu.class);
            startActivity(inspection);
        }

    }
}
