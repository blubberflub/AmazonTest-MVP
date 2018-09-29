package com.blub.amazontest.contacts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.blub.amazontest.contacts.model.ContactDto;
import com.blub.amazontest.contacts.repository.ContactsDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    public final static int FAVORITES_LIST = 0;
    public final static int OTHER_CONTACTS_LIST = 1;

    private final ContactsDataSource mContactsRepository;
    private final LiveData<SparseArray<List<ContactDto>>> mContactSparseArrayObservable;
    private final LiveData<List<ContactDto>> mContactsListObservable;

    ContactsViewModel(Application application, ContactsDataSource repository) {
        super(application);

        mContactsRepository = repository;

        mContactsListObservable = mContactsRepository.fetchContactsList();
        mContactSparseArrayObservable = Transformations.map(mContactsListObservable, this::listToContactSparseMap);
    }

    private SparseArray<List<ContactDto>> listToContactSparseMap(List<ContactDto> input) {
        SparseArray<List<ContactDto>> mContactsSparseArray = new SparseArray<>();
        List<ContactDto> favoritesList = separateFavorites(input);
        sortByName(favoritesList);
        sortByName(input);

        mContactsSparseArray.put(FAVORITES_LIST, favoritesList);
        mContactsSparseArray.put(OTHER_CONTACTS_LIST, input);

        return mContactsSparseArray;
    }

    public LiveData<List<ContactDto>> getContactsListObservable() {
        return mContactsListObservable;
    }

    public LiveData<SparseArray<List<ContactDto>>> getContactSparseArrayObservable() {
        return mContactSparseArrayObservable;
    }

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

    //user actions
    public void toggledFavorite(ContactDto contact) {
        mContactsRepository.toggleFavorite(contact);
    }
}
