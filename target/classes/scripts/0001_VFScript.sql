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

CREATE OR REPLACE VIEW user_json AS
SELECT
	user_to_json(
		u.first_name || ' ' || u.last_name,
		r.name)
FROM user u
JOIN role r
	ON r.id = u.role_id;