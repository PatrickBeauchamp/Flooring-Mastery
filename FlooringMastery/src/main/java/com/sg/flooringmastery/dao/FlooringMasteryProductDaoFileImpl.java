/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author patty
 */
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {

    public static final String PRODUCT_FILE = "Products.txt";
    public static final String DELIMITER = ",";

    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getProducts() {
        try {
            loadProducts();
        } catch (FlooringMasteryPersistenceException ex) {
        }
        return new ArrayList<Product>(products);
    }

    @Override
    public Product addProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product removeProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getOneProduct(String productType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadProducts() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load Product info into memory.");
        }

        String headers;
        String currentLine;
        String[] currentTokens;

        headers = scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct = new Product(currentTokens[0]);
            currentProduct.setMaterialCostPerSqFt(new BigDecimal(currentTokens[1]));
            currentProduct.setLaborCostPerSqft(new BigDecimal(currentTokens[2]));

            products.add(currentProduct);
        }
        scanner.close();
    }

    @Override
    public Product getProductInfo(String product) throws FlooringMasteryPersistenceException {
        Product newProduct = null;
        loadProducts();
        for (Product p : products) {
            if (p.getProductName().substring(0, 1).toUpperCase().equals(product)
                    || p.getProductName().equals(product)) {
                newProduct = p;
            }
        }
        return newProduct;
    }

    @Override
    public boolean checkForProduct(String product) throws FlooringMasteryPersistenceException {
        boolean exists = false;
        loadProducts();
        for (Product p : products) {
            if (p.getProductName().substring(0, 1).equals(product.substring(0, 1).toUpperCase())) {
                exists = true;
            }
        }
        return exists;
    }

}
