package com.cos.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.model.User;
import com.cos.util.SHA256;
import com.cos.util.Script;

import dao.UserDao;

public class UserLoginAction implements Action{
	
	private static final String TAG = "UserLoginAction : ";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//나중에 할 것 : 유효성 검사
		String username = request.getParameter("username");
		String rawPassword = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");
		
		String password = SHA256.getEncrypt(rawPassword, "cos");
		
//		System.out.println(TAG + "username : " + username);
//		System.out.println(TAG + "password : " + password);
//		System.out.println(TAG + "cookie : " + rememberMe);
		
		UserDao dao = new UserDao();
		int result = dao.findByUsernameAndPassword(username, password);
		
		//-1:로그인 실패 , 0:이메일 미인증 , 1:로그인 성공
		if (result == 1) {

			User user = dao.findByUsername(username);
			//세션 등록
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			if (rememberMe != null) {
				//쿠키 저장
				Cookie cookie = new Cookie("username", username);
				cookie.setMaxAge(6000);//6000초
				response.addCookie(cookie);
			}else {
				//쿠키 삭제
				Cookie cookie = new Cookie("username", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			
			response.sendRedirect("/blog");			

		} else if(result==0){
			
			response.sendRedirect("/blog/user/noEmailCheck.jsp");
		} else {
			Script.back(response);
		}
	}
}
