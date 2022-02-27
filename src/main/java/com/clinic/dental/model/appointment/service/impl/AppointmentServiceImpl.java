package com.clinic.dental.model.appointment.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.EnumUtils;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.converter.AppointmentConverter;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.dto.TimeSlotDto;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.appointment.service.AppointmentService;
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
	
	Logger log = LogManager.getLogger(AppointmentServiceImpl.class);

	private final AppointmentRepository appointmentRepo;
	private final OriginalAppointmentRepository originalAppointmentRepo;
	private final UserService userService;
	private final FeedbackRepository feedbackRepository;

	public static final Integer WORK_HOUR_START = 8;
	public static final Integer WORK_HOUR_END = 16;

	public List<AppointmentEntity> getAllAppointments() {
		List<AppointmentEntity> appointments = new ArrayList<>();
		appointmentRepo.findAll().stream().forEach(appointment -> appointments.add(appointment));
		log.info("Getting all Appointments!");
		return appointments;
	}

	@Override
	public DisplayAppointmentDto getAppointmentById(Long id, UserEntity authenticated) {
		AppointmentEntity appointment = appointmentRepo.locateById(id);
		if (appointment != null) {
			if (authenticated.getRole().equals(Role.ROLE_PUBLIC) && appointment.getPatient().equals(authenticated)) {
				log.info("Getting appointment by id: {} for public user!",id);
				return AppointmentConverter.toDto(appointment);
			}
			if (authenticated.getRole().equals(Role.ROLE_DOCTOR)
					&& appointment.getDentist().equals(authenticated.getUsername())) {
				log.info("Getting appointment by id: {} for doctor user!",id);
				return AppointmentConverter.toDto(appointment);
			}
			if(authenticated.getRole().equals(Role.ROLE_ADMIN) || authenticated.getRole().equals(Role.ROLE_SECRETARY)){
				log.info("Getting appointment by id: {} for admin and secretary user!",id);
				return AppointmentConverter.toDto(appointment);
			}
			throw new DataIdNotFoundException("Can not find appointment with id: " + id);
		}
		throw new DataIdNotFoundException("Can not find appointment with id: " + id);
	}

	@Override
	public List<SlotDto> getFreeTimes() {
		List<LocalDateTime> timeList = getSevenDaysForward(
				LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1), LocalDateTime.now().plusWeeks(1L));

		List<AppointmentEntity> appointmentList = appointmentRepo.findAllAfterToday().stream()
				.filter(app -> !app.getStatus().equals(Status.REFUZED)).collect(Collectors.toList());

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
		log.info("Getting Free Slots for 7 days!");
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
	public DisplayAppointmentDto rezerveAppointment(@Valid CreatePublicAppointmentDto rezerveDto,
			UserEntity authenticated) {
		Optional<AppointmentEntity> existAppointment = appointmentRepo.existScheduleByUser(authenticated,
				rezerveDto.getDate(), rezerveDto.getStartTime().truncatedTo(ChronoUnit.HOURS));
		if (existAppointment.isPresent()) {
			throw new CustomMessageException("You already hava an appointment at this time");
		}

		List<SlotDto> freeSlots = getFreeTimes();

		if (rezerveDto.getDentist() == null) {
			boolean hasFreeSlotNoDoc = hasFreeSlotNoDentist(freeSlots, rezerveDto.getDate(), rezerveDto.getStartTime());
			if (hasFreeSlotNoDoc) {
				AppointmentEntity appointment = AppointmentConverter.rezervationToEntity(rezerveDto, authenticated,
						"No Doctor Selected");
				log.info("Rezerving new Appointment with no doctor with date:{} and start time: {}!",rezerveDto.getDate(), rezerveDto.getStartTime().truncatedTo(ChronoUnit.HOURS));
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Please pick another time!");
		} else {
			UserEntity doctorSelected = userService.getDoctorByUsername(rezerveDto.getDentist());
			boolean hasFreeSlot = hasSlot(freeSlots, rezerveDto.getDate(), rezerveDto.getStartTime(),
					rezerveDto.getDentist());
			if (hasFreeSlot) {
				AppointmentEntity appointment = AppointmentConverter.rezervationToEntity(rezerveDto, authenticated,
						doctorSelected.getUsername());
				AppointmentEntity savedAppointment = appointmentRepo.save(appointment);
				log.info("Rezerving new Appointment with date:{} and start time: {}!",rezerveDto.getDate(), rezerveDto.getStartTime().truncatedTo(ChronoUnit.HOURS));
				return AppointmentConverter.toDto(savedAppointment);
			}
			throw new CustomMessageException("Please pick another time!");
		}
	}

	private boolean hasFreeSlotNoDentist(List<SlotDto> freeSlots, LocalDate date, LocalTime startTime) {
		return freeSlots.stream().anyMatch(slot -> slot.getVisitStart().equals(startTime.truncatedTo(ChronoUnit.HOURS))
				&& slot.getDate().equals(date));
	}

	private boolean hasSlot(List<SlotDto> slots, LocalDate day, LocalTime startTime, String dentist) {
		Optional<SlotDto> slotDto = slots.stream()
				.filter(slot -> slot.getVisitStart().equals(startTime.truncatedTo(ChronoUnit.HOURS))
						&& slot.getDate().equals(day))
				.findAny();

		if (!slotDto.isPresent()) {
			return false;
		}
		
		if(slotDto.get().getDoctors() == null) {
			return false;
		}
		
		for (int i = 0; i < slotDto.get().getDoctors().length; i++) {
			if (slotDto.get().getDoctors()[i].contains(dentist)) {
				return true;
			}
		}

		System.out.println(slotDto);

		return false;
	}

	@Override
	@Transactional
	public DisplayAppointmentDto approveAppointmentById(Long id) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if (appointment != null) {
			if (appointment.getStatus().equals(Status.APPENDING)) {
				appointment.setStatus(Status.ACTIVE);
				log.info("Approving appointment by id:{}",id);
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
			if (appointment.getOriginalDate() == null) {
				boolean hasFreeSlot = hasSlot(getFreeTimes(), dto.getDay(), dto.getStartTime(),
						appointment.getDentist());
				if (hasFreeSlot) {
					OriginalAppointmentEntity originalDate = OriginalAppointmentConverter.toEntity(appointment);
					appointment.setOriginalDate(originalAppointmentRepo.save(originalDate));
					appointment.setStatus(Status.APPENDING_USER);
					appointment.setDate(dto.getDay());
					appointment.setStartTime(dto.getStartTime().truncatedTo(ChronoUnit.HOURS));
					appointment.setEndTime(dto.getStartTime().plusHours(1).truncatedTo(ChronoUnit.HOURS));
					log.info("Changing appointment time by id:{}",id);
					return AppointmentConverter.toDto(appointmentRepo.save(appointment));
				}
				throw new CustomMessageException("Please pick another time!");
			}
			throw new CustomMessageException("Already suggested another time");
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
					log.info("Changing appointment dentist by id:{}",id);
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
		return slots.stream().anyMatch(slot -> {
			if(slot.getVisitStart().equals(startTime.truncatedTo(ChronoUnit.HOURS))&&slot.getDate().equals(day)) {
				List<String> doctors = Arrays.asList(slot.getDoctors());
				if(doctors.contains(username)) {
					return true;
				}
				return false;
			}
			return false;
		});
	}

	@Override
	@Transactional
	public DisplayAppointmentDto closeAppointmentById(Long id) {
		AppointmentEntity appointment = appointmentRepo.getById(id);
		if (appointment != null) {
			if (appointment.getDate().equals(LocalDate.now()) || appointment.getDate().isBefore(LocalDate.now())) {
				if (appointment.getEndTime().isBefore(LocalTime.now()) && appointment.getFeedback() != null) {
					appointment.setStatus(Status.DONE);
					log.info("Closing appointment by id:{}",id);
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
			return doctorUserAppointments(status, authentication);

		} else if (authentication.getRole().equals(Role.ROLE_PUBLIC)) {
			return publicUserAppointments(status, authentication);

		} else {
			return adminSecretaryUserAppointments(status);
		}
	}

	private List<DisplayAppointmentDto> adminSecretaryUserAppointments(String status) {
		if (status != null && !(status.length()==0)) {
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

	private List<DisplayAppointmentDto> doctorUserAppointments(String status, UserEntity authentication) {
		List<DisplayAppointmentDto> appointments = new ArrayList<>();
		if (isValidStatus(status)) {
			appointmentRepo.findByDentist(authentication.getUsername()).stream()
					.filter(appointment -> appointment.getStatus().equals(Status.valueOf(status.toUpperCase())))
					.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
			log.info("Returning {} doctor appointments",authentication.getUsername());
			return appointments;
		} else {
			appointmentRepo.findByDentist(authentication.getUsername()).stream()
					.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
			return appointments;
		}
	}

	private List<DisplayAppointmentDto> publicUserAppointments(String status, UserEntity authentication) {
		if (status != null && !(status.length() == 0)) {
			List<DisplayAppointmentDto> appointments = new ArrayList<>();
			appointmentRepo.findByPatient(authentication).stream()
					.filter(appointment -> appointment.getStatus().equals(Status.valueOf(status.toUpperCase())))
					.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
			log.info("Returning {} public user appointments",authentication.getUsername());
			return appointments;
		} else {
			List<DisplayAppointmentDto> appointments = new ArrayList<>();
			appointmentRepo.findByPatient(authentication).stream()
					.forEach(appointment -> appointments.add(AppointmentConverter.toDto(appointment)));
			log.info("Returning {} admin or secretary user appointments",authentication.getUsername());
			return appointments;
		}
	}

	private boolean isValidStatus(String status) {
		if (status != null && !(status.length() == 0)) {
			if (EnumUtils.findEnumInsensitiveCase(Status.class, status) != null) {
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
			log.info("Approving appointment new time: {}",appointment.getStartTime());
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
				log.info("Refuzing appointment new time: {}",appointment.getStartTime());
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new DataIdNotFoundException("Cannot locate your appointment by id:" + id);
		}
		throw new DataIdNotFoundException("Can not find with given id: " + id + ". please verify dates");
	}

	@Override
	@Transactional
	public DisplayAppointmentDto cancelAppointment(Long id, UserEntity user) {
		AppointmentEntity appointment = appointmentRepo.findAppointmentToCancel(id);
		if (appointment != null) {
			if (appointment.getPatient().equals(user)) {
				appointment.setLastUpdatedBy(user.getUsername());
				appointment.setStatus(Status.USER_CANCELLED);
				appointment.setLastUpdatedAt(LocalDateTime.now());
				log.info("User {} cancelling appointment with id: {}",user.getUsername(),id);
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			} else if (appointment.getDentist().equals(user.getUsername())) {
				appointment.setLastUpdatedBy(user.getUsername());
				appointment.setStatus(Status.DOCTOR_CANCELLED);
				appointment.setLastUpdatedAt(LocalDateTime.now());
				log.info("Doctor {} cancelling appointment with id: {}",user.getUsername(),id);
				return AppointmentConverter.toDto(appointmentRepo.save(appointment));
			}
			throw new CustomMessageException("Selected Appointment not your appointment!");
		}
		throw new DataIdNotFoundException("Can not find with given id: " + id + ".  Can not Cancel!");
	}

	@Override
	@Transactional
	public void setFeedbackAfterEightHoursNull(String defaultFeedback) {
		FeedbackEntity defaultFeedbackEntity = feedbackRepository.getById(1L);
		List<AppointmentEntity> appointments = appointmentRepo.getAppoinmentForUpdateFeedback().stream().collect(Collectors.toList());

		if (!(appointments.size() == 0)) {
			appointments.forEach(app -> {
				app.setFeedback(defaultFeedbackEntity);
				appointmentRepo.save(app);
			});
		} 
	}

	@Override
	@Transactional
	public DisplayAppointmentDto setAppointmentFeedback(Long id, @Valid CreateFeedbackDto dto, UserEntity thisUser) {
		AppointmentEntity appointment = appointmentRepo.getActiveAppointmentForFeedback(thisUser.getUsername());
		if (appointment != null) {
			FeedbackEntity feedback = FeedbackConverter.toEntity(dto);
			appointment.setFeedback(feedbackRepository.save(feedback));
			log.info("Doctor {} set appointment id: {} with feedback: {}",thisUser.getUsername(),id,feedback.getDescription());
			return AppointmentConverter.toDto(appointmentRepo.save(appointment));
		}
		throw new DataIdNotFoundException("Can not find and active or in progress appointment with id: " + id);
	}

	@Override
	public List<TimeSlotDto> getDoctorFreeTimes(UserEntity thisUser) {
		List<LocalDateTime> timeList = getSevenDaysForward(
				LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1), LocalDateTime.now().plusWeeks(1L));
		List<AppointmentEntity> appointmentList = appointmentRepo.findAllAfterToday().stream()
				.filter(app -> !app.getStatus().equals(Status.REFUZED))
				.filter(app -> app.getDentist().equals(thisUser.getUsername())).collect(Collectors.toList());

		List<TimeSlotDto> listOfTimeSlots = new ArrayList<>();
		timeList.stream()
				.map(time -> listOfTimeSlots
						.add(new TimeSlotDto(time.toLocalDate(), time.toLocalTime(), time.plusHours(1).toLocalTime())))
				.collect(Collectors.toList());
		appointmentList.forEach(app -> {
			listOfTimeSlots.removeIf(
					slot -> slot.getDate().equals(app.getDate()) && slot.getVisitStart().equals(app.getStartTime()));
		});
		return listOfTimeSlots;

	}

	@Override
	public List<DisplayAppointmentDto> getDoctorApprovedAppointments(UserEntity thisUser) {
		List<DisplayAppointmentDto> dtos = new ArrayList<>();

		appointmentRepo.findByDentist(thisUser.getUsername()).stream()
				.filter(app -> app.getStatus().equals(Status.ACTIVE) || app.getStatus().equals(Status.IN_PROGRESS))
				.map(app -> dtos.add(AppointmentConverter.toDto(app))).collect(Collectors.toList());

		return dtos;
	}

	@Override
	public List<DisplayAppointmentDto> getDoctorCancelledAppointments(UserEntity thisUser) {
		List<DisplayAppointmentDto> dtos = new ArrayList<>();

		appointmentRepo.findByDentist(thisUser.getUsername()).stream()
				.filter(app -> app.getStatus().equals(Status.REFUZED) || app.getStatus().equals(Status.DOCTOR_CANCELLED)
						|| app.getStatus().equals(Status.USER_CANCELLED))
				.map(app -> dtos.add(AppointmentConverter.toDto(app))).collect(Collectors.toList());

		return dtos;
	}

}
