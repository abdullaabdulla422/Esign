package com.ebs.rental.general;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ebs.rental.utils.RootActivity;

@SuppressWarnings("ALL")
public class OperationalInspection extends RootActivity implements
		OnClickListener {

	/**
	 * {@link Field}
	 */
	@SuppressLint("StaticFieldLeak")
	private static TextView title;
	private ImageView back;
	private ImageView home;
	String visualInspectionId;
	private EditText representative;
	private EditText descrepencies;
	private Button next;
	private Spinner engine;
	private Spinner brake;
	private Spinner controls;
	private Spinner lights;
	private Spinner seatBelts;
	private Spinner drive;
	private Spinner lift;
	private Spinner cab;
	int engineOperation = 1, brakeOperation = 1, controlsOperation = 1,
			lightsOperation = 1, seatBeltsOperation = 1, driveOperation = 1,
	 		liftOperation = 1, cabOperation = 1;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_operational_inspection);
		title = (TextView) findViewById(R.id.title_text);
		title.setText("Operational Inspect");
		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);

		home = (ImageView) findViewById(R.id.img_next);
		next = (Button) findViewById(R.id.btn_oper_next);
		engine = (Spinner) findViewById(R.id.spin_engine);
		brake = (Spinner) findViewById(R.id.spin_brake);
		controls = (Spinner) findViewById(R.id.spin_controls);
		lights = (Spinner) findViewById(R.id.spin_lights);

		seatBelts = (Spinner) findViewById(R.id.spin_seatbelt);
		drive = (Spinner) findViewById(R.id.spin_drive);
		lift = (Spinner) findViewById(R.id.spin_lift);
		cab = (Spinner) findViewById(R.id.spin_cab);

		representative = (EditText) findViewById(R.id.edt_hugg);
		descrepencies = (EditText) findViewById(R.id.edt_descrepencies);
		home.setOnClickListener(this);
		next.setOnClickListener(this);
		back.setVisibility(View.VISIBLE);
		home.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			onBackPressed();
		}
		if (v == home) {
			finish();
			Intent intent1 = new Intent(OperationalInspection.this,
					CustomerListActivity.class);
			startActivity(intent1);
		}
		if (v == next) {
			finish();
			startActivity(new Intent(OperationalInspection.this,
					VisualInspection.class));
			InspectionActivity.checkIn.setDescrepencies(descrepencies.getText()
					.toString());
			InspectionActivity.checkIn.setRepresentative(representative
					.getText().toString());
			InspectionActivity.checkIn.setEngineOperation(getType(engine
					.getSelectedItem().toString()));
			InspectionActivity.checkIn.setBrake(getType(brake.getSelectedItem()
					.toString()));
			InspectionActivity.checkIn.setControls(getType(controls
					.getSelectedItem().toString()));

			InspectionActivity.checkIn.setLights(getType(lights
					.getSelectedItem().toString()));
			InspectionActivity.checkIn.setSeatBelt(getType(seatBelts
					.getSelectedItem().toString()));
			InspectionActivity.checkIn.setDrive(getType(drive.getSelectedItem()
					.toString()));
			InspectionActivity.checkIn.setLift(getType(lift.getSelectedItem()
					.toString()));
			InspectionActivity.checkIn.setCabOperation(getType(cab
					.getSelectedItem().toString()));
		}

	}

	private int getType(String selectedItem) {
		if (selectedItem.equalsIgnoreCase("Good")) {
			return 1;
		} else if (selectedItem.equalsIgnoreCase("N/A")) {
			return 2;
		} else if (selectedItem.equalsIgnoreCase("Repair")) {
			return 3;
		}
		return 0;
	}

	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(OperationalInspection.this,
				InspectionActivity.class));
	}

}
