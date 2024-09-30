package com.fast.campus.simplesns.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.CommentEntity;
import com.fast.campus.simplesns.model.entity.PostEntity;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
	Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

}
