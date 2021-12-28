/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Product;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class ProductDaoFileImpl implements ProductDao {

    private Map<String, Product> products = new HashMap<>();
    private static final String DELIMITER = ",";
    private final String PRODUCT_FILE;

    public ProductDaoFileImpl() {
        PRODUCT_FILE = "Products.txt";
    }

    public ProductDaoFileImpl(String productTextFile) {
        PRODUCT_FILE = productTextFile;
    }

    @Override
    public Product addProduct(Product product) throws ProductPersistenceException {
        loadProduct();
        Product currentProduct = products.put(product.getProductType(), product);
        writeProduct();
        return currentProduct;
    }

    @Override
    public List<Product> getAllProducts() throws ProductPersistenceException {
        loadProduct();
        return new ArrayList(products.values());
    }

    @Override
    public Product getProduct(String productType) throws ProductPersistenceException {
        loadProduct();
        Product currentProduct = products.get(productType);
        return currentProduct;
    }

    @Override
    public Set<String> getAllProductTypes() throws ProductPersistenceException {
        loadProduct();
        return products.keySet();
    }

    private Product unmarshallProduct(String productAsText) {
        String[] productFieldsArray = productAsText.split(DELIMITER);
        String productType = productFieldsArray[0];
        String costPerSquareFoot = productFieldsArray[1];
        String laborCostPerSquareFoot = productFieldsArray[2];
        Product productObject = new Product();
        productObject.setProductType(productType.toLowerCase());
        productObject.setCostPerSquareFoot(new BigDecimal(costPerSquareFoot));
        productObject.setLaborCostPerSquareFoot(new BigDecimal(laborCostPerSquareFoot));
        return productObject;
    }

    private void loadProduct() throws ProductPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new ProductPersistenceException("could not load Product data into memory.", e);
        }
         try {
                scanner.nextLine();
            } catch (Exception e) {
            }
        String currentLine;
        Product currentProduct;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    private String marshallProduct(Product product) {
        String productAsText;
        productAsText = product.getProductType() + DELIMITER;
        productAsText += product.getCostPerSquareFoot() + DELIMITER;
        productAsText += product.getLaborCostPerSquareFoot();
        return productAsText;
    }

    private void writeProduct() throws ProductPersistenceException {
        PrintWriter out;
        String currentLine;
                 try {
            out = new PrintWriter(new FileWriter(PRODUCT_FILE));
        } catch (IOException e) {
            throw new ProductPersistenceException("couldn't save product data.", e);
        }
        String header = "ProductType,CostPerSquareFoot,LaborCostPerSquareFoot";
        out.println(header);
        List<Product> productList = getAllProducts();
        for (Product product : productList) {
            currentLine = marshallProduct(product);
            out.println(currentLine);
            out.flush();
        }
        out.close();
    }
}
