-- 국어 테스트 문항
INSERT INTO `online_korean` (`answer`, `created_at`, `que_id`, `updated_at`, `question`) VALUES (1, '2024-07-31 16:08:28.000000', 1, '2024-07-31 16:08:30.000000', '(국어 테스트 문항) 다음 중 옳은 것을 고르시오.');
INSERT INTO `online_korean_multiple` (`num`, `answer_id`, `que_id`, `sentence`) VALUES (1, 1, 1, '국어 테스트문항 1번 보기입니다.'),(2, 2, 1, '국어 테스트문항 2번 보기입니다.'), (3, 3, 1, '국어 테스트문항 3번 보기입니다.'), (4, 4, 1, '국어 테스트문항 4번 보기입니다.'), (5, 5, 1, '국어 테스트문항 5번 보기입니다.');
INSERT INTO `online_korean_pics` (`created_at`, `pic_id`, `que_id`, `pic`) VALUES ('2024-07-31 16:11:25.000000', 1, 1, 'KOREAN_EXAMPLE_PICTURE');
-- 수학 테스트 문항
INSERT INTO `online_math` (`answer`, `created_at`, `que_id`, `updated_at`, `question`) VALUES (3, '2024-07-31 16:12:28.000000', 1, NULL, '(수학 객관식 테스트 문항) 삼각형의 내각의 합으로 옳은 것을 고르시오.'), (111111111, '2024-07-31 16:13:10.000000', 2, '2024-07-31 16:13:12.000000', '(수학 주관식 테스트 문항) 12345679*9=?');
INSERT INTO `online_math_multiple` (`num`, `answer_id`, `que_id`, `sentence`) VALUES (1, 1, 1, '10'), (2, 2, 1, '20'), (3, 3, 1, '180'), (4, 4, 1, '40'), (5, 5, 1, '50'), (111111111, 6, 2, '');
INSERT INTO `online_math_pics` (`created_at`, `pic_id`, `que_id`, `pic`) VALUES ('2024-07-31 16:17:53.000000', 1, 1, 'MATH_EXAMPLE_PICTURE'), ('2024-07-31 16:26:12.000000', 2, 2, 'NO_MORE_MATH');

INSERT INTO `teacher` (`accept`, `birth`, `state`, `gender`, `created_at`, `tea_id`, `updated_at`, `name`, `phone`, `auth`, `uid`, `upw`, `addr`, `email`) VALUES	(1, '2024-07-31', 1, NULL, '2024-07-31 15:30:54.000000', 1, '2024-07-31 15:30:55.000000', '김철', '010-000-0000', 'ROLE_TEACHER', 'rlacjf111', '$2a$10$K/DxoACUp2sB/nZGq02yseVoKRaFUQbzpfp/Yyst33IoO1KLP7.L.', NULL, 'rlacjf111@naver.com');

INSERT INTO `class` (`class_id`, `created_at`, `updated_at`, `tea_id`) VALUES (101, '2024-08-01 09:35:37.000000', '2024-08-01 09:35:38.000000', 1);

INSERT INTO `student` (`accept`, `birth`, `grade`, `state`, `gender`, `created_at`, `parent_id`, `stu_id`, `updated_at`, `name`, `phone`, `auth`, `eng_name`, `uid`, `upw`, `addr`, `etc`, `email`, `pic`, `rand_code`) VALUES (2, '2024-08-01', 10101, 1, '남', '2024-08-01 09:37:33.000000', NULL, 1, '2024-08-01 09:37:39.000000', '홍길동', '010-3453-4345', 'ROLE_STUDENT', NULL, 'ghdrlfehd111', NULL, NULL, NULL, 'rlfehd@naver.com', NULL, '123412');

INSERT INTO `student_class` (`sc_id`, `created_at`, `updated_at`, `class_id`, `stu_id`) VALUES (1, '2024-08-01 09:38:54.000000', '2024-08-01 09:38:55.000000', 101, 1);

INSERT INTO `parents` (`accept`, `state`, `created_at`, `parents_id`, `updated_at`, `connect`, `name`, `phone`, `sub_phone`, `auth`, `uid`, `upw`, `addr`, `email`) VALUES (1, 1, '2024-08-01 15:44:59.162901', 1, '2024-08-01 15:44:59.162901', '부', '홍길동', '010-4353-1256', NULL, 'ROLE_PARENTS', 'parent1', '$2a$10$bVBBIiXewDQOCGLu/CavBuC21HgAsf5a28sf5P1e1wYjIvk0wjfga', NULL, 'string@gmailc.com');