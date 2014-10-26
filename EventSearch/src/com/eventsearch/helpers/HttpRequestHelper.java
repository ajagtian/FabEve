package com.eventsearch.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpRequestHelper {
	
	final static Logger LOG = Logger.getLogger(HttpRequestHelper.class);
	
	public static String sendGet(String location) throws IOException {
		final String user_agent = getProps().getUser_agent();
		URL url = new URL(location);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", user_agent);
		
		int responseCode = con.getResponseCode();
		
		LOG.info("Sending GET Request - "+url);
		LOG.info("Response Code - "+responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		LOG.info("Responce = "+response.substring(0,10)+"...");
		return response.toString();
	}
	
	public static String sendPOST(String location,String params) throws IOException {
		final String user_agent = getProps().getUser_agent();
		URL url = new URL(location);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		//	set connection properties for POST
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", user_agent);
		con.setRequestProperty("Accept-Language","en-US,en;q=0.5");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		
		LOG.info("Sending POST Request - "+url);
		LOG.info("PARAMS - "+params);
		LOG.info("Response Code - "+responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		LOG.info("Responce = "+response.substring(0,10)+"...");
		
		return response.toString();
	}
	
	private static Properties getProps() {
		return (Properties)ContextHelper.getBean("properties");	
	}	
	
	
}
