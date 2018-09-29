package com.blub.amazontest.contacts.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.blub.amazontest.contacts.model.ContactDto;

import java.util.List;

@Dao
public interface ContactsDao {

    @Insert
    void insertContact(ContactDto contact);

    @Insert
    void insertListOfContacts(List<ContactDto> contactsList);

    @Query("SELECT * FROM contacts")
    LiveData<List<ContactDto>> getAllContacts();

    @Query("SELECT COUNT(*) FROM contacts")
    int getContactsCount();

    @Query("UPDATE contacts SET isfavorite = :isFav WHERE id = :id")
    void toggleFavorite(int id, boolean isFav);
}
