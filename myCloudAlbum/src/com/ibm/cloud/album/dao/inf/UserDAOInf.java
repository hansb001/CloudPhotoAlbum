package com.ibm.cloud.album.dao.inf;

import com.ibm.cloud.ablum.exceptions.UserLoginFailedException;
import com.ibm.cloud.ablum.exceptions.UserRegisteredException;
import  com.ibm.cloud.album.beans.UserBean;

/**
 * Interface method for UserRegister
 * @author dengs
 *
 */
public interface UserDAOInf {
	public int registerUser(UserBean user) throws UserRegisteredException;
	public int login(String userName, String password) throws UserLoginFailedException;
	public long getNextUserId();
	public boolean isUserExisted(String email);
	public UserBean querybyEmail(String email);
	
}
