package com.clinic.dental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clinic.dental.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	@Query("SELECT u FROM UserEntity u WHERE u.id = ?1")
	UserEntity locateById(Long id);
}
