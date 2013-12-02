package models.helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.Employee;
import models.EmployeePOJO;
import models.PasswordChangePOJO;
import models.UserRole;

public class EmployeeHelper {
	private static final String DEFAULT_PASSWORD = "ddl";
	private static final String ADMIN_ROLE = "admin";
	private static final String CLIENT_ROLE = "manage clients";
	private static final String CASES_ROLE = "manage cases";
	private static final String RESULTS_ROLE = "manage results";
	
	public static EmployeePOJO getEmployeePOJO(int findById) {
		return employeeToPOJO(Employee.findById(findById));
	}

	private static EmployeePOJO employeeToPOJO(Employee employee){
		EmployeePOJO toReturn = new EmployeePOJO();
		if(employee!=null){
			toReturn.setEmployeeNumber(employee.getEmployeeNumber());
			toReturn.setFirst(employee.getFirst());
			toReturn.setLast(employee.getLast());
			toReturn.setUserName(employee.getUserName());
			Set<String> roles = employee.getUserRolesAsStrings();
			toReturn.setAdmin(roles.contains(ADMIN_ROLE));
			toReturn.setManageClients(roles.contains(CLIENT_ROLE));
			toReturn.setManageCases(roles.contains(CASES_ROLE));
			toReturn.setManageCaseResults(roles.contains(RESULTS_ROLE));
		}
		return toReturn;
	}
	
	public static List<EmployeePOJO> getAll() {
		List<Employee> allEmployees = Employee.all();
		List<EmployeePOJO> employeesAsPOJOs = new LinkedList<EmployeePOJO>();
		for(Employee employee : allEmployees){
			employeesAsPOJOs.add(employeeToPOJO(employee));
		}
		return employeesAsPOJOs;
	}

	
	
	public static void save(EmployeePOJO employeePOJO) {
		Employee toSave = new Employee();
		toSave.setFirst(employeePOJO.getFirst());
		toSave.setLast(employeePOJO.getLast());
		toSave.setUserName(employeePOJO.getUserName());
		toSave.setPassword(DEFAULT_PASSWORD);
		toSave.setUserRoles(booleansToUserRoles(employeePOJO));
		toSave.save();
	}
	
	public static void update(int employeeNumber, EmployeePOJO employeePOJO) {
		Employee toUpdate = Employee.findById(employeeNumber);

		toUpdate.setFirst(employeePOJO.getFirst());
		toUpdate.setLast(employeePOJO.getLast());
		toUpdate.setUserName(employeePOJO.getUserName());
		List<UserRole> newRoles = booleansToUserRoles(employeePOJO);
		for(UserRole role : newRoles){
			role.setEmployee(toUpdate);
		}
		toUpdate.setUserRoles(newRoles);
		toUpdate.update(employeeNumber);
	}
	
	private static List<UserRole> booleansToUserRoles(EmployeePOJO employeePOJO){
		List<UserRole> userRoles = new LinkedList<UserRole>();
		if(employeePOJO.isAdmin()){
			UserRole admin = new UserRole();
			admin.setRoleName(ADMIN_ROLE);
			userRoles.add(admin);
		}
		if(employeePOJO.isManageClients()){
			UserRole clients = new UserRole();
			clients.setRoleName(CLIENT_ROLE);
			userRoles.add(clients);
		}
		if(employeePOJO.isManageCases()){
			UserRole cases = new UserRole();
			cases.setRoleName(CASES_ROLE);
			userRoles.add(cases);
		}
		if(employeePOJO.isManageCaseResults()){
			UserRole results = new UserRole();
			results.setRoleName(RESULTS_ROLE);
			userRoles.add(results);
		}
		return userRoles;
	}

	public static PasswordChangePOJO getPasswordChangePOJO(int id) {
		Employee user = Employee.findById(id);
		PasswordChangePOJO passwordPojo = new PasswordChangePOJO();
		passwordPojo.setEmployeeNumber(user.getEmployeeNumber());
		passwordPojo.setUserName(user.getUserName());
		passwordPojo.setNewPassword("");
		passwordPojo.setOldPassword(null);
		return passwordPojo;
	}

	public static void updatePassword(PasswordChangePOJO change) {
		Employee userToChange = Employee.findById(change.getEmployeeNumber());
		String newPassword = change.getNewPassword();
		if(newPassword.equals("null") && userToChange.getUserRolesAsStrings().contains(ADMIN_ROLE)){
			newPassword = null;
		}
		userToChange.setPassword(newPassword);
		userToChange.update(change.getEmployeeNumber());
	}

	public static void resetEmployeePassword(int id) {
		Employee toReset = Employee.findById(id);
		toReset.setPassword(DEFAULT_PASSWORD);
		toReset.update(id);
	}
}
