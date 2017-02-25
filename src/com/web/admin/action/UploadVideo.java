package com.web.admin.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Base64Util;
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
		if(user == null) {
			responseMessage = "error-login";
		}
		String base64 = req.getParameter("base64");
		
		if(responseMessage == "") {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
			String str = sdf.format(date);
			String basePathUrl = req.getSession().getServletContext().getRealPath("/");
			String pathUrl = "videoPath/";
			String url = str + ".mp4";
			
			File pathFile = new File(pathUrl);
			if  (!pathFile.exists()  && !pathFile.isDirectory()){       
				pathFile.mkdirs();
			}
			File file = new File(basePathUrl+pathUrl + url);
			
			if  (!file.exists()  && !file.isDirectory()){       
				file.createNewFile();   
			}
			
			boolean result = Base64Util.base64ToFile(base64, file);
			
			if(result){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("path", basePathUrl+pathUrl+url);
				jsonFormat.addMap(map);
			}else{
				responseMessage="error";
			}
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
