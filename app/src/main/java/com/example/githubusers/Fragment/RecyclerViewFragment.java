package com.example.githubusers.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.githubusers.Adapter.RecyclerViewBaseAdapter;
import com.example.githubusers.R;

// add super.initFragmentView(rootView, inflater, container, savedInstanceState);
// in initFragmentView

public abstract class RecyclerViewFragment extends BaseFragment
{
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();

    private RelativeLayout mRVRelativeLayout;
    private TextView mEmptyRVTextView;
    private ImageView mEmptyRVImageView;
    private RelativeLayout mEmptyRVRelativeLayout;
    private RecyclerView mRecyclerView;

    private LinearLayout mProgressContainer;

    @Override
    protected int getLayoutID()
    {
        return 0;
    }

    @Override
    protected void initFragmentView(ViewGroup rootView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.e(TAG, "RecyclerViewFragment");

        //recycler_view_fragment_layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        mRVRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.mRVRelativeLayout);
        mRecyclerView.addOnScrollListener(mScrollListener);

        mProgressContainer = (LinearLayout) rootView.findViewById(R.id.progressContainer);
    }

    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }

    public final void setRecyclerViewAdapter(RecyclerView.Adapter adapter)
    {
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setSelected(true);
    }

    public RecyclerView.Adapter getRecyclerViewAdapter()
    {
        if (mRecyclerView == null)
            return null;

        return mRecyclerView.getAdapter();
    }


//=========================================================================
// detect RecycleView scroll status
//=========================================================================
    private EndlessScrollListener mEndLessScrollListener;

    public interface EndlessScrollListener
    {
        void onScrolledUp();
        void onScrolledDown();
        void onScrolledToTop();
        void onScrolledToBottom();
    }

    public void setEndlessScrollListener(EndlessScrollListener endLessScrollListener)
    {
        this.mEndLessScrollListener = endLessScrollListener;
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener()
    {
        @Override
        public final void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            if (!recyclerView.canScrollVertically(-1))
            {
                if (mEndLessScrollListener != null)
                    mEndLessScrollListener.onScrolledToTop();
            }
            else if (!recyclerView.canScrollVertically(1))
            {
                if (mEndLessScrollListener != null)
                    mEndLessScrollListener.onScrolledToBottom();
            }
            else if (dy < 0)
            {
                if (mEndLessScrollListener != null)
                    mEndLessScrollListener.onScrolledUp();
            }
            else if (dy > 0)
            {
                if (mEndLessScrollListener != null)
                    mEndLessScrollListener.onScrolledDown();
            }
        }
    };

    public void setRecyclerViewShown(boolean shown)
    {
        if (shown)
        {
            //1. List 		  appear
            //2. Circle       disapear
            //3. No data text appear(if list is empty)
            if (mProgressContainer != null)
                mProgressContainer.setVisibility(View.GONE);

            if (mRVRelativeLayout != null)
                mRVRelativeLayout.setVisibility(View.VISIBLE);

            if (mEmptyRVRelativeLayout != null)
            {
                mEmptyRVRelativeLayout.setVisibility(View.GONE);

                try
                {
                    int iListItemSize = 0;

                    if (getRecyclerView().getAdapter() != null)
                        iListItemSize = getRecyclerView().getAdapter().getItemCount();

                    if (getRecyclerView().getAdapter() instanceof RecyclerViewBaseAdapter)
                    {
                        RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter)getRecyclerView().getAdapter();

                        if (recyclerViewBaseAdapter.getHeaderViewHolder() != null)
                            iListItemSize--;

                        if (recyclerViewBaseAdapter.getFooterViewHolder() != null)
                            iListItemSize--;

                        Log.e(TAG, "Banner Count: " + String.valueOf(iListItemSize));

                        if (iListItemSize == 0)
                            mEmptyRVRelativeLayout.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            //1. List 		    disapear
            //2. Circle   	    appear
            //3. No data text 	disapear
            if (mProgressContainer != null)
                mProgressContainer.setVisibility(View.VISIBLE);

            if (mRVRelativeLayout != null)
                mRVRelativeLayout.setVisibility(View.GONE);

            if (mEmptyRVRelativeLayout != null)
                mEmptyRVRelativeLayout.setVisibility(View.GONE);
        }
    }

    protected RelativeLayout getEmptyRVRelativeLayout()
    {
        return mEmptyRVRelativeLayout;
    }
}
