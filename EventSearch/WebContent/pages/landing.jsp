<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="author" content="Akshay Jagtiani[ajagtian@usc.edu]">
		<meta lang="en-US">
		<title>Event Suggest - Home</title>
		<link rel = "stylesheet" href = "./style/main.css" />
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
		<link rel="shortcut icon" href="./images/favicon.ico">
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script src = "./script/main.js" type = "text/javascript"></script>
		<script>
			App.init();
			function setVariables() {
				$("document").ready(function() {
					$.ajax({
						type:"GET",
						url:"./get_location.io",
						success:function(data) {
							$("#location").val(data);
						}
					});
					
					$.ajax({
						type:"GET",
						url:"./gettags.io",
						success:function(data) {
							if(data != "" || data != null) {
								var tags = data.split(",");
								console.log(tags)
								for (i = 0 ; i < tags.length ; i++) {
									tag = tags[i];
									var html = "<input type = 'text' class = 'tag' value = '"+tag+"'>";
									$("#tag_field").append(html);
								}
									
							}
						}
					});
				});
			}
		</script>
	</head>
	<body lang="en">
	<div class = "ext_container">
	<header>
	<c:if test="${empty user}">
		<div id = "fb_login_button" class = "big"> 
			<div id = "fb_profile_pic"> 
			</div>
			<div id = "lltext">
			</div>
		</div>
		<div id = "right_header">
			<c:if test="${empty user}"><span>Login to get event suggestions</span></c:if>
		</div>
	</c:if>	
	<c:if test="${not empty user}">
		<div id = "fb_user_button" class = "big"> 
			<div id = "fb_profile_pic"> 
				<a href = "https://facebook.com/${user.id}"><img src = "${user.picture}" alt = "Profile"/></a>
			</div>
			<div id = "hellotext">
				What's up, ${user.first_name}
			</div>
			
		</div>
		<div id = "right_header">
		<c:if test="${not empty user}">Set your preferences and we will suggest some cool FB events</c:if>
		</div>
	</c:if>
				
	</header>
		<c:if test="${not empty user}">
			<script>setVariables()</script>
		</c:if>
	
		<div class = "int_container" id = "left_i_c">
			
			<div id = "location_tag_input">
				<input class = "input_1" id = "location" type = "text" placeholder="set you location"/>
				<button class = "button_1" id = "setLocation">></button>
				<br /><br /><br /><br />	
				<input class = "input_1" id = "tag_input" placeholder="set some keywords" list = "hotwords"/>
				<datalist id = "hotwords">
					<option value="Hackathon" >
						<option value="Free Food" >
						<option value="Fun Stuff" >
						<option value="Hiking" >
						<option value="Women In Tech" >
						<option value="Internships" >
						<option value="Community" >
						<option value="New Year Eve" >
						<option value="Dance" >
						<option value="Concerts" >
						<option value="Research seminars" >
						<option value="Ideation" >
						<option value="Masquerades" >
						<option value="Halloween" >
						<option value="Cultural" >
						<option value="Spiritual" >
						<option value="Mega Sale" >
						<option value="Auctions" >
						<option value="Wedding" >
						<option value="Carnival" >
						<option value="Ceremony" >
						<option value="Festival" >
						<option value="Business Expo" >
						<option value="Auto Expo" >
						<option value="Retail" >
						<option value="Camping" >
						<option value="Roadtrip" >
						<option value="Sporting Event" >
						<option value="Tournament" >
						<option value="Group Trip" >
						<option value="Audition" >
						<option value="Exhibition" >
						<option value="Football" >
						<option value="Performance" >
						<option value="Rehearsal" >
						<option value="Business Meeting" >
						<option value="Workshops" >
						<option value="Lectures" >
						<option value="Study Group" >
						<option value="Fundraiser" >
						<option value="Rally" >
						<option value="Protest" >
						<option value="Reunion" >
						<option value="Birthday Party " >
						<option value="Cocktail Party" >
						<option value="Club Party" >
						<option value="Barbecue" >
						<option value="Bar Nights" >
						<option value="Social Mixer" >
						<option value="Fraternity" >
						<option value="Dinner Parties" >
						<option value="Pool Parties" >
						<option value="Charity" >
						<option value="Ted Talk" >
						<option value="Beach Parties" >
						<option value="Comic Expos" >
						<option value="Film Festivals" >
						<option value="Career Fairs" >
						<option value="Yoga" >
						<option value="Leadership" >
						<option value="Entrepreneur">
						<option value="Political Talks" >
						<option value="Movie Premiere" >
						<option value="Promotional" >
						<option value="Gaming Competition" >
						<option value="Networking" >
						<option value="Conventions" >
						<option value="Social Awareness" >
						<option value="Church" >
				</datalist>
				<button class = "button_1" id = "setTag">></button>
				<div id = "tag_field">
			</div>
				<button id = "setKeyWords" class = "button_1"> 
					>
				</button>
			</div>
			<br /><br /><br /><br />
			<a  href = "./events.io?force=1" class = "button_1" id = "getTrendingEvents">GET ></a>&nbsp;&nbsp;<span>Trending</span>
			<c:if test="${not empty autoSuggestedEvents}">
			<div id = "trending_events">
					<h2 class = "right">Trending Events</h2>
					<table>
						<thead>
							<tr><th>Name</th><th>Attending</th><th>Location</th><th>URL</th><th>Venue</th></tr>
						</thead>
							<c:forEach items="${autoSuggestedEvents}" var="event">
								<tr id = "drop"><td>${event.name}</td><td>${event.attending}</td><td>${event.location}</td><td><a target = "_blank" href = "${event.url}">URL</a></td><td><a href = "https://www.google.com/maps/@${event.lati},${event.longi}" target = "_blank">Venue</a></td></tr>
								<tr id = "desc" class = "hidden"><td colspan = '5'>&nbsp;Start Time : ${event.startTime}<br />End Time : ${event.endTime}<br />Description - <br />${event.description}</td></tr>
							</c:forEach>
					</table>
			</div>
			</c:if>	
			
			<br /><br /><br /><br /><br />
			<a  href = "./tagged_events.io?force=1" class = "button_1" id = "getTaggedEvents">GET ></a>&nbsp;&nbsp;<span>Favorites</span>
			<c:if test="${not empty tagSuggestions}">
			<div id = "tagged_events">
					<h2 class = "right">Favorite Events</h2>
					<table>
						<thead>
							<tr><th>Name</th><th>Attending</th><th>Location</th><th>URL</th><th>Venue</th></tr>
						</thead>
							<c:forEach items="${tagSuggestions}" var="event">
								<tr id = "drop"><td>${event.name}</td><td>${event.attending}</td><td>${event.location}</td><td><a target = "_blank" href = "${event.url}">URL</a></td><td><a href = "https://www.google.com/maps/@${event.lati},${event.longi}" target = "_blank">Venue</a></td></tr>
								<tr id = "desc" class = "hidden"><td colspan = '5'>Start Time : ${event.startTime}<br /><br />End Time : ${event.endTime}<br /><br />Description - <br /><br />${event.description}</td></tr>
							</c:forEach>
					</table>
			</div>
			</c:if>	
	</div>
	
	</body>
	
</html>