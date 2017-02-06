package com.appirio.service.billingaccount.dao;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
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
 * DAO to handle billing accounts
 *
 * @author TCSCODER
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
     * @param startDate the start date
     * @param endDate the end date
     * @param statusId the status id
     * @param userName the user name (for audit purposes only)
     * @return the id of the created billing account
     */
    @SqlUpdateFile("sql/billing-account/create-billing-account.sql")
    Long createBillingAccount(@Bind("projectId") Long projectId,
                              @Bind("name") String name, @Bind("paymentTermId") Long paymentTermId,
                              @Bind("startDate") Date startDate, @Bind("endDate") Date endDate,
                              @Bind("statusId") Long statusId, @Bind("userName") String userName,
                              @Bind("salesTax") Float salesTax, @Bind("poNumber") String poNumber);

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
     * @param amount the budged of the account
     * @param name the name of the account
     * @param startDate the start date
     * @param endDate the end date
     * @param statusId the status id
     * @param userName the user name (for audit purposes only)
     */
    @SqlUpdateFile("sql/billing-account/update-billing-account.sql")
    void updateBillingAccount(@Bind("billingAccountId") Long billingAccountId, @Bind("amount") Float amount,
                              @Bind("name") String name, @Bind("paymentTermId") Long paymentTermId,
                              @Bind("startDate") Date startDate, @Bind("endDate") Date endDate,
                              @Bind("statusId") Long statusId, @Bind("userName") String userName,
                              @Bind("salesTax") Float salesTax, @Bind("poNumber") String poNumber);

    /**
     * Get users for given billing account
     *
     * @param billingAccountId the billing account id
     * @return return the users associated with the billing account
     */
    @SqlQueryFile("sql/users/get-users-from-billing-account.sql")
    QueryResult<List<BillingAccountUser>> getBillingAccountUsers(@Bind("billingAccountId") Long billingAccountId);

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
     * Helper method to check if a user with the given id exists.
     *
     * @param userId the user id to check
     * @return the client id
     */
    @SqlQueryFile("sql/users/user-exists.sql")
    IdDTO checkUserExists(@Bind("userId") Long userId);

    /**
     * Helper method to get the status id for a status name.
     *
     * @param status the status name
     * @return the status id
     */
    @SqlQueryFile("sql/billing-account/get-status-id-by-name.sql")
    IdDTO getStatusIdByName(@Bind("name") String status);
}