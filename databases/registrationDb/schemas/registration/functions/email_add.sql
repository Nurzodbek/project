CREATE OR REPLACE FUNCTION registration.email_add(
    in_login_id BIGINT,
    in_email_address TEXT
)
RETURNS BIGINT
AS $$

DECLARE FN_NAME CONSTANT TEXT := 'registration.email_add';
STEP_INDEX INT;
STEP_DESC VARCHAR(500);
result BIGINT;

BEGIN

STEP_INDEX := 0;
STEP_DESC := FN_NAME || ' : Initiliazed...';

STEP_INDEX := 10;
STEP_DESC := FN_NAME || ' : Validation check permission';
RAISE NOTICE '%',STEP_DESC;

STEP_INDEX := 20;
STEP_DESC := FN_NAME || ' : Select data';
RAISE NOTICE '%' , STEP_DESC;

INSERT INTO registration.email(
    email_address,
    created_by,
    deleted
)
VALUES(
    in_email_address,
    in_login_id,
    FALSE
)

RETURNING email_id INTO result;
RETURN result;

END;
$$ LANGUAGE plpgsql;