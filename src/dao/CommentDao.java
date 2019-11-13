package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.model.Board;
import com.cos.model.Comment;
import com.cos.util.DBClose;

public class CommentDao {
	private Connection conn; //MySQL과 연결하기 위한 객체
	private PreparedStatement pstmt; //SQL 작성을 위한 객체
	private ResultSet rs; //결과를 보관할 객체
	
	
	// 마지막 comment 가져오기
	public Comment findLastComment() {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id, c.userId, c.boardId, c.content, c.createDate, u.username, u.userProfile ");
		sb.append("from comment c, user u ");
		sb.append("where c.userId = u.id ");
		sb.append("and c.id = (select max(id) from comment)");
		
		conn = DBConn.getConnection();

		final String SQL = sb.toString();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("c.id"));
				comment.setUserId(rs.getInt("c.userId"));
				comment.setBoardId(rs.getInt("boardId"));
				comment.setContent(rs.getString("c.content"));
				comment.setCreateDate(rs.getTimestamp("c.createDate"));
				comment.getUser().setUsername(rs.getString("u.username"));
				comment.getUser().setUserProfile(rs.getString("u.userProfile"));
				
				return comment;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	
	//해당 게시글의 comment 가져오기
	public List<Comment> findByBoardId(int boardId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.id, c.userId, c.boardId, c.content, c.createDate, u.username, u.userProfile ");
		sb.append("FROM comment c, user u ");
		sb.append("WHERE c.userId = u.id and c.boardId = ?");
		
		conn = DBConn.getConnection();
		
		final String SQL = sb.toString();
		
		try {
			List<Comment> comments = new ArrayList<Comment>();
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("c.id"));
				comment.setBoardId(rs.getInt("c.boardId"));
				comment.setUserId(rs.getInt("c.userId"));
				comment.setContent(rs.getString("c.content"));
				comment.setCreateDate(rs.getTimestamp("c.createDate"));
				comment.getUser().setUsername(rs.getString("u.username"));
				comment.getUser().setUserProfile(rs.getString("u.userProfile"));
				
				comments.add(comment);
			}
			
			return comments;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// comment 삭제
	public int delete(int id) {
		conn = DBConn.getConnection();
		
		final String SQL = "DELETE FROM comment WHERE id = ?";
		
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
	
	//comment 쓰기
	public int save(Comment comment) {
		conn = DBConn.getConnection();
		
		final String SQL = "INSERT INTO comment(userId, boardId, content, createDate) VALUES(?, ?, ?, now())";
		
		try {			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment.getUserId());
			pstmt.setInt(2, comment.getBoardId());
			pstmt.setString(3, comment.getContent());
			
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
