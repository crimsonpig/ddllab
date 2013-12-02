package controllers;

import static play.data.Form.*;
import models.Employee;
import models.EmployeePOJO;
import models.PasswordChangePOJO;
import models.helpers.EmployeeHelper;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

@Security.Authenticated(Avocado.class)
public class EmployeeController extends Controller {
  

//    @Transactional
//    public static Result employee(Integer id){
//        Form<EmployeePOJO> employeeForm = form(EmployeePOJO.class).fill(
//        			EmployeeHelper.getEmployeePOJO(id)
//        );
//        return ok(
//            views.html.employee.employee.render("V", employeeForm)
//        );
//    }
	@Transactional
	public static Result editEmployee(Integer id){
    	if(Avocado.hasRole("admin")){	
		    Form<EmployeePOJO> employeeForm = form(EmployeePOJO.class).fill(
		      			EmployeeHelper.getEmployeePOJO(id)
		    );
		    return ok(
		        views.html.employee.employee.render(id, employeeForm)
		    );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
	}
	
    @Transactional(readOnly=true)
    public static Result employees(){
    	if(Avocado.hasRole("admin")){
	        return ok(
	        	views.html.employee.employees.render(EmployeeHelper.getAll())
	        );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result newEmployee(){
    	if(Avocado.hasRole("admin")){
    		Form<EmployeePOJO> employeeForm = form(EmployeePOJO.class);
	        return ok(
	            views.html.employee.createEmployee.render(employeeForm)
	        );
    	}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result save() {
        Form<EmployeePOJO> employeeForm = form(EmployeePOJO.class).bindFromRequest();
        if(employeeForm.hasErrors()) {
            return badRequest(views.html.employee.createEmployee.render(employeeForm));
        }
        if(Employee.findByUserName(employeeForm.get().getUserName()) != null){
        	flash("userexists", "User Name already exists");
        	return badRequest(views.html.employee.createEmployee.render(employeeForm));
        }
        EmployeeHelper.save(employeeForm.get());
        return redirect(routes.EmployeeController.employees());
    }
    
    @Transactional
    public static Result update(Integer employeeNumber){
        Form<EmployeePOJO> employeeForm = form(EmployeePOJO.class).bindFromRequest();
        if(employeeForm.hasErrors()) {
            return badRequest(views.html.employee.employee.render(employeeNumber, employeeForm));
        }
        EmployeePOJO toUpdate = employeeForm.get();
        Employee existingUserName = Employee.findByUserName(toUpdate.getUserName());
        
        if(existingUserName != null 
        		&& existingUserName.getEmployeeNumber() != employeeNumber){
        	flash("userexists", "User Name already exists");
        	return badRequest(views.html.employee.employee.render(employeeNumber, employeeForm));
        }
        if(Avocado.getCurrentUser().getEmployeeNumber() == employeeNumber 
        		&& !(toUpdate.isAdmin())){
        	flash("adminIssue", "Admin cannot remove their own Admin role!");
        	return badRequest(views.html.employee.employee.render(employeeNumber, employeeForm));  	
        }
        EmployeeHelper.update(employeeNumber, toUpdate);
        return redirect(routes.EmployeeController.employees());
    }
    
    @Transactional
    public static Result editPassword(){
    	int id = Avocado.getCurrentUser().getEmployeeNumber();
	    Form<PasswordChangePOJO> passwordForm = form(PasswordChangePOJO.class).fill(
      			EmployeeHelper.getPasswordChangePOJO(id)
	    );
	    return ok(
	        views.html.employee.changePassword.render(passwordForm)
	    );
    }
    
    @Transactional
    public static Result resetPassword(int id){
    	EmployeeHelper.resetEmployeePassword(id);
    	flash("pwReset", "Password for Employee "+id+" was reset to the default password!");
    	return redirect(routes.EmployeeController.employees());
    }
    
    @Transactional
    	public static Result changePassword(){
    	Form<PasswordChangePOJO> passwordForm = form(PasswordChangePOJO.class).bindFromRequest();
        if(passwordForm.hasErrors()) {
            return badRequest(views.html.employee.changePassword.render(passwordForm));
        }
        PasswordChangePOJO change = passwordForm.get();
        Employee current = Avocado.getCurrentUser(); 
        change.setEmployeeNumber(current.getEmployeeNumber());
        String currentPassword = current.getPassword();
        if(currentPassword != null && !((change.getOldPassword()).equals(currentPassword))){
        	change.setUserName(current.getUserName());
        	change.setNewPassword("");
        	change.setOldPassword("");
        	passwordForm.fill(change);
        	flash("wrongPw", "Incorrect password entered!");
            return badRequest(views.html.employee.changePassword.render(passwordForm));
        }

        if( !(current.hasUserRole("admin")) && 
        		(   (change.getNewPassword() == null) ||
        			(change.getNewPassword().length() == 0)		)  ){
        	change.setUserName(current.getUserName());
        	change.setNewPassword("");
        	change.setOldPassword("");
        	passwordForm.fill(change);
        	flash("noPw", "Password cannot be blank!");
            return badRequest(views.html.employee.changePassword.render(passwordForm));
        }
        EmployeeHelper.updatePassword(change);
        flash("pwChange", "Password changed!");
    	return redirect(routes.Application.logout());
    }
//    
//    public static Result deleteEmployee(Integer id){
//    	return TODO;
//    }
    
    
}
