/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class OrderDaoFileImplTest {
    
    OrderDao orderTestDao;
    
    public OrderDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        File exportTestFile = new File("C:\\Users\\ebisa\\SGRepos\\FlooringMastery\\SampleFileData\\Export\\Export.txt");
        File directory = new File("C:\\Users\\ebisa\\SGRepos\\FlooringMastery\\SampleFileData\\OrderT");
        for (File file : directory.listFiles()) {
            file.delete();
        }
        // Use the FileWriter to quickly blank the file
        new FileWriter(exportTestFile.getName());
        // orderTestDao = new OrderDaoFileImpl(testFile);
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        orderTestDao
                = ctx.getBean("orderDao", OrderDaoFileImpl.class);
    }
    
    @Test
    public void testAddGetOrder() throws Exception {
        Order order = new Order();
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
        orderTestDao.addOrder(order);
        Order retrievedOrder = orderTestDao.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertEquals(order.getArea(), retrievedOrder.getArea(), "checking order Area");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), "checking order Customer Name.");
        assertEquals(order.getLaborCost(), retrievedOrder.getLaborCost(), "checking order Labor cost");
        assertEquals(order.getMaterialCost(), retrievedOrder.getMaterialCost(), "checking  Material cost");
        //assertEquals(order.getOrderDate(), retrievedOrder.getOrderDate(), "checking order Date");
        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), "checking order Number");
        assertEquals(order.getProduct(), retrievedOrder.getProduct(), "checking order Product");
        //assertEquals(order.getTax(), retrievedOrder.getTax(), "checking order Tax");
        assertEquals(order.getTaxes(), retrievedOrder.getTaxes(), "checking order tax total");
        assertEquals(order.getTotal(), retrievedOrder.getTotal(), "checking order Total Cost");
    }
    
    @Test
    public void testGetAllOrderByDate() throws Exception {
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-08-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("11"));
        product.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax.setState("Calfornia");
        tax.setStateAbbrivation("CA");
        tax.setTaxRate(new BigDecimal("5.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
        //Second order Object
        Order order2 = new Order();
        order2.setArea(new BigDecimal("123"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-08-09"));
        order2.setLaborCost(new BigDecimal("115"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(2);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("10"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax2.setState("Texas");
        tax2.setStateAbbrivation("TX");
        tax2.setTaxRate(new BigDecimal("4.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("6"));
        order2.setTotal(new BigDecimal("1100"));
        //Add both of our Order Object.
        orderTestDao.addOrder(order);
        orderTestDao.addOrder(order2);
        // Retrieve the list of all orders within the DAO
        List<Order> allOrders = orderTestDao.getOrdersByDate(order.getOrderDate());

        // First check the general contents of the list
        assertNotNull(allOrders, "The list of orders must not null");
        assertEquals(2, allOrders.size(), "List of Orders should have 2 orders.");

        // Then the specifics
//        assertTrue(allOrders.contains(order),"The list of orders should include Wood.");
//        assertTrue(orderTestDao.getOrdersByDate(order.getOrderDate()).contains(order2),
//            "The list of Orders should include Tile.");
    }
    
    @Test
    public void testRemoveOrder() throws Exception {
        //Arrange
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-08-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("11"));
        product.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax.setState("Calfornia");
        tax.setStateAbbrivation("CA");
        tax.setTaxRate(new BigDecimal("5.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
        //Second order Object
        Order order2 = new Order();
        order2.setArea(new BigDecimal("123"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-08-09"));
        order2.setLaborCost(new BigDecimal("115"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(2);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("10"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax2.setState("Texas");
        tax2.setStateAbbrivation("TX");
        tax2.setTaxRate(new BigDecimal("4.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("6"));
        order2.setTotal(new BigDecimal("1100"));
        //Add both of our Order Object.
        orderTestDao.addOrder(order);
        orderTestDao.addOrder(order2);
        //Act
        Order removedOrder = orderTestDao.removeOrder(order.getOrderDate(), order.getOrderNumber());
        List<Order> orderList = orderTestDao.getOrdersByDate(order2.getOrderDate());
        assertNotNull(orderList, "order list must not be null");
        assertEquals(1, orderList.size(), "orderList should only have one order.");
        removedOrder = orderTestDao.removeOrder(order2.getOrderDate(), order2.getOrderNumber());

        // retrieve all of the order again, and check the list.
        orderList = orderTestDao.getOrdersByDate(order.getOrderDate());

        // Check the contents of the list - it should be empty
        assertTrue(orderList.isEmpty(), "The retrieved list of orders should be empty.");

        // Try to 'get' both orders by their old order numbers - they should be null!
        Order retrievedOrder = orderTestDao.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertNull(retrievedOrder, "wood was removed, should be null.");
        
        retrievedOrder = orderTestDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertNull(retrievedOrder, "Tile was removed, should be null.");
    }
    
    @Test
    public void testGetAllOrderDates() throws Exception {
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-08-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("11"));
        product.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax.setState("Calfornia");
        tax.setStateAbbrivation("CA");
        tax.setTaxRate(new BigDecimal("5.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
        //Second order Object
        Order order2 = new Order();
        order2.setArea(new BigDecimal("123"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-09-09"));
        order2.setLaborCost(new BigDecimal("115"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(2);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("10"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax2.setState("Texas");
        tax2.setStateAbbrivation("TX");
        tax2.setTaxRate(new BigDecimal("4.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("6"));
        order2.setTotal(new BigDecimal("1100"));
        //Add both of our Order Object.
        orderTestDao.addOrder(order);
        orderTestDao.addOrder(order2);
        Set<LocalDate> allDates = orderTestDao.getAllOrderDates();
        assertNotNull(allDates, "allDates must not be null.");
        assertEquals(2, allDates.size(), "all dates must be 2");
    }
    
    @Test
    public void testGetOrderNumbers() throws Exception {
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-09-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("11"));
        product.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax.setState("Calfornia");
        tax.setStateAbbrivation("CA");
        tax.setTaxRate(new BigDecimal("5.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
        //Second order Object
        Order order2 = new Order();
        order2.setArea(new BigDecimal("123"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-09-09"));
        order2.setLaborCost(new BigDecimal("115"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(2);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("10"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax2.setState("Texas");
        tax2.setStateAbbrivation("TX");
        tax2.setTaxRate(new BigDecimal("4.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("6"));
        order2.setTotal(new BigDecimal("1100"));
        //Add both of our Order Object.
        orderTestDao.addOrder(order);
        orderTestDao.addOrder(order2);
        Set<Integer> allOrderNumbers = orderTestDao.getOrderNumbers(order.getOrderDate());
        assertNotNull(allOrderNumbers, "all Order numbers must not null");
        assertEquals(2, allOrderNumbers.size(), "all order number size must equals 2.");
    }
    
    @Test
    public void testEditOrder() throws Exception {
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-09-09"));
        order.setLaborCost(new BigDecimal("125"));
        order.setMaterialCost(new BigDecimal("130"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("11"));
        product.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax.setState("Calfornia");
        tax.setStateAbbrivation("CA");
        tax.setTaxRate(new BigDecimal("5.25"));
        order.setProduct(product);
        order.setTax(tax);
        order.setTaxes(new BigDecimal("5"));
        order.setTotal(new BigDecimal("1200"));
        //Second order Object
        Order order2 = new Order();
        order2.setArea(new BigDecimal("123"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-09-09"));
        order2.setLaborCost(new BigDecimal("115"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(2);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("10"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("12"));
        tax2.setState("Texas");
        tax2.setStateAbbrivation("TX");
        tax2.setTaxRate(new BigDecimal("4.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("6"));
        order2.setTotal(new BigDecimal("1100"));
        //Add both of our Order Object.
        orderTestDao.addOrder(order);
        orderTestDao.addOrder(order2);
        List<Order> orderList = orderTestDao.getOrdersByDate(order.getOrderDate());
        assertNotNull(orderList, "order list must not null");
        assertEquals(2, orderList.size(), "order list size must be 2");
        Order editedOrder = orderTestDao.editOrder(order.getOrderDate(), order.getOrderNumber(), order2);
        editedOrder.setTax(tax2);
        order2.setLaborCost(editedOrder.getLaborCost());
        order2.setMaterialCost(editedOrder.getMaterialCost());
        order2.setTaxes(editedOrder.getTaxes());
        order2.setTotal(editedOrder.getTotal());
        Order calculatedOrder2 = order2;
        assertEquals(editedOrder, calculatedOrder2);
    }
    @Test
    public void testExport()throws Exception{
        orderTestDao.writeExport();
    }
}
