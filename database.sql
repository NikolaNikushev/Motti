CREATE TABLE addiction_type (
	"name" varchar(256) NOT NULL,
	CONSTRAINT addiction_type_pk PRIMARY KEY (name)
);

CREATE INDEX addiction_type_pk ON addiction_type (name);

CREATE TABLE age_group (
	"name" varchar(100) NOT NULL,
	CONSTRAINT age_group_pk PRIMARY KEY (name)
);

CREATE INDEX age_group_pk ON age_group (name);

CREATE TABLE motivation (
	description text(2147483647) NOT NULL,
	addiction varchar(256),
	is_positive bool,
	CONSTRAINT motivation_pk PRIMARY KEY (description),
	CONSTRAINT motivation_addiction_type_fk FOREIGN KEY (addiction) REFERENCES addiction_type(name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX motivation_pk ON motivation (description);

CREATE TABLE profile (
	username varchar(256) NOT NULL,
	age_range varchar(100),
	credits int4,
	CONSTRAINT profile_pk PRIMARY KEY (username),
	CONSTRAINT profile_age_group_fk FOREIGN KEY (age_range) REFERENCES age_group(name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX profile_pk ON profile (username);

CREATE TABLE profile_addiction (
	profile varchar(256) NOT NULL,
	addiction varchar(256) NOT NULL,
	amount int4,
	CONSTRAINT profile_addiction_pk PRIMARY KEY (profile,addiction),
	CONSTRAINT profile_addiction_addiction_type_fk FOREIGN KEY (addiction) REFERENCES addiction_type(name) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT profile_addiction_profile_fk FOREIGN KEY (profile) REFERENCES profile(username)
);

CREATE INDEX profile_addiction_pk ON profile_addiction (profile,addiction);

CREATE TABLE question (
	description text(2147483647) NOT NULL,
	answer_yes_points int4,
	answer_no_points int4,
	addiction_type varchar(256),
	minimum_addiction int4,
	CONSTRAINT question_pk PRIMARY KEY (description),
	CONSTRAINT question_addiction_type_fk FOREIGN KEY (addiction_type) REFERENCES addiction_type(name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX question_pk ON question (description);