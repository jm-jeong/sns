package com.fast.campus.simplesns.model.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import com.fast.campus.simplesns.model.AlarmArgs;
import com.fast.campus.simplesns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "\"alarm\"", indexes = {
	@Index(name = "user_id_idx", columnList = "user_id")
})
@TypeDef(name = "json", typeClass = JsonType.class)
@SQLDelete(sql = "UPDATE alarm SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@Entity
public class AlarmEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Enumerated(EnumType.STRING)
	private AlarmType alarmType;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private AlarmArgs args;

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

	public static AlarmEntity of(AlarmType alarmType, AlarmArgs args, UserEntity user) {
		AlarmEntity entity = new AlarmEntity();
		entity.setAlarmType(alarmType);
		entity.setArgs(args);
		entity.setUser(user);
		return entity;
	}
}
