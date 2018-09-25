package com.blub.amazontest.Contacts.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.blub.amazontest.Contacts.model.ContactDto;

@Database(entities = ContactDto.class, version = 1, exportSchema = false)
public abstract class ContactsDatabase extends RoomDatabase {

    private static ContactsDatabase INSTANCE;

    public abstract ContactsDao contactsRoomDao();

    public static ContactsDatabase getContactsDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, ContactsDatabase.class, "contacts-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
}
