package com.up.uwei.shshop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.fragment.NavFragment;
import com.up.uwei.shshop.fragment.PicDetailFragment;
import com.up.uwei.shshop.fragment.PicFragment;
import com.up.uwei.shshop.utils.StatusBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager_goods)
    ViewPager mViewPagerGoods;
    @BindView(R.id.id_flowLayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.ll_dot_container)
    LinearLayout mLlDot;
    private ArrayList<Fragment> mPicFragmentss;
    private ArrayList<ImageView> mDots;
    private ArrayList<String> mTags;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init();
    }

    /*
     * 创建ImageView,轮播图的小点
     * */
    public ImageView makeDot() {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
        params.setMargins(10, 0, 0, 0);
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.bg_dot);
        return imageView;
    }

    public void clearDots() {
        for (int i = 0; i < mDots.size(); i++) {
            mDots.get(i).setSelected(false);
        }
    }

    private void init(){
        mLayoutInflater = getLayoutInflater();
        //设置状态栏透明
        StatusBar.setColor(this, Color.TRANSPARENT);
        mPicFragmentss = new ArrayList<>();
        mDots = new ArrayList<>();
        mTags = new ArrayList<>();
        mTags.add("标志是靠你");
        mTags.add("标志二是");
        mTags.add("标三");
        mTags.add("标志四");
        for (int i = 0; i < 5; i++) {
            mPicFragmentss.add(PicDetailFragment.getInstance(i));
            mDots.add(makeDot());
            mLlDot.addView(mDots.get(mDots.size() - 1));
        }
        mDots.get(0).setSelected(true);
       LunBoAdapter adapter = new LunBoAdapter(getSupportFragmentManager());
        mViewPagerGoods.setAdapter(adapter);
        mTagFlowLayout.setAdapter(new TagAdapter<String>(mTags) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView textView = (TextView) mLayoutInflater.inflate(R.layout.item_goods_tag, mTagFlowLayout, false);
                textView.setText(o);
                return textView;
            }
        });
        mViewPagerGoods.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearDots();
                mDots.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    public class LunBoAdapter extends FragmentPagerAdapter {

        public LunBoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mPicFragmentss.get(position);
        }

        @Override
        public int getCount() {
            return mPicFragmentss.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object); //不会销毁fragment
        }
    }
}
