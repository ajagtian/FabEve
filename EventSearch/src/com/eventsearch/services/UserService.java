package com.eventsearch.services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.eventsearch.entity.User;
import com.eventsearch.helpers.HttpsRequestHelper;

public class UserService {
	
	public void getUserFriends(User user, String access_token) {
		
		String q = "https://graph.facebook.com/me/friends&"+access_token;
		try {
			String response = HttpsRequestHelper.sendGet(q);
			JSONObject friends = new JSONObject(response);
			System.out.println(friends);
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		catch (JSONException e) {
		}
		
		
	}

}
