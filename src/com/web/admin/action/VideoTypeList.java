package com.web.admin.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.db.VideoDB;

public class VideoTypeList {
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);

		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = videoDB.findVideoTypeList();
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
