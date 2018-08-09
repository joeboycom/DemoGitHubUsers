package com.example.githubusers.Adapter;

import android.content.Context;

import java.util.ArrayList;

public abstract class RecyclerViewListAdapter<T> extends RecyclerViewBaseAdapter
{
	private ArrayList<T> mDataArrayList;

    private int miVisiableCount = -1;

	public RecyclerViewListAdapter(Context context, ArrayList<T> dataArrayList)
	{
		super(context);
		mDataArrayList = dataArrayList;
	}

	@Override
	public int getItemCount()
	{
		if (miVisiableCount != -1 && miVisiableCount < getDataCount())
			return miVisiableCount + 1;

		return super.getItemCount();
	}

	@Override
	public int getDataCount()
	{
		if (getDataArrayList() != null)
			return getDataArrayList().size();

		return 0;
	}

	@Override
	public T getItem(int position)
	{
		if (getDataArrayList() == null)
			return null;

		if (getDataArrayList().size() == 0)
			return null;

		position = offsetHeaderPosition(position);

		if (position >= getDataArrayList().size())
			return null;

		return getDataArrayList().get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return offsetHeaderPosition(position);
	}

	public void clear() 
	{
		if (getDataArrayList() != null)
		{
			getDataArrayList().clear();
			setVisiableCount(-1);
			notifyDataSetChanged();
		}
	}

	public void appendArrayList(ArrayList<T> appendArrayList)
	{
		if (getDataArrayList() == null)
		{
			mDataArrayList = appendArrayList;
		}
		else
		{
			getDataArrayList().addAll(appendArrayList);
		}
	}

	public int 				getVisiableCount(int iVisiableCount) 	{return miVisiableCount;}
	public ArrayList<T> getDataArrayList() 							{return mDataArrayList;}

	public void setVisiableCount(int iVisiableIndex) 				{miVisiableCount 	= iVisiableIndex;}
	public void setDataArrayList(ArrayList<T> dataArrayList) 		{mDataArrayList		= dataArrayList;}

}
