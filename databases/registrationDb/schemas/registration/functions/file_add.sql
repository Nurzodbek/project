CREATE OR REPLACE FUNCTION registration.file_add(
    in_login_id BIGINT,
    in_name TEXT,
    in_display_name TEXT,
    in_unique_name TEXT,
    in_description TEXT,
    in_size BIGINT,
    in_mime_type TEXT
)

RETURNS BIGINT
AS $$

 DECLARE FN_NAME CONSTANT TEXT :='registration.file_add';
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

 INSERT INTO registration.file(
     name,
     display_name,
     unique_name,
     description,
     size,
     mime_type,
     created_by,
     deleted
 )
 VALUES(
     in_name,
     in_display_name,
     in_unique_name,
     in_description,
     in_size,
     in_mime_type,
     in_login_id,
     FALSE
 )
 RETURNING file_id INTO result;
 RETURN result;

 END;
 $$ LANGUAGE plpgsql;