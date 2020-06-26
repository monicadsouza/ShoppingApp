package com.bignerdranch.android.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.bignerdranch.android.shopping.database.ItemBaseHelper;
import com.bignerdranch.android.shopping.database.ItemCursorWrapper;
import com.bignerdranch.android.shopping.database.ItemsDbSchema.ItemTable;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class ItemsDB extends Observable {
    private Context mContext;
    private static ItemsDB sItemsDB;
    private static SQLiteDatabase mDatabase;

    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            mDatabase = new ItemBaseHelper(context.getApplicationContext())
                    .getWritableDatabase();
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    } //End of the singleton inner private class

    private ItemsDB(Context context) {
        mContext = context.getApplicationContext();
    } // Constructor

    public ArrayList<Item> getItemsDB() {
        ArrayList<Item> items = new ArrayList<Item>();
        ItemCursorWrapper cursor = queryItems(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(cursor.getItem());
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.Cols.WHAT, item.getWhat());
        values.put(ItemTable.Cols.WHERE, item.getWhere());
        return values;
    }

    public void addItem(String what, String where) {
        Item newItem = new Item(what, where);
        ContentValues values = getContentValues(newItem);
        mDatabase.insert(ItemTable.NAME, null, values);
        this.setChanged(); notifyObservers();
    }

    static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemTable.NAME,
                null, // Columns - null selects all columns
                whereClause, whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    public String listItems() {
        String r = "";
        if (getItemsDB().size() == 0) {
            return r;
        } else {
            for (int i = 0; i < getItemsDB().size(); i++) {
                r = r + "\n Buy " + getItemsDB().get(i).toString();
            }
        }
        return r;
    }

    public void fillItemsDB(Item item) {
        addItem(item.getWhat(), item.getWhere());
        this.setChanged(); notifyObservers();
    }

    public void deleteItemsDB() {
        for (Item item: getItemsDB()) {
            String currentWhat = item.getWhat();
            mDatabase.delete(ItemTable.NAME, "what = ?", new String[] {currentWhat});
        }
        this.setChanged(); notifyObservers();
    }

    public void deleteOneItem(String what) {
        mDatabase.delete(ItemTable.NAME, "what = ?", new String[] {what});
        this.setChanged(); notifyObservers();
    }

    public int DBSize() {
        return getItemsDB().size();
    }

    public File getPhotoFile(Item item) {
        File filesDir = sItemsDB.mContext.getFilesDir();
        return new File(filesDir, item.getPhotoFilename());
    }

}