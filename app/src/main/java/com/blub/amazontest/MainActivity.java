package com.blub.amazontest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.blub.amazontest.contacts.ContactsViewModel;
import com.blub.amazontest.contacts.ViewModelFactory;
import com.blub.amazontest.contacts.model.ContactDto;
import com.blub.amazontest.contacts.repository.ContactsDao;
import com.blub.amazontest.contacts.repository.ContactsDatabase;
import com.blub.amazontest.contacts.repository.ContactsRepository;
import com.blub.amazontest.contacts.view.ContactDetailsFragment;
import com.blub.amazontest.contacts.view.ContactListFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main controller for fragments and toolbar
 * <p>
 * UI for ViewModel
 */
public class MainActivity extends AppCompatActivity implements ContactListFragment.ListFragmentCallBack,
        ContactDetailsFragment.DetailsFragmentCallback {

    @BindView(R.id.actionbar)
    Toolbar mActionBar;
    @BindView(R.id.back_button)
    TextView mBackButton;
    @BindView(R.id.pageTitle)
    TextView mPageTitle;

    private FragmentManager mFragmentManager;

    //fragments
    private ContactListFragment mContactListFragment;
    private ContactDetailsFragment mContactDetailsFragment;

    private LiveData<SparseArray<List<ContactDto>>> mContactMapObservable;
    private ContactsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mActionBar);
        getSupportActionBar().setTitle(null);

        mBackButton.setOnClickListener(v -> onBackPressed());

        ContactsDao dao = ContactsDatabase.getContactsDatabase(this).contactsRoomDao();

        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(),
                new ContactsRepository(dao))).get(ContactsViewModel.class);
        mContactMapObservable = mViewModel.getContactSparseArrayObservable();

        mFragmentManager = getSupportFragmentManager();
        initializeFragments();

        preloadImages();
        setFragment(mContactListFragment, false);
    }

    private void preloadImages() {
        RequestOptions placeHolder = new RequestOptions()
                .placeholder(R.drawable.ic_person);

        mViewModel.getContactsListObservable().observe(this, contactDtos -> {
            for (ContactDto contact : contactDtos) {
                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(placeHolder)
                        .load(contact.getLargeImageURL())
                        .preload();
            }
        });
    }

    private void initializeFragments() {
        mContactListFragment = new ContactListFragment();
        mContactDetailsFragment = new ContactDetailsFragment();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.frag_container,
                mContactListFragment,
                "Contacts");
        transaction.add(R.id.frag_container,
                mContactDetailsFragment);

        transaction.hide(mContactListFragment);
        transaction.hide(mContactDetailsFragment);

        transaction.commit();
    }

    //allows fragments to set page title
    public void setPageTitle(@Nullable String title) {
        mPageTitle.setText(title);
    }

    //Shows back button if fragment backstack available
    public void initBackButton() {
        if (mFragmentManager.getBackStackEntryCount() != 0) {
            mBackButton.setVisibility(View.VISIBLE);
            mBackButton.setText(getString(R.string.contacts_list_title));
        } else {
            mBackButton.setVisibility(View.GONE);
        }
    }

    /**
     * Set current visible fragment
     */
    public void setFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(fragment);

        if (addToBackstack) {
            transaction.addToBackStack(fragment.getTag());
            transaction.hide(mContactListFragment);
        }
        transaction.commit();
    }

    //Allows this MainActivity to communicate with fragments
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof ContactListFragment) {
            mContactListFragment = (ContactListFragment) fragment;
            mContactMapObservable.observe(mContactListFragment, listSparseArray -> {
                if (listSparseArray.get(ContactsViewModel.OTHER_CONTACTS_LIST).size() != 0) {
                    mContactListFragment.refreshList(listSparseArray);
                }
            });
        } else if (fragment instanceof ContactDetailsFragment) {
            mContactDetailsFragment = (ContactDetailsFragment) fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);

        return true;
    }


    //CALLBACK METHODS
    //These allow the fragments to notify user events to this MainActivity
    @Override
    public void clickedContact(ContactDto contact) {
        Bundle args = new Bundle();
        args.putParcelable(ContactDetailsFragment.BUNDLE_KEY, contact);
        mContactDetailsFragment.setArguments(args);

        setFragment(mContactDetailsFragment, true);
    }

    //DETAILS FRAGMENT CALLBACK
    @Override
    public void toggleFavorite(ContactDto contact) {
        mViewModel.toggledFavorite(contact);
    }
}
