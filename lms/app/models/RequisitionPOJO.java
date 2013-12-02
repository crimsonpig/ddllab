package models;

//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import play.data.validation.Constraints.*;
import play.db.jpa.Transactional;

public class RequisitionPOJO {
	
	//case data
	public String clientID; //client id#
	public String subjectFirstName;
	public String subjectLastName;
	public String otherIdNumber;
	public String sampleType;
	public Date dateCollected;
	
	@Required
	@MaxLength(7)
	public String caseNumber;
	public Date dateReceived;
	public String caseNote;
	
	
	public String receivedByEmployee; //employee id#
	
	
	//services
	public List<Integer> testNumber;
	
	
	public RequisitionPOJO(){
		
	}
	
	@Transactional
	public static void persistRequisition(RequisitionPOJO req, List<String> err){
		//performs non-trivial input validation, and persists the new requisition
		//if there are no errors.
		
		Employee emp;
    	int empID = -1;
    	
    	Client cli;
    	int cliID = -1;

		
		//check if caseNumber is unique
    	CaseEntityObject newCase = CaseEntityObject.findByCaseNumber(req.caseNumber);
    	if(newCase.getCaseNumber() != null && newCase.getCaseNumber().length() > 0){
    		//a case with this case# already exists
    		err.add("Case#: " + req.caseNumber + " already exists.");
    		return;  //if we don't return here, there is a rollback exception. don't know why.
    	}else{
    		//good to proceed.
    		newCase = new CaseEntityObject();
    		newCase.setCaseNumber(req.caseNumber);
    	}
    	
    	if(req.receivedByEmployee.length() > 0){
    		//check if it's a valid #
    		try{
    			empID = Integer.parseInt(req.receivedByEmployee);
    		}catch (Exception ex){
    			err.add("Receieved by employee id# can only contain digits");
    			empID = -1;
    		}
    		
    		//check if receivedByEmployee is valid #
    		
    		emp = Employee.findById(empID);
    		if((emp != null) && (emp.getEmployeeNumber() == empID)){
    			
    		}else{
    			err.add("Employee: " + req.receivedByEmployee + " was not found.");
    		}
    	}else{
    		err.add("Must enter a \"received by\" employee number");
    		emp = null;
    	}
    	
    	
    	
    	//parse clientID from form.
    	try{
    		cliID = Integer.parseInt(req.clientID);
    	}catch (Exception ex){
    		err.add("Client: " + req.clientID + " not found.");
    	}
    	
    	//link client to case.
    	cli = Client.findByClientNumber(cliID);
    	newCase.setClient(cli);
    	
    	//add case tests
    	List<CaseTest> theTests = new LinkedList<CaseTest>();
    	CaseTest theTest;
    	for(Integer testNum : req.testNumber){
    		if(testNum != null && testNum != -1){
    			
    			TestEntityObject t = TestEntityObject.findByTestNumber((int)testNum);
    			if(t == null){
    				err.add("Test number: " + testNum + " is not valid.");
    			}
    			theTest = new CaseTest();
    			theTest.setTest(t); //link the test to the caseTest
    			theTests.add(theTest);
    			
    			
    		}
    	}
    	newCase.setCaseTests(theTests);
    	
    	
    	//fill in other standard fields for the case from the form.
		newCase.setSubjectFirstname(req.subjectFirstName);
		newCase.setSubjectLastname(req.subjectLastName);
		newCase.setReceivedDate(req.dateReceived);
		newCase.setDateCollected(req.dateCollected);
		newCase.setReceivedByEmployee(emp);
		newCase.setSampleType(req.sampleType);
		newCase.setEmailResultsOk(cli.getEmailReportOk());
		newCase.setAllTasksCompleted(false);
		
		if(req.otherIdNumber != null && (req.otherIdNumber.length() > 0)){
			newCase.setOtherIdNumber(req.otherIdNumber);
		}
		
		if(req.caseNote != null && (req.caseNote.length() > 0)){
			Comment comment = new Comment();
			comment.setCommentText(req.caseNote);
			newCase.setCaseNote(comment);
		}
		

		if(err.size() == 0){
			newCase.save();
		}
	}

