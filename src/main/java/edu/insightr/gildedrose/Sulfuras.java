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
public class Sulfuras extends Item {

    public Sulfuras(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
    
}
