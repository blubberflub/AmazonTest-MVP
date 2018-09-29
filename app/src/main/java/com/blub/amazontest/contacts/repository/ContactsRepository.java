package com.blub.amazontest.contacts.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.blub.amazontest.contacts.model.ContactDto;
import com.blub.amazontest.retro.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsRepository implements ContactsDataSource {

    private final ContactsDao mContactsDao;

    public ContactsRepository(ContactsDao mContactsDao) {
        this.mContactsDao = mContactsDao;
    }

    @Override
    public LiveData<List<ContactDto>> fetchContactsList() {
        if (mContactsDao.getContactsCount() != 0) {
            return mContactsDao.getAllContacts();
        }

        RetrofitClient.getCallService().getContacts().enqueue(new Callback<List<ContactDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<ContactDto>> call,
                                   @NonNull Response<List<ContactDto>> response) {
                mContactsDao.insertListOfContacts(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ContactDto>> call, @NonNull Throwable t) {
            }
        });

        return mContactsDao.getAllContacts();
    }

    @Override
    public void toggleFavorite(ContactDto contactDto) {
        mContactsDao.toggleFavorite(Integer.parseInt(contactDto.getId()), !contactDto.isFavorite());
    }
}
