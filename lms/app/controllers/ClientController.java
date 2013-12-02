package controllers;

import java.util.LinkedList;
import java.util.List;



import play.*;
import play.mvc.*;
import static play.data.Form.*;
import play.data.*;
import play.db.jpa.Transactional;
import models.*;

@Security.Authenticated(Avocado.class)
public class ClientController extends Controller {

    public static Result search(String searchString) {

		if(Avocado.hasRole("manage clients")){
			List<Client> clientsFound = new LinkedList<Client>();
			if(SearchTools.isNumber(searchString)){
				int clientNumber = Integer.parseInt(searchString);
				Client client = Client.findByClientNumber(clientNumber);
				if(client!=null){
					clientsFound.add(client);
				}
			}
			else if(SearchTools.isFormattedFirstAndLast(searchString)){
				String[] firstAndLast = SearchTools.getFormattedFirstAndLast(searchString);
				String first = firstAndLast[0];
				String last = firstAndLast[1];
				clientsFound.addAll(Client.findByFirstAndLastName(first, last));
			}
			else if(SearchTools.isFirstOrLast(searchString)){
				clientsFound.addAll(Client.findByFirstOrLastName(searchString));
			}
			return MainController.showDashboardWithClients(clientsFound);
		}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    

    public static Result createClient() {
		if (Avocado.hasRole("manage clients")) {
			return ok(views.html.client.client.render("Create",form(Client.class)));
		} else {
			return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result saveClient() {
		Form<Client> newClientForm = form(Client.class).bindFromRequest();
		if(newClientForm.hasErrors()){
			return badRequest(views.html.client.client.render("Create",newClientForm));
		}else{
			newClientForm.get().save();
	    	return redirect(routes.MainController.returnToDashboard());
		}
    }
    
    @Transactional
    public static Result updateClient(int id) {
		Form<Client> updateClientForm = form(Client.class).bindFromRequest();
		if(updateClientForm.hasErrors()){
			return badRequest(views.html.client.client.render("Edit", updateClientForm));
		}else{
			Client theClient = updateClientForm.get();
			theClient.update(id);
	    	return redirect(routes.MainController.searchResults("client",theClient.getClientId()+""));
		}
    }
    
    
    @Transactional
    public static Result viewClient(int id){
    	Client theClient = Client.findByClientNumber(id);
    	Form<Client> clientForm = form(Client.class).fill(theClient);
    	return ok(views.html.client.client.render("View", clientForm));
    }
    
    @Transactional
    public static Result editClient(int id){
		if (Avocado.hasRole("manage clients")) {
	    	Client theClient = Client.findByClientNumber(id);
	    	Form<Client> clientForm = form(Client.class).fill(theClient);
	    	return ok(views.html.client.client.render("Edit", clientForm));
		}
		else{
			return redirect(routes.MainController.returnToDashboard());
		}
    }

    @Transactional
    public static Result deleteClient(int id){
		if (Avocado.hasRole("admin")) {
	    	Client theClient = Client.findByClientNumber(id);
	    	if(theClient != null){
	    		theClient.delete();	
	    	}
		}
		return redirect(routes.MainController.returnToDashboard());
    }
}
