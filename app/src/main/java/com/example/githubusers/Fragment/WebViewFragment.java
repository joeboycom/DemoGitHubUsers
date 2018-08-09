package com.example.githubusers.Fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubusers.BuildConfig;
import com.example.githubusers.MainActivity;
import com.example.githubusers.ParameterKey;
import com.example.githubusers.R;

/**
 * WebView page
 * Created by joewu
 * */
public class WebViewFragment extends BaseFragment
{
    private static final String TAG = WebViewFragment.class.getName();
    private TextView mToolbarTitleTextView;
    private ImageView mToolbarBackButtonImageView;
    private ImageView mToolbarExitButtonImageView;

    private WebView mWebView;

    @Override
    protected int getLayoutID()
    {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initFragmentView(ViewGroup rootView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mWebView                    = (WebView) rootView.findViewById(R.id.mWebView);
        mToolbarTitleTextView       = (TextView) rootView.findViewById(R.id.mToolbarTitleTextView);
        mToolbarBackButtonImageView = (ImageView) rootView.findViewById(R.id.mToolbarBackButtonImageView);
        mToolbarBackButtonImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "mWebview.canGoBack():" + mWebView.canGoBack());
                if (mWebView.canGoBack())
                {
                    mWebView.goBack();
                }
                else
                {
                    getActivity().onBackPressed();
                }

            }
        });
        mToolbarBackButtonImageView.setVisibility(View.INVISIBLE);

        mToolbarExitButtonImageView = (ImageView) rootView.findViewById(R.id.mToolbarExitButtonImageView);
        mToolbarExitButtonImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().onBackPressed();
            }
        });

        try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setDisplayZoomControls(true);
        }
        catch (Exception ex)
        {
        }

        mWebView.setWebViewClient(new HelpWebViewClient());

        if (getArguments() != null)
        {
            String url = getArguments().getString(ParameterKey.BUNDLE_BLOG_URL);
            if (TextUtils.isEmpty(url))
            {
                if (BuildConfig.DEBUG)
                    Toast.makeText(getActivity(), "Failed to load Page.", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d(TAG, "mWebviewUrl: " + url);
            mWebView.loadUrl(url);
        }
        else
        {
            if (BuildConfig.DEBUG)
                Toast.makeText(getActivity(), "Failed to load Page.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityCreatedAction(Bundle savedInstanceState)
    {
        Log.e(TAG, "onActivityCreatedAction");
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).hideActionBar();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).showActionBar();
    }

    private class HelpWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            Log.e(TAG, "shouldOverrideUrlLoading");
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            Log.e(TAG, "onPageStarted");
            showLoading();
        }

        @Override
        public void onPageFinished(WebView view, final String url)
        {
            Log.e(TAG, "onPageFinished");

            dismissLoading();

            if (mWebView.canGoBack())
                mToolbarBackButtonImageView.setVisibility(View.VISIBLE);
            else
                mToolbarBackButtonImageView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            Log.e(TAG, "onReceivedSslError");
            handler.cancel();
        }
    }
}
