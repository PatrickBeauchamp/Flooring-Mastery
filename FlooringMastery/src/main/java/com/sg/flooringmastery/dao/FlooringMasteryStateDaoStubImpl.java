/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.service.FlooringMasteryPersistenceException;
import java.math.BigDecimal;

/**
 *
 * @author patty
 */
public class FlooringMasteryStateDaoStubImpl implements FlooringMasteryStateDao {

    private State onlyState;

    public FlooringMasteryStateDaoStubImpl() {
        onlyState = new State("FL");
        onlyState.setStateAbbreviation("FL");
        onlyState.setTaxRate(new BigDecimal("7.25"));
    }

    @Override
    public State addState(State state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public State removeState(State state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getTax(State state) throws FlooringMasteryPersistenceException {
        if (state.getStateAbbreviation().equalsIgnoreCase(onlyState.getStateAbbreviation())) {
            return onlyState.getTaxRate();
        } else {
            return null;
        }
    }

    @Override
    public boolean checkForState(String state) throws FlooringMasteryPersistenceException {
        if (state.equalsIgnoreCase(onlyState.getStateAbbreviation())) {
            return true;
        } else {
            return false;
        }
    }

}
