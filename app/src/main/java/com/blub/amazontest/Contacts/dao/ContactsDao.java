package com.blub.amazontest.Contacts.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.blub.amazontest.Contacts.model.ContactDto;

import java.util.List;

@Dao
public interface ContactsDao {

    @Insert
    void insertContact(ContactDto contact);

    @Insert
    void insertListOfContacts(List<ContactDto> contactsList);

    @Query("SELECT * FROM contacts")
    List<ContactDto> getAllContacts();

    @Query("SELECT COUNT(*) FROM contacts")
    int getContactsCount();

    @Query("UPDATE contacts SET isfavorite = :isFav WHERE id = :id")
    void toggleFavorite(int id, boolean isFav);
}
