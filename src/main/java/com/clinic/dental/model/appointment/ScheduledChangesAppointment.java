package com.clinic.dental.model.appointment;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.appointment.service.AppointmentService;

@Component
public class ScheduledChangesAppointment {

	Logger log =  LogManager.getLogger(ScheduledChangesAppointment.class);
	private static final String DEFAULT_FEEDBACK = "No feedback given for this appointment!";
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
	protected void updateAppointmentStatusAfterTime() {
		appointmentRepo.setStatusDoneAfterTime();
		log.info("Schedule run for status update!");
	}
	
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	protected void updatedAppointmentFeedbackOnNull() {
		appointmentService.setFeedbackAfterEightHoursNull(DEFAULT_FEEDBACK);
		log.info("Schedule run for feedback update!");
	}
}
