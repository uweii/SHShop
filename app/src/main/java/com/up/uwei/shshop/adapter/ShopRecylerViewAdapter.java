package com.up.uwei.shshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.activity.GoodsDetailActivity;

import java.util.ArrayList;

public class ShopRecylerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Integer> mList;
    private Context mContext;
    public ShopRecylerViewAdapter(ArrayList<Integer> list, Context context){
        mList = list;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop,parent,false);
        return new ShopHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopHolder hold = (ShopHolder) holder;
        Picasso.with(holder.itemView.getContext()).load(mList.get(position)).fit().into(((ShopHolder) holder).iv_photo);
        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ShopHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_price;
        ImageView iv_photo;
        public ShopHolder(View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
           // tv_name = itemView.findViewByI
        }
    }
}
