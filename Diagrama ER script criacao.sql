-- MySQL Script generated by MySQL Workbench
-- Mon Nov 23 12:40:58 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema restaurante
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema restaurante
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restaurante` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `restaurante` ;

-- -----------------------------------------------------
-- Table `restaurante`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`clientes` (
  `login` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(64) NOT NULL,
  `pnome` VARCHAR(45) NOT NULL,
  `snome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`funcionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`funcionarios` (
  `login` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(64) NOT NULL,
  `pnome` VARCHAR(45) NOT NULL,
  `snome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `cargo` VARCHAR(45) NOT NULL,
  `setor` VARCHAR(45) NOT NULL,
  `salario` DECIMAL(8,2) NOT NULL,
  `data_efetivacao` DATE NOT NULL,
  `gerente_login` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`),
  INDEX `fk_funcionario_funcionario1_idx` (`gerente_login` ASC),
  CONSTRAINT `fk_funcionario_funcionario1`
    FOREIGN KEY (`gerente_login`)
    REFERENCES `restaurante`.`funcionarios` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`estoques`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`estoques` (
  `idEstoque` INT NOT NULL,
  `funcionario_login` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEstoque`),
  INDEX `fk_estoque_funcionario1_idx` (`funcionario_login` ASC),
  CONSTRAINT `fk_estoque_funcionario1`
    FOREIGN KEY (`funcionario_login`)
    REFERENCES `restaurante`.`funcionarios` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`pedidos` (
  `idPedido` INT NOT NULL AUTO_INCREMENT,
  `comanda` INT NULL DEFAULT NULL,
  `cliente_login` VARCHAR(45) NULL,
  `funcionario_login` VARCHAR(45) NULL,
  PRIMARY KEY (`idPedido`),
  UNIQUE INDEX `idPedido_UNIQUE` (`idPedido` ASC),
  INDEX `fk_pedido_cliente1_idx` (`cliente_login` ASC),
  INDEX `fk_pedido_funcionario1_idx` (`funcionario_login` ASC),
  CONSTRAINT `fk_pedido_cliente1`
    FOREIGN KEY (`cliente_login`)
    REFERENCES `restaurante`.`clientes` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_funcionario1`
    FOREIGN KEY (`funcionario_login`)
    REFERENCES `restaurante`.`funcionarios` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`produtos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`produtos` (
  `idProduto` INT NOT NULL,
  `produto_idEstoque` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `valor_de_compra` DOUBLE NOT NULL,
  `valor_de_venda` DOUBLE NOT NULL,
  PRIMARY KEY (`idProduto`),
  INDEX `fk_produtos_estoques1_idx` (`produto_idEstoque` ASC),
  CONSTRAINT `fk_produtos_estoques1`
    FOREIGN KEY (`produto_idEstoque`)
    REFERENCES `restaurante`.`estoques` (`idEstoque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`produtos_pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`produtos_pedidos` (
  `pedido_idPedido` INT NOT NULL,
  `produto_idProduto` INT NOT NULL,
  `valor` DECIMAL(7,2) NOT NULL,
  `qtd` INT NOT NULL,
  `observacao` VARCHAR(45) NULL,
  PRIMARY KEY (`pedido_idPedido`, `produto_idProduto`),
  INDEX `fk_pedido_has_produto_produto1_idx` (`produto_idProduto` ASC),
  INDEX `fk_pedido_has_produto_pedido1_idx` (`pedido_idPedido` ASC),
  CONSTRAINT `fk_pedido_has_produto_pedido1`
    FOREIGN KEY (`pedido_idPedido`)
    REFERENCES `restaurante`.`pedidos` (`idPedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_has_produto_produto1`
    FOREIGN KEY (`produto_idProduto`)
    REFERENCES `restaurante`.`produtos` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `restaurante`.`Atendimentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurante`.`Atendimentos` (
  `idAtendimento` INT NOT NULL AUTO_INCREMENT,
  `inicio` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fim` TIMESTAMP NULL,
  `atend_idPedido` INT NOT NULL,
  `atend_idProduto` INT NOT NULL,
  PRIMARY KEY (`idAtendimento`, `atend_idPedido`, `atend_idProduto`),
  UNIQUE INDEX `idAtendimento_UNIQUE` (`idAtendimento` ASC),
  INDEX `fk_Atendimento_pedido_solicita_produto1_idx` (`atend_idPedido` ASC, `atend_idProduto` ASC),
  CONSTRAINT `fk_Atendimento_pedido_solicita_produto1`
    FOREIGN KEY (`atend_idPedido` , `atend_idProduto`)
    REFERENCES `restaurante`.`produtos_pedidos` (`pedido_idPedido` , `produto_idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;