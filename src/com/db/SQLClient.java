package com.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;



public class SQLClient{
	
	
	
	

	
	private Logger logger = Logger.getLogger(this.getClass());
	private Connection conn;
	private LinkedList<String> parameterList = new LinkedList<String>(); 
	
	private boolean debug = false;
	
	private String   sqlString  ="";
	private String   sqlString1 ="";
	private String   dataSourceName  = "dataSource";
	
	
	public SQLClient()
	{
		
	}
	public SQLClient( String name)
	{
		dataSourceName = name;
	}
	
	
	
	public void setDebug(boolean _debug)
	{
		debug = _debug;
	}
	
	public void addParameter(String _parameter)
	{
		parameterList.addLast(_parameter);
	}
	
	
	public int execUpdate(String _sql) throws SQLException
	{
		
		int updateIdx = -1;
		try
		{
			if(conn == null)
			{
				conn = ConnectionPool.getConnection(dataSourceName);
			}
			
			PreparedStatement pstmt = conn.prepareStatement(_sql);
			int parameterIdx = 1;
			while(parameterList.size() > 0)
			{
				pstmt.setString(parameterIdx++, parameterList.removeFirst());
			}
			
			
			showSQL(pstmt.toString());
				
			
			updateIdx = pstmt.executeUpdate();
						
			if(conn.getAutoCommit())
			{
				conn.close();
				conn = null;
			}
			
			
		}
		catch(SQLException e)
		{
			
			logger.error(sqlString);
			logger.error(e.getMessage(), e);
			
			parameterList.clear();
			closeConnection();
			throw e;
		}
		
		parameterList.clear();
		return updateIdx;
	}
	
	

	public LinkedList<HashMap<String, String>> execQuery(String sql) throws SQLException{
		
		LinkedList<HashMap<String, String>> resultList = new LinkedList<HashMap<String, String>>() ;
		execQuery(resultList , sql ,false ,0,0);
		return resultList;
		
	}
	
	
	
	public int execQuery(  List<HashMap<String, String>> resultList,   String sql ,  int start ,int offset) throws SQLException{
		return execQuery(resultList , sql ,true ,start,offset);
	}
	

