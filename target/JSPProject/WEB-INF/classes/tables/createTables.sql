CREATE TABLE PERSON (
	ID 				int unsigned 		auto_increment,
	first_name 		varchar(26) 		not null,
	last_name 		varchar(26) 		not null,
	street_address 	varchar(50),
	city 			varchar(26),
	state 			varchar(26),
	zip 			varchar(26),
	country 		varchar(26),
	phone_number 	varchar(26),
	email 			varchar(26) 		not null,
	gender 			varchar(1),
	sin 			varchar(8),
	
	PRIMARY KEY(ID),
	
	CONSTRAINT name_cnt UNIQUE(first_name, last_name, email)
);

CREATE TABLE STUDENTS (
	person_id 	int unsigned 			not null,
	admission_status 	varchar(26) 	not null,
	major 		varchar(26) 			not null,
	minor 		varchar(26),
	credit 		int unsigned 			default 0 not null,
	start_date 	timestamp 				default current_timestamp not null,
	
	PRIMARY KEY(person_id),
	
	FOREIGN KEY(person_id)
	  REFERENCES PERSON(id)
);

CREATE TABLE USERS (
	username 		varchar(26),
	password 		varchar(26)  		not null,
	owner_id 		int unsigned 		not null,
	authority 		int unsigned 		default 5 not null,
	
	PRIMARY KEY(username),
	
	FOREIGN KEY (owner_id)
	  REFERENCES PERSON(id)
);