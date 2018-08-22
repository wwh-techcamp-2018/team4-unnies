CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `expire_date_time` datetime DEFAULT NULL,
  `is_bowl_needed` bit(1) NOT NULL,
  `max_participant` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `share_date_time` datetime DEFAULT NULL,
  `title` varchar(40) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `product_image` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_productImage_product` (`product_id`),
  CONSTRAINT `fk_productImage_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `product_product_images` (
  `product_id` bigint(20) NOT NULL,
  `product_images_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_5vj3i7hktqk0bbtsidjqpv8yo` (`product_images_id`),
  KEY `FKjcpr41wjqs2rxs1cs4460gn6h` (`product_id`),
  CONSTRAINT `FKbjsjlf3drowy1qjot4yf2ww8c` FOREIGN KEY (`product_images_id`) REFERENCES `product_image` (`id`),
  CONSTRAINT `FKjcpr41wjqs2rxs1cs4460gn6h` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `about_me` longtext,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB;