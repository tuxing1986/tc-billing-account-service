/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.appirio.service.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
import com.appirio.service.billingaccount.api.PaymentTermsDTO;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
import com.appirio.supply.dataaccess.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skife.jdbi.v2.Query;

import com.appirio.supply.SupplyException;

/**
 * Test BillingAccountDAO.
 * <p>
 *  Changes in v 1.1 "FAST 72HRS!! - ADD APIS FOR CLIENTS AND SOME LOGIC CHANGES"
 *   -- Updated to take into consideration the newly added fields to BliingAccount.
 * </p>
 *
 *  <p>
 *  Changes in v 1.2 Fast 48hrs!! Topcoder - Improvement For Billing Account Service
 *  -- Updated to take into consideration the newly added clientId field.
 * </p>
 *
 * @author TCSCODER, TCSCODER.
 * @version 1.2
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Query.class)
public class BillingAccountDAOTest extends GenericDAOTest {

    private BillingAccountDAO dao;

    /**
     * Setup the jdbi instance and required data
     *
     * @throws SupplyException Exception for supply
     */
    @Before
    public void before() throws SupplyException {
        List<BillingAccount> billingAccounts = new ArrayList<>();

   billingAccounts.add(new BillingAccount(1l, "1", "Active", new Date(), new Date(), 500.0f, 1.0f, "po1",
   		new PaymentTermsDTO(1l, "30 Days"), "description1", "subscription#1", 1l, 0l, 1l, false ));
   billingAccounts.add(new BillingAccount(2l, "2", "Active", new Date(), new Date(), 500.0f, 1.0f, "po2",
   		new PaymentTermsDTO(1l, "30 Days"), "description2", "subscription#2", 1l, 0l, 1l, false));

        List<Map<String, Object>> unmappedData = new ArrayList<Map<String, Object>>();
        unmappedData.add(new HashMap<>());
        unmappedData.get(0).put("ct", new BigDecimal(2));

        dao = createDAO(billingAccounts, unmappedData, BillingAccountDAO.class);
    }

    @Test
    public void testSearchBillingAccounts() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.searchBillingAccounts(createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/search-billing-accounts.sql", 0);
    }

    @Test
    public void testSearchMyBillingAccounts() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.searchMyBillingAccounts(1l, createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/search-my-billing-accounts.sql", 0);
    }

    @Test
    public void testCreateBillingAccount() throws IOException {
        // Invoke method
        dao.createBillingAccount(1l, 500.0f, "testNew", 1l,new Date(), new Date(), 1l, "test", 1.0f, "po1","desc1","subscr1", 1l, 0l, 1l, false);

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/create-billing-account.sql", 0);
    }

    @Test
    public void testGetBillingAccount() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccount>> billingAccounts = dao.getBillingAccount(1l);

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/billing-account/get-billing-account-by-id.sql", 0);
    }

    @Test
    public void testGetBillingAccountUsers() throws IOException {
        // Invoke method
        QueryResult<List<BillingAccountUser>> billingAccounts = dao.getBillingAccountUsers(1l, createQueryParam(""));

        // Verify result
        assertNotNull(billingAccounts);
        assertEquals(billingAccounts.getData().size(), 2);

        // Verify that JDBI was called
        verifyListObjectWithMetadataQuery(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/get-users-from-billing-account.sql", 0);
    }

    @Test
    public void testAddUserToBillingAccount() throws IOException {
        // Invoke method
        dao.addUserToBillingAccount(1l, 1l, "test");

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/add-user-to-billing-account.sql", 0);
    }

    @Test
    public void testRemoveUserFromBillingAccount() throws IOException {
        // Invoke method
        dao.removeUserFromBillingAccount(1l, 1l);

        // Verify that JDBI was called
        verifySingleUpdate(mocker);

        // Verify that the generated SQL file is as expected
        verifyGeneratedSQL(mocker, "expected-sql/users/remove-user-from-billing-account.sql", 0);
    }
}
