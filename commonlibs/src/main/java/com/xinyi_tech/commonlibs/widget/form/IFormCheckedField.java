package com.xinyi_tech.commonlibs.widget.form;

/**
 * Created by studyjun on 2016/3/11.
 */
public interface IFormCheckedField {



    boolean isVaild();

    boolean isMust();

    Object getValue();

    String warning();

    void setVaule(String value);

    boolean isChecked();

    void setChecked(boolean checked);

}
