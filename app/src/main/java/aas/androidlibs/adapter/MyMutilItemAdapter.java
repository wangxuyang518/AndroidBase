package aas.androidlibs.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import aas.androidlibs.R;
import aas.androidlibs.model.MultipleItem;

/**
 * Created by $ wxy on 2017/4/7.
 */

public class MyMutilItemAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>{

    private Context context;
    private List<MultipleItem>data;
    public MyMutilItemAdapter(List<MultipleItem> data) {
        super(data);
    }

    public MyMutilItemAdapter(Context context, List<MultipleItem>data) {
        super(data);
        this.context=context;
        this.data=data;
        MultipleItem item1=new MultipleItem(0, R.layout.multi_item1);
        MultipleItem item2=new MultipleItem(1, R.layout.multi_item2);
        addItemType(item1.getItemType(),item1.getLayoutres());
        addItemType(item2.getItemType(),item2.getLayoutres());
    }
    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.item1,item.getContent());
                break;
            case 1:
                helper.setText(R.id.item2,item.getContent());
                break;
        }
    }
}
