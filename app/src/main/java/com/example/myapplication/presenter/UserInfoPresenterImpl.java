package com.example.myapplication.presenter;

import android.util.Log;

import com.example.myapplication.apiservices.ApiInterface;
import com.example.myapplication.apiservices.BaseOkHttpBuilder;
import com.example.myapplication.apiservices.BaseRetrofitBuilder;
import com.example.myapplication.globalservicecases.GlobalServiceCases;
import com.example.myapplication.model.UserInfo;
import com.example.myapplication.view.UserInfoView;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInfoPresenterImpl implements UserInfoPresenter, Callback<List<UserInfo>> {
    private UserInfoView userInfoView;
    private ApiInterface service;
    private Call<List<UserInfo>> call;

    public UserInfoPresenterImpl(UserInfoView userInfoView) {
        super();
        this.userInfoView = userInfoView;
    }


    @Override
    public void requestUserInfo() {
        userInfoView.showProgress(GlobalServiceCases.USER_INFO);

        OkHttpClient client = new BaseOkHttpBuilder().build();
        Retrofit retrofit = new BaseRetrofitBuilder().build(client);
        service = retrofit.create(ApiInterface.class);

        call = service.getUserInfo();
        call.enqueue(this);
    }

    @Override
    public void onScreenPause() {
        userInfoView
                .dismissProgress(GlobalServiceCases.USER_INFO);
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
        if (response.isSuccessful()) {
            /**
             * the status of the response is {@link AppConstant.OK_MESSAGE}
             */


            Log.e("a","success");
            userInfoView.dismissProgress(GlobalServiceCases.USER_INFO);
            userInfoView.onUserInfoSuccess(response.body());


            /**
             * the status of the response implies the Auth key fail/extend the auth key session
             */
        }
        /**
         * unsuccessful {@link FavRewardCardsResponse} from API/Service
         */
        else {
            userInfoView.dismissProgress(GlobalServiceCases.USER_INFO);
            Log.e("a","fail");

            userInfoView.onServerNetworkIssue(GlobalServiceCases.USER_INFO,
                   "Server/Network Error"
                    );

        }
    }

    @Override
    public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        userInfoView.dismissProgress(GlobalServiceCases.USER_INFO);
        Log.e("a","eee");

        userInfoView.onServerNetworkIssue(GlobalServiceCases.USER_INFO,
                "Server/Network Error"
        );
    }
}
