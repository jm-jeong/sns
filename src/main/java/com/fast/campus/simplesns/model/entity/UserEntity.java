package com.fast.campus.simplesns.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fast.campus.simplesns.model.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_name", unique = true)
	private String userName;

	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;

	@Column(name = "registered_at")
	private LocalDateTime registeredAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "removed_at")
	private LocalDateTime removedAt;

	@PrePersist
	void registeredAt() {
		this.registeredAt = LocalDateTime.now(); }

	@PreUpdate
	void updatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

	public static UserEntity of(String userName, String password) {
		UserEntity entity = new UserEntity();
		entity.userName = userName;
		entity.password = password;
		return entity;
	}
}
