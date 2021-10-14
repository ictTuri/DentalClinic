INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('admin','admin','admin','0690000000',30,'MALE',true,'A00000001A','admin@admin.com','2021-10-05 10:21:05.805','ROLE_ADMIN','$2a$10$lOt1TwPZV1UMOeAD8y7HCOo1bwUSkd.IKEMRLPI99ZnmIEtwAesKC');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('secretary','secretary','secretary','0690000001',25,'FEMALE',true,'S00000001S','secretary@secretary.com','2021-10-05 10:21:05.805','ROLE_SECREATRY','$2a$10$PJ70b4pV2fWPGHyk6lmdPew6Mu69/oG/1gWnmlleFI3xhuBR6sbI2');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('dentistone','dentistone','dentistone','0680000001',30,'MALE',true,'D00000001D','dentistone@dentistone.com','2021-10-05 10:21:05.805','ROLE_DOCTOR','$2a$10$4GYXQCDGYjwJ1Mxa35UfbuaoUJRze7ugWFlP86TRI8QTpDvCWrKom');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('dentisttwo','dentisttwo','dentisttwo','0680000002',30,'FEMALE',true,'D00000001D','dentisttwo@dentisttwo.com','2021-10-05 10:21:05.805','ROLE_DOCTOR','$2a$10$4GYXQCDGYjwJ1Mxa35UfbuaoUJRze7ugWFlP86TRI8QTpDvCWrKom');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('userone','userone','userone','0670000001',45,'MALE',true,'U00000001U','userone@userone.com','2021-10-05 10:21:05.805','ROLE_PUBLIC','$2a$10$cDr0W8sc605ROV61.Wiz9e6CB5RNwiIB0f05jnexmua1iYdWxQSi.');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('usertwo','usertwo','usertwo','0670000002',28,'FEMALE',true,'U00000002U','usertwo@usertwo.com','2021-10-05 10:21:05.805','ROLE_PUBLIC','$2a$10$cDr0W8sc605ROV61.Wiz9e6CB5RNwiIB0f05jnexmua1iYdWxQSi.');
INSERT INTO users (first_name,last_name,username,phone_number,age,gender,is_active,national_id,email,created_at,role,password)
VALUES ('userthree','userthree','userthree','0670000003',32,'MALE',true,'U00000003U','userthree@userthree.com','2021-10-05 10:21:05.805','ROLE_PUBLIC','$2a$10$cDr0W8sc605ROV61.Wiz9e6CB5RNwiIB0f05jnexmua1iYdWxQSi.');


INSERT INTO appointments (created_at,date,dentist,last_updated_at,last_updated_by,start_time,end_time,status,type,feedback,original_date,patient_user_id)
VALUES ('2021-10-05 10:21:05.805','2021-11-01','dentistone','2021-10-05 10:21:05.805','userone','10:00:00','11:00:00','APPENDING','FILLINGS',null,null,5);
INSERT INTO appointments (created_at,date,dentist,last_updated_at,last_updated_by,start_time,end_time,status,type,feedback,original_date,patient_user_id)
VALUES ('2021-10-05 10:21:05.805','2021-11-05','dentisttwo','2021-10-05 10:21:05.805','usertwo','11:00:00','12:00:00','ACTIVE','FILLINGS',null,null,6);
INSERT INTO appointments (created_at,date,dentist,last_updated_at,last_updated_by,start_time,end_time,status,type,feedback,original_date,patient_user_id)
VALUES ('2021-10-05 10:21:05.805','2021-11-05','dentistone','2021-10-05 10:21:05.805','usertwo','13:00:00','14:00:00','APPENDING','FILLINGS',null,null,6);
INSERT INTO appointments (created_at,date,dentist,last_updated_at,last_updated_by,start_time,end_time,status,type,feedback,original_date,patient_user_id)
VALUES ('2021-10-05 10:21:05.805','2021-11-05','dentistone','2021-10-05 10:21:05.805','userthree','14:00:00','15:00:00','APPENDING_USER','FILLINGS',null,null,7);


INSERT INTO feedback (created_at,description)
VALUES ('2021-10-05 10:21:05.805','DEFAULT FEEDBACK');