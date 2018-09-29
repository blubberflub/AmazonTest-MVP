package com.blub.amazontest.contacts.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blub.amazontest.contacts.BaseFragment;
import com.blub.amazontest.contacts.ContactsViewModel;
import com.blub.amazontest.contacts.model.ContactDto;
import com.blub.amazontest.contacts.model.Header;
import com.blub.amazontest.contacts.model.ViewItem;
import com.blub.amazontest.MainActivity;
import com.blub.amazontest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListFragment extends BaseFragment implements
        ContactsAdapter.ContactViewHolder.OnItemClicked {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.contact_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private ArrayList<ViewItem> mContactViewItems;
    private ListFragmentCallBack mActivityCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        ButterKnife.bind(this, view);

        mContactViewItems = new ArrayList<>();
        mAdapter = new ContactsAdapter(getContext(), mContactViewItems, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new ContactListDivider(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof ListFragmentCallBack) {
            mActivityCallback = (ListFragmentCallBack) getActivity();
        }
    }

    public void refreshList(SparseArray<List<ContactDto>> contactsSparseArray) {
        mProgressBar.setVisibility(View.GONE);
        mContactViewItems.clear();

        mContactViewItems.add(new Header(ViewItem.FAVORITE_CONTACTS_HEADER));
        mContactViewItems.addAll(contactsSparseArray.get(ContactsViewModel.FAVORITES_LIST));
        mContactViewItems.add(new Header(ViewItem.OTHER_CONTACTS_HEADER));
        mContactViewItems.addAll(contactsSparseArray.get(ContactsViewModel.OTHER_CONTACTS_LIST));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        ContactDto contactItem = (ContactDto) mContactViewItems.get(position);
        mActivityCallback.clickedContact(contactItem);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
    }

    public interface ListFragmentCallBack {

        void clickedContact(ContactDto position);
    }
}
