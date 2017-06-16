package com.ff.pp.cniao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ff.pp.myapplication2.R;

/**
 * Created by PP on 2017/3/29.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private ImageButton mAddButton, mSubButton;

    private TextView mNumber;

    private int mValue, mMinValue, mMaxValue;
    private Context mContext;

    private OnButtonClickListener mListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.mListener = listener;
    }

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(mContext,attrs,defStyleAttr);

    }


    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View rootView = LayoutInflater.from(mContext).
                inflate(R.layout.widget_number_add_sub, this, true);
        mAddButton = (ImageButton) rootView.findViewById(R.id.button_add);
        mSubButton = (ImageButton) rootView.findViewById(R.id.button_sub);
        mNumber = (TextView) rootView.findViewById(R.id.textView_number);

        mAddButton.setOnClickListener(this);
        mSubButton.setOnClickListener(this);
        initDefaultValue();

        TypedArray a=mContext.obtainStyledAttributes(attrs,
                R.styleable.NumberAddSubView,defStyleAttr,0);
        int val=a.getInt(R.styleable.NumberAddSubView_value,1);
        int minval=a.getInt(R.styleable.NumberAddSubView_minvalue,1);
        int maxval=a.getInt(R.styleable.NumberAddSubView_maxvalue,10);

        setValue(val);
        setMinValue(minval);
        setMaxValue(maxval);

        int textColor=a.getColor(R.styleable.NumberAddSubView_number_text_color,0);
        float textSize=a.getDimension(R.styleable.NumberAddSubView_number_text_size, 0);
        Drawable textBackground=a.getDrawable(R.styleable.NumberAddSubView_number_textview_background);
        Drawable addBtnBackground=a.getDrawable(R.styleable.NumberAddSubView_add_button_background);
        Drawable subBtnBackground=a.getDrawable(R.styleable.NumberAddSubView_sub_button_background);

        if(textSize!=0) mNumber.setTextSize(textSize);
        if (textColor!=0) mNumber.setTextColor(textColor);
        if (textBackground!=null) mNumber.setBackground(textBackground);
        if (addBtnBackground!=null) mAddButton.setBackground(addBtnBackground);
        if (subBtnBackground!=null) mSubButton.setBackground(subBtnBackground);
        a.recycle();
    }

    private void initDefaultValue() {
        mValue = 1;
        mMinValue = 1;
        mNumber.setText(mValue + "");
        setButtonEnable();
    }

    private void setButtonEnable() {
        mSubButton.setEnabled(true);
        mAddButton.setEnabled(true);
        if (mValue == mMinValue) {
            mSubButton.setEnabled(false);
        }
        if (mValue == mMaxValue) {
            mAddButton.setEnabled(false);
        }
    }


    public boolean setValues(int currValue, int minValue, int maxValue) {
        if (currValue < 1 || currValue < minValue || currValue > maxValue) {
            return false;
        }
        mValue = currValue;
        mMinValue = minValue;
        mMaxValue = maxValue;
        mNumber.setText(mValue + "");
        setButtonEnable();
        return true;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        this.mValue = value;
        mNumber.setText(mValue + "");
        setButtonEnable();
    }

    public int getMinValue() {
        return mMinValue;

    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
        setButtonEnable();
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
        setButtonEnable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:

                if (numberAdd() && mListener != null) {
                    mListener.onButtonAddClick(v, mValue);
                }

                break;
            case R.id.button_sub:

                if (numberSub() && mListener != null) {
                    mListener.onButtonSubClick(v, mValue);
                }

                break;
        }
    }

    private boolean numberAdd() {
        if (mValue >= mMaxValue) return false;
        mValue++;
        mNumber.setText(mValue + "");
        setButtonEnable();
        return true;
    }

    private boolean numberSub() {
        if (mValue <= mMinValue) return false;
        mValue--;
        mNumber.setText(mValue + "");
        setButtonEnable();
        return true;
    }
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public interface OnButtonClickListener {
        void onButtonAddClick(View view, int value);

        void onButtonSubClick(View view, int value);
    }
}
