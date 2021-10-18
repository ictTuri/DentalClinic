package com.clinic.dental.model.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.repository.UserRepository;
import com.clinic.dental.util.user.UserUtilTest;

@SpringBootTest
@Transactional
class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;
	
	@BeforeEach
	void saveUser() {
		UserEntity user = UserUtilTest.general();
		userRepo.save(user);
		UserEntity userOne = UserUtilTest.generalOne();
		userRepo.save(userOne);
		UserEntity userTwo = UserUtilTest.generalTwo();
		userRepo.save(userTwo);
	}
	
	@Test
	void givenUser_WhenRetrieveById_Validate() {
		UserEntity user = userRepo.findByEmail("general@gmail.com");
		UserEntity userRetrieved = userRepo.locateById(user.getId());
		
		assertNotNull(userRetrieved);
		assertEquals("general", userRetrieved.getFirstName());
		assertEquals("G00000000G",userRetrieved.getNID());
	}
	
	@Test
	void givenList_WhenRetrieveByRole_Validate() {
		List<UserEntity> usersRetrieved = userRepo.getByRole("ROLE_ADMIN");
		
		assertNotNull(usersRetrieved);
		assertEquals("admin", usersRetrieved.get(0).getFirstName());
		assertEquals("A00000001A",usersRetrieved.get(0).getNID());
	}
	
	@Test
	void givenNID_WhenRetrieveByNID_Validate() {
		UserEntity user = userRepo.findByNID("G00000000G");
		
		assertNotNull(user);
		assertEquals("general", user.getFirstName());
		assertEquals("G00000000G",user.getNID());
	}
	
	@Test
	void givenPhone_WhenRetrieveByPhoneNumber_Validate() {
		UserEntity user = userRepo.findByPhone("0970000019");
		
		assertNotNull(user);
		assertEquals("generalTwo", user.getFirstName());
		assertEquals("G00000002G",user.getNID());
	}

	@Test
	void givenEmail_WhenRetrieveByEmail_Validate() {
		UserEntity user = userRepo.findByEmail("generalOne@gmail.com");
		
		assertNotNull(user);
		assertEquals("generalOne", user.getFirstName());
		assertEquals("G00000001G",user.getNID());
	}
	
	@Test
	void givenUsername_WhenRetrieveByUsername_Validate() {
		UserEntity user = userRepo.findByUsername("general");
		
		assertNotNull(user);
		assertEquals("general", user.getFirstName());
		assertEquals("G00000000G",user.getNID());
	}
	
	@Test
	void givenWrongUsername_WhenCheckExistence_GetFalse() {
		boolean exists = userRepo.existsByUsername("tralala");
		
		assertFalse(exists);
	}
	
	@Test
	void givenWrongNID_WhenCheckExistence_GetFalse() {
		boolean exists = userRepo.existsByNID("NONIDHERE");
		
		assertFalse(exists);
	}
	
	@Test
	void givenEmail_WhenCheckExistence_GetTrue() {
		boolean exists = userRepo.existsByEmail("general@gmail.com");
		
		assertTrue(exists);
	}
	
	@Test
	void givenPhone_WhenCheckExistence_GetTrue() {
		boolean exists = userRepo.existsByPhone("0970000019");
		
		assertTrue(exists);
	}
	
	@Test
	void givenDoctorUsername_WhenGetDoctor_Validate() {
		UserEntity doctor = userRepo.findDoctorByUsername("general");
		
		assertNotNull(doctor);
		assertEquals("G00000000G", doctor.getNID());
	}
	
	@Test
	void givenNIDAndRole_WhenGetUser_Validate() {
		UserEntity doctor = userRepo.findByNIDAndRole("G00000000G", Role.ROLE_DOCTOR);
		
		assertNotNull(doctor);
		assertEquals("G00000000G", doctor.getNID());
	}
	
	@Test
	void givenPhoneAndRole_WhenGetUser_Validate() {
		UserEntity doctor = userRepo.findByPhoneClientAndRole("0970000010", Role.ROLE_ADMIN);
		
		assertNotNull(doctor);
		assertEquals("G00000001G", doctor.getNID());
	}
	
	@Test
	void givenEmailAndRole_WhenGetUser_Validate() {
		UserEntity doctor = userRepo.findByEmailClientAndRole("generalOne@gmail.com", Role.ROLE_ADMIN);
		
		assertNotNull(doctor);
		assertEquals("G00000001G", doctor.getNID());
	}
}
