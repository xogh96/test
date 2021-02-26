-- 사용자 
-- user //유저권한 
-- admin //admin권한
-- 비밀번호 123 공통 

TRUNCATE TABLE tb_user;

INSERT INTO `tb_user` (`user_seq`, `user_id`, `user_pw`) VALUES
	(1, 'admin', '$2a$10$Velg.T0pDp/1LryJLeN3Y.//8ek2lyQNeeLxKhYbTUGsByy/FEQ4u');
	
INSERT INTO `tb_user` (`user_seq`, `user_id`, `user_pw`) VALUES
	(2, 'user', '$2a$10$KZtepHV18.kLvm1gG6qfHuT/7WD6ag0fIY/xNyHAaXU/nWYfEDykq');