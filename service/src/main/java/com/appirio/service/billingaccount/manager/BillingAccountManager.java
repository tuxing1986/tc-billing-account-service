package com.appirio.service.billingaccount.manager;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
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
 * @author TCSCODER
 */
public class BillingAccountManager {

    /**
     * DAO for the billing accounts
     */
    private BillingAccountDAO billingAccountDAO;

    /**
     * Id generate to create the billing account ids
     */
    private IdGenerator generator;

    /**
     * Constructor for billing account manager
     */
    public BillingAccountManager(BillingAccountDAO billingAccountDAO, IdGenerator generator) {
        this.billingAccountDAO = billingAccountDAO;
        this.generator = generator;
    }

    /**
     * Search for billing accounts
     *
     * @param queryParameter sort and filter parameters
     * @return the billing accounts
     */
    public QueryResult<List<BillingAccount>> searchBillingAccounts(QueryParameter queryParameter) {
        return billingAccountDAO.searchBillingAccounts(queryParameter);
    }

    /**
     * Search for billing accounts limited by the privileges of the logged in user
     *
     * @param userId the user id of the logged in user
     * @param queryParameter sort and filter parameters
     * @return the billing accounts
     */
    public QueryResult<List<BillingAccount>> searchMyBillingAccounts(Long userId, QueryParameter queryParameter) {
        return billingAccountDAO.searchMyBillingAccounts(userId, queryParameter);
    }

    /**
     * Create a billing account
     *
     * @param user the currently logged in user
     * @param billingAccount the billing account to create
     * @return the created billing account
     */
    public QueryResult<List<BillingAccount>> createBillingAccount(AuthUser user, BillingAccount billingAccount) throws SupplyException {
        IdDTO statusId = billingAccountDAO.getStatusIdByName(billingAccount.getStatus());
        if(statusId == null) {
            throw new SupplyException("Couldn't find status name " + billingAccount.getStatus(), 404);
        }
        Long id = generator.getNextId();
        LoggerFactory.getLogger(BillingAccountManager.class).debug("Next ID: " + id);
        billingAccountDAO.createBillingAccount(id, billingAccount.getName(), billingAccount.getPaymentTerms().getId(),
                billingAccount.getStartDate(), billingAccount.getEndDate(), statusId.getId(), user.getName(),
                billingAccount.getSalesTax(), billingAccount.getPoNumber());
        return billingAccountDAO.getBillingAccount(id);
    }

    /**
     * Get a billing account by id
     *
     * @param billingAccountId the id
     * @return the billing account with the given id
     */
    public QueryResult<List<BillingAccount>> getBillingAccount(Long billingAccountId) {
        return billingAccountDAO.getBillingAccount(billingAccountId);
    }

    /**
     * Update a billing account
     *
     * @param user the currently logged in user
     * @param billingAccount the billing account to update
     * @return the updated billing account
     */
    public QueryResult<List<BillingAccount>> updateBillingAccount(AuthUser user, BillingAccount billingAccount) throws SupplyException {
        IdDTO statusId = billingAccountDAO.getStatusIdByName(billingAccount.getStatus());
        if(statusId == null) {
            throw new SupplyException("Couldn't find status name " + billingAccount.getStatus(), 404);
        }
        billingAccountDAO.updateBillingAccount(billingAccount.getId(), billingAccount.getAmount(), billingAccount.getName(),
                billingAccount.getPaymentTerms().getId(), billingAccount.getStartDate(), billingAccount.getEndDate(), statusId.getId(), user.getName(),
                billingAccount.getSalesTax(), billingAccount.getPoNumber());
        return billingAccountDAO.getBillingAccount(billingAccount.getId());
    }

    /**
     * Get users for given billing account
     *
     * @param billingAccountId the billing account id
     * @return return the users associated with the billing account
     */
    public QueryResult<List<BillingAccountUser>> getBillingAccountUsers(Long billingAccountId) {
        return billingAccountDAO.getBillingAccountUsers(billingAccountId);
    }

    /**
     * Add user to a billing account
     *
     * @param billingAccountId the billing account id
     * @param userId the user id
     * @param user the user (for audit purposes only)
     */
    public QueryResult<List<BillingAccount>> addUserToBillingAccount(AuthUser user, Long billingAccountId, Long userId) throws SupplyException {
        IdDTO id = billingAccountDAO.checkUserExists(userId);
        if(id == null) {
            throw new SupplyException("User with the following id doesn't exist " + userId, 404);
        }
        List<BillingAccountUser> existingUsers = billingAccountDAO.getBillingAccountUsers(billingAccountId).getData();
        for(BillingAccountUser baUser: existingUsers) {
            if(baUser.getId().equals(userId)) {
                throw new SupplyException("The user with id " + userId + " already belongs to this id", 400);
            }
        }
        billingAccountDAO.addUserToBillingAccount(billingAccountId, userId, user.getName());
        return billingAccountDAO.getBillingAccount(billingAccountId);
    }

    /**
     * Remove user from billing account
     *
     * @param billingAccountId the billing account id
     * @param userId the user id
     */
    public void removeUserFromBillingAccount(Long billingAccountId, Long userId) {
        billingAccountDAO.removeUserFromBillingAccount(billingAccountId, userId);
    }
}