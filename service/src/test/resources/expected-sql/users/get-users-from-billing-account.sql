SELECT SKIP 0 FIRST 10
     u.user_account_id AS id,
     u.user_name AS name
FROM user_account u
INNER JOIN project_manager pm ON pm.user_account_id = u.user_account_id
WHERE pm.project_id = :billingAccountId