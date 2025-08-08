ALTER TABLE passes
DROP CONSTRAINT passes_territory_id_fkey;

ALTER TABLE passes
    ADD CONSTRAINT passes_territory_id_fkey
        FOREIGN KEY (territory_id) REFERENCES territories(id)
            ON DELETE CASCADE;

ALTER TABLE passes
DROP CONSTRAINT passes_user_id_fkey,
ADD CONSTRAINT passes_user_id_fkey
FOREIGN KEY (user_id) REFERENCES users(id)
ON DELETE CASCADE;