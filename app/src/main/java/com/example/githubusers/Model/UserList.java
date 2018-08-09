package com.example.githubusers.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joewu on 2018/8/7.
 */

public class UserList implements Parcelable
{
    private String login;
    private String avatar_url;
    private boolean site_admin;

    public String getLogin()
    {
        return login;
    }

    public String getAvatarUrl()
    {
        return avatar_url;
    }

    public boolean isSiteAdmin()
    {
        return site_admin;
    }

    public int current_page;

    public UserList()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.login);
        dest.writeString(this.avatar_url);
        dest.writeValue(this.site_admin);
        dest.writeInt(this.current_page);
    }

    protected UserList(Parcel in)
    {
        this.login = in.readString();
        this.avatar_url = in.readString();
        this.site_admin = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.current_page = in.readInt();
    }

    public static final Creator<UserList> CREATOR = new Creator<UserList>()
    {
        @Override
        public UserList createFromParcel(Parcel source)
        {
            return new UserList(source);
        }

        @Override
        public UserList[] newArray(int size)
        {
            return new UserList[size];
        }
    };
}
