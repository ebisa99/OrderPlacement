/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.*;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ebisa
 */
public class TaxDaoFileImplTest {

    TaxDao taxTestDao;

    public TaxDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testTax.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        taxTestDao = new TaxDaoFileImpl(testFile);
        ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    taxTestDao = 
        ctx.getBean("taxDao", TaxDaoFileImpl.class);
    }

    @Test
    public void testAddGetTax() throws Exception{
        Tax tax = new Tax();
        tax.setState("texas");
        tax.setStateAbbrivation("TX");
        tax.setTaxRate(new BigDecimal("5.15"));
        taxTestDao.addTax(tax);
        Tax retrievedTax = taxTestDao.getTax(tax.getState());
        assertEquals(tax.getState(), retrievedTax.getState(), "checking state.");
        assertEquals(tax.getStateAbbrivation(), retrievedTax.getStateAbbrivation(), "checking state Abbr.");
        assertEquals(tax.getTaxRate(), retrievedTax.getTaxRate(), "checking tax rate");  
    }
    @Test
    public void testGetAllTaxes()throws Exception{
        Tax tax = new Tax();
        tax.setState("texas");
        tax.setStateAbbrivation("TX");
        tax.setTaxRate(new BigDecimal("5.15"));
        Tax tax2 = new Tax();
        tax2.setState("calfornia");
        tax2.setStateAbbrivation("CA");
        tax2.setTaxRate(new BigDecimal("6.15"));
        taxTestDao.addTax(tax);
        taxTestDao.addTax(tax2);
        List<Tax> listOfTax = taxTestDao.getAllTaxes();
        assertNotNull(listOfTax, "list of tax should not null.");
        assertEquals(2, listOfTax.size(), "tax list should be 2.");
        assertTrue(listOfTax.contains(tax), "Texas should be in the list of tax.");
        assertTrue(listOfTax.contains(tax2), "calfornia should in the list of tax objects");
    }
    @Test
    public void testGetAllStates()throws Exception{
        Tax tax = new Tax();
        tax.setState("texas");
        tax.setStateAbbrivation("TX");
        tax.setTaxRate(new BigDecimal("5.15"));
        Tax tax2 = new Tax();
        tax2.setState("calfornia");
        tax2.setStateAbbrivation("CA");
        tax2.setTaxRate(new BigDecimal("6.15"));
        taxTestDao.addTax(tax);
        taxTestDao.addTax(tax2);
        Set<String> listOfState = taxTestDao.getAllStates();
        assertNotNull(listOfState, "list of state should not null.");
        assertEquals(2, listOfState.size(), "the number of states in the list should be 2.");
        assertTrue(listOfState.contains("texas"), "the list of state should have Texas.");
        assertTrue(listOfState.contains("calfornia"), "the list of state should have calfornia.");
    }

}
