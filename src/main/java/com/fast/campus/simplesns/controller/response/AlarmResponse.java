package com.fast.campus.simplesns.controller.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fast.campus.simplesns.model.AlarmDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmResponse {
	private Integer id;
	private String text;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime removedAt;

	public static AlarmResponse fromAlarm(AlarmDto alarm) {
		return new AlarmResponse(
			alarm.getId(),
			alarm.getAlarmText(),
			alarm.getRegisteredAt(),
			alarm.getUpdatedAt(),
			alarm.getRemovedAt()
		);
	}
}
