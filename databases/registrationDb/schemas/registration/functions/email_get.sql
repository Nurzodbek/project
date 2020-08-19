CREATE OR REPLACE FUNCTION registration.email_get(
    in_login_id BIGINT,
    in_email_id BIGINT
)

RETURNS TABLE(
    email_id BIGINT,
    email_address TEXT
)

AS $$

DECLARE FN_NAME CONSTANT TEXT := 'registration.email_get';
STEP_INDEX INT;
STEP_DESC VARCHAR(500);

BEGIN

STEP_INDEX := 0;
STEP_DESC := FN_NAME || ' : Initiliazed...';
RAISE NOTICE '%' , STEP_DESC;

STEP_INDEX := 10;
STEP_DESC := FN_NAME || ' : Validation check permission';
RAISE NOTICE '%' , STEP_DESC;

IF in_email_id IS NULL OR in_email_id < 0 THEN
RAISE EXCEPTION ' Non exists Id --> % step % %' , in_email_id , STEP_INDEX , STEP_DESC
USING HINT := ' Please check your email ID';
END IF;

STEP_INDEX := 20;
STEP_DESC := FN_NAME || ' : Select data';
RAISE NOTICE '%' ,STEP_DESC;

RETURN QUERY
SELECT 
registration.email.email_id,
registration.email.email_address
FROM registration.email
WHERE registration.email.email_id = in_email_id AND NOT deleted;
END;
$$ LANGUAGE plpgsql;
