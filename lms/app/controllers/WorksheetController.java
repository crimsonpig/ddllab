package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import play.*;
import play.mvc.*;
import static play.data.Form.*;
import play.data.*;
import play.db.jpa.Transactional;
import models.*;
import models.helpers.WorksheetHelper;

@Security.Authenticated(Avocado.class)
public class WorksheetController extends Controller {

    public static Result printWorksheet() {
		if (Avocado.hasRole("manage cases")) {
			return ok(views.html.worksheet.selectTest.render(WorksheetHelper.getTestsNeedingResults()));
		} else {
			return redirect(routes.MainController.returnToDashboard());
		}
    }

    @Transactional
    public static Result getCasesForWorksheet(int testNumber) {
		if (Avocado.hasRole("manage cases")) {
			TestEntityObject theTest = TestEntityObject.findByTestNumber(testNumber);
			if(theTest == null){
				return redirect(routes.MainController.printWorksheet());
			}
			List<CaseTest> caseTests = CaseTest.caseTestsNeedingResults(testNumber);
			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			return ok(views.html.worksheet.worksheet.render(theTest, caseTests, today));
		} else {
			return redirect(routes.MainController.returnToDashboard());
		}
    }

    public static Result enterResults() {
		if (Avocado.hasRole("manage results")) {
			return ok(views.html.worksheet.results_select_test.render(WorksheetHelper.getTestsNeedingResults()));
		} else {
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result getCasesForResults(int testNumber) {
		if (Avocado.hasRole("manage results")) {
			TestEntityObject theTest = TestEntityObject.findByTestNumber(testNumber);
			if(theTest == null){
				return redirect(routes.MainController.enterResults());
			}
			List<CaseTest> caseTests = CaseTest.caseTestsNeedingResults(testNumber);
			return ok(views.html.worksheet.worksheet_overview.render(theTest, caseTests));
		} else {
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result enterResultsForCaseTest(int testNumber){

    	List<CaseTest> cases = CaseTest.caseTestsNeedingResults(testNumber);
    	if(cases.size() < 1){
    		return redirect(routes.MainController.enterResults());
    	}
    	CaseTest ct = cases.get(0);
    	EnterResultsPOJO res = new EnterResultsPOJO();
    	res.setCaseTestPK(ct.getCaseTestPK());
    	Form<EnterResultsPOJO> resForm = form(EnterResultsPOJO.class).fill(res);
    	return ok(views.html.worksheet.enter_results.render(ct, resForm));
    }

    
    
    @Transactional
    public static Result enterComment(int testNumber, long caseTestPK){
    	Form<EnterResultsPOJO> resSent = form(EnterResultsPOJO.class).bindFromRequest();
    	CaseTest ct = CaseTest.findByCaseTestPK(caseTestPK);
    	if(ct == null){
    		return redirect(routes.MainController.enterResults());
    	}
    	if(resSent.hasErrors()){
    		flash("resLenError", "Results should be 8 characters or less!");
    		return badRequest(views.html.worksheet.enter_results.render(ct, resSent));
    	}
    	EnterResultsPOJO res = resSent.get();
    	res.setInformationalComment(ct.getTestDefaultCommentText());
    	Form<EnterResultsPOJO> resComments = form(EnterResultsPOJO.class).fill(res);
    	return ok(views.html.worksheet.enter_comments.render(ct, resComments));
    }

    @Transactional
    public static Result save(int testNumber, long caseTestPK) {
    	Form<EnterResultsPOJO> results = form(EnterResultsPOJO.class).bindFromRequest();
    	CaseTest toUpdate = CaseTest.findByCaseTestPK(caseTestPK);
    	if(results.hasErrors()){
    		return badRequest(views.html.worksheet.enter_comments.render(toUpdate, results));
    	}
    	WorksheetHelper.saveResultsEntry(toUpdate, results.get());
    	return redirect(routes.WorksheetController.enterResultsForCaseTest(testNumber));
    }


    private static void createPDF(){

    }


}
