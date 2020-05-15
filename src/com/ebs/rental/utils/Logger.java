package com.ebs.rental.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@SuppressWarnings("ALL")
public class Logger {
	
	@SuppressWarnings("CanBeFinal")
	private static  String path = "data/data/com.ebs.rental.general/files" ;
	private static final String file = "/ebs_rental.txt" ;
	private static final boolean canCreateLog = true;
	static {
		if(canCreateLog) {
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
			path = path + "/EBS_Rental";
			new File(path).mkdirs();
			path +=file;
			try {
				FileOutputStream out = new FileOutputStream(path);
				out.flush();
				out.close();
			} catch (Exception e) {
			}
		}
		
	}
	
	public static void log(String logString) {
		if(canCreateLog) {
			try {
				Log.d("Logger", logString);
				FileOutputStream out = new FileOutputStream(path, true);
				out.write(("\n "+new Date(System.currentTimeMillis())+" : "+logString).getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void log(Exception e) {
		if(canCreateLog) {
			try {
				Log.d("Logger", e.getMessage());
				e.printStackTrace();
				FileOutputStream out = new FileOutputStream(path, true);
				out.write(("\n Exception: "+new Date(System.currentTimeMillis())+" : "+e.getMessage()).getBytes());
				for(StackTraceElement element : e.getStackTrace()) {
					out.write(("\n >> "+element.toString()).getBytes());
					out.flush();
				}
				out.flush();
				out.close();
			} catch (Exception ex) {
			}
		}
	}
	
}
