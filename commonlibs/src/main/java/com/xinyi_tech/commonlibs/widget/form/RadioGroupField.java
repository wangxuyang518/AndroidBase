package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.xinyi_tech.commonlibs.R;


/**
 * Created by studyjun on 2016/3/11.
 */
public class RadioGroupField extends RadioGroup implements IFormField{

    private String name;
    private boolean must;
    private String warnText;
    private Bundle extraInfo;

    public RadioGroupField(Context context) {
        super(context);
    }

    public RadioGroupField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextEditField);
        must=ta.getBoolean(R.styleable.TextEditField_must,false);
        warnText=ta.getString(R.styleable.TextEditField_warnText);
        name=ta.getString(R.styleable.TextEditField_name);
        ta.recycle();
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
        return false;
    }

    @Override
    public boolean isMust() {
        return must;
    }

    @Override
    public Object getValue() {
        View view =findViewById(getCheckedRadioButtonId());
        if (view!=null && view instanceof IFormCheckedField){
            return ((IFormCheckedField) view).getValue();
        }
        return null;
    }

    @Override
    public String warning() {
        return warnText;
    }

    @Override
    public void setVaule(String value) {
        for (int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if (view instanceof IFormCheckedField){
                if (value!=null&&((IFormCheckedField) view).getValue().equals(value))
                    ((IFormCheckedField) view).setChecked(true);
            }
        }
    }

    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }
    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo= bundle;
    }
}
