/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpattern.esilv.s7.model;

import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victorseguin
 */
public class InventoryTest {
    protected Inventory inv;
    ArrayList<Item> ancienneListeDesItems;

    @Before
    public void setUp()
    {
        inv = new Inventory();
        ancienneListeDesItems = inv.getItems();
    }

    @Test
    public void testQualityIsNeverNegative() throws Exception
    {
        ArrayList<Item> items = inv.getItems();
        
        int nbIterations = 10;
        for (int i=0;i < nbIterations;i++)
        {
            inv.updateQuality();
            items = inv.getItems();
            for(Item item : items){
                assertTrue(item.getQuality() >= 0);
            }
        }
    }

    @Test
    public void testQualityAgedBrie() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        for (Item item : items)
        {
            if (item.getName().equals("Aged Brie"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++)
                {
                    if(ancienneListeDesItems.get(i).getName() == "Aged Brie")
                    {
                        assertTrue(item.getQuality() >= ancienneListeDesItems.get(i).getQuality());
                    }
                }
            }
        }
    }
    @Test
    public void testSulfurasNotDecreaseInQuality() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        
        for (Item item : items)
        {
            if (item.getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++){
                    if(ancienneListeDesItems.get(i).getName() == "Sulfuras, Hand of Ragnaros"){
                        assertTrue(item.getQuality() == ancienneListeDesItems.get(i).getQuality());
                    }
                }
            }
        }
    }

    @Test
    public void testSulfurasNeverHasToBeSold() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        
        for (Item item : items)
        {
            if (item.getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++){
                    if(ancienneListeDesItems.get(i).getName() == "Sulfuras, Hand of Ragnaros"){
                        assertFalse(item.getSellIn() < ancienneListeDesItems.get(i).getSellIn());
                    }
                }
            }
        }
    }

    @Test
    public void testSulfurasQualityIs80() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        
        for (Item item : items)
        {
            if (item.getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertTrue(item.getQuality() == 80);
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy2When10OrLess() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        
        for (Item item : items)
        {
            if (item.getName().equals("Backstage passes to a TAFKAL80ETC concert"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++){
                    if(ancienneListeDesItems.get(i).getName() == "Backstage passes to a TAFKAL80ETC concert" && item.getSellIn() <= 10){
                         assertEquals(item.getQuality(),ancienneListeDesItems.get(i).getQuality() + 2);
                    }
                }
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy3When5OrLess() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();
        for (Item item : items)
        {
            if (item.getName().equals("Backstage passes to a TAFKAL80ETC concert"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++){
                    if(ancienneListeDesItems.get(i).getName() == "Backstage passes to a TAFKAL80ETC concert" && item.getSellIn() <= 5){
                         assertEquals(item.getQuality(),ancienneListeDesItems.get(i).getQuality() + 3);
                    }
                }
            }
        }
    }
    
    @Test
    public void testBackStagePassesQualityGoesToZero() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();

        for (Item item : items)
        {
            if (item.getName().equals("Backstage passes to a TAFKAL80ETC concert"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++){
                    if(ancienneListeDesItems.get(i).getName() == "Backstage passes to a TAFKAL80ETC concert" && item.getSellIn() >= 0){
                        assertTrue(item.getQuality() == 0);
                    }
                }
            }
        }
    }
    
    @Test
    public void QualityItemIsNotMoreThanFifty() throws Exception
    {
        inv.updateQuality();
        ArrayList<Item> items = inv.getItems();

        for (Item item : items)
        {
            if (! item.getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertTrue(item.getQuality() <= 50);
            }
        }
    }


    
    @Test
    public void updateConjuredCakeQuality(){

        ArrayList<Item> items = inv.getItems();
        inv.updateQuality();
        
        for (Item item : items)
        {
            if (item.getName().equals("Conjured Mana Cake"))
            {
                for(int i = 0; i < ancienneListeDesItems.size(); i++)
                {
                    if(ancienneListeDesItems.get(i).getName() == "Conjured Mana Cake")
                    { 
                        assertEquals(item.getQuality(),ancienneListeDesItems.get(i).getQuality() + 2);
                    }
                }
            }
        }
    }
    
    // As a User, I can check at what time was an item added.
    @Test
    public void checkTimeItemAdded(){
        Item new_item = new Item(1, "Test Item", 10, 11);
        String now = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        inv.addItem(new_item);
        assertEquals(inv.getItem(1).getCreationDate(), now);
        
    }
    
    /** 
     * As a User, I cannot add an Item if ID is already in the inventory
     **/ 
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkIDItemAdded(){
        inv.addItem(new Item(1, "Test item", 10, 11));
        Item new_item = new Item(1, "Test Item", 10, 11);
        inv.addItem(new_item);
        inv.getItem(2);
    }
    
    /**
     * As a User, I can see if the PieChart is correctly updated
     * We have to check the stock 
     */
    @Test
    public void checkPieChartValue(){
        Item item = new Item(1, "Test", 10, 11);
        // Item not in inventory
         assertEquals(inv.getStockByName("Test"), -1);
        
        // Buy an item
        inv.addItem(item);
        assertEquals(inv.getStockByName("Test"), 1);
        
        // Sell an item
        inv.deleteItem(item);
        assertEquals(inv.getStockByName("Test"), 0);
    }
    
    /**
     * As a User, I can add an Item from a Json File
     */    
    @Test
    public void checkAddItemFromJson(){
        String json = "[ { \"serialId\": 1, \"name\": \"Conjured Mana Cake\", \"sellIn\": 3, \"quality\": 6 } ]";
        inv.loadData(json);
        assertEquals(inv.getItem(1).getName(), "Conjured Mana Cake");
    }
    
    /**
     * As a User, I can add  Items from a Json File
     */  
    @Test
    public void checkAddItemsFromJson(){
        String json = ("[{\"serialId\": 2,\"name\": \"+5 Dexterity Vest\",\"sellIn\": 20,\"quality\": 30},{\"serialId\": 3,\"name\": \"Aged Brie\",\"sellIn\": 20,\"quality\": 10}]");
        inv.loadData(json);
        assertEquals(inv.getItem(1).getName(), "+5 Dexterity Vest");
        assertEquals(inv.getItem(2).getName(), "Aged Brie");
    }
    
    /**
     * As a User, I can add  Items from a Json File on a non-empty inventory
     */ 
    @Test
    public void checkDoubleAddItemsFromJson(){
        String json1 = ("[{\"serialId\": 2,\"name\": \"+5 Dexterity Vest\",\"sellIn\": 20,\"quality\": 30},{\"serialId\": 3,\"name\": \"Aged Brie\",\"sellIn\": 20,\"quality\": 10}]");
        inv.loadData(json1);
        String json = "[ { \"serialId\": 1, \"name\": \"Conjured Mana Cake\", \"sellIn\": 3, \"quality\": 6 } ]";
        inv.loadData(json);
        assertEquals(inv.getItem(1).getName(), "+5 Dexterity Vest");
        assertEquals(inv.getItem(2).getName(), "Aged Brie");
        assertEquals(inv.getItem(3).getName(), "Conjured Mana Cake");
    }
    
    /**
     * As a User, I can check the number of Items of the Inventory
     */ 
    @Test
    public void checkSize(){
        String json1 = ("[{\"serialId\": 2,\"name\": \"+5 Dexterity Vest\",\"sellIn\": 20,\"quality\": 30},{\"serialId\": 3,\"name\": \"Aged Brie\",\"sellIn\": 20,\"quality\": 10}]");
        inv.loadData(json1);
        String json = "[ { \"serialId\": 1, \"name\": \"Conjured Mana Cake\", \"sellIn\": 3, \"quality\": 6 } ]";
        inv.loadData(json);
        assertEquals(inv.getItems().size(),3);
    }
    
    /**
     * As a User, I can buy an Item and check BuyList
     */ 
    @Test
    public void checkBuy(){
        Item item = new Item(1, "Test", 10, 11);
        inv.buyItem(item);
        assertEquals(inv.getBoughtItemByName("Test"), 1);
    }
    
    /**
     * As a User, I sell an Item and check SellList
     */ 
    @Test
    public void checkSell(){
        Item item = new Item(1, "Test", 10, 11);
        inv.buyItem(item);
        inv.sellItem(item);
        assertEquals(inv.getSellItemByName("Test"), -1);
    }
    
    
}

