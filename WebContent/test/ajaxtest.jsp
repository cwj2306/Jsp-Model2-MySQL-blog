<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Ajax 테스트</h1>
	<hr />

	<div id="demo">
		1
	</div>
	<button type="button" onclick="loadDoc()">Change count</button>



	<script>
		/* 댓글 객체로 보내기 */
		var reply = {
			id: null,
			boardId: 1,
			userId: 3,
			content: 'input태그에 적힌 값 들고오기',
			createDate: null
		}
	
		var replyJson = JSON.stringify(reply);
		
		function loadDoc() {
			var xhttp = new XMLHttpRequest();
			
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.querySelector("#demo").innerHTML = this.responseText;
				}
			};
			
			xhttp.open("POST", "http://localhost:8000/blog/test", true);
			xhttp.setRequestHeader("Content-type", "application/json");
			xhttp.send(replyJson);
		}
	</script>

</body>
</html>