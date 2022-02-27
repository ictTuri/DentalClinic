package com.clinic.dental.model.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.dental.model.clinic.ClinicEntity;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, Long>{

}
