package com.clinic.dental.model.appointment.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.user.UserEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{

	@Query("SELECT a FROM AppointmentEntity a WHERE a.id = ?1")
	AppointmentEntity locateById(Long id);

	Collection<AppointmentEntity> findByDentist(String username);

	Collection<AppointmentEntity> findByPatient(UserEntity user);

	AppointmentEntity findByIdAndStatus(Long id, Status appendingUser);

	@Query(value = "SELECT * FROM appointments WHERE appointment_id = ?1 status NOT IN ('DONE','ACTIVE','USER_CANCELLED','DOCTOR_CANCELLED') AND (AGE(now(),date)) < '1' ", nativeQuery = true)
	AppointmentEntity findAppointmentToCancel(Long id);

	@Query(value = "SELECT * FROM appointments WHERE status NOT IN ('REFUZED','DOCTOR_CANCELLED','USER_CANCELLED') AND (AGE(date(now()),date)) <= '0'", nativeQuery = true)
	Collection<AppointmentEntity> findAllAfterToday();

	@Transactional
	@Modifying
	@Query(value = "UPDATE appointments SET status = 'IN_PROGRESS' WHERE status in ('ACTIVE') AND (AGE(date(now()),date)) >= '0'", nativeQuery = true)
	void setStatusDoneAfterTime();

	@Query(value = "SELECT * FROM appointments WHERE status = 'DONE' AND feedback IS NULL AND (AGE(now(),last_updated_at)) > '08:00:00'", nativeQuery = true)
	Collection<AppointmentEntity> getAppoinmentForUpdateFeedback();

	@Query("SELECT app FROM AppointmentEntity app WHERE app.dentist = ?1 AND app.status = 'ACTIVE' OR app.status = 'IN_PROGRESS' ")
	AppointmentEntity getActiveAppointmentForFeedback(String username);

}
