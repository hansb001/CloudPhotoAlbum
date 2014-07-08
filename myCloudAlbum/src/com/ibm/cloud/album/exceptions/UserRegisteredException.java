
/**
 * @author dengs
 *
 */
package com.ibm.cloud.album.exceptions;

public class UserRegisteredException extends Exception{
	String exceptionInfo = null;
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public UserRegisteredException(String exception){
		super();
		setExceptionInfo(exception);
	}
}