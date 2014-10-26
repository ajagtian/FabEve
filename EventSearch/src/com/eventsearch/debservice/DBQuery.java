package com.eventsearch.debservice;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import redis.clients.jedis.Jedis;

import com.eventsearch.entity.Event;
import com.eventsearch.entity.User;
import com.eventsearch.helpers.DBUtil;

public class DBQuery {
	
	public static void pingAndSaveUser(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		jedis.set(user.getId(),user.toString());
		DBUtil.closeClient();
	}
	
	public static String getUserTags(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		String keywords = "";
		String u = jedis.get("k_w_"+user.getId());
		if(u != null) {
			int i = u.indexOf("[");
			int j = u.lastIndexOf("]");
			keywords= u.substring(i+1,j);
		}
		DBUtil.closeClient();
		return keywords;
	}
	
	public static void setUserTags(User user) {
		ArrayList<String> keywords = user.getKeywords();
		Jedis jedis = DBUtil.getJEDIS();
		jedis.set("k_w_"+user.getId(), keywords.toString());
		DBUtil.closeClient();
	}
	
	public static void setUserLocation(User user,String location) {
		Jedis jedis = DBUtil.getJEDIS();
		jedis.set("loc_"+user.getId(),location);
		DBUtil.closeClient();
	}
	
	public static String getUserLocation(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		String location = "";
		if(jedis.get("loc_"+user.getId())!= null) {
			location = jedis.get("loc_"+user.getId());
		}
		DBUtil.closeClient();
		return location;
	}
	
	public static void setGenericEventSaveTimeStamp(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = formatter.format(new Date());
		jedis.set("gen_event_time_stamp"+user.getId(),date);
		DBUtil.closeClient();
	}
	
	public static String getGenericEventSaveTimeStamp(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		if(jedis.get("gen_event_"+user.getId()) != null){
			DBUtil.closeClient();
			return jedis.get("gen_event_time_stamp"+user.getId());
		}
		else {
			DBUtil.closeClient();
			return "1990-01-01T00:00:00";
		}		
	}
	
	public static void setGeneralEventsInDb(User user, ArrayList<Event> generalEvents) {
		Jedis jedis = DBUtil.getJEDIS();
		jedis.set("gen_evens_"+user.getId(),generalEvents.toString());
	}
	
	public static ArrayList<Event> getGeneralEventsFromDb(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		String general_events = jedis.get("gen_evens_"+user.getId());
		if(general_events != null) {
			return null;
		}else {
			return null;
		}
	}
	
	public static void addTaggedEventsToDB(User user, ArrayList<Event> events){
		Jedis jedis = DBUtil.getJEDIS();
		jedis.set("tag_events_"+user.getId(), events.toString());
		DBUtil.closeClient();
	}
	
	public static String getTaggedEventsFromDB(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		String events = jedis.get("tag_events_"+user.getId());
		if(events != null) {
			DBUtil.closeClient();
			return events;
		}
		else {
			DBUtil.closeClient();
			return "";
		}
	}
	public static void setTaggedEventSaveTimeStamp(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = formatter.format(new Date());
		jedis.set("tag_event_time_stamp"+user.getId(),date);
		DBUtil.closeClient();
	}
	
	public static String getTaggedEventSaveTimeStamp(User user) {
		Jedis jedis = DBUtil.getJEDIS();
		if(jedis.get("tag_event_"+user.getId()) != null){
			DBUtil.closeClient();
			return jedis.get("tag_event_time_stamp"+user.getId());
		}
		else {
			DBUtil.closeClient();
			return "1990-01-01T00:00:00";
		}		
	}
}
