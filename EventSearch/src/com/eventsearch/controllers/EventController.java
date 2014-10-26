package com.eventsearch.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventsearch.debservice.DBQuery;
import com.eventsearch.entity.Event;
import com.eventsearch.entity.User;
import com.eventsearch.helpers.HttpsRequestHelper;

@Controller
public class EventController {

	final Comparator<Event> EVENT_COMPARATOR = new Comparator<Event>() {

		public int compare(Event o1, Event o2) {
			if(o1.getAttending() > o2.getAttending()) {
				return -1;
			}
			else if(o1.getAttending() <  o2.getAttending()) {
				return 1;
			}
			else {
				return 0;
			}	
		}
	};


	@RequestMapping(value = "tagged_events.io")
	public ModelAndView getTaggedEvents(HttpServletRequest request, HttpServletResponse response) {
		
		boolean force = false;
		if(request.getParameter("force") != null && request.getParameter("force").compareTo("1")==0) {
			force = true;
		}
		ModelAndView mav  = new ModelAndView();
		mav.setViewName("pages/landing.jsp");
		HttpSession session = request.getSession(false); 
		if (session.getAttribute("user") != null) {
			
			mav.addObject("user",(User)session.getAttribute("user"));
			
			String access_token = (String)session.getAttribute("access_token");
			User user = (User)session.getAttribute("user");
			
			String location = DBQuery.getUserLocation(user);
			String tags [] = DBQuery.getUserTags(user).split(",");
			
			ArrayList<Event> tagSuggestions = new ArrayList<Event>();
			// 	need o get special events
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date now = new Date();
			Date then;
			int period = 0;
			try {
				then = formatter.parse(DBQuery.getTaggedEventSaveTimeStamp(user));
				period = (int)((now.getTime() - then.getTime())/(60*60*60));
			} catch (ParseException e2) {
				System.out.println(e2.getMessage());
			}
			if(force || period > 24 && tags[0] != "") {
				for (String tag : tags) {
					String q;
					try {
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						String since = format.format(d);
						q = "https://graph.facebook.com/search?q="+URLEncoder.encode(location+" "+tag,"UTF-8")+"&limit=200&type=event&fields=id,name,start_time,end_time,description,venue,location&"+access_token+"&since="+since;
						String events = HttpsRequestHelper.sendGet(q);
						JSONObject jEvents= new JSONObject(events);
						String name,id,event_location,start_time,end_time,url,desc;
						Double lati,longi;
						int goingCount = 0;
						JSONArray eventData = jEvents.getJSONArray("data");
						for (int i = 0 ; i < eventData.length() ; i++) {
							JSONObject event = (JSONObject)eventData.get(i);
							id = event.getString("id");
							name = event.getString("name");
							event_location= "";
							try{
							event_location = event.getString("location");
							}
							catch(JSONException e){
								System.out.println(e.getMessage());
							}

							start_time = end_time = "";
							try{
								start_time = event.getString("start_time");
								end_time = event.getString("end_time");
							}
							catch(JSONException e) {
								System.out.println(e.getMessage());
							}
							desc = "";
							try{
								desc = event.getString("description");
							}							
							catch(JSONException e){
								System.out.println(e.getMessage());
							}

							lati = longi = 0.0;
							goingCount = 0;
							try{
								JSONObject venue = event.getJSONObject("venue");
								lati = venue.getDouble("latitude");
								longi = venue.getDouble("longitude");
							}
							catch(JSONException e) {
								System.out.println(e.getMessage());
							}
							//	find attendees of this event
							String a = "https://graph.facebook.com"+"/"+id+"?fields=attending&"+access_token;
							try {
								String ding = HttpsRequestHelper.sendGet(a);
								JSONObject ten = new JSONObject(ding);
								JSONObject attending  =ten.getJSONObject("attending");
								int at = attending.getJSONArray("data").length();
								goingCount = at;
							}
							catch(JSONException e) {
								System.out.println(e.getMessage());
							}
							url = "https://facebook.com/events"+"/"+id;
							
							Event tempEvent = new Event();

							tempEvent.setAttending(goingCount);
							tempEvent.setDescription(desc);
							tempEvent.setEndTime(end_time);
							tempEvent.setId(id);
							tempEvent.setLati(lati);
							tempEvent.setLocation(event_location);
							tempEvent.setLongi(longi);
							tempEvent.setName(name);
							tempEvent.setStartTime(start_time);
							tempEvent.setUrl(url);
							tagSuggestions.add(tempEvent);		
						}
					} catch (UnsupportedEncodingException e1) {
						System.out.println(e1.getMessage());
					}
					catch (IOException e) {
						System.out.println(e.getLocalizedMessage());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Collections.sort(tagSuggestions, EVENT_COMPARATOR);
				mav.addObject("tagSuggestions",tagSuggestions);
				DBQuery.addTaggedEventsToDB(user, tagSuggestions);
				DBQuery.setTaggedEventSaveTimeStamp(user);
			}
			else if(period < 24) {
				// get from db
				String taggedEvents = DBQuery.getTaggedEventsFromDB(user);
				try {
					JSONObject jevent = new JSONObject(taggedEvents);
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		else {
			System.out.println("Session not found");
			//	 redirect to home
		}

		return mav;
	}

	@RequestMapping(value = "/events.io")
	public ModelAndView getEventSuggestionsBySearch(HttpServletRequest request, HttpServletResponse response) {
		
		boolean force = false;
		if(request.getParameter("force") != null && request.getParameter("force").compareTo("1")==0) {
			force = true;
		}
		ModelAndView mav  = new ModelAndView();
		
		mav.setViewName("pages/landing.jsp");
		
		HttpSession session = request.getSession(false); 
		
		if (session.getAttribute("user") != null) {
			
			mav.addObject("user",(User)session.getAttribute("user"));
			
			String access_token = (String)session.getAttribute("access_token");
			
			User user = (User)session.getAttribute("user");
			
			String location = DBQuery.getUserLocation(user);
			String tags [] = DBQuery.getUserTags(user).split(",");

			if(force || (location == "" && tags[0] == "")) {
				Date then = null;
				try {
					then = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(DBQuery.getGenericEventSaveTimeStamp(user));
				} catch (ParseException e2) {
					System.out.println(e2.getMessage());
				}
				Date now = new Date();
				int period = (int)(now.getTime() - then.getTime())/(60*60*60);
				if( period > 24) {
					System.out.println("can't get suggestions");
					// No criteria get events from groups
					ArrayList<String> groupIds = new ArrayList<String>();
					ArrayList<Event> eventsuggestions = new ArrayList<Event>(); 
					String g = "https://graph.facebook.com/v2.1/me/groups?"+access_token;
					try {
						String groups = HttpsRequestHelper.sendGet(g);
						JSONObject jGroup = new JSONObject(groups);
						JSONArray data = jGroup.getJSONArray("data");
						for (int i = 0 ; i < data.length() ; i++) {
							groupIds.add(((JSONObject)data.get(i)).getString("id"));
						}
						for (String id : groupIds) {
							// get the events of the group
							Date d = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							String since = format.format(d);
							String q = "https://graph.facebook.com"+"/"+id+"/events?"+access_token+"&since="+since;
							String events = HttpsRequestHelper.sendGet(q);
							JSONObject jEvents = new JSONObject(events);
							JSONArray eventdata = jEvents.getJSONArray("data");
							for (int i = 0 ; i< eventdata.length(); i++) {
								String eventid = ((JSONObject)eventdata.get(i)).getString("id");
								//	get this event 
								String e = "https://graph.facebook.com"+"/"+eventid+"?fields=attending,description,name,end_time,start_time,location,venue&"+access_token;
								String eventRespose = HttpsRequestHelper.sendGet(e);
								JSONObject jEvent = new JSONObject(eventRespose);
								JSONObject attending = jEvent.getJSONObject("attending");
								int goingCout = attending.getJSONArray("data").length();
								String desc = jEvent.getString("description");
								String name = jEvent.getString("name");
								String end_time = jEvent.getString("end_time");
								String start_time = jEvent.getString("start_time");
								String eventLocation = jEvent.getString("location");
								Double lati = 0.0;
								Double longi = 0.0;
								try{
									lati = jEvent.getJSONObject("venue").getDouble("latitude");
									longi = jEvent.getJSONObject("venue").getDouble("longitude");
								}
								catch(JSONException e1) {
									System.out.println(e1.getMessage());	
								}
								String event_id = jEvent.getString("id");
								Event event = new Event();
								event.setId(event_id);
								event.setAttending(goingCout);
								event.setDescription(desc);
								event.setEndTime(end_time);
								event.setStartTime(start_time);
								event.setLati(lati);
								event.setLongi(longi);
								event.setLocation(eventLocation);
								event.setName(name);
								event.setUrl("https://facebook.com/events/"+id);
								eventsuggestions.add(event);	
							}
						}	
					} catch (IOException e) {
						System.out.println(e.getMessage());
					} catch (JSONException e) {
						System.out.println(e.getMessage());
					}

					// events to suggets to used  when no preference is given are event-suggestions
					System.out.println(eventsuggestions);
					DBQuery.setGeneralEventsInDb(user, eventsuggestions);
					DBQuery.setGenericEventSaveTimeStamp(user);
					Collections.sort(eventsuggestions,EVENT_COMPARATOR);
					mav.addObject("autoSuggestedEvents",eventsuggestions);
				}
				else {
						// can get the general events from db
						//Str
				}	
			}
			else {
				//no need to get these events no
			}
		}
		return mav;


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
			DBQuery.setUserTags(loggedInUser);
		}
	}

	@RequestMapping(value = "/set_location.io")
	public void setEcoSystemLocation(HttpServletRequest req, HttpServletResponse rep){
		String location = req.getParameter("location");
		HttpSession session = req.getSession(false);
		if (session.getAttribute("user") != null) {
			User user = (User)session.getAttribute("user");
			DBQuery.setUserLocation(user, location);
		}
		else {
			System.out.println("Logged Out");

		}
	}

	@RequestMapping(value = "/get_location.io")
	public void getEcoSystemLocation(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession(false);
		String location = "";
		if(session.getAttribute("user") != null) {
			User user = (User)session.getAttribute("user");
			location = DBQuery.getUserLocation(user);

			try {
				PrintWriter out = res.getWriter();
				res.setContentType("text/plain");
				out.print(location);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else {
			System.out.println("No Session");
		}
	}

}
