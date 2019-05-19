package com.up.uwei.shshop.fragment;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.uwei.shshop.BaseFragment;
import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.activity.SearchActivity;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;
import com.up.uwei.shshop.event.Event;
import com.up.uwei.shshop.pojo.Goods;
import com.up.uwei.shshop.pojo.SearchBean;
import com.up.uwei.shshop.service.RetrofitService;
import com.up.uwei.shshop.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.VISIBLE;

public class ShopFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_search_hint)
    TextView mSearHint;
    @BindView(R.id.recylerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.swipefresh)
    SwipeRefreshLayout mRefreshLayout;

    private boolean mRefresh = false;
    private GridLayoutManager manager;
    private int mLastPosition = 0;  //滑到这里开始加载
//    private int mCurrentOffset = 0;   //从后台查询数据库下标
//    private int mLength = 20;  //每次查询20条
    private Timer mTimer = null;
    private Unbinder mUnbinder;  //ButterKnife解绑
    private ArrayList<Goods> mGoods;
    private ShopRecylerViewAdapter mAdapter;
    private boolean isGettingNow = false;  //标志现在是否在请求数据
    public static ShopFragment newInstance(){
        ShopFragment fragment = new ShopFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
    * 定义footview回调接口
    * 控制footview的显示和隐藏
    * */
    public interface CallBack{
        void onDismiss();
        void onShow();
    }
    private static CallBack myCallBack;
    public static void setMyCallBack(CallBack callBack){
        myCallBack = callBack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        SearchBean searchBean = new SearchBean(0,20,2,null,null);
        getGoods(searchBean);

    }

    private void getGoods(SearchBean searchBean){
        if(isGettingNow){
            return;
        }
        isGettingNow = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        service.getGoods(searchBean.getOffset(), searchBean.getLength(),searchBean.getKey(),
                searchBean.getPlace(),searchBean.getSecondhand())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Goods>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Goods> goods) {
                        if (mRefresh){
                            mAdapter.cleanGoods();
                            mAdapter.notifyDataSetChanged();
                        }
                        LogUtil.d("getGoods============");
                        if(goods.size() > 0){
                            mTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Event event = new Event("add",2);
                                    event.setGoods(goods);
                                    EventBus.getDefault().post(event);
                                    isGettingNow = false;
                                    if (mRefreshLayout.isRefreshing()){
                                        mRefreshLayout.setRefreshing(false);
                                        mRefresh = false;
                                    }
                                }
                            },1000);

                        }

                        isGettingNow = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Event event = new Event("dismiss", 3);
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(event);
                                isGettingNow = false;
                            }
                        },1000);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void setTimer(Event event){
        if (event.getMessage().equals("timer") && event.getTime() == 1){
            SearchBean searchBean = new SearchBean(manager.getItemCount(), 20, 2,null,null);
            getGoods(searchBean);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addGoods(Event event){
        if (event.getMessage().equals("add") && event.getTime() == 2){
            mAdapter.addGoods(event.getGoods());
            mAdapter.notifyDataSetChanged();
        }else if(event.getMessage().equals("dismiss") && event.getTime() == 3){
            myCallBack.onDismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View  v = inflater.inflate(R.layout.shop_fragment, container,false);
            mUnbinder = ButterKnife.bind(this, v);
            mRecyclerView = v.findViewById(R.id.recylerView);
             init();
        manager = new GridLayoutManager(getActivity(), 2);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isFooter = position == mAdapter.getItemCount() - 1;
                    return isFooter ? 2:1;
                }
            });
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            return v;
    }

    private void init(){
        mTimer = new Timer();
        mAdapter = new ShopRecylerViewAdapter(getActivity());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(mLastPosition + 1 == manager.getItemCount()){
                        //加载更多
                        myCallBack.onShow();
                        SearchBean searchBean = new SearchBean(manager.getItemCount(), 20, 2, null, null);
                        getGoods(searchBean);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastPosition = manager.findLastVisibleItemPosition();
                if (dy > 0 && mFab.getVisibility() == VISIBLE) {
                    mFab.hide();
                } else if (dy < 0 && mFab.getVisibility() != VISIBLE) {
                    mFab.show();
                }
            }
        });
    //设置显示刷新时的颜色
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);  //添加监听，重写onFresh()方法
    }
    @OnClick({R.id.tv_search_hint, R.id.fab})
    public void click(View v){
        switch (v.getId()){
            case R.id.tv_search_hint:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.fab:
                mRecyclerView.smoothScrollToPosition(0);  //滑动到第一项
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }

    }

    @Override
    public void onRefresh() {
        mRefresh = true;
        mRefreshLayout.setRefreshing(true);
        SearchBean searchBean = new SearchBean(0,20,2,null,null);
        getGoods(searchBean);
    }
}
