/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.manager;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
import com.appirio.service.billingaccount.dto.TCUserDTO;
import com.appirio.supply.SupplyException;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.db.IdGenerator;
import com.appirio.tech.core.api.v3.request.QueryParameter;
import com.appirio.tech.core.auth.AuthUser;

import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Manager for billing account business logic
 * 
 * <p>
 * Changes in v1.1 FAST 72HRS!! - ADD APIS FOR CLIENTS AND SOME LOGIC CHANGES 
 * -- Updated createBillingAccount() to pass additional parameters (description, subscriptionNumber , companyId and 
 *    manual prize setting) to the billingAccountDAO.createBillingAccount(). 
 * -- Added validateCompanyId() to validate the input company id. 
 * -- Updated the business logic to use userId instead of user handle for auditing purpose.
 * -- changed to extend from BaseResource to gain access to the helper method for converting the status to 'active' flag value.
 * -- Added userAccountIdGenerator reference to be used to generate the ids of the new user accounts.
 * </p>
 * 
 * @author TCSCODER, TCSCODER
 * @version 1.1
 */
public class BillingAccountManager extends BaseManager {

    /**
     * DAO for the billing accounts
     */
    private BillingAccountDAO billingAccountDAO;

    /**
     * Id generator to create the billing account ids
     */
    private IdGenerator billingAccountIdGenerator;

    /**
     * The Id Generator to be used to generate user account ids.
     */
    private IdGenerator userAccountIdGenerator;

    /**
     * Constructor for billing account manager
     * 
     * @param billingAccountDAO
     *            The billing account DAO.
     * @param billingAccountIdGenerator
     *            The billing account id generator.
     * @param userAccountIdGenerator
     *            The user account id generator.
     */
    public BillingAccountManager(BillingAccountDAO billingAccountDAO, IdGenerator billingAccountIdGenerator,
            IdGenerator userAccountIdGenerator) {
        this.billingAccountDAO = billingAccountDAO;
        this.billingAccountIdGenerator = billingAccountIdGenerator;
        this.userAccountIdGenerator = userAccountIdGenerator;
    }

    /**
     * Search for billing accounts
     * 
     * @param queryParameter
     *            sort and filter parameters
     * @return the billing accounts
     */
    public QueryResult<List<BillingAccount>> searchBillingAccounts(QueryParameter queryParameter) {
        return billingAccountDAO.searchBillingAccounts(queryParameter);
    }

    /**
     * Search for billing accounts limited by the privileges of the logged in user
     * 
     * @param userId
     *            the user id of the logged in user
     * @param queryParameter
     *            sort and filter parameters
     * @return the billing accounts
     */
    public QueryResult<List<BillingAccount>> searchMyBillingAccounts(Long userId, QueryParameter queryParameter) {
        return billingAccountDAO.searchMyBillingAccounts(userId, queryParameter);
    }

    /**
     * Create a billing account
     * 
     * @param user
     *            the currently logged in user
     * @param billingAccount
     *            the billing account to create
     * @return the created billing account
     */
    public QueryResult<List<BillingAccount>> createBillingAccount(AuthUser user, BillingAccount billingAccount)
            throws SupplyException {

        // validate the specified status and get the corresponding active flag
        Long activeFlag = getActiveFlagFromStatusName("billing account", billingAccount.getStatus());

        // validate the provided companyId
        validateCompanyId(billingAccount.getCompanyId());

        Long id = billingAccountIdGenerator.getNextId();
        LoggerFactory.getLogger(BillingAccountManager.class).debug("Next ID: " + id);
        billingAccountDAO.createBillingAccount(id, billingAccount.getName(), billingAccount.getPaymentTerms().getId(),
                billingAccount.getStartDate(), billingAccount.getEndDate(), activeFlag, user.getUserId().toString(),
                billingAccount.getSalesTax(), billingAccount.getPoNumber(), billingAccount.getDescription(),
                billingAccount.getSubscriptionNumber(), billingAccount.getCompanyId(),
                billingAccount.getManualPrizeSetting());
        return billingAccountDAO.getBillingAccount(id);
    }

    /**
     * Get a billing account by id
     * 
     * @param billingAccountId
     *            the id
     * @return the billing account with the given id
     */
    public QueryResult<List<BillingAccount>> getBillingAccount(Long billingAccountId) {
        return billingAccountDAO.getBillingAccount(billingAccountId);
    }

