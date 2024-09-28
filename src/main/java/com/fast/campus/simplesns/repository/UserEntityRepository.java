package com.fast.campus.simplesns.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findByUserName(String username);
}
