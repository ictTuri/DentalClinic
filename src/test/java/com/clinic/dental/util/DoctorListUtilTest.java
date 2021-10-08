package com.clinic.dental.util;

import java.util.ArrayList;
import java.util.List;

public class DoctorListUtilTest {

	public static String[] getDoctors(){
		List<String> list = new ArrayList<>();
		list.add("doctorname");
		String[] returnList = list.toArray(new String[0]);
		return returnList;
	}
}
