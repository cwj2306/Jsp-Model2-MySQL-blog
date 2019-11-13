package com.cos.action.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.model.Reply;
import com.cos.util.Script;
import com.google.gson.Gson;

import dao.ReplyDao;

public class ReplyListAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		int commentId = Integer.parseInt(in.readLine());
		
		ReplyDao replyDao = new ReplyDao();
		List<Reply> replys = replyDao.findByCommentId(commentId);
		
		if(replys != null) {
			Gson gson = new Gson();
			String replysJson = gson.toJson(replys);
			
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(replysJson);
			out.flush();
					
		}else {
			Script.back(response);
		}
		
	}
}
