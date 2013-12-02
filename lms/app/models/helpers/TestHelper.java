package models.helpers;
import java.util.LinkedList;
import java.util.List;

import models.*;

public class TestHelper {

	private static TestPOJO entityToPojo(TestEntityObject from){
		TestPOJO to = new TestPOJO();
		to.setTestNumber(from.getTestNumber());
		to.setTestName(from.getTestName());
		to.setTestType(from.getTestType());
		to.setTypeOfSample(from.getTypeOfSample());
		to.setUnits(from.getUnits());
		to.setRespicture(from.getRespicture());
		to.setControlResultLine(from.getControlResultLine());
		to.setControlText(from.getControlText());
		to.setResultText(from.getResultText());
		Comment defaultComment = from.getDefaultComment();
		if(defaultComment != null){
			to.setDefaultComment(defaultComment.getCommentText());
		}else{
			to.setDefaultComment("");
		}
		return to;
	}
	
	private static TestEntityObject pojoToEntity(TestPOJO from){
		TestEntityObject to = new TestEntityObject();
		to.setTestNumber(from.getTestNumber());
		to.setTestName(from.getTestName());
		to.setTestType(from.getTestType());
		to.setTypeOfSample(from.getTypeOfSample());
		to.setUnits(from.getUnits());
		to.setRespicture(from.getRespicture());
		to.setControlResultLine(from.getControlResultLine());
		to.setControlText(from.getControlText());
		to.setResultText(from.getResultText());
		String defaultComment = from.getDefaultComment();
		if(!(defaultComment.equals(""))){
			Comment defComment = new Comment();
			defComment.setCommentText(defaultComment);
			to.setDefaultComment(defComment);
		}
		return to;
	}

	public static void savePojo(TestPOJO toSave) {
		TestEntityObject save = pojoToEntity(toSave);
		save.save();
	}

	public static TestPOJO findByTestNumber(int id) {
		return entityToPojo(TestEntityObject.findByTestNumber(id));
	}

	public static void update(int id, TestPOJO testPOJO) {
		TestEntityObject toUpdate = pojoToEntity(testPOJO);
		toUpdate.update(id);
	}

	public static List<TestPOJO> getAllTests() {
		List<TestPOJO> tests = new LinkedList<TestPOJO>();
		List<TestEntityObject> entities = TestEntityObject.getAllTests();
		for(TestEntityObject test : entities){
			tests.add(entityToPojo(test));
		}
		return tests;
	}
}
