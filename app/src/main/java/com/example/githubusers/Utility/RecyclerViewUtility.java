package com.example.githubusers.Utility;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.githubusers.Adapter.RecyclerViewBaseAdapter;


public class RecyclerViewUtility
{
    //RecyclerViewUtility.setLinearLayoutManager(getActivity(), getRecyclerView(), LinearLayoutManager.VERTICAL, true);
    public static void setLinearLayoutManager(Context context, RecyclerView recyclerView, int iOrientation, boolean boDivider)
    {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(iOrientation);
        recyclerView.setLayoutManager(null);
        recyclerView.setLayoutManager(layoutManager);

        //one boDivider == true;
        //then can't not call again
        if (boDivider)
        {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    //RecyclerViewUtility.setLinearLayoutManagerNoFooterDivider(getActivity(), getRecyclerView());
    public static void setLinearLayoutManagerNoFooterDivider(Context context, RecyclerView recyclerView)
    {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ItemDecorationNoFooterDivider verticalSpaceItemDecoration = new ItemDecorationNoFooterDivider(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(verticalSpaceItemDecoration);
    }

    //int iPadding= getActivity().getResources().getDimensionPixelSize(R.dimen.padding_micro);
    //RecyclerViewUtility.setGridLayoutManager(getActivity(), mAlbumToasterRecyclerView, LinearLayoutManager.VERTICAL, new MarginDecoration(getActivity(), iPadding), 2);
    //android:padding="@dimen/padding_micro"
    public static void setGridLayoutManager(Context context, RecyclerView recyclerView, int iOrientation, MarginDecoration marginDecoration, int spanCount)
    {
        final GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        layoutManager.setOrientation(iOrientation);
        recyclerView.setLayoutManager(layoutManager);

        if (marginDecoration != null)
            recyclerView.addItemDecoration(marginDecoration);
    }

    public static void setGridLayoutManagerWithHeaderOrFooter(Context context, RecyclerView recyclerView, int iOrientation, MarginDecoration marginDecoration, int spanCount, final RecyclerViewBaseAdapter recyclerViewBaseAdapter)
    {
        final GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        layoutManager.setOrientation(iOrientation);
        recyclerView.setLayoutManager(layoutManager);

        if (marginDecoration != null)
            recyclerView.addItemDecoration(marginDecoration);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if (recyclerViewBaseAdapter == null)
                    return 1;

                if (recyclerViewBaseAdapter.getHeaderViewHolder()       != null &&
                    recyclerViewBaseAdapter.getItemViewType(position)   == RecyclerViewBaseAdapter.TYPE_HEADER)
                    return layoutManager.getSpanCount();

                if (recyclerViewBaseAdapter.getFooterViewHolder()       != null &&
                    recyclerViewBaseAdapter.getItemViewType(position)   == RecyclerViewBaseAdapter.TYPE_FOOTER)
                    return layoutManager.getSpanCount();

                return 1;
            }
        });
    }

    public static void setStaggeredGridLayoutManager(Context context, RecyclerView recyclerView, int iOrientation, MarginDecoration marginDecoration, int spanCount)
    {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, iOrientation);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(null); // remove all animation on recyclerview

        if (marginDecoration != null)
            recyclerView.addItemDecoration(marginDecoration);
    }
}
