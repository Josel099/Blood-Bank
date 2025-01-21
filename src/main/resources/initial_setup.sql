-- Step 1: Create the database
CREATE DATABASE blb;

-- Use the newly created database
USE blb;

-- Step 2: Create the BloodInfo table
CREATE TABLE blood_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blood_group ENUM('A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 
                     'AB_POSITIVE', 'AB_NEGATIVE', 'O_POSITIVE', 'O_NEGATIVE') NOT NULL,
    quantity INT NOT NULL
);

-- Insert enum values into the BloodInfo table with default quantity as 0
INSERT INTO blood_info (blood_group, quantity) 
VALUES 
('A_POSITIVE', 0),
('A_NEGATIVE', 0),
('B_POSITIVE', 0),
('B_NEGATIVE', 0),
('AB_POSITIVE', 0),
('AB_NEGATIVE', 0),
('O_POSITIVE', 0),
('O_NEGATIVE', 0);

-- Step 3: Create the Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    age INT,
    contact_num VARCHAR(20),
    address TEXT,
    blood_group ENUM('A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 
                     'AB_POSITIVE', 'AB_NEGATIVE', 'O_POSITIVE', 'O_NEGATIVE'),
    role ENUM('ADMIN', 'HOSPITAL_STAFF', 'DONOR', 'RECEIVER') NOT NULL
);

-- Insert default admin user
INSERT INTO users (user_name, password, role) 
VALUES ('admin', 'admin', 'ADMIN');
