/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author patty
 */
public class FlooringMasteryStateDaoTest {
    
    FlooringMasteryStateDao dao = new FlooringMasteryStateDaoStubImpl();
    
    public FlooringMasteryStateDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getStates method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testGetStates() {
    }

    /**
     * Test of addState method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testAddState() {
    }

    /**
     * Test of removeState method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testRemoveState() {
    }

    /**
     * Test of getOneState method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testGetOneState() {
    }

    /**
     * Test of getTax method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testRightGetTax() throws Exception {
        State oneState = new State("FL");
        oneState.setStateAbbreviation("FL");
                
        BigDecimal thisTax = dao.getTax(oneState);
        
        assertEquals(new BigDecimal("7.25"), thisTax);
    }
    
    @Test
    public void testWongGetTax() throws Exception{
        State oneState = new State("FL");
        oneState.setStateAbbreviation("FL");
                
        BigDecimal thisTax = dao.getTax(oneState);
        
        assertNotEquals(new BigDecimal("8.00"), thisTax);
        
    }

    /**
     * Test of checkForState method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testCheckForRightState() throws Exception {
        State oneState = new State("NH");
        oneState.setStateAbbreviation("NH");
        
        boolean exists = dao.checkForState(oneState.getStateAbbreviation());
        assertFalse(exists);     
        
    }
    
    @Test
    public void testForRightState() throws Exception{
        State oneState = new State("FL");
        oneState.setStateAbbreviation("FL");
        
        boolean exists = dao.checkForState(oneState.getStateAbbreviation());
        assertTrue(exists);    
        
    }

    
}
