package com.cos.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GmailTestController
 */
@WebServlet("/test/gmail")
public class GmailTestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GmailTestController() {
        super();
    }
    
    //실제로 구현할 때는 컨트롤러가 아니라 action jsp 만들고 거기에 구현하기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email= request.getParameter("email");
		
		//db에 insert했다고 가정
		
		response.sendRedirect("/blog/test/gmailSendAction.jsp?email="+email);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
