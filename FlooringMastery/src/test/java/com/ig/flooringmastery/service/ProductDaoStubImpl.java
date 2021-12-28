/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.service;

import com.ig.flooringmastery.dao.*;
import com.ig.flooringmastery.dto.*;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class ProductDaoStubImpl implements ProductDao{
    public Product onlyProduct;

    public ProductDaoStubImpl() {
        onlyProduct = new Product();
        onlyProduct.setProductType("Tile");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("3.25"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.25"));
    }

    public ProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }
    @Override
    public List<Product> getAllProducts() throws ProductPersistenceException {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public Product addProduct(Product product) throws ProductPersistenceException {
        if (product.getProductType().equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product getProduct(String productType) throws ProductPersistenceException {
       if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Set<String> getAllProductTypes() throws ProductPersistenceException {
        Set<String> productList = new HashSet<>();
        productList.add(onlyProduct.getProductType());
        return productList;
    }
    
}
