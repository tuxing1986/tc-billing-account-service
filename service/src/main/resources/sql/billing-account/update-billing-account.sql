UPDATE tt_project SET
       name = :name,
       project_status_id = :statusId,
       start_date = :startDate,
       end_date = :endDate,
       budget = :amount,
       modification_user = :userName,
       modification_date = current,
       payment_terms_id = :paymentTermId,
       po_box_number = :poNumber,
       sales_tax = :salesTax
WHERE
       project_id = :billingAccountId