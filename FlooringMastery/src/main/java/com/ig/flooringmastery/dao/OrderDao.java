/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author ebisa
 */
public interface OrderDao {

    Order addOrder(Order order) throws OrderPersistenceException;

    List<Order> getOrdersByDate(LocalDate date) throws OrderPersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws OrderPersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException;

    Set<LocalDate> getAllOrderDates() throws OrderPersistenceException;

    Set<Integer> getOrderNumbers(LocalDate date) throws OrderPersistenceException;

    void writeExport() throws OrderPersistenceException;

    Order editOrder(LocalDate date, int orderNumber, Order order) throws OrderPersistenceException;
}
