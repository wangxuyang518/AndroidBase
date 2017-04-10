package com.xinyi_tech.commonlibs.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by studyjun on 2016/6/18.
 */
public class ExpandOvalAnimation extends Animation {

    private int mStartHeight;
    private int mDeltaHeight;
    private int mStartWidth;
    private int mDeltaWidth;
    Bitmap tpBitmap = null;

    private View mView;
    Canvas mCanvas = null;
    Paint mPaint = null;

    public ExpandOvalAnimation(View view, int startWidth, int endWidth, int startHeight, int endHeight) {
        mStartWidth = startWidth;
        mDeltaWidth = endWidth - startWidth;
        mStartHeight = startHeight;
        mDeltaHeight = endHeight - startHeight;
        mView = view;
        tpBitmap = Bitmap.createBitmap(endWidth, endHeight, Bitmap.Config.ARGB_4444);
        mCanvas = new Canvas(tpBitmap);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL); //实心

        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.width = endWidth;
        params.height = endHeight;
        view.setLayoutParams(params);
        mView.setBackgroundColor(Color.RED);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (mDeltaHeight < 0)
            mCanvas.drawCircle(mView.getRight(), mView.getTop(), mDeltaHeight*-1 * 2 * (1-interpolatedTime), mPaint);
        else
            mCanvas.drawCircle(mView.getRight(), mView.getTop(), mDeltaHeight * 2 * interpolatedTime, mPaint);
        mView.setBackgroundDrawable(new BitmapDrawable(tpBitmap));
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
