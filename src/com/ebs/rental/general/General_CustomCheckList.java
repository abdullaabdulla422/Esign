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
public class General_CustomCheckList extends AppCompatActivity implements View.OnClickListener {

    private Button Submit;
    private ImageView imgBack;
    private TextView txtBack;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.general_checklist);
        Submit = findViewById(R.id.btn_submit);
        imgBack = findViewById(R.id.close);
        txtBack = findViewById(R.id.back);

        Submit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        if(Submit == v){
            Intent inspection = new Intent(General_CustomCheckList.this, General_Signature_Capture.class);
            startActivity(inspection);
        }
        else if(imgBack == v){

            Intent inspection = new Intent(General_CustomCheckList.this, General_WalkAroundInspection.class);
            startActivity(inspection);
        }
        else if(txtBack == v){
            Intent inspection = new Intent(General_CustomCheckList.this, General_WalkAroundInspection.class);
            startActivity(inspection);
        }
    }
}
