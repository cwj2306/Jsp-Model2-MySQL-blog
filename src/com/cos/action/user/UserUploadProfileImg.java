package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.model.User;
import com.cos.util.Script;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.UserDao;

public class UserUploadProfileImg implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		ServletContext application = request.getServletContext();
		
		String path = application.getRealPath("media");

		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(
					request,
					path, //경로 
					1024*1024*2, //2MB
					"UTF-8", //인코딩방식
					new DefaultFileRenamePolicy() //동일한 파일명이 들어오면 파일명 뒤에 숫자를 붙임.			
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String filename = multi.getFilesystemName("userProfile"); //정책에 의해서 변경된 이름
//		String originFilename = multi.getOriginalFileName("userProfile");//원래 파일 이름
		
		String contextPath = application.getContextPath(); 
		String filepath = contextPath + "/media/"+filename;
		
		UserDao userDao = new UserDao();
		int result = userDao.updateUserProfile(user.getId(), filepath);
		
		if(result == 1) {
			user.setUserProfile(filepath);
			session.setAttribute("user", user);
			
			response.sendRedirect("/blog/board?cmd=list&page=1");
			
		}else {
			Script.back(response);
		}
		
	}
}
