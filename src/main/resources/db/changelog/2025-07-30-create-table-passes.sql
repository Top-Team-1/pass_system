CREATE TABLE passes
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT REFERENCES users (id),
    territory_id BIGINT REFERENCES territories (id),
    is_active    BOOLEAN DEFAULT FALSE
);