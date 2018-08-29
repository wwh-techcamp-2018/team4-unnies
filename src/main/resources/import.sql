-- password origin : 123456a!
INSERT INTO user (id, email, password, name, phone_number, address, address_detail, about_me)
VALUES
(1, 'tech_syk@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '강석윤', '010-1234-2222', '경기도 용인시 수지구 죽전동 내대지마을', '힐스테이트 1603동 701호', '나대는 사람'),
(2, 'tech_lhj@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '이혁진', '010-3555-2342', '광주광역시 서구 화정동', '된장찌개동 1603동 701호', '퍽하는 사람'),
(3, 'tech_rms@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '유민성', '010-1351-8221', '서울특별시 송파구 잠실동', '감자튀김동 1603동 701호', '클랜 장인 사람'),
(4, 'tech_lsy@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '임성열', '010-6148-3421', '경기도 일산시 곱창동', '곱창동 1603동 701호', '진정한 식애인'),
(5, 'tech_jiwoo@woowahan.com', '$2a$10$VWzPAYMsYevYEppl.gXvgewcl4fVPpIuBVrtPENSg1I4bu5.Nfzxu', '홍지우', '010-3175-8754', '경기도 광교신도시', '수제초콜릿동 1603동 701호', '애묘인');

INSERT INTO category (id, name)
VALUES
(1, '밑반찬'),
(2, '국·찌개'),
(3, '메인반찬'),
(4, '아이반찬'),
(5, '정기식단'),
(6, '간편식'),
(7, '간식');

INSERT INTO product (id, owner_id, category_id, name, title, price, description, address, address_detail, latitude, longitude, max_participant, expire_date_time, share_date_time, is_bowl_needed)
VALUES
(1, 1, 1, '진리의 탕수육', '석윤이가 만든 진리의 탕수육입니다. 정말 맛있어요!', 7000, '존맛 탕수육 나눠먹어요~', '서울시 어딘가', '내 집', 0.00, 0.00, 4, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) + interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) + interval 2 hour, true),
(2, 2, 2, '퍽 된장찌개', '혁진이가 만든 엄청난 된장찌개입니다. 드셔보시면 본가 된장 찌개 저리가라 입니다.', 8000, '된장찌개 드시고 ... 혁진이의 퍼..ㄱ도 드세요', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true),
(3, 3, 3, '내꼬 감자튀김', '민성이가 만든 바삭한 소리가 지구 반대편 아르헨티나까지 들린다는 천국의 수제 감자튀김입니다. 드시고 천국가시죠(?)', 9000, '감튀는 1년 내내', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true),
(4, 4, 4, '야식은 곱창', '성열이가 만든 별미 곱창 사장님이 레시피 배우려고 뛰어오신다는 환상의 곱창볶음입니다. 당신의 술자리는 이제부터 성열이의 곱창 외에는 없습니다.', 9000, '내 곱창 진리 곱창', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true),
(5, 5, 5, '찰리와 촥컬릿 공장', '지우가 만든 장맛보다 찐한 수제 초콜릿입니다. 손맛 좀 보시죠. 쿨럭쿨럭', 9000, '팀장은 홍T', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true),
(6, 1, 6, '국물의 왕 오뎅탕', '석윤이가 두 번째로 1만년 지하수보다 더 깊은 국물 맛을 내는 오뎅탕입니다. 소주 한짝은 눈감고도 비우고 다음날 전혀 무리가 없즈오! ', 9000, '오뎅탕 호로록', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true);

INSERT INTO order_product (id, product_id, participant_id, delivery_type, share_status)
VALUES (1, 1, 3, 'BAEMIN_RIDER', 'COMPLETE_SHARING'),
(2, 1, 4, 'PICKUP', 'COMPLETE_SHARING'),
(3, 1, 5, 'BAEMIN_RIDER', 'ON_SHARING'),
(4, 2, 1, 'BAEMIN_RIDER', 'COMPLETE_SHARING'),
(5, 3, 1, 'PICKUP', 'ON_SHARING'),
(6, 3, 2, 'BAEMIN_RIDER', 'ON_SHARING'),
(7, 4, 2, 'PICKUP', 'ON_SHARING'),
(8, 4, 3, 'BAEMIN_RIDER', 'ON_SHARING'),
(9, 5, 2, 'PICKUP', 'ON_SHARING'),
(10, 6, 2, 'PICKUP', 'COMPLETE_SHARING');

INSERT INTO review (id, product_id, writer_id, chef_id, comment, rating)
VALUES
(1, 1, 3, 1, '성열 답글', 3),
(2, 2, 3, 2, '성열 답글', 4);