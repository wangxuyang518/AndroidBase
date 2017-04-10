package com.xinyi_tech.commonlibs.widget.indicator;

import android.support.v4.view.ViewPager;

/**
 * Created by studyjun on 15/12/23.
 * example see as https://github.com/JakeWharton/ViewPagerIndicator/blob/master/library/src/com/viewpagerindicator/PageIndicator.java
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener{

    /**
     * bing a indicator with a viewpager
     * @param viewPager
     */
    void setViewPager(ViewPager viewPager);


    /**
     * bind a indicator with viewpager and initial the viewpager position
     * @param viewPager
     * @param initPosition
     */
    void setViewPager(ViewPager viewPager,int initPosition);

    /**
     * set the current page of both viewpager and indicator
     * @param item
     */
    void setCurrentItem(int item);


    /**
     * set viewpagerchangelistener replace in default viewpager listener
     * @param listener
     */
    void setPageChangeListener(ViewPager.OnPageChangeListener listener);


    /**
     * notify the indicator that the fragment list has changed
     */
    void notifyDataSetChanged();
}
