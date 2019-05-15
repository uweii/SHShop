package com.up.uwei.shshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.activity.SearchActivity;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopFragment extends BaseFragment {
    @BindView(R.id.tv_search_hint)
    TextView mSearHint;
    @BindView(R.id.recylerView)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;  //ButterKnife解绑
    private ArrayList<Integer> mImgs;
    private ShopRecylerViewAdapter mAdapter;
    public static ShopFragment newInstance(){
        ShopFragment fragment = new ShopFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View  v = inflater.inflate(R.layout.shop_fragment, container,false);
            mUnbinder = ButterKnife.bind(this, v);
            RecyclerView recyclerView = v.findViewById(R.id.recylerView);
            recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(),2));
            recyclerView.setAdapter(mAdapter);
            return v;
    }

    private void init(){
        mImgs = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
                mImgs.add(R.drawable.demo3);
        }
        mAdapter = new ShopRecylerViewAdapter(mImgs, getActivity());
    }
    @OnClick({R.id.tv_search_hint})
    public void click(View v){
        switch (v.getId()){
            case R.id.tv_search_hint:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();


    }
}
