package com.fast.campus.simplesns.controller.response;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
	private Integer id;
	private String comment;
	private Integer userId;
	private String userName;
	private Integer postId;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static CommentResponse fromComment(CommentDto comment) {
		return new CommentResponse(
			comment.getId(),
			comment.getComment(),
			comment.getUserId(),
			comment.getUserName(),
			comment.getPostId(),
			comment.getRegisteredAt(),
			comment.getUpdatedAt(),
			comment.getRemovedAt()
		);
	}
}
