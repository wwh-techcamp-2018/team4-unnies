-- password origin : 123456a!
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(1, 'tech_syk@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '강석윤', '010-1234-2222', '경기도 용인시 수지구 죽전동 내대지마을', '나대는 사람', now());
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(2, 'tech_lhj@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '이혁진', '010-3555-2342', '광주광역시 서구 화정동','퍽하는 사람', now());
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(3, 'tech_rms@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '유민성', '010-1351-8221', '서울특별시 송파구 잠실동','클랜 장인 사람', now());
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(4, 'tech_lsy@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '임성열', '010-6148-3421', '경기도 일산시 곱창동','진정한 식애인', now());
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(5, 'tech_jiwoo@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '홍지우', '010-3175-8754', '경기도 광교신도시','애묘인', now());

INSERT INTO Category(id, name, created_at) VALUES (1, '한식', now());
INSERT INTO Category(id, name, created_at) VALUES (2, '분식', now());
INSERT INTO Category(id, name, created_at) VALUES (3, '돈까스회일식', now());
INSERT INTO Category(id, name, created_at) VALUES (4, '치킨', now());
INSERT INTO Category(id, name, created_at) VALUES (5, '피자', now());
INSERT INTO Category(id, name, created_at) VALUES (6, '중국집', now());
INSERT INTO Category(id, name, created_at) VALUES (7, '족발보쌈', now());
INSERT INTO Category(id, name, created_at) VALUES (8, '야식', now());
INSERT INTO Category(id, name, created_at) VALUES (9, '찜탕', now());
INSERT INTO Category(id, name, created_at) VALUES (10, '카페디져트', now());
INSERT INTO Category(id, name, created_at) VALUES (11, '패스트푸드', now());

-- 모집 중 샘플 데이터1 , 추가 신청할 경우 모집 완료로 변경
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(1, '석윤이가 만든 진리의 탕수육입니다. 정말 맛있어요!', '2018-08-24 17:22:20', true, 4, '진리의 탕수육', 7000, '2018-08-25 15:22:20', '존맛 탕수육 나눠먹어요~', 6, 1, now());
-- 기간만료 샘플 데이터1
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(2, '혁진이가 만든 엄청난 된장찌개입니다. 드셔보시면 본가 된장 찌개  저리가라 입니다.', '2018-08-22 15:22:20', false, 3, '퍽 된장찌개', 2500, '2018-08-23 15:22:20', '된장찌개 드시고 ... 혁진이의 퍼..ㄱ도 드세요', 1, 2, now());
-- 모집완료 샘플 데이터1
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(3, '민성이가 만든 바삭한 소리가 지구 반대편 아르헨티나까지 들린다는 천국의 수제 감자튀김입니다. 드시고 천국가시죠(?)', '2018-08-26 15:22:20', true, 2, '내꼬 감자튀김', 3000, '2018-08-27 15:22:20', '감튀는 1년 내내', 11, 3, now());
-- 모집 중 샘플 데이터2
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(4, '성열이가 만든 별미 곱창 사장님이 레시피 배우려고 뛰어오신다는 환상의 곱창볶음입니다. 당신의 술자리는 이제부터 성열이의 곱창 외에는 없습니다.', '2018-08-26 15:22:20', false, 3, '야식은 곱창', 10000, '2018-08-27 15:22:20', '내 곱창 진리 곱창', 8, 4, now());
-- 기간만료 샘플 데이터2
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(5, '지우가 만든 ... 사실 누님이 뭘 좋아하시는지 잘 몰겠습니다. 어서 알려주시죠', '2018-08-23 14:22:20', true, 3, '홍T의 음식', 0, '2018-08-24 15:22:20', '팀장은 홍T', 10, 5, now());
-- 모집완료 샘플 데이터2
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(6, '석윤이가 두 번째로 1만년 지하수보다 더 깊은 국물 맛을 내는 오뎅탕입니다. 소주 한짝은 눈감고도 비우고 다음날 전혀 무리가 없즈오! ', '2018-08-26 15:22:20', false, 1, '국물의 왕 오뎅탕', 5000, '2018-08-27 15:22:20', '오뎅탕 호로록', 8, 1, now());

-- 1번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(1, 1, 3, 'BAEMIN_RIDER', 'ON_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(2, 1, 4, 'PICKUP', 'COMPLETE_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(3, 1, 5, 'BAEMIN_RIDER', 'ON_SHARING', now());

-- 2번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(4, 2, 1, 'BAEMIN_RIDER', 'COMPLETE_SHARING', now());

-- 3번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(5, 3, 1, 'PICKUP', 'ON_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(6, 3, 2, 'BAEMIN_RIDER', 'ON_SHARING', now());

-- 4번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(7, 4, 2, 'PICKUP', 'ON_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(8, 4, 3, 'BAEMIN_RIDER', 'ON_SHARING', now());

-- 5번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(9, 5, 2, 'PICKUP', 'ON_SHARING', now());

-- 6번 음식
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(10, 6, 2, 'PICKUP', 'COMPLETE_SHARING', now());

INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(1, 1, 1, 4,'comment1',3, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(2, 2, 2, 1, 'comment2',4, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(3, 1, 1, 3, 'comment3',2, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(4, 1, 1, 5, 'comment4',2, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(5, 1, 1, 5, 'comment5',5, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(6, 1, 1, 2, 'comment6',3, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(7, 1, 1, 2,'comment7',2, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(8, 1, 1, 5,'comment8',3, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(9, 1, 1, 3,'comment9',4, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(10, 1, 1, 2,'comment10',5, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(11, 2, 2, 'comment11',1, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(12, 3, 3, 'comment12',2, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(13, 4, 4, 'comment13',3, now());
INSERT INTO Review(id, product_id, chef_id, writer_id, comment, rating, created_at) VALUES(14, 5, 5, 'comment14',4, now());