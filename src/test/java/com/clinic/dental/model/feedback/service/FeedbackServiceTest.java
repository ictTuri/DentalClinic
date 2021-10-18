package com.clinic.dental.model.feedback.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;
import com.clinic.dental.model.feedback.service.impl.FeedbackServiceImpl;
import com.clinic.dental.util.appointment.AppointmentUtilTest;

@SpringBootTest
@Transactional
class FeedbackServiceTest {
	@InjectMocks
	FeedbackServiceImpl feedbackService;
	@Mock
	AppointmentRepository appointmentRepo;
	@Test
	void givenId_WhenGetFeedback_Validate() {
		Long id = 1L;
		AppointmentEntity app = AppointmentUtilTest.appointmentSix();
		FeedbackEntity feedback = new FeedbackEntity();
		feedback.setCreatedAt(LocalDateTime.now());
		feedback.setId(2L);
		feedback.setDescription("Testing it");
		app.setFeedback(feedback);
		
		when(appointmentRepo.getById(id)).thenReturn(app);
		
		DisplayFeedbackDto dto = feedbackService.getAppointmentFeedback(id);
		
		assertNotNull(dto);
		assertEquals("Testing it", dto.getDescription());
	}
	
	@Test
	void givenId_WhenGetFeedback_ThrowsDataNotFound() {
		Long id = 1L;
		AppointmentEntity app = AppointmentUtilTest.appointmentSix();
		
		when(appointmentRepo.getById(id)).thenReturn(null);
		
		assertThrows(DataIdNotFoundException.class, ()->feedbackService.getAppointmentFeedback(id));
	}
	
	@Test
	void givenId_WhenGetFeedback_ThrowCustomMessage() {
		Long id = 1L;
		AppointmentEntity app = AppointmentUtilTest.appointmentSix();
		
		when(appointmentRepo.getById(id)).thenReturn(app);
		
		assertThrows(CustomMessageException.class, ()->feedbackService.getAppointmentFeedback(id));
	}

}
