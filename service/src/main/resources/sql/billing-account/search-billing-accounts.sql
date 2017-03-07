SELECT SKIP {offset} FIRST {limit}
       p.project_id AS id,
       p.name AS name,
       pt.payment_terms_id AS paymentTerms_Id,
       pt.description AS paymentTerms_Description,
       RTRIM(decode (p.active, 1, 'Active', 'Inactive')) AS status,
       p.sales_tax AS salesTax,
       p.po_box_number AS poNumber,
       p.start_date AS startDate,
       p.end_date AS endDate,
       p.budget AS budgetAmount,
       p.creation_date AS createdAt,
       p.creation_user AS createdBy,
       p.modification_date AS updatedAt,
       p.modification_user AS updatedBy,
       p.description AS description,
       p.subscription_number AS subscriptionNumber,
       p.company_id AS companyId,
       p.is_manual_prize_setting AS manualPrizeSetting
FROM project p
LEFT OUTER JOIN payment_terms pt ON pt.payment_terms_id = p.payment_terms_id 
WHERE  {filter}
ORDER BY {order}