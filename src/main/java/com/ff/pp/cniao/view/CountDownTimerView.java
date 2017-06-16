package com.ff.pp.cniao.view;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.ff.pp.myapplication2.R;

/**
 * Created by PP on 2017/4/13.
 */

public class CountDownTimerView extends CountDownTimer {

    private static final int TOTAL_COUNT=61000;
    private TextView messageView;
    private int endStringId;


    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerView(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CountDownTimerView(TextView messageView) {
        super(TOTAL_COUNT  , 1000);
        this.messageView=messageView;
        this.endStringId=R.string.getVerificationCodeAgain;
    }

    public CountDownTimerView(TextView messageView,int endStringId) {
        super(TOTAL_COUNT  , 1000);
        this.messageView=messageView;
        this.endStringId=endStringId;
    }

    public CountDownTimerView(long millisInFuture, long countDownInterval
            ,TextView messageView,int endStringId) {
        super(millisInFuture, countDownInterval);
        this.messageView=messageView;
        this.endStringId=endStringId;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        messageView.setEnabled(false);
        messageView.setText((int)millisUntilFinished/1000+" 秒后重新获取验证码");
    }

    @Override
    public void onFinish() {
        messageView.setEnabled(true);
        messageView.setText(endStringId);
    }
}
