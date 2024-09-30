package com.fast.campus.simplesns.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.entity.AlarmEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {
	private Integer id = null;

	private UserDto user;

	private AlarmType alarmType;

	private AlarmArgs args;

	private LocalDateTime registeredAt;

	private LocalDateTime updatedAt;

	private LocalDateTime removedAt;

	public String getAlarmText() {
		return alarmType.getAlarmText();
	}

	public static AlarmDto fromEntity(AlarmEntity entity) {
		return new AlarmDto(
			entity.getId(),
			UserDto.fromEntity(entity.getUser()),
			entity.getAlarmType(),
			entity.getArgs(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getRemovedAt()
		);
	}

}
