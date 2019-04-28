package com.up.uwei.shshop.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.fragment.NavFragment;
import com.up.uwei.shshop.view.NoSwipeViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  {
    @BindView(R.id.viewPager) NoSwipeViewPager mViewPager;
    @BindView(R.id.iv_nav) ImageView mIvNav;
    @BindView(R.id.tv_nav) TextView mTvNav;
    @BindView(R.id.iv_shop) ImageView mIvShop;
    @BindView(R.id.tv_shop) TextView mTVShop;
    @BindView(R.id.iv_add) ImageView mIvAdd;
    @BindView(R.id.tv_add) TextView mTvAdd;
    @BindView(R.id.iv_advice) ImageView mIvAdvice;
    @BindView(R.id.tv_advice) TextView mTvAdvice;
    @BindView(R.id.iv_mine) ImageView mIvMine;
    @BindView(R.id.tv_mine) TextView mTvMine;
    @BindView(R.id.ll_nav) LinearLayout mLlNav;
    @BindView(R.id.ll_shop) LinearLayout mLlShop;
    @BindView(R.id.ll_add) LinearLayout mLlAdd;
    @BindView(R.id.ll_advice) LinearLayout mLlAdvice;
    @BindView(R.id.ll_mine) LinearLayout mLlMine;
    @BindView(R.id.tv_search_hint) TextView mTvSearchHint;

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTabName;
    private ArrayList<Integer> mTabIcons;
    private int mCurrentTab = 0;   //记录当前tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @OnClick({R.id.ll_nav, R.id.ll_shop, R.id.ll_add,R.id.ll_advice,R.id.ll_mine})
    public void onTabClicked(View v){
        switch (v.getId()){
            case R.id.ll_nav:
                if(mCurrentTab == 0){
                    return;
                }else {
                    clearTab();
                    mCurrentTab = 0;
                    mViewPager.setCurrentItem(0, false);
                    mIvNav.setSelected(true);
                }
                break;
            case R.id.ll_shop:
                if(mCurrentTab == 1){
                    return;
                }else {
                    clearTab();
                    mCurrentTab = 1;
                    mViewPager.setCurrentItem(1, false);
                    mIvShop.setSelected(true);
                }
                break;
            case R.id.ll_add:
                Toast.makeText(this, "发布宝贝", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_advice:
                if(mCurrentTab == 3){
                    return;
                }else {
                    clearTab();
                    mCurrentTab = 3;
                    mViewPager.setCurrentItem(2, false);
                    mIvAdvice.setSelected(true);
                }
                break;
            case R.id.ll_mine:
                if(mCurrentTab == 4){
                    return;
                }else {
                    clearTab();
                    mCurrentTab = 4;
                    mViewPager.setCurrentItem(3, false);
                    mIvMine.setSelected(true);
                }
                break;
            case R.id.tv_search_hint:
                break;
        }
    }

    private void clearTab(){
        mIvShop.setSelected(false);
        mIvNav.setSelected(false);
        mIvAdvice.setSelected(false);
        mIvMine.setSelected(false);
    }

    private void init(){
        //设置状态栏颜色为黑色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mFragments = new ArrayList<>();
        mTabName = new ArrayList<>();
        mTabIcons = new ArrayList<>();
        mTabName.add("导航");
        mTabName.add("商城");
        mTabName.add("发布");
        mTabName.add("推荐");
        mTabName.add("我的");
        mFragments.add(NavFragment.newInstance(1));
        mFragments.add(NavFragment.newInstance(2));
        mFragments.add(NavFragment.newInstance(3));
        mFragments.add(NavFragment.newInstance(4));
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mViewPager.setCanSwipe(false);
        mIvNav.setSelected(true);

    }




    public void disableClipOnParents(View v) {
        if (v.getParent() == null) {
            return; }
        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false); }
        if (v.getParent() instanceof View) {
            disableClipOnParents((View) v.getParent()); }
    }



    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName.get(position);
        }
    }



}

