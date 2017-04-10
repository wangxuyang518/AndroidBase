package com.xinyi_tech.commonlibs.widget.form;

import android.os.Bundle;

/**
 * Created by studyjun on 2016/3/11.
 */
public interface IFormField {

    void setName(String name);

    String getName();

    boolean isVaild();

    boolean isMust();

    Object getValue();

    String warning();

    void setVaule(String value);

    void setExtraInfo(Bundle bundle);

    Bundle getExtraInfo();

}
