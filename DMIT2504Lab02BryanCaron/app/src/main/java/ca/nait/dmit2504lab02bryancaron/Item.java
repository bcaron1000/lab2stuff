package ca.nait.dmit2504lab02bryancaron;

public class Item {
    private int itemID;
    private String itemName;

    public Item(){

    }
    public Item(int itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
