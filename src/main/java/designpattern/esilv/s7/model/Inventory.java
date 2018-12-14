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

    public void addItems(Item[] itemArray) {
        for(Item newItem : itemArray){
            addItem(newItem);
        }
    }

    public void addItem(Item item) {
        if(itemIDNotExist(item)){
            items.add(item);
            String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
            int count = stock.containsKey(name) ? stock.get(name) : 0;
            stock.put(name, count + 1);
        }       
    }
    
    private boolean itemIDNotExist(Item item) {
        boolean ok = true;
            for(Item invItem : items){
                if(item.getSerialId() == invItem.getSerialId()){
                    ok = false;
                    break;
                }
            }
         return ok;
    }
    
    public void deleteItem(Item item){
        items.remove(item);
        String name = item.getName().contains("Backstage") ? "Backstage TAFKAL80ETC" : item.getName();
        stock.put(name, stock.get(name) - 1);
    }


    public boolean notContains(int newItemID) {
        boolean notIn = true;
        for(Item item : items){
            if(item.getSerialId() == newItemID) {
                notIn = false;
                break;
            }
        }
        return notIn;
    }

    public Item getItem(int i) {
       return items.get(i-1);
    }
    
    public int getStockByName(String name){
        String fname = name.contains("Backstage") ? "Backstage TAFKAL80ETC" : name;
        return stock.containsKey(fname) ? stock.get(fname) : -1;
    }
}
