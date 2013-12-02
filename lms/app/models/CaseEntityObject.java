package models;

import java.io.Serializable;
import javax.persistence.*;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;
import play.data.format.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CASES database table.
 * 
 */
@Entity
@Table(name="CASES")
public class CaseEntityObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int case_PK;

	@Column(name="all_tasks_completed", nullable=false)
	private boolean allTasksCompleted;

	@Column(name="case_number", length=7, unique=true)
	@MaxLength(7)
	@Required
	private String caseNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="date_collected")
	private Date dateCollected;

	@Temporal(TemporalType.DATE)
	@Column(name="date_tasks_completed")
	private Date dateTasksCompleted;



	@Column(nullable=false)
	private boolean email_results_OK;


	@Column(name="medical_history_notes", length=100)
	private String medicalHistoryNotes;

	@Column(name="other_id_number", length=10)
	private String otherIdNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "received_date", 
			columnDefinition = "received_date DATE")
	@Formats.DateTime(pattern="yyyy-MM-dd")
	private Date receivedDate;

	@Column(name="received_from")
	private int receivedFrom;

	@Column(name="sample_type", nullable=false, length=10)
	private String sampleType;

	@Column(name="subject_firstname", nullable=false, length=20)
	private String subjectFirstname;

	@Column(name="subject_lastname", nullable=false, length=20)
	private String subjectLastname;

	@Column(name="time_collected")
	private Time timeCollected;



	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="clt_no", nullable=false)
	private Client client;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="ccto_client")
	private Client cctoClient;

	//bi-directional many-to-one association to Comment
