/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.dao;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;

import com.appirio.service.billingaccount.api.Client;
import com.appirio.supply.dataaccess.ApiQueryInput;
import com.appirio.supply.dataaccess.DatasourceName;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.SqlQueryFile;
import com.appirio.supply.dataaccess.SqlUpdateFile;
import com.appirio.tech.core.api.v3.request.QueryParameter;

/**
 * DAO to handle clients in the persistence.
 *
 * <p>
 *  Changes in v 1.1 Fast 48hrs!! Topcoder - Improvement For Billing Account Service
 *  -- Added sort, limit and offset when finding all clients
 *  -- Added support for filtering on name (partial match), status, start date, and end date
 * </p>
 *
 * @author TCSCODER
 * @version 1.1
 */
@DatasourceName("oltp")
public interface ClientDAO {

	/**
     * Gets all client from the persistence.
	 * @param queryParameter
     *
     * @return All the clients from the persistence.
     */
    @SqlQueryFile("sql/client/find-all-clients.sql")
    QueryResult<List<Client>> findAllClients(@ApiQueryInput QueryParameter queryParameter);

    /**
     * Creates a new client in the persistence.
     *
     * @param id The client id.
     * @param name The client name.
     * @param status The client status.
     * @param startDate The client start date.
     * @param endDate The client end date.
     * @param codeName The client code name.
     * @param userId The id of the user who creates the client (for auditing purpose).
     */
    @SqlUpdateFile("sql/client/create-client.sql")
    void addNewClient(
    		@Bind("id") Long id, @Bind("name") String name,
    		@Bind("status") Long status, @Bind("startDate") Date startDate,
    		@Bind("endDate") Date endDate, @Bind("codeName") String codeName,
    		@Bind("userId") String userId, @Bind("customerNumber") String customerNumber);

    /**
     * Updates an existing client.
     * @param id The client id.
     * @param name The client name.
     * @param status  The client status.
     * @param startDate The client start date.
     * @param endDate  The client end date.
     * @param codeName The client code name.
     * @param userId  The id of the user whi updates the client (for auditing purpose).
     */
    @SqlUpdateFile("sql/client/update-client.sql")
    void updateClient(
    		@Bind("id") Long id, @Bind("name") String name,
    		@Bind("status") Long status, @Bind("startDate") Date startDate,
    		@Bind("endDate") Date endDate, @Bind("codeName") String codeName,
    		@Bind("userId") String userId, @Bind("customerNumber") String customerNumber);

    /**
     * Gets the client identified by the specified id from the persistence.
     *
     * @param clientId  The id of the client to retrieve.
     * @return The matched Client.
     */
    @SqlQueryFile("sql/client/get-client-by-id.sql")
    Client getClientById(@Bind("clientId") Long clientId);

    /**
     * Gets the client by name from the persistence.
     *
     * @param clientName The client name to search for.
     * @return The matched Client instance.
     */
    @SqlQueryFile("sql/client/get-client-by-name.sql")
    Client getClientByName(@Bind("clientName") String clientName);
}