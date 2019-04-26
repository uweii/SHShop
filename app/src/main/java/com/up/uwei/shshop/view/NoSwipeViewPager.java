package com.up.uwei.shshop.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSwipeViewPager extends ViewPager {
    private boolean canSwipe = true;
    public NoSwipeViewPager(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }
    public void setCanSwipe(boolean canSwipe)
    {
        this.canSwipe = canSwipe;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSwipe && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe && super.onInterceptTouchEvent(ev);
    }
}
