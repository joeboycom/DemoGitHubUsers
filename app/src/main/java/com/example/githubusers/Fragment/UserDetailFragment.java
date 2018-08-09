package com.example.githubusers.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubusers.API.APIStatusCode;
import com.example.githubusers.API.ApiResponse.GetUserDetailApiResponse;
import com.example.githubusers.API.GetUserDetailAPI;
import com.example.githubusers.MainActivity;
import com.example.githubusers.ParameterKey;
import com.example.githubusers.R;
import com.example.githubusers.Utility.GlideUtility;

/**
 * User detail page
 * Created by joewu on 2018/8/8.
 */
public class UserDetailFragment extends BaseFragment
{
    private static final String TAG = UserDetailFragment.class.getSimpleName();

    private String mstrLogin                = "";
    private String mstrBlogURL              = "";
    private ImageView mCancelImageView      = null;
    private ImageView mUserLoginImageView   = null;
    private ImageView mUserPhotoImageView   = null;
    private ImageView mUserLocationImageView= null;
    private ImageView mUserBlogImageView    = null;
    private TextView mUserNameTextView      = null;
    private TextView mUserBioTextView       = null;
    private TextView mUserLoginTextView     = null;
    private TextView mUserSiteAdminTextView = null;
    private TextView mUserLocationTextView  = null;
    private TextView mUserBlogTextView      = null;

    @Override
    protected int getLayoutID()
    {
        return R.layout.fragment_user_detail;
    }

    @Override
    protected void initFragmentView(ViewGroup rootView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.e(TAG, "initFragmentView");

        if (getArguments() != null)
            mstrLogin = getArguments().getString(ParameterKey.BUNDLE_LOGIN);

        mCancelImageView = (ImageView) rootView.findViewById(R.id.mCancelImageView);
        mCancelImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().onBackPressed();
            }
        });


        mUserPhotoImageView     = (ImageView) rootView.findViewById(R.id.mUserPhotoImageView);
        mUserLoginImageView     = (ImageView) rootView.findViewById(R.id.mUserLoginImageView);
        mUserLocationImageView  = (ImageView) rootView.findViewById(R.id.mUserLocationImageView);
        mUserBlogImageView      = (ImageView) rootView.findViewById(R.id.mUserBlogImageView);
        mUserNameTextView       = (TextView) rootView.findViewById(R.id.mUserNameTextView);
        mUserBioTextView        = (TextView) rootView.findViewById(R.id.mUserBioTextView);
        mUserLoginTextView      = (TextView) rootView.findViewById(R.id.mUserLoginTextView);
        mUserSiteAdminTextView  = (TextView) rootView.findViewById(R.id.mUserSiteAdminTextView);
        mUserLocationTextView   = (TextView) rootView.findViewById(R.id.mUserLocationTextView);
        mUserBlogTextView       = (TextView) rootView.findViewById(R.id.mUserBlogTextView);
        mUserBlogTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(mstrBlogURL))
                    return ;

                Bundle bundle = new Bundle();
                bundle.putString(ParameterKey.BUNDLE_BLOG_URL, mstrBlogURL);

                WebViewFragment webViewFragment = new WebViewFragment();
                webViewFragment.setArguments(bundle);
                replaceFragment(webViewFragment, "WebViewFragment");
            }
        });

        GetUserDetailAPI getUserDetailAPI = new GetUserDetailAPI(mstrLogin);
        getUserDetailAPI.setApiCallback(new GetUserDetailAPI.Callback()
        {
            @Override
            public void onCompleted(GetUserDetailApiResponse getUserDetailApiResponse, int statusCode)
            {
                if (!isLiveFragment())
                    return ;

                if (statusCode != APIStatusCode.ACCESS_SUCCESS_200)
                {
                    closeLoadingProgressDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                    return ;
                }

                mstrBlogURL = getUserDetailApiResponse.blog;
                GlideUtility.useGlide(getActivity(), getUserDetailApiResponse.avatar_url, mUserPhotoImageView, R.drawable.default_avatar, R.drawable.default_avatar);
                mUserNameTextView.setText(getUserDetailApiResponse.name);
                mUserBioTextView.setText(getUserDetailApiResponse.bio);
                if (!TextUtils.isEmpty(getUserDetailApiResponse.login))
                {
                    mUserLoginImageView.setVisibility(View.VISIBLE);
                    mUserLoginTextView.setText(getUserDetailApiResponse.login);
                }
                if (getUserDetailApiResponse.site_admin)
                    mUserSiteAdminTextView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(getUserDetailApiResponse.location))
                {
                    mUserLocationImageView.setVisibility(View.VISIBLE);
                    mUserLocationTextView.setText(getUserDetailApiResponse.location);
                }
                if (!TextUtils.isEmpty(getUserDetailApiResponse.blog))
                {
                    mUserBlogImageView.setVisibility(View.VISIBLE);
                    mUserBlogTextView.setText(getUserDetailApiResponse.blog);
                }
                closeLoadingProgressDialog();
            }
        });
        getUserDetailAPI.execute();

        showLoadingProgressDialog();
    }

    @Override
    protected void onActivityCreatedAction(Bundle savedInstanceState)
    {
        Log.e(TAG, "onActivityCreatedAction");
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).hideActionBar();
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
    public void onDestroy()
    {
        super.onDestroy();
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).showActionBar();
    }
}
