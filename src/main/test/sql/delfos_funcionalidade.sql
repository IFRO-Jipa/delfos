-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: delfos
-- ------------------------------------------------------
-- Server version	5.6.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `Funcionalidade`
--

LOCK TABLES `Funcionalidade` WRITE;
/*!40000 ALTER TABLE `Funcionalidade` DISABLE KEYS */;
INSERT INTO `Funcionalidade` VALUES (1,'menuCadastro','Menu relacionado aos cadastros','Cadastro',NULL),(2,'menuCadastroPessoa','Menu relacionado aos cadastros de Pessoa','Cadastro de Pessoa',1),(3,'menuCadastroPesquisa','Menu relacionado aos cadastros de Pesquisas','Cadastro de Pesquisas',1),(4,'menuCadastroLocalizacao','Menu relacionado aos cadastros das Localizações, tais como Cidade, Estado, Bairro, Tipo de Logradouro, etc.','Localizacao',1),(5,'menuCadastroEstado','Menu relacionado ao cadastro de estados','Cadastro de Estados',4),(6,'menuCadastroCidade','Menu relacionado ao cadastro de cidades','Cadastro de Cidades',4),(7,'menuCadastroTipoLogradouro','Menu relacionado ao cadastro de Tipos de Logradouro','Cadastro de Tipos de Logradouro',4),(8,'menuCadastroAcesso','Menu relacionado aos cadastros de acesso, tal como Usuario e Perfil de acesso','menuCadastroAcesso',1),(9,'menuCadastroFuncionalidade','Menu relacionado aos cadastros de Funcionalidade','menuCadastroFuncionalidade',8);
/*!40000 ALTER TABLE `Funcionalidade` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-30 15:24:27
