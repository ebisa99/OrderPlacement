/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.service;

import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.dto.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class FlooringServiceImpl implements FlooringService {

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    public FlooringServiceImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    private int generateOrderNumber() throws OrderPersistenceException,TaxPersistenceException {
        Set<LocalDate> orderDates = orderDao.getAllOrderDates();
        Set<Integer> orderNumbers = new HashSet<>();
        orderNumbers.add(0);
        for (LocalDate date : orderDates) {
            orderNumbers.addAll(orderDao.getOrderNumbers(date));
        }
        Integer max = orderNumbers.stream().max(Integer::compare).get();
        int nextOrderNumber = max + 1;
        return nextOrderNumber;
    }

    @Override
    public Set<String> getAllStates() throws TaxPersistenceException {
        return taxDao.getAllStates();
    }

    @Override
    public void validateOrderData(Order order) throws InvalidOrderDateException,
            InvalidAreaException, InvalidCustomerNameException, TaxPersistenceException,
            ProductPersistenceException,
            ProductNotFoundException, StateNotFoundException, OrderPersistenceException {
        String invalidChars = "<>?/:|}\\';{[]=+-_)(*&^%$#@!";
        invalidChars = invalidChars.concat(String.valueOf('"'));
        for (int i = 0; i < invalidChars.length(); i++) {
            if (order.getCustomerName().contains(String.valueOf(invalidChars.charAt(i)))) {
                throw new InvalidCustomerNameException("ERROR!....invalid character included in the customer name.");
            }
        }
        if (order.getCustomerName() == null || order.getCustomerName()
                .trim().length() == 0) {
            throw new InvalidCustomerNameException("ERROR!....the name cannot be empty.");
        }
        Set<String> stateList = taxDao.getAllStates();
        boolean stateAbbrExist = false;
        for (String state : stateList) {
            if (taxDao.getTax(state).getStateAbbrivation()
                    .equalsIgnoreCase(order.getTax().getStateAbbrivation())) {
                Tax tax = new Tax();
                tax.setState(state);
                order.setTax(tax);
                break;
            }
        }
        if (taxDao.getAllStates().contains(order.getTax().getState()) != true) {
            throw new StateNotFoundException("ERROR!....Sorry! The state provided is not among where we sell product.");
        }
        if (productDao.getAllProductTypes().contains(order.getProduct().getProductType()) != true) {
            throw new ProductNotFoundException("ERROR!....Entered product is not available.");
        }
        if (order.getArea().compareTo(new BigDecimal("100.00")) < 0) {
            throw new InvalidAreaException("ERROR!....minimum area is 100 sq ft.");
        }
    }

    @Override
    public Order calculateOrder(Order order) throws OrderPersistenceException,
            TaxPersistenceException, ProductPersistenceException {
        int orderNumber = generateOrderNumber();
        Tax tax = taxDao.getTax(order.getTax().getState());
        Product product = productDao.getProduct(order.getProduct().getProductType());
        BigDecimal taxRate = tax.getTaxRate();
        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        BigDecimal materialCost = order.getArea().multiply(costPerSquareFoot);
        BigDecimal laborCost = order.getArea().multiply(laborCostPerSquareFoot);
        BigDecimal totalTax = materialCost.add(laborCost);
        BigDecimal rate = taxRate.divide(new BigDecimal("100"));
        totalTax = totalTax.multiply(rate);
        BigDecimal totalCost = materialCost.add(laborCost).add(totalTax);
        order.setOrderNumber(orderNumber);
        order.setProduct(product);
        order.setTax(tax);
        BigDecimal roundedLaborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundedMaterialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundedTotalTax = totalTax.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundedTotalCost = totalCost.setScale(2, RoundingMode.HALF_UP);
        order.setLaborCost(roundedLaborCost);
        order.setMaterialCost(roundedMaterialCost);
        order.setTaxes(roundedTotalTax);
        order.setTotal(roundedTotalCost);
        return order;
    }

    @Override
    public List<Product> getAllProducts() throws ProductPersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public Order addOrder(Order order) throws OrderPersistenceException {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws OrderPersistenceException {
        return orderDao.getOrder(orderDate, orderNumber);
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws OrderPersistenceException {
        return orderDao.removeOrder(orderDate, orderNumber);
    }

    @Override
    public List<Order> getOrderByDate(LocalDate orderDate) throws OrderPersistenceException {
        return orderDao.getOrdersByDate(orderDate);
    }

    @Override
    public Order updateOrder(LocalDate date, int orderNumber, Order order) throws OrderPersistenceException {
        return orderDao.editOrder(date, orderNumber, order);
    }

    @Override
    public void writeExport() throws OrderPersistenceException {
        orderDao.writeExport();
    }

}
