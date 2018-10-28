/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
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
public class FlooringMasteryStateDaoFileImpl implements FlooringMasteryStateDao {

    public static final String STATE_FILE = "Taxes.txt";
    public static final String DELIMITER = ",";

    private List<State> states = new ArrayList();

    /**
     *
     * @param state
     * @return
     */
    @Override
    public State addState(State state
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public State removeState(State state
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     *
     * @throws FlooringMasteryPersistenceException
     */
    public void loadStates() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(STATE_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_-Could not load State info into memory.");
        }

        String headers;
        String currentLine;
        String[] currentTokens;

 //       if (scanner.nextLine() != null) {
            headers = scanner.nextLine();
//        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            State currentState = new State(currentTokens[0]);
            currentState.setTaxRate(new BigDecimal(currentTokens[1]));

            states.add(currentState);
        }
        scanner.close();
    }
    
    /**
     *
     * @param state
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    @Override
    public boolean checkForState(String state) throws FlooringMasteryPersistenceException{
        boolean exists = false;
        loadStates();
        for (State s : states){
            if (s.getStateAbbreviation().equals(state)){
                exists = true;
            }
        }
        return exists;
    }
            

    @Override
    public BigDecimal getTax(State state) throws FlooringMasteryPersistenceException {
        BigDecimal tax = null;
        loadStates();
        for (State s : states) {
            if (state.getStateAbbreviation().equals(s.getStateAbbreviation())) {
                tax = s.getTaxRate();
            }

        }
        return tax;
    }
}
