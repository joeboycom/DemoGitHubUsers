package com.example.githubusers;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.githubusers.Fragment.UserListFragment;

import java.util.Stack;

/**
 * Created by joewu on 2018/8/7.
 */

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Stack<String> mStackTitleBar = new Stack<String>();    //ActionBar Title stack

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mContainerFrameLayout, new UserListFragment()).commitAllowingStateLoss();
    }

//==================================================================================================
// Fragment Control
//==================================================================================================
    public void replaceFragment(Fragment fragment, boolean boAddToBackStack, String actionBarTitle)
    {
        Log.e(TAG, "replaceFragment : " + fragment.getClass().getSimpleName());
        closeKeyBoard(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.mContainerFrameLayout, fragment);

        if (boAddToBackStack)
        {
            fragmentTransaction.addToBackStack(null);
            mStackTitleBar.push(actionBarTitle);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public void closeKeyBoard(Context context)
    {
        try
        {
            InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
            if (imm != null && getCurrentFocus() != null)
            {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void hideActionBar()
    {
        getSupportActionBar().hide();
    }

    public void showActionBar()
    {
        getSupportActionBar().show();
    }

    @Override
    public void onBackPressed()
    {
        Log.e(TAG, "onBackPressed");
        closeKeyBoard(this);

        if (!popBackStackFragment())
        {
            showFinishDialog();
        }
    }

    private boolean popBackStackFragment()
    {
        Log.e(TAG, "BackStackEntryCount: " + getSupportFragmentManager().getBackStackEntryCount());

        String title = "";
        boolean boHasFragment = hasFragment();

        if (boHasFragment) // There have more than one fragment in BackStack
        {
            if (!mStackTitleBar.isEmpty())
            {
                title = mStackTitleBar.pop();                 // pop previous Fragment's title
            }

            Log.e(TAG, "popFragment title: " + title);

            boolean popFragment = getSupportFragmentManager().popBackStackImmediate(); // pop previous Fragment

            Log.e(TAG, "popFragment " + String.valueOf(popFragment));

            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            {
                mStackTitleBar.clear();
            }
        }
        return boHasFragment;
    }

    private boolean hasFragment()
    {
        return getSupportFragmentManager().getBackStackEntryCount() > 0;
    }

    private void showFinishDialog()
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_exit_app, (ViewGroup) null);

        final Dialog dialog = new Dialog(this, R.style.AlertDialogFadeAnim);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView mPositiveTextView = (TextView) dialog.getWindow().findViewById(R.id.mPositiveTextView);
        mPositiveTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();

                killBackgroundProcess();
                finish();
            }
        });

        TextView mNegativeTextView = (TextView) dialog.getWindow().findViewById(R.id.mNegativeTextView);
        mNegativeTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void killBackgroundProcess()
    {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        mActivityManager.killBackgroundProcesses(getPackageName());
    }
}
