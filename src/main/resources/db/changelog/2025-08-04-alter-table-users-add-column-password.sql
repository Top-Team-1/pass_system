--liquibase formatted sql
--changeset Cr1staix:PAS-24
ALTER TABLE users
ADD COLUMN password VARCHAR(100);