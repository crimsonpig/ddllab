package controllers;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import play.libs.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import play.libs.F.*;
import play.db.jpa.*;
import com.google.common.collect.ImmutableMap;
import play.*;
import play.mvc.*;

public class ClientControllerTest extends WithApplication {
	

	@Test
	public void createClient() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
						Result result = callAction(
							controllers.routes.ref.MainController.createClient(), 
							fakeRequest().withSession("username", "labman")
						);
						assertEquals(200, status(result));
				   }
		       });
		   }
		});
	}

	@Test
	public void saveClient() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
		        	    Map<String, String> clientToSave = new HashMap<String,String>();
		        	    clientToSave.put("first", "Bud");
		        	    clientToSave.put("last", "Weiser");
		        	    clientToSave.put("city", "San Bernardino");
		        	    clientToSave.put("zip", "95670");
		        	    clientToSave.put("officePhone", "(909) 566-9342");
//		        	    clientToSave.put("emailInvoiceOk", "true");
		        	    clientToSave.put("emailReportOk", "false");
		        	    clientToSave.put("phoneReportOk", "true");
		        	    
						Result result = callAction(
							controllers.routes.ref.ClientController.saveClient(), 
							fakeRequest().withSession("username", "labman")
							.withFormUrlEncodedBody(clientToSave)
						);
						assertEquals(303, status(result));
						
						List<Client> found1 = Client.findByFirstOrLastName("Bud");
						assertEquals(1,found1.size());
						Client bud1 = found1.get(0);
						assertEquals("Bud",bud1.getFirst());
//						assertEquals(true,bud1.getEmailInvoiceOk());
						assertEquals("9095669342",bud1.getOfficePhone());
						
						List<Client> found2 = Client.findByFirstOrLastName("Weiser");
						assertEquals(1,found2.size());
						Client bud2 = found2.get(0);
						assertEquals("Weiser",bud2.getLast());
						assertEquals(false,bud2.getEmailReportOk());
						assertEquals("95670",bud2.getZip());
						assertEquals(null,bud2.getCompany());
						
						List<Client> found3 = Client.findByFirstAndLastName("Bud","Weiser");
						assertEquals(1,found3.size());
						Client bud3 = found3.get(0);
						assertEquals("San Bernardino",bud3.getCity());
						assertEquals(true,bud3.getPhoneReportOk());
				   }
		       });
		   }
		});
	}
}
