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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * TODO - updateSellin
 *
 * @author qunnamed
 */
public class Inventory {

//    private Item[] items;
    private ArrayList<Item> items;
    private HashMap<String, Integer> stock;

    public Inventory(Item[] items) {
        super();
        this.items = new ArrayList<Item>();
    }

    public Inventory() {
        super();
        items = new ArrayList<Item>();
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
    public ArrayList<Item> getItems() {
        return items;
    }

    public HashMap<String, Integer> getStock() {
        return stock;
    }

    public void setItems(ArrayList<Item> itemArray) {
        items = itemArray;
    }

    public void loadData(File file, String itemJson) {
        try {
            itemJson = new String(Files.readAllBytes(file.toPath()));
            Gson gson = new Gson();
            Item[] itemArray = gson.fromJson(itemJson, Item[].class);
            setCreationDate(itemArray);
            
            addItems(itemArray);
            
//            this.setItems(itemArray);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        this.updateStock();
    }

    public void updateStock() {
        for (Item item : items) {
            int count = stock.containsKey(item.getName()) ? stock.get(item.getName()) : 0;
            String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
            stock.put(name, count + 1);
        }
        
        
    }

    private void setCreationDate(Item[] itemArray) {
        for(Item item : itemArray){
            item.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        }
    }

    private void addItems(Item[] itemArray) {
        for(Item newItem : itemArray){
            boolean ok = true;
            for(Item invItem : items){
                if(newItem.getSerialId() == invItem.getSerialId()){
                    ok = false;
                    continue;
                }
            }
            if(ok) addItem(newItem);
        }
    }

    private void addItem(Item item) {
        items.add(item);
        String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
        int count = stock.containsKey(name) ? stock.get(name) : 0;
        stock.put(name, count + 1);
        
    }
    
    private void deleteItem(Item item){
        items.remove(item);
    }
}
