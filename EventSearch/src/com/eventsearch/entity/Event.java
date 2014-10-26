package com.eventsearch.entity;



public class Event {
	
	private String name;
	private String id;
	private String description;
	private String location;
	private String startTime;
	private String endTime;
	private int attending;
	private Double lati;
	private Double longi;
	private String url;
	
	
	
	
	
	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"id\":\"" + id + "\", \"description\":\""
				+ description + "\", \"location\":\"" + location + "\", \"startTime\":\""
				+ startTime + "\", \"endTime\":\"" + endTime + "\", \"attending\":\""
				+ attending + "\", \"lati\":\"" + lati + "\", \"longi\":\"" + longi + "\", \"url\":\""
				+ url + "\"}";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getAttending() {
		return attending;
	}
	public void setAttending(int attending) {
		this.attending = attending;
	}
	public Double getLati() {
		return lati;
	}
	public void setLati(Double lati) {
		this.lati = lati;
	}
	public Double getLongi() {
		return longi;
	}
	public void setLongi(Double longi) {
		this.longi = longi;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
