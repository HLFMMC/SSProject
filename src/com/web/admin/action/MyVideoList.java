package com.web.admin.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.User;
import com.web.admin.db.VideoDB;

public class MyVideoList {
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			responseMessage = "error-login";
		}
		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = videoDB.findVideoList(null, null);
			while (data.size()>0) {
				HashMap<String,String> map = data.remove();
				jsonFormat.addMap(map);
			}
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else if("error-login".equals(responseMessage)) jsonFormat.setShowMsg("用户无登录");
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
