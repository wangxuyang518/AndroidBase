package aas.androidlibs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import aas.androidlibs.model.MultipleItem;

/**
 * Created by $ wxy on 2017/4/7.
 */

public class PullToRefreshAdapter extends BaseQuickAdapter<MultipleItem, BaseViewHolder> {


    public PullToRefreshAdapter(int layoutResId, List<MultipleItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

    }
}
