package com.ebs.rental.webutils;

import android.util.Base64;
import android.util.Log;

import com.ebs.rental.objects.AvailableChecklist;
import com.ebs.rental.objects.AvailableParking;
import com.ebs.rental.objects.CategoryObject;
import com.ebs.rental.objects.CheckinDetail;
import com.ebs.rental.objects.CheckinEqupments;
import com.ebs.rental.objects.CrossReferenceobject;
import com.ebs.rental.objects.CustomerList;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.EquipmentParts;
import com.ebs.rental.objects.EquipmentTransferChecklistDetailObject;
import com.ebs.rental.objects.EquipmentTransferHeadObject;
import com.ebs.rental.objects.Equipmentsubstatusdesc;
import com.ebs.rental.objects.EqupmentSubStatus;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.InspectionChecklist;
import com.ebs.rental.objects.ParkingSpot;
import com.ebs.rental.objects.PartOrderTerms;
import com.ebs.rental.objects.PartorderList;
import com.ebs.rental.objects.ProductSearchObject;
import com.ebs.rental.objects.RentalCheck;
import com.ebs.rental.objects.RentalCheckIn;
import com.ebs.rental.objects.RentalCheckinList;
import com.ebs.rental.objects.RentalChecklistPDF;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalDocumentDetail;
import com.ebs.rental.objects.RentalListSelectorObject;
import com.ebs.rental.objects.RentalOrderCustomerList;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderNotesDetailObject;
import com.ebs.rental.objects.RentalOrderSignedDocmuntPDFObject;
import com.ebs.rental.objects.RentalOrderTerms;
import com.ebs.rental.objects.RentalsList;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.TransportDetailsPDFObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;
import com.ebs.rental.utils.Logger;
import com.ebs.rental.utils.SessionData;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class WebServiceConsumer {

    private static WebServiceConsumer instance = null;
    private SoapObject requestSoap;
    private SoapSerializationEnvelope envelope;
    private final int timeout = 60 * 1000;
    private int array;
    private ArrayList<String> filterdesc;
    private ArrayList<String> filterequip;
    private ArrayList<String> filterequipold;
    private ArrayList<Integer> qtyShipper;

    private ArrayList<String> WalkAroundNotes;
    private ArrayList<String> WalkAroundType;
    ArrayList<String> WalkAroundImage;

    private ArrayList<byte[]> image = new ArrayList<>();

    public static WebServiceConsumer getInstance() {

        if (instance == null) {
            instance = new WebServiceConsumer();
        }

        return instance;
    }

    private SoapSerializationEnvelope getEnvelope(SoapObject soapObject) {
        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope1.dotNet = true;
        envelope1.setOutputSoapObject(soapObject);


        return envelope1;
    }

    private void callService(String method, SoapSerializationEnvelope envelope)
            throws Exception {
        try {
            HttpTransportSE httpTransport = new HttpTransportSE(
                    EBSRentalConstants.SOAP_ADDRESS, timeout);
            httpTransport.debug = true;
            httpTransport.call(method, envelope);
            httpTransport.getServiceConnection().disconnect();
            httpTransport = null;
            System.gc();
        } catch (Exception e) {
            throw e;
        }
    }

    public User authenticateUserDealer(String username, String password)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_AUTHENTICATE_DEALER);
        requestSoap.addProperty("Usrname", username);
        requestSoap.addProperty("Usrpass", password);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_AUTHENTICATE_DEALER,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("The Login Respnse is ", response.toString());
            // int numProps = response.getPropertyCount();
            User user = null;

            // if (numProps > 0) {
            Log.d("The response for login", response.toString());
            user = User.parseUser(response.toString());
            // }
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    public RentalListSelectorObject RentalCheckinList(String grpName, String partNo, int equpId,
                                                      int rentalId, String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_LIST);
        requestSoap.addProperty("grpname", grpName);
        requestSoap.addProperty("partno", partNo);
        requestSoap.addProperty("equipmentid", equpId);
        requestSoap.addProperty("rentdetlid", rentalId);
        requestSoap.addProperty("usrToken", userToken);
        Log.d("The Soap object", requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKIN_LIST,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("rental checkid list is", response.toString());
            RentalListSelectorObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalListSelector(1);
                rental = RentalListSelectorObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalListSelector(0);
                rental = RentalListSelectorObject.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalCheck RentalCheckin(int rentDetId, int completestatus, int id,
                                     String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECK_IN);
        requestSoap.addProperty("rentdetlid", rentDetId);
        requestSoap.addProperty("completestatus", completestatus);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);


        Log.d("RentalCheckin",
                "Rental RentalCheckin is " + requestSoap.toString());

        Logger.log("RentalCheckin" + "Rental RentalCheckin is "
                + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECK_IN,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("RentalCheckin result", response.toString());

            Logger.log("RentalCheckin result" + response.toString());
            RentalCheck rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = RentalCheck.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = RentalCheck.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalCheck RentalCheckinV2(int rentDetId, int completestatus, int id,
                                       String userToken, String inspectiontype) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_V2);
        requestSoap.addProperty("rentdetlid", rentDetId);
        requestSoap.addProperty("completestatus", completestatus);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("inspectiontype", inspectiontype);

        Log.d("RentalCheckin",
                "Rental RentalCheckin is " + requestSoap.toString());

        Logger.log("RentalCheckin" + "Rental RentalCheckin is "
                + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_V2,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("RentalCheckin result", response.toString());

            Logger.log("RentalCheckin result" + response.toString());
            RentalCheck rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = RentalCheck.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = RentalCheck.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public RentalCheck RentalCheckinV3(int rentDetId, int completestatus, int id,
                                       String userToken, String inspectiontype, int kordnum, int transcallnum) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_V3);
        requestSoap.addProperty("rentdetlid", rentDetId);
        requestSoap.addProperty("completestatus", completestatus);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("inspectiontype", inspectiontype);
        requestSoap.addProperty("kordnum", kordnum);
        requestSoap.addProperty("transcallnum", transcallnum);

        Log.d("method1 RentalCheckinV3",
                "Rental RentalCheckin is " + requestSoap.toString());

        Logger.log("RentalCheckin" + "Rental RentalCheckin is "
                + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_V3,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("method1 RentalCheckinV3 result", response.toString());

            Logger.log("RentalCheckin result" + response.toString());
            RentalCheck rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = RentalCheck.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = RentalCheck.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalCheck generalCheckin(int rentDetId, int completestatus, int id, String inspectiontype,
                                      String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_GENERAL_CHECKIN_V2);
        requestSoap.addProperty("rentdetlid", rentDetId);
        requestSoap.addProperty("completestatus", completestatus);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("inspectiontype", inspectiontype);
        requestSoap.addProperty("usrToken", userToken);

        Log.d("RentalCheckin",
                "Rental RentalCheckin is " + requestSoap.toString());

        Logger.log("RentalCheckin" + "Rental RentalCheckin is "
                + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_SET_GENERAL_CHECKIN_V2,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("RentalCheckin result", response.toString());

            Logger.log("RentalCheckin result" + response.toString());
            RentalCheck rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = RentalCheck.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = RentalCheck.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalChecklistPDF RentalChecklistPdf(String userToken,
                                                 String equpId, int equipblId, String rentalId, String signimage, String selectedaddress, String inputemailaddress, int rentdetaild) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKLIST_PDF);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipid", equpId);
        requestSoap.addProperty("equiptblid", equipblId);
        requestSoap.addProperty("rentalid", rentalId);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("rentdetlid", rentdetaild);


        Log.d("RentalChecklistPDF", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChecklistPDF" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKLIST_PDF,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalChecklistPDF result", "" + response.toString());
            Logger.log("RentalChecklistPDF result" + "" + response.toString());

            RentalChecklistPDF rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalChecklistPDF.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalChecklistPDF.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public RentalChecklistPDF RentalChecklistPdfV2(String userToken,
                                                   String equpId, int equipblId, String rentalId, String signimage, String selectedaddress, String inputemailaddress,
                                                   int rentdetaild, int inspectionid, String type, String signeename) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKLIST_PDF_V3);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipid", equpId);
        requestSoap.addProperty("equiptblid", equipblId);
        requestSoap.addProperty("rentalid", rentalId);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("rentdetlid", rentdetaild);
        requestSoap.addProperty("inspectionid", inspectionid);
        requestSoap.addProperty("type", type);
        requestSoap.addProperty("signeename", signeename);


        Log.d("method1 RentalChecklistPdfV2", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChecklistPDF" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKLIST_PDF_V3,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("method1 RentalChecklistPdfV2 result", "" + response.toString());
            Logger.log("RentalChecklistPDF result" + "" + response.toString());

            RentalChecklistPDF rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalChecklistPDF.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalChecklistPDF.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalChecklistPDF RentalChecklistPdfV3(String userToken,
                                                   String equpId, int equipblId, String rentalId, String signimage, String selectedaddress, String inputemailaddress,
                                                   int rentdetaild, int inspectionid, String type, String signeename) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKLIST_PDF_V3);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipid", equpId);
        requestSoap.addProperty("equiptblid", equipblId);
        requestSoap.addProperty("rentalid", rentalId);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("rentdetlid", rentdetaild);
        requestSoap.addProperty("inspectionid", inspectionid);
        requestSoap.addProperty("type", type);
        requestSoap.addProperty("signeename", signeename);


        Log.d("method1 RentalChecklistPdfV3", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChecklistPDF" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKLIST_PDF_V3,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("method1 RentalChecklistPdfV3 result", "" + response.toString());
            Logger.log("RentalChecklistPDF result" + "" + response.toString());

            RentalChecklistPDF rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalChecklistPDF.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalChecklistPDF.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public CrossReferenceobject CrossReference(String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_CROSS_REFERENCE);
        requestSoap.addProperty("usrToken", userToken);


        Log.d("CrossReference", "Rental CrossReference is "
                + requestSoap.toString());
        Logger.log("CrossReference" + "Rental CrossReference is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_CROSS_REFERENCE,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("CrossReference result", "" + response.toString());
            Logger.log("CrossReference result" + "" + response.toString());

            CrossReferenceobject rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = CrossReferenceobject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = CrossReferenceobject.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public RentalChecklistPDF generalChecklistPdf(String userToken,
                                                  String equpId, int equipblId, String rentalId, String signimage,
                                                  String selectedaddress, String inputemailaddress, int rentdetaild,
                                                  int inspectionid, String username) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GENERAL_CHECKLIST_PDF);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipid", equpId);
        requestSoap.addProperty("equiptblid", equipblId);
        requestSoap.addProperty("rentalid", rentalId);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("rentdetlid", rentdetaild);
        requestSoap.addProperty("inspectionid", inspectionid);
        requestSoap.addProperty("username", username);


        Log.d("General ChecklistPDF", "General GenerallChickinImages is "
                + requestSoap.toString());
        Logger.log("General" + "General RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_GENERAL_CHECKLIST_PDF,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("General result", "" + response.toString());
            Logger.log("General result" + "" + response.toString());

            RentalChecklistPDF rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalChecklistPDF.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalChecklistPDF.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public RentalChecklistPDF transferChecklistPdf(String userToken,
                                                   String equpId, int equipblId, String rentalId, String signimage,
                                                   String selectedaddress, String inputemailaddress, int rentdetaild,
                                                   int inspectionid, String frombranch, String tobranch,
                                                   String username) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_TRANSFER_CHECKLIST_PDF);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipid", equpId);
        requestSoap.addProperty("equiptblid", equipblId);
        requestSoap.addProperty("rentalid", rentalId);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("rentdetlid", rentdetaild);
        requestSoap.addProperty("frombranch", frombranch);
        requestSoap.addProperty("tobranch", tobranch);
        requestSoap.addProperty("inspectionid", inspectionid);
        requestSoap.addProperty("username", username);


        Log.d("Transfer ChecklistPDF", "Transfer GenerallChickinImages is "
                + requestSoap.toString());
        Logger.log("Transfer" + "Transfer RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_TRANSFER_CHECKLIST_PDF,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Transfer result", "" + response.toString());
            Logger.log("Transfer result" + "" + response.toString());

            RentalChecklistPDF rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalChecklistPDF.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalChecklistPDF.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public RentalOrderTerms rentalOrderTerms(String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_ORDER_TERMS);
        requestSoap.addProperty("usrToken", userToken);

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_ORDER_TERMS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalOrderTerms result", "" + response.toString());
            Logger.log("RentalOrderTerms result" + "" + response.toString());

            RentalOrderTerms rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalChecklistPDFResult(1);
                rental = RentalOrderTerms.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalChecklistPDFResult(0);
                rental = RentalOrderTerms.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public PartOrderTerms partOrderTerms(String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_PART_ORDER_TERMS);
        requestSoap.addProperty("usrToken", userToken);

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_PART_ORDER_TERMS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("PartOrderTerms result", "" + response.toString());
            Logger.log("PartOrderTerms result" + "" + response.toString());

            PartOrderTerms rental = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setPartOrderTermsResult(1);
                rental = PartOrderTerms.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setPartOrderTermsResult(0);
                rental = PartOrderTerms.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }

    public Customeremails customermails(String userToken, String customerno,
                                        String custnum, String signtype) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_CUSTOMER_MAILS);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("customerno", customerno);
        requestSoap.addProperty("custsnum", custnum);
        requestSoap.addProperty("signtype", signtype);

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_CUSTOMER_MAILS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Customeremails result", "" + response.toString());
            Logger.log("Customeremails result" + "" + response.toString());
            Customeremails rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setCustomeremailsResult(1);
                rental = Customeremails.parseMessage(response.toString());

            } else {

                SessionData.getInstance().setCustomeremailsResult(0);
                rental = Customeremails.parseUser(response.toString());

            }

            return rental;

        } catch (Exception e) {
            throw e;
        }

    }


    public ArrayList<CustomerNameMail> customermailsv1(String userToken, String customerno,
                                                       String custnum, String signtype) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_CUSTOMER_MAILS_V1);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("customerno", customerno);
        requestSoap.addProperty("custsnum", custnum);
        requestSoap.addProperty("signtype", signtype);

        SessionData.getInstance().setStrKcustnum(customerno);
        SessionData.getInstance().setStrCustnum(custnum);
        SessionData.getInstance().setStrSigntype(signtype);
        Log.d("Customeremails result", "" + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_SET_CUSTOMER_MAILS_V1,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Customeremails result", "" + response.toString());
            Logger.log("Customeremails result" + "" + response.toString());
            ArrayList<CustomerNameMail> customerNameMails = new ArrayList<>();

            customerNameMails = CustomerNameMail.parseData(response.toString());

            return customerNameMails;

        } catch (Exception e) {
            throw e;
        }

    }

    public String RentalChickinImages(int checkinDetId, String imgPath,
                                      String description, int id, String content, int offSet,
                                      String userToken, String equipmentId) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_IMAGES);
        requestSoap.addProperty("checkindetlid", checkinDetId);
        requestSoap.addProperty("imgpath", imgPath);
        requestSoap.addProperty("description", description);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("content", content);
        requestSoap.addProperty("offset", offSet);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipmentid", equipmentId);


        Log.d("RentalChickinImages", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChickinImages" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_IMAGES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalChickinImages result", "" + response.toString());
            Logger.log("RentalChickinImages result" + "" + response.toString());
            return response.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    public String GeneralCheckInImages(int checkinDetId, String imgPath,
                                       String description, int id, String content, int offSet,
                                       String userToken, String equipmentId) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_GENERAL_CHECKIN_IMAGES);
        requestSoap.addProperty("checkindetlid", checkinDetId);
        requestSoap.addProperty("imgpath", imgPath);
        requestSoap.addProperty("description", description);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("content", content);
        requestSoap.addProperty("offset", offSet);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipmentid", equipmentId);


        Log.d("RentalChickinImages", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChickinImages" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_GENERAL_CHECKIN_IMAGES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalChickinImages result", "" + response.toString());
            Logger.log("RentalChickinImages result" + "" + response.toString());
            return response.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    public String RentalChickinImagesV2(int checkinDetId, String imgPath,
                                        String description, int id, String content, int offSet,
                                        String userToken, String equipmentId, String type) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_IMAGES_V2);
        requestSoap.addProperty("checkindetlid", checkinDetId);
        requestSoap.addProperty("imgpath", imgPath);
        requestSoap.addProperty("description", description);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("content", content);
        requestSoap.addProperty("offset", offSet);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("equipmentid", equipmentId);
        requestSoap.addProperty("type", type);


        Log.d("method1 RentalChickinImages RentalChickinImagesV2", "Rental RentalChickinImages is "
                + requestSoap.toString());
        Logger.log("RentalChickinImages" + "Rental RentalChickinImages is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_IMAGES_V2,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("method1 RentalChickinImages result RentalChickinImagesV2", "" + response.toString());
            Logger.log("RentalChickinImages result" + "" + response.toString());
            return response.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    public CheckinDetail rentalCheckinDetl(int checkinEqupmentId,
                                           int checkListId, String checkListdelValue, String notes, int id,
                                           String userToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_DETL);
        requestSoap.addProperty("checkinequipdetlid", checkinEqupmentId);
        requestSoap.addProperty("checklistid", checkListId);
        requestSoap.addProperty("checklistdetlvalue", checkListdelValue);
        requestSoap.addProperty("notes", notes);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);

        Log.d("method1 CheckinDetl rentalCheckinDetl", "Rental CheckinDetl is " + requestSoap.toString());
        Logger.log(" method1 CheckinDetl rentalCheckinDetl" + "Rental CheckinDetl is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKIN_DETL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("method1 CheckinDetl rentalCheckinDetl result", " " + response.toString());
            Logger.log("CheckinDetl rentalCheckinDetl result" + " " + response.toString());
            CheckinDetail checkin = null;
            if (response.toString().contains("Message")) {
                SessionData.getInstance().setSetRentalCheckIndetlResult(1);
                checkin = CheckinDetail.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setSetRentalCheckIndetlResult(0);
                checkin = CheckinDetail.parseUser(response.toString());
            }

            return checkin;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }

    }

