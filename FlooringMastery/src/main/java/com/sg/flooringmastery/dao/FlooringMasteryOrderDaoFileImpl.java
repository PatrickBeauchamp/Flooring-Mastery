/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Double.parseDouble;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author patty
 */
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao {

    public static final String DELIMITER = ",";

    private Map<String, List<Order>> allOrders = new HashMap<>();

    private List<Order> orders = new ArrayList<Order>();

    private static final String MODE = "Mode.txt";

    /**
     *
     * @return @throws FlooringMasteryPersistenceException
     */
    public List<String> getAllFileNames() {

        List<String> fileNames = new ArrayList<>();
        File folder = new File("allOrders");
        File[] files = folder.listFiles();
        for (File file : files) {
            fileNames.add(file.getAbsolutePath());
        }

        return fileNames;

    }

    @Override
    public void getAllOrders() throws FlooringMasteryPersistenceException {

        List fileNames = getAllFileNames();
        Scanner scanner;
        int i = 0;
        while (i < fileNames.size()) {
            String fileName = (fileNames.get(i++).toString());
            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader(fileName)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException(
                        "-_- Could not load Order data into memory.");
            }

            String[] splitFileName = fileName.toString().split("Orders_");
            String orderDate = splitFileName[1].substring(0, 8);
            State state;
            Product product;
            String headers;
            Order currentOrder = null;
            String currentLine;
            String[] currentTokens;
            List orders = new ArrayList<Order>();

            headers = scanner.nextLine();

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                currentOrder = new Order(currentTokens[0]);
                currentOrder.setCustomerName(currentTokens[1]);
                currentOrder.setState(state = new State(currentTokens[2]));
                state.setTaxRate(new BigDecimal(currentTokens[3]));
                currentOrder.setProduct(product = new Product(currentTokens[4]));
                currentOrder.setArea(parseDouble(currentTokens[5]));
                product.setMaterialCostPerSqFt(new BigDecimal(currentTokens[6]));
                product.setLaborCostPerSqft(new BigDecimal(currentTokens[7]));
                currentOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(currentTokens[9]));
                currentOrder.setTax(new BigDecimal(currentTokens[10]));
                currentOrder.setTotalCost(new BigDecimal(currentTokens[11]));
                currentOrder.setOrderDate(orderDate);
                orders.add(currentOrder);
            }
            scanner.close();
            allOrders.put(currentOrder.getOrderDate(), orders);

        }

    }

    @Override
    public Order addOrder(String orderId, Order order) throws FlooringMasteryPersistenceException {

        String date = getDateString();
        return order;
    }

    @Override
    public Order getOrder(String date, String orderId) throws FlooringMasteryPersistenceException {

        List<Order> orders = allOrders.get(date);
        Order order = null;

        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                order = o;
            }
        }
        return order;
    }

    @Override
    public Order removeOrder(Order order) {
        List<Order> orders = new ArrayList<>();
        orders = allOrders.get(order.getOrderDate());
        Order orderToRemove = null;
        for (Order o : orders) {
            if (o.getOrderId().equals(order.getOrderId())) {
                orderToRemove = o;
            }
        }
        orders.remove(orderToRemove);
        return order;
    }

    @Override
    public List getOrdersByDate(String date) throws FlooringMasteryPersistenceException {

        return allOrders.get(date);

    }

    @Override
    public List<Order> addTempOrder(Order order
    ) {
        List<Order> orders = allOrders.get(order.getOrderDate());
        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.add(order);

        allOrders.put(order.getOrderDate(), orders);

        return orders;
    }

    @Override
    public String getDateString() {
        LocalDate ld = LocalDate.now();
        String date = (ld.format(DateTimeFormatter.ofPattern("MMddyyyy"))).replace("-", "");
        return date;
    }

    @Override
    public void saveData() throws FlooringMasteryPersistenceException {

        Set<String> keys = allOrders.keySet();

        String obliterateDate = null;
        for (String key : keys) {
            String fileName = "allOrders\\Orders_" + key + ".txt";
            File file = new File(fileName);
            file.delete();
            if (allOrders.get(key).isEmpty()) {
                obliterateDate = key;
            }
            allOrders.remove(obliterateDate);
        }

        keys = allOrders.keySet();

        for (String key : keys) {

            String fileName = "allOrders\\Orders_" + key + ".txt";
            PrintWriter out = null;
            int i = 0;
            try {
                out = new PrintWriter(new FileWriter(fileName), true);
            } catch (IOException e) {
                throw new FlooringMasteryPersistenceException(
                        "Could not save order data.", e);
            }
            List<Order> orders = allOrders.get(key);
            for (Order o : orders) {
                if (i == 0) {
                    out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
                            + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                    i = 1;
                }
                out.println(o.getOrderId() + DELIMITER + o.getCustomerName() + DELIMITER
                        + o.getState().getStateAbbreviation() + DELIMITER + o.getState().getTaxRate() + DELIMITER
                        + o.getProduct().getProductName() + DELIMITER + o.getArea() + DELIMITER
                        + o.getProduct().getMaterialCostPerSqFt() + DELIMITER
                        + o.getProduct().getLaborCostPerSqft() + DELIMITER + o.getMaterialCost()
                        + DELIMITER + o.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                        + o.getTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                        + o.getTotalCost().setScale(2, RoundingMode.HALF_UP));
                out.flush();
            }
            out.close();
        }
    }

    @Override
    public boolean getMode() throws FlooringMasteryPersistenceException {

        Scanner scanner;
        String mode = null;
        boolean prod = false;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(MODE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load Order data into memory.");
        }
        while (scanner.hasNextLine()) {
            mode = scanner.nextLine();
        }
        if (mode.equalsIgnoreCase("PROD")) {
            prod = true;
        }
        return prod;
    }
}
