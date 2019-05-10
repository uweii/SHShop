package com.up.uwei.shshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.utils.LogUtil;

public class RoundImageView extends AppCompatImageView {
    //圆角大小，默认为10
    private int mBorderRadius = 40;
    private Paint mPaint;
    //渲染图像，使用图像为绘制图形着色
    private BitmapShader mBitmapShader;
    public RoundImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    public void init(Context context, AttributeSet attrs){
        //读取配置
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Custom_View);
         mBorderRadius = (int) array.getDimension(R.styleable.Custom_View_radius, 40);
         array.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable() == null){
            return;
        }
        Bitmap bitmap = drawToBitmap(getDrawable());
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
//        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, mPaint);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), mBorderRadius, mBorderRadius, mPaint);

    }
    private Bitmap drawToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        //当设置不为图片，为颜色时，获取的drawable宽高会有问题，所以当为颜色时获取控件的宽高
        int w = getWidth();
        int h = getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
