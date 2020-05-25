package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.adapter.UserInfoRecycleViewAdapter;
import com.example.myapplication.globalservicecases.GlobalServiceCases;
import com.example.myapplication.listener.SelectedUserListener;
import com.example.myapplication.model.UserInfo;
import com.example.myapplication.presenter.UserInfoPresenter;
import com.example.myapplication.presenter.UserInfoPresenterImpl;
import com.example.myapplication.utility.NetworkUtility;
import com.example.myapplication.view.UserInfoView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserInfoView, View.OnClickListener {
    private RecyclerView userInfoRecyclerView;
    private LinearLayout mProgressLayout;
    private LinearLayout mConnectionIssueLayout;
    private NetworkUtility networkUtil;
    private UserInfoPresenter userInfoPresenter;
    private List<UserInfo>  userInfoList=new ArrayList<>();
    private UserInfoRecycleViewAdapter userInfoRecyclerViewAdapter;
    SelectedUserListener listener;
    Button retryBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfoRecyclerView =findViewById(R.id.userInfoList);
        mProgressLayout = (LinearLayout) findViewById(R.id.fragment_user_info_progress_layout);
        mConnectionIssueLayout = (LinearLayout)findViewById(R.id.fragment_user_info_connection_issue_layout);
        retryBtn=(Button)findViewById(R.id.common_connection_issue_layout_retry_button);
        retryBtn.setOnClickListener(this);

        networkUtil = new NetworkUtility(getApplication());
        userInfoPresenter=new UserInfoPresenterImpl(this);


        Log.d("aa","aa");
        /*
        * if network is available request data from API
        */
        if (networkUtil.isNetworkAvailable()){
            userInfoPresenter.requestUserInfo();
        }else {
            mProgressLayout.setVisibility(View.GONE);
            mConnectionIssueLayout.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void showProgress(GlobalServiceCases globalServiceCases) {
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress(GlobalServiceCases globalServiceCases) {
        mProgressLayout.setVisibility(View.GONE);

    }

    @Override
    public void onServerNetworkIssue(GlobalServiceCases globalServiceCases, String message) {
        mConnectionIssueLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onUserInfoSuccess(List<UserInfo> userInfoList) {

        this.userInfoList=userInfoList;
        userInfoRecyclerView.setVisibility(View.VISIBLE);
        userInfoRecyclerViewAdapter = new UserInfoRecycleViewAdapter(this,
                userInfoList, listener);
        userInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userInfoRecyclerView.setAdapter(userInfoRecyclerViewAdapter);
        userInfoRecyclerViewAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_connection_issue_layout_retry_button:

                if (networkUtil.isNetworkAvailable()){
                    mConnectionIssueLayout.setVisibility(View.GONE);
                    userInfoPresenter.requestUserInfo();
                }else {
                    mProgressLayout.setVisibility(View.GONE);
                    mConnectionIssueLayout.setVisibility(View.VISIBLE);
                }
        }


    }
}
