package com.fast.campus.simplesns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.CommentEntity;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
}
