package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ebs.rental.objects.RentalCheckIn;
import com.ebs.rental.utils.RootActivity;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;

import java.util.Calendar;

@SuppressWarnings("ALL")
public class InspectionActivity extends RootActivity implements OnClickListener {

	private ImageView back;
	private ImageView home;
	@SuppressLint("StaticFieldLeak")
	private static EditText hourMeter;
	public static int inspectionFragmentSlecion = 0;
	public static boolean isDelivery;
	private static final String TAG_PLACEHOLDER = "placholder";
	public static String TAG_VISUAL = "visual";
	public static String TAG_OPERATION = "operation";
	static RentalCheckIn checkIn;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspection);
		checkIn = new RentalCheckIn();
		Button btnNext = (Button) findViewById(R.id.btn_next);
		Button btnBack = (Button) findViewById(R.id.btn_back);

		back = (ImageView) findViewById(R.id.img_back);
		back.setOnClickListener(this);
		home = (ImageView) findViewById(R.id.img_next);
		home.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.title_text);
		title.setText("Rental Inspection");
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new PlaceholderFragment(),
							TAG_PLACEHOLDER).commit();
			back.setVisibility(View.VISIBLE);
			home.setVisibility(View.VISIBLE);
			btnNext.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					checkIn.setHourMeter(hourMeter.getText().toString());
					Log.d("Delivery Id", ""
							+ SessionData.getInstance().getDeliveryId());
					Log.d("Return Id", ""
							+ SessionData.getInstance().getReturnId());
					if (isDelivery
							&& SessionData.getInstance().getDeliveryId()
									.equals("0")) {
						finish();
						Intent intentVisual = new Intent(
								InspectionActivity.this, VisualInspection.class);
						startActivity(intentVisual);
					} else if (!isDelivery
							&& SessionData.getInstance().getReturnId()
									.equals("0")) {
						finish();
						Intent intentVisual = new Intent(
								InspectionActivity.this,
								OperationalInspection.class);
						startActivity(intentVisual);
					}

					else {
						AlertDialogBox.showAlertDialog(InspectionActivity.this,
								"No Parts To Inspect");
					}
				}
			});

		}
	}

	// public void showFragment(int selectedFragment) {
	// switch (selectedFragment) {
	// case 0:
	// btnBack.setVisibility(View.GONE);
	// btnNext.setVisibility(View.VISIBLE);
	// title.setText("Rental Inspection");
	//
	// break;
	// case 1:
	// btnBack.setVisibility(View.VISIBLE);
	// btnNext.setVisibility(View.VISIBLE);
	// if(isDelivery) {
	// title.setText("Visual Inspection");
	// } else {
	// title.setText("Visual Inspection");
	// }
	// getFragmentManager().beginTransaction()
	// .replace(R.id.container, new VisualInspection(), TAG_VISUAL).commit();
	// break;
	// case 2:
	// btnNext.setVisibility(View.GONE);
	// btnBack.setVisibility(View.VISIBLE);
	// if(isDelivery) {
	// title.setText("Operational Inspection");
	// } else {
	// title.setText("Operational Inspection");
	// }
	// getFragmentManager().beginTransaction()
	// .replace(R.id.container, new OperationalInspection(),
	// TAG_OPERATION).commit();
	// break;
	// }
	// }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		RadioButton inspectionDelivery, inspectionReturn, locationOnsite,
				locationYard;
		int year, month, day, hour, minute, globalyear;
		EditText insertDate, insertEquipmentid;
		DatePickerDialog datepickerDialog;
		TimePickerDialog timepickerDialog;
		String selectedMonth, selectedDay, timeSet;
		boolean mFirst;
		int location = 1, checkinType = 1;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_inspection,
					container, false);
			isDelivery = true;

			insertDate = (EditText) rootView.findViewById(R.id.edt_insert_date);
			insertEquipmentid = (EditText) rootView
					.findViewById(R.id.edt_insert_equipment);
			hourMeter = (EditText) rootView
					.findViewById(R.id.edt_insert_hourmeter);
			insertEquipmentid.setText(SessionData.getInstance()
					.getEquipmentId());

			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			hour = c.get(Calendar.HOUR);
			minute = c.get(Calendar.MINUTE);

			selectedMonth = pad(month);
			selectedDay = pad(day);
			timeSet = getTimeset(hour);
			insertDate.setText(new StringBuilder().append(selectedDay)
					.append("/").append(selectedMonth).append("/").append(year)
					.append(" ").append(" ").append(pad(hour)).append(":")
					.append(pad(minute)).append(" ").append(timeSet));
			insertDate.setOnClickListener(this);

			inspectionDelivery = (RadioButton) rootView
					.findViewById(R.id.rad_delivery);
			inspectionReturn = (RadioButton) rootView
					.findViewById(R.id.rad_return);
			locationOnsite = (RadioButton) rootView
					.findViewById(R.id.rad_onsite);
			locationYard = (RadioButton) rootView.findViewById(R.id.rad_yard);

			getFragmentManager().beginTransaction()
					.add(R.id.deli_or_ret, new DeliveryHolderFragment())
					.commit();
			locationYard
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								location = 2;
								checkIn.setLocation(location);
							} else {
								location = 1;
								checkIn.setLocation(location);
							}
						}
					});

			inspectionReturn
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.deli_or_ret,
												new ReturnHolderFragment())
										.commit();
								isDelivery = false;
								checkinType = 2;
								checkIn.setCheckInType(checkinType);
							} else {
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.deli_or_ret,
												new DeliveryHolderFragment())
										.commit();
								isDelivery = true;
								checkinType = 1;
								checkIn.setCheckInType(checkinType);
							}
						}

					});
			checkIn.setLocation(location);
			checkIn.setCheckInType(checkinType);

			return rootView;
		}

		private static String pad(int c) {
			if (c >= 10)
				return String.valueOf(c);
			else
				return "0" + String.valueOf(c);
		}

		private static String getTimeset(int hours) {
			String timeSet = "";
			if (hours > 12) {
				hours -= 12;
				timeSet = "PM";
			} else if (hours == 0) {
				hours += 12;
				timeSet = "AM";
			} else if (hours == 12)
				timeSet = "PM";
			else
				timeSet = "AM";
			return timeSet;
		}

		@Override
		public void onClick(View v) {
			if (v == insertDate) {
				mFirst = true;
				showDatepicker();

			}
		}

		public void showTimePicker() {
			if (mFirst) {
				mFirst = false;
				timepickerDialog = new TimePickerDialog(getActivity(),
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minuteofHour) {

								timeSet = getTimeset(hourOfDay);
								insertDate.setText(new StringBuilder()
										.append(selectedDay).append("/")
										.append(selectedMonth).append("/")
										.append(globalyear).append(" ")
										.append(" ").append(pad(hourOfDay))
										.append(":").append(pad(minuteofHour))
										.append(" ").append(timeSet));
								timepickerDialog.dismiss();

							}
						}, hour, minute, false);
				timepickerDialog.setTitle("Select Time");
				timepickerDialog.show();
			}
		}

		public void showDatepicker() {

			datepickerDialog = new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int getyear,
								int monthOfYear, int dayOfMonth) {

							globalyear = getyear;
							selectedMonth = pad(monthOfYear + 1);
							selectedDay = pad(dayOfMonth);
							showTimePicker();
						}
					}, year, month, day);
			datepickerDialog.setTitle("Select Date");
			datepickerDialog.show();
		}

	}

	public static class DeliveryHolderFragment extends Fragment implements
			OnClickListener {

		CheckBox replacement;
		boolean isReplacement;
		CheckBox operatorPresent;
		boolean isOperator;

		public DeliveryHolderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View deliveryView = inflater.inflate(R.layout.fragment_delivery,
					container, false);
			replacement = (CheckBox) deliveryView
					.findViewById(R.id.chk_replacement);
			operatorPresent = (CheckBox) deliveryView
					.findViewById(R.id.chk_operator_avail);
			replacement
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							if (isChecked) {
								isReplacement = true;
								checkIn.setReplacement(true);
							} else {
								isReplacement = false;
								checkIn.setReplacement(false);
							}

						}
					});
			operatorPresent
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							if (isChecked) {
								isOperator = true;
								checkIn.setOperatorPresent(true);
							} else {
								isOperator = false;
								checkIn.setOperatorPresent(false);
							}
						}
					});
			checkIn.setReplacement(isReplacement);
			checkIn.setOperatorPresent(isOperator);
			return deliveryView;
		}

		@Override
		public void onClick(View v) {

		}
	}

	public static class ReturnHolderFragment extends Fragment implements
			OnClickListener {

		CheckBox equipmentCheck, customerDamageCheck, jobComplete, pmOther;
		RadioGroup equipmentGroup, customerGroup;
		RadioButton wearSelect, breakSelect, repSelect, operatorSelect;
		int returnReason, equipmentCase;

		public ReturnHolderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View returnView = inflater.inflate(
					R.layout.fragment_inspection_return, container, false);
			jobComplete = (CheckBox) returnView
					.findViewById(R.id.chk_job_complete);
			pmOther = (CheckBox) returnView.findViewById(R.id.chk_pm_other);
			equipmentCheck = (CheckBox) returnView
					.findViewById(R.id.chk_equipment_fail);
			customerDamageCheck = (CheckBox) returnView
					.findViewById(R.id.chk_customer_damage);
			equipmentGroup = (RadioGroup) returnView
					.findViewById(R.id.rad_group_equipment);

			customerGroup = (RadioGroup) returnView
					.findViewById(R.id.rad_group_customer_damge);
			wearSelect = (RadioButton) returnView.findViewById(R.id.rad_wear);
			breakSelect = (RadioButton) returnView
					.findViewById(R.id.rad_breakage);
			repSelect = (RadioButton) returnView.findViewById(R.id.rad_rep);
			operatorSelect = (RadioButton) returnView
					.findViewById(R.id.rad_operator);
			equipmentCheck
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean checked) {
							if (checked) {
								jobComplete.setChecked(false);
								pmOther.setChecked(false);
								wearSelect.setEnabled(true);
								breakSelect.setEnabled(true);
								returnReason = 2;
								checkIn.setReturnReason(returnReason);
								if (breakSelect.isChecked()) {
									customerDamageCheck.setEnabled(true);
								} else {
									customerDamageCheck.setEnabled(false);
								}

							} else {
								wearSelect.setEnabled(false);
								breakSelect.setEnabled(false);
								customerDamageCheck.setEnabled(false);
								repSelect.setEnabled(false);
								operatorSelect.setEnabled(false);
							}
						}
					});
			customerDamageCheck
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean checked) {
							if (checked) {
								repSelect.setEnabled(true);
								equipmentCase = 2;
								checkIn.setEquipmentFailure(equipmentCase);
								operatorSelect.setEnabled(true);
							} else {
								repSelect.setEnabled(false);
								operatorSelect.setEnabled(false);
							}
						}
					});

			jobComplete
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean checked) {
							if (checked) {
								equipmentCheck.setChecked(false);
								pmOther.setChecked(false);
								returnReason = 1;
								checkIn.setReturnReason(returnReason);
							}

						}
					});

			pmOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0,
						boolean checked) {
					if (checked) {
						equipmentCheck.setChecked(false);
						jobComplete.setChecked(false);
						returnReason = 3;
						checkIn.setReturnReason(returnReason);
					}

				}
			});

			breakSelect
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								customerDamageCheck.setEnabled(true);
							} else {
								customerDamageCheck.setEnabled(false);
								repSelect.setEnabled(false);
								operatorSelect.setEnabled(false);
							}

						}
					});
			wearSelect
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								equipmentCase = 1;
								checkIn.setEquipmentFailure(equipmentCase);
							}
						}
					});
			checkIn.setReturnReason(returnReason);
			checkIn.setEquipmentFailure(equipmentCase);
			return returnView;
		}

		@Override
		public void onClick(View v) {

		}
	}

	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(InspectionActivity.this,
				RentalListActivity.class));
	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			onBackPressed();
		}
		if (v == home) {
			finish();
			Intent intent1 = new Intent(InspectionActivity.this,
					CustomerListActivity.class);
			startActivity(intent1);
		}

	}

}
