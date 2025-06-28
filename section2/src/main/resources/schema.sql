CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` int AUTO_INCREMENT  PRIMARY KEY,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
  `customer_id` int NOT NULL,
   `account_number` int AUTO_INCREMENT  PRIMARY KEY,
  `account_type` varchar(100) NOT NULL,
  `branch_address` varchar(200) NOT NULL,
  `created_at` date NOT NULL,
   `created_by` varchar(20) NOT NULL,
   `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);



INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('John Doe', 'john.doe@email.com', '1234567890', CURDATE(), 'admin');

INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('Jane Smith', 'jane.smith@email.com', '9876543210', CURDATE(), 'admin');

INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('Mike Johnson', 'mike.johnson@email.com', '5555555555', CURDATE(), 'admin');

INSERT INTO `accounts` (`customer_id`, `account_type`, `branch_address`, `created_at`, `created_by`)
VALUES (1, 'Savings', '123 Main St, New York, NY 10001', CURDATE(), 'admin');

INSERT INTO `accounts` (`customer_id`, `account_type`, `branch_address`, `created_at`, `created_by`)
VALUES (2, 'Checking', '456 Park Ave, New York, NY 10002', CURDATE(), 'admin');

INSERT INTO `accounts` (`customer_id`, `account_type`, `branch_address`, `created_at`, `created_by`)
    VALUES (3, 'Savings', '789 Broadway, New York, NY 10003', CURDATE(), 'admin');