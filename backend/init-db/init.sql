CREATE DATABASE IF NOT EXISTS collabchat_db;
USE collabchat_db;

CREATE USER IF NOT EXISTS 'collabuser'@'%' IDENTIFIED BY 'collabpass';
GRANT ALL PRIVILEGES ON collabchat_db.* TO 'collabuser'@'%';
FLUSH PRIVILEGES;