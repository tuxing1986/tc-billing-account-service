INSERT INTO project (project_id, company_id, name, active, sales_tax,
                     po_box_number, payment_terms_id, description,
                     start_date, end_date, creation_date,
                     creation_user, modification_date, modification_user,
                     subscription_number, is_manual_prize_setting, budget)
VALUES (:projectId, :companyId, :name,
        :active, :salesTax, :poNumber,
        :paymentTermId, :description, :startDate,
        :endDate, current,
        :userId, current, :userId,
        :subscriptionNumber, :manualPrizeSetting, :budgetAmount)