package com.ebs.rental.general;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class RentalFoot extends AppCompatActivity implements OnClickListener{
	private ActionBar actionBar;
	private Button back;
	private Button submit;
	private Button next;
	private ImageView settings;
	private ImageView imgback;
	private TextView textRentalComName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		actionBar = getActionBar();

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.footer);
		back = (Button) findViewById(R.id.btnbackpress);
		back.setOnClickListener(this);
		submit = (Button) findViewById(R.id.btnsubmitpress);
		submit.setOnClickListener(this);
		next = (Button) findViewById(R.id.btnnextpress);
		next.setOnClickListener(this);
textRentalComName=(TextView) findViewById(R.id.rentalcompanyname);
		
		settings = (ImageView) findViewById(R.id.settings);
		settings.setOnClickListener(this);
		imgback = (ImageView) findViewById(R.id.back);
		imgback.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
