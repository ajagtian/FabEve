package com.eventsearch.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventsearch.debservice.DBQuery;
import com.eventsearch.entity.User;
import com.eventsearch.helpers.ContextHelper;
import com.eventsearch.helpers.EventSuggestApp;
import com.eventsearch.helpers.HttpsRequestHelper;
import com.eventsearch.helpers.Properties;
import com.eventsearch.services.UserService;

@Controller
public class FBLoginController {
	
	
	static final Logger LOG = Logger.getLogger(FBLoginController.class);
	
	EventSuggestApp app = (EventSuggestApp)ContextHelper.getBean("eventSuggestApp");
	Properties props = (Properties)ContextHelper.getBean("properties");

	

	@RequestMapping(value = "/getFBWidget.io")
	public void getWidget(HttpServletRequest req, HttpServletResponse res) {
		
		String dialogRequest = "https://facebook.com/dialog/oauth?client_id="+app.getAppId()+"&redirect_uri="+props.getApp_home()+"&scope="+props.getApp_scope();
		
		String html = "<a href = "+ dialogRequest +">Facebook Login</a>";
		
		res.setContentType("text/html");
		
		PrintWriter out = null;
		try {
			out = res.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		out.print(html);	
	}
	
	@RequestMapping("/login.io")
	public void dpFBLogin(HttpServletRequest req, HttpServletResponse res) throws MalformedURLException {
		
		String code = req.getParameter("code");
		String access_token = null;
		String appId = app.getAppId();
		String appSecret = app.getAppSecret();
		
		if(code == null || code.isEmpty()) {
		}
		try {
			String oauthRequest = "https://graph.facebook.com/oauth/access_token?client_id="+appId+
					"&redirect_uri="+URLEncoder.encode(props.getApp_home(), "UTF-8")+"&client_secret="+appSecret+
					"&code="+code;
			access_token = HttpsRequestHelper.sendGet(oauthRequest);
			System.out.println(access_token);
			if(access_token.startsWith("{")){

				throw new Exception("error on requesting token: " + access_token + " with code: " + code);
			}
		} 
		catch (IOException e) {
			LOG.error("Could not make url connection to FB",e);
		} 
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		// create a user session now.
		HttpSession session = req.getSession(false);
		session.setAttribute("access_token", access_token);

		String graph = null;

		String userRequest = "https://graph.facebook.com/me?fields=id,first_name,last_name,picture,location&"+access_token;

		try {
			graph = HttpsRequestHelper.sendGet(userRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
		}

		// got user profice
		JSONObject profile = null;
		try {
			profile = new JSONObject(graph);
		} 
		catch (JSONException e) {
		
		}
		
		User loggedInUser = new User();
		loggedInUser.setUpUser(profile);
		System.out.println(loggedInUser);
		DBQuery.pingAndSaveUser(loggedInUser,false);
		session.setAttribute("user", loggedInUser);
			
		//redirect to landing page
		
		try {
			res.sendRedirect("./home.io");
		} catch (IOException e) {
		}
		
	}
	
}
