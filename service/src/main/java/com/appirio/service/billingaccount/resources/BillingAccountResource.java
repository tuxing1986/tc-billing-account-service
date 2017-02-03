package com.appirio.service.billingaccount.resources;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUpdatedDTO;
import com.appirio.service.billingaccount.api.UserIdDTO;
import com.appirio.service.billingaccount.manager.BillingAccountManager;
import com.appirio.service.supply.resources.MetadataApiResponseFactory;
import com.appirio.supply.ErrorHandler;
import com.appirio.supply.SupplyException;
import com.appirio.tech.core.api.v3.request.OrderByQuery;
import com.appirio.tech.core.api.v3.request.QueryParameter;
import com.appirio.tech.core.api.v3.request.annotation.APIQueryParam;
import com.appirio.tech.core.api.v3.response.ApiResponse;
import com.appirio.tech.core.api.v3.response.ApiResponseFactory;
import com.appirio.tech.core.auth.AuthUser;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * REST resource endpoint for billing accounts
 *
 * @author TCSCODER
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillingAccountResource {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BillingAccountResource.class);

    private static final String START_DATE = "startDate";

    private static final String END_DATE = "endDate";

    /**
     * billing account manager
     */
    private BillingAccountManager billingAccountManager;

    /**
     * Date formatter to parse the date filters
     */
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    /**
     * Constructor to initialize billing account manager
     * @param billingAccountManager manager for billing accounts
     */
    public BillingAccountResource(BillingAccountManager billingAccountManager){
        this.billingAccountManager = billingAccountManager;
    }

    /**
     * Search for billing accounts.
     *
     * @param user the currently logged in user
     * @param queryParameter the query parameters
     * @param sort the sort column
     * @return the api response
     */
    @GET
    @Path("billingAccounts")
    public ApiResponse searchBillingAccounts(@Auth AuthUser user,
                                             @APIQueryParam(repClass = BillingAccount.class) QueryParameter queryParameter,
                                             @QueryParam("sort") String sort) {
        try {
            checkAdmin(user);
            prepareParameters(queryParameter, sort);
            return MetadataApiResponseFactory.createResponse(billingAccountManager.searchBillingAccounts(queryParameter));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Create a new billing account.
     *
     * @param user the currently logged in user
     * @param request the billing account to create
     * @return the api response
     */
    @POST
    @Path("billingAccounts")
    public ApiResponse createBillingAccount(@Auth AuthUser user, @Valid BillingAccount request) {
        try {
            checkAdmin(user);
            return MetadataApiResponseFactory.createResponse(billingAccountManager.createBillingAccount(user, request));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Get a billing account by id.
     *
     * @param user the currently logged in user
     * @param billingAccountId the billing account id
     * @return the api response
     */
    @GET
    @Path("billingAccounts/{billingAccountId}")
    public ApiResponse getBillingAccountsById(@Auth AuthUser user, @PathParam("billingAccountId") Long billingAccountId) {
        try {
            checkAdmin(user);
            List<BillingAccount> response = getBillingAccounts(billingAccountId);
            return ApiResponseFactory.createResponse(response.get(0));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Update a billing account.
     *
     * @param user the currently logged in user
     * @param billingAccountId the billing account id
     * @param request the billing account to update
     * @return the api response
     */
    @PATCH
    @Path("billingAccounts/{billingAccountId}")
    public ApiResponse updateBillingAccount(@Auth AuthUser user, @PathParam("billingAccountId") Long billingAccountId,
                                            @Valid BillingAccount request) {
        try {
            checkAdmin(user);
            List<BillingAccount> originals = getBillingAccounts(billingAccountId);
            BillingAccount original = originals.get(0);
            // make sure to update the correct billing account
            request.setId(billingAccountId);
            BillingAccount updated = billingAccountManager.updateBillingAccount(user, request).getData().get(0);
            BillingAccountUpdatedDTO response = new BillingAccountUpdatedDTO(original, updated);
            return ApiResponseFactory.createResponse(response);
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Get users for a billing account.
     *
     * @param user the currently logged in user
     * @param billingAccountId the billing account id
     * @return the api response
     */
    @GET
    @Path("billingAccounts/{billingAccountId}/users")
    public ApiResponse getBillingAccountUsers(@Auth AuthUser user, @PathParam("billingAccountId") Long billingAccountId) {
        try {
            checkAdmin(user);
            getBillingAccounts(billingAccountId);
            return MetadataApiResponseFactory.createResponse(billingAccountManager.getBillingAccountUsers(billingAccountId));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Add user to a billing account.
     *
     * @param user the currently logged in user
     * @param billingAccountId the billing account id
     * @param request the user id
     * @return the api response
     */
    @POST
    @Path("billingAccounts/{billingAccountId}/users")
    public ApiResponse addUserToBillingAccount(@Auth AuthUser user, @PathParam("billingAccountId") Long billingAccountId, @Valid UserIdDTO request) {
        try {
            checkAdmin(user);
            getBillingAccounts(billingAccountId);
            return MetadataApiResponseFactory.createResponse(billingAccountManager.addUserToBillingAccount(user, billingAccountId, request.getUserId()));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Remove user from a billing account.
     *
     * @param user the currently logged in user
     * @param billingAccountId the billing account id
     * @param userId the user id
     * @return the api response
     */
    @DELETE
    @Path("billingAccounts/{billingAccountId}/users/{userId}")
    public ApiResponse removeUserFromBillingAccount(@Auth AuthUser user, @PathParam("billingAccountId") Long billingAccountId, @PathParam("userId") Long userId) {
        try {
            checkAdmin(user);
            getBillingAccounts(billingAccountId);
            billingAccountManager.removeUserFromBillingAccount(billingAccountId, userId);
            ApiResponse result = new ApiResponse();
            result.setResult(true, 204, null);
            return result;
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Search for billing accounts restricted to the logged in user.
     *
     * @param user the currently logged in user
     * @param queryParameter the query parameters
     * @param sort the sort column
     * @return the api response
     */
    @GET
    @Path("me/billingAccounts")
    public ApiResponse searchMyBillingAccounts(@Auth AuthUser user,
                                               @APIQueryParam(repClass = BillingAccount.class) QueryParameter queryParameter,
                                               @QueryParam("sort") String sort) {
        try {
            prepareParameters(queryParameter, sort);
            return MetadataApiResponseFactory.createResponse(billingAccountManager.searchMyBillingAccounts(Long.parseLong(user.getUserId().getId()), queryParameter));
        } catch (Exception e) {
            return ErrorHandler.handle(e, logger);
        }
    }

    /**
     * Prepare date filters and sort column to make it usable by the DAO.
     *
     * @param queryParameter the query parameters
     * @param sort the sort column
     */
    private void prepareParameters(QueryParameter queryParameter, String sort) throws ParseException {
        if(sort != null) {
            queryParameter.setOrderByQuery(OrderByQuery.instanceFromRaw(sort));
        }
        if(queryParameter.getFilter().getFields().contains(START_DATE)) {
            queryParameter.getFilter().put(START_DATE, formatter.parse(queryParameter.getFilter().get(START_DATE).toString()).getTime()/1000);
        }
        if(queryParameter.getFilter().getFields().contains(END_DATE)) {
            queryParameter.getFilter().put(END_DATE, formatter.parse(queryParameter.getFilter().get(END_DATE).toString()).getTime()/1000);
        }
    }

    /**
     * Get billing account by id. Can either be used to verify that an account exists or to get the original billing account.
     *
     * @param billingAccountId the billing account id
     */
    private List<BillingAccount> getBillingAccounts(Long billingAccountId) throws SupplyException {
        List<BillingAccount> originals = billingAccountManager.getBillingAccount(billingAccountId).getData();
        if(originals.size() == 0) {
            throw new SupplyException("Couldn't find billing account with id " + billingAccountId, 404);
        }
        return originals;
    }

    /**
     *  Checks if the user is an administrator.
     *
     * @param user the currently logged in user
     */
    private void checkAdmin(AuthUser user) throws SupplyException {
        if(!user.hasRole("administrator")) {
            throw new SupplyException("Only administrators can access this method", 403);
        }
    }
}
