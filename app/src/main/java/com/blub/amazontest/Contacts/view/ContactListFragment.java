package com.blub.amazontest.Contacts.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blub.amazontest.Contacts.BaseFragment;
import com.blub.amazontest.Contacts.model.ContactDto;
import com.blub.amazontest.Contacts.model.Header;
import com.blub.amazontest.Contacts.model.ViewItem;
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

    public static ContactListFragment newInstance(Parcelable in) {

        Bundle args = new Bundle();
        args.putParcelable(MainActivity.BUNDLE_KEY, in);

        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
            setTitle("Contacts");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter.getItemCount() == 0) {
            mActivityCallback.openedListView();
        }
    }

    public void toggleProgressBar(int visible) {
        mProgressBar.setVisibility(visible);
    }

    public void showContactsList(List<ContactDto> favoritesList, List<ContactDto> contactsList) {
        //add favorites header
        mContactViewItems.add(new Header(ViewItem.FAVORITE_CONTACTS_HEADER));
        mContactViewItems.addAll(favoritesList);
        mContactViewItems.add(new Header(ViewItem.OTHER_CONTACTS_HEADER));
        mContactViewItems.addAll(contactsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        ContactDto contactItem = (ContactDto) mContactViewItems.get(position);
        mActivityCallback.clickedContact(contactItem);
    }

    public interface ListFragmentCallBack {

        void openedListView();

        void clickedContact(ContactDto position);

    }
}
