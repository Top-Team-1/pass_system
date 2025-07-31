--liquibase formatted sql
--changeset AlexSkill:PAS-20
ALTER TABLE territories
ADD COLUMN type VARCHAR(20);