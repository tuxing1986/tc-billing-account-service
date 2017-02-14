SELECT
     pm.project_id AS id
FROM project_manager pm
WHERE pm.project_id = :billingAccountId AND
      pm.user_account_id = :userAccountId