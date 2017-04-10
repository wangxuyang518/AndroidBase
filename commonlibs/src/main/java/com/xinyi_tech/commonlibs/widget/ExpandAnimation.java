package com.xinyi_tech.commonlibs.widget;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by studyjun on 2016/6/18.
 */
public class ExpandAnimation extends Animation {

    private  int mStartWidth;
    private  int mDeltaWidth;


    private View mView;


    public ExpandAnimation(View view, int startWidth, int endWidth) {
        mStartWidth = startWidth;
        mDeltaWidth = endWidth - startWidth;
        mView = view;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        android.view.ViewGroup.LayoutParams lp = mView.getLayoutParams();
        lp.width = (int) (mStartWidth + mDeltaWidth * interpolatedTime);
        mView.setLayoutParams(lp);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
