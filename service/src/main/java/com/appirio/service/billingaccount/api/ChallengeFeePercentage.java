/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.api;

import com.appirio.supply.dataaccess.api.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class represents project contest fee percentage.
 * </p>
 * <p>
 * It's added in Topcoder - Create Challenge Fee Management APIs For Billing Accounts v1.0
 * </p>
 * 
 * @author TCCoder
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeFeePercentage extends BaseModel {
    /**
     * <p>
     * The id.
     * </p>
     */
    @Getter
    @Setter
    private long id;

    /**
     * <p>
     * The project id.
     * </p>
     */
    @Getter
    @Setter
    private long projectId;

    /**
     * <p>
     * The contest fee percentage.
     * </p>
     */
    @Getter
    @Setter
    private double challengeFeePercentage;

    /**
     * <p>
     * The active flag.
     * </p>
     */
    @Getter
    @Setter
    private boolean active;
}