//	public CheckinEqupments rentalCheckinEqupments(int checkinHeadId,
//			String equpmentId, int hourMeter, String fuelLevel, String id,
//			String userToken, String subStatus, String subStatusDesc,
//			String eqpStatus,String signimage,String selectedemailaddress,String inputemailaddress) throws Exception {
//		requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
//				EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_EQUPMENTS);
//		requestSoap.addProperty("checkinheadid", checkinHeadId);
//		requestSoap.addProperty("equipmentid", equpmentId);
//		requestSoap.addProperty("hourmeter", hourMeter);
//		requestSoap.addProperty("fuellevel", fuelLevel);
//		requestSoap.addProperty("id", id);
//		requestSoap.addProperty("usrToken", userToken);
//		requestSoap.addProperty("substatus", subStatus);
//		requestSoap.addProperty("substatusdesc", subStatusDesc);
//		requestSoap.addProperty("eqpstatus", eqpStatus);
//		requestSoap.addProperty("signimage", signimage);
//		requestSoap.addProperty("selectedemailaddress",selectedemailaddress);
//		requestSoap.addProperty("inputemailaddress",inputemailaddress);
//
//		Logger.log(signimage);
//
//
//		Log.d("CheckinEquipments",
//				"RentalCheckinEquipments is " + requestSoap.toString());
//		Logger.log("CheckinEquipments" + "Rental CheckinEquipments is "
//				+ requestSoap.toString());
//		envelope = getEnvelope(requestSoap);
//
//		try {
//			callService(
//					EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKIN_EQUPMENTS,
//					envelope);
//			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//
//			Log.d("CheckinEquipments result", "" + response.toString());
//			Logger.log("CheckinEquipments result" + "" + response.toString());
//			CheckinEqupments checkin = null;
//
//			if(response.toString().contains("Message")){
//				SessionData.getInstance().setRentalCheckinEquipmentsResult(1);
//				checkin = CheckinEqupments.parseMessage(response.toString());
//			}
//			else{
//				SessionData.getInstance().setRentalCheckinEquipmentsResult(0);
//				checkin = CheckinEqupments.parseUser(response.toString());
//			}
//           return checkin;
//			// return Integer.parseInt(response.toString());
//		} catch (Exception e) {
//			throw e;
//		}
//	}


    public CheckinEqupments rentalCheckinEqupmentsWalkAround(int checkinHeadId,
                                                             String equpmentId, int hourMeter, String fuelLevel, String id,
                                                             String userToken, String subStatus, String subStatusDesc,
                                                             String eqpStatus, String signimage, String selectedemailaddress, String inputemailaddress, int type, String inspectiontype, String signeename) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITHWALKAROUND);
        requestSoap.addProperty("checkinheadid", checkinHeadId);
        requestSoap.addProperty("equipmentid", equpmentId);
        requestSoap.addProperty("hourmeter", hourMeter);
        requestSoap.addProperty("fuellevel", fuelLevel);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("substatus", subStatus);
        requestSoap.addProperty("substatusdesc", subStatusDesc);
        requestSoap.addProperty("eqpstatus", eqpStatus);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedemailaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("inspectiontype", inspectiontype);
        requestSoap.addProperty("signeename", signeename);

        SoapObject requestwalkaroundinspectionlist = new SoapObject();

        requestwalkaroundinspectionlist.addProperty("id", 0);
        requestwalkaroundinspectionlist.addProperty("transheadid", 0);
        requestwalkaroundinspectionlist.addProperty("type", type);

        requestwalkaroundinspectionlist.addProperty("equipmentid", equpmentId);
        requestwalkaroundinspectionlist.addProperty("notes", SessionData.getInstance().getWalkaroundNotes());

        SoapObject requestImageslist = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "Imageslist");

        WalkAroundNotes = SessionData.getInstance().getWalkAroundNotes();
        WalkAroundType = SessionData.getInstance().getWalkAroundCategoryId();
        image = SessionData.getInstance().getWalkaroundgeneralimages();

        for (int i = 0; i < WalkAroundNotes.size(); i++) {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "WalkAroundInspectionImages");
            String encodedImage = Base64.encodeToString(image.get(i), Base64.DEFAULT);
            requestSoapequp.addProperty("id", 0);
            requestSoapequp.addProperty("wkinspectionheaderid", 0);
            requestSoapequp.addProperty("imgstring", encodedImage);
            requestSoapequp.addProperty("notes", WalkAroundNotes.get(i));
            requestSoapequp.addProperty("categoryid", WalkAroundType.get(i));
            requestImageslist.addSoapObject(requestSoapequp);
        }

        requestwalkaroundinspectionlist.addSoapObject(requestImageslist);

        requestSoap.addProperty("walkaroundinspectionlist", requestwalkaroundinspectionlist);

        Logger.log(signimage);

        Log.d("method1 CheckinEquipments rentalCheckinEqupmentsWalkAround",
                "RentalCheckinEquipments is " + requestSoap.toString());
        Logger.log("method1 rentalCheckinEqupmentsWalkAround" + "Rental CheckinEquipments is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        envelope.implicitTypes = true;

        envelope.setAddAdornments(false);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITHWALKAROUND,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("method1 CheckinEquipments rentalCheckinEqupmentsWalkAround result", "" + response.toString());
            Logger.log("CheckinEquipments result" + "" + response.toString());
            CheckinEqupments checkin = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(1);
                checkin = CheckinEqupments.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(0);
                checkin = CheckinEqupments.parseUser(response.toString());
            }

            return checkin;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public CheckinEqupments transportEqupmentsWalkAround(int checkinHeadId,
                                                         String equpmentId, int hourMeter, String fuelLevel, String id,
                                                         String userToken, String subStatus, String subStatusDesc,
                                                         String eqpStatus, String signimage, String selectedemailaddress, String inputemailaddress, int type, String inspectiontype, String signeename) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITH_WALKAROUND_V3);
        requestSoap.addProperty("checkinheadid", checkinHeadId);
        requestSoap.addProperty("equipmentid", equpmentId);
        requestSoap.addProperty("hourmeter", hourMeter);
        requestSoap.addProperty("fuellevel", fuelLevel);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("substatus", subStatus);
        requestSoap.addProperty("substatusdesc", subStatusDesc);
        requestSoap.addProperty("eqpstatus", eqpStatus);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedemailaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
        requestSoap.addProperty("inspectiontype", inspectiontype);
        requestSoap.addProperty("signeename", signeename);

        SoapObject requestwalkaroundinspectionlist = new SoapObject();

        requestwalkaroundinspectionlist.addProperty("id", 0);
        requestwalkaroundinspectionlist.addProperty("transheadid", 0);
        requestwalkaroundinspectionlist.addProperty("type", type);

        requestwalkaroundinspectionlist.addProperty("equipmentid", equpmentId);
        requestwalkaroundinspectionlist.addProperty("notes", SessionData.getInstance().getWalkaroundNotes());

        SoapObject requestImageslist = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "Imageslist");

        WalkAroundNotes = SessionData.getInstance().getWalkAroundNotes();
        WalkAroundType = SessionData.getInstance().getWalkAroundCategoryId();
        image = SessionData.getInstance().getWalkaroundgeneralimages();

        for (int i = 0; i < WalkAroundNotes.size(); i++) {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "WalkAroundInspectionImages");
            String encodedImage = Base64.encodeToString(image.get(i), Base64.DEFAULT);
            requestSoapequp.addProperty("id", 0);
            requestSoapequp.addProperty("wkinspectionheaderid", 0);
            requestSoapequp.addProperty("imgstring", encodedImage);
            requestSoapequp.addProperty("notes", WalkAroundNotes.get(i));
            requestSoapequp.addProperty("categoryid", WalkAroundType.get(i));
            requestImageslist.addSoapObject(requestSoapequp);
        }

        requestwalkaroundinspectionlist.addSoapObject(requestImageslist);

        requestSoap.addProperty("walkaroundinspectionlist", requestwalkaroundinspectionlist);

        Logger.log(signimage);

        Log.d("CheckinEquipments",
                "RentalCheckinEquipments is " + requestSoap.toString());
        Logger.log("CheckinEquipments" + "Rental CheckinEquipments is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        envelope.implicitTypes = true;

        envelope.setAddAdornments(false);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITH_WALKAROUND_V3,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("CheckinEquipments result", "" + response.toString());
            Logger.log("CheckinEquipments result" + "" + response.toString());
            CheckinEqupments checkin = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(1);
                checkin = CheckinEqupments.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(0);
                checkin = CheckinEqupments.parseUser(response.toString());
            }

            return checkin;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public CheckinEqupments EquipmentTransferDetailWithWalkAround(String usrToken,
                                                                  int id, int transferheaderid, String equipmentid,
                                                                  String ponum,
                                                                  String transferbranch, int transportid, String notes
            , String custrecbranch, int transtype, boolean transneeded,
                                                                  String kcustnum, String kmfg, String kmodel, String kserialno,
                                                                  String kbranch, String username, int hourmeter, int custrec) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_TRANSFER_DETAIL_WITH_WALKAROUND);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("transferheaderid", transferheaderid);
        requestSoap.addProperty("equipmentid", equipmentid);
        requestSoap.addProperty("ponum", ponum);
        requestSoap.addProperty("transferbranch", transferbranch);
        requestSoap.addProperty("notes", notes);


        SoapObject requestwalkaroundinspectionlist = new SoapObject();

        requestwalkaroundinspectionlist.addProperty("id", 0);
        requestwalkaroundinspectionlist.addProperty("transheadid", 0);
        requestwalkaroundinspectionlist.addProperty("type", 3);

        requestwalkaroundinspectionlist.addProperty("equipmentid", equipmentid);
        requestwalkaroundinspectionlist.addProperty("notes", SessionData.getInstance().getWalkaroundNotes());

        SoapObject requestImageslist = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "Imageslist");

        WalkAroundNotes = SessionData.getInstance().getWalkAroundNotes();
        WalkAroundType = SessionData.getInstance().getWalkAroundCategoryId();
        image = SessionData.getInstance().getWalkaroundgeneralimages();

        for (int i = 0; i < WalkAroundNotes.size(); i++) {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "WalkAroundInspectionImages");
            String encodedImage = Base64.encodeToString(image.get(i), Base64.DEFAULT);
            requestSoapequp.addProperty("id", 0);
            requestSoapequp.addProperty("wkinspectionheaderid", 0);
            requestSoapequp.addProperty("imgstring", encodedImage);
            requestSoapequp.addProperty("notes", WalkAroundNotes.get(i));
            requestSoapequp.addProperty("categoryid", WalkAroundType.get(i));
            requestImageslist.addSoapObject(requestSoapequp);
        }

        requestwalkaroundinspectionlist.addSoapObject(requestImageslist);

        requestSoap.addProperty("custrecbranch", custrecbranch);
        requestSoap.addProperty("transtype", transtype);
        requestSoap.addProperty("transneeded", transneeded);
        requestSoap.addProperty("kmfg", kmfg);
        requestSoap.addProperty("kmodel", kmodel);
        requestSoap.addProperty("kserialno", kserialno);
        requestSoap.addProperty("kbranch", kbranch);
        requestSoap.addProperty("username", username);
        requestSoap.addProperty("hourmeter", hourmeter);
        requestSoap.addProperty("custrec", custrec);

        requestSoap.addProperty("walkaroundinspectionlist", requestwalkaroundinspectionlist);

        Log.d("CheckinEquipments",
                "RentalCheckinEquipments is " + requestSoap.toString());
        Logger.log("CheckinEquipments" + "Rental CheckinEquipments is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        envelope.implicitTypes = true;

        envelope.setAddAdornments(false);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_TRANSFER_DETAIL_WITH_WALKAROUND,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("CheckinEquipments result", "" + response.toString());
            Logger.log("CheckinEquipments result" + "" + response.toString());
            CheckinEqupments checkin = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(1);
                checkin = CheckinEqupments.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(0);
                checkin = CheckinEqupments.parseUser(response.toString());
            }

            return checkin;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public CheckinEqupments EquipmentTransferReceiveDetailWithWalkAround(String usrToken,
                                                                         int id, int transferheaderid, String equipmentid,
                                                                         String ponum,
                                                                         String transferbranch, int transportid, String notes
            , String username, int hourmeter, int transtype

    ) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_TRANSFER_RECEIVE_DETAIL_WITHWALKAROUND);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("transferheaderid", transferheaderid);
        requestSoap.addProperty("equipmentid", equipmentid);
        requestSoap.addProperty("ponum", ponum);
        requestSoap.addProperty("transferbranch", transferbranch);
        requestSoap.addProperty("notes", notes);
        requestSoap.addProperty("transferid", transportid);
        requestSoap.addProperty("username", username);
        requestSoap.addProperty("hourmeter", hourmeter);
        requestSoap.addProperty("transtype", transtype);


        SoapObject requestwalkaroundinspectionlist = new SoapObject();

        requestwalkaroundinspectionlist.addProperty("id", 0);
        requestwalkaroundinspectionlist.addProperty("transheadid", 0);
        requestwalkaroundinspectionlist.addProperty("type", 4);

        requestwalkaroundinspectionlist.addProperty("equipmentid", equipmentid);
        requestwalkaroundinspectionlist.addProperty("notes", SessionData.getInstance().getWalkaroundNotes());

        SoapObject requestImageslist = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "Imageslist");

        WalkAroundNotes = SessionData.getInstance().getWalkAroundNotes();
        WalkAroundType = SessionData.getInstance().getWalkAroundCategoryId();
        image = SessionData.getInstance().getWalkaroundgeneralimages();

        for (int i = 0; i < WalkAroundNotes.size(); i++) {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "WalkAroundInspectionImages");
            String encodedImage = Base64.encodeToString(image.get(i), Base64.DEFAULT);
            requestSoapequp.addProperty("id", 0);
            requestSoapequp.addProperty("wkinspectionheaderid", 0);
            requestSoapequp.addProperty("imgstring", encodedImage);
            requestSoapequp.addProperty("notes", WalkAroundNotes.get(i));
            requestSoapequp.addProperty("categoryid", WalkAroundType.get(i));
            requestImageslist.addSoapObject(requestSoapequp);
        }
        SessionData.getInstance().getWalkAroundNotes().clear();
        SessionData.getInstance().getWalkAroundCategoryId().clear();
        SessionData.getInstance().getWalkaroundgeneralimages().clear();

        requestwalkaroundinspectionlist.addSoapObject(requestImageslist);


        requestSoap.addProperty("walkaroundinspectionlist", requestwalkaroundinspectionlist);


        Log.d("CheckinEquipments",
                "RentalCheckinEquipments is " + requestSoap.toString());
        Logger.log("CheckinEquipments" + "Rental CheckinEquipments is "
                + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        envelope.implicitTypes = true;

        envelope.setAddAdornments(false);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_TRANSFER_RECEIVE_DETAIL_WITHWALKAROUND,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("CheckinEquipments result", "" + response.toString());
            Logger.log("CheckinEquipments result" + "" + response.toString());
            CheckinEqupments checkin = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(1);
                checkin = CheckinEqupments.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinEquipmentsResult(0);
                checkin = CheckinEqupments.parseUser(response.toString());
            }

            return checkin;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public EqupmentSubStatus equpmentSubStatus(String userToken,
                                               String eqpStatus) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_EQUPMENT_SUB_STATUS);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("eqpstatus", eqpStatus);
        envelope = getEnvelope(requestSoap);
        Log.d("EqupmentSubStatus request", "" + requestSoap.toString());
        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_EQUPMENT_SUB_STATUS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("EqupmentSubStatus result", "" + response.toString());
            Logger.log("EqupmentSubStatus result" + "" + response.toString());
            EqupmentSubStatus substatus = null;
            if (response.toString().contains("Message")) {
                SessionData.getInstance().setEqupmentSubStatusResult(1);
                substatus = EqupmentSubStatus.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setEqupmentSubStatusResult(0);
                substatus = EqupmentSubStatus.parseUser(response.toString());
            }

            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }

    }


    public EquipmentTransferHeadObject EquipmentTransferHead(int id,
                                                             String branch, String type, String usrToken, int transcallnum) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_TRANSFERHEAD);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("branch", branch);
        requestSoap.addProperty("type", type);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("transcallnum", transcallnum);
        envelope = getEnvelope(requestSoap);
        Log.d("EqupmentSubStatus request", "" + requestSoap.toString());
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERHEAD,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("EqupmentSubStatus result", "" + response.toString());
            Logger.log("EqupmentSubStatus result" + "" + response.toString());
            EquipmentTransferHeadObject substatus = null;
            if (response.toString().contains("Message")) {
                SessionData.getInstance().setEquipmentTransferHeadObject(1);
                substatus = EquipmentTransferHeadObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setEquipmentTransferHeadObject(0);
                substatus = EquipmentTransferHeadObject.parseUser(response.toString());
            }

            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }

    }


    public Equipmentsubstatusdesc equpmentdescription(String userToken,
                                                      String eqpStatus, String shortDesc) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_EQUPMENT_DESCRIPTION);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("eqpstatus", eqpStatus);
        requestSoap.addProperty("shortdesc", shortDesc);
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_SET_RENTAL_EQUPMENT_DESCRIPTION,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Equipmentsubstatusdesc result", "" + response.toString());
            Logger.log("Equipmentsubstatusdesc result" + ""
                    + response.toString());
            Equipmentsubstatusdesc substatus = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setEquipmentsubstatusdescResult(1);
                substatus = Equipmentsubstatusdesc.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setEquipmentsubstatusdescResult(0);
                substatus = Equipmentsubstatusdesc.parseUser(response.toString());
            }

            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public EquipmentTransferChecklistDetailObject equipmentTransferChecklistDetail(int transferequipdetlid,
                                                                                   int checklistid, String checklistdetlvalue,
                                                                                   String notes, int id, String usrToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_TRANSFERCHECK_LISTDETAIL);
        requestSoap.addProperty("transferequipdetlid", transferequipdetlid);
        requestSoap.addProperty("checklistid", checklistid);
        requestSoap.addProperty("checklistdetlvalue", checklistdetlvalue);
        requestSoap.addProperty("notes", notes);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", usrToken);
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERCHECK_LISTDETAIL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("EquipmentTransferChecklistDetailObject result", "" + response.toString());
            Logger.log("EquipmentTransferChecklistDetailObject result" + ""
                    + response.toString());
            EquipmentTransferChecklistDetailObject substatus = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setEquipmentTransferChecklistDetailObject(1);
                substatus = EquipmentTransferChecklistDetailObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setEquipmentTransferChecklistDetailObject(0);
                substatus = EquipmentTransferChecklistDetailObject.parseUser(response.toString());
            }

            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public EquipmentTransferChecklistDetailObject equipmentTransferChecklistImages(int checkindetlid,
                                                                                   String imgpath,
                                                                                   String description,
                                                                                   int id, String content,
                                                                                   int offset, String usrToken,
                                                                                   String equipmentid, String type) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_TRANSFERCHECK_LIST_IMAGES);

        requestSoap.addProperty("checkindetlid", checkindetlid);
        requestSoap.addProperty("imgpath", imgpath);
        requestSoap.addProperty("description", description);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("content", content);
        requestSoap.addProperty("offset", offset);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("equipmentid", equipmentid);
        requestSoap.addProperty("type", type);

        Log.d("Equip ID web", "" + equipmentid);
        Log.d("imgpath", "" + imgpath);
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERCHECK_LIST_IMAGES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("EquipmentTransferChecklistDetailObject result", "" + response.toString());
            Logger.log("EquipmentTransferChecklistDetailObject result" + ""
                    + response.toString());
            EquipmentTransferChecklistDetailObject substatus = null;

            if (response.toString().contains("Message")) {
                SessionData.getInstance().setEquipmentTransferChecklistDetailObject(1);
                substatus = EquipmentTransferChecklistDetailObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setEquipmentTransferChecklistDetailObject(0);
                substatus = EquipmentTransferChecklistDetailObject.parseUser(response.toString());
            }

            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }
    }


    public RentalOrderSignedDocmuntPDFObject rentalOrderSignedDocumentPdf(
            String userToken, String orderno, String kcustnum,
            String branchcode, String ordertype, String custsign, long offset,
            String custsnum, String sendemailaddress,
            String insertemailaddress, String signername, boolean hadsign,
            String equipid, String location, String desc, String eqp) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_ORDER_SIGNED_DOC_PDF);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("kcustnum", kcustnum);
        requestSoap.addProperty("branchcode", branchcode);
        requestSoap.addProperty("custsign", custsign);
        requestSoap.addProperty("ordertype", ordertype);
        requestSoap.addProperty("offset", offset);
        requestSoap.addProperty("custsnum", custsnum);
        requestSoap.addProperty("sendemailaddress", sendemailaddress);
        requestSoap.addProperty("insertemailaddress", insertemailaddress);
        requestSoap.addProperty("signername", signername);
        requestSoap.addProperty("HadSigned", hadsign);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("location", location);
        SoapObject requestSoapnew = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "partdescription");

        requestSoap.addSoapObject(requestSoapnew);
        array = SessionData.getInstance().getFiltereddesc().size();
        filterdesc = SessionData.getInstance().getFiltereddesc();
        filterequip = SessionData.getInstance().getFilteredequip();
        filterequipold = SessionData.getInstance().getFilteredequoldip();
        if (array != 0) {
            for (int i = 0; i < array; i++) {
                SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                        "Parts");
                requestSoapequp.addProperty("equipid", filterdesc.get(i));
                requestSoapequp.addProperty("equipoldid", filterequipold.get(i));
                requestSoapequp.addProperty("equipindex", filterequip.get(i));

                requestSoapnew.addSoapObject(requestSoapequp);
                Log.d("With data", "" + requestSoapequp);
            }
        } else {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "Parts");
            requestSoapequp.addProperty("equipid", "");
            requestSoapequp.addProperty("equipoldid", "");
            requestSoapequp.addProperty("equipindex", "");

            requestSoapnew.addSoapObject(requestSoapequp);
            Log.d("Without data", "" + requestSoapequp);


        }

