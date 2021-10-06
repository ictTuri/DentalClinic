package com.clinic.dental.model.feedback.service;

import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;

public interface FeedbackService {

	DisplayFeedbackDto getAppointmentFeedback(long id);

}
