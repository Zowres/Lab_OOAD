-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2025 at 02:01 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `govlash`
--

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transactionID` int(11) NOT NULL,
  `serviceID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `receptionistID` int(11) DEFAULT NULL,
  `laundryStaffID` int(11) DEFAULT NULL,
  `transactionDate` datetime DEFAULT current_timestamp(),
  `transactionStatus` varchar(50) DEFAULT 'Pending',
  `totalWeight` decimal(8,2) DEFAULT NULL,
  `notes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionID`, `serviceID`, `customerID`, `receptionistID`, `laundryStaffID`, `transactionDate`, `transactionStatus`, `totalWeight`, `notes`) VALUES
(2, 4, 5, 7, 6, '2025-12-14 00:00:00', 'Finished', 2.00, ''),
(3, 1, 1, 7, 6, '2025-12-14 00:00:00', 'Finished', 2.00, 'yang bersih bang'),
(4, 4, 1, 7, 6, '2025-12-14 00:00:00', 'Finished', 2.00, 'yang kotor'),
(5, 1, 5, 7, 6, '2025-12-14 00:00:00', 'Finished', 22.00, 'yahh udah'),
(6, 1, 1, 7, 6, '2025-12-14 00:00:00', 'Finished', 22.00, 'awdjknawidjbnawd'),
(7, 4, 5, 7, 6, '2025-12-14 00:00:00', 'Finished', 2.00, 'makan bang');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transactionID`),
  ADD KEY `serviceID` (`serviceID`),
  ADD KEY `customerID` (`customerID`),
  ADD KEY `receptionistID` (`receptionistID`),
  ADD KEY `laundryStaffID` (`laundryStaffID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`serviceID`) REFERENCES `service` (`serviceID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `users` (`userID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`receptionistID`) REFERENCES `users` (`userID`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_ibfk_4` FOREIGN KEY (`laundryStaffID`) REFERENCES `users` (`userID`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
