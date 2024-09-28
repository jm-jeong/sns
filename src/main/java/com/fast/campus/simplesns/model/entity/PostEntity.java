package com.fast.campus.simplesns.model.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@NoArgsConstructor
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "body", columnDefinition = "TEXT")
	private String body;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "registered_at")
	private LocalDateTime registeredAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "removed_at")
	private LocalDateTime removedAt;

	@PrePersist
	void registeredAt() {
		this.registeredAt = LocalDateTime.now();
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

	public static PostEntity of(String title, String body, UserEntity user) {
		PostEntity entity = new PostEntity();
		entity.setTitle(title);
		entity.setBody(body);
		entity.setUser(user);
		return entity;
	}
}
