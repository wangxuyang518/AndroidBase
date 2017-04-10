package com.xinyi_tech.commonlibs.widget.rcv_decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinyi_tech.commonlibs.util.UI;


/**
 * Created by studyjun on 15/10/28.
 */
public class GridItemWithHeaderDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public static final int UNIT_PX = 1;
    public static final int UNIT_DP = 2;


    public GridItemWithHeaderDecoration(int space, Context context, int unit) {
        if (unit == UNIT_DP) {
            this.space = UI.dip2px(context, space);
        } else {
           this.space = space;
        }
    }

    public GridItemWithHeaderDecoration(int spaceInDp, Context context) {

        this.space = UI.dip2px(context, spaceInDp);

    }

    public GridItemWithHeaderDecoration(int spaceInPx) {

            this.space = spaceInPx;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//         Add top margin only for the first item to avoid double space between items
        if(parent.getChildLayoutPosition(view) > 0){
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        }


    }


}
