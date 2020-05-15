package com.ebs.rental.general;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class RentalRoot extends AppCompatActivity implements OnClickListener {
	private ActionBar actionBar;
	private ImageView settings;
	private ImageView back;
	private TextView textRentalComName;
	private TextView textRentalorderno;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		actionBar = getActionBar();

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.activity_action_bar);
		textRentalComName=(TextView) findViewById(R.id.rentalcompanyname);
		textRentalorderno=(TextView) findViewById(R.id.orderno);
		
		settings = (ImageView) findViewById(R.id.settings);
		settings.setOnClickListener(this);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {

	}

}
