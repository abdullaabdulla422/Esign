package com.ebs.rental.webutils;

@SuppressWarnings("ALL")
public class EBSRentalConstants {

//	public static String SOAP_BASE_ADDRESS = "http://portal.ebs-next.com/RentalInspection/";
//
//	public static String SOAP_ADDRESS = SOAP_BASE_ADDRESS
//			+ "/ServiceLayer/EbsRentalCheckInServiceLayer.asmx";

	public static String SOAP_BASE_ADDRESS = "http://portal.ebs-next.com/esign/";

	public static String SOAP_ADDRESS = SOAP_BASE_ADDRESS
			+ "/ServiceLayer/EbsRentalCheckInServiceLayer.asmx";

	public static final String NAME_SPACE = "http://portal.ebs-next.com/RentalsInspection/";

	public static final String METHOD_AUTHENTICATE_DEALER = "GetRentaluservalidation_v1";

	public static final String SOAP_ACTION_AUTHENTICATE_DEALER =
			NAME_SPACE
			+ METHOD_AUTHENTICATE_DEALER;

	//GetRentalEquipmentSearch to GetRentalEquipmentSearch_V1
	public static final String METHOD_GET_EQUIPMENT_PARTS = "GetRentalEquipmentSearch_V1";

	public static final String METHOD_GET_RENTALS_CUSTOMER_LIST = "GetRentalsCustomerList";

	public static final String METHOD_RENTALS_LIST_INSPECTION = "RentalsListforInspection";

	public static final String METHOD_SET_RENTAL_CHECKIN = "SetRentalCheckInDetail";

	public static final String METHOD_SET_VISUAL_INSPECTION = "SetRentalVisualInspection";

	public static final String METHOD_SET_RENTAL_CHECKIN_LIST = "GetRentalCheckListItems";

	public static final String METHOD_SET_RENTAL_CHECK_IN = "SetRentalCheckIn";

	public static final String METHOD_SET_RENTAL_IMAGES = "SetRentalCheckInImages";

	public static final String METHOD_SET_RENTAL_CHECKIN_IMAGES = "SetRentalCheckInImages";

	public static final String METHOD_SET_GENERAL_CHECKIN_IMAGES = "SetGeneralCheckInImages";

	public static final String SOAP_ACTION_METHOD_SET_GENERAL_CHECKIN_IMAGES = NAME_SPACE
			+ METHOD_SET_GENERAL_CHECKIN_IMAGES;

	public static final String METHOD_SET_RENTAL_CHECKIN_DETL = "SetRentalCheckIndetl";

	private static final String METHOD_SET_RENTAL_CHECKIN_EQUPMENTS = "SetRentalcheckInEquipments";

	public static final String METHOD_SET_RENTAL_CHECKLIST_PDF = "SetRentalCheckListPdf";

	public static final String METHOD_SET_EQUPMENT_SUB_STATUS = "GetEquipmentsubstatus";

	public static final String METHOD_SET_EQUPMENT_DESCRIPTION = "GetEquipmentsubstatusdesc";

	public static final String METHOD_SET_DEALER_BRANCHES = "GetDealerBranches";
	
	public static final String METHOD_SET_PART_ORDER_TERMS = "GetPartOrderTerms";

	public static final String METHOD_SET_RENTAL_ORDER_CUSTOMER_LIST = "GetRentalorderCustomerList";

	public static final String METHOD_SET_RENTAL_ORDER_LIST = "GetRentalorderList";

	public static final String METHOD_SET_PARTS_ORDER_LIST = "GetPartorderList";

	public static final String METHOD_SET_RENTAL_ORDER_TERMS = "GetRentalOrderTerms";

	//GetRentalorderListDetail Method Was Changed to GetRentalorderListDetailV2
	public static final String METHOD_SET_RENTAL_ORDER_LIST_DETAIL = "GetRentalorderListDetailV2";

	public static final String METHOD_SET_CUSTOMER_MAILS = "GetCustomeremails";

	public static final String METHOD_SET_CUSTOMER_MAILS_V1 = "GetCustomeremails_V1";

	public static final String METHOD_UPDATE_CONTACT_EMAILS = "AddContactEmails";

    //RentalOrderSignedDocumentPdf Method Was Changed to RentalOrderSignedDocumentPdfV3
	public static final String METHOD_RENTAL_ORDER_SIGNED_DOC_PDF = "RentalOrderSignedDocumentPdfV3";

