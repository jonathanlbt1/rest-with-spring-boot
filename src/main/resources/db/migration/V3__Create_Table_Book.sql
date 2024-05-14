
CREATE TABLE IF NOT EXISTS `book` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `author` VARCHAR(100) NOT NULL,
    `pages` INTEGER NOT NULL,
    `editor` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
    );