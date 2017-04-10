package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.xinyi_tech.commonlibs.R;

/**
 * Created by studyjun on 2016/3/11.
 */
public class RadioButtonField extends RadioButton implements IFormCheckedField {

    String mVaule;
    String mName;


    public RadioButtonField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonField);
        mVaule = ta.getString(R.styleable.RadioButtonField_value);
        mName= ta.getString(R.styleable.RadioButtonField_name);

    }



    @Override
    public boolean isVaild() {
        return true;
    }

    @Override
    public boolean isMust() {
        return false;
    }

    public Object getValue(){
        return mVaule;
    }

    @Override
    public String warning() {
        return null;
    }

    @Override
    public void setVaule(String value) {
        this.mVaule=value;
    }
}
