CREATE TABLE IF NOT EXISTS registration.file(
    file_id BIGSERIAL NOT NULL,
    CONSTRAINT pc_registration_file_file_id PRIMARY KEY (file_id)
);
ALTER TABLE registration.file
ADD COLUMN IF NOT EXISTS name text COLLATE pg_catalog."default",
ADD COLUMN IF NOT EXISTS display_name text COLLATE pg_catalog."default",
ADD COLUMN IF NOT EXISTS unique_name text COLLATE pg_catalog."default",
ADD COLUMN IF NOT EXISTS description text COLLATE pg_catalog."default",
ADD COLUMN IF NOT EXISTS size BIGINT,
ADD COLUMN IF NOT EXISTS mime_type text COLLATE pg_catalog."default",
ADD COLUMN IF NOT EXISTS created_on TIMESTAMP WITH TIME ZONE DEFAULT now(),
ADD COLUMN IF NOT EXISTS created_by BIGINT NOT NULL,
ADD COLUMN IF NOT EXISTS modified_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS modified_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS deleted_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE;