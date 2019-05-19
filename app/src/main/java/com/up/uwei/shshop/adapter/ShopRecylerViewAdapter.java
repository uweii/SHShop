package com.up.uwei.shshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.activity.GoodsDetailActivity;
import com.up.uwei.shshop.activity.RegActivity;
import com.up.uwei.shshop.fragment.ShopFragment;
import com.up.uwei.shshop.pojo.Goods;

import java.util.ArrayList;

import static com.up.uwei.shshop.Configs.GOOD_PARCELABLE_KEY;

public class ShopRecylerViewAdapter extends RecyclerView.Adapter {
    private int FOOTVIEW = 1;
    private int NORMALVIEW = 2;
    private ArrayList<Goods> mGoods = new ArrayList<>();
    private Context mContext;
    private Animation mAnimation ;
    public ShopRecylerViewAdapter(Context context){
        this.mContext = context;
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        DecelerateInterpolator di = new DecelerateInterpolator();
        mAnimation.setInterpolator(di);
    }


    public ArrayList<Goods> getGoods() {
        return mGoods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        mGoods = goods;
    }

    public void cleanGoods() {
        mGoods.clear();
    }

    public void addGoods(ArrayList<Goods> goods){
        mGoods.addAll(goods);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == NORMALVIEW){
            View   v = inflater.inflate(R.layout.item_shop,parent,false);
            return new ShopHolder(v);
        }else {
            View  v = inflater.inflate(R.layout.foot_view, parent, false);
            return new FootHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position +1 == getItemCount()){
            return FOOTVIEW;
        }else {
            return NORMALVIEW;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  ShopHolder){
            ShopHolder hold = (ShopHolder) holder;
            Goods goods = mGoods.get(position);
            String[] imgs =  Configs.getImgs(goods.getImgs());
            String tag = Configs.getTag(goods.getTag());
            Picasso.with(holder.itemView.getContext()).load(Configs.BASE_URL + "/images/" + (imgs == null? "":imgs[0])).placeholder(R.drawable.placeholder).error(R.drawable.load_error).fit().into(((ShopHolder) holder).iv_photo);
            hold.tv_name.setText(goods.getName());
            hold.tv_tag.setText(tag);
            hold.tv_place.setText(goods.getPlace());
            hold.tv_price.setText("￥"+goods.getPrice());
            hold.tv_saleCount.setText(goods.getSalecount() + "人付款");
            hold.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra(Configs.GOOD_PARCELABLE_KEY, goods);
                    mContext.startActivity(intent);

                }
            });
        }else {
            FootHolder h = (FootHolder) holder;
            ShopFragment.CallBack callBack = new ShopFragment.CallBack() {
                @Override
                public void onDismiss() {
                    holder.itemView.setVisibility(View.INVISIBLE);
                    h.mLoading.clearAnimation();
                }

                @Override
                public void onShow() {
                    holder.itemView.setVisibility(View.VISIBLE);
                    h.mLoading.startAnimation(mAnimation);
                }
            };
            ShopFragment.setMyCallBack(callBack);
        }
    }

    @Override
    public int getItemCount() {
        return mGoods.size() + 1;
    }
    //正常商品holder
    class ShopHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_price, tv_place, tv_tag, tv_saleCount;
        ImageView iv_photo;
        public ShopHolder(View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_tag = itemView.findViewById(R.id.tv_tag);
            tv_place = itemView.findViewById(R.id.tv_college);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_saleCount = itemView.findViewById(R.id.tv_saleCount);
           // tv_name = itemView.findViewByI
        }
    }
    //底部加载holder
    class FootHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ImageView mLoading;
        public FootHolder(View itemView) {
            super(itemView);
            mLoading = itemView.findViewById(R.id.iv_loading);
        }
    }
}