	public void fillFromEntity(CaseEntityObject c) {
		clientID = c.getClientNumber()+"";
		subjectFirstName = c.getSubjectFirstname();
		subjectLastName = c.getSubjectLastname();
		otherIdNumber = c.getOtherIdNumber();
		sampleType = c.getSampleType();
		dateCollected = c.getDateCollected();
		caseNumber = c.getCaseNumber();
		dateReceived = c.getReceivedDate();
		caseNote = c.getCaseNoteText();
		if(c.getReceivedByEmployee() != null){
		receivedByEmployee = c.getReceivedByEmployee().getEmployeeNumber()+"";
		}
		Set<Integer> caseTests = c.getCaseTestNumbers();
		testNumber = new ArrayList<Integer>(caseTests.size());
		testNumber.addAll(caseTests);
	}

	public static void updateRequisition(RequisitionPOJO req, List<String> err) {
		//performs non-trivial input validation, and persists the updated requisition
		//if there are no errors.
		
		Employee emp;
    	int empID = -1;
    	
    	Client cli;
    	int cliID = -1;

    	CaseEntityObject existingCase = CaseEntityObject.findByCaseNumber(req.caseNumber);

    	
    	if(req.receivedByEmployee.length() > 0){
    		//check if it's a valid #
    		try{
    			empID = Integer.parseInt(req.receivedByEmployee);
    		}catch (Exception ex){
    			err.add("Receieved by employee id# can only contain digits");
    			empID = -1;
    		}
    		
    		//check if receivedByEmployee is valid #
    		
    		emp = Employee.findById(empID);
    		if((emp == null) || (emp.getEmployeeNumber() != empID)){
    			err.add("Employee: " + req.receivedByEmployee + " was not found.");
    			return;
    		}
    	}else{
    		err.add("Must enter a \"received by\" employee number");
    		emp = null;
    		return;
    	}
    	
    	

    	
    	//add case tests
    	List<CaseTest> theTests = new LinkedList<CaseTest>();
    	if(req.testNumber != null){
        	for(Integer testNum : req.testNumber){
        		if(testNum != null && testNum != -1 && 
        				!(existingCase.getCaseTestNumbers().contains(testNum))){
        			CaseTest theTest;
        			TestEntityObject t = TestEntityObject.findByTestNumber((int)testNum);
        			if(t == null){
        				err.add("Test number: " + testNum + " is not valid.");
        				return;
        			}
        			theTest = new CaseTest();
        			theTest.setTest(t); //link the test to the caseTest
        			theTests.add(theTest);
        			
        			
        		}
        	}
    	}

//    	existingCase.setCaseTests(theTests);
    	existingCase.getCaseTests().addAll(theTests);
    	
    	//fill in other standard fields for the case from the form.
    	existingCase.setSubjectFirstname(req.subjectFirstName);
    	existingCase.setSubjectLastname(req.subjectLastName);
    	existingCase.setReceivedDate(req.dateReceived);
    	existingCase.setDateCollected(req.dateCollected);
    	existingCase.setReceivedByEmployee(emp);
    	existingCase.setSampleType(req.sampleType);
//    	existingCase.setEmailResultsOk(cli.getEmailReportOk());
		
		
		if(req.otherIdNumber != null && (req.otherIdNumber.length() > 0)){
			existingCase.setOtherIdNumber(req.otherIdNumber);
		}
		
		if(req.caseNote != null && (req.caseNote.length() > 0)){
			Comment comment = null;
			if(existingCase.getCaseNote() == null || !(existingCase.getCaseNoteText().equals(req.caseNote))){
				comment = new Comment();
			}
			else{
				comment = existingCase.getCaseNote();
			}
			comment.setCommentText(req.caseNote);
			existingCase.setCaseNote(comment);
		}
		

		if(err.size() == 0){
			existingCase.update(existingCase.getCasePK());
		}
	}
	
	
	
	

}
