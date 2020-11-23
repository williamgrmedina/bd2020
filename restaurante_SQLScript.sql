CREATE DATABASE  IF NOT EXISTS `restaurante` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `restaurante`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restaurante
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `atendimentos`
--

DROP TABLE IF EXISTS `atendimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `atendimentos` (
  `idAtendimento` int NOT NULL AUTO_INCREMENT,
  `inicio` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fim` timestamp NULL DEFAULT NULL,
  `atend_idPedido` int NOT NULL,
  `atend_idProduto` int NOT NULL,
  PRIMARY KEY (`idAtendimento`,`atend_idPedido`,`atend_idProduto`),
  UNIQUE KEY `idAtendimento_UNIQUE` (`idAtendimento`),
  KEY `fk_Atendimento_pedido_solicita_produto1_idx` (`atend_idPedido`,`atend_idProduto`),
  CONSTRAINT `fk_Atendimento_pedido_solicita_produto1` FOREIGN KEY (`atend_idPedido`, `atend_idProduto`) REFERENCES `produtos_pedidos` (`pedido_idPedido`, `produto_idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atendimentos`
--

LOCK TABLES `atendimentos` WRITE;
/*!40000 ALTER TABLE `atendimentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `atendimentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `login` varchar(45) NOT NULL,
  `senha` varchar(64) NOT NULL,
  `pnome` varchar(45) NOT NULL,
  `snome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estoques`
--

DROP TABLE IF EXISTS `estoques`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estoques` (
  `idEstoque` int NOT NULL,
  `funcionario_login` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstoque`),
  KEY `fk_estoque_funcionario1_idx` (`funcionario_login`),
  CONSTRAINT `fk_estoque_funcionario1` FOREIGN KEY (`funcionario_login`) REFERENCES `funcionarios` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoques`
--

LOCK TABLES `estoques` WRITE;
/*!40000 ALTER TABLE `estoques` DISABLE KEYS */;
/*!40000 ALTER TABLE `estoques` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionarios` (
  `login` varchar(45) NOT NULL,
  `senha` varchar(64) NOT NULL,
  `pnome` varchar(45) NOT NULL,
  `snome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `cargo` varchar(45) NOT NULL,
  `setor` varchar(45) NOT NULL,
  `salario` decimal(8,2) NOT NULL,
  `data_efetivacao` date NOT NULL DEFAULT (curdate()),
  `gerente_login` varchar(45) NOT NULL,
  PRIMARY KEY (`login`),
  KEY `fk_funcionario_funcionario1_idx` (`gerente_login`),
  CONSTRAINT `fk_funcionario_funcionario1` FOREIGN KEY (`gerente_login`) REFERENCES `funcionarios` (`login`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES ('asdasd','903d008327e6530d5da504441d8c4eab1ea98a55290d67ea6c93d5ce281444eb','setor_2','setor_2_snome','email','gerente','setor2',1000.00,'2020-11-09','Medina2'),('login_setor2','903d008327e6530d5da504441d8c4eab1ea98a55290d67ea6c93d5ce281444eb','setor_2','setor_2_snome','email','nada','setor2',1000.00,'2020-08-08','Medina2'),('Medina','ac9689e2272427085e35b9d3e3e8bed88cb3434828b43b86fc0596cad4c6e270','William','Medina','william.medina@uel.br','gerente','gerencia-1',7000.00,'2020-11-09','Medina'),('Medina2','903d008327e6530d5da504441d8c4eab1ea98a55290d67ea6c93d5ce281444eb','setor_2','setor_2_snome','email','gerente','setor2',1000.00,'2020-08-08','Medina2'),('Natalia','12ebc912f7670dc1c2ce12a12b595d09e78e7f61af9cc7536ab03af5028247d5','Natalia','Cortez e Sousa','ncortez1105@gmail.com','Gerente','gerencia-1',99999.99,'2020-01-11','Medina'),('VitÃ³ria','05c792c9fdee4eec9213b13b40e06174c3559e6a2a31ca838b586d3ee011bfa3','VitÃ³ria','Cardoso Azevedo','VitoriaCardosoAzevedo@gmail.com','garcom','atendimento',1500.00,'2019-01-15','Medina'),('wqe','qwewe','Mourão','wqeewqe','weqewqew','rwrqwr','rqwrw',1233.00,'2020-01-01','Medina2');
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `idPedido` int NOT NULL AUTO_INCREMENT,
  `comanda` int DEFAULT NULL,
  `cliente_login` varchar(45) DEFAULT NULL,
  `funcionario_login` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPedido`),
  UNIQUE KEY `idPedido_UNIQUE` (`idPedido`),
  KEY `fk_pedido_cliente1_idx` (`cliente_login`),
  KEY `fk_pedido_funcionario1_idx` (`funcionario_login`),
  CONSTRAINT `fk_pedido_cliente1` FOREIGN KEY (`cliente_login`) REFERENCES `clientes` (`login`),
  CONSTRAINT `fk_pedido_funcionario1` FOREIGN KEY (`funcionario_login`) REFERENCES `funcionarios` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `idProduto` int NOT NULL,
  `produto_idEstoque` int NOT NULL,
  `nome` varchar(45) NOT NULL,
  `valor_de_compra` double NOT NULL,
  `valor_de_venda` double NOT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `fk_produtos_estoques1_idx` (`produto_idEstoque`),
  CONSTRAINT `fk_produtos_estoques1` FOREIGN KEY (`produto_idEstoque`) REFERENCES `estoques` (`idEstoque`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos_pedidos`
--

DROP TABLE IF EXISTS `produtos_pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos_pedidos` (
  `pedido_idPedido` int NOT NULL,
  `produto_idProduto` int NOT NULL,
  `valor` decimal(7,2) NOT NULL,
  `qtd` int NOT NULL,
  `observacao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pedido_idPedido`,`produto_idProduto`),
  KEY `fk_pedido_has_produto_produto1_idx` (`produto_idProduto`),
  KEY `fk_pedido_has_produto_pedido1_idx` (`pedido_idPedido`),
  CONSTRAINT `fk_pedido_has_produto_pedido1` FOREIGN KEY (`pedido_idPedido`) REFERENCES `pedidos` (`idPedido`),
  CONSTRAINT `fk_pedido_has_produto_produto1` FOREIGN KEY (`produto_idProduto`) REFERENCES `produtos` (`idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos_pedidos`
--

LOCK TABLES `produtos_pedidos` WRITE;
/*!40000 ALTER TABLE `produtos_pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `produtos_pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'restaurante'
--

--
-- Dumping routines for database 'restaurante'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-23 12:18:54
