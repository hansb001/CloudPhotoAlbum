package com.ibm.cloud.album.dao.inf;

import java.util.ArrayList;

import com.ibm.cloud.album.beans.CategoryBean;
import com.ibm.cloud.album.beans.PhotoAlbumBean;
import com.ibm.cloud.album.beans.PhotoBean;

public interface PhotoDaoInf {
	/**
	 * Add a new photo into Photo_ablum table.
	 * @param userId
	 * @param path
	 * @param photoName
	 * @return sql execution return code
	 */
	public int addPhoto(long userId, String path, String photoName);
	/**
	 * Remove a photo from photo_album table.
	 * @param userId
	 * @param photoId
	 * @return sql execution return code
	 */
	public int removePhoto(long userId, long photoId);
	/**
	 * Update photo name from photo_album table by userId and photoId.
	 * @param userId
	 * @param photoId
	 * @param newName
	 * @return sql execution return code
	 */
	public int renamePhotoById(long userId, long photoId, String newName);
	/**
	 * Retrieve PhotoInformation from photo_album table by userId and photoId
	 * @param userId
	 * @param photoId
	 * @return photoBean with photo information
	 */
	public PhotoBean getPhotoInfoById(long userId, long photoId);
	/**
	 * Add category into CATEGORY table with userId and categoryName information
	 * @param userId
	 * @param categoryName
	 * @return sql execution return code
	 */
	public int addCategory(long userId, String categoryName);
	/**
	 * Remove a category from CATEGORY table by userId and categoryId
	 * @param userId
	 * @param categoryId
	 * @return sql execution return code
	 */
	public int removeCategory(long userId, long categoryId );
	/**
	 * Rename category by categoryId from CATEGORY table.
	 * @param userId
	 * @param categoryId
	 * @param newName
	 * @return sql execution return code
	 */
	public int renameCategoryById(long userId, long categoryId, String newName);
	/**
	 * Retrieve category and category relevant photos, then put category info and relevant photos into categoryBean.
	 * @param userId
	 * @param categoryId
	 * @return categoryBean
	 */
	public CategoryBean getCategoryById(long userId, long categoryId);
	
	/**
	 * Get photos under the userId and categoryId
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	public ArrayList<PhotoBean> retrievePhotosbyIds(long userId, long categoryId);
	/**
	 * Get all the photos under the userId, but it without category.
	 * @param userId
	 * @return photos without category
	 */
	public ArrayList<PhotoBean> retrieveAllPhotosByUserId(long userId);
	/**
	 * PhotoAlbumBean includes a HasMap, the key is category id, the value is the category Id relevant photos.
	 * PhotoAlbumBean includes userId.
	 * @param userId
	 * @return PhotoAlbumBean
	 */
	public PhotoAlbumBean retrieveAllPhotosWithCategoriesByUserId(long userId);
	/**
	 * Retrieve user relevant categoryId from category table by userId
	 * @param userId
	 * @return user relevant categoryId.
	 */
	public ArrayList<Long> retrieveUserRelevantCategoryIds(long userId);
	
	/**
	 * insert userId, photoId and categoryId into CATEGORY_RELEVANT_PHOTO table.
	 * @param userId
	 * @param photoId
	 * @param categoryId
	 * @return SQL execution result
	 */
	public int putPhotoUnderCategory(long userId, long categoryId, long photoId);
	/**
	 * Update CATEGORY_RELEVANT_PHOTO to replace existing category id with a new category id.
	 *
	 * @param userId
	 * @param fromCId
	 * @param toCId
	 * @param photoId
	 * @return SQL execution result
	 */
	public int movePhotoBetweenCategories(long userId, long fromCId, long toCId, long photoId);
	/**
	 * Generate unique primary key for Photo relevant tables.
	 * @return generated primary key.
	 */
	public long getNextId();
}
