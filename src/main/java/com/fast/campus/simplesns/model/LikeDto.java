package com.fast.campus.simplesns.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.entity.LikeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeDto {
	private Integer id;
	private Integer userId;
	private String userName;
	private Integer postId;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static LikeDto fromEntity(LikeEntity entity) {
		return new LikeDto(
			entity.getId(),
			entity.getUser().getId(),
			entity.getUser().getUserName(),
			entity.getPost().getId(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getRemovedAt()
		);
	}
}
