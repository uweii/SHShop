package com.up.uwei.shshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.up.uwei.shshop.R;

public class RoundTextView extends AppCompatTextView {
    private Paint mPaint;
    private int mRadius;
    public RoundTextView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet set){
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.price_color));
        TypedArray td = context.obtainStyledAttributes(set, R.styleable.Custom_View);
        mRadius = td.getDimensionPixelOffset(R.styleable.Custom_View_text_radis,20);
        td.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        mPaint.setAntiAlias(true); //抗锯齿
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0,0, width, height, mRadius,mRadius,mPaint);
        }
        super.onDraw(canvas);
    }

}
