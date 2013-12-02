package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import models.*;
import models.helpers.TestHelper;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Avocado.class)
public class TestController extends Controller {

	@Transactional
	public static Result tests() {
    	if(Avocado.hasRole("admin")){
	        return ok(
		        	views.html.test.tests.render(TestHelper.getAllTests())
		    );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
	}
	
	@Transactional
	public static Result newTest() {
    	if(Avocado.hasRole("admin")){	
    		Form<TestPOJO> newTestForm = form(TestPOJO.class);
	        return ok(
		        	views.html.test.test.render("C", newTestForm)
		    );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
	}
	
	@Transactional
	public static Result save() {
		Form<TestPOJO> toSaveForm = form(TestPOJO.class).bindFromRequest();
		if(toSaveForm.hasErrors()){
			return badRequest(views.html.test.test.render("C", toSaveForm));
		}
		else{
			TestPOJO toSave = toSaveForm.get();
			
			TestEntityObject alreadyExists = 
					TestEntityObject.findByTestNumber(toSave.getTestNumber());
			if(alreadyExists != null){
				flash("testexists", "Test "+alreadyExists.getTestNumber()+" already exists!");
				return badRequest(views.html.test.test.render("C", toSaveForm));
			}else{
				TestHelper.savePojo(toSave);
				return redirect(routes.TestController.tests());	
			}
			
		}        
	}
	
	@Transactional
	public static Result edit(int id) {
    	if(Avocado.hasRole("admin")){	
    		Form<TestPOJO> testForm = form(TestPOJO.class)
    				.fill(TestHelper.findByTestNumber(id));
	        return ok(
		        	views.html.test.test.render("E", testForm)
		    );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
	}
	
	@Transactional
	public static Result update(int id) {
		Form<TestPOJO> toUpdateForm = form(TestPOJO.class).bindFromRequest();
		if(toUpdateForm.hasErrors()){
			return badRequest(views.html.test.test.render("E", toUpdateForm));
		}
		TestHelper.update(id, toUpdateForm.get());
        return redirect(routes.TestController.tests());
	}

}
