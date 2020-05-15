package com.ebs.rental.general;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class CustomizedOperationcheck extends AppCompatActivity {

	private LinearLayout linearMain;
	CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_customized_operationcheck);

		linearMain = (LinearLayout) findViewById(R.id.linearMain1);

		
		LinkedHashMap<String, String> alphabet = new LinkedHashMap<String, String>();
		
	}

	private TableRow addRow(String s) {
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT));
		TableRow.LayoutParams tlparams = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		tlparams.setMargins(10, 10, 0, 10);
		TextView textView = new TextView(this);
		textView.setLayoutParams(tlparams);
		textView.setText(s);
		tr.addView(textView);
		TableRow.LayoutParams blparams = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);

		ArrayList<String> spinnerArray = new ArrayList<String>();
		spinnerArray.add("GOOD");
		spinnerArray.add("N/A");
		spinnerArray.add("REPAIR");
		Spinner spinner = new Spinner(this);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				spinnerArray);
		spinner.setAdapter(spinnerArrayAdapter);

		tr.addView(spinner);
		return tr;
	}
}
