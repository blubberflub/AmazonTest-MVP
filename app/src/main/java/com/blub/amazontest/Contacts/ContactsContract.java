package com.blub.amazontest.Contacts;

import com.blub.amazontest.Contacts.model.ContactDto;

import java.util.List;

public interface ContactsContract {

    interface View {
        void toggleProgressBar(boolean visible);

        void showContactsList(List<ContactDto> favoritesList, List<ContactDto> contactsList);

        void showContactDetails(ContactDto contactDto);
    }

    interface UserActionsListener {

        void openedContactsList();

        void selectedContact(ContactDto contact);

        void toggleFavorite(ContactDto contactDto);
    }
}
