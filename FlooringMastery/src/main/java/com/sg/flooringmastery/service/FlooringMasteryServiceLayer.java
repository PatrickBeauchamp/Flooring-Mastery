/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author patty
 */
public interface FlooringMasteryServiceLayer {
    
    Order getOrder(String date, String orderId) throws FlooringMasteryPersistenceException;
    
    String getNewId() throws FlooringMasteryPersistenceException;

    Order calculateOrder(String orderId, Order newOrder) throws FlooringMasteryDataValidationException,
            FlooringMasteryPersistenceException;
    
    void getAllOrders() throws FlooringMasteryPersistenceException;
    
    List getOrdersByDate(String date) throws FlooringMasteryPersistenceException;
    
    List addTempOrder(Order order);
    
    void removeOrder(Order order);
    
    void saveData();
    
    boolean getMode() throws FlooringMasteryPersistenceException;
    
    
    
}
