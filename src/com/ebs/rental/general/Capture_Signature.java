package com.ebs.rental.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.SessionData;
import com.ebs.rental.webutils.AlertDialogBox;
import com.ebs.rental.webutils.ProgressBar;
import com.ebs.rental.webutils.WebServiceConsumer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("ALL")
public class Capture_Signature extends AppCompatActivity implements OnClickListener {
	private SignaturePanel mSignature;
	private LinearLayout mContent;
	private LinearLayout btnsShow1;
	private LinearLayout btnsShow2;
	private static Dialog dialog;
	private byte[] signatureData;
	private TextView textCancel;
	private ArrayList<RentalOrderListDetailObject> listDetailObjects;
	private RentalOrderListDetailObject DetailObjects;
	private ArrayList<CustomerNameMail> customeremails;
	private ImageView back;
	private EditText username;
	private User user;
	private Button btnClear;
	private Button btnSaveSignature;
	private Button NewSignature;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_signature);
		new ContextWrapper(getApplicationContext());
		user = SessionData.getInstance().getUser();
		
		back = (ImageView)findViewById(R.id.backicon);
		listDetailObjects = SessionData.getInstance().getOrderListDetail();
		DetailObjects = listDetailObjects.get(0);
		if (SessionData.getInstance().getSignData() != null)
			signatureData = SessionData.getInstance().getSignData().clone();
		btnClear = (Button) findViewById(R.id.clear);
		btnSaveSignature = (Button) findViewById(R.id.getsign);
		NewSignature = (Button) findViewById(R.id.newSign);
		mContent = (LinearLayout) findViewById(R.id.linearLayoutSign);
		textCancel = (TextView) findViewById(R.id.textViewback);
		btnsShow1 = (LinearLayout) findViewById(R.id.linearLayoutSignButtons1);
		btnsShow2 = (LinearLayout) findViewById(R.id.linearLayoutSignButtons2);
		username = (EditText) findViewById(R.id.Signame);
		username.setText(SessionData.getInstance().getContact());

		textCancel.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnSaveSignature.setOnClickListener(this);
		NewSignature.setOnClickListener(this);
		
		back.setOnClickListener(this);

		if (signatureData == null) {
			btnsShow1.setVisibility(View.GONE);
			btnsShow2.setVisibility(View.VISIBLE);
			btnSaveSignature.setEnabled(false);
		} else {
			btnsShow1.setVisibility(View.VISIBLE);
			btnsShow2.setVisibility(View.GONE);
		}
		mSignature = new SignaturePanel(this, null);
		if (signatureData != null) {
			final Paint paint = new Paint();
			final Bitmap mBitmap = BitmapFactory.decodeByteArray(signatureData,
					0, signatureData.length);
			mContent.setBackground(new Drawable() {

				@Override
				public void setColorFilter(ColorFilter cf) {

				}

				@Override
				public void setAlpha(int alpha) {

				}

				@Override
				public int getOpacity() {
					return 0;
				}

				@Override
				public void draw(Canvas canvas) {
					canvas.drawBitmap(mBitmap, 0, 0, paint);

				}
			});
		} else {
			mContent.addView(mSignature, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
	}

	public class SignaturePanel extends View {
		private static final float STROKE_WIDTH = 2f;
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private final Paint paint = new Paint();
		private final Path path = new Path();

		public Bitmap mBitmap;
		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF();

		public SignaturePanel(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);

		}

		public void save(View v) {

			mBitmap = Bitmap.createBitmap(mContent.getWidth(),
					mContent.getHeight(), Bitmap.Config.RGB_565);

			Canvas canvas = new Canvas(mBitmap);

			try {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				v.draw(canvas);
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
				try {
					SessionData.getInstance().setSignData(
							setExifMetaData(stream));
				} catch (Exception e) {
					SessionData.getInstance().setSignData(stream.toByteArray());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void clear() {
			mContent.setBackgroundResource(R.drawable.backgroundsignature);
			path.reset();
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			btnSaveSignature.setEnabled(true);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(eventX, eventY);
				lastTouchX = eventX;
				lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:

			case MotionEvent.ACTION_UP:

				resetDirtyRect(eventX, eventY);
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					expandDirtyRect(historicalX, historicalY);
					path.lineTo(historicalX, historicalY); 
				}
				path.lineTo(eventX, eventY);
				break;

			default:
				debug("Ignored touch event: " + event.toString());
				return false;
			}

			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		private void debug(String string) {
		}

		private void expandDirtyRect(float historicalX, float historicalY) {
			if (historicalX < dirtyRect.left) {
				dirtyRect.left = historicalX;
			} else if (historicalX > dirtyRect.right) {
				dirtyRect.right = historicalX;
			}

			if (historicalY < dirtyRect.top) {
				dirtyRect.top = historicalY;
			} else if (historicalY > dirtyRect.bottom) {
				dirtyRect.bottom = historicalY;
			}
		}

		private void resetDirtyRect(float eventX, float eventY) {
			dirtyRect.left = Math.min(lastTouchX, eventX);
			dirtyRect.right = Math.max(lastTouchX, eventX);
			dirtyRect.top = Math.min(lastTouchY, eventY);
			dirtyRect.bottom = Math.max(lastTouchY, eventY);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == textCancel) {
			finish();
		}
		if (v == btnClear) {
			mSignature.clear();
			btnSaveSignature.setEnabled(false);
		}

		else if (v == btnSaveSignature) {
			mContent.setDrawingCacheEnabled(true);
			mSignature.save(mContent);
			Toast.makeText(Capture_Signature.this,
					"Signature saved successfully", Toast.LENGTH_LONG).show();
			new AsynMail().execute();

		} else if (v == NewSignature) {
			mSignature.clear();
			btnSaveSignature.setEnabled(false);
			if (mContent.getChildCount() == 0) {
				mContent.addView(mSignature, LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
			}
			btnsShow1.setVisibility(View.GONE);
			btnsShow2.setVisibility(View.VISIBLE);
		}
		else if(v == back){
			Intent intent = new Intent(Capture_Signature.this,
					TearmsAndCondisions.class);

			startActivity(intent);
		}
		
	}

	private byte[] setExifMetaData(ByteArrayOutputStream stream)
			throws IOException {
		String path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		path = path + "/Data/signature";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");
		new File(path).mkdirs();
		path += "/sign_" + format.format(date) + ".jpg";

		FileOutputStream fout = new FileOutputStream(path);
		fout.write(stream.toByteArray());
		fout.flush();
		fout.close();

		ExifInterface newexif = new ExifInterface(path);

		newexif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, "0");

		double latitude = 0D, longitude = 0D;

		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		newexif.setAttribute(ExifInterface.TAG_DATETIME, format.format(date));
		newexif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude + "");
		newexif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, "" + longitude);

		newexif.saveAttributes();

		FileInputStream inStream = new FileInputStream(path);
		byte[] buffer = new byte[inStream.available()];
		inStream.read(buffer);
		inStream.close();
		// new File(path).delete();
		return buffer;
	}

	private class AsynMail extends AsyncTask<Void, Void, Void> {
		protected void onPreExecute() {

			ProgressBar.showCommonProgressDialog(Capture_Signature.this);
		}

		;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				customeremails = WebServiceConsumer.getInstance()
						.customermailsv1(user.getUserDescription(),
								DetailObjects.getKcustnum(),
								DetailObjects.getCustsnum(), "R");

			} catch (java.net.SocketTimeoutException e) {
				customeremails = null;

			} catch (Exception e) {
				customeremails = null;

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();

			if (customeremails != null) {

				if (customeremails.size() != 0) {

					if (customeremails.get(0).getMessage().length() != 0) {
						if (customeremails.get(0).getMessage().contains("Session")) {
							new AsyncLoginTask().execute();
						} else {
							dialog = new Dialog(Capture_Signature.this);
							dialog.setCanceledOnTouchOutside(true);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.message);


							TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
							ImageView closeImg=dialog.findViewById(R.id.close_img);

							TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
							Message.setText(customeremails.get(0).getMessage());

							closeImg.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
							yes.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {

//								Intent inspection = new Intent(Capture_Signature.this,
//										MainActivity.class);
//								startActivity(inspection);
									dialog.dismiss();
								}
							});


							dialog.show();
						}
					} else {
						SessionData.getInstance().setCustomerNameMails(customeremails);
						SessionData.getInstance().setContact(username.getText().toString());
						Intent intent = new Intent(Capture_Signature.this,
								CustomerMailDetails.class);
						startActivity(intent);
						finish();
					}


				} else {
					SessionData.getInstance().setCustomerNameMails(customeremails);
					SessionData.getInstance().setContact(username.getText().toString());
					Intent intent = new Intent(Capture_Signature.this,
							CustomerMailDetails.class);
					startActivity(intent);
					finish();
				}

			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(Capture_Signature.this,
						"Data is not found.");
			}

		}
	}


	private class AsyncLoginTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			ProgressBar.showCommonProgressDialog(Capture_Signature.this);
		};

		@Override
		protected Void doInBackground(Void... params) {
			try {
				user = WebServiceConsumer.getInstance()
						.authenticateUserDealer(SessionData.getInstance().getLogin_username(),
								SessionData.getInstance().getLogin_password());
				Log.d("Session Expiered", "Session Expiered");
				Log.d("New User Token",""+SessionData.getInstance().getTemp_userToken());
				Log.d("After Session Expired","Equip_ID"+SessionData.getInstance().getTemp_equipId());
			} catch (java.net.SocketTimeoutException e) {
				user = null;
			} catch (Exception e) {
				user = null;
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			ProgressBar.dismiss();
			if(user!=null){
				SessionData.getInstance().setUser(user);


				if(user.getUserId()==0){
					dialog = new Dialog(Capture_Signature.this);
					dialog.setCanceledOnTouchOutside(true);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.message);


					TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
					TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
					ImageView closeImg=dialog.findViewById(R.id.close_img);

					Message.setText(user.getUserDescription());

					closeImg.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

					yes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							Intent inspection = new Intent(Capture_Signature.this,
									MainActivity.class);
							startActivity(inspection);
							dialog.dismiss();
						}
					});


					dialog.show();
				}else{

					new AsynMail().execute();

				}



			}else{
				ProgressBar.dismiss();
				AlertDialogBox.showAlertDialog(Capture_Signature.this,
						"Data is not found");
			}



		}
	}

}
