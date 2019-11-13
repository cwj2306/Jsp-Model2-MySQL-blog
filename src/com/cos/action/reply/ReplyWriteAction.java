package com.cos.action.reply;

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

public class ReplyWriteAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		String content = request.getParameter("content");
		
		Reply reply = new Reply();
		reply.setCommentId(commentId);
		reply.setUserId(userId);
		reply.setContent(content);
		
		//db에 insert하고
		ReplyDao replyDao = new ReplyDao();
		int result = replyDao.save(reply);
		
		if(result==1) {
			//모든 reply 다 들고오기
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
			
		}else {
			Script.back(response);
		}
		
	}

}
