UPDATE client
SET
   name = :name,
   status = :status,
   start_date = :startDate,
   end_date = :endDate,
   code_name = :codeName
WHERE client_id = :id