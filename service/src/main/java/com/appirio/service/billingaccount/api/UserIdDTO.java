package com.appirio.service.billingaccount.api;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to receive an userId from the request body
 *
 * @author TCSCODER
 */
public class UserIdDTO {

    /**
     * The received userId
     */
    @Getter
    @Setter
    private Long userId;
}
