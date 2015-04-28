package com.github.sanshi0518.androidlights.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.github.sanshi0518.androidlights.R;

/**
 * 带清除功能的EditText.
 *
 * @author wanglei
 * @since 15/4/28 下午4:11
 */
public class ClearableEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    private boolean mHasFoucs;

    private TextChangedListener mTextChangedListener;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能在XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 获取EditText的DrawableRight
        mClearDrawable = getCompoundDrawables()[2];

        // 假如没有设置就使用默认的图片
        if (mClearDrawable == null)
            mClearDrawable = getResources().getDrawable(R.drawable.img_delete);

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        // 默认设置隐藏图标
        setClearIconVisible(false);

        // 设置焦点改变的监听
        setOnFocusChangeListener(this);

        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去。
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;

        // 左上右下
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件。
     * 当我们按下的位置 在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 和
     * EditText的宽度 - 图标到控件右边的间距之间，我们就算点击了图标，竖直方向就没有考虑。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean clearTouched = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (clearTouched) this.setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearableEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏。
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.mHasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mTextChangedListener != null)
            mTextChangedListener.afterTextChanged(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (mHasFoucs)
            setClearIconVisible(text.length() > 0);
    }

    public void setTextChangeListener(TextChangedListener textChangeListener) {
        mTextChangedListener = textChangeListener;
    }

    public interface TextChangedListener {
        void afterTextChanged(String text);
    }
    
}
