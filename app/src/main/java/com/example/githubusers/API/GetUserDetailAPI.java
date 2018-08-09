package com.example.githubusers.API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.githubusers.API.ApiResponse.GetUserDetailApiResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by joewu on 2018/8/8.
 */
public class GetUserDetailAPI extends AsyncTask<String, Integer, GetUserDetailApiResponse>
{
    private static final String TAG = GetUserDetailAPI.class.getSimpleName();

    private String mLogin = "";
    private int mStatusCode = -1;

    public GetUserDetailAPI(String login)
    {
        mLogin       = login;
    }

    private Callback mCallback;
    public interface Callback
    {
        public void onCompleted(GetUserDetailApiResponse getUserDetailApiResponse, int statusCode);
    }
    public void setApiCallback(Callback callback)
    {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    protected GetUserDetailApiResponse doInBackground(String... params)
    {
        Log.e(TAG, "==================" + TAG + "=================");
        APIUtility mService = APIUtility.API_BUILDER.create(APIUtility.class);

        GetUserDetailApiResponse getUserDetailApiResponse = new GetUserDetailApiResponse();

        try
        {
            Call<GetUserDetailApiResponse> call = mService.getUserDetailAPI(mLogin);

            Response<GetUserDetailApiResponse> response = call.execute();
            mStatusCode = response.code();
            String link = response.headers().get("Link");

            Log.e(TAG, "statusCode: " + mStatusCode);
            Log.e(TAG, "link: " + link);

            if (mStatusCode == APIStatusCode.ACCESS_SUCCESS_200)
            {
                getUserDetailApiResponse = response.body();
                Log.e(TAG, new Gson().toJson(response));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return getUserDetailApiResponse;
    }

    protected void onPostExecute(GetUserDetailApiResponse getUserDetailApiResponse)
    {
        Log.e(TAG, "onPostExecute");

        if (mCallback != null)
            mCallback.onCompleted(getUserDetailApiResponse, mStatusCode);
    }
}