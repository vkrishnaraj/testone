package com.nettracer.claims.admin;

/**
 * @author Utpal
 * Description: This is needed for binding the user credential with the front end
 */
public class LoginBean {
	 String userName;
	 String passWord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
