package com.clinic.dental.model.appointment.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.user.UserEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{

	@Query("SELECT a FROM AppointmentEntity a WHERE a.id = ?1")
	AppointmentEntity locateById(Long id);

	Collection<AppointmentEntity> findByDentist(String username);

	Collection<AppointmentEntity> findByPatient(UserEntity user);

}
