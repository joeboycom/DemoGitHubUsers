package com.example.githubusers.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ResourceUtility
{
    private final static String TAG = ResourceUtility.class.getSimpleName();

    public static final float RADIO_SCREEN_WIDTH = 100;

    //float fRatio = ResourceUtility.getRatioFormScreen(getResources(), R.integer.ratio_digit_tracing_star_fragment_tracing_imageview_width);
    public static float getRatioFormScreen(Resources resources, int iNumeratorResID)
    {
        float fNumerator = resources.getInteger(iNumeratorResID);
        float fDenominator = RADIO_SCREEN_WIDTH;
        float fValue = fNumerator / fDenominator;

        return fValue;
    }

    public static float getRatio(Resources resources, int iNumeratorResID, int iDenominatorResID)
    {
        float fNumerator = resources.getInteger(iNumeratorResID);
        float fDenominator = resources.getInteger(iDenominatorResID);
        float fValue = fNumerator / fDenominator;

        return fValue;
    }

    public static int get(Resources resources, int iResID)
    {
        int fValue = resources.getInteger(iResID);
        return fValue;
    }

    public static float dpToPixel(float dp, Context context)
    {
        float px = dp * getDensity(context);
        return px;
    }

    public static float pixelToDp(float px, Context context)
    {
        float dp = px / getDensity(context);
        return dp;
    }

    /**
     * get screen density
     * 120dpi = 0.75
     * 160dpi = 1 (default)
     * 240dpi = 1.5
     */
    public static float getDensity(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity)
    {
        DisplayMetrics metrics = new DisplayMetrics();

        try
        {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return metrics;
    }

    public static DisplayMetrics getDisplayMetrics(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();

        try
        {
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return metrics;
    }

    public static DisplayMetrics getDisplayMetrics(Fragment fragment)
    {
        try
        {
            DisplayMetrics metrics = fragment.getResources().getDisplayMetrics();
            return metrics;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new DisplayMetrics();
    }

    // float fOrgScreenWidth = ResourceUtility.getScreenWidth(getActivity());
    public static float getScreenWidth(Activity activity)
    {
        float fWidth = 0;

        if (activity == null)
            return fWidth;

        try
        {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            fWidth = metrics.widthPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fWidth;
    }

    public static float getScreenWidth(Fragment fragment)
    {
        float fWidth = 0;

        if (fragment == null)
            return fWidth;

        try
        {
            DisplayMetrics displayMetrics = fragment.getResources().getDisplayMetrics();
            fWidth = displayMetrics.widthPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fWidth;
    }

    public static float getScreenWidth(Context context)
    {
        float fWidth = 0;

        if (context == null)
            return fWidth;

        try
        {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            fWidth = displayMetrics.widthPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fWidth;
    }

    public static float getScreenHeight(Activity activity)
    {
        float fHeight = 0;

        if (activity == null)
            return fHeight;

        try
        {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            fHeight = metrics.heightPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fHeight;
    }

    public static float getScreenHeight(Fragment fragment)
    {
        float fHeight = 0;

        if (fragment == null)
            return fHeight;

        try
        {
            DisplayMetrics displayMetrics = fragment.getResources().getDisplayMetrics();
            fHeight = displayMetrics.heightPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fHeight;
    }

    public static float getScreenHeight(Context context)
    {
        float fHeight = 0;

        if (context == null)
            return fHeight;

        try
        {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            fHeight = displayMetrics.heightPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fHeight;
    }

    public static int getViewHeight(View view)
    {
        int iViewHeight = 0;

        if (view == null)
            return iViewHeight;

        try
        {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);

            iViewHeight = view.getMeasuredHeight();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return iViewHeight;
    }

    public static int getViewWidth(View view)
    {
        int iViewWidth = 0;

        if (view == null)
            return iViewWidth;

        try
        {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);

            iViewWidth = view.getMeasuredWidth();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return iViewWidth;
    }
}
