<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String path = application.getRealPath("media");

		MultipartRequest multi = new MultipartRequest(
				request,
				path, //경로 
				1024*1024*2, //2MB
				"UTF-8", //인코딩방식
				new DefaultFileRenamePolicy() //동일한 파일명이 들어오면 파일명 뒤에 숫자를 붙임.			
		);
		
		String username = multi.getParameter("username");
		String filename = multi.getFilesystemName("userProfile"); //정책에 의해서 변경된 이름
		String originFilename = multi.getOriginalFileName("userProfile");//원래 파일 이름
		String contextPath = getServletContext().getContextPath();
		
		String filepath = contextPath + "/media/"+filename;
		
	%>
	path :<%=path%><br />
	username :<%=username%><br />
	filename :<%=filename%><br />
	originFilename :<%=originFilename%><br />
	
	
	<img src = "<%=filepath%>" width="300px" height="300px" style="border-radius: 50%;"/>
</body>
</html>