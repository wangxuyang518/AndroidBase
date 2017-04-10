package com.xinyi_tech.commonlibs.widget;

import android.os.Handler;

/**
 * Created by studyjun on 2016/3/10.
 */
public class DoubleClickHandler extends Handler {

    public boolean isExit = false;

    private Runnable exit = new Runnable() {
        @Override
        public void run() {
            isExit = false;
        }
    };

    public DoubleClickHandler() {

    }

    public void exit() {
        isExit = true;
        this.postDelayed(exit, 3000);
    }


    public void cancal() {
        this.removeCallbacks(exit);
    }
}
