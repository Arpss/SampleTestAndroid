package com.example.myapplication.presenter;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.apiservices.ApiInterface;
import com.example.myapplication.apiservices.BaseOkHttpBuilder;
import com.example.myapplication.apiservices.BaseRetrofitBuilder;
import com.example.myapplication.globalservicecases.GlobalServiceCases;
import com.example.myapplication.model.UserImage;
import com.example.myapplication.view.UserImageView;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserImagePresenterImpl implements UserImagePresenter, Callback<List<UserImage>> {
    UserImageView userImageView;
    ApiInterface service;
    Call<List<UserImage>> call;

    public UserImagePresenterImpl( UserImageView userImageView) {
        this.userImageView=userImageView;
    }

    @Override
    public void requestImage() {
        userImageView.showProgress(GlobalServiceCases.USER_IMAGES);
        OkHttpClient client = new BaseOkHttpBuilder().build();
        Retrofit retrofit = new BaseRetrofitBuilder().build(client);
        service = retrofit.create(ApiInterface.class);

        call = service.getUserImage();
        call.enqueue(this);


    }

    @Override
    public void onScreenPause() {

        userImageView
                .dismissProgress(GlobalServiceCases.USER_IMAGES);
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onResponse(Call<List<UserImage>> call, Response<List<UserImage>> response) {
        if (response.isSuccessful()) {
            /**
             * the status of the response is {@link AppConstant.OK_MESSAGE}
             */


            Log.e("a","success");
            userImageView.dismissProgress(GlobalServiceCases.USER_IMAGES);
            userImageView.onSuccess(response.body());


            /**
             * the status of the response implies the Auth key fail/extend the auth key session
             */
        }
        /**
         * unsuccessful {@link FavRewardCardsResponse} from API/Service
         */
        else {
            userImageView.dismissProgress(GlobalServiceCases.USER_IMAGES);
            Log.e("a","fail");

            userImageView.onServerNetworkIssue(GlobalServiceCases.USER_IMAGES,
                    "Server/Network Error"
            );

        }

    }

    @Override
    public void onFailure(Call<List<UserImage>> call, Throwable t) {
        userImageView.dismissProgress(GlobalServiceCases.USER_IMAGES);
        Log.e("a","fail");

        userImageView.onServerNetworkIssue(GlobalServiceCases.USER_IMAGES,
                "Server/Network Error"
        );

    }
}
