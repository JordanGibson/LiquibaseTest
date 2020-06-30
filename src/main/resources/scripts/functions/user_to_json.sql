CREATE OR REPLACE FUNCTION user_to_json (name varchar, role_name varchar) RETURNS jsonb AS $$
	BEGIN
 	RETURN json_build_object(
 			'name', name,
			'role', json_build_object(
				'name', role_name
			)
 		);
	END;
$$ LANGUAGE plpgsql;