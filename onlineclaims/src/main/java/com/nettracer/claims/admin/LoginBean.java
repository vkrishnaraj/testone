package com.nettracer.claims.admin;

/**
 * @author Utpal Description: This is needed for binding the user credential
 *         with the front end
 */
public class LoginBean {
	 String userName;
	 String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
