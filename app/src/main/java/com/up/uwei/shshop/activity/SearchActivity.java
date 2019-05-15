package com.up.uwei.shshop.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.utils.InputMethodUtils;
import com.up.uwei.shshop.utils.StatusBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tab_search)
    TabLayout mTabSearch;
    @BindView(R.id.id_flowLayout)
    TagFlowLayout mFlowLayout;
    private ArrayList<String> mAllTips;
    private ArrayList<String> mSecondTips;
    private ArrayList<String> mPlaceTips;
    private LayoutInflater mLayoutInflater;
    private int mTabTag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }


    public void init() {
        //设置状态栏颜色
        mLayoutInflater = LayoutInflater.from(this);
        StatusBar.setColor(this, getResources().getColor(R.color.bg_search));
        //setIndicator(mTabSearch, 40,40);  //设置TabLayout指示器宽度
        mAllTips = new ArrayList<>();
        mSecondTips = new ArrayList<>();
        mPlaceTips = new ArrayList<>();
        mAllTips.add("阔腿裤男");
        mAllTips.add("计算机别业生成品");
        mAllTips.add("固态硬盘台式机1t");
        mAllTips.add("鼠标");
        mAllTips.add("笔记本键盘改装加灯");
        mAllTips.add("笔记本键盘背光灯加装");
        mAllTips.add("键盘加灯");
        mAllTips.add("背光键盘笔记本电脑");
        mAllTips.add("背光键盘");
        mAllTips.add("SSD500g");
        mSecondTips.add("二手手机");
        mSecondTips.add("二手笔记本");
        mSecondTips.add("二手篮球");
        mSecondTips.add("二手羽毛球");
        mSecondTips.add("二手桌子椅子");
        mSecondTips.add("二手球拍乒乓球");
        mSecondTips.add("二手女朋友");
        mPlaceTips.add("数学与计算机学院");
        mPlaceTips.add("外国语学院");
        mPlaceTips.add("艺术与传媒学院");
        mPlaceTips.add("经济与管理学院");
        mPlaceTips.add("生工院");
        mPlaceTips.add("化环学院");
        mFlowLayout.setAdapter(new TagAdapter<String>(mAllTips) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.item_tips, mFlowLayout, false);
                tv.setText(o);
                return tv;
            }
        });



        //设置FlowLayout标签点击响应逻辑
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(mTabTag == 0)
                    Toast.makeText(SearchActivity.this, mAllTips.get(position), Toast.LENGTH_SHORT).show();
                else if(mTabTag == 1)
                    Toast.makeText(SearchActivity.this, mSecondTips.get(position), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SearchActivity.this, mPlaceTips.get(position), Toast.LENGTH_SHORT).show();
                if(InputMethodUtils.isShowing(SearchActivity.this)){
                    InputMethodUtils.showOrHide(SearchActivity.this, getCurrentFocus());
                }
                return false;
            }
        });
        //设置Tablayout item点击响应
        mTabSearch.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(mTabSearch.getTabAt(0) == tab){
                    //选中”全部“
                    mTabTag = 0;
                    mFlowLayout.setAdapter(new TagAdapter<String>(mAllTips) {
                        @Override
                        public View getView(FlowLayout parent, int position, String o) {
                            TextView tv = (TextView) mLayoutInflater.inflate(R.layout.item_tips, mFlowLayout, false);
                            tv.setText(o);
                            return tv;
                        }
                    });

                }else if(mTabSearch.getTabAt(1) == tab){
                    mTabTag=1;
                    mFlowLayout.setAdapter(new TagAdapter<String>(mSecondTips) {
                        @Override
                        public View getView(FlowLayout parent, int position, String o) {
                            TextView tv = (TextView) mLayoutInflater.inflate(R.layout.item_tips, mFlowLayout, false);
                            tv.setText(o);
                            return tv;
                        }
                    });
                }else {
                    mTabTag=2;
                    mFlowLayout.setAdapter(new TagAdapter<String>(mPlaceTips) {
                        @Override
                        public View getView(FlowLayout parent, int position, String o) {
                            TextView tv = (TextView) mLayoutInflater.inflate(R.layout.item_tips, mFlowLayout, false);
                            tv.setText(o);
                            return tv;
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            boolean isTouchOutOfEditText = event.getY() > mEtSearch.getY();
            if(isTouchOutOfEditText){
                mEtSearch.clearFocus();
                //隐藏输入法
                if(InputMethodUtils.isShowing(this)){
                    InputMethodUtils.showOrHide(this, getCurrentFocus());
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_back, R.id.et_search})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    //设置TabLayout的指示器宽度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
