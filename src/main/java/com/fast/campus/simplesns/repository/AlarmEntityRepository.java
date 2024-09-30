package com.fast.campus.simplesns.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fast.campus.simplesns.model.entity.AlarmEntity;
import com.fast.campus.simplesns.model.entity.UserEntity;

public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {

	Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}
