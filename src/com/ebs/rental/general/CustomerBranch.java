package com.ebs.rental.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by techunity on 6/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class CustomerBranch extends AppCompatActivity implements View.OnClickListener {

    private Button submit;
    private ImageView imgback;
    private TextView txtback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customer_receiving_branch);
        submit = findViewById(R.id.submit);

        String[] values = new String[] { "001-NATIONAL SERVICE CENTER",
                "100-YOUR EQUIPMENT COMPANY",
                "108-NATIONAL SERVICE CENTER",

        };

        imgback = findViewById(R.id.close);
        txtback = findViewById(R.id.back);

        ListView branchSearch = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, android.R.id.text1, values);


        // Assign adapter to ListView
        branchSearch.setAdapter(adapter);

        branchSearch.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        submit.setOnClickListener(this);
        imgback.setOnClickListener(this);
        txtback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v ==submit){
            Intent inspection = new Intent(CustomerBranch.this,
                    Equip_WalkAround.class);
            startActivity(inspection);
        }


        else if(imgback==v){
            Intent inspection = new Intent(CustomerBranch.this, SelectBranch.class);
            startActivity(inspection);
        }
        else if(txtback == v){
            Intent inspection = new Intent(CustomerBranch.this, SelectBranch.class);
            startActivity(inspection);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inspection = new Intent(CustomerBranch.this, SelectBranch.class);
        startActivity(inspection);

    }
}
