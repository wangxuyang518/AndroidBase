package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.xinyi_tech.commonlibs.R;


/**
 * Created by studyjun on 2016/3/23.
 */
public class CheckBoxField extends AppCompatCheckBox implements IFormCheckedField {

    private String name;
    private String value;


    public CheckBoxField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxField);
        value=ta.getString(R.styleable.CheckBoxField_value);
        name=ta.getString(R.styleable.CheckBoxField_name);
        ta.recycle();

    }

    @Override
    public boolean isVaild() {
        return false;
    }

    @Override
    public boolean isMust() {
        return false;
    }

    @Override
    public Object getValue() {
        return isChecked()?value:null;
    }

    @Override
    public String warning() {
        return null;
    }

    @Override
    public void setVaule(String value) {
        this.value=value;
    }


}
