package designpattern.esilv.s7.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.sun.javafx.collections.MappingChange.Map;
import designpattern.esilv.s7.controller.FXMLController;

/**
 * TODO - updateSellin
 *
 * @author qunnamed
 */
public class Inventory {

    private Item[] items;
    private HashMap<String, Integer> stock;

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }

    public Inventory() {
        super();
        items = new Item[]{};
        stock = new HashMap<String, Integer>();
    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

    public void updateQuality() {
        for (Item item : items) {
            UpdateStrategyFactory fact = new UpdateStrategyFactory(item);
            fact.strat.update(item);
        }
    }

//    public static void main(String[] args) {
//        Inventory inventory = new Inventory();
//        for (int i = 0; i < 10; i++) {
//            inventory.updateQuality();
//            inventory.printInventory();
//        }
//    }
    public Item[] getItems() {
        return items;
    }

    public HashMap<String, Integer> getStock() {
        return stock;
    }

    public void setItems(Item[] itemArray) {
        items = itemArray;
    }

    public void loadData(File file, String itemJson) {
        try {
            itemJson = new String(Files.readAllBytes(file.toPath()));

            Gson gson = new Gson();
            Item[] itemArray = gson.fromJson(itemJson, Item[].class);
            this.setItems(itemArray);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.updateStock();
    }

    public void updateStock() {
        for (Item item : items) {
            int count = stock.containsKey(item.getName()) ? stock.get(item.getName()) : 0;
            String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
            stock.put(name, count + 1);
        }

    }
}
