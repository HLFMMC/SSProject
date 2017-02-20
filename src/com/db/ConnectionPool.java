package com.db;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
@SuppressWarnings("resource")
public class ConnectionPool {
	
	private static Map<String , DataSource > dataSourceMap = new  HashMap<String , DataSource >();

	
	static{
		
		String fileName = "beans-config-db.xml";
		File file = new File(fileName);
		ApplicationContext context = null;
		if(file.exists()){
			 context = new FileSystemXmlApplicationContext(fileName);
			 
		}else{
			 context = new ClassPathXmlApplicationContext(fileName);
		}
		
		
		String[] names = context.getBeanDefinitionNames();
		for (int i = 0; i < names.length; i++) {
			
			Object beanObject = context.getBean(names[i]);
			if (beanObject instanceof DataSource) {
				dataSourceMap.put(names[i], (DataSource)beanObject);
			}
		}
		
		
		
		
	}
	

	
	public static Connection getConnection(String name) throws SQLException {
		
		return dataSourceMap.get(name).getConnection();
		
	}

	public static boolean isConnect(String name) {
		
		boolean b = false;
		try {
			
			Connection con = ConnectionPool.getConnection(name);
			if(con.isClosed()==false)b = true;
			con.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return b;
	}

}
