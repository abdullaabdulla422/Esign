package com.ebs.rental.general;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

@SuppressWarnings("ALL")
public class ScannerRoot extends AppCompatActivity implements OnClickListener {
	private ActionBar actionBar;
	private ImageView settings;
	

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
		actionBar.setCustomView(R.layout.scanner_bar);
		
		
		settings = (ImageView) findViewById(R.id.close);
		settings.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {

	}

}
