/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.controller;

import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.dto.*;
import com.ig.flooringmastery.service.*;
import com.ig.flooringmastery.ui.FlooringView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class FlooringController {

    private FlooringView view;
    private FlooringService service;

    public FlooringController(FlooringView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws OrderPersistenceException, TaxPersistenceException,
            ProductPersistenceException {
        boolean keepGoing = true;
        while (keepGoing) {
            try {
                view.mainMenu();
                int selection = view.menuSelection();
                switch (selection) {
                    case 1:
                        displayOrderByDate();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        writeExport();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                }
            } catch (StringIndexOutOfBoundsException |NullPointerException e) {
                view.noSuchOrderErrorMessage();
            }
        }
    }

    private void displayOrderByDate() throws OrderPersistenceException {
        view.displayingOrderBanner();
        List<Order> orderList = new ArrayList<>();
        LocalDate date = view.acceptOrderDate();
        try {
            orderList = service.getOrderByDate(date);
            view.displayOrderByDate(orderList);
        } catch (NullPointerException e) {
            view.noOrderForTheDateErrorMessage();
        }
    }

    private void createOrder() throws OrderPersistenceException, TaxPersistenceException,
            ProductPersistenceException {
        view.creatingOrderBanner();
        List<Product> productList = service.getAllProducts();
        Set<String> listOfStates = service.getAllStates();
        view.displayAllProducts(productList);
        view.displayAllStates(listOfStates);
        Order order = view.createNewOrderInfo();
        try {
            service.validateOrderData(order);
            Order calculatedOrder = service.calculateOrder(order);
            view.displayOrder(calculatedOrder);
            String response = view.confirmToPlaceOrder();
            if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
                service.addOrder(calculatedOrder);
                view.createOrderSuccessfulBanner();
            }
        } catch (InvalidAreaException | InvalidCustomerNameException | InvalidOrderDateException
                | ProductNotFoundException | StateNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void displayAllProducts() throws ProductPersistenceException {
        List<Product> productList = service.getAllProducts();
        view.displayAllProducts(productList);
    }

    private void editOrder() throws OrderPersistenceException, TaxPersistenceException,
            ProductPersistenceException {
        view.editingOrderBanner();
        LocalDate date = view.acceptOrderDate();
        Order order = new Order();
        int orderNumber = view.acceptOrderNumber();
        order = service.getOrder(date, orderNumber);
        Order editedOrder = view.getEditableOrderInfo(order);
        editedOrder.setOrderDate(date);
        try {
            service.validateOrderData(editedOrder);
            Order calculatedOrder = service.calculateOrder(editedOrder);
            calculatedOrder.setOrderNumber(orderNumber);
            view.displayOrder(calculatedOrder);
            String response = view.confirmToEditOrder();
            if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
                service.updateOrder(calculatedOrder.getOrderDate(), calculatedOrder.getOrderNumber(),
                        calculatedOrder);
                view.editOrderSuccessfulBanner();
            }
        } catch (InvalidAreaException | InvalidCustomerNameException | InvalidOrderDateException
                | ProductNotFoundException | StateNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws OrderPersistenceException {
        view.removingOrderBanner();
        LocalDate date = view.acceptOrderDate();
        int orderNumber = view.acceptOrderNumber();
        Order order = service.getOrder(date, orderNumber);
        view.displayOrder(order);
        if (order != null) {
            String response = view.confirmToRemoveOrder();
            if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
                service.removeOrder(date, orderNumber);
                view.displayRemoveOrderSuccessfulBanner();
            }
        }
    }

    private void writeExport() throws OrderPersistenceException {
        view.exportingOrderBanner();
        service.writeExport();
        view.writeExportOrderSuccessfulBanner();
    }
}
