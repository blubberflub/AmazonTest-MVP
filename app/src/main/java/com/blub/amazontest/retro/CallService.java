package com.blub.amazontest.retro;

import com.blub.amazontest.contacts.model.ContactDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CallService {

    @GET("/technical-challenge/v3/contacts.json")
    Call<List<ContactDto>> getContacts();
}
