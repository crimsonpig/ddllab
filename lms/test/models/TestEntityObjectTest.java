package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import play.libs.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import play.libs.F.*;
import play.db.jpa.*;

public class TestEntityObjectTest extends WithApplication{

	private void setUp() {
		start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
	}
	
	@Test
	public void getAllTests(){
		running(fakeApplication(), new Runnable() {
			   public void run() {
			       JPA.withTransaction(new play.libs.F.Callback0() {
			           public void invoke() {
					TestEntityObject t1 = new TestEntityObject();
					t1.setTestNumber(201);
					t1.setTestName("alcohol");
					t1.setTestType("t");
//					t1.setPrice(new BigDecimal(25.5));
//					t1.setPriceType("f");
					t1.save();
					
					TestEntityObject t2 = new TestEntityObject();
					t2.setTestNumber(202);
					t2.setTestName("alcohol");
					t2.setTestType("T");
//					t2.setPrice(new BigDecimal(30.5));
//					t2.setPriceType("f");
					t2.save();

					List<TestEntityObject> tests = TestEntityObject.getAllTests();

					//tests.size == 4 because there are already 2 tests in the table.
					assertEquals(4, tests.size());
			           }
			       });
			   }
			});
	}

	@Test
	public void createTestWithDefaultComment(){
		running(fakeApplication(), new Runnable() {
			   public void run() {
			       JPA.withTransaction(new play.libs.F.Callback0() {
			           public void invoke() {
							TestEntityObject t1 = new TestEntityObject();
							t1.setTestNumber(203);
							t1.setTestName("hydrocodone");
							t1.setTestType("T");
							Comment newComment = new Comment();
							newComment.setCommentText("This is a comment");
							t1.setDefaultComment(newComment);
							t1.save();
							
							TestEntityObject t1f = TestEntityObject.findByTestNumber(203);
							assertNotNull(t1f.getDefaultComment());
							assertEquals(t1f.getDefaultComment().getCommentText(),"This is a comment");
			           }
			       });
			   }
			});
	}
}
