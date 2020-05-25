package com.example.myapplication.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.ImageDetailsActivity;
import com.example.myapplication.R;

import com.example.myapplication.listener.SelectedImageListener;

import com.example.myapplication.model.UserImage;
import com.example.myapplication.utility.ImageUrlDecoder;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

public class UserImageListAdapter extends RecyclerView.Adapter<UserImageListAdapter.ViewHolder> {
    Context mContext;
    List<UserImage> userImageList;
    SelectedImageListener listener;
    LayoutInflater inflater;





    public UserImageListAdapter(Context mContext, List<UserImage> userImageList, SelectedImageListener listener) {
        this.mContext=mContext;
        this.userImageList=userImageList;
        this.listener=listener;
        this.inflater=LayoutInflater.from(mContext);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_user_image,
                parent, false);
        ViewHolder viewHolder=new ViewHolder(view, new SelectedImageListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(mContext, ImageDetailsActivity.class);
                intent.putExtra("albumId",userImageList.get(position).getAlbumId());
                intent.putExtra("photoId",userImageList.get(position).getId());
                intent.putExtra("imageTitle",userImageList.get(position).getTitle());
                intent.putExtra("imageURL",userImageList.get(position).getThumbnailUrl());

                mContext.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(userImageList.get(position));


          new ImageUrlDecoder(userImageList.get(position).getThumbnailUrl(),holder.mImageView,holder.itemView.getContext()).decode();
             holder.mImageTitle.setText(userImageList.get(position).getTitle());
















    }


    @Override
    public int getItemCount() {
        return userImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        SelectedImageListener listener;
        TextView mAlbumId;
        ImageView mImageView;
        TextView mImageTitle;


        public ViewHolder(@NonNull View itemView,SelectedImageListener listener) {
            super(itemView);

            this.listener=listener;

            mImageView = (ImageView) itemView.findViewById(R.id.card_view_album_image);
            mImageTitle = (TextView) itemView.findViewById(R.id.card_view_user_album_name);
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
