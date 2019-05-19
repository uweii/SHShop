package com.up.uwei.shshop.activity;

import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.fragment.NavFragment;
import com.up.uwei.shshop.fragment.PicDetailFragment;
import com.up.uwei.shshop.fragment.PicFragment;
import com.up.uwei.shshop.pojo.Goods;
import com.up.uwei.shshop.utils.LogUtil;
import com.up.uwei.shshop.utils.StatusBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends AppCompatActivity{
    @BindView(R.id.viewPager_goods)
    ViewPager mViewPagerGoods;
    @BindView(R.id.id_flowLayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.ll_dot_container)
    LinearLayout mLlDot;
    @BindView(R.id.ll_collect)
    LinearLayout mLlCollect;
    @BindView(R.id.tv_add_car)
    TextView mTvAddToCar;
    @BindView(R.id.tv_buy)
    TextView mTvBuy;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_express_price)
    TextView mTvExpress;
    @BindView(R.id.tv_sale_count)
    TextView mTvSaleCount;
    @BindView(R.id.tv_bonus_desc)
    TextView mTvBonus;
    @BindView(R.id.rl_bonus)
    RelativeLayout mRlBonus;
    @BindView(R.id.tv_promise_desc)
    TextView mTvPromise;
    @BindView(R.id.rl_promise)
    RelativeLayout mRlPromise;
    @BindView(R.id.tv_description)
    TextView mTvDescription;

    private ArrayList<Fragment> mPicFragmentss;
    private ArrayList<ImageView> mDots;
    private ArrayList<String> mTags;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mImgs;
    private Goods mGoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mGoods = intent.getParcelableExtra(Configs.GOOD_PARCELABLE_KEY);
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
        mImgs = new ArrayList<>();
        mDots = new ArrayList<>();  //小圆点根据图片个数改变
        //图片地址
        mImgs = new ArrayList<>();
        LogUtil.d(mGoods.getImgs());
        LogUtil.d("text : " + mGoods.getName());
        mImgs.addAll(Arrays.asList(Configs.getImgs(mGoods.getImgs())));
        //标签
        mTags = new ArrayList<>();
        mTags.addAll(Configs.getListTags(mGoods.getTag()));

        for (int i = 0; i < mImgs.size(); i++) {
            mPicFragmentss.add(PicDetailFragment.getInstance(mImgs.get(i)));
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
        mTvName.setText(mGoods.getName());
        mTvPrice.setText("￥" + mGoods.getPrice());
        mTvPlace.setText(mGoods.getPlace());
        mTvExpress.setText(mGoods.getExpress());
        mTvSaleCount.setText("销量" + mGoods.getSalecount());
        mTvBonus.setText("购买可获得" + mGoods.getBonus() + "积分");
        mTvPromise.setText(mGoods.getPromise());
        mTvDescription.setText(mGoods.getDesc());

    }
        @OnClick({R.id.rl_bonus, R.id.rl_promise, R.id.fab, R.id.ll_collect, R.id.tv_add_car, R.id.tv_buy})
        public void click(View v){
            switch (v.getId()){
                case R.id.rl_bonus:
                    break;
                case R.id.rl_promise:
                    break;
                case R.id.fab:
                case R.id.ll_collect:
                    break;
                case R.id.tv_add_car:
                    break;
                case R.id.tv_buy:
                    break;
            }
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
