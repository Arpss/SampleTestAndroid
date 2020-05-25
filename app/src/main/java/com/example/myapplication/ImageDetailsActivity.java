package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.myapplication.utility.ImageUrlDecoder;


public class ImageDetailsActivity extends AppCompatActivity {
    private String albumId;
    private String photoId;
    ActionBar actionBar;
    String imageTitle;
    String imageUrl;

    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        Intent intent=getIntent();
        albumId=intent.getExtras().getString("albumId");
        photoId=intent.getExtras().getString("photoId");
        imageTitle=intent.getExtras().getString("imageTitle");
        imageUrl=intent.getExtras().getString("imageURL");


        Log.e("idd",albumId+""+photoId);


        actionBar=getSupportActionBar();
        actionBar.setTitle("AlbumID:"+albumId+"PhotoID"+photoId);

        imageView=(ImageView)findViewById(R.id.imageDetailsImageView);
        textView=(TextView) findViewById(R.id.imageDetailsTextView);

        textView.setText(imageTitle);



         new ImageUrlDecoder(imageUrl,imageView,this).decode();


    }



}
