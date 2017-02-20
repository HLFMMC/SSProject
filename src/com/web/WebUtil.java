package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.CheckUtil;
import com.JSONListFormat;


public class WebUtil {

	public static final String SUCCESS_SHOW_MSG = "处理完成";
	
	public static  JSONListFormat  createJSONListFormat( HttpServletRequest req ,boolean isPage){
		
		JSONListFormat  jFormat = new JSONListFormat();
		jFormat.setServerCode(req.getParameter("serverCode"));
		jFormat.setShowMsg("处理完成");
		
		if(isPage){
			
			String   start  = req.getParameter("start");
			String   offset = req.getParameter("offset");
			
			if(CheckUtil.isInteger(start )==false) start= "0";
			if(CheckUtil.isInteger(offset)==false)offset="10";
			jFormat.setLimit(Integer.valueOf(start), Integer.valueOf(offset));
			
		}
		
		
		return jFormat;
		
		
		
	}
	

	
	
	
	
	
	public static boolean checkVerifyCode( HttpServletRequest req){
		
		String reqVerifyCode = req.getParameter("verifyCode");
		
		HttpSession httpSession = 	req.getSession();
		String httpSessiontVerifyCode = (String)httpSession.getAttribute("verifyCode");
				
		if(         reqVerifyCode == null) return false;
		if(httpSessiontVerifyCode == null) return false;
		if(reqVerifyCode.length() < 4    ) return false;
		
		httpSessiontVerifyCode = httpSessiontVerifyCode.toUpperCase();
		         reqVerifyCode = reqVerifyCode.toUpperCase();
		if(httpSessiontVerifyCode.equals(reqVerifyCode)){
			return true;
		}
		
		return false;
		
		
	}
	
	
	
	public static  int optInt(HttpServletRequest req , String name , int defaut){
		
		String  value  =  req.getParameter(name);
		if(CheckUtil.isInteger(value))
			return Integer.valueOf(value);
		else
			return defaut;
		
	}
	
	
	
	
	

}