//        StringBuilder sb = new StringBuilder();
//        for(String str : filterdesc){
//            sb.append(str).append("~"); //separating contents using semi colon
//        }
//        String des = sb.toString();
//        
//        StringBuilder sb1 = new StringBuilder();
//        for(String str1 : filterequip){
//            sb1.append(str1).append("~"); //separating contents using semi colon
//        }
//
//		String eqpid = sb1.toString();
//		
//		SoapObject requestSoapequp =new SoapObject(EBSRentalConstants.NAME_SPACE,
//				"Parts");
//		requestSoapequp.addProperty("desc", des);
//		requestSoapequp.addProperty("equipid", eqpid);
//		requestSoapnew.addSoapObject(requestSoapequp);
//		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        
//		envelope.setOutputSoapObject(requestSoap);
//        
//		envelope.addMapping(EBSRentalConstants.NAME_SPACE, "description", new Desc().getClass());
        //	Log.d("description", "" + requestSoap.addProperty("description",dis));
        Log.d("RentalOrderDocumentPdf request", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        String rentalCheckin = new String();
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_RENTAL_ORDER_SIGNED_DOC_PDF,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalOrderDocumentPdf result", "" + response.toString());
            Logger.log("RentalOrderDocumentPdf result" + ""
                    + response.toString());
            RentalOrderSignedDocmuntPDFObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalOrderSignedDocumentPdf(1);
                rental = RentalOrderSignedDocmuntPDFObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalOrderSignedDocumentPdf(0);
                rental = RentalOrderSignedDocmuntPDFObject.parseUser(response.toString());
            }

            return rental;

        } catch (Exception e) {
            throw e;
        }

    }


    public RentalOrderSignedDocmuntPDFObject rentalOrderSignedDocumentPdfV4(
            String userToken, String orderno, String kcustnum,
            String branchcode, String ordertype, String custsign, long offset,
            String custsnum, String sendemailaddress,
            String insertemailaddress, String signername, boolean hadsign,
            String equipid, String location, String desc, String eqp, int insheadid, String equipnum) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V4);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("kcustnum", kcustnum);
        requestSoap.addProperty("branchcode", branchcode);
        requestSoap.addProperty("custsign", custsign);
        requestSoap.addProperty("ordertype", ordertype);
        requestSoap.addProperty("offset", offset);
        requestSoap.addProperty("custsnum", custsnum);
        requestSoap.addProperty("sendemailaddress", sendemailaddress);
        requestSoap.addProperty("insertemailaddress", insertemailaddress);
        requestSoap.addProperty("signername", signername);
        requestSoap.addProperty("HadSigned", true);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("location", location);
        requestSoap.addProperty("insheadid", insheadid);
        SoapObject requestSoapnew = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "partdescription");

        requestSoap.addSoapObject(requestSoapnew);


        SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "Parts");
        requestSoapequp.addProperty("equipid", equipnum);
        requestSoapequp.addProperty("equipoldid", "0");
        requestSoapequp.addProperty("equipindex", "0");

        requestSoapnew.addSoapObject(requestSoapequp);
        Log.d("Without data", "" + requestSoapequp);


