/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author patty
 */
public interface FlooringMasteryOrderDao {
    
    Order getOrder(String date, String orderId) throws
            FlooringMasteryPersistenceException;
    
    /**
     *
     * @param orderId
     * @param order
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    Order addOrder(String orderId, Order order) throws FlooringMasteryPersistenceException;
    
   // List getAllFiles();

    /**
     *
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    
    void getAllOrders() throws FlooringMasteryPersistenceException;
    
    List getOrdersByDate(String date) throws FlooringMasteryPersistenceException;
    
    List<Order> addTempOrder(Order order);
    
    String getDateString();
    
    Order removeOrder(Order order);
    
    void saveData() throws FlooringMasteryPersistenceException;
    
    boolean getMode() throws FlooringMasteryPersistenceException;
    
}
