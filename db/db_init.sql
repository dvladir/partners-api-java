create database partners_db;
create user p_admin with encrypted password 'p_admin';
grant all privileges on database partners_db to p_admin;
