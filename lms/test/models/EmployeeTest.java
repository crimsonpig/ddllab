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


public class EmployeeTest extends WithApplication {
	
	private void setUp() {
		start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
	}

	@Test
	public void createAndRetrieveUser() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
				Employee bob = new Employee();
				bob.setFirst("bob");
				bob.setLast("boba");
				bob.setUserName("bb");
				bob.save();

				Employee foundBob = Employee.findByUserName("bb");

				assertEquals("bob",foundBob.getFirst());

				List<Employee> foundBobAgain = Employee.findByFirstAndLastName("bob","boba");
				assertEquals("boba",foundBobAgain.get(0).getLast());
		           }
		       });
		   }
		});
	}

	@Test
	public void addUserRole() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
				Employee labman = Employee.findByUserName("labman");
				assertEquals("Jeff",labman.getFirst());
				assertEquals("Zehnder",labman.getLast());
//				labman.addUserRole("manage clients");
				labman.save();


				Employee jeff = Employee.findByUserName("labman");
				List<UserRole> userRoles = jeff.getUserRoles();
				assertEquals(4, userRoles.size());
				assertEquals(true, jeff.hasUserRole("admin"));
//				assertEquals(true, jeff.hasUserRole("produce quarterly reports"));
				assertEquals(false, jeff.hasUserRole("manage some stuff"));
		           }
		       });
		   }
		});
	}
}
