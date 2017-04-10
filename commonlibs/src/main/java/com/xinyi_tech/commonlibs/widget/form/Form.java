package com.xinyi_tech.commonlibs.widget.form;

import java.util.List;
import java.util.Map;

/**
 * Created by studyjun on 2016/3/11.
 * form表单
 */
public interface Form {

    String MediaType_MULTIPART="multipart/form-data";

    void bindData(Object o);

    boolean checkForm();

    Map<String,String> getParams();

    List<IFormField> getFields();

    void setOnlyRead(boolean onlyRead);

}
