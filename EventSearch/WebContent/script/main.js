	App = {
			init:function() {
				$(document).ready(function() {
					
						fbDiv = $("#lltext");
						$.ajax({
							type:"GET",
							url:"getFBWidget.io",
							success:function(text){
								console.log(text);
								fbDiv.html(text);
							},
							error:function(e) {
								console.log("error");
								console.log(e);
							}
						});
				
				
				$("#lltext a").click(function() {
					$("#right_header").fadeOut(1000);
				});
				
				$("#setLocation").click(function() {
					console.log("click");
					var location = $("#location").val();
					console.log(location);
					if(location == null || location == undefined) {
					}
					else {
						$.ajax({
							type:"GET",
							data:{ location: $("#location").val().trim()},
							url:"./set_location.io",
							success:function() {
								$("#location").addClass("readonly");
							}
							
						});
					}
				});
				
				
				$("#setTag").click(function () {
					var tagname = $("#tag_input").val();
					if(tagname == "" || tagname == undefined || tagname == null) {
						
					}else {
						var html = "<input type = 'text' class = 'tag' value = '"+tagname+"'>";
						$("#tag_field").append(html);
					}
				});	
				
				$("#setKeyWords").click(function (){
					keywords = "";
					$("#tag_field input").each(function () {
						keywords +=  $(this).val().trim()+"~";
					});
					console.log(keywords);
					$.ajax({
						type:"GET",
						url:"./settags.io",
						data:{keywords:keywords},
						success:function() {
							$(".tag").css("color","gray");
						}
					
					});
				}); 
				
				$("#getTrendingEvents").click(function(){
					$.ajax({
						type:"GET",
						url:"./events.io",
						data:{force:"1"}
					});
					
				});
				$("#drop").click(function() {
					var ele = $(this).next();
					if(ele.hasClass("hidden")) {
						ele.removeClass("hidden");
						ele.slideDown(2000);
					}
					else {
						ele.addClass("hidden");
						ele.slideUp(2000);
					}
				});

				
				
				
				
			});
			}
			
	}