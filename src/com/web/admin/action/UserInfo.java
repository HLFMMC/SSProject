package com.web.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.JSONListFormat;
import com.web.WebUtil;
import com.web.admin.User;

public class UserInfo {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		HttpSession httpSession = req.getSession();
		User user = (User) httpSession.getAttribute("user");
		
		if(user == null) {
			responseMessage = "error-login";
		}
		if(responseMessage == "") {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", user.getUserId());
			jsonObject.put("userName", user.getUserName());
			jsonObject.put("phone", user.getPhone());
			jsonObject.put("pic", user.getPic());
			jsonObject.put("nikeName", user.getNikeName());
			jsonObject.put("email", user.getEmail());
			jsonObject.put("sex", user.getSex());
			jsonFormat.addJSONObject(jsonObject);
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-login")) jsonFormat.setShowMsg("用户无登陆");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
