package com.up.uwei.shshop.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.up.uwei.shshop.Configs;

import java.util.ArrayList;
import java.util.Random;
/*
* 自定义textview
* 随机产生彩色背景
* */
public class ColorTextView extends AppCompatTextView {
    private Paint mPaint;
    private Random mRandom;
    public ColorTextView(Context context) {
        this(context, null);
        init(context, null);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public void init(Context context, AttributeSet sttr){
        mPaint = new Paint();
        mRandom = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPaint.setColor(getRamdonColor());
            canvas.drawRoundRect(0,0,getWidth(),getHeight(),10,10,mPaint);
        }
        super.onDraw(canvas);
    }

    private int getRamdonColor(){
        int i = mRandom.nextInt(9);
        return Configs.COLORS.get(i);
    }


}
