package com.up.uwei.shshop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.up.uwei.shshop.R;

import java.util.ArrayList;

public class ShopRecylerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Integer> mList;
    public ShopRecylerViewAdapter(ArrayList<Integer> list){
        mList = list;
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
        Picasso.with(holder.itemView.getContext()).load(mList.get(position)).fit().into(((ShopHolder) holder).iv_photo);
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
            iv_photo = itemView.findViewById(R.id.cv_photo);
           // tv_name = itemView.findViewByI
        }
    }
}
