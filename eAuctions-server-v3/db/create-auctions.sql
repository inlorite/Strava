/* DELETE 'auctions_user' database*/
DROP SCHEMA IF EXISTS auctionsdb;
/* DELETE USER 'auctions_user' AT LOCAL SERVER*/
DROP USER IF EXISTS 'auctions_user'@'%';

/* CREATE ''auctionsdb' DATABASE */
CREATE SCHEMA IF NOT EXISTS auctionsdb;
/* CREATE THE USER 'auctions_user' AT LOCAL SERVER WITH PASSWORD 'auctions_user' */
CREATE USER IF NOT EXISTS 'auctions_user'@'%' IDENTIFIED BY 'password';
/* GRANT FULL ACCESS TO THE DATABASE 'auctionsdb' FOR THE USER 'auctions_user' AT LOCAL SERVER*/
GRANT ALL ON auctionsdb.* TO 'auctions_user'@'%';