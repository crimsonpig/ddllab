package models.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import controllers.Avocado;

import play.db.jpa.JPA;
import models.CaseTest;
import models.CaseTestResultsComments;
import models.Comment;
import models.EnterResultsPOJO;
import models.TestEntityObject;
import models.SelectTestPOJO;

public class WorksheetHelper {

	public static List<SelectTestPOJO> getTestsNeedingResults(){
		List<SelectTestPOJO> toReturn = new LinkedList<SelectTestPOJO>();
		String query = "select t.testNumber, t.testName, " +
				"t.typeOfSample, count(t.testNumber) " +
				"from TestEntityObject t " +
				"inner join t.caseTests as ct " +
				"with ct.resultsEntered = 0 " +
				"where t.testType = 'T' " +
				"group by t.testNumber " +
				"having count(t.testNumber) > 0 " +
				"order by t.testNumber ASC ";
	
		List<Object[]> resultList = JPA.em().createQuery(query).getResultList();
		for(Object[] obj : resultList){
			SelectTestPOJO test = new SelectTestPOJO();
			test.setTestNumber((Integer)obj[0]);
			test.setTestName((String)obj[1]);
			test.setTypeOfSample((String)obj[2]);
			test.setNumberOfCases((Long)obj[3]);
			toReturn.add(test);
		}
		return toReturn;
	}

	public static void saveResultsEntry(CaseTest toUpdate,
			EnterResultsPOJO enterResultsPOJO) {
		CaseTestResultsComments resComms = new CaseTestResultsComments();
		
		Comment actualComment = new Comment();
		actualComment.setCommentText(enterResultsPOJO.getActualComment());
		resComms.setActualComment(actualComment);
		String infoCommentText = enterResultsPOJO.getInformationalComment();
		if(infoCommentText.equals("")){
			resComms.setInformationalComment(null);
		}
		else if(!(infoCommentText
				.equals(toUpdate.getTestDefaultCommentText()))){
			Comment infoComment = new Comment();
			infoComment.setCommentText(infoCommentText);
			
			resComms.setInformationalComment(infoComment);
		}else{
			Comment defaultComm = toUpdate.getTestDefaultComment();
			resComms.setInformationalComment(defaultComm);
		}
		resComms.setResults(enterResultsPOJO.getResults());
		resComms.setEmployeeEntered(Avocado.getCurrentUser());
		resComms.setCaseTestFK(toUpdate.getCaseTestPK());
		toUpdate.setResultsAndComments(resComms);
		toUpdate.setResultsEntered(true);
		toUpdate.setDateCompleted(new Date(Calendar.getInstance().getTimeInMillis()));
		toUpdate.update();
	
	}
	
	



}
