/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryProductDao;
import com.sg.flooringmastery.dao.FlooringMasteryStateDao;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patty
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    FlooringMasteryOrderDao orderDao;
    FlooringMasteryProductDao productDao;
    FlooringMasteryStateDao stateDao;

    public FlooringMasteryServiceLayerImpl(
            FlooringMasteryOrderDao orderDao, FlooringMasteryProductDao productDao, FlooringMasteryStateDao stateDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.stateDao = stateDao;
    }

    @Override
    public Order getOrder(String date, String orderId) throws FlooringMasteryPersistenceException {
        return orderDao.getOrder(date, orderId);
    }

    /**
     *
     * @return
     */
    @Override
    public String getNewId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 5);
    }

    /**
     *
     * @param orderId
     * @param order
     * @return 
     * @throws FlooringMasteryDataValidationException
     * @throws
     * com.sg.flooringmastery.service.FlooringMasteryPersistenceException
     */
    @Override
    public Order calculateOrder(String orderId, Order order) throws FlooringMasteryDataValidationException,
            FlooringMasteryPersistenceException {
        BigDecimal hundred = new BigDecimal("100");
        validateFlooringMasteryStateData(order);
        validateFlooringMasteryProductData(order);
        validateFlooringMasteryAreaData(order);
        order.getState().setTaxRate(stateDao.getTax(order.getState()));
        order.setProduct(productDao.getProductInfo(order.getProduct().getProductName()));
        order.setMaterialCost(order.getProduct().getMaterialCostPerSqFt().multiply(BigDecimal.valueOf(order.getArea()))
                .setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getProduct().getLaborCostPerSqft().multiply(BigDecimal.valueOf(order.getArea()))
                .setScale(2, RoundingMode.HALF_UP));
        order.setTax((order.getMaterialCost().add(order.getLaborCost())).multiply((order.getState().getTaxRate())
                .divide(hundred)).setScale(2, RoundingMode.HALF_UP));
        order.setTotalCost(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax())
                .setScale(2, RoundingMode.HALF_UP));
        if (order.getOrderDate() == null) {
            order.setOrderDate(orderDao.getDateString());
        }
        return order;
    }

    /**
     *
     * @return @throws FlooringMasteryPersistenceException
     */
    @Override
    public void getAllOrders() throws FlooringMasteryPersistenceException {
        orderDao.getAllOrders();

    }

    @Override
    public List getOrdersByDate(String date) throws FlooringMasteryPersistenceException {
        List<Order> orders = orderDao.getOrdersByDate(date);
        return orders;
    }

    private void validateFlooringMasteryStateData(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        boolean exists = stateDao.checkForState(order.getState().getStateAbbreviation());
        if (exists) {
        } else {
            throw new FlooringMasteryDataValidationException(
                    "ERROR: Please enter the information in the requested format...");
        }
    }

    private void validateFlooringMasteryProductData(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        boolean exists = productDao.checkForProduct(order.getProduct().getProductName());
        if (exists) {
        } else {
            throw new FlooringMasteryDataValidationException(
                    "ERROR: Please enter the information in the requested format...");
        }
    }

    private void validateFlooringMasteryAreaData(Order order) throws
            FlooringMasteryDataValidationException {
        if (order.getArea() < 1) {
            throw new FlooringMasteryDataValidationException(
                    "ERROR: Please enter the information in the requested format...");
        }
    }

    @Override
    public List<Order> addTempOrder(Order order) {

        List<Order> orders = orderDao.addTempOrder(order);
        return orders;

    }

    @Override
    public void removeOrder(Order order) {
        Order removeOrder = orderDao.removeOrder(order);
    }

    @Override
    public void saveData() {
        try {
            orderDao.saveData();
        } catch (FlooringMasteryPersistenceException ex) {
        }
    }
    
    @Override
    public boolean getMode() throws FlooringMasteryPersistenceException{
        return orderDao.getMode();
    }
}
