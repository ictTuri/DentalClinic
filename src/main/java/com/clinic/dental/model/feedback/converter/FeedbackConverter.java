package com.clinic.dental.model.feedback.converter;

import java.time.LocalDateTime;

import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;

public class FeedbackConverter {

	private FeedbackConverter() {}
	
	public static FeedbackEntity toEntity(CreateFeedbackDto dto) {
		return new FeedbackEntity(null,dto.getDescription(),LocalDateTime.now());
	}
	
	public static DisplayFeedbackDto toDto(FeedbackEntity entity) {
		DisplayFeedbackDto dto = new DisplayFeedbackDto();
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		return dto;
	}
	
}
