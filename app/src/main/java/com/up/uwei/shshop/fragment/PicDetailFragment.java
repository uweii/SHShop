package com.up.uwei.shshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
* 放在viewpager里面，作为轮播图
* */
public class PicDetailFragment extends BaseFragment {
    @BindView(R.id.iv_container) ImageView mImageView;
    private Unbinder mUnbinder;
    private String mImgUrl;
    public static PicDetailFragment getInstance(String url){
        PicDetailFragment fragment = new PicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pic_detail_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        init();
        return v;
    }

    public void init(){
        Picasso.with(getActivity()).load(Configs.BASE_URL + "/images/" + mImgUrl).placeholder(R.drawable.placeholder).error(R.drawable.load_error).fit().into(mImageView);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImgUrl = getArguments().getString("url");
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
