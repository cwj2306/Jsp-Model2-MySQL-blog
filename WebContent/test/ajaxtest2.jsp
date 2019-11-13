<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<button onclick="sendPost3()">post 요청</button>
	
	<h3><div id="demo">0</div></h3>
	

	<script>
		
		let postNum2 = 0;
		let postNum3 = 0;
		let sum = 0;
	
		function sendPost2() {
			var xhttp = new XMLHttpRequest();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					postNum2 = this.responseText;
					console.log('post2 : ' + postNum2);
				}
			};

			xhttp.open("POST", "http://localhost:8000/blog/test2", true);
			xhttp.setRequestHeader("Content-type", "text/plain");
			xhttp.send();
			
		}
		
		function sendPost3() {
			
			sendPost2();
			
			var xhttp = new XMLHttpRequest();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					postNum3 = this.responseText;
					console.log('post3 : ' + postNum3);
					
					sum = Number(postNum2) + Number(postNum3);
					console.log('sum = ' + sum);
					
					document.querySelector("#demo").innerHTML = sum;
				}
			};

			xhttp.open("POST", "http://localhost:8000/blog/test3", true);
			xhttp.setRequestHeader("Content-type", "text/plain");
			xhttp.send();
		}
	</script>

</body>
</html>