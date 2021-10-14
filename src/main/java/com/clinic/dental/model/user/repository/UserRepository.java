package com.clinic.dental.model.user.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.enums.Role;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	@Query(value = "SELECT u FROM UserEntity u WHERE u.id = ?1")
	UserEntity locateById(Long id);

	@Query(value = "SELECT * FROM users WHERE role = ?1", nativeQuery = true)
	List<UserEntity> getByRole(String value);

	UserEntity findByNID(String credential);

	UserEntity findByPhone(String credential);

	UserEntity findByEmail(String credential);
	
	UserEntity findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByNID(String nid);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	@Query(value = "SELECT u FROM UserEntity u WHERE u.username = ?1 AND u.role = 'ROLE_DOCTOR'")
	UserEntity findDoctorByUsername(String doctorUsername);

	@Query("SELECT u FROM UserEntity u WHERE u.NID = ?1 AND u.role = ?2")
	UserEntity findByNIDAndRole(@NotBlank String credentials, Role rolePublic);

	@Query("SELECT u FROM UserEntity u WHERE u.phone = ?1 AND u.role = ?2")
	UserEntity findByPhoneClientAndRole(@NotBlank String credentials, Role rolePublic);

	@Query("SELECT u FROM UserEntity u WHERE u.email = ?1 AND u.role = ?2")
	UserEntity findByEmailClientAndRole(@NotBlank String credentials, Role rolePublic);
}