//        StringBuilder sb = new StringBuilder();
//        for(String str : filterdesc){
//            sb.append(str).append("~"); //separating contents using semi colon
//        }
//        String des = sb.toString();
//
//        StringBuilder sb1 = new StringBuilder();
//        for(String str1 : filterequip){
//            sb1.append(str1).append("~"); //separating contents using semi colon
//        }
//
//		String eqpid = sb1.toString();
//
//		SoapObject requestSoapequp =new SoapObject(EBSRentalConstants.NAME_SPACE,
//				"Parts");
//		requestSoapequp.addProperty("desc", des);
//		requestSoapequp.addProperty("equipid", eqpid);
//		requestSoapnew.addSoapObject(requestSoapequp);
//		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//		envelope.setOutputSoapObject(requestSoap);
//
//		envelope.addMapping(EBSRentalConstants.NAME_SPACE, "description", new Desc().getClass());
        //	Log.d("description", "" + requestSoap.addProperty("description",dis));
        Log.d("RentalOrderDocumentPdf request", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        String rentalCheckin = new String();
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V4,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalOrderDocumentPdf result", "" + response.toString());
            Logger.log("RentalOrderDocumentPdf result" + ""
                    + response.toString());
            RentalOrderSignedDocmuntPDFObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalOrderSignedDocumentPdf(1);
                rental = RentalOrderSignedDocmuntPDFObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalOrderSignedDocumentPdf(0);
                rental = RentalOrderSignedDocmuntPDFObject.parseUser(response.toString());
            }

            return rental;

        } catch (Exception e) {
            throw e;
        }

    }


    public RentalOrderSignedDocmuntPDFObject rentalOrderSignedDocumentPdfV2(
            String userToken, String orderno, String kcustnum,
            String branchcode, String ordertype, String custsign, long offset,
            String custsnum, String sendemailaddress,
            String insertemailaddress, String signername, boolean hadsign,
            String equipid, String location, String desc, String eqp) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V2);
        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("kcustnum", kcustnum);
        requestSoap.addProperty("branchcode", branchcode);
        requestSoap.addProperty("custsign", custsign);
        requestSoap.addProperty("ordertype", ordertype);
        requestSoap.addProperty("offset", offset);
        requestSoap.addProperty("custsnum", custsnum);
        requestSoap.addProperty("sendemailaddress", sendemailaddress);
        requestSoap.addProperty("insertemailaddress", insertemailaddress);
        requestSoap.addProperty("signername", signername);
        requestSoap.addProperty("HadSigned", hadsign);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("location", location);
        SoapObject requestSoapnew = new SoapObject(EBSRentalConstants.NAME_SPACE,
                "partdescription");

        requestSoap.addSoapObject(requestSoapnew);
        array = SessionData.getInstance().getFiltereddesc().size();
        filterdesc = SessionData.getInstance().getFiltereddesc();
        filterequip = SessionData.getInstance().getFilteredequip();
        filterequipold = SessionData.getInstance().getFilteredequoldip();
        qtyShipper = SessionData.getInstance().getQtyshipped();
        if (array != 0) {
            for (int i = 0; i < array; i++) {
                SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                        "PartsShippedQty");
                requestSoapequp.addProperty("equipid", filterdesc.get(i));
                requestSoapequp.addProperty("equipoldid", filterequipold.get(i));
                requestSoapequp.addProperty("equipindex", filterequip.get(i));
                requestSoapequp.addProperty("qtyshipped", qtyShipper.get(i));

                requestSoapnew.addSoapObject(requestSoapequp);
                Log.d("With data", "" + requestSoapequp);
            }
        } else {
            SoapObject requestSoapequp = new SoapObject(EBSRentalConstants.NAME_SPACE,
                    "PartsShippedQty");
            requestSoapequp.addProperty("equipid", "");
            requestSoapequp.addProperty("equipoldid", "");
            requestSoapequp.addProperty("equipindex", "");
            requestSoapequp.addProperty("qtyshipped", "0");

            requestSoapnew.addSoapObject(requestSoapequp);
            Log.d("Without data", "" + requestSoapequp);


        }

