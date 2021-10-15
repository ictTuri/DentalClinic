package com.clinic.dental.model.feedback.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.feedback.converter.FeedbackConverter;
import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;
import com.clinic.dental.model.feedback.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {
	
	Logger log = LogManager.getLogger(FeedbackServiceImpl.class);

	private final AppointmentRepository appointmentRepo;
	
	@Override
	public DisplayFeedbackDto getAppointmentFeedback(long id) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if(appointment != null) {
			if(appointment.getFeedback() != null) {
				log.info("Getting feedback for appointment id: {}",id);
				return FeedbackConverter.toDto(appointment.getFeedback());
			}
			throw new CustomMessageException("Feedback is null for this appointment");
		}
		throw new DataIdNotFoundException("Can not find appointmet with id: "+id);
	}

}
