/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Tax;
import java.util.*;

/**
 *
 * @author ebisa
 */
public interface TaxDao {

    Tax addTax(Tax tax) throws TaxPersistenceException;

    List<Tax> getAllTaxes() throws TaxPersistenceException;

    Tax getTax(String stateAbbrivation) throws TaxPersistenceException;

    Set<String> getAllStates() throws TaxPersistenceException;
}
