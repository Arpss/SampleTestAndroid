package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UserImageListActivity;
import com.example.myapplication.listener.SelectedUserListener;
import com.example.myapplication.model.UserInfo;
import com.example.myapplication.utility.NetworkUtility;
import com.example.myapplication.validator.PhoneValidator;

import java.util.List;

public class UserInfoRecycleViewAdapter extends RecyclerView.Adapter<UserInfoRecycleViewAdapter.ViewHolder> {
    Context mContext;
    List<UserInfo> userInfoList;
    LayoutInflater inflater;
    SelectedUserListener listener;
    public UserInfoRecycleViewAdapter(Context context, List<UserInfo> userInfoList, SelectedUserListener listener) {
        this.mContext=context;
        this.userInfoList=userInfoList;
        this.inflater=LayoutInflater.from(mContext);
        this.listener=listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_view_user_info,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view, new SelectedUserListener() {
            @Override
            public void onItemClick(int position) {
               Intent intent=new Intent(mContext, UserImageListActivity.class);
               intent.putExtra("userId",userInfoList.get(position).getId());
               mContext.startActivity(intent);

            }
        });
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(userInfoList.get(position));

        UserInfo user = userInfoList.get(position);

        holder.mUserId.setText(String.valueOf(user.getId()));
        holder.mUserName.setText("Name:"+user.getName());
        holder.mUserEmail.setText("Email:"+user.getEmail());
        holder.mUserPhone.setText("Phone:"+ new PhoneValidator().phoneValidator(user.getPhone()));

    }

    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(userInfoList.size()));
        return userInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        SelectedUserListener listener;
        TextView mUserId;
        TextView mUserName;
        TextView mUserEmail;
        TextView mUserPhone;

        public ViewHolder(@NonNull View itemView,SelectedUserListener listener) {
            super(itemView);

            this.listener=listener;
            mUserId = (TextView) itemView.findViewById(R.id.card_view_user_info_id);
            mUserName = (TextView) itemView.findViewById(R.id.card_view_user_info_name);
            mUserEmail = (TextView) itemView.findViewById(R.id.card_view_user_info_email);
            mUserPhone = (TextView) itemView.findViewById(R.id.card_view_user_info_phone);
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
