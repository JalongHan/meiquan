package com.haoqu.meiquan.tools;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

/**
 * 获取验证码的倒计时功能60秒
 * Created by apple on 16/7/2.
 */
public class CountDown {

    private CountDownTimer timer;



    public void countDownTime(final Button button){
        timer = new CountDownTimer(60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setEnabled(false);
                Log.i("onTick", "" + millisUntilFinished);
                button.setText(String.valueOf((int) (millisUntilFinished / 1000)) + "秒");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("获取验证码");
            }
        };
        timer.start();
    }

    public CountDownTimer getTimer() {

            return timer;

    }
}
