/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.ui;

import com.ig.flooringmastery.dto.*;
import java.time.LocalDate;
import java.util.List;
import java.math.*;
import java.util.Set;

/**
 *
 * @author ebisa
 */
public class FlooringView {

    private UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void mainMenu() {
        io.print("*************************************");
        io.print("<<Flooring Program Menu>>");
        io.print("* 1.Display Orders");
        io.print("* 2.Add an Order");
        io.print("* 3.Edit an Order");
        io.print("* 4.Remove an Order");
        io.print("* 5.Export All Data");
        io.print("* 6.Quit");
        io.print("**************************************");
    }

    public int menuSelection() {
        int selection = io.readInt("please select from the menu.", 1, 6);
        return selection;
    }

    public Order createNewOrderInfo() {
        LocalDate min = LocalDate.now().plusDays(1);
        LocalDate orderDate = io.readLocalDate("enter order date in the yyyy-mm-dd format.",
                min, LocalDate.parse("2022-06-28"));
        String customerName = io.readString("enter customer name.");
        String state = io.readString("enter the state.");
        String productType = io.readString("enter product type.");
        BigDecimal area = io.readBigDecimal("enter the area of the product.",
                new BigDecimal("100.00"), new BigDecimal("600.00"));
        Order currentOrder = new Order();
        currentOrder.setArea(area);
        currentOrder.setCustomerName(customerName);
        currentOrder.setOrderDate(orderDate);
        Tax tax = new Tax();
        Product product = new Product();
        tax.setState(state.toLowerCase());
        product.setProductType(productType.toLowerCase());
        currentOrder.setProduct(product);
        currentOrder.setTax(tax);
        return currentOrder;
    }

    public Order getEditableOrderInfo(Order order) {
        if (order != null) {
            String customerName = io.readString("enter customer Name " + "(" + order.getCustomerName() + "):");
            if (customerName.equals("") != true) {
                order.setCustomerName(customerName);
            }
            io.print("(" + order.getCustomerName() + ")");
            String state = io.readString("enter the state " + "(" + order.getTax().getStateAbbrivation() + "):");
            if (state.equals("") != true) {
                Tax tax = new Tax();
                tax.setState(state.toLowerCase());
                order.setTax(tax);
                io.print("(" + order.getTax().getState() + ")");
            } else {
                io.print("(" + order.getTax().getStateAbbrivation() + ")");
            }
            String productType = io.readString("enter the product Type " + "(" + order.getProduct().getProductType() + "):");
            if (productType.equals("") != true) {
                order.getProduct().setProductType(productType.toLowerCase());
            }
            order.getProduct().setProductType(order.getProduct().getProductType().toLowerCase());
            io.print("(" + order.getProduct().getProductType() + ")");
            String StringArea = io.readString("enter the area of the product " + "(" + order.getArea() + "):");
            try {
                BigDecimal bigArea = new BigDecimal(StringArea);
                order.setArea(bigArea);
            } catch (NumberFormatException e) {
            }
            io.print("(" + order.getArea() + ")");
        }
        return order;
    }

    public LocalDate acceptOrderDate() {
        return io.readLocalDate("enter order date in yyyy-mm-dd format");
    }

    public int acceptOrderNumber() {
        return io.readInt("Enter order Number");
    }

    public void noSuchOrderErrorMessage() {
        io.print("order Not found.");
    }

    public void noOrderForTheDateErrorMessage() {
        io.print("No Order for the Date.");
    }

    public void displayOrder(Order order) {
        if (order != null) {
            String header = "OrderNumber\tCustomerName\tState\tProductType\tArea\t"
                    + "MaterialCost\tLaborcost\tTax\tTotal";
            io.print(header);
            String orderInfo = String.format("%-15s %-17s %-13s %-10s %-12s $%-9s $%-13s $%-13s $%-12s",
                    order.getOrderNumber(), order.getCustomerName(),
                    order.getTax().getStateAbbrivation(),
                    order.getProduct().getProductType(), order.getArea(),
                    order.getMaterialCost(), order.getLaborCost(), order.getTaxes(),
                    order.getTotal());
            io.print(orderInfo);
        } else {
            io.print("No order found.");
        }
    }

    public void createOrderSuccessfulBanner() {
        io.print("order successfully created.");
    }

    public void editOrderSuccessfulBanner() {
        io.print("order successfully edited.");
    }

    public void writeExportOrderSuccessfulBanner() {
        io.print("The Order is successfully exported.");
    }

    public void displayingOrderBanner() {
        io.print("displaying order for the date....");
    }

    public void editingOrderBanner() {
        io.print("editing order......");
    }

    public void creatingOrderBanner() {
        io.print("creating order.....");
    }

    public void exportingOrderBanner() {
        io.print("Exporting order......");
    }

    public void removingOrderBanner() {
        io.print("Removing Order......");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public String confirmToPlaceOrder() {
        String response = io.readString("would you like to place the order?");
        return response;
    }

    public String confirmToEditOrder() {
        String response = io.readString("would you like to save the change?");
        return response;
    }

    public String confirmToRemoveOrder() {
        String response = io.readString("Are you sure you want to remove the order?");
        return response;
    }

    public void displayAllProducts(List<Product> productList) {
        io.print("THE LIST OF PRODUTS AND PRICING INFO.");
        io.print("Product Type     Cost Per Square Foot     Labor Cost Per Square Foot");
        for (Product product : productList) {
            String productInfo = String.format("%s %25s %25s",
                    product.getProductType(), product.getCostPerSquareFoot(),
                    product.getLaborCostPerSquareFoot());
            io.print(productInfo);
        }
    }

    public void displayAllStates(Set<String> states) {
        io.print("THE LIST OF STATE WHERE WE SELL PRODUCT.");
        for (String state : states) {
            io.print(state.toUpperCase());
        }
    }

    public void displayRemoveOrderSuccessfulBanner() {
        io.print("Order is successfully removed.");
    }

    public void displayOrderByDate(List<Order> orderList) {
        if (orderList != null) {
            String header = "OrderNumber\tCustomerName\tState\tProductType\tArea\t"
                    + "MaterialCost\tLaborcost\tTax\tTotal";
            io.print(header);
            for (Order order : orderList) {
                String orderInfo = String.format("%-15s %-17s %-10s %-10s %-10s $%-12s $%-12s $%-10s $%-10s",
                        order.getOrderNumber(), order.getCustomerName(),
                        order.getTax().getStateAbbrivation(),
                        order.getProduct().getProductType(), order.getArea(),
                        order.getMaterialCost(), order.getLaborCost(), order.getTaxes(),
                        order.getTotal());
                io.print(orderInfo);
            }
        }
    }
}
