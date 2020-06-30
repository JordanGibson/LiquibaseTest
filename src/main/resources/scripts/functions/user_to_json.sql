CREATE OR REPLACE FUNCTION user_to_json (name varchar, role_name varchar, street varchar, town varchar) RETURNS jsonb AS $$
	BEGIN
 	RETURN json_build_object(
 			'name', name,
			'role', json_build_object(
				'name', role_name
			),
			'address', json_build_object(
				'street', street,
				'town', town
			)
 		);
	END;
$$ LANGUAGE plpgsql;