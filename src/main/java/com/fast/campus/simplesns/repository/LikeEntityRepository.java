package com.fast.campus.simplesns.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fast.campus.simplesns.model.entity.LikeEntity;
import com.fast.campus.simplesns.model.entity.PostEntity;
import com.fast.campus.simplesns.model.entity.UserEntity;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

	Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

	@Query(value = "SELECT COUNT(*) from LikeEntity entity WHERE entity.post = :post")
	Integer countByPost(@Param("post") PostEntity post);

	List<LikeEntity> findAllByPost(PostEntity post);
}
