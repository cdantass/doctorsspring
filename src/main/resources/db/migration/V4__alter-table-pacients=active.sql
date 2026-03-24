ALTER TABLE pacients ADD COLUMN active BOOLEAN;

UPDATE doctors SET active = TRUE;