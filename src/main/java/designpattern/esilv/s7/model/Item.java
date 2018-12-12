package designpattern.esilv.s7.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Item {
    private static int counter = 0;
    private int serialId;
    private String name;
    private int sellIn;
    private int quality;
    private String creationDate;

    public Item(String name, int sellIn, int quality) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.creationDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        this.serialId = ++counter;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
        counter++;
    }
    
    public void setSerialId(){
        this.serialId = ++counter;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    boolean isExpired() {
        return this.sellIn < 0;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", sellIn=" + sellIn + ", quality=" + quality + ", creationDate=" + creationDate + '}';
    }

}
