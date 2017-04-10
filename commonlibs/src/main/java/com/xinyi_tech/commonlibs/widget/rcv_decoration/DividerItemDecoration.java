package com.xinyi_tech.commonlibs.widget.rcv_decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by studyjun on 15/12/23.
 * 默认的分割线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDivider;
    private int mOrientation;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider}; //use defaulst listdivider


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }

    }


    /**
     * use divider with system default
     * @param context
     * @param orientation
     */
    public DividerItemDecoration(Context context,int orientation) {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        if (orientation!=VERTICAL&&orientation!=HORIZONTAL){
            throw new IllegalArgumentException("invaild orientation");
        } else {
            mOrientation = orientation;
        }
    }

    /**
     * use divider with drawable
     * @param drawable
     * @param orientation
     */
    public DividerItemDecoration(Drawable drawable,int orientation) {
        mDivider = drawable;
        if (orientation!=VERTICAL&&orientation!=HORIZONTAL){
            throw new IllegalArgumentException("invaild orientation");
        } else {
            mOrientation = orientation;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation==VERTICAL){
            drawVertial(c,parent);
        } else {
            drawHorizontal(c,parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertial(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
