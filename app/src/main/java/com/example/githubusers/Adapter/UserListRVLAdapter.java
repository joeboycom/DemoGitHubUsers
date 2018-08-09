package com.example.githubusers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubusers.Model.UserList;
import com.example.githubusers.R;
import com.example.githubusers.Utility.GlideUtility;

import java.util.ArrayList;

public class UserListRVLAdapter extends RecyclerViewListAdapter
{
    private View.OnClickListener mOnClickListener = null;

    public UserListRVLAdapter(Context context, ArrayList<UserList> list, View.OnClickListener listener)
    {
        super(context, list);
        mOnClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder hfvh = getHeaderOrFooterViewHolder(viewType);
        if (hfvh != null)
            return hfvh;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_list, parent, false);
        UserListViewHolder vh = new UserListViewHolder(v);

        vh.mUserListRowLinearLayout.setOnClickListener(mOnClickListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (checkHeaderOrFooterViewHolder(holder))
            return;

        if (getDataArrayList() == null)
            return;

        UserListViewHolder vh = (UserListViewHolder) holder;

        UserList userList = (UserList) getItem(position);

        if (userList == null)
            return ;

        vh.mUserLoginTextView.setText(userList.getLogin());

        vh.mUserSiteAdminTextView.setVisibility(View.GONE);
        if (userList.isSiteAdmin())
            vh.mUserSiteAdminTextView.setVisibility(View.VISIBLE);

        GlideUtility.useGlide(mContext, userList.getAvatarUrl(), vh.mUserPhotoCircularImageView, R.drawable.default_avatar, R.drawable.default_avatar);

        vh.mUserListRowLinearLayout.setTag(userList);
    }
}