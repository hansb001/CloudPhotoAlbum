package com.ibm.cloud.album.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ibm.cloud.ablum.exceptions.FriendCantAddedException;
import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.dao.inf.FriendsDAOInf;
import com.ibm.cloud.album.util.SimplerJDBCHelper;

public class FriendDao implements FriendsDAOInf{

	@Override
	public List<UserBean> getFriends(long userId) {
		// Define the SQL
		String sql = 
				"SELECT U.USER_ID, U.USER_NAME, U.USER_EMAIL, U.USER_SELF_DESCRIPTION, U.QQ, U.USER_MOBILE from A_ALBUM.USER U where U.USER_ID "
				+ "in (SELECT F.FRIEND_ID from A_ALBUM.USER U, A_ALBUM.FRIEND F WHERE U.USER_ID=? AND U.USER_ID=F.USER_ID) WITH UR";

				// Generate the parameters list
				ArrayList<String> paramList = new ArrayList();
				paramList.add(String.valueOf(userId));

				// Define Connection, statement and resultSet
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet result = null;
				
				ArrayList<UserBean> resultList = new ArrayList();
				try {
					conn = SimplerJDBCHelper.getConn();
					pstmt = conn.prepareStatement(sql);
					conn.setAutoCommit(true);
					result = SimplerJDBCHelper.query(paramList, conn, pstmt);
					while (result != null && result.next()) {
						long uId = result.getLong("USER_ID");
						String uName = result.getString("USER_NAME");
						String uDesc = result.getString("USER_SELF_DESCRIPTION");
						String uEmail = result.getString("USER_EMAIL");
						String uQQ = result.getString("QQ");
						String uMobile = result.getString("USER_MOBILE");
						
						UserBean user = new UserBean();
						user.setUeserId(uId);
						user.setUserName(uName);
						user.setEmail(uEmail);
						user.setMobile(uMobile);
						user.setQQ(uQQ);
						user.setSelfIntroduction(uDesc);
						
						resultList.add(user);
					
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					SimplerJDBCHelper.close(conn, pstmt, result);
				}
				return resultList;
	}

	@Override
	public int addFriend(long userId, long friendUserId) throws FriendCantAddedException{
		if(isFriendAdded(userId, friendUserId)){
			throw new FriendCantAddedException("FriendUserId->" + friendUserId + " can't be added to user->" + userId + " as a friend, because the friend was already added to the user.");
		}
		
		String sql = "insert into A_ALBUM.FRIEND (USER_ID,FRIEND_ID) values (?,?)";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(friendUserId));
		
		int returnCd = -100;
		//Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			returnCd = SimplerJDBCHelper.executeUpdate(paramList, conn, pstmt);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			//close connections.
			SimplerJDBCHelper.close(conn, pstmt);
		}
		return returnCd;
	}
	@Override
	public boolean isFriendAdded(long userId, long friendUserId) {
		String sql = 
				"select 1 from A_ALBUM.USER U, A_ALBUM.FRIEND F WHERE U.USER_ID=? AND F.FRIEND_ID=? AND U.USER_ID=F.USER_ID AND F.FRIEND_ID=U.USER_ID";
		// Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(friendUserId));

		// Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		boolean isFriendAdded = false;
		try {
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(paramList, conn, pstmt);
			if (result != null && result.next()) {
				isFriendAdded = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SimplerJDBCHelper.close(conn, pstmt, result);
		}
		return isFriendAdded;
	}
	@Override
	public int removeFriend(long userId, long friendUserId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sharePhoto2Friend(long photoId, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sharePhoto2Friend(long[] photoId, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shareCategories2Friend(long categoryId, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shareCategories2Friend(long[] categoryId, long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sharePhoto2Friends(long photoId, long[] userIds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sharePhoto2Friends(long[] photoId, long[] userIds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shareCategories2Friends(long[] categoryId, long[] userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getNextId(){
		//Using the system currentTimeMillis and Random prefix and suffix to ensure ID is generated unique.
		long ranNum1 = (long)(Math.random() * 9);
		long ranNum2 = (long)(Math.random() * 9);
		long ranNum = ranNum1 + ranNum2;
		
		long currentTimeMills = System.currentTimeMillis();
		String nextIdStr = String.valueOf(currentTimeMills) + String.valueOf(ranNum);
		long nextId = Long.parseLong(nextIdStr);
		return nextId;
	}

}
