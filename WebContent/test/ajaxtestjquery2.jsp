<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$(document).ready(function(){
			$.ajax({
				url: "/blog/test3",
				type: "POST",
				dataType: "text", //서버로부터 받을 데이터 타입(기대)
				contentType: "text/plain; charset=utf-8", //MIME 타입
				data: "어떤 데이터든지 가능",
				success: function(r){
					alert(r);
				},
				error: function(){
					alert('실패');
				}
				
			});
			
		});
		
		function send(){
			
			var formData = $("#user").serialize();
			console.log(formData);
			
			$.ajax({
				url: "/blog/test4",
				type: "POST",
				data: formData,
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success: function(r){
					alert(r);
				},
				error: function(xhr){
					console.log("xhr.status : " + xhr.status);
				}
			});
			
		}
		
	</script>
	
</head>
<body>

	<h1>jQuery로 ajax</h1>
	
	<form id="user">
		<input type="text" name="username" value="아이디"/></br>
		<input type="text" name="password" value="1234"/></br>
		<input type="button" onclick="send()" value="전송"/>
	</form>
	
	<div id="demo"></div>
	
	
	

</body>
</html>