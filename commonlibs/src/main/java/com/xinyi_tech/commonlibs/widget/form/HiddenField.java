package com.xinyi_tech.commonlibs.widget.form;

import android.os.Bundle;

/**
 * Created by studyjun on 2016/5/3.
 */
public class HiddenField implements IFormField {

    private String name;
    private String value;
    private Bundle extraInfo;

    @Override
    public void setName(String name) {
        this.name=name;
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
        return false;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String warning() {
        return null;
    }

    @Override
    public void setVaule(String value) {
        this.value=value;
    }
    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo= bundle;
    }

    public HiddenField() {
    }

    public HiddenField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }
}
