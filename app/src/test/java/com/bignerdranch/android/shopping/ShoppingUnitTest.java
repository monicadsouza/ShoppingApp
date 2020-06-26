package com.bignerdranch.android.shopping;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShoppingUnitTest {
    ItemsDB mItemsDB;
    Item mItem;

    /*@Before
    public void createDB() {
        //mItemsDB = new ItemsDB(null);
        mItemsDB.fillItemsDB(new Item("banana", "Netto"));
        mItemsDB.fillItemsDB(new Item("coffee", "Irma"));
        mItemsDB.fillItemsDB(new Item("apple", "Fakta"));
        mItemsDB.fillItemsDB(new Item("water", "Netto"));
    }

    @Test
    public void testItem() {
        //Testing Item
        mItem = new Item("rice", "Fakta");
        assertEquals(mItem.getWhat(), "rice");
        assertEquals(mItem.getWhere(), "Fakta");
        mItem.setWhat("rice and beans");
        mItem.setWhere("Fakta or Netto");
        assertEquals(mItem.getWhat(), "rice and beans");
        assertEquals(mItem.getWhere(), "Fakta or Netto");
        assertEquals(mItem.toString(), "rice and beans in: Fakta or Netto");
    }
    @Test
    public void testEmptyDB() {
        //Empty database
        //mItemsDB = new ItemsDB(null);
        assertEquals(mItemsDB.DBSize(), 0);
    }
    @Test
    public void testSingleItem() {
        //Database with a single Item
        //mItemsDB = new ItemsDB(null);
        mItemsDB.fillItemsDB(new Item("pasta", "Netto"));
        assertEquals(mItemsDB.DBSize(), 1);
        assertEquals(mItemsDB.getItemsDB().get(0).getWhat(), "pasta");
        assertEquals(mItemsDB.getItemsDB().get(0).getWhere(), "Netto");
        assertEquals(mItemsDB.listItems(), "\n Buy pasta in: Netto");
    }
    @Test
    public void testMultipleItems() {
        //Database with more than one Item
        //mItemsDB = new ItemsDB(null);
        mItemsDB.fillItemsDB(new Item("banana", "Netto"));
        mItemsDB.fillItemsDB(new Item("coffee", "Irma"));
        mItemsDB.fillItemsDB(new Item("apple", "Fakta"));
        mItemsDB.fillItemsDB(new Item("water", "Netto"));
        assertEquals(mItemsDB.DBSize(), 4);
        assertEquals(mItemsDB.getItemsDB().get(2).getWhat(), "apple");
        assertEquals(mItemsDB.getItemsDB().get(3).getWhere(), "Netto");
    }
    @Test
    public void deleteMiddle() {
        //Testing delete one Item in the middle
        //mItemsDB.deleteOneItem(2);
        assertEquals(mItemsDB.getItemsDB().get(2).getWhat(), "water");
        assertEquals(mItemsDB.getItemsDB().get(2).getWhere(), "Netto");
    }
    @Test
    public void deleteFirst() {
        //Testing delete first Item
        //mItemsDB.deleteOneItem(0);
        assertEquals(mItemsDB.getItemsDB().get(0).getWhat(), "coffee");
    }
    @Test
    public void deleteLast() {
        //Testing delete last Item
        //mItemsDB.deleteOneItem(1);
        assertEquals(mItemsDB.getItemsDB().get(0).getWhere(), "Netto");
    }
    @Test
    public void deleteAllItems() {
        //Testing delete all Items
        mItemsDB.deleteItemsDB();
        assertEquals(mItemsDB.DBSize(), 0);
    }*/
}