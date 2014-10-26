package com.eventsearch.debservice;

import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

import com.eventsearch.entity.User;
import com.eventsearch.helpers.DBUtil;

public class DBQuery {
	
	public static void pingAndSaveUser(User user, boolean override) {
		
		Jedis jedis = DBUtil.getJEDIS();
		if(override || jedis.get(user.getId()) == null){
			jedis.set(user.getId(),user.toString());
		}
	}
	
	public static String getUserTags(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		String keywords = null;
		String u = jedis.get(user.getId());
		int i = u.indexOf("~");
		int j = u.lastIndexOf("~");
		keywords= u.substring(i+2,j-1); 
		return keywords;
	}

}
