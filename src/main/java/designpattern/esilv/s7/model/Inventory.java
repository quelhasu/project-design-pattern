package designpattern.esilv.s7.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.collections.MappingChange.Map;
import designpattern.esilv.s7.controller.FXMLController;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.fxml.FXML;

/**
 *
 * @author qunnamed
 */
public class Inventory {

    private ArrayList<Item> items;
    private ArrayList<Item> boughtItems;
    private ArrayList<Item> soldItems;

    private HashMap<String, Integer> stock;
    private HashMap<String, Integer> boughtItemsValue;
    private HashMap<String, Integer> soldItemsValue;

    public Inventory(Item[] items) {
        super();
        this.items = new ArrayList<Item>();
    }

    public Inventory() {
        super();
        items = new ArrayList<Item>();
        soldItems = new ArrayList<Item>();
        boughtItems = new ArrayList<Item>();
        stock = new HashMap<String, Integer>();
        boughtItemsValue = new HashMap<String, Integer>();
        soldItemsValue = new HashMap<String, Integer>();
    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

    /**
     * Update de quality of all the items
     */
    public void updateQuality() {
        for (Item item : items) {
            UpdateStrategyFactory fact = new UpdateStrategyFactory(item);
            fact.strat.update(item);
        }
    }

    /**
     * Get the items of all the inventory
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Get the stock with name and value of each type of item
     */
    public HashMap<String, Integer> getStock() {
        return stock;
    }

    /**
     * Get the bought items history
     */
    public HashMap<String, Integer> getBoughtItemsValue() {
        return boughtItemsValue;
    }

    /**
     * Get the sold items history
     */
    public HashMap<String, Integer> getSoldItemsValue() {
        return soldItemsValue;
    }
    
    /**
     * Get the bought items history
     */
    public ArrayList<Item> getBoughtItems() {
        return boughtItems;
    }

    /**
     * Get the sold items history
     */
    public ArrayList<Item> getSoldItems() {
        return soldItems;
    }

    /**
     * Set the inventory items from arrayList
     */
    public void setItems(ArrayList<Item> itemArray) {
        items = itemArray;
    }

    /**
     * Load the data into the inventory from Json file
     */
    public void loadData(String itemJson) {
        Gson gson = new Gson();
        Item[] itemArray = gson.fromJson(itemJson, Item[].class);
//        setCreationDate(itemArray);
        addItems(itemArray);
    }

    /**
     * Update the stock values according to the inventory
     */
    public void updateStock() {
        for (Item item : items) {
            int count = stock.containsKey(item.getName()) ? stock.get(item.getName()) : 0;
            String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
            stock.put(name, count + 1);
        }

    }

    /**
     * Set the actual date time of all inventory
     */
    private void setCreationDate(Item[] itemArray) {
        for (Item item : itemArray) {
            item.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        }
    }

    /**
     * Add array of items in inventory
     */
    public void addItems(Item[] itemArray) {
        for (Item newItem : itemArray) {
            addItem(newItem);
            int count = boughtItemsValue.containsKey(newItem.getCreationDate()) ? boughtItemsValue.get(newItem.getCreationDate()) : 0;
            boughtItemsValue.put(newItem.getCreationDate(), count + 1);
        }
    }

    /**
     * Add an item in inventory after verifying if it exists
     */
    public void addItem(Item item) {
        if (itemIDNotExist(item)) {
            boughtItems.add(item);
            items.add(item);
            String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
            int count = stock.containsKey(name) ? stock.get(name) : 0;
            stock.put(name, count + 1);
        }
    }

    /**
     * Check if item exists
     */
    public boolean itemIDNotExist(Item item) {
        boolean ok = true;
        for (Item invItem : items) {
            if (item.getSerialId() == invItem.getSerialId()) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    public boolean notContains(int newItemID) {
        boolean notIn = true;
        for (Item item : items) {
            if (item.getSerialId() == newItemID) {
                notIn = false;
                break;
            }
        }
        return notIn;
    }

    /**
     * Get the bought items history
     */
    public void deleteItem(Item item) {
        items.remove(item);
        soldItems.add(item);
        String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
        stock.put(name, stock.get(name) - 1);
    }

    /**
     * Return item according to the place it belong
     */
    public Item getItem(int i) {
        return items.get(i - 1);
    }

    /**
     * Get stock value by name
     */
    public int getStockByName(String name) {
        String fname = name.contains("Backstage") ? "Backstage TAFKAL80ETC" : name;
        return stock.containsKey(fname) ? stock.get(fname) : -1;
    }

    /**
     * Update the bought items history
     */
    public void buyItem(Item newItem) {
        if (itemIDNotExist(newItem)) {
            addItem(newItem);
            String now = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            int count = boughtItemsValue.containsKey(now) ? boughtItemsValue.get(now) : 0;
            boughtItemsValue.put(now, count + 1);
        }
    }

    /**
     * Update the sold items history
     */
    public void sellItem(Item newItem) {
        deleteItem(newItem);
        String now = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        int count = soldItemsValue.containsKey(now) ? soldItemsValue.get(now) : 0;
        soldItemsValue.put(now, count + 1);
    }

    /**
     * return the bought items history
     */
    public int getBoughtItemByName(String name) {
        return stock.containsKey(name) ? stock.get(name) : -1;
    }

    /**
     * return the sold items history
     */
    public int getSellItemByName(String name) {
        return soldItemsValue.containsKey(name) ? stock.get(name) : -1;
    }
    
    

}
