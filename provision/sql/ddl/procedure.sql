
/**
  시퀀스 생성 호출 프로시저
 */
DELIMITER $$
CREATE DEFINER=`develop`@`%` PROCEDURE `create_sequence`(IN input_name TEXT)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
DELETE FROM sequences WHERE name = input_name;
INSERT INTO sequences VALUES (input_name, 0);
END$$
DELIMITER ;

/**
  currentval function 생성
 */
DELIMITER $$
CREATE DEFINER=`develop`@`%` FUNCTION `currentval`(input_name VARCHAR(32)) RETURNS bigint(20) unsigned
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ret BIGINT UNSIGNED;
SELECT currval INTO ret FROM sequences WHERE name = input_name LIMIT 1;
RETURN ret;
END$$
DELIMITER ;

/**
  nextval function 생성
 */
DELIMITER $$
CREATE DEFINER=`develop`@`%` FUNCTION `nextval`(input_name VARCHAR(32)) RETURNS bigint(20) unsigned
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE ret BIGINT UNSIGNED;
UPDATE sequences SET currval = currval + 1 WHERE name = input_name;
SELECT currval INTO ret FROM sequences WHERE name = input_name LIMIT 1;
RETURN ret;
END$$
DELIMITER ;