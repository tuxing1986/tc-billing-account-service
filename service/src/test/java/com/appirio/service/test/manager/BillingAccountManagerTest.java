package com.appirio.service.test.manager;

import com.appirio.service.billingaccount.api.BillingAccount;
import com.appirio.service.billingaccount.api.BillingAccountUser;
import com.appirio.service.billingaccount.api.IdDTO;
import com.appirio.service.billingaccount.api.PaymentTermsDTO;
import com.appirio.service.billingaccount.dao.BillingAccountDAO;
import com.appirio.service.billingaccount.manager.BillingAccountManager;
import com.appirio.service.test.dao.GenericDAOTest;
import com.appirio.supply.SupplyException;
import com.appirio.supply.dataaccess.QueryResult;
import com.appirio.supply.dataaccess.db.IdGenerator;
import com.appirio.tech.core.auth.AuthUser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class BillingAccountManagerTest extends GenericDAOTest {
    private BillingAccountDAO billingAccountDAO = mock(BillingAccountDAO.class);
    private IdGenerator generator = mock(IdGenerator.class);
    private BillingAccountManager unit = new BillingAccountManager(billingAccountDAO, generator);

    @Test
    public void testSearchBillingAccounts() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        when(billingAccountDAO.searchBillingAccounts(anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.searchBillingAccounts(createQueryParam(""));
        assertEquals(2, result.getData().size());
        verify(billingAccountDAO).searchBillingAccounts(anyObject());
    }

    @Test
    public void testSearchMyBillingAccounts() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        when(billingAccountDAO.searchMyBillingAccounts(anyObject(), anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.searchMyBillingAccounts(1l, createQueryParam(""));
        assertEquals(2, result.getData().size());
        verify(billingAccountDAO).searchMyBillingAccounts(anyObject(), anyObject());
    }

    @Test
    public void testCreateBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        when(billingAccountDAO.createBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject())).thenReturn(1l);
        when(billingAccountDAO.getStatusIdByName(anyObject())).thenReturn(new IdDTO(1l));
        when(generator.getNextId()).thenReturn(1l);

        BillingAccount test = new BillingAccount();
        test.setPaymentTerms(new PaymentTermsDTO());
        QueryResult<List<BillingAccount>> result = unit.createBillingAccount(new AuthUser(), test);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).getStatusIdByName(anyObject());
        verify(generator).getNextId();
        verify(billingAccountDAO).createBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject());
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }

    @Test
    public void testGetBillingAccount() {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccount>> result = unit.getBillingAccount(1l);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }

    @Test
    public void testUpdateBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        when(billingAccountDAO.getStatusIdByName(anyObject())).thenReturn(new IdDTO(1l));

        BillingAccount test = new BillingAccount();
        test.setPaymentTerms(new PaymentTermsDTO());
        QueryResult<List<BillingAccount>> result = unit.updateBillingAccount(new AuthUser(), test);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).getStatusIdByName(anyObject());
        verify(billingAccountDAO).updateBillingAccount(anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject(),
                anyObject());
        verify(billingAccountDAO).getBillingAccount(anyObject());
    }

    @Test
    public void testGetBillingAccountUsers() {
        QueryResult<List<BillingAccountUser>> expected = new QueryResult<>(new ArrayList<>());
        when(billingAccountDAO.getBillingAccountUsers(anyObject())).thenReturn(expected);
        QueryResult<List<BillingAccountUser>> result = unit.getBillingAccountUsers(1l);
        assertEquals(0, result.getData().size());
        verify(billingAccountDAO).getBillingAccountUsers(anyObject());
    }

    @Test
    public void testRemoveBillingAccountUsers() {
        unit.removeUserFromBillingAccount(1l, 1l);
        verify(billingAccountDAO).removeUserFromBillingAccount(anyObject(), anyObject());
    }

    @Test
    public void testAddUserToBillingAccount() throws SupplyException {
        QueryResult<List<BillingAccount>> expected = getListQueryResult();
        QueryResult<List<BillingAccountUser>> expectedUsers = new QueryResult<>(new ArrayList<>());
        IdDTO expectedId = new IdDTO(1l);
        expected.getData().remove(1);

        when(billingAccountDAO.getBillingAccount(anyObject())).thenReturn(expected);
        when(billingAccountDAO.checkUserExists(anyObject())).thenReturn(expectedId);
        when(billingAccountDAO.getBillingAccountUsers(anyObject())).thenReturn(expectedUsers);
        QueryResult<List<BillingAccount>> result = unit.addUserToBillingAccount(new AuthUser(), 1l, 1l);
        assertEquals(1, result.getData().size());
        verify(billingAccountDAO).addUserToBillingAccount(anyObject(), anyObject(), anyObject());
        verify(billingAccountDAO).getBillingAccount(anyObject());
        verify(billingAccountDAO).checkUserExists(anyObject());
        verify(billingAccountDAO).getBillingAccountUsers(anyObject());
    }

    private QueryResult<List<BillingAccount>> getListQueryResult() {
        List<BillingAccount> billingAccounts = new ArrayList<>();

        billingAccounts.add(new BillingAccount(1l, "1", "Active", new Date(), new Date(), 500.0f, 1.0f, "po1",  new PaymentTermsDTO(1l, "30 Days")));
        billingAccounts.add(new BillingAccount(2l, "2", "Active", new Date(), new Date(), 500.0f, 1.0f, "po2",  new PaymentTermsDTO(1l, "30 Days")));
        QueryResult<List<BillingAccount>> expected = new QueryResult<>();
        expected.setData(billingAccounts);
        return expected;
    }
}
