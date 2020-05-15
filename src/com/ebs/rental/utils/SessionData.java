package com.ebs.rental.utils;

import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.widget.TableLayout;

import com.ebs.rental.objects.CategoryObject;
import com.ebs.rental.objects.CheckinListData;
import com.ebs.rental.objects.Custnolist;
import com.ebs.rental.objects.CustomerNameMail;
import com.ebs.rental.objects.Customeremails;
import com.ebs.rental.objects.DealerBranches;
import com.ebs.rental.objects.Filtereddatas;
import com.ebs.rental.objects.GeneralEquipmentSearchObject;
import com.ebs.rental.objects.GetTransportDetailsDescOjbect;
import com.ebs.rental.objects.PartOrderTerms;
import com.ebs.rental.objects.PartorderList;
import com.ebs.rental.objects.PartsPath;
import com.ebs.rental.objects.ProductSearchObject;
import com.ebs.rental.objects.RentalDetails;
import com.ebs.rental.objects.RentalOrderCustomerList;
import com.ebs.rental.objects.RentalOrderList;
import com.ebs.rental.objects.RentalOrderListDetailObject;
import com.ebs.rental.objects.RentalOrderNotesDetailObject;
import com.ebs.rental.objects.RentalOrderTerms;
import com.ebs.rental.objects.TransferEquipmentSearchObject;
import com.ebs.rental.objects.TransferReceiveEquipmentSearchObject;
import com.ebs.rental.objects.TransportListObject;
import com.ebs.rental.objects.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

//@SuppressWarnings("ALL")
public class SessionData {

    private static SessionData instance;
    public static boolean isTablet;
    private Bitmap imageData;
    private User user;
    private Customeremails customeremails;
    private int signdocid;
    private String ResieveSelectedStatus;
    private String ReceiveSelectedSubStatus;
    private int sind;
    private String kcustnums;
    private String custnums;
    private String kcustadd;
    private int rentaldetlid;
    private String custadd;
    private int multicheckinvalidate;
    private String loginbranch = "";
    private String hint_toSpech = "";

    private String rentalSigneeName;
    private String generalSigneeName;

    private ArrayList<GetTransportDetailsDescOjbect> transportDetailsDescOjbects;
    private ArrayList<CustomerNameMail> customerNameMails = new ArrayList<>();

    private String edtEquipId;

    private int ZbarScan = 0;

    private int ScanNavigation = 0;

    private int transportOrderNum = 0;
    private int transportcallNum = 0;

    private int TransportListIndex = 0;
    private String SelectedTransferbranch;
    private String SelectedCustomerBranch;

    private String general_hourmeter;
    private String general_fuelmeter;
    private String general_dueStatus;
    private String general_currentStatus;
    private String general_subStatus;
    private String general_description;

    private String temp_Username;
    private String temp_password;
    private ArrayList<Integer> qtyshipped;
    private String previoushours = "0";
    private int scanFlag = 0;
//	private String temp_Usertoken;

    private int mail;
    private int labelcondition;
    private String descrption;
    private String dd;
    private int checklist;
    private int checklistdata;
    private ArrayList<String> checkinequip = new ArrayList<>();
    private ArrayList<String> checkinequiplocal = new ArrayList<>();
    private int eind;
    private String spinvalues;
    private String custname;
    private String branchcode;
    private String hrmeter;
    private String fuel;
    private int checkin;
    private boolean hassign;
    private boolean issign;
    private String signed;
    private String duestatus;
    private String currentstatus;
    private String substatusfield;
    private String desc;
    private int selectEquip;
    private ArrayList<RentalDetails> detail;
    private ArrayList<RentalDetails> selectedDetail;
    private ArrayList<DealerBranches> dealer=new ArrayList<>();
    private ArrayList<DealerBranches> index;
    private ArrayList<RentalOrderCustomerList> customerlist;
    private ArrayList<RentalOrderList> orderlist;
    private ArrayList<PartorderList> porder;
    private ArrayList<PartorderList> porderlist;
    private ArrayList<RentalOrderListDetailObject> orderListDetail;
    private ArrayList<Filtereddatas> filtereddatas;
    private ArrayList<CategoryObject> categoryObjects;
    private ArrayList<TransportListObject> transportTruckList=new ArrayList<>();

    private TransportListObject transportObject;

    private RentalOrderTerms terms;

    private ArrayList<String> WalkAroundNotes = new ArrayList<>();
    private ArrayList<String> WalkAroundType = new ArrayList<>();
    private ArrayList<String> WalkAroundCategoryId = new ArrayList<>();

    private ArrayList<RentalOrderNotesDetailObject> NotesDetailObjects;
    private ArrayList<TransportListObject> transportListObjects;

    private PartOrderTerms pterms;
    private String customernumber;
    private RentalOrderList val;
    private String branch;
    private int ordernumber;
    private int pordernumber;
    private String contact;
    private String contactsummary;
    private ArrayList<DealerBranches> pscount;
    private int back;
    private String eqipoldid;
    private int equipenable;

    private int scan_equipment;

    private int TransportTransfer;

    private String username = "";
    private String usertoken = "";
    private String customshipto;
    private DealerBranches cindex;
    private String equipmentId;
    private String custNum;
    private String custName;
    private int rentalId;
    private String datasummary;
    private int order;
    private int orderNo;
    private String checkinData;
    private int inspectionID;
    private int equipinspectionID;
    private String decode;
    private String custnum;
    private String kcustnum;
    private String bname;
    private RentalOrderList bno;
    private String deliveryId;
    private String equpsubstatusdes;

