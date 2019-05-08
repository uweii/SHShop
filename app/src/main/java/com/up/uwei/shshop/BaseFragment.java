package com.up.uwei.shshop;

import android.support.v4.app.Fragment;

import com.up.uwei.shshop.utils.LogUtil;

public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("BaseFragment");
        Configs.REF_WATCHER.watch(this);
    }
}
