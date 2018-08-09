package com.example.githubusers.Fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.githubusers.API.ApiResponse.GetUserListApiResponse;
import com.example.githubusers.API.GetUserListLoader;
import com.example.githubusers.Adapter.FooterRecyclerViewHolder;
import com.example.githubusers.Adapter.HeaderRecyclerViewHolder;
import com.example.githubusers.Adapter.UserListRVLAdapter;
import com.example.githubusers.MainActivity;
import com.example.githubusers.Model.UserList;
import com.example.githubusers.ParameterKey;
import com.example.githubusers.R;
import com.example.githubusers.Utility.RecyclerViewUtility;
import com.example.githubusers.Utility.ResourceUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Github user list page
 * Created by joewu on 2018/8/8.
 */
public class UserListFragment extends RecyclerViewFragment implements RecyclerViewFragment.EndlessScrollListener
{
    private static final String TAG = UserListFragment.class.getSimpleName();

    private String mNextPageLink    = "";
    private String mDefaultPageSize = "10";

    private boolean mIsLoading       = false;
    private View    mEndlessProgress = null;              // show on list bottom when no data

    private UserListRVLAdapter mUserListRVLAdapter = null;

    private class UserListOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            UserList userList = (UserList) v.getTag();

            UserDetailFragment userDetailFragment = new UserDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ParameterKey.BUNDLE_LOGIN, userList.getLogin());
            userDetailFragment.setArguments(bundle);
            replaceFragment(userDetailFragment, "UserDetailFragment");
        }
    }
    private UserListOnClickListener mUserListOnClickListener = new UserListOnClickListener();

    private LoaderManager.LoaderCallbacks<GetUserListApiResponse> mUserListCallBack = new LoaderManager.LoaderCallbacks<GetUserListApiResponse>()
    {
        @Override
        public Loader<GetUserListApiResponse> onCreateLoader(int id, Bundle args)
        {
            Log.e(TAG, "onCreateLoader");

            if (    args != null &&
                    (args.containsKey(GetUserListLoader.ARG_REQUEST_SINCE) &&
                     args.containsKey(GetUserListLoader.ARG_REQUEST_PAGE_SIZE)) ||
                    args.containsKey(GetUserListLoader.ARG_REQUEST_NEXT_PAGE_LINK))
            {
                return new GetUserListLoader(getActivity(), args);
            }

            return null;
        }

        @Override
        public void onLoadFinished(Loader<GetUserListApiResponse> loader, GetUserListApiResponse restResponse)
        {
            if (restResponse.dataArrayList != null)
            {
                Log.e(TAG, "onLoadFinished");
                try
                {
                    if (restResponse.nextPageLink.equals("-1"))
                    {
                        setRecyclerViewShown(true);
                        onLoadMoreDataFinished();
                        return ;
                    }

                    List<UserList> rowList = restResponse.dataArrayList;
                    Log.e(TAG, "restResponse arrayList size: " + rowList.size());
                    mNextPageLink = restResponse.nextPageLink;
                    mUserListRVLAdapter.getDataArrayList().addAll(rowList);
                    mUserListRVLAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            setRecyclerViewShown(true);
            onLoadMoreDataFinished();
        }

        @Override
        public void onLoaderReset(Loader<GetUserListApiResponse> loader)
        {
            Log.e(TAG, "onLoaderReset");
        }
    };

    @Override
    protected int getLayoutID()
    {
        return R.layout.fragment_user_list;
    }

    @Override
    protected void initFragmentView(ViewGroup rootView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.initFragmentView(rootView, inflater, container, savedInstanceState);
        Log.e(TAG, "initFragmentView");

        RecyclerViewUtility.setLinearLayoutManager(getActivity(), getRecyclerView(), LinearLayoutManager.VERTICAL, false);
        mUserListRVLAdapter = new UserListRVLAdapter(getActivity(), new ArrayList<UserList>(), mUserListOnClickListener);
        setRecyclerViewAdapter(mUserListRVLAdapter);

        TextView usersHeaderTextView = new TextView(getActivity());
        usersHeaderTextView.setText(getResources().getString(R.string.header_user_list));
        usersHeaderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        usersHeaderTextView.setPadding((int) ResourceUtility.dpToPixel((float) 20, getActivity()), (int) ResourceUtility.dpToPixel((float) 20, getActivity()),0,0);
        HeaderRecyclerViewHolder headerRecyclerViewHolder = new HeaderRecyclerViewHolder(usersHeaderTextView);
        mUserListRVLAdapter.addHeaderViewHolder(headerRecyclerViewHolder);

        mEndlessProgress = LayoutInflater.from(getActivity()).inflate(R.layout.progress, getRecyclerView(), false);
        FooterRecyclerViewHolder footerView = new FooterRecyclerViewHolder(mEndlessProgress);
        mUserListRVLAdapter.addFooterViewHolder(footerView);

        setEndlessScrollListener(this);
        setRecyclerViewShown(false);
        restartLoader("0", mDefaultPageSize);
    }

    @Override
    protected void onActivityCreatedAction(Bundle savedInstanceState)
    {
        Log.e(TAG, "onActivityCreatedAction");
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).showActionBar();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).hideActionBar();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onScrolledUp()
    {
    }

    @Override
    public void onScrolledDown()
    {
    }

    @Override
    public void onScrolledToTop()
    {
    }

    @Override
    public void onScrolledToBottom()
    {
        Log.e(TAG, "onScrolledToBottom");

        if (mIsLoading)
        {
            Log.e(TAG, "onEndlessScroll still loading");
            return ;
        }

        if (!TextUtils.isEmpty(mNextPageLink))
        {
            mIsLoading = true;
            mEndlessProgress.setVisibility(View.VISIBLE);

            loadMoreData(mNextPageLink);

            Log.e(TAG, "onEndlessScroll has more data");
            return ;
        }
        else
        {
            onLoadMoreDataFinished();
            Log.e(TAG, "onEndlessScroll no more data");
        }
    }

    public void restartLoader(String since, String pageSize)
    {
        Log.e(TAG, "restartLoader");

        Bundle args = new Bundle();
        args.putString(GetUserListLoader.ARG_REQUEST_SINCE,         since);      // start from specific item
        args.putString(GetUserListLoader.ARG_REQUEST_PAGE_SIZE,     pageSize);   // default page size

        if (getLoaderManager().getLoader(GetUserListLoader.LOADER_ID_GET_USER_LIST) == null)
            getLoaderManager().initLoader(GetUserListLoader.LOADER_ID_GET_USER_LIST, args, mUserListCallBack);
        else
            getLoaderManager().restartLoader(GetUserListLoader.LOADER_ID_GET_USER_LIST, args, mUserListCallBack);
    }

    private void loadMoreData(String nextPageLink)
    {
        Log.e(TAG, "loadMoreData");

        Bundle args = new Bundle();
        args.putString(GetUserListLoader.ARG_REQUEST_NEXT_PAGE_LINK, nextPageLink); // link header about next page

        getLoaderManager().restartLoader(GetUserListLoader.LOADER_ID_GET_USER_LIST, args, mUserListCallBack);
    }

    private void onLoadMoreDataFinished()
    {
        Log.e(TAG, "onLoadMoreDataFinished");

        if (mIsLoading)
            mIsLoading = false;

        mEndlessProgress.setVisibility(View.GONE);
    }
}
