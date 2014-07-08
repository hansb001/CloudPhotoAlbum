package com.ibm.cloud.album.beans;

public class UserBean {
	private long ueserId;
	private String userSN = null;
	private String imageBluePgUrl = null;
	public String getImageBluePgUrl() {
		return imageBluePgUrl;
	}
	public void setImageBluePgUrl(String imageBluePgUrl) {
		this.imageBluePgUrl = imageBluePgUrl;
	}
	public String getUserSN() {
		return userSN;
	}
	public void setUserSN(String userSN) {
		this.userSN = userSN;
	}
	private String userName = null;
	private String password = null;
	private String email = null;
	private String mobile = null;
	private String homePhoneNum = null;
	private String QQ = null;
	private String selfIntroduction = null;
	private String note = null;
	public long getUserId() {
		return ueserId;
	}
	public void setUeserId(long ueserId) {
		this.ueserId = ueserId;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHomePhoneNum() {
		return homePhoneNum;
	}
	public void setHomePhoneNum(String homePhoneNum) {
		this.homePhoneNum = homePhoneNum;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
