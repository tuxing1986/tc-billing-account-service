/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.dao;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;

import com.appirio.service.billingaccount.api.Client;
import com.appirio.supply.dataaccess.DatasourceName;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.SqlQueryFile;
import com.appirio.supply.dataaccess.SqlUpdateFile;

/**
 * DAO to handle clients in the persistence.
 *
 * @author TCSCODER
 * @version 1.0
 */
@DatasourceName("oltp")
public interface ClientDAO {
    
	/**
     * Gets all client from the persistence.
     *
     * @return All the clients from the persistence.
     */
    @SqlQueryFile("sql/client/find-all-clients.sql")
    QueryResult<List<Client>> findAllClients();
    
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
    		@Bind("userId") String userId);

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
    		@Bind("userId") String userId);
    
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