package com.up.uwei.shshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.activity.SearchActivity;
import com.up.uwei.shshop.event.LunBoEvent;
import com.up.uwei.shshop.utils.LogUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class NavFragment extends BaseFragment {
    @BindView(R.id.vp_photo_advice)
    ViewPager mVpPhoto;
    @BindView(R.id.ll_dot_container)
    LinearLayout mLlDot;
    @BindView(R.id.tv_search_hint)
    TextView mSearchHint;
    private View mFragmentView;
    private static String TAG = "NavFragment";
    private Unbinder mUnbinder;
    private int mCurrent = 0;
    private ArrayList<PicFragment> mPicFragmentss;
    private ArrayList<ImageView> mDots;
    private boolean mLunBoTouch = false; //判断是否认为滑动轮播图
    private Timer t;

    public static NavFragment newInstance() {
        NavFragment fragment = new NavFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.nav_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, mFragmentView);
        init();
        return mFragmentView;
    }

    /*
     * 创建ImageView,轮播图的小点
     * */
    public ImageView makeDot() {
        ImageView imageView = new ImageView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
        params.setMargins(10, 0, 0, 0);
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.bg_dot);
        return imageView;
    }

    private void init() {
        mPicFragmentss = new ArrayList<>();
        mDots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mPicFragmentss.add(PicFragment.getInstance(i));
            mDots.add(makeDot());
            mLlDot.addView(mDots.get(mDots.size() - 1));
        }
        LunBoAdapter adapter = new LunBoAdapter(getChildFragmentManager());
        mVpPhoto.setAdapter(adapter);
        mDots.get(mCurrent).setSelected(true);
        mVpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                mCurrent = position;
//                mDisposable.dispose();
//                mObservable.subscribe(mObserver);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 1){
                    mLunBoTouch = true;
                   EventBus.getDefault().post(new LunBoEvent("stop"));
                }
                if(mLunBoTouch && state == 2){
                    mLunBoTouch = false;
                    mCurrent = mVpPhoto.getCurrentItem();
                    clearDots();
                    mDots.get(mCurrent).setSelected(true);
                   startLunBoEvent();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().post(new LunBoEvent("stop"));
    }

    @OnClick({R.id.tv_search_hint})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tv_search_hint:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
        }
    }

    private void startLunBoEvent() {
        if (t == null)
            t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                EventBus.getDefault().post(new LunBoEvent("start"));
            }
        }, 3000);
    }

    public void clearDots() {
        for (int i = 0; i < mDots.size(); i++) {
            mDots.get(i).setSelected(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        LogUtil.d("=========:" + isVisibleToUser);
        if (isVisibleToUser) {
            startLunBoEvent();
        } else {
            EventBus.getDefault().post(new LunBoEvent("stop"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //LogUtil.d("=========  onResume");
        if(t == null){
            startLunBoEvent();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LuoBoEvent(LunBoEvent event) {
        switch (event.getMessage()) {
            case "start":
//                LogUtil.d("收到start消息");
                mCurrent++;
                if (mCurrent == 5)
                    mCurrent = 0;
                mVpPhoto.setCurrentItem(mCurrent);
                clearDots();
                mDots.get(mCurrent).setSelected(true);
                startLunBoEvent();
                break;
            case "stop":
                if(t != null){
                    t.cancel();
                    t = null;
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
