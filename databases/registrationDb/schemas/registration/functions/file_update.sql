CREATE OR REPLACE FUNCTION registration.file_update(
    in_login_id BIGINT,
    in_file_id BIGINT,
    in_name TEXT,
    in_display_name TEXT,
    in_unique_name TEXT,
    in_description TEXT,
    in_size BIGINT,
    in_mime_type TEXT
    )  
    RETURNS BIGINT 
    AS $$

    DECLARE FN_NAME CONSTANT TEXT := 'registration.file_update';
    STEP_INDEX INT;
    STEP_DESC VARCHAR(500);
        result BIGINT;        
    BEGIN

    STEP_INDEX := 0;
    STEP_DESC := FN_NAME || ': Initialized ...';
    RAISE NOTICE '%', STEP_DESC;
    
    STEP_INDEX := 10;
    STEP_DESC := FN_NAME || ': Validation check permission';
    RAISE NOTICE '%', STEP_DESC;

IF NOT EXISTS(SELECT * FROM registration.file WHERE registration.file.file_id = in_file_id AND NOT deleted) THEN 
    RAISE EXCEPTION 'file % not exists --> % step %', in_file_id, STEP_INDEX, STEP_DESC
            USING HINT = 'Please check your file ID';
  END IF;


    STEP_INDEX := 20;
    STEP_DESC := FN_NAME || ': Select Data';
    RAISE NOTICE '%',STEP_DESC;

UPDATE registration.file
    SET
        name = in_name,
        display_name = in_display_name,
        unique_name = in_unique_name,
        description = in_description,
        size = in_size,
        mime_type = in_mime_type,
        modified_on = now(),
        modified_by = in_login_id
    WHERE 
        registration.file.file_id = in_file_id
        AND NOT registration.file.deleted 
    RETURNING file_id INTO result;

    RETURN result;
END;
$$ LANGUAGE plpgsql;
