package com.blub.amazontest.Contacts;

import android.util.Log;

import com.blub.amazontest.Contacts.dao.ContactsRepository;
import com.blub.amazontest.Contacts.model.ContactDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsPresenter implements ContactsContract.UserActionsListener {

    private final ContactsContract.View mContactsView;
    private final ContactsRepository mContactsRepository;

    public ContactsPresenter(ContactsContract.View contactsView, ContactsRepository contactsRepository) {
        this.mContactsView = contactsView;
        this.mContactsRepository = contactsRepository;
    }

    @Override
    public void openedContactsList() {
        mContactsView.toggleProgressBar(true);
        mContactsRepository.fetchContactsList(callback);
    }

    @Override
    public void selectedContact(ContactDto contact) {
        mContactsView.showContactDetails(contact);
    }

    @Override
    public void toggleFavorite(ContactDto contactDto) {
        mContactsRepository.toggleFavorite(contactDto);
    }

    private ContactsRepository.Callback callback = new ContactsRepository.Callback() {
        @Override
        public void onSucces(List<ContactDto> contactsList) {
            List<ContactDto> favoritesList = separateFavorites(contactsList);

            sortByName(favoritesList);
            sortByName(contactsList);

            mContactsView.toggleProgressBar(false);
            mContactsView.showContactsList(favoritesList, contactsList);
        }

        @Override
        public void onError(String message) {
            Log.e("ERRORLOG", message);
        }
    };

    private List<ContactDto> separateFavorites(List<ContactDto> contactsList) {
        List<ContactDto> favoritesList = new ArrayList<>();
        List<ContactDto> otherContactsList = new ArrayList<>();

        for (ContactDto contact : contactsList) {
            if (contact.isFavorite()) {
                favoritesList.add(contact);
            } else {
                otherContactsList.add(contact);
            }
        }

        contactsList.clear();
        contactsList.addAll(otherContactsList);

        return favoritesList;
    }

    private void sortByName(List<ContactDto> contactsList) {
        Collections.sort(contactsList, (ContactDto c1, ContactDto c2) ->
                c1.getName().compareToIgnoreCase(c2.getName()));
    }
}
