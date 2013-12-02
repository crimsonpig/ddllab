package controllers;

import play.*;
import play.mvc.*;
import static play.data.Form.*;
import play.data.*;
import play.db.jpa.Transactional;
import models.*;

public class Application extends Controller {

    @Security.Authenticated(Avocado.class)
    public static Result index() {
        return ok(views.html.index.render("Your new application is ready."));
    }


    public static Result login() {
    	return ok(views.html.login.render(form(Login.class)));
    }

    public static Result logout() {
	session().clear();
	flash("success", "You've been logged out");
	return redirect(routes.Application.login());
    }

    @Transactional
    public static Result authenticate() {
	Form<Login> loginForm = form(Login.class).bindFromRequest();

	if(loginForm.hasErrors()) {
		flash("error",loginForm.errorsAsJson().toString().replaceAll("[^0-9a-zA-Z ]", ""));
//		return badRequest(views.html.login.render(loginForm));
		return redirect(routes.Application.login());
	} else {
		session().clear();
		session("username", loginForm.get().username);
		return redirect(
			routes.MainController.returnToDashboard()
		);
	}
    }

  
    public static class Login {
    	public String username;
		public String password;

		public String validate() {
			if (Employee.authenticate(username, password) == null) {
				return "Invalid user or password";
			}
		    return null;
		}
    }  
}
