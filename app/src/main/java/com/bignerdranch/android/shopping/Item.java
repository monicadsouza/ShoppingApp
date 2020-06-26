package com.bignerdranch.android.shopping;

public class Item {
    private String mWhat;
    private String mWhere;

    public Item(String what, String where) {
        mWhat = what;
        mWhere = where;
    }

    @Override
    public String toString() { return oneLine(""," in: "); }

    public String getWhat() { return mWhat; }

    public void setWhat(String what) { mWhat = what; }

    public String getWhere() { return mWhere; }

    public void setWhere(String where) { mWhere = where; }

    public String oneLine(String pre, String post) {
        return pre+mWhat + post + mWhere;
    }

    public String getPhotoFilename() {
        return "IMG_" + getWhat() + getWhere() + ".jpg";
    }
}