	public static final String METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V4 = "RentalOrderSignedDocumentV4";
	
	public static final String METHOD_RENTAL_EQUIPMENT_DETAIL = "GetEquipmentdetail";
	
	public static final String METHOD_RENTAL_DOCUMENT_DETAIL = "SetRentalDocumentdetail";

	public static final String METHOD_REMOVE_ACTIVE_USER = "RemoveActiveUser";

	public static final String METHOD_GET_CATEGORY_LIST = "GetCategoryList";

	public static final String METHOD_GENERAL_CHECKLIST_PDF = "GeneralCheckListPdf";




	public static final String SOAP_ACTION_METHOD_SET_CUSTOMER_MAILS_V1 = NAME_SPACE +
			METHOD_SET_CUSTOMER_MAILS_V1;


	public static final String SOAP_ACTION_METHOD_GENERAL_CHECKLIST_PDF = NAME_SPACE +
			METHOD_GENERAL_CHECKLIST_PDF;

	public static final String SOAP_ACTION_METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V4 = NAME_SPACE +
			METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V4;

    // SetRentalCheckInEquipmentwithWalkAround has been changed to SetRentalCheckInEquipmentwithWalkAroundV2
	public static final String METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITHWALKAROUND = "SetRentalCheckInEquipmentwithWalkAroundV2";

	public static final String METHOD_GENERAL_EQUIPMENT_SEARCH = "GetGeneralEquipmentSearch";

	public static final String METHOD_EQUIPMENT_TRANSFER_DETAIL_WITH_WALKAROUND ="EquipmentTransferDetailWithWalkAround";

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_TRANSFER_DETAIL_WITH_WALKAROUND = NAME_SPACE +
			METHOD_EQUIPMENT_TRANSFER_DETAIL_WITH_WALKAROUND;

	//EquipmentTransferHead to EquipmentTransferHead_V1
	public static final String METHOD_EQUIPMENT_TRANSFERHEAD = "EquipmentTransferHead_V1";

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERHEAD = NAME_SPACE +
			METHOD_EQUIPMENT_TRANSFERHEAD;

	public static final String METHOD_EQUIPMENT_TRANSFERCHECK_LISTDETAIL = "EquipmentTransferChecklistDetail";

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERCHECK_LISTDETAIL = NAME_SPACE +
			METHOD_EQUIPMENT_TRANSFERCHECK_LISTDETAIL;

	public static final String METHOD_EQUIPMENT_TRANSFERCHECK_LIST_IMAGES = "EquipmentTransferChecklistImages";

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_TRANSFERCHECK_LIST_IMAGES = NAME_SPACE + METHOD_EQUIPMENT_TRANSFERCHECK_LIST_IMAGES;

	public static final String METHOD_TRANSFER_EQUIPMENT_SEARCH = "TransferEquipmentSearch";

	//TransferCheckListPdf to TransferCheckListPdf_V1
	public static final String METHOD_TRANSFER_CHECKLIST_PDF = "TransferCheckListPdf_V1";

	public static final String METHOD_TRANSFER_RECEIVE_EQUIPMENT_SERACH = "TransferReceiveEquipmentSearch";

	public static final String METHOD_EQUIPMENT_TRANSFER_RECEIVE_DETAIL_WITHWALKAROUND = "EquipmentTransferReceiveDetailWithWalkAround";

	public static final String METHOD_SET_GENERAL_CHECKIN_V2 = "SetGeneralCheckInV2";

	public static final String METHOD_SET_RENTAL_CHECKLIST_PDF_V2 = "SetRentalCheckListPdfV2";

	public static final String METHOD_SET_RENTAL_CHECKLIST_PDF_V3 = "SetRentalCheckListPdfV3";

	public static final String METHOD_SET_RENTAL_CHECKIN_V2 = "SetRentalCheckInV2";

	public static final String METHOD_SET_RENTAL_CHECKIN_V3 = "SetRentalCheckInV3";

	public static final String METHOD_SET_RENTAL_CHECKIN_IMAGES_V2 = "SetRentalCheckInImagesV2";

	public static final String METHOD_CROSS_REFERENCE = "CrossReference";

	public static final String METHOD_PRODUCT_SEARCH = "ProductSearch";

	public static final String METHOD_EQUIPMENT_EDITABLES = "EquipmentEditables";

