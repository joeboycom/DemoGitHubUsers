package com.example.githubusers.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.githubusers.MainActivity;
import com.example.githubusers.R;

/**
 * Must be extend it when you want to implement a Fragment！
 * */
public abstract class BaseFragment extends Fragment
{
	private final String TAG = BaseFragment.class.getSimpleName();
	
	private ViewGroup mRootView = null;
	protected RelativeLayout mLoadingCircle;
	private ProgressDialog mLoadingProgressDialog;

	//========== set getLayoutID to receive layout id ==========
	protected abstract int getLayoutID();

	//========== initView ==========
	protected abstract void initFragmentView(ViewGroup rootView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	//========== onActivityCreate ==========
	protected abstract void onActivityCreatedAction(Bundle savedInstanceState);

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
       
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{		
		if (mRootView == null)
		{
			mRootView = (ViewGroup) inflater.inflate(getLayoutID(), container, false);
			mLoadingCircle = (RelativeLayout) mRootView.findViewById(R.id.loadingCircle);
			initFragmentView(mRootView, inflater, container, savedInstanceState);
		}
		else
		{
			Log.e(TAG, "We have mRootView no initView");
        	ViewGroup parent = (ViewGroup) mRootView.getParent();
        	if (parent != null)
        		parent.removeView(mRootView);
        }

		return mRootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
        super.onActivityCreated(savedInstanceState);

        onActivityCreatedAction(savedInstanceState);
    }
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
	}
	
	public ViewGroup getRootView()
	{
		return mRootView;
	}

	public final void replaceFragment(Fragment fragment, String title)
	{
		((MainActivity)getActivity()).replaceFragment(fragment, true, title);
	}

	public boolean isLiveFragment()
	{
		if (isLiveActivity() == false)
			return false;
				
		if (isAdded() == false)
			return false;
		
		return true;
	}
	
	public boolean isLiveActivity()
	{
		if (getActivity() == null)
			return false;
		
		if (getActivity().isFinishing())
			return false;
		
		return true;
	}

//==========================================================================================
// 頁面載入對話方塊
//==========================================================================================

	//circle in the middle (won't block screen)
	public void showLoading()
	{
		if (mLoadingCircle != null)
			mLoadingCircle.setVisibility(View.VISIBLE);
	}

	public void dismissLoading()
	{
		if (mLoadingCircle != null)
			mLoadingCircle.setVisibility(View.GONE);
	}

	//Loading...(will block screen)
	protected void showLoadingProgressDialog()
	{
		showLoadingProgressDialog(getString(R.string.loading));
	}

	protected void showLoadingProgressDialog(final String strMessage)
	{
		if (!isLiveFragment())
			return;

		int delayMillis = 0;
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (!isLiveFragment())
					return;

				if (mLoadingProgressDialog != null && mLoadingProgressDialog.isShowing())
				{
					mLoadingProgressDialog.setMessage(strMessage);
					return;
				}

				mLoadingProgressDialog = new  ProgressDialog(getActivity());
				mLoadingProgressDialog.setProgressStyle(R.style.ProgressBar);
				mLoadingProgressDialog.setTitle("");
				mLoadingProgressDialog.setMessage(strMessage);
				mLoadingProgressDialog.show();
			}
		}, delayMillis);
	}

	protected void closeLoadingProgressDialog()
	{
		if (!isLiveFragment())
			return;

		int delayMillis = 0;
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (!isLiveFragment())
					return;

				if (mLoadingProgressDialog != null)
					mLoadingProgressDialog.dismiss();
			}
		}, delayMillis);
	}
}
