package com.eventsearch.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventsearch.entity.Event;
import com.eventsearch.helpers.HttpsRequestHelper;

@Controller
public class EventController {
	
	@RequestMapping(value = "/events.io")
	public ModelAndView getEventsByKeyWord(HttpServletRequest req, HttpServletResponse res)  {
		String [] keywords = req.getParameter("keywords").split(",");
		ArrayList<Event> events = new ArrayList<Event>();
		
		String access_token = (String)req.getSession(false).getAttribute("access_token");
		for (String keyword : keywords) {
			String query = "https://graph.facebook.com/search?q="+keyword+"&type=event&"+access_token;
			try {
				JSONObject event = new JSONObject(HttpsRequestHelper.sendGet(query));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
		
		
	}

}
