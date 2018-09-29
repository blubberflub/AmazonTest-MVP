package com.blub.amazontest.contacts;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.blub.amazontest.contacts.repository.ContactsDataSource;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private ContactsDataSource mRepository;

    public ViewModelFactory(Application application, ContactsDataSource repository) {
        mApplication = application;
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ContactsViewModel(mApplication, mRepository);
    }
}
