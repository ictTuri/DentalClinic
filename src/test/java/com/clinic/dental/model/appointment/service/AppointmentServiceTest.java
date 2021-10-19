package com.clinic.dental.model.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.appointment.service.impl.AppointmentServiceImpl;
import com.clinic.dental.model.feedback.FeedbackEntity;
import com.clinic.dental.model.feedback.converter.FeedbackConverter;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.feedback.repository.FeedbackRepository;
import com.clinic.dental.model.original_appointment.repository.OriginalAppointmentRepository;
import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.repository.UserRepository;
import com.clinic.dental.model.user.service.UserService;
import com.clinic.dental.util.appointment.AppointmentDtoUtilTest;
import com.clinic.dental.util.appointment.AppointmentUtilTest;
import com.clinic.dental.util.user.UserUtilTest;

@SpringBootTest
@Transactional
class AppointmentServiceTest {

	@InjectMocks
	AppointmentServiceImpl appService;
	@Mock
	AppointmentRepository appointmentRepo;
	@Mock
	FeedbackRepository feedbackRepo;
	@Mock
	UserRepository userRepo;
	@Mock
	OriginalAppointmentRepository originalAppointmentRepo;
	@Mock
	UserService userService;

	private AppointmentEntity app1, app2, app3;

	@BeforeEach
	public void setup() {
		this.app1 = AppointmentUtilTest.appointmentFour();
		this.app2 = AppointmentUtilTest.appointmentFive();
		this.app3 = AppointmentUtilTest.appointmentSix();
	}

	@Test
	void givenList_WhenGetAll_thenValidate() {
		List<AppointmentEntity> appointments = new ArrayList<>();
		appointments.add(app1);
		appointments.add(app2);
		appointments.add(app3);

		when(appointmentRepo.findAll()).thenReturn(appointments);

		List<AppointmentEntity> returnedList = appService.getAllAppointments();

		assertEquals(3, returnedList.size());
		assertNotNull(returnedList);
	}

	@Test
	void givenId_WhenGetById_ThenCheckValidations() {
		Long id = 1L;
		UserEntity thisPublicUser = UserUtilTest.publicOne();
		AppointmentEntity appointment = app1;
		appointment.setPatient(UserUtilTest.publicOne());
		AppointmentEntity nextAppointment = AppointmentUtilTest.appointmentFive();

		when(appointmentRepo.locateById(id)).thenReturn(null).thenReturn(appointment).thenReturn(nextAppointment)
				.thenReturn(appointment);

		assertThrows(DataIdNotFoundException.class, () -> {
			appService.getAppointmentById(id, thisPublicUser);
		});

		DisplayAppointmentDto publicDto = appService.getAppointmentById(id, thisPublicUser);

		assertNotNull(publicDto);
		assertEquals("dentistname", publicDto.getDentist());

		UserEntity thisDoctorUser = UserUtilTest.doctorOne();
		DisplayAppointmentDto doctorDto = appService.getAppointmentById(id, thisDoctorUser);

		assertNotNull(doctorDto);
		assertEquals(Status.APPENDING_USER.toString(), doctorDto.getStatus());
	}

	@Test
	void givenSlotDtoList_WhenAskedFor_Validate() {
		List<AppointmentEntity> appointmentList = new ArrayList<>();
		appointmentList.add(app1);
		appointmentList.add(app2);
		appointmentList.add(app3);
		String[] doctors = { "dentist", "doctor" };

		when(appointmentRepo.findAllAfterToday()).thenReturn(appointmentList);
		when(userService.getDoctorsName(Role.ROLE_DOCTOR.toString())).thenReturn(doctors);

		List<SlotDto> slots = appService.getFreeTimes();

		assertNotNull(slots);
	}

