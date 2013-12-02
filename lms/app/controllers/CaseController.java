package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import play.*;
import play.mvc.*;
import static play.data.Form.*;
import play.data.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import views.html.helper.form;
import models.*;

@Security.Authenticated(Avocado.class)
public class CaseController extends Controller {
	
    public static Result search(String searchString) {
		if(Avocado.hasRole("manage cases")){
			List<CaseEntityObject> casesFound = new LinkedList<CaseEntityObject>();
			if(SearchTools.isNumber(searchString)){
				casesFound.addAll(CaseEntityObject.searchByCaseNumber(searchString));
			}
			else if(SearchTools.isFormattedFirstAndLast(searchString)){
				String[] firstAndLast = SearchTools.getFormattedFirstAndLast(searchString);
				String first = firstAndLast[0];
				String last = firstAndLast[1];
				casesFound.addAll(CaseEntityObject.findBySubjectFirstAndLastName(first, last));
			}
			else if(SearchTools.isFirstOrLast(searchString)){
				casesFound.addAll(CaseEntityObject.findBySubjectFirstOrLastName(searchString));
			}
			return MainController.showDashboardWithCases(casesFound);
		}
		else{
			return redirect(routes.MainController.returnToDashboard());			
		}
    }

    @Transactional
    public static Result createCase(int clientID) {
    	
    	Client c = Client.findByClientNumber(clientID);
    	if(c == null){
    		return forbidden("\nThe client: " + clientID + " could not be found\n");
    	}else{
    		//get list of tests
    		//List<TestEntityObject> tests = TestEntityObject.getAllTests();
    		
    		//create form object
        	Form<RequisitionPOJO> theForm = form(RequisitionPOJO.class);
        	RequisitionPOJO req = new RequisitionPOJO();
        	req.clientID = "" + clientID;
        	theForm = theForm.fill(req);
        	
        	return ok(views.html.cases.create_case.render(theForm, null));
    	}
    	
    }
    
    @Transactional
    public static Result editCase(String caseNumber) {

    	CaseEntityObject c = CaseEntityObject.findByCaseNumber(caseNumber);
    	if(c == null){
    		return forbidden("\nThe case: " + caseNumber + " could not be found\n");
    	}else{

        	Form<RequisitionPOJO> theForm = form(RequisitionPOJO.class);
        	RequisitionPOJO req = new RequisitionPOJO();
        	req.fillFromEntity(c);
        	theForm = theForm.fill(req);

        	return ok(views.html.cases.edit_case.render(theForm, req.testNumber, null));
    	}
    	
    }    

    @Transactional
    public static Result saveCase() {
    	
    	Form<RequisitionPOJO> theForm = form(RequisitionPOJO.class).bindFromRequest();
    	List<String> err = new LinkedList<String>(); //used to hold a list of custom errors (if any).
    	//make sure everything is peachy with the form.
    	if(theForm.hasErrors()){
    		
    		err.add("There are errors with the submitted form data");
    		return badRequest(views.html.cases.create_case.render(theForm, err));
    	}else{
	    	
	    	RequisitionPOJO req = theForm.get();
	    	RequisitionPOJO.persistRequisition(req, err);
	    	if(err.size() > 0){
				return badRequest(views.html.cases.create_case.render(theForm, err));
			}else{
		    	return redirect(routes.MainController.returnToDashboard());
			}
	    	
    	}
    }
    
    @Transactional
    public static Result updateCase(){
    	Form<RequisitionPOJO> theForm = form(RequisitionPOJO.class).bindFromRequest();
    	List<String> err = new LinkedList<String>(); 
    	if(theForm.hasErrors()){
    		err.add("There are errors with the submitted form data");
    		return badRequest(views.html.cases.edit_case.render(theForm, theForm.get().testNumber, err));
    	}else{
	    	
	    	RequisitionPOJO req = theForm.get();
	    	RequisitionPOJO.updateRequisition(req, err);
	    	if(err.size() > 0){
	    		RequisitionPOJO bad = theForm.get();
	    		List<Integer> tests = bad.testNumber;
				return badRequest(views.html.cases.edit_case.render(theForm, tests , err));
			}else{
		    	return redirect(routes.MainController.searchResults("case", req.caseNumber));
			}
	    	
    	}
    }
    
    @Transactional
    public static Result deleteCase(String caseNumber){
		if (Avocado.hasRole("admin")) {
	    	CaseEntityObject toDelete = CaseEntityObject.findByCaseNumber(caseNumber);
	    	if(toDelete == null){
	    		return redirect(routes.MainController.searchResults("case", caseNumber));
	    	}
	    	toDelete.delete();
		}
    	return redirect(routes.MainController.returnToDashboard());
    }
}
