/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Product;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
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
public class ProductDaoFileImplTest {

    ProductDao productTestDao;

    public ProductDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testProduct.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        //productTestDao = new ProductDaoFileImpl(testFile);
        ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    productTestDao = 
        ctx.getBean("productDao", ProductDaoFileImpl.class);
    }

    @AfterEach
    public void tearDown() {
    }
    /*
    @org.junit.jupiter.api.Test
    public void testSomeMethod() {
        fail("The test case is a prototype.");
    }
    */
    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setCostPerSquareFoot(new BigDecimal("4.15"));
        product1.setLaborCostPerSquareFoot(new BigDecimal("6.15"));
        product1.setProductType("tile");
        //second product
        Product product2 = new Product();
        product2.setCostPerSquareFoot(new BigDecimal("7.15"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("8.15"));
        product2.setProductType("wood");
        productTestDao.addProduct(product1);
        productTestDao.addProduct(product2);
        List<Product> productList = productTestDao.getAllProducts();
        assertNotNull(productList, "THe List of product should not null");
        assertEquals(2, productList.size(), "the number of product list should be 2.");
    }
    @Test
    public void testGetAddProduct() throws Exception{
        Product product = new Product();
        product.setCostPerSquareFoot(new BigDecimal("4.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("6.15"));
        product.setProductType("tile");
        productTestDao.addProduct(product);
        Product retrievedProduct = productTestDao.getProduct(product.getProductType());
        assertEquals(product.getProductType(), retrievedProduct.getProductType(), "checking Product Type.");
        assertEquals(product.getCostPerSquareFoot(), retrievedProduct.getCostPerSquareFoot(),
                "checking cost per square foot.");
        assertEquals(product.getLaborCostPerSquareFoot(), retrievedProduct.getLaborCostPerSquareFoot(), 
                "checking Labor cost per square foot.");
    }
    @Test
    public void testGetAllProductTypes() throws Exception{
        Product product1 = new Product();
        product1.setCostPerSquareFoot(new BigDecimal("4.15"));
        product1.setLaborCostPerSquareFoot(new BigDecimal("6.15"));
        product1.setProductType("tile");
        //second product
        Product product2 = new Product();
        product2.setCostPerSquareFoot(new BigDecimal("7.15"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("8.15"));
        product2.setProductType("wood");
        productTestDao.addProduct(product1);
        productTestDao.addProduct(product2);
        List<Product> productList = productTestDao.getAllProducts();
        assertNotNull(productList, "THe List of product should not null");
        assertEquals(2, productList.size(), "the number of product list should be 2.");
        Set<String> listOfProductTypes = productTestDao.getAllProductTypes();
        assertNotNull(listOfProductTypes, "list of product Types should not null.");
        assertEquals(2, listOfProductTypes.size(), "the number of states should be 2.");
        assertTrue(listOfProductTypes.contains("tile"), "list of produt types should contain Tile");
        assertTrue(listOfProductTypes.contains("wood"), "list of product Types should contain Wood.");
        
    }
}
