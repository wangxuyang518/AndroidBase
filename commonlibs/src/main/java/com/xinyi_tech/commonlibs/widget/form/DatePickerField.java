package com.xinyi_tech.commonlibs.widget.form;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by studyjun on 2016/5/4.
 */
public class DatePickerField extends TextView implements IFormField{

    private String name;
    private boolean must;
    private String warnText;
    private Bundle extraInfo;

//    DatePickerDialog

    public DatePickerField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
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
        return null;
    }

    @Override
    public String warning() {
        return null;
    }

    @Override
    public void setVaule(String value) {

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
