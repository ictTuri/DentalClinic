package com.clinic.dental.model.appointment.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.converter.AppointmentConverter;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.feedback.converter.FeedbackConverter;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.feedback.repository.FeedbackRepository;
import com.clinic.dental.model.original_appointment.OriginalAppointmentEntity;
import com.clinic.dental.model.original_appointment.converter.OriginalAppointmentConverter;
import com.clinic.dental.model.original_appointment.repository.OriginalAppointmentRepository;
import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final OriginalAppointmentRepository originalAppointmentRepo;
	private final UserService userService;
	private final FeedbackRepository feedbackRepository;

	public static final Integer WORK_HOUR_START = 8;
	public static final Integer WORK_HOUR_END = 16;

	public List<AppointmentEntity> getAllAppointments() {
		List<AppointmentEntity> appointments = new ArrayList<>();
		appointmentRepo.findAll().stream()
				.forEach(appointment -> appointments.add(appointment));
		return appointments;
	}

	@Override
	public DisplayAppointmentDto getAppointmentById(Long id, UserEntity authenticated) {
		AppointmentEntity appointment = appointmentRepo.locateById(id);
		if (appointment != null) {
			if (authenticated.getRole().equals(Role.ROLE_PUBLIC) && appointment.getPatient().equals(authenticated)) {
				return AppointmentConverter.toDto(appointment);
			} else if (authenticated.getRole().equals(Role.ROLE_DOCTOR) && appointment.getDentist().equals(authenticated.getUsername())) {
				return AppointmentConverter.toDto(appointment);
			} else {
				return AppointmentConverter.toDto(appointment);
			}
		}
		throw new DataIdNotFoundException("Can not find appointment with id: " + id);
	}

	@Override
	public List<SlotDto> getFreeTimes() {
		List<LocalDateTime> timeList = getSevenDaysForward(
				LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1), LocalDateTime.now().plusWeeks(1L));

		List<AppointmentEntity> appointmentList = appointmentRepo.findAllAfterToday().stream()
				.filter(app -> !app.getStatus().equals(Status.REFUZED)).toList();

		final String[] doctorsList = userService.getDoctorsName(Role.ROLE_DOCTOR.toString());

		List<SlotDto> listByAppoinment = new ArrayList<>();
		appointmentList.stream().map(app -> listByAppoinment.add(
				new SlotDto(app.getDate(), app.getStartTime(), app.getEndTime(), new String[] { app.getDentist() })))
				.collect(Collectors.toList());
		List<SlotDto> listOfSlots = new ArrayList<>();
		timeList.stream().map(time -> listOfSlots
				.add(new SlotDto(time.toLocalDate(), time.toLocalTime(), time.plusHours(1).toLocalTime(), doctorsList)))
				.collect(Collectors.toList());
		for (SlotDto app : listByAppoinment) {
			listOfSlots.stream().filter(
					slot -> slot.getDate().equals(app.getDate()) && slot.getVisitStart().equals(app.getVisitStart()))
					.map(slot -> {
						String[] str_array = slot.getDoctors();
						List<String> list = new ArrayList<String>(Arrays.asList(str_array));
						list.remove(app.getDoctors()[0]);
						str_array = list.toArray(new String[0]);
						slot.setDoctors(str_array);
						return null;
					}).collect(Collectors.toList());
		}
		listOfSlots.stream().filter(slot -> slot.getDoctors() != null).findFirst()
				.map(slot -> listOfSlots.remove(slot));

		return listOfSlots;
	}

	private static List<LocalDateTime> getSevenDaysForward(LocalDateTime startDate, LocalDateTime endDate) {
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

	@Override
	@Transactional
	public DisplayAppointmentDto rezerveAppointment(@Valid CreatePublicAppointmentDto rezerveDto, UserEntity authenticated) {
		List<SlotDto> freeSlots = getFreeTimes();
		boolean hasFreeSlot = hasSlot(freeSlots, rezerveDto.getDate(), rezerveDto.getStartTime());
		if (rezerveDto.getDentist() == null) {
			if (hasFreeSlot) {
				AppointmentEntity appointment = AppointmentConverter.rezervationToEntity(rezerveDto, authenticated,
						"No Doctor Selected");
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Please pick another time!");
		} else {
			UserEntity doctorSelected = userService.getDoctorByUsername(rezerveDto.getDentist());

			if (hasFreeSlot) {
				AppointmentEntity appointment = AppointmentConverter.rezervationToEntity(rezerveDto, authenticated,
						doctorSelected.getUsername());
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Please pick another time!");
		}
	}

	private boolean hasSlot(List<SlotDto> slots, LocalDate day, LocalTime startTime) {
		return slots.stream().anyMatch(slot -> slot.getVisitStart().equals(startTime.truncatedTo(ChronoUnit.HOURS))
				&& slot.getDate().equals(day));
	}

	@Override
	@Transactional
	public DisplayAppointmentDto approveAppointmentById(Long id) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if (appointment != null) {
			if (appointment.getStatus().equals(Status.APPENDING)) {
				appointment.setStatus(Status.ACTIVE);
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Appointment is not on PENDING status!");
		}
		throw new DataIdNotFoundException("Can not locate rezervation with id: " + id);
	}

	@Override
	@Transactional
	public DisplayAppointmentDto changeAppointmentTimeById(Long id, @Valid ChangeAppointmentTimeDto dto) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if (appointment != null) {
			boolean hasFreeSlot = hasSlot(getFreeTimes(), dto.getDay(), dto.getStartTime());
			if (hasFreeSlot) {
				OriginalAppointmentEntity originalDate = OriginalAppointmentConverter.toEntity(appointment);
				appointment.setOriginalDate(originalAppointmentRepo.save(originalDate));
				appointment.setStatus(Status.APPENDING_USER);
				appointment.setDate(dto.getDay());
				appointment.setStartTime(dto.getStartTime().truncatedTo(ChronoUnit.HOURS));
				appointment.setEndTime(dto.getStartTime().plusHours(1).truncatedTo(ChronoUnit.HOURS));
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Please pick another time!");
		}
		throw new DataIdNotFoundException("Can not find appointment with given id: " + id);
	}

	@Override
	public DisplayAppointmentDto changeAppointmentDentist(Long id, @Valid ChangeAppointmentDentistDto dto) {
		AppointmentEntity appointment = appointmentRepo.locateById(id);
		if (appointment != null && appointment.getStatus().equals(Status.ACTIVE)) {
			UserEntity dentist = userService.getDoctorByUsername(dto.getDentist().trim().toLowerCase());
			if (dentist != null) {
				boolean isDoctorFree = dentistIsFree(getFreeTimes(), appointment.getDate(), appointment.getStartTime(),
						dentist.getUsername());
				if (isDoctorFree) {
					appointment.setDentist(dentist.getUsername());
					appointment.setLastUpdatedAt(LocalDateTime.now());
					appointment.setLastUpdatedBy(userService.getAuthenticatedUser().getUsername());
					return AppointmentConverter.toDto(appointmentRepo.save(appointment));
				}
				throw new CustomMessageException("Dentist: " + dto.getDentist() + " is not free at this time");
			}
			throw new DataIdNotFoundException("Can not find dentist with given username: " + dto.getDentist());
		}
		throw new DataIdNotFoundException(
				"Can not find appointment with given id: " + id + " or status did not match to ACTIVE.");
	}

	private boolean dentistIsFree(List<SlotDto> slots, LocalDate day, LocalTime startTime, String username) {
		return slots.stream().anyMatch(slot -> slot.getVisitStart().equals(startTime.truncatedTo(ChronoUnit.HOURS))
				&& slot.getDate().equals(day) && slot.getDoctors().toString().contains(username));
	}

	@Override
	@Transactional
	public DisplayAppointmentDto closeAppointmentById(Long id) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if (appointment != null) {
			if (appointment.getDate().equals(LocalDate.now()) || appointment.getDate().isBefore(LocalDate.now())) {
				if (appointment.getEndTime().isBefore(LocalTime.now()) && appointment.getFeedback() != null) {
					appointment.setStatus(Status.DONE);
					return AppointmentConverter.toDto(appointmentRepo.save(appointment));
				}
				throw new CustomMessageException("Appointment not finished or feedback not given yet!");
			}
			throw new CustomMessageException("Appointment day is not reached yet!");
		}
		throw new DataIdNotFoundException("Can not find appointment with id: " + id);
	}

	@Override
	public List<DisplayAppointmentDto> getMyAllAppointments(String status, UserEntity authentication) {
		if (authentication.getRole().equals(Role.ROLE_DOCTOR)) {
			List<DisplayAppointmentDto> appointments = new ArrayList<>();
			if (isValidStatus(status)) {
				appointmentRepo.findByDentist(authentication.getUsername()).stream()
						.filter(appointment -> appointment.getStatus().equals(Status.valueOf(status.toUpperCase())))
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			} else {
				appointmentRepo.findByDentist(authentication.getUsername()).stream()
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			}

		} else if (authentication.getRole().equals(Role.ROLE_PUBLIC)) {
			if (status != null && !status.isEmpty()) {
				List<DisplayAppointmentDto> appointments = new ArrayList<>();
				appointmentRepo.findByPatient(authentication).stream()
						.filter(appointment -> appointment.getStatus().equals(Status.valueOf(status.toUpperCase())))
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			} else {
				List<DisplayAppointmentDto> appointments = new ArrayList<>();
				appointmentRepo.findByPatient(authentication).stream()
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			}

		} else {
			if (status != null && !status.isEmpty()) {
				List<DisplayAppointmentDto> appointments = new ArrayList<>();
				getAllAppointments().stream()
						.filter(appointment -> appointment.getStatus().equals(Status.valueOf(status.toUpperCase())))
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			} else {
				List<DisplayAppointmentDto> appointments = new ArrayList<>();
				getAllAppointments().stream()
						.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
				return appointments;
			}
		}
	}

	private boolean isValidStatus(String status) {
		if (status != null && !status.isBlank()) {
			if (status.strip().toUpperCase().equals(Status.ACTIVE.name())
					|| status.strip().toUpperCase().equals(Status.REFUZED.name())
					|| status.strip().toUpperCase().equals(Status.DONE.name())
					|| status.strip().toUpperCase().equals(Status.DOCTOR_CANCELLED.name())
					|| status.strip().toUpperCase().equals(Status.USER_CANCELLED.name())
					|| status.strip().toUpperCase().equals(Status.USER_REFUZED_TIME.name())
					|| status.strip().toUpperCase().equals(Status.IN_PROGRESS.name())) {
				return true;
			}
			throw new CustomMessageException(
					"Please enter a valid status such as: 'ACTIVE','REFUZED','DONE','DOCTOR_CANCELLED','USER_CANCELLED','IN_PROGRESS','USER_REFUZED_TIME'!");
		}
		return false;
	}

	@Override
	@Transactional
	public DisplayAppointmentDto approveNewTimeAppointment(Long id) {
		UserEntity user = userService.getAuthenticatedUser();
		AppointmentEntity appointment = appointmentRepo.findByIdAndStatus(id, Status.APPENDING_USER);
		if (appointment.getPatient().equals(user)) {
			appointment.setStatus(Status.ACTIVE);
			return AppointmentConverter.toDto(appointmentRepo.save(appointment));
		}
		throw new DataIdNotFoundException("Cannot locate your appointment by id:" + id);
	}

	@Override
	@Transactional
	public DisplayAppointmentDto refuzeNewTimeAppointment(Long id) {
		UserEntity user = userService.getAuthenticatedUser();
		AppointmentEntity appointment = appointmentRepo.findByIdAndStatus(id, Status.APPENDING_USER);
		if (appointment != null) {
			if (appointment.getPatient().equals(user)) {
				OriginalAppointmentEntity originalDate = appointment.getOriginalDate();
				appointment.setStatus(Status.USER_REFUZED_TIME);
				appointment.setDate(originalDate.getDay());
				appointment.setStartTime(originalDate.getStartTime());
				appointment.setEndTime(originalDate.getEndTime());
				appointment.setLastUpdatedAt(LocalDateTime.now());
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new DataIdNotFoundException("Cannot locate your appointment by id:" + id);
		}
		throw new DataIdNotFoundException("Can not find with given id: " + id + ". please verify dates");
	}

	@Override
	@Transactional
	public DisplayAppointmentDto cancelAppointment(Long id) {
		UserEntity user = userService.getAuthenticatedUser();
		AppointmentEntity appointment = appointmentRepo.findAppointmentToCancel(id);
		if (appointment != null) {
			if (appointment.getPatient().equals(user)) {
				appointment.setLastUpdatedBy(user.getUsername());
				appointment.setStatus(Status.USER_CANCELLED);
				appointment.setLastUpdatedAt(LocalDateTime.now());
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			} else if (appointment.getDentist().equals(user.getUsername())) {
				appointment.setLastUpdatedBy(user.getUsername());
				appointment.setStatus(Status.DOCTOR_CANCELLED);
				appointment.setLastUpdatedAt(LocalDateTime.now());
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Selected Appointment not your appointment!");
		}
		throw new DataIdNotFoundException("Can not find with given id: " + id + ".  Can not Cancel!");
	}

	@Override
	@Transactional
	public void setFeedbackAfterEightHoursNull(String defaultFeedback) {
		appointmentRepo.getAppoinmentForUpdateFeedback().stream().map(appointment -> {
			appointment.setFeedback(new FeedbackEntity(null, defaultFeedback, LocalDateTime.now()));
			appointmentRepo.save(appointment);
			return null;
		});
	}

	@Override
	@Transactional
	public DisplayAppointmentDto setAppointmentFeedback(Long id, @Valid CreateFeedbackDto dto, UserEntity thisUser) {
		AppointmentEntity appointment = appointmentRepo.getActiveAppointmentForFeedback(thisUser.getUsername());
		if(appointment != null) {
			FeedbackEntity feedback = FeedbackConverter.toEntity(dto);
			appointment.setFeedback(feedbackRepository.save(feedback));
			return AppointmentConverter.toDto(appointmentRepo.save(appointment));
		}
		throw new DataIdNotFoundException("Can not find and active or in progress appointment with id: "+id);
	}
}
