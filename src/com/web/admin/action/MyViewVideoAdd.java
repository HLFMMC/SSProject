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

public class MyViewVideoAdd {
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String videoId = req.getParameter("videoId");
		if(user == null) {
			responseMessage = "error-login";
		}
		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = videoDB.ViewVideoRepeat(videoId, user.getUserId());  //查询是否观看
			System.out.println(data);
			if(data.size()>0) { // 存在更新
				videoDB.ViewVideoUpdate(videoId, user.getUserId());
			} else { // 不存在 新增
				videoDB.ViewVideoAdd(videoId, user.getUserId());
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
