UPDATE project
SET    name = :name,
       active = :active,
       start_date = :startDate,
       end_date = :endDate,
       budget = :budgetAmount,
       modification_user = :userId,
       modification_date = current,
       payment_terms_id = :paymentTermId,
       po_box_number = :poNumber,
       sales_tax = :salesTax,
       description = :description,
       subscription_number = :subscriptionNumber,
       company_id = :companyId,
       is_manual_prize_setting = :manualPrizeSetting
WHERE
       project_id = :billingAccountId