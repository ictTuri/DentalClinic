package com.clinic.dental.model.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clinic.dental.model.appointment.AppointmentEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{

	@Query("SELECT a FROM AppointmentEntity a WHERE a.id = ?1")
	AppointmentEntity locateById(Long id);

}
