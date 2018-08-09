package com.example.githubusers.Utility;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginDecoration extends RecyclerView.ItemDecoration
{
    private int mMargin;

    public MarginDecoration(Context context, int margin)
    {
        mMargin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.set(mMargin, mMargin, mMargin, mMargin);
    }

}
