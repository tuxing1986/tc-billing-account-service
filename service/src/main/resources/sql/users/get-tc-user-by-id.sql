SELECT
     co.user_id as id,
     co.handle as handle
FROM common_oltp\:user co
WHERE co.user_id = :userId