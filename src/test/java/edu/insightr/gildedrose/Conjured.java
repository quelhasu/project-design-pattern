/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.insightr.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author qunnamed
 */
public class Conjured {
    
    public Conjured() {
    }
    
    @Test
    public void qualityCheck(){
        Inventory inventory = new Inventory();
        Item[] items = inventory.getItems();
        
        Item itemConjured = items[items.length - 1];
        assertThat(itemConjured.getName(), is("Conjured Mana Cake"));
        assertThat(itemConjured.getQuality(), is(6));
        
        inventory.updateQuality();
        assertThat(itemConjured.getQuality(), is(4));
    }
    
}
