package com.eventsearch.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventsearch.debservice.DBQuery;
import com.eventsearch.entity.Event;
import com.eventsearch.entity.User;
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
	
	@RequestMapping(value = "events.io")
	public void getEventSuggestions(HttpServletRequest request, HttpServletResponse response) {
		
		String [] keywords;
		
	}
	@RequestMapping("/gettags.io")
	public void getTags(HttpServletRequest req , HttpServletResponse res) {
		HttpSession session = req.getSession(false);
		User user = (User)session.getAttribute("user");
		String tags = DBQuery.getUserTags(user);
		
		res.setContentType("text/plain");
		PrintWriter pw;
		try {
			pw = res.getWriter();
			pw.print(tags);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@RequestMapping(value = "/settags.io")
	public void setKeywords(HttpServletRequest req, HttpServletResponse res) {
		
		String [] keywords = req.getParameter("keywords").split("~");
		ArrayList<String> tags = new ArrayList<String>();
		HttpSession session = req.getSession(false);
		
		if(session.getAttribute("user") != null) {
			
			User loggedInUser = (User)session.getAttribute("user");
			for (String keyword : keywords) {
				tags.add(keyword);
			}
			loggedInUser.setKeywords(tags);
			DBQuery.pingAndSaveUser(loggedInUser,true);
		}
		
		
		
	}

}
