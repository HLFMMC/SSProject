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
import com.web.admin.db.VideoDB;

/**
 * 发布悬赏
 * @author HM
 *
 */
public class RewardAdd {
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String responseMessage = "";
		
		JSONListFormat  jsonFormat = WebUtil.createJSONListFormat(req, false);
		
		String content = req.getParameter("content");
		String money = req.getParameter("money");
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			responseMessage = "error-login";
		} else if(CheckUtil.isEmpty(content)) {
			responseMessage = "error-content"; 
		} else if(!CheckUtil.isDouble(money)) {
			responseMessage = "error-money";
		} 
		
		SQLClient sqlClient = new SQLClient();
		VideoDB videoDB = new VideoDB(sqlClient);
		
		if(responseMessage == "") {
			videoDB.RewardAdd(content,money,user.getUserId());
		}
		
		if(responseMessage == "") {
			responseMessage = "success";
		} else {
			if(responseMessage.equals("error-login")) jsonFormat.setShowMsg("用户无登录");
			if(responseMessage.equals("error-content")) jsonFormat.setShowMsg("悬赏内容不能为空");
			if(responseMessage.equals("error-money")) jsonFormat.setShowMsg("悬赏金额错误");
		}
		
		jsonFormat.setServerMsg(responseMessage);
		PrintWriter out = resp.getWriter();
		out.println(jsonFormat.toString());
		out.close();
	}

}
