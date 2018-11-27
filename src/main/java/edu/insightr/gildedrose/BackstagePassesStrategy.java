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
public class BackstagePassesStrategy implements IStrategyUpdate {

    @Override
    public void update(Item item) {
        // Quality change
        if (item.isExpired()) {
            item.setQuality(0);
        } else {
            if (item.getSellIn() <= 5) {
                item.setQuality(item.getQuality() + 3);
            } else if (item.getSellIn() <= 10) {
                item.setQuality(item.getQuality() + 2);
            } else {
                item.setQuality(item.getQuality() + 1);
            }
        }

        // SellIn change
        item.setSellIn(item.getSellIn() - 1);
    }
    
}