//	@ManyToOne(cascade={CascadeType.ALL})
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="note_code")
	private Comment caseNote;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="received_by", nullable=false)
	private Employee receivedByEmployee;

	//bi-directional many-to-one association to CaseTest
	//@OneToMany(mappedBy="caseEntity", fetch=FetchType.EAGER, cascade={CascadeType.MERGE})//mappedBy="caseEntity",
	@OneToMany(mappedBy="caseEntity", cascade=CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name="case_FK")
	private List<CaseTest> caseTests;

	public CaseEntityObject() {

	}



	public int getCasePK() {
		return case_PK;
	}

	public void setCasePK(int case_PK) {
		this.case_PK = case_PK;
	}

	public boolean isAllTasksCompleted() {
		return allTasksCompleted;
	}



	public String getCaseNumber() {
		return caseNumber;
	}



	public Date getDateCollected() {
		return dateCollected;
	}



	public Date getDateTasksCompleted() {
		return dateTasksCompleted;
	}



	public boolean isEmailResultsOk() {
		return email_results_OK;
	}



	public String getMedicalHistoryNotes() {
		return medicalHistoryNotes;
	}



	public String getOtherIdNumber() {
		return otherIdNumber;
	}



	public Date getReceivedDate() {
		return receivedDate;
	}



	public int getReceivedFrom() {
		return receivedFrom;
	}



	public String getSampleType() {
		return sampleType;
	}



	public String getSubjectFirstname() {
		return subjectFirstname;
	}



	public String getSubjectLastname() {
		return subjectLastname;
	}



	public Time getTimeCollected() {
		return timeCollected;
	}



	public Client getClient() {
		return client;
	}

	public Client getCctoClient() {
		return cctoClient;
	}



	public Comment getCaseNote() {
		return caseNote;
	}
	
	public String getCaseNoteText(){
		String toReturn = "";
		if(getCaseNote()!=null){
			toReturn = getCaseNote().getCommentText();
		}
		return toReturn;
	}



	public Employee getReceivedByEmployee() {
		return receivedByEmployee;
	}



	public List<CaseTest> getCaseTests() {
		return caseTests;
	}

	public Set<Integer> getCaseTestNumbers() {
		Set<Integer> caseTestNumbers = new HashSet<Integer>();
		for(CaseTest ct : getCaseTests()){
			caseTestNumbers.add(ct.getTestNumber());
		}
		return caseTestNumbers;
	}

	public void setAllCaseTestsReportedDate(Date date) {
		for(CaseTest ct: getCaseTests()){
			ct.setReportedDate(date);
		}
	}
	
	public void setAllCaseTestsToReported() {
		for(CaseTest ct: getCaseTests()){
			ct.setReported(true);
		}
	}
	
	public void setAllCaseTestsToReported(Date date) {
		for(CaseTest ct: getCaseTests()){
			ct.setReported(true);
			ct.setReportedDate(date);
		}
	}
	
	public Date getLatestReportedDate(){
		Date toReturn = new Date();
		for(CaseTest ct: getCaseTests()){
			toReturn = ct.getReportedDate();
		}
		
		return toReturn;
	}
	
	public void setAllTasksCompleted(boolean allTasksCompleted) {
		this.allTasksCompleted = allTasksCompleted;
	}



	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}



	public void setDateCollected(Date dateCollected) {
		this.dateCollected = dateCollected;
	}



	public void setDateTasksCompleted(Date dateTasksCompleted) {
		this.dateTasksCompleted = dateTasksCompleted;
	}



	public void setEmailResultsOk(boolean email_results_OK) {
		this.email_results_OK = email_results_OK;
	}



	public void setMedicalHistoryNotes(String medicalHistoryNotes) {
		this.medicalHistoryNotes = medicalHistoryNotes;
	}



	public void setOtherIdNumber(String otherIdNumber) {
		this.otherIdNumber = otherIdNumber;
	}



	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}



	public void setReceivedFrom(int receivedFrom) {
		this.receivedFrom = receivedFrom;
	}



	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}



	public void setSubjectFirstname(String subjectFirstname) {
		this.subjectFirstname = subjectFirstname;
	}



	public void setSubjectLastname(String subjectLastname) {
		this.subjectLastname = subjectLastname;
	}



	public void setTimeCollected(Time timeCollected) {
		this.timeCollected = timeCollected;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public void setCctoClient(Client cctoClient) {
		this.cctoClient = cctoClient;
	}



	public void setCaseNote(Comment caseNote) {
		this.caseNote = caseNote;
	}



	public void setReceivedByEmployee(Employee receivedByEmployee) {
		this.receivedByEmployee = receivedByEmployee;
	}



	public void setCaseTests(List<CaseTest> caseTests) {
		this.caseTests = caseTests;
	}

	public int getClientNumber(){
		return getClient().getClientId();
	}
	
	public String getClientFirst(){
		return getClient().getFirst();
	}

	public String getClientLast(){
		return getClient().getLast();
	}
	
	public String getClientOfficePhone(){
		return getClient().getOfficePhone();
	}
	
	public String getClientCellPhone(){
		return getClient().getCellPhone();
	}
	
	public String getClientCompany(){
		String toReturn = getClient().getCompany();
		if(toReturn == null){
			toReturn = "";
		}
		return toReturn;
	}
	
	public String getClientMailingAddress(){
		String toReturn = getClient().getMailingAddress();
		if(toReturn == null){
			toReturn = "";
		}
		return toReturn;
	}
	
	public String getClientCity(){
		String toReturn = getClient().getCity();
		if(toReturn == null){
			toReturn = "";
		}
		return toReturn;
	}
	
	public String getClientState(){
		String toReturn = getClient().getState();
		if(toReturn == null){
			toReturn = "";
		}
		return toReturn;
	}
	
	public String getClientZip(){
		String toReturn = getClient().getZip();
		if(toReturn == null){
			toReturn = "";
		}
		return toReturn;
	}
	

	
	public List<String> getCaseTestsSparse(){
		List<String> sparseTests = new LinkedList<String>();
		for(CaseTest caseTest : this.getCaseTests()){
			sparseTests.add(caseTest.getTestNumber()+" "+caseTest.getTestName());
		}
		return sparseTests;
	}

    public void save(){
    	if(this.getCaseNote() != null){ //save the comment if they entered one.
    		JPA.em().persist(this.getCaseNote());
    	}
    	if(caseTests!=null){
	    	for(CaseTest t : caseTests){
				t.setCaseEntity(this);
			}
    	}
        JPA.em().persist(this);
    }
    
    public void update(int casePK) {
    	setCasePK(casePK);

    	if(caseTests!=null){
	    	for(CaseTest t : caseTests){
				t.setCaseEntity(this);
			}
    	}
    	JPA.em().merge(this);
    }
    
    public static CaseEntityObject findByCasePK(int casePK){
    	return JPA.em().find(CaseEntityObject.class, casePK);
    }
    
    public static CaseEntityObject findByCaseNumber(String caseNumber){
    	List<CaseEntityObject> found = JPA.em().createQuery("from " +
    			"CaseEntityObject where caseNumber = ? ")
    			.setParameter(1, caseNumber)
    			.getResultList();
    	if(found.size()>0){
    		return found.get(0);
    	}else{
    		return new CaseEntityObject();
    	}
    }
    
    public static List<CaseEntityObject> searchByCaseNumber(String caseNumber){
    	return JPA.em().createQuery("from " +
    			"CaseEntityObject where caseNumber LIKE ? ")
    			.setParameter(1, caseNumber+"%")
    			.getResultList();

    }
    
	public static List<CaseEntityObject> findBySubjectFirstAndLastName(
			String first, String last) {
    	return JPA.em().createQuery("from CaseEntityObject where lower(subjectFirstname) LIKE ? " +
    			"AND lower(subjectLastname) LIKE ? ")
    		.setParameter(1, first.toLowerCase()+"%")
    		.setParameter(2, last.toLowerCase()+"%")
    		.getResultList(); 
	}

	public static List<CaseEntityObject> findBySubjectFirstOrLastName(
			String firstOrLast) {
    	String asLowerCase = firstOrLast.toLowerCase() + "%";
    	return JPA.em().createQuery("from CaseEntityObject where lower(subjectFirstname) LIKE ? " +
    			"OR lower(subjectLastname) LIKE ? ")
    		.setParameter(1, asLowerCase)
    		.setParameter(2, asLowerCase)
    		.getResultList();
	}



	public void delete() {
		JPA.em().remove(this);
	}









}
