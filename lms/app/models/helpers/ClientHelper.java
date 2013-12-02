package models.helpers;

import models.Client;
import java.util.*;

public class ClientHelper {

//	private List<String> errMsg = new LinkedList<String>();
//
//	public static Client getClientForViewing(Client theClient){
//		theClient.setOfficePhone(formatPhone(theClient.getOfficePhone()));
//		theClient.setCellPhone(formatPhone(theClient.getCellPhone()));
//		theClient.setFax(formatPhone(theClient.getFax()));
//		return theClient;
//	}
//	
//	private static final String formatPhone(String number){
//		String toReturn = number;
//		if(number.length() == 10){
//			toReturn = "("+number.substring(0,3)+") "+number.substring(3,6)+"-"+number.substring(6);
//		}
//		else if(number.length() == 7){
//			toReturn = number.substring(0,3)+"-"+number.substring(3);
//		}
//		return toReturn;
//	}
//
//	public boolean hasConstraintViolations(Client toValidate){
//		boolean toReturn = false;
//		int cityLen = 30;
//		int phoneLen = 10;
//		int companyLen = 30;
//		int firstLen = 20;
//		int lastLen = 20;
//		if(toValidate.getCity() == null){
//			errMsg.add("'City' is required");
//		}
//
//		else if(violatesMaxLength(toValidate.getCity(), cityLen)){
//			errMsg.add("'City' must be "+cityLen+" characters or less");
//		}	
//		if(violatesMaxLength(toValidate.getCellPhone(),phoneLen)){
//			errMsg.add("'Cell Phone' must be "+phoneLen+" characters or less");
//		}
//		if(violatesMaxLength(toValidate.getCompany(), companyLen)){
//			errMsg.add("'Company' must be "+companyLen+" characters or less");
//		}
//		if(violatesMaxLength(toValidate.getFax(),10)){
//			errMsg.add("'Fax' must be 10 characters or less");
//		}
//		if(toValidate.getFirst() == null){
//			errMsg.add("First Name is required");
//		}
//		else if(violatesMaxLength(toValidate.getFirst(),20)){
//			errMsg.add("First Name must be 20 characters or less");
//		}
//		if(errMsg.size()>0){toReturn = true;}
//		return toReturn;
//	}
//
//	public List<String> getErrorMessages(){
//		return errMsg;
//	}
//
//	private boolean violatesMaxLength(String string, int length){
//		return string.length() > length;
//	}

}
