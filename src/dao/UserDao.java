package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.model.User;
import com.cos.util.DBClose;

public class UserDao {
	// 나중에 할 것 : 싱글톤으로 만들 것

	private Connection conn; // MySQL과 연결하기 위한 객체
	private PreparedStatement pstmt; // SQL 작성을 위한 객체
	private ResultSet rs; // 결과를 보관할 객체

	public int updateUserProfile(int userId, String userProfile) {

		conn = DBConn.getConnection();

		final String SQL = "UPDATE user SET userProfile = ? WHERE id = ?";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userProfile);
			pstmt.setInt(2, userId);

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}
	
	
	public int updateEmailCheck(String username) {

		conn = DBConn.getConnection();

		final String SQL = "UPDATE user SET emailCheck = 1 WHERE username = ?";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, username);

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}
	
	
	// 회원가입
	public int save(User user) {

		conn = DBConn.getConnection();

		final String SQL = "INSERT INTO user(username, password, email, address, createDate) VALUES(?, ?, ?, ?, now())";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());

			int result = pstmt.executeUpdate(); // 변경된 튜플의 갯수 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}

	// 로그인 1:성공, 0:실패, -1:DB오류
	public int findByUsernameAndPassword(String username, String password) {

		conn = DBConn.getConnection();

		final String SQL = "SELECT emailCheck FROM user WHERE username=? and password=?";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {//로그인 성공
				int result = rs.getInt(1);
				
				return result;// 1: 이메일 인증, 0 : 이메일 미인증
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		//아이디 or 비밀번호가 틀림
		return -1;
	}

	// username으로 검색해서 user객체 리턴
	public User findByUsername(String username) {

		conn = DBConn.getConnection();

		final String SQL = "SELECT * FROM user WHERE username=?";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, username);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setUserProfile(rs.getString("userProfile"));

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	public int update(User user) {

		conn = DBConn.getConnection();

		final String SQL = "UPDATE user SET password = ?, address = ? WHERE id = ?";

		try {
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getAddress());
			pstmt.setInt(3, user.getId());

			int result = pstmt.executeUpdate(); // 변경된 튜플의 갯수 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}

}
