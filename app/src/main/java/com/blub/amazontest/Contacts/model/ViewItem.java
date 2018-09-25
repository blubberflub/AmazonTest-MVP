package com.blub.amazontest.Contacts.model;

public class ViewItem {

    public static final int FAVORITE_CONTACTS_HEADER = 0;
    public static final int OTHER_CONTACTS_HEADER = 1;

    public static final int CONTACT = 2;

    private int type;

    ViewItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
