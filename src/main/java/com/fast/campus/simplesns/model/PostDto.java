package com.fast.campus.simplesns.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fast.campus.simplesns.model.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {
	private Integer id = null;

	private String title;

	private String body;

	private UserDto user;

	private LocalDateTime registeredAt;

	private LocalDateTime updatedAt;

	private LocalDateTime removedAt;

	public static PostDto fromEntity(PostEntity entity) {
		return new PostDto(
			entity.getId(),
			entity.getTitle(),
			entity.getBody(),
			UserDto.fromEntity(entity.getUser()),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getRemovedAt()
		);
	}
}
