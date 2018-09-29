package com.blub.amazontest.contacts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blub.amazontest.contacts.model.ContactDto;
import com.blub.amazontest.R;

public class ContactListDivider extends android.support.v7.widget.DividerItemDecoration {

    private final Context mContext;

    ContactListDivider(Context context, int orientation) {
        super(context, orientation);

        mContext = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(mContext, R.color.divider));

        int childCount = parent.getChildCount() -1;
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int itemCount = parent.getAdapter().getItemCount();

        for (int i = 0; i < childCount ; i++) {
            View view = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(view);
            int viewType = parent.getAdapter().getItemViewType(position);

            if (ContactDto.CONTACT == viewType) {
                int nextItem = position + 1;
                if(nextItem < itemCount)
                {
                    int nextViewType = parent.getAdapter().getItemViewType(nextItem);
                    if(ContactDto.CONTACT != nextViewType)
                        continue;
                }

                float topDraw = view.getBottom();
                float bottomDraw = view.getBottom() + 1;

                c.drawRect(left+50, topDraw, right-50, bottomDraw, paint);
            }
        }

    }
}
