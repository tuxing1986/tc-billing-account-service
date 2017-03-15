/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.dao;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
import com.appirio.service.billingaccount.dto.TCUserDTO;
import com.appirio.supply.dataaccess.ApiQueryInput;
import com.appirio.supply.dataaccess.DatasourceName;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.SqlQueryFile;
import com.appirio.supply.dataaccess.SqlUpdateFile;
import com.appirio.tech.core.api.v3.request.QueryParameter;

import org.skife.jdbi.v2.sqlobject.Bind;

import java.util.Date;
import java.util.List;

/**
 * DAO to handle billing accounts.
 *
 * <p>
 *  Changes in v 1.1 FAST 72HRS!! - ADD APIS FOR CLIENTS AND SOME LOGIC CHANGES
 *  -- Updated createBillingAccount()/updateBillingAccount() to accept additional fields.
 *  -- Added checkCompanyExists(), getTCUserById() and createUserAccount(), checkUserBelongsToBillingAccount
 * </p>
 *
 * <p>
 *  Changes in v 1.2 Fast 48hrs!! Topcoder - Improvement For Billing Account Service
 *  -- Updated createBillingAccount()/updateBillingAccount() to support budgetAmount and additional clientId field.
 *  -- Added checkClientExists(), addBillingAccountToClient() and removeBillingAccountFromClient()
 *  -- Updated getBillingAccountUsers() to support limit and offset
 * </p>
 *
 * @author TCSCODER, TCSCODER
 * @version 1.2
 */
@DatasourceName("oltp")
public interface BillingAccountDAO {

    /**
     * Search for billing accounts
     *
     * @param queryParameter sort and filter parameters
     * @return the billing accounts
     */
    @SqlQueryFile("sql/billing-account/search-billing-accounts.sql")
    QueryResult<List<BillingAccount>> searchBillingAccounts(@ApiQueryInput QueryParameter queryParameter);

    /**
     * Search for billing accounts limited by the privileges of the logged in user
     *
     * @param userId the user id of the logged in user
     * @param queryParameter sort and filter parameters
     * @return the billing accounts
     */
    @SqlQueryFile("sql/billing-account/search-my-billing-accounts.sql")
    QueryResult<List<BillingAccount>> searchMyBillingAccounts(@Bind("loggedInUser") Long userId,
                                                              @ApiQueryInput QueryParameter queryParameter);

    /**
     * Create a billing account
     *
     * @param projectId the billing account id
     * @param name the billing account name
     * @param paymentTermId The payment term id.
     * @param startDate the start date
     * @param endDate the end date
     * @param active The flag indicating whether the billing account is active (1) or inactive (0)
     * @param userId the user id (for audit purposes only)
     * @param salestax the sales tax value.
     * @param poNumber the PO number.
     * @param description The billing account description.
     * @param subscriptionNumber The billing account subscription number.
     * @param companyId The id of the company to which the billing account is associated.
     * @param manualPrizeSetting The manual prize setting flag
     * @param clientId the id of the client to which the billing account is associated.
     * @param billable the flag indicating the account is billable
     * 
     * @return the id of the created billing account
     */
    @SqlUpdateFile("sql/billing-account/create-billing-account.sql")
    Long createBillingAccount(@Bind("projectId") Long projectId, @Bind("budgetAmount") Float budgetAmount,
                              @Bind("name") String name, @Bind("paymentTermId") Long paymentTermId,
                              @Bind("startDate") Date startDate, @Bind("endDate") Date endDate,
                              @Bind("active") Long active, @Bind("userId") String userId,
                              @Bind("salesTax") Float salesTax, @Bind("poNumber") String poNumber,
                              @Bind("description") String description,
                              @Bind("subscriptionNumber") String subscriptionNumber,
                              @Bind("companyId") Long companyId,
                              @Bind("manualPrizeSetting") Long manualPrizeSetting,
                              @Bind("clientId") Long clientId,
                              @Bind("billable") boolean billable
                              );

    /**
     * Get a billing account by id
     *
     * @param billingAccountId the id
     * @return the billing account with the given id
     */
    @SqlQueryFile("sql/billing-account/get-billing-account-by-id.sql")
    QueryResult<List<BillingAccount>> getBillingAccount(@Bind("billingAccountId") Long billingAccountId);

    /**
     * Update existing billing account
     *
     * @param billingAccountId the billing account id
     * @param budgetAmount the budged of the account
     * @param name the name of the account
     * @param startDate the start date
     * @param endDate the end date
     * @param active The flag indicating whether the billing account is active or not
     * @param userName the user name (for audit purposes only)
     * @param salestax The billing account sales tax
     * @param poNumber The billing account PO number.
     * @param description The billing account description.
     * @param subscriptionNumber The billing account subscription number.
     * @param companyId The company id.
     * @param manualPrizeSetting The manual prize setting flag
     * @param clientId the id of the client to which the billing account is associated.
     * @param billable the flag indicating the account is billable
     */
    @SqlUpdateFile("sql/billing-account/update-billing-account.sql")
    void updateBillingAccount(@Bind("billingAccountId") Long billingAccountId, @Bind("budgetAmount") Float budgetAmount,
                              @Bind("name") String name, @Bind("paymentTermId") Long paymentTermId,
                              @Bind("startDate") Date startDate, @Bind("endDate") Date endDate,
                              @Bind("active") Long active, @Bind("userId") String userId,
                              @Bind("salesTax") Float salesTax, @Bind("poNumber") String poNumber,
                              @Bind("description") String description,
                              @Bind("subscriptionNumber") String subscriptionNumber,
                              @Bind("companyId") Long companyId,
                              @Bind("manualPrizeSetting") Long manualPrizeSetting,
                              @Bind("clientId") Long clientId,
                              @Bind("billable") boolean billable);

