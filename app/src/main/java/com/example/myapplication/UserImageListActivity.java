package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.UserImageListAdapter;
import com.example.myapplication.globalservicecases.GlobalServiceCases;
import com.example.myapplication.listener.SelectedImageListener;

import com.example.myapplication.model.UserImage;
import com.example.myapplication.presenter.UserImagePresenter;
import com.example.myapplication.presenter.UserImagePresenterImpl;
import com.example.myapplication.utility.NetworkUtility;
import com.example.myapplication.view.UserImageView;

import java.util.ArrayList;
import java.util.List;

public class UserImageListActivity extends AppCompatActivity implements UserImageView, View.OnClickListener {
    private String value;
    private int userId;
    private NetworkUtility networkUtility;

    RecyclerView userImageRecyclerView;
    LinearLayout mProgressLayout;
    LinearLayout mConnectionIssueLayout;
    Button retryBtn;

    UserImagePresenter userImagePresenter;
    List<UserImage> userImageList=new ArrayList<>();
    List<UserImage> albumImageList=new ArrayList<>();
    String albumId;

    UserImageListAdapter userImageListAdapter;
    SelectedImageListener listener;


    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image_list);
        Intent intent=getIntent();
        userId=intent.getExtras().getInt("userId");
        albumId=String.valueOf(userId);
        Log.e("sss",String.valueOf(userId));

        actionBar=getSupportActionBar();
           actionBar.setTitle("AlbumID: "+userId);


        userImageRecyclerView =findViewById(R.id.userImageList);
        mProgressLayout = (LinearLayout) findViewById(R.id.fragment_image_list_progress_layout);
        mConnectionIssueLayout = (LinearLayout)findViewById(R.id.fragment_image_list_connection_issue_layout);
        retryBtn=(Button)findViewById(R.id.common_connection_issue_layout_retry_button);
        retryBtn.setOnClickListener(this);


        userImagePresenter=new UserImagePresenterImpl(this);

        networkUtility=new NetworkUtility(getApplication());
        if(networkUtility.isNetworkAvailable()){
            userImagePresenter.requestImage();
        }else {
            mProgressLayout.setVisibility(View.GONE);
            mConnectionIssueLayout.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onSuccess(List<UserImage> userImageList) {

        this.userImageList=userImageList;
        int i=0;
        int j=0;
        for (i=0;i<userImageList.size();i++){
            if (userImageList.get(i).getAlbumId().equalsIgnoreCase(albumId)){
                albumImageList.add(userImageList.get(i));
                j++;
            }
            Log.e("j",String.valueOf(j));
        }
        if (albumImageList!=null){
            userImageRecyclerView.setVisibility(View.VISIBLE);
            userImageListAdapter = new UserImageListAdapter(this,
                    albumImageList, listener);
            userImageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            userImageRecyclerView.setAdapter(userImageListAdapter);
            userImageListAdapter.notifyDataSetChanged();


        }else{
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

        mProgressLayout.setVisibility(View.GONE);
        mConnectionIssueLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        userImageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_connection_issue_layout_retry_button:
                if(networkUtility.isNetworkAvailable()){
                    mConnectionIssueLayout.setVisibility(View.GONE);
                    userImagePresenter.requestImage();
                }else {
                    mProgressLayout.setVisibility(View.GONE);
                    mConnectionIssueLayout.setVisibility(View.VISIBLE);
                }
        }
    }
}
