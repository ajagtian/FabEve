package com.eventsearch.debservice;

import redis.clients.jedis.Jedis;

import com.eventsearch.entity.User;
import com.eventsearch.helpers.DBUtil;

public class DBQuery {
	
	public static void pingAndSaveUser(User user) {
		
		Jedis jedis = DBUtil.getJEDIS();
		if(jedis.get(user.getId()) == null) {
		//	new user
			jedis.set(user.getId(),user.toString());
		}
	}

}
