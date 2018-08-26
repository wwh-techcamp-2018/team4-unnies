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
(6, 1, 6, '국물의 왕 오뎅탕', '석윤이가 두 번째로 1만년 지하수보다 더 깊은 국물 맛을 내는 오뎅탕입니다. 소주 한짝은 눈감고도 비우고 다음날 전혀 무리가 없즈오! ', 9000, '오뎅탕 호로록', '경기도 어딘가', '네 집', 0.00, 0.00, 5, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 2 hour, from_unixtime(ceil(unix_timestamp(now()) / 600) * 600) - interval 1 hour, true),
(7, 1, 3, '자장면은 진리', '맛있는 자장면.', 4000, '같이 먹어요!', '서울 송파구 올림픽로35길 104', '장미1차아파트 1동 101호', 37.5200607, 127.10383300000001, 5, '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(8, 2, 2, '환상의 김치찌개.', '맛나는 김치찌개', 6000, '같이 먹어요!', '서울 송파구 올림픽로35길 94', '장미3차맨션 202호', 37.5184863, 127.10398179999993, 3, '2018-09-01 15:22:20', '2018-09-02 15:22:20', true),
(9, 3, 6, '맛있는 새우튀김.', '새우튀김은 진리', 5000, '같이 먹어요!', '서울 송파구 올림픽로32길 39-27', '그린파크빌아파트 303호', 37.5139502,127.11129389999996, 4, '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(10, 4, 5, '환상의 대창.', '맛깔난 대창', 9000, '서울 송파구 올림픽로4길 15', '아시아선수촌아파트', 37.5074631, 127.07658820000006, 3, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', true),
(11, 5, 7, '맛있는 눈깔사탕.', '추억의 눈깔사탕', 1000, '서울 송파구 올림픽로 289', '잠실시그마타워 404호', 37.5145667, 127.10249279999994, 4, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(12, 1, 1, '알탕은 진리', '환상의 알탕.', 3000, '서울 송파구 송파대로48길 29', '호수임광아파트 102동 606호', 37.5102862, 127.10730719999992, 5, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(13, 2, 3, '맛있는 짬뽕.', '짬뽕은 진리', 4000, '서울 송파구 강동대로9길 8', '극동아파트 507동 707호', 37.5265521, 127.11666209999999, 3, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(14, 3, 2, '맛나는 오모리찌개', '환상의 오모리찌개.', 6000, '서울 송파구 신천동 17-10', '하늘가람근린공원', 37.5211214, 127.10945879999997, 4, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(15, 4, 6, '맛있는 고구마튀김.', '고구마튀김은 진리', 5000, '서울 송파구 석촌호수로 230', '미켈란호수가아파트 909호', 37.5073341, 127.10227970000005, 5, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(16, 4, 5, '맛깔난 막창', '환상의 막창.', 9000, '서울 송파구 토성로 38-6', '한강극동아파트 108동 505호', 37.5292392, 127.11284249999994, 3, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(17, 5, 7, '고오급진 홍차쿠키', '맛있는 홍차쿠키.', 1000, '서울 송파구 백제고분로41길 20', '기린유치원', 37.5077265, 127.10756949999995, 4, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(18, 1, 1, '해물탕은 진리', '환상의 해물탕.', 1000, '서울 송파구 한가람로 448', '서울 송파구 한가람로 448 1101호', 37.5371027, 127.11561200000006, 5, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(19, 2, 3, '진리의 볶음밥', '맛있는 볶음밥.', 4000, '서울 강동구 풍성로 127', '성내삼성아파트 1010호', 37.5318427, 127.12618180000004, 3, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(20, 3, 2, '맛나는 부대찌개', '환상의 부대찌개.', 6000, '서울 광진구 광나루로56길 29', '현대프라임아파트 1201호', 37.5373373, 127.09767239999996, 4, '같이 먹어요!', '2018-09-01 15:22:20', '2018-09-02 15:22:20', false),
(21, 4, 6, '맛있는 김말이튀김.', '김말이튀김은 진리', 5000, '서울 광진구 뚝섬로52마길 70', '자양삼성아파트 1301호', 37.5285662, 127.08220000000006, 3, '같이 먹어요!' '2018-09-01 15:22:20', '2018-09-02 15:22:20', false);

INSERT INTO product_image (id, url)
VALUES
(1, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(2, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(3, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(4, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(5, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(6, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(7, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(8, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(9, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(10, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(11, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(12, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(13, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(14, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(15, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(16, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(17, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(18, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(19, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(20, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg'),
(21, 'https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg');

INSERT INTO product_product_images(product_id, product_images_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 17),
(18, 18),
(19, 19),
(20, 20),
(21, 21);

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