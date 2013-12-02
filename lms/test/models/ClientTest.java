package models;

import models.*;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import play.libs.*;
import java.util.List;
import java.util.Set;
import play.libs.F.*;
import play.db.jpa.*;


public class ClientTest extends WithApplication {
	
	private void setUp() {
		start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
	}

	@Test
	public void createClient() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
						Client rick = new Client();
						rick.setFirst("Rick");
						rick.setLast("Shorewood");
						rick.setCity("Sacramento");
						rick.setState("CA");
						rick.setZip("92525");
						rick.setOfficePhone("(916) 925-9292");
//						rick.setEmailInvoiceOk(true);
						rick.setEmailReportOk(true);
						rick.setPhoneReportOk(true);
						rick.save();
		           }
		       });
		   }
		});
	}
	
	@Test
	public void findExistingClient() {
		running(fakeApplication(), new Runnable() {
			   public void run() {
			       JPA.withTransaction(new play.libs.F.Callback0() {
			           public void invoke() {
							List<Client> isSaulHere = Client.findByFirstAndLastName("Saul", "Goodman");
							
							assertEquals(1,isSaulHere.size());
							Client saul = isSaulHere.get(0);
							assertEquals("riverside",saul.getCity().toLowerCase());
			           }
			       });
			   }
			});
	}

}
