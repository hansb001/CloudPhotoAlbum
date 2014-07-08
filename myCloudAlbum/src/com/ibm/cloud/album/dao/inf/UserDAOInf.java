package com.ibm.cloud.album.dao.inf;

import  com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.exceptions.UserLoginFailedException;
import com.ibm.cloud.album.exceptions.UserRegisteredException;

/**
 * Interface method for UserRegister
 * @author dengs
 *
 */
public interface UserDAOInf {
	public int registerUser(UserBean user) throws UserRegisteredException;
	public UserBean login(String email, String password) throws UserLoginFailedException;
	public long getNextUserId();
	public boolean isUserExisted(String email);
	public UserBean querybyEmail(String email);
	
}
