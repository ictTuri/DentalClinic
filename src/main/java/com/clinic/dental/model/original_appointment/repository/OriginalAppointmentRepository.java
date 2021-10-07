package com.clinic.dental.model.original_appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinic.dental.model.original_appointment.OriginalAppointmentEntity;

public interface OriginalAppointmentRepository extends JpaRepository<OriginalAppointmentEntity, Long>{

}
