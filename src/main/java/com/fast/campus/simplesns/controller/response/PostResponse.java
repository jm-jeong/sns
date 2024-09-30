package com.fast.campus.simplesns.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fast.campus.simplesns.model.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
	private Integer id;
	private String title;
	private String body;
	private UserResponse userResponse;
	private List<CommentResponse> comments;
	private List<LikeResponse> likes;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	public static PostResponse fromPostDto(PostDto post) {
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getBody(),
			UserResponse.fromUser(post.getUser()),
			post.getComments().stream().map(CommentResponse::fromComment).collect(Collectors.toList()),
			post.getLikes().stream().map(LikeResponse::fromLike).collect(Collectors.toList()),
			post.getRegisteredAt(),
			post.getUpdatedAt()
		);
	}
}
