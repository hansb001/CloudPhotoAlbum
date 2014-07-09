package com.ibm.cloud.ablum.exceptions;

public class UserLoginFailedException extends Exception {
	String exceptionInfo = null;
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public UserLoginFailedException(String exception){
		super();
		setExceptionInfo(exception);
	}
}
