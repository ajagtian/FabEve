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
		<jsp:include page="./pre_master.jsp" />
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
		</div>
	</c:if>	
	<c:if test="${not empty user}">
		<div id = "fb_user_button" class = "big"> 
			<div id = "fb_profile_pic"> 
				<a href = "https://facebook.com/${user.id}"><img src = "${user.picture}" alt = "Profile"/></a>
			</div>
			<div id = "hellotext">
				Hello, ${user.first_name}
			</div>
			
		</div>
		<div id = "right_header">
		</div>
	</c:if>
				
	</header>
		<div class = "int_container" id = "left_i_c">
		</div>
	</div>
	
	
	
	
	
	
	
	</body>
	<jsp:include page="./post_master.jsp" />
	<script>
		App.init();
	</script>
</html>