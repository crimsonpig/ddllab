# --- First database schema

# --- !Ups

CREATE TABLE IF NOT EXISTS EMPLOYEES
(
	employee_number TINYINT NOT NULL AUTO_INCREMENT,  
	username VARCHAR(20) NOT NULL, 
	password VARCHAR(20), 
	first VARCHAR(20) NOT NULL, 
	last VARCHAR(20) NOT NULL, 
	UNIQUE(username), 
	PRIMARY KEY(employee_number) 
); 

CREATE TABLE IF NOT EXISTS USER_ROLES 
(
	employee_number TINYINT NOT NULL, 
	role_name VARCHAR(64) NOT NULL, 
	PRIMARY KEY(employee_number, role_name),   
	FOREIGN KEY(employee_number) REFERENCES EMPLOYEES(employee_number) 
); 
 
CREATE TABLE IF NOT EXISTS COMMENTS
(
	comment_code INT NOT NULL AUTO_INCREMENT, 
	comment_text VARCHAR(800) NOT NULL, 
	test_number SMALLINT, 
	PRIMARY KEY(comment_code) 
);

CREATE TABLE IF NOT EXISTS TEST (
	test_number SMALLINT NOT NULL, 
	test_name VARCHAR(30) NOT NULL, 
	test_type CHAR(1) NOT NULL, 
	units VARCHAR(10), 
	default_comment INT, 
	type_of_sample VARCHAR(10), 
	respicture VARCHAR(8), 
	control_text VARCHAR(500),  
	result_text VARCHAR(500), 
	control_result_line VARCHAR(10), 
	PRIMARY KEY(test_number), 
	FOREIGN KEY (default_comment) REFERENCES COMMENTS(comment_code) 
);

CREATE TABLE IF NOT EXISTS CLIENT
(
	client_id INT NOT NULL AUTO_INCREMENT,	
	last VARCHAR(20) NOT NULL,
	first VARCHAR(20) NOT NULL, 
	mi VARCHAR(1), 
	company VARCHAR(30), 
	street_address VARCHAR(50), 
	mailing_address VARCHAR(50), 
	city VARCHAR(30) NOT NULL, 
	state CHAR(2), 
	zip VARCHAR(10) NOT NULL, 
	office_phone CHAR(10) NOT NULL, 
	cell_phone CHAR(10), 
	sex CHAR(1), 
	fax CHAR(10), 
	reporting_email VARCHAR(30), 
	phone_report_OK BIT NOT NULL, 
	email_report_OK BIT NOT NULL, 
	PRIMARY KEY(client_id) 
);

CREATE TABLE IF NOT EXISTS CASES
(
	case_PK INT NOT NULL AUTO_INCREMENT, 
	case_number VARCHAR(7), 
	clt_no INT NOT NULL, 
	ccto_client INT, 
	subject_lastname VARCHAR(20) NOT NULL, 
	subject_firstname VARCHAR(20) NOT NULL, 
	received_date DATE, 
	received_from INT DEFAULT 0, 
	received_by TINYINT NOT NULL, 
	date_collected DATE, 
	time_collected TIME, 
	other_id_number VARCHAR(10), 
	sample_type VARCHAR(10) NOT NULL, 
	medical_history_notes VARCHAR(100), 
	note_code INT, 
	email_results_OK BIT NOT NULL, 
	all_tasks_completed BIT NOT NULL DEFAULT 0, 
	date_tasks_completed DATE, 
	UNIQUE(case_number), 
	PRIMARY KEY(case_PK), 
	FOREIGN KEY (clt_no) REFERENCES CLIENT(client_id), 
	FOREIGN KEY (note_code) REFERENCES COMMENTS(comment_code), 
	FOREIGN KEY (ccto_client) REFERENCES CLIENT(client_id), 
	FOREIGN KEY (received_by) REFERENCES EMPLOYEES(employee_number) 
);

CREATE TABLE IF NOT EXISTS CASE_TEST 
(
	case_test_PK BIGINT NOT NULL AUTO_INCREMENT, 	
	case_FK INT NOT NULL, 
	test_FK SMALLINT NOT NULL, 
	results_entered BIT NOT NULL DEFAULT 0, 
	reported BIT NOT NULL DEFAULT 0, 
	reported_date DATE DEFAULT NULL, 
	date_completed DATE DEFAULT NULL, 
	employee_entered TINYINT, 
	employee_performed TINYINT, 
	PRIMARY KEY(case_test_PK),  
	FOREIGN KEY (test_FK) REFERENCES TEST(test_number), 
	FOREIGN KEY (case_FK) REFERENCES CASES(case_PK), 
	FOREIGN KEY (employee_entered) REFERENCES EMPLOYEES(employee_number), 
	FOREIGN KEY (employee_performed) REFERENCES EMPLOYEES(employee_number) 
);

CREATE TABLE IF NOT EXISTS CASE_TEST_RESULTS_COMMENTS 
(
	case_test_FK BIGINT NOT NULL, 
	results VARCHAR(8), 
	employee_entered TINYINT, 
	informational_comment INT, 
	actual_comment INT, 
	UNIQUE(case_test_FK), 
	FOREIGN KEY (case_test_FK) REFERENCES CASE_TEST(case_test_PK), 
	FOREIGN KEY (employee_entered) REFERENCES EMPLOYEES(employee_number), 
	FOREIGN KEY (informational_comment) REFERENCES COMMENTS(comment_code), 
	FOREIGN KEY (actual_comment) REFERENCES COMMENTS(comment_code) 
);

INSERT INTO EMPLOYEES(first,last,username) VALUES('Jeff','Zehnder','labman');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'admin');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage clients');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage cases');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage results');

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS EMPLOYEES;

DROP TABLE IF EXISTS USER_ROLES; 

DROP TABLE IF EXISTS COMMENTS;

DROP TABLE IF EXISTS TEST; 

DROP TABLE IF EXISTS CLIENT; 

DROP TABLE IF EXISTS CASES;

DROP TABLE IF EXISTS CASE_TEST; 

DROP TABLE IF EXISTS CASE_TEST_RESULTS_COMMENTS; 

SET REFERENTIAL_INTEGRITY TRUE;
