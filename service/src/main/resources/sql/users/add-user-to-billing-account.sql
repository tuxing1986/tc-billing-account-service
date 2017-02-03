INSERT INTO tt_project_manager
           (project_id, user_account_id, cost, active, pay_rate, creation_date, creation_user, modification_date, modification_user)
    VALUES (:billingAccountId, :userId, 0.0, 1, 0.0, current, :userName, current, :userName)