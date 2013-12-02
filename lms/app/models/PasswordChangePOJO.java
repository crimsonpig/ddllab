package models;

public class PasswordChangePOJO {
	private int employeeNumber;
	private String userName;
	private String oldPassword;
	private String newPassword;
	public PasswordChangePOJO(){}
	public String getUserName() {
		return userName;
	}
	public void setEmployeeNumber(int id){
		this.employeeNumber = id;
	}
	public int getEmployeeNumber(){
		return employeeNumber;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
