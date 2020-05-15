package com.ebs.rental.webutils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

@SuppressWarnings("ALL")
public class SoapUtils {
	protected static String getProperty(SoapObject serviceCallObject, String tag) {
		try {
			if(serviceCallObject.getProperty(tag) instanceof SoapPrimitive)
				return serviceCallObject.getPropertyAsString( tag);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	protected static int getPropertyAsInt(SoapObject soapObject, String tag) {
		tag = getProperty(soapObject, tag);
		try {
			if(tag.equals(""))
				return 0;
			return Integer.parseInt(tag.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	protected static boolean getPropertyAsBoolean(SoapObject soapObject, String tag) {
		tag = getProperty(soapObject, tag);
		if(tag.equals(""))
			return false;
		return (tag.equals("1") || tag.equals("true"));
	}

}
