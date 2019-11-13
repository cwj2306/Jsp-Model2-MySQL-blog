package com.cos.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.model.User;
import com.cos.util.SHA256;
import com.cos.util.Script;

import dao.UserDao;

public class UserUpdateAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// form태그로부터 받은 데이터 DB에 insert하고 페이지 이동
		
		// 해야할 것 : null값 처리하기, 유효성 검사
		int id = Integer.parseInt(request.getParameter("id"));
		
		String username = request.getParameter("username");
		String rawPassword = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		String password = SHA256.getEncrypt(rawPassword, "cos");
		
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password); // 암호화 해야함
		user.setEmail(email);
		user.setAddress(address);
		
		//dao연결
		UserDao dao = new UserDao();
		int result = dao.update(user);
		
		if (result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			response.sendRedirect("/blog");
		}else {
			Script.back(response);
		}
		
	}
}