    private boolean transportNeeded;

    private ArrayList<String> kpartlist;
    private ArrayList<String> eqpStatus;

    private ArrayList<String> hourmeterlist;
    private ArrayList<String> pmspeclist;
    private ArrayList<String> duestatuslist;
    private ArrayList<String> equpmentmeterlist;
    private ArrayList<String> equpmentreadinglist;
    private ArrayList<String> lastdatelist;
    private ArrayList<String> lasthourlist;
    private String substatus = "";
    private int rcount;

    private String enteredEquipID;
    private ArrayList<String> Countvalue;
    private ArrayList<byte[]> attachedFilesData = new ArrayList<byte[]>();
    private ArrayList<byte[]> walkaroundgeneralimages = new ArrayList<>();
    private byte[] imageDatas;
    private byte[] signData;
    private byte[] sig;

    private double latitude;
    private double longitude;

    private String equipID;
    private String description;
    private ArrayList<String> filtereddesc;
    private ArrayList<String> filteredequip;
    private ArrayList<String> filteredequoldip;

    private TransferEquipmentSearchObject TransferEquipment;

    private TransferReceiveEquipmentSearchObject ReceiveEquipmentSearchObject;

    private String transferKcustnum;

    private String transfer_hourMeter;
    private String transferIn_out;
    private String rentalIn_Out;
    private int mailcount;

    private String currentBranch_TransIn_Out;
    private String currentBranch_receive;
    private String currentBranch_receive_from;

    private int RentalCheckinResult = 0;
    private int RentalChecklistPDFResult = 0;
    private int RentalOrderTermsResult = 0;
    private int PartOrderTermsResult = 0;
    private int CustomeremailsResult = 0;
    private int SetRentalCheckIndetlResult = 0;
    private int RentalCheckinEquipmentsResult = 0;
    private int EqupmentSubStatusResult = 0;
    private int EquipmentsubstatusdescResult = 0;
    private int RentalListSelector = 0;
    private int RentalOrderSignedDocumentPdf = 0;
    private int EquipmentTransferHeadObject = 0;
    private int EquipmentTransferChecklistDetailObject = 0;

    private String walkaroundNotes = "";

    private String temp_equipId;
    private String temp_userToken;
    private int temp_userid;

    private String login_username;
    private String login_password;

    private String WalkAroundEquipmentID;

    private GeneralEquipmentSearchObject generalEquipmentSearchObject;

    private ArrayList<ProductSearchObject> productSearchObject;
    //private EquipmentTransferHeadObject equipmentTransferHeadObject;

    private int rentalOrGeneral;

    private ArrayList<Custnolist> custnolists;

    private String RentalEquipId;

    private String Refer;

    private String tobranch;

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    private String transport_id;


    private int kord_num;


    private ArrayList<Double> arraylatitude;
    private ArrayList<Double> arryalongitud;


    private String tab = "";

    private String inspectionEquipmentId="";

    private TabLayout tabLayout;
    private int previousTabSelection=0;
    private int inspectionType=0;
    private boolean orderFirstload=true;
    private boolean isProductSearch=false;
    private String inspectionChecklist="";

    private String checklistid;


    private String test="";

    String strKcustnum ="";
    String strCustnum ="";
    String strSigntype ="";


    private SessionData() {
    }


    public String getHint_toSpech() {
        return hint_toSpech;
    }

    public void setHint_toSpech(String hint_toSpech) {
        this.hint_toSpech = hint_toSpech;
    }

    public ArrayList<String> getHourmeterlist() {
        if (hourmeterlist == null) {
            hourmeterlist = new ArrayList<>();
        }
        return hourmeterlist;
    }

    public void setHourmeterlist(ArrayList<String> hourmeterlist) {
        this.hourmeterlist = hourmeterlist;
    }

    public ArrayList<String> getEqpStatus() {
        if (eqpStatus == null) {
            eqpStatus = new ArrayList<>();
        }
        return eqpStatus;
    }

    public void setEqpStatus(ArrayList<String> eqpStatus) {
        this.eqpStatus = eqpStatus;
    }

    public ArrayList<String> getLastdatelist() {
        if (lastdatelist == null) {
            lastdatelist = new ArrayList<>();
        }
        return lastdatelist;
    }

    public void setLastdatelist(ArrayList<String> lastdatelist) {
        this.lastdatelist = lastdatelist;
    }

    public ArrayList<String> getLasthourlist() {
        if (lasthourlist == null) {
            lasthourlist = new ArrayList<>();
        }
        return lasthourlist;
    }

    public void setLasthourlist(ArrayList<String> lasthourlist) {
        this.lasthourlist = lasthourlist;
    }

    public ArrayList<String> getEqupmentreadinglist() {
        if (equpmentreadinglist == null) {
            equpmentreadinglist = new ArrayList<>();
        }
        return equpmentreadinglist;
    }

    public void setEqupmentreadinglist(ArrayList<String> equpmentreadinglist) {
        this.equpmentreadinglist = equpmentreadinglist;
    }

    public ArrayList<String> getEqupmentmeterlist() {
        if (equpmentmeterlist == null) {
            equpmentmeterlist = new ArrayList<>();
        }
        return equpmentmeterlist;
    }

    public void setEqupmentmeterlist(ArrayList<String> equpmentmeterlist) {
        this.equpmentmeterlist = equpmentmeterlist;
    }

    public ArrayList<String> getDuestatuslist() {
        if (duestatuslist == null) {
            duestatuslist = new ArrayList<>();
        }
        return duestatuslist;
    }

    public void setDuestatuslist(ArrayList<String> duestatuslist) {
        this.duestatuslist = duestatuslist;
    }

    public ArrayList<String> getPmspeclist() {
        if (pmspeclist == null) {
            pmspeclist = new ArrayList<>();
        }
        return pmspeclist;
    }

    public void setPmspeclist(ArrayList<String> pmspeclist) {
        this.pmspeclist = pmspeclist;
    }

    public ArrayList<String> getKpartlist() {
        if (kpartlist == null) {
            kpartlist = new ArrayList<>();
        }
        return kpartlist;
    }

    public void setKpartlist(ArrayList<String> kpartlist) {
        this.kpartlist = kpartlist;
    }

    private LinkedHashMap<Integer, CheckinListData> dataHandlelist;


    public LinkedHashMap<Integer, CheckinListData> getDataHandlelist() {
        if (dataHandlelist == null) {
            dataHandlelist = new LinkedHashMap<>();
        }
        return dataHandlelist;
    }

    public void setDataHandlelist(
            LinkedHashMap<Integer, CheckinListData> dataHandlelist) {
        this.dataHandlelist = dataHandlelist;
    }

    private LinkedHashMap<String, EquipmentCheckParts> parts;

    private LinkedHashMap<String, EquipmentCheckParts> getparts() {
        if (parts == null) {
            parts = new LinkedHashMap<>();
        }
        return parts;

    }

    public void setparts(
            LinkedHashMap<String, EquipmentCheckParts> parts) {
        this.parts = parts;
    }

    public LinkedHashMap<RentalDetails, String> getCheckListData() {
        if (checkListData == null) {
            checkListData = new LinkedHashMap<>();
        }
        return checkListData;
    }

    public void setCheckListData(LinkedHashMap<RentalDetails, String> checkListData) {
        this.checkListData = checkListData;
    }

    private String returnId;
    private LinkedHashMap<RentalDetails, String> checkListData;

    private LinkedHashMap<GeneralEquipmentSearchObject, String> generalcheckListData;


//	public String getInspectionId() {
//		return inspectionId;
//	}
//
//	public void setInspectionId(String inspectionId) {
//		this.inspectionId = inspectionId;
//	}

    private LinkedHashMap<String, PartsPath> listVerification;
    private LinkedHashMap<String, Integer> getKey;
    private LinkedHashMap<String, String> visualInspectionId;

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public static SessionData getInstance() {
        if (instance == null) {
            instance = new SessionData();
        }
        return instance;
    }

    public Bitmap getImageData() {
        return imageData;
    }

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    public LinkedHashMap<String, PartsPath> getListVerification() {
        if (listVerification == null) {
            listVerification = new LinkedHashMap<String, PartsPath>();
        }
        return listVerification;
    }

    public void setListVerification(
            LinkedHashMap<String, PartsPath> listVerification) {
        this.listVerification = listVerification;
    }

    public LinkedHashMap<String, Integer> getGetKey() {
        if (getKey == null)
            getKey = new LinkedHashMap<>();
        return getKey;
    }

    public void setGetKey(LinkedHashMap<String, Integer> getKey) {
        this.getKey = getKey;
    }

    public LinkedHashMap<String, String> getVisualInspectionId() {
        if (visualInspectionId == null)
            visualInspectionId = new LinkedHashMap<>();
        return null;
    }

