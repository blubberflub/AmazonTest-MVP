package com.blub.amazontest.Contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.blub.amazontest.MainActivity;

public class BaseFragment extends Fragment {

    private MainActivity mainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        mainActivity.initBackButton();
    }

    public void setTitle(String title) {
        mainActivity.setPageTitle(title);
    }
}
