package com.web.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.JSONListFormat;
import com.db.SQLClient;
import com.web.WebUtil;
import com.web.admin.User;
import com.web.admin.db.VideoDB;

/**
 * 保存视频
 * @author HM
 *
 */
public class SaveVideoUpload {
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		String videoName = req.getParameter("videoName");
		String videoDesc = req.getParameter("videoDesc");
		String pic = req.getParameter("pic");
		String address = req.getParameter("address");
		String videoTypeId = req.getParameter("videoTypeId");
		
		if(user == null) {
			responseMessage = "error-login";
		}
		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			videoDB.saveVideoUpload(videoName, videoDesc, pic, address, videoTypeId, user.getUserId());
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
