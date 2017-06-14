CREATE TABLE `book_suggestion`.`book` (
  `book_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(100) NOT NULL,
  `genre` VARCHAR(20) NOT NULL,
  `number_of_pages` INT NOT NULL,
  `year_of_publication` INT NOT NULL,
  `rating` FLOAT NOT NULL,
  PRIMARY KEY (`book_id`)
);
