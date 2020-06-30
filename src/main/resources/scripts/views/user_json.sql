SELECT
	user_to_json(
		u.first_name || ' ' || u.last_name,
		r.name)
FROM "user" u
JOIN "role" r
	ON r.id = u.role_id;