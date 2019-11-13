<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Fetch API 사용하기</h1>
	<button onclick="getNum1()">데이터 가져오기</button>
	
	<script>
	
		let num1 = 0;
		let num2 = 0;
		let sum = 0;
	
		function getNum1(){
			
			fetch("http://localhost:8000/blog/test2",{
				method:'POST',
				headers:{
					'content-type':'text/plain'	
				}			
			}).then(function(fetch에서받은respone객체){
				return fetch에서받은respone객체.text();
			}).then(function(위의then에서반환된값){
				num1 = 위의then에서반환된값;
				console.log('num1 : ' + num1);
				getNum2();
			});
			
		}
		
		function getNum2(){
			
			fetch("http://localhost:8000/blog/test3",{
				method:'POST',
				headers:{
					'content-type':'text/plain'	
				}			
			}).then(function(fetch에서받은respone객체){
				return fetch에서받은respone객체.text();
			}).then(function(위의then에서반환된값){
				num2 = 위의then에서반환된값;
				console.log('num2 : ' + num2);
				sum = Number(num1) + Number(num2);
				console.log(sum);
			});
			
		}
		
	</script>

</body>
</html>