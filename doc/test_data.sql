database common_oltp;
insert into id_sequences values ('com.appirio.service.billingaccount.api.Client', 1000, 20, 0);
insert into id_sequences values ('com.appirio.service.billingaccount.api.BillingAccount', 1000, 20, 0);
insert into id_sequences values ('user_account_seq', 1000, 20, 0);

database time_oltp;
insert into payment_terms(payment_terms_id, description, active, term, creation_date, creation_user, modification_date, modification_user)
values(100, 'payment terms for testing', 1, 0, current, '132456', current, '132456');

insert into company(company_id, name, passcode, creation_user, creation_date, modification_user, modification_date)
VALUES(-1, 'Dummy company','Dummy passcode', '132456', current, '132456', current);