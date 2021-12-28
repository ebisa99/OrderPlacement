/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dao;

import com.ig.flooringmastery.dto.Order;
import com.ig.flooringmastery.dto.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author ebisa
 */
public class OrderDaoFileImpl implements OrderDao {

    private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();
    private static final String DELIMITER = ",";
    private String myFile;
    private String directory;
    private String exportFile;

    public OrderDaoFileImpl() {
        directory = "C:\\Users\\ebisa\\SGRepos\\FlooringMastery\\SampleFileData\\Orders";
        exportFile = "C:\\Users\\ebisa\\SGRepos\\FlooringMastery\\SampleFileData\\Backup\\DataExport.txt";
    }

    public OrderDaoFileImpl(String orderTextFile, String exportTextFile) {
        directory = orderTextFile;
        exportFile = exportTextFile;
    }

    @Override
    public Order addOrder(Order order) throws OrderPersistenceException {
        orders.put(order.getOrderDate(), new HashMap<>());
        loadOrder();
        Order currentOrder = orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        writeOrder(order.getOrderDate());
        return currentOrder;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws OrderPersistenceException {
        loadOrder();
        return new ArrayList(orders.get(date).values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        loadOrder();
        Order currentOrder = orders.get(date).get(orderNumber);
        return currentOrder;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws OrderPersistenceException {
        loadOrder();
        Order currentOrder = orders.get(date).remove(orderNumber);
        writeOrder(date);
        return currentOrder;
    }

    @Override
    public Set<LocalDate> getAllOrderDates() throws OrderPersistenceException {
        loadOrder();
        Set<LocalDate> keys = orders.keySet();
        return keys;
    }

    @Override
    public Set<Integer> getOrderNumbers(LocalDate date) throws OrderPersistenceException {
        loadOrder();
        Set<Integer> orderNumbers = orders.get(date).keySet();
        return orderNumbers;
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order order) throws OrderPersistenceException {
        orders.put(order.getOrderDate(), new HashMap<Integer, Order>());
        loadOrder();
        orders.get(date).remove(orderNumber);
        Order updatedOrder = orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        writeOrder(date);
        return updatedOrder;
    }

    private Order unmarshallOrder(String orderAsText) {
        String[] orderFieldsArray = orderAsText.split(",");
        String orderNumber = orderFieldsArray[0];
        String customerName = orderFieldsArray[1];
        String state = orderFieldsArray[2];
        String taxRate = orderFieldsArray[3];
        String productType = orderFieldsArray[4];
        String area = orderFieldsArray[5];
        String costPerSquareFoot = orderFieldsArray[6];
        String laborCostPerSquareFoot = orderFieldsArray[7];
        String materialCost = orderFieldsArray[8];
        String laborCost = orderFieldsArray[9];
        String tax = orderFieldsArray[10];
        String total = orderFieldsArray[11];
        Product product = new Product();
        Tax taxs = new Tax();
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(orderNumber.trim()));
        order.setCustomerName(customerName.trim().replaceAll("@", ","));
        order.setArea(new BigDecimal(area));
        taxs.setStateAbbrivation(state.trim());
        taxs.setTaxRate(new BigDecimal(taxRate.trim()));
        product.setProductType(productType.trim());
        product.setCostPerSquareFoot(new BigDecimal(costPerSquareFoot.trim()));
        product.setLaborCostPerSquareFoot(new BigDecimal(laborCostPerSquareFoot.trim()));
        order.setProduct(product);
        order.setTax(taxs);
        order.setLaborCost(new BigDecimal(laborCost.trim()));
        order.setMaterialCost(new BigDecimal(materialCost.trim()));
        order.setTaxes(new BigDecimal(tax.trim()));
        order.setTotal(new BigDecimal(total.trim()));
        return order;
    }

    private void loadOrder() throws OrderPersistenceException {
        Scanner scanner;
        String currentLine;
        Order currentOrder = new Order();
        File filePath = new File(directory);
        File[] listingAllFiles = filePath.listFiles();
        for (File fileName : listingAllFiles) {
            myFile = fileName.getAbsolutePath();
            String dateString = myFile.substring(68, 76);
            StringBuilder date = new StringBuilder(dateString);
            date = date.insert(2, "/").insert(5, "/");
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            try {
                scanner = new Scanner(new BufferedReader(
                        new FileReader(myFile)));
            } catch (FileNotFoundException e) {
                throw new OrderPersistenceException("could not load order data into memory", e);
            }
            try {
                scanner.nextLine();
            } catch (Exception e) {
            }
            orders.put(localDate, new HashMap<>());
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                currentOrder.setOrderDate(localDate);
                orders.get(localDate).put(currentOrder.getOrderNumber(), currentOrder);
            }
        }
    }

    private String marshallOrder(Order order) {
        String orderAsText = order.getOrderNumber() + DELIMITER;
        orderAsText += order.getCustomerName().replaceAll(",", "@") + DELIMITER;
        orderAsText += order.getTax().getStateAbbrivation() + DELIMITER;
        orderAsText += order.getTax().getTaxRate() + DELIMITER;
        orderAsText += order.getProduct().getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getProduct().getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTaxes() + DELIMITER;
        orderAsText += order.getTotal();
        return orderAsText;
    }

    private void writeOrder(LocalDate date) throws OrderPersistenceException {
        PrintWriter writer;
        String orderAsText;
        File filePath = new File(directory);
        String stringDate = date.toString();
        stringDate = stringDate.replaceAll("-", "").substring(4) + stringDate.substring(0, 4);
        stringDate = "Orders_" + stringDate + ".txt";
        myFile = filePath.getAbsolutePath() + "\\" + stringDate;
        try {
            writer = new PrintWriter(new FileWriter(myFile));
        } catch (IOException e) {
            throw new OrderPersistenceException("could't save order data", e);
        }
        String header = "OrderNumber,CustomerName,State,"
                + "TaxRate,ProductType,Area,CostPerSquareFoot,"
                + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        writer.println(header);
        List<Order> orderList = new ArrayList(orders.get(date).values());
        for (Order order : orderList) {
            orderAsText = marshallOrder(order);
            writer.println(orderAsText);
            writer.flush();
        }
        writer.close();
    }

    @Override
    public void writeExport() throws OrderPersistenceException {
        PrintWriter writer;
        Scanner scanner;
        String currentLine;
        File filePath = new File(directory);
        //String exportFilePath =  exportFile+"\\DataExport.txt";
        try {
            writer = new PrintWriter(new FileWriter(exportFile));
        } catch (IOException e) {
            throw new OrderPersistenceException("could't save order data", e);
        }
        String header = "OrderNumber,CustomerName,State,"
                + "TaxRate,ProductType,Area,CostPerSquareFoot,"
                + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate";
        writer.println(header);
        writer.flush();
        File[] listingAllFiles = filePath.listFiles();
        for (File fileName : listingAllFiles) {
            myFile = fileName.getAbsolutePath();
            String dateString = myFile.substring(68, 76);
            StringBuilder date = new StringBuilder(dateString);
            date = date.insert(2, "-").insert(5, "-");
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(myFile)));
            } catch (FileNotFoundException e) {
                throw new OrderPersistenceException("could't read order data", e);
            }
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine() + "," + date;
                writer.println(currentLine);
            }
            writer.flush();
        }
    }
}
