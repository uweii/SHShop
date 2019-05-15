package com.up.uwei.shshop.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.up.uwei.shshop.R;
import com.up.uwei.shshop.utils.LogUtil;
/*
* 自定义EditText，有文字时，右边显示删除按钮
* 没有文字时，隐藏删除按钮
* */
public class ClearTextEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private boolean hasFocus; //控件是否有焦点
    public ClearTextEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearTextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearTextEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attr){
        mClearDrawable = getCompoundDrawables()[2];
        if(mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.del_icon);
        }
        mClearDrawable.setBounds(0,0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        this.hasFocus = b;
        LogUtil.d("hasFocus: " + b);
        if (hasFocus){
            //焦点存在，而且有输入值，显示
            setDrawableRightVisible(getText().length() > 0);
        }else {
            //没有焦点，不显示
            setDrawableRightVisible(false);
            clearFocus();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(getCompoundDrawables()[2] != null){
                boolean isTouchRight = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                //LogUtil.d("isTouchRight： " + isTouchRight);
                if(isTouchRight){
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setDrawableRightVisible(boolean visible){
        Drawable drawableRight = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1], drawableRight, getCompoundDrawables()[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(mOnTextChangedListener != null){
            mOnTextChangedListener.beforeTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(hasFocus){
            setDrawableRightVisible(charSequence.length() > 0);
        }
        if(mOnTextChangedListener != null){
            mOnTextChangedListener.onTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(mOnTextChangedListener != null){
            mOnTextChangedListener.afterTextChanged(editable);
        }
    }
    //文本改变接口监听
    public interface OnTextChangedListener{
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);

        void afterTextChanged(Editable s);
    }
    private OnTextChangedListener mOnTextChangedListener;
    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener){
        this.mOnTextChangedListener = onTextChangedListener;
    }

}
