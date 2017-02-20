package com.web.admin.db;

import java.util.HashMap;
import java.util.LinkedList;

import com.db.SQLClient;

public class ManagerDB {

	private SQLClient sqlClient;

	public ManagerDB(SQLClient sqlClient) {
		this.sqlClient = sqlClient;
		sqlClient.setDebug(true);
	}

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> UserLogin(String userName, String password) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.Manage.ID AS userId, ");
		sql.append("   ss.Manage.UserName AS userName, ");
		sql.append("   ss.Manage.Phone AS phone,  ");
		sql.append("   ss.Manage.Pic AS pic,  ");
		sql.append("   ss.Manage.NikeName AS nikeName,  ");
		sql.append("   ss.Manage.Sex AS sex, ");
		sql.append("   ss.Manage.Email AS email   ");
		sql.append(" FROM  ");
		sql.append("   ss.Manage  ");
		sql.append(" WHERE  ");
		sql.append("   ss.Manage.UserName = ?  ");
		sql.append(" AND ss.Manage.`Password` = ? ");
		sqlClient.addParameter(userName);
		sqlClient.addParameter(password);
		
		return sqlClient.execQuery(sql.toString());
	}
	/**
	 * 注册前查询重复
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public LinkedList<HashMap<String, String>> UserRepear(String userName) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("   ss.Manage.ID AS userId ");
		sql.append(" FROM  ");
		sql.append("   ss.Manage  ");
		sql.append(" WHERE  ");
		sql.append("   ss.Manage.UserName = ?  ");
		sqlClient.addParameter(userName);
		
		return sqlClient.execQuery(sql.toString());
	}
	
	/**
	 * 用户注册
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int  UserRegister(String userName, String password) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO  ");
		sql.append("   ss.Manage  ");
		sql.append("   ( ");
		sql.append("   ss.Manage.UserName,  ");
		sql.append("   ss.Manage.`Password`  ");
		sql.append("   )  ");
		sql.append("   VALUES  ");
		sql.append("   (   ");
		sql.append("   ?,  ");
		sql.append("   ?  ");
		sql.append("   )  ");
		sqlClient.addParameter(userName);
		sqlClient.addParameter(password);
		
		return sqlClient.execUpdate(sql.toString());
	}

	// 修改个人信息
	public int UserUpdate(String phone, String pic, String nikeName, String email, String sex,String userId)  throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE   ");
		sql.append("   ss.Manage  ");
		sql.append(" SET ");
		sql.append("   ss.Manage.ID = ?  ");
		sqlClient.addParameter(userId);
		sql.append("   ,ss.Manage.Phone = ?  ");
		sql.append("   ,ss.Manage.Pic = ?   ");
		sql.append("   ,ss.Manage.NikeName = ?  ");
		sql.append("   ,ss.Manage.Sex = ?   ");
		sql.append("   ,ss.Manage.Email = ?   ");
		sql.append(" WHERE  ");
		sql.append("   ss.Manage.ID = ?   ");
		sqlClient.addParameter(phone);
		sqlClient.addParameter(pic);
		sqlClient.addParameter(nikeName);
		sqlClient.addParameter(sex);
		sqlClient.addParameter(email);
		sqlClient.addParameter(userId);
		
		return sqlClient.execUpdate(sql.toString());
	}

}
