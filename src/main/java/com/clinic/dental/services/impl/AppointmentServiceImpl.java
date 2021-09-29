package com.clinic.dental.services.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.dto.AppointmentDto;
import com.clinic.dental.services.AppointmentService;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

	@Override
	public List<AppointmentDto> getAllAppointments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppointmentDto getAppointmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public AppointmentDto createAppointment(@Valid AppointmentDto appointmentDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public AppointmentDto updateAppointmentById(@Valid AppointmentDto appointmentDto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Void deleteAppointmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
