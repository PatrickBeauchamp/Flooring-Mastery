/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patty
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        boolean keepGoing = true;
        int menuSelection = 0;
        boolean mode = service.getMode();

        service.getAllOrders();

        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrdersByDate();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        if (mode) {
                            saveData();
                            alertSaved();
                        } else {
                            alertTraining();
                        }
                        break;
                    case 6:
                        if (mode) {
                            saveData();
                            alertSaved();
                        } else {
                            alertTraining();
                        }
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();

                }

            }
        } catch (FlooringMasteryPersistenceException ex) {
        }

    }

    private void addOrder() throws FlooringMasteryPersistenceException,
            FlooringMasteryDataValidationException {
        String orderId = service.getNewId();
        Order newOrder = view.getNewOrderInfo(orderId);
        Order order = new Order(orderId);
        try {
            order = service.calculateOrder(orderId, newOrder);
            String response = view.displayNewOrderSummary(order);
            if (response.equals("Y")) {
                service.addTempOrder(order);
            }

        } catch (FlooringMasteryDataValidationException ex) {
            view.displayErrorMessage(ex.getMessage());
        }

    }

    private int getMenuSelection() throws FlooringMasteryPersistenceException {
        return view.printMenuAndGetSelection();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private String displayOrdersByDate() throws FlooringMasteryPersistenceException {
        String date = view.getDate();
        List<Order> orders = service.getOrdersByDate(date);
        try {
            view.displayAllOrders(orders);
        } catch (NullPointerException ex) {
            view.displayNoOrders();
        }
        return date;

    }

    private void editOrder() throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {

        Order order;
        try {
            String date = view.getDate();
            String orderId = view.getOrderId();
            order = service.getOrder(date, orderId);
            view.displayOrder(order);
            view.getEditOrder(order);
            order = service.calculateOrder(orderId, order);
            String response = view.displayNewOrderSummary(order);
            if (response.equals("Y")) {
                service.removeOrder(order);
                service.addTempOrder(order);
            }
        } catch (NullPointerException ex) {
            view.displayNoOrders();
        }

    }

    private void removeOrder() throws FlooringMasteryPersistenceException {
        try {
            String date = view.getDate();
            String orderId = view.getOrderId();
            Order order = service.getOrder(date, orderId);
            String response = view.displayNewOrderSummary(order);
            if (response.equals("Y")) {
                service.removeOrder(order);
            }
        } catch (NullPointerException ex) {
            view.displayNoOrders();
        }
    }

    private void saveData() {
        service.saveData();
    }

    private void alertTraining() {
        view.displayTraining();
    }

    private void alertSaved() {
        view.displaySaved();
    }
}
