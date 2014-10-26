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
			} );
		}		
}