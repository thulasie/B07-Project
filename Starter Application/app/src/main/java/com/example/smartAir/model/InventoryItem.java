package com.example.smartAir.model;

/*import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

//@Entity(tableName = "inventory_items")
public class InventoryItem {
  //  @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String purchaseDate;
    public int amountLeft;
    public String expiryDate;
    public boolean parentMarked;
}
