package com.clinic.dental.model.appointment.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.converter.AppointmentConverter;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final UserService userService;
	public static final Integer WORK_HOUR_START = 8;
	public static final Integer WORK_HOUR_END = 16;

	@Override
	public List<DisplayAppointmentDto> getAllAppointments() {
		List<DisplayAppointmentDto> appointments = new ArrayList<>();
		appointmentRepo.findAll().stream()
				.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
		return appointments;
	}

	@Override
	public DisplayAppointmentDto getAppointmentById(Long id) {
		DisplayAppointmentDto dto = AppointmentConverter.toDto(appointmentRepo.locateById(id));
		return dto;
	}

	@Override
	@Transactional
	public CreatePublicAppointmentDto createAppointment(@Valid CreatePublicAppointmentDto appointmentDto) {
		return AppointmentConverter.toDisplayDto(appointmentRepo.save(AppointmentConverter.toEntity(appointmentDto)));
	}

	@Override
	@Transactional
	public CreatePublicAppointmentDto updateAppointmentById(@Valid CreatePublicAppointmentDto appointmentDto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Void deleteAppointmentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlotDto> getFreeTimes() {
		List<LocalDateTime> timeList = getSevenDaysForward(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS),LocalDateTime.now().plusWeeks(1L));
		List<AppointmentEntity> appointmentList = appointmentRepo.findAll();
		List<String> doctors = userService.getDoctorsName(Role.DOCTOR.toString());
		List<SlotDto> listAllTimes = new ArrayList<>();
		
		timeList.stream()
				.map(time -> listAllTimes.add(new SlotDto(time.toLocalDate(), time.toLocalTime(), time.plusHours(1).toLocalTime(),doctors)))
				.collect(Collectors.toList());
		
		
		for(AppointmentEntity appointment : appointmentList) {
			listAllTimes.forEach(slot -> {
				if(slot.getDate().equals(appointment.getDate())&&slot.getVisitStart().equals(appointment.getStartTime())) {
					if(!appointment.getDentist().isEmpty()) {
						slot.getDoctors().remove(appointment.getDentist());
					}
					continue;
				}
			});
		}
		

		return listAllTimes;
	}


	public static List<LocalDateTime> getSevenDaysForward(LocalDateTime startDate, LocalDateTime endDate) {
		long numOfDaysBetween = ChronoUnit.HOURS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusHours(i))
				.filter(i -> isWorkingHours(i)).filter(i -> isWorkingDay(i)).collect(Collectors.toList());
	}

	private static boolean isWorkingDay(final LocalDateTime time) {
		return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
	}

	private static boolean isWorkingHours(final LocalDateTime time) {
		int hour = time.getHour();
		return WORK_HOUR_START <= hour && hour <= WORK_HOUR_END;
	}
}
