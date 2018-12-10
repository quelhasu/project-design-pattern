/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpattern.esilv.s7.project;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 *
 * @author victorseguin
 * @author fenohanta
 */


@RunWith(Cucumber.class)
@CucumberOptions(strict=true)
public class InventoryTest {
    protected Inventory inv;
    Item[] ancienneListeDesItems;

    @Before
    public void setUp()
    {
        inv = new Inventory();
        ancienneListeDesItems = inv.getItems();
    }

    @Test
    public void testQualityIsNeverNegative() throws Exception
    {
        Item[] items;
        int nbIterations = 10;
        for (int i=0;i < nbIterations;i++)
        {
            inv.updateQuality();
            items = inv.getItems();
            for (int j=0;j < items.length;j++)
            {
                assertTrue(items[j].getQuality() >= 0);
            }
        }
    }

    @Test
    public void testQualityAgedBrie() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Aged Brie"))
            {
                assertTrue(items[i].getQuality() >= ancienneListeDesItems[i].getQuality());
            }
        }
    }
    @Test
    public void testSulfurasNotDecreaseInQuality() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertFalse(items[i].getQuality() < ancienneListeDesItems[i].getQuality());
            }
        }
    }

    @Test
    public void testSulfurasNeverHasToBeSold() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertFalse(items[i].getSellIn() < ancienneListeDesItems[i].getSellIn());
            }
        }
    }

    @Test
    public void testSulfurasQualityIs80() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertTrue(items[i].getQuality() == 80);
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy2When10OrLess() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert") && items[i].getSellIn() <= 10)
            {
                assertEquals(items[i].getQuality(),ancienneListeDesItems[i].getQuality() + 2);
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy3When5OrLess() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert") && items[i].getSellIn() <= 5)
            {
                assertEquals(items[i].getQuality(),ancienneListeDesItems[i].getQuality() + 3);
            }
        }
    }


    @Test
    public void updateQuality(){

        Item[] items = inv.getItems();
        
        for (int i=0;i < items.length;i++){
            if (items[i].getName().equals("Conjured Mana Cake"))
            {
                assertThat(items[i].getName(),is("Conjured Mana Cake"));
                
                assertThat(items[i].getQuality(),is(6));
                inv.updateQuality();
                
                assertThat(items[i].getQuality(),is(7));
            }
        }
    }

    @org.junit.Test
    public void getItems() {
    }

    @org.junit.Test
    public void setItems() {
    }

    @After
    public void tearDown()
    {

    }
}

