package com.blub.amazontest.Contacts.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blub.amazontest.R;
import com.blub.amazontest.Contacts.model.ContactDto;
import com.blub.amazontest.Contacts.model.ViewItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<ViewItem> mItems;
    private final ContactViewHolder.OnItemClicked mClickListener;

    ContactsAdapter(Context context,
                    List<ViewItem> items,
                    ContactViewHolder.OnItemClicked clickListener) {
        mContext = context;
        mItems = items;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case ViewItem.FAVORITE_CONTACTS_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,
                        parent, false);
                return new HeaderViewHolder(view);
            case ViewItem.OTHER_CONTACTS_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,
                        parent, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact,
                        parent, false);
                break;
        }

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = holder.getItemViewType();

        switch (type) {
            case ViewItem.FAVORITE_CONTACTS_HEADER:
                HeaderViewHolder favoritesHeaderHolder = (HeaderViewHolder) holder;
                favoritesHeaderHolder.mHeader.setText("FAVORITE CONTACTS");
                break;
            case ViewItem.OTHER_CONTACTS_HEADER:
                HeaderViewHolder otherHeaderHolder = (HeaderViewHolder) holder;
                otherHeaderHolder.mHeader.setText("OTHER CONTACTS");
                break;
            case ViewItem.CONTACT:
                ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
                ContactDto item = (ContactDto) mItems.get(position);

                contactViewHolder.star.setVisibility(item.isFavorite() ? View.VISIBLE :View.GONE);
                contactViewHolder.mContactName.setText(item.getName());
                contactViewHolder.mContactJob.setText(item.getCompanyName());

                contactViewHolder.itemView.setOnClickListener(v ->
                        mClickListener.onItemClick(position));

                RequestOptions placeHolder = new RequestOptions()
                        .placeholder(R.drawable.ic_person);

                Glide.with(mContext)
                        .setDefaultRequestOptions(placeHolder)
                        .load(item.getSmallImageURL())
                        .into(contactViewHolder.mContactPicture);

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header)
        TextView mHeader;

        HeaderViewHolder(@NonNull View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_star)
        TextView star;
        @BindView(R.id.list_contact_profile_pic)
        ImageView mContactPicture;
        @BindView(R.id.list_contact_name)
        TextView mContactName;
        @BindView(R.id.list_contact_job)
        TextView mContactJob;

        ContactViewHolder(@NonNull View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        public interface OnItemClicked {
            void onItemClick(int position);
        }
    }
}
