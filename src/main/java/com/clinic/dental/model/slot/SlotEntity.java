package com.clinic.dental.model.slot;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.clinic.dental.model.user.UserEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "slot")
public class SlotEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Calendar classStart;
	private Calendar classEnd;
	
	@ManyToOne
	private UserEntity doctors;
}