    /**
     * Update a billing account
     * 
     * @param user
     *            the currently logged in user
     * @param billingAccount
     *            the billing account to update
     * @return the updated billing account
     */
    public QueryResult<List<BillingAccount>> updateBillingAccount(AuthUser user, BillingAccount billingAccount)
            throws SupplyException {

        // Validate the billing account status and get the corresponding active flag value.
        Long activeFlag = getActiveFlagFromStatusName("billing account", billingAccount.getStatus());

        // validate the provided companyId
        validateCompanyId(billingAccount.getCompanyId());

        billingAccountDAO.updateBillingAccount(billingAccount.getId(), billingAccount.getAmount(),
                billingAccount.getName(), billingAccount.getPaymentTerms().getId(), billingAccount.getStartDate(),
                billingAccount.getEndDate(), activeFlag, user.getUserId().getId(), billingAccount.getSalesTax(),
                billingAccount.getPoNumber(), billingAccount.getDescription(), billingAccount.getSubscriptionNumber(),
                billingAccount.getCompanyId(), billingAccount.getManualPrizeSetting());
        return billingAccountDAO.getBillingAccount(billingAccount.getId());
    }

    /**
     * Get users for given billing account
     * 
     * @param billingAccountId
     *            the billing account id
     * @return return the users associated with the billing account
     */
    public QueryResult<List<BillingAccountUser>> getBillingAccountUsers(Long billingAccountId) {
        return billingAccountDAO.getBillingAccountUsers(billingAccountId);
    }

    /**
     * Add user to a billing account
     * 
     * @param billingAccountId
     *            the billing account id
     * @param userId
     *            the user id
     * @param user
     *            the user (for audit purposes only)
     */
    public QueryResult<List<BillingAccount>> addUserToBillingAccount(AuthUser user, Long billingAccountId, Long userId)
            throws SupplyException {

        // Check if the user id exists
        TCUserDTO tcUser = billingAccountDAO.getTCUserById(userId);
        if (tcUser == null) {
            throw new SupplyException("User with the following id doesn't exist " + userId, 404);
        }

        // Check if the user has a user account.
        IdDTO id = billingAccountDAO.checkUserExists(tcUser.getHandle());
        Long userAccountId = null;

        if (id == null) { // The user does not have a user account, then we should create one.
            userAccountId = userAccountIdGenerator.getNextId();
            billingAccountDAO.createUserAccount(userAccountId, tcUser.getHandle(), user.getUserId().toString());
        } else { // The user has a user account, we need to check if he already belongs to the billing account.
            userAccountId = id.getId();
            IdDTO projectManagerId = billingAccountDAO.checkUserBelongsToBillingAccount(billingAccountId,
                    userAccountId);
            if (projectManagerId != null) {
                throw new SupplyException("The user { 'id': " + userId + ", 'handle' : " + tcUser.getHandle() + "} "
                        + "already belongs to this billing account", 400);
            }
        }
        billingAccountDAO.addUserToBillingAccount(billingAccountId, userAccountId, user.getUserId().toString());
        return billingAccountDAO.getBillingAccount(billingAccountId);
    }

    /**
     * Remove user from billing account
     * 
     * @param billingAccountId
     *            the billing account id
     * @param userId
     *            the user id
     */
    public void removeUserFromBillingAccount(Long billingAccountId, Long userId) throws SupplyException {
        // Check if the user id exists
        TCUserDTO tcUser = billingAccountDAO.getTCUserById(userId);
        if (tcUser == null) {
            throw new SupplyException("User with the following id doesn't exist " + userId, 404);
        }

        //
        IdDTO id = billingAccountDAO.checkUserExists(tcUser.getHandle());
        Long userAccountId = null;

        if (id == null) { // The user does not even have a user account
            throw new SupplyException(String.format("The user {'id':'%s', 'handle':'/%s'} does not even have "
                    + "a user account.", userId, tcUser.getHandle()));
        } else { // The user has a user account, we need to check if he already belongs to the billing account.
            userAccountId = id.getId();
            IdDTO projectManagerId = billingAccountDAO.checkUserBelongsToBillingAccount(billingAccountId,
                    userAccountId);
            if (projectManagerId == null) {
                throw new SupplyException("The user { 'id': " + userId + ", 'handle' : " + tcUser.getHandle() + "} "
                        + "does not belong to this billing account", 400);
            }
        }

        // If all checks succeed, then remove the user from the billing account
        billingAccountDAO.removeUserFromBillingAccount(billingAccountId, userAccountId);
    }

    /**
     * Checks whether company identified by the given id exists in the persistence.
     * 
     * @param companyId
     *            The id of the company to check if it exists
     */
    private void validateCompanyId(Long companyId) {
        IdDTO id = billingAccountDAO.checkCompanyExists(companyId);
        if (id == null) {
            throw new IllegalArgumentException(String.format(
                    "The company identified by id = %s, " + "does not exist.", companyId));
        }
    }
}