SELECT
	user_to_json(
		u.first_name || ' ' || u.last_name,
		' ')
FROM user u;