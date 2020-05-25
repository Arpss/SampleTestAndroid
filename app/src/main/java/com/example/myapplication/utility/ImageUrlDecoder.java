package com.example.myapplication.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;

/**
 * For loading Images from Image URl
 */
public class ImageUrlDecoder {


    String url;
    ImageView imageView;

    Context mContext;

    public ImageUrlDecoder( String url, ImageView imageView,Context mContext) {

        this.url = url;
        this.imageView=imageView;
        this.mContext=mContext;
    }

        public void decode(){
            if (url != null) {




                RequestOptions requestOptions=new RequestOptions() .placeholder(R.drawable.ic_launcher_background) //5
                        .error(R.drawable.ic_launcher_background) //6

                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()

                        ;


                Glide.with(mContext)
                        .asDrawable()
                        .load(url)
                        .apply(requestOptions)
                        .thumbnail(0.5f)
                        .onlyRetrieveFromCache(true)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                Log.v("aa","aa");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                imageView.setImageDrawable(resource);
                                return true;
                            }
                        })
                        .into(imageView); //8

            }
        }

    }

