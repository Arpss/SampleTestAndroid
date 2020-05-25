package com.example.myapplication.view;

import com.example.myapplication.model.UserInfo;

import java.util.List;

public interface UserInfoView extends BaseView{
     void onUserInfoSuccess(List<UserInfo> userInfoList);

}
