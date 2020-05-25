package com.example.myapplication.view;

import com.example.myapplication.model.UserImage;

import java.util.List;

public interface UserImageView extends BaseView {
    void onSuccess(List<UserImage> userImage);
}
