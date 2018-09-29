package com.blub.amazontest.contacts;

import android.support.v4.app.Fragment;

import com.blub.amazontest.MainActivity;

public class BaseFragment extends Fragment {

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.initBackButton();
            mainActivity.setPageTitle(this.getTag());
        } else {
            if (this.getArguments() != null) {
                this.getArguments().clear();
            }
        }
    }
}
