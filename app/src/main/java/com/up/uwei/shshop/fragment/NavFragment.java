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

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;

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

public class NavFragment extends Fragment {
    @BindView(R.id.vp_photo_advice) ViewPager mVpPhoto;

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
        Log.e("Test", "onCreateView");
        View  v = inflater.inflate(R.layout.nav_fragment, container,false);
        mUnbinder = ButterKnife.bind(this, v);
        init();
        return v;
    }

    private void init(){
        mPicFragmentss = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mPicFragmentss.add(PicFragment.getInstance(i));
        }
        LunBoAdapter adapter = new LunBoAdapter(getChildFragmentManager());
        mVpPhoto.setAdapter(adapter);
        startChange();
    }
    private void startChange(){
        mCurrent = mVpPhoto.getCurrentItem();
        /*new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVpPhoto.setCurrentItem(++mCurrent);
                            }
                        });
                        if (mCurrent == 4)
                            mCurrent = 0;
                    }
                },1500,2000);*/
        mObservable = Observable.interval(1500, 1500, TimeUnit.MILLISECONDS)
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
                if(mCurrent == 4)
                    mCurrent = 0;
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
        Log.d(TAG, "onDetach=======");
        mDisposable.dispose();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (mDisposable != null && mDisposable.isDisposed()){
                mObservable.subscribe(mObserver);
            }
            Log.d(TAG, "isVisible");
        }else{
            Log.d(TAG, "unVisible");
            if (mDisposable != null && mDisposable.isDisposed()){
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume======");
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
            //super.destroyItem(container, position, object); 不会销毁fragment
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
