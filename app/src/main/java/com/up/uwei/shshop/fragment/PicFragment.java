package com.up.uwei.shshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
* 放在viewpager里面，作为轮播图
* */
public class PicFragment extends BaseFragment {
    @BindView(R.id.iv_container) ImageView mImageView;
    private Unbinder mUnbinder;
    private int type;
    public static PicFragment getInstance(int type){
        PicFragment fragment = new PicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pic_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        init();
        return v;
    }

    public void init(){
        if (type == 0)
            Picasso.with(getActivity()).load(R.drawable.demo).fit().centerCrop().into(mImageView);
        else if(type == 1)
            Picasso.with(getActivity()).load(R.drawable.demo1).fit().centerCrop().into(mImageView);
        else if(type == 2)
            Picasso.with(getActivity()).load(R.drawable.demo2).fit().centerCrop().into(mImageView);
        else if(type == 3)
            Picasso.with(getActivity()).load(R.drawable.demo3).fit().centerCrop().into(mImageView);
        else
            Picasso.with(getActivity()).load(R.drawable.demo2).fit().centerCrop().into(mImageView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
