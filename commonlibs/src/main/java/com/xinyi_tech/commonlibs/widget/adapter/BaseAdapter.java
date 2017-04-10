package com.xinyi_tech.commonlibs.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyi_tech.commonlibs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by studyjun on 15/12/22.
 */
public abstract class BaseAdapter<T extends Object> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int STATE_EMPTY_ITEM = 100;
    public static final int STATE_NO_DATA = 101;
    public static final int STATE_NO_MORE = 102;
    public static final int STATE_NETWORK_ERROR = 103;
    public static final int STATE_LESS_ONE_PAGE = 104;
    public static final int STATE_LOAD_MORE = 105;
    public static final int STATE_OTHER = 106;
    public static final int STATE_ERROR = 121;

    public static final int BASE_FOOTER = 107;
    public static final int BASE_CONTENT = 108;
    public static final int BASE_HEADER = 109;

    private View mFootView;

    protected ArrayList<T> datas = new ArrayList<T>();

    private int state = STATE_LESS_ONE_PAGE;

    public List<T> getData() {
        return datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BASE_CONTENT) {
            return onCreateViewContentHolder(parent, viewType);
        } else if (viewType==BASE_HEADER) {
            return onCreateViewHeaderHolder(parent,viewType);
        }else {
            switch (state) {
                case STATE_EMPTY_ITEM:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_LOAD_MORE:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_NETWORK_ERROR:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_NO_MORE:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_NO_DATA:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_LESS_ONE_PAGE:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                case STATE_ERROR:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
                default:
                    mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
                    return new FootViewHolder(mFootView);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (position == getItemCount() - 1 && hasFooter()) {
            type = BASE_FOOTER;
        } else if (position==0&&hasHeader() ){
            type = BASE_HEADER;
        } else {
            type = BASE_CONTENT;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == BASE_FOOTER) {
            switch (state) {
                case STATE_EMPTY_ITEM:
                    holder.itemView.findViewById(R.id.item_foot_progress).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_foot_text).setVisibility(View.VISIBLE);
                    ((TextView)holder.itemView.findViewById(R.id.item_foot_text)).setText(R.string.no_data);
                    break;
                case STATE_LOAD_MORE:
                    holder.itemView.findViewById(R.id.item_foot_progress).setVisibility(View.VISIBLE);
                    holder.itemView.findViewById(R.id.item_foot_text).setVisibility(View.VISIBLE);
                    ((TextView)holder.itemView.findViewById(R.id.item_foot_text)).setText(R.string.loadding);
                    break;
                case STATE_NETWORK_ERROR:
                    holder.itemView.findViewById(R.id.item_foot_progress).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_foot_text).setVisibility(View.VISIBLE);
                    ((TextView)holder.itemView.findViewById(R.id.item_foot_text)).setText(R.string.network_error);
                    break;
                case STATE_NO_MORE:
                    holder.itemView.findViewById(R.id.item_foot_progress).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_foot_text).setVisibility(View.VISIBLE);
                    ((TextView)holder.itemView.findViewById(R.id.item_foot_text)).setText(R.string.has_not_more);
                    break;
                case STATE_ERROR:
                    holder.itemView.findViewById(R.id.item_foot_progress).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.item_foot_text).setVisibility(View.VISIBLE);
                    ((TextView)holder.itemView.findViewById(R.id.item_foot_text)).setText(R.string.data_error);
                    break;
                case STATE_NO_DATA:
                    break;
                default:
                    break;

            }
        } else if (getItemViewType(position) == BASE_CONTENT){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(holder.itemView,getData().get(position));
                    }
                }
            });
            onBindContentViewHolder(holder, position);
        } else {
            onBindHeaderViewHolder(holder, position);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateViewContentHolder(ViewGroup parent, int viewType);

    protected abstract void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position);


    protected  RecyclerView.ViewHolder onCreateViewHeaderHolder(ViewGroup parent, int viewType){
        return null;
    }

    protected  void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position){

    }

    protected boolean hasFooter() {
        return true;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        switch (state) {
            case STATE_EMPTY_ITEM:
                size = 1;
                break;
            case STATE_NO_DATA:
                size = 1;
                break;
            case STATE_NO_MORE:
                size = getSizePlus1();
                break;
            case STATE_NETWORK_ERROR:
                size = 1;
                break;
            case STATE_LESS_ONE_PAGE:
                size = getSize();
                break;
            case STATE_LOAD_MORE:
                size = getSizePlus1();
                break;
            case STATE_OTHER:
                break;
            default:
                size = getSize();
        }
        if (hasHeader())
            size+=1;
        return size;
    }

    public int getState() {
        return state;
    }


    /**
     * 获取size+1
     *
     * @return
     */
    public int getSizePlus1() {
        return datas.size() + 1;

    }

    public int getSize() {
        return datas.size();
    }

    public void setState(int state) {
        this.state = state;
    }



    /**
     *
     */
    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 获取footView
     *
     * @return
     */
    public View getFooterView() {
        return mFootView;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface  OnItemClickListener<T>{
        void onItemClick(View view,T o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }

    public boolean hasHeader(){
        return false;
    }
}
