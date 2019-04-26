package com.up.uwei.shshop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

public class CenterOutTabLayout extends TabLayout {
    public CenterOutTabLayout(Context context) {
        super(context);
    }

    public CenterOutTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
