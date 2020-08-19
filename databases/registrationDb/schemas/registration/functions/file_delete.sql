CREATE OR REPLACE FUNCTION registration.file_delete(
    in_login_id BIGINT,
    in_file_id BIGINT
)

RETURNS BIGINT
AS $$

DECLARE FN_NAME CONSTANT TEXT := ' registration.file_delete';
STEP_INDEX INT;
STEP_DESC VARCHAR(500);
result BIGINT;

BEGIN

STEP_INDEX := 0;
STEP_DESC := FN_NAME || ' : Initialized...';
RAISE NOTICE '%' , STEP_DESC;

STEP_INDEX := 10;
STEP_DESC := FN_NAME || ' : Validation check permission';
RAISE NOTICE '%' , STEP_DESC;

IF NOT EXISTS(SELECT * FROM registration.file WHERE registration.file.file_id = in_file_id AND NOT deleted)THEN
    RAISE EXCEPTION ' Connot delete file % not exists --> % step %',in_file_id, STEP_INDEX,STEP_DESC
        USING HINT = 'Please check your file ID';
END IF;

STEP_INDEX := 20;
STEP_DESC := FN_NAME || ' : Select data';
RAISE NOTICE '%',STEP_DESC;

UPDATE registration.file
SET 
    deleted_on = now(),
    deleted_by = in_login_id,
    deleted = TRUE
WHERE 
    registration.file.file_id = in_file_id
        AND NOT registration.file.deleted
RETURNING file_id INTO result;
RETURN result;
END;
$$ LANGUAGE plpgsql;