package com.web.admin.db;

import java.util.HashMap;
import java.util.LinkedList;

import com.CheckUtil;
import com.db.SQLClient;

public class VideoDB {

	private SQLClient sqlClient;

	public VideoDB(SQLClient sqlClient) {
		this.sqlClient = sqlClient;
		sqlClient.setDebug(true);
	}


	/**
	 * Video列表
	 * @param videoTypeId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> findVideoList(String videoTypeId,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.Video.ID AS videoId, ");
		sql.append("   ss.Video.`Name` AS videoName, ");
		sql.append("   ss.Video.`Desc` AS videoDesc, ");
		sql.append("   ss.Video.Pic AS videoPic, ");
		sql.append("   ss.Video.Address AS address, ");
		sql.append("   ss.Video.CreateDate AS createDate, ");
		sql.append("   ss.Manage.ID AS userId, ");
		sql.append("   ss.Manage.UserName AS userName, ");
		sql.append("   ss.VideoType.ID AS videoTypeId,  ");
		sql.append("   ss.VideoType.`Name` AS videoTypeName ");
		sql.append(" FROM  ");
		sql.append("   ss.Video  ");
		sql.append(" JOIN ss.VideoType ON ss.VideoType.ID = ss.Video.VideoTypeID  ");
		sql.append(" JOIN ss.Manage ON ss.Manage.ID = ss.Video.UserID  ");
		sql.append(" WHERE 1 = 1  ");
		if(CheckUtil.isInteger(videoTypeId)) {
			sql.append("   AND ss.VideoType.ID = ?   ");
			sqlClient.addParameter(videoTypeId);
		}
		if(CheckUtil.isInteger(userId)) {
			sql.append("   AND ss.Manage.ID = ?   ");
			sqlClient.addParameter(userId);
		}
		
		return sqlClient.execQuery(sql.toString());
	}
	
	/**
	 * 保存视频
	 * @param videoName
	 * @param videoDesc
	 * @param pic
	 * @param address
	 * @param videoTypeId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int saveVideoUpload(String videoName,String videoDesc,String pic,String address,String videoTypeId,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO  ");
		sql.append("   ss.Video  ");
		sql.append("   ( ");
		sql.append("   ss.Video.`Name`,  ");
		sql.append("   ss.Video.`Desc`, ");
		sql.append("   ss.Video.Pic,  ");
		sql.append("   ss.Video.Address, ");
		sql.append("   ss.Video.VideoTypeID, ");
		sql.append("   ss.Video.UserID,  ");
		sql.append("   ss.Video.CreateDate  ");
		sql.append("   )  ");
		sql.append(" VALUES  ");
		sql.append("   ( ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?,  ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   NOW()  ");
		sql.append("   ) ");
		sqlClient.addParameter(videoName);
		sqlClient.addParameter(videoDesc);
		sqlClient.addParameter(pic);
		sqlClient.addParameter(address);
		sqlClient.addParameter(videoTypeId);
		sqlClient.addParameter(userId);
		
		return sqlClient.execUpdate(sql.toString());
	}

	/**
	 * 收藏列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> findCollectVideoList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.CollectionVideo.ID AS collectId, ");
		sql.append("   ss.Video.ID AS videoId, ");
		sql.append("   ss.Video.`Name` AS videoName, ");
		sql.append("   ss.Video.`Desc` AS videoDesc, ");
		sql.append("   ss.Video.Pic AS videoPic, ");
		sql.append("   ss.Video.Address AS address, ");
		sql.append("   ss.Video.CreateDate AS createDate, ");
		sql.append("   ss.Manage.ID AS userId, ");
		sql.append("   ss.Manage.UserName AS userName, ");
		sql.append("   ss.VideoType.ID AS videoTypeId,  ");
		sql.append("   ss.VideoType.`Name` AS videoTypeName ");
		sql.append(" FROM  ");
		sql.append("   ss.CollectionVideo  ");
		sql.append(" JOIN ss.Video ON ss.Video.ID = ss.CollectionVideo.VideoID  ");
		sql.append(" JOIN ss.VideoType ON ss.VideoType.ID = ss.Video.VideoTypeID  ");
		sql.append(" JOIN ss.Manage ON ss.Manage.ID = ss.CollectionVideo.UserID  ");
		sql.append(" WHERE   ");
		sql.append("   ss.Manage.ID = ?   ");
		sqlClient.addParameter(userId);
		
		return sqlClient.execQuery(sql.toString());
	}
	/**
	 * 观看列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> findViewVideoList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.ViewVideo.ID AS viewId, ");
		sql.append("   ss.Video.ID AS videoId, ");
		sql.append("   ss.Video.`Name` AS videoName, ");
		sql.append("   ss.Video.`Desc` AS videoDesc, ");
		sql.append("   ss.Video.Pic AS videoPic, ");
		sql.append("   ss.Video.Address AS address, ");
		sql.append("   ss.Video.CreateDate AS createDate, ");
		sql.append("   ss.Manage.ID AS userId, ");
		sql.append("   ss.Manage.UserName AS userName, ");
		sql.append("   ss.VideoType.ID AS videoTypeId,  ");
		sql.append("   ss.VideoType.`Name` AS videoTypeName ");
		sql.append(" FROM  ");
		sql.append("   ss.ViewVideo  ");
		sql.append(" JOIN ss.Video ON ss.Video.ID = ss.ViewVideo.VideoID  ");
		sql.append(" JOIN ss.VideoType ON ss.VideoType.ID = ss.Video.VideoTypeID  ");
		sql.append(" JOIN ss.Manage ON ss.Manage.ID = ss.ViewVideo.UserID  ");
		sql.append(" WHERE   ");
		sql.append("   ss.Manage.ID = ?   ");
		sqlClient.addParameter(userId);
		
		return sqlClient.execQuery(sql.toString());
	}
	/**
	 * 新增观看
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int ViewVideoAdd(String videoId,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO  ");
		sql.append("   ss.ViewVideo  ");
		sql.append("   ( ");
		sql.append("   ss.ViewVideo.UserID,  ");
		sql.append("   ss.ViewVideo.VideoID,  ");
		sql.append("   ss.ViewVideo.CreateDate  ");
		sql.append("   )  ");
		sql.append(" VALUES  ");
		sql.append("   ( ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   NOW()  ");
		sql.append("   ) ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		
		return sqlClient.execUpdate(sql.toString());
	}
	/**
	 * 更新观看
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int ViewVideoUpdate(String videoId,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE  ");
		sql.append("   ss.ViewVideo  ");
		sql.append(" SET  ");
		sql.append("   ss.ViewVideo.UpdateDate = NOW()  ");
		sql.append(" WHERE  ");
		sql.append("   ss.ViewVideo.UserID = ?  ");
		sql.append(" AND ss.ViewVideo.VideoID = ? ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		
		return sqlClient.execUpdate(sql.toString());
	}
	
	/**
	 * 查询观看重复
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> ViewVideoRepeat(String videoId,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ");
		sql.append("   ss.ViewVideo.ID  ");
		sql.append(" FROM  ");
		sql.append("   ss.ViewVideo  ");
		sql.append(" WHERE  ");
		sql.append("   ss.ViewVideo.UserID = ? ");
		sql.append(" AND ss.ViewVideo.VideoID = ? ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		
		return sqlClient.execQuery(sql.toString());
	}
	
	/**
	 * 悬赏列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> findRewardList(String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.Reward.ID AS rewardId, ");
		sql.append("   ss.Reward.Content AS content, ");
		sql.append("   ss.Reward.Money AS money, ");
		sql.append("   ss.Reward.CreateDate AS createDate, ");
		sql.append("   ss.Manage.ID AS userId, ");
		sql.append("   ss.Manage.UserName AS userName  ");
		sql.append(" FROM  ");
		sql.append("   ss.Reward  ");
		sql.append(" JOIN ss.Manage ON ss.Manage.ID = ss.Reward.UserID  ");
		sql.append(" WHERE 1 = 1   ");
		if(CheckUtil.isInteger(userId)) {
			sql.append(" AND ss.Manage.ID = ?   ");
			sqlClient.addParameter(userId);
		}
		
		return sqlClient.execQuery(sql.toString());
	}
	/**
	 * 发布悬赏
	 * @param content
	 * @param money
	 * @return
	 * @throws Exception
	 */
	public int  RewardAdd(String content, String money,String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO ");
		sql.append("   ss.Reward ");
		sql.append("   ( ");
		sql.append("   ss.Reward.Content, ");
		sql.append("   ss.Reward.Money, ");
		sql.append("   ss.Reward.UserID, ");
		sql.append("   ss.Reward.CreateDate ");
		sql.append("   ) ");
		sql.append(" VALUES  ");
		sql.append("   (  ");
		sql.append("   ?,  ");
		sql.append("   ?,  ");
		sql.append("   ?,  ");
		sql.append("   NOW()  ");
		sql.append("   ) ");
		sqlClient.addParameter(content);
		sqlClient.addParameter(money);
		sqlClient.addParameter(userId);
		
		return sqlClient.execUpdate(sql.toString());
	}


