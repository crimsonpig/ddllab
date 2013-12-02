package models;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.criteria.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.Iterator;
import play.db.jpa.*;

/**
 * The persistent class for the EMPLOYEES database table.
 * 
 */
@Entity
@Table(name="EMPLOYEES")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="employee_number", unique=true, nullable=false)
	private int employeeNumber;

	@Column(nullable=false, length=20)
	private String first;

	@Column(nullable=false, length=20)
	private String last;

	@Column(length=20)
	private String password;

	@Column(nullable=false, length=20)
	private String username;

	//bi-directional many-to-one association to CaseEntityObject
	@OneToMany(mappedBy="receivedByEmployee")
	private List<CaseEntityObject> casesReceived;

	//bi-directional many-to-one association to CaseTest
	@OneToMany(mappedBy="employeeEntered")
	private List<CaseTest> caseTestsEntered;

	//bi-directional many-to-one association to CaseTest
	@OneToMany(mappedBy="employeePerformed")
	private List<CaseTest> caseTestsPerformed;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserRole> userRoles;

	public Employee() {
		userRoles = new LinkedList<UserRole>();
	}

	public int getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public List<CaseEntityObject> getCasesReceived() {
		return this.casesReceived;
	}

	public void setCasesReceived(List<CaseEntityObject> casesReceived) {
		this.casesReceived = casesReceived;
	}

	public List<CaseTest> getCaseTestsEntered() {
		return this.caseTestsEntered;
	}

	public void setCaseTestsEntered(List<CaseTest> caseTestsEntered) {
		this.caseTestsEntered = caseTestsEntered;
	}

	public List<CaseTest> getCaseTestsPerformed() {
		return this.caseTestsPerformed;
	}

	public void setCaseTestsPerformed(List<CaseTest> caseTestsPerformed) {
		this.caseTestsPerformed = caseTestsPerformed;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}
	
	public Set<String> getUserRolesAsStrings() {
		Set<String> roleStrings = new HashSet<String>();
		for(UserRole ur : getUserRoles()){
			roleStrings.add(ur.getRoleName());
		}
		return roleStrings;
	}
	
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles.clear();
		this.userRoles.addAll(userRoles);
//		this.userRoles = userRoles;
	}

	public boolean hasUserRole(String roleName) {
		boolean roleFound = false;
		Iterator<UserRole> rolesIterator = getUserRoles().iterator();
		while(rolesIterator.hasNext() && !roleFound){
			if((rolesIterator.next().getRoleName()).equals(roleName)){
				roleFound = true;
			}
		}
		return roleFound;
	}	
	
	public void addUserRole(String roleName){
		UserRole added = new UserRole(this,roleName);
		userRoles.add(added);
	}

	public static Employee authenticate(String username, String password) {
		Employee found = findByUserName(username);
		if(found != null){
			String foundPw = found.getPassword();
			if(foundPw == null && password.length() > 0){
				found = null;
			}
			else if(foundPw != null && !(foundPw.equals(password))){
				found = null;
			}
		}
		return found;
	}

    public static Employee findById(int id) {
        return JPA.em().find(Employee.class, id);
    }
    

    public static Employee findByUserName(String user) {
		CriteriaBuilder builder = JPA.em().getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		Root<Employee> employee = query.from(Employee.class);
		query.select(employee);
	
		//List<Predicate> predicateList = new LinkedList<Predicate>();
		Predicate predicate = builder.equal(employee.<Employee>get("username"), user);
		//predicateList.add(userNamePredicate);
	
		query.where(predicate);
		
		try{
			return JPA.em().createQuery(query).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
		
//		List<Employee> resultList = JPA.em().createQuery(query).getResultList();
//		Employee found = null;
//		if(resultList.size()>0){
//			found = resultList.get(0);
//		}
//		return found;
	/*
	return JPA.em().createQuery("from Employee where username = ? ")
		.setParameter(1, user)
		.getResultList(); */
    }

    public static List<Employee> findByFirstAndLastName(String first, String last) {
	CriteriaBuilder builder = JPA.em().getCriteriaBuilder();
	CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
	Root<Employee> employee = query.from(Employee.class);
	query.select(employee);

	Predicate predicate = builder.equal(employee.<Employee>get("last"), last);
	predicate = builder.and(predicate, builder.equal(employee.<Employee>get("first"), first));

	query.where(predicate);
	return JPA.em().createQuery(query).getResultList();
    }

    public void update(int employeeNumber) {
    	setEmployeeNumber(employeeNumber);
    	JPA.em().merge(this);

        if(userRoles!=null){
			for(UserRole role : userRoles){
				role.setEmployee(this);
//				JPA.em().merge(role);
			}
        } 	
    }
    
    public void save(){
        JPA.em().persist(this);
        if(userRoles!=null){
			for(UserRole role : userRoles){
				role.setEmployee(this);
//				JPA.em().persist(role);
			}
        }
    }
    
    public void delete() {
    	userRoles.clear();
        JPA.em().remove(this);
    }
    public static List<Employee> all(){
    	return JPA.em().createQuery("from Employee").getResultList();
    }
}
