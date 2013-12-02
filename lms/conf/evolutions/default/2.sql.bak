# --- !Ups


INSERT INTO EMPLOYEES(first,last,username) VALUES('Jeff','Zehnder','labman');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'admin');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage clients');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage cases');
INSERT INTO USER_ROLES(employee_number,role_name) VALUES((SELECT employee_number FROM EMPLOYEES WHERE username = 'labman') ,'manage results');
INSERT INTO CLIENT(last,first,city,zip, office_phone,phone_report_OK, email_report_OK) 
VALUES('goodman','saul','Riverside','92005','9098675309',1,1);

INSERT INTO CASES(clt_no, case_number, subject_lastname, subject_firstname,  received_date, received_by, sample_type, email_results_OK) 
VALUES((SELECT c.client_id FROM CLIENT AS c WHERE c.last = 'goodman'), '007','Heisenberg', 'Walter', CURDATE(), (SELECT employee_number FROM EMPLOYEES WHERE username = 'labman'), 
'blood',  1); 


INSERT INTO CASES(clt_no, case_number, subject_lastname, subject_firstname, received_date, received_by, sample_type, email_results_OK) 
VALUES((SELECT c.client_id FROM CLIENT AS c WHERE c.last = 'goodman'), '008','Heisenberg', 'Walter', CURDATE(), (SELECT employee_number FROM EMPLOYEES WHERE username = 'labman'), 
'blood', 1); 
INSERT INTO COMMENTS(comment_code, comment_text) 
VALUES(1, 'Mary-J is bad, mkay');

INSERT INTO TEST(test_number, test_name, test_type, type_of_sample)
VALUES(100, 'mary-j blood', 'T', 'blood');

INSERT INTO TEST(test_number, test_name, test_type, type_of_sample, default_comment)
VALUES(101, 'mary-j urine', 'T', 'urine', 1);

INSERT INTO CASE_TEST(case_FK, test_FK, reported, reported_date, date_completed, 
	employee_entered, employee_performed) 
VALUES((SELECT case_PK FROM CASES WHERE case_number = '007'), 101, 
	0, NULL, NULL, NULL, NULL);

INSERT INTO CASE_TEST(case_FK, test_FK, reported, reported_date, date_completed, 
	employee_entered, employee_performed) 
VALUES((SELECT case_PK FROM CASES WHERE case_number = '008'), 101, 
	0, NULL, NULL, NULL, NULL);

INSERT INTO CASE_TEST(case_FK, test_FK, reported, reported_date, date_completed, 
	employee_entered, employee_performed) 
VALUES((SELECT case_PK FROM CASES WHERE case_number = '008'), 100, 
	0, NULL, NULL, NULL, NULL);	
	
--alter table EMPLOYEES ENGINE=InnoDB;
--alter table USER_ROLES ENGINE=InnoDB;
--alter table COMMENTS ENGINE=InnoDB;
--alter table TEST ENGINE=InnoDB;
--alter table CLIENT ENGINE=InnoDB;
--alter table CASES ENGINE=InnoDB;
--alter table CASE_TEST ENGINE=InnoDB;
--alter table CASE_TEST_RESULTS_COMMENTS ENGINE=InnoDB;

# --- !Downs

