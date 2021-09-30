package com.clinic.dental.model.user;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.clinic.dental.model.user.repository.UserRepository;

@Component
public class ScheduleTest {

	@Autowired
	private UserRepository userRepo;
	
//	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	@Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
	protected void runPeriodicallyIfNullUpdate() {
		userRepo.updateWhereAgeNull(100);
		System.out.println("Hello mozerfuckers");
	}
}
