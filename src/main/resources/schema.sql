-- 학부모
CREATE TABLE `PARENTS`
(
    `parents_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `uid`        VARCHAR(30) not NULL UNIQUE,
    `upw`        VARCHAR(70) NULL,
    `nm`         VARCHAR(20) not NULL,
    `phone`      VARCHAR(20) not NULL,
    `sub_phone`  VARCHAR(20) NULL,
    `email`      VARCHAR(255) NULL UNIQUE,
    `connet`     VARCHAR(10) not NULL,
    `auth`       VARCHAR(30) not NULL DEFAULT 'ROLE_PARENTS',
    `addr`       VARCHAR(300) NULL,
    `acept`      INT         NOT NULL DEFAULT 2,
    created_at   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME ON UPDATE CURRENT_TIMESTAMP
);
TRUNCATE TABLE `PARENTS`;
-- 학생
CREATE TABLE `STUDENT`
(
    `stu_id`     BIGINT PRIMARY KEY AUTO_INCREMENT,
    `parents_id` BIGINT NULL,
    `grade`      int          not NULL COMMENT 'ex) 1(학년)01(반)01(번호)->10101 ',
    `name`       VARCHAR(20)  not NULL,
    `gender`     VARCHAR(5)   not NULL,
    `birth`      DATE         not NULL,
    `pic`        VARCHAR(255) NULL,
    `addr`       VARCHAR(300) not NULL,
    `phone`      VARCHAR(20)  not NULL,
    `etc`        VARCHAR(1000) NULL,
    state        INT          NOT NULL DEFAULT 1 COMMENT '1-> 재학중, 2-> 졸업, 3-> 전학',
    eng_name     VARCHAR(30) NULL,
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parents_id) REFERENCES PARENTS (parents_id) ON DELETE SET NULL
);
TRUNCATE TABLE `STUDENT`;
-- 선생
CREATE TABLE `TEACHER`
(
    `tea_id`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `uid`      VARCHAR(30)  not NULL UNIQUE,
    `upw`      VARCHAR(70) NULL,
    `nm`       VARCHAR(20)  not NULL,
    `phone`    VARCHAR(20)  not NULL,
    `email`    VARCHAR(255) not NULL UNIQUE,
    `gender`   VARCHAR(5)   not NULL,
    `birth`    DATE NULL,
    `auth`     VARCHAR(30)  not NULL DEFAULT 'ROLE_TEAHCER',
    `addr`     VARCHAR(300) NULL,
    `acept`    INT          NOT NULL DEFAULT 2,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);
TRUNCATE TABLE `TEACHER`;
-- 반
CREATE TABLE `CLASS`
(
    `class_id` INT PRIMARY KEY,
    `tea_id`   BIGINT UNIQUE NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (tea_id) REFERENCES teacher (tea_id)
);
TRUNCATE TABLE `CLASS`;
-- 학생 - 반
CREATE TABLE `student_classes`
(
    sc_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `stu_id`   BIGINT   NOT NULL,
    `class_id` INT      not NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (stu_id) REFERENCES `STUDENT` (stu_id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES `CLASS` (class_id)
);
TRUNCATE TABLE `student_classes`;
-- 성적
CREATE TABLE score
(
    score_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    sc_id      BIGINT      NOT NULL,
    `year`     INT         NOT NULL,
    `semester` INT         NOT NULL,
    `name`     VARCHAR(10) NOT NULL,
    `exam`     INT         NOT NULL,
    mark       INT         NOT NULL,
    created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (sc_id) REFERENCES `student_classes` (sc_id)
);

TRUNCATE TABLE `score`;
-- 알림장
CREATE TABLE `NOTICE`
(
    `notice_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `tea_id`    BIGINT        NOT NULL,
    `title`     VARCHAR(50)   not NULL,
    `content`   VARCHAR(1000) not NULL,
    `class_id`  INT           NOT NULL,
    created_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (tea_id) REFERENCES `TEACHER` (tea_id),
    FOREIGN KEY (class_id) REFERENCES `CLASS` (class_id)
);
TRUNCATE TABLE `NOTICE`;
-- 성적 확인
CREATE TABLE `score_sign`
(
    sign_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    stu_id     BIGINT       NOT NULL,
    `year`     INT          NOT NULL,
    `semester` INT          NOT NULL,
    pic        VARCHAR(255) not NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (stu_id, `year`, `semester`),
    FOREIGN KEY (stu_id) REFERENCES `STUDENT` (stu_id)
);
TRUNCATE TABLE `score_sign`;