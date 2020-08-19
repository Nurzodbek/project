CREATE OR REPLACE FUNCTION registration.email_update(
    in_login_id BIGINT,
    in_email_id BIGINT,
    in_email_address TEXT
) 

RETURNS BIGINT
AS $$

DECLARE FN_NAME CONSTANT TEXT := 'registration.email_update';
STEP_INDEX INT;
STEP_DESC VARCHAR(500);
result BIGINT;

BEGIN

STEP_INDEX := 0;
STEP_DESC := FN_NAME || ' : Initialized... ';
RAISE NOTICE '%' ,STEP_DESC;

STEP_INDEX := 10;
STEP_DESC := FN_NAME || ' : Validation check permission ';
RAISE NOTICE '%',STEP_DESC;

IF NOT EXISTS (SELECT * FROM registration.email WHERE registration.email.email_id = in_email_id AND NOT deleted)THEN
    RAISE EXCEPTION ' email % not exists ---> % step %', in_email_id , STEP_INDEX ,STEP_DESC
        USING HINT = ' Please check your ID';
END IF;

STEP_INDEX := 20;
STEP_DESC := FN_NAME || ' : Select data ';
RAISE NOTICE '%' , STEP_DESC;

UPDATE registration.email 
SET 
    email_address = in_email_address,
    modified_on = now(),
    modified_by = in_login_id
WHERE 
    registration.email.email_id = in_email_id 
    AND NOT registration.email.deleted
    RETURNING email_id INTO result;
    RETURN result;
END;

$$ LANGUAGE plpgsql;

