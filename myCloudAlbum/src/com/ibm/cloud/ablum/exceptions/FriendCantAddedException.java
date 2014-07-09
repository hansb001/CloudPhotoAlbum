package com.ibm.cloud.ablum.exceptions;

public class FriendCantAddedException extends Exception {
	String exceptionInfo = null;
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public FriendCantAddedException(String exception){
		super();
		setExceptionInfo(exception);
	}
}
