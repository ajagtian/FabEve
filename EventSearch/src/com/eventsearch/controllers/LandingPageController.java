package com.eventsearch.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventsearch.entity.User;
import com.eventsearch.helpers.ContextHelper;
import com.eventsearch.services.UserService;

@Controller
public class LandingPageController {
	
	private UserService userService = (UserService)ContextHelper.getBean("userService");

	@RequestMapping(value = "/home.io")
	public ModelAndView getLandingPage(HttpServletRequest req, HttpServletRequest res) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pages/landing.jsp");;
		HttpSession session  = req.getSession(false);
		if(session.getAttribute("user") != null) {
			mav.addObject("user",session.getAttribute("user"));
			// get friends 
			userService.getUserFriends((User)session.getAttribute("user"), (String)session.getAttribute("access_token"));
		}
		return mav;
	}
	
	

}
