package aas.androidlibs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import aas.androidlibs.adapter.MyMutilItemAdapter;
import aas.androidlibs.model.MultipleItem;

/**
 * Created by $ wxy on 2017/4/7.
 */

public class MultiItemActivity extends AppCompatActivity {

    List<MultipleItem>mList=new ArrayList<MultipleItem>();
    RecyclerView mRecycleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item);
        mRecycleView= (RecyclerView) findViewById(R.id.mrecycleview);
        initData();
        initView();

    }
    private void initData() {
        for (int i=0;i<59;i++){
            MultipleItem item=new MultipleItem();
            if (i%2==0){
                item.setItemType(0);
                item.setContent("这是第一种类型item"+i);
            }else {
                item.setItemType(1);
                item.setContent("这是第二种类型item"+i);
            }
            mList.add(item);
        }
    }
    private void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        MyMutilItemAdapter adapter=new MyMutilItemAdapter(MultiItemActivity.this,mList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Toast.makeText(MultiItemActivity.this, ""+position, Toast.LENGTH_SHORT).show();
        });
        mRecycleView.setAdapter(adapter);
    }
}
