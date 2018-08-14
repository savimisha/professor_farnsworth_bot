-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 14, 2018 at 01:41 PM
-- Server version: 10.1.34-MariaDB-0ubuntu0.18.04.1
-- PHP Version: 7.2.7-0ubuntu0.18.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `professor_farnsworth_bot`
--

-- --------------------------------------------------------

--
-- Table structure for table `callback`
--

CREATE TABLE `callback` (
  `id` int(11) NOT NULL,
  `callback_id` text COLLATE utf8mb4_unicode_520_ci,
  `from_id` int(11) NOT NULL,
  `message_id` int(11) DEFAULT NULL,
  `chat_id` bigint(20) DEFAULT NULL,
  `data` text COLLATE utf8mb4_unicode_520_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` bigint(20) NOT NULL,
  `type` enum('private','group','supergroup','channel') COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `title` text COLLATE utf8mb4_unicode_520_ci,
  `username` text COLLATE utf8mb4_unicode_520_ci,
  `first_name` text COLLATE utf8mb4_unicode_520_ci,
  `last_name` text COLLATE utf8mb4_unicode_520_ci,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `subscribed` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `delay`
--

CREATE TABLE `delay` (
  `id` int(11) NOT NULL,
  `delay` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `delay`
--

INSERT INTO `delay` (`id`, `delay`) VALUES
(1, 1200000);

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `id` int(11) UNSIGNED NOT NULL,
  `tag` text COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `type` enum('INFO','ERROR') COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `message` longtext COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `timestamp` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `updates`
--

CREATE TABLE `updates` (
  `id` int(10) UNSIGNED NOT NULL,
  `from_id` int(11) DEFAULT NULL,
  `date` int(11) DEFAULT NULL,
  `chat_id` bigint(20) DEFAULT NULL,
  `author_signature` text COLLATE utf8mb4_unicode_520_ci,
  `text` text COLLATE utf8mb4_unicode_520_ci,
  `location` text COLLATE utf8mb4_unicode_520_ci,
  `left_chat_member` int(11) DEFAULT NULL,
  `group_chat_created` tinyint(4) DEFAULT NULL,
  `migrate_to_chat_id` bigint(20) DEFAULT NULL,
  `migrate_from_chat_id` bigint(20) DEFAULT NULL,
  `callback_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `is_bot` tinyint(4) NOT NULL DEFAULT '0',
  `first_name` text COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_unicode_520_ci,
  `username` text COLLATE utf8mb4_unicode_520_ci,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `video`
--

CREATE TABLE `video` (
  `id` int(11) NOT NULL,
  `link` text COLLATE utf8mb4_unicode_520_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;


--
-- Indexes for dumped tables
--

--
-- Indexes for table `callback`
--
ALTER TABLE `callback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `from_id` (`from_id`),
  ADD KEY `chat_id` (`chat_id`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `id_2` (`id`);

--
-- Indexes for table `delay`
--
ALTER TABLE `delay`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `updates`
--
ALTER TABLE `updates`
  ADD PRIMARY KEY (`id`),
  ADD KEY `from_id` (`from_id`),
  ADD KEY `chat_id` (`chat_id`),
  ADD KEY `left_chat_member` (`left_chat_member`),
  ADD KEY `callback_id` (`callback_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `video`
--
ALTER TABLE `video`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `callback`
--
ALTER TABLE `callback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `delay`
--
ALTER TABLE `delay`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `updates`
--
ALTER TABLE `updates`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `video`
--
ALTER TABLE `video`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `callback`
--
ALTER TABLE `callback`
  ADD CONSTRAINT `callback_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `callback_ibfk_2` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`);

--
-- Constraints for table `updates`
--
ALTER TABLE `updates`
  ADD CONSTRAINT `updates_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `updates_ibfk_2` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`),
  ADD CONSTRAINT `updates_ibfk_3` FOREIGN KEY (`left_chat_member`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `updates_ibfk_4` FOREIGN KEY (`callback_id`) REFERENCES `callback` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
