package com.blub.amazontest;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blub.amazontest.Contacts.ContactsContract;
import com.blub.amazontest.Contacts.ContactsPresenter;
import com.blub.amazontest.Contacts.dao.ContactsDatabase;
import com.blub.amazontest.Contacts.dao.ContactsRepositoryImpl;
import com.blub.amazontest.Contacts.model.ContactDto;
import com.blub.amazontest.Contacts.view.ContactDetailsFragment;
import com.blub.amazontest.Contacts.view.ContactListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main controller for fragments and toolbar
 *
 * UI for {@link ContactsPresenter}
 */
public class MainActivity extends AppCompatActivity implements ContactsContract.View,
        ContactListFragment.ListFragmentCallBack,
        ContactDetailsFragment.DetailsFragmentCallback {

    @BindView(R.id.actionbar)
    Toolbar mActionBar;
    @BindView(R.id.back_button)
    TextView mBackButton;
    @BindView(R.id.pageTitle)
    TextView mPageTitle;

    private FragmentManager mFragmentManager;
    private ContactsContract.UserActionsListener mPresenter;

    //fragments
    private ContactListFragment mContactListFragment;
    private ContactDetailsFragment mContactDetailsFragment;

    //fragment codes
    public static final String BUNDLE_KEY = "ARGS";

    public static final int DETAILS_FRAGMENT = 0;
    public static final int LIST_FRAGMENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mActionBar);
        getSupportActionBar().setTitle(null);

        mBackButton.setOnClickListener(v -> onBackPressed());

        mPresenter = new ContactsPresenter(this,
                new ContactsRepositoryImpl(ContactsDatabase
                        .getContactsDatabase(this)
                        .contactsRoomDao()));

        mFragmentManager = getSupportFragmentManager();

        setFragment(LIST_FRAGMENT, null, false);
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
     *
     * @param fragmentCode int references for fragments found in this {@link MainActivity}
     * @param extras parcelable extras for fragment communication
     * @param addToBackStack adds fragment to backstack if set to true
     */
    public void setFragment(int fragmentCode,@Nullable Parcelable extras, boolean addToBackStack) {
        Fragment fragment = null;

        switch (fragmentCode) {
            case LIST_FRAGMENT:
                fragment = mFragmentManager.findFragmentByTag(ContactListFragment.class.getSimpleName());

                if (fragment == null) {
                    fragment = ContactListFragment.newInstance(extras);
                }
                break;
            case DETAILS_FRAGMENT:
                fragment = mFragmentManager.findFragmentByTag(ContactDetailsFragment.class.getSimpleName());

                if (fragment == null) {
                    fragment = ContactDetailsFragment.newInstance(extras);
                }
                break;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName());

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    //Allows this MainActivity to communicate with fragments
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof ContactListFragment) {
            mContactListFragment = (ContactListFragment) fragment;
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

    //ContactsContract.View methods

    @Override
    public void toggleProgressBar(boolean visible) {
        mContactListFragment.toggleProgressBar(visible ? ProgressBar.VISIBLE : ProgressBar.GONE);
    }

    @Override
    public void showContactsList(List<ContactDto> favoritesList, List<ContactDto> contactsList) {
        mContactListFragment.showContactsList(favoritesList, contactsList);
    }

    @Override
    public void showContactDetails(ContactDto contactDto) {
        setFragment(DETAILS_FRAGMENT, contactDto, true);
    }

    //CALLBACK METHODS
    //These allow the fragments to notify user events to this MainActivity

    //LIST FRAGMENT CALLBACK
    @Override
    public void openedListView() {
        mPresenter.openedContactsList();
    }

    @Override
    public void clickedContact(ContactDto contact) {
        mPresenter.selectedContact(contact);
    }

    //DETAILS FRAGMENT CALLBACK
    @Override
    public void toggleFavorite(ContactDto contact) {
        mPresenter.toggleFavorite(contact);
    }
}
