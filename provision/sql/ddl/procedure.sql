
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
