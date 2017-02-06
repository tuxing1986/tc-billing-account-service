SELECT p.project_id as id, p.name as name, pt.payment_terms_id as paymentTerms_Id,
       pt.description as paymentTerms_Description,
       ps.name as status, p.sales_tax as salesTax, p.po_box_number as poNumber,
       p.start_date as startDate, p.end_date as endDate, p.budget as amount,
       p.creation_date as createdAt, p.creation_user as createdBy,
       p.modification_date as updatedAt, p.modification_user as updatedBy
FROM tt_project p, project_status_lu ps, time_oltp\:payment_terms pt
WHERE ps.project_status_id = p.project_status_id
      AND p.project_id = :billingAccountId
      AND pt.payment_terms_id = p.payment_terms_id