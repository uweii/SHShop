package com.up.uwei.shshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.MyApplication;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;
import com.up.uwei.shshop.utils.LogUtil;
import com.up.uwei.shshop.view.MyPagerTransfomer;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;




//遗留bug，viewpager的第一个fragment隐藏后，任然后台在轮播图片






public class NavFragment extends BaseFragment {
    @BindView(R.id.vp_photo_advice) ViewPager mVpPhoto;
    private View mFragmentView;
    private static String TAG = "NavFragment";
    private Unbinder mUnbinder;
    private Disposable mDisposable;
    private Observable<Long> mObservable;
    private Observer mObserver;
    private int mCurrent = 0;
    private ArrayList<PicFragment> mPicFragmentss;
    public static NavFragment newInstance(){
        NavFragment fragment = new NavFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.nav_fragment, container, false);
        ButterKnife.bind(this, mFragmentView);
        init();
        return mFragmentView;
    }

    private void init(){
        mPicFragmentss = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mPicFragmentss.add(PicFragment.getInstance(i));
        }
        LunBoAdapter adapter = new LunBoAdapter(getChildFragmentManager());
        mVpPhoto.setAdapter(adapter);
        mVpPhoto.setPageTransformer(true, new MyPagerTransfomer());
        startChange();
        mVpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrent = position;
                mDisposable.dispose();
                mObservable.subscribe(mObserver);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(null != mDisposable ){
            if (mDisposable.isDisposed())
                return;
            mDisposable.dispose();
        }
    }

    private void startChange(){
        mCurrent = mVpPhoto.getCurrentItem();
        mObservable = Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        mObserver = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "i am running");
                mVpPhoto.setCurrentItem(++mCurrent);
                if(mCurrent == 5)
                    mCurrent = -1;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mObservable.subscribe(mObserver);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d("Visiblity:  =======> " + isVisibleToUser );
        if(isVisibleToUser){
            if (null != mObservable)
                mObservable.subscribe(mObserver);
        }else {
            if(null != mDisposable ){
                if (mDisposable.isDisposed())
                    return;
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume======");
        if(mDisposable.isDisposed()){
            mObservable.subscribe(mObserver);
        }
    }

    class LunBoAdapter extends FragmentPagerAdapter{

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
    }
}
