package models;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import play.data.validation.Constraints.*;
import play.db.jpa.JPA;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TEST database table.
 * 
 */
@Entity
@Table(name="TEST")
public class TestEntityObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Required
	@Column(name="test_number", unique=true, nullable=false)
	private int testNumber;

	@Column(name="control_result_line", length=10)
	@MaxLength(10)
	private String controlResultLine;

	@Column(name="control_text", length=500)
	@MaxLength(500)
	private String controlText;
	
	@Column(name="result_text", length=500)
	@MaxLength(500)
	private String resultText;

	@Column(length=8)
	@MaxLength(8)
	private String respicture;

	@Column(name="test_name", nullable=false, length=30)
	@MaxLength(30)
	@Required
	private String testName;

	//Acceptable values = T or C
	@Column(name="test_type", nullable=false, length=1)
	@MaxLength(1)
	@Required
	private String testType;

	@Column(name="type_of_sample", length=10)
	@MaxLength(10)
	private String typeOfSample;

	@Column(length=10)
	@MaxLength(10)
	private String units;

	//bi-directional many-to-one association to CaseTest
	@OneToMany(mappedBy="test", cascade = CascadeType.ALL)
	private List<CaseTest> caseTests;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="associatedTest", cascade = CascadeType.ALL)
	private List<Comment> associatedComments;

	//bi-directional many-to-one association to Comment
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="default_comment")
	private Comment defaultComment;

	public TestEntityObject() {
	}



	public int getTestNumber() {
		return testNumber;
	}



	public String getControlResultLine() {
		return controlResultLine;
	}



	public String getControlText() {
		return controlText;
	}



	public String getResultText() {
		return resultText;
	}



	public String getRespicture() {
		return respicture;
	}



	public String getTestName() {
		return testName;
	}



	public String getTestType() {
		return testType;
	}



	public String getTypeOfSample() {
		return typeOfSample;
	}



	public String getUnits() {
		return units;
	}



	public List<CaseTest> getCaseTests() {
		return caseTests;
	}



	public int getNumberOfCaseTests(){
		int toReturn = 0;
		if(getCaseTests() != null){
			toReturn = getCaseTests().size();
		}
		return toReturn;
	}
	

	public List<Comment> getAssociatedComments() {
		return associatedComments;
	}



	public Comment getDefaultComment() {
		return defaultComment;
	}



	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}



	public void setControlResultLine(String controlResultLine) {
		this.controlResultLine = controlResultLine;
	}



	public void setControlText(String controlText) {
		this.controlText = controlText;
	}



	public void setResultText(String resultText) {
		this.resultText = resultText;
	}



	public void setRespicture(String respicture) {
		this.respicture = respicture;
	}



	public void setTestName(String testName) {
		this.testName = testName;
	}



	public void setTestType(String testType) {
		this.testType = testType;
	}



	public void setTypeOfSample(String typeOfSample) {
		this.typeOfSample = typeOfSample;
	}



	public void setUnits(String units) {
		this.units = units;
	}



	public void setCaseTests(List<CaseTest> caseTests) {
		this.caseTests = caseTests;
	}



	public void setAssociatedComments(List<Comment> associatedComments) {
		this.associatedComments = associatedComments;
	}



	public void setDefaultComment(Comment defaultComment) {
		this.defaultComment = defaultComment;
	}


	
	public static List<TestEntityObject> getAllTests(){
		//get all tests from the database;
		CriteriaBuilder builder = JPA.em().getCriteriaBuilder();
		CriteriaQuery<TestEntityObject> query = builder.createQuery(TestEntityObject.class);
		Root<TestEntityObject> tests = query.from(TestEntityObject.class);
		query.select(tests);
	
		List<TestEntityObject> resultList = JPA.em().createQuery(query).getResultList();
		return resultList;
    	//return JPA.em().createQuery("from Test").getResultList();
	}
	
	public static List<TestEntityObject> getLabTests(){
		String query = "from TestEntityObject t " +
				"where t.testType = 'T' ";
		return JPA.em().createQuery(query).getResultList();
	}
	
	public static TestEntityObject findByTestNumber(int tnum){
		return JPA.em().find(TestEntityObject.class, tnum);
	}
	
	public void save(){
		merge();
    }
	
	public void merge(){
		JPA.em().merge(this);
	}
	
	public void update(int testNumber){
		setTestNumber(testNumber);
		JPA.em().merge(this);
	}

}
