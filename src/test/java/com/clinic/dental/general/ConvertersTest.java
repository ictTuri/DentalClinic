package com.clinic.dental.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.feedback.converter.FeedbackConverter;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;
import com.clinic.dental.model.original_appointment.OriginalAppointmentEntity;
import com.clinic.dental.model.original_appointment.converter.OriginalAppointmentConverter;
import com.clinic.dental.util.appointment.AppointmentUtilTest;

public class ConvertersTest {

	@Test
	void givenDto_VerifyToEntity_Conversion() {
		AppointmentEntity app = AppointmentUtilTest.appointmentFive();
		
		OriginalAppointmentEntity realApp = OriginalAppointmentConverter.toEntity(app);
		
		assertNotNull(realApp);
	}
	
	@Test
	void givenEntity_VerifyToDto_Feedback() {
		FeedbackEntity feedback = new FeedbackEntity();
		feedback.setId(1L);
		feedback.setCreatedAt(LocalDateTime.now());
		feedback.setDescription("Just a Test Feedback");
		
		DisplayFeedbackDto dto = FeedbackConverter.toDto(feedback);
		
		assertNotNull(dto);
		assertEquals("Just a Test Feedback", dto.getDescription());
	}
	
	@Test
	void givenDto_VerifyEntity_Feedback() {
		CreateFeedbackDto dto = new CreateFeedbackDto();
		dto.setDescription("Another Test Feedback");
		
		FeedbackEntity entity = FeedbackConverter.toEntity(dto);
		
		assertNotNull(entity);
		assertNull(entity.getId());
	}
}
