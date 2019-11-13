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

@WebServlet("/test/reply")
public class ReplyTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8"); //MIME 타입
		
		//1. json 데이터 받기, getReader()
		BufferedReader in = request.getReader();
		String jsonData = in.readLine();
		System.out.println(jsonData);
		
		//2. json 데이터 => 자바 객체로 변환
		Gson gson = new Gson();
		Comment reply = gson.fromJson(jsonData, Comment.class);
		
		System.out.println("userId : " + reply.getUserId());
		System.out.println("boardId : " + reply.getBoardId());
		System.out.println("content : " + reply.getContent());
		
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.flush();
		
		
		
	}

}
