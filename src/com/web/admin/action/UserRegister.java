package com.web.admin.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CheckUtil;
import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.db.ManagerDB;

public class UserRegister {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		System.out.println(userName);
		if(CheckUtil.isEmpty(userName)) {
			responseMessage = "error-userName"; 
		} else if(!CheckUtil.isPassword(password)) {
			responseMessage = "error-password";
		} else if(!CheckUtil.isActionPhone(phone)) {
			responseMessage = "error-phone";
		}
		
		SQLClient sqlClient = new SQLClient();
		ManagerDB managerDB = new ManagerDB(sqlClient);
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = managerDB.UserRepear(userName);
			
			if(data.size()>0) {
				responseMessage = "error-repeat";
			} else {
				managerDB.UserRegister(userName, password,phone);
			}
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-userName")) jsonFormat.setShowMsg("用户名错误");
			if(responseMessage.equals("error-password")) jsonFormat.setShowMsg("密码格式错误");
			if(responseMessage.equals("error-phone")) jsonFormat.setShowMsg("电话号码格式错误");
			if(responseMessage.equals("error-repeat")) jsonFormat.setShowMsg("用户名已被使用");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
