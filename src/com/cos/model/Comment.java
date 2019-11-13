package com.cos.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private ResponseData responseData = new ResponseData(); //db와 상관없음
	private int id;
	private int userId;
	private int boardId;
	private String content;
	private Timestamp createDate;
	private User user = new User(); //db와 상관없음	
}
