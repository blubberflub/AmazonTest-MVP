package com.blub.amazontest.Contacts.dao;

import com.blub.amazontest.Contacts.model.ContactDto;
import com.blub.amazontest.retro.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ContactsRepositoryImpl implements ContactsRepository {

    private final ContactsDao mContactsDao;

    public ContactsRepositoryImpl(ContactsDao mContactsDao) {
        this.mContactsDao = mContactsDao;
    }

    @Override
    public void fetchContactsList(final Callback callback) {
        if (mContactsDao.getContactsCount() != 0) {
            callback.onSucces(mContactsDao.getAllContacts());
            return;
        }

        RetrofitClient.getCallService().getContacts().enqueue(new retrofit2.Callback<List<ContactDto>>() {
            @Override
            public void onResponse(Call<List<ContactDto>> call, Response<List<ContactDto>> response) {
                mContactsDao.insertListOfContacts(response.body());
                callback.onSucces(mContactsDao.getAllContacts());
            }

            @Override
            public void onFailure(Call<List<ContactDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void toggleFavorite(ContactDto contactDto) {
        mContactsDao.toggleFavorite(Integer.parseInt(contactDto.getId()), !contactDto.isFavorite());
    }
}
