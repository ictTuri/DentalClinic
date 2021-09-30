package com.clinic.dental.model.appointment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.clinic.dental.model.appointment.enums.AppointmentType;
import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.slot.SlotEntity;
import com.clinic.dental.model.user.UserEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class AppointmentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id", nullable = false, unique = true)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity patient;
	@Column(nullable = true)
	private String dentist;
	private LocalDateTime date;
	@OneToOne
	@JoinColumn(name = "slot", referencedColumnName = "id", nullable = false)
	private SlotEntity slot;
	@OneToOne
	@JoinColumn(name = "feedback", referencedColumnName = "id", nullable = true)
	private FeedbackEntity feedback;
	
	@Enumerated(EnumType.STRING)
	private AppointmentType type;
	private LocalDateTime createdAt;
}
