<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>

	<div>
		<img id="img__wrap" />
	</div>

	<!-- enctype는 mime타입에서 찾아봐서 써주자 -->
	<form action="uploadAction.jsp" method ="POST" enctype="multipart/form-data">
		username : <input type="text" name="username" /><br />
		image : <input id="img__input" type="file" name="userProfile"/><br />
		<input type = "submit" value="전송"/>
	</form>
	
	<script src="/blog/js/jquery-3.2.1.min.js"></script>
	<script>
		$("#img__input").on("change", handleImgFile);
	
		function handleImgFile(e){
			console.log(e);
			console.log(e.target.files);
			console.log(e.target.files[0]);
			
			var f = e.target.files[0];
			
			if(f.type.match("image.*")){
				var reader = new FileReader();
				
				reader.onload = function(e){
					console.log(e.target.result);
					$("#img__wrap").attr("src", e.target.result);
				}
				
				reader.readAsDataURL(f);
			}else{
				alert("이미지 파일이 아닙니다.");
				$("#img__wrap").attr("src", "");
			}
			
		}
	</script>
	
</body>
</html>