	@Test
	void givenLocalDateTime_WhenCheckIsWorkingHour_Validate() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Method method = AppointmentServiceImpl.class.getDeclaredMethod("isWorkingHours", LocalDateTime.class);
		method.setAccessible(true);
		LocalDateTime time = LocalDateTime.of(2021, 12, 6, 15, 0);
		boolean isWorkingHour = (boolean) method.invoke(appService, time);
		assertTrue(isWorkingHour);
	}

	@Test
	void givenDay_WhenCheckIsWorking_Validate() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = AppointmentServiceImpl.class.getDeclaredMethod("isWorkingDay", LocalDateTime.class);
		method.setAccessible(true);
		LocalDateTime time = LocalDateTime.of(2021, 12, 6, 8, 0);
		boolean isWorkingDay = (boolean) method.invoke(appService, time);
		assertTrue(isWorkingDay);
	}

	@Test
	void givenTimes_WhenGetTimeSlots_VerifySize() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = AppointmentServiceImpl.class.getDeclaredMethod("getSevenDaysForward", LocalDateTime.class,
				LocalDateTime.class);
		method.setAccessible(true);
		LocalDateTime startTime = LocalDateTime.of(2021, 12, 6, 8, 0);
		@SuppressWarnings("unchecked")
		List<LocalDateTime> timeList = (List<LocalDateTime>) method.invoke(appService, startTime,
				startTime.plusDays(7));
		assertNotNull(timeList);
	}

	@Test
	void givenDto_WhenRezerve_thenThrows() {
		CreatePublicAppointmentDto dto = AppointmentDtoUtilTest.rezerveSlot();
		UserEntity user = UserUtilTest.publicTwo();

		assertThrows(CustomMessageException.class, () -> appService.rezerveAppointment(dto, user));
	}

	@Test
	void givenId_WhenApprove_verify() {
		Long id = 1L;
		AppointmentEntity app = AppointmentUtilTest.rezervedAppointment();

		when(appointmentRepo.getById(id)).thenReturn(app);
		when(appointmentRepo.save(app)).thenReturn(app);

		DisplayAppointmentDto dto = appService.approveAppointmentById(id);

		assertNotNull(dto);
		assertEquals("ACTIVE", dto.getStatus().toString());

	}

	@Test
	void givenId_WhenApprove_ThrowsCustomException() {
		Long id = 1L;
		AppointmentEntity app = AppointmentUtilTest.appointmentFive();

		when(appointmentRepo.getById(id)).thenReturn(app);

		assertThrows(CustomMessageException.class, () -> appService.approveAppointmentById(id));
	}

	@Test
	void givenId_WhenApprove_ThrowsForNullApp() {
		Long id = 1L;
		;

		when(appointmentRepo.getById(id)).thenReturn(null);

		assertThrows(DataIdNotFoundException.class, () -> appService.approveAppointmentById(id));
	}

	@Test
	void givenNewDate_WhenGet_ReturnNull() {
		ChangeAppointmentTimeDto dto = new ChangeAppointmentTimeDto();
		when(appointmentRepo.getById(1L)).thenReturn(null);

		assertThrows(DataIdNotFoundException.class, () -> appService.changeAppointmentTimeById(1L, dto));
	}

	@Test
	void givenNewTime_WhenChange_Verify() {
		ChangeAppointmentTimeDto dto = new ChangeAppointmentTimeDto();
		dto.setDay(LocalDate.of(2021, 10, 21));
		dto.setStartTime(LocalTime.of(13, 00));

		when(appointmentRepo.getById(1L)).thenReturn(app1);
		when(appointmentRepo.save(app1)).thenReturn(app1);

		assertThrows(CustomMessageException.class, () -> appService.changeAppointmentTimeById(1L, dto));
	}

	@Test
	void givenList_WhenGetCancelledByDentist_Filter() {
		UserEntity user = UserUtilTest.doctorTwo();
		List<AppointmentEntity> list = new ArrayList<>();
		list.add(app1);
		list.add(app2);
		list.add(app3);

		when(appointmentRepo.findByDentist(user.getUsername())).thenReturn(list);

		List<DisplayAppointmentDto> dtos = appService.getDoctorCancelledAppointments(user);
		assertNotNull(dtos);
	}

	@Test
	void givenList_WhenGetApprovedByDentist_Filter() {
		UserEntity user = UserUtilTest.doctorTwo();
		List<AppointmentEntity> list = new ArrayList<>();
		list.add(app1);
		list.add(app2);
		list.add(app3);

		when(appointmentRepo.findByDentist(user.getUsername())).thenReturn(list);

		List<DisplayAppointmentDto> dtos = appService.getDoctorApprovedAppointments(user);
		assertNotNull(dtos);
	}

	@Test
	void givenID_WhenSetFeedBack_Validate() {
		UserEntity user = UserUtilTest.doctorTwo();
		CreateFeedbackDto dto = new CreateFeedbackDto();
		FeedbackEntity feedback = FeedbackConverter.toEntity(dto);
		when(appointmentRepo.getActiveAppointmentForFeedback(user.getUsername())).thenReturn(app1);
		when(feedbackRepo.save(feedback)).thenReturn(feedback);
		when(appointmentRepo.save(app1)).thenReturn(app1);

		DisplayAppointmentDto dtoGet = appService.setAppointmentFeedback(1L, dto, user);
		assertNotNull(dtoGet);
	}

	@Test
	void givenNewTime_WhenUserApprove_Throw() {
		UserEntity user = UserUtilTest.publicTwo();

		when(userService.getAuthenticatedUser()).thenReturn(user);
		when(appointmentRepo.findByIdAndStatus(1L, Status.APPENDING_USER)).thenReturn(app1);

		assertThrows(DataIdNotFoundException.class, () -> appService.approveNewTimeAppointment(1L));
	}

	@Test
	void givenNewTime_WhenUserApprove_Validate() {
		UserEntity user = UserUtilTest.publicTwo();
		app1.setPatient(user);

		when(userService.getAuthenticatedUser()).thenReturn(user);
		when(appointmentRepo.findByIdAndStatus(1L, Status.APPENDING_USER)).thenReturn(app1);
		when(appointmentRepo.save(app1)).thenReturn(app1);

		DisplayAppointmentDto dto = appService.approveNewTimeAppointment(1L);

		assertNotNull(dto);
	}

	@Test
	void givenStatus_Verify_OrThrow() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = AppointmentServiceImpl.class.getDeclaredMethod("isValidStatus", String.class);
		method.setAccessible(true);
		String status = Status.DONE.toString();
		boolean isValid = (boolean) method.invoke(appService, status);
		assertTrue(isValid);
		boolean emptyStatus = (boolean) method.invoke(appService, "");
		assertFalse(emptyStatus);
	}

}
