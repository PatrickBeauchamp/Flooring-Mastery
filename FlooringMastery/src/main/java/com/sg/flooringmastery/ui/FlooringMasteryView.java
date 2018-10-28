/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import static java.lang.Double.parseDouble;
import java.util.List;

/**
 *
 * @author patty
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders By Date");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices (1-6): ", 1, 6);
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown command...");
    }

    public void displayOrder(Order order) {
        io.print("Name: " + order.getCustomerName().replace("|", ","));
        io.print("State: " + String.valueOf(order.getState().getStateAbbreviation()));
        io.print("Area: " + String.valueOf(order.getArea()));
        io.print("Product Type: " + order.getProduct().getProductName());
        io.print("Total Material Cost: " + order.getMaterialCost());
        io.print("Total Labor Cost: " + order.getLaborCost());
        io.print("Tax: " + order.getTax());
        io.print("Total Cost: " + order.getTotalCost());

    }

    /**
     *
     * @param orderID
     * @return
     */
    public Order getNewOrderInfo(String orderID) {
        String name = io.readString("Enter customer name: ");
        String stateName = io.readString("Enter customer state (as two-letter abbreviation: OH, IN, PA, or MI): ");
        String productType = io.readString("Enter the first letter only of your flooring choice: C)arpet, W)ood, L)aminate, or T)ile: ");
        double area = io.readDouble("Enter area in square feet (no less than 1, please): ");

        State currentState = new State(stateName.toUpperCase());
        Product currentProduct = new Product(productType.toUpperCase());
        Order currentOrder = new Order(orderID);
        currentOrder.setCustomerName(name.replace(",", "|"));
        currentOrder.setProduct(currentProduct);
        currentOrder.setState(currentState);
        currentOrder.setArea(area);

        return currentOrder;
    }

    public String displayNewOrderSummary(Order order) {
        io.print("Name: " + order.getCustomerName().replace("|", ","));
        io.print("Area: " + String.valueOf(order.getArea()));
        io.print("State: " + order.getState().getStateAbbreviation());
        io.print("Product type: " + order.getProduct().getProductName());
        io.print("Total Cost: " + order.getTotalCost().toString());
        String response = io.readString("Do you wish to commit (save, edit, or delete) this order (Y/N): ").substring(0, 1)
                .toUpperCase();
        return response;
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public void displayAllOrders(List<Order> orders) {
        orders.forEach((o) -> {
            io.print("Name: " + o.getCustomerName().replace("|", ",") + " | Order ID: " + o.getOrderId()
                    + " | Flooring Material: " + o.getProduct().getProductName()
                    + " | Area: " + o.getArea()
                    + " | State: " + o.getState().getStateAbbreviation()
                    + " | Total Cost: " + o.getTotalCost());
        });
    }

    public String getDate() {
        return io.readString("Please enter the date for which you wish to display the orders (in the form MMDDYYYY): ");
    }

    public void displayNoOrders() {
        io.print("Sorry, no matching orders...");
    }

    public String getOrderId() {
        return io.readString("Please enter the Order ID: ");
    }

    public Order getEditOrder(Order order) {
        String customer = io.readString("Enter customer name (" + order.getCustomerName().replace("|", ",") + "): ").replace(",", "|");
        String stateName = io.readString("Enter state (" + order.getState().getStateAbbreviation()
                + ")--(OH, PA, IN, MI): ").toUpperCase();
        String productName = io.readString("Enter product (" + order.getProduct().getProductName()
                + ")--(C)arpet, T)ile, L)aminate, W)ood): ").toUpperCase();
        String area = io.readString("Enter area (" + order.getArea() + ")(More than 1 sq. ft.): ");
        boolean canParse = true;
        if (!customer.equals("")) {
            order.setCustomerName(customer);
        }
        if (!stateName.equals("")) {
            order.getState().setStateAbbreviation(stateName);
        }
        if (!productName.equals("")) {
            order.getProduct().setProductName(productName.toUpperCase());
        }
        if (!area.equals("")) {
            while (canParse) {

                try {
                    double areaDouble = Double.parseDouble(area);
                    canParse = false;
                    order.setArea(areaDouble);
                } catch (NumberFormatException ex) {
                    io.print("Invalid format");
                    area = io.readString("Enter area (" + order.getArea() + "): ");
                }
            }
        }
        return order;

    }

    public void displayTraining() {
        io.print("In training mode, no data will be saved...");
    }

    public void displaySaved() {
        io.print("Current work session has been saved...");
    }
}