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
public class FlooringServiceImplTest {

    private FlooringService service;

    public FlooringServiceImplTest() {
//        OrderDao orderDao = new OrderDaoStubImpl();
//        ProductDao productDao = new ProductDaoStubImpl();
//        TaxDao taxDao = new TaxDaoStubImpl();
//        service = new FlooringServiceImpl(orderDao, productDao, taxDao);
        ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    service = 
        ctx.getBean("serviceLayer", FlooringServiceImpl.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidateOrderData() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        try {
            service.validateOrderData(order);
        } catch (InvalidAreaException
                | InvalidCustomerNameException
                | InvalidOrderDateException
                | ProductNotFoundException
                | StateNotFoundException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testCalculateOrder() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
        Order order = new Order();
        order.setArea(new BigDecimal("120"));
        order.setCustomerName("Ibsa");
        order.setOrderDate(LocalDate.parse("2021-08-09"));
        order.setOrderNumber(1);
        Product product = new Product();
        Tax tax = new Tax();
        product.setProductType("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.25"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.25"));
        tax.setState("texas");
        tax.setStateAbbrivation("TX");
        tax.setTaxRate(new BigDecimal("5.15"));
        order.setProduct(product);
        order.setTax(tax);
        Order calculatedOrder = service.calculateOrder(order);
        assertEquals(BigDecimal.valueOf(390.00).setScale(2), calculatedOrder.getMaterialCost(), "material cost should be 390.00");
        assertEquals(BigDecimal.valueOf(510.00).setScale(2), calculatedOrder.getLaborCost(), "labor cost should be 510.00");
        assertEquals(BigDecimal.valueOf(46.35).setScale(2), calculatedOrder.getTaxes(), "total tax should be 49.00");
        assertEquals(BigDecimal.valueOf(946.35).setScale(2), calculatedOrder.getTotal(), "total cost should be 949.00");
    }

    @Test
    public void testAddOrder() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        service.addOrder(order);
        Order retrievedOrder = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertNotNull(retrievedOrder, "Tile should not null.");
        assertEquals(order, retrievedOrder, "original order and retrieved order should be equal.");
    }

    @Test
    public void testGetOrder() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        service.addOrder(order);
        Order retrievedOrder = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), "checking order number");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), "checking customer name");
        assertEquals(order.getArea(), retrievedOrder.getArea(), "checking area");
        assertEquals(order.getLaborCost(), retrievedOrder.getLaborCost(), "checking labor cost");
        assertEquals(order.getProduct(), retrievedOrder.getProduct(), "checking order product object");
        assertEquals(order.getTax(), retrievedOrder.getTax(), "checking order Tax object");
        assertEquals(order.getOrderDate(), retrievedOrder.getOrderDate(), "checking order date");
        assertEquals(order.getTaxes(), retrievedOrder.getTaxes(), "checking order tax");
        assertEquals(order.getTotal(), retrievedOrder.getTotal(), "checking order Total cost");
    }

    @Test
    public void testGetOrderByDate() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        service.addOrder(order);
        List<Order> orderList = service.getOrderByDate(LocalDate.parse("2021-08-09"));
        assertNotNull(orderList, "list of order should not null");
        assertEquals(1, orderList.size(), "order list size should be 1.");
        assertTrue(orderList.contains(order), "the one order should be order list");
    }

    @Test
    public void testRemoveOrder() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        service.addOrder(order);
        Order retrieved = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertNotNull(retrieved, "retrieved order should not null.");
        assertEquals(retrieved, order, "original order and retrieved should the one order");
        Order removed = service.removeOrder(order.getOrderDate(), order.getOrderNumber());
        assertEquals(removed, order, "removed order should the one order.");
        retrieved = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        Order nullOrder = service.removeOrder(LocalDate.parse("2021-10-10"), 3);
        assertNull(nullOrder, "order with the given date and  order order number should be null.");
    }

    @Test
    public void testUpdateOrder() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
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
        service.addOrder(order);
        Order retrieved = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertNotNull(retrieved, "retrieved order should not null.");
        assertEquals(retrieved, order, "original order and retrieved should the one order");
        //second order
        Order order2 = new Order();
        order2.setArea(new BigDecimal("120"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-08-09"));
        order2.setLaborCost(new BigDecimal("120"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(1);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Wood");
        product2.setCostPerSquareFoot(new BigDecimal("11"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax2.setState("Calfornia");
        tax2.setStateAbbrivation("CA");
        tax2.setTaxRate(new BigDecimal("5.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("5"));
        order2.setTotal(new BigDecimal("1200"));
        service.addOrder(order2);
        Order updated = service.updateOrder(order.getOrderDate(), order.getOrderNumber(), order2);
        assertNotNull(updated, "updated order should not null.");
        assertEquals(updated, order2, "updated order should equal to the last order,");
    }

    @Test
    public void testGetAllProducts() throws TaxPersistenceException,
            ProductPersistenceException, OrderPersistenceException {
        Product onlyProduct = new Product();
        onlyProduct.setProductType("Tile");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("3.25"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.25"));
        List<Product> productList = service.getAllProducts();
        assertNotNull(productList, "product list must not null.");
        assertEquals(1, productList.size(), "product list size should be 1");
        assertTrue(productList.contains(onlyProduct), "product list have the one product");
    }

    @Test
    public void testGetAllStates() throws TaxPersistenceException{
        Tax onlyTax = new Tax();
        onlyTax.setState("texas");
        onlyTax.setStateAbbrivation("TX");
        onlyTax.setTaxRate(new BigDecimal("5.15"));
        Set<String> states = service.getAllStates();
        assertNotNull(states, "states must not be null");
        assertTrue(states.contains(onlyTax.getState()));
        assertEquals(1, states.size(), "state is one.");
    }
    @Test
    public void testWriteExport()throws Exception{
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
        service.addOrder(order);
        Order retrieved = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        assertNotNull(retrieved, "retrieved order should not null.");
        assertEquals(retrieved, order, "original order and retrieved should the one order");
        //second order
        Order order2 = new Order();
        order2.setArea(new BigDecimal("120"));
        order2.setCustomerName("Tolani");
        order2.setOrderDate(LocalDate.parse("2021-09-09"));
        order2.setLaborCost(new BigDecimal("120"));
        order2.setMaterialCost(new BigDecimal("140"));
        order2.setOrderNumber(1);
        Product product2 = new Product();
        Tax tax2 = new Tax();
        product2.setProductType("Wood");
        product2.setCostPerSquareFoot(new BigDecimal("11"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("13"));
        tax2.setState("Calfornia");
        tax2.setStateAbbrivation("CA");
        tax2.setTaxRate(new BigDecimal("5.25"));
        order2.setProduct(product2);
        order2.setTax(tax2);
        order2.setTaxes(new BigDecimal("5"));
        order2.setTotal(new BigDecimal("1200"));
        service.addOrder(order2);
        service.writeExport();
    }
}
