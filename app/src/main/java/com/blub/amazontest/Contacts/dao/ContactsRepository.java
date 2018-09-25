package com.blub.amazontest.Contacts.dao;

import com.blub.amazontest.Contacts.model.ContactDto;

import java.util.List;

public interface ContactsRepository {

    void fetchContactsList(Callback callback);

    void toggleFavorite(ContactDto contactDto);

    interface Callback {

        void onSucces(List<ContactDto> contactsList);

        void onError(String message);
    }
}
