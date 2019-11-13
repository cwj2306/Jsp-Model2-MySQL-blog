<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$(document).ready(function(){
			$("#my_hide").click(function(){
				$("p").hide();
			});
			
			$("#my_animate").click(function(){
				$("div").animate({
					left: '250px'
				});
			});
			
		});
	</script>
	
</head>
<body>

	<h1>제목</h1>
	
	<p>p11111111</p>
	<p>p2222222</p>
	
	<button id="my_hide">click</button>

	<div style="background: #98bf21; height:200px; width:200px; position:absolute;">
	
	</div>
	
	<button id="my_animate">start animation</button>
	

</body>
</html>