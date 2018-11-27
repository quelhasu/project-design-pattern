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
public class UpdateVisitor implements IVisitor {

    @Override
    public void visit(AgedBrie aThis) {
        // Quality change
        int rate = aThis.isExpired() ? 2 : 1;
        aThis.setQuality(aThis.getQuality() + rate);

        // SellIn change
        aThis.setSellIn(aThis.getSellIn() - 1);

        // If Expired
        if (aThis.isExpired()) {
            aThis.setQuality(aThis.getQuality() + 1);
        }
    }

    @Override
    public void visit(BackstagePasses aThis) {
        // Quality change
        if (aThis.isExpired()) {
            aThis.setQuality(0);
        } else {
            if (aThis.getSellIn() <= 5) {
                aThis.setQuality(aThis.getQuality() + 3);
            } else if (aThis.getSellIn() <= 10) {
                aThis.setQuality(aThis.getQuality() + 2);
            } else {
                aThis.setQuality(aThis.getQuality() + 1);
            }
        }

        // SellIn change
        aThis.setSellIn(aThis.getSellIn() - 1);

    }

    @Override
    public void visit(ConjuredItem aThis) {
        // Quality change
        int rate = aThis.isExpired() ? 4 : 2;
        aThis.setQuality(aThis.getQuality() - rate);

        // SellIn change
        aThis.setSellIn(aThis.getSellIn() - 2);

        // If Expired
        if (aThis.isExpired()) {
            aThis.setQuality(aThis.getQuality() - 1);
        }
    }

    @Override
    public void visit(Sulfuras aThis) {
    }

    @Override
    public void visit(OtherItem aThis) {
        // Quality change
        int rate = aThis.isExpired() ? 2 : 1;
        aThis.setQuality(aThis.getQuality() - rate);

        // SellIn change
        aThis.setSellIn(aThis.getSellIn() - 1);

        // If Expired
        if (aThis.isExpired()) {
            aThis.setQuality(aThis.getQuality() - 1);
        }
    }

}
