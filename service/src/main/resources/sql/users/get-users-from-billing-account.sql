select u.user_account_id as id, u.user_name as name
FROM tt_project_manager pm, tt_user_account u
WHERE pm.user_account_id = u.user_account_id
AND pm.project_id = :billingAccountId