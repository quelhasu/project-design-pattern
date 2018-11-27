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
public interface IVisitor {

    public void visit(AgedBrie aThis);
    
    public void visit(BackstagePasses aThis);

    public void visit(ConjuredItem aThis);

    public void visit(Sulfuras aThis);

    public void visit(OtherItem aThis);
    
}
