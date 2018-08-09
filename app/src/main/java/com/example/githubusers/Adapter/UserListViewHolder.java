package com.example.githubusers.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.githubusers.R;


public class UserListViewHolder extends RecyclerView.ViewHolder
{
    public LinearLayout mUserListRowLinearLayout;
    public ImageView    mUserPhotoCircularImageView;
    public TextView     mUserLoginTextView;
    public TextView     mUserSiteAdminTextView;

    public UserListViewHolder(View itemView)
    {
        super(itemView);

        mUserListRowLinearLayout        = (LinearLayout) itemView.findViewById(R.id.mUserListRowLinearLayout);
        mUserPhotoCircularImageView     = (ImageView) itemView.findViewById(R.id.mUserPhotoImageView);
        mUserLoginTextView              = (TextView) itemView.findViewById(R.id.mUserLoginTextView);
        mUserSiteAdminTextView          = (TextView) itemView.findViewById(R.id.mUserSiteAdminTextView);
    }
}
