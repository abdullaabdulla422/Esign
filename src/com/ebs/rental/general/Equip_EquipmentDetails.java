package com.ebs.rental.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by techunity on 5/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Equip_EquipmentDetails extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.general_inspection_equip_details);

        btnSubmit = findViewById(R.id.btn_next);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btnSubmit == v){
            Intent inspection = new Intent(Equip_EquipmentDetails.this, Equip_WalkAround.class);
            startActivity(inspection);
        }

    }
}
