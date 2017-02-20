package com;

public class CheckUtil {
	
	
	public  static boolean isEmpty(String str){
		
		
		if(str==null)return true;
		str = str.trim();
		if(str.length()==0)return true;
		return false;
	}
	

	public  static boolean isNotEmpty(String str){
		
		
		if(str==null)return false;
		str = str.trim();
		if(str.length()==0)return false;
		return true;
	}
	
	
	
	
	public static boolean isAccount(String str){
		
		if(isEmpty(str))return false;
		if(!isCharString(str))return false;
		if(str.length()<6) return false;
		if(str.length()>20) return false;
		
		return true ;
		
	}

	
	
	public static boolean isPassword(String str) {
		
		if(isEmpty(str))return false;
		if(!isCharString(str))return false;
		if(str.length()<6) return false;
		if(str.length()>20) return false;
		return true ;
	}
	

	
	
	public static boolean isMail(String str) {

		if(isEmpty(str))return false;
		String mailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = str.matches(mailRegex);

		return b;

	}
	

	public static boolean isNickname(String str) {
		
		
		if(isEmpty(str))return false;
		if(str.length()<2) return false;
		if(str.length()>50) return false;
		
		return true;

	}
	
	
	
	
	public  static boolean isCharString(String str){
		
		if(isEmpty(str))return false;
		return str.matches("[a-zA-Z0-9]*") ;
		
	}
	
	
	
	public  static boolean isInteger(String str){
		try {
			Integer.valueOf(str);
			return true;
			
		} catch (Exception e) {}
		
		return false;
	}
	public  static boolean isDouble(String str){
		
		try {
			Double.valueOf(str);
			return true;
			
		} catch (Exception e) {}
		
		return false;
	}
	public static boolean isNumeric(String str) {
		return str.matches("[0-9]*") ;
		
	}
	
	
	public static boolean isDate(String str){
		if(isEmpty(str))return false;
//		return str.matches("^\\d{4}(-|.|/)\\d{2}(-|.|/)\\d{2}$");
		return str.matches("^\\d{4}(-)\\d{2}(-)\\d{2}$");
	}
	
	public static boolean isDateYM(String str){
		if(isEmpty(str))return false;
		return str.matches("^\\d{4}[0-1][0-9]$");
	}
	
	public static boolean endsWith(String photo){
		boolean boo = photo.endsWith(".png")||photo.endsWith(".jpg");
		return boo;
	}
	
	/**
	 * 判断是手机号码，11位数，第一个数字为1
	 * @param str
	 * @return
	 */
	public static boolean isActionPhone(String str){
		if(isEmpty(str))return false;
		return str.matches("^1[0-9]{10}$");
	}

}
