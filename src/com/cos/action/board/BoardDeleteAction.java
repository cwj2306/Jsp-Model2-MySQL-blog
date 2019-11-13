package com.cos.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.model.User;
import com.cos.util.Script;

import dao.BoardDao;

public class BoardDeleteAction implements Action{
	 @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 if(request.getParameter("id")==null || request.getParameter("id").equals("")) return;
		 
		 int boardId = Integer.parseInt(request.getParameter("id"));
		 
		 BoardDao bDao = new BoardDao();
		 int result = bDao.deleteById(boardId);
		 
		 if(result==1) {
			 response.sendRedirect("/blog/index.jsp");
		 }else {
			 Script.back(response);
		 }
		
	}
}
