package com.ebs.rental.objects;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

@SuppressWarnings("ALL")
public class Parts implements KvmSerializable{
//	 public static String TAG = Parts.class.getSimpleName();
//	    public static String TAG_DESC = "desc";
//	    public static String TAG_EQUPID = "equipid";
	
	private java.lang.String desc;
	private java.lang.String equpid;
		@Override
		public Object getProperty(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0){
	        case 0:
	            return desc;
	        case 1:
	            return equpid;
	        default:
	            return null;
	        }
		}
		
		public Parts(String desc, String equpid) {
	        // TODO Auto-generated constructor stub
	        this.desc=desc;
	        this.equpid=equpid;
	    }
		public Parts() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getPropertyCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		@Override
		public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
			// TODO Auto-generated method stub
			
			switch (index) {
	        case 0:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "desc";
	            break;
	        case 1:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "equipid";
	            break;
	        default:
	            break;
			}
			
		}
		@Override
		public void setProperty(int index, Object value) {
			
			 switch (index) {
		        case 0:
		        	desc = value.toString();
		            break;
		        case 1:
		        	equpid = value.toString();
		            break;

		        }
			
		}

	

	    

}
