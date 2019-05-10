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

import io.reactivex.Scheduler;

public class StarRecylerViewAdapter extends RecyclerView.Adapter {
    private boolean isCheck = false;
    private ArrayList<Integer> mList;
    public StarRecylerViewAdapter(ArrayList<Integer> list){
        mList = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_star,parent,false);
        return new ShopHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ShopHolder sh = (ShopHolder) holder;
        ((ShopHolder) holder).iv_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheck){
                    sh.iv_checkBox.setSelected(false);
                    isCheck = false;
                }else {
                    sh.iv_checkBox.setSelected(true);
                    isCheck = true;
                }
            }
        });
        //Picasso.with(holder.itemView.getContext()).load(mList.get(position)).fit().into(((ShopHolder) holder).iv_photo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ShopHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_price, tv_desc;
        ImageView iv_goods, iv_checkBox;
        public ShopHolder(View itemView) {
            super(itemView);
            iv_goods = itemView.findViewById(R.id.iv_goods);
            iv_checkBox = itemView.findViewById(R.id.iv_uncheck);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
