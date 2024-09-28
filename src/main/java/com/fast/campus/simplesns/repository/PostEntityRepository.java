package com.fast.campus.simplesns.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.PostEntity;
import com.fast.campus.simplesns.model.entity.UserEntity;

public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

	Page<PostEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
}
