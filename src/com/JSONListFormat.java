package com;



import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONListFormat {

	
	private String serverCode =  "";
	private String serverMsg  =  "";
	private String showMsg = "";
	
	private int     dataTotal =    0;
	private int    startIndex =    0;
	private int      endIndex = 1000;
	private int  currentIndex =    0;
	
	

	private JSONArray   jsonArray = new JSONArray();
	
	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	
	public String getServerMsg() {
		return serverMsg;
	}

	public void setServerMsg(String str) {
		this.serverMsg = str;
	}

	public String getShowMsg(){
		return showMsg;
	}
	
	
	
	public JSONArray getDataJsonArray0() {
		
		return jsonArray;
		
	}
	
	public void setLimit(int start, int offset){
		startIndex = start;
		  endIndex = start  + offset;
	}
	

	

	public void addMap(HashMap<String,String> map) throws Exception {
		
		if( startIndex <= currentIndex & currentIndex < endIndex ){
			jsonArray.put(createJSONObject(map));
		}
		currentIndex++;
				
		
	}
	
	public void addJSONObject(JSONObject jObject) throws Exception {
		
		if( startIndex <= currentIndex & currentIndex < endIndex ){
			jsonArray.put(jObject);
		}
		currentIndex++;
		
	}
	//新加 的	
	public void addJSONArray(JSONArray jArray)throws Exception{
		if( startIndex <= currentIndex & currentIndex < endIndex ){
			jsonArray.put(jArray);
		}
		currentIndex++;
	}
	
	//添加错误提示
	public void setShowMsg(String showMsg){
		this.showMsg = showMsg;
	}
	
	//直接加入JSONArray
	public void setJsonArray(JSONArray jsonArray){
		this.jsonArray=jsonArray;
	}
	public static JSONObject createJSONObject(HashMap<String,String> map) throws Exception {
		
		JSONObject reposeDataObject = new JSONObject();
		
		for (String key : map.keySet()) {
			reposeDataObject.put(key , map.get(key));
        }
		
		return reposeDataObject;
		
		
	}



	public void setDataTotal(int dataCount) {
		this.dataTotal = dataCount;
	}
	

	@Override
	public String toString(){
	  	
		
		try {
	
	 		
			JSONObject jsonObject = new JSONObject();
			
			
			jsonObject.put("serverMsg"   , serverMsg);
			jsonObject.put("serverCode"  , serverCode);
			jsonObject.put("dataTotal"   , dataTotal);
			jsonObject.put("showMsg"     , showMsg);
			
			if(dataTotal==0){
				jsonObject.put("dataTotal", currentIndex);
			}
			jsonObject.put("data"  , jsonArray);
			
			
		  	return jsonObject.toString(4);
		  	
		  	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		  	
		return"";
	}
	
	
	
	
	
}
