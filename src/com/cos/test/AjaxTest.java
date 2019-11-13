package com.cos.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.model.Comment;
import com.google.gson.Gson;

@WebServlet("/test")
public class AjaxTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); //MIME 타입
		
		//요청 데이터 처리
		BufferedReader in = request.getReader();
		String replyJson = in.readLine();
		System.out.println("요청 데이터 : " + replyJson);
		
		Gson gson = new Gson();
		Comment reply = gson.fromJson(replyJson, Comment.class);
		System.out.println(reply.getId());
		System.out.println(reply.getBoardId());
		
		//응답 데이터 처리
		String jsonData = "{\"name\":\"hiii\", \"sal\":100}";
		
		PrintWriter out = response.getWriter();
		out.println(jsonData);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
