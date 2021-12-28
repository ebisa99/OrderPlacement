/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author ebisa
 */
public class Order {

    private int orderNumber;
    private String customerName;
    private Tax tax;
    private Product product;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxes;
    private BigDecimal total;

    @Override
    public String toString() {
        return "Order{" + "orderDate=" + orderDate + ", orderNumber=" + orderNumber
                + ", customerName=" + customerName + ", tax=" + tax + ", product="
                + product + ", area=" + area + ", materialCost=" + materialCost + ", laborCost="
                + laborCost + ", taxes=" + taxes + ", total=" + total + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.orderDate);
        hash = 13 * hash + this.orderNumber;
        hash = 13 * hash + Objects.hashCode(this.customerName);
        hash = 13 * hash + Objects.hashCode(this.tax);
        hash = 13 * hash + Objects.hashCode(this.product);
        hash = 13 * hash + Objects.hashCode(this.area);
        hash = 13 * hash + Objects.hashCode(this.materialCost);
        hash = 13 * hash + Objects.hashCode(this.laborCost);
        hash = 13 * hash + Objects.hashCode(this.taxes);
        hash = 13 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.taxes, other.taxes)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMaterialCost() {
        return getArea().multiply(getProduct().getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal getLaborCost() {
        return getArea().multiply(getProduct().getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal getTaxes() {
        BigDecimal totalCost = getMaterialCost().add(getLaborCost());
        BigDecimal taxRate = getTax().getTaxRate().divide(new BigDecimal("100"));
        return totalCost.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return getMaterialCost().add(getLaborCost()).add(getTaxes()).setScale(2, RoundingMode.HALF_UP);
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    private LocalDate orderDate;

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
