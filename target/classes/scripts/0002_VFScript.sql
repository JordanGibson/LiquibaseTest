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

CREATE OR REPLACE VIEW user_json AS
SELECT
	user_to_json(
		u.first_name || ' ' || u.last_name,
		r.name,
		a.street,
		a.town)
FROM user u
JOIN role r
	ON r.id = u.role_id
LEFT JOIN address a
	ON a.id = a.address_id;