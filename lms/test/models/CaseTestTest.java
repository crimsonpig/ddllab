package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import play.libs.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import play.libs.F.*;
import play.db.jpa.*;


public class CaseTestTest extends WithApplication {

	@Test
	public void getCaseTestsNeedingResults() {
		running(fakeApplication(), new Runnable() {
		   public void run() {
		       JPA.withTransaction(new play.libs.F.Callback0() {
		           public void invoke() {
		        	   List<CaseTest> needsResults = CaseTest.caseTestsNeedingResults();
		        	   assertEquals(3, needsResults.size());
		        	   CaseTest ct = needsResults.get(0);
		        	   TestEntityObject test = ct.getTest();
		        	   assertEquals(101, test.getTestNumber());
		           }
		       });
		   }
		});
	}
	

}
