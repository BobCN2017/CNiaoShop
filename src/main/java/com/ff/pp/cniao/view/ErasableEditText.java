package com.ff.pp.cniao.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.ff.pp.myapplication2.R;


/**
 * Created by PP on 2017/4/10.
 */

public class ErasableEditText extends android.support.v7.widget.AppCompatEditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;

    private Drawable mClearDrawable;

    public ErasableEditText(Context context) {
        this(context, null);
    }

    public ErasableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErasableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mClearDrawable = ContextCompat.getDrawable(context, R.drawable.icon_cancel_128);
        mClearDrawable.setBounds(0, 0, (int) (mClearDrawable.getIntrinsicWidth() * 0.3),
                (int) (mClearDrawable.getIntrinsicHeight() * 0.3));

        setBackgroundResource(R.drawable.edittext_background_selector);
        setCompoundDrawablePadding(20);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(text.length() > 0 && hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawable != null && event.getX() >
                    getWidth() - getPaddingRight() - drawable.getIntrinsicWidth()*0.5) {
                setText("");
            }

            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
            this.requestFocus();
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {

        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP],
                visible ? mClearDrawable : null, drawables[DRAWABLE_BOTTOM]);
    }

}
