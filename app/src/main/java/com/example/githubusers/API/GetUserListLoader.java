package com.example.githubusers.API;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.example.githubusers.API.ApiResponse.GetUserListApiResponse;
import com.example.githubusers.Model.UserList;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by joewu on 2018/8/8.
 */
public class GetUserListLoader extends AsyncTaskLoader<GetUserListApiResponse>
{
    private static final String TAG = GetUserListLoader.class.getSimpleName();

    public static final int LOADER_ID_GET_USER_LIST = 101;

    public static final String ARG_REQUEST_NEXT_PAGE_LINK = "ARG_REQUEST_NEXT_PAGE_LINK";
    public static final String ARG_REQUEST_SINCE          = "ARG_REQUEST_SINCE";
    public static final String ARG_REQUEST_PAGE_SIZE      = "ARG_REQUEST_PAGE_SIZE";

    // We use this delta to determine if our cached data is old or not. The value we have here is 10 minutes;
    private static final long STALE_DELTA = 600000;

    private long mLastLoadTimestamp;

    private int mStatusCode = -1;


    private String mSince       = "";
    private String mPageSize    = "";
    private String mNextPageLink= "";

    private GetUserListApiResponse mRestResponse;

    public GetUserListLoader(Context context, Bundle args)
    {
        super(context);
        mNextPageLink= args.getString(GetUserListLoader.ARG_REQUEST_NEXT_PAGE_LINK);
        mSince       = args.getString(GetUserListLoader.ARG_REQUEST_SINCE);
        mPageSize    = args.getString(GetUserListLoader.ARG_REQUEST_PAGE_SIZE);
    }

    @Override
    public GetUserListApiResponse loadInBackground()
    {
        Log.e(TAG, "==================" + TAG + "=================");
        APIUtility mService = APIUtility.API_BUILDER.create(APIUtility.class);

        GetUserListApiResponse getUserListApiResponse = new GetUserListApiResponse();

        try
        {
            Call<List<UserList>> call = null;
            if (TextUtils.isEmpty(mNextPageLink))
            {
                call = mService.getUserListAPI(mSince, mPageSize);
            }
            else
            {
                call = mService.getUserListAPI(mNextPageLink);
            }

            Response<List<UserList>> response = call.execute();
            mStatusCode = response.code();
            Log.e(TAG, "statusCode: " + mStatusCode);

            if (mStatusCode == APIStatusCode.ACCESS_SUCCESS_200)
            {
                getUserListApiResponse.dataArrayList = response.body();
                getUserListApiResponse.nextPageLink = nextFromGithubLinks(response.headers().get("Link"));
                Log.e(TAG, new Gson().toJson(response));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return getUserListApiResponse;
    }

    @Override
    protected void onStartLoading()
    {
        if (mRestResponse != null)
            super.deliverResult(mRestResponse);

        // If our response is null or we have hung onto it for a long time, then we perform a force load.
        if (mRestResponse == null || System.currentTimeMillis() - mLastLoadTimestamp >= STALE_DELTA)
            forceLoad();

        mLastLoadTimestamp = System.currentTimeMillis();
    }

    @Override
    protected void onStopLoading()
    {
        cancelLoad();
    }

    @Override
    protected void onReset()
    {
        super.onReset();

        onStopLoading();
        mRestResponse = null;
        mLastLoadTimestamp = 0;
    }

    public void deliverResult(GetUserListApiResponse data)
    {
        super.deliverResult(data);

        mRestResponse = data;
    }

    private String nextFromGithubLinks(String link)
    {
        String nextPageLink = "-1";

        try
        {
            nextPageLink = link.substring(link.indexOf("<") + 1, link.indexOf(">; rel=\"next\""));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.e(TAG, "next page link: " + nextPageLink);

        return nextPageLink;
    }
}