package com.fast.campus.simplesns.service;

import java.util.List;
import java.util.Objects;

import javax.xml.stream.events.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fast.campus.simplesns.exception.ErrorCode;
import com.fast.campus.simplesns.exception.SimpleSnsApplicationException;
import com.fast.campus.simplesns.model.AlarmArgs;
import com.fast.campus.simplesns.model.AlarmType;
import com.fast.campus.simplesns.model.CommentDto;
import com.fast.campus.simplesns.model.PostDto;
import com.fast.campus.simplesns.model.entity.AlarmEntity;
import com.fast.campus.simplesns.model.entity.CommentEntity;
import com.fast.campus.simplesns.model.entity.LikeEntity;
import com.fast.campus.simplesns.model.entity.PostEntity;
import com.fast.campus.simplesns.model.entity.UserEntity;
import com.fast.campus.simplesns.repository.AlarmEntityRepository;
import com.fast.campus.simplesns.repository.CommentEntityRepository;
import com.fast.campus.simplesns.repository.LikeEntityRepository;
import com.fast.campus.simplesns.repository.PostEntityRepository;
import com.fast.campus.simplesns.repository.UserEntityRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostService {
	private final PostEntityRepository postEntityRepository;
	private final UserEntityRepository userEntityRepository;
	private final CommentEntityRepository commentEntityRepository;
	private final LikeEntityRepository likeEntityRepository;
	private final AlarmEntityRepository alarmEntityRepository;
	private final AlarmService notificationService;

	@Transactional
	public void create(String userName, String title, String body) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
		);
		PostEntity postEntity = PostEntity.of(title, body, userEntity);
		postEntityRepository.save(postEntity);
	}

	public Page<PostDto> list(Pageable pageable) {
		return postEntityRepository.findAll(pageable).map(PostDto::fromEntity);
	}

	public Page<PostDto> my(Integer userId, Pageable pageable) {
		return postEntityRepository.findAllByUserId(userId, pageable).map(PostDto::fromEntity);
	}


	@Transactional
	public PostDto modify(Integer userId, Integer postId, String title, String body) {
		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %s", postId))
		);

		if (!Objects.equals(postEntity.getUser().getId(), userId)) {
			throw new SimpleSnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", userId, postId));
		}

		postEntity.setTitle(title);
		postEntity.setBody(body);
		return PostDto.fromEntity(postEntityRepository.saveAndFlush(postEntity));
	}

	@Transactional
	public void delete(Integer userId, Integer postId) {

		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %s", postId))
		);
		if (!Objects.equals(postEntity.getUser().getId(), userId)) {
			throw new SimpleSnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with post %d", userId, postId));
		}
		likeEntityRepository.deleteAllByPost(postEntity);
		commentEntityRepository.deleteAllByPost(postEntity);
		postEntityRepository.delete(postEntity);
	}

	@Transactional
	public void comment(Integer postId, String userName, String comment) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
		);
		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %s", postId))
		);

		commentEntityRepository.save(CommentEntity.of(comment, postEntity, userEntity));

		notificationService.send(AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(userEntity.getId(), postId), postEntity.getUser());

	}

	public Page<CommentDto> getComments(Integer postId, Pageable pageable) {
		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
		return commentEntityRepository.findAllByPost(postEntity, pageable).map(CommentDto::fromEntity);
	}

	@Transactional
	public void like(Integer postId, String userName) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
		);
		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(
			() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %s", postId))
		);
		likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
			throw new SimpleSnsApplicationException(ErrorCode.ALREADY_LIKED_POST, String.format("userName %s already like the post %s", userName, postId));
		});

		likeEntityRepository.save(LikeEntity.of(postEntity, userEntity));

		// create alarm
		notificationService.send(AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userEntity.getId(), postId), postEntity.getUser());
	}

	public Integer getLikeCount(Integer postId) {
		PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
		List<LikeEntity> likes = likeEntityRepository.findAllByPost(postEntity);
		return likes.size();
	}
}
