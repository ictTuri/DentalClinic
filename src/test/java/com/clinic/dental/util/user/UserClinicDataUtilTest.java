package com.clinic.dental.util.user;

import com.clinic.dental.model.user.dto.UserClinicDataDto;

public class UserClinicDataUtilTest {

	public static UserClinicDataDto dataUserOne() {
		UserClinicDataDto dataUser = new UserClinicDataDto();
		dataUser.setAge(12);
		dataUser.setEmail("email@gmail.com");
		dataUser.setFirstName("Test");
		dataUser.setLastName("Test");
		return dataUser;
	}
}
