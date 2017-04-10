package com.xinyi_tech.commonlibs.widget.indicator;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi_tech.commonlibs.R;


/**
 * Created by studyjun on 15/12/23.
 */
public class SimplePagerIndicator extends LinearLayout implements PageIndicator {


    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int mCurrentPosition;
    private String mCurrentTitle;

    public SimplePagerIndicator(Context context) {
        super(context);
    }

    public SimplePagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimplePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置viewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        init();
    }

    @Override
    public void setViewPager(ViewPager viewPager, int initPosition) {
        setViewPager(viewPager);
        setCurrentItem(initPosition);

    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("viewpage is null in indicator");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPosition = item;
        invalidate();

    }

    @Override
    public void setPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.pageChangeListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            final int position = i;
            TextView tv = new TextView(getContext());
            tv.setText(getTitle(i));
            tv.setGravity(Gravity.CENTER);
            int padding =getResources().getDimensionPixelOffset(R.dimen.spacing_4dp);
            tv.setPadding(2*padding, padding, 2*padding, padding);

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentItem(position);
                }
            });
            if (mCurrentPosition==i){
                tv.setTextColor(getResources().getColor(R.color.focus_text));
            } else {
                tv.setTextColor(getResources().getColor(R.color.unfocus_text));
            }
            addView(tv);
        }
//        setIndicator();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageSelected(position);
        }
        mCurrentPosition = position;
        setIndicator();

    }

    private void setIndicator() {
//        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
//            if (i==mCurrentPosition){
//                ((TextView) getChildAt(mCurrentPosition)).setTextColor(getResources().getColor(R.color.focus_text));
//            } else {
//                ((TextView) getChildAt(mCurrentPosition)).setTextColor(getResources().getColor(R.color.unfocus_text));
//            }
//        }
        removeAllViews();
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            final int position =i;
            TextView tv = new TextView(getContext());
            tv.setText(getTitle(i));
            tv.setGravity(Gravity.CENTER);
            int padding =getResources().getDimensionPixelOffset(R.dimen.spacing_4dp);
            tv.setPadding(2*padding, padding, 2*padding, padding);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentItem(position);
                }
            });

            if (mCurrentPosition==i){
                tv.setTextColor(getResources().getColor(R.color.focus_text));
            } else {
                tv.setTextColor(getResources().getColor(R.color.unfocus_text));
            }
            addView(tv);
        }
//        requestLayout();
//        invalidate();

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public CharSequence getTitle(int i) {
        return mViewPager.getAdapter().getPageTitle(i);
    }
}
