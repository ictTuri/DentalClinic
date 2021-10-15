-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

-- DROP SEQUENCE public.appointments_appointment_id_seq;

CREATE SEQUENCE public.appointments_appointment_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.feedback_id_seq;

CREATE SEQUENCE public.feedback_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.original_appointment_id_seq;

CREATE SEQUENCE public.original_appointment_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.users_user_id_seq;

CREATE SEQUENCE public.users_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- public.feedback definition

-- Drop table

-- DROP TABLE feedback;

CREATE TABLE feedback (
	id bigserial NOT NULL,
	created_at timestamp NULL,
	description varchar(255) NULL,
	CONSTRAINT feedback_pkey PRIMARY KEY (id)
);


-- public.original_appointment definition

-- Drop table

-- DROP TABLE original_appointment;

CREATE TABLE original_appointment (
	id bigserial NOT NULL,
	"day" date NULL,
	end_time time NULL,
	start_time time NULL,
	CONSTRAINT original_appointment_pkey PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE users;

CREATE TABLE users (
	user_id bigserial NOT NULL,
	national_id varchar(255) NULL,
	age int4 NULL,
	created_at timestamp NULL,
	email varchar(255) NULL,
	first_name varchar(255) NULL,
	gender varchar(255) NULL,
	is_active bool NOT NULL,
	last_name varchar(255) NULL,
	"password" varchar(255) NULL,
	phone_number varchar(255) NULL,
	"role" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


-- public.appointments definition

-- Drop table

-- DROP TABLE appointments;

CREATE TABLE appointments (
	appointment_id bigserial NOT NULL,
	created_at timestamp NULL,
	"date" date NULL,
	dentist varchar(255) NULL,
	end_time time NULL,
	last_updated_at timestamp NULL,
	last_updated_by varchar(255) NULL,
	start_time time NULL,
	status varchar(255) NULL,
	"type" varchar(255) NULL,
	feedback int8 NULL,
	original_date int8 NULL,
	patient_user_id int8 NULL,
	CONSTRAINT appointments_pkey PRIMARY KEY (appointment_id),
	CONSTRAINT fk615h5vhg64nthaqfiegkypepe FOREIGN KEY (original_date) REFERENCES original_appointment(id),
	CONSTRAINT fkrd7qy9a093dlstw7ovstcfscl FOREIGN KEY (feedback) REFERENCES feedback(id),
	CONSTRAINT fksvnkjcycyc8v0jeh1vklfsmm8 FOREIGN KEY (patient_user_id) REFERENCES users(user_id)
);
