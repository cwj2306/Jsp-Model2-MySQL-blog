package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.model.Board;
import com.cos.util.DBClose;

public class BoardDao {
	private Connection conn; //MySQL과 연결하기 위한 객체
	private PreparedStatement pstmt; //SQL 작성을 위한 객체
	private ResultSet rs; //결과를 보관할 객체
	
	// 글수정
	public int update(Board board) {
		conn = DBConn.getConnection();
		
		final String SQL = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
			
			int result = pstmt.executeUpdate(); //변경된 튜플의 갯수 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return -1;

	}
	
	//글쓰기
	public int save(Board board) {
		conn = DBConn.getConnection();
		
		final String SQL = "INSERT INTO board(userId, title, content, createDate) VALUES(?, ?, ?, now())";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, board.getUserId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());			
			
			int result = pstmt.executeUpdate(); //변경된 튜플의 갯수 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return -1;
	}
	
	//게시글 전체 갯수
	public int getNumOfBoards(){
		conn = DBConn.getConnection();
		
		final String SQL = "SELECT count(*) FROM board";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return 0;
	}
	
	//검색 게시글 전체 갯수
	public int getNumOfBoards(String search){
		conn = DBConn.getConnection();
		
		final String SQL = "SELECT count(*) FROM board WHERE title like ? or content like ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return 0;
	}
	
	//리스트 페이징
	public List<Board> findAll(int page){
		conn = DBConn.getConnection();
		
		//한 페이지에 글 3개씩 페이징
		final String SQL = "SELECT * FROM board b, user u WHERE b.userId = u.id ORDER BY b.id DESC limit ?, 3";
		
		try {
			List<Board> boards = new ArrayList<Board>();
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, 3*(page-1));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content")+"");
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				
				board.getUser().setUsername(rs.getString("u.username"));
				boards.add(board);
			}
			
			return boards;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	//게시글 검색
	public List<Board> findAll(int page, String search){
		conn = DBConn.getConnection();
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM board b, user u ");
		sb.append("WHERE b.userId = u.id and (b.title like ? or b.content like ?) ");
		sb.append("ORDER BY b.id DESC limit ?, 3");
		final String SQL = sb.toString();
		
		try {
			List<Board> boards = new ArrayList<Board>();
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, 3*(page-1));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content")+"");
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				
				board.getUser().setUsername(rs.getString("u.username"));
				boards.add(board);
			}
			
			return boards;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	
	//상세보기
	public Board findById(int id) {
		conn = DBConn.getConnection();
		
		final String SQL = "SELECT * FROM board b, user u WHERE b.userId = u.id AND b.id = ?";
		
		try {			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content"));
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				
				board.getUser().setUsername(rs.getString("u.username"));
				board.getUser().setUserProfile(rs.getString("u.userProfile"));
				return board;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	//조회수 증가
	public int increaseReadCount(int id) {
		conn = DBConn.getConnection();
		
		final String SQL = "UPDATE board SET readCount = readCount+1 WHERE id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setInt(1, id);			
			
			int result = pstmt.executeUpdate(); //변경된 튜플의 갯수 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return -1;
	}
	
	// 인기리스트 3건 가져오기
	public List<Board> findOrderByReadCountDesc(){
		conn = DBConn.getConnection();
		
		final String SQL = "SELECT * FROM board ORDER BY readCount DESC limit 3";
		
		try {
			List<Board> boards = new ArrayList<Board>();
			
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setUserId(rs.getInt("userId"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setCreateDate(rs.getTimestamp("createDate"));
				
				boards.add(board);
			}
			
			return boards;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	//글 삭제
	public int deleteById(int id) {
		conn = DBConn.getConnection();
		
		final String SQL = "DELETE FROM board WHERE id = ?";
		
		try {			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return -1;
	}
	
	
	
}
