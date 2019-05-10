package com.up.uwei.shshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;
import com.up.uwei.shshop.adapter.StarRecylerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StarFragment extends BaseFragment {
    private ArrayList<Integer> mImgs;
    private StarRecylerViewAdapter mAdapter;
    private Unbinder mUnbinder;
    private boolean mIfEditting = false;  //标志是否正在编辑货物
    @BindView(R.id.tv_manage) TextView mTvManage;
    @BindView(R.id.iv_star_edit_all) ImageView mIvEditAll;
    @BindView(R.id.tv_pay) TextView mTvPay;
    @BindView(R.id.tv_goods_price) TextView mTvGoodsPrice;
    @BindView(R.id.tv_summary) TextView mTvSummary;
    @BindView(R.id.tv_delete) TextView mTvDelete;
    @BindView(R.id.tv_put_in_star) TextView mTvPutInStar;
    @BindView(R.id.ll_left_container) LinearLayout mLlLeftContainer;
    public static StarFragment newInstance(){
        StarFragment fragment = new StarFragment();
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
            View  v = inflater.inflate(R.layout.star_fragment, container,false);
            mUnbinder = ButterKnife.bind(this, v);
            RecyclerView recyclerView = v.findViewById(R.id.recylerView);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(mAdapter);
            return v;
    }

    @OnClick({R.id.tv_manage, R.id.ll_left_container})
    public void click(View v){
        switch (v.getId()){
            case R.id.tv_manage:
                if(mIfEditting){
                    mTvSummary.setVisibility(View.VISIBLE);
                    mTvGoodsPrice.setVisibility(View.VISIBLE);
                    mTvPay.setVisibility(View.VISIBLE);
                    mTvDelete.setVisibility(View.GONE);
                    mTvPutInStar.setVisibility(View.GONE);
                    mTvManage.setText("管理");
                    mIfEditting = false;
                }else {
                    mTvSummary.setVisibility(View.GONE);
                    mTvGoodsPrice.setVisibility(View.GONE);
                    mTvPay.setVisibility(View.GONE);
                    mTvDelete.setVisibility(View.VISIBLE);
                    mTvPutInStar.setVisibility(View.VISIBLE);
                    mTvManage.setText("完成");
                    mIfEditting = true;
                }
                break;
            case R.id.ll_left_container:

        }
    }

    private void init(){
        mImgs = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
                mImgs.add(R.drawable.demo3);
        }
        mAdapter = new StarRecylerViewAdapter(mImgs);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
