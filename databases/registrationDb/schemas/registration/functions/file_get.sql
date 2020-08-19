CREATE OR REPLACE FUNCTION registration.file_get(
    in_login_id BIGINT, 
    in_file_id BIGINT
    )  
RETURNS TABLE (
    file_id BIGINT,
    name TEXT,
    display_name TEXT,
    unique_name TEXT,
    description TEXT,
    size BIGINT,
    mime_type TEXT
)
AS $$

DECLARE FN_NAME CONSTANT TEXT := 'registration.file_get';
    STEP_INDEX INT;
    STEP_DESC VARCHAR(500);
    BEGIN

    STEP_INDEX := 0;
    STEP_DESC := FN_NAME || ': Initialized ...';
    RAISE NOTICE '%', STEP_DESC;

    STEP_INDEX := 10;
    STEP_DESC := FN_NAME || ': Validation check permission';
    RAISE NOTICE '%', STEP_DESC;


    IF in_file_id IS NULL OR in_file_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_file_id, STEP_INDEX, STEP_DESC
            USING HINT = 'Please check your file ID';
    END IF;

    STEP_INDEX := 20;
    RAISE NOTICE '%', FN_NAME || ': Select Data';

    RETURN QUERY
    SELECT 
        registration.file.file_id,        
        registration.file.name,
        registration.file.display_name,
        registration.file.unique_name,
        registration.file.description,
        registration.file.size,
        registration.file.mime_type
    FROM registration.file
    WHERE 
        registration.file.file_id = in_file_id
        AND NOT registration.file.deleted;
    END;
    $$ LANGUAGE plpgsql;

