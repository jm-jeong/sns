package com.fast.campus.simplesns.controller.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.LikeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {
	private Integer id;
	private Integer userId;
	private String userName;
	private Integer postId;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static LikeResponse fromLike(LikeDto like) {
		return new LikeResponse(
			like.getId(),
			like.getUserId(),
			like.getUserName(),
			like.getPostId(),
			like.getRegisteredAt(),
			like.getUpdatedAt(),
			like.getRemovedAt()
		);
	}
}
