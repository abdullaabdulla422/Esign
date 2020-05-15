package com.ebs.rental.webutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ebs.rental.general.R;

@SuppressWarnings("ALL")
public class AlertDialogBox {
	private static AlertDialog.Builder builder;
	private static Dialog dialog;
	private static AlertDialog alert;
	public static void showAlertDialog(Activity activity, String message) {
		
//		builder = new AlertDialog.Builder(activity);
//		builder.setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//		           public void onClick(DialogInterface dialog, int id) {
//		        	   alert.dismiss();
//		           }
//		       });
//		alert = builder.create();
//		alert.show();


		dialog = new Dialog(activity);
		dialog.setCanceledOnTouchOutside(true);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.message);


		TextView Message = (TextView) dialog.findViewById(R.id.txt_dialog);
		TextView yes = (TextView) dialog.findViewById(R.id.dialog_Ok);
		Message.setText(message);
		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});
		dialog.show();

	}
	
}
