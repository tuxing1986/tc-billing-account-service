/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount;

import com.appirio.service.BaseAppConfiguration;
import com.appirio.service.supply.resources.SupplyDatasourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Billing account service configuration
 *
 * @author TCSCODER
 */
public class BillingAccountServiceConfiguration extends BaseAppConfiguration {

	/**
	 * Datasources
	 */
	@Valid
	@NotNull
	@JsonProperty
	private SupplyDatasourceFactory database;

	/**
	 * Get the data source factory
	 *
	 * @return Data source factory
	 */
	public SupplyDatasourceFactory getDatabase() {
		return database;
	}
}
