package com.fast.campus.simplesns.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.LikeEntity;
import com.fast.campus.simplesns.model.entity.PostEntity;
import com.fast.campus.simplesns.model.entity.UserEntity;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

	Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

	List<LikeEntity> findAllByPost(PostEntity post);
}
