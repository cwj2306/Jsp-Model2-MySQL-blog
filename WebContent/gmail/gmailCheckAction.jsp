<%@page import="com.cos.util.Script"%>
<%@page import="dao.UserDao"%>
<%@page import="com.cos.model.User"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.cos.util.SHA256"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//code 값 받기
	String code = request.getParameter("code");
	
	boolean rightCode = false;
	User user = new User();
	
	HttpSession s = request.getSession();
	if(s.getAttribute("emailAuth") != null){
		user = (User)s.getAttribute("emailAuth");
		
		//return code값이랑 send code 값 비교
		rightCode = SHA256.getEncrypt(user.getEmail(), "salt").equals(code);
	}
		
	PrintWriter script = response.getWriter();
	if(rightCode){
		//인증 성공
		//db emailCheck컬럼 1로 update
		UserDao userDao = new UserDao();
		int result = userDao.updateEmailCheck(user.getUsername());
		
		if (result == 1) {
			script.println("<script>");
			script.println("alert('이메일 인증에 성공하였습니다.')");
			script.println("location.href='/blog/user/login.jsp'");
			script.println("</script>");
		} else {
			script.println("<script>");
			script.println("alert('일시적 오류입니다. 링크를 통해 다시 인증해 주세요.')");
			script.println("</script>");
		}
		
	} else{
		script.println("<script>");
		script.println("alert('이메일 인증에 실패하였습니다.')");
		script.println("location.href='errorPage.jsp'");
		script.println("</script>");
	}
			
%>
