/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

/**
 *
 * @author ebisa
 */
public class TaxPersistenceException extends Exception{
    public TaxPersistenceException(String message){
        super(message);
    }
    public TaxPersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
