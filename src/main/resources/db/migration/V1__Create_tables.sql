-- hub_exercise definition

CREATE TABLE hub_exercise (
	hub_exercise_id INTEGER auto_increment,
	load_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	load_source varchar(100),
	CONSTRAINT hub_exercise_PK PRIMARY KEY (hub_exercise_id)
);


-- hub_homework definition

CREATE TABLE hub_homework (
	hub_homework_id INTEGER auto_increment,
	publish_date DATE,
	load_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	load_source varchar(100),
	CONSTRAINT hub_homework_PK PRIMARY KEY (hub_homework_id)
);


-- hub_subject definition

CREATE TABLE hub_subject (
	hub_subject_id INTEGER auto_increment,
	subject varchar(50),
	load_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	load_source varchar(100),
	CONSTRAINT hub_subject_PK PRIMARY KEY (hub_subject_id)
);


-- hun_reward definition

CREATE TABLE hun_reward (
	hub_reward_id INTEGER auto_increment,
	load_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	load_source varchar(100),
	CONSTRAINT hun_reward_PK PRIMARY KEY (hub_reward_id)
);


-- lnk_exercise_subject definition

CREATE TABLE lnk_exercise_subject (
	lnk_exercise_subject_id INTEGER auto_increment,
	hub_exercise_id INTEGER NOT NULL,
	hub_subject_id INTEGER NOT NULL,
	CONSTRAINT lnk_exercise_subject_PK PRIMARY KEY (lnk_exercise_subject_id),
	CONSTRAINT lnk_exercise_subject_FK FOREIGN KEY (hub_exercise_id) REFERENCES hub_exercise(hub_exercise_id),
	CONSTRAINT lnk_exercise_subject_FK_1 FOREIGN KEY (hub_subject_id) REFERENCES hub_subject(hub_subject_id)
);


-- lnk_homework_subject definition

CREATE TABLE lnk_homework_subject (
	lnk_homework_subject_id INTEGER auto_increment,
	hub_homework_id INTEGER NOT NULL,
	hub_subject_id INTEGER NOT NULL,
	CONSTRAINT lnk_homework_subject_PK PRIMARY KEY (lnk_homework_subject_id),
	CONSTRAINT lnk_homework_subject_FK FOREIGN KEY (hub_homework_id) REFERENCES hub_homework(hub_homework_id),
	CONSTRAINT lnk_homework_subject_FK_1 FOREIGN KEY (hub_subject_id) REFERENCES hub_subject(hub_subject_id)
);


-- sat_exercise definition

CREATE TABLE sat_exercise (
	hub_exercise_id INTEGER NOT NULL,
	grade varchar(50),
	approval_date DATE,
	title varchar(100),
	CONSTRAINT sat_exercise_PK PRIMARY KEY (hub_exercise_id),
	CONSTRAINT sat_exercise_FK FOREIGN KEY (hub_exercise_id) REFERENCES hub_exercise(hub_exercise_id) ON DELETE CASCADE
);


-- sat_homework definition

CREATE TABLE sat_homework (
	hub_homework_id INTEGER NOT NULL,
	begin_time DATETIME,
	complete_time DATETIME,
	CONSTRAINT sat_homework_PK PRIMARY KEY (hub_homework_id),
	CONSTRAINT sat_homework_FK FOREIGN KEY (hub_homework_id) REFERENCES hub_homework(hub_homework_id) ON DELETE CASCADE
);


-- sat_reward definition

CREATE TABLE sat_reward (
	hub_reward_id INTEGER NOT NULL,
	content varchar(200),
	approval_date DATE,
	CONSTRAINT sat_reward_PK PRIMARY KEY (hub_reward_id),
	CONSTRAINT sat_reward_FK FOREIGN KEY (hub_reward_id) REFERENCES hun_reward(hub_reward_id)
);