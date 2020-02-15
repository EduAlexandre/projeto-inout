CREATE TABLE `registro` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(15) NOT NULL,
  `data_entrada` VARCHAR(30) NOT NULL,
  `flag` BIT(1) NOT NULL,
  `quant_ent` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC));
