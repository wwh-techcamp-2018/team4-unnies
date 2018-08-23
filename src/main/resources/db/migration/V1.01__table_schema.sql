SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `user` ADD COLUMN `created_at` datetime NOT NULL;
ALTER TABLE `user` ADD COLUMN `updated_at` datetime DEFAULT NULL;

ALTER TABLE `category` ADD COLUMN `created_at` datetime NOT NULL;
ALTER TABLE `category` ADD COLUMN `updated_at` datetime DEFAULT NULL;

CREATE TABLE `location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(50) NOT NULL,
  `address_detail` varchar(50) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE `product` MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT;
ALTER TABLE `product` MODIFY COLUMN `description` longtext;
ALTER TABLE `product` ADD COLUMN `location_id` bigint(20) DEFAULT NULL;
ALTER TABLE `product` ADD COLUMN `owner_id` bigint(20) DEFAULT NULL;
ALTER TABLE `product` ADD KEY `FKp131h1ob6trty73by707lexmx` (`location_id`);
ALTER TABLE `product` ADD KEY `fk_product_owner` (`owner_id`);
ALTER TABLE `product` ADD CONSTRAINT `FKp131h1ob6trty73by707lexmx` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`);
ALTER TABLE `product` ADD CONSTRAINT `fk_product_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

ALTER TABLE `product_image` MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT;

CREATE TABLE `order_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delivery_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `participant_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_participant` (`participant_id`),
  KEY `fk_order_product` (`product_id`),
  CONSTRAINT `fk_order_participant` FOREIGN KEY (`participant_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_order_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `comment` varchar(255) NOT NULL,
  `rating` double NOT NULL,
  `chef_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `writer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_review_chef` (`chef_id`),
  KEY `fk_review_product` (`product_id`),
  KEY `fk_review_writer` (`writer_id`),
  CONSTRAINT `fk_review_chef` FOREIGN KEY (`chef_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_review_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_review_writer` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;