	public static final String METHOD_RENTAL_ORDER_NOTES_DETAIL = "GetRentalorderListDetailNotes";

    public static final String METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V2 = "RentalOrderSignedDocumentPdfV2";

	public static final String METHOD_GET_TRANSPORT_TRUCK_DETAILS = "GetTransportTruckDetails";

//	public static final String METHOD_GET_TRANSPORT_DETAILS = "GetTransportDetails";
	public static final String METHOD_GET_TRANSPORT_DETAILS = "GetTransportDetails_V1";

	public static final String METHOD_GET_TRANSPORT_DETAILS_V2 = "GetTransportDetails_V2";

	//GetTransportDetailsDesc to GetTransportDetailsDesc_V1
	public static final String METHOD_GET_TRANSPORT_DETAILS_DESCRIPTION = "GetTransportDetailsDesc_V1";

	public static final String METHOD_GET_TRANSPORT_DETAILS_FOR_TRANSFER = "GetTransportDetailsForTransfer";

	public static final String METHOD_UPDATE_TRANSPORT_DELIVERY_DET = "UpdateTransportDeliveryDet";

	public static final String METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITH_WALKAROUND_V3 = "SetRentalCheckInEquipmentwithWalkAroundV3";

	public static final String METHOD_RENTAL_ORDER_SIGNED_DOCUMENT_PDF_V4 = "RentalOrderSignedDocumentPdfV4";

	public static final String METHOD_TRANSPORT_DETAILSPDF_V1 = "TransportDetailsPDF_V1";

//	GetTransportationEquipmentsearch
	public static final String METHOD_GET_TRANSPORTATION_EQUIPMENT_SEARCH= "GetTransportationEquipmentsearch";

	public static final String METHOD_GET_INSPECTION_CHECKLISTS= "GetInspectionCheckLists";

	public static final String METHOD_GET_GENERAL_CHECKLIST_ITEMS= "GetGeneralCheckListItems";

	public static final String METHOD_SEND_DISPATCH_NOTIFICATION= "SendDispatchNotification";

	public static final String METHOD_GET_AVAILABLE_PARKING_SPOTS= "GetAvailableParkingSpots";

	public static final String METHOD_GET_AVAILABLE_LISTS_V1= "GetAvailableLists_V1";

	public static final String METHOD_GET_PARKING_SPOT_FOR_EQUIPMENT= "GetParkingSpotForEquipment";


	public static final String SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_FOR_TRANSFER = NAME_SPACE
            + METHOD_GET_TRANSPORT_DETAILS_FOR_TRANSFER;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKLIST_PDF_V3 = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKLIST_PDF_V3;

	public static final String SOAP_ACTION_METHOD_TRANSPORT_DETAILSPDF_V1 = NAME_SPACE
			+ METHOD_TRANSPORT_DETAILSPDF_V1;


	//GetTransportationEquipmentsearch
	public static final String SOAP_ACTION_METHOD_GET_TRANSPORTATION_EQUIPMENT_SEARCH = NAME_SPACE
			+ METHOD_GET_TRANSPORTATION_EQUIPMENT_SEARCH;



	public static final String SOAP_ACTION_METHOD_RENTAL_ORDER_SIGNED_DOCUMENT_PDF_V4 = NAME_SPACE
			+ METHOD_RENTAL_ORDER_SIGNED_DOCUMENT_PDF_V4;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITH_WALKAROUND_V3 = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITH_WALKAROUND_V3;

	public static final String SOAP_ACTION_METHOD_UPDATE_TRANSPORT_DELIVERY_DET = NAME_SPACE
			+ METHOD_UPDATE_TRANSPORT_DELIVERY_DET;

	public static final String SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_DESCRIPTION = NAME_SPACE
			+ METHOD_GET_TRANSPORT_DETAILS_DESCRIPTION;

	public static final String SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS = NAME_SPACE
			+ METHOD_GET_TRANSPORT_DETAILS;

	public static final String SOAP_ACTION_METHOD_GET_TRANSPORT_DETAILS_V2 = NAME_SPACE
			+ METHOD_GET_TRANSPORT_DETAILS_V2;


	public static final String SOAP_ACTION_METHOD_GET_TRANSPORT_TRUCK_DETAILS = NAME_SPACE
			+ METHOD_GET_TRANSPORT_TRUCK_DETAILS;

    public static final String SOAP_ACTION_METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V2 = NAME_SPACE
            + METHOD_RENTAL_ORDER_SIGNED_DOC_PDF_V2;

	public static final String SOAP_ACTION_METHOD_RENTAL_ORDER_NOTES_DETAIL = NAME_SPACE
			+ METHOD_RENTAL_ORDER_NOTES_DETAIL;

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_EDITABLES = NAME_SPACE
			+ METHOD_EQUIPMENT_EDITABLES;

	public static final String SOAP_ACTION_PRODUCT_SEARCH = NAME_SPACE + METHOD_PRODUCT_SEARCH;

	public static final String SOAP_ACTION_CROSS_REFERENCE = NAME_SPACE + METHOD_CROSS_REFERENCE;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_IMAGES_V2 = NAME_SPACE +
			METHOD_SET_RENTAL_CHECKIN_IMAGES_V2;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_V2 = NAME_SPACE +
			METHOD_SET_RENTAL_CHECKIN_V2;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_V3 = NAME_SPACE +
			METHOD_SET_RENTAL_CHECKIN_V3;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKLIST_PDF_V2 = NAME_SPACE +
			METHOD_SET_RENTAL_CHECKLIST_PDF_V2;

	public static final String SOAP_ACTION_METHOD_SET_GENERAL_CHECKIN_V2 = NAME_SPACE +
			METHOD_SET_GENERAL_CHECKIN_V2;

	public static final String SOAP_ACTION_METHOD_EQUIPMENT_TRANSFER_RECEIVE_DETAIL_WITHWALKAROUND = NAME_SPACE +
			METHOD_EQUIPMENT_TRANSFER_RECEIVE_DETAIL_WITHWALKAROUND;

	public static final String SOAP_ACTION_METHOD_TRANSFER_RECEIVE_EQUIPMENT_SERACH = NAME_SPACE +
			METHOD_TRANSFER_RECEIVE_EQUIPMENT_SERACH;

	public static final String SOAP_ACTION_METHOD_TRANSFER_CHECKLIST_PDF = NAME_SPACE +
			METHOD_TRANSFER_CHECKLIST_PDF;

	public static final String SOAP_ACTION_METHOD_TRANSFER_EQUIPMENT_SEARCH = NAME_SPACE +
			METHOD_TRANSFER_EQUIPMENT_SEARCH;

	public static final String SOAP_ACTION_METHOD_GENERAL_EQUIPMENT_SEARCH = NAME_SPACE +
			METHOD_GENERAL_EQUIPMENT_SEARCH;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITHWALKAROUND = NAME_SPACE +
			METHOD_SET_RENTAL_CHECKIN_EQUIPMENT_WITHWALKAROUND;

	public static final String SOAP_ACTION_METHOD_GET_CATEGORY_LIST = NAME_SPACE + METHOD_GET_CATEGORY_LIST;

	public static final String SOAP_ACTION_METHOD_REMOVE_ACTIVE_USER = NAME_SPACE
			+ METHOD_REMOVE_ACTIVE_USER;
	
	public static final String SOAP_ACTION_METHOD_RENTAL_DOCUMENT_DETAIL = NAME_SPACE
			+ METHOD_RENTAL_DOCUMENT_DETAIL;
	
	public static final String SOAP_ACTION_METHOD_RENTAL_EQUIPMENT_DETAIL = NAME_SPACE
			+ METHOD_RENTAL_EQUIPMENT_DETAIL ;

	public static final String SOAP_ACTION_RENTAL_ORDER_SIGNED_DOC_PDF = NAME_SPACE
			+ METHOD_RENTAL_ORDER_SIGNED_DOC_PDF;

	public static final String SOAP_ACTION_SET_CUSTOMER_MAILS = NAME_SPACE
			+ METHOD_SET_CUSTOMER_MAILS;

	public static final String SOAP_ACTION_SET_RENTAL_ORDER_LIST_DETAIL = NAME_SPACE
			+ METHOD_SET_RENTAL_ORDER_LIST_DETAIL;

	public static final String SOAP_ACTION_SET_RENTAL_ORDER_TERMS = NAME_SPACE
			+ METHOD_SET_RENTAL_ORDER_TERMS;
	
	public static final String SOAP_ACTION_SET_PART_ORDER_TERMS = NAME_SPACE
	          + METHOD_SET_PART_ORDER_TERMS;
	
