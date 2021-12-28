/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.*;
import java.io.*;

/**
 *
 * @author ebisa
 */
public class TaxDaoFileImpl implements TaxDao {

    private Map<String, Tax> taxes = new HashMap<>();
    private static final String DELIMITER = ",";
    private final String TAX_FILE;

    public TaxDaoFileImpl() {
        TAX_FILE = "Taxes.txt";
    }

    public TaxDaoFileImpl(String taxTextFile) {
        TAX_FILE = taxTextFile;
    }

    @Override
    public Tax addTax(Tax tax) throws TaxPersistenceException {
        loadTax();
        Tax currentTax = taxes.put(tax.getState(), tax);
        writeTax();
        return currentTax;
    }

    @Override
    public List<Tax> getAllTaxes() throws TaxPersistenceException {
        loadTax();
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax getTax(String state) throws TaxPersistenceException {
        loadTax();
        Tax currentTax = taxes.get(state);
        return currentTax;
    }

    @Override
    public Set<String> getAllStates() throws TaxPersistenceException {
        loadTax();
        return taxes.keySet();
    }

    private Tax unmarshallTax(String taxAsText) {
        String[] taxFieldsArray = taxAsText.split(DELIMITER);
        String stateAbbrivation = taxFieldsArray[0];
        String state = taxFieldsArray[1];
        String taxRate = taxFieldsArray[2];
        Tax taxObject = new Tax();
        taxObject.setStateAbbrivation(stateAbbrivation.toUpperCase());
        taxObject.setState(state.toLowerCase());
        taxObject.setTaxRate(new BigDecimal(taxRate));
        return taxObject;
    }

    private void loadTax() throws TaxPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new TaxPersistenceException("could not load Product data into memory.", e);
        }
        String currentLine;
        Tax currentTax;
         try {
                scanner.nextLine();
            } catch (Exception e) {
            }
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getState(), currentTax);
        }
        scanner.close();
    }

    private String marshallTax(Tax tax) {
        String taxtAsText;
        taxtAsText = tax.getStateAbbrivation() + DELIMITER;
        taxtAsText += tax.getState() + DELIMITER;
        taxtAsText += tax.getTaxRate();
        return taxtAsText;
    }

    private void writeTax() throws TaxPersistenceException {
        PrintWriter out;
        String currentLine;
        try {
            out = new PrintWriter(new FileWriter(TAX_FILE));
        } catch (IOException e) {
            throw new TaxPersistenceException("couldn't save tax data.", e);
        }
         String header = "State,StateName,TaxRate";
        out.println(header);
        List<Tax> taxList = getAllTaxes();
        for (Tax tax : taxList) {
            currentLine = marshallTax(tax);
            out.println(currentLine);
            out.flush();
        }
        out.close();
    }
}
