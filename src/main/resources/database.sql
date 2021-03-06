--
-- File: schema.sql
-- Author: Vinod Parlapalli
-- Created on 2019/09/27
--

-- DROP TABLE STUDENT_COURSE;
-- DROP TABLE STUDENT;
-- DROP TABLE COURSE;
-- DROP TABLE DEPARTMENT;

/*
CREATE TABLE DEPARTMENT (
	ID BIGINT AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE COURSE (
	ID BIGINT AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL,
    DEPARTMENT_ID BIGINT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY(DEPARTMENT_ID) REFERENCES DEPARTMENT(ID) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE STUDENT (
	ID BIGINT AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL,
    DOB DATE,
    PRIMARY KEY(ID)
);

CREATE TABLE STUDENT_COURSE (
	STUDENT_ID BIGINT,
    COURSE_ID BIGINT,
    PRIMARY KEY(STUDENT_ID, COURSE_ID),
    FOREIGN KEY(STUDENT_ID) references STUDENT(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(COURSE_ID) REFERENCES COURSE(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- DELETE FROM DEPARTMENT;
-- DELETE FROM COURSE;
-- DELETE FROM STUDENT;
-- DELETE FROM STUDENT_COURSE;

