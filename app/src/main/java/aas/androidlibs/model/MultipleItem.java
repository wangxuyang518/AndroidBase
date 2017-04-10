package aas.androidlibs.model;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by $ wxy on 2017/4/7.
 */

public class MultipleItem implements MultiItemEntity {

    public int itemType;

    @LayoutRes
    public int layoutres;

    public String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
    public MultipleItem(){

    }
    public MultipleItem(int itemType,int layoutres){
         this.itemType=itemType;
         this.layoutres= layoutres;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getLayoutres() {
        return layoutres;
    }

    public void setLayoutres(int layoutres) {
        this.layoutres = layoutres;
    }
}
