-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema socialdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `socialdb` ;

-- -----------------------------------------------------
-- Schema socialdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `socialdb` DEFAULT CHARACTER SET utf8 ;
USE `socialdb` ;

-- -----------------------------------------------------
-- Table `page_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `page_user` ;

CREATE TABLE IF NOT EXISTS `page_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `biography` VARCHAR(150) NULL,
  `picture_url` VARCHAR(150) NULL,
  `create_date` DATETIME NOT NULL,
  `last_update` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `followed_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `followed_user` ;

CREATE TABLE IF NOT EXISTS `followed_user` (
  `user_id` INT NOT NULL,
  `followed_user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `followed_user_id`),
  INDEX `fk_followed_user_page_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_followed_user_page_user2_idx` (`followed_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_followed_user_page_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `page_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_followed_user_page_user2`
    FOREIGN KEY (`followed_user_id`)
    REFERENCES `page_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post` ;

CREATE TABLE IF NOT EXISTS `post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `image_url` VARCHAR(200) NULL,
  `create_date` DATETIME NOT NULL,
  `last_update` DATETIME NOT NULL,
  `page_user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_page_user1_idx` (`page_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_page_user1`
    FOREIGN KEY (`page_user_id`)
    REFERENCES `page_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_comment` ;

CREATE TABLE IF NOT EXISTS `post_comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comments` TEXT NULL,
  `create_date` DATETIME NOT NULL,
  `last_update` DATETIME NOT NULL,
  `post_id` INT NOT NULL,
  `page_user_id` INT NOT NULL,
  `parent_comment_id` INT NULL,
  INDEX `fk_post_comment_post1_idx` (`post_id` ASC) VISIBLE,
  INDEX `fk_post_comment_page_user1_idx` (`page_user_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_post_comment_post_comment1_idx` (`parent_comment_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comment_page_user1`
    FOREIGN KEY (`page_user_id`)
    REFERENCES `page_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comment_post_comment1`
    FOREIGN KEY (`parent_comment_id`)
    REFERENCES `post_comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(55) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post_has_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_has_category` ;

CREATE TABLE IF NOT EXISTS `post_has_category` (
  `category_id` INT NOT NULL,
  `post_id` INT NOT NULL,
  PRIMARY KEY (`category_id`, `post_id`),
  INDEX `fk_post_has_category_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_post_has_category_post1_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `fk_post_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_has_category_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS pager@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'pager'@'localhost' IDENTIFIED BY 'P@gerBleepBleep343.';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'pager'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `page_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `page_user` (`id`, `name`, `email`, `password`, `biography`, `picture_url`, `create_date`, `last_update`) VALUES (1, 'admin', 'admin@dinglebutt.com', 'Admin123.', NULL, NULL, '2025-01-26', '2025-01-26');
INSERT INTO `page_user` (`id`, `name`, `email`, `password`, `biography`, `picture_url`, `create_date`, `last_update`) VALUES (2, 'bingus', 'bingus@crumbus.com', 'beepbeep1', NULL, NULL, '2025-01-27', '2025-01-27');
INSERT INTO `page_user` (`id`, `name`, `email`, `password`, `biography`, `picture_url`, `create_date`, `last_update`) VALUES (3, 'blipnar', 'blipnar@crumbus.com', 'blipnarBest', NULL, NULL, '2025-01-27', '2025-01-27');

COMMIT;


-- -----------------------------------------------------
-- Data for table `followed_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `followed_user` (`user_id`, `followed_user_id`) VALUES (1, 2);
INSERT INTO `followed_user` (`user_id`, `followed_user_id`) VALUES (2, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `post`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `post` (`id`, `title`, `description`, `image_url`, `create_date`, `last_update`, `page_user_id`) VALUES (1, 'Photos Printed?', 'Hey, just wondering if you got your photos printed?', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTi9Hfc1iTzPNdEYFOmp-FkD2L955QVCIKJbUmiHyi-ocBSW7be_Kr4bJZF_GhHRxJ7Bdc&usqp=CAU', '2025-01-26', '2025-01-26', 1);
INSERT INTO `post` (`id`, `title`, `description`, `image_url`, `create_date`, `last_update`, `page_user_id`) VALUES (2, 'Why did it come out Bad?', 'I made from alfredo and I substituted the sauce with chocolate syrup and cheese flavoring. Why does it taste bad???', NULL, '2025-01-27', '2025-01-27', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `post_comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `post_comment` (`id`, `comments`, `create_date`, `last_update`, `post_id`, `page_user_id`, `parent_comment_id`) VALUES (1, 'Bogos Binted?', '2025-01-26', '2025-01-26', 1, 2, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `category` (`id`, `type`) VALUES (1, 'photos');
INSERT INTO `category` (`id`, `type`) VALUES (2, 'food');
INSERT INTO `category` (`id`, `type`) VALUES (3, 'comedy');
INSERT INTO `category` (`id`, `type`) VALUES (4, 'outdoors');
INSERT INTO `category` (`id`, `type`) VALUES (5, 'poetry');
INSERT INTO `category` (`id`, `type`) VALUES (6, 'music');
INSERT INTO `category` (`id`, `type`) VALUES (7, 'cars');
INSERT INTO `category` (`id`, `type`) VALUES (8, 'technology');
INSERT INTO `category` (`id`, `type`) VALUES (9, 'beauty');
INSERT INTO `category` (`id`, `type`) VALUES (10, 'misc');

COMMIT;


-- -----------------------------------------------------
-- Data for table `post_has_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `socialdb`;
INSERT INTO `post_has_category` (`category_id`, `post_id`) VALUES (1, 1);
INSERT INTO `post_has_category` (`category_id`, `post_id`) VALUES (2, 2);

COMMIT;

