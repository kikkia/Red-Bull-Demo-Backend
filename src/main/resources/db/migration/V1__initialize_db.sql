-- ----------------------------------------
-- Table `user`
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT(32) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `password_hash` VARCHAR(2047) NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `videos` (
    `id` VARCHAR(255) NOT NULL,
    `media_type` VARCHAR(255) NOT NULL,
    `source` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `description` VARCHAR(4095) NOT NULL,
    `content_url` VARCHAR(2047) NOT NULL,
    `preview_url` VARCHAR(2047) NOT NULL,
    `rating` DOUBLE(2, 1) NOT NULL DEFAULT 0.0,
    PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `video_rating` (
    `id` INT(32) NOT NULL AUTO_INCREMENT,
    `user_id` INT(32) NOT NULL,
    `video_id` VARCHAR(255) NOT NULL,
    `rating` INT(32) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `rating_user_id_fk`
        FOREIGN KEY(`user_id`)
        REFERENCES `users` (`id`),
    CONSTRAINT `rating_video_id_fk`
        FOREIGN KEY(`video_id`)
        REFERENCES `videos` (`id`))
ENGINE = InnoDB;