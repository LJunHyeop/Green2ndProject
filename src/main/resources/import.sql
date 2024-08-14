
INSERT INTO `teacher` (`accept`, `birth`, `state`, `gender`, `created_at`, `tea_id`, `updated_at`, `name`, `phone`, `auth`, `uid`, `upw`, `addr`, `email`) VALUES	(1, '2024-07-31', 1, NULL, '2024-07-31 15:30:54.000000', 1, '2024-07-31 15:30:55.000000', '김철', '010-000-0000', 'ROLE_ADMIN', 'admin1234', '$2a$10$K/DxoACUp2sB/nZGq02yseVoKRaFUQbzpfp/Yyst33IoO1KLP7.L.', NULL, 'rlacjf111@naver.com');

-- 학부모 더미 데이터
INSERT INTO `parents` (`parents_id`, `created_at`, `updated_at`, `accept`, `addr`, `auth`, `email`, `name`, `phone`, `state`, `uid`, `upw`, `connect`, `sub_phone`) VALUES (1, '2024-08-02 14:38:23.000000', NULL, 1, NULL, 'ROLE_PARENTS', 'rlacjf111@naver.com', '김철부모', '010-0000-0000', 1, 'rlacjf111', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL),(2, '2024-08-02 14:38:23.000000', NULL, 2, NULL, 'ROLE_PARENTS', 'aaaa111@naver.com', '테스트', '010-0000-0000', 1, '1111', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL),(3, '2024-08-02 14:38:23.000000', NULL, 2, NULL, 'ROLE_PARENTS', 'aaaa112@naver.com', '테스트2', '010-0000-0000', 1, '1112', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL),(4, '2024-08-02 14:38:23.000000', NULL, 2, NULL, 'ROLE_PARENTS', 'aaaa113@naver.com', '테스트3', '010-0000-0000', 1, '1113', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL),(5, '2024-08-02 14:38:23.000000', NULL, 2, NULL, 'ROLE_PARENTS', 'aaaa114@naver.com', '테스트4', '010-0000-0000', 1, '1114', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL),(6, '2024-08-02 14:38:23.000000', NULL, 2, NULL, 'ROLE_PARENTS', 'aaaa115@naver.com', '테스트5', '010-0000-0000', 1, '1115', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', NULL, NULL);

INSERT INTO `student` (`accept`, `birth`, `grade`, `state`, `gender`, `created_at`, `parent_id`, `stu_id`, `updated_at`, `name`, `phone`, `auth`, `eng_name`, `uid`, `upw`, `addr`, `etc`, `email`, `pic`, `rand_code`) VALUES (1, '2024-08-01', 10101, 1, '남', '2024-08-01 09:37:33.000000', 1, 1, '2024-08-01 09:37:39.000000', '홍길동', '010-3453-4345', 'ROLE_STUDENT', NULL, 'ghdrlfehd111', '$2a$10$K/DxoACUp2sB/nZGq02yseVoKRaFUQbzpfp/Yyst33IoO1KLP7.L.', NULL, NULL, 'rlfehd@naver.com', NULL, '123412');
INSERT INTO `student` (`accept`, `birth`, `grade`, `state`, `gender`, `created_at`, `parent_id`, `stu_id`, `updated_at`, `name`, `phone`, `auth`, `eng_name`, `uid`, `upw`, `addr`, `etc`, `email`, `pic`, `rand_code`) VALUES (1, '2024-08-02', 10102, 1, '여', '2024-08-01 09:37:33.000000', NULL, 2, '2024-08-01 09:37:39.000000', '낭길동', '010-3452-4345', 'ROLE_STUDENT', NULL, 'ghdrl3123fehd111', NULL, NULL, NULL, 'rlfasdfhd@naver.com', NULL, '133412');

INSERT INTO `parent_oath2` (`provider_type`, `created_at`, `oath2id`, `parent_id`, `updated_at`, `email`, `id`, `name`) VALUES (4, '2024-08-01 17:33:28.000000', 1, 1, '2024-08-01 17:33:30.000000', 'asdf@gmail.com', NULL, '홍길동');

INSERT INTO `student` (`stu_id`, `created_at`, `updated_at`, `accept`,`addr`,`auth`,`email`,`name`,`phone`,`state`,`uid`,`upw`,`birth`,`gender`,`eng_name`,`etc`,`grade`,`pic`,`rand_code`,`parent_id`) VALUES (3, '2024-08-05 09:00:00', '2024-08-05 09:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student1@example.com', '김철수', '010-1234-5678',1 , 'student1', 'password1', '2000-01-01', '남', 'Chulsoo Kim', NULL, 10103, NULL, 'ABC123', NULL), (4, '2024-08-05 09:15:00', '2024-08-05 09:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student2@example.com', '이영희', '010-2345-6789',1 , 'student2', 'password2', '2001-02-02', '여', 'Younghee Lee', NULL, 20201, NULL, 'DEF456', NULL), (5, '2024-08-05 09:30:00', '2024-08-05 09:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student3@example.com', '박민수', '010-3456-7890', 1, 'student3', 'password3', '2002-03-03', '남', 'Minsoo Park', NULL, 20202, NULL, 'GHI789', NULL), (6, '2024-08-05 09:45:00', '2024-08-05 09:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student4@example.com', '정수연', '010-4567-8901', 1, 'student4', 'password4', '2000-04-04', '여', 'Suyeon Jung', NULL, 20203, NULL, 'JKL012', NULL), (7, '2024-08-05 10:00:00', '2024-08-05 10:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student5@example.com', '홍길동', '010-5678-9012', 1, 'student5', 'password5', '2001-05-05', '남', 'Gildong Hong', NULL,20101, NULL, 'MNO345', NULL), (8, '2024-08-05 10:15:00', '2024-08-05 10:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student6@example.com', '강지연', '010-6789-0123', 1, 'student6', 'password6', '2002-06-06', '여', 'Jiyeon Kang', NULL, 30301, NULL, 'PQR678', NULL), (9, '2024-08-05 10:30:00', '2024-08-05 10:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student7@example.com', '손흥민', '010-7890-1234', 1, 'student7', 'password7', '2000-07-07', '남', 'Heungmin Son', NULL, 30101, NULL, 'STU901', NULL), (10, '2024-08-05 10:45:00', '2024-08-05 10:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student8@example.com', '김연아', '010-8901-2345', 1, 'student8', 'password8', '2001-08-08', '여', 'Yuna Kim', NULL, 30102, NULL, 'VWX234', NULL), (11, '2024-08-05 11:00:00', '2024-08-05 11:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student9@example.com', '박지성', '010-9012-3456', 1, 'student9', 'password9', '2002-09-09', '남', 'Jisung Park', NULL, 40101, NULL, 'YZA567', NULL), (12, '2024-08-05 11:15:00', '2024-08-05 11:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student10@example.com', '이나영', '010-0123-4567', 1, 'student10', 'password10', '2000-10-10', '여', 'Nayoung Lee', NULL, 40102, NULL, 'BCD890', NULL), (13, '2024-08-05 11:30:00', '2024-08-05 11:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student11@example.com', '정우성', '010-1234-5679', 1, 'student11', 'password11', '2001-11-11', '남', 'Woosung Jung', NULL, 40103, NULL, 'EFG123', NULL), (14, '2024-08-05 11:45:00', '2024-08-05 11:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student12@example.com', '송혜교', '010-2345-6780', 1, 'student12', 'password12', '2002-12-12', '여', 'Hyekyo Song', NULL,50101, NULL, 'HIJ456', NULL), (15, '2024-08-05 12:00:00', '2024-08-05 12:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student13@example.com', '유재석', '010-3456-7891', 1, 'student13', 'password13', '2000-01-13', '남', 'Jaesuk Yoo', NULL, 50102, NULL, 'KLM789', NULL), (16, '2024-08-05 12:15:00', '2024-08-05 12:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student14@example.com', '김태희', '010-4567-8902', 1, 'student14', 'password14', '2001-02-14', '여', 'Taehee Kim', NULL, 50103, NULL, 'NOP012', NULL), (17, '2024-08-05 12:30:00', '2024-08-05 12:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student15@example.com', '조인성', '010-5678-9013', 1, 'student15', 'password15', '2002-03-15', '남', 'Insung Jo', NULL, 40203, NULL, 'QRS345', NULL), (18, '2024-08-05 12:45:00', '2024-08-05 12:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student16@example.com', '전지현', '010-6789-0124', 1, 'student16', 'password16', '2000-04-16', '여', 'Jihyun Jun', NULL, 50201, NULL, 'TUV678', NULL), (19, '2024-08-05 13:00:00', '2024-08-05 13:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student17@example.com', '이병헌', '010-7890-1235', 1, 'student17', 'password17', '2001-05-17', '남', 'Byunghun Lee', NULL, 50202, NULL, 'WXY901', NULL), (20, '2024-08-05 13:15:00', '2024-08-05 13:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student18@example.com', '고소영', '010-8901-2346', 1, 'student18', 'password18', '200-06-18', '여', 'Soyoung Go', NULL, 50204, NULL, 'ZAB234', NULL), (21, '2024-08-05 13:30:00', '2024-08-05 13:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student19@example.com', '원빈', '010-9012-3457', 1, 'student19', 'password19', '2000-07-19', '남', 'Bin Won', NULL, 60101, NULL, 'CDE567', NULL), (22, '2024-08-05 13:45:00', '2024-08-05 13:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student20@example.com', '한가인', '010-0123-4568', 1, 'student20', 'password20', '2001-08-20', '여', 'Gain Han', NULL, 60102, NULL, 'FGH890', NULL), (23, '2024-08-05 14:00:00', '2024-08-05 14:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student21@example.com', '장동건', '010-1234-5680', 1, 'student21', 'password21', '2002-09-21', '남', 'Donggun Jang', NULL, 60103, NULL, 'IJK123', NULL), (24, '2024-08-05 14:15:00', '2024-08-05 14:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student22@example.com', '김하늘', '010-2345-6781', 1, 'student22', 'password22', '2000-10-22', '여', 'Haneul Kim', NULL, 60104, NULL, 'LMN456', NULL), (25, '2024-08-05 14:30:00', '2024-08-05 14:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student23@example.com', '조정석', '010-3456-7892', 1, 'student23', 'password23', '2001-11-23', '남', 'Jungseok Jo', NULL, 60105, NULL, 'OPQ789', NULL), (26, '2024-08-05 14:45:00', '2024-08-05 14:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student24@example.com', '수지', '010-4567-8903', 1, 'student24', 'password24', '2002-12-24', '여', 'Suzy', NULL, 60201, NULL, 'RST012', NULL), (27, '2024-08-05 15:00:00', '2024-08-05 15:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student25@example.com', '공유', '010-5678-9014', 1, 'student25', 'password25', '2000-01-25', '남', 'Gong Yoo', NULL, 60202, NULL, 'UVW345', NULL), (28, '2024-08-05 15:15:00', '2024-08-05 15:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student26@example.com', '박보영', '010-6789-0125', 1, 'student26', 'password26', '2001-02-26', '여', 'Boyoung Park', NULL, 40201, NULL, 'XYZ678', NULL), (29, '2024-08-05 15:30:00', '2024-08-05 15:30:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student27@example.com', '이승기', '010-7890-1236', 1, 'student27', 'password27', '2002-03-27', '남', 'Seunggi Lee', NULL, 40202, NULL, 'ABC901', NULL), (30, '2024-08-05 15:45:00', '2024-08-05 15:45:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student28@example.com', '문채원', '010-8901-2347', 1, 'student28', 'password28', '2000-04-28', '여', 'Chaewon Moon', NULL, 50203, NULL, 'DEF234', NULL), (31, '2024-08-05 16:00:00', '2024-08-05 16:00:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student29@example.com', '현빈', '010-9012-3458', 1, 'student29', 'password29', '2001-05-29', '남', 'Hyun Bin', NULL, 10104, NULL, 'GHI567', NULL), (32, '2024-08-05 16:15:00', '2024-08-05 16:15:00', 1, '123 # 서울시 강남구# 101호', 'ROLE_STUDENT', 'student30@example.com', '박신혜', '010-0123-4569', 1, 'student30', 'password30', '2002-06-30', '여', 'Shinhye Park', NULL, 10105, NULL, 'JKL890', NULL);

-- 선생 더미 데이터
INSERT INTO `teacher` (`tea_id`, `created_at`, `updated_at`, `accept`, `addr`, `auth`, `email`, `name`, `phone`, `state`, `uid`, `upw`, `birth`, `gender`) VALUES (3, '2024-08-01 16:42:08.420143', '2024-08-01 16:42:08.420143', 1, '12345 # 서울 판교 1234 # 101동', 'ROLE_TEACHER', 'test1111@naver.com', '선생1', '010-0000-0000', 1, 'test1234', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '2024-06-28', '남'),(5, '2024-08-01 16:42:08.420143', '2024-08-01 16:42:08.420143', 2, '12345 # 서울 판교 1234 # 101동', 'ROLE_TEACHER', 'test1112@naver.com', '선생2', '010-0000-0000', 1, 'test1112', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '2024-06-28', '남'),(6, '2024-08-01 16:42:08.420143', '2024-08-01 16:42:08.420143', 2, '12345 # 서울 판교 1234 # 101동', 'ROLE_TEACHER', 'test1113@naver.com', '선생3', '010-0000-0000', 1, 'test1113', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '2024-06-28', '남'),(7, '2024-08-01 16:42:08.420143', '2024-08-01 16:42:08.420143', 2, '12345 # 서울 판교 1234 # 101동', 'ROLE_TEACHER', 'test1114@naver.com', '선생4', '010-0000-0000', 1, 'test1114', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '2024-06-28', '남'),(8, '2024-08-01 16:42:08.420143', '2024-08-01 16:42:08.420143', 2, '12345 # 서울 판교 1234 # 101동', 'ROLE_TEACHER', 'test1115@naver.com', '선생5', '010-0000-0000', 1, 'test1115', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '2024-06-28', '남');

INSERT INTO `score` (`score_id`, `created_at`, `updated_at`, `exam`, `mark`, `name`, `semester`, `year`, `sc_id`) VALUES (83, '2024-08-02 17:48:46.625378', NULL, 1, 100, '국어', 1, '2024', 1),(84, '2024-08-02 17:48:46.625378', NULL, 1, 100, '수학', 1, '2024', 1),(85, '2024-08-02 17:48:46.625378', NULL, 1, 100, '바른 생활', 1, '2024', 1),	(86, '2024-08-02 17:48:46.625378', NULL, 1, 100, '사회/도덕', 1, '2024', 1),	(87, '2024-08-02 17:48:46.625378', NULL, 1, 0, '과학', 1, '2024', 1),	(88, '2024-08-02 17:48:46.625378', NULL, 1, 0, '영어', 1, '2024', 1),	(89, '2024-08-02 17:48:46.625378', NULL, 1, 0, '실과', 1, '2024', 1),	(90, '2024-08-02 17:48:46.625378', NULL, 1, 0, '체육', 1, '2024', 1),	(91, '2024-08-02 17:48:46.625378', NULL, 1, 0, '음악', 1, '2024', 1),	(92, '2024-08-02 17:48:46.625378', NULL, 1, 0, '미술', 1, '2024', 1),	(103, '2024-08-02 17:51:06.905800', NULL, 2, 100, '국어', 1, '2024', 1),	(104, '2024-08-02 17:51:06.905800', NULL, 2, 50, '수학', 1, '2024', 1),	(105, '2024-08-02 17:51:06.905800', NULL, 2, 50, '바른 생활', 1, '2024', 1),	(106, '2024-08-02 17:51:06.905800', NULL, 2, 50, '사회/도덕', 1, '2024', 1),	(107, '2024-08-02 17:51:06.905800', NULL, 2, 0, '과학', 1, '2024', 1),	(108, '2024-08-02 17:51:06.905800', NULL, 2, 0, '영어', 1, '2024', 1),	(109, '2024-08-02 17:51:06.905800', NULL, 2, 0, '실과', 1, '2024', 1),	(110, '2024-08-02 17:51:06.905800', NULL, 2, 0, '체육', 1, '2024', 1),	(111, '2024-08-02 17:51:06.905800', NULL, 2, 0, '음악', 1, '2024', 1),	(112, '2024-08-02 17:51:06.905800', NULL, 2, 0, '미술', 1, '2024', 1);

-- 캘린더 더미 데이터
INSERT INTO `calender` (`schedule`, `type`, `calender_id`, `name`) VALUES ('2024-08-07', 1, 1, '수요일은 다 먹는 날'),('2024-08-14', 1, 2, '수요일은 다 먹는 날'),('2024-08-21', 1, 3, '수요일은 다 먹는 날'),('2024-08-28', 1, 4, '수요일은 다 먹는 날'),('2024-07-03', 1, 5, '수요일은 다 먹는 날'),('2024-07-10', 1, 6, '수요일은 다 먹는 날'),('2024-07-17', 1, 7, '수요일은 다 먹는 날'),('2024-07-24', 1, 8, '수요일은 다 먹는 날'),('2024-07-31', 1, 9, '수요일은 다 먹는 날'),('2024-09-04', 1, 10, '수요일은 다 먹는 날'),('2024-09-11', 1, 11, '수요일은 다 먹는 날'),('2024-09-18', 1, 12, '수요일은 다 먹는 날'),('2024-09-25', 1, 13, '수요일은 다 먹는 날'),('2024-08-15', 3, 14, '광복절'),('2024-08-05', 3, 15, '개교 기념일'),('2024-08-26', 2, 16, '기말 고사');


-- JSH : 과목 DB, 세부 분류 DB, 문제 및 보기 임의 데이터
INSERT INTO `subject` (subject_name) VALUE ('국어'),('수학'),('영어');
INSERT INTO type_tag (tag_id, subject_id, type_num, tag_name) VALUE (1, 1, 11, '독해'), (2, 1, 12, '문법'), (3, 1, 13, '문학'), (4, 2, 21, '사칙연산'), (5, 2, 22, '단위환산'), (6, 2, 23, '그래프'), (7, 2, 24, '규칙찾기'), (8, 2, 25, '도형넓이 계산');

INSERT INTO haesol_online (que_id, tea_id, class_id, subject_code, `type_tag`, `que_tag`,`level`, question, contents, answer, pic, created_at, updated_at, explanation) VALUES (1, 1, 1, 1, 1, 1, 3, '(DEFAULT) 다음 중 옳은 것을 고르시오.','(DEFAULT)별 하나에 추억과 별 하나에 사랑과 별 하나에 쓸쓸함과', 1, 'DEFAULT_PICTURE_DATA','2024-07-31 16:08:28.000000', '2024-07-31 16:08:30.000000', '(DEFAULT)해설입니다. 시인은 ~입니다.'), (2, 1, 1, 2, 5, 1, 5, '(DEFAULT-객관식) 옳은 말을 하는 사람을 고르시오.', '(DEFAULT-객관식)민수: 삼각형 내각의 합은 400도야 ...', 3, 'TEST_DEFAULT_MATH_PIC1', '2024-07-31 16:12:28.000000', NULL, '(DEFAULT)해설입니다. 예리와 민희가 맞는 말을 하고 있습니다.'), (3, 1, 1, 2, 7, 2, 1, '(DEFAULT-주관식) 12345679*9=?', '(DEFAULT-주관식) 내용내용', 111111111, 'TEST_DEFAULT_MATH_PIC2', '2024-07-31 16:13:10.000000', '2024-07-31 16:13:12.000000', '(DEFAULT)해설입니다 답은 곱해보면 나옵니다');
INSERT INTO `haesol_online_multiple` (`answer_id`,`num`, `que_id`, `sentence`) VALUES (1, 1, 1, '국어 테스트문항 1번 보기입니다.'), (2, 2, 1, '국어 테스트문항 2번 보기입니다.'),(3, 3, 1, '국어 테스트문항 3번 보기입니다.'),(4, 4, 1, '국어 테스트문항 4번 보기입니다.'), (5, 5, 1, '국어 테스트문항 5번 보기입니다.'), (6, 1, 2, '민수, 영희'), (7, 2, 2, '영희, 다현'), (8, 3, 2, '다현, 민수'), (9, 4, 2, '영희'), (10, 5, 2, '민수');

INSERT INTO `online_english_word` (`grade`, `tea_id`, `word_pk`, `answer`, `word`, `pic`) VALUES (1, 1, 1, '(DEFAULT)사과', '(DEFAULT)apple', '(DEFAULT)THIS_IS_APPLE'), (1, 1, 2, '(DEFAULT)도서관', '(DEFAULT)library', '(DEFAULT)THIS_IS_LIBRARY'), (1, 1, 3, '(DEFAULT)여행', '(DEFAULT)journey', '(DEFAULT)THIS_IS_JOURNEY'), (1, 1, 4, '(DEFAULT)친구', '(DEFAULT)friend', '(DEFAULT)THIS_IS_FRIEND'), (1, 1, 5, '(DEFAULT)정원', '(DEFAULT)garden', '(DEFAULT)THIS_IS_GARDEN');

INSERT INTO `online_english_listening` (`grade`, `listening_pk`, `tea_id`, `answer`, `pic`, `question`, `sentence`) VALUES (1, 1, 1, '(DEFAULT)캠핑', '(DEFAULT)THIS_IS_CAMPING', '(DEFAULT)화자가 금요일에 할 일은 무엇인가요?', '(DEFAULT)This Friday is a holiday. My familiy make a plan for this holiday. We love camping. So, We go camping.'), (1, 2, 1, '(DEFAULT)토요일', '(DEFAULT)THIS_IS_FRIDAY', '(DEFAULT)내일은 무슨 요일인가요?', '(DEFAULT)Today is friday. we have to presentation. I’m so nervous. But, tomorrow is so expected.'), (1, 3, 1, '(DEFAULT)쇼핑센터', '(DEFAULT)THIS_IS_MUSIUM', '(DEFAULT)박물관 옆에는 무엇이 있나요?', '(DEFAULT)Come and visit the Madison Museum.It’s next to the shopping center.'), (1, 4, 1, '(DEFAULT)7시', '(DEFAULT)THIS_IS_JIHO', '(DEFAULT)화자는 몇 시에 일어나나요?', '(DEFAULT)Hello, I’m Jiho.I get up at 7 o\’clock. I go home at 4 o’clock. I go to bed at 10 o’clock.'), (1, 5, 1, '(DEFAULT)간호사', '(DEFAULT)THIS_IS_NURSE', '(DEFAULT)화자의 아버지의 직업은 무엇인가요?', '(DEFAULT)My father cares for sick people. But he isn’t doctor. Guess My father’s job.');

-- 2024-08-14 추가 선생 더미데이터
INSERT INTO `teacher` (`accept`, `birth`, `state`, `gender`, `created_at`, `tea_id`, `updated_at`, `name`, `phone`, `auth`, `uid`, `upw`, `addr`, `email`) VALUES(1, '2024-07-01', 1, '여', NOW(), 9, NULL, '선생6', '010-0000-0001', 'ROLE_TEACHER', 'test1116', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1116@naver.com'),(1, '2024-07-02', 1, '남', NOW(), 10, NULL, '선생7', '010-0000-0002', 'ROLE_TEACHER', 'test1117', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1117@naver.com'),(1, '2024-07-03', 1, '여', NOW(), 11, NULL, '선생8', '010-0000-0003', 'ROLE_TEACHER', 'test1118', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1118@naver.com'),(1, '2024-07-04', 1, '남', NOW(), 12, NULL, '선생9', '010-0000-0004', 'ROLE_TEACHER', 'test1119', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1119@naver.com'),(1, '2024-07-05', 1, '여', NOW(), 13, NULL, '선생10', '010-0000-0005', 'ROLE_TEACHER', 'test1120', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1120@naver.com');
INSERT INTO `teacher` (`accept`, `birth`, `state`, `gender`, `created_at`, `tea_id`, `updated_at`, `name`, `phone`, `auth`, `uid`, `upw`, `addr`, `email`) VALUES(1, '2024-07-06', 1, '남', NOW(), 14, NULL, '선생11', '010-0000-0006', 'ROLE_TEACHER', 'test1121', '$2a$10$n81i7ql6vle0Rc5u6p/xf.d6BHzLdI0bKsCCfFATgd31pP.yF7DfK', '123 # 대구 중구 # 101호', 'test1121@naver.com');


-- class  더미데이터
INSERT INTO `class` (`class_id`, `created_at`, `tea_id`, `updated_at`) VALUES(101, NOW(), 3, NULL),(201, NOW(), 5, NULL),(202, NOW(), 6, NULL),(303, NOW(), 7, NULL),(301, NOW(), 8, NULL),(401, NOW(), 9, NULL),(402, NOW(), 10, NULL),(501, NOW(), 11, NULL),(502, NOW(), 12, NULL),(601, NOW(), 13, NULL), (602, NOW(), 14, NULL);

-- student_class 더미데이터
INSERT INTO student_class (class_id, sc_id, created_at, stu_id, updated_at) VALUES(101, 1, '2024-08-14 12:05:08.000000', 1, NULL),(101, 2, '2024-08-14 12:05:08.000000', 2, NULL),(101, 3, '2024-08-14 12:05:08.000000', 3, NULL),(202, 4, '2024-08-14 12:05:08.000000', 4, NULL),(202, 5, '2024-08-14 12:05:08.000000', 5, NULL),(202, 6, '2024-08-14 12:05:08.000000', 6, NULL),(201, 7, '2024-08-14 12:05:08.000000', 7, NULL),(303, 8, '2024-08-14 12:05:08.000000', 8, NULL),(301, 9, '2024-08-14 12:05:08.000000', 9, NULL),(301, 10, '2024-08-14 12:05:08.000000', 10, NULL),(401, 11, '2024-08-14 12:05:08.000000', 11, NULL),(401, 12, '2024-08-14 12:05:08.000000', 12, NULL),(401, 13, '2024-08-14 12:05:08.000000', 13, NULL),(501, 14, '2024-08-14 12:05:08.000000', 14, NULL),(501, 15, '2024-08-14 12:05:08.000000', 15, NULL),(501, 16, '2024-08-14 12:05:08.000000', 16, NULL),(402, 17, '2024-08-14 12:05:08.000000', 17, NULL),(502, 18, '2024-08-14 12:05:08.000000', 18, NULL),(502, 19, '2024-08-14 12:05:08.000000', 19, NULL),(502, 20, '2024-08-14 12:05:08.000000', 20, NULL),(601, 21, '2024-08-14 12:05:08.000000', 21, NULL),(601, 22, '2024-08-14 12:05:08.000000', 22, NULL),(601, 23, '2024-08-14 12:05:08.000000', 23, NULL),(601, 24, '2024-08-14 12:05:08.000000', 24, NULL),(601, 25, '2024-08-14 12:05:08.000000', 25, NULL),(602, 26, '2024-08-14 12:05:08.000000', 26, NULL),(602, 27, '2024-08-14 12:05:08.000000', 27, NULL),(402, 28, '2024-08-14 12:05:08.000000', 28, NULL),(402, 29, '2024-08-14 12:05:08.000000', 29, NULL),(502, 30, '2024-08-14 12:05:08.000000', 30, NULL),(101, 31, '2024-08-14 12:05:08.000000', 31, NULL),(101, 32, '2024-08-14 12:05:08.000000', 32, NULL);