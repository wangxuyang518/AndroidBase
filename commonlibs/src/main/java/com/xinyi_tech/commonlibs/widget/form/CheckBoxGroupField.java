package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.xinyi_tech.commonlibs.R;
import com.xinyi_tech.commonlibs.widget.LinearLineWrapLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by studyjun on 2016/4/25.
 */
public class CheckBoxGroupField extends LinearLineWrapLayout implements IFormField {

    private String name;
    private boolean must;
    private String warnText;
    private Bundle extraInfo;

    public CheckBoxGroupField(Context context) {
        super(context);
    }

    public CheckBoxGroupField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxGroupField);
        must = ta.getBoolean(R.styleable.CheckBoxGroupField_must, false);
        warnText = ta.getString(R.styleable.CheckBoxGroupField_warnText);
        name = ta.getString(R.styleable.CheckBoxGroupField_name);
        ta.recycle();
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
        return isShown()? must : false;//不可见情况下，不拿值
    }

    @Override
    public Object getValue() {
        if (!isShown()) //不可见情况下，不拿值
            return null;
        List<String> values = null;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null && view instanceof IFormCheckedField && ((IFormCheckedField) view).isChecked()) {
                if (values == null) {
                    values = new ArrayList<>();
                }
                values.add(((IFormCheckedField) view).getValue() + "");
            }
        }

        return values;
    }

    @Override
    public String warning() {
        return warnText;
    }

    @Override
    public void setVaule(String value) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof IFormCheckedField) {
                if (value != null && ((IFormCheckedField) view).getValue().equals(value))
                    ((IFormCheckedField) view).setChecked(true);
            }
        }
    }

    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo = bundle;
    }

    /**
     * 设置值域
     *
     * @param values
     */
    public void setVaule(String... values) {
        for (String value : values) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view instanceof IFormCheckedField) {
                    if (value != null && ((IFormCheckedField) view).getValue().equals(value))
                        ((IFormCheckedField) view).setChecked(true);
                }
            }
        }
    }

    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }
}
