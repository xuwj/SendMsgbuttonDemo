package com.xwj.sendmsgbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

/**
 * 自定义消息发送按钮
 * Created by user on 2015/5/21.
 */
public class SendMsgButton extends ViewAnimator implements View.OnClickListener {
    public static final int STATE_SEND = 0;  // 发送
    public static final int STATE_DONE = 1; // 发送成功

    public static final int RESET_STATE_DELAY_MILLIS = 1200;
    private int mCurrentState;
    private OnSendClickListener mOnSendClickListener;

    private Runnable revertStateRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentState(STATE_SEND);  // 状态变为初始状态
        }
    };

    public SendMsgButton(Context context) {
        super(context);
        initView(context);
    }

    public SendMsgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_send_comment_button, this, true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mCurrentState = STATE_SEND;
        super.setOnClickListener(this);
    }

    /**
     * 当按钮从当前界面脱离时（即activity结束）,按钮恢复初始状态
     */
    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(revertStateRunnable);
        super.onDetachedFromWindow();
    }

    /**
     * 设置当前的状态
     *
     * @param state    0 - 发送  1 - 发送完成
     */
    public void setCurrentState(int state) {
        if (state == mCurrentState) {
            return;
        }
        mCurrentState = state;
        switch (state) {
            case STATE_DONE:
                setEnabled(false);
                // 2s后变成发送状态
                postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILLIS);
                setInAnimation(getContext(), R.anim.slide_in_done);
                setOutAnimation(getContext(), R.anim.slide_out_send);
                break;
            case STATE_SEND:
                setEnabled(true);
                setInAnimation(getContext(), R.anim.slide_in_send);
                setOutAnimation(getContext(), R.anim.slide_out_done);
                break;
        }
        //显示下一个界面
        showNext();
    }

    @Override
    public void onClick(View v) {
        if (mOnSendClickListener != null) {
            mOnSendClickListener.onSendClickListener(this);
        }
    }

    public void setOnSendClickListener(OnSendClickListener onSendClickListener) {
        this.mOnSendClickListener = onSendClickListener;
    }

    public interface OnSendClickListener {
        public void onSendClickListener(View v);
    }
}
