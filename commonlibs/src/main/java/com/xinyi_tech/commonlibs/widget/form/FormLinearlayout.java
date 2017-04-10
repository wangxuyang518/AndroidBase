package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.graphics.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.xinyi_tech.commonlibs.util.StringUtil;
import com.xinyi_tech.commonlibs.util.UI;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by studyjun on 2016/3/11.
 */
public class FormLinearlayout extends LinearLayout implements Form {


    public FormLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FormLinearlayout(Context context) {
        super(context);
    }


    public FormLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 按name来查找Field对应的值
     *
     * @param name
     * @return
     */
    public Object getValueWithName(String name) {
        return getValueWithName(this, name);
    }


    public Object getValueWithName(ViewGroup viewGroup, String name) {
        if (!StringUtil.isEmpty(name)) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof IFormField) {
                    if (name.equals(((IFormField) view).getName())) {
                        return ((IFormField) view).getValue();
                    }
                } else if (view instanceof ViewGroup) {
                    getValueWithName((ViewGroup) view, name);
                }
            }
        }
        return null;
    }


    public void getFormField(ViewGroup viewGroup, List<IFormField> list) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof IFormField) {
                list.add(((IFormField) view));
            } else if (view instanceof ViewGroup) {
                getFormField((ViewGroup) view, list);
            }
        }
    }

    /**
     * 获取表单字段
     * @return
     */
    public List<IFormField> getFields() {
        List<IFormField> formFields = new ArrayList<>();
        getFormField(this, formFields);
        return formFields;
    }


    /**
     * 检查表单样式
     */
    public boolean checkForm() {
        for (IFormField field : getFields()) {
            if (field.isMust()) {
                if (field.getValue()==null||StringUtil.isEmpty(field.getValue().toString())){
                    UI.toast(getContext(), field.warning());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 绑定数据
     * 用了反射，感觉速度为变慢
     * @param datas
     */
    public void bindData(Object datas){
        for (IFormField formField:getFields()){
            try {
                Field field = datas.getClass().getField(formField.getName());
                field.setAccessible(true);
                formField.setVaule(field.get(datas)+"");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e){ //未设置name字段会抛此错误
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置只读
     */
    public void setOnlyRead(boolean onlyRead){
        for (IFormField iFormField:getFields()){
            if (iFormField instanceof View){
                ((View) iFormField).setEnabled(!onlyRead);
            }
        }
    }


    /**
     * 获取form表单内的参数
     * @return
     */
    public Map<String,String> getParams() {
        checkForm();
        Map<String,String> map= new HashMap<>();
        for (IFormField f:getFields()){
            if (f.getValue() instanceof IFormValue){ //先判断有没有继承IFormValue接口
                if (!TextUtils.isEmpty(f.getName())&&f.getValue()!=null&&!TextUtils.isEmpty(((IFormValue)f.getValue()).toValue())){
                    map.put(f.getName(),f.getValue().toString());
                }
            } else {
                if (!TextUtils.isEmpty(f.getName())&&f.getValue()!=null&&!TextUtils.isEmpty(f.getValue().toString())){
                    map.put(f.getName(),getValue(f.getValue()));
                }
            }
        }

        return map;
    }

    /**
     * 获取form表单内的参数
     * @return
     */
    public Map<String,Bundle> getExtroInfo() {
        Map<String,Bundle> map= new HashMap<>();
        for (IFormField f:getFields()){
            if (!TextUtils.isEmpty(f.getName())&&f.getExtraInfo()!=null){
                map.put(f.getName(),f.getExtraInfo());
            }
        }
        return map;
    }


    /**
     * 根据注解来获取值
     * 如果有注解，首先会拿注解上的值，如果没有注解，会调用object的toString()方法
     * @param o
     * @return
     */
    public String getValue(Object o){
        try {

            Field[] fields = Class.forName(o.getClass().getName()).getFields();
            if (fields!=null){
                for (Field f:fields){
                    FormValue formValue = f.getAnnotation(FormValue.class);
                    if (formValue!=null){
                        return f.get(o).toString();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o.toString();
    }
}
