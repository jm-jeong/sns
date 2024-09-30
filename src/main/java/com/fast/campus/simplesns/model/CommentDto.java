package com.fast.campus.simplesns.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.entity.CommentEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
	private Integer id;
	private String comment;
	private Integer userId;
	private String userName;
	private Integer postId;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static CommentDto fromEntity(CommentEntity entity) {
		return new CommentDto(
			entity.getId(),
			entity.getComment(),
			entity.getUser().getId(),
			entity.getUser().getUserName(),
			entity.getPost().getId(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getRemovedAt()
		);
	}
}