	public int execQuery(  List<HashMap<String, String>> resultList,  String sql , boolean isPage, int start ,int offset) throws SQLException{
		
		int dataTotal = 0;
		
		
		try
		{
			
			
			if(conn == null)
			{
				conn = ConnectionPool.getConnection(dataSourceName);
			}
			
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			int parameterIndex  = 0;
			
		
			long time1 = System.currentTimeMillis();
			if(isPage){
				
				String sqlTemp  = "SELECT COUNT(1) "+sql.substring(sql.toUpperCase().indexOf("FROM"));
				preparedStatement = conn.prepareStatement(sqlTemp);
				
				
				showSQL(preparedStatement.toString());
				for (String params : parameterList) {
					preparedStatement.setString(++parameterIndex, params);
				}
				
				
				resultSet = preparedStatement.executeQuery();         // 執行取得該SQL指令結果
				resultSet.first();
				dataTotal = resultSet.getInt(1); 
				parameterIndex = 0;
				resultSet.close();			
				preparedStatement.close();
				
				sql = sql+ " LIMIT "+start+","+offset;
			
				sqlString1 = sqlString;
				
			}
			
			
			long time2 = System.currentTimeMillis();
			
			
			
			preparedStatement = conn.prepareStatement(sql);
			for (String params : parameterList) {
				preparedStatement.setString(++parameterIndex, params);
			}
			parameterList.clear();
			
			
			showSQL(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();        
			
			
			
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();   
			int columnCount = resultSetMetaData.getColumnCount();            
		
			
			long time3 = System.currentTimeMillis();				
			
			while(resultSet.next())
			{
				
				HashMap<String,String> columnObject = new HashMap<String,String>();
				resultList.add(columnObject);

				
				for (int i = 0; i < columnCount; i++) {
						
					String columnName  = resultSetMetaData.getColumnName(i+1);
					String      value  =         resultSet.getString(columnName);
					
					if (value == null) value="";
				
					columnObject.put(columnName, value);
					
						
				}
				
				
			}
		
			long time4 = System.currentTimeMillis();
			
			resultSet.close();			
			preparedStatement.close();
			
			if(dataTotal == 0) dataTotal = resultList.size();
			
			if(conn.getAutoCommit())
			{
				conn.close();
				conn = null;
			}
			
			
			
			
			double querySec1 = (time2 - time1) /1000.0;
			double querySec2 = (time3 - time2) /1000.0;
			double   loadSec = (time4 - time3) /1000.0;
			double  totalSec = (time4 - time1) /1000.0;
			
			if(totalSec > 6){
				
				
				logger.warn("##########  Query Too Long Time ########## ");
				
				logger.warn("[query1]\t" +querySec1+" Sec");
				logger.warn("[query2]\t" +querySec2+" Sec");
				logger.warn("[parse]\t" +  loadSec+" Sec");
				logger.warn("[total]\t" + totalSec+" Sec");
				logger.warn("[count]\t" +  dataTotal);
			
				
				StackTraceElement[] stack = new Exception("executeQuery").getStackTrace();
				for (int i = 0; i < stack.length; i++) {
					if (stack[i].toString().startsWith("com"))	logger.warn(stack[i]);
				}
				
				
				logger.warn(sqlString1+"\r\n");
				logger.warn(sqlString+"\r\n");
				
			
				
			}
			
		
			
			
		}
		catch(Exception e)
		{
			
			parameterList.clear();
			logger.error(sqlString+"\r\n");
			logger.error(e.getMessage(), e);
			closeConnection();
			throw e;
		}	
		
		
		return dataTotal;
		
	}
	
	
	
	
	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public LinkedList<LinkedList<HashMap<String, String>>> executeQueryMore(String sql) throws SQLException{
		LinkedList<LinkedList<HashMap<String, String>>> resultLists = new LinkedList<>();

		if(conn == null){
			conn = ConnectionPool.getConnection(dataSourceName);
		}
		
		CallableStatement callableStatement = null;
//		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int parameterIndex  = 0;
		
//		preparedStatement = conn.prepareStatement(sql);
		callableStatement = conn.prepareCall(sql);
		for (String params : parameterList) {
			callableStatement.setString(++parameterIndex, params);
		}
		parameterList.clear();
		
		showSQL0(callableStatement.toString());
		callableStatement.executeQuery();
		
		resultSet = callableStatement.getResultSet();   
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();   
		int columnCount = resultSetMetaData.getColumnCount();            
		LinkedList<HashMap<String, String>> resultList = new LinkedList<HashMap<String, String>>();
		while(resultSet.next()){
			HashMap<String,String> columnObject = new HashMap<String,String>();
			resultList.add(columnObject);
			for (int i = 0; i < columnCount; i++) {
				String columnName  = resultSetMetaData.getColumnName(i+1);
				String      value  =         resultSet.getString(columnName);
				if (value == null) value="";
				columnObject.put(columnName, value);
			}
		}
		resultLists.add(resultList);
		
		while(callableStatement.getMoreResults()){
			resultSet = callableStatement.getResultSet();
			ResultSetMetaData resultSetMetaData0 = resultSet.getMetaData();   
			int columnCount0 = resultSetMetaData0.getColumnCount();            
			LinkedList<HashMap<String, String>> resultList0 = new LinkedList<HashMap<String, String>>();
			while(resultSet.next()){
				HashMap<String,String> columnObject = new HashMap<String,String>();
				resultList0.add(columnObject);
				for (int i = 0; i < columnCount0; i++) {
					String columnName  = resultSetMetaData0.getColumnName(i+1);
					String      value  =         resultSet.getString(columnName);
					if (value == null) value="";
					columnObject.put(columnName, value);
				}
			}
			resultLists.add(resultList0);
		}
	
		
		if(conn.getAutoCommit()){
			conn.close();
			conn = null;
		}
		parameterList.clear();
		closeConnection();
		
		return resultLists;
	}
	
	

	
	
	
	
	
	
	
	public void commit()
	{
		if(conn != null)
		{
			try {
				conn.commit();
				conn.setAutoCommit(true);
				conn.close();
				conn = null;
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
	}


	
	
	public void rollBack()
	{
		if(conn != null)
		{	
			try {
				conn.rollback();
				conn.setAutoCommit(true);
				conn.close();
				conn = null;
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
	}
	
	public void setAutoCommit(boolean _commit)
	{
		
		try {
			conn = ConnectionPool.getConnection(dataSourceName);
			conn.setAutoCommit(_commit);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public boolean getAutoCommit()
	{
		
		
		try {
			return conn.getAutoCommit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	
	public void closeConnection()
	{
		try 
		{
			if(conn != null)
			{
				if(!conn.getAutoCommit())
				{
					conn.rollback();
					conn.setAutoCommit(true);
				}
				conn.close();
				conn = null;
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	public void showSQL(String sql){
		sqlString = sql;
//		sqlString = sql.substring(sql.toUpperCase().indexOf("-"));
		if(debug){
			logger.info(sqlString+"\r\n");
		}else{
			logger.debug(sqlString+"\r\n");
		}
		
	}
	
	public void showSQL0(String sql){
		System.out.println(sql);
//		sqlString = sql.substring(sql.toUpperCase().indexOf(":"));
		sqlString = sql;
		if(debug){
			logger.info(sqlString+"\r\n");
		}else{
			logger.debug(sqlString+"\r\n");
		}
		
	}
	
	
}
