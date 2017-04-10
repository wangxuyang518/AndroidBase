package com.xinyi_tech.commonlibs.widget.form;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi_tech.commonlibs.util.StringUtil;


/**
 * Created by studyjun on 2016/3/11.
 */
public class TextEditForm extends LinearLayout implements IFormField{

    private TextView mLabelV;
    private EditText mEditV;
    private String name;
    private Bundle extraInfo;

    public TextEditForm(Context context) {
        super(context);
        init();
    }

    @Override
    public Bundle getExtraInfo() {
        return extraInfo;
    }

    public TextEditForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextEditForm(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TextEditForm(Context context,String label ,String hint) {
        super(context);
        init();
        setLabel(label);
        setEditVHint(hint);
    }


    protected void init(){
        setOrientation(HORIZONTAL);
        mEditV = new EditText(getContext());
        mLabelV= new TextView(getContext());
        addView(mLabelV);
        addView(mEditV);
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
        if (isMust()){
            if (StringUtil.isEmpty(mLabelV.getText().toString())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMust() {
        return false;
    }

    @Override
    public Object getValue() {
        return mEditV.getText().toString();
    }

    @Override
    public String warning() {
        return "不能为空";
    }

    @Override
    public void setVaule(String value) {
        mEditV.setText(value);
    }

    @Override
    public void setExtraInfo(Bundle bundle) {
        this.extraInfo= bundle;
    }


    public void setClomn1Width(int px){
        mLabelV.setWidth(px);
    }


    public void setClomn2Width(int px){
        mEditV.setWidth(px);
    }



    public void setClomn1Weight(float weight){
        mLabelV.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight));
    }

    public void setClomn2Weight(float weight) {
        mEditV.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight));
    }


    public void setLabel(String label){
        mLabelV.setText(label);
    }

    public void setEditText(String text){
        mEditV.setText(text);
    }

    public void setEditVHint(String hint){
        mEditV.setHint(hint);
    }



}
