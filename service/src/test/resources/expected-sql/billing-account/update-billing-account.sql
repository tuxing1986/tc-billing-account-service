UPDATE tt_project SET
       client_id = :clientId,
       name = :name,
       project_status_id = :statusId,
       start_date = :startDate,
       end_date = :endDate,
       budget = :amount,
       modification_user = :userName,
       modification_date = current
WHERE
       project_id = :billingAccountId