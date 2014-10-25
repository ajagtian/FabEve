package com.eventsearch.helpers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextHelper {
	
	private static ApplicationContext applicationContext;
	
	public static Object getBean(String id) {
		applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		return applicationContext.getBean(id);
	}

}
