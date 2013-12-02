package models;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USER_ROLES database table.
 * 
 */
@Entity
@Table(name="USER_ROLES")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Column(name="role_name", nullable=false, length=64)
//	private String roleName;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="role_PK", unique=true, nullable=false)
//	private int role_PK;
	@EmbeddedId
	private UserRolePK id;
	
	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_number", insertable = false, updatable = false, nullable=false)
	private Employee employee;

	public UserRole() {
		id = new UserRolePK();
	}


	public UserRole(Employee employee, String roleName){
		this();
		setEmployee(employee);
		setRoleName(roleName);
	}
	public String getRoleName() {
//		return this.roleName;
		return id.getRoleName();
	}

	public void setRoleName(String roleName) {
//		this.roleName = roleName;
		id.setRoleName(roleName);
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		this.id.setEmployeeNumber(this.employee.getEmployeeNumber());
	}
	
	@Embeddable
	private static class UserRolePK implements Serializable {
		//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		@Column(name="employee_number")
		private int employeeNumber;

		@Column(name="role_name")
		private String roleName;

		protected UserRolePK() {
		}
		public int getEmployeeNumber() {
			return this.employeeNumber;
		}
		public void setEmployeeNumber(int employeeNumber) {
			this.employeeNumber = employeeNumber;
		}
		public String getRoleName() {
			return this.roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof UserRolePK)) {
				return false;
			}
			UserRolePK castOther = (UserRolePK)other;
			return 
				(this.employeeNumber == castOther.employeeNumber)
				&& this.roleName.equals(castOther.roleName);
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) this.employeeNumber);
			hash = hash * prime + this.roleName.hashCode();
			
			return hash;
		}
	}
}
