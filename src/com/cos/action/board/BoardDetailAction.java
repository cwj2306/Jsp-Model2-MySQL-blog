package com.cos.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.model.Board;
import com.cos.model.Comment;
import com.cos.util.Script;
import com.cos.util.Utils;

import dao.BoardDao;
import dao.CommentDao;

public class BoardDetailAction implements Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id") == null || request.getParameter("id").equals("")) return;
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		BoardDao dao = new BoardDao();
		Board board = dao.findById(id);
		
		CommentDao commentDao = new CommentDao();
		List<Comment> comments = commentDao.findByBoardId(id);
		
		if(board != null) {
			//============== 조회수 무한 증가 방지===============
			boolean readFirst = true;
			
			//request의 쿠기 전부 조회
			Cookie[] cookies = request.getCookies();
			for (Cookie c : cookies) {
				if( c.getName().equals("myCookie"+id) ) {
					readFirst = false;
					break;
				}
			}
			
			int result = 1;
			//없으면 response에 쿠키 담기
			if(readFirst) {
				Cookie cookie = new Cookie("myCookie"+id, id+"");
				cookie.setMaxAge(86400);//sec
				response.addCookie(cookie);
				
				result = dao.increaseReadCount(id);
			}
			//=======================================
			
			if(result==1) {
				//유투브 주소 파싱
				Utils.setPreviewYoutube(board);
				
				// board를 request에 담고 dispatcher 이동
				request.setAttribute("board", board);
				request.setAttribute("comments", comments);
				
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}else {
				Script.back(response);
			}

		}else {
			Script.back(response);
		}
		
	}
}
