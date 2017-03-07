/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This DTO hold the information of a TC user ( id and TC handle).
 * 
 * @author TCSCODER
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class TCUserDTO {

	/**
	 * The TC user id.
	 */
    @Getter
    @Setter
    private Long id;
    
    
    /**
     * The tc user handle.
     */
    @Getter
    @Setter
    private String handle;
}