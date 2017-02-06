package com.appirio.service.billingaccount.api;

import com.appirio.tech.core.api.v3.resource.old.RESTResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User associated with a billing account.
 *
 * @author TCSCODER
 */
@NoArgsConstructor
@AllArgsConstructor
public class BillingAccountUser implements RESTResource {
    /**
     * The userId
     */
    @Getter
    @Setter
    private Long id;

    /**
     * The user name
     */
    @Getter
    @Setter
    private String name;

    /**
     * The description
     */
    @Getter
    @Setter
    private String description;
}
