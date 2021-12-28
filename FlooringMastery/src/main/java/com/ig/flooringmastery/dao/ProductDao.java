/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Product;
import java.util.*;

/**
 *
 * @author ebisa
 */
public interface ProductDao {

    List<Product> getAllProducts() throws ProductPersistenceException;

    Product addProduct(Product product) throws ProductPersistenceException;

    Product getProduct(String productType) throws ProductPersistenceException;

    Set<String> getAllProductTypes() throws ProductPersistenceException;
}
