package designpattern.esilv.s7.project;

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
        items = new Item[]{};

    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

//    public void updateQuality() {
//        for(Item item : items) {
//            UpdateStrategyFactory fact = new UpdateStrategyFactory(item);
//            fact.strat.update(item);
//        }
//    }

//    public static void main(String[] args) {
//        Inventory inventory = new Inventory();
//        for (int i = 0; i < 10; i++) {
//            inventory.updateQuality();
//            inventory.printInventory();
//        }
//    }

    Item[] getItems() {
        return items;
    }

    void setItems(Item[] itemArray) {
        items = itemArray;
    }
}