//        StringBuilder sb = new StringBuilder();
//        for(String str : filterdesc){
//            sb.append(str).append("~"); //separating contents using semi colon
//        }
//        String des = sb.toString();
//
//        StringBuilder sb1 = new StringBuilder();
//        for(String str1 : filterequip){
//            sb1.append(str1).append("~"); //separating contents using semi colon
//        }
//
//		String eqpid = sb1.toString();
//
//		SoapObject requestSoapequp =new SoapObject(EBSRentalConstants.NAME_SPACE,
//				"Parts");
//		requestSoapequp.addProperty("desc", des);
//		requestSoapequp.addProperty("equipid", eqpid);
//		requestSoapnew.addSoapObject(requestSoapequp);
//		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//		envelope.setOutputSoapObject(requestSoap);
//
//		envelope.addMapping(EBSRentalConstants.NAME_SPACE, "description", new Desc().getClass());
        //	Log.d("description", "" + requestSoap.addProperty("description",dis));
        Log.d("RentalOrderDocumentPdf request", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);
        String rentalCheckin = new String();
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V2,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalOrderDocumentPdf result", "" + response.toString());
            Logger.log("RentalOrderDocumentPdf result" + ""
                    + response.toString());
            RentalOrderSignedDocmuntPDFObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalOrderSignedDocumentPdf(1);
                rental = RentalOrderSignedDocmuntPDFObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalOrderSignedDocumentPdf(0);
                rental = RentalOrderSignedDocmuntPDFObject.parseUser(response.toString());
            }

            return rental;

        } catch (Exception e) {
            throw e;
        }

    }


    public RentalDocumentDetail getRentalDocumentDetail(String userToken, int docHeaderId, String description,
                                                        String mfg, String model, String equipid, String serialnum, double rate) throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_DOCUMENT_DETAIL);

        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("orderno", docHeaderId);
        requestSoap.addProperty("kcustnum", description);
        requestSoap.addProperty("branchcode", mfg);
        requestSoap.addProperty("custsign", model);
        requestSoap.addProperty("ordertype", equipid);
        requestSoap.addProperty("offset", serialnum);
        requestSoap.addProperty("custsnum", rate);
        envelope = getEnvelope(requestSoap);
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_RENTAL_DOCUMENT_DETAIL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("RentalDocumentDetail result", "" + response.toString());
            Logger.log("RentalDocumentDetail result" + ""
                    + response.toString());
            RentalDocumentDetail substatus = null;
            substatus = RentalDocumentDetail.parseUser(response.toString());
            return substatus;
            // return Integer.parseInt(response.toString());
        } catch (Exception e) {
            throw e;
        }

    }

    public ArrayList<RentalDetails> getRentalDetail(String equipmentId,
                                                    String UserToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_EQUIPMENT_PARTS);
        requestSoap.addProperty("eqmntid", equipmentId);
        requestSoap.addProperty("usrToken", UserToken);
        Log.d("today passing getRentalDetail", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_EQUIPMENTS_PARTS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("today passing getRentalDetail", "" + response.toString());
            ArrayList<RentalDetails> detail = null;
            detail = RentalDetails.parseData(response.toString());


            return detail;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<RentalDetails> getRentalDetail1(String equipmentId,
                                                     String UserToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_TRANSPORTATION_EQUIPMENT_SEARCH);
        requestSoap.addProperty("eqmntid", equipmentId);
        requestSoap.addProperty("usrToken", UserToken);
        Log.d("", "getRentalDetail1: "+requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_TRANSPORTATION_EQUIPMENT_SEARCH,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("today passing", "" + response.toString());
            ArrayList<RentalDetails> detail = null;
            detail = RentalDetails.parseData(response.toString());


            return detail;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<DealerBranches> getDealerBranch(String UserToken,
                                                     String bname, int startindex, int endindex) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_DEALER_BRANCHES);

        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("branchname", bname);
        requestSoap.addProperty("startindex", startindex);
        requestSoap.addProperty("endindex", endindex);
        Log.d("", "getDealerBranch: "+requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_DEALER_BRANCHES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<DealerBranches> dealer = null;


            Log.d("Before passing", "" + response.toString());
            dealer = DealerBranches.parseData(response.toString());
            Log.d("DealerBranches", "" + response.toString());

            return dealer;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<ProductSearchObject> ProductSearch(String productid,
                                                        String equipid, int startindex, int endindex, String usrToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_PRODUCT_SEARCH);

        requestSoap.addProperty("productid", productid);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("startindex", startindex);
        requestSoap.addProperty("endindex", endindex);
        requestSoap.addProperty("usrToken", usrToken);
        Log.d("", "ProductSearch: "+requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_PRODUCT_SEARCH,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<ProductSearchObject> dealer = null;


            Log.d("Before passing", "" + response.toString());
            dealer = ProductSearchObject.parseData(response.toString());
            Log.d("ProductSearchObject", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<CategoryObject> getCategory(String UserToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_CATEGORY_LIST);

        requestSoap.addProperty("usrToken", UserToken);
        Log.d("", "getCategory: "+requestSoap.toString());

        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_CATEGORY_LIST,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<CategoryObject> dealer = null;


            Log.d("Before passing", "" + response.toString());
            dealer = CategoryObject.parseData(response.toString());
            Log.d("CategoryObject", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<RentalOrderCustomerList> getRentalOrderCustomerList(
            String UserToken, String custname) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_ORDER_CUSTOMER_LIST);

        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("custname", custname);
        Log.d("", "getRentalOrderCustomerList: "+requestSoap.toString());

        envelope = getEnvelope(requestSoap);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_SET_RENTAL_ORDER_CUSTOMER_LIST,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<RentalOrderCustomerList> dealer = null;
            dealer = RentalOrderCustomerList.parseData(response.toString());
            Log.d("DealerBranches", "getRentalOrderCustomerList : " + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<RentalOrderList> getRentalOrderList(String UserToken,
                                                         String branchcode, String orderno, String kcustname, String custnum)
            throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_ORDER_LIST);
        requestSoap.addProperty("usrToken", UserToken);
        Log.d("UserToken", "" + UserToken.toString());
        requestSoap.addProperty("branchcode", branchcode);
        Log.d("branchcode", "" + branchcode.toString());
        requestSoap.addProperty("orderno", orderno);
        Log.d("orderno", "" + orderno.toString());
        requestSoap.addProperty("kcustnum", kcustname);
        Log.d("kcustname", "" + kcustname.toString());
        requestSoap.addProperty("custsnum", custnum);
        Log.d("custsnum", "" + custnum.toString());
        Log.d("getRentalOrderList request", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_ORDER_LIST,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<RentalOrderList> dealer = null;
            dealer = RentalOrderList.parseData(response.toString());
            Log.d("getRentalOrderList", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<RentalOrderList> getRentalEquipsearch(String UserToken,
                                                           String equipid, String ordertype, String branch)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_EQUIPMENT_DETAIL);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("ordertype", ordertype);
        requestSoap.addProperty("branchcode", branch);
        Log.d("getRentalEquipsearch", "" + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_RENTAL_EQUIPMENT_DETAIL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<RentalOrderList> dealer = null;
            dealer = RentalOrderList.parseData(response.toString());
            Log.d("Rentalorderlist", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }

    }


    public ArrayList<RentalOrderNotesDetailObject> getRentalOrderNotes(String UserToken,
                                                                       int orderno, String branchcode, String ordertype)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTAL_ORDER_NOTES_DETAIL);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("branchcode", branchcode);
        requestSoap.addProperty("ordertype", ordertype);


        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_RENTAL_ORDER_NOTES_DETAIL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<RentalOrderNotesDetailObject> dealer = null;
            Log.d("Rentalorderlist", "" + response.toString());
            if (response.toString() != "") {
                dealer = RentalOrderNotesDetailObject.parseData(response.toString());

            }


            return dealer;
        } catch (Exception e) {
            throw e;
        }

    }


    public ArrayList<PartorderList> getPartorderList(String UserToken,
                                                     String branchcode, String orderno, String kcustname, String custnum)
            throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_PARTS_ORDER_LIST);

        requestSoap.addProperty("usrToken", UserToken);
        Log.d("UserToken", "" + UserToken.toString());
        requestSoap.addProperty("branchcode", branchcode);
        Log.d("branchcode", "" + branchcode.toString());
        requestSoap.addProperty("orderno", orderno);
        Log.d("orderno", "" + orderno.toString());
        requestSoap.addProperty("kcustnum", kcustname);
        requestSoap.addProperty("custsnum", custnum);
        Log.d("getPartorderList request", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_PARTS_ORDER_LIST,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<PartorderList> dealer = null;
            dealer = PartorderList.parseData(response.toString());
            Log.d("getPartorderList", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<RentalOrderListDetailObject> getRentalOrderListDetail(
            String UserToken, String orderno, String custno, String ordertype, String branchcode) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_ORDER_LIST_DETAIL);

        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("custnum", custno);
        requestSoap.addProperty("ordertype", ordertype);
        requestSoap.addProperty("branchcode", branchcode);
        Log.d("GetRentalorderListDetailV2 request", "" + requestSoap.toString());

        envelope = getEnvelope(requestSoap);

        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_SET_RENTAL_ORDER_LIST_DETAIL,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            ArrayList<RentalOrderListDetailObject> dealer = null;
            dealer = RentalOrderListDetailObject.parseData(response.toString());
            Log.d("GetRentalorderListDetailV2", "" + response.toString());

            return dealer;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<EquipmentParts> getEquipmentParts(int partfaceNo,
                                                       String userToken) throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_EQUIPMENT_PARTS);
        requestSoap.addProperty("partfaceno", partfaceNo); // key Username case
        requestSoap.addProperty("UserToken", userToken);
        envelope = getEnvelope(requestSoap);
        ArrayList<EquipmentParts> partsList = new ArrayList<EquipmentParts>();
        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_EQUIPMENTS_PARTS,
                    envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            Log.d("The Login response", response.toString());
            int numProps = response.getPropertyCount();
            Log.d("Count of Customer: ", "" + numProps);
            for (int i = 0; i < numProps; i++) {

                partsList.add(EquipmentParts
                        .parseEquipments((SoapObject) response.getProperty(i)));
            }
        } catch (Exception e) {
            Log.d("Soap parts List ", requestSoap.toString());
            e.printStackTrace();
        }
        return partsList;
    }

    public ArrayList<CustomerList> getRentalsCustomerList(String userToken)
            throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_RENTALS_CUSTOMER_LIST);
        requestSoap.addProperty("UserToken", userToken);
        envelope = getEnvelope(requestSoap);
        ArrayList<CustomerList> customerList = new ArrayList<CustomerList>();
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_GET_RENTALS_CUSTOMER_LIST,
                    envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            int numProps = response.getPropertyCount();
            for (int i = 0; i < numProps; i++) {
                customerList
                        .add(CustomerList
                                .parseCustomerList((SoapObject) response
                                        .getProperty(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }


    public TransferEquipmentSearchObject transferEquipmentSearch(String eqmntid, String usrToken)
            throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_TRANSFER_EQUIPMENT_SEARCH);
        requestSoap.addProperty("eqmntid", eqmntid);
        requestSoap.addProperty("usrToken", usrToken);
        envelope = getEnvelope(requestSoap);
        Log.d("Transfer equipment requestSoap", "" + requestSoap);
        TransferEquipmentSearchObject transferEquipment = new TransferEquipmentSearchObject();
        try {
            callService(
                    EBSRentalConstants.SOAP_ACTION_METHOD_TRANSFER_EQUIPMENT_SEARCH,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //int numProps = response.getPropertyCount();

            Log.d("Transfer equipment response", "" + response);

            transferEquipment = TransferEquipmentSearchObject
                    .parseUser(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferEquipment;
    }


    public ArrayList<RentalsList> getRentalList(String userToken,
                                                String userCode) throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_RENTALS_LIST_INSPECTION);

        requestSoap.addProperty("usrToken", userToken);
        requestSoap.addProperty("kusrcode", userCode);
        envelope = getEnvelope(requestSoap);

        ArrayList<RentalsList> rentalsList = new ArrayList<RentalsList>();

        try {
            callService(EBSRentalConstants.SOAP_ACTION_RENTALS_LIST_INSPECTION,
                    envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            int numProps = response.getPropertyCount();
            Log.d("Total: ", "" + numProps);

            for (int i = 0; i < numProps; i++) {
                rentalsList
                        .add(RentalsList.parseRentalsList((SoapObject) response
                                .getProperty(i)));
            }
        } catch (Exception e) {
            Log.d("Soap parts List ", requestSoap.toString());
            e.printStackTrace();
        }
        return rentalsList;
    }

    public String setRentalImages(int visualInspectionId, String imgPath,
                                  String description, int id, String content, long offset,
                                  String userToken) throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_IMAGES);
        requestSoap.addProperty("visualinspectionid", visualInspectionId);
        requestSoap.addProperty("imgpath", imgPath);
        requestSoap.addProperty("description", description);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("content", content);
        requestSoap.addProperty("offset", offset);
        requestSoap.addProperty("usrToken", userToken);
        envelope = getEnvelope(requestSoap);
        String rentalImages = "";

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_IMAGES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            rentalImages = response.toString();
        } catch (Exception e) {
            Log.d("Images ", requestSoap.toString());
            e.printStackTrace();
        }
        return rentalImages;
    }

    public String setRentalCheckIn(RentalCheckIn checkin) {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN);
        requestSoap.addProperty("location", checkin.getLocation());
        requestSoap.addProperty("equipmentid", SessionData.getInstance()
                .getEquipmentId());
        requestSoap.addProperty("hourmeter", checkin.getHourMeter());
        requestSoap.addProperty("checkintype", checkin.getCheckInType());
        requestSoap.addProperty("deliveryorreturnId", SessionData.getInstance()
                .getRentalId());
        requestSoap.addProperty("replacement", checkin.isReplacement());
        requestSoap.addProperty("operatorpresent", checkin.isOperatorPresent());
        requestSoap.addProperty("returnReason", checkin.getReturnReason());
        requestSoap.addProperty("equipmentFailurecase",
                checkin.getEquipmentFailure());
        requestSoap.addProperty("customerDamagedescription",
                checkin.getCustomerDamage());
        requestSoap.addProperty("customerRep", checkin.getCustomerRepair());
        requestSoap
                .addProperty("engineOperation", checkin.getEngineOperation());
        requestSoap.addProperty("brake", checkin.getBrake());
        requestSoap.addProperty("controls", checkin.getControls());
        requestSoap.addProperty("lights", checkin.getLights());
        requestSoap.addProperty("seatBelt", checkin.getSeatBelt());
        requestSoap.addProperty("drive", checkin.getDrive());
        requestSoap.addProperty("lift", checkin.getLift());
        requestSoap.addProperty("cabOperation", checkin.getCabOperation());
        requestSoap.addProperty("representative", checkin.getRepresentative());
        requestSoap.addProperty("descrepencies", checkin.getDescrepencies());
        requestSoap.addProperty("id", checkin.getId());
        requestSoap.addProperty("usrToken", SessionData.getInstance()
                .getUsertoken());
        envelope = getEnvelope(requestSoap);
        String rentalCheckin = new String();

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKIN,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Inspection Id", "" + response.toString());
            rentalCheckin = response.toString();
        } catch (Exception e) {
            Log.d("Rental Check in ", requestSoap.toString());
            e.printStackTrace();
        }

        return rentalCheckin;
    }


    public String setRentalVisualInspection(int inspectionId, int partMasterId,
                                            boolean status, int id, String userToken) {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_VISUAL_INSPECTION);
        requestSoap.addProperty("inspectionid", inspectionId);
        requestSoap.addProperty("partmasterid", partMasterId);
        requestSoap.addProperty("status", status);
        requestSoap.addProperty("id", id);
        requestSoap.addProperty("usrToken", userToken);
        envelope = getEnvelope(requestSoap);
        String visualInspection = new String();

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_VISUAL_INSPECTION,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            visualInspection = response.toString();

        } catch (Exception e) {
            Log.d("Rental Visual Inspection ", requestSoap.toString());
            e.printStackTrace();
        }

        return visualInspection;
    }

    public ArrayList<RentalCheckinList> getRentalCheckinList(String grapName,
                                                             String portNo) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SET_RENTAL_CHECKIN_LIST);
        requestSoap.addProperty("grpname", grapName);
        requestSoap.addProperty("partno", portNo);
        ArrayList<RentalCheckinList> checkinList = new ArrayList<RentalCheckinList>();
        try {
            callService(EBSRentalConstants.SOAP_ACTION_SET_RENTAL_CHECKIN_LIST,
                    envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            int numProps = response.getPropertyCount();

            for (int i = 0; i < numProps; i++) {
                checkinList.add(RentalCheckinList
                        .parseRentalCheckinList((SoapObject) response
                                .getProperty(i)));
            }

        } catch (Exception e) {
            Log.d("Rental Visual Inspection ", requestSoap.toString());
            e.printStackTrace();
        }
        return checkinList;

    }


    public String RemoveActiveUser(String userToken, int userId) {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_REMOVE_ACTIVE_USER);


        requestSoap.addProperty("usertoken", userToken);
        requestSoap.addProperty("userid", userId);
        envelope = getEnvelope(requestSoap);
        String visualInspection = new String();

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_REMOVE_ACTIVE_USER,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            visualInspection = response.toString();

        } catch (Exception e) {
            Log.d("Rental Visual Inspection ", requestSoap.toString());
            e.printStackTrace();
        }

        return visualInspection;
    }


    public GeneralEquipmentSearchObject generalEquipSearch(String eqmntid, String usrToken)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GENERAL_EQUIPMENT_SEARCH);
        requestSoap.addProperty("eqmntid", eqmntid);
        requestSoap.addProperty("usrToken", usrToken);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GENERAL_EQUIPMENT_SEARCH,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("The General Respnse is ", response.toString());
            // int numProps = response.getPropertyCount();
            GeneralEquipmentSearchObject user = null;

            // if (numProps > 0) {
            Log.d("The response for General", response.toString());
            user = GeneralEquipmentSearchObject.parseData(response.toString());
            // }
            return user;
        } catch (Exception e) {
            throw e;
        }
    }


    public TransferReceiveEquipmentSearchObject receiveEquipSearch(String eqmntid, String usrToken)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_TRANSFER_RECEIVE_EQUIPMENT_SERACH);
        requestSoap.addProperty("eqmntid", eqmntid);
        requestSoap.addProperty("usrToken", usrToken);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_TRANSFER_RECEIVE_EQUIPMENT_SERACH,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("The Login Respnse is ", response.toString());
            // int numProps = response.getPropertyCount();
            TransferReceiveEquipmentSearchObject user = null;

            // if (numProps > 0) {
            Log.d("The response for login", response.toString());
            user = TransferReceiveEquipmentSearchObject.parseData(response.toString());
            // }
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    public String equipmentEditables(String usrToken) throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_EQUIPMENT_EDITABLES);

        requestSoap.addProperty("usrToken", usrToken);

        envelope = getEnvelope(requestSoap);
        String EquipmentEditable = new String();

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_EQUIPMENT_EDITABLES,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            EquipmentEditable = response.toString();

            Log.d("request Equipment Editables", requestSoap.toString());

            Log.d("response Equipment Editables", EquipmentEditable);

        } catch (Exception e) {

            Log.d("request Equipment Editables", requestSoap.toString());

            Log.d("response Equipment Editables", EquipmentEditable);
            e.printStackTrace();
        }

        return EquipmentEditable;
    }


    public ArrayList<TransportListObject> GetTransportTruckList(
            String UserToken) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_TRANSPORT_TRUCK_DETAILS);
        requestSoap.addProperty("usrToken", UserToken);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_TRANSPORT_TRUCK_DETAILS,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Truck List :", "" + response.toString());
            ArrayList<TransportListObject> detail = null;
            detail = TransportListObject.parseData(response.toString());


            return detail;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<TransportListObject> GetTransportList(
            String UserToken, String trucknumber) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_TRANSPORT_DETAILS_V2);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("trucknumber", trucknumber);
        Log.d("METHOD_GET_TRANSPORT_DETAILS_V2", "GetTransportList:  request"+requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_V2,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("METHOD_GET_TRANSPORT_DETAILS_V2 Truck List :", "" + response.toString());
            ArrayList<TransportListObject> detail = null;
            detail = TransportListObject.parseData(response.toString());

            return detail;
        } catch (Exception e) {
            throw e;
        }
    }


    public String SendDispatchNotification(
            String UserToken, String recordnumber, String mobiletrackstatus) throws Exception {

        String result = "";
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_SEND_DISPATCH_NOTIFICATION);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("recordnumber", recordnumber);
        requestSoap.addProperty("mobiletrackstatus", mobiletrackstatus);
        requestSoap.addProperty("userid", SessionData.getInstance().getTemp_userid());
        Log.d("SendDispatchNotification :requestSoap", "" + requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_SEND_DISPATCH_NOTIFICATION,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("SendDispatchNotification :response", "" + response.toString());

            result = response.toString();
//            ArrayList<TransportListObject> detail = null;
//            detail = TransportListObject.parseData(response.toString());

            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<GetTransportDetailsDescOjbect> GetTransportDesc(
            String UserToken, int RcNo) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE, EBSRentalConstants.METHOD_GET_TRANSPORT_DETAILS_DESCRIPTION);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("recnum", RcNo);

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_DESCRIPTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Truck Description:", "" + response.toString());
            ArrayList<GetTransportDetailsDescOjbect> detail = null;
            detail = GetTransportDetailsDescOjbect.parseData(response.toString());
            return detail;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<GetTransportDetailsDescOjbect> GetTransportForTransfer(
            String UserToken, int RcNo) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE, EBSRentalConstants.METHOD_GET_TRANSPORT_DETAILS_FOR_TRANSFER);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("recnum", RcNo);

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_FOR_TRANSFER, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("Truck Description:", "" + response.toString());
            ArrayList<GetTransportDetailsDescOjbect> detail = null;
            detail = GetTransportDetailsDescOjbect.parseData(response.toString());
            return detail;
        } catch (Exception e) {
            throw e;
        }
    }

    public TransportDetailsPDFObject UpdateTransportDeliveryDet(
            String UserToken, int transport_id, String oejobnum, int kordnum) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE, EBSRentalConstants.METHOD_UPDATE_TRANSPORT_DELIVERY_DET);
        requestSoap.addProperty("usrToken", UserToken);
        requestSoap.addProperty("transport_id", transport_id);
        requestSoap.addProperty("oejobnum", oejobnum);
        requestSoap.addProperty("kordnum", kordnum);
        Log.d("","method1 TransportDetailsPDF UpdateTransportDeliveryDet request" + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_UPDATE_TRANSPORT_DELIVERY_DET, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("method1 TransportDetailsPDF UpdateTransportDeliveryDet result", response.toString());

            Logger.log("TransportDetailsPDF UpdateTransportDeliveryDet result" + response.toString());
            TransportDetailsPDFObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = TransportDetailsPDFObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = TransportDetailsPDFObject.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }
    }


    public TransportDetailsPDFObject TransportDetailsPDF(String usrToken, String equipid, int equiptblid, int rentalid,
                                                         String signimage, String selectedemailaddress, String inputemailaddress,
                                                         int rentdetlid, int inspectionid, String type, String signeename,
                                                         int orderno, String kcustnum, String branchcode, String ordertype,
                                                         String custsnum, String location, String equipmentidlist,
                                                         String equipoldidlist
    ) throws Exception {
        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_TRANSPORT_DETAILSPDF_V1);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("equipid", equipid);
        requestSoap.addProperty("equiptblid", equiptblid);
        requestSoap.addProperty("rentalid", rentalid);
        requestSoap.addProperty("signimage", signimage);
        requestSoap.addProperty("selectedemailaddress", selectedemailaddress);
        requestSoap.addProperty("inputemailaddress", inputemailaddress);
