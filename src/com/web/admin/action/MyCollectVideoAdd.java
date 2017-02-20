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
import com.web.admin.db.VideoDB;

/**
 * 新增收藏
 * @author HM
 * 
 */
public class MyCollectVideoAdd {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		
		String videoId = req.getParameter("videoId");
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			responseMessage = "error-login";
		} else if(CheckUtil.isEmpty(videoId)) {
			responseMessage = "error-videoId"; 
		}
		
		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			LinkedList<HashMap<String, String>> data = videoDB.TheVideoIsCollect(videoId,user.getUserId());
			if(data.size()>0) {
				responseMessage = "error-repeat";
			} else {
				videoDB.MyCollectVideoAdd(videoId,user.getUserId());
			}
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-login")) jsonFormat.setShowMsg("用户无登录");
			if(responseMessage.equals("error-videoId")) jsonFormat.setShowMsg("视频ID错误");
			if(responseMessage.equals("error-repeat")) jsonFormat.setShowMsg("已收藏");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
