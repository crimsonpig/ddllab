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

public class DepositControllerTest extends WithApplication {
	

	@Test
	public void viewDeposit() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
						Result result = callAction(
							controllers.routes.ref.DepositController.viewDeposit(1), 
							fakeRequest().withSession("username", "labman")
						);
						assertEquals(200, status(result));
				}
		       });
		   }
		});
	}	
	
	@Test
	public void createDeposit() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
						Result result = callAction(
							controllers.routes.ref.DepositController.createDeposit(1), 
							fakeRequest().withSession("username", "labman")
						);
						assertEquals(200, status(result));
				}
		       });
		   }
		});
	}
	
	@Test
	public void getDepositRefund() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
						Result result = callAction(
							controllers.routes.ref.DepositController.getDepositRefund(1), 
							fakeRequest().withSession("username", "labman")
						);
						assertEquals(200, status(result));
				}
		       });
		   }
		});
	}
	
	@Test
	public void saveDeposit() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
		        	    Map<String, String> depositToSave = new HashMap<String,String>();

		        	    depositToSave.put("amount", "1000");
		        	    depositToSave.put("amountRemaining", "1000");
		        	    depositToSave.put("amountRefunded", "0");
		        	    depositToSave.put("date", "2013-10-20");

//						Client clientBefore = Client.findByClientNumber(1);
//						doing this step will break the client's deposits
//						System.out.println(clientBefore.getClientId()+", "+clientBefore.getDeposits().size());

						Result result = callAction(
							controllers.routes.ref.DepositController.saveDeposit(1), 
							fakeRequest().withSession("username", "labman")
							.withFormUrlEncodedBody(depositToSave)
						);
						assertEquals(200, status(result));
						
						Client clientAfter = Client.findByClientNumber(1);
						assertEquals(2,clientAfter.getDeposits().size());

					}
		       });
		   }
		});
	}
	
	@Test
	public void saveDepositRefund() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
		        	    Map<String, String> refundToSave = new HashMap<String,String>();

		        	    refundToSave.put("refundAmount", "10");
		        	    refundToSave.put("refundDate", "2013-10-21");
		        	    
						Result result = callAction(
							controllers.routes.ref.DepositController.saveDepositRefund(1), 
							fakeRequest().withSession("username", "labman")
							.withFormUrlEncodedBody(refundToSave)
						);
						assertEquals(200, status(result));
					
						Deposit refunded= Deposit.findByDepositNumber(1);
						List<DepositRefund> refunds = refunded.getRefunds();
						//assertEquals(1, refunds.size());
					}
		       });
		   }
		});
	}
}
