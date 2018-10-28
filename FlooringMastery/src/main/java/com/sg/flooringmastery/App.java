/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author patty
 */
public class App {

    public static void main(String[] args) throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        
            ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
            controller.run();
    }
}
