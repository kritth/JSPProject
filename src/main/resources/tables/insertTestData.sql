/* Insert a person */
INSERT INTO PERSON(first_name, last_name, email) VALUES("Admin", "Admin", "Admin@gmail.com");
INSERT INTO PERSON(first_name, last_name, email) VALUES("Subadmin", "Subadmin", "Subadmin@gmail.com");

/* Insert a student */
INSERT INTO STUDENTS(person_id, admission_status, major, credit) VALUES(2, "admitted", "comp sci", 120);

/* Insert a user */
INSERT INTO USERS(username, password, owner_id) VALUES("admin", "admin", 1);
INSERT INTO USERS(username, password, owner_id) VALUES("sub", "sub", 2);