package com.example.githubusers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

public abstract class RecyclerViewBaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
{
	private final static String TAG = RecyclerViewBaseAdapter.class.getSimpleName();

	public static final int TYPE_HEADER = 0;  //has Header
	public static final int TYPE_FOOTER = 1;  //has Footer
	public static final int TYPE_NORMAL = 2;  //no header and footer

	protected Context mContext;
	private LayoutInflater mInflater;
	protected RecyclerView.ViewHolder mHeaderViewHolder = null;
	protected RecyclerView.ViewHolder mFooterViewHolder = null;

	public RecyclerViewBaseAdapter(Context context)
	{
		super();
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemCount()
	{
		int iTotalItemCount = getDataCount();

		if (getHeaderViewHolder() != null)
			iTotalItemCount++;

		if (getFooterViewHolder() != null)
			iTotalItemCount++;

		return iTotalItemCount;
	}

	public abstract int getDataCount();

	public abstract Object getItem(int position);

	@Override
	public int getItemViewType(int position)
	{
		if (getHeaderViewHolder() == null && getFooterViewHolder() == null)
			return TYPE_NORMAL;

		if (getHeaderViewHolder() != null && position == 0)
			return TYPE_HEADER;

		if (getFooterViewHolder() != null && position == getItemCount()-1)
			return TYPE_FOOTER;

		return TYPE_NORMAL;
	}

	public LayoutInflater getInflater()											{return mInflater;}

	public RecyclerView.ViewHolder 			getFooterViewHolder() 					{return mFooterViewHolder;}
	public RecyclerView.ViewHolder 			getHeaderViewHolder() 					{return mHeaderViewHolder;}

	public boolean checkHeaderViewHolder(int viewType)
	{
		if (getHeaderViewHolder() != null && viewType == TYPE_HEADER)
			return true;

		return false;
	}

	public boolean checkFooterViewHolder(int viewType)
	{
		if (getFooterViewHolder() != null && viewType == TYPE_FOOTER)
			return true;

		return false;
	}

	public boolean checkHeaderOrFooterViewHolder(RecyclerView.ViewHolder holder)
	{
		int viewType = holder.getItemViewType();
		if (checkHeaderViewHolder(viewType) || checkFooterViewHolder(viewType))
			return true;

		return false;
	}

	public RecyclerView.ViewHolder getHeaderOrFooterViewHolder(int viewType)
	{
		if (checkHeaderViewHolder(viewType))
			return getHeaderViewHolder();

		if (checkFooterViewHolder(viewType))
			return getFooterViewHolder();

		return null;
	}

	public void addHeaderViewHolder(RecyclerView.ViewHolder headerViewHolder)
	{
		mHeaderViewHolder = headerViewHolder;
		notifyItemInserted(0);
	}

	public void addFooterViewHolder(RecyclerView.ViewHolder footerViewHolder)
	{
		mFooterViewHolder = footerViewHolder;
		notifyItemInserted(getItemCount()-1);
	}

	public int offsetHeaderPosition(int position)
	{
		if (getHeaderViewHolder() != null)
		{
			position = position - 1;
			Log.e(TAG, "offsetHeaderPosition position: " + position);
		}

		return position;
	}
}
