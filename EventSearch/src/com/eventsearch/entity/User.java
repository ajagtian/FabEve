package com.eventsearch.entity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	private String id;
	private String first_name;
	private String email;
	private String name;
	private String gender;
	private String last_name;
	private List<Event> userEvents;
	private List<User> friends;
	
	
	public void setUpUser(JSONObject profile) {
		try {
			this.id = profile.getString("id");
			this.first_name = profile.getString("first_name");
			this.last_name = profile.getString("last_name");
			this.email = profile.getString("email");
			this.name = profile.getString("email");
			this.gender = profile.getString("gender");
		} 
		catch (JSONException e) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	@Override
	public String toString() {
		return "User [id=" + id + ", first_name=" + first_name + ", email="
				+ email + ", name=" + name + ", gender=" + gender
				+ ", last_name=" + last_name + ", userEvents=" + userEvents
				+ ", friends=" + friends + "]";
	}
	

	
	

}
