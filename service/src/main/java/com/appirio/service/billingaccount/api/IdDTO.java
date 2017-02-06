package com.appirio.service.billingaccount.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO to receive IDs from the database
 *
 * @author TCSCODER
 */
@NoArgsConstructor
@AllArgsConstructor
public class IdDTO {

    /**
     * The queried id
     */
    @Getter
    @Setter
    private Long id;
}
