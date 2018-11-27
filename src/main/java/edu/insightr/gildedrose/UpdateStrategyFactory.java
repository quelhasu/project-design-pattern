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
public class UpdateStrategyFactory {
    
    IStrategyUpdate strat;
    
    public UpdateStrategyFactory(Item item){
        if(item.getName().equals("Aged Brie")) strat = new AgedBrieStrategy();
        else if (item.getName().equals("Backstage passes to a TAFKAL80ETC concert")) strat = new BackstagePassesStrategy();
        else if (item.getName().equals("Conjured Mana Cake")) strat = new ConjuredItemStrategy();
        else if (item.getName().equals("Sulfuras, Hand of Ragnaros")) strat = new SulfurasStrategy();
        else strat = new OtherStrategy();
    }
}
