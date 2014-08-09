package frame.http.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


public class HttpResultBean {

	private String result;
	
	public HttpResultBean(String result){
		this.result=result;
	}
	
	
	public String getString(){
		return result;
	}
	
	
	public JSONObject getJSONObject(){
		if(result==null)return null;
		try {
			return JSON.parseObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONArray getJSONArray(){
		if(result==null)return null;
		try {
			return JSON.parseArray(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JSONObject getJSONArrayToJSONObject(String key){
		if(result==null)return null;
		try {
			JSONObject jobj=new JSONObject();
			jobj.put(key, JSON.parseArray(result));
			return jobj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
