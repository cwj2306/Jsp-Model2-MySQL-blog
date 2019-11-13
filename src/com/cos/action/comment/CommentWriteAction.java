package com.cos.action.comment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.model.Comment;
import com.cos.util.Script;
import com.google.gson.Gson;

import dao.CommentDao;

public class CommentWriteAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//null 처리 직접하기
		int userId = Integer.parseInt(request.getParameter("userId"));
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String content = request.getParameter("content");
		
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setBoardId(boardId);
		comment.setContent(content);
		
		CommentDao commentDao = new CommentDao();
		//db comment 테이블에 insert
		int result = commentDao.save(comment);
		
		/**
		 * 만약 save() 하고 findLastComment() 하는 사이에 다른 사람이 save()를 한다면
		 * findLastComment()의 결과는 내가 방금 작성한 내용이 아니라 다른 사람이 작성한 내용이 될 것이다.
		 * 이것을 막기 위해서는 
		 */
		
		if(result==1) {
			//comment 테이블의 가장 마지막 튜플 가져오기
			Comment lastComment = commentDao.findLastComment();
			lastComment.getResponseData().setStatusCode(1);
			lastComment.getResponseData().setStatus("ok");
			lastComment.getResponseData().setStatusMessage("write was completed");
			
			Gson gson = new Gson();
			String lastCommentJson = gson.toJson(lastComment);
			
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(lastCommentJson);
			out.flush();
		}else {
			Script.back(response);
		}
		
	}
}
