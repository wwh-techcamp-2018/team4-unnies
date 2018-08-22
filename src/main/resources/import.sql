-- password origin : 123456a!
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(1, 'unnies@naver.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '강석윤', '010-1111-2222', '서울특별시 배민동 배민아파트', null, now());
insert into user(id, email, password, name, phone_number, address, about_me, created_at) values(2, 'unnies2@naver.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '이혁진', '010-1111-2222', '서울특별시 배민동 배민아파트2',null, now());

INSERT INTO Category(id, name, created_at) VALUES (1, '한식', now());
INSERT INTO Category(id, name, created_at) VALUES (2, '분신', now());
INSERT INTO Category(id, name, created_at) VALUES (3, '돈까스회일식', now());
INSERT INTO Category(id, name, created_at) VALUES (4, '치킨', now());
INSERT INTO Category(id, name, created_at) VALUES (5, '피자', now());
INSERT INTO Category(id, name, created_at) VALUES (6, '중국집', now());
INSERT INTO Category(id, name, created_at) VALUES (7, '족발보쌈', now());
INSERT INTO Category(id, name, created_at) VALUES (8, '야식', now());
INSERT INTO Category(id, name, created_at) VALUES (9, '찜탕', now());
INSERT INTO Category(id, name, created_at) VALUES (10, '카페디져트', now());
INSERT INTO Category(id, name, created_at) VALUES (11, '패스트푸드', now());

INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(1, 'description', '2018-08-23 15:22:20', false, 2, 'name', 1000, '2018-08-24 15:22:20', 'title', 1, 1, now());
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(2, 'description2', '2018-08-23 15:22:20', false, 2, 'name2', 2000, '2018-08-24 15:22:20', 'title2', 2, 2, now());
INSERT INTO PRODUCT(id, description, expire_date_time, is_bowl_needed, max_participant, name, price, share_date_time, title, category_id, owner_id, created_at) VALUES(3, 'description3', '2018-08-23 15:22:20', false, 3, 'name3', 3000, '2018-08-24 15:22:20', 'title2', 3, 1, now());

INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(1, 1, 2, 'BAEMIN_RIDER', 'ON_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(2, 2, 1, 'BAEMIN_RIDER', 'COMPLETE_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(3, 3, 2, 'BAEMIN_RIDER', 'ON_SHARING', now());
INSERT INTO order_product(id, product_id, participant_id, delivery_type, status, created_at) VALUES(4, 1, 1, 'BAEMIN_RIDER', 'ON_SHARING', now());

INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(1, 1, 1, 'comment1',3, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(2, 1, 2, 'comment2',4, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(3, 2, 1, 'comment3',2, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(4, 1, 1, 'comment4',2, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(5, 2, 1, 'comment5',5, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(6, 1, 1, 'comment6',3, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(7, 1, 1, 'comment7',2, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(8, 1, 1, 'comment8',3, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(9, 2, 1, 'comment9',4, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(10, 1, 1, 'comment10',5, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(11, 1, 1, 'comment11',1, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(12, 1, 1, 'comment12',2, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(13, 2, 1, 'comment13',4, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(14, 1, 1, 'comment14',5, now());
INSERT INTO Review(id, product_id, writer_id, comment, rating, created_at) VALUES(15, 1, 2, 'comment15',2, now());

