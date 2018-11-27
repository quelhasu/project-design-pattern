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
public class InventoryIT {
    private Item[] ancienneListDesItems;
    private Inventory inv;
    
    
    @Before
    public void setUp() {
        inv = new Inventory();
        ancienneListDesItems = inv.getItems();
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testQualityDegrades() throws Exception{
        inv.updateQuality();
        Item[] items = inv.getItems();
        for(int i=0; i<items.length; i++){
            if(ancienneListDesItems[i].getSellIn() == 0 && ancienneListDesItems[i].getName() != "Sulfuras, Hand of Ragnaros"){
                assertEquals(ancienneListDesItems[i].getQuality() - 2,
                             items[i].getQuality());
            }
        }
    }
    
    
}
