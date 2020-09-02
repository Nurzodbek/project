CREATE OR REPLACE FUNCTION registration.email_file_add(
    in_login_id BIGINT,
    in_email_id BIGINT,
    in_file_id BIGINT
)
RETURNS BIGINT
AS $$

 DECLARE FN_NAME CONSTANT TEXT :='registration.email_file_add';
 STEP_INDEX INT;
 STEP_DESC VARCHAR(500);
 result BIGINT;

 BEGIN

 STEP_INDEX := 0;
 STEP_DESC := FN_NAME || ' :Initialized...';
 RAISE NOTICE '%',STEP_DESC;

 STEP_INDEX :=10;
 STEP_DESC := FN_NAME || ' : Validation check permission';
 RAISE NOTICE '%',STEP_DESC;

 STEP_INDEX := 20;
 STEP_DESC := FN_NAME || ' : Select data';
 RAISE NOTICE '%',STEP_DESC;

 INSERT INTO registration.email_file(
     email_id,
     file_id,
     created_by,
     deleted
 )
 VALUES(
     in_email_id,
     in_file_id,
     in_login_id,
     FALSE
 )
 RETURNING email_file_id INTO result;
 RETURN result;

 END;
 $$ LANGUAGE plpgsql;