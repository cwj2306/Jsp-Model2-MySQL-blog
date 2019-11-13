package com.cos.action.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.model.Board;
import com.cos.model.User;
import com.cos.util.Script;

import dao.BoardDao;

public class BoardUpdateFormAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id")==null) return;
		
		int boardId = Integer.parseInt(request.getParameter("id"));
		
		BoardDao bDao = new BoardDao();
		Board board = bDao.findById(boardId);
		
		if(board != null) {
			request.setAttribute("board", board);
			RequestDispatcher dis = request.getRequestDispatcher("board/update.jsp");
			dis.forward(request, response);
		}
		
	}
}
