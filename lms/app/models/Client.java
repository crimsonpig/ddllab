package models;

import java.io.Serializable;
import javax.persistence.*;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

import java.util.List;


/**
 * The persistent class for the CLIENT database table.
 * 
 */
@Entity
@Table(name="CLIENT")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="client_id", unique=true, nullable=false)
	private int clientId;

	@Column(name="cell_phone", length=10)
	private String cellPhone;

	@Column(nullable=false, length=30)
	@MaxLength(30)
	@Required
	private String city;

	@Column(length=30)
	@MaxLength(30)
	private String company;

//	@Column(nullable=false)
//	private boolean email_invoice_OK;

	@Column(nullable=false)
	private boolean email_report_OK;

	@Column(length=10)
	private String fax;

	@Column(nullable=false, length=20)
	@Required
	@MaxLength(20)
	private String first;


	@Column(nullable=false, length=20)
	@Required
	@MaxLength(20)
	private String last;

	@Column(name="mailing_address", length=50)
	@MaxLength(50)
	private String mailingAddress;

	@Column(length=1)
	@MaxLength(1)
	private String mi;

	@Column(name="office_phone", nullable=false, length=10)
	private String officePhone;

	@Column(nullable=false)
	private boolean phone_report_OK;

	@Column(name="reporting_email", length=30)
	@MaxLength(30)
	private String reportingEmail;

	@Column(length=1)
	@MaxLength(1)
	private String sex;

	@Column(length=2)
	private String state;

	@Column(name="street_address", length=50)
	private String streetAddress;

	@Column(nullable=false, length=10)
	private String zip;

	//bi-directional many-to-one association to CaseEntityObject
	@OneToMany(mappedBy="client")
	private List<CaseEntityObject> cases;

	//bi-directional many-to-one association to CaseEntityObject
	@OneToMany(mappedBy="cctoClient")
	private List<CaseEntityObject> casesCCedOn;



	public Client() {
	}

	private String trimPhoneNumber(String phoneNumber){
		if(!(phoneNumber.matches("[0-9a-zA-Z]"))){
			return phoneNumber.replaceAll("[^0-9a-zA-Z]", "");
		}else{
			return phoneNumber;
		}
	}
	
	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = trimPhoneNumber(cellPhone);
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}



	public boolean getEmailReportOk() {
		return this.email_report_OK;
	}

	public void setEmailReportOk(boolean email_report_OK) {
		this.email_report_OK = email_report_OK;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = trimPhoneNumber(fax);
	}

	public String getFirst() {
		return this.first;
	}

	public void setFirst(String first) {
		this.first = first;
	}


	public String getLast() {
		return this.last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getMailingAddress() {
		return this.mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getMi() {
		return this.mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = trimPhoneNumber(officePhone);
	}

	public boolean getPhoneReportOk() {
		return this.phone_report_OK;
	}

	public void setPhoneReportOk(boolean phone_report_OK) {
		this.phone_report_OK = phone_report_OK;
	}

	public String getReportingEmail() {
		return this.reportingEmail;
	}

	public void setReportingEmail(String reportingEmail) {
		this.reportingEmail = reportingEmail;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<CaseEntityObject> getCases() {
		return this.cases;
	}

	public void setCases(List<CaseEntityObject> cases) {
		this.cases = cases;
	}

	public List<CaseEntityObject> getCasesCCedOn() {
		return this.casesCCedOn;
	}

	public void setCasesCCedOn(List<CaseEntityObject> casesCCedOn) {
		this.casesCCedOn = casesCCedOn;
	}


	
    public void save(){
        JPA.em().persist(this);
    }
    
    public void update(int clientId) {
    	setClientId(clientId);
    	JPA.em().merge(this);
    }

    public void delete(){
    	for(CaseEntityObject en : getCases()){
    		en.delete();
    	}
    	JPA.em().remove(this);
    }
    
    public static Client findByClientNumber(int clientNumber){
    	return JPA.em().find(Client.class, clientNumber);
    }
    
    public static List<Client> findByFirstOrLastName(String firstOrLast){
    	String asLowerCase = firstOrLast.toLowerCase() + "%";
    	return JPA.em().createQuery("from Client where lower(first) LIKE ? " +
    			"OR lower(last) LIKE ? ")
    		.setParameter(1, asLowerCase)
    		.setParameter(2, asLowerCase)
    		.getResultList(); 
    }

    public static List<Client> findByFirstAndLastName(String first, String last){
    	return JPA.em().createQuery("from Client where lower(first) LIKE ? " +
    			"AND lower(last) LIKE ? ")
    		.setParameter(1, first.toLowerCase()+"%")
    		.setParameter(2, last.toLowerCase()+"%")
    		.getResultList(); 
    }
    

}
