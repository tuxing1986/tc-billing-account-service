package com.appirio.service.billingaccount.api;

import com.appirio.tech.core.api.v3.resource.old.RESTResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for update billing account response
 *
 * @author TCSCODER
 */
@NoArgsConstructor
@AllArgsConstructor
public class BillingAccountUpdatedDTO implements RESTResource {

    /**
     * The original billing account
     */
    @Getter
    @Setter
    private BillingAccount original;

    /**
     * The updated billing account
     */
    @Getter
    @Setter
    private BillingAccount updated;
}
