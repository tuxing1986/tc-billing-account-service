package com.appirio.service.billingaccount.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents payment terms
 *
 * @author TCSCODER
 */
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsDTO {

    /**
     * ID of the payment terms
     */
    @Getter
    @Setter
    private Long id;

    /**
     * Description of the payment terms
     */
    @Getter
    @Setter
    private String description;
}
