package com.fast.campus.simplesns.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fast.campus.simplesns.controller.request.PostWriteRequest;
import com.fast.campus.simplesns.controller.response.PostResponse;
import com.fast.campus.simplesns.controller.response.Response;
import com.fast.campus.simplesns.service.PostService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public Response<Void> create(@RequestBody PostWriteRequest request, Authentication authentication) {
		postService.create(authentication.getName(), request.getTitle(), request.getBody());
		return Response.success();
	}

	@GetMapping
	public Response<Page<PostResponse>> list(Pageable pageable, Authentication authentication) {
		return Response.success(postService.list(pageable).map(PostResponse::fromPostDto));
	}

	@GetMapping("/my")
	public Response<Page<PostResponse>> myPosts(Pageable pageable, Authentication authentication) {
		return Response.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPostDto))
	}

	@PutMapping("/{postId}")
	public Response<PostResponse> modify(@PathVariable Integer postId, @RequestBody PostWriteRequest request,
		Authentication authentication) {
		return Response.success(
			PostResponse.fromPostDto(postService.modify(authentication.getName(), postId, request.getTitle(),
				request.getBody())));
	}

	@DeleteMapping("/{postId}")
	public Response<Void> delete(@PathVariable Integer postId, Authentication authentication) {
		postService.delete(authentication.getName(), postId);
		return Response.success();
	}
}
