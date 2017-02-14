/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.manager;

/**
 * This is the parent class of all the managers defined in this service.
 * It defines a helper method for getting the active flag from the status name.
 * 
 * @author TCSCODER
 * @version 1.0
 */
public class BaseManager {

    /**
     * The 'Active' status name.
     */
    private static final String ACTIVE_STATUS_NAME = "Active";
    
    /**
     * The 'Inactive' status name.
     */
    private static final String INACTIVE_STATUS_NAME = "Inactive";
    
    /**
     * Gets the active flag value by status name.
     * 
     * @param resourceName The resource name.
     * @param status The status name.
     * 
     * @return The active flag ( 1- Active, 0 - Inactive)
     */
    protected Long getActiveFlagFromStatusName(String resourceName, String status) {

    	if(!status.equalsIgnoreCase(ACTIVE_STATUS_NAME) && !status.equalsIgnoreCase(INACTIVE_STATUS_NAME)) {
        	throw new IllegalArgumentException(String.format("The specified %s status '%s' is invalid, "
        			+ "valid status values are : '%s' or '%s'", resourceName, status,
        			ACTIVE_STATUS_NAME, INACTIVE_STATUS_NAME));
        }
        // Get the active flag value to send to the backend DAO.
        // 1 - Active
        // 0 - Inactive
        return status.equalsIgnoreCase(ACTIVE_STATUS_NAME) ? 1L : 0L;
    }
}