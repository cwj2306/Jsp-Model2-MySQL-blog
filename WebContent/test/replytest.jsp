<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		let replyJS = {
				id: null,
				boardId: 5,
				userId: 3,
				content: "내용입니다.",
				createDate: null
		}
		
		function send(){
			// json으로 변환
			let jsonData = JSON.stringify(replyJS);
			
			// ajax 호출 /blog/test/reply, post, application/json utf-8
			// jQuery
			$.ajax({
				url: "/blog/test/reply",
				type: "POST",
				contentType: "application/json; charset=utf-8",
				data: jsonData,
				success: function(r){
					alert(r);
				},
				error: function(xhr){
					console.log("xhr.status : " + xhr.status);
				}
			});
			
			// fetch then
			/* fetch("/blog/test/reply",{
				method:'POST',
				headers:{
					'content-type':'application/json; charset=utf-8'
				},
				data: jsonData
			}).then(function(respone){
				return respone.text();
			}).then(function(data){
				alert(data);
			}); */
			
		}
	</script>
	
</head>
<body>

	<button onclick="send()">전송</button>

</body>
</html>