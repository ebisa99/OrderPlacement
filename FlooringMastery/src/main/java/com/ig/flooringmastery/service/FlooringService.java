/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.service;

import com.ig.flooringmastery.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import com.ig.flooringmastery.dao.*;

/**
 *
 * @author ebisa
 */
public interface FlooringService {

    Order calculateOrder(Order order) throws OrderPersistenceException, TaxPersistenceException,
            ProductPersistenceException;

    void validateOrderData(Order order) throws InvalidOrderDateException,
            InvalidAreaException, InvalidCustomerNameException, TaxPersistenceException,
            ProductPersistenceException,
            ProductNotFoundException, StateNotFoundException, OrderPersistenceException;

    /*
    Order createOrder(Order order)
            throws InvalidOrderDateException,
            InvalidAreaException, InvalidCustomerNameException, TaxPersistenceException,
            ProductNotFoundException, StateNotFoundException, OrderPersistenceException;
     */
    List<Product> getAllProducts() throws ProductPersistenceException;

    Order addOrder(Order order) throws OrderPersistenceException;

    Order getOrder(LocalDate orderDate, int orderNumber) throws OrderPersistenceException;

    Order removeOrder(LocalDate orderDate, int orderNumber) throws OrderPersistenceException;

    List<Order> getOrderByDate(LocalDate orderDate) throws OrderPersistenceException;

    //List<Order> getAllOrders() throws OrderPersistenceException;

    Order updateOrder(LocalDate date, int orderNumber, Order order) throws OrderPersistenceException;

    Set<String> getAllStates() throws TaxPersistenceException;

    void writeExport() throws OrderPersistenceException;
}
