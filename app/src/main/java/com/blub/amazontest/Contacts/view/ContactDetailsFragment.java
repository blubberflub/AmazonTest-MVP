package com.blub.amazontest.Contacts.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blub.amazontest.Contacts.BaseFragment;
import com.blub.amazontest.Contacts.model.ContactDto;
import com.blub.amazontest.MainActivity;
import com.blub.amazontest.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailsFragment extends BaseFragment {
    @BindView(R.id.details_profile_pic)
    ImageView mProfilePic;
    @BindView(R.id.details_contact_name)
    TextView mName;
    @BindView(R.id.details_contact_job)
    TextView mJob;
    @BindView(R.id.details_home_phone_number)
    TextView mHomePhone;
    @BindView(R.id.details_mobile_phone_number)
    TextView mMobilePhone;
    @BindView(R.id.details_work_phone_number)
    TextView mWorkPhone;
    @BindView(R.id.details_address)
    TextView mAddress;
    @BindView(R.id.details_birthday)
    TextView mBirthday;
    @BindView(R.id.details_email)
    TextView mEmail;

    private ContactDto mContact;

    private DetailsFragmentCallback mActivityCallback;

    public static ContactDetailsFragment newInstance(Parcelable in) {

        Bundle args = new Bundle();
        args.putParcelable(MainActivity.BUNDLE_KEY, in);

        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mContact = getArguments().getParcelable(MainActivity.BUNDLE_KEY);
        }

        RequestOptions placeHolder = new RequestOptions()
                .placeholder(R.drawable.ic_person);

        Glide.with(requireContext())
                .setDefaultRequestOptions(placeHolder)
                .load(mContact.getLargeImageURL())
                .into(mProfilePic);

        mName.setText(mContact.getName());
        mJob.setText(mContact.getCompanyName());
        mHomePhone.setText(mContact.getPhone().getHome());
        mMobilePhone.setText(mContact.getPhone().getMobile());
        mWorkPhone.setText(mContact.getPhone().getWork());

        //address
        String fullAddress = new StringBuilder()
                .append(mContact.getAddress().getStreet())
                .append(System.getProperty("line.separator"))
                .append(mContact.getAddress().getCity())
                .append(", ")
                .append(mContact.getAddress().getState())
                .append(" ")
                .append(mContact.getAddress().getZipCode())
                .append(", ")
                .append(mContact.getAddress().getCountry())
                .toString();

        mAddress.setText(fullAddress);
        mBirthday.setText(mContact.getBirthdate());
        mEmail.setText(mContact.getEmailAddress());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof DetailsFragmentCallback) {
            mActivityCallback = (DetailsFragmentCallback) getActivity();
            setTitle(null);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.findItem(R.id.favorite_btn)
                .setVisible(true)
                .setIcon(mContact.isFavorite() ? R.drawable.ic_star : R.drawable.ic_star_border);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_btn:
                item.setIcon(mContact.isFavorite() ? R.drawable.ic_star_border : R.drawable.ic_star);
                mActivityCallback.toggleFavorite(mContact);
                break;
        }

        return false;
    }

    public interface DetailsFragmentCallback {
        void toggleFavorite(ContactDto contactDto);
    }
}
