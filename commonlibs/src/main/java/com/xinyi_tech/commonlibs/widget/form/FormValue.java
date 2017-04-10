package com.xinyi_tech.commonlibs.widget.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by studyjun on 2016/5/3.
 * 当字段注解该注解时，form表单会取该值作为value
 * see @IFormValue 也有类似的功能
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormValue {

}