//		requestSoap.addProperty("rentdetlid", rentdetlid);
        requestSoap.addProperty("inspectionid", inspectionid);
        requestSoap.addProperty("type", type);
        requestSoap.addProperty("signeename", signeename);
        requestSoap.addProperty("orderno", orderno);
        requestSoap.addProperty("kcustnum", kcustnum);
        requestSoap.addProperty("branchcode", branchcode);
        requestSoap.addProperty("ordertype", ordertype);

        requestSoap.addProperty("custsnum", custsnum);
//		requestSoap.addProperty("location", location);
        requestSoap.addProperty("equipmentidlist", equipmentidlist);
        requestSoap.addProperty("equipoldidlist", equipoldidlist);


        Log.d("TransportDetailsPDF",
                "TransportDetailsPDF_V1 is " + requestSoap.toString());

        Logger.log("TransportDetailsPDF" + "TransportDetailsPDF_V1 is "
                + requestSoap.toString());

        envelope = getEnvelope(requestSoap);
        try {
            callService(EBSRentalConstants.SOAP_ACTION_METHOD_TRANSPORT_DETAILSPDF_V1,
                    envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("TransportDetailsPDF TransportDetailsPDF_V1 result", response.toString());

            Logger.log("TransportDetailsPDF TransportDetailsPDF_V1 result" + response.toString());
            TransportDetailsPDFObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalCheckinResult(1);
                rental = TransportDetailsPDFObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalCheckinResult(0);
                rental = TransportDetailsPDFObject.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }

    }


    public ArrayList<String> GetInspectionCheckListsold(String usrtoken, int userid)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_INSPECTION_CHECKLISTS);
        requestSoap.addProperty("usrtoken", usrtoken);
        requestSoap.addProperty("userid", userid);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_INSPECTION_CHECKLISTS,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("GetInspectionCheckLists are ", response.toString());

            ArrayList<String> checlists = new ArrayList<>();

            if (response.toString().contains("Session has expired")) {

                checlists.add("Session has expired");
            } else {
                checlists.addAll(InspectionChecklist.parseData(response.toString()));
            }

            return checlists;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<InspectionChecklist> GetInspectionCheckLists(String usrtoken, int userid)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_INSPECTION_CHECKLISTS);
        requestSoap.addProperty("usrtoken", usrtoken);
        requestSoap.addProperty("userid", userid);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_INSPECTION_CHECKLISTS,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("GetInspectionCheckLists are ", response.toString());

            ArrayList<InspectionChecklist> checlists = new ArrayList<>();
            if (response.toString().contains("Session has expired")) {
                InspectionChecklist inspectionChecklist = new InspectionChecklist();
                inspectionChecklist.setMessage("Session has expired");
                checlists.add(inspectionChecklist);
            } else {
                checlists = InspectionChecklist.parseChecklist(response.toString());
            }

            return checlists;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<AvailableChecklist> GetAvailableCheckListsV1(String usrtoken, int userid)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_AVAILABLE_LISTS_V1);
        requestSoap.addProperty("usrToken", usrtoken);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_AVAILABLE_LISTS_V1,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("GetAvailableCheckListsV1 are ", response.toString());

            ArrayList<AvailableChecklist> checlists = new ArrayList<>();
            if (response.toString().contains("Session has expired")) {
                AvailableChecklist inspectionChecklist = new AvailableChecklist();
                inspectionChecklist.setMessage("Session has expired");
                checlists.add(inspectionChecklist);
            } else {
                checlists = AvailableChecklist.parseChecklist(response.toString());
            }

            return checlists;
        } catch (Exception e) {
            throw e;
        }
    }

    public RentalListSelectorObject GetGeneralCheckListItems(String usrtoken, String checklistid)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_GENERAL_CHECKLIST_ITEMS);
        requestSoap.addProperty("usrToken", usrtoken);
        requestSoap.addProperty("checklistid", checklistid);
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_GENERAL_CHECKLIST_ITEMS,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("rental checkid list is", response.toString());
            RentalListSelectorObject rental = null;

            if (response.toString().contains("Message")) {

                SessionData.getInstance().setRentalListSelector(1);
                rental = RentalListSelectorObject.parseMessage(response.toString());
            } else {
                SessionData.getInstance().setRentalListSelector(0);
                rental = RentalListSelectorObject.parseUser(response.toString());
            }

            return rental;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<AvailableParking> GetAvailableParkingSpots(String usrToken, String kbranch)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_AVAILABLE_PARKING_SPOTS);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("kbranch", kbranch);
        Log.d("GetAvailableParkingSpots request ", requestSoap.toString());

        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_AVAILABLE_PARKING_SPOTS,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("GetAvailableParkingSpots are ", response.toString());

            ArrayList<AvailableParking> parkingLists = new ArrayList<>();
            if (response.toString().contains("Session has expired")) {
                AvailableParking availableParking = new AvailableParking();
                availableParking.setMessage("Session has expired");
                parkingLists.add(availableParking);
            } else {
                parkingLists = AvailableParking.parseParkingList(response.toString());
            }

            return parkingLists;
        } catch (Exception e) {
            throw e;
        }
    }

    public ParkingSpot GetParkingSpotForEquipment(String usrToken, String kbranch, String kequipnum)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_GET_PARKING_SPOT_FOR_EQUIPMENT);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("kbranch", kbranch);
        requestSoap.addProperty("kequipnum", kequipnum);
        Log.d("GetParkingSpotForEquipment request ", requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_GET_PARKING_SPOT_FOR_EQUIPMENT,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("GetParkingSpotForEquipment are ", response.toString());

            ParkingSpot parkingSpotLists = new ParkingSpot();
            if (response.toString().contains("Session has expired")) {
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setMessage("Session has expired");
                parkingSpotLists = parkingSpot;
            } else {
                parkingSpotLists = ParkingSpot.ParseParkingList(response.toString());
            }

            return parkingSpotLists;
        } catch (Exception e) {
            throw e;
        }
    }

    public String updateContactEmails(String usrToken, String kcustnum, String custsnum, String signtype, String contactname, String email)
            throws Exception {

        requestSoap = new SoapObject(EBSRentalConstants.NAME_SPACE,
                EBSRentalConstants.METHOD_UPDATE_CONTACT_EMAILS);
        requestSoap.addProperty("usrToken", usrToken);
        requestSoap.addProperty("recordid", 0);
        requestSoap.addProperty("kcustnum", kcustnum);
        requestSoap.addProperty("custsnum", custsnum);
        requestSoap.addProperty("contactname", contactname);
        requestSoap.addProperty("email", email);
        requestSoap.addProperty("signtype", signtype);
        Log.i("method1 AddContactEmails", " requestSoap:"+requestSoap.toString());
        envelope = getEnvelope(requestSoap);

        try {
            callService(EBSRentalConstants.SOAP_ACTION_UPDATE_CONTACT_EMAILS,
                    envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.d("method1 AddContactEmails are ", response.toString());

//            ParkingSpot parkingSpotLists = new ParkingSpot();
//            if (response.toString().contains("Session has expired")){
//                ParkingSpot parkingSpot =new ParkingSpot();
//                parkingSpot.setMessage("Session has expired");
//                parkingSpotLists = parkingSpot;
//            }else {
//                parkingSpotLists = ParkingSpot.ParseParkingList(response.toString());
//            }

            return response.toString();
        } catch (Exception e) {
            throw e;
        }
    }

}
