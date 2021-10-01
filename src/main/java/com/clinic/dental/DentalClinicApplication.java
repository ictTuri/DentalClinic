package com.clinic.dental;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DentalClinicApplication {
	
	public static final Integer WORK_HOUR_START = 8;
	public static final Integer WORK_HOUR_END = 17;
	
	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApplication.class, args);
		
		List<LocalDateTime> times = getSevenDaysForward(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1),LocalDateTime.now().plusWeeks(1L));
		for (LocalDateTime localDateTime : times) {
			System.out.println("The time is: "+localDateTime);
		}
		
		
	}

	public static List<LocalDateTime> getSevenDaysForward(LocalDateTime startDate, LocalDateTime endDate) {
		long numOfDaysBetween = ChronoUnit.HOURS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1)
				.limit(numOfDaysBetween)
				.mapToObj(i -> startDate.plusHours(i))
				.filter(i -> isWorkingHours(i))
				.filter(i -> isWorkingDay(i))
				.collect(Collectors.toList());
	}

	private static boolean isWorkingDay(final LocalDateTime time) {
		return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
	}

	private static boolean isWorkingHours(final LocalDateTime time) {
		int hour = time.getHour();
		return WORK_HOUR_START <= hour && hour <= WORK_HOUR_END;
	}
}
