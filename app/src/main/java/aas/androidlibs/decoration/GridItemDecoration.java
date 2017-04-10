package aas.androidlibs.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by studyjun on 15/10/28.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public static final int UNIT_PX = 1;
    public static final int UNIT_DP = 2;


    public GridItemDecoration(int space, Context context, int unit) {
        if (unit == UNIT_DP) {
            this.space = UI.dip2px(context, space);
        } else {
            this.space = space;
        }
    }

    public GridItemDecoration(int spaceInDp, Context context) {

        this.space = UI.dip2px(context, spaceInDp);

    }

    public GridItemDecoration(int spaceInPx) {

        this.space = spaceInPx;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//         Add top margin only for the first item to avoid double space between items

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;


    }


}
