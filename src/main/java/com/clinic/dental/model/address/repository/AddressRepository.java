package com.clinic.dental.model.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.dental.model.address.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>{

}
