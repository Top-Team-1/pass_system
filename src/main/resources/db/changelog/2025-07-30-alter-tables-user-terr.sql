ALTER TABLE users
ADD COLUMN added_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN updated_at TIMESTAMP;

ALTER TABLE territories
    ADD COLUMN added_at TIMESTAMP;

ALTER TABLE territories
    ADD COLUMN updated_at TIMESTAMP;

ALTER TABLE territories
DROP COLUMN description;

ALTER TABLE territories
    ADD COLUMN address VARCHAR(200);