	public static final String SOAP_ACTION_SET_DEALER_BRANCHES = NAME_SPACE
			+ METHOD_SET_DEALER_BRANCHES;

	public static final String SOAP_ACTION_SET_RENTAL_ORDER_CUSTOMER_LIST = NAME_SPACE
			+ METHOD_SET_RENTAL_ORDER_CUSTOMER_LIST;

	public static final String SOAP_ACTION_SET_RENTAL_ORDER_LIST = NAME_SPACE
			+ METHOD_SET_RENTAL_ORDER_LIST;
	
	public static final String SOAP_ACTION_SET_PARTS_ORDER_LIST = NAME_SPACE
			+ METHOD_SET_PARTS_ORDER_LIST;

	public static final String SOAP_ACTION_GET_EQUPMENT_SUB_STATUS = NAME_SPACE
			+ METHOD_SET_EQUPMENT_SUB_STATUS;

	public static final String SOAP_ACTION_GET_EQUIPMENTS_PARTS = NAME_SPACE
			+ METHOD_GET_EQUIPMENT_PARTS;

	public static final String SOAP_ACTION_GET_RENTALS_CUSTOMER_LIST = NAME_SPACE
			+ METHOD_GET_RENTALS_CUSTOMER_LIST;

	public static final String SOAP_ACTION_RENTALS_LIST_INSPECTION = NAME_SPACE
			+ METHOD_RENTALS_LIST_INSPECTION;

	public static final String SOAP_ACTION_SET_RENTAL_IMAGES = NAME_SPACE
			+ METHOD_SET_RENTAL_IMAGES;

	public static final String SOAP_ACTION_SET_RENTAL_CHECKIN = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN;

	public static final String SOAP_ACTION_SET_VISUAL_INSPECTION = NAME_SPACE
			+ METHOD_SET_VISUAL_INSPECTION;

	public static final String SOAP_ACTION_SET_RENTAL_CHECKIN_LIST = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN_LIST;

	public static final String SOAP_ACTION_SET_RENTAL_CHECK_IN = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECK_IN;

	public static final String SOAP_ACTION_METHOD_SET_RENTAL_CHECKIN_IMAGES = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN_IMAGES;

	public static final String SOAP_ACTION_SET_RENTAL_CHECKIN_DETL = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN_DETL;

	public static String SOAP_ACTION_SET_RENTAL_CHECKIN_EQUPMENTS = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKIN_EQUPMENTS;

	public static final String SOAP_ACTION_SET_RENTAL_CHECKLIST_PDF = NAME_SPACE
			+ METHOD_SET_RENTAL_CHECKLIST_PDF;

	public static final String SOAP_ACTION_SET_RENTAL_EQUPMENT_DESCRIPTION = NAME_SPACE
			+ METHOD_SET_EQUPMENT_DESCRIPTION;

	public static final String SOAP_ACTION_GET_INSPECTION_CHECKLISTS = NAME_SPACE
			+ METHOD_GET_INSPECTION_CHECKLISTS;

public static final String SOAP_ACTION_GET_GENERAL_CHECKLIST_ITEMS = NAME_SPACE
			+ METHOD_GET_GENERAL_CHECKLIST_ITEMS;

public static final String SOAP_ACTION_SEND_DISPATCH_NOTIFICATION = NAME_SPACE
			+ METHOD_SEND_DISPATCH_NOTIFICATION;


public static final String SOAP_ACTION_GET_AVAILABLE_PARKING_SPOTS = NAME_SPACE
			+ METHOD_GET_AVAILABLE_PARKING_SPOTS;


public static final String SOAP_ACTION_GET_AVAILABLE_LISTS_V1 = NAME_SPACE
			+ METHOD_GET_AVAILABLE_LISTS_V1;


public static final String SOAP_ACTION_GET_PARKING_SPOT_FOR_EQUIPMENT = NAME_SPACE
			+ METHOD_GET_PARKING_SPOT_FOR_EQUIPMENT;

public static final String SOAP_ACTION_UPDATE_CONTACT_EMAILS = NAME_SPACE
			+ METHOD_UPDATE_CONTACT_EMAILS;

	public static void resetSOAPAddress() {

		SOAP_ADDRESS = SOAP_BASE_ADDRESS
				+ "/ServiceLayer/EbsRentalCheckInServiceLayer.asmx";

	}
}
