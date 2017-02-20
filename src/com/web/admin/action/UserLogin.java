package com.web.admin.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.CheckUtil;
import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.User;
import com.web.admin.db.ManagerDB;

public class UserLogin {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		
		
		if(CheckUtil.isEmpty(userName)) {
			responseMessage = "error-userName"; 
		} else if(!CheckUtil.isPassword(password)) {
			responseMessage = "error-password";
		} 
		
		SQLClient sqlClient = new SQLClient();
		ManagerDB managerDB = new ManagerDB(sqlClient);
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = managerDB.UserLogin(userName, password);
			if(data.size()>0) {
				HashMap<String, String> map = data.get(0);
				String userId = map.get("userId");
				String username = map.get("userName");
				String phone = map.get("phone");
				String pic = map.get("pic");
				String nikeName = map.get("nikeName");
				String email = map.get("email");
				String sex = map.get("sex");
				
				User user = new User();
				user.setUserId(userId);
				user.setUserName(username);
				user.setPhone(phone);
				user.setPic(pic);
				user.setNikeName(nikeName);
				user.setEmail(email);
				user.setSex(sex);
				
				
			   HttpSession httpSession = req.getSession();
			   httpSession.setAttribute("user", user);
			} else {
				responseMessage = "error-account";
			}
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-userName")) jsonFormat.setShowMsg("账号错误");
			if(responseMessage.equals("error-password")) jsonFormat.setShowMsg("密码错误");
			if(responseMessage.equals("error-account")) jsonFormat.setShowMsg("账号或密码错误");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
