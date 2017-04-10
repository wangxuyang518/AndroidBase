package aas.androidlibs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aas.androidlibs.adapter.PullToRefreshAdapter;
import aas.androidlibs.decoration.DividerItemDecoration;
import aas.androidlibs.model.MultipleItem;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by $ wxy on 2017/4/7.
 * 上拉下拉的 listview...
 *
 * 以后将 网络获取数据的代码。单独的放在一个类里面
 */

public class PullToRefreshActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int TOTAL_COUNTER = 18;//总共的数据
    private static final int PAGE_SIZE = 6;//获取数据
    private int mCurrentCounter = 0;//保存PAGE_SIZE
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;
    List<MultipleItem> mList=new ArrayList<MultipleItem>();
    RecyclerView mRecycleView;
    SwipeRefreshLayout swipelayout;
    PullToRefreshAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        mRecycleView= (RecyclerView) findViewById(R.id.mrecycleview);
        swipelayout= (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        initData();
        initView();
    }
    private void initData() {
        for (int i=0;i<10;i++){
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
        adapter=new PullToRefreshAdapter(R.layout.multi_item1,mList);
    }

    private void initView() {
        swipelayout.setOnRefreshListener(this);
        swipelayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        View view=View.inflate(PullToRefreshActivity.this,R.layout.head_view,null);
        view.setOnClickListener(v -> {
            adapter.setLoadMoreView(new CustomLoadMoreView());
            mRecycleView.setAdapter(adapter);
            Toast.makeText(PullToRefreshActivity.this, "点击", Toast.LENGTH_SHORT).show();
        });
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnLoadMoreListener(this,mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(PullToRefreshActivity.this, LinearLayout.VERTICAL);
        itemDecoration.setmDivider(10);
        mRecycleView.addItemDecoration(itemDecoration);
        mRecycleView.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();
        adapter.addHeaderView(view);

    }

    @Override
    public void onLoadMoreRequested() {
        swipelayout.setRefreshing(false);//禁止下拉，在上拉的时候
        adapter.setEnableLoadMore(false);//禁止上拉加载更多，在下拉的时候
       /* Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                 *//* adapter.addData(getSampleData(10));
                    adapter.loadMoreComplete();*//*
                    //adapter.setEnableLoadMore(false);
                    //adapter.loadMoreEnd();没有更多数据
                     //adapter.setEnableLoadMore(false);
                    //adapter.loadMoreFail();//加载失败显示的视图
                });*/
    }

    @Override
    public void onRefresh() {
        //adapter.setAutoLoadMoreSize(10);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setEnableLoadMore(false);//禁止上拉加载更多，在下拉的时候
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    adapter.setNewData(getSampleData(10));
                    isErr = false;
                    mCurrentCounter = PAGE_SIZE;
                    swipelayout.setRefreshing(false);
                    adapter.setEnableLoadMore(true);
                    /**
                     * 这里应该有一个判断 判断请求的个数是不是少于10个
                     * 假如少于 10个 应该设置成没有更多数据或者 隐藏footview
                     * 假如大于10个  应该将加载更多设置成true
                     */
                });
    }

    public List<MultipleItem> getSampleData(int page){
        List<MultipleItem> List=new ArrayList<MultipleItem>();
        for (int i=0;i<page;i++)
        {
            MultipleItem item=new MultipleItem();
            item.setContent("新添加的item"+i);
            List.add(item);
        }
        return List;
    }
}
