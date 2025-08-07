--liquibase formatted sql
--changeset Cr1staix:PAS-27
CREATE TABLE user_territory(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    territory_id BIGINT NOT NULL,
    CONSTRAINT user_terr_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT terr_user_terr FOREIGN KEY (territory_id) REFERENCES territories(id)
);