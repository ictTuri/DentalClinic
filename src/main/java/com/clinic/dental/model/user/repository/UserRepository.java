package com.clinic.dental.model.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	@Query(value = "SELECT u FROM UserEntity u WHERE u.id = ?1")
	UserEntity locateById(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET age = ?1 WHERE (age is null) AND (AGE(now(),created_at)) > '00:02:00' ",nativeQuery = true)
	void updateWhereAgeNull(int age);

	@Query(value = "SELECT * FROM users WHERE role = ?1", nativeQuery = true)
	List<UserEntity> getByRole(String value);
}
