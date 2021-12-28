/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.service;

import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class OrderDaoStubImpl implements OrderDao {

    public Order order;

    public OrderDaoStubImpl() {
        order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-08-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Tile");
        product.setCostPerSquareFoot(new BigDecimal("10"));
        product.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax.setState("Texas");
        tax.setStateAbbrivation("TX");
        tax.setTaxRate(new BigDecimal("4.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
    }

    public OrderDaoStubImpl(Order testOrder) {
        this.order = testOrder;
    }

    @Override
    public Order addOrder(Order currentOrder) throws OrderPersistenceException {
        if (currentOrder.equals(order)) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws OrderPersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        return orderList;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        if (order.getOrderDate().equals(date) && order.getOrderNumber() == orderNumber) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        if (order.getOrderDate().equals(date) && order.getOrderNumber() == orderNumber) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Set<LocalDate> getAllOrderDates() throws OrderPersistenceException {
        Set<LocalDate> dateList = new HashSet<>();
        dateList.add(order.getOrderDate());
        return dateList;
    }

    @Override
    public Set<Integer> getOrderNumbers(LocalDate date) throws OrderPersistenceException {
        Set<Integer> orderNumberList = new HashSet<>();
        orderNumberList.add(order.getOrderNumber());
        return orderNumberList;
    }

    @Override
    public void writeExport() throws OrderPersistenceException {
        order = order;
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order currentOrder)
            throws OrderPersistenceException {

        order = currentOrder;
        return order;
    }

}
