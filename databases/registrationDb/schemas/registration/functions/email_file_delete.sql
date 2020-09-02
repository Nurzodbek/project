CREATE OR REPLACE FUNCTION registration.email_file_delete(
    in_login_id BIGINT,
    in_email_file_id BIGINT
)

RETURNS BIGINT
AS $$

DECLARE FN_NAME CONSTANT TEXT := ' registration.email_file_delete';
STEP_INDEX INT;
STEP_DESC VARCHAR(500);
result BIGINT;

BEGIN

STEP_INDEX := 0;
STEP_DESC := FN_NAME || ' : Initialized...';
RAISE NOTICE '%',STEP_DESC;

STEP_INDEX := 10;
STEP_DESC := FN_NAME || ' : Validation check permission';
RAISE NOTICE '%',STEP_DESC;

IF NOT EXISTS (SELECT * FROM registration.email_file WHERE registration.email_file.email_file_id = in_email_file_id AND NOT deleted)THEN
    RAISE EXCEPTION ' Cannot delete email_file % not exists ---> % step %',in_email_file_id,STEP_INDEX,STEP_DESC
    USING HINT = 'Please check your email_file ID';

END IF;

STEP_INDEX := 20;
STEP_DESC := FN_NAME || ' : Select data';
RAISE NOTICE '%' , STEP_DESC;

UPDATE registration.email_file
SET
    modified_on = now(),
    modified_by = in_login_id,
    deleted = TRUE

WHERE 
    registration.email_file.email_file_id = in_email_file_id
        AND NOT registration.email_file.deleted
RETURNING email_file_id INTO result;
RETURN result;
END;
$$ LANGUAGE plpgsql;    