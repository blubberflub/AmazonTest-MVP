package com.blub.amazontest.contacts.repository;

import android.arch.lifecycle.LiveData;

import com.blub.amazontest.contacts.model.ContactDto;

import java.util.List;

public interface ContactsDataSource {

    LiveData<List<ContactDto>> fetchContactsList();

    void toggleFavorite(ContactDto contactDto);
}
