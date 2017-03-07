/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.billingaccount.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This DTO holds the information needed for creating/updating a user.
 *  
 * @author TCSCODER.
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class SaveClientDTO {
	
	/**
	 * The client name.
	 */
	@Getter
	@Setter
	@NotBlank
	private String name;
	
	/**
	 * The client status.
	 */
	@Getter
	@Setter
	@NotBlank
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
	@NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'", timezone="GMT")
	private Date endDate;
	
	/**
	 * The client code name.
	 */
	@Getter
	@Setter
	private String codeName;
}