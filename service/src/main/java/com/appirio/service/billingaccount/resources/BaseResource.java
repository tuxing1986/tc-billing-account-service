/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.resources;

import com.appirio.supply.SupplyException;
import com.appirio.tech.core.auth.AuthUser;

/**
 * This is the base class of all resources defined in this service. It provides a helper method to check whether the
 * authenticated user is an administrator.
 * 
 * @author TCSCODER.
 * @version 1.0
 */
public class BaseResource {

    /**
     * Checks if the user is an administrator.
     * 
     * @param user
     *            The user to check if he is an administrator.
     */
    protected void checkAdmin(AuthUser user) throws SupplyException {
        if (!user.hasRole("administrator")) {
            throw new SupplyException("Only administrators can access this resource.", 403);
        }
    }
}