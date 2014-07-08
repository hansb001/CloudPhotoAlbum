package com.ibm.cloud.album.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibm.cloud.album.beans.CategoryBean;
import com.ibm.cloud.album.beans.PhotoAlbumBean;
import com.ibm.cloud.album.beans.PhotoBean;
import com.ibm.cloud.album.dao.inf.PhotoDaoInf;
import com.ibm.cloud.album.util.SimplerJDBCHelper;

public class PhotoDao implements PhotoDaoInf{
	@Override
	public int addPhoto(long userId, String path, String photoName) {
		String sql = "insert into A_ALBUM.PHOTO_ALBUM (USER_ID, PHOTO_ID, PHOTO_NAME, P_URL, P_DATE_TIME) values (?,?,?,?,?)";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(getNextId()));
		paramList.add(photoName);
		paramList.add(path);
		paramList.add(String.valueOf(new Date(System.currentTimeMillis())));
		
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
	public PhotoBean getPhotoInfoById(long userId, long photoId) {
		// Define the SQL
		String sql = "SELECT PHOTO_ID, PHOTO_NAME, P_DATE_TIME, P_URL from A_ALBUM.PHOTO_ALBUM WHERE USER_ID=? and PHOTO_ID=? WITH UR";

		// Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(photoId));

		// Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		PhotoBean pBean = null;
		try {
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(paramList, conn, pstmt);
			if (result != null && result.next()) {
				long pId = result.getLong("PHOTO_ID");
				String pName = result.getString("PHOTO_NAME");
				Date date = result.getDate("P_DATE_TIME");
				String url = result.getString("P_URL");

				pBean = new PhotoBean();
				pBean.setPhotoId(pId);
				pBean.setUserId(userId);
				pBean.setPhotoDate(date);
				pBean.setPhotoName(pName);
				pBean.setPhotoURL(url);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SimplerJDBCHelper.close(conn, pstmt, result);
		}
		return pBean;
	}

	@Override
	public CategoryBean getCategoryById(long userId, long categoryId) {
		// Define the SQL
		String sql = "SELECT C.CATEGORY_NAME, C.C_DATE_TIME, C.C_DESC, from A_ALBUM.CATEGORY C WHERE C.USER_ID=? AND C.CATEGORY_ID=? WITH UR";
		// Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(categoryId));

		// Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		CategoryBean cBean = null;
		try {
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(paramList, conn, pstmt);
			if (result != null && result.next()) {
				String categoryName = result.getString("CATEGORY_NAME");
				Date date = result.getDate("C_DATE_TIME");
				String desc = result.getString("C_DESC");
				
				//search category relevant photos
				ArrayList cPhotoList = retrievePhotosbyIds(userId, categoryId);
				cBean = new CategoryBean();
				cBean.setCategoryId(categoryId);
				cBean.setUserId(userId);
				cBean.setCategoryName(categoryName);
				cBean.setCategoryDate(date);
				cBean.setCategoryDesc(desc);
				cBean.setPhotoList(cPhotoList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SimplerJDBCHelper.close(conn, pstmt, result);
		}
		return cBean;
	}

	@Override
	public ArrayList retrievePhotosbyIds(long userId, long categoryId){
		// Define the SQL
		String sql = 
				"SELECT A.PHOTO_ID, A.PHOTO_NAME, A.PHOTO_DESC, A.P_DATE_TIME, A.P_URL "
				+ "from A_ALBUM.PHOTO_ALBUM A "
				+ "WHERE PHOTO_ID IN "
				+ "(select C.PHOTO_ID from A_ALBUM.CATEGORY_RELEVANT_PHOTO C WHERE C.USER_ID=? AND C.CATEGORY_ID=?) "
				+ "WITH UR";

		// Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		userParamList.add(String.valueOf(userId));
		userParamList.add(String.valueOf(categoryId));

		// Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		ArrayList resultList = new ArrayList();
		try {
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(userParamList, conn, pstmt);
			while (result != null && result.next()) {
				long pId = result.getLong("PHOTO_ID");
				String pName = result.getString("PHOTO_NAME");
				String pDesc = result.getString("PHOTO_DESC");
				Date date = result.getDate("P_DATE_TIME");
				String url = result.getString("P_URL");

				PhotoBean pBean = new PhotoBean();
				pBean.setPhotoId(pId);
				pBean.setPhotoDate(date);
				pBean.setPhotoDesc(pDesc);
				pBean.setPhotoName(pName);
				pBean.setPhotoURL(url);

				resultList.add(pBean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SimplerJDBCHelper.close(conn, pstmt, result);
		}
				return resultList;
	}
	@Override
	
	public ArrayList retrieveAllPhotosByUserId(long userId) {
		//Define the SQL
		String sql = "SELECT PHOTO_ID, PHOTO_NAME, PHOTO_DESC, P_DATE_TIME, P_URL from A_ALBUM.PHOTO_ALBUM WHERE USER_ID=? WITH UR";
					   
		//Generate the parameters list
		ArrayList<String> userParamList = new ArrayList();
		userParamList.add(String.valueOf(userId));
			
		//Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		ArrayList resultList = new ArrayList();
		try{
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(userParamList, conn, pstmt);
			while(result != null && result.next()){
				long pId = result.getLong("PHOTO_ID");
				String pName = result.getString("PHOTO_NAME");
				String pDesc = result.getString("PHOTO_DESC");
				Date date = result.getDate("P_DATE_TIME");
				String url = result.getString("P_URL");
						
				PhotoBean pBean = new PhotoBean();
				pBean.setPhotoId(pId);
				pBean.setPhotoDate(date);
				pBean.setPhotoDesc(pDesc);
				pBean.setPhotoName(pName);
				pBean.setPhotoURL(url);
						
				resultList.add(pBean);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			SimplerJDBCHelper.close(conn, pstmt,result);
		}
		return resultList;
		
	}

	@Override
	public PhotoAlbumBean retrieveAllPhotosWithCategoriesByUserId(long userId) {
		//Define the SQL
		ArrayList categoryIds = retrieveUserRelevantCategoryIds(userId);
		Iterator iter = categoryIds.iterator();
		PhotoAlbumBean paBean = new PhotoAlbumBean();
		paBean.setUserId(userId);
		Map paMap = new HashMap();
		while(iter.hasNext()){
			long cId = ((Long)iter.next()).longValue();
			ArrayList photos = retrievePhotosbyIds(userId, cId);
			paMap.put(cId, photos);
		}
		paBean.setCategoryPhotoList(paMap);
		return paBean;
		
	}

	@Override
	public ArrayList retrieveUserRelevantCategoryIds(long userId){
		String sql = "select CR.CATEGORY_ID from CATEGORY_RELEVANT_PHOTO CR WHERE CR.USER_ID=?";
		// Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		paramList.add(String.valueOf(userId));

		// Define Connection, statement and resultSet
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		ArrayList resultList = new ArrayList();
		try {
			conn = SimplerJDBCHelper.getConn();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(true);
			result = SimplerJDBCHelper.query(paramList, conn, pstmt);
			while (result != null && result.next()) {
				long cId = result.getLong("CATEGORY_ID");
				resultList.add(Long.valueOf(cId));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SimplerJDBCHelper.close(conn, pstmt, result);
		}
		return resultList;
	}

	
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

	@Override
	public int addCategory(long userId, String categoryName) {
		String sql = "INSERT into A_ALBUM.CATEGORY (CATEGORY_ID, USER_ID, CATEGORY_NAME, CREATE_DATE) values (?,?,?,?)";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(getNextId()));
		paramList.add(String.valueOf(userId));
		paramList.add(categoryName);
		paramList.add(String.valueOf(new Date(System.currentTimeMillis())));
		
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
	public int removePhoto(long userId, long photoId) {
		String sql = "DELETE FROM A_ALBUM.PHOTO_ALBUM WHERE USER_ID=? AND PHOTO_ID=?";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(photoId));
		
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
	public int renamePhotoById(long userId, long photoId,String newName) {
		String sql = "UPDATE A_ALBUM.PHOTO_ALBUM SET PHOTO_NAME=? WHERE USER_ID=? AND PHOTO_ID=?";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(newName);
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(photoId));
		
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
	public int removeCategory(long userId, long categoryId) {
		String sql = "DELETE FROM A_ALBUM.CATEGORY WHERE USER_ID=? AND CATEGORY_ID=?";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(categoryId));
		
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
	public int renameCategoryById(long userId, long categoryId,String newName) {
		String sql = "UPDATE A_ALBUM.CATEGORY SET CATEGORY_NAME=? WHERE USER_ID=? AND CATEGORY_ID=?";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(newName);
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(categoryId));
		
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
			SimplerJDBCHelper.close(conn, pstmt);
		}
		return returnCd;
	}
	@Override
	public int putPhotoUnderCategory(long userId, long categoryId, long photoId){
		String sql = "INSERT into A_ALBUM.CATEGORY_RELEVANT_PHOTO (USER_ID, CATEGORY_ID, PHOTO_ID) values (?,?,?)";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(categoryId));
		paramList.add(String.valueOf(photoId));
		
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
	public int movePhotoBetweenCategories(long userId, long fromCId, long toCId, long photoId){
		String sql = "UPDATE A_ALBUM.CATEGORY_RELEVANT_PHOTO SET CATEGORY_ID=? WHERE USER_ID=? AND CATEGORY_ID=? AND PHOTO_ID=?";
		//Generate the parameters list
		ArrayList<String> paramList = new ArrayList();
		//Put user_Id;
		paramList.add(String.valueOf(toCId));
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(fromCId));
		paramList.add(String.valueOf(photoId));
		
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
}
