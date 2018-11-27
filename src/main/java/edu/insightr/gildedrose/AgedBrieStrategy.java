/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.insightr.gildedrose;

/**
 *
 * @author qunnamed
 */
public class AgedBrieStrategy implements IStrategyUpdate{

    @Override
    public void update(Item item) {
        // Quality change
        int rate = item.isExpired() ? 2 : 1;
        item.setQuality(item.getQuality() + rate);

        // SellIn change
        item.setSellIn(item.getSellIn() - 1);

        // If Expired
        if  (item.isExpired()) {
            item.setQuality (item.getQuality() + 1);
        }
    }
    
}
