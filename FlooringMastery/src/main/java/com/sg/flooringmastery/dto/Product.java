/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author patty
 */
public class Product {

    private String productName;

    private BigDecimal materialCostPerSqFt;

    private BigDecimal laborCostPerSqft;

    public Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getMaterialCostPerSqFt() {
        return materialCostPerSqFt;
    }

    public void setMaterialCostPerSqFt(BigDecimal materialCostPerSqFt) {
        this.materialCostPerSqFt = materialCostPerSqFt;
    }

    public BigDecimal getLaborCostPerSqft() {
        return laborCostPerSqft;
    }

    public void setLaborCostPerSqft(BigDecimal laborCostPerSqft) {
        this.laborCostPerSqft = laborCostPerSqft;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.productName);
        hash = 41 * hash + Objects.hashCode(this.materialCostPerSqFt);
        hash = 41 * hash + Objects.hashCode(this.laborCostPerSqft);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.materialCostPerSqFt, other.materialCostPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqft, other.laborCostPerSqft)) {
            return false;
        }
        return true;
    }

}
