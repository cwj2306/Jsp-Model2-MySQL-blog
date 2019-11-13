package com.cos.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.action.Action;
import com.cos.model.Board;
import com.cos.util.Utils;

import dao.BoardDao;

public class BoardListAction implements Action{
	
	private final static String TAG = "BoardListAction : ";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int page;
		
		if(request.getParameter("page") == null) {
			page=1;
		}else {
			page = Integer.parseInt(request.getParameter("page"));
			if(page<1) {
				page = 1;
			}
		}
		
		BoardDao bDao = new BoardDao();
		List<Board> boards = null;
		List<Board> hotBoards = bDao.findOrderByReadCountDesc();
		
		//search와 목록 분기
		if(request.getParameter("search")==null || request.getParameter("search").equalsIgnoreCase("")) {
			boards = bDao.findAll(page);
			request.setAttribute("search", null);
		}else {
			String search = request.getParameter("search");
			boards = bDao.findAll(page, search);
			request.setAttribute("search", search);
		}
		
		Utils.setPreviewImg(boards);
		Utils.setPreviewContent(boards);//이미지 제거
		Utils.setPreviewImg(hotBoards);
		
		request.setAttribute("boards", boards);
		request.setAttribute("hotBoards", hotBoards);
		
		//=========페이지 번호 (5개씩 표시) 를 위한 변수==============
		int numOfBoards = 0;
		if(request.getParameter("search")==null || request.getParameter("search").equalsIgnoreCase("")) {
			numOfBoards = bDao.getNumOfBoards();//모든 게시글 갯수
		}else {
			numOfBoards = bDao.getNumOfBoards(request.getParameter("search"));//검색한 게시글 갯수
		}
		
		int numOfPages = numOfBoards%3==0?numOfBoards/3:numOfBoards/3+1;
		if(numOfPages==0) {numOfPages = 1;}
		
		int startNum=1;
		int endNum=5;

		if(page<=3) {
			startNum = 1;
			endNum = 5;
		}else if(page>numOfPages-3) {
			startNum = numOfPages-4; 
			endNum = numOfPages;
		}else {
			startNum = page-2; 
			endNum = page+2;
		}
		
		if(numOfPages<5) {
			startNum = 1;
			endNum = numOfPages;				
		}
		
		request.setAttribute("startNum", startNum);
		request.setAttribute("endNum", endNum);
		//==============================================
		
		
		RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
		dis.forward(request, response);
		
	}
}
