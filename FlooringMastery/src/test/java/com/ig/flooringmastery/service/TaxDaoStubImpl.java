/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.service;

import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class TaxDaoStubImpl implements TaxDao {

    public Tax onlyTax;

    public TaxDaoStubImpl() {
        onlyTax = new Tax();
        onlyTax.setState("texas");
        onlyTax.setStateAbbrivation("TX");
        onlyTax.setTaxRate(new BigDecimal("5.15"));
    }

    public TaxDaoStubImpl(Tax testTax) {
        this.onlyTax = testTax;
    }

    @Override
    public Tax addTax(Tax tax) throws TaxPersistenceException {
        if (tax.getState().equals(onlyTax.getState())) {
            return onlyTax;
        } else {
            return null;
        }
    }

    @Override
    public List<Tax> getAllTaxes() throws TaxPersistenceException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(onlyTax);
        return taxList;
    }

    @Override
    public Tax getTax(String state) throws TaxPersistenceException {
        if (state.equals(onlyTax.getState())) {
            return onlyTax;
        } else {
            return null;
        }
    }

    @Override
    public Set<String> getAllStates() throws TaxPersistenceException {
        Set<String> stateList = new HashSet<>();
        stateList.add(onlyTax.getState());
        return stateList;
    }
}
