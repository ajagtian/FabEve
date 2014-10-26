package com.eventsearch.helpers;

import redis.clients.jedis.Jedis;

public class DBUtil {
	
	private static Jedis jedis;
	public static Jedis getJEDIS() {
		jedis = new Jedis("localhost");
		return jedis;
	}
	public static void closeClient() {
		jedis.close();
	}
}
