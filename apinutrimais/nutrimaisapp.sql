-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 30-Nov-2022 às 00:50
-- Versão do servidor: 10.4.25-MariaDB
-- versão do PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `nutrimaisapp`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `chat`
--

CREATE TABLE `chat` (
  `idchat` int(11) NOT NULL,
  `idremetente` int(11) NOT NULL,
  `iddestinatario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `chat`
--

INSERT INTO `chat` (`idchat`, `idremetente`, `iddestinatario`) VALUES
(9, 4, 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `dieta`
--

CREATE TABLE `dieta` (
  `iddieta` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `vencimento` datetime NOT NULL,
  `idpaciente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `dieta`
--

INSERT INTO `dieta` (`iddieta`, `descricao`, `vencimento`, `idpaciente`) VALUES
(3, 'teste', '0000-00-00 00:00:00', 4),
(4, 'oi', '0000-00-00 00:00:00', 4),
(5, 'descricao', '2020-12-12 00:00:00', 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE `endereco` (
  `idendereco` int(11) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `numero` int(11) NOT NULL,
  `bairro` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `estado` char(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`idendereco`, `cep`, `endereco`, `numero`, `bairro`, `cidade`, `estado`) VALUES
(5, '72125-120', 'Bla bla bla', 11, 'Tagua', 'Brasilia alterada', 'DF'),
(6, '72125-600', 'QNF 10', 11, 'Taguatinga Norte (Taguatinga)', 'Brasília', 'DF'),
(7, '72120-500', 'QND 50', 24, 'Taguatinga Norte (Taguatinga)', 'Brasília', 'DF'),
(8, '', '', 0, '', '', '');

-- --------------------------------------------------------

--
-- Estrutura da tabela `mensagens`
--

CREATE TABLE `mensagens` (
  `idmsg` int(11) NOT NULL,
  `idremetente` int(11) NOT NULL,
  `iddestinatario` int(11) NOT NULL,
  `msg` text NOT NULL,
  `chat_idchat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `mensagens`
--

INSERT INTO `mensagens` (`idmsg`, `idremetente`, `iddestinatario`, `msg`, `chat_idchat`) VALUES
(2, 6, 4, 'Oi dr', 9),
(10, 4, 6, 'oi julia.', 9),
(14, 4, 6, 'oi  doutor', 9);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `nome_usuario` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `sobrenome` varchar(200) NOT NULL,
  `tipo_usuario` int(1) NOT NULL,
  `endereco_idendereco` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`idusuario`, `nome_usuario`, `senha`, `nome`, `sobrenome`, `tipo_usuario`, `endereco_idendereco`) VALUES
(4, 'victor', '123', 'Victor', 'Gonçalves', 1, 5),
(5, 'joao', '123', 'Joao', 'Evangelista', 1, 6),
(6, 'julia', '123', 'Julia', 'Gabriela', 2, 7);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`idchat`);

--
-- Índices para tabela `dieta`
--
ALTER TABLE `dieta`
  ADD PRIMARY KEY (`iddieta`);

--
-- Índices para tabela `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`idendereco`);

--
-- Índices para tabela `mensagens`
--
ALTER TABLE `mensagens`
  ADD PRIMARY KEY (`idmsg`);

--
-- Índices para tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `chat`
--
ALTER TABLE `chat`
  MODIFY `idchat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de tabela `dieta`
--
ALTER TABLE `dieta`
  MODIFY `iddieta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `endereco`
--
ALTER TABLE `endereco`
  MODIFY `idendereco` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `mensagens`
--
ALTER TABLE `mensagens`
  MODIFY `idmsg` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