	/**
	 * 新增收藏
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int MyCollectVideoAdd(String videoId, String userId) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO ");
		sql.append("   ss.CollectionVideo ");
		sql.append("   ( ");
		sql.append("   ss.CollectionVideo.UserID, ");
		sql.append("   ss.CollectionVideo.VideoID, ");
		sql.append("   ss.CollectionVideo.CreateDate ");
		sql.append("   ) ");
		sql.append(" VALUES  ");
		sql.append("   (  ");
		sql.append("   ?,  ");
		sql.append("   ?,  ");
		sql.append("   NOW()  ");
		sql.append("   ) ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		
		return sqlClient.execUpdate(sql.toString());
	}
	/**
	 * 视频是否已收藏
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> TheVideoIsCollect(String videoId, String userId) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.CollectionVideo.ID ");
		sql.append(" FROM ");
		sql.append("   ss.CollectionVideo ");
		sql.append(" WHERE  ");
		sql.append("   ss.CollectionVideo.UserID = ?   ");
		sql.append(" AND ss.CollectionVideo.VideoID  = ?   ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		return sqlClient.execQuery(sql.toString());
	}
	/**
	 * 删除我的收藏
	 * @param videoId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int deleteMyVideoCollect(String videoId, String userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM  ");
		sql.append("   ss.CollectionVideo  ");
		sql.append(" WHERE  ");
		sql.append("   ss.CollectionVideo.UserID = ?  ");
		sql.append(" AND ss.CollectionVideo.VideoID = ?  ");
		sqlClient.addParameter(userId);
		sqlClient.addParameter(videoId);
		
		return sqlClient.execUpdate(sql.toString());
	}
}
