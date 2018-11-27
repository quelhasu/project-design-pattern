/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.insightr.gildedrose;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author qunnamed
 */
public class ItemIT {
    Item item;
    
    @Before
    public void setUp() {
//        item = new Item("name", 10, 2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testQuality() {
        assertEquals(2, item.getQuality());
    }
    
    public ItemIT() {
        
    }

    
}
