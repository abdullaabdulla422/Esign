package com.ebs.rental.utils;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.util.Log;
 
@SuppressWarnings("ALL")
class ReadXMLFileUsingDom {
		private static ArrayList<EquipmentCheckParts> partList;
		private static ArrayList<CheckList> checklist;
	
		
	    public static ArrayList<CheckList> getPartsList(Activity activity,String file) {
	    	
	 
	        try {
	 
	        	
	        	checklist = new ArrayList<>();
	
	            InputStream is = activity.getAssets().open(file);
	 
	            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	 
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	 
	            Document doc = documentBuilder.parse(is);
	 
	            doc.getDocumentElement().normalize();
	 
	 
	            NodeList nodeList = doc.getElementsByTagName("checklist");
	            Log.d("The total length", ""+nodeList.getLength());
	 
	 
	            for (int itr = 0; itr < nodeList.getLength(); itr++) {
	 
	            	NodeList checItem = nodeList.item(itr).getChildNodes();
	            	CheckList check = new CheckList();
	            	partList = new ArrayList<>();
	            	for(int i = 0; i < checItem.getLength() ; i++) {
	            		Node node = checItem.item(i);
	            		if (node.getNodeType() == Node.ELEMENT_NODE) {
	 
	                    Element eElement = (Element) node;
	                    
	                    EquipmentCheckParts partsCheck = new EquipmentCheckParts();
	 
////	                    partsCheck.setPartsID(eElement.getAttribute("id"));
//	                    partsCheck.setPartsLabel(eElement.getAttribute("label"));
//	                    partsCheck.setPartType(eElement.getAttribute("controltype"));
//	                    partsCheck.setPartRequired(eElement.getAttribute("required"));
//	                    partsCheck.setPartCaptureRequired(eElement.getAttribute("imageallowed"));
	                    partList.add(partsCheck);
	 
	                }
	            	}
	            	check.setChecklist(partList);
	            	checklist.add(check);
	            }
	          
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return checklist;
			
	    }
}
