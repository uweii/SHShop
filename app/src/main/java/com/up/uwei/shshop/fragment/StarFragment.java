package com.up.uwei.shshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.adapter.ShopRecylerViewAdapter;

import java.util.ArrayList;

public class StarFragment extends Fragment {
    private ArrayList<Integer> mImgs;
    private ShopRecylerViewAdapter mAdapter;
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
            View  v = inflater.inflate(R.layout.navfragment, container,false);
            RecyclerView recyclerView = v.findViewById(R.id.recylerView);
            recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(),3));
            recyclerView.setAdapter(mAdapter);
        return v;
    }

    private void init(){
        mImgs = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
                mImgs.add(R.drawable.demo3);
        }
        mAdapter = new ShopRecylerViewAdapter(mImgs);
    }
}