    /**
     * Get users for given billing account
     *
     * @param billingAccountId the billing account id
     * @param queryParameter sort and filter parameters
     * @return return the users associated with the billing account
     */
    @SqlQueryFile("sql/users/get-users-from-billing-account.sql")
    QueryResult<List<BillingAccountUser>> getBillingAccountUsers(@Bind("billingAccountId") Long billingAccountId,
        @ApiQueryInput QueryParameter queryParameter);

    /**
     * Add user to a billing account
     *
     * @param billingAccountId the billing account id
     * @param userId the user id
     * @param userName the user name (for audit purposes only)
     */
    @SqlUpdateFile("sql/users/add-user-to-billing-account.sql")
    void addUserToBillingAccount(@Bind("billingAccountId") Long billingAccountId,
                                 @Bind("userId") Long userId,
                                 @Bind("userName") String userName);

    /**
     * Remove user from billing account
     *
     * @param billingAccountId the billing account id
     * @param userId the user id
     */
    @SqlUpdateFile("sql/users/remove-user-from-billing-account.sql")
    void removeUserFromBillingAccount(@Bind("billingAccountId") Long billingAccountId, @Bind("userId") Long userId);

    /**
     * Helper method to check if the user with the given handle has a user account.
     *
     * @param handle the user id to check
     * @return The IdDTO instance matching the given handle.
     */
    @SqlQueryFile("sql/users/user-exists.sql")
    IdDTO checkUserExists(@Bind("handle") String handle);

    /**
     * Checks whether the company identified by the specified company id exists in the database.
     *
     * @param companyId The id of the company to check if it exists.
     *
     * @return The IdDTO instance containing the id of the company. ( null of the company does not exist)
     *
     * @since 1.1
     */
    @SqlQueryFile("sql/billing-account/check-company-exists.sql")
    IdDTO checkCompanyExists(@Bind("companyId") Long companyId);

    /**
     * Gets the TCUserDTO from the persistence by id.
     *
     * @param userId The id of the user to retrieve.
     * @return The TC user matching the given id.
     */
    @SqlQueryFile("sql/users/get-tc-user-by-id.sql")
    TCUserDTO getTCUserById(@Bind("userId") Long userId);

    /**
     * Creates a user account using the supplied parameters.
     *
     * @param userAccountId The user account id.
     * @param name The user name.
     * @param userId The id of user creating the account ( for auditing purpose)
     */
    @SqlUpdateFile("sql/users/create-user-account.sql")
    void createUserAccount(@Bind("userAccountId") Long userAccountId,
                           @Bind("name") String name,
                           @Bind("userId") String userId);

    /**
     * Checks whether a user, identified by the user account id, belongs to a billing account.
     *
     * @param billingAccountId The billing account id.
     * @param userAccountId The user account id.
     * @return returns the IdDTO instance containing the id of the project id.
     */
    @SqlQueryFile("sql/users/check-user-belongs-to-billing-account.sql")
    IdDTO checkUserBelongsToBillingAccount(@Bind("billingAccountId") Long billingAccountId,
                                           @Bind("userAccountId") Long userAccountId);

    /**
     * Checks whether the client identified by the specified client id exists in the database.
     *
     * @param clientId The id of the client to check if it exists.
     *
     * @return The IdDTO instance containing the id of the client. ( null if the client does not exist)
     *
     * @since 1.2
     */
    @SqlQueryFile("sql/billing-account/check-client-exists.sql")
    IdDTO checkClientExists(@Bind("clientId") Long clientId);

    /**
     * Creates a mapping between client and billing account.
     *
     * @param userId the user id (for audit purposes only)
     * @param billingAccountId the billing account id
     * @param clientId the id of the client to which the billing account is associated.
     *
     * @since 1.2
     */
    @SqlUpdateFile("sql/billing-account/add-billing-account-to-client.sql")
    void addBillingAccountToClient(
        @Bind("billingAccountId") Long billingAccountId,
        @Bind("clientId") Long clientId,
        @Bind("userId") String userId);

    /**
     * Removes a mapping between client and billing account.
     *
     * @param billingAccountId the billing account id
     *
     * @since 1.2
     */
    @SqlUpdateFile("sql/billing-account/remove-billing-account-from-client.sql")
    void removeBillingAccountFromClient(@Bind("billingAccountId") Long billingAccountId);
}