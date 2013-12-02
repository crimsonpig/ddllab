package controllers;

import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Avocado extends Security.Authenticator {
	
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("username");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}

	public static boolean hasRole(String userRole) {
//		Employee found = Employee.findByUserName(Context.current().request().username());
		Employee found = getCurrentUser();
		boolean hasRole = false;
		if(found!=null && found.hasUserRole(userRole)){
			hasRole = true;
		}
		return hasRole;
	}

	public static Employee getCurrentUser() {
		return Employee.findByUserName(Context.current().request().username());
	}
}
