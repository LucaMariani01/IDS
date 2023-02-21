-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Feb 21, 2023 alle 18:19
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `loyaltyplatform`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `admin`
--

CREATE TABLE `admin` (
  `codiceFiscale` varchar(16) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `azienda` varchar(11) NOT NULL,
  `password` varchar(66) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `aziende`
--

CREATE TABLE `aziende` (
  `nome` varchar(50) NOT NULL,
  `partitaIva` varchar(11) NOT NULL,
  `password` varchar(66) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `campagnelivello`
--

CREATE TABLE `campagnelivello` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `numLivelli` int(11) NOT NULL,
  `dataInizio` date NOT NULL,
  `dataFine` date NOT NULL,
  `azienda` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `campagnepunti`
--

CREATE TABLE `campagnepunti` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataInizio` date NOT NULL,
  `dataFine` date NOT NULL,
  `maxPunti` int(11) NOT NULL,
  `azienda` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `cashback`
--

CREATE TABLE `cashback` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `dataInizio` date NOT NULL,
  `dataFine` date NOT NULL,
  `sogliaMinima` int(11) NOT NULL,
  `sogliaMassima` int(11) NOT NULL,
  `azienda` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `categorieprodotti`
--

CREATE TABLE `categorieprodotti` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descrizione` varchar(250) NOT NULL,
  `programmaCashBack` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `clienti`
--

CREATE TABLE `clienti` (
  `email` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `password` varchar(66) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `clienticampagnaaderite`
--

CREATE TABLE `clienticampagnaaderite` (
  `idAdesione` int(11) NOT NULL,
  `emailCliente` varchar(50) NOT NULL,
  `campagnelivello` int(11) DEFAULT NULL,
  `campagnepunti` int(11) DEFAULT NULL,
  `cashback` int(11) DEFAULT NULL,
  `membership` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `livelli`
--

CREATE TABLE `livelli` (
  `id` int(11) NOT NULL,
  `numLivello` int(11) NOT NULL,
  `campagnaLivello` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `requisitoEntrata` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `membership`
--

CREATE TABLE `membership` (
  `id` int(11) NOT NULL,
  `dataInizio` date NOT NULL,
  `dataFine` date NOT NULL,
  `costo` float NOT NULL,
  `nome` varchar(50) NOT NULL,
  `azienda` varchar(11) NOT NULL,
  `descrizione` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `premi`
--

CREATE TABLE `premi` (
  `codice` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `premioLivello` int(11) DEFAULT NULL,
  `premioPunti` int(11) DEFAULT NULL,
  `puntiNecessari` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `recensioni`
--

CREATE TABLE `recensioni` (
  `idRecensione` int(11) NOT NULL,
  `utente` varchar(50) NOT NULL,
  `recensione` varchar(300) NOT NULL,
  `azienda` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `tessere`
--

CREATE TABLE `tessere` (
  `idTessera` int(11) NOT NULL,
  `utente` varchar(50) NOT NULL,
  `livelloAttuale` int(11) DEFAULT NULL,
  `puntiAccumulati` int(11) DEFAULT NULL,
  `cashbackAccumulato` double DEFAULT NULL,
  `idAdesione` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`codiceFiscale`),
  ADD KEY `AziendaDiAppartenenza` (`azienda`);

--
-- Indici per le tabelle `aziende`
--
ALTER TABLE `aziende`
  ADD PRIMARY KEY (`partitaIva`);

--
-- Indici per le tabelle `campagnelivello`
--
ALTER TABLE `campagnelivello`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `idaziendalivello` (`azienda`);

--
-- Indici per le tabelle `campagnepunti`
--
ALTER TABLE `campagnepunti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idaziendapunti` (`azienda`);

--
-- Indici per le tabelle `cashback`
--
ALTER TABLE `cashback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idaziendacashback` (`azienda`);

--
-- Indici per le tabelle `categorieprodotti`
--
ALTER TABLE `categorieprodotti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categorieProdottiCashback` (`programmaCashBack`);

--
-- Indici per le tabelle `clienti`
--
ALTER TABLE `clienti`
  ADD PRIMARY KEY (`email`);

--
-- Indici per le tabelle `clienticampagnaaderite`
--
ALTER TABLE `clienticampagnaaderite`
  ADD PRIMARY KEY (`idAdesione`),
  ADD KEY `cliente` (`emailCliente`),
  ADD KEY `campagnalivello` (`campagnelivello`),
  ADD KEY `campagnapunti` (`campagnepunti`),
  ADD KEY `campagnacashback` (`cashback`),
  ADD KEY `campagnamembership` (`membership`);

--
-- Indici per le tabelle `livelli`
--
ALTER TABLE `livelli`
  ADD PRIMARY KEY (`id`),
  ADD KEY `campagnaLivelloAppartenenza` (`campagnaLivello`);

--
-- Indici per le tabelle `membership`
--
ALTER TABLE `membership`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `idaziendamembership` (`azienda`);

--
-- Indici per le tabelle `premi`
--
ALTER TABLE `premi`
  ADD PRIMARY KEY (`codice`),
  ADD KEY `premiolivello` (`premioLivello`),
  ADD KEY `premiopunti` (`premioPunti`);

--
-- Indici per le tabelle `recensioni`
--
ALTER TABLE `recensioni`
  ADD PRIMARY KEY (`idRecensione`),
  ADD KEY `aziendarecensita` (`azienda`),
  ADD KEY `emailcliente` (`utente`);

--
-- Indici per le tabelle `tessere`
--
ALTER TABLE `tessere`
  ADD PRIMARY KEY (`idTessera`),
  ADD KEY `emailutente` (`utente`),
  ADD KEY `idadesione` (`idAdesione`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `clienticampagnaaderite`
--
ALTER TABLE `clienticampagnaaderite`
  MODIFY `idAdesione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT per la tabella `recensioni`
--
ALTER TABLE `recensioni`
  MODIFY `idRecensione` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `tessere`
--
ALTER TABLE `tessere`
  MODIFY `idTessera` int(11) NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `AziendaDiAppartenenza` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`);

--
-- Limiti per la tabella `campagnelivello`
--
ALTER TABLE `campagnelivello`
  ADD CONSTRAINT `idaziendalivello` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`);

--
-- Limiti per la tabella `campagnepunti`
--
ALTER TABLE `campagnepunti`
  ADD CONSTRAINT `idaziendapunti` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`);

--
-- Limiti per la tabella `cashback`
--
ALTER TABLE `cashback`
  ADD CONSTRAINT `idaziendacashback` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`);

--
-- Limiti per la tabella `categorieprodotti`
--
ALTER TABLE `categorieprodotti`
  ADD CONSTRAINT `categorieProdottiCashback` FOREIGN KEY (`programmaCashBack`) REFERENCES `cashback` (`id`);

--
-- Limiti per la tabella `clienticampagnaaderite`
--
ALTER TABLE `clienticampagnaaderite`
  ADD CONSTRAINT `campagnacashback` FOREIGN KEY (`cashback`) REFERENCES `cashback` (`id`),
  ADD CONSTRAINT `campagnalivello` FOREIGN KEY (`campagnelivello`) REFERENCES `campagnelivello` (`id`),
  ADD CONSTRAINT `campagnamembership` FOREIGN KEY (`membership`) REFERENCES `membership` (`id`),
  ADD CONSTRAINT `campagnapunti` FOREIGN KEY (`campagnepunti`) REFERENCES `campagnepunti` (`id`),
  ADD CONSTRAINT `cliente` FOREIGN KEY (`emailCliente`) REFERENCES `clienti` (`email`);

--
-- Limiti per la tabella `livelli`
--
ALTER TABLE `livelli`
  ADD CONSTRAINT `campagnaLivelloAppartenenza` FOREIGN KEY (`campagnaLivello`) REFERENCES `campagnelivello` (`id`);

--
-- Limiti per la tabella `membership`
--
ALTER TABLE `membership`
  ADD CONSTRAINT `idaziendamembership` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`);

--
-- Limiti per la tabella `premi`
--
ALTER TABLE `premi`
  ADD CONSTRAINT `premiolivello` FOREIGN KEY (`premioLivello`) REFERENCES `livelli` (`id`),
  ADD CONSTRAINT `premiopunti` FOREIGN KEY (`premioPunti`) REFERENCES `campagnepunti` (`id`);

--
-- Limiti per la tabella `recensioni`
--
ALTER TABLE `recensioni`
  ADD CONSTRAINT `aziendarecensita` FOREIGN KEY (`azienda`) REFERENCES `aziende` (`partitaIva`),
  ADD CONSTRAINT `emailcliente` FOREIGN KEY (`utente`) REFERENCES `clienti` (`email`);

--
-- Limiti per la tabella `tessere`
--
ALTER TABLE `tessere`
  ADD CONSTRAINT `emailutente` FOREIGN KEY (`utente`) REFERENCES `clienti` (`email`),
  ADD CONSTRAINT `idadesione` FOREIGN KEY (`idAdesione`) REFERENCES `clienticampagnaaderite` (`idAdesione`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