    public void setVisualInspectionId(
            LinkedHashMap<String, String> visualInspectionId) {
        this.visualInspectionId = visualInspectionId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getReturnId() {
        return returnId;
    }


    public ArrayList<RentalDetails> getDetail() {
		/*if(count == null){
			
			
		}*/
        return detail;
    }

    public ArrayList<RentalDetails> count;

    public void setDetail(ArrayList<RentalDetails> detail) {
        this.detail = detail;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public static Object manager() {
        return null;
    }

    public ArrayList<RentalDetails> getSelectedDetail() {
        if (selectedDetail == null) {
            selectedDetail = new ArrayList<>();
        }

        return selectedDetail;
    }

    public void setSelectedDetail(ArrayList<RentalDetails> selectedDetail) {
        this.selectedDetail = selectedDetail;
    }

    public String getCheckinData() {
        return checkinData;
    }

    public void setCheckinData(String checkinData) {
        this.checkinData = checkinData;
    }

    public String getEnteredEquipID() {
        return enteredEquipID;
    }

    public void setEnteredEquipID(String enteredEquipID) {
        this.enteredEquipID = enteredEquipID;
    }

    public int getInspectionID() {
        return inspectionID;
    }

    public void setInspectionID(int inspectionID) {
        this.inspectionID = inspectionID;
    }

    public int getEquipinspectionID() {
        return equipinspectionID;
    }

    public void setEquipinspectionID(int equipinspectionID) {
        this.equipinspectionID = equipinspectionID;
    }

    public ArrayList<String> getCountvalue() {
        if (Countvalue == null) {
            Countvalue = new ArrayList<>();
        }
        return Countvalue;
    }

    public void setCountvalue(ArrayList<String> countvalue) {
        Countvalue = countvalue;
    }

    public ArrayList<byte[]> getAttachedFilesData() {
        return attachedFilesData;
    }

    public void setAttachedFilesData(ArrayList<byte[]> attachedFilesData) {
        this.attachedFilesData = attachedFilesData;
    }

    public byte[] getImageDatas() {
        return imageDatas;
    }

    public void setImageDatas(byte[] imageDatas) {
        this.imageDatas = imageDatas;
    }

    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }

    public String getEqupsubstatusdes() {
        return equpsubstatusdes;
    }

    public void setEqupsubstatusdes(String equpsubstatusdes) {
        this.equpsubstatusdes = equpsubstatusdes;
    }

    public ArrayList<DealerBranches> getDealer() {
        return dealer;
    }

    public void setDealer(ArrayList<DealerBranches> dealer) {
        this.dealer = dealer;
    }

    public ArrayList<RentalOrderCustomerList> getCustomerlist() {
        return customerlist;
    }

    public void setCustomerlist(ArrayList<RentalOrderCustomerList> customerlist) {
        this.customerlist = customerlist;
    }

    public String getCustomshipto() {
        return customshipto;
    }

    public void setCustomshipto(String customshipto) {
        this.customshipto = customshipto;
    }

    public ArrayList<RentalOrderList> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(ArrayList<RentalOrderList> orderlist) {
        this.orderlist = orderlist;
    }

    public ArrayList<PartorderList> getPorder() {
        return porder;
    }

    public void setPorder(ArrayList<PartorderList> porder) {
        this.porder = porder;
    }

    public String getCustnum() {
        return custnum;
    }

    public void setCustnum(String custnum) {
        this.custnum = custnum;
    }

    public String getKcustnum() {
        return kcustnum;
    }

    public void setKcustnum(String kcustnum) {
        this.kcustnum = kcustnum;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public ArrayList<RentalOrderListDetailObject> getOrderListDetail() {
        return orderListDetail;
    }

    public void setOrderListDetail(ArrayList<RentalOrderListDetailObject> orderListDetail) {
        this.orderListDetail = orderListDetail;
    }

    public RentalOrderTerms getTerms() {
        return terms;
    }

    public void setTerms(RentalOrderTerms terms) {
        this.terms = terms;
    }

    public byte[] getSignData() {
        return signData;
    }

    public void setSignData(byte[] signData) {
        this.signData = signData;
    }

    public Customeremails getCustomeremails() {
        return customeremails;
    }

    public void setCustomeremails(Customeremails customeremails) {
        this.customeremails = customeremails;
    }

    public int getSigndocid() {
        return signdocid;
    }

    public void setSigndocid(int signdocid) {
        this.signdocid = signdocid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String unitIdChanged) {

        this.bname = unitIdChanged;
    }

    public RentalOrderList getVal() {
        return val;
    }

    public void setVal(RentalOrderList val) {
        this.val = val;
    }

    public RentalOrderList getBno() {
        return bno;
    }

    public void setBno(RentalOrderList bno) {
        this.bno = bno;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public ArrayList<PartorderList> getPorderlist() {
        return porderlist;
    }

    public void setPorderlist(ArrayList<PartorderList> porderlist) {
        this.porderlist = porderlist;
    }

    public PartOrderTerms getPterms() {
        return pterms;
    }

    public void setPterms(PartOrderTerms pterms) {
        this.pterms = pterms;
    }


    public int getPordernumber() {
        return pordernumber;
    }

    public void setPordernumber(int pordernumber) {
        this.pordernumber = pordernumber;
    }

    public int getSind() {
        return sind;
    }

    public void setSind(int sind) {
        this.sind = sind;
    }

    public int getEind() {
        return eind;
    }

    public void setEind(int eind) {
        this.eind = eind;
    }

    public ArrayList<DealerBranches> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<DealerBranches> index) {
        this.index = index;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public ArrayList<DealerBranches> getPscount() {
        return pscount;
    }

    public void setPscount(ArrayList<DealerBranches> pscount) {
        this.pscount = pscount;
    }

    public DealerBranches getCindex() {
        return cindex;
    }

    public void setCindex(DealerBranches cindex) {
        this.cindex = cindex;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public boolean getHassign() {
        return hassign;
    }

    public void setHassign(boolean hassign) {
        this.hassign = hassign;
    }

    public byte[] getSig() {
        return sig;
    }

    public void setSig(byte[] sig) {
        this.sig = sig;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getEquipID() {
        return equipID;
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getFiltereddesc() {
        return filtereddesc;
    }

    public void setFiltereddesc(ArrayList<String> filtereddesc) {
        this.filtereddesc = filtereddesc;
    }

    public ArrayList<String> getFilteredequip() {
        return filteredequip;
    }

    public void setFilteredequip(ArrayList<String> filteredequip) {
        this.filteredequip = filteredequip;
    }

    public ArrayList<Filtereddatas> getFiltereddatas() {
        return filtereddatas;
    }

    public void setFiltereddatas(ArrayList<Filtereddatas> filtereddatas) {
        this.filtereddatas = filtereddatas;
    }

    public String getEqipoldid() {
        return eqipoldid;
    }

    public void setEqipoldid(String eqipoldid) {
        this.eqipoldid = eqipoldid;
    }

    public ArrayList<String> getFilteredequoldip() {
        return filteredequoldip;
    }

    public void setFilteredequoldip(ArrayList<String> filteredequoldip) {
        this.filteredequoldip = filteredequoldip;
    }

    public int getEquipenable() {
        return equipenable;
    }

    public void setEquipenable(int equipenable) {
        this.equipenable = equipenable;
    }


    public int getCheckin() {
        return checkin;
    }

    public void setCheckin(int checkin) {
        this.checkin = checkin;
    }

    public String getHrmeter() {
        return hrmeter;
    }

    public void setHrmeter(String hrmeter) {
        this.hrmeter = hrmeter;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getDuestatus() {
        return duestatus;
    }

    public void setDuestatus(String duestatus) {
        this.duestatus = duestatus;
    }

    public String getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(String currentstatus) {
        this.currentstatus = currentstatus;
    }

    public String getSubstatusfield() {
        return substatusfield;
    }

    public void setSubstatusfield(String substatusfield) {
        this.substatusfield = substatusfield;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getCheckinequip() {
        return checkinequip;
    }

    public void setCheckinequip(ArrayList<String> mylabel) {
        this.checkinequip = mylabel;
    }

    public String getSpinvalues() {
        return spinvalues;
    }

    public void setSpinvalues(String spinvalues) {
        this.spinvalues = spinvalues;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getContactsummary() {
        return contactsummary;
    }

    public void setContactsummary(String contactsummary) {
        this.contactsummary = contactsummary;
    }

    public String getDatasummary() {
        return datasummary;
    }

    public void setDatasummary(String datasummary) {
        this.datasummary = datasummary;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getLabelcondition() {
        return labelcondition;
    }

    public void setLabelcondition(int labelcondition) {
        this.labelcondition = labelcondition;
    }

    public ArrayList<String> getCheckinequiplocal() {
        return checkinequiplocal;
    }

    public void setCheckinequiplocal(ArrayList<String> checkinequiplocal) {
        this.checkinequiplocal = checkinequiplocal;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }


    public boolean isIssign() {
        return issign;
    }

    public void setIssign(boolean issign) {
        this.issign = issign;
    }


    public boolean getIssign() {
        return issign;
    }

    public int getChecklist() {
        return checklist;
    }

    public void setChecklist(int checklist) {
        this.checklist = checklist;
    }

    public int getChecklistdata() {
        return checklistdata;
    }

    public void setChecklistdata(int checklistdata) {
        this.checklistdata = checklistdata;
    }

    public int getMail() {
        return mail;
    }

    public void setMail(int mail) {
        this.mail = mail;
    }

    public String getKcustnums() {
        return kcustnums;
    }

    public void setKcustnums(String kcustnums) {
        this.kcustnums = kcustnums;
    }

    public String getCustnums() {
        return custnums;
    }

    public void setCustnums(String custnums) {
        this.custnums = custnums;
    }

    public String getKcustadd() {
        return kcustadd;
    }

    public void setKcustadd(String kcustadd) {
        this.kcustadd = kcustadd;
    }

    public String getCustadd() {
        return custadd;
    }

    public void setCustadd(String custadd) {
        this.custadd = custadd;
    }

    public int getRentaldetlid() {
        return rentaldetlid;
    }

    public void setRentaldetlid(int rentaldetlid) {
        this.rentaldetlid = rentaldetlid;
    }

    public int getMulticheckinvalidate() {
        return multicheckinvalidate;
    }

    public void setMulticheckinvalidate(int multicheckinvalidate) {
        this.multicheckinvalidate = multicheckinvalidate;
    }

    public int getMailcount() {
        return mailcount;
    }

    public void setMailcount(int mailcount) {
        this.mailcount = mailcount;
    }


    public int getRentalCheckinResult() {
        return RentalCheckinResult;
    }

    public void setRentalCheckinResult(int rentalCheckinResult) {
        RentalCheckinResult = rentalCheckinResult;
    }

    public int getRentalChecklistPDFResult() {
        return RentalChecklistPDFResult;
    }

    public void setRentalChecklistPDFResult(int rentalChecklistPDFResult) {
        RentalChecklistPDFResult = rentalChecklistPDFResult;
    }

    public int getRentalOrderTermsResult() {
        return RentalOrderTermsResult;
    }

    public void setRentalOrderTermsResult(int rentalOrderTermsResult) {
        RentalOrderTermsResult = rentalOrderTermsResult;
    }

    public int getPartOrderTermsResult() {
        return PartOrderTermsResult;
    }

    public void setPartOrderTermsResult(int partOrderTermsResult) {
        PartOrderTermsResult = partOrderTermsResult;
    }


    public int getCustomeremailsResult() {
        return CustomeremailsResult;
    }

    public void setCustomeremailsResult(int customeremailsResult) {
        CustomeremailsResult = customeremailsResult;
    }

    public int getSetRentalCheckIndetlResult() {
        return SetRentalCheckIndetlResult;
    }

    public void setSetRentalCheckIndetlResult(int setRentalCheckIndetlResult) {
        SetRentalCheckIndetlResult = setRentalCheckIndetlResult;
    }

    public int getRentalCheckinEquipmentsResult() {
        return RentalCheckinEquipmentsResult;
    }

    public void setRentalCheckinEquipmentsResult(int rentalCheckinEquipmentsResult) {
        RentalCheckinEquipmentsResult = rentalCheckinEquipmentsResult;
    }

    public int getEqupmentSubStatusResult() {
        return EqupmentSubStatusResult;
    }

    public void setEqupmentSubStatusResult(int equpmentSubStatusResult) {
        EqupmentSubStatusResult = equpmentSubStatusResult;
    }

    public int getEquipmentsubstatusdescResult() {
        return EquipmentsubstatusdescResult;
    }

    public void setEquipmentsubstatusdescResult(int equipmentsubstatusdescResult) {
        EquipmentsubstatusdescResult = equipmentsubstatusdescResult;
    }

    public int getRentalListSelector() {
        return RentalListSelector;
    }

    public void setRentalListSelector(int rentalListSelector) {
        RentalListSelector = rentalListSelector;
    }

    public int getRentalOrderSignedDocumentPdf() {
        return RentalOrderSignedDocumentPdf;
    }

    public void setRentalOrderSignedDocumentPdf(int rentalOrderSignedDocumentPdf) {
        RentalOrderSignedDocumentPdf = rentalOrderSignedDocumentPdf;
    }

    public int getScan_equipment() {
        return scan_equipment;
    }

    public void setScan_equipment(int scan_equipment) {
        this.scan_equipment = scan_equipment;
    }

    public int getSelectEquip() {
        return selectEquip;
    }

    public void setSelectEquip(int selectEquip) {
        this.selectEquip = selectEquip;
    }

    public ArrayList<byte[]> getWalkaroundgeneralimages() {
        return walkaroundgeneralimages;
    }

    public void setWalkaroundgeneralimages(ArrayList<byte[]> walkaroundgeneralimages) {
        this.walkaroundgeneralimages = walkaroundgeneralimages;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getWalkAroundNotes() {
        return WalkAroundNotes;
    }

    public void setWalkAroundNotes(ArrayList<String> walkAroundNotes) {
        WalkAroundNotes = walkAroundNotes;
    }

    public ArrayList<String> getWalkAroundType() {
        return WalkAroundType;
    }

    public void setWalkAroundType(ArrayList<String> walkAroundType) {
        WalkAroundType = walkAroundType;
    }

    public ArrayList<CategoryObject> getCategoryObjects() {
        return categoryObjects;
    }

    public void setCategoryObjects(ArrayList<CategoryObject> categoryObjects) {
        this.categoryObjects = categoryObjects;
    }

    public ArrayList<String> getWalkAroundCategoryId() {
        return WalkAroundCategoryId;
    }

    public void setWalkAroundCategoryId(ArrayList<String> walkAroundCategoryId) {
        WalkAroundCategoryId = walkAroundCategoryId;
    }

    public GeneralEquipmentSearchObject getGeneralEquipmentSearchObject() {
        return generalEquipmentSearchObject;
    }

    public void setGeneralEquipmentSearchObject(GeneralEquipmentSearchObject generalEquipmentSearchObject) {
        this.generalEquipmentSearchObject = generalEquipmentSearchObject;
    }

    public int getRentalOrGeneral() {
        return rentalOrGeneral;
    }

    public void setRentalOrGeneral(int rentalOrGeneral) {
        this.rentalOrGeneral = rentalOrGeneral;
    }

    public LinkedHashMap<GeneralEquipmentSearchObject, String> getGeneralcheckListData() {

        if (generalcheckListData == null) {
            generalcheckListData = new LinkedHashMap<>();
        }
        return generalcheckListData;
    }

    public void setGeneralcheckListData(LinkedHashMap<GeneralEquipmentSearchObject, String> generalcheckListData) {
        this.generalcheckListData = generalcheckListData;
    }

    public int getEquipmentTransferHeadObject() {
        return EquipmentTransferHeadObject;
    }

    public void setEquipmentTransferHeadObject(int equipmentTransferHeadObject) {
        EquipmentTransferHeadObject = equipmentTransferHeadObject;
    }

    public int getEquipmentTransferChecklistDetailObject() {
        return EquipmentTransferChecklistDetailObject;
    }

    public void setEquipmentTransferChecklistDetailObject(int equipmentTransferChecklistDetailObject) {
        EquipmentTransferChecklistDetailObject = equipmentTransferChecklistDetailObject;
    }

    public TransferEquipmentSearchObject getTransferEquipment() {
        return TransferEquipment;
    }

    public void setTransferEquipment(TransferEquipmentSearchObject transferEquipment) {
        TransferEquipment = transferEquipment;
    }

    public ArrayList<Custnolist> getCustnolists() {
        return custnolists;
    }

    public void setCustnolists(ArrayList<Custnolist> custnolists) {
        this.custnolists = custnolists;
    }

    public String getSelectedTransferbranch() {
        return SelectedTransferbranch;
    }

    public void setSelectedTransferbranch(String selectedTransferbranch) {
        SelectedTransferbranch = selectedTransferbranch;
    }

    public String getSelectedCustomerBranch() {
        return SelectedCustomerBranch;
    }

    public void setSelectedCustomerBranch(String selectedCustomerBranch) {
        SelectedCustomerBranch = selectedCustomerBranch;
    }

    public String getGeneral_hourmeter() {
        return general_hourmeter;
    }

    public void setGeneral_hourmeter(String general_hourmeter) {
        this.general_hourmeter = general_hourmeter;
    }

    public String getGeneral_fuelmeter() {
        return general_fuelmeter;
    }

    public void setGeneral_fuelmeter(String general_fuelmeter) {
        this.general_fuelmeter = general_fuelmeter;
    }

    public String getGeneral_dueStatus() {
        return general_dueStatus;
    }

    public void setGeneral_dueStatus(String general_dueStatus) {
        this.general_dueStatus = general_dueStatus;
    }

    public String getGeneral_currentStatus() {
        return general_currentStatus;
    }

    public void setGeneral_currentStatus(String general_currentStatus) {
        this.general_currentStatus = general_currentStatus;
    }

    public String getGeneral_subStatus() {
        return general_subStatus;
    }

    public void setGeneral_subStatus(String general_subStatus) {
        this.general_subStatus = general_subStatus;
    }

    public String getGeneral_description() {
        return general_description;
    }

    public void setGeneral_description(String general_description) {
        this.general_description = general_description;
    }

    public String getTransfer_hourMeter() {
        return transfer_hourMeter;
    }

    public void setTransfer_hourMeter(String transfer_hourMeter) {
        this.transfer_hourMeter = transfer_hourMeter;
    }

    public String getTransferIn_out() {
        return transferIn_out;
    }

    public void setTransferIn_out(String transferIn_out) {
        this.transferIn_out = transferIn_out;
    }

    public String getTransferKcustnum() {
        return transferKcustnum;
    }

    public void setTransferKcustnum(String transferKcustnum) {
        this.transferKcustnum = transferKcustnum;
    }

    public String getWalkAroundEquipmentID() {
        return WalkAroundEquipmentID;
    }

    public void setWalkAroundEquipmentID(String walkAroundEquipmentID) {
        WalkAroundEquipmentID = walkAroundEquipmentID;
    }

    public String getRentalEquipId() {
        return RentalEquipId;
    }

    public void setRentalEquipId(String rentalEquipId) {
        RentalEquipId = rentalEquipId;
    }

    public TransferReceiveEquipmentSearchObject getReceiveEquipmentSearchObject() {
        return ReceiveEquipmentSearchObject;
    }

    public void setReceiveEquipmentSearchObject(TransferReceiveEquipmentSearchObject receiveEquipmentSearchObject) {
        ReceiveEquipmentSearchObject = receiveEquipmentSearchObject;
    }

    public String getWalkaroundNotes() {
        return walkaroundNotes;
    }

    public void setWalkaroundNotes(String walkaroundNotes) {
        this.walkaroundNotes = walkaroundNotes;
    }

    public String getRentalIn_Out() {
        return rentalIn_Out;
    }

    public void setRentalIn_Out(String rentalIn_Out) {
        this.rentalIn_Out = rentalIn_Out;
    }

    public String getTemp_Username() {
        return temp_Username;
    }

    public void setTemp_Username(String temp_Username) {
        this.temp_Username = temp_Username;
    }

    public String getTemp_password() {
        return temp_password;
    }

    public void setTemp_password(String temp_password) {
        this.temp_password = temp_password;
    }

//	public String getTemp_Usertoken() {
//		return temp_Usertoken;
//	}
//
//	public void setTemp_Usertoken(String temp_Usertoken) {
//		this.temp_Usertoken = temp_Usertoken;
//	}

    public String getCurrentBranch_TransIn_Out() {
        return currentBranch_TransIn_Out;
    }

    public void setCurrentBranch_TransIn_Out(String currentBranch_TransIn_Out) {
        this.currentBranch_TransIn_Out = currentBranch_TransIn_Out;
    }

    public String getLogin_username() {
        return login_username;
    }

    public void setLogin_username(String login_username) {
        this.login_username = login_username;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getTemp_equipId() {
        return temp_equipId;
    }

    public void setTemp_equipId(String temp_equipId) {
        this.temp_equipId = temp_equipId;
    }

    public String getTemp_userToken() {
        return temp_userToken;
    }

    public void setTemp_userToken(String temp_userToken) {
        this.temp_userToken = temp_userToken;
    }

    public String getCurrentBranch_receive() {
        return currentBranch_receive;
    }

    public void setCurrentBranch_receive(String currentBranch_receive) {
        this.currentBranch_receive = currentBranch_receive;
    }

    public String getCurrentBranch_receive_from() {
        return currentBranch_receive_from;
    }

    public void setCurrentBranch_receive_from(String currentBranch_receive_from) {
        this.currentBranch_receive_from = currentBranch_receive_from;
    }

    public boolean isTransportNeeded() {
        return transportNeeded;
    }

    public void setTransportNeeded(boolean transportNeeded) {
        this.transportNeeded = transportNeeded;
    }

    public ArrayList<ProductSearchObject> getProductSearchObject() {
        return productSearchObject;
    }

    public void setProductSearchObject(ArrayList<ProductSearchObject> productSearchObject) {
        this.productSearchObject = productSearchObject;
    }

    public int getScanNavigation() {
        return ScanNavigation;
    }

    public void setScanNavigation(int scanNavigation) {
        ScanNavigation = scanNavigation;
    }

    public String getResieveSelectedStatus() {
        return ResieveSelectedStatus;
    }

    public void setResieveSelectedStatus(String resieveSelectedStatus) {
        ResieveSelectedStatus = resieveSelectedStatus;
    }

    public String getReceiveSelectedSubStatus() {
        return ReceiveSelectedSubStatus;
    }

    public void setReceiveSelectedSubStatus(String receiveSelectedSubStatus) {
        ReceiveSelectedSubStatus = receiveSelectedSubStatus;
    }

    public int getZbarScan() {
        return ZbarScan;
    }

    public void setZbarScan(int zbarScan) {
        ZbarScan = zbarScan;
    }

    public String getLoginbranch() {
        return loginbranch;
    }

    public void setLoginbranch(String loginbranch) {
        this.loginbranch = loginbranch;
    }

    public String getRefer() {
        return Refer;
    }

    public void setRefer(String refer) {
        Refer = refer;
    }

    public String getTobranch() {
        return tobranch;
    }

    public void setTobranch(String tobranch) {
        this.tobranch = tobranch;
    }

    public String getEdtEquipId() {
        return edtEquipId;
    }

    public void setEdtEquipId(String edtEquipId) {
        this.edtEquipId = edtEquipId;
    }

    public ArrayList<RentalOrderNotesDetailObject> getNotesDetailObjects() {
        return NotesDetailObjects;
    }

    public void setNotesDetailObjects(ArrayList<RentalOrderNotesDetailObject> notesDetailObjects) {
        NotesDetailObjects = notesDetailObjects;
    }

    public ArrayList<Integer> getQtyshipped() {
        return qtyshipped;
    }

    public void setQtyshipped(ArrayList<Integer> qtyshipped) {
        this.qtyshipped = qtyshipped;
    }

    public String getRentalSigneeName() {
        return rentalSigneeName;
    }

    public void setRentalSigneeName(String rentalSigneeName) {
        this.rentalSigneeName = rentalSigneeName;
    }

    public String getGeneralSigneeName() {
        return generalSigneeName;
    }

    public void setGeneralSigneeName(String generalSigneeName) {
        this.generalSigneeName = generalSigneeName;
    }

    public ArrayList<TransportListObject> getTransportTruckList() {
        return transportTruckList;
    }

    public void setTransportTruckList(ArrayList<TransportListObject> transportTruckList) {
        this.transportTruckList = transportTruckList;
    }

    public ArrayList<Double> getArraylatitude() {
        return arraylatitude;
    }

    public void setArraylatitude(ArrayList<Double> arraylatitude) {
        this.arraylatitude = arraylatitude;
    }

    public ArrayList<Double> getArryalongitud() {
        return arryalongitud;
    }

    public void setArryalongitud(ArrayList<Double> arryalongitud) {
        this.arryalongitud = arryalongitud;
    }

    public ArrayList<TransportListObject> getTransportListObjects() {
        return transportListObjects;
    }

    public void setTransportListObjects(ArrayList<TransportListObject> transportListObjects) {
        this.transportListObjects = transportListObjects;
    }

    public TransportListObject getTransportObject() {
        return transportObject;
    }

    public void setTransportObject(TransportListObject transportObject) {
        this.transportObject = transportObject;
    }

    public ArrayList<GetTransportDetailsDescOjbect> getTransportDetailsDescOjbects() {
        return transportDetailsDescOjbects;
    }

    public void setTransportDetailsDescOjbects(ArrayList<GetTransportDetailsDescOjbect> transportDetailsDescOjbects) {
        this.transportDetailsDescOjbects = transportDetailsDescOjbects;
    }

    public int getTransportTransfer() {
        return TransportTransfer;
    }

    public void setTransportTransfer(int transportTransfer) {
        TransportTransfer = transportTransfer;
    }

    public int getTransportOrderNum() {
        return transportOrderNum;
    }

    public void setTransportOrderNum(int transportOrderNum) {
        this.transportOrderNum = transportOrderNum;
    }

    public int getTransportcallNum() {
        return transportcallNum;
    }

    public void setTransportcallNum(int transportcallNum) {
        this.transportcallNum = transportcallNum;
    }

    public int getTransportListIndex() {
        return TransportListIndex;
    }

    public void setTransportListIndex(int transportListIndex) {
        TransportListIndex = transportListIndex;
    }

    public int getKord_num() {
        return kord_num;
    }

    public void setKord_num(int kord_num) {
        this.kord_num = kord_num;
    }

    public String getPrevioushours() {
        return previoushours;
    }

    public void setPrevioushours(String previoushours) {
        this.previoushours = previoushours;
    }

    //	public EquipmentTransferHeadObject getEquipmentTransferHeadObject() {
//		return equipmentTransferHeadObject;
//	}
//
//	public void setEquipmentTransferHeadObject(EquipmentTransferHeadObject equipmentTransferHeadObject) {
//		this.equipmentTransferHeadObject = equipmentTransferHeadObject;
//	}


    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public int getPreviousTabSelection() {
        return previousTabSelection;
    }

    public void setPreviousTabSelection(int previousTabSelection) {
        this.previousTabSelection = previousTabSelection;
    }

    public boolean isOrderFirstload() {
        return orderFirstload;
    }

    public void setOrderFirstload(boolean orderFirstload) {
        this.orderFirstload = orderFirstload;
    }

    public int getScanFlag() {
        return scanFlag;
    }

    public void setScanFlag(int scanFlag) {
        this.scanFlag = scanFlag;
    }

    public boolean isProductSearch() {
        return isProductSearch;
    }

    public void setProductSearch(boolean productSearch) {
        isProductSearch = productSearch;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getInspectionChecklist() {
        return inspectionChecklist;
    }

    public void setInspectionChecklist(String inspectionChecklist) {
        this.inspectionChecklist = inspectionChecklist;
    }

    public String getChecklistid() {
        return checklistid;
    }

    public void setChecklistid(String checklistid) {
        this.checklistid = checklistid;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void clearSessionData(){
        if (instance != null) {
            instance = null;
        }

    }

    public ArrayList<CustomerNameMail> getCustomerNameMails() {
        return customerNameMails;
    }

    public void setCustomerNameMails(ArrayList<CustomerNameMail> customerNameMails) {
        this.customerNameMails = customerNameMails;
    }

    public String getInspectionEquipmentId() {
        return inspectionEquipmentId;
    }

    public void setInspectionEquipmentId(String inspectionEquipmentId) {
        this.inspectionEquipmentId = inspectionEquipmentId;
    }

    public String getStrKcustnum() {
        return strKcustnum;
    }

    public void setStrKcustnum(String strKcustnum) {
        this.strKcustnum = strKcustnum;
    }

    public String getStrCustnum() {
        return strCustnum;
    }

    public void setStrCustnum(String strCustnum) {
        this.strCustnum = strCustnum;
    }

    public String getStrSigntype() {
        return strSigntype;
    }

    public void setStrSigntype(String strSigntype) {
        this.strSigntype = strSigntype;
    }

    public int getTemp_userid() {
        return temp_userid;
    }

    public void setTemp_userid(int temp_userid) {
        this.temp_userid = temp_userid;
    }
}
