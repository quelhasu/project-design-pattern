package edu.insightr.gildedrose;

/**
 * TODO - updateSellin
 *
 * @author qunnamed
 */
public class Inventory {

    private Item[] items;

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }
    
    

    public Inventory() {
        super();
        items = new Item[]{
            new OtherItem("+5 Dexterity Vest", 10, 20),
            new AgedBrie("Aged Brie", 2, 0),
            new OtherItem("Elixir of the Mongoose", 5, 7),
            new Sulfuras("Sulfuras, Hand of Ragnaros", 0, 80),
            new BackstagePasses("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new ConjuredItem("Conjured Mana Cake", 3, 6)
        };

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
        UpdateVisitor visitor = new UpdateVisitor();
        
        for(Item item : items) 
        { 
            item.accept(visitor); 
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 10; i++) {
            inventory.updateQuality();
            inventory.printInventory();
        }
    }

    Item[] getItems() {
        return items;
    }
}
