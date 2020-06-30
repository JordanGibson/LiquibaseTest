SELECT
	user_to_json(
		u.first_name || ' ' || u.last_name,
		r.name,
		a.street,
		a.town)
FROM "user" u
JOIN "role" r
	ON r.id = u.role_id
LEFT JOIN "address" a
	ON a.id = u.address_id;