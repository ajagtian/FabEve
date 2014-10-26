package com.eventsearch.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	private String id;
	private String first_name;
	private String last_name;
	private String picture;
	private List<Event> userEvents;
	private List<User> friends;
	private ArrayList<String> keywords;
	
	
	public void setUpUser(JSONObject profile) {
		try {
			this.id = profile.getString("id");
		}
		catch (JSONException e) {
				// TODO: handle exception
		}
		try{
			this.first_name = profile.getString("first_name");
		}
		catch(JSONException e){
			
		}
		try {
			this.last_name = profile.getString("last_name");
		}
		catch(JSONException e ) {
			
		}
		try{
			this.picture = profile.getJSONObject("picture").getJSONObject("data").getString("url");
		}
		catch(JSONException e) {
			
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public List<Event> getUserEvents() {
		return userEvents;
	}
	public void setUserEvents(List<Event> userEvents) {
		this.userEvents = userEvents;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", first_name=" + first_name + ", last_name="
				+ last_name + ", picture=" + picture + ", userEvents="
				+ userEvents + ", friends=" + friends + ", keywords=~"
				+ keywords + "~]";
	}
	
	
	
	
	
	

}
