package com.ibm.cloud.album.dao.inf;

import java.util.List;

import com.ibm.cloud.album.beans.UserBean;
import com.ibm.cloud.album.exceptions.FriendCantAddedException;

public interface FriendsDAOInf {
	public List<UserBean> getFriends(long userId);
	public int addFriend (long userId, long friendUserId) throws FriendCantAddedException;
	public int removeFriend(long userId, long friendUserId);
	public boolean isFriendAdded(long userId, long friendUserId);
	
	public int sharePhoto2Friend(long photoId, long userId);
	public int sharePhoto2Friend(long[] photoId, long userId);
	
	public int shareCategories2Friend(long categoryId, long userId);
	public int shareCategories2Friend(long[] categoryId, long userId);
	
	public int sharePhoto2Friends(long photoId, long[] userIds);
	public int sharePhoto2Friends(long[] photoId, long[] userIds);
	public int shareCategories2Friends(long[] categoryId, long[] userId);
	
	public long getNextId();
	
}
