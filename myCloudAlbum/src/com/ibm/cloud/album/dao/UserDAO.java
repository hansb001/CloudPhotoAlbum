package com.ibm.cloud.album.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.cloud.ablum.exceptions.UserLoginFailedException;
import com.ibm.cloud.ablum.exceptions.UserRegisteredException;
import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.dao.inf.UserDAOInf;
import com.ibm.cloud.album.util.SimplerJDBCHelper;

public class UserDAO implements UserDAOInf {
	private final static int INIT_USER_ID = 1000;

	
	@Override
	public int registerUser(UserBean user) throws UserRegisteredException {
		
		String email = user.getEmail();
		if(isUserExisted(email)){
			throw new UserRegisteredException("Your email address ->" + email+ " was registered, pls login directly!" );
		}
		
		int returnCd = -100;
		//Define the SQL
		String sql = 
		"insert into A_ALBUM.USER"
		+ "(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MOBILE, USER_HOME_PHONE, USER_QQ, USER_NOTE, USER_SELF_DESCRIPTION, USER_BLUEPG_IMG_URL)"
	    + "values (?,?,?,?,?,?,?,?,?)";
		
		
		//Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		//Put user_Id;
		userParamList.add(String.valueOf(getNextUserId()));
		userParamList.add(user.getUserName());
		userParamList.add(user.getEmail());
		userParamList.add(user.getPassword());
		userParamList.add(user.getMobile());
		userParamList.add(user.getHomePhoneNum());
		userParamList.add(user.getQQ());
		userParamList.add(user.getNote());
		userParamList.add(user.getSelfIntroduction());
		userParamList.add(user.getImageBluePgUrl());
		
		
		//Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			returnCd = SimplerJDBCHelper.executeUpdate(userParamList, conn, pstmt);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			//close connections.
			SimplerJDBCHelper.close(conn, pstmt);
		}
		
		return returnCd;
		
	}
	
	@Override
	/**
	 * login code is not 1, user can't login
	 * only when user name and password are well matched in database, system can allow user login.
	 */
	public int login(String userName, String password) throws UserLoginFailedException{
		int loginCd = -100;
		//Define the SQL
		String sql = "select 1 from A_ALBUM.USER WHERE USER_NAME=? AND USER_PASSWORD=?";
			   
		//Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		userParamList.add(userName);
		userParamList.add(password);
	
		//Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(userParamList, conn, pstmt);
			if(result != null && result.next()){
				loginCd = 1;
			}else{
				throw new UserLoginFailedException("Your email and passoword are not matched in the system, please try it again later.");
			}
		}catch(UserLoginFailedException ue){
			throw ue;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			SimplerJDBCHelper.close(conn, pstmt,result);
		}
		
		return loginCd;
		
	}
	
	public long getNextUserId() {
		//Using the system currentTimeMillis and Random prefix and suffix to ensure ID is generated unique.
		long ranNum1 = (long)(Math.random() * 9);
		long ranNum2 = (long)(Math.random() * 9);
		long ranNum = ranNum1 + ranNum2;
				
		long currentTimeMills = System.currentTimeMillis();
		String nextIdStr = String.valueOf(currentTimeMills) + String.valueOf(ranNum);
		long nextId = Long.parseLong(nextIdStr);
		return nextId;
	}

	@Override
	public boolean isUserExisted(String email) {
		boolean returnCode = false;
		//Define the SQL
		String sql = "select 1 from A_ALBUM.USER WHERE USER_EMAIL=? WITH UR";
			   
		//Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		userParamList.add(email);
	
		//Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(userParamList, conn, pstmt);
			if(result != null && result.next()){
				returnCode = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			SimplerJDBCHelper.close(conn, pstmt,result);
		}
		return returnCode;
	
	}

	@Override
	public UserBean querybyEmail(String email) {
		UserBean user = null;
		//Define the SQL
		String sql = "select * from A_ALBUM.USER WHERE USER_EMAIL=? WITH UR";
			   
		//Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		userParamList.add(email);
	
		//Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(userParamList, conn, pstmt);
			if(result != null && result.next()){
				user = new UserBean();
				String userName = result.getString("USER_NAME");
				String userEmail = result.getString("USER_EMAIL");
				String mobile = result.getString("USER_MOBILE");
				String homePhone = result.getString("USER_HOME_PHONE");
				String QQ = result.getString("USER_QQ");
				String note = result.getString("USER_NOTE");
				String selfDesc = result.getString("USER_SELF_DESCRIPTION");
				
				user.setUserName(userName);
				user.setEmail(email);
				user.setMobile(mobile);
				user.setHomePhoneNum(homePhone);
				user.setQQ(QQ);
				user.setNote(note);
				user.setSelfIntroduction(selfDesc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			SimplerJDBCHelper.close(conn, pstmt,result);
		}
		return user;
	}
	
}
