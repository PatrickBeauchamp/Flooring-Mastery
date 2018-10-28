/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import java.util.List;

/**
 *
 * @author patty
 */
public interface FlooringMasteryProductDao {
    
    List<Product> getProducts();
    
    Product addProduct(Product product);
    
    Product removeProduct(Product product);
    
    Product getOneProduct(String productType);
    Product getProductInfo(String product) throws FlooringMasteryPersistenceException;
    
    boolean checkForProduct(String product) throws FlooringMasteryPersistenceException;
    
}
