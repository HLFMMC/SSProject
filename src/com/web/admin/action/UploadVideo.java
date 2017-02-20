package com.web.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.CheckUtil;
import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.User;
import com.web.admin.db.ManagerDB;

public class UploadVideo {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		
		String phone = req.getParameter("phone");
		String pic = req.getParameter("pic");
		String nikeName = req.getParameter("nikeName");
		String email = req.getParameter("email");
		String sex = req.getParameter("sex");
		if(user == null) {
			responseMessage = "error-login";
		} else if(CheckUtil.isNotEmpty(email)){
			if(!CheckUtil.isMail(email)){
				responseMessage = "error-email";
			}
		}
		
		SQLClient sqlClient = new SQLClient();
		ManagerDB managerDB = new ManagerDB(sqlClient);
		if(responseMessage == "") {
			managerDB.UserUpdate(phone, pic,nikeName,email,sex,user.getUserId());
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-login")) jsonFormat.setShowMsg("用户无登录");
			if(responseMessage.equals("error-email")) jsonFormat.setShowMsg("邮箱格式错误");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
