package com.appirio.service.billingaccount.api;

import com.appirio.supply.dataaccess.api.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Represents a billing account
 *
 * @author TCSCODER
 */
@NoArgsConstructor
@AllArgsConstructor
public class BillingAccount extends BaseModel {
	/**
	 * PK of the billing account
	 */
	@Getter
	@Setter
	private Long id;

	/**
	 * The name of the billing account
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * The status of the billing account
	 */
	@Getter
	@Setter
	private String status;

	/**
	 * The start date of the billing account
	 */
	@Getter
	@Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'", timezone="GMT")
	private Date startDate;

	/**
	 * The end date of the billing account
	 */
	@Getter
	@Setter
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'", timezone="GMT")
	private Date endDate;

	/**
	 * The challenge budget for this billing account.
	 */
	@Getter
	@Setter
	private Float amount;

	/**
	 * The sales tax.
	 */
	@Getter
	@Setter
	@Min(0)
	private Float salesTax;

	/**
	 * The po box number
	 */
	@Getter
	@Setter
	private String poNumber;

	/**
	 * The payment terms
	 */
	@Getter
	@Setter
	@NotNull
	private PaymentTermsDTO paymentTerms;
}
