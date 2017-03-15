/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.api;

import java.util.Date;

import com.appirio.tech.core.api.v3.resource.old.RESTResource;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Client entity. It holds the client related fields.
 *
 *  <p>
 *  Changes in v 1.1 Fast 48hrs!! Topcoder - Improvement For Billing Account Service
 *  -- Added customerNumber field
 * </p>
 *
 * @author TCSCODER
 * @version 1.1
 */
@AllArgsConstructor
@NoArgsConstructor
public class Client implements RESTResource {
	/**
	 * The client id.
	 */
	@Getter
	@Setter
	private Long id;

	/**
	 * The client name.
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * The client status
	 */
	@Getter
	@Setter
	private String status;

	/**
	 * The client start date.
	 */
    @Getter
	@Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'", timezone="GMT")
	private Date startDate;

    /**
     * The client end date.
     */
	@Getter
	@Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'", timezone="GMT")
	private Date endDate;

	/**
	 * The client code name.
	 */
	@Getter
	@Setter
	private String codeName;

	/**
     * The client customer number.
     *
     * @since 1.1
     */
    @Getter
    @Setter
    private String customerNumber;
}