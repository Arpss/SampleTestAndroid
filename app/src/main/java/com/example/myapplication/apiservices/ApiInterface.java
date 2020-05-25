package com.example.myapplication.apiservices;

import com.example.myapplication.model.UserImage;
import com.example.myapplication.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**/
public interface ApiInterface {
// for @GET request

    @GET(Resources.USER_INFO) // specify the sub url for our base url
    Call<List<UserInfo>> getUserInfo();

    @GET(Resources.USER_IMAGES)
    Call<List<UserImage>> getUserImage();


}
