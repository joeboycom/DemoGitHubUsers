package com.example.githubusers.API.ApiResponse;

import com.example.githubusers.Model.UserList;

import java.util.List;

/**
 * Created by joewu on 2018/8/7.
 */

public class GetUserListApiResponse
{
    public String nextPageLink;
    public List<UserList> dataArrayList;
}
