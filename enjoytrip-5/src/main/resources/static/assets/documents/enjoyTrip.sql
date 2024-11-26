-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL,
  `sido_name` VARCHAR(20) NULL,
  PRIMARY KEY (`no`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `gugun_name` VARCHAR(20) NULL,
  `gugun_code` INT NULL,
  `sidos_no` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_guguns_sidos1_idx` (`sidos_no` ASC) VISIBLE,
  CONSTRAINT `fk_guguns_sidos1`
    FOREIGN KEY (`sidos_no`)
    REFERENCES `mydb`.`sidos` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`attractions` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL,
  `title` VARCHAR(500) NULL,
  `first_image1` VARCHAR(100) NULL,
  `first_image2` VARCHAR(100) NULL,
  `map_level` INT NULL,
  `latitude` DECIMAL(20,17) NULL,
  `longitude` DECIMAL(20,17) NULL,
  `tel` VARCHAR(20) NULL,
  `addr1` VARCHAR(100) NULL,
  `add2` VARCHAR(100) NULL,
  `homepage` VARCHAR(1000) NULL,
  `overview` VARCHAR(10000) NULL,
  `content_type_id` INT NOT NULL,
  `guguns_no` INT NOT NULL,
  `area_code` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_attractions_contenttypes1_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `fk_attractions_guguns1_idx` (`guguns_no` ASC) VISIBLE,
  INDEX `fk_attractions_sidos1_idx` (`area_code` ASC) VISIBLE,
  CONSTRAINT `fk_attractions_contenttypes1`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `mydb`.`contenttypes` (`content_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attractions_guguns1`
    FOREIGN KEY (`guguns_no`)
    REFERENCES `mydb`.`guguns` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attractions_sidos1`
    FOREIGN KEY (`area_code`)
    REFERENCES `mydb`.`sidos` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` VARCHAR(20) NOT NULL,
  `user_pwd` VARCHAR(60) NOT NULL,
  `user_name` VARCHAR(10) NOT NULL,
  `user_email` VARCHAR(45) NULL,
  `created_at` TIMESTAMP NULL DEFAULT current_timestamp,
  `is_withdraw` TINYINT NULL DEFAULT 0,
  `role` TINYINT NULL DEFAULT 0,
  `token` VARCHAR(255) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board_type` (
  `board_id` INT NOT NULL,
  `board_subject` VARCHAR(10) NULL,
  PRIMARY KEY (`board_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board` (
  `board_id` INT NOT NULL,
  `board_type_id` INT NOT NULL,
  `content` VARCHAR(300) NOT NULL,
  `view` INT NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `attraction_url` VARCHAR(100) NULL,
  `attraction_name` VARCHAR(45) NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`board_id`),
  INDEX `fk_board_board_type1_idx` (`board_type_id` ASC) VISIBLE,
  INDEX `fk_board_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_board_type1`
    FOREIGN KEY (`board_type_id`)
    REFERENCES `mydb`.`board_type` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board_comment` (
  `board_comment_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `content` VARCHAR(100) NULL,
  `board_id` INT NOT NULL,
  `created_at` TIMESTAMP NULL,
  PRIMARY KEY (`board_comment_id`),
  INDEX `fk_board_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_board_comment_board1_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_comment_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `mydb`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`board_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`board_file` (
  `file_id` INT NOT NULL AUTO_INCREMENT,
  `file_url` VARCHAR(100) NOT NULL,
  `board_id` INT NOT NULL,
  PRIMARY KEY (`file_id`),
  INDEX `fk_file_board1_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_file_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `mydb`.`board` (`board_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `sido_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`trip_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`trip_plan` (
  `trip_plan_id` INT NOT NULL AUTO_INCREMENT,
  `trip_name` VARCHAR(20) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  `updated_at` TIMESTAMP NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `sidos_no` INT NOT NULL,
  PRIMARY KEY (`trip_plan_id`),
  INDEX `fk_trip_plan_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_trip_plan_sidos1_idx` (`sidos_no` ASC) VISIBLE,
  CONSTRAINT `fk_trip_plan_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trip_plan_sidos1`
    FOREIGN KEY (`sidos_no`)
    REFERENCES `test`.`sidos` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`day_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`day_plan` (
  `day_plan_id` INT NOT NULL AUTO_INCREMENT,
  `day_number` INT NOT NULL,
  `date` DATE NULL,
  `trip_plan_id` INT NOT NULL,
  PRIMARY KEY (`day_plan_id`),
  INDEX `fk_day_plan_trip_plan1_idx` (`trip_plan_id` ASC) VISIBLE,
  CONSTRAINT `fk_day_plan_trip_plan1`
    FOREIGN KEY (`trip_plan_id`)
    REFERENCES `mydb`.`trip_plan` (`trip_plan_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`timestamps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);


-- -----------------------------------------------------
-- Table `mydb`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`video` (
  `video_id` INT NOT NULL AUTO_INCREMENT,
  `video_title` VARCHAR(20) NOT NULL,
  `video_url` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `sidos_no` INT NOT NULL,
  PRIMARY KEY (`video_id`),
  INDEX `fk_video_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_video_sidos1_idx` (`sidos_no` ASC) VISIBLE,
  CONSTRAINT `fk_video_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_video_sidos1`
    FOREIGN KEY (`sidos_no`)
    REFERENCES `test`.`sidos` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `test`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 235
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `test`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `test`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`attractions` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(20) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(1000) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `test`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `test`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `test`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56644
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mydb`.`popular`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`popular` (
  `popular_id` INT NOT NULL AUTO_INCREMENT,
  `count` INT NULL,
  `attractions_no` INT NOT NULL,
  PRIMARY KEY (`popular_id`),
  INDEX `fk_popular_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_popular_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `test`.`attractions` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`favorite` (
  `favorite_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `attractions_no` INT NOT NULL,
  PRIMARY KEY (`favorite_id`),
  INDEX `fk_favorite_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_favorite_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_favorite_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favorite_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `test`.`attractions` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `ssafytrip` ;

-- -----------------------------------------------------
-- Table `ssafytrip`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `sido_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 235
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`attractions` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(20) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(1000) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `ssafytrip`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `ssafytrip`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56644
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gender` INT NOT NULL,
  `age` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `author` VARCHAR(16) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT NOT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  INDEX `fk_board_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`b_bookmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`b_bookmark` (
  `bbm_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `board_id` INT NOT NULL,
  PRIMARY KEY (`bbm_id`),
  INDEX `fk_b_bookmark_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_b_bookmark_board1_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_b_bookmark_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `ssafytrip`.`board` (`board_id`),
  CONSTRAINT `fk_b_bookmark_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`b_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`b_comment` (
  `bc_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `board_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`bc_id`),
  INDEX `fk_b_comment_board1_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_b_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_b_comment_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `ssafytrip`.`board` (`board_id`),
  CONSTRAINT `fk_b_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`tourplan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`tourplan` (
  `plan_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `plan_title` VARCHAR(10) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`plan_id`),
  INDEX `fk_tourplan_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_tourplan_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`plan_attraction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`plan_attraction` (
  `pa_id` INT NOT NULL AUTO_INCREMENT,
  `attractions_no` INT NOT NULL,
  `plan_id` INT NOT NULL,
  PRIMARY KEY (`pa_id`),
  INDEX `fk_plan_attraction_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  INDEX `fk_plan_attraction_tourplan1_idx` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `fk_plan_attraction_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `ssafytrip`.`attractions` (`no`),
  CONSTRAINT `fk_plan_attraction_tourplan1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `ssafytrip`.`tourplan` (`plan_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`t_bookmark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`t_bookmark` (
  `tbm_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `plan_id` INT NOT NULL,
  PRIMARY KEY (`tbm_id`),
  INDEX `fk_b_bookmark_copy1_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_b_bookmark_copy1_tourplan1_idx` (`plan_id` ASC) VISIBLE,
  CONSTRAINT `fk_b_bookmark_copy1_tourplan1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `ssafytrip`.`tourplan` (`plan_id`),
  CONSTRAINT `fk_b_bookmark_copy1_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`t_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`t_comment` (
  `tc_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `plan_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`tc_id`),
  INDEX `fk_t_comment_tourplan1_idx` (`plan_id` ASC) VISIBLE,
  INDEX `fk_t_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_t_comment_tourplan1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `ssafytrip`.`tourplan` (`plan_id`),
  CONSTRAINT `fk_t_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `test` ;

-- -----------------------------------------------------
-- Table `test`.`day_plan_attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`day_plan_attractions` (
  `attractions_no` INT NOT NULL,
  `plan_day_id` INT NOT NULL,
  `visit_order` INT NULL,
  PRIMARY KEY (`attractions_no`, `plan_day_id`),
  INDEX `fk_attractions_has_day_plan_day_plan1_idx` (`plan_day_id` ASC) VISIBLE,
  INDEX `fk_attractions_has_day_plan_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_attractions_has_day_plan_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `test`.`attractions` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attractions_has_day_plan_day_plan1`
    FOREIGN KEY (`plan_day_id`)
    REFERENCES `mydb`.`day_plan` (`day_plan_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `test`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`favorite` (
  `attractions_no` INT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT current_timestamp,
  PRIMARY KEY (`attractions_no`, `user_id`),
  INDEX `fk_attractions_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_attractions_has_user_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_attractions_has_user_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `test`.`attractions` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attractions_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sidos` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `sido_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`guguns` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sidos` (`sido_code`))
ENGINE = InnoDB
AUTO_INCREMENT = 235
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`contenttypes` (
  `content_type_id` INT NOT NULL,
  `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attractions` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `area_code` INT NULL DEFAULT NULL,
  `si_gun_gu_code` INT NULL DEFAULT NULL,
  `first_image1` VARCHAR(100) NULL DEFAULT NULL,
  `first_image2` VARCHAR(100) NULL DEFAULT NULL,
  `map_level` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `tel` VARCHAR(20) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(100) NULL DEFAULT NULL,
  `homepage` VARCHAR(1000) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
  INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
  CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `enjoytrip`.`sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `enjoytrip`.`guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `enjoytrip`.`contenttypes` (`content_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56644
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board_type` (
  `board_id` INT NOT NULL,
  `board_subject` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`board_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`user` (
  `user_id` VARCHAR(20) NOT NULL,
  `user_pwd` VARCHAR(60) NOT NULL,
  `user_name` VARCHAR(10) NOT NULL,
  `user_email` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `is_withdraw` TINYINT NULL DEFAULT '0',
  `role` TINYINT NULL DEFAULT '0',
  `token` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `board_type_id` INT NOT NULL,
  `content` VARCHAR(300) NOT NULL,
  `view` INT NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `attraction_url` VARCHAR(100) NULL DEFAULT NULL,
  `attraction_name` VARCHAR(45) NULL DEFAULT NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`board_id`),
  INDEX `fk_board_board_type1_idx` (`board_type_id` ASC) VISIBLE,
  INDEX `fk_board_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_board_type1`
    FOREIGN KEY (`board_type_id`)
    REFERENCES `enjoytrip`.`board_type` (`board_id`),
  CONSTRAINT `fk_board_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 90
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board_comment` (
  `board_comment_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `content` VARCHAR(100) NULL DEFAULT NULL,
  `board_id` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`board_comment_id`),
  INDEX `fk_board_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_board_comment_board1_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_comment_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `enjoytrip`.`board` (`board_id`),
  CONSTRAINT `fk_board_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board_file` (
  `file_id` INT NOT NULL AUTO_INCREMENT,
  `file_url` VARCHAR(100) NOT NULL,
  `board_id` INT NOT NULL,
  PRIMARY KEY (`file_id`),
  INDEX `fk_board_file_board_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_file_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `enjoytrip`.`board` (`board_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 118
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`trip_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`trip_plan` (
  `trip_plan_id` INT NOT NULL AUTO_INCREMENT,
  `trip_name` VARCHAR(20) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  PRIMARY KEY (`trip_plan_id`),
  INDEX `fk_trip_plan_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_trip_plan_sido_code` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `fk_trip_plan_sido_code`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sidos` (`sido_code`),
  CONSTRAINT `fk_trip_plan_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`day_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`day_plan` (
  `day_plan_id` INT NOT NULL AUTO_INCREMENT,
  `day_number` INT NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `trip_plan_id` INT NOT NULL,
  PRIMARY KEY (`day_plan_id`),
  INDEX `fk_day_plan_trip_plan1_idx` (`trip_plan_id` ASC) VISIBLE,
  CONSTRAINT `fk_day_plan_trip_plan1`
    FOREIGN KEY (`trip_plan_id`)
    REFERENCES `enjoytrip`.`trip_plan` (`trip_plan_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`day_plan_attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`day_plan_attractions` (
  `attractions_no` INT NOT NULL,
  `plan_day_id` INT NOT NULL,
  `visit_order` INT NULL DEFAULT NULL,
  `memo` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`attractions_no`, `plan_day_id`),
  INDEX `fk_day_plan_attractions_plan_day_idx` (`plan_day_id` ASC) VISIBLE,
  INDEX `fk_day_plan_attractions_attractions_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_day_plan_attractions_attractions`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `enjoytrip`.`attractions` (`no`),
  CONSTRAINT `fk_day_plan_attractions_day_plan`
    FOREIGN KEY (`plan_day_id`)
    REFERENCES `enjoytrip`.`day_plan` (`day_plan_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_day_plan_attractions_plan_day`
    FOREIGN KEY (`plan_day_id`)
    REFERENCES `enjoytrip`.`day_plan` (`day_plan_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`favorite` (
  `favorite_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `attractions_no` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`favorite_id`),
  INDEX `fk_favorite_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_favorite_attractions1_idx` (`attractions_no` ASC) VISIBLE,
  CONSTRAINT `fk_favorite_attractions1`
    FOREIGN KEY (`attractions_no`)
    REFERENCES `enjoytrip`.`attractions` (`no`),
  CONSTRAINT `fk_favorite_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`video` (
  `video_id` INT NOT NULL AUTO_INCREMENT,
  `video_title` VARCHAR(20) NOT NULL,
  `video_url` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` VARCHAR(20) NOT NULL,
  `sidos_no` INT NOT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`video_id`),
  INDEX `fk_video_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_video_sidos1_idx` (`sidos_no` ASC) VISIBLE,
  CONSTRAINT `fk_video_sidos1`
    FOREIGN KEY (`sidos_no`)
    REFERENCES `enjoytrip`.`sidos` (`no`),
  CONSTRAINT `fk_video_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
