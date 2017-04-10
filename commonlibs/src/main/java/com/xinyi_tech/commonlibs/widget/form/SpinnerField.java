package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xinyi_tech.commonlibs.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by studyjun on 2016/3/12.
 */
public class SpinnerField<T> extends AppCompatSpinner implements IFormField{

    private String name;
    private boolean must;
    private String warnText;
    private boolean isFristInit=true;
    private Bundle extraInfo;


    public SpinnerField(Context context) {
        super(context);

    }

    public SpinnerField(Context context, int mode) {
        super(context, mode);
    }

    public SpinnerField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpinnerField);
        must=ta.getBoolean(R.styleable.SpinnerField_must,false);
        warnText=ta.getString(R.styleable.SpinnerField_warnText);
        name=ta.getString(R.styleable.SpinnerField_name);
        ta.recycle();
        setDropItems();
    }


    @Override
    public void setName(String name) {
        this.name =name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isVaild() {
        if (isMust()){
            if (null==getValue()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMust() {
        return must;
    }

    @Override
    public String getValue() {
        if (getSelectedItem()instanceof IFormValue) {
            return ((IFormValue)getSelectedItem()).toValue();
        } else {
           return getSelectedItem()==null?"":getValue(getSelectedItem());
        }
    }

    @Override
    public String warning() {
        return warnText;
    }

    @Override
    public void setVaule(String value) {
        if (null==value)
            return;
        for (int i=0;i<getCount();i++){
            if (value.equals(getValue(getItemAtPosition(i)))){
                setSelection(i);
                return;
            }
        }
    }

    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo= bundle;
    }

    /**
     * 设置下拉值
     * @param items
     */
    public ArrayAdapter<T> setDropItems(T... items){
        ArrayAdapter<T> adapter= new ArrayAdapter<T>(getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        setAdapter(adapter);
        return adapter;
    }


    /**
     * 设置下拉值
     * @param items
     */
    public ArrayAdapter<T> setDropItems(List<T> items){
        ArrayAdapter<T> adapter= new ArrayAdapter<T>(getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        setAdapter(adapter);
        return adapter;
    }

    /**
     * 根据注解来获取值
     * 如果有注解，首先会拿注解上的值，如果没有注解，会调用object的toString()方法
     * @param o
     * @return
     */
    public String getValue(Object o){
        try {
            Field[] fields = Class.forName(o.getClass().getName()).getFields();
            if (fields!=null){
                for (Field f:fields){
                    FormValue formValue = f.getAnnotation(FormValue.class);
                    if (formValue!=null){
                        return f.get(o).toString();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o.toString();
    }


    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }
    /**
     * 设置默认选中状态，需要在setAdapter
     * 没实现
     * @param selected
     */
    public void setDefaultSelected(boolean selected){
//        if (!selected){
//            Class<T> c = T.class;
//
//        }
    }
}
