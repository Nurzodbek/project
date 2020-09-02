CREATE TABLE IF NOT EXISTS registration.email_file(
    email_file_id BIGSERIAL NOT NULL,
    CONSTRAINT pc_registration_email_file_email_file_id PRIMARY KEY (email_file_id)
);
ALTER TABLE registration.email_file
ADD COLUMN IF NOT EXISTS email_id BIGINT,
ADD COLUMN IF NOT EXISTS file_id BIGINT,
ADD COLUMN IF NOT EXISTS created_on TIMESTAMP WITH TIME ZONE DEFAULT now(),
ADD COLUMN IF NOT EXISTS created_by BIGINT NOT NULL,
ADD COLUMN IF NOT EXISTS modified_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS modified_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS deleted_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